package com.land;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.google.gson.Gson;
import com.land.adapter.NearestPropertyAdapter;
import com.land.api.model.FilterModel;
import com.land.api.model.NearestPropertiesModel;
import com.land.constant.ApiConstant;
import com.land.constant.UrlConstant;
import com.land.fragment.MySupportMapFragment;
import com.land.helper.LogHelper;
import com.land.helper.MyHttpClientHelper;
import com.land.helper.TintDrawableHelper;
import com.land.utils.MyPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class Drivebymapactivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<LocationSettingsResult>, GoogleMap.OnMarkerClickListener, LocationListener {
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 20 * 1000;  //min*sec*millisecond
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    //var
    private final static String TAG = Drivebymapactivity.class.getSimpleName();
    private static final int REQUEST_PERMISSION = 2001;
    protected LocationRequest mLocationRequest;
    protected LocationSettingsRequest mLocationSettingsRequest;
    ProgressBar progressBar;
    NearestPropertyAdapter nearestPropertyAdapter;
    SharedPreferences mPrefs;
    FilterModel f;
    //icon
    BitmapDescriptor iconblue_agent;
    BitmapDescriptor iconorange_buy;
    BitmapDescriptor icongreen_openhouse;
    BitmapDescriptor iconpink_project;
    BitmapDescriptor iconbluecyan_rent;
    BitmapDescriptor iconRed;
    BitmapDescriptor iconBlue;
    BitmapDescriptor iconcar;
    List<Marker> m;
    //map
    Marker current;
    PendingIntent pintent;
    AlarmManager alarm;
    private Toolbar toolbar;
    private GoogleMap mmMap;
    private ViewPager viewpager;
    private double lastdis;
    private String selectedradius = "";
    private boolean moveForCall = true;
    private Call lastCall;
    private Handler handler;
    //list
    private List<NearestPropertiesModel> propertiesModels;
    private List<NearestPropertiesModel> Mainlist = new ArrayList<>();
    private GoogleApiClient mGoogleApiClient;
    private LatLng mLastLocation;
    private MyHttpClientHelper.ProcessResponseHelper mNearestPropertyResponseHelper = new MyHttpClientHelper.ProcessResponseHelper() {
        @Override
        public void onRequest() {
        }

        @Override
        public void onResponse(JSONObject object) {
            try {
                int code = object.getInt(ApiConstant.JSON_KEY_CODE);
                if (code == 1) {
                    Mainlist = new ArrayList<>();
                    JSONArray dataObject = object.getJSONArray(ApiConstant.JSON_KEY_DATA);
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
                                addMarkers();
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
            LogHelper.d("Tag", "Something Want Wrong");
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivebymapactivity);
        MapsInitializer.initialize(getApplicationContext());

        //Init
        progressBar = (ProgressBar) findViewById(R.id.reesmap_progressbar);
        toolbar = (Toolbar) findViewById(R.id.maintoolbar);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        handler = new Handler(Looper.getMainLooper());


        toolbar.setNavigationIcon(TintDrawableHelper.getTintedResource(this, android.support.design.R.drawable.abc_ic_ab_back_material, R.color.colorWhite));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        iconBlue = BitmapDescriptorFactory.fromResource(R.drawable.icn_marker_blue);
        iconRed = BitmapDescriptorFactory.fromResource(R.drawable.icn_marker_red);
        iconbluecyan_rent = BitmapDescriptorFactory.fromResource(R.drawable.icn_rent_locpin);
        iconorange_buy = BitmapDescriptorFactory.fromResource(R.drawable.icn_buy_locpin);
        iconpink_project = BitmapDescriptorFactory.fromResource(R.drawable.icn_prjct_locpin);
        iconblue_agent = BitmapDescriptorFactory.fromResource(R.drawable.icn_agents_locpin);
        icongreen_openhouse = BitmapDescriptorFactory.fromResource(R.drawable.icn_openhouse_locpin);
        iconcar = BitmapDescriptorFactory.fromResource(R.drawable.icn_car_driveby);

        final MySupportMapFragment customdrivebyMapFragment = ((MySupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.drivebymap));
        customdrivebyMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mmMap = googleMap;
                mmMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getApplicationContext(), R.raw.style_json));
                mmMap.setOnMarkerClickListener(Drivebymapactivity.this);
                if (ActivityCompat.checkSelfPermission(Drivebymapactivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Drivebymapactivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION);
                    }
                    return;
                }

                mmMap.setMyLocationEnabled(true);
                mmMap.getUiSettings().setMyLocationButtonEnabled(false);
                initiateLocationExecution();
                MyPreference myPreference = new MyPreference(getApplicationContext());
                mLastLocation = myPreference.getLatestLocation();
                MarkerOptions markerOptions = new MarkerOptions().icon(iconcar).position(mLastLocation).title("I am hire !");
                current = mmMap.addMarker(markerOptions);
                current.setTag("current");
                mmMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                    @Override
                    public void onCameraIdle() {

                        VisibleRegion vr = mmMap.getProjection().getVisibleRegion();
                        double left = vr.latLngBounds.southwest.longitude;
                        double top = vr.latLngBounds.northeast.latitude;
                        double right = vr.latLngBounds.northeast.longitude;
                        double bottom = vr.latLngBounds.southwest.latitude;

                        Location MiddleLeftCornerLocation;//(center's latitude,vr.latLngBounds.southwest.longitude)
                        Location center = new Location("center");
                        center.setLatitude(vr.latLngBounds.getCenter().latitude);
                        center.setLongitude(vr.latLngBounds.getCenter().longitude);

                        MiddleLeftCornerLocation = new Location("bottom");
                        MiddleLeftCornerLocation.setLatitude(vr.latLngBounds.southwest.latitude);
                        MiddleLeftCornerLocation.setLongitude(vr.latLngBounds.southwest.longitude);

                        float dis = center.distanceTo(MiddleLeftCornerLocation) / 1000;

                        if (moveForCall)
                            actionGetNearestProperties("" + dis, "" + center.getLatitude(), "" + center.getLongitude());
                        moveForCall = true;

                    }
                });
            }
        });

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                Log.e("Position", "" + position);
//                Log.e("size", "" + m.size());
//                Log.e("propertymodel", "" + propertiesModels.size());

                for (int i = 0; i < m.size(); i++) {
//                        Log.e("i", "" + i);
                    if (position == i) {
                        m.get(i).setIcon(iconRed);
                    } else {
//                            Log.e("proprty for", "" + propertiesModels.get(i).getPropertyFor());
                        if (propertiesModels.get(i).getPropertyFor().equals("1")) {
                            m.get(i).setIcon(iconorange_buy);
                        } else if (propertiesModels.get(i).getPropertyFor().equals("2")) {
                            m.get(i).setIcon(iconbluecyan_rent);
                        }
//
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void actionGetNearestProperties(String radius, String lat, String lang) {
        progressBar.setVisibility(View.VISIBLE);
        RequestBody requestBody = new FormBody.Builder()
                .addEncoded("radius", radius)
                .addEncoded("latitude", lat)
                .addEncoded("longitude", lang).build();
        lastCall = MyHttpClientHelper.getInstance().enqueueCall(Drivebymapactivity.this, UrlConstant.API_GET_NEAREST_PROPERTIES_URL, requestBody, mNearestPropertyResponseHelper);
    }

    private void addMarkers() {
        propertiesModels = new ArrayList<>();
        if (m != null && m.size() > 0) {
            for (Marker marker : m) {
                marker.remove();
            }
        }

        if (!filterarray(Mainlist).isEmpty()) {
            propertiesModels = filterarray(Mainlist);
        }


        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
//                    Log.e("pmlist", "" + propertiesModels);
                    m = new ArrayList<>();
                    if (mmMap != null && propertiesModels.size() > 0) {
                        nearestPropertyAdapter = new NearestPropertyAdapter(Drivebymapactivity.this, propertiesModels);
                        //mmMap.clear();


                        //mmMap.addMarker(markerOptions);

                        for (int i = 0; i < propertiesModels.size(); i++) {
                            Marker mar = null;
                            if (propertiesModels.get(i).getPropertyFor().equals("1")) {
                                mar = mmMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(Float.parseFloat(propertiesModels.get(i).getLatitude()), Float.parseFloat(propertiesModels.get(i).getLongitude())))
                                        .icon(iconorange_buy)
                                        .title(propertiesModels.get(i).getTitle()));
                            } else if (propertiesModels.get(i).getPropertyFor().equals("2")) {
                                mar = mmMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(Float.parseFloat(propertiesModels.get(i).getLatitude()), Float.parseFloat(propertiesModels.get(i).getLongitude())))
                                        .icon(iconbluecyan_rent)
                                        .title(propertiesModels.get(i).getTitle()));
                            } else if (propertiesModels.get(i).getPropertyFor().equals("0")) {
                                if (propertiesModels.get(i).getPropertyFor().equals("1")) {
                                    mar = mmMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(Float.parseFloat(propertiesModels.get(i).getLatitude()), Float.parseFloat(propertiesModels.get(i).getLongitude())))
                                            .icon(iconorange_buy)
                                            .title(propertiesModels.get(i).getTitle()));
                                } else if (propertiesModels.get(i).getPropertyFor().equals("2")) {
                                    mar = mmMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(Float.parseFloat(propertiesModels.get(i).getLatitude()), Float.parseFloat(propertiesModels.get(i).getLongitude())))
                                            .icon(iconbluecyan_rent)
                                            .title(propertiesModels.get(i).getTitle()));
                                }
                            } else {
                                propertiesModels.remove(i);
                            }
