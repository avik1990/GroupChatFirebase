package com.app.classdiary.announcement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.app.classdiary.R;
import com.app.classdiary.chat.ChatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnnouncementDetails extends AppCompatActivity {
    @BindView(R.id.fab_comments)
    FloatingActionButton fab_comments;
    Context mContext;
    String announcement_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_details);
        ButterKnife.bind(this);
        mContext = this;
        announcement_id=getIntent().getStringExtra("announcement_id");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @OnClick (R.id.fab_comments) void OnClick() {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("announcement_id",announcement_id);
        startActivity(intent);
    }
}
