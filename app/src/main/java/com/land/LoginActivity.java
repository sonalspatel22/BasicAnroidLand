package com.land;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.land.api.model.UserModel;
import com.land.constant.ApiConstant;
import com.land.constant.AppConstant;
import com.land.constant.UrlConstant;
import com.land.custom.RobotoEditText;
import com.land.custom.RobotoTextView;
import com.land.helper.MyHttpClientHelper;
import com.land.utils.MyPreference;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {
    private static int onoff = 0;
    String name = "Landlord1";
    String pass = "legal_123";
    Handler handler;
    private RobotoEditText etEmail;
    MyHttpClientHelper.ProcessResponseHelper mLoginResponseHelper = new MyHttpClientHelper.ProcessResponseHelper() {
        @Override
        public void onRequest() {
//
//            mHandler.post(new Runnable() {
//                @Override
//                public void run() {
//                    loadingDialog.show();
//                }
//            });

        }

        @Override
        public void onFinish() {

//            mHandler.post(new Runnable() {
//                @Override
//                public void run() {
//                    loadingDialog.dismiss();
//                }
//            });
        }

        @Override
        public void onFail() throws Exception {


        }

        @Override
        public void onResponse(JSONObject jsonObject) {
            try {
                int code = jsonObject.getInt(ApiConstant.JSON_KEY_CODE);
                final String message = jsonObject.getString(ApiConstant.JSON_KEY_MSG) + "";
                Log.e("message", message);
                if (code == 1) {
                    String[] userid = jsonObject.getString(ApiConstant.JSON_KEY_DATA).split("=");
                    String Userid = userid[1];
                    UserModel userModel = new UserModel();
                    userModel.setU_id(Userid);
                    userModel.setU_name(etEmail.getText().toString().trim());
                    MyPreference preference = new MyPreference(getApplicationContext());
                    Gson gson = new Gson();
                    String json = gson.toJson(userModel);
                    preference.setuser(json);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            MyPreference preference = new MyPreference(getApplicationContext());
                            Gson gson = new Gson();
                            String json = preference.getuser();
                            UserModel userModel = gson.fromJson(json, UserModel.class);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            AppConstant.isuser = true;
                            Log.e("uname", userModel.getU_name());
                            Log.e("uid", userModel.getU_id());
                        }
                    });

                } else if (code == 2) {

                } else if (code == 3) {

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
    private RobotoEditText etPassword;
    private RobotoTextView tvLogin;
    private RobotoTextView tvForgotpassword;
    private RobotoTextView tvNewuser;
    private ImageView imageView;

    private void findViews() {
        etEmail = (RobotoEditText) findViewById(R.id.et_email);
        etPassword = (RobotoEditText) findViewById(R.id.et_password);
        tvLogin = (RobotoTextView) findViewById(R.id.tv_login);
        tvForgotpassword = (RobotoTextView) findViewById(R.id.tv_forgotpassword);
        tvNewuser = (RobotoTextView) findViewById(R.id.tv_newuser);
        imageView = (ImageView) findViewById(R.id.iv_background);
        Picasso.with(getApplicationContext()).load(R.drawable.bg).fit().centerCrop().into(imageView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        handler = new Handler(Looper.getMainLooper());
        tvNewuser.setOnClickListener(this);
        tvForgotpassword.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        etPassword.setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_newuser:
                startActivity(new Intent(this, SignupActivity.class));
                break;
            case R.id.tv_forgotpassword:
                break;
            case R.id.tv_login:
                actionLogin();
                break;
        }
    }

    private boolean validate() {

        boolean flag = true;
        String Name = etEmail.getText().toString().trim();
        if (Name.length() == 0) {
            etEmail.setError("Enter valid User Name");
            flag = false;
        }

//        String password = etPassword.getText().toString().trim();
//        String pattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*_=+-/])[A-Za-z\\d!@#$%^&*_=+-/]{8,}$";
//        if (password.length() < 3 || !password.matches(pattern)) {
//            etPassword.setError("Invalid password");
//            flag = false;
//        }

        return flag;
    }

    private void actionLogin() {


        if (validate()) {
            RequestBody requestBody = new FormBody.Builder()
                    .add("UserName", etEmail.getText().toString().trim())
                    .add("Password", etPassword.getText().toString().trim())
                    .build();
            MyHttpClientHelper.getInstance().enqueueCall(LoginActivity.this, UrlConstant.API_LOGIN_URL, requestBody, mLoginResponseHelper);

        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int DRAWABLE_RIGHT = 2;
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (event.getRawX() >= (etPassword.getRight() - etPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                Drawable img;
                if (onoff == 0) {
                    etPassword.setTransformationMethod(null);
                    img = getResources().getDrawable(R.drawable.icn_password_show);
                    onoff = 1;
                } else {
                    etPassword.setTransformationMethod(new PasswordTransformationMethod());
                    img = getResources().getDrawable(R.drawable.icn_password);
                    onoff = 0;
                }
                etPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
                return true;
            }
        }
        return false;
    }
}
