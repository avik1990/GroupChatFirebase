package com.app.classdiary.group;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.app.classdiary.R;
import com.app.classdiary.group.creategroup.GroupMemberSel;
import com.app.classdiary.group.groupadapter.Groupadapter;
import com.app.classdiary.group.groupmodel.Group;
import com.app.classdiary.utils.Helper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GroupList extends AppCompatActivity {

    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @BindView(R.id.fab_group)
    FloatingActionButton fab_group;

    List<Group> list_group = new ArrayList<>();
    Context mContext;
    Groupadapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        ButterKnife.bind(this);
        mContext = this;
        Helper.setupbottomview(mContext, navigation);

        initialization();
        filldata();
    }

    private void initialization() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
    }

    private void filldata() {
        // public Group(String gid, String group_name, String created_date, String owner, String group_desc) {
        list_group.clear();
        list_group.add(new Group("1", "Group1", "12-09-2018", "Avik", "We are kings"));
        list_group.add(new Group("2", "Group2", "13-09-2018", "Raka", "We are kings"));
        list_group.add(new Group("3", "Group3", "14-09-2018", "DB", "We are kings"));
        list_group.add(new Group("4", "Group4", "16-09-2018", "Ashadul", "We are kings"));

        inflatelist();
    }

    private void inflatelist() {
        if (list_group.size() > 0) {
            mAdapter = new Groupadapter(mContext,list_group);
            recycler_view.setAdapter(mAdapter);
        }
    }

    @OnClick(R.id.fab_group)
    void submit() {
        Intent intent = new Intent(mContext, GroupMemberSel.class);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
    }
}
