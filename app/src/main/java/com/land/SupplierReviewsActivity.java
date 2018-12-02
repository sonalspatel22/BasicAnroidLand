package com.land;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.land.adapter.AdapterSupplierReviews;
import com.land.api.model.SupplierReviewModel;
import com.land.constant.ApiConstant;
import com.land.constant.AppConstant;
import com.land.constant.UrlConstant;
import com.land.custom.RobotoTextView;
import com.land.events.ReviewsEvent;
import com.land.fragment.AddReviewSupplierDialogFragment;
import com.land.helper.MyHttpClientHelper;
import com.land.helper.TintDrawableHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class SupplierReviewsActivity extends AppCompatActivity {

    protected Toolbar toolbar;
    RecyclerView recyclerView;
    AdapterSupplierReviews adapterSupplierReviews;
    ProgressDialog progressDialog;
    String jointId;
    List<SupplierReviewModel> supplierReviewModels;
    RobotoTextView txt_review;
    RobotoTextView txt_quality_count;
    RobotoTextView txt_service_count;
    RobotoTextView txt_convenience_count;
    RobotoTextView txt_base_on;
    ProgressBar pro_quality;
    ProgressBar pro_service;
    ProgressBar pro_convenience;
    String total_review = "0";
    String quality_star = "0";
    String services_star = "0";
    String convienancy_star = "0";
    private Handler mHandler;
    private MyHttpClientHelper.ProcessResponseHelper mResponseHelper = new MyHttpClientHelper.ProcessResponseHelper() {
        @Override
        public void onRequest() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    progressDialog = new ProgressDialog(SupplierReviewsActivity.this);
                    progressDialog.setIcon(R.drawable.icn_draw);
                    progressDialog.setCancelable(false);
                    progressDialog.setTitle(R.string.app_name);
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();
                }
            });
        }

        @Override
        public void onFinish() {

            mHandler.post(new Runnable() {
                @Override
                public void run() {

                    if (progressDialog != null && progressDialog.isShowing())
                        progressDialog.dismiss();
                }
            });
        }

        @Override
        public void onFail() throws Exception {

            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();
        }

        @Override
        public void onResponse(JSONObject jsonObject) {
            try {
                final String msg = jsonObject.getString(ApiConstant.JSON_KEY_MSG);
                if (jsonObject.getInt(ApiConstant.JSON_KEY_CODE) == 1) {
                    JSONObject objectData = jsonObject.getJSONObject(ApiConstant.JSON_KEY_DATA);
                    JSONArray jsonArray = objectData.getJSONArray("review_list");

                    final JSONArray average_reviews = objectData.getJSONArray("average_review");
                    if (average_reviews.length() > 0) {
                        final JSONObject average_review = average_reviews.getJSONObject(0);

                        total_review = average_review.getString("total_count");
                        quality_star = average_review.getString("quality_star");
                        services_star = average_review.getString("services_star");
                        convienancy_star = average_review.getString("convienancy_star");
                    }


                    supplierReviewModels = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {

                        SupplierReviewModel jointReviewModel = new SupplierReviewModel();
                        jointReviewModel.fromJson(jsonArray.getJSONObject(i));
                        supplierReviewModels.add(jointReviewModel);
                    }

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            adapterSupplierReviews = new AdapterSupplierReviews(SupplierReviewsActivity.this, supplierReviewModels);
                            recyclerView.setAdapter(adapterSupplierReviews);
                            txt_base_on.setText("Based on " + total_review + " Reviews");
                            txt_quality_count.setText(quality_star + " / 5.0");
                            txt_service_count.setText(services_star + " / 5.0");
                            txt_convenience_count.setText(convienancy_star + " / 5.0");
                            pro_quality.setProgress((int) Double.parseDouble(quality_star));
                            pro_service.setProgress((int) Double.parseDouble(services_star));
                            pro_convenience.setProgress((int) Double.parseDouble(convienancy_star));
                            Double treviews = (Double.parseDouble(quality_star) + Double.parseDouble(services_star) + Double.parseDouble(convienancy_star)) / 3;
                            txt_review.setText("" + String.format("%.1f", treviews) + "");

                        }
                    });

                } else {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SupplierReviewsActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            } catch (JSONException e) {
                e.printStackTrace();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SupplierReviewsActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_reviews);
        initView();
        setResult(RESULT_CANCELED);
    }

    private void initView() {

        Intent intent = getIntent();
        jointId = intent.getExtras().getString("joint_id");
        mHandler = new Handler(getMainLooper());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationIcon(TintDrawableHelper.getTintedResource(this, android.support.design.R.drawable.abc_ic_ab_back_material, R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        txt_review = (RobotoTextView) findViewById(R.id.txt_review);
        txt_base_on = (RobotoTextView) findViewById(R.id.txt_base_on);
        txt_quality_count = (RobotoTextView) findViewById(R.id.txt_quality_count);
        txt_service_count = (RobotoTextView) findViewById(R.id.txt_service_count);
        txt_convenience_count = (RobotoTextView) findViewById(R.id.txt_convenience_count);
        pro_quality = (ProgressBar) findViewById(R.id.pro_quality);
        pro_service = (ProgressBar) findViewById(R.id.pro_service);
        pro_convenience = (ProgressBar) findViewById(R.id.pro_convenience);
        pro_quality.setMax(5);
        pro_service.setMax(5);
        pro_convenience.setMax(5);
        actionGetReviewList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_addreviews, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_add:
                if (AppConstant.isuser) {
                    AddReviewSupplierDialogFragment.instantiate(jointId).show(getSupportFragmentManager(), "POST");
                } else {
                    startActivity(new Intent(SupplierReviewsActivity.this, SignupActivity.class));
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void actionGetReviewList() {
        RequestBody requestBody = new FormBody.Builder().add("joints_id", jointId).build();
        MyHttpClientHelper.getInstance().enqueueCall(this, UrlConstant.API_GET_SUPPLIER_DETAILS, requestBody, mResponseHelper);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ReviewsEvent event) {
        if (event.getAction() == ReviewsEvent.ACTION_ADD) {
            setResult(RESULT_OK);
            actionGetReviewList();
        }
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

}
