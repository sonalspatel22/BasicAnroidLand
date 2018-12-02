package com.land.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.land.R;
import com.land.adapter.MymatchcriteriaAdapter;
import com.land.api.model.FilterModel;
import com.land.api.model.MymatchesCriteriamodel;
import com.land.constant.ApiConstant;
import com.land.constant.UrlConstant;
import com.land.helper.MyHttpClientHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;


public class MyMatchesFragment extends Fragment {

    public SwipeRefreshLayout swipeRefreshLayout;
    protected RecyclerView recyclerView;
    protected MymatchcriteriaAdapter mymatchcriteriaAdapter;
    FilterModel f;
    private ImageView ivfilter;
    private Call lastCall;
    private Handler handler;
    private ArrayList<MymatchesCriteriamodel> criteriaofmymatch = new ArrayList<>();
    //strings
    private MyHttpClientHelper.ProcessResponseHelper mPropertiesListingResponseHelper = new MyHttpClientHelper.ProcessResponseHelper() {
        @Override
        public void onRequest() {
        }

        @Override
        public void onResponse(JSONObject object) {
            try {
                int code = object.getInt(ApiConstant.JSON_KEY_CODE);

                if (code == 1) {
                    //mGroups.clear();
                    JSONArray dataObject = object.getJSONArray(ApiConstant.JSON_KEY_DATA);
                    criteriaofmymatch = new ArrayList<>();
                    //Log.e("propertylist", "" + dataObject.getJSONObject(3));
                    for (int i = 0; i < dataObject.length(); i++) {
                        JSONObject property = dataObject.getJSONObject(i);
                        MymatchesCriteriamodel mymatchesCriteriamodel = new MymatchesCriteriamodel();
                        mymatchesCriteriamodel.fromJson(property);
                        criteriaofmymatch.add(mymatchesCriteriamodel);
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                mymatchcriteriaAdapter = new MymatchcriteriaAdapter(getActivity(), criteriaofmymatch);
                                recyclerView.setAdapter(mymatchcriteriaAdapter);
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

    public MyMatchesFragment() {
        // Required empty public constructor
    }

    public static MyMatchesFragment newInstance() {
        MyMatchesFragment fragment = new MyMatchesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        UrlConstant.fragmenttag = 4;
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_listing, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiper);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                actionGetPropertiesListing();
            }
        });

        handler = new Handler(Looper.getMainLooper());
        initView(view);
    }

    private void initView(View rootView) {

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);

//        listingAdapter = new ListingAdapter(getActivity(), criteriaofmymatch);
//        recyclerView.setAdapter(listingAdapter);

        actionGetPropertiesListing();
    }

    private void actionGetPropertiesListing() {
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setRefreshing(true);
        RequestBody requestBody = new FormBody.Builder().addEncoded("Userid", "1").build();
        lastCall = MyHttpClientHelper.getInstance().enqueueCall(getActivity(), UrlConstant.API_GET_MYMATCHES_CRITERIA_LISTING_URL, requestBody, mPropertiesListingResponseHelper);
    }
}
