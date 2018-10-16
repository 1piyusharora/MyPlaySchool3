package com.auribisesmyplayschool.myplayschool.adminApp.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.auribisesmyplayschool.myplayschool.Helper.Constants;
import com.auribisesmyplayschool.myplayschool.R;
import com.auribisesmyplayschool.myplayschool.adminApp.activity.AddUserActivity;
import com.auribisesmyplayschool.myplayschool.adminApp.activity.ManageBranchManagerActivity;
import com.auribisesmyplayschool.myplayschool.adminApp.adapter.UserListAdapter;
import com.auribisesmyplayschool.myplayschool.adminApp.bean.UserBean;
import com.auribisesmyplayschool.myplayschool.adminApp.classes.AdminUtil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import dmax.dialog.SpotsDialog;

public class CounsellorFragment extends Fragment {
    private UserBean userBean;
    private int pos;
    private String uStatus;
    private SharedPreferences prefs;
    private ListView listView;
    public UserListAdapter userListAdapter;
    FirebaseFirestore db;
    SpotsDialog progressDialog;

    public CounsellorFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_counsellor, container, false);
        listView = view.findViewById(R.id.lvFragmentCounsellor);
        db = FirebaseFirestore.getInstance();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                //userId = branchManagerList.get(position).getUserId();
                userBean = ManageBranchManagerActivity.counsellorBeanArrayList.get(position);
                Log.i("test",userBean.toString());
                if(userBean.getUserStatus() == 1){
                    uStatus = "Deactivate";
                } else {
                    uStatus = "Activate";
                }
                show_options_two();
            }
        });
        return view;
    }

    public void show_options_two(){
        AlertDialog.Builder build = new AlertDialog.Builder(getActivity());
        String[] options = {"Update Details",uStatus,"Resend Credentials"};

        build.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case 0:
                        Intent passBeanData = new Intent(getActivity(),AddUserActivity.class);
                        passBeanData.putExtra(AdminUtil.TAG_USER,userBean);
                        passBeanData.putExtra(AdminUtil.TAG_BRANCHARRAYLIST,ManageBranchManagerActivity.branchBeanArrayList);
                        startActivityForResult(passBeanData,AdminUtil.REQ_CODE);
                        break;
                    case 1:
                        if (AdminUtil.isNetworkConnected(getActivity())){
                            progressDialog = new SpotsDialog(getContext(), R.style.Custom);
                            progressDialog.show();
                            start_deactivate();
                        }

                        else
                           // activateNetConnect();
                        break;
                    case 2:
                       /* if (AdminUtil.isNetworkConnected(getActivity()))
                            resendCredentials();
                        else
                            resendNetConnect();*/
                }
            }
        });

        build.create().show();
    }

     void start_deactivate() {
        if(userBean.getUserStatus()==1){
            userBean.setUserStatus(0);
        }else{
            userBean.setUserStatus(1);
        }
        db.collection(Constants.usersCollection).document(String.valueOf(userBean.getUserId()))
                .update("userStatus",userBean.getUserStatus()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressDialog.dismiss();
                userListAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(),"User "+userBean.getUserName()+" status changed.",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void initlist(){
        userListAdapter = new UserListAdapter(getActivity()
                ,R.layout.admin_adapter_user_listitem, ManageBranchManagerActivity.counsellorBeanArrayList);
        listView.setAdapter(userListAdapter);
    }

    public void updateListFunction(){
        if(ManageBranchManagerActivity.counsellorBeanArrayList.size()>1
                &&ManageBranchManagerActivity.counsellorBeanArrayList!=null){
            userListAdapter.notifyDataSetChanged();
        }else{
            userListAdapter =new UserListAdapter(getActivity()
                    , R.layout.admin_adapter_user_listitem,
                    ManageBranchManagerActivity.counsellorBeanArrayList);
            listView.setAdapter(userListAdapter);
        }
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Counsellors"
                +" ("+ManageBranchManagerActivity.counsellorBeanArrayList.size()+")");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode == AdminUtil.REQ_CODE)&&(resultCode == AdminUtil.RES_CODE)){
            userBean  = (UserBean) data.getSerializableExtra(AdminUtil.TAG_USER);
            ManageBranchManagerActivity.counsellorBeanArrayList.set(pos,userBean);
            ((AppCompatActivity)getActivity()).getSupportActionBar()
                    .setTitle("Counsellors" +" ("+ManageBranchManagerActivity.counsellorBeanArrayList.size()+")");
            userListAdapter.notifyDataSetChanged();
        }
    }


}
