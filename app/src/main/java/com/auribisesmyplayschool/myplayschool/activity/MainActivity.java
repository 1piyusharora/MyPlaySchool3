package com.auribisesmyplayschool.myplayschool.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.auribisesmyplayschool.myplayschool.Helper.Constants;
import com.auribisesmyplayschool.myplayschool.R;
import com.auribisesmyplayschool.myplayschool.adapter.GridItemsMainAdapter;
import com.auribisesmyplayschool.myplayschool.adminApp.activity.AddUserActivity;
import com.auribisesmyplayschool.myplayschool.adminApp.activity.ManageBranchManagerActivity;
import com.auribisesmyplayschool.myplayschool.adminApp.bean.BranCourBean;
import com.auribisesmyplayschool.myplayschool.adminApp.bean.BranchBean;
import com.auribisesmyplayschool.myplayschool.adminApp.classes.AdminUtil;
import com.auribisesmyplayschool.myplayschool.bean.BatchBean;
import com.auribisesmyplayschool.myplayschool.bean.GridItemUserHomeBean;
import com.auribisesmyplayschool.myplayschool.adminApp.bean.UserBean;
import com.auribisesmyplayschool.myplayschool.bean.TeacherBean;
import com.auribisesmyplayschool.myplayschool.classes.MyGridView;
import com.auribisesmyplayschool.myplayschool.bean.SignInBean;
import com.auribisesmyplayschool.myplayschool.classes.AttUtil;
import com.auribisesmyplayschool.myplayschool.classes.RecyclerItemClickListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity {
    String error = "Kindly Select Batch";
    ArrayList<SignInBean> signInBeanArrayList;
    ArrayList<String> batchNameArray;
    ArrayList<Integer> batchIdArray;
    SignInBean signInBean;
    int userAdminId,userId;
    Intent rcv;
    String[] options;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Spinner spGroup;
    Button btnSelect;
   // ArrayList<QuoteBean> quoteList;
    ArrayList<String> spinList;
    ArrayList<BranchBean> branchBeanArrayList,rcvBranchBeanArrayList;
    //ArrayList<BatchBean> batchList;
    Dialog dialog;
    String strGroup;
    int batchId, reqCode = 0, branchListPosition = 0, request;
    int k = 0, pos = 0;
    ArrayList<UserBean> userBeanArrayList,teacherBeanArrayList,counsellorBeanArrayList;
    ArrayList<BranCourBean> courseBeanArrayList, courseBeanArrayListActive = new ArrayList<>();
    private SwipeRefreshLayout waveSwipeRefreshLayout;
    private int posEnquiry = 0;
    //private ArrayList<StudentBean> stuEduEnquiryBeanArrayList, stuUpcomingJoiningBeanArrayList;
   // MainPageEduEnquiryAdapter mainPageEduEnquiryAdapter;
   // MainPageUpcomingJoiningsAdapter mainPageUpcomingJoiningsAdapter;
    RecyclerView listViewEduEnquiry, listViewUpcomingJoinings;
    //StudentBean upcomingStudentBean;
    TextView tvNumberOfParentsInstalled, tvMAHeaderDetails, tvMATitle;
    MyGridView gridViewMainActivityOne, gridViewMainActivityTwo, gridViewMainActivityThree;
    ArrayList<GridItemUserHomeBean> listGridItemsOne, listGridItemsTwo, listGridItemsThree;
    GridItemsMainAdapter gridItemsMainAdapterOne, gridItemsMainAdapterTwo, gridItemsMainAdapterThree;
    ImageView ivVisitInfo,ivMASettings;
    EditText editTextQuoteUpdate;
    FirebaseFirestore db;
    BranCourBean branCourBean;
    UserBean userBean;
    SpotsDialog progressDialog;
    ArrayList<BatchBean> batchList;
    BatchBean batchBean;
    BranchBean branchBean;
    String branchName;

    TeacherBean teacherBean;
    ArrayList<TeacherBean> teacherBeanArrayList1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        preferences = getSharedPreferences(AttUtil.shpREG, MODE_PRIVATE);
        rcv = getIntent();
        signInBean = (SignInBean) rcv.getSerializableExtra(AttUtil.signInBean);
        userAdminId = rcv.getIntExtra("adminId",0);
        userId = rcv.getIntExtra("userId",0);
        branchName = rcv.getStringExtra("branchName");
        rcvBranchBeanArrayList= (ArrayList<BranchBean>) rcv.getSerializableExtra("branchBeanArrayList");

        db = FirebaseFirestore.getInstance();
        init();
        progressDialog = new SpotsDialog(MainActivity.this, R.style.Custom);
        progressDialog.show();
        getCoursesActiveList();
        getUsersList();
     //   Toast.makeText(MainActivity.this,""+branchBeanArrayList.size(),Toast.LENGTH_SHORT).show();
    }

     void getSizeModules() {
         listGridItemsOne.get(5).setTitle("Class(" + courseBeanArrayList.size() + ")");
         listGridItemsOne.get(7).setTitle("Teachers(" + teacherBeanArrayList.size() + ")");
         gridItemsMainAdapterOne.notifyDataSetChanged();
    }

    public void getUsersList() {
        db.collection(Constants.usersCollection).whereEqualTo("branchId",signInBean.getBranchId()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                //progressDialog.dismiss();
             for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                 userBean = doc.getDocument().toObject(UserBean.class);
                 userBeanArrayList.add(userBean);
             }

                for(int i=0;i<userBeanArrayList.size();i++){
                    if(userBeanArrayList.get(i).getUserType()==3)
                        teacherBeanArrayList.add(userBeanArrayList.get(i));
                    if(userBeanArrayList.get(i).getUserType()==2)
                        counsellorBeanArrayList.add(userBeanArrayList.get(i));
                }
            }
        });
    }

    public void getCoursesActiveList() {
        db.collection(Constants.branchCollection).document(String.valueOf(signInBean.getBranchId()))
                .collection(Constants.branch_course_collection).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for(DocumentChange doc: queryDocumentSnapshots.getDocumentChanges()){
                    branCourBean = doc.getDocument().toObject(BranCourBean.class);
                    courseBeanArrayList.add(branCourBean);
                }

                for (int i = 0; i < courseBeanArrayList.size(); i++) {
                    if (courseBeanArrayList.get(i).getCourseStatus() == 1)
                        courseBeanArrayListActive.add(courseBeanArrayList.get(i));
                }
                fetchTeacherList();


            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    void init() {
        branchBean = new BranchBean();
        branchBeanArrayList = new ArrayList<>();
        teacherBean = new TeacherBean();
        teacherBeanArrayList = new ArrayList<>();
        teacherBeanArrayList1 = new ArrayList<>();
        counsellorBeanArrayList = new ArrayList<>();
        userBeanArrayList = new ArrayList<>();
        batchIdArray = new ArrayList<>();
        batchNameArray = new ArrayList<>();
        signInBeanArrayList = new ArrayList<>();
        branCourBean = new BranCourBean();
        courseBeanArrayList = new ArrayList<>();
        preferences = getSharedPreferences(AttUtil.shpREG, Context.MODE_PRIVATE);
        editor = preferences.edit();
        //SignInBean signInBean = new Gson().fromJson(preferences.getString(AttUtil.shpSignInBean, ""), SignInBean.class);
        //batchList = new ArrayList<>();
        spinList = new ArrayList<>();
//        nav_view =  findViewById(R.id.nav_drawer);
//        mDrawerLayout =  findViewById(R.id.drawer_layout);
//        coordinatorLayout =  findViewById(R.id.snackBarPosition);
//        View view = nav_view.getHeaderView(0);

        TextView textViewEmail =  findViewById(R.id.textViewEmail);
        TextView textViewName =  findViewById(R.id.textViewName);
        tvMATitle=  findViewById(R.id.tvMATitle);
        ivMASettings =  findViewById(R.id.ivMASettings);
        ivMASettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!waveSwipeRefreshLayout.isRefreshing()) {
                   // startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                }
            }
        });
           if (preferences.getInt(AttUtil.shpLoginType, 0) == 2) {
            textViewEmail.setText(signInBean.getUserEmail());
            textViewName.setText(signInBean.getUserName());
        }
        if (preferences.getInt(AttUtil.shpLoginType, 0) == 4) {
         //   textViewEmail.setText(new Gson().fromJson(preferences.getString(AttUtil.shpCounsellorBean, ""), SignInBean.class).getUserEmail());
           // textViewName.setText(new Gson().fromJson(preferences.getString(AttUtil.shpCounsellorBean, ""), SignInBean.class).getUserName());
        }
        tvNumberOfParentsInstalled = findViewById(R.id.tvNumberOfParentsInstalled);
        tvMAHeaderDetails = findViewById(R.id.tvMAHeaderDetails);
        ivVisitInfo = findViewById(R.id.ivVisitInfo);
       // stuEduEnquiryBeanArrayList = new ArrayList<>();
        gridViewMainActivityOne = findViewById(R.id.gridViewMainActivityOne);
        gridViewMainActivityTwo = findViewById(R.id.gridViewMainActivityTwo);
        gridViewMainActivityThree = findViewById(R.id.gridViewMainActivityThree);
        waveSwipeRefreshLayout = findViewById(R.id.mainPageSwipe);
        waveSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary));
        try {
            waveSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    waveSwipeRefreshLayout.setRefreshing(true);
                    /*if (preferences.getInt(AttUtil.shpLoginType, 0) == 2) {
                        if (AttUtil.isNetworkConnected(MainActivity.this)) {
                            retrieveUsersCourses();
                        } else {
                            retrieveCBENetConnect();
                        }
                    } else if (preferences.getInt(AttUtil.shpLoginType, 0) == 4) {
                        if (AttUtil.isNetworkConnected(MainActivity.this)) {
                            retrieveCounsellorData();
                        } else {
                            retrieveCBENetConnect();
                        }
                    }*/
                    waveSwipeRefreshLayout.setRefreshing(false);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        ivVisitInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordInfo();
            }
        });
        listViewEduEnquiry = findViewById(R.id.lvMainEnquiry);
        listViewUpcomingJoinings = findViewById(R.id.lvUpcomingJoining);
        try {
            listViewEduEnquiry.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
            listViewEduEnquiry.addOnItemTouchListener(new RecyclerItemClickListener(MainActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (!waveSwipeRefreshLayout.isRefreshing()) {
                        posEnquiry = position;
                        /*startActivityForResult(new Intent(MainActivity.this, ViewEnquiryDetailActivity.class)
                                .putExtra(AttUtil.TAG_STUDENTBEAN, stuEduEnquiryBeanArrayList.get(posEnquiry))
                                .putExtra(AttUtil.KEY_COURSE_ARRAYLIST, courseBeanArrayListActive)
                                .putExtra(AttUtil.KEY_USER_ARRAYLIST, teacherBeanArrayList)
                                .putExtra(AttUtil.KEY_COUNSELLOR_ARRAYLIST, counsellorBeanArrayList), AttUtil.REQ_CODE);*/
                    }
                }
            }));

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            listViewUpcomingJoinings.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
            listViewUpcomingJoinings.addOnItemTouchListener(new RecyclerItemClickListener(MainActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (!waveSwipeRefreshLayout.isRefreshing()) {
      //                  upcomingStudentBean = stuUpcomingJoiningBeanArrayList.get(position);
                        //showOptionsUpcoming();
                    }
                }
            }));
        } catch (Exception e) {
            e.printStackTrace();
        }
        listGridItemsOne = new ArrayList<>();
        listGridItemsTwo = new ArrayList<>();
        listGridItemsThree = new ArrayList<>();
        if (preferences.getInt(AttUtil.shpLoginType, 0) == 2) {
            listGridItemsOne.add(new GridItemUserHomeBean("Add Application", R.drawable.ic_forms));
            listGridItemsOne.add(new GridItemUserHomeBean("Application(0)", R.drawable.ic_open_folder_with_document));
            listGridItemsOne.add(new GridItemUserHomeBean("Students", R.drawable.ic_group));
            listGridItemsOne.add(new GridItemUserHomeBean("Add Section", R.drawable.ic_add_list));
            listGridItemsOne.add(new GridItemUserHomeBean("Section(0)", R.drawable.ic_list));
            listGridItemsOne.add(new GridItemUserHomeBean("Class"+"("+courseBeanArrayListActive.size()+")", R.drawable.ic_books));
            listGridItemsOne.add(new GridItemUserHomeBean("Assign Fee Plan(0)", R.drawable.ic_strategy));
            listGridItemsOne.add(new GridItemUserHomeBean("Teacher(0)", R.drawable.ic_presentation));
            listGridItemsOne.add(new GridItemUserHomeBean("Employees", R.drawable.ic_group_employee));
            //listGridItemsOne.add(new GridItemUserHomeBean("Counsellor(0)", R.drawable.ic_users));
            listGridItemsOne.add(new GridItemUserHomeBean("Fee Summary", R.drawable.ic_user_fee_summary));
            listGridItemsOne.add(new GridItemUserHomeBean("Attendance", R.drawable.ic_hold));
            listGridItemsOne.add(new GridItemUserHomeBean("Transport", R.drawable.ic_transport));

            listGridItemsTwo.add(new GridItemUserHomeBean("Digital Message", R.drawable.ic_mail));
            listGridItemsTwo.add(new GridItemUserHomeBean("Media", R.drawable.ic_network));
            listGridItemsTwo.add(new GridItemUserHomeBean("Update Tip", R.drawable.ic_light_bulb));
            listGridItemsThree.add(new GridItemUserHomeBean("Switch branch", R.drawable.ic_change));
            listGridItemsThree.add(new GridItemUserHomeBean("Logout", R.drawable.ic_logout));
            gridItemsMainAdapterOne = new GridItemsMainAdapter(MainActivity.this, R.layout.grid_item_main_activity, listGridItemsOne);
            gridViewMainActivityOne.setAdapter(gridItemsMainAdapterOne);
            gridItemsMainAdapterTwo = new GridItemsMainAdapter(MainActivity.this, R.layout.grid_item_main_activity, listGridItemsTwo);
            gridViewMainActivityTwo.setAdapter(gridItemsMainAdapterTwo);
            gridItemsMainAdapterThree = new GridItemsMainAdapter(MainActivity.this, R.layout.grid_item_main_activity, listGridItemsThree);
            gridViewMainActivityThree.setAdapter(gridItemsMainAdapterThree);
            gridViewMainActivityOne.setOnItemClickListener(onItemClickListenerOne);
            //gridViewMainActivityTwo.setOnItemClickListener(onItemClickListenerTwo);
            gridViewMainActivityThree.setOnItemClickListener(onItemClickListenerThree);
            tvMAHeaderDetails.setText(signInBean.getUserEmail());
            tvMATitle.setText("MPS - Manager");
        } else {
            listGridItemsOne.add(new GridItemUserHomeBean("Add Application", R.drawable.ic_forms));
            listGridItemsOne.add(new GridItemUserHomeBean("Application(0)", R.drawable.ic_open_folder_with_document));
            listGridItemsOne.add(new GridItemUserHomeBean("Assign Fee Plan(0)", R.drawable.ic_strategy));

            listGridItemsThree.add(new GridItemUserHomeBean("Logout", R.drawable.ic_logout));
            gridItemsMainAdapterOne = new GridItemsMainAdapter(MainActivity.this, R.layout.grid_item_main_activity, listGridItemsOne);
            gridViewMainActivityOne.setAdapter(gridItemsMainAdapterOne);

            gridItemsMainAdapterThree = new GridItemsMainAdapter(MainActivity.this, R.layout.grid_item_main_activity, listGridItemsThree);
            gridViewMainActivityThree.setAdapter(gridItemsMainAdapterThree);
            gridViewMainActivityOne.setOnItemClickListener(onItemClickListenerOne);
            gridViewMainActivityThree.setOnItemClickListener(onItemClickListenerThree);

            findViewById(R.id.lUpcomingJoiningsLayout).setVisibility(View.GONE);
            findViewById(R.id.lAppInstalls).setVisibility(View.GONE);
           // tvMAHeaderDetails.setText(new Gson().fromJson(preferences.getString(AttUtil.shpCounsellorBean, ""), SignInBean.class).getUserEmail());
            tvMATitle.setText("MPS - Counsellor");
        }


