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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.land.R;
import com.land.adapter.AdapterAgents;
import com.land.api.model.AgentModel;
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

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class AjentsFragment extends Fragment {

    public SwipeRefreshLayout swipeRefreshLayout;
    protected RecyclerView recyclerView;
    AdapterAgents adapterAgents;
    AgentModel agentModel;
    ArrayList<AgentModel> listallagent = new ArrayList<>();
    LinearLayout linearLayout;
    LinearLayoutManager manager;
    int lastItemIndex = 0;
    String msg;
    private Call lastCall;
    private Handler handler;
    private int curpage = 0;
    private int totalpage = 0;
    private int totalcount = 0;
    private double latitude;
    private double longitude;
    private String nextpage = "";
    private boolean isLoading = true;
    private MyPreference myPreference;
    private MyHttpClientHelper.ProcessResponseHelper mResponseHelper = new MyHttpClientHelper.ProcessResponseHelper() {
        @Override
        public void onRequest() {
            isLoading = true;
        }

        @Override
        public void onResponse(JSONObject object) {
            try {
                int code = object.getInt(ApiConstant.JSON_KEY_CODE);
                if (code == 1) {
                    JSONArray dataObject = object.getJSONArray(ApiConstant.JSON_KEY_DATA);
                    if (dataObject.length() > 0) {
                        JSONObject pageObject = object.getJSONObject(ApiConstant.JSON_KEY_PAGE);
                        curpage = Integer.parseInt(String.valueOf(pageObject.getString(ApiConstant.JSON_KEY_CURRPAGE)));
                        totalpage = Integer.parseInt(String.valueOf(pageObject.getString(ApiConstant.JSON_KEY_TOTALPAGE)));
                        nextpage = String.valueOf(pageObject.getString(ApiConstant.JSON_KEY_IFNEXTPAGE));
                        totalcount = Integer.parseInt(String.valueOf(pageObject.getString(ApiConstant.JSON_KEY_TOTALCOUNT)));
                    }

                    for (int i = 0; i < dataObject.length(); i++) {
                        agentModel = new AgentModel();
                        agentModel.fromJson(dataObject.getJSONObject(i));
                        listallagent.add(agentModel);
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (curpage == 1) {
                                    adapterAgents = new AdapterAgents(getActivity(), listallagent);
                                    recyclerView.setAdapter(adapterAgents);
                                } else {
                                    adapterAgents.notifyDataSetChanged();
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

    public AjentsFragment() {
        // Required empty public constructor
    }

    public static AjentsFragment newInstance() {
        AjentsFragment fragment = new AjentsFragment();
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_search:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        UrlConstant.fragmenttag = 2;

        setHasOptionsMenu(true);
        myPreference = new MyPreference(getActivity());
        return inflater.inflate(R.layout.fragment_ajents, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayout = (LinearLayout) view.findViewById(R.id.ll1);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiper);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        latitude = myPreference.getLatestLocation().latitude;
        longitude = myPreference.getLatestLocation().longitude;
        listallagent = new ArrayList<>();
        curpage = 1;
        getdata(curpage, latitude, longitude);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapterAgents.notifyDataSetChanged();
            }
        });


        recyclerView.addOnScrollListener(
                new RecyclerView.OnScrollListener() {

                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        int visibleItemCount = recyclerView.getChildCount();
                        int totalItemCount = recyclerView.getAdapter().getItemCount();
                        int firstVisibleItemIndex = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                        lastItemIndex = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                        if ((totalItemCount - visibleItemCount) <= firstVisibleItemIndex) {
                            if (nextpage.equals("Yes") && !isLoading) {
                                //actionGetJoints();
                                getdata(curpage, latitude, longitude);
                            }
                        }

                    }
                }
        );


    }

    public void getdata(int curpage, double lat, double lng) {
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setRefreshing(true);
        handler = new Handler(Looper.getMainLooper());
        RequestBody requestBody = new FormBody.Builder().addEncoded("currentPage", "" + curpage)
                .addEncoded("latitude", "" + lat)
                .addEncoded("longitude", "" + lng).build();
        lastCall = MyHttpClientHelper.getInstance().enqueueCall(getActivity(), UrlConstant.API_GET_All_AGENTS_DETAIL_URL, requestBody, mResponseHelper);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SearchEvent event) {
        String txtsearch = event.getData();
//        if (event.getAction() == SearchEvent.ACTION_BACKPRESSED) {
//
//            }
        if (event.getAction() == SearchEvent.ACTION_SEARCH) {
            listallagent = new ArrayList<>();
            String[] eve = event.getData().split(",");
            latitude = Double.parseDouble(eve[0]);
            longitude = Double.parseDouble(eve[1]);
            if (txtsearch.length() > 2) {
                curpage = 1;
                getdata(curpage, latitude, longitude);
            }
        } else if (event.getAction() == SearchEvent.ACTION_CURR) {
            latitude = myPreference.getLatestLocation().latitude;
            longitude = myPreference.getLatestLocation().longitude;
            listallagent = new ArrayList<>();
            curpage = 1;
            getdata(curpage, latitude, longitude);
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
