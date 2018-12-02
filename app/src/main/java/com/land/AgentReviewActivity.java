package com.land;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.land.adapter.ReviewListingAdapter;
import com.land.api.model.AgentReviewModel;
import com.land.constant.ApiConstant;
import com.land.constant.AppConstant;
import com.land.constant.UrlConstant;
import com.land.events.ReviewsEvent;
import com.land.fragment.AddReviewDialogFragment;
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

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class AgentReviewActivity extends AppCompatActivity {
    public SwipeRefreshLayout swipeRefreshLayout;
    Toolbar toolbar;
    RecyclerView recyclerView;
    Call lastCall;
    Handler handler;
    String a_id;
    String userid;
    private List<AgentReviewModel> mItems = new ArrayList<>();
    private MyHttpClientHelper.ProcessResponseHelper mPropertiesListingResponseHelper = new MyHttpClientHelper.ProcessResponseHelper() {
        @Override
        public void onRequest() {
        }

        @Override
        public void onResponse(JSONObject object) {
            try {
                int code = object.getInt(ApiConstant.JSON_KEY_CODE);
                if (code == 1) {
                    mItems = new ArrayList<>();
                    JSONArray dataObject = object.getJSONArray(ApiConstant.JSON_KEY_DATA);
                    for (int i = 0; i < dataObject.length(); i++) {
                        JSONObject reviews = dataObject.getJSONObject(i);
                        AgentReviewModel agentReviewModel = new AgentReviewModel();
                        agentReviewModel.fromJson(reviews);
                        mItems.add(agentReviewModel);
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                ReviewListingAdapter adapterAgents = new ReviewListingAdapter(AgentReviewActivity.this, mItems);
                                LinearLayoutManager llmanager_expandlist2 = new LinearLayoutManager(AgentReviewActivity.this);
                                recyclerView.setLayoutManager(llmanager_expandlist2);
                                recyclerView.setAdapter(adapterAgents);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFinish() {

            handler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        swipeRefreshLayout.setRefreshing(false);
                        swipeRefreshLayout.setEnabled(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        public void onFail() throws Exception {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_review);
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
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiper);
        handler = new Handler(Looper.getMainLooper());
        Intent i = getIntent();
        try {
            a_id = i.getStringExtra("agentid");
            userid = i.getStringExtra("userid");
            Log.e("a_id", "" + a_id);
        } catch (Exception e) {
            return;
        }
        actionGetReviewListing();
        setResult(RESULT_CANCELED);

    }

    private void actionGetReviewListing() {
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setRefreshing(true);
        RequestBody requestBody = new FormBody.Builder().addEncoded("AgentID", a_id).build();
        lastCall = MyHttpClientHelper.getInstance().enqueueCall(this, UrlConstant.API_GETING_AGENTS_RATE_URL, requestBody, mPropertiesListingResponseHelper);
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
                    AddReviewDialogFragment.instantiate(a_id, userid).show(this.getSupportFragmentManager(), "POST");
                } else {
                    startActivity(new Intent(AgentReviewActivity.this, SignupActivity.class));
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ReviewsEvent event) {
        if (event.getAction() == ReviewsEvent.ACTION_ADD) {
            setResult(RESULT_OK);
            actionGetReviewListing();
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
