package com.app.classdiary.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by User1 on 21-02-2018.
 */

public class SharedprefUtils {

    public static void setMobileAppPreferences(Context mContext, String zone) {
        SharedPreferences preferences = mContext.getSharedPreferences("lionsclub", 0); // 0 - for private mode
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("mobile", zone);
        editor.apply();
    }

    public static String get_MobileAppPreferences(Context mContext) {
        SharedPreferences loginPreferences = mContext.getSharedPreferences("lionsclub", 0); // 0 - for private mode
        String a_key = loginPreferences.getString("mobile", "0");
        return a_key;
    }

    public static boolean getisVerified(Context mContext) {
        SharedPreferences loginPreferences = mContext.getSharedPreferences("lionsclub", 0); // 0 - for private mode
        boolean flag = loginPreferences.getBoolean("is_verified", false);
        return flag;
    }

    public static void setisVerified(Context mContext, boolean isVerified) {
        SharedPreferences preferences = mContext.getSharedPreferences("lionsclub", 0); // 0 - for private mode
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("is_verified", isVerified);
        editor.apply();
    }


}
