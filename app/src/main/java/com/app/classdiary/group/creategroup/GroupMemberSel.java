package com.app.classdiary.group.creategroup;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.classdiary.R;
import com.app.classdiary.group.groupmodel.Members;
import com.app.classdiary.group.groupmodel.Members_;
import com.app.classdiary.utils.GroupMembers;
import com.app.classdiary.utils.LCHashMapUtils;
import com.app.classdiary.utils.LCUtils;
import com.google.common.collect.Lists;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@SuppressLint("NewApi")
public class GroupMemberSel extends Activity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    static int mPosition = 0;
    static Context mContext;
    ImageView iv_back/*, iv_search*/;
    TextView tv_actionbar_title;
    static ListView lv_dirtectory;
    static List<Members> membersData = new ArrayList<Members>();
    static List<Members_> membersData_1 = new ArrayList<>();
    public static MemberClubAdapter adapter;
    String c_id;
    RelativeLayout filterview;
    static RecyclerView recycler_view;
    ExpandableListView ex_listview_zone;
    RelativeLayout rv_exlistview, rv_listview;
    boolean f_clicked = true;
    boolean f_clicked1 = true;
    public static PercentRelativeLayout p_mainview;
    Map<String, Integer> mapIndex;
    int position = 0;
    static EditText et_searchview;
    static int scroll_flag = 0;
    // static LinearLayout v_linear;
    String from = "";
    String group_id = "";
    public static long totalSize = 0;
    String status = "", msg = "";
    ProgressDialog pDialog;
    public static Dashboard_group_adapter healthTipsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_group_selection);
        mContext = this;


        initializeViews();
        /*NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        TextView txt_name = (TextView) headerView.findViewById(R.id.tv_username);
        ImageView profile_img = (ImageView) headerView.findViewById(R.id.iv_userimg);
        txt_name.setText(LCUtils.getMemberNamePreferences(mContext));*/
       /* try {
            if (!LCUtils.getMemberImagePreferences(mContext).isEmpty()) {
                Picasso.with(mContext)
                        .load(LCUtils.getMemberImagePreferences(mContext))
                        .placeholder(R.drawable.user_icon)
                        .error(R.drawable.user_icon)
                        .into(profile_img);
            }
        }catch (Exception e){
        }*/


        //LCUtils.setcountnavigation(mContext, navigationView);
        /*from = getIntent().getExtras().getString("from");
        if (from.equals("GroupMemberDetails")) {
            group_id = getIntent().getExtras().getString("getGroup_id");
        }*/

        setViewxToBody(0);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (LCHashMapUtils.m_group_members_temp.size() > 0) {
                    Intent i = new Intent(mContext, ActivityNewGroup.class);
                    i.putExtra("getGroup_id", group_id);
                    startActivity(i);

//                    if (!from.equals("GroupMemberDetails")) {
//                        Intent i = new Intent(mContext, ActivityNewGroup.class);
//                        i.putExtra("getGroup_id", group_id);
//                        startActivity(i);
//                    } else {
//                        if (cd.isConnected()) {
//                            new add_group_members().execute();
//                        } else {
//                            LCUtils.showToastShort(mContext, "Internet Connection Required");
//                        }
//                    }
                    overridePendingTransition(R.anim.anim_in_reverse, R.anim.anim_out_reverse);
                    LCUtils.showToastShort(mContext, "" + LCHashMapUtils.m_group_members_temp.size());
                } else {
                    LCUtils.showToastShort(mContext, "You haven't selected any Members/Clubs");
                }
            }
        });
    }


    public static void showResult(boolean flag) {
        if (LCHashMapUtils.m_group_members1.size() > 0) {
            LCHashMapUtils.m_group_members_temp.clear();
            for (int i = 0; i < LCHashMapUtils.m_group_members1.size(); i++) {
                if (!LCHashMapUtils.m_group_members1.get(i).getUser_name().equals("0")) {
                    LCHashMapUtils.m_group_members_temp.add(new GroupMembers(LCHashMapUtils.m_group_members1.get(i).getUser_id(), LCHashMapUtils.m_group_members1.get(i).getUser_image(), LCHashMapUtils.m_group_members1.get(i).getUser_name()));
                }
            }
            if (LCHashMapUtils.m_group_members_temp.size() > 0) {
                p_mainview.setVisibility(View.VISIBLE);
                recycler_view.setVisibility(View.VISIBLE);
                //v_linear.setVisibility(View.VISIBLE);
                healthTipsAdapter = new Dashboard_group_adapter(mContext, R.layout.listbar_group_mem, Lists.reverse(LCHashMapUtils.m_group_members_temp), "dashboard");
                recycler_view.setAdapter(healthTipsAdapter);
                recycler_view.scrollToPosition(0);
            } else {
                 p_mainview.setVisibility(View.GONE);
                //v_linear.setVisibility(View.GONE);
                recycler_view.setVisibility(View.GONE);
            }
        } else {
            p_mainview.setVisibility(View.GONE);
            //v_linear.setVisibility(View.GONE);
            recycler_view.setVisibility(View.GONE);
        }
        scroll_flag = 0;
    }

    private void initializeViews() {
        ex_listview_zone = (ExpandableListView) findViewById(R.id.ex_listview_zone);
        p_mainview=findViewById(R.id.p_mainview);
        rv_exlistview = (RelativeLayout) findViewById(R.id.rv_exlistview);
        rv_listview = (RelativeLayout) findViewById(R.id.rv_listview);

        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, true);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recycler_view.setLayoutManager(mLayoutManager);
        lv_dirtectory = (ListView) findViewById(R.id.list);
        // iv_search.setOnClickListener(this);
        // tv_actionbar_title.setOnClickListener(this);