//                else if (propertiesModels.get(i).getPropertyFor().equals("0") && !f.isChkRent() && !f.isChkBuy()) {
//                    mar = mmMap.addMarker(new MarkerOptions()
//                            .position(new LatLng(Float.parseFloat(propertiesModels.get(i).getLatitude()), Float.parseFloat(propertiesModels.get(i).getLongitude())))
//                            .icon(iconRed)
//                            .title(propertiesModels.get(i).getTitle()));
//                }


                            if (mar != null) {
                                mar.setTag(String.valueOf(i));
                                m.add(mar);
                            }
                        }

                        viewpager.setAdapter(nearestPropertyAdapter);
                        progressBar.setVisibility(View.GONE);

                        final ArrayList<Double> list = new ArrayList<>();
                        final ArrayList<String> pid = new ArrayList<>();
                        for (NearestPropertiesModel n : propertiesModels) {
                            lastdis = Double.parseDouble(n.getDISTANCE().toString());
                            list.add(lastdis);
                            pid.add(n.getPropertyID().toString());
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
//                                    Log.e("plist", "" + pid);
//                                    Log.e("dis", "" + list);
                                    int minIndex = list.indexOf(Collections.min(list));
                                    viewpager.setCurrentItem(minIndex);
//                                    Log.e("dis", "" + minIndex);
//                                    Log.e("getmindis", "" + list.get(minIndex));
                                    Log.e("getpid", "" + "" + pid.get(minIndex));

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public ArrayList<NearestPropertiesModel> filterarray(List<NearestPropertiesModel> listn) {
        ArrayList<NearestPropertiesModel> filterlist = new ArrayList<>();
        MyPreference preference = new MyPreference(getApplicationContext());
        Gson gson = new Gson();
        String json = preference.getFilter();
//        Log.e("getfm", "" + json);
        f = gson.fromJson(json, FilterModel.class);
        if (f != null) {
            for (int i = 0; i < listn.size(); i++) {
                if (isMatch(f, listn.get(i))) {


                    if (f.isChkRent()) {
                        if (listn.get(i).getPropertyFor().equals("2")) {
                            if (filterlist.isEmpty()) {
                                filterlist.add(listn.get(i));
                            } else {
                                if (!filterlist.contains(listn.get(i))) {
                                    filterlist.add(listn.get(i));
                                }

                            }

//                            Log.e("propertyfor  " + i, listn.get(i).getPropertyFor());
//                            Log.e("Filterlist  " + i, "" + filterlist.size());

                        }
                    }
                    if (f.isChkBuy()) {
                        if (listn.get(i).getPropertyFor().equals("1")) {
                            if (filterlist.isEmpty()) {
                                filterlist.add(listn.get(i));
                            } else {
                                if (!filterlist.contains(listn.get(i))) {
                                    filterlist.add(listn.get(i));
                                }
                            }
//                            Log.e("Filterlist  " + i, "" + filterlist.size());
//                            Log.e("propertyfor  " + i, listn.get(i).getPropertyFor());
                        }
                    }

                    if (!f.isChkBuy() && !f.isChkRent()) {
                        if (filterlist.isEmpty()) {
                            filterlist.add(listn.get(i));
                        } else {
                            if (!filterlist.contains(listn.get(i))) {
                                filterlist.add(listn.get(i));
                            }
                        }
//                        Log.e("propertyfor  " + i, listn.get(i).getPropertyFor());
//                        Log.e("Filterlist  " + i, "" + filterlist.size());
                    }
//                    if (f.isChkProject()) {
//                        if (listn.get(i).getPropertyFor().equals("3")) {
//                            filterlist.add(listn.get(i));
//                        }
//                    }
//                    if (f.isChkOpenhouse()) {
//                        if (listn.get(i).getPropertyFor().equals("4")) {
//                            filterlist.add(listn.get(i));
//                        }
//                    }
                }
            }
        }
        return filterlist;
    }

    public boolean isMatch(FilterModel f, NearestPropertiesModel n) {
        Double minprice = Double.parseDouble(f.getMinprice());
        Double maxprice = Double.parseDouble(f.getMaxprice());
        Double mincoverarea = Double.parseDouble(f.getMincoverarea());
        Double maxcoverarea = Double.parseDouble(f.getMaxcoverarea());
        Double minploatarea = Double.parseDouble(f.getMinplotarea());
        Double maxplotarea = Double.parseDouble(f.getMaxplotarea());
        Double priceoflistitem = Double.parseDouble(n.getSalePrice());
        Double coverareaoflistitem = Double.parseDouble(n.getLandArea());
        Double plotareaoflistitem = Double.parseDouble(n.getBuiltArea());

        if (f.getBedRooms().equals("0") || f.getBedRooms().equals(n.getNoofBedrooms())) {
            if (f.getBathRooms().equals("0") || f.getBathRooms().equals(n.getNoofBathrooms())) {
                if (f.getTransaction_type().equals("13") || f.getTransaction_type().equals(n.getTransactionType())) {
                    if (minprice == 1000 && maxprice == 100000 || (priceoflistitem >= minprice && priceoflistitem <= maxprice)) {
                        if ((mincoverarea == 0.0 && maxcoverarea == 0.0) || (coverareaoflistitem >= mincoverarea && coverareaoflistitem <= maxcoverarea)) {
                            if ((minploatarea == 0.0 && maxplotarea == 0.0) || (plotareaoflistitem >= minploatarea && plotareaoflistitem <= maxplotarea)) {
                                if (f.isChkcommercial() && f.isChkresident() || !f.isChkcommercial() && !f.isChkresident()) {
                                    return true;
                                } else {
                                    if (f.isChkcommercial() && n.getPropertyType().equals("4")) {
                                        return true;
                                    } else if (f.isChkresident() && n.getPropertyType().equals("3")) {
                                        return true;
                                    } else {
                                        return false;
                                    }
                                }
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    protected void checkLocationSettings() {
        try {
            buildLocationSettingsRequest();
            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, mLocationSettingsRequest);
            result.setResultCallback(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        checkLocationSettings();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();

        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                LocationRequest updaterequest = LocationRequest.create();
                updaterequest.setSmallestDisplacement((float) 25.0);
                updaterequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
                updaterequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Drivebymapactivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                } else {
                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, updaterequest, this);
                }
                LogHelper.d(TAG, "All location settings are satisfied." + location);
                if (location != null) {
                    mmMap.clear();
                    mLastLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    MarkerOptions markerOptions = new MarkerOptions().position(mLastLocation).icon(iconcar).title("I am hire !");
                    current = mmMap.addMarker(markerOptions);
                    current.setTag("current");
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(mLastLocation).zoom(12).build();
                    mmMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    actionGetNearestProperties(selectedradius, String.valueOf(location.getLatitude()), String.valueOf(location.getLatitude()));
                } else {
                    LocationRequest request = LocationRequest.create();
                    request.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
                    request.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Drivebymapactivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    } else {
                        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, request, this);
                    }
                }
                break;

            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                LogHelper.i(TAG, "Location settings are not satisfied. Show the user a dialog to" + "upgrade location settings ");
                try {
                    this.startIntentSenderForResult(status.getResolution().getIntentSender(), REQUEST_CHECK_SETTINGS, null, 0, 0, 0, null);
                    // status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException e) {
                    LogHelper.i(TAG, "PendingIntent unable to execute request.");
                }
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                LogHelper.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog " + "not created.");
                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.e("onlocationchange", "" + location);
        try {
            if (location != null) {
                current.remove();
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                mLastLocation = latLng;
                MarkerOptions markerOptions = new MarkerOptions().icon(iconcar).position(mLastLocation).title("I am hire !");
                current = mmMap.addMarker(markerOptions);
                current.setTag("current");
                Log.e("onlocationchange", "" + location);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        addMarkers();
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    private void initiateLocationExecution() {
        buildGoogleApiClient();
        createLocationRequest();
        buildLocationSettingsRequest();
    }

    protected synchronized void buildGoogleApiClient() {
        LogHelper.e(TAG, "Building GoogleApiClient");
        //  if (mGoogleApiClient == null) {
        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        //  }
        if (!mGoogleApiClient.isConnected() && !mGoogleApiClient.isConnecting()) {
            mGoogleApiClient.connect();
        }
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }


}
