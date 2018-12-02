package com.land;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.land.adapter.AdapterSummary;
import com.land.adapter.GalleryvpAdaptor;
import com.land.adapter.SimilarPropertyAdapter;
import com.land.api.model.NearestPropertyModel;
import com.land.api.model.PropertyDetailModel;
import com.land.constant.ApiConstant;
import com.land.constant.UrlConstant;
import com.land.custom.RobotoTextView;
import com.land.helper.LogHelper;
import com.land.helper.MyHttpClientHelper;
import com.land.helper.TintDrawableHelper;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;

import static com.land.helper.StringHelper.isEmptyString;

public class PropertyDetailActivity extends AppCompatActivity {
    public static String agentid;
    AdapterSummary adapterSummary;
    RecyclerView recyclerView;
    ViewPager viewpager;
    String Pid, gid;
    RobotoTextView txt_address, txt_price, txt_property_detail;
    ImageView img_location;
    LinearLayout table_1, table_2, table_3;
    LayoutInflater linf;
    String strLatitude = "";
    String strLongitude = "";
    Toolbar toolbar;
    JSONArray imagearray;
    RobotoTextView tvcontctagent;
    RobotoTextView tvtitle;
    RobotoTextView tv_pfeatures;
    RobotoTextView tv_similerhomesforsale;
    CardView card_pfeature;
    ArrayList<String> imagearraylist;
    ViewPager viewpager_gallary;
    String orderby = "UsdSalePrice";
    String propertyFor = "0";
    private SwipeRefreshLayout swiper;
    private Call lastCall;
    private Handler handler;
    private PropertyDetailModel detailModel;
    private ArrayList<NearestPropertyModel> similerpropertiesModels = new ArrayList<>();
    private RobotoTextView rtv_imagenumber;
    private MyHttpClientHelper.ProcessResponseHelper mSearchPropertyResponseHelper = new MyHttpClientHelper.ProcessResponseHelper() {
        @Override
        public void onRequest() {
        }

        @Override
        public void onResponse(JSONObject object) {
            try {
                int code = object.getInt(ApiConstant.JSON_KEY_CODE);
                if (code == 1) {
                    //mGroups.clear();
                    similerpropertiesModels = new ArrayList<>();
                    JSONArray dataObject = object.getJSONArray(ApiConstant.JSON_KEY_DETAIL_DATA);
                    for (int i = 0; i < dataObject.length(); i++) {
                        JSONObject property = dataObject.getJSONObject(i);
                        NearestPropertyModel propertiesModel = new NearestPropertyModel();
                        propertiesModel.fromJson(property);
                        if (!String.valueOf(detailModel.getPropertyID()).equals(propertiesModel.getPropertyID())) {
                            similerpropertiesModels.add(propertiesModel);
                        }
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (similerpropertiesModels.size() > 0) {
                                    viewpager.setVisibility(View.VISIBLE);
                                    tv_similerhomesforsale.setText("Similer Homes For Sale(" + similerpropertiesModels.size() + ")");
                                    viewpager.setAdapter(new SimilarPropertyAdapter(PropertyDetailActivity.this, similerpropertiesModels, "PropertyDetailActivity"));
                                } else {
                                    tv_similerhomesforsale.setText("No Similer Proprty Found");
                                    viewpager.setVisibility(View.GONE);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {

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
            LogHelper.d("Tag", "Something Want Wrong");
        }
    };
    private MyHttpClientHelper.ProcessResponseHelper mResponseHelper = new MyHttpClientHelper.ProcessResponseHelper() {
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
                    detailModel = new PropertyDetailModel();
                    detailModel.fromJson(dataObject.getJSONObject(0));
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                // addMarkers();
                                preperUi();
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
                        swiper.setEnabled(false);
                        swiper.setRefreshing(false);
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
        setContentView(R.layout.activity_property_detail);
        getpropertydata();
        initView();

        img_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!swiper.isRefreshing()) {
                    if (!strLatitude.equals("") && !strLongitude.equals("")) {
                        Intent intent = new Intent(PropertyDetailActivity.this, NearbyPlacesMapActivity.class);
                        intent.putExtra("Latlang", "" + strLatitude + "," + strLongitude);
                        startActivity(intent);
                    }
                }
            }
        });


        tvcontctagent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AgentDetailActivity.class);
                i.putExtra("agentid", "" + agentid);
                startActivity(i);
            }
        });
    }

    public void getpropertydata() {
        try {
            Intent i = getIntent();
            Pid = i.getStringExtra("pid");
            gid = i.getStringExtra("groupid");
        } catch (Exception e) {

        }

    }

    private void initView() {
        rtv_imagenumber = (RobotoTextView) findViewById(R.id.rtv_imagenumber);
        viewpager_gallary = (ViewPager) findViewById(R.id.vpgallery);
        swiper = (SwipeRefreshLayout) findViewById(R.id.swiper);
        swiper.setColorSchemeResources(R.color.colorPrimary);
        card_pfeature = (CardView) findViewById(R.id.card_pfeature);
        tv_pfeatures = (RobotoTextView) findViewById(R.id.tv_pfeatures);
        tvtitle = (RobotoTextView) findViewById(R.id.tvtoolbartitle);
        tv_similerhomesforsale = (RobotoTextView) findViewById(R.id.tv_similerhomesforsale);
        tvtitle.setText("Property Detail");
        toolbar = (Toolbar) findViewById(R.id.maintoolbar);
        toolbar.setNavigationIcon(TintDrawableHelper.getTintedResource(this, android.support.design.R.drawable.abc_ic_ab_back_material, R.color.colorWhite));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvcontctagent = (RobotoTextView) findViewById(R.id.tvcontactagent);

        table_1 = (LinearLayout) findViewById(R.id.table_1);
        table_2 = (LinearLayout) findViewById(R.id.table_2);
        table_3 = (LinearLayout) findViewById(R.id.table_3);

        txt_address = (RobotoTextView) findViewById(R.id.txt_address);
        txt_price = (RobotoTextView) findViewById(R.id.txt_price);
        txt_property_detail = (RobotoTextView) findViewById(R.id.txt_property_detail);


        img_location = (ImageView) findViewById(R.id.img_location);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);


        viewpager_gallary.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                rtv_imagenumber.setText(position + 1 + "/" + imagearraylist.size());
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

