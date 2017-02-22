package com.rathor.hci.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rathor.hci.R;
import com.rathor.hci.utils.AppSession;
import com.rathor.hci.utils.FileCache;
import com.rathor.hci.utils.Utils;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private ProgressDialog mBar;
    private Spinner mSpinDay, mSpinMonth, mSpinYear, mSpinGender, mSpinAccom;
    private ImageView mIvProfile;
    private ArrayAdapter<String> dayAdapter;
    private int mSelectedDay = 01, mSelectedMonth = 01, mSelectedYear = 2010;
    private String mAccomodation = "", mSeletedGender = "";
    private static Bitmap sBitmap;
    private String mImageUrl;


    private TextView mTvPersonal, mTvLoginDetails, mTvUnivercity;
    private boolean mIsPersonalOpen, mIsLoginOpen, mIsUniversityOpen;
    private LinearLayout mLinerPersonal, mLinerLogin, mLinerUniversity;
    private EditText mEtFname, mEtLname, mEtMobile, mEtEmail, mEtConfirmEmail, mEtPassword, mEtConfPasswprd,
            mEtUniversity, mEtLevel, mEtCourse;

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        FileCache fileCache = new FileCache(RegistrationActivity.this);
        File file = fileCache.getFile(mImageUrl);
        sBitmap = Utils.decodeFile(file, 400);
        if (sBitmap != null) {
            mIvProfile.setImageBitmap(sBitmap);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);

        mTvPersonal = (TextView) findViewById(R.id.tvPersonal);
        mTvLoginDetails = (TextView) findViewById(R.id.tvLoginDetails);
        mTvUnivercity = (TextView) findViewById(R.id.tvUnivercity);
        mLinerPersonal = (LinearLayout) findViewById(R.id.linerPersonal);
        mLinerLogin = (LinearLayout) findViewById(R.id.linerLogin);
        mLinerUniversity = (LinearLayout) findViewById(R.id.linerUniversity);
        TextView tvRegistration = (TextView) findViewById(R.id.toolbar_title);
        ImageView ivBack = (ImageView) findViewById(R.id.iv_left);
        tvRegistration.setText("Sign Up");
        mImageUrl = "1234" + ".bk";

        mBar = new ProgressDialog(this);
        mBar.setMessage(getResources().getString(R.string.Please_wait));
        mBar.setCancelable(false);

        mSpinDay = (Spinner) findViewById(R.id.spinner1);
        mSpinMonth = (Spinner) findViewById(R.id.spinner2);
        mSpinYear = (Spinner) findViewById(R.id.spinner3);
        mSpinGender = (Spinner) findViewById(R.id.spinner4);
        mSpinAccom = (Spinner) findViewById(R.id.spinner5);

        TextView btnSignUp = (TextView) findViewById(R.id.tvSubmit);
        mIvProfile = (ImageView) findViewById(R.id.ivProfile);
        mEtFname = (EditText) findViewById(R.id.editText1);
        mEtLname = (EditText) findViewById(R.id.editText2);
        mEtMobile = (EditText) findViewById(R.id.editText3);
        mEtEmail = (EditText) findViewById(R.id.editText4);
        mEtConfirmEmail = (EditText) findViewById(R.id.editText5);
        mEtPassword = (EditText) findViewById(R.id.editText6);
        mEtConfPasswprd = (EditText) findViewById(R.id.editText7);
        mEtUniversity = (EditText) findViewById(R.id.editText8);
        mEtLevel = (EditText) findViewById(R.id.editText9);
        mEtCourse = (EditText) findViewById(R.id.editText10);

        mTvPersonal.setOnClickListener(this);
        mTvLoginDetails.setOnClickListener(this);
        mTvUnivercity.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        mIvProfile.setOnClickListener(this);
        ivBack.setVisibility(View.VISIBLE);

        mSpinDay.setOnItemSelectedListener(this);
        mSpinMonth.setOnItemSelectedListener(this);
        mSpinYear.setOnItemSelectedListener(this);
        mSpinGender.setOnItemSelectedListener(this);
        mSpinAccom.setOnItemSelectedListener(this);

        Calendar calendar = Calendar.getInstance();
        mSelectedMonth = calendar.get(Calendar.MONTH);
        mSelectedYear = calendar.get(Calendar.YEAR);

        setSpinGgnder();
        setSpinnerDay();
        setSpinnerMonth();
        setSpinnerYear();
        setSpinAccom();
    }


    private void signUp() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isAcceptingText()) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        String birthDay = "";
        if (mSelectedDay != 0 && mSelectedMonth != 0 && mSelectedYear != 0) {
            birthDay = mSelectedYear + "-" + new DecimalFormat("00").format(mSelectedMonth) + "-" +
                    new DecimalFormat("00").format(mSelectedDay);
        }


        boolean isValidDate = true;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date currentDate = null, strDate = null;

        try {
            strDate = sdf.parse(mSelectedDay + "/" + mSelectedMonth + "/" + mSelectedYear);
            currentDate = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (currentDate.before(strDate) || currentDate.equals(strDate)) {
            isValidDate = false;
        }


        if (mEtFname.getText().toString().equals("")) {
            Toast.makeText(this, getResources().getString(R.string.Please_enter_fname_name), Toast.LENGTH_SHORT).show();
        } else if (mEtLname.getText().toString().equals("")) {
            Toast.makeText(this, getResources().getString(R.string.Please_enter_lname_name), Toast.LENGTH_SHORT).show();
        } else if (mSeletedGender.equals("")) {
            Toast.makeText(this, getResources().getString(R.string.Please_select_gender_name), Toast.LENGTH_SHORT).show();
        } else if (!isValidDate) {
            Toast.makeText(this, getResources().getString(R.string.Date_of_Birth_can_not_be_earlier_than_today_date), Toast.LENGTH_SHORT).show();
        } else if (mEtMobile.getText().toString().length() < 8) {
            Toast.makeText(this, getResources().getString(R.string.Please_enter_phone_number), Toast.LENGTH_SHORT).show();
        } else if (mEtEmail.getText().toString().equals("") || !Patterns.EMAIL_ADDRESS.matcher(mEtEmail.getText().toString()).matches()) {
            Toast.makeText(this, getResources().getString(R.string.Please_enter_correct_email_id), Toast.LENGTH_SHORT).show();
        } else if (mEtConfirmEmail.getText().toString().equals("") || !Patterns.EMAIL_ADDRESS.matcher(mEtEmail.getText().toString()).matches()) {
            Toast.makeText(this, getResources().getString(R.string.Please_enter_confirm_password), Toast.LENGTH_SHORT).show();
        } else if (!mEtEmail.getText().toString().equals(mEtConfirmEmail.getText().toString())) {
            Toast.makeText(this, getResources().getString(R.string.Email_address_not_matching), Toast.LENGTH_SHORT).show();
        } else if (mEtPassword.getText().toString().length() < 4) {
            Toast.makeText(this, getResources().getString(R.string.Please_enter_password), Toast.LENGTH_SHORT).show();
        } else if (mEtConfPasswprd.getText().toString().length() < 4) {
            Toast.makeText(this, getResources().getString(R.string.Please_enter_confirm_password), Toast.LENGTH_SHORT).show();
        } else if (!mEtPassword.getText().toString().equals(mEtConfPasswprd.getText().toString())) {
            Toast.makeText(this, getResources().getString(R.string.Your_password_doesn_match), Toast.LENGTH_SHORT).show();
        } else if (mEtUniversity.getText().toString().equals("")) {
            Toast.makeText(this, getResources().getString(R.string.Please_enter_University), Toast.LENGTH_SHORT).show();
        } else if (mEtLevel.getText().toString().equals("")) {
            Toast.makeText(this, getResources().getString(R.string.Please_enter_Level), Toast.LENGTH_SHORT).show();
        } else if (mEtCourse.getText().toString().equals("")) {
            Toast.makeText(this, getResources().getString(R.string.Please_enter_Course), Toast.LENGTH_SHORT).show();
        } else if (mAccomodation.equals("")) {
            Toast.makeText(this, getResources().getString(R.string.lease_select_Accomodation), Toast.LENGTH_SHORT).show();
        }else if (sBitmap==null) {
            Toast.makeText(this, getResources().getString(R.string.lease_select_profile_image), Toast.LENGTH_SHORT).show();             //profile pic is not needed
        } else {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                finish();
                break;

            case R.id.tvSubmit:
                signUp();
                break;

            case R.id.ivProfile:
                dialogPhoto();
                break;
            case R.id.tvPersonal:
                if (mIsPersonalOpen) {
                    mTvPersonal.setText("1. Personal +");
                    mIsPersonalOpen = false;
                    mLinerPersonal.setVisibility(View.GONE);
                } else {
                    mLinerPersonal.setVisibility(View.VISIBLE);
                    mTvPersonal.setText("1. Personal -");
                    mIsPersonalOpen = true;
                }

                mLinerLogin.setVisibility(View.GONE);
                mLinerUniversity.setVisibility(View.GONE);
                mTvLoginDetails.setText("2. Login Details +");
                mTvUnivercity.setText("3. University +");
                mIsLoginOpen = false;
                mIsUniversityOpen = false;

                break;

            case R.id.tvLoginDetails:
                if (mIsLoginOpen) {
                    mTvLoginDetails.setText("2. Login Details +");
                    mIsLoginOpen = false;
                    mLinerLogin.setVisibility(View.GONE);
                } else {
                    mTvLoginDetails.setText("2. Login Details -");
                    mIsLoginOpen = true;
                    mLinerLogin.setVisibility(View.VISIBLE);
                }
                mLinerPersonal.setVisibility(View.GONE);
                mLinerUniversity.setVisibility(View.GONE);
                mTvPersonal.setText("1. Personal +");
                mTvUnivercity.setText("3. University +");
                mIsPersonalOpen = false;
                mIsUniversityOpen = false;
                break;

            case R.id.tvUnivercity:
                if (mIsUniversityOpen) {
                    mTvUnivercity.setText("3. University +");
                    mIsUniversityOpen = false;
                    mLinerUniversity.setVisibility(View.GONE);
                } else {
                    mTvUnivercity.setText("3. University -");
                    mIsUniversityOpen = true;
                    mLinerUniversity.setVisibility(View.VISIBLE);
                }

                mLinerLogin.setVisibility(View.GONE);
                mLinerPersonal.setVisibility(View.GONE);
                mTvLoginDetails.setText("2. Login Details +");
                mTvPersonal.setText("1. Personal +");
                mIsPersonalOpen = false;
                mIsPersonalOpen = false;
                break;

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinner1:
                if (position == 0)
                    mSelectedDay = 0;
                else
                    mSelectedDay = Integer.parseInt(parent.getSelectedItem().toString());
                break;
            case R.id.spinner2:
                if (position == 0)
                    mSelectedMonth = 0;
                else
                    mSelectedMonth = position;

                updateAdapterData();
                break;
            case R.id.spinner3:
                if (position == 0)
                    mSelectedYear = 0;
                else
                    mSelectedYear = Integer.parseInt(mSpinYear.getSelectedItem().toString());

                updateAdapterData();
                break;
            case R.id.spinner4:
                if (position != 0)
                    mSeletedGender = parent.getSelectedItem().toString();
                break;
            case R.id.spinner5:
                mAccomodation = parent.getSelectedItem().toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void setSpinGgnder() {
        String[] country = {"Select", "Male", "Female"};
        ArrayAdapter<CharSequence> monthAdapter = new ArrayAdapter<CharSequence>(this, R.layout.spinner_text, country);
        monthAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        mSpinGender.setAdapter(monthAdapter);
    }

    private void setSpinAccom() {
        String[] country = {"Yes", "No"};
        ArrayAdapter<CharSequence> monthAdapter = new ArrayAdapter<CharSequence>(this, R.layout.spinner_text, country);
        monthAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        mSpinAccom.setAdapter(monthAdapter);
    }

    private void updateAdapterData() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, mSelectedMonth - 1);
        calendar.set(Calendar.YEAR, mSelectedYear);
        int numberOfDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
        DecimalFormat df = (DecimalFormat) nf;
        df.applyPattern("00");

        ArrayList<String> day = new ArrayList<>();
        for (int i = 1; i <= numberOfDays; i++) {
            day.add(String.valueOf(df.format(i)));
        }
        day.add(0, "Date");
        if (dayAdapter != null) {
            dayAdapter.clear();
            dayAdapter.addAll(day);
            dayAdapter.notifyDataSetChanged();
        }
    }

    private void setSpinnerDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, mSelectedMonth - 1);
        calendar.set(Calendar.YEAR, mSelectedYear);
        int numberOfDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        ArrayList<String> day = new ArrayList<>();

        NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
        DecimalFormat df = (DecimalFormat) nf;
        df.applyPattern("00");

        for (int i = 1; i <= numberOfDays; i++) {
            day.add(String.valueOf(df.format(i)));
        }
        day.add(0, "Date");
        if (dayAdapter != null) {
            dayAdapter.clear();
            dayAdapter.addAll(day);
            mSpinDay.setAdapter(dayAdapter);
            dayAdapter.notifyDataSetChanged();
        } else {
            dayAdapter = new ArrayAdapter<String>(RegistrationActivity.this, R.layout.spinner_text, day);
            dayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
            mSpinDay.setAdapter(dayAdapter);
        }
    }

    private void setSpinnerMonth() {

        String[] months = getResources().getStringArray(R.array.months);
        months[0] = "Month";
        ArrayAdapter<CharSequence> monthAdapter = new ArrayAdapter<CharSequence>(RegistrationActivity.this, R.layout.spinner_text, months);
        monthAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        mSpinMonth.setAdapter(monthAdapter);
    }

    private void setSpinnerYear() {
        ArrayList<String> years = new ArrayList<>();
        years.add(0, "Year");
        int minYear = 1940;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = minYear; i <= currentYear; i++) {
            years.add(String.valueOf(i));
        }

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(RegistrationActivity.this, R.layout.spinner_text, years);
        yearAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        mSpinYear.setAdapter(yearAdapter);
    }



    private void dialogPhoto() {
        final CharSequence[] items = getResources().getStringArray(R.array.gallery);
        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
        builder.setTitle(getResources().getString(R.string.Select_Option));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    FileCache fileCache = new FileCache(RegistrationActivity.this);
                    File file = fileCache.getFile(AppSession.getInstance(RegistrationActivity.this).getUserID());
                    Crop.pickImageCamera(RegistrationActivity.this, file);
                } else if (item == 1) {
                    Crop.pickImage(RegistrationActivity.this);
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(result.getData());
        } else if (requestCode == Crop.REQUEST_CAMERA && resultCode == RESULT_OK) {
            FileCache fileCache = new FileCache(this);
            File file = fileCache.getFile("1234.bk");
            Uri uri = Uri.fromFile(file);
            beginCropFromCamera(uri, uri);

        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, result);
        }

    }

    private void beginCrop(Uri source) {
        FileCache fileCache = new FileCache(this);
        File file = fileCache.getFile("1234.bk");
        Uri uri = Uri.fromFile(file);
        Crop.of(source, uri).asSquare().start(this);
    }

    private void beginCropFromCamera(Uri source, Uri destination) {
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            uploadImage(Crop.getOutput(result));
        } else if (resultCode == Crop.RESULT_ERROR) {
        }
    }

    public void uploadImage(Uri imageUri) {
        Bitmap bitmap = BitmapFactory.decodeFile(imageUri.getPath());
        mIvProfile.setImageBitmap(bitmap);

    }
}
