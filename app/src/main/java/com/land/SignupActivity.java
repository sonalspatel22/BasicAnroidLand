package com.land;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.land.api.model.CountryCodeModel;
import com.land.constant.ApiConstant;
import com.land.constant.AppConstant;
import com.land.constant.UrlConstant;
import com.land.custom.RobotoEditText;
import com.land.custom.RobotoTextView;
import com.land.events.CountryCodeEvent;
import com.land.fragment.CountryPickerFragment;
import com.land.helper.LogHelper;
import com.land.helper.MyHttpClientHelper;
import com.land.utils.CountryCodeUtil;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {
    private static int passwordonoff = 0;
    private static int confirmpasswordonoff = 0;
    MyHttpClientHelper.ProcessResponseHelper mLoginResponseHelper = new MyHttpClientHelper.ProcessResponseHelper() {
        String message;

        @Override
        public void onRequest() {
        }

        @Override
        public void onFinish() {
            Toast.makeText(SignupActivity.this, "" + message, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFail() throws Exception {
        }

        @Override
        public void onResponse(JSONObject jsonObject) {
            try {
                int code = jsonObject.getInt(ApiConstant.JSON_KEY_CODE);
                message = jsonObject.getString(ApiConstant.JSON_KEY_MSG) + "";
                Log.e("message", message);

                if (code == 1) {
                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                    AppConstant.isuser = true;

                } else if (code == 2) {

                } else if (code == 3) {

                } else if (code == 0) {
                    if (message.equals("Success")) {
                        startActivity(new Intent(SignupActivity.this, MainActivity.class));
                    }
                    AppConstant.isuser = true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        ToastUtil.makeText(getActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
        }
    };
    private ImageView imageView;
    private RobotoEditText etName;
    private RobotoEditText etDisplayname;
    private RobotoEditText etEmail;
    private RobotoEditText etMobileno;
    private RobotoEditText etcode;
    private RobotoEditText etPassword;
    private RobotoEditText etConfirmpassword;
    private RobotoTextView tvSignup;
    private CountryPickerFragment mCountryPickerFragment;
    private CountryCodeModel mSelectedCountryCode;

    private void findViews() {

        etName = (RobotoEditText) findViewById(R.id.et_name);
        etDisplayname = (RobotoEditText) findViewById(R.id.et_displayname);

        etEmail = (RobotoEditText) findViewById(R.id.et_email);
        etMobileno = (RobotoEditText) findViewById(R.id.et_mobileno);
        etcode = (RobotoEditText) findViewById(R.id.et_country_code);
        etPassword = (RobotoEditText) findViewById(R.id.et_password);
        etConfirmpassword = (RobotoEditText) findViewById(R.id.et_confirmpassword);
        tvSignup = (RobotoTextView) findViewById(R.id.tv_signup);
        imageView = (ImageView) findViewById(R.id.iv_background);
        Picasso.with(getApplicationContext()).load(R.drawable.bg).fit().centerCrop().into(imageView);
        tvSignup.setOnClickListener(this);
        etcode.setOnClickListener(this);
        etPassword.setOnTouchListener(this);
        etConfirmpassword.setOnTouchListener(this);
        mSelectedCountryCode = CountryCodeUtil.getSystemCountryCode(this);

//        if (mSelectedCountryCode != null) {
//            etcode.setText(mSelectedCountryCode.getCode());
//        }
    }

    @Override
    public void onClick(View v) {
        if (v == tvSignup) {

            actionSignUp();
        } else if (v == etcode) {
            if (mCountryPickerFragment != null) {
                mCountryPickerFragment.show(getSupportFragmentManager(), "COUNTRYCODE");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        findViews();
        if (mCountryPickerFragment == null)
            mCountryPickerFragment = new CountryPickerFragment();
    }

    private boolean validate() {

        boolean flag = true;

        String Name = etName.getText().toString().trim();
        if (Name.length() == 0) {
            etName.setError("Enter valid Name");
            flag = false;
        }


        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString().trim()).matches()) {
            etEmail.setError("Enter valid email");
            flag = false;
        }

        if (mSelectedCountryCode == null) {
            etcode.setError("Select country");
            flag = false;
        } else {
            String phone = etMobileno.getText().toString().trim();
            if (!TextUtils.isDigitsOnly(phone) || phone.length() != mSelectedCountryCode.getValidationLength()) {
                etMobileno.setError("Enter valid  " + mSelectedCountryCode.getValidationLength() + " digit phone number");
                flag = false;
            }
        }


//        String code = RobotoEditTextCountryCode.getText().toString().trim();
//        if (code.length() == 0) {
//            RobotoEditTextCountryCode.setError("Select country code");
//
//            flag = false;
//        }


        String password = etPassword.getText().toString().trim();
        /*String pattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*_=+-/])[A-Za-z\\d!@#$%^&*_=+-/]{8,}$";
        if (!password.matches(pattern)) {
            RobotoEditTextPassword.setError(getString(R.string.password_hint));
            LogHelper.e("Password Error", password);
            flag = false;
        }*/

        if (password.length() < 6) {
            etPassword.setError(getString(R.string.password_lenght_hint));
            LogHelper.e("Password Error", password);
            flag = false;
        }


        String confirmPassword = etConfirmpassword.getText().toString().trim();

        if (!confirmPassword.equals(password)) {
            etConfirmpassword.setError("Password mismatch");
            LogHelper.e("Password CONFIRM", password);
            flag = false;
        }


        return flag;
    }

    @Override
    public void onStart() {
        super.onStart();

        EventBus.getDefault().register(this);

    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(CountryCodeEvent event) {
        try {
            mSelectedCountryCode = event.getModel();
            etcode.setText(mSelectedCountryCode.getCode());
            mCountryPickerFragment.dismiss();
            etcode.setError(null);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int DRAWABLE_RIGHT = 2;
        if (v == etPassword) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (etPassword.getRight() - etPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    Drawable img;
                    if (passwordonoff == 0) {
                        etPassword.setTransformationMethod(null);
                        img = getResources().getDrawable(R.drawable.icn_password_show);
                        passwordonoff = 1;
                    } else {
                        etPassword.setTransformationMethod(new PasswordTransformationMethod());
                        img = getResources().getDrawable(R.drawable.icn_password);
                        passwordonoff = 0;
                    }
                    etPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
                    return true;
                }
            }
        } else if (v == etConfirmpassword) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (etPassword.getRight() - etConfirmpassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    Drawable img;
                    if (confirmpasswordonoff == 0) {
                        etConfirmpassword.setTransformationMethod(null);
                        img = getResources().getDrawable(R.drawable.icn_password_show);
                        confirmpasswordonoff = 1;
                    } else {
                        etConfirmpassword.setTransformationMethod(new PasswordTransformationMethod());
                        img = getResources().getDrawable(R.drawable.icn_password);
                        confirmpasswordonoff = 0;
                    }
                    etConfirmpassword.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
                    return true;
                }
            }
        }
        return false;
    }

    private void actionSignUp() {
        if (validate()) {
            RequestBody requestBody = new FormBody.Builder()
                    .add("UserName", etName.getText().toString().trim())
                    .add("Password", etPassword.getText().toString().trim())
                    .add("DisplayName", etDisplayname.getText().toString().trim())
                    .add("Email", etEmail.getText().toString().trim())
                    .add("Mobile", etMobileno.getText().toString().trim())
                    .build();
            MyHttpClientHelper.getInstance().enqueueCall(SignupActivity.this, UrlConstant.API_SIGNUP_URL, requestBody, mLoginResponseHelper);
        }
    }

    public void cleardata() {
        etEmail.setText("");
        etName.setText("");
        etDisplayname.setText("");
        etPassword.setText("");
        etMobileno.setText("");
        etcode.setText("");
        etConfirmpassword.setText("");
    }
}
