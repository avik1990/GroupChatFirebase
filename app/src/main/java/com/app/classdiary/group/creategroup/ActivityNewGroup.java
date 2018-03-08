package com.app.classdiary.group.creategroup;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.classdiary.R;
import com.app.classdiary.utils.ConnectionDetector;
import com.app.classdiary.utils.LCHashMapUtils;
import com.app.classdiary.utils.LCUtils;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONObject;
import java.io.File;
import java.util.Calendar;

public class ActivityNewGroup extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recycler_view;
    TextView tv_memcount, tv_save, actionbar_title;
    PercentRelativeLayout img_view;
    ImageView img_back, iv_img;
    LinearLayout title_view;
    private GridLayoutManager lLayout;
    Context mContext;
    Group_adapter healthTipsAdapter;
    ProgressDialog pDialog;
    public static long totalSize = 0;
    private String finalfileUploadPath = "";
    String status = "", msg = "", group_title = "", group_sub = "", group_expdate;
    EditText et_group_title, et_group_sub, et_group_date;
    Calendar myCalendar = Calendar.getInstance();
    String expiry_date = "";
    private Uri filepath = null;
    LinearLayout sv;
    CheckBox chk_visible;
    String chk_value="0";
    ConnectionDetector cd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);
        mContext = this;
        pDialog = LCUtils.initializeProgressDialog(mContext);
        cd=new ConnectionDetector(mContext);
        initialization();
        inflatelist();
    }


    private void inflatelist() {
        /*if(LCHashMapUtils.m_club_name_list.size()>0){
            String[] movieArray = LCHashMapUtils.m_club_name_list.toArray(new String[LCHashMapUtils.m_club_name_list.size()]);
            LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            buttonLayoutParams.setMargins(0,0,4,0);
            for(int i = 0; i < LCHashMapUtils.m_club_name_list.size(); i++){
                TextView myText = new TextView(this);
                myText.setPadding(10,5,10,5);
                myText.setTextSize(13);
                myText.setGravity(Gravity.CENTER);
                myText.setHeight(100);
                myText.setTextColor(Color.parseColor("#000000"));
                myText.setBackgroundResource(R.drawable.rounded_corner_yellow);
                myText.setLayoutParams(buttonLayoutParams);
                myText.setText(movieArray[i]);
                sv.addView(myText);
            }
        }*/
        if (LCHashMapUtils.m_group_members_temp.size() > 0) {
            tv_memcount.setVisibility(View.VISIBLE);
            recycler_view.setVisibility(View.VISIBLE);
            healthTipsAdapter = new Group_adapter(mContext, R.layout.listbar_group_mem, LCHashMapUtils.m_group_members_temp, "dashboard");
            recycler_view.setAdapter(healthTipsAdapter);
        } else {
            tv_memcount.setVisibility(View.GONE);
            recycler_view.setVisibility(View.GONE);
        }
    }

    private void initialization() {
        chk_visible= (CheckBox) findViewById(R.id.chk_visible);
        sv = (LinearLayout)findViewById(R.id.ll);
        lLayout = new GridLayoutManager(ActivityNewGroup.this, 4);
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(lLayout);

        tv_memcount = (TextView) findViewById(R.id.tv_memcount);
        tv_save = (TextView) findViewById(R.id.tv_save);
        actionbar_title = (TextView) findViewById(R.id.actionbar_title);
        iv_img = (ImageView) findViewById(R.id.iv_img);

        img_view = (PercentRelativeLayout) findViewById(R.id.img_view);
        et_group_title = (EditText) findViewById(R.id.et_group_title);
        et_group_date = (EditText) findViewById(R.id.et_group_date);
        et_group_sub = (EditText) findViewById(R.id.et_group_sub);

        img_back = (ImageView) findViewById(R.id.img_back);
        title_view = (LinearLayout) findViewById(R.id.title_view);

        img_back.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        img_view.setOnClickListener(this);
        title_view.setOnClickListener(this);
        et_group_date.setOnClickListener(this);
        tv_memcount.setText("Members: " + LCHashMapUtils.m_group_members_temp.size());


        chk_visible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    chk_value="1";
                }else{
                    chk_value="0";
                }
            }
        });

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
            expiry_date = formatteddate;
            et_group_date.setText(LCUtils.getFormattedDate(formatteddate));
        }
    };

    @Override
    public void onClick(View v) {
        if (v == et_group_date) {
            DatePickerDialog dpd = new DatePickerDialog(ActivityNewGroup.this, date,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            dpd.getDatePicker().setMinDate(System.currentTimeMillis());
            dpd.show();
        }
        if (v == img_back || v == title_view) {
            onBackPressed();
        }

        if (v == tv_save) {
           /* if (filepath != null && !filepath.equals(Uri.EMPTY)) {
                finalfileUploadPath = getPath(mContext, filepath);
            } else {
                finalfileUploadPath = "";
            }*/
            group_title = et_group_title.getText().toString().trim();
            group_sub = et_group_sub.getText().toString().trim();
            group_expdate = et_group_date.getText().toString().trim();
            if (!group_title.isEmpty()) {
                if (!group_sub.isEmpty()) {
                    if (!group_expdate.isEmpty()) {
                        if (cd.isConnected()) {
                           // pDialog.show();
                           // new Uploadimage().execute();
                        }
                    } else {
                        LCUtils.showToastShort(mContext, "Please Select Expiry Date");
                    }
                } else {
                    LCUtils.showToastShort(mContext, "Please Enter Group Subject");
                }
            } else {
                LCUtils.showToastShort(mContext, "Please Enter Group Title");
            }
        }
        if (v == img_view) {
            /*final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add Photo!");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (options[item].equals("Take Photo")) {
                        new PickerBuilder(ActivityNewGroup.this, PickerBuilder.SELECT_FROM_CAMERA)
                                .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                                    @Override
                                    public void onImageReceived(Uri imageUri) {
                                        iv_img.setImageURI(imageUri);
                                        filepath = imageUri;
                                    }
                                })
                                .setImageName("testImage")
                                .setImageFolderName("testFolder")
                                .withTimeStamp(false)
                                .setCropScreenColor(Color.parseColor("#00529c"))
                                .start();
                    } else if (options[item].equals("Choose from Gallery")) {
                        new PickerBuilder(ActivityNewGroup.this, PickerBuilder.SELECT_FROM_GALLERY)
                                .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                                    @Override
                                    public void onImageReceived(Uri imageUri) {
                                        iv_img.setImageURI(imageUri);
                                        filepath = imageUri;
                                        //  Toast.makeText(ActivityNewGroup.this,"Got image - " + imageUri,Toast.LENGTH_LONG).show();
                                    }
                                })
                                .setImageName("test")
                                .setImageFolderName("testFolder")
                                .setCropScreenColor(Color.parseColor("#00529c"))
                                .setOnPermissionRefusedListener(new PickerBuilder.onPermissionRefusedListener() {
                                    @Override
                                    public void onPermissionRefused() {
                                    }
                                })
                                .start();

                    } else if (options[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();*/
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setActivityTitle("Resize")
                    .setCropShape(CropImageView.CropShape.RECTANGLE)
                    .setCropMenuCropButtonTitle("Done")
                    .setRequestedSize(200, 200)
                    .setFixAspectRatio(true)
                    .setCropMenuCropButtonIcon(R.drawable.ic_checked)
                    .start(this);
        }
    }


    /*class Uploadimage extends AsyncTask<Void, Integer, String> {
        @SuppressWarnings("deprecation")

        @Override
        protected void onProgressUpdate(Integer... progress) {
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                return post_edit_images(mContext, finalfileUploadPath);
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
                LCHashMapUtils.m_group_members_temp.clear();
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

    /*public String post_edit_images(Context mContext, String iamgeurl) {
       *//*String upLoadServerUri = mContext.getResources().getString(R.string.baseurl)
                + mContext.getString(R.string.groupcreate)
                + mContext.getResources().getString(R.string.format);*//*
        String upLoadServerUri = mContext.getResources().getString(R.string.groupcreate1);
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

            if (!iamgeurl.equals("") || !iamgeurl.isEmpty())
                entity.addPart("group_image", new FileBody(sourceFile));
            entity.addPart("group_owner_id", new StringBody(LCUtils.getMemberIdPreferences(mContext)));
            entity.addPart("member_id", new StringBody(LCUtils.getMemberIdPreferences(mContext)));
            entity.addPart("group_title", new StringBody(group_title));
            entity.addPart("group_description", new StringBody(group_sub));
            entity.addPart("group_expiry", new StringBody(LCUtils.getFormattedServerDate(group_expdate)));
            entity.addPart("device_type", new StringBody("A"));
            entity.addPart("device_id", new StringBody(LCUtils.getdeviceid(mContext)));
            entity.addPart("group_visible_status", new StringBody(chk_value));

            if (LCHashMapUtils.m_group_members_temp.size() > 0) {
                for (int i = 0; i < LCHashMapUtils.m_group_members_temp.size(); i++) {
                    entity.addPart("group_users[]", new StringBody(LCHashMapUtils.m_group_members_temp.get(i).getUser_id()));
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
            } else {
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseString;
    }

    private void showSuccessDialogFalse() {
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

    /*private void showSuccessDialogTrue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(false);
        builder.setTitle("Message");
        builder.setMessage(msg);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent i = new Intent(mContext, MyGroups.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(i);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                finish();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(mContext.getResources().getColor(R.color.black));
    }*/

    ///////////////////////////////////////////////////////////////////////////////
    public static String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }


    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

   /* @Override
    protected void onDestroy() {
        super.onDestroy();
        LCUtils.saveLastClosedappdate(mContext);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LCUtils.saveLastClosedappdate(mContext);
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                iv_img.setImageURI(result.getUri());
                filepath = result.getUri();
                if (filepath != null && !filepath.equals(Uri.EMPTY)) {
                    finalfileUploadPath = getPath(mContext, filepath);
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_SHORT).show();
            }
        }
    }











}
