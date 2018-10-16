package com.auribisesmyplayschool.myplayschool.adminApp.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.auribisesmyplayschool.myplayschool.Helper.Constants;
import com.auribisesmyplayschool.myplayschool.R;
import com.auribisesmyplayschool.myplayschool.adminApp.adapter.BranchCourseAdapter;
import com.auribisesmyplayschool.myplayschool.adminApp.bean.BranCourBean;
import com.auribisesmyplayschool.myplayschool.adminApp.bean.BranchBean;
import com.auribisesmyplayschool.myplayschool.adminApp.classes.AdminUtil;
import com.auribisesmyplayschool.myplayschool.classes.AttUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

public class ManageBranchCourseActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<BranCourBean> branCourBeanArrayList;
    BranchCourseAdapter branchCourseAdapter;
    String courseDesc,cStatus;
    Intent i;
    BranCourBean branCourBean;
    SharedPreferences preferences;
    int pos,branchId;
    FirebaseFirestore db;
    SpotsDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_branch_course);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        preferences = getSharedPreferences(AttUtil.shpREG, Context.MODE_PRIVATE);
        db = FirebaseFirestore.getInstance();
        initViews();
        progressDialog = new SpotsDialog(ManageBranchCourseActivity.this, R.style.Custom);
        progressDialog.show();
        i = getIntent();
        branchId = i.getIntExtra(AdminUtil.branchId,0);
        get_branchCourse();
    }

    public void initViews(){
        listView = (ListView) findViewById(R.id.lvBranCourse);
        branCourBean = new BranCourBean();
        branCourBeanArrayList = new ArrayList<>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                branCourBean = branCourBeanArrayList.get(position);
                courseDesc = branCourBeanArrayList.get(position).getCourseDesc();

                if(branCourBean.getCourseStatus() == 1){
                    cStatus = "Deactivate";
                } else {
                    cStatus = "Activate";
                }

                show_options();
            }
        });
    }

    public void show_options(){
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        String[] options = {"Course Description","Update Course's Details",cStatus};

        build.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case 0:
                        displayDesc();
                        break;
                    case 1:
                        Intent intent = new Intent(ManageBranchCourseActivity.this,BranchCourseActivity.class);
                        intent.putExtra(AdminUtil.TAG_BRANCOUR,branCourBean);
                        intent.putExtra(AdminUtil.TAG_BRANCHARRAYLIST,
                                (ArrayList<BranchBean>)i.getSerializableExtra(AdminUtil.TAG_BRANCHARRAYLIST));
                        startActivityForResult(intent,AdminUtil.REQ_CODE);
                        break;
                    case 2:
                       // if (AdminUtil.isNetworkConnected(ManageBranchCourseActivity.this))
                            //start_deactivate();
                        //else
                            //activateNetConnect();
                        break;
                    default:
                        break;
                }
            }
        });

        build.create().show();
    }



    public void displayDesc(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(courseDesc);

        alertDialogBuilder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialogBuilder.setCancelable(false);
        alertDialog.show();
    }

    public void get_branchCourse(){
        db.collection(Constants.branchCollection).document(String.valueOf(branchId)).collection(Constants.branch_course_collection).whereEqualTo("branchId",branchId).get().
                addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                progressDialog.dismiss();
                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            //size = queryDocumentSnapshots.size();
                            branCourBean = doc.getDocument().toObject(BranCourBean.class);
                            branCourBeanArrayList.add(branCourBean);
                            branchCourseAdapter = new BranchCourseAdapter(ManageBranchCourseActivity.this,
                                    R.layout.admin_adapter_branch_course_listitem, branCourBeanArrayList);
                            listView.setAdapter(branchCourseAdapter);
                            branchCourseAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
}
