package com.land.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatRatingBar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.land.R;
import com.land.constant.UrlConstant;
import com.land.custom.RobotoTextView;
import com.land.dialog.LoadingDialog;
import com.land.events.ReviewsEvent;
import com.land.helper.LogHelper;
import com.land.helper.MyHttpClientHelper;
import com.land.helper.ReviewAdded;
import com.land.utils.MyPreference;
import com.land.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by Dashrath on 1/5/2018.
 */

public class AddReviewSupplierDialogFragment extends BaseDialogFragment implements MyHttpClientHelper.ProcessResponseHelper, View.OnClickListener, ReviewAdded {

    protected View rootView;
    protected EditText editTextMessage;
    protected AppCompatRatingBar ratingBar, services_ratingBar, convenience_ratingBar;
    protected RobotoTextView buttonSubmit;
    protected RobotoTextView button_cancel;
    protected ImageView buttonClose;
    String JointId;
    private LoadingDialog mProgressDialog;
    private Handler mHandler;
    private Call lastCall;

    public static AddReviewSupplierDialogFragment instantiate(String jointModel) {
        AddReviewSupplierDialogFragment fragment = new AddReviewSupplierDialogFragment();
        fragment.JointId = jointModel;
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), R.style.TransperentTheme);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.supplier_add_review_dialog, container, false);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHandler = new Handler(Looper.getMainLooper());
    }

    private void initView(View rootView) {
        editTextMessage = (EditText) rootView.findViewById(R.id.editText_message);
        ratingBar = (AppCompatRatingBar) rootView.findViewById(R.id.ratingBar);
        services_ratingBar = (AppCompatRatingBar) rootView.findViewById(R.id.services_ratingBar);
        convenience_ratingBar = (AppCompatRatingBar) rootView.findViewById(R.id.convenience_ratingBar);
        buttonSubmit = (RobotoTextView) rootView.findViewById(R.id.button_submit);
        button_cancel = (RobotoTextView) rootView.findViewById(R.id.button_cancel);
        buttonSubmit.setOnClickListener(AddReviewSupplierDialogFragment.this);
        button_cancel.setOnClickListener(AddReviewSupplierDialogFragment.this);
        buttonClose = (ImageView) rootView.findViewById(R.id.button_close);
        buttonClose.setOnClickListener(AddReviewSupplierDialogFragment.this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.button_cancel:
                dismiss();
                break;
            case R.id.button_close:
                dismiss();
                break;
            case R.id.button_submit:
                float rating = services_ratingBar.getRating();
                LogHelper.d("Rating", " " + rating + " " + services_ratingBar.getProgress());
                actionAddReviews();
                break;
            default:
                break;
        }
    }

    private boolean validate() {
        if (TextUtils.isEmpty(editTextMessage.getText())) {
            editTextMessage.setError("Empty");
            return false;
        }

        return true;
    }

    private void actionAddReviews() {

        if (validate()) {

            MyPreference preference = new MyPreference(getActivity());
            //UserModel userModel = preference.getUser();
            // String loginToken = preference.getApiToken();
            RequestBody requestBody = new FormBody.Builder()
                    // .add(ApiConstant.REQUEST_KEY_API_TOKEN, loginToken)
                    .add("user_id", "" + "1")
                    .add("joints_id", "" + JointId)
                    .add("user_name", "" + "Sonal Patels")
                    .add("user_image", "" + "http://reesguru.com/DesktopModules/ReEsWeb/Images/Property-Images/agents-3.jpg")
                    .add("quality_star", "" + ratingBar.getProgress())
                    .add("services_star", "" + services_ratingBar.getProgress())
                    .add("convienancy_star", "" + convenience_ratingBar.getProgress())
                    .add("message", "" + editTextMessage.getText())
                    .build();
            if (lastCall != null) {
                lastCall.cancel();
            }
            lastCall = MyHttpClientHelper.getInstance().enqueueCall(getActivity(), UrlConstant.API_ADD_REVIEW_API, requestBody, this);
        }
    }

    @Override
    public void onRequest() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {

                mProgressDialog = new LoadingDialog(getActivity());
                mProgressDialog.setMessage("Please wait...");
                mProgressDialog.show();
            }
        });
    }

    @Override
    public void onResponse(JSONObject object) throws Exception {

        LogHelper.d("result", object.toString());

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                ToastUtil.makeText(getActivity(), "Sent", Toast.LENGTH_SHORT).show();
                mProgressDialog.dismiss();
                EventBus.getDefault().post(new ReviewsEvent(ReviewsEvent.ACTION_ADD));
                dismiss();
            }
        });
    }

    @Override
    public void onFinish() throws Exception {

    }

    @Override
    public void onFail() throws Exception {

    }


    @Override
    public void reviewAdded(boolean isAdded) {

    }
}