//        gridViewMainActivity.setOnItemClickListener(this);

        /*if (preferences.getInt(AttUtil.shpLoginType, 0) == 2) {
            if (AttUtil.isNetworkConnected(MainActivity.this)) {
                retrieveUsersCourses();
            } else {
                retrieveCBENetConnect();
            }
        } else if (preferences.getInt(AttUtil.shpLoginType, 0) == 4) {
            if (AttUtil.isNetworkConnected(MainActivity.this)) {
                retrieveCounsellorData();
            } else {
                retrieveCBENetConnect();
            }
        }*/
    }

        void recordInfo() {
            android.support.v7.app.AlertDialog.Builder dialogBuilder =
                    new android.support.v7.app.AlertDialog.Builder(MainActivity.this);
            dialogBuilder.setTitle("Information");
            LinearLayout layout = new LinearLayout(MainActivity.this);
            layout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(15, LinearLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(0, 0, 0, 0);
            params1.setMargins(30, 0, 0, 0);
            LinearLayout enquiryLinearLayout = new LinearLayout(MainActivity.this);
            LinearLayout counsellingLinearLayout = new LinearLayout(MainActivity.this);
//        LinearLayout demoLinearLayout = new LinearLayout(MainActivity.this);
            LinearLayout registerLinearLayout = new LinearLayout(MainActivity.this);
            LinearLayout discardLinearLayout = new LinearLayout(MainActivity.this);
            enquiryLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            counsellingLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
//        demoLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            registerLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            discardLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            TextView enquiry = new TextView(MainActivity.this);
            TextView counselling = new TextView(MainActivity.this);
//        TextView demo = new TextView(MainActivity.this);
            TextView register = new TextView(MainActivity.this);
            TextView discard = new TextView(MainActivity.this);
            enquiry.setText(" - Enquiry");
            counselling.setText(" - Counsellinging");
//        demo.setText(" - Demo Student");
            register.setText(" - Registered Student");
            discard.setText(" - Discarded Enquiry");
            View enquiryview = new View(MainActivity.this);
            View counsellingview = new View(MainActivity.this);
//        View demoview = new View(MainActivity.this);
            View registerview = new View(MainActivity.this);
            View discardview = new View(MainActivity.this);
            enquiryview.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorSeaGreen));
            counsellingview.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorYellow));
