package com.app.classdiary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.app.classdiary.dashboard.Dashboard;
import com.app.classdiary.group.GroupList;
import com.app.classdiary.R;

/**
 * Created by User1 on 19-02-2018.
 */

public class Helper {

    public static void setupbottomview(final Context mContext, BottomNavigationView navigation) {
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_notifications:
                        Intent i = new Intent(mContext, Dashboard.class);
                        mContext.startActivity(i);
                        ((Activity) mContext).overridePendingTransition(0, 0);
                        ((Activity) mContext).finish();
                        return true;
                    case R.id.navigation_groups:
                        Intent i1 = new Intent(mContext, GroupList.class);
                        mContext.startActivity(i1);
                        ((Activity) mContext).overridePendingTransition(0, 0);
                        ((Activity) mContext).finish();
                        return true;
                   /* case R.id.navigation_notifications:
                        Intent i2 = new Intent(mContext, Dashboard.class);
                        mContext.startActivity(i2);
                        ((Activity) mContext).overridePendingTransition(0, 0);
                        ((Activity) mContext).finish();
                        return true;*/
                }
                return false;
            }
        });
    }


}
