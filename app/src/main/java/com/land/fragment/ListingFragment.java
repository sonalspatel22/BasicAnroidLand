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
import android.widget.Toast;

import com.land.R;
import com.land.adapter.ListingAdapter;
import com.land.api.model.NearestPropertiesModel;
import com.land.constant.ApiConstant;
import com.land.constant.UrlConstant;
import com.land.events.SearchEvent;
import com.land.helper.MyHttpClientHelper;
import com.land.utils.MyPreference;

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


public class ListingFragment extends Fragment {

    public SwipeRefreshLayout swipeRefreshLayout;
    protected RecyclerView recyclerView;
    protected ListingAdapter listingAdapter;
    String msg;
    private LinearLayoutManager manager;
    private Call lastCall;
    private Handler handler;
    private List<NearestPropertiesModel> propertiesModels = new ArrayList<>();
    private List<NearestPropertiesModel> Mainlist = new ArrayList<>();
    private int curpage = 1;
    private String nextpage = "";
    private boolean isLoading = true;
    private double latitude;
    private double longitude;
    private MyPreference myPreference;
    private MyHttpClientHelper.ProcessResponseHelper mPropertiesListingResponseHelper = new MyHttpClientHelper.ProcessResponseHelper() {
        @Override
        public void onRequest() {
            isLoading = true;
        }

        @Override
        public void onResponse(JSONObject object) {
            try {
                int code = object.getInt(ApiConstant.JSON_KEY_CODE);
                msg = object.getString(ApiConstant.JSON_KEY_MSG);

                if (code == 1) {
                    JSONArray dataObject = object.getJSONArray(ApiConstant.JSON_KEY_DATA);
                    if (dataObject.length() > 0) {
                        JSONObject pageObject = object.getJSONObject(ApiConstant.JSON_KEY_PAGE);
                        curpage = Integer.parseInt(String.valueOf(pageObject.getString(ApiConstant.JSON_KEY_CURRPAGE)));
                        nextpage = String.valueOf(pageObject.getString(ApiConstant.JSON_KEY_IFNEXTPAGE));
                    }
                    for (int i = 0; i < dataObject.length(); i++) {
                        JSONObject property = dataObject.getJSONObject(i);
                        NearestPropertiesModel propertiesModel = new NearestPropertiesModel();
                        propertiesModel.fromJson(property);
                        Mainlist.add(propertiesModel);
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (curpage == 1) {
                                    listingAdapter = new ListingAdapter(getActivity(), Mainlist);
                                    recyclerView.setAdapter(listingAdapter);
                                } else {
                                    listingAdapter.notifyDataSetChanged();
                                }
                                if (nextpage.equals("Yes")) {
                                    curpage = curpage + 1;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } else {
                    curpage = -1;
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
                        isLoading = false;
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

    public ListingFragment() {
        // Required empty public constructor
    }

    public static ListingFragment newInstance() {
        return new ListingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        UrlConstant.fragmenttag = 1;
        myPreference = new MyPreference(getActivity());
        return inflater.inflate(R.layout.fragment_listing, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiper);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        latitude = myPreference.getLatestLocation().latitude;
        longitude = myPreference.getLatestLocation().longitude;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listingAdapter.notifyDataSetChanged();
            }
        });

        handler = new Handler(Looper.getMainLooper());
        initView(view);
        Mainlist = new ArrayList<>();
        curpage = 1;
        actionGetPropertiesListing(curpage, latitude, longitude);
        recyclerView.addOnScrollListener(
                new RecyclerView.OnScrollListener() {

                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        try {
                            int visibleItemCount = recyclerView.getChildCount();
                            int totalItemCount = recyclerView.getAdapter().getItemCount();
                            int firstVisibleItemIndex = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

//                            LogHelper.d("Pagination==", "visibleItemCount= " + visibleItemCount + " totalItemCount= " + totalItemCount + " firstVisibleItemIndex " + firstVisibleItemIndex);
                            if ((totalItemCount - visibleItemCount) <= firstVisibleItemIndex) {
                                if (nextpage.equals("Yes") && !isLoading) {
                                    //actionGetJoints();
                                    actionGetPropertiesListing(curpage, latitude, longitude);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    private void initView(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
//        listingAdapter = new ListingAdapter(getActivity(), propertiesModels);
//        recyclerView.setAdapter(listingAdapter);

    }

    private void actionGetPropertiesListing(int curpage, double latitude, double longitude) {
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setRefreshing(true);
        RequestBody requestBody = new FormBody.Builder().addEncoded("currentPage", "" + curpage)
                .addEncoded("latitude", "" + latitude)
                .addEncoded("longitude", "" + longitude).build();
        lastCall = MyHttpClientHelper.getInstance().enqueueCall(getActivity(), UrlConstant.API_GET_NEAREST_PROPERTIES_WITHPAGINATION_URL, requestBody, mPropertiesListingResponseHelper);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SearchEvent event) {
        String txtsearch = event.getData();
        if (event.getAction() == SearchEvent.ACTION_FILTER) {
            if (Mainlist.size() > 0) {
                PropertyFilterDialogFragmnet.newInstance(Mainlist).show(getActivity().getSupportFragmentManager(), "SHARE-VOICE");
            } else {
                Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
            }
        } else if (event.getAction() == SearchEvent.ACTION_SEARCH) {
            Mainlist = new ArrayList<>();
            String[] eve = event.getData().split(",");
            latitude = Double.parseDouble(eve[0]);
            longitude = Double.parseDouble(eve[1]);
            if (txtsearch.length() > 2) {
                curpage = 1;
                actionGetPropertiesListing(curpage, latitude, longitude);
            }
        } else if (event.getAction() == SearchEvent.ACTION_FILTER_RESULT) {

        } else if (event.getAction() == SearchEvent.ACTION_CURR) {
            latitude = myPreference.getLatestLocation().latitude;
            longitude = myPreference.getLatestLocation().longitude;
            Mainlist = new ArrayList<>();
            curpage = 1;
            actionGetPropertiesListing(curpage, latitude, longitude);
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
