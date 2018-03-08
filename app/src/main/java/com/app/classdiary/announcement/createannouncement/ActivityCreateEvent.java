package com.app.classdiary.announcement.createannouncement;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.app.classdiary.R;
import com.app.classdiary.utils.ConnectionDetector;
import com.app.classdiary.utils.LCUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class ActivityCreateEvent extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    EditText et_title, et_when, et_where, et_start_time, et_end_time, et_fee_member, et_fee_guest;
    EditText et_speaker, et_details, et_contact_person, et_contact_person_mobile;
    TextView tv_save, tv_count, tv_actionbar_title;
    ImageView iv_back, iv_search;
    TimePicker timePicker;
    String title = "", when = "", where = "", start_time = "", end_time = "", fee_member = "0", fee_guest = "", speaker = "", details = "", contact_person = "", contact_person_mobile = "";
    static Context mContext;
    private InputFilter filter;
    int MAX_WORDS = 500;
    String offered_date = "";
    private int hour;
    private int minute;
    static final int TIME_DIALOG_ID = 786;
    static final int TIME_DIALOG_ID_END = 999;
    Calendar myCalendar = Calendar.getInstance();
    String StartTime, EndTime;
    ConnectionDetector cd;
    ProgressDialog pDialog;
    NavigationView navigationView;
    CheckBox chk_guest, chk_hide;
    String enable_guest = "0", hide_event = "0";
    String event_id = "";
    String group_id = "", from = "";
    String url_create_event = "";
    String event_name = "";
    Spinner sp_eventtype;
    String event_type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        mContext = this;
        cd = new ConnectionDetector(mContext);
        pDialog = LCUtils.initializeProgressDialog(mContext);
        /*from = getIntent().getExtras().getString("from");
        if (from.equals("GroupMemberDetails")) {
            group_id = getIntent().getExtras().getString("group_id");
        }*/
        /*navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        TextView txt_name = (TextView) headerView.findViewById(R.id.tv_username);
        ImageView profile_img = (ImageView) headerView.findViewById(R.id.iv_userimg);
        txt_name.setText(LCUtils.getMemberNamePreferences(mContext));
        try {
            if (!LCUtils.getMemberImagePreferences(mContext).isEmpty()) {
                Picasso.with(mContext)
                        .load(LCUtils.getMemberImagePreferences(mContext))
                        .placeholder(R.drawable.user_icon)
                        .error(R.drawable.user_icon)
                        .into(profile_img);
            }
        } catch (Exception e) {
        }
        if (LCUtils.getMenuVar(mContext)) {
            LCUtils.showItem(mContext);
        } else {
            LCUtils.hideItem(mContext);
        }
        LCUtils.setcountnavigation(mContext, navigationView);*/
        initializeView();
    }

    /*@Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }*/

    private void initializeView() {
        sp_eventtype = (Spinner) findViewById(R.id.sp_eventtype);
        et_title = (EditText) findViewById(R.id.et_title);
        et_when = (EditText) findViewById(R.id.et_when);
        et_where = (EditText) findViewById(R.id.et_when1);
        et_start_time = (EditText) findViewById(R.id.et_start_time);
        chk_guest = (CheckBox) findViewById(R.id.chk_guest);
        chk_hide = (CheckBox) findViewById(R.id.chk_hide);
        et_end_time = (EditText) findViewById(R.id.et_end_time);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        et_fee_member = (EditText) findViewById(R.id.et_fee_member);
        et_fee_guest = (EditText) findViewById(R.id.et_fee_guest);
        et_speaker = (EditText) findViewById(R.id.et_speaker);
        et_details = (EditText) findViewById(R.id.et_details);
        et_contact_person = (EditText) findViewById(R.id.et_contact_person);
        et_contact_person_mobile = (EditText) findViewById(R.id.et_contact_person_mobile);

        tv_save = (TextView) findViewById(R.id.tv_save);
        tv_count = (TextView) findViewById(R.id.tv_count);

        iv_back = (ImageView) findViewById(R.id.imageView_back);
        iv_search = (ImageView) findViewById(R.id.imageView_search);
        iv_search.setVisibility(View.INVISIBLE);
        tv_actionbar_title = (TextView) findViewById(R.id.textView_actionbar_title);

        tv_actionbar_title.setText(""+"Create Announcement");
        tv_actionbar_title.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        et_when.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        et_start_time.setOnClickListener(this);
        et_end_time.setOnClickListener(this);

//        Typeface tf_regular = Typeface.createFromAsset(getAssets(), "fonts/Lato-Regular.ttf");
//        tv_actionbar_title.setTypeface(tf_regular);

        et_details.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                int wordsLength = countWords(s.toString());
                if (count == 0 && wordsLength >= MAX_WORDS) {
                    setCharLimit(et_details, et_details.getText().length());
                } else {
                    removeFilter(et_details);
                }
                tv_count.setText(String.valueOf(wordsLength) + "/" + MAX_WORDS + " words");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        chk_guest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (chk_guest.isPressed()) {
                    try {
                        et_title.clearFocus();
                        et_when.clearFocus();
                        et_where.clearFocus();
                        et_start_time.clearFocus();
                        et_end_time.clearFocus();
                        et_fee_member.clearFocus();
                        et_fee_guest.clearFocus();
                        et_speaker.clearFocus();
                        et_details.clearFocus();
                        et_contact_person.clearFocus();
                        et_contact_person_mobile.clearFocus();
                        LCUtils.hideKeyboard(mContext);
                    } catch (Exception e) {
                    }
                }
                if (isChecked) {
                    enable_guest = "1";
                    et_fee_guest.setVisibility(View.VISIBLE);
                } else {
                    enable_guest = "0";
                    et_fee_guest.setVisibility(View.GONE);
                }
            }
        });

        chk_hide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    hide_event = "1";
                } else {
                    hide_event = "0";
                }
            }
        });
        if (from.equals("GroupMemberDetails")) {
            chk_hide.setVisibility(View.GONE);
        } else {
            chk_hide.setVisibility(View.VISIBLE);
        }

        sp_eventtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (pos != 0) {
                    if (pos == 1) {
                        event_type = "1";
                    }
                    if (pos == 2) {
                        event_type = "2";
                    }
                    // LCUtils.showToastShort(mContext, event_type);
                } else {
                    event_type = "";
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == iv_back || view == tv_actionbar_title) {
            onBackPressed();
        }

        if (view == et_when) {
            try {
                et_title.clearFocus();
                et_when.clearFocus();
                et_where.clearFocus();
                et_start_time.clearFocus();
                et_end_time.clearFocus();
                et_fee_member.clearFocus();
                et_fee_guest.clearFocus();
                et_speaker.clearFocus();
                et_details.clearFocus();
                et_contact_person.clearFocus();
                et_contact_person_mobile.clearFocus();
                LCUtils.hideKeyboard(mContext);
            } catch (Exception e) {
            }

            DatePickerDialog dpd = new DatePickerDialog(ActivityCreateEvent.this, date,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            dpd.getDatePicker().setMinDate(System.currentTimeMillis());
            dpd.show();
        }

        if (view == et_start_time) {
            try {
                et_title.clearFocus();
                et_when.clearFocus();
                et_where.clearFocus();
                et_start_time.clearFocus();
                et_end_time.clearFocus();
                et_fee_member.clearFocus();
                et_fee_guest.clearFocus();
                et_speaker.clearFocus();
                et_details.clearFocus();
                et_contact_person.clearFocus();
                et_contact_person_mobile.clearFocus();
                LCUtils.hideKeyboard(mContext);
            } catch (Exception e) {
            }

            showDialog(TIME_DIALOG_ID);
        }

        if (view == et_end_time) {
            try {
                et_title.clearFocus();
                et_when.clearFocus();
                et_where.clearFocus();
                et_start_time.clearFocus();
                et_end_time.clearFocus();
                et_fee_member.clearFocus();
                et_fee_guest.clearFocus();
                et_speaker.clearFocus();
                et_details.clearFocus();
                et_contact_person.clearFocus();
                et_contact_person_mobile.clearFocus();
                LCUtils.hideKeyboard(mContext);
            } catch (Exception e) {
            }
            showDialog(TIME_DIALOG_ID_END);
        }

        if (view == tv_save) {
            title = et_title.getText().toString().trim();
            when = et_when.getText().toString().trim();
            where = et_where.getText().toString().trim();
            start_time = et_start_time.getText().toString().trim();
            end_time = et_end_time.getText().toString().trim();
            fee_member = et_fee_member.getText().toString().trim();
            fee_guest = et_fee_guest.getText().toString().trim();
            event_name = title;
            speaker = et_speaker.getText().toString().trim();
            details = et_details.getText().toString().trim();
            contact_person = et_contact_person.getText().toString().trim();
            contact_person_mobile = et_contact_person_mobile.getText().toString().trim();

            if (!title.isEmpty() || !title.equals("")) {
                if (!when.isEmpty()) {
                    if (!where.isEmpty() || !where.equals("")) {
                        if (!start_time.isEmpty() || !start_time.equals("")) {
                            if (!end_time.isEmpty() || !end_time.equals("")) {
                                if (!fee_member.isEmpty() || !fee_member.equals("")) {
                                    if (chk_guest.isChecked()) {
                                        if (!fee_guest.isEmpty() || !fee_guest.equals("")) {
                                            if (!event_type.isEmpty()) {
                                                if (!speaker.isEmpty() || !speaker.equals("")) {
                                                    if (!details.isEmpty() || !details.equals("")) {
                                                        if (!contact_person.isEmpty() || !contact_person.equals("")) {
                                                            if (!contact_person_mobile.isEmpty() || !contact_person_mobile.equals("")) {
                                                                if (cd.isConnected()) {
                                                                   /* pDialog.show();
                                                                    Createevent_Submit();*/
                                                                } else {
                                                                    LCUtils.hideKeyboard(mContext);
                                                                    Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_SHORT).show();
                                                                }
                                                            } else {
                                                                et_contact_person_mobile.requestFocus();
                                                                LCUtils.showToastShort(mContext, "Enter mobile number");
                                                            }
                                                        } else {
                                                            et_contact_person.requestFocus();
                                                            LCUtils.showToastShort(mContext, "Enter contact person name");
                                                        }
                                                    } else {
                                                        et_details.requestFocus();
                                                        LCUtils.showToastShort(mContext, "Enter details");
                                                    }
                                                } else {
                                                    et_speaker.requestFocus();
                                                    LCUtils.showToastShort(mContext, "Enter speaker name");
                                                }
                                            } else {
                                                LCUtils.showToastShort(mContext, "Select Event Type");
                                            }
                                        } else {
                                            et_fee_guest.requestFocus();
                                            LCUtils.showToastShort(mContext, "Enter guest fee");
                                        }
                                    } else {
                                        if (!event_type.isEmpty()) {
                                            if (!speaker.isEmpty() || !speaker.equals("")) {
                                                if (!details.isEmpty() || !details.equals("")) {
                                                    if (!contact_person.isEmpty() || !contact_person.equals("")) {
                                                        if (!contact_person_mobile.isEmpty() || !contact_person_mobile.equals("")) {
                                                            if (cd.isConnected()) {
                                                                /*pDialog.show();
                                                                Createevent_Submit();*/
                                                                //clearField();
                                                                //LCUtils.showToastShort(mContext, "Data submitted sucessfully...");
                                                            } else {
                                                                LCUtils.hideKeyboard(mContext);
                                                                Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_SHORT).show();
                                                            }
                                                        } else {
                                                            et_contact_person_mobile.requestFocus();
                                                            LCUtils.showToastShort(mContext, "Enter mobile number");
                                                        }
                                                    } else {
                                                        et_contact_person.requestFocus();
                                                        LCUtils.showToastShort(mContext, "Enter contact person name");
                                                    }
                                                } else {
                                                    et_details.requestFocus();
                                                    LCUtils.showToastShort(mContext, "Enter details");
                                                }
                                            } else {
                                                et_speaker.requestFocus();
                                                LCUtils.showToastShort(mContext, "Enter speaker name");
                                            }
                                        } else {
                                            LCUtils.showToastShort(mContext, "Select Event Type");
                                        }
                                    }
                                } else {
                                    et_fee_member.requestFocus();
                                    LCUtils.showToastShort(mContext, "Enter member fee");
                                }
                            } else {
                                et_end_time.requestFocus();
                                LCUtils.showToastShort(mContext, "Enter event end time");
                            }
                        } else {
                            et_start_time.requestFocus();
                            LCUtils.showToastShort(mContext, "Enter event start time");
                        }
                    } else {
                        et_where.requestFocus();
                        LCUtils.showToastShort(mContext, "Enter event place");
                    }
                } else {
                    et_when.requestFocus();
                    LCUtils.showToastShort(mContext, "Enter event date");
                }
            } else {
                et_title.requestFocus();
                LCUtils.showToastShort(mContext, "Enter event title");
            }
        }
    }

    /*private void Createevent_Submit() {
        if (!from.equals("GroupMemberDetails")) {
            url_create_event = getResources().getString(R.string.baseurl)
                    + getResources().getString(R.string.eventsave)
                    + getResources().getString(R.string.format);
        } else {
            url_create_event = getResources().getString(R.string.baseurl_group)
                    + getResources().getString(R.string.creategroupevent)
                    + getResources().getString(R.string.format);
        }
        Log.d("event_save", url_create_event);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_create_event,
                new Response.Listener<String>() {
                    String status = "", msg = "";

                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();
                        try {
                            JSONObject jObj = new JSONObject(response);
                            status = jObj.getString("status");
                            msg = jObj.getString("msg");
                            LCUtils.setGenericPreferences(mContext, msg);
                            if (status.equals("1")) {
                                event_id = jObj.getJSONObject("data").getString("event_id");
                                showSuccessDialogTrue();
                            } else {
                                showSuccessDialogFalse();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("title", title);
                params.put("date", LCUtils.getFormattedServerDate(when));
                params.put("venue", where);
                params.put("start_time", start_time);
                params.put("end_time", end_time);
                params.put("member_fee", fee_member);
                if (fee_guest.isEmpty()) {
                    fee_guest = "0";
                }
                params.put("guest_fee", fee_guest);
                params.put("enable_guest", enable_guest);
                params.put("speaker", speaker);
                params.put("details", details);
                params.put("contact_person", contact_person);
                params.put("contact_mobile", contact_person_mobile);
                if (!from.equals("GroupMemberDetails")) {
                    params.put("hide_event", hide_event);
                }
                params.put("device_id", LCUtils.getdeviceid(mContext));
                params.put("member_id", LCUtils.getMemberIdPreferences(mContext));
                if (from.equals("GroupMemberDetails")) {
                    params.put("user_group_id", group_id);
                }
                params.put("event_type", event_type);
                for (Object o : params.keySet()) {
                    String key = o.toString();
                    String value = params.get(key);
                    Log.d("posted_values", key + "--" + value);
                }
                return params;
            }
        };
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }*/


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String years;
            years = "" + year;
            int month = monthOfYear;
            month = month + 1;
            String strDateTime = years + "-" + month + "-" + dayOfMonth;
            String formatteddate = LCUtils.getMonthFormattedDateAppoList_(strDateTime);
            offered_date = formatteddate;
            et_when.setText(LCUtils.getFormattedDate(formatteddate));
        }
    };

    public void setTime() {
        hour = myCalendar.get(Calendar.HOUR_OF_DAY);
        minute = myCalendar.get(Calendar.MINUTE);
        updateTimeStart(hour, minute);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this, timePickerListenerStart, hour, minute, true);
            case TIME_DIALOG_ID_END:
                return new TimePickerDialog(this, timePickerListenerEnd, hour, minute, true);
        }
        return null;
    }

    // Used to convert 24hr format to 12hr format with AM/PM values
    private void updateTimeStart(int hours, int mins) {
        String timeSet = "";
        /*if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";*/
        if (hours == 0) {
            hours += 12;
        }
        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        StartTime = new StringBuilder().append(hours).append(" ").append(':').append(" ").append(minutes).append(" ").append(timeSet).toString();
        et_start_time.setText(StartTime);
        et_start_time.setSelection(et_start_time.getText().length());
    }

    private TimePickerDialog.OnTimeSetListener timePickerListenerStart = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
            hour = hourOfDay;
            minute = minutes;
            updateTimeStart(hour, minute);
        }
    };

    private void updateTimeEnd(int hours, int mins) {
        String timeSet = "";
        /*if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";*/
        if (hours == 0) {
            hours += 12;
        }
        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        EndTime = new StringBuilder().append(hours).append(" ").append(':').append(" ").append(minutes).append(" ").append(timeSet).toString();
        et_end_time.setText(EndTime);
        et_end_time.setSelection(et_end_time.getText().length());

    }

    private TimePickerDialog.OnTimeSetListener timePickerListenerEnd = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
            hour = hourOfDay;
            minute = minutes;
            updateTimeEnd(hour, minute);
        }
    };

    private int countWords(String s) {
        String trim = s.trim();
        if (trim.isEmpty())
            return 0;
        return trim.split("\\s+").length;
    }

    private void setCharLimit(EditText et, int max) {
        filter = new InputFilter.LengthFilter(max);
        et.setFilters(new InputFilter[]{filter});
    }

    private void removeFilter(EditText et) {
        if (filter != null) {
            et.setFilters(new InputFilter[0]);
            filter = null;
        }
    }

    private void clearField() {
        et_title.setText("");
        et_when.setText("");
        et_where.setText("");
        et_start_time.setText("");
        et_end_time.setText("");
        et_fee_member.setText("");
        et_fee_guest.setText("");
        et_speaker.setText("");
        et_details.setText("");
        et_contact_person.setText("");
        et_contact_person_mobile.setText("");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        /*LCUtils.openNavDrawer(id, mContext);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);*/
        return true;
    }

    private static void showSuccessDialogFalse() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(false);
        builder.setTitle("Sorry");
        builder.setMessage(LCUtils.getGenericPreferences(mContext));
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(mContext.getResources().getColor(R.color.black));
    }

    /*private void showSuccessDialogTrue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(false);
        builder.setTitle("Message");
        builder.setMessage(LCUtils.getGenericPreferences(mContext));
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(mContext, AddEventImage_backup.class);
                i.putExtra("event_id", event_id);
                if (from.equals("GroupMemberDetails")) {
                    i.putExtra("group_id", group_id);
                    i.putExtra("from", "GroupMemberDetails");
                    i.putExtra("event_name", event_name);
                } else {
                    i.putExtra("event_name", event_name);
                    i.putExtra("from", "ActivityCreateEvent");
                }
                mContext.startActivity(i);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                finish();
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(mContext.getResources().getColor(R.color.black));
    }*/
}
