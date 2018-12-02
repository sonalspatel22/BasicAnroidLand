package com.land.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.land.Place.GetlatlangbyAddress;
import com.land.R;
import com.land.SignupActivity;
import com.land.api.model.CurrencyModel;
import com.land.api.model.FilterModel;
import com.land.api.model.NearestPropertiesModel;
import com.land.constant.ApiConstant;
import com.land.constant.AppConstant;
import com.land.constant.UrlConstant;
import com.land.custom.RobotoTextView;
import com.land.events.SearchEvent;
import com.land.helper.LogHelper;
import com.land.helper.MyHttpClientHelper;
import com.land.helper.TintDrawableHelper;
import com.land.utils.MyPreference;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;

import static com.land.helper.StringHelper.isEmptyString;

/**
 * Created by Dashrath on 12/29/2017.
 */

public class PropertyFilterDialogFragmnet extends BaseDialogFragment {

    public static final String CURRENCY_NAMES[] = {"EUR", "USD", "ZAR", "JPY", "GBP", "AED", "ZMW"};
    Toolbar toolbar;
    RobotoTextView txt_min_price, txt_max_price;
    CrystalRangeSeekbar rangeSeekbarPrice;
    CheckBox chk_commercial, chk_resident;
    RadioGroup rdg_bed_roomes;
    RadioButton radio_any, radio_1, radio_2, radio_3, radio_4;
    RadioGroup rdg_bathrooms;

    //RadioGroup rdg_property_search_for;
    //RadioButton radio_for_sale, radio_for_rent, radio_project;
    RadioButton radio_any_bath, radio_1_bath, radio_2_bath, radio_3_bath, radio_4_bath;
    RadioGroup rdg_possession;
    RadioButton radio_under_construction, radio_ready, radio_both;
    RadioGroup rdg_transaction_type;
    RadioButton radio_new, radio_resale, radio_both_transaction_type;
    Spinner spinner_price_unit, spinner_cover_unit, spinner_plot_unit;
    EditText min_cover_area, max_cover_area, min_plot_area, max_plot_area;
    RobotoTextView search_button;
    RobotoTextView savesearch_button;

