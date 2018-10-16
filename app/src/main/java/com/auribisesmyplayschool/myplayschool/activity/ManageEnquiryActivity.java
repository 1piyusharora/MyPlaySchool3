package com.auribisesmyplayschool.myplayschool.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.auribisesmyplayschool.myplayschool.Helper.Constants;
import com.auribisesmyplayschool.myplayschool.R;
import com.auribisesmyplayschool.myplayschool.adapter.EduEnquiryAdapter;
import com.auribisesmyplayschool.myplayschool.adminApp.bean.BranCourBean;
import com.auribisesmyplayschool.myplayschool.adminApp.bean.CourseBean;
import com.auribisesmyplayschool.myplayschool.adminApp.bean.UserBean;
import com.auribisesmyplayschool.myplayschool.bean.SignInBean;
import com.auribisesmyplayschool.myplayschool.bean.StudentBean;
import com.auribisesmyplayschool.myplayschool.classes.AttUtil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import dmax.dialog.SpotsDialog;

public class ManageEnquiryActivity extends AppCompatActivity {

    private EduEnquiryAdapter eduEnquiryAdapter;
    private EditText edtdateto, edtdatefrom, edtNextDate;
    private Date dateto = null, datefrom = null, datecurrent, d;
    private DateFormat format;
    private int dateSignal = 0, cyear, cmonth, cday, reqCode = 0, retrieveSignal, posEnquiry = 0,
            formSignal = 0, activitySignal = 0, studentId = 0, rbtnSignal = 0, cbExportEnqSignal = 0, cbSearchEnqSignal = 1;
    private DatePickerDialog datePickerDialog, datePickerDialog1;
    private Calendar calendar;
    private SharedPreferences preferences;
    private ArrayList<StudentBean> studentBeanArrayList, tempStudentBeanArrayList;
    private ArrayList<BranCourBean> courseBeanArrayList;
    private ArrayList<UserBean> teacherBeanArrayList;
    private ListView listViewEnquiry;
    private StudentBean studentBean;
    private int branchId = 0, branCourId = 0, responseSignal = 0;
    private String dateFrom, dateTo, reference = "";
    private RelativeLayout rlEFReset;
    private TextView tvEnquiryFilterreset;
    private CheckBox cbExportEnquiries, cbSearchEnquiries;
    private String strFileName = "", base64File = "";
    private File fWriter;
    private Uri path;
    FirebaseFirestore db;
    SignInBean signInBean;
    SpotsDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_enquiry);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = FirebaseFirestore.getInstance();
        preferences = getSharedPreferences(AttUtil.shpREG, MODE_PRIVATE);
        initview();
    }

    void initview() {
        //courseBeanArrayList = new ArrayList<>();
        teacherBeanArrayList = new ArrayList<>();
        Intent ircv = getIntent();
        if (ircv.hasExtra("activitySignal")) {
            activitySignal = ircv.getIntExtra("activitySignal", 0);
            branchId = ircv.getIntExtra(AttUtil.branchId, 0);
            dateFrom = ircv.getStringExtra("dateFrom");
            dateTo = ircv.getStringExtra("dateTo");
            branCourId = ircv.getIntExtra(AttUtil.branCourId, 0);
            reference = ircv.getStringExtra(AttUtil.reference);
            studentId = ircv.getIntExtra(AttUtil.studentId, 0);
            Log.i("test", reference + "");
        }

        courseBeanArrayList = (ArrayList<BranCourBean>) ircv.getSerializableExtra(AttUtil.KEY_COURSE_ARRAYLIST);
        signInBean = (SignInBean) ircv.getSerializableExtra(AttUtil.signInBean);
       // teacherBeanArrayList = (ArrayList<UserBean>) ircv.getSerializableExtra(AttUtil.KEY_USER_ARRAYLIST);
        studentBean = new StudentBean();
        calendar = Calendar.getInstance();
        cyear = calendar.get(Calendar.YEAR);
        cmonth = calendar.get(Calendar.MONTH);
        cday = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(ManageEnquiryActivity.this, onDateSetListener, cyear, cmonth, cday);
        datePickerDialog1 = new DatePickerDialog(ManageEnquiryActivity.this, onDateSetListener, cyear, cmonth, cday);
        d = new Date();
        format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            datecurrent = format.parse(new SimpleDateFormat("yyyy-MM-dd").format(d));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        listViewEnquiry =  findViewById(R.id.lvEnquiry);
        studentBeanArrayList = new ArrayList<>();
        tempStudentBeanArrayList = new ArrayList<>();
        rlEFReset = (RelativeLayout) findViewById(R.id.rlEFReset);
        tvEnquiryFilterreset = (TextView) findViewById(R.id.tvEnquiryFilterreset);
        tvEnquiryFilterreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentBeanArrayList.clear();
                studentBeanArrayList.addAll(tempStudentBeanArrayList);
                eduEnquiryAdapter.notifyDataSetChanged();
                rlEFReset.setVisibility(View.GONE);
                rbtnSignal = 0;
            }
        });
        listViewEnquiry.setOnItemClickListener(onItemClickListener);
        if (AttUtil.isNetworkConnected(ManageEnquiryActivity.this)) {
            progressDialog = new SpotsDialog(ManageEnquiryActivity.this, R.style.Custom);
            progressDialog.show();
            retrieveFromDb();
        } else {
           // retrieveNetConnect();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        if (item.getItemId() == 1) {
            //enquirylistfilter();
        }
        if (item.getItemId() == 2) {
            retrieveSignal = 2;
           // updateNextVisitDialog();
        }
        /*if (item.getItemId() == R.id.enquiryRecordInfo) {
            recordInfo();
        }*/
        if (item.getItemId() == 3) {
            //filterTypeDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    public void retrieveFromDb() {
        db.collection(Constants.enquiry_application_form_collection).whereEqualTo("branchId",signInBean.getBranchId()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                progressDialog.dismiss();
                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            studentBean = doc.getDocument().toObject(StudentBean.class);
                            studentBeanArrayList.add(studentBean);
                        }
                    }
                }
                if (studentBeanArrayList.size() > 0 && studentBeanArrayList != null) {
                    getSupportActionBar().setTitle("Enquiries(" + studentBeanArrayList.size() + ")");
                    eduEnquiryAdapter = new EduEnquiryAdapter(ManageEnquiryActivity.this, R.layout.adapter_edu_enquiry_list
                            , studentBeanArrayList);
                    listViewEnquiry.setAdapter(eduEnquiryAdapter);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AttUtil.REQ_CODE && resultCode == AttUtil.RES_CODE) {
            StudentBean bean = (StudentBean) data.getSerializableExtra(AttUtil.TAG_STUDENTBEAN);
            studentBeanArrayList.remove(posEnquiry);
            studentBeanArrayList.add(posEnquiry, bean);
            eduEnquiryAdapter.notifyDataSetChanged();
        }
    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            posEnquiry = position;
            Log.i("test", studentBean.toString());
            studentBean = studentBeanArrayList.get(position);
            startActivityForResult(new Intent(ManageEnquiryActivity.this, ViewEnquiryDetailActivity.class)
                    .putExtra(AttUtil.TAG_STUDENTBEAN, studentBean)
                    .putExtra(AttUtil.KEY_COURSE_ARRAYLIST, courseBeanArrayList)
                    //.putExtra(AttUtil.KEY_USER_ARRAYLIST, teacherBeanArrayList)
                    .putExtra(AttUtil.KEY_COUNSELLOR_ARRAYLIST, (ArrayList<UserBean>) getIntent().getSerializableExtra(AttUtil.KEY_COUNSELLOR_ARRAYLIST))
                    .putExtra("activitySignal", activitySignal), AttUtil.REQ_CODE);
            overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
            //show_options();
        }
    };

    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            monthOfYear = monthOfYear + 1;
            String cDayString, cMonthString;
            if (dayOfMonth <= 9) {
                cDayString = "0" + dayOfMonth;
            } else {
                cDayString = String.valueOf(dayOfMonth);
            }
            if (monthOfYear <= 9) {
                cMonthString = "0" + monthOfYear;
            } else {
                cMonthString = String.valueOf(monthOfYear);
            }
            if (dateSignal == 1) {
                edtdateto.setText(year + "-" + cMonthString + "-" + cDayString);
                try {
                    dateto = format.parse(edtdateto.getText().toString().trim());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (dateSignal == 2) {
                edtdatefrom.setText(year + "-" + cMonthString + "-" + cDayString);
                try {
                    datefrom = format.parse(String.valueOf(edtdatefrom.getText().toString().trim()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (dateSignal == 3) {
                edtNextDate.setText(year + "-" + cMonthString + "-" + cDayString);
            }
            /*if(dateSignal==4){
                edtchequedate.setText(year+"-"+cMonthString+"-"+cDayString);
            }
            if(dateSignal==5){
                edtcourseStartingDate.setText(year+"-"+cMonthString+"-"+cDayString);
            }*/
        }
    };
}
