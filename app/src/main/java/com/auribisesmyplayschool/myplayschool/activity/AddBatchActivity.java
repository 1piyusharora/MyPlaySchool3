package com.auribisesmyplayschool.myplayschool.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.auribisesmyplayschool.myplayschool.Helper.Constants;
import com.auribisesmyplayschool.myplayschool.R;
import com.auribisesmyplayschool.myplayschool.adminApp.bean.BranCourBean;
import com.auribisesmyplayschool.myplayschool.bean.BatchBean;
import com.auribisesmyplayschool.myplayschool.bean.CourseBean;
import com.auribisesmyplayschool.myplayschool.classes.AttUtil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import dmax.dialog.SpotsDialog;

public class AddBatchActivity extends AppCompatActivity {
    EditText edtBatch,edtSeat,edtTime,edtYear,edtCourseHours,tvStartDate;
    Spinner spnbatchcourse;
    Button btnSubmit;
    BatchBean batchBean,rcvBean;
    boolean bool,updateMode = false;
    int reqCode,branchId,arraysize,spnselection;
    Toolbar toolbar;
    int batchId;
    SharedPreferences pref;
    ArrayList arrayListcourse,arrayListCourseHour;
    ArrayList<Integer> arrayListcourseId;
    //ArrayList<CourseBean> courseBeanArrayList;
    ArrayList<BranCourBean> courseBeanArrayList;
    DatePickerDialog datePickerDialog;
    Calendar calendar;
    int c_year,c_month,c_day,pos=0;
    FirebaseFirestore db;
    SpotsDialog progressDialog;

