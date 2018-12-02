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
import com.land.custom.RobotoButton;
import com.land.dialog.LoadingDialog;
import com.land.events.ReviewsEvent;
import com.land.helper.LogHelper;
import com.land.helper.MyHttpClientHelper;
import com.land.helper.ReviewAdded;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by Dashrath on 11/16/2017.
 */

public class AddReviewDialogFragment extends BaseDialogFragment implements MyHttpClientHelper.ProcessResponseHelper, View.OnClickListener, ReviewAdded {

    public static String agentid;
    public static String userid;
    protected View rootView;
    protected EditText editTextMessage;
    protected AppCompatRatingBar ratingBar;
    protected RobotoButton buttonSubmit;
    protected RobotoButton button_cancel;
    protected ImageView buttonClose;
    String rating;
    String comment;
    private LoadingDialog mProgressDialog;
    private Handler mHandler;
    private Call lastCall;

    public static AddReviewDialogFragment instantiate(String sagentid, String suserid) {
        AddReviewDialogFragment fragment = new AddReviewDialogFragment();
        agentid = sagentid;
        userid = suserid;
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
        rootView = inflater.inflate(R.layout.item_reviews, container, false);
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
        buttonSubmit = (RobotoButton) rootView.findViewById(R.id.button_submit);
        button_cancel = (RobotoButton) rootView.findViewById(R.id.button_cancel);
        buttonSubmit.setOnClickListener(AddReviewDialogFragment.this);
        button_cancel.setOnClickListener(AddReviewDialogFragment.this);
        buttonClose = (ImageView) rootView.findViewById(R.id.button_close);
        buttonClose.setOnClickListener(AddReviewDialogFragment.this);

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
                int rate = (int) ratingBar.getRating();
                rating = String.valueOf(rate);
                comment = String.valueOf(editTextMessage.getText().toString());
                LogHelper.d("Rating", " " + rating + " " + ratingBar.getProgress());
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

            Toast.makeText(getActivity(), "Review sending..", Toast.LENGTH_SHORT).show();
//            Log.e("info",""+agentid+" "+userid+" "+comment+" "+rating);
            //   AgentID,Comments,UserID,RatingStars
            RequestBody requestBodyofrating = new FormBody.Builder()
                    .addEncoded("AgentID", agentid)
                    .addEncoded("Comments", comment)
                    .addEncoded("UserID", userid)
                    .addEncoded("RatingStars", rating)
                    .build();
            lastCall = MyHttpClientHelper.getInstance().enqueueCall(getActivity(), UrlConstant.API_GET_AGENTS_RATE_URL, requestBodyofrating, this);

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

//        LogHelper.d("result", object.toString());

        mHandler.post(new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(getActivity(), "Sucsses", Toast.LENGTH_SHORT).show();
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
