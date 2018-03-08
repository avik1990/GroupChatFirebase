package com.app.classdiary;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.classdiary.dashboard.Dashboard;
import com.app.classdiary.utils.SharedprefUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.sign_in_button)
    Button sign_in_button;
    Context mCOntext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mCOntext = this;
    }

    @OnClick(R.id.sign_in_button)
    void submit() {
        if (!et_phone.getText().toString().trim().isEmpty() && et_phone.getText().toString().length() == 10) {
            SharedprefUtils.setMobileAppPreferences(mCOntext, et_phone.getText().toString().trim());
            Intent i = new Intent(mCOntext, Dashboard.class);
            startActivity(i);
            finishAffinity();
            SharedprefUtils.setisVerified(mCOntext, true);
        } else {
            Toast.makeText(mCOntext, "Enter Your Phone Number", Toast.LENGTH_LONG).show();
        }
    }

}