//         adapterSummary = new AdapterSummary(this);
//         recyclerView.setNestedScrollingEnabled(false);
//        recyclerView.setAdapter(adapterSummary);
//


        actionGetAllPropertiesForFeatureDetails();
    }

    private void actionGetAllPropertiesForFeatureDetails() {
        swiper.setEnabled(true);
        swiper.setRefreshing(true);
        handler = new Handler(Looper.getMainLooper());
        RequestBody requestBody = new FormBody.Builder()
                .addEncoded("PropertyID", "" + Pid)
                .addEncoded("GroupID", "" + gid).build();
        lastCall = MyHttpClientHelper.getInstance().enqueueCall(this, UrlConstant.API_GET_ALL_PROPERTIES_FOR_FEATURE_DETAILS_URL, requestBody, mResponseHelper);
    }

    private void actionGetNearestProperties() {

        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = this.getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = this.getResources().getConfiguration().locale;
        }

        String locales = locale.getCountry();
        LogHelper.d("Country code", locales);
        propertyFor = String.valueOf(detailModel.getPropertyFor());

        if (propertyFor.equals("1")) {
            orderby = "UsdSalePrice";
        } else if (propertyFor.equals("2")) {
            orderby = "UsdMonthlyRent";
        } else {
            orderby = "UsdSalePrice";
        }

        RequestBody requestBody = new FormBody.Builder()
                .addEncoded("location", "")
                .addEncoded("propertyFor", "" + detailModel.getPropertyFor())
                .addEncoded("propertyType", "" + detailModel.getPropertyType())
                .addEncoded("bathroom", "" + detailModel.getNoofBathrooms())
                .addEncoded("bedroom", "" + detailModel.getNoofBedrooms())
                .addEncoded("minprice", "0")
                .addEncoded("maxprice", "0")
                .addEncoded("mincoverarea", "0")
                .addEncoded("maxcoverarea", "0")
                .addEncoded("minplotarea", "0")
                .addEncoded("maxplotarea", "0")
                .addEncoded("TransactionType", "0")
                .addEncoded("Possession", "0")
                .addEncoded("CurrencyUnit", "AED")
                .addEncoded("CoverAreaUnit", "SqMeter")
                .addEncoded("PlotAreaUnit", "SqMeter")
                .addEncoded("orderby", orderby)
                .addEncoded("orderto", "desc")
                .addEncoded("distance", "0")
                .addEncoded("center_latitude", "0")
                .addEncoded("center_longitude", "0")
                .build();
        lastCall = MyHttpClientHelper.getInstance().enqueueCall(this, UrlConstant.API_GET_SEARCH_BY_SALE_RENT_PROPERTY_URL, requestBody, mSearchPropertyResponseHelper);
    }

    private void preperUi() {
        try {

            agentid = "" + detailModel.getAgentId();
            String title = "" + detailModel.getTitle();
            String keylndmark = "" + detailModel.getKeyLandmark();
            String price = "" + detailModel.getSalePrice();
            String propertydetail = "" + detailModel.getDescription();
            strLatitude = String.valueOf(detailModel.getLatitude());
            strLongitude = String.valueOf(detailModel.getLongitude());
            String imagesarray = "" + detailModel.getPropertyImage();
            String[] strings = {title, keylndmark, price, propertydetail};
            TextView[] textViews = {txt_address, txt_price, txt_property_detail};
            String propertyFor = detailModel.getPropertyFor().toString();
            String propertyType = detailModel.getPropertyType().toString();
            String bedroom = detailModel.getNoofBedrooms().toString();
            String bathroom = detailModel.getNoofBathrooms().toString();
            actionGetNearestProperties();

            for (int i = 0; i < strings.length; i++) {
                if (isEmptyString(strings[i])) {
                    strings[i] = " ";
                } else {
                    strings[i] = strings[i];
                }
            }

            txt_address.setText(strings[0] + "," + strings[1]);
            txt_price.setText("$" + strings[2]);
            txt_property_detail.setText(strings[3]);
//
            adapterSummary = new AdapterSummary(this, detailModel.getSummery());
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setAdapter(adapterSummary);

            imagearray = new JSONArray();
            imagearraylist = new ArrayList<>();
            String[] imagess = detailModel.getPropertyImage().split(",");
            for (String name : imagess) {
                imagearray.put(name);
                imagearraylist.add(name);
            }
            viewpager_gallary.setAdapter(new GalleryvpAdaptor(getApplicationContext(), imagearraylist, "true", "PropertyDetailActivity"));
            if (!strLatitude.isEmpty() && !strLongitude.isEmpty()) {
                String mapUrl = UrlConstant.StaticMapUrl;
                mapUrl = mapUrl + Float.parseFloat(strLatitude) + "," + Float.parseFloat(strLongitude) + "&center=" + Float.parseFloat(strLatitude) + "," + Float.parseFloat(strLongitude);
                Picasso.with(img_location.getContext()).load(mapUrl).fit().centerCrop().placeholder(R.color.colorAccent).into(img_location);
            }
            String[] homefetures = detailModel.getHomeFeaturesListKeyName().split(",");
            String[] societyfetures = detailModel.getSocietyFeaturesListKeyName().split(",");
            String[] otherfetures = detailModel.getOtherFeaturesListKeyName().split(",");


            //FrameLayout.LayoutParams layoutParams;
            //layoutParams = new FrameLayout.LayoutParams(ViewPager.LayoutParams.WRAP_CONTENT, ViewPager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);

            linf = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            linf = LayoutInflater.from(this);

            final ArrayList<String> arraylength = new ArrayList<>();

            for (String name : homefetures) {
                final TextView v = (TextView) linf.inflate(R.layout.item, null);
                if (!isEmptyString(name)) {
                    v.setText(name);
                    arraylength.add(name);
                    table_1.addView(v);
                }
            }
            for (String name : societyfetures) {
                final TextView v = (TextView) linf.inflate(R.layout.item, null);
                if (!isEmptyString(name)) {
                    v.setText(name);
                    arraylength.add(name);
                    table_2.addView(v);
                }
            }
            for (String name : otherfetures) {
                final TextView v = (TextView) linf.inflate(R.layout.item, null);
                if (!isEmptyString(name)) {
                    v.setText(name);
                    arraylength.add(name);
                    table_3.addView(v);
                }
            }


            handler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (arraylength.isEmpty()) {
                            card_pfeature.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
