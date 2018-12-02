package com.land.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.land.R;
import com.land.api.model.SupplierModel;
import com.land.constant.ApiConstant;
import com.land.constant.UrlConstant;
import com.land.events.SearchEvent;
import com.land.helper.LogHelper;
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

public class SuppliersFragment extends Fragment {
    protected ViewPager viewPager;
    protected TabLayout tabLayout;
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected MyPreference myPreference;
    ArrayList<SupplierModel> listallsupplier = new ArrayList<>();
    TabsPagerAdapter mAdapter;
    private Call lastCall;
    private Handler handler;
    private double latitude;
    private double longitude;
    private MyHttpClientHelper.ProcessResponseHelper mResponseHelper = new MyHttpClientHelper.ProcessResponseHelper() {
        @Override
        public void onRequest() {

        }

        //dashratrh
        @Override
        public void onResponse(JSONObject object) {
            try {
                int code = object.getInt(ApiConstant.JSON_KEY_CODE);
                if (code == 1) {
                    JSONArray dataObject = object.getJSONArray(ApiConstant.JSON_KEY_DATA);
                    listallsupplier = new ArrayList<>();
                    for (int i = 0; i < dataObject.length(); i++) {
                        SupplierModel supplierModel = new SupplierModel();
                        supplierModel.fromJson(dataObject.getJSONObject(i));
                        listallsupplier.add(supplierModel);
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                mAdapter = new TabsPagerAdapter(getFragmentManager(), listallsupplier);
                                viewPager.setAdapter(mAdapter);
                                tabLayout.setupWithViewPager(viewPager);
                                // adding functionality to tab and viewpager to manage each other when a page is changed or when a tab is selected
                                viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                                viewPager.setCurrentItem(4);
                                try {
                                    viewPager.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            swipeRefreshLayout.setRefreshing(false);
                                            swipeRefreshLayout.setEnabled(false);
                                            viewPager.setCurrentItem(0);
//                                            mAdapter = new TabsPagerAdapter(getActivity().getSupportFragmentManager(), listallsupplier);
                                            viewPager.setAdapter(mAdapter);
//                                            tabLayout.setupWithViewPager(viewPager);
                                            // adding functionality to tab and viewpager to manage each other when a page is changed or when a tab is selected
                                            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                                        }
                                    }, 1500);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

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

        }

        @Override
        public void onFail() throws Exception {

        }
    };

    public SuppliersFragment() {
        // Required empty public constructor
    }

    public static SuppliersFragment newInstance(String param1, String param2) {
        SuppliersFragment fragment = new SuppliersFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        UrlConstant.fragmenttag = 5;
        myPreference = new MyPreference(getActivity());
        return inflater.inflate(R.layout.fragment_suppliers, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        latitude = myPreference.getLatestLocation().latitude;
        longitude = myPreference.getLatestLocation().longitude;
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorPrimary));
        getdata(latitude, longitude);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // categoryWiseItems.clear();
                swipeRefreshLayout.setEnabled(false);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    public void getdata(double latitude, double longitude) {
        LogHelper.d("Suplierlalong", "lat" + myPreference.getLatestLocation().latitude + " " + "log" + myPreference.getLatestLocation().longitude);
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setRefreshing(true);
        handler = new Handler(Looper.getMainLooper());
        RequestBody requestBody = new FormBody.Builder()
                .addEncoded("latitude", "" + latitude)
                .addEncoded("longitude", "" + longitude)
                .build();
        lastCall = MyHttpClientHelper.getInstance().enqueueCall(getActivity(), UrlConstant.API_GET_All_SUPPLIER_URL, requestBody, mResponseHelper);
    }

    private void prepareUi() {
        //viewPager.setAdapter(new MingleJointPagerAdapter(getSupportFragmentManager(), new ArrayList<String>(categoryWiseItems.keySet())));
        mAdapter = new TabsPagerAdapter(getFragmentManager(), listallsupplier);
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);
        // adding functionality to tab and viewpager to manage each other when a page is changed or when a tab is selected
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SearchEvent event) {
        String txtsearch = event.getData();
        if (event.getAction() == SearchEvent.ACTION_SEARCH) {
            String[] eve = event.getData().split(",");
            latitude = Double.parseDouble(eve[0]);
            longitude = Double.parseDouble(eve[1]);
            if (txtsearch.length() > 2) {
                getdata(latitude, longitude);
            }
        } else if (event.getAction() == SearchEvent.ACTION_CURR) {
            latitude = myPreference.getLatestLocation().latitude;
            longitude = myPreference.getLatestLocation().longitude;
            getdata(latitude, longitude);
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

    public class TabsPagerAdapter extends FragmentStatePagerAdapter {

        ArrayList<SupplierModel> listallsupplier = new ArrayList<>();

        public TabsPagerAdapter(FragmentManager fm, ArrayList<SupplierModel> listallsuppliers) {
            super(fm);
            this.listallsupplier = listallsuppliers;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return listallsupplier.get(position).getSc_name();
        }

        @Override
        public Fragment getItem(int index) {
//            LogHelper.e("index", "" + index);
            return SupplierCategoryFragment.newInstance(listallsupplier.get(index).getJoints());
        }

//        @Override
//        public int getItemPosition(Object object) {
//            mAdapter.notifyDataSetChanged();
////            SupplierCategoryFragment supplierCategoryFragment = (SupplierCategoryFragment) object;
////            if (supplierCategoryFragment != null) {
////                supplierCategoryFragment.update();
////            }
//            return super.getItemPosition(object);
//        }

        @Override
        public int getCount() {
            // get item count - equal to number of tabs
            return listallsupplier.size();
        }
    }
}
