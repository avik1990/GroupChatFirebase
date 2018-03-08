package com.app.classdiary.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.classdiary.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LCUtils {
    final static int M_REQUEST_CODE = 100;
    public static final String SMS_ORIGIN = "VK-040003";
    public static final String SMS_ORIGIN1 = "WEBAPP";
    public static final String SMS_ORIGIN2 = "TESTSM";


    public static String getJSONString(String url) {
        String jsonString = null;
        HttpURLConnection linkConnection = null;
        try {
            URL linkurl = new URL(url);
            linkConnection = (HttpURLConnection) linkurl.openConnection();
            int responseCode = linkConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream linkinStream = linkConnection.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int j = 0;
                while ((j = linkinStream.read()) != -1) {
                    baos.write(j);
                }
                byte[] data = baos.toByteArray();
                jsonString = new String(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (linkConnection != null) {
                linkConnection.disconnect();
            }
        }
        return jsonString;
    }

    public static void shareAll(Context mContext, String heading, String links) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_TITLE, heading);
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, heading);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, links);
        mContext.startActivity(Intent.createChooser(sharingIntent, "Share Using"));
    }

    public static void shareEvent(Context mContext, String heading, String links) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_TITLE, heading);
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, heading);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, links);
        mContext.startActivity(Intent.createChooser(sharingIntent, "Share Using"));
    }

    public static String getFormattedDate0(String normal_date) {
        String anni = normal_date;
        String formated_date = "";
        if (anni.length() > 6) {
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
//SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            SimpleDateFormat targetFormat = new SimpleDateFormat("dd MMM");
            Date date;
            try {
                date = originalFormat.parse(anni);
                formated_date = targetFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            formated_date = anni;
        }
        return formated_date;
    }

    //----------------------------------------------
   /* public static void phoneCall(String number, Context mContext) {
        TelephonyManager telMgr = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        int simState = telMgr.getSimState();
        switch (simState) {
            case 0:
            case 1:
                showPhoneErrorDialog(mContext);
                break;

            case TelephonyManager.SIM_STATE_READY:
                try {

                    mContext.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }*/

    public static void showSuccessDialogFalse(Context mContext, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(false);
        builder.setTitle("Message");
        builder.setMessage(msg);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
       /* builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });*/
        AlertDialog alert = builder.create();
        alert.show();
        Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(mContext.getResources().getColor(R.color.black));

    }


    public static void showSuccessDialogFalseFinished(final Context mContext, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(false);
        builder.setTitle("Message");
        builder.setMessage(msg);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ((Activity) mContext).onBackPressed();
                ((Activity) mContext).finish();
            }
        });
       /* builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });*/
        AlertDialog alert = builder.create();
        alert.show();
        Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(mContext.getResources().getColor(R.color.black));
    }

    public static void showSuccessDialogTrue(Context mContext, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(false);
        builder.setTitle("Message");
        builder.setMessage(msg);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*Intent i = new Intent(mContext, AddSponserImage.class);
                i.putExtra("event_id",event_id);
                mContext.startActivity(i);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);*/
                dialog.dismiss();
            }
        });
       /* builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });*/
        AlertDialog alert = builder.create();
        alert.show();
        Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(mContext.getResources().getColor(R.color.black));
    }
    //---------------------------------------


    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "[A-Za-z0-9._%+-]+@[A-Za-z0-9._]+\\.[A-Za-z]{2,4}";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    //---------------------------------------------------------------------
    public static boolean isStringNullOrEmpty(String str) {
        boolean result = false;
        if (str == null || str.equals("")) {

            result = true;
        }

        return result;

    }


    public static void shareAllEvents(Context mContext, String heading, String sub) {
        //String title = heading;
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, heading);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, sub);
        mContext.startActivity(Intent.createChooser(sharingIntent, "Share Using"));
    }


    public static void shareAll(Context mContext, String heading, String sub, String links) {
        //String title = heading;
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        //sharingIntent.putExtra(android.content.Intent.EXTRA_TITLE, heading);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, links);
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, heading);
        mContext.startActivity(Intent.createChooser(sharingIntent, "Share Using"));
    }

    public static void setActivity_flag(Context mContext, String cat) {
        SharedPreferences preferences = mContext.getSharedPreferences("LionesClubPreferences", 0); // 0 - for private mode
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("acvivity_flag", cat);
        editor.commit();
    }

    public static String getActivity_flag(Context mContext) {
        SharedPreferences preferences = mContext.getSharedPreferences("LionesClubPreferences", 0); // 0 - for private mode
        String name = preferences.getString("acvivity_flag", "");
        return name;
    }

    public static void sendMail(Context mContext, String mailto) {

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:" + mailto));
        try {
            mContext.startActivity(Intent.createChooser(emailIntent, "Send email using..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(mContext, "No email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }


    public static void sendMail(Context mContext, String mailto,
                                String message, int M_REQUEST_CODE) {

        String body = "\n\nMessage: " + message.trim();

		/*Intent mailer = new Intent(Intent.ACTION_SEND);
        mailer.setType("message/rfc822");
		mailer.putExtra(Intent.EXTRA_EMAIL, new String[] { mailto });
		mailer.putExtra(Intent.EXTRA_SUBJECT, "Enquiry From Lions Clubs 322B1 App");
		mailer.putExtra(Intent.EXTRA_TEXT, body);
		mContext.startActivity(Intent.createChooser(mailer, "Send email..."));


		String body = "\n\nName: " + name + "\nEmail Id: "
				+ email + "\nMobile: " + mobile + "\nAddress: " + address
				+ "\nProfession: " + profession+
				"\n\nMessage: " + message.trim();
*/
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:" + mailto));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Enquiry From Lions Clubs 322B1 App");
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);
        //emailIntent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
        try {
            //mContext.startActivity(Intent.createChooser(emailIntent, "Send email using..."));
            ((Activity) mContext).startActivityForResult(Intent.createChooser(emailIntent, "Send email using..."), M_REQUEST_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(mContext, "No email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }


    //------------------------------------------------------------------------------------------

    /**
     * @param mContext
     * @param emailTo
     * @param subject
     * @param emailText
     * @param filePathsMap
     * @param M_REQUEST_CODE
     */
    @SuppressLint("NewApi")
    public static void sendMail(Context mContext, String emailTo,
                                String subject, String emailText, LinkedHashMap filePathsMap, int M_REQUEST_CODE) {
        //need to "send multiple" to get more than one attachment
        final Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        //emailIntent.setType("text/plain");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{emailTo});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, emailText);
        //has to be an ArrayList
        ArrayList<Uri> uris = new ArrayList<Uri>();
        //convert from paths to Android friendly Parcelable Uri's
            /*for (String file : filePaths){
                File fileIn = new File(file);
		        Uri u = Uri.fromFile(fileIn);
		        uris.add(u);
		    }*/
        Iterator entries = filePathsMap.entrySet().iterator();
        while (entries.hasNext()) {
            Entry thisEntry = (Entry) entries.next();
            //Object key   = thisEntry.getKey();
            Object value = thisEntry.getValue();
            File fileIn = new File(value.toString());
            Uri u = Uri.fromFile(fileIn);
            uris.add(u);
        }
        emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
        //mContext.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        ///////////////////////////////////////////////////////////////////////////////////////////////
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            emailIntent.setType(null); // If we're using a selector, then clear the type to null. I don't know why this is needed, but it doesn't work without it.
            final Intent restrictIntent = new Intent(Intent.ACTION_SENDTO);
            Uri data = Uri.parse("mailto:?to=" + emailTo);
            restrictIntent.setData(data);
            emailIntent.setSelector(restrictIntent);
        }
        //////////////////////////////////////////////////////////////////////////////////////////////
        try {
            ((Activity) mContext).startActivityForResult(Intent.createChooser(emailIntent, "Send email using..."), M_REQUEST_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(mContext, "No email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    //------------------------------------------------------------------------------------------

    public static void openURL(Context mContext, String URL) {

        String inputUrl = URL;
        /*if (!inputUrl.contains("http://"))
            inputUrl = "http://" + inputUrl;*/

        java.net.URL url = null;
        try {
            url = new URL(inputUrl);
        } catch (MalformedURLException e) {
            Log.v("myApp", "bad url entered");
        }
        if (url == null) {

        } else {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(inputUrl));
            mContext.startActivity(i);
        }

    }


    public static void openURL1(Context mContext, String URL) {

        String inputUrl = URL;
        if (!inputUrl.contains("http://"))
            inputUrl = "http://" + inputUrl;

        java.net.URL url = null;
        try {
            url = new URL(inputUrl);
        } catch (MalformedURLException e) {
            Log.v("myApp", "bad url entered");
        }
        if (url == null) {

        } else {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(inputUrl));
            mContext.startActivity(i);
        }

    }

    public static void setOTP_Text(Context mContext, String cat) {
        SharedPreferences preferences = mContext.getSharedPreferences("LionesClubPreferences", 0); // 0 - for private mode
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("otp_text", cat);
        editor.commit();
    }

    public static String getOTP_Text(Context mContext) {
        SharedPreferences preferences = mContext.getSharedPreferences("LionesClubPreferences", 0); // 0 - for private mode
        String name = preferences.getString("otp_text", "");
        return name;
    }

    //---------------------------------------------------------------------------------------
    public static void sendMail(Context mContext, String mailto,
                                String name, String email, String mobile,
                                String message, int M_REQUEST_CODE) {
        // String body1 = msg.trim() + "\n\nPhone Number: " + ph +
        // "\nEmail Id: " + mail;
        String body = "Name: " + name + "\nEmail: "
                + email + "\nMobile: " + mobile +
                "\n\nMessage:\n " + message.trim();

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:" + mailto));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Enquiry From Lions Clubs 322B1 App");
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);
        //emailIntent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
        try {
            //mContext.startActivity(Intent.createChooser(emailIntent, "Send email using..."));
            ((Activity) mContext).startActivityForResult(Intent.createChooser(emailIntent, "Send email using..."), M_REQUEST_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(mContext, "No email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public static void sendMail(Context mContext, String mailto, int M_REQUEST_CODE) {
        // String body1 = msg.trim() + "\n\nPhone Number: " + ph +
        // "\nEmail Id: " + mail;
        /*String body = "Name: " + name + "\nEmail: "
                + email + "\nMobile: " + mobile +
				"\n\nMessage:\n " + message.trim();*/

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:" + mailto));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Enquiry From Lions Clubs 322B1 App");
        //emailIntent.putExtra(Intent.EXTRA_TEXT, body);
        //emailIntent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
        try {
            //mContext.startActivity(Intent.createChooser(emailIntent, "Send email using..."));
            ((Activity) mContext).startActivityForResult(Intent.createChooser(emailIntent, "Send email using..."), M_REQUEST_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(mContext, "No email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }


    //---------------------------------------------------------------------------------------

    /**
     * @param normal_date
     * @return dd MMMM
     */
    public static String getFormattedDate(String normal_date) {
        String anni = normal_date;
        String formated_date = "";
        if (anni.length() > 6) {
            SimpleDateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            //SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            SimpleDateFormat targetFormat = new SimpleDateFormat("dd, MMM yyyy");
            Date date;
            try {
                date = originalFormat.parse(anni);
                formated_date = targetFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            formated_date = anni;
        }
        return formated_date;
    }

    public static String getFormattedServerDate(String normal_date) {
        String anni = normal_date;
        String formated_date = "";
        if (anni.length() > 6) {
            SimpleDateFormat originalFormat = new SimpleDateFormat("dd, MMM yyyy", Locale.ENGLISH);
            //SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date;
            try {
                date = originalFormat.parse(anni);
                formated_date = targetFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            formated_date = anni;
        }
        return formated_date;
    }


    /**
     * @param normal_date
     * @return dd MMMM, yyyy
     */
    public static String getFormattedDate2(String normal_date) {
        String anni = normal_date;
        String formated_date = "";
        if (anni.length() > 6) {
            //SimpleDateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            SimpleDateFormat targetFormat = new SimpleDateFormat("dd, MMM yyyy");
            Date date;
            try {
                date = originalFormat.parse(anni);
                formated_date = targetFormat.format(date);  // 20120821
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            formated_date = anni;
        }
        return formated_date;
    }

    public static String getFormattedDate3(String normal_date) {
        String anni = normal_date;
        String formated_date = "";
        if (anni.length() > 6) {
            //SimpleDateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            SimpleDateFormat targetFormat = new SimpleDateFormat("dd MMM yyyy");
            Date date;
            try {
                date = originalFormat.parse(anni);
                formated_date = targetFormat.format(date);  // 20120821
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            formated_date = anni;
        }
        return formated_date;
    }

    public static String getFormattedDateevent(String normal_date) {
        String anni = normal_date;
        String formated_date = "";
        if (anni.length() > 6) {
            //SimpleDateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            SimpleDateFormat targetFormat = new SimpleDateFormat("dd, MMMM yyyy");
            Date date;
            try {
                date = originalFormat.parse(anni);
                formated_date = targetFormat.format(date);  // 20120821
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            formated_date = anni;
        }
        return formated_date;
    }


    /**
     * open google map from location.
     *
     * @param mContext
     * @param addr
     * @author SOUMIK MAITY
     */
    public static void openMapFromLocation(Context mContext, String addr) {
        /*String query = addr.trim() + "(" + mCompany+memAdr + ")";*/
        String query = addr.trim();
        String encodedQuery = Uri.encode(query);
        String uri = "geo:0,0?q=" + encodedQuery + "&z=16";
        mContext.startActivity(new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri)));
    }

    public static void showAlert(Context context, String msg, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setTextColor(context.getResources().getColor(R.color.black));
    }

    public static String getMonthFormattedDateAppoList_(String normal_date) {
        String anni = normal_date;
        String formated_date = "";

        if (normal_date.equals("0000-00-00"))
            formated_date = "";

        if (anni.length() > 6) {
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            SimpleDateFormat targetFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date;
            try {
                date = originalFormat.parse(anni);
                formated_date = targetFormat.format(date); // 20120821
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            formated_date = anni;
        }
        return formated_date;
    }

    public static ProgressDialog initializeProgressDialog(Context context) {
        ProgressDialog pDialog;
        pDialog = new ProgressDialog(context);
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setMessage("Please wait");
        return pDialog;
    }

    public static void showToastShort(Context mContext, String msg) {
        Toast toast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void hideItem(Context mContext) {
       /* NavigationView navigationView = (NavigationView) ((Activity) mContext).findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_create_event).setVisible(false);*/
    }

    public static void showItem(Context mContext) {
       /* NavigationView navigationView = (NavigationView) ((Activity) mContext).findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_create_event).setVisible(true);*/
    }

    public static void hideKeyboard(Context mContext) {
        View view = ((Activity) mContext).getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void showKeyboard(Context mContext) {
        View view = ((Activity) mContext).getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInputFromWindow(view.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
        }
    }



   /* public static void openNavDrawer(int id, final Context mContext) {
        ConnectionDetector cd = new ConnectionDetector(mContext);
        if (id == R.id.nav_map) {
            if (!(mContext instanceof MapsActivity)) {
                if (LCUtils.getMemberIdPreferences(mContext).equals("d852df9f93f19de1cbacf44e55635b84")) {
                    LCUtils.openGuestDialog(mContext);
                } else {
                    if (cd.isConnected()) {
                        Intent intent = new Intent(mContext, MapsActivity.class);
                        mContext.startActivity(intent);
                        ((Activity) mContext).overridePendingTransition(R.anim.anim_in_reverse, R.anim.anim_out_reverse);
                    } else {
                        LCUtils.showToastShort(mContext, "Internet Connection Required");
                    }
                }
            }
        } else if (id == R.id.nav_myhome) {
            if (!(mContext instanceof Dashboard)) {
                Intent intent = new Intent(mContext, Dashboard.class);
                mContext.startActivity(intent);
                ((Activity) mContext).overridePendingTransition(R.anim.anim_in_reverse, R.anim.anim_out_reverse);
            }
        } else if (id == R.id.nav_myprofile) {
            if (cd.isConnected()) {
                if (!(mContext instanceof ActivityEditProfile)) {
                    if (LCUtils.getMemberIdPreferences(mContext).equals("d852df9f93f19de1cbacf44e55635b84")) {
                        LCUtils.openGuestDialog(mContext);
                    } else {
                        Intent intent = new Intent(mContext, ActivityEditProfile.class);
                        mContext.startActivity(intent);
                        ((Activity) mContext).overridePendingTransition(R.anim.anim_in_reverse, R.anim.anim_out_reverse);
                    }
                }
            } else {
                LCUtils.showToastShort(mContext, "Internet Connection Required");
            }
        } else if (id == R.id.nav_group)

        {
            if (cd.isConnected()) {
                if (!(mContext instanceof MyGroups)) {
                    if (LCUtils.getMemberIdPreferences(mContext).equals("d852df9f93f19de1cbacf44e55635b84")) {
                        LCUtils.openGuestDialog(mContext);
                    } else {

                        Intent intent = new Intent(mContext, MyGroups.class);
                        mContext.startActivity(intent);
                        ((Activity) mContext).overridePendingTransition(R.anim.anim_in_reverse, R.anim.anim_out_reverse);
                    }
                }
            } else {
                LCUtils.showToastShort(mContext, "Internet Connection Required");
            }
        } else if (id == R.id.nav_refresh)

        {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setCancelable(false);
            builder.setTitle("Update Data");
            builder.setMessage("Data update process might take a few minutes. Please Stay in...");
            builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ConnectionDetector cd = new ConnectionDetector(mContext);
                    if (cd.isConnected()) {
                        if (!LCUtils.getMemberIdPreferences(mContext).equals("d852df9f93f19de1cbacf44e55635b84")) {
                            LCUtils.setDownloadKeyPreferences(mContext, false);
                            Intent i = new Intent(mContext, Dashboard.class);
                            mContext.startActivity(i);
                            ((Activity) mContext).overridePendingTransition(R.anim.anim_in_reverse, R.anim.anim_out_reverse);
                            ((Activity) mContext).finish();
                        } else {
                            LCUtils.setGuestDownloadKeyPreferences(mContext, false);
                            Intent i = new Intent(mContext, Dashboard.class);
                            mContext.startActivity(i);
                            ((Activity) mContext).overridePendingTransition(R.anim.anim_in_reverse, R.anim.anim_out_reverse);
                            ((Activity) mContext).finish();
                        }
                    } else {
                        LCUtils.showToastShort(mContext, "Internet Connection Required");
                    }
                }
            })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setIcon(R.drawable.ic_dialog_info);
            AlertDialog alert = builder.create();
            alert.show();
            Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
            pbutton.setTextColor(mContext.getResources().getColor(R.color.black));
            Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
            nbutton.setTextColor(mContext.getResources().getColor(R.color.black));
        } else if (id == R.id.nav_share) {
            LCUtils.shareAll(mContext, "Lions Clubs Int District 322B1\n\n", "", mContext.getResources().getString(R.string.share_pkg)
                    + "\n\n" + "Help Form :\n" + "https://goo.gl/forms/ACwha5dZuNsUgmQT2\n\n" + "Download app \n" + "https://goo.gl/Xwe5DU \n\n" + "Sent using Lions Distt 322B1 Mobile App.");
        } else if (id == R.id.nav_myevents) {
            if (!(mContext instanceof MyEvents)) {
                if (LCUtils.getMemberIdPreferences(mContext).equals("d852df9f93f19de1cbacf44e55635b84")) {
                    LCUtils.openGuestDialog(mContext);
                } else {
                    if (cd.isConnected()) {
                        Intent intent = new Intent(mContext, MyEvents.class);
                        mContext.startActivity(intent);
                        ((Activity) mContext).overridePendingTransition(R.anim.anim_in_reverse, R.anim.anim_out_reverse);
                    } else {
                        LCUtils.showToastShort(mContext, "Internet Connection Required");
                    }
                }
            }
        } else if (id == R.id.nav_create_event) {
            if (LCUtils.getMemberIdPreferences(mContext).equals("d852df9f93f19de1cbacf44e55635b84")) {
                LCUtils.openGuestDialog(mContext);
            } else {
                if (!(mContext instanceof ActivityCreateEvent)) {
                    LCHashMapUtils.m_boolean.clear();
                    LCHashMapUtils.m_group_members1.clear();
                    Intent i = new Intent(mContext, ActivityCreateEvent.class);
                    i.putExtra("from", "");
                    mContext.startActivity(i);
                    ((Activity) mContext).overridePendingTransition(R.anim.anim_in_reverse, R.anim.anim_out_reverse);
                }
            }
        } else if (id == R.id.nav_memupdate) {
            if (!(mContext instanceof MemberUpdate)) {
                Intent i = new Intent(mContext, MemberUpdate.class);
                mContext.startActivity(i);
                ((Activity) mContext).overridePendingTransition(R.anim.anim_in_reverse, R.anim.anim_out_reverse);
            }
        } else if (id == R.id.nav_addupdates) {
            if (!(mContext instanceof AddUpdate)) {
                Intent i = new Intent(mContext, AddUpdate.class);
                mContext.startActivity(i);
                ((Activity) mContext).overridePendingTransition(R.anim.anim_in_reverse, R.anim.anim_out_reverse);
            }
        } else if (id == R.id.nav_BDayAnniv) {
            if (LCUtils.getMemberIdPreferences(mContext).equals("d852df9f93f19de1cbacf44e55635b84")) {
                LCUtils.openGuestDialog(mContext);
            } else {
                if (!(mContext instanceof BDayAnniv)) {
                    Intent i = new Intent(mContext, BDayAnniv.class);
                    mContext.startActivity(i);
                    ((Activity) mContext).overridePendingTransition(R.anim.anim_in_reverse, R.anim.anim_out_reverse);
                }
            }

        } else if (id == R.id.navlogout) {
            showLogoutAlert(mContext, "Are you sure?", "Logout");
        } else if (id == R.id.nav_inbox)

        {
            if (LCUtils.getMemberIdPreferences(mContext).equals("d852df9f93f19de1cbacf44e55635b84")) {
                LCUtils.openGuestDialog(mContext);
            } else {
                if (!(mContext instanceof ActivityInbox)) {
                    Intent i = new Intent(mContext, ActivityInbox.class);
                    mContext.startActivity(i);
                    ((Activity) mContext).overridePendingTransition(R.anim.anim_in_reverse, R.anim.anim_out_reverse);
                }
            }
        } else if (id == R.id.nav_mydirectory)

        {
            if (LCUtils.getMemberIdPreferences(mContext).equals("d852df9f93f19de1cbacf44e55635b84")) {
                LCUtils.openGuestDialog(mContext);
            } else {
                if (!(mContext instanceof ActivityDirectory)) {
                    Intent i = new Intent(mContext, ActivityDirectory.class);
                    mContext.startActivity(i);
                    ((Activity) mContext).overridePendingTransition(R.anim.anim_in_reverse, R.anim.anim_out_reverse);
                }
            }
        } else if (id == R.id.nav_international)

        {
            *//*if (LCUtils.getMemberIdPreferences(mContext).equals("d852df9f93f19de1cbacf44e55635b84")) {
                LCUtils.openGuestDialog(mContext);
            } else {*//*
            if (!(mContext instanceof ActivityInternational)) {
                Intent i = new Intent(mContext, ActivityInternational.class);
                mContext.startActivity(i);
                ((Activity) mContext).overridePendingTransition(R.anim.anim_in_reverse, R.anim.anim_out_reverse);
            }
            //}
        } else if (id == R.id.nav_district322b1)

        {
            if (!(mContext instanceof ActivityDistrict)) {
                Intent i = new Intent(mContext, ActivityDistrict.class);
                mContext.startActivity(i);
                ((Activity) mContext).overridePendingTransition(R.anim.anim_in_reverse, R.anim.anim_out_reverse);
            }
        } else if (id == R.id.nav_district322b1)

        {
            if (!(mContext instanceof ActivityDistrict)) {
                Intent i = new Intent(mContext, ActivityDistrict.class);
                mContext.startActivity(i);
                ((Activity) mContext).overridePendingTransition(R.anim.anim_in_reverse, R.anim.anim_out_reverse);
            }
        } else if (id == R.id.nav_zone)

        {
            if (!(mContext instanceof ActivityZone)) {
                Intent i = new Intent(mContext, ActivityZone.class);
                mContext.startActivity(i);
                ((Activity) mContext).overridePendingTransition(R.anim.anim_in_reverse, R.anim.anim_out_reverse);
            }
        } else if (id == R.id.nav_leo)

        {
            if (LCUtils.getMemberIdPreferences(mContext).equals("d852df9f93f19de1cbacf44e55635b84")) {
                LCUtils.openGuestDialog(mContext);
            } else {
                if (!(mContext instanceof ActivityLeo)) {
                    Intent i = new Intent(mContext, ActivityLeo.class);
                    mContext.startActivity(i);
                    ((Activity) mContext).overridePendingTransition(R.anim.anim_in_reverse, R.anim.anim_out_reverse);
                }
            }
        } else if (id == R.id.nav_events)

        {
            if (!(mContext instanceof ActivityEvents)) {
                Intent i = new Intent(mContext, ActivityEvents.class);
                mContext.startActivity(i);
                ((Activity) mContext).overridePendingTransition(R.anim.anim_in_reverse, R.anim.anim_out_reverse);
            }
        } else if (id == R.id.nav_gallery)

        {
            if (!(mContext instanceof ActivityGalleryWebView)) {
                Intent i = new Intent(mContext, ActivityGalleryWebView.class);
                mContext.startActivity(i);
                ((Activity) mContext).overridePendingTransition(R.anim.anim_in_reverse, R.anim.anim_out_reverse);
            }
        } else if (id == R.id.nav_cantactus)

        {
            if (!(mContext instanceof ActivityContactUs)) {
                Intent i = new Intent(mContext, ActivityContactUs.class);
                mContext.startActivity(i);
                ((Activity) mContext).overridePendingTransition(R.anim.anim_in_reverse, R.anim.anim_out_reverse);
            }
        } else if (id == R.id.nav_powered)

        {
            if (cd.isConnected()) {
                LCUtils.openURL(mContext, "http://www.supertroninfotech.in");
            } else {
                LCUtils.showToastShort(mContext, "Internet Connection Required");
            }
        } else if (id == R.id.nav_help)

        {
            if (cd.isConnected()) {
                LCUtils.openURL(mContext, "https://goo.gl/forms/ACwha5dZuNsUgmQT2");
            } else {
                LCUtils.showToastShort(mContext, "Internet Connection Required");
            }
        }

    }*/




    public static String getdeviceid(Context mContext) {
        TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            if (!telephonyManager.getDeviceId().equals(null)) {
            }
            return telephonyManager.getDeviceId();
        } catch (Exception e) // else
        {
            return Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
    }

    public static String getFormattedDateP(String normal_date) {
        String anni = normal_date;
        String formated_date = "";
        if (anni.length() > 6) {
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            SimpleDateFormat targetFormat = new SimpleDateFormat("dd MMM, yyyy");
            Date date;
            try {
                date = originalFormat.parse(anni);
                formated_date = targetFormat.format(date); // 20120821
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            formated_date = anni;
        }
        return formated_date;
    }

    public static void setNotificationFlag(Context mContext, String NOTIFY_VAL) {
        SharedPreferences preferences = mContext.getSharedPreferences("lionsclub", 0); // 0 - for private mode
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("NOTIFY_Flag", NOTIFY_VAL);
        editor.commit();
    }

    public static String getNotificationFlag(Context mContext) {
        SharedPreferences preferences = mContext.getSharedPreferences("lionsclub", 0); // 0 - for private mode
        String flag = preferences.getString("NOTIFY_Flag", "");
        return flag;
    }

    public static String getgcmtoken(Context mContext) {
        SharedPreferences preferences = mContext.getSharedPreferences("lionsclub_11", 0); // 0 - for private mode
        return preferences.getString("gcm_token", "");
    }

    public static void setgcmtokenPreferences(Context mContext, String user_address) {
        SharedPreferences preferences = mContext.getSharedPreferences("lionsclub_11", 0); // 0 - for private mode
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("gcm_token", user_address);
        editor.apply();
    }

    public static String getGenericPreferences(Context mContext) {
        SharedPreferences preferences = mContext.getSharedPreferences("lionsclub", 0); // 0 - for private mode
        String flag = preferences.getString("msg", "");
        return flag;
    }

    public static void setGenericPreferences(Context mContext, String zone) {
        SharedPreferences preferences = mContext.getSharedPreferences("lionsclub", 0); // 0 - for private mode
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("msg", zone);
        editor.apply();
    }


    public static String getGPreferences(Context mContext) {
        SharedPreferences preferences = mContext.getSharedPreferences("lionsclub", 0); // 0 - for private mode
        String flag = preferences.getString("g", "");
        return flag;
    }

    public static void setGPreferences(Context mContext, String zone) {
        SharedPreferences preferences = mContext.getSharedPreferences("lionsclub", 0); // 0 - for private mode
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("g", zone);
        editor.apply();
    }


    public static String getLastAppPreferences(Context mContext) {
        SharedPreferences preferences = mContext.getSharedPreferences("lionsclub", 0); // 0 - for private mode
        String flag = preferences.getString("last_date_time", "");
        return flag;
    }

    public static void setLastAppPreferences(Context mContext, String zone) {
        SharedPreferences preferences = mContext.getSharedPreferences("lionsclub", 0); // 0 - for private mode
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("last_date_time", zone);
        editor.apply();
    }

    public static String getLeoLastAppPreferences(Context mContext) {
        SharedPreferences preferences = mContext.getSharedPreferences("lionsclub", 0); // 0 - for private mode
        String flag = preferences.getString("last_date_time_leo", "");
        return flag;
    }

    public static void setLeoLastAppPreferences(Context mContext, String zone) {
        SharedPreferences preferences = mContext.getSharedPreferences("lionsclub", 0); // 0 - for private mode
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("last_date_time_leo", zone);
        editor.apply();
    }

    public static String getCommLastAppPreferences(Context mContext) {
        SharedPreferences preferences = mContext.getSharedPreferences("lionsclub", 0); // 0 - for private mode
        String flag = preferences.getString("last_date_time_comm", "");
        return flag;
    }

    public static void setCommLastAppPreferences(Context mContext, String zone) {
        SharedPreferences preferences = mContext.getSharedPreferences("lionsclub", 0); // 0 - for private mode
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("last_date_time_comm", zone);
        editor.apply();
    }

    public static String getLastEventAppPreferences(Context mContext) {
        SharedPreferences preferences = mContext.getSharedPreferences("lionsclub", 0); // 0 - for private mode
        String flag = preferences.getString("last_date_time_ev", "");
        return flag;
    }

    public static void setLastEventAppPreferences(Context mContext, String zone) {
        SharedPreferences preferences = mContext.getSharedPreferences("lionsclub", 0); // 0 - for private mode
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("last_date_time_ev", zone);
        editor.apply();
    }

    public static void saveLastClosedappdate(Context mContext) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        setLastAppPreferences(mContext, formattedDate);
        setLastEventAppPreferences(mContext, formattedDate);
        setLeoLastAppPreferences(mContext, formattedDate);
        setCommLastAppPreferences(mContext, formattedDate);
    }

    public static String getMonthFormattedDate1(String normal_date) {
        String anni = normal_date;
        String formated_date = "";

        if (normal_date.equals("0000-00-00"))
            formated_date = "";

        if (anni.length() > 6) {
//SimpleDateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
// SimpleDateFormat originalFormat = new SimpleDateFormat("MMM dd, yy", Locale.ENGLISH);
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            SimpleDateFormat targetFormat = new SimpleDateFormat("MMMM");
            Date date;
            try {
                date = originalFormat.parse(anni);
                formated_date = targetFormat.format(date); // 20120821
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            formated_date = anni;
        }
        return formated_date;
    }


    public static String getFormattedDate_day(String normal_date) {
        String anni = normal_date;
        String formated_date = "";
        if (anni.length() > 6) {
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            SimpleDateFormat targetFormat = new SimpleDateFormat("dd");
            Date date;
            try {
                date = originalFormat.parse(anni);
                formated_date = targetFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            formated_date = anni;
        }
        return formated_date;
    }

    public static String getFormattedDate_onlyemonth(String normal_date) {
        String anni = normal_date;
        String formated_date = "";
        if (anni.length() > 6) {
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            SimpleDateFormat targetFormat = new SimpleDateFormat("MM");
            Date date;
            try {
                date = originalFormat.parse(anni);
                formated_date = targetFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            formated_date = anni;
        }
        return formated_date;
    }

    ////////////////////////////event dom
    public static void seteventDomPreferences(Context mContext, String user_name) {
        SharedPreferences preferences = mContext.getSharedPreferences("LionesClubPreferences", 0); // 0 - for private mode
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("event_dom_1", user_name);
        editor.apply();
    }

    public static String getEventDomPreferences(Context mContext) {
        SharedPreferences preferences = mContext.getSharedPreferences("LionesClubPreferences", 0); // 0 - for private mode
        return preferences.getString("event_dom_1", "");
    }

    public static void set_old_EventDomflag(Context mContext, String mobile) {
        SharedPreferences preferences = mContext.getSharedPreferences("IBSPreference", 0); // 0 - for private mode
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("event_dom_old", mobile);
        editor.apply();
    }

    public static String get_old_EventDomflag(Context mContext) {
        SharedPreferences loginPreferences = mContext.getSharedPreferences("IBSPreference", 0); // 0 - for private mode
        String a_key = loginPreferences.getString("event_dom_old", "");
        return a_key;
    }
    ////////////////////////////member dom
    public static void setMemberDomPreferences(Context mContext, String user_name) {
        SharedPreferences preferences = mContext.getSharedPreferences("LionesClubPreferences", 0); // 0 - for private mode
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("member_dom_", user_name);
        editor.apply();
    }

    public static String getMemberDomPreferences(Context mContext) {
        SharedPreferences preferences = mContext.getSharedPreferences("LionesClubPreferences", 0); // 0 - for private mode
        return preferences.getString("member_dom_", "");
    }

    public static void set_old_MemberDomflag(Context mContext, String mobile) {
        SharedPreferences preferences = mContext.getSharedPreferences("LionesClubPreferences", 0); // 0 - for private mode
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("member_dom_old", mobile);
        editor.apply();
    }

    public static String get_old_MemberDomflag(Context mContext) {
        SharedPreferences loginPreferences = mContext.getSharedPreferences("LionesClubPreferences", 0); // 0 - for private mode
        String a_key = loginPreferences.getString("member_dom_old", "");
        return a_key;
    }

    ///////////////////////Club Dom
    public static void setClubDomPreferences(Context mContext, String user_name) {
        SharedPreferences preferences = mContext.getSharedPreferences("LionesClubPreferences", 0); // 0 - for private mode
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Club_dom", user_name);
        editor.apply();
    }

    public static String getClubDomPreferences(Context mContext) {
        SharedPreferences preferences = mContext.getSharedPreferences("LionesClubPreferences", 0); // 0 - for private mode
        return preferences.getString("Club_dom", "");
    }

    public static void set_old_ClubDomflag(Context mContext, String mobile) {
        SharedPreferences preferences = mContext.getSharedPreferences("LionesClubPreferences", 0); // 0 - for private mode
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Club_dom_old", mobile);
        editor.apply();
    }

    public static String get_old_Clubflag(Context mContext) {
        SharedPreferences loginPreferences = mContext.getSharedPreferences("LionesClubPreferences", 0); // 0 - for private mode
        String a_key = loginPreferences.getString("Club_dom_old", "");
        return a_key;
    }

    ///////////////////////Group Dom
    public static void setGroupDomPreferences(Context mContext, String user_name) {
        SharedPreferences preferences = mContext.getSharedPreferences("LionesClubPreferences", 0); // 0 - for private mode
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Group_dom", user_name);
        editor.apply();
    }

    public static String getGroupDomPreferences(Context mContext) {
        SharedPreferences preferences = mContext.getSharedPreferences("LionesClubPreferences", 0); // 0 - for private mode
        return preferences.getString("Group_dom", "");
    }

    public static void set_old_GroupDomflag(Context mContext, String mobile) {
        SharedPreferences preferences = mContext.getSharedPreferences("LionesClubPreferences", 0); // 0 - for private mode
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Group_dom_old", mobile);
        editor.apply();
    }

    public static String get_old_Groupflag(Context mContext) {
        SharedPreferences loginPreferences = mContext.getSharedPreferences("LionesClubPreferences", 0); // 0 - for private mode
        String a_key = loginPreferences.getString("Group_dom_old", "");
        return a_key;
    }

    //////district dom
    public static void setDissDomPreferences(Context mContext, String user_name) {
        SharedPreferences preferences = mContext.getSharedPreferences("LionesClubPreferences", 0); // 0 - for private mode
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("new_district_322B1", user_name);
        editor.apply();
    }

    public static String getDisDomPreferences(Context mContext) {
        SharedPreferences preferences = mContext.getSharedPreferences("LionesClubPreferences", 0); // 0 - for private mode
        return preferences.getString("new_district_322B1", "");
    }

    public static void set_old_DisDomflag(Context mContext, String mobile) {
        SharedPreferences preferences = mContext.getSharedPreferences("LionesClubPreferences", 0); // 0 - for private mode
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("new_district_322B1_old", mobile);
        editor.apply();
    }

    public static String get_old_Disflag(Context mContext) {
        SharedPreferences loginPreferences = mContext.getSharedPreferences("LionesClubPreferences", 0); // 0 - for private mode
        String a_key = loginPreferences.getString("new_district_322B1_old", "");
        return a_key;
    }

    //////internationl dom
    public static void setinternationlDomPreferences(Context mContext, String user_name) {
        SharedPreferences preferences = mContext.getSharedPreferences("LionesClubPreferences", 0); // 0 - for private mode
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("ineternationnal_member_dom", user_name);
        editor.apply();
    }

    public static String getinternationlDomPreferences(Context mContext) {
        SharedPreferences preferences = mContext.getSharedPreferences("LionesClubPreferences", 0); // 0 - for private mode
        return preferences.getString("ineternationnal_member_dom", "");
    }

    public static void set_old_internationlDomflag(Context mContext, String mobile) {
        SharedPreferences preferences = mContext.getSharedPreferences("LionesClubPreferences", 0); // 0 - for private mode
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("ineternationnal_member_dom_old", mobile);
        editor.apply();
    }

    public static String get_old_internationlflag(Context mContext) {
        SharedPreferences loginPreferences = mContext.getSharedPreferences("LionesClubPreferences", 0); // 0 - for private mode
        String a_key = loginPreferences.getString("ineternationnal_member_dom_old", "");
        return a_key;
    }

    /////////////////
    ///////////////////////Leo Dom
    public static void setLeoDomPreferences(Context mContext, String user_name) {
        SharedPreferences preferences = mContext.getSharedPreferences("LionesClubPreferences", 0); // 0 - for private mode
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Leo_dom", user_name);
        editor.apply();
    }

    public static String getLeoDomPreferences(Context mContext) {
        SharedPreferences preferences = mContext.getSharedPreferences("LionesClubPreferences", 0); // 0 - for private mode
        return preferences.getString("Leo_dom", "");
    }

    public static void set_old_LeoDomflag(Context mContext, String mobile) {
        SharedPreferences preferences = mContext.getSharedPreferences("LionesClubPreferences", 0); // 0 - for private mode
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Leo_dom_old", mobile);
        editor.apply();
    }

    public static String get_old_Leoflag(Context mContext) {
        SharedPreferences loginPreferences = mContext.getSharedPreferences("LionesClubPreferences", 0); // 0 - for private mode
        String a_key = loginPreferences.getString("Leo_dom_old", "");
        return a_key;
    }


    ///////////////////////Leo Dom
    public static void setCommDomPreferences(Context mContext, String user_name) {
        SharedPreferences preferences = mContext.getSharedPreferences("LionesClubPreferences", 0); // 0 - for private mode
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Leo_dom_comm", user_name);
        editor.apply();
    }

    public static String getCommDomPreferences(Context mContext) {
        SharedPreferences preferences = mContext.getSharedPreferences("LionesClubPreferences", 0); // 0 - for private mode
        return preferences.getString("Leo_dom_comm", "");
    }

    public static void set_old_CommDomflag(Context mContext, String mobile) {
        SharedPreferences preferences = mContext.getSharedPreferences("LionesClubPreferences", 0); // 0 - for private mode
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Leo_dom_old_comm", mobile);
        editor.apply();
    }

    public static String get_old_Commflag(Context mContext) {
        SharedPreferences loginPreferences = mContext.getSharedPreferences("LionesClubPreferences", 0); // 0 - for private mode
        String a_key = loginPreferences.getString("Leo_dom_old_comm", "");
        return a_key;
    }


    public static String getFormattedDatePost(String normal_date) {
        String anni = normal_date;
        String formated_date = "";
        if (anni.length() > 6) {
            SimpleDateFormat originalFormat = new SimpleDateFormat("dd, MMM yyyy", Locale.ENGLISH);
            SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");//dd MMM, yyyy
            Date date;
            try {
                date = originalFormat.parse(anni);
                formated_date = targetFormat.format(date); // 20120821
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            formated_date = anni;
        }
        return formated_date;
    }

    public static String getFormasssttedDate1(String normal_date) {
        String anni = normal_date;
        String formated_date = "";
        if (anni.length() > 6) {
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            SimpleDateFormat targetFormat = new SimpleDateFormat("dd MMM yyyy");
            Date date;
            try {
                date = originalFormat.parse(anni);
                formated_date = targetFormat.format(date);  // 20120821
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            formated_date = anni;
        }
        return formated_date;
    }


}
