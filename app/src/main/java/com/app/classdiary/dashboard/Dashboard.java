package com.app.classdiary.dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.app.classdiary.R;
import com.app.classdiary.dashboard.dashadapter.Notiadapter;
import com.app.classdiary.group.creategroup.GroupMemberSel;
import com.app.classdiary.model.Notification;
import com.app.classdiary.utils.Helper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Dashboard extends AppCompatActivity {

    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @BindView(R.id.fab_group)
    FloatingActionButton fab_group;

    List<Notification> list_noti = new ArrayList<>();
    Context mContext;
    Notiadapter mAdapter;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Lato-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;
        Helper.setupbottomview(mContext, navigation);

        initialization();
        filldata();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void initialization() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
    }

    private void filldata() {
        list_noti.clear();
        list_noti.add(new Notification("1", "Announcement1", "Have a good day", "12-09-2018", "abhi", "0", "1"));
        list_noti.add(new Notification("2", "Announcement2", "Have a good day", "12-09-2018", "abhi", "0", "2"));
        list_noti.add(new Notification("3", "Announcement3", "Prosenjit Roy pocked you", "12-09-2018", "abhi", "0", "1"));
        list_noti.add(new Notification("4", "Announcement4", "Your announcement is posted successfully", "12-09-2018", "raka", "0", "2"));
        list_noti.add(new Notification("5", "Announcement5", "Raima sent commented on your post", "12-09-2018", "abhi", "0", "1"));
        list_noti.add(new Notification("6", "Announcement6", "Your are great poked by Raima", "12-09-2018", "abhi", "0", "1"));
        list_noti.add(new Notification("7", "Announcement7", "Have a good day", "12-09-2018", "abhi", "0", "1"));
        list_noti.add(new Notification("8", "Announcement8", "Yes have a nice day", "12-09-2018", "abhi", "0", "1"));
        list_noti.add(new Notification("9", "Announcement9", "Have a good day", "12-09-2018", "abhi", "0", "1"));
        list_noti.add(new Notification("10", "Announcement10", "Have a good day", "12-09-2018", "abhi", "0", "2"));
        list_noti.add(new Notification("11", "Announcement11", "Have a good day", "12-09-2018", "abhi", "0", "1"));
        list_noti.add(new Notification("12", "Announcement12", "Have a good day", "12-09-2018", "abhi", "0", "2"));
        list_noti.add(new Notification("13", "Announcement13", "Have a good day", "12-09-2018", "abhi", "0", "1"));
        list_noti.add(new Notification("14", "Announcement14", "Have a good day", "12-09-2018", "abhi", "0", "2"));
        list_noti.add(new Notification("15", "Announcement15", "Nice post commented by Rishi", "12-09-2018", "abhi", "0", "1"));
        list_noti.add(new Notification("16", "Announcement16", "Rocking group created by Rishi", "12-09-2018", "abhi", "0", "1"));
        list_noti.add(new Notification("17", "Announcement17", "Exam protfolio created", "12-09-2018", "abhi", "0", "1"));
        list_noti.add(new Notification("18", "Announcement18", "Have a good day", "12-09-2018", "abhi", "0", "2"));
        inflatelist();
    }

    private void inflatelist() {
        if (list_noti.size() > 0) {
            mAdapter = new Notiadapter(list_noti);
            recycler_view.setAdapter(mAdapter);
        }
    }

    @OnClick(R.id.fab_group)
    void submit() {
        Intent intent = new Intent(mContext, GroupMemberSel.class);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finishAffinity();
            overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        //Toast.makeText(this, "Double tap to exit", Toast.LENGTH_SHORT).show();
        //LCUtils.showToastShort(mContext, "Double tap to exit");
        Toast.makeText(getApplicationContext(), "Double tap to exit", Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}
