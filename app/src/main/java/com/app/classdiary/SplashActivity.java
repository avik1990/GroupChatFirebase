package com.app.classdiary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.app.classdiary.dashboard.Dashboard;
import com.app.classdiary.utils.SharedprefUtils;

public class SplashActivity extends AppCompatActivity {

    boolean is_login=false;
    private static final int SPLASH_TIME = 2 * 1000;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext = this;
        is_login= SharedprefUtils.getisVerified(mContext);
        if (is_login) {
            goToHomeActivity();
        } else {
            goToLoginActivity();
        }
    }


    private void goToHomeActivity() {
        Thread background = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(SPLASH_TIME);
                    Intent i = new Intent(mContext, Dashboard.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        background.start();
    }

    private void goToLoginActivity() {
        Thread background = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(SPLASH_TIME);
                    Intent i = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        background.start();
    }
}