/*
        et_searchview.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                String text = et_searchview.getText().toString();
                adapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });*/
    }

    @Override
    public void onClick(View view) {
        /*if (view == iv_back || view == tv_actionbar_title) {
            onBackPressed();
        }*/

        /*if (view == iv_search) {
            if (et_searchview.getVisibility() == View.VISIBLE) {
                et_searchview.setVisibility(View.GONE);
                LCUtils.hideKeyboard(mContext);
            } else {
                LCUtils.showKeyboard(mContext);
                et_searchview.setVisibility(View.VISIBLE);
                et_searchview.requestFocus();
            }
        }*/
        if (position == 0) {
            try {
                TextView selectedIndex = (TextView) view;
                lv_dirtectory.setSelection(mapIndex.get(selectedIndex.getText()));
            } catch (Exception e) {
            }
        }
    }

    public void setViewxToBody(int position) {
        if (position == 0) {
            //  iv_search.setVisibility(View.VISIBLE);
            rv_listview.setVisibility(View.VISIBLE);
            rv_exlistview.setVisibility(View.GONE);
            fillmembers();
            // membersData = db.getAllMembers();
            if (membersData.size() > 0) {
                if (f_clicked1) {
                    //membersData_1 = db.getAllMembers_();
                    LCHashMapUtils.m_group_members1.clear();
                    for (int i = 0; i < membersData.size(); i++) {
                        LCHashMapUtils.m_boolean.add(membersData.get(i).isCheckedflag());
                        LCHashMapUtils.m_group_members1.add(new GroupMembers("0", "0", "0"));
                    }
                    adapter = new MemberClubAdapter(mContext, R.layout.listbar_members_checkbox, membersData, "1");
                    lv_dirtectory.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    getIndexList(membersData);
                    displayIndex();
                    f_clicked1 = false;
                }
            }
            mPosition = position;
        }
    }

    private void fillmembers() {
        //(int id, String member_id, String group_id, String designation_id, String member_first_name, String member_email, String member_image, boolean checkedflag, String member_check)
        membersData.add(new Members(1, "1", "3", "Math Teacher", "ASilpha", "shilpa@gmail.com", "https://i0.wp.com/www.imshahrukhkhan.com/wp-content/uploads/2010/12/srk-2010.jpg?resize=205%2C245", false, "0"));
        membersData.add(new Members(2, "2", "3", "Science Teacher", "BSilpha", "shilpa@gmail.com", "https://i0.wp.com/www.imshahrukhkhan.com/wp-content/uploads/2010/12/srk-2010.jpg?resize=205%2C245", false, "0"));
        membersData.add(new Members(3, "3", "3", "Biolgy Teacher", "CSilpha", "shilpa@gmail.com", "https://i0.wp.com/www.imshahrukhkhan.com/wp-content/uploads/2010/12/srk-2010.jpg?resize=205%2C245", false, "0"));
        membersData.add(new Members(4, "4", "3", "Hindi Teacher", "DSilpha", "shilpa@gmail.com", "https://i0.wp.com/www.imshahrukhkhan.com/wp-content/uploads/2010/12/srk-2010.jpg?resize=205%2C245", false, "0"));
        membersData.add(new Members(5, "5", "3", "English Teacher", "ESilpha", "shilpa@gmail.com", "https://i0.wp.com/www.imshahrukhkhan.com/wp-content/uploads/2010/12/srk-2010.jpg?resize=205%2C245", false, "0"));
        membersData.add(new Members(6, "6", "3", "History Teacher", "FSilpha", "shilpa@gmail.com", "https://i0.wp.com/www.imshahrukhkhan.com/wp-content/uploads/2010/12/srk-2010.jpg?resize=205%2C245", false, "0"));
        membersData.add(new Members(7, "7", "3", "Bangla Teacher", "GSilpha", "shilpa@gmail.com", "https://i0.wp.com/www.imshahrukhkhan.com/wp-content/uploads/2010/12/srk-2010.jpg?resize=205%2C245", false, "0"));
        membersData.add(new Members(8, "8", "3", "Math Teacher", "HSilpha", "shilpa@gmail.com", "https://i0.wp.com/www.imshahrukhkhan.com/wp-content/uploads/2010/12/srk-2010.jpg?resize=205%2C245", false, "0"));
        membersData.add(new Members(9, "9", "3", "Math Teacher", "ISilpha", "shilpa@gmail.com", "https://i0.wp.com/www.imshahrukhkhan.com/wp-content/uploads/2010/12/srk-2010.jpg?resize=205%2C245", false, "0"));
        membersData.add(new Members(10, "10", "3", "Phychology Teacher", "JSilpha", "shilpa@gmail.com", "https://i0.wp.com/www.imshahrukhkhan.com/wp-content/uploads/2010/12/srk-2010.jpg?resize=205%2C245", false, "0"));
        membersData.add(new Members(11, "11", "3", "Polyscience Teacher", "KSilpha", "shilpa@gmail.com", "https://i0.wp.com/www.imshahrukhkhan.com/wp-content/uploads/2010/12/srk-2010.jpg?resize=205%2C245", false, "0"));
        membersData.add(new Members(12, "12", "3", " Teacher", "LSilpha", "shilpa@gmail.com", "https://i0.wp.com/www.imshahrukhkhan.com/wp-content/uploads/2010/12/srk-2010.jpg?resize=205%2C245", false, "0"));
        membersData.add(new Members(13, "13", "3", "Math Teacher", "MSilpha", "shilpa@gmail.com", "https://i0.wp.com/www.imshahrukhkhan.com/wp-content/uploads/2010/12/srk-2010.jpg?resize=205%2C245", false, "0"));
        membersData.add(new Members(14, "14", "3", "Math Teacher", "NSilpha", "shilpa@gmail.com", "https://i0.wp.com/www.imshahrukhkhan.com/wp-content/uploads/2010/12/srk-2010.jpg?resize=205%2C245", false, "0"));
        membersData.add(new Members(15, "15", "3", "Math Teacher", "OSilpha", "shilpa@gmail.com", "https://i0.wp.com/www.imshahrukhkhan.com/wp-content/uploads/2010/12/srk-2010.jpg?resize=205%2C245", false, "0"));
        membersData.add(new Members(16, "16", "3", "Math Teacher", "PSilpha", "shilpa@gmail.com", "https://i0.wp.com/www.imshahrukhkhan.com/wp-content/uploads/2010/12/srk-2010.jpg?resize=205%2C245", false, "0"));
        membersData.add(new Members(17, "17", "3", "Math Teacher", "QSilpha", "shilpa@gmail.com", "https://i0.wp.com/www.imshahrukhkhan.com/wp-content/uploads/2010/12/srk-2010.jpg?resize=205%2C245", false, "0"));
        membersData.add(new Members(18, "18", "3", "Math Teacher", "RSilpha", "shilpa@gmail.com", "https://i0.wp.com/www.imshahrukhkhan.com/wp-content/uploads/2010/12/srk-2010.jpg?resize=205%2C245", false, "0"));
        membersData.add(new Members(19, "19", "3", "Math Teacher", "SSilpha", "shilpa@gmail.com", "https://i0.wp.com/www.imshahrukhkhan.com/wp-content/uploads/2010/12/srk-2010.jpg?resize=205%2C245", false, "0"));
        membersData.add(new Members(20, "20", "3", "Math Teacher", "TSilpha", "shilpa@gmail.com", "https://i0.wp.com/www.imshahrukhkhan.com/wp-content/uploads/2010/12/srk-2010.jpg?resize=205%2C245", false, "0"));

        membersData_1.add(new Members_(1, "1", "3", "Math Teacher", "ASilpha", "shilpa@gmail.com", "https://i0.wp.com/www.imshahrukhkhan.com/wp-content/uploads/2010/12/srk-2010.jpg?resize=205%2C245", false, "0"));
        membersData_1.add(new Members_(2, "2", "3", "Science Teacher", "BSilpha", "shilpa@gmail.com", "https://i0.wp.com/www.imshahrukhkhan.com/wp-content/uploads/2010/12/srk-2010.jpg?resize=205%2C245", false, "0"));
        membersData_1.add(new Members_(3, "3", "3", "Biolgy Teacher", "CSilpha", "shilpa@gmail.com", "https://i0.wp.com/www.imshahrukhkhan.com/wp-content/uploads/2010/12/srk-2010.jpg?resize=205%2C245", false, "0"));
        membersData_1.add(new Members_(4, "4", "3", "Hindi Teacher", "DSilpha", "shilpa@gmail.com", "https://i0.wp.com/www.imshahrukhkhan.com/wp-content/uploads/2010/12/srk-2010.jpg?resize=205%2C245", false, "0"));
        membersData_1.add(new Members_(5, "5", "3", "English Teacher", "ESilpha", "shilpa@gmail.com", "https://i0.wp.com/www.imshahrukhkhan.com/wp-content/uploads/2010/12/srk-2010.jpg?resize=205%2C245", false, "0"));
        membersData_1.add(new Members_(6, "6", "3", "History Teacher", "FSilpha", "shilpa@gmail.com", "https://i0.wp.com/www.imshahrukhkhan.com/wp-content/uploads/2010/12/srk-2010.jpg?resize=205%2C245", false, "0"));
        membersData_1.add(new Members_(7, "7", "3", "Bangla Teacher", "GSilpha", "shilpa@gmail.com", "https://i0.wp.com/www.imshahrukhkhan.com/wp-content/uploads/2010/12/srk-2010.jpg?resize=205%2C245", false, "0"));
        membersData_1.add(new Members_(8, "8", "3", "Math Teacher", "HSilpha", "shilpa@gmail.com", "https://i0.wp.com/www.imshahrukhkhan.com/wp-content/uploads/2010/12/srk-2010.jpg?resize=205%2C245", false, "0"));
        membersData_1.add(new Members_(9, "9", "3", "Math Teacher", "ISilpha", "shilpa@gmail.com", "https://i0.wp.com/www.imshahrukhkhan.com/wp-content/uploads/2010/12/srk-2010.jpg?resize=205%2C245", false, "0"));
        membersData_1.add(new Members_(10, "10", "3", "Phychology Teacher", "JSilpha", "shilpa@gmail.com", "https://i0.wp.com/www.imshahrukhkhan.com/wp-content/uploads/2010/12/srk-2010.jpg?resize=205%2C245", false, "0"));
        membersData_1.add(new Members_(11, "11", "3", "Polyscience Teacher", "KSilpha", "shilpa@gmail.com", "https://i0.wp.com/www.imshahrukhkhan.com/wp-content/uploads/2010/12/srk-2010.jpg?resize=205%2C245", false, "0"));
        membersData_1.add(new Members_(12, "12", "3", " Teacher", "LSilpha", "shilpa@gmail.com", "https://i0.wp.com/www.imshahrukhkhan.com/wp-content/uploads/2010/12/srk-2010.jpg?resize=205%2C245", false, "0"));
        membersData_1.add(new Members_(13, "13", "3", "Math Teacher", "MSilpha", "shilpa@gmail.com", "https://i0.wp.com/www.imshahrukhkhan.com/wp-content/uploads/2010/12/srk-2010.jpg?resize=205%2C245", false, "0"));
        membersData_1.add(new Members_(14, "14", "3", "Math Teacher", "NSilpha", "shilpa@gmail.com", "https://i0.wp.com/www.imshahrukhkhan.com/wp-content/uploads/2010/12/srk-2010.jpg?resize=205%2C245", false, "0"));
        membersData_1.add(new Members_(15, "15", "3", "Math Teacher", "OSilpha", "shilpa@gmail.com", "https://i0.wp.com/www.imshahrukhkhan.com/wp-content/uploads/2010/12/srk-2010.jpg?resize=205%2C245", false, "0"));
        membersData_1.add(new Members_(16, "16", "3", "Math Teacher", "PSilpha", "shilpa@gmail.com", "https://i0.wp.com/www.imshahrukhkhan.com/wp-content/uploads/2010/12/srk-2010.jpg?resize=205%2C245", false, "0"));
        membersData_1.add(new Members_(17, "17", "3", "Math Teacher", "QSilpha", "shilpa@gmail.com", "https://i0.wp.com/www.imshahrukhkhan.com/wp-content/uploads/2010/12/srk-2010.jpg?resize=205%2C245", false, "0"));
        membersData_1.add(new Members_(18, "18", "3", "Math Teacher", "RSilpha", "shilpa@gmail.com", "https://i0.wp.com/www.imshahrukhkhan.com/wp-content/uploads/2010/12/srk-2010.jpg?resize=205%2C245", false, "0"));
        membersData_1.add(new Members_(19, "19", "3", "Math Teacher", "SSilpha", "shilpa@gmail.com", "https://i0.wp.com/www.imshahrukhkhan.com/wp-content/uploads/2010/12/srk-2010.jpg?resize=205%2C245", false, "0"));
        membersData_1.add(new Members_(20, "20", "3", "Math Teacher", "TSilpha", "shilpa@gmail.com", "https://i0.wp.com/www.imshahrukhkhan.com/wp-content/uploads/2010/12/srk-2010.jpg?resize=205%2C245", false, "0"));

    }

    private void getIndexList(List<Members> membersData) {
        mapIndex = new LinkedHashMap<>();
        for (int i = 0; i < membersData.size(); i++) {
            String index = membersData.get(i).getMember_first_name().substring(0, 1);
            if (mapIndex.get(index) == null)
                mapIndex.put(index, i);
        }
    }

    private void displayIndex() {
        LinearLayout indexLayout = (LinearLayout) findViewById(R.id.side_index);
        TextView textView;
        List<String> indexList = new ArrayList<String>(mapIndex.keySet());
        for (String index : indexList) {
            textView = (TextView) getLayoutInflater().inflate(R.layout.side_index_item, null);
            textView.setText(index);
            textView.setTextAppearance(this, android.R.style.TextAppearance_Medium);
            textView.setOnClickListener(this);
            indexLayout.addView(textView);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            LCUtils.hideKeyboard(mContext);
        } catch (Exception e) {
        }
        finish();
        overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
      /*  LCUtils.openNavDrawer(id, mContext);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);*/
        return true;
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    /*private void showSuccessDialogTrue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(false);
        builder.setTitle("Message");
        builder.setMessage(msg);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(mContext, MyGroups.class);
                mContext.startActivity(i);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                finish();
                dialog.dismiss();
            }
        });
        *//* builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });*//*
        AlertDialog alert = builder.create();
        alert.show();
        Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(mContext.getResources().getColor(R.color.black));
    }*/


   /* public String add_group_members(Context mContext, String iamgeurl) {
        String upLoadServerUri = mContext.getResources().getString(R.string.addgroupmember);
        Log.d("urlurlurlurlurl", upLoadServerUri);
        File sourceFile = null;
        if (!iamgeurl.equals("") || !iamgeurl.isEmpty()) sourceFile = new File(iamgeurl);
        String responseString = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(upLoadServerUri);
        try {

            AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                    new AndroidMultiPartEntity.ProgressListener() {
                        @Override
                        public void transferred(long num) {
                            //publishProgress((int) ((num / (float) totalSize) * 100));
                        }
                    });

            entity.addPart("member_id", new StringBody(LCUtils.getMemberIdPreferences(mContext)));
            entity.addPart("device_type", new StringBody("A"));
            entity.addPart("device_id", new StringBody(LCUtils.getdeviceid(mContext)));
            entity.addPart("group_id", new StringBody(group_id));

            if (LCHashMapUtils.m_group_members_temp.size() > 0) {
                for (int i = 0; i < LCHashMapUtils.m_group_members_temp.size(); i++) {
                    entity.addPart("group_users_id[]", new StringBody(LCHashMapUtils.m_group_members_temp.get(i).getUser_id()));
                }
            }

            if (LCHashMapUtils.m_club_list.size() > 0) {
                for (int i = 0; i < LCHashMapUtils.m_club_list.size(); i++) {
                    for (String stock : LCHashMapUtils.m_club_list) {
                        entity.addPart("club_id[]", new StringBody(stock));
                    }
                }
            }
            totalSize = entity.getContentLength();
            httppost.setEntity(entity);
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity r_entity = response.getEntity();
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                responseString = EntityUtils.toString(r_entity).trim();
                Log.d("responseString", responseString);
            } else {
                responseString = "Error occurred! Http Status Code: " + statusCode;
            }
            JSONObject jsonObj = null;
            JSONObject results = null;
            jsonObj = new JSONObject(responseString);
            status = jsonObj.getString("status");
            msg = jsonObj.getString("msg");
            if (status.equals("1")) {
                // String imgurl = jsonObj.getJSONObject("data").getString("mem_head_photo");
                // LCUtils.setUser_profImagePreferences(mContext, imgurl);
            } else {
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseString;
    }*/


    /*class add_group_members extends AsyncTask<Void, Integer, String> {
        @SuppressWarnings("deprecation")

        @Override
        protected void onProgressUpdate(Integer... progress) {
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                return add_group_members(mContext, "");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {
            if (status.equals("0")) {
                showSuccessDialogFalse();
            } else if (status.equals("1")) {
                showSuccessDialogTrue();
            } else {
                msg = "Something went wrong please try again later!!";
                showSuccessDialogFalse();
            }
            pDialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            pDialog.show();
        }
    }*/

    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        LCUtils.saveLastClosedappdate(mContext);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LCUtils.saveLastClosedappdate(mContext);
    }*/
}