    boolean validate(){
        bool = true;
        if(batchBean.getBatch_title().isEmpty())
        {
            bool = false;
            edtBatch.setError(AttUtil.error);
        }
        if(batchBean.getBatch_seat().isEmpty())
        {
            bool = false;
            edtSeat.setError(AttUtil.error);
        }
//        if(batchBean.getBatch_time().isEmpty())
//        {
//            bool = false;
//            edtTime.setError(AttUtil.error);
//        }
        if(batchBean.getBatch_year().isEmpty()) {
            bool = false;
            edtYear.setError(AttUtil.error);
        }
//        if(batchBean.getBatch_start_date().isEmpty()) {
//            bool = false;
//            tvStartDate.setError(AttUtil.error);
//        }
        if(batchBean.getBranCourId()==0) {
            bool = false;
            Toast.makeText(AddBatchActivity.this,"Kindly Select Class",Toast.LENGTH_SHORT).show();
        }
        return bool;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_batch);
        db = FirebaseFirestore.getInstance();
        pref=getSharedPreferences(AttUtil.shpREG, MODE_PRIVATE);
        branchId = pref.getInt(AttUtil.shpBranchId, 0);
        Intent i = getIntent();
        updateMode = i.hasExtra(AttUtil.KEY_BATCH);
        if(updateMode) {
            rcvBean = (BatchBean)i.getSerializableExtra(AttUtil.KEY_BATCH);
            Toast.makeText(AddBatchActivity.this,""+rcvBean.getBatchId(),Toast.LENGTH_SHORT).show();
        }//else{
            courseBeanArrayList = (ArrayList<BranCourBean>) i.getSerializableExtra(AttUtil.KEY_COURSE_ARRAYLIST);
         Toast.makeText(AddBatchActivity.this,""+courseBeanArrayList.size(),Toast.LENGTH_SHORT).show();
       // }
        init();
        if(AttUtil.isNetworkConnected(AddBatchActivity.this)){
            retrievecourse();
        }else{
            //courseNetConnect();
        }
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy" );
        edtYear.setText(sdf.format(new Date()));
        edtYear.setInputType(InputType.TYPE_NULL);
        calendar=Calendar.getInstance();
        c_year=calendar.get(Calendar.YEAR);
        c_month=calendar.get(Calendar.MONTH);
        c_day=calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(this,onDateSetListener,c_year,c_month,c_day);
        tvStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnClick();
            }
        });
        edtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeShowPicker();
            }
        });
    }

     void retrieveBatchId() {
         db.collection(Constants.branchCollection).
                 document(String.valueOf(branchId)).
                 collection(Constants.branch_course_collection).document(String.valueOf(pos)).
                 collection(Constants.batch_section_collection).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
             @Override
             public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                 if(queryDocumentSnapshots.size()>0){
                     for(DocumentChange doc: queryDocumentSnapshots.getDocumentChanges()){
                         batchId = doc.getDocument().getLong("batchId").intValue();
                     }
                 }else{
                     batchId = 0;
                 }

             }
         });
    }

    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            monthOfYear=monthOfYear+1;
            tvStartDate.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
        }
    };

    void btnClick(){
        progressDialog = new SpotsDialog(AddBatchActivity.this, R.style.Custom);
        progressDialog.show();
       // retrieveBatchId();
        setData();
        Log.i("batchBean",batchBean.toString());
        if(validate()){
            if(AttUtil.isNetworkConnected(AddBatchActivity.this)){
                if(!updateMode){
                    addBatch();
                }else{
                    updateBatch();
                }

            }else {
                //batchNetConnect();
            }
        }
    }

    //CONTINUE
     void updateBatch() {
        db.collection(Constants.branchCollection).document(String.valueOf(branchId))
                .collection(Constants.branch_course_collection).document(String.valueOf(pos))
                .collection(Constants.batch_section_collection).document(String.valueOf(rcvBean.getBatchId())).set(batchBean).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AddBatchActivity.this,"UPDATED",Toast.LENGTH_SHORT).show();
            }
        });
    }

    void addBatch() {
        db.collection(Constants.branchCollection).
                document(String.valueOf(branchId)).
                collection(Constants.branch_course_collection).document(String.valueOf(pos)).
                collection(Constants.batch_section_collection).
                document(String.valueOf(batchId+1)).set(batchBean).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressDialog.dismiss();
                clearFields();
                batchId = 0;
                Toast.makeText(AddBatchActivity.this,"Succesfully added",Toast.LENGTH_SHORT).show();
            }
        });

    }
    void clearFields()
    {
        edtBatch.setText("");
        edtSeat.setText("");
        edtTime.setText("");
        //edtYear.setText("");
        edtCourseHours.setText("");
        tvStartDate.setText("");
        spnbatchcourse.setSelection(0);
        edtBatch.requestFocus();
    }

    void timeShowPicker() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int mint = cal.get(Calendar.MINUTE);

        TimePickerDialog tp = new TimePickerDialog(this,tpd,hour,mint,true);
        tp.show();
    }

    TimePickerDialog.OnTimeSetListener tpd = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute ) {
            // TODO Auto-generated method stub

            String timeSet = "";
            if (hourOfDay > 12) {
                hourOfDay -= 12;
                timeSet = "PM";
            } else if (hourOfDay == 0) {
                hourOfDay += 12;
                timeSet = "AM";
            } else if (hourOfDay == 12)
                timeSet = "PM";
            else
                timeSet = "AM";


            String minutes = "";
            if (minute < 10){
                minutes = "0" + minute;
            }else{
                minutes = String.valueOf(minute);
            }
            edtTime.setText(hourOfDay + ":" + minutes +" "+timeSet);
        }
    };

    private void retrievecourse() {
        if(arrayListcourse.size()>0)
            arrayListcourse.clear();
        else
            arrayListcourse.add("--Select Class--");
        //arrayListCourseHour.clear();
        arrayListCourseHour.add("--Select Class First--");
      //  arrayListcourseId.clear();
        arrayListcourseId.add(0);

        if(courseBeanArrayList!=null && courseBeanArrayList.size()>0){
            for(BranCourBean cb : courseBeanArrayList){
                    arrayListcourse.add(cb.getCourseName());
                    arrayListCourseHour.add(cb.getCourseHours());
                    arrayListcourseId.add(cb.getBranCourId());
            }
            if(updateMode){
                for(int i=0;i<arrayListcourseId.size();i++){
                    if(rcvBean.getBranCourId()==arrayListcourseId.get(i)){
                        spnselection=i;
                        break;
                    }
                }
            }
            spnbatchcourse.setAdapter(new ArrayAdapter(AddBatchActivity.this, android.R.layout.simple_dropdown_item_1line, arrayListcourse));
            spnbatchcourse.setOnItemSelectedListener(onItemSelectedListener);
            spnbatchcourse.setSelection(spnselection);
        }

    }

    AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            pos = position;
            if(position==0){
                batchBean.setBranCourId(0);
            }else{
               // batchBean.setBranCourId(arrayListcourseId.get(position));
               // batchBean.setBatch_course(arrayListcourse.get(position).toString());
                //edtCourseHours.setText(arrayListCourseHour.get(position).toString());
            }
            if(!updateMode){
                retrieveBatchId();
            }

           // Toast.makeText(AddBatchActivity.this,""+batchId,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    void init(){

       // toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_activity_add_batch);

        edtBatch = (EditText)findViewById(R.id.edtBatch);
        edtSeat = (EditText)findViewById(R.id.edtSeat);
        edtTime = (EditText)findViewById(R.id.edtTime);
        edtYear = (EditText)findViewById(R.id.edtyear);
        edtCourseHours =(EditText)findViewById(R.id.edt_course_hours);
        tvStartDate = (EditText)findViewById(R.id.tv_StartDate);
        spnbatchcourse = (Spinner)findViewById(R.id.spn_batch_course);
        btnSubmit = (Button)findViewById(R.id.batch_submit);
       // requestQ_v= Volley.newRequestQueue(this);
        if(!updateMode){
            batchBean =  new BatchBean();
        }
       // courseBeanArrayList = new ArrayList<>();
        arrayListcourse = new ArrayList();
        arrayListCourseHour = new ArrayList();
        arrayListcourseId = new ArrayList<>();
        if(updateMode) {
            edtBatch.setText(rcvBean.getBatch_title());
            edtSeat.setText(rcvBean.getBatch_seat());
            edtTime.setText(rcvBean.getBatch_time());
            edtYear.setText(rcvBean.getBatch_year());
            tvStartDate.setText(rcvBean.getBatch_start_date());
            btnSubmit.setText("Update Section");
            getSupportActionBar().setTitle(R.string.action_update_batch);
        }
    }

    void setData(){
        if(updateMode){
            batchBean.setBatchId(rcvBean.getBatchId());
            batchBean.setBranchId(rcvBean.getBranchId());
        }else{
            batchBean.setBranchId(branchId);
            batchBean.setBatchStatus(1);
            batchBean.setBatchId(batchId+1);
        }
        batchBean.setCoursetitle(courseBeanArrayList.get(pos-1).getCourseName());
        batchBean.setBatch_start_date(tvStartDate.getText().toString());
        batchBean.setBatch_title(edtBatch.getText().toString());
        batchBean.setBatch_seat(edtSeat.getText().toString());
        batchBean.setBatch_time(edtTime.getText().toString());
        batchBean.setBatch_year(edtYear.getText().toString());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