//        demoview.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorBlue));
            registerview.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorGreen));
            discardview.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorOrange));

            enquiryLinearLayout.addView(enquiryview, 0, params1);
            enquiryLinearLayout.addView(enquiry, 1, params);
            counsellingLinearLayout.addView(counsellingview, 0, params1);
            counsellingLinearLayout.addView(counselling, 1, params);
//        demoLinearLayout.addView(demoview, 0, params1);
//        demoLinearLayout.addView(demo, 1, params);
            registerLinearLayout.addView(registerview, 0, params1);
            registerLinearLayout.addView(register, 1, params);
            discardLinearLayout.addView(discardview, 0, params1);
            discardLinearLayout.addView(discard, 1, params);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                enquiry.setTextAppearance(android.R.style.TextAppearance_Medium);
                counselling.setTextAppearance(android.R.style.TextAppearance_Medium);
//            demo.setTextAppearance(android.R.style.TextAppearance_Medium);
                register.setTextAppearance(android.R.style.TextAppearance_Medium);
                discard.setTextAppearance(android.R.style.TextAppearance_Medium);
            }

            layout.addView(enquiryLinearLayout, params);
            layout.addView(counsellingLinearLayout, params);
//        layout.addView(demoLinearLayout, params);
            layout.addView(registerLinearLayout, params);
            layout.addView(discardLinearLayout, params);

            dialogBuilder.setView(layout);
            dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            android.support.v7.app.AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.setCancelable(false);
            alertDialog.show();
        }



    AdapterView.OnItemClickListener onItemClickListenerOne = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (preferences.getInt(AttUtil.shpLoginType, 0) == 2) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(MainActivity.this, AddEnquiryActivity.class)
                                .putExtra(AttUtil.KEY_COURSE_ARRAYLIST, courseBeanArrayListActive)
                                .putExtra(AttUtil.KEY_COUNSELLOR_ARRAYLIST, counsellorBeanArrayList)
                                .putExtra(AttUtil.signInBean,signInBean)
                        );
                        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
                        break;
                    case 1:
                       startActivity(new Intent(MainActivity.this, ManageEnquiryActivity.class)
                                .putExtra(AttUtil.KEY_COURSE_ARRAYLIST, courseBeanArrayListActive)
                               // .putExtra(AttUtil.KEY_USER_ARRAYLIST, teacherBeanArrayList)
                                .putExtra(AttUtil.KEY_COUNSELLOR_ARRAYLIST, counsellorBeanArrayList)
                                .putExtra(AttUtil.signInBean,signInBean)
                       );
                        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
                        break;
                    case 2:
                        k = 1;
                        //viewBatch(1);
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this, AddBatchActivity.class)
                        .putExtra(AttUtil.KEY_COURSE_ARRAYLIST, courseBeanArrayListActive));
                        break;
                    case 4:
                        batchBean = new BatchBean();
                        batchList = new ArrayList<>();
                        coursesList();
                        break;
                    case 5:
                        progressDialog = new SpotsDialog(MainActivity.this, R.style.Custom);
                        progressDialog.show();
                        startActivity(new Intent(MainActivity.this, CourseListActivity.class)
                                .putExtra(AttUtil.KEY_COURSE_ARRAYLIST, courseBeanArrayListActive));
                        progressDialog.dismiss();
                        break;
                    case 6:
                        /*startActivity(new Intent(MainActivity.this, StudentFeeListActivity.class)
                                .putExtra("approved_students", 0));
                        break;*/
                    case 7:
                        startActivity(new Intent(MainActivity.this, TeachersListActivity.class)
                                .putExtra("teacherBeanArrayList",teacherBeanArrayList1));
                        break;
                    case 8:
                        //Toast.makeText(MainActivity.this,""+branchBeanArrayList.size(),Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, ManageBranchManagerActivity.class)
                                .putExtra(AttUtil.signInBean,signInBean).putExtra("adminId",userAdminId)
                                .putExtra("userId",userId).putExtra("branchName",branchName));
                        break;
                    case 9:
                        /*startActivity(new Intent(MainActivity.this, StudentListActivity.class)
                                .putExtra("lateJoining", 2).putExtra("choose", 1)
                                .putExtra(AttUtil.KEY_BATCH_ID, 0));
                        break;*/
                    case 10:
                        /*startActivity(new Intent(MainActivity.this, StudentAttendanceActivity.class));
                        break;*/
                    case 11:
                        /*startActivity(new Intent(MainActivity.this, TransportActivity.class));
                        break;*/
                }
            }else{
                switch (position) {
                    case 0:
                        /*startActivity(new Intent(MainActivity.this, AddEnquiryActivity.class)
                                .putExtra(AttUtil.KEY_COURSE_ARRAYLIST, courseBeanArrayListActive)
                                .putExtra(AttUtil.KEY_COUNSELLOR_ARRAYLIST, counsellorBeanArrayList));
                        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);*/
                        break;
                   /* case 1:
                        startActivity(new Intent(MainActivity.this, ManageEnquiryActivity.class)
                                .putExtra(AttUtil.KEY_COURSE_ARRAYLIST, courseBeanArrayListActive)
                                .putExtra(AttUtil.KEY_USER_ARRAYLIST, teacherBeanArrayList)
                                .putExtra(AttUtil.KEY_COUNSELLOR_ARRAYLIST, counsellorBeanArrayList));
                        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this, StudentFeeListActivity.class)
                                .putExtra("approved_students", 0));
                        break;*/

                }
            }

        }
    };

    public void fetchTeacherList(){
        db.collection(Constants.usersCollection).whereEqualTo("branchName",branchName)
                .whereEqualTo("userType",3).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                progressDialog.dismiss();
                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            teacherBean = doc.getDocument().toObject(TeacherBean.class);
                            teacherBeanArrayList1.add(teacherBean);
                        }
                    }
                }
                getSwitchBranches();

            }
        });
    }

    private void getSwitchBranches() {
        for(int i=0;i<rcvBranchBeanArrayList.size();i++){
            if(rcvBranchBeanArrayList.get(i).getBranchName().equals(branchName)){
                rcvBranchBeanArrayList.remove(i);
            }
        }

        getSizeModules();

    }


    void coursesList() {
         AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
         String options[]= new String[courseBeanArrayListActive.size()];
         for(int i=0;i<courseBeanArrayListActive.size();i++){
             options[i]=courseBeanArrayListActive.get(i).getCourseName();
         }
         builder.setTitle("Choose a course");
         builder.setItems(options, new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                 progressDialog = new SpotsDialog(MainActivity.this, R.style.Custom);
                 progressDialog.show();
                 getBatches(which);
             }
         });
         builder.create().show();
    }

     void getBatches(final int pos) {
         db.collection(Constants.branchCollection).document(String.valueOf(signInBean.getBranchId()))
                 .collection(Constants.branch_course_collection).
                 document(String.valueOf(courseBeanArrayListActive.get(pos).getBranCourId()))
                 .collection(Constants.batch_section_collection).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
             @Override
             public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                 progressDialog.dismiss();
                 for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                     batchBean = doc.getDocument().toObject(BatchBean.class);
                     batchList.add(batchBean);
                 }
                 startActivity(new Intent(MainActivity.this, BatchListActivity.class)
                         .putExtra(AttUtil.BATCH_LIST,batchList)
                 .putExtra(AttUtil.KEY_COURSE_ARRAYLIST,courseBeanArrayListActive));
             }
         });

    }

    AdapterView.OnItemClickListener onItemClickListenerThree = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (preferences.getInt(AttUtil.shpLoginType, 0) == 2) {
                switch (position) {
                    case 0:
                        retrieveBranch();
                        break;
                    case 1:
                        logout();
                        break;
                }
            }else{
                switch (position) {
                    case 0:
                        logout();
                        break;
                }
            }
        }
    };

    private void retrieveBranch() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose a Branch");

        String options[]= new String[rcvBranchBeanArrayList.size()];
        for(int i=0;i<rcvBranchBeanArrayList.size();i++){
            options[i]=rcvBranchBeanArrayList.get(i).getBranchName();
        }
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override

            public void onClick(DialogInterface dialog, int which) {


            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setCancelable(false);
        builder.create().show();

    }

    void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editor.clear().commit();
                teacherBeanArrayList1.clear();
                courseBeanArrayListActive.clear();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }

}