    // EditText edt_search_for_property;
    RobotoTextView apply_button;
    ImageView iv_refresh;
    SwipeRefreshLayout swiper;
    Switch sw_open_house, sw_project, sw_rent, sw_buy;
    String possession = "0";
    String transaction_type = "13";
    String bathRooms = "0";
    String bedRooms = "0";
    String CurrencyUnit;
    String CoverAreaUnit;
    String PlotAreaUnit;
    String minprice = "10000";
    String maxprice = "1000000";
    String mincoverarea = "0.0";
    String maxcoverarea = "0.0";
    String minplotarea = "0.0";
    String maxplotarea = "0.0";
    String curlat;
    String curlang;
    String orderby = "UsdSalePrice";
    FilterModel f;
    LatLng latLng = null;
    String address;
    String distance;
    String msg;
    private List<NearestPropertiesModel> propertiesModels = new ArrayList<>();
    private List<CurrencyModel> currencyllist = new ArrayList<>();
    private LinearLayout llsaveapply;
    private LinearLayout llapply;
    private Handler handler;
    private String propertyFor = "0";
    private String propertyType = "0";
    private Call lastCall;
    private MyHttpClientHelper.ProcessResponseHelper mSearchPropertyResponseHelper = new MyHttpClientHelper.ProcessResponseHelper() {
        @Override
        public void onRequest() {

        }

        @Override
        public void onResponse(JSONObject object) {
            try {

                int code = object.getInt(ApiConstant.JSON_KEY_CODE);

                if (code == 1) {
//
                    if (UrlConstant.fragmenttag == 1) {
                        JSONArray dataObject = object.getJSONArray(ApiConstant.JSON_KEY_DETAIL_DATA);
                        propertiesModels = new ArrayList<>();
                        //Log.e("propertylist", "" + dataObject.getJSONObject(3));
                        for (int i = 0; i < dataObject.length(); i++) {
                            JSONObject property = dataObject.getJSONObject(i);
                            NearestPropertiesModel propertiesModel = new NearestPropertiesModel();
                            propertiesModel.fromJson(property);
                            propertiesModels.add(propertiesModel);
                        }

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    if (!propertiesModels.isEmpty()) {
                                        SearchListingFragment.newInstance(propertiesModels).show(getActivity().getSupportFragmentManager(), "SHARE-VOICE");
                                    } else {
                                        Toast.makeText(getActivity(), "Data Not Found", Toast.LENGTH_SHORT).show();
                                        swiper.setEnabled(false);
                                        swiper.setRefreshing(false);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
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
            LogHelper.d("Tag", "Something Want Wrong");
        }
    };

    public PropertyFilterDialogFragmnet() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PropertyFilterDialogFragmnet newInstance(List<NearestPropertiesModel> propertiesModels, LatLng lastlocation, String dis) {
        PropertyFilterDialogFragmnet fragment = new PropertyFilterDialogFragmnet();
        fragment.propertiesModels = propertiesModels;
        fragment.latLng = lastlocation;
        fragment.distance = dis;
        return fragment;
    }

    public static PropertyFilterDialogFragmnet newInstance(List<NearestPropertiesModel> propertiesModels) {
        PropertyFilterDialogFragmnet fragment = new PropertyFilterDialogFragmnet();
        fragment.propertiesModels = propertiesModels;
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.MyActivityTheme) {
            @Override
            public void onBackPressed() {
                super.onBackPressed();
            }
        };
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyPreference preference = new MyPreference(getActivity());
        if (latLng == null) {
            latLng = preference.getLatestLocation();
        }
        curlat = String.valueOf(latLng.latitude);
        curlang = String.valueOf(latLng.longitude);
        Gson gson = new Gson();
        String json = preference.getFilter();
        f = gson.fromJson(json, FilterModel.class);
        GetlatlangbyAddress getlatlangbyAddress = new GetlatlangbyAddress();
        address = getlatlangbyAddress.getadd(getActivity(), Double.parseDouble(curlat), Double.parseDouble(curlang));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return inflater.inflate(R.layout.fragment_filter_for_sale, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        curruncyconvert("100", "USD", "EUR");
        if (UrlConstant.fragmenttag == 3) {
            llapply.setVisibility(View.GONE);
            llsaveapply.setVisibility(View.VISIBLE);
            if (f != null) {
                setvalues();
            }
        } else if (UrlConstant.fragmenttag == 1) {
            llapply.setVisibility(View.VISIBLE);
            llsaveapply.setVisibility(View.GONE);
        } else if (UrlConstant.fragmenttag == 4) {
            llapply.setVisibility(View.VISIBLE);
            llsaveapply.setVisibility(View.GONE);
        }

    }

    public void OnRefresh() {
        sw_buy.setChecked(false);
        sw_rent.setChecked(false);
        sw_open_house.setChecked(false);
        sw_project.setChecked(false);
        chk_commercial.setChecked(false);
        chk_resident.setChecked(false);
        radio_any.setChecked(true);
        radio_any_bath.setChecked(true);
        rangeSeekbarPrice.setMinValue(1000);
        rangeSeekbarPrice.setMaxValue(100000);
        txt_min_price.setText("10000");
        txt_max_price.setText("1000000");
        spinner_price_unit.setSelection(0);
        spinner_cover_unit.setSelection(0);
        min_cover_area.setText("0.0");
        max_cover_area.setText("0.0");
        spinner_plot_unit.setSelection(0);
        min_plot_area.setText("0.0");
        max_plot_area.setText("0.0");
        rdg_possession.clearCheck();
        rdg_transaction_type.clearCheck();
    }

    public void setvalues() {
        if (f.isChkBuy()) {
            sw_buy.setChecked(true);
        }
        if (f.isChkRent()) {
            sw_rent.setChecked(true);
        }
        if (f.isChkOpenhouse()) {
            sw_open_house.setChecked(true);
        }
        if (f.isChkProject()) {
            sw_project.setChecked(true);
        }
        if (f.isChkcommercial()) {
            chk_commercial.setChecked(true);
        }

        if (f.isChkresident()) {
            chk_resident.setChecked(true);
        }

        if (f.getBedRooms() != null) {
            bedRooms = f.getBedRooms().toString();
            if (bedRooms.equals("0")) {
                radio_any.setChecked(true);
            } else if (bedRooms.equals("1")) {
                radio_1.setChecked(true);
            } else if (bedRooms.equals("2")) {
                radio_2.setChecked(true);
            } else if (bedRooms.equals("3")) {
                radio_3.setChecked(true);
            } else if (bedRooms.equals("4")) {
                radio_4.setChecked(true);
            }
        }
        if (f.getBathRooms() != null) {
            bathRooms = f.getBathRooms().toString();
            if (bathRooms.equals("0")) {
                radio_any_bath.setChecked(true);
            } else if (bathRooms.equals("1")) {
                radio_1_bath.setChecked(true);
            } else if (bathRooms.equals("2")) {
                radio_2_bath.setChecked(true);
            } else if (bathRooms.equals("3")) {
                radio_3_bath.setChecked(true);
            } else if (bathRooms.equals("4")) {
                radio_4_bath.setChecked(true);
            }
        }
        if (f.getMinprice().equals("0")) {
            txt_min_price.setText(minprice);
            rangeSeekbarPrice.setMinStartValue(Float.parseFloat(minprice));
        } else if (f.getMinprice() != null) {
            txt_min_price.setText(f.getMinprice());
            rangeSeekbarPrice.setMinStartValue(Float.parseFloat(f.getMinprice()));
        }
        if (f.getMaxprice().equals("0")) {
            txt_max_price.setText(maxprice);
            rangeSeekbarPrice.setRight(Integer.parseInt(maxprice));
//            rangeSeekbarPrice.setMaxStartValue(Float.parseFloat(maxprice));
        } else if (f.getMaxprice() != null) {
            txt_max_price.setText(f.getMaxprice());
            rangeSeekbarPrice.setRight(Integer.parseInt(maxprice));
//            rangeSeekbarPrice.setMaxStartValue(Float.parseFloat(f.getMaxprice()));
        }
        if (f.getMincoverarea() != null) {
            min_cover_area.setText(f.getMincoverarea());
        }
        if (f.getMaxcoverarea() != null) {
            max_cover_area.setText(f.getMaxcoverarea());
        }

        if (f.getMinplotarea() != null) {
            min_plot_area.setText(f.getMinplotarea());
        }
        if (f.getMaxplotarea() != null) {
            max_plot_area.setText(f.getMaxplotarea());
        }
        if (f.getPossession() != null) {
            possession = f.getPossession();
            if (possession.equals("1")) {
                radio_under_construction.setChecked(true);
            } else if (possession.equals("2")) {
                radio_ready.setChecked(true);
            } else if (possession.equals("3")) {
                radio_both.setChecked(true);
            }
        }
        if (f.getTransaction_type() != null) {
            transaction_type = f.getTransaction_type();
            if (transaction_type.equals("11")) {
                radio_new.setChecked(true);
            } else if (transaction_type.equals("12")) {
                radio_resale.setChecked(true);
            } else if (transaction_type.equals("13")) {
                radio_both_transaction_type.setChecked(true);
            }
        }

    }

    private void initView(View v) {
        iv_refresh = (ImageView) v.findViewById(R.id.refresh);
        llapply = (LinearLayout) v.findViewById(R.id.llapply);
        llsaveapply = (LinearLayout) v.findViewById(R.id.llsaveapply);

        handler = new Handler(Looper.getMainLooper());
        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(TintDrawableHelper.getTintedResource(getActivity(), android.support.design.R.drawable.abc_ic_ab_back_material, R.color.colorWhite));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        swiper = (SwipeRefreshLayout) v.findViewById(R.id.swiper);
        swiper.setColorSchemeResources(R.color.colorPrimary);
        txt_min_price = (RobotoTextView) v.findViewById(R.id.txt_min_price);
        txt_max_price = (RobotoTextView) v.findViewById(R.id.txt_max_price);

        min_cover_area = (EditText) v.findViewById(R.id.min_cover_area);
        max_cover_area = (EditText) v.findViewById(R.id.max_cover_area);
        min_plot_area = (EditText) v.findViewById(R.id.min_plot_area);
        max_plot_area = (EditText) v.findViewById(R.id.max_plot_area);

        spinner_price_unit = (Spinner) v.findViewById(R.id.spinner_price_unit);
        spinner_cover_unit = (Spinner) v.findViewById(R.id.spinner_cover_unit);
        spinner_plot_unit = (Spinner) v.findViewById(R.id.spinner_plot_unit);

        sw_open_house = (Switch) v.findViewById(R.id.sw_open_house);
        sw_project = (Switch) v.findViewById(R.id.sw_project);
        sw_rent = (Switch) v.findViewById(R.id.sw_rent);
        sw_buy = (Switch) v.findViewById(R.id.sw_buy);
        chk_commercial = (CheckBox) v.findViewById(R.id.chk_Commercial);
        chk_resident = (CheckBox) v.findViewById(R.id.chk_resident);

//        sw_open_house.setChecked(AppConstant.filterModel.isChkOpenhouse());
//        sw_project.setChecked(AppConstant.filterModel.isChkProject());
//        sw_rent.setChecked(AppConstant.filterModel.isChkRent());
//        sw_buy.setChecked(AppConstant.filterModel.isChkBuy());

        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swiper.setEnabled(false);
                swiper.setRefreshing(false);
            }
        });

        rangeSeekbarPrice = (CrystalRangeSeekbar) v.findViewById(R.id.rangeSeekbarPrice);
        rdg_bed_roomes = (RadioGroup) v.findViewById(R.id.rdg_bed_roomes);
        radio_any = (RadioButton) v.findViewById(R.id.radio_any);
        radio_any.setTag("0");
        radio_1 = (RadioButton) v.findViewById(R.id.radio_1);
        radio_1.setTag("1");
        radio_2 = (RadioButton) v.findViewById(R.id.radio_2);
        radio_2.setTag("2");
        radio_3 = (RadioButton) v.findViewById(R.id.radio_3);
        radio_3.setTag("3");
        radio_4 = (RadioButton) v.findViewById(R.id.radio_4);
        radio_4.setTag("4");
        radio_any.setChecked(true);

        rdg_bathrooms = (RadioGroup) v.findViewById(R.id.rdg_bathrooms);
        radio_any_bath = (RadioButton) v.findViewById(R.id.radio_any_bath);
        radio_any_bath.setTag("0");
        radio_1_bath = (RadioButton) v.findViewById(R.id.radio_1_bath);
        radio_1_bath.setTag("1");
        radio_2_bath = (RadioButton) v.findViewById(R.id.radio_2_bath);
        radio_2_bath.setTag("2");
        radio_3_bath = (RadioButton) v.findViewById(R.id.radio_3_bath);
        radio_3_bath.setTag("3");
        radio_4_bath = (RadioButton) v.findViewById(R.id.radio_4_bath);
        radio_4_bath.setTag("4");
        radio_any_bath.setChecked(true);

        rdg_possession = (RadioGroup) v.findViewById(R.id.rdg_possession);
        radio_under_construction = (RadioButton) v.findViewById(R.id.radio_under_construction);
        radio_under_construction.setTag("1");
        radio_ready = (RadioButton) v.findViewById(R.id.radio_ready);
        radio_ready.setTag("2");
        radio_both = (RadioButton) v.findViewById(R.id.radio_both);
        radio_both.setTag("3");

        rdg_transaction_type = (RadioGroup) v.findViewById(R.id.rdg_transaction_type);
        radio_new = (RadioButton) v.findViewById(R.id.radio_new);
        radio_new.setTag("11");
        radio_resale = (RadioButton) v.findViewById(R.id.radio_resale);
        radio_resale.setTag("12");
        radio_both_transaction_type = (RadioButton) v.findViewById(R.id.radio_both_transaction_type);
        radio_both_transaction_type.setTag("13");

        search_button = (RobotoTextView) v.findViewById(R.id.tv_search);
        savesearch_button = (RobotoTextView) v.findViewById(R.id.tv_savesearch);
        apply_button = (RobotoTextView) v.findViewById(R.id.tv_apply);

        iv_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnRefresh();

            }
        });
        rangeSeekbarPrice.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                txt_min_price.setText(String.valueOf(minValue));
                txt_max_price.setText(String.valueOf(maxValue));

            }
        });

        rangeSeekbarPrice.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
                Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
            }
        });


        rdg_possession.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                try {
                    if (!radioButton.getTag().equals(null)) {
                        LogHelper.d("Selected property type", (String) radioButton.getTag());
                        possession = (String) radioButton.getTag();
                    }
                } catch (Exception e) {

                }


            }
        });

        rdg_transaction_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);

                try {
                    if (!radioButton.getTag().equals(null)) {
                        LogHelper.d("Selected property type", (String) radioButton.getTag());
                        transaction_type = (String) radioButton.getTag();
                    }
                } catch (Exception e) {

                }
            }
        });

        rdg_bathrooms.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                LogHelper.d("Selected property type", (String) radioButton.getTag());
                bathRooms = (String) radioButton.getTag();
            }
        });

        rdg_bed_roomes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                LogHelper.d("Selected property type", (String) radioButton.getTag());
                bedRooms = (String) radioButton.getTag();
            }
        });

        spinner_price_unit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LogHelper.d("Selected property type", parent.getItemAtPosition(position).toString());
                CurrencyUnit = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_cover_unit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LogHelper.d("Selected property type", parent.getItemAtPosition(position).toString());
                CoverAreaUnit = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_plot_unit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LogHelper.d("Selected property type", parent.getItemAtPosition(position).toString());
                PlotAreaUnit = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        savesearch_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppConstant.isuser) {
                    swiper.setEnabled(true);
                    swiper.setRefreshing(true);
                    applyorsave(true);
                } else {
                    getActivity().startActivity(new Intent(getActivity(), SignupActivity.class));
                }
            }
        });

        search_button.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                swiper.setEnabled(true);
                swiper.setRefreshing(true);
                applyorsave(false);

            }
        });
        apply_button.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                swiper.setEnabled(true);
                swiper.setRefreshing(true);
                applyorsave(false);
            }
        });

    }

    //    String s = "{"HRK":7.44,"AUD":1.5346,"PLN":4.177,"INR":76.6055,"IDR":16239.12,"JPY":135.01,"EUR":1,"BGN":1.9558,"SGD":1.6024,"ILS":4.1635,"GBP":0.88723,"USD":1.1993,"PHP":59.795,"NZD":1.685,"DKK":7.4449,"CZK":25.535,"CNY":7.8044,"ZAR":14.8054,"HUF":310.33,"TRY":4.5464,"RUB":69.392,"KRW":1279.61,"NOK":9.8403,"BRL":3.9729,"CHF":1.1702,"MXN":23.6612,"HKD":9.372,"RON":4.6585,"CAD":1.5039,"SEK":9.8438,"THB":39.121,"MYR":4.8536}"

    public void applyorsave(boolean issave) {
        swiper.setEnabled(true);
        // swiper.setRefreshing(true);
        //actionGetNearestProperties();
        FilterModel filterModel = new FilterModel();
        filterModel.setChkBuy(sw_buy.isChecked());
        filterModel.setChkRent(sw_rent.isChecked());
        filterModel.setChkOpenhouse(sw_open_house.isChecked());
        filterModel.setChkProject(sw_project.isChecked());
        filterModel.setChkcommercial(chk_commercial.isChecked());
        filterModel.setChkresident(chk_resident.isChecked());
        filterModel.setBedRooms(bedRooms);
        filterModel.setBathRooms(bathRooms);

        //minmaxprice
        if (!txt_min_price.getText().toString().isEmpty()) {
            filterModel.setMinprice(txt_min_price.getText().toString());
        }
        if (!txt_max_price.getText().toString().isEmpty()) {
            filterModel.setMaxprice(txt_max_price.getText().toString());
        }
        if (!isEmptyString(CurrencyUnit)) {
            filterModel.setCurrencyUnit(CurrencyUnit);
        }
        //minmaxcoverarea

        if (!isEmptyString(CoverAreaUnit)) {
            filterModel.setCoverAreaUnit("Sqfeet");
        }

        if (!isEmptyString(min_cover_area.getText().toString())) {
            filterModel.setMincoverarea(String.valueOf(areaconvert(Double.parseDouble(min_cover_area.getText().toString()), CoverAreaUnit)));
        }
        if (!isEmptyString(max_cover_area.getText().toString())) {
            filterModel.setMaxcoverarea(String.valueOf(areaconvert(Double.parseDouble(max_cover_area.getText().toString()), CoverAreaUnit)));
        }

        //minmaxplotarea
        if (!isEmptyString(PlotAreaUnit)) {
            filterModel.setPlotAreaUnit("Sqfeet");
        }

        if (!isEmptyString(min_plot_area.getText().toString())) {
            filterModel.setMinplotarea(String.valueOf(areaconvert(Double.parseDouble(min_plot_area.getText().toString()), PlotAreaUnit)));
        }
        if (!isEmptyString(max_plot_area.getText().toString())) {
            filterModel.setMaxplotarea(String.valueOf(areaconvert(Double.parseDouble(max_plot_area.getText().toString()), PlotAreaUnit)));
        }


        filterModel.setPossession(possession);
        filterModel.setTransaction_type(transaction_type);

        AppConstant.filterModel = filterModel;

        Gson gson = new Gson();
        String json = gson.toJson(filterModel);
        if (UrlConstant.fragmenttag == 3) {
            if (issave) {
                saveflitervaluesonapi(true);
//                Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();

//                EventBus.getDefault().post(new SearchEvent(SearchEvent.ACTION_FILTER_RESULT, ""));
            } else {
                MyPreference myPreference = new MyPreference(getActivity());
                myPreference.saveFilter(json);
                EventBus.getDefault().post(new SearchEvent(SearchEvent.ACTION_FILTER_RESULT, json));
                dismiss();
            }
        } else if (UrlConstant.fragmenttag == 1) {
//            EventBus.getDefault().post(new SearchEvent(SearchEvent.ACTION_FILTER_RESULT, json));
//            dismiss();

            saveflitervaluesonapi(false);
        }


    }

    public double areaconvert(double val, String unit) {
        double area = 0;
        String[] sqfeetstring = {"SqMeter", "SqYards", "Acers"};
        Double[] sqfeetconvert = {10.76391, 9.0, 43.560};
        if (unit.equals(sqfeetstring[0])) {
            area = val * sqfeetconvert[0];
        } else if (unit.equals(sqfeetstring[1])) {
            area = val * sqfeetconvert[1];
        } else if (unit.equals(sqfeetstring[2])) {
            area = val * sqfeetconvert[2];
        } else {
            area = val;
        }
        return area;
    }

    public void saveflitervaluesonapi(boolean issave) {
        minprice = txt_min_price.getText().toString();
        maxprice = txt_max_price.getText().toString();

        mincoverarea = min_cover_area.getText().toString();
        maxcoverarea = max_cover_area.getText().toString();
        minplotarea = min_plot_area.getText().toString();
        maxplotarea = max_plot_area.getText().toString();
        if (mincoverarea.equals("")) {
            mincoverarea = "0";
        }
        if (maxcoverarea.equals("")) {
            maxcoverarea = "0";
        }
        if (minplotarea.equals("")) {
            minplotarea = "0";
        }
        if (maxplotarea.equals("")) {
            maxplotarea = "0";
        }
        if (sw_rent.isChecked() && sw_buy.isChecked() || !sw_rent.isChecked() && !sw_buy.isChecked()) {
            propertyFor = "0";
            orderby = "UsdSalePrice";
        } else if (sw_rent.isChecked()) {
            propertyFor = "2";
            orderby = "UsdMonthlyRent";
        } else if (sw_buy.isChecked()) {
            propertyFor = "1";
            orderby = "UsdSalePrice";
        }


//        if (f.isChkRent() && f.isChkBuy()) {
//            propertyFor = "0";
//        } else if (!f.isChkRent() && !f.isChkBuy()) {
//            propertyFor = "0";
//        } else {
//            if (f.isChkBuy()) {
//                propertyFor = "1";
//                orderby = "UsdSalePrice";
//            }
//            if (f.isChkRent()) {
//                propertyFor = "2";
//
//            }
//            if (f.isChkOpenhouse()) {
//                propertyFor = "3";
//            }
//            if (f.isChkProject()) {
//                propertyFor = "4";
//            }
//        }
        if (chk_commercial.isChecked() && chk_resident.isChecked() || !chk_commercial.isChecked() && !chk_resident.isChecked()) {
            propertyType = "0";
        } else if (f.isChkcommercial()) {
            propertyType = "4";
        } else if (f.isChkresident()) {
            propertyType = "3";
        }

//        possession = f.getPossession();
//        transaction_type = f.getTransaction_type();
//        bedRooms = f.getBedRooms();
//        bathRooms = f.getBathRooms();
//        minprice = f.getMinprice();
//        maxprice = f.getMaxprice();
//        mincoverarea = f.getMincoverarea();
//        maxcoverarea = f.getMaxcoverarea();
//        minplotarea = f.getMinplotarea();
//        maxplotarea = f.getMaxplotarea();
        if (issave) {
            sendfilterdata(true);
        } else {
            sendfilterdata(false);
        }
    }

    private void sendfilterdata(boolean issave) {
        if (CurrencyUnit.length() > 3) {
            CurrencyUnit = CurrencyUnit.substring(CurrencyUnit.indexOf("(") + 1, CurrencyUnit.indexOf(")"));
        }
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = this.getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = this.getResources().getConfiguration().locale;
        }
        if (issave) {
            RequestBody requestBody1 = new FormBody.Builder()
                    .addEncoded("propertyFor", propertyFor)
                    .addEncoded("propertyType", propertyType)
                    .addEncoded("bathroom", bedRooms)
                    .addEncoded("bedroom", bathRooms)
                    .addEncoded("minprice", minprice)
                    .addEncoded("maxprice", maxprice)
                    .addEncoded("mincoverarea", mincoverarea)
                    .addEncoded("maxcoverarea", maxcoverarea)
                    .addEncoded("minplotarea", minplotarea)
                    .addEncoded("maxplotarea", maxplotarea)
                    .addEncoded("Possession", possession)
                    .addEncoded("TransactionType", transaction_type)
                    .addEncoded("orderby", orderby)
                    .addEncoded("orderto", "desc")
                    .addEncoded("CurrencyUnit", CurrencyUnit)
                    .addEncoded("CoverAreaUnit", CoverAreaUnit)
                    .addEncoded("PlotAreaUnit", PlotAreaUnit)
                    .addEncoded("center_latitude", curlat)
                    .addEncoded("center_longitude", curlang)
                    .addEncoded("Title", address)
                    .addEncoded("distance", distance)
                    .build();
            lastCall = MyHttpClientHelper.getInstance().enqueueCall(getActivity(), UrlConstant.API_SAVESEARCHPROPRTY_URL, requestBody1, mSearchPropertyResponseHelper);
        } else {
            RequestBody requestBody = new FormBody.Builder()
                    .addEncoded("propertyFor", propertyFor)
                    .addEncoded("propertyType", propertyType)
                    .addEncoded("bathroom", bedRooms)
                    .addEncoded("bedroom", bathRooms)
                    .addEncoded("minprice", minprice)
                    .addEncoded("maxprice", maxprice)
                    .addEncoded("mincoverarea", mincoverarea)
                    .addEncoded("maxcoverarea", maxcoverarea)
                    .addEncoded("minplotarea", minplotarea)
                    .addEncoded("maxplotarea", maxplotarea)
                    .addEncoded("Possession", possession)
                    .addEncoded("TransactionType", transaction_type)
                    .addEncoded("orderby", orderby)
                    .addEncoded("orderto", "desc")
                    .addEncoded("CurrencyUnit", CurrencyUnit)
                    .addEncoded("CoverAreaUnit", CoverAreaUnit)
                    .addEncoded("PlotAreaUnit", PlotAreaUnit)
                    .addEncoded("center_latitude", curlat)
                    .addEncoded("center_longitude", curlang)
                    .addEncoded("location", "")
                    .build();
            lastCall = MyHttpClientHelper.getInstance().enqueueCall(getActivity(), UrlConstant.API_GET_SEARCH_BY_SALE_RENT_PROPERTY_URL, requestBody, mSearchPropertyResponseHelper);
        }
    }

    private void curruncyconvert(String val, String from, String to) {
        new RetrieveFeedTask().execute();
    }

    class RetrieveFeedTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {

//                String url = "https://finance.google.com/finance/converter?a=1&from=ZMW&to=USD";

//                HttpWebRequest request = (HttpWebRequest)WebRequest.Create(url);
//                WebResponse response = request.GetResponse();
//                using (Stream responseStream = response.GetResponseStream())
//                {
//                    StreamReader reader = new StreamReader(responseStream, Encoding.UTF8);
//                    txt = reader.ReadToEnd();
//                }

                URL url = new URL("https://finance.google.com/finance/converter?a=1&from=ZMW&to=USD");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                InputStream in = new BufferedInputStream(conn.getInputStream());
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                String data = String.valueOf(r.read());

//                data.matches("<span class=\"?bld\"?>([^<]+)</span>"[0].Groups[1].Value);
                Log.e("data", "" + data);
//                String val = data.Matches(data, "<span class=\"?bld\"?>([^<]+)</span>")[0].Groups[1].Value;


//                StringBuilder total = new StringBuilder();
//                String line;
//                while ((line = r.readLine()) != null) {
//                    total.append(line).append('\n');
//                }
//                String result = total.toString();
//                Log.e("result", result);
//                JSONObject jsonArray = new JSONObject(result);
//                JSONArray jsonArray1 = jsonArray.getJSONArray("rates");
//                for (int i = 0; i < jsonArray1.length(); i++) {
//                    CurrencyModel currencyModel = new CurrencyModel();
//                    String[] val = jsonArray1.getJSONObject(i).toString().split(":");
//                    currencyModel.setCurrencyname(val[0]);
//                    currencyModel.setCurrencyvalue(val[1]);
//                    currencyllist.add(currencyModel);
//                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
