package com.auribisesmyplayschool.myplayschool.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.auribisesmyplayschool.myplayschool.R;
import com.auribisesmyplayschool.myplayschool.adapter.TeacherAdapter;
import com.auribisesmyplayschool.myplayschool.adminApp.bean.UserBean;
import com.auribisesmyplayschool.myplayschool.bean.TeacherBean;
import com.auribisesmyplayschool.myplayschool.classes.AttUtil;

import java.util.ArrayList;

public class TeachersListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private SharedPreferences preferences;
    private ListView listViewTeachers;
    private ArrayList<TeacherBean> teacherBeanArrayList;
    private TeacherAdapter adapter;

    Intent rcv;

    private void initViews(){
        listViewTeachers=(ListView)findViewById(R.id.listViewTeachers);
        listViewTeachers.setOnItemClickListener(this);
        preferences=getSharedPreferences(AttUtil.shpREG, Context.MODE_PRIVATE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();
        retrieveTeachers();
    }


    private void retrieveTeachers(){
        rcv=getIntent();
        teacherBeanArrayList = (ArrayList<TeacherBean>) rcv.getSerializableExtra("teacherBeanArrayList");
        adapter=new TeacherAdapter(this, R.layout.adapter_teacher_layout,teacherBeanArrayList);
        getSupportActionBar().setTitle("Teacher("+teacherBeanArrayList.size()+")");
        listViewTeachers.setAdapter(adapter);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
