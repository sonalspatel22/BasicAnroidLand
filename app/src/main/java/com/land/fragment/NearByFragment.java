package com.land.fragment;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.google.gson.Gson;
import com.google.maps.android.PolyUtil;
import com.land.Drivebymapactivity;
import com.land.MainActivity;
import com.land.Place.GetlatlangbyAddress;
import com.land.R;
import com.land.adapter.NearestPropertyAdapter;
import com.land.adapter.PlacesAutoCompleteAdapter;
import com.land.api.model.FilterModel;
import com.land.api.model.NearestPropertiesModel;
import com.land.api.model.SearchPlaceModel;
import com.land.constant.ApiConstant;
import com.land.constant.AppConstant;
import com.land.constant.UrlConstant;
import com.land.custom.RobotoTextView;
import com.land.events.SearchEvent;
import com.land.helper.LogHelper;
import com.land.helper.MyHttpClientHelper;
import com.land.utils.MyPreference;
import com.land.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;

import static com.land.R.id.txt_serch;

public class NearByFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<LocationSettingsResult>, LocationListener, GoogleMap.OnMarkerClickListener {
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 5 * 1000;  //min*sec*millisecond
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    private static final int REQUEST_PERMISSION = 2001;
    private static final int INTENT_LOCATION_SETTINGS = 2002;
    private final static String TAG = NearByFragment.class.getSimpleName();
    public static boolean driveby = false;
    public double latitude;
    public double longitude;
    public Location targetLocation;
    protected LocationRequest mLocationRequest;
    protected LocationSettingsRequest mLocationSettingsRequest;
    AutoCompleteTextView searchAutoComplete;
    Marker current;
    double lastdis = 0.0;
    String selectedradius = "";
    FilterModel f;
    int zoomlevel = 12;
    //
    Boolean Is_MAP_Moveable = false; // to detect map is movable
    Boolean IS_POLIGONE_EXIST = false;
    Boolean IS_MAP_DRAW_MODE = false;
    Projection projection;
    ArrayList<LatLng> val = new ArrayList<>();
    PolygonOptions rectOptions;
    Polygon polygon;
    LatLng lastpoint;
    LatLng Currentpoint;
    BitmapDescriptor iconblue_agent;
    BitmapDescriptor iconorange_buy;
    BitmapDescriptor icongreen_openhouse;
    BitmapDescriptor iconpink_project;
    BitmapDescriptor iconbluecyan_rent;
    BitmapDescriptor iconRed;
    BitmapDescriptor iconBlue;
    BitmapDescriptor iconcar;
    ViewPager viewpager;
    NearestPropertyAdapter nearestPropertyAdapter;
    List<Marker> m;
    View views;
    float dis;
    ProgressBar progressBar;
    boolean ispageerchange = false;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 81;
    MyPreference preference;
    private RelativeLayout rlfilterview;
    private Toolbar toolbar;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private LatLng mLastLocation;
    private RelativeLayout footer, draw_toolbar;
    private RobotoTextView txt_clear, txt_back, txt_apply;
    private ImageView img_btn_filter;
    private boolean isMapDrawable = false;
    private boolean moveForCall = true;
    //
    private Call lastCall;
    private Handler handler;
    private Context context;
    private Activity activity;
    private List<NearestPropertiesModel> propertiesModels = new ArrayList<>();
    private List<NearestPropertiesModel> propertiesModelsSelected = new ArrayList<>();
    private List<NearestPropertiesModel> Mainlist = new ArrayList<>();
    private Map<String, Double> valueMap;
    private MyHttpClientHelper.ProcessResponseHelper mNearestPropertyResponseHelper = new MyHttpClientHelper.ProcessResponseHelper() {
        @Override
        public void onRequest() {
        }

        @Override
        public void onResponse(JSONObject object) {
            try {
                int code = object.getInt(ApiConstant.JSON_KEY_CODE);
                if (code == 1) {
                    JSONArray dataObject = object.getJSONArray(ApiConstant.JSON_KEY_DATA);
                    Mainlist = new ArrayList<>();
                    propertiesModels = new ArrayList<>();
                    for (int i = 0; i < dataObject.length(); i++) {
                        JSONObject property = dataObject.getJSONObject(i);
                        NearestPropertiesModel propertiesModel = new NearestPropertiesModel();
                        propertiesModel.fromJson(property);
                        Mainlist.add(propertiesModel);
                    }
//for zoomin map;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (Mainlist.isEmpty()) {
                                    Toast.makeText(context, "No Property Found", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    viewpager.setVisibility(View.GONE);
                                } else {
                                    addMarkers(mMap);
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
            LogHelper.d("Tag", "Something Want Wrong");
        }
    };

    //dashrarth
    public NearByFragment() {
        // Required empty public constructor
    }

    public static NearByFragment newInstance() {
        NearByFragment fragment = new NearByFragment();
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // Not implemented here

                return false;
            default:
                break;
        }

        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        MapsInitializer.initialize(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        UrlConstant.fragmenttag = 3;
        context = getActivity().getApplicationContext();
        activity = getActivity();
        preference = new MyPreference(activity);
        return inflater.inflate(R.layout.fragment_near_by, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        views = view;
        handler = new Handler(Looper.getMainLooper());
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        iconBlue = BitmapDescriptorFactory.fromResource(R.drawable.icn_marker_blue);
        iconRed = BitmapDescriptorFactory.fromResource(R.drawable.icn_marker_red);
        iconbluecyan_rent = BitmapDescriptorFactory.fromResource(R.drawable.icn_rent_locpin);
        iconorange_buy = BitmapDescriptorFactory.fromResource(R.drawable.icn_buy_locpin);
        iconpink_project = BitmapDescriptorFactory.fromResource(R.drawable.icn_prjct_locpin);
        iconblue_agent = BitmapDescriptorFactory.fromResource(R.drawable.icn_agents_locpin);
        icongreen_openhouse = BitmapDescriptorFactory.fromResource(R.drawable.icn_openhouse_locpin);
        iconcar = BitmapDescriptorFactory.fromResource(R.drawable.icn_car_driveby);

        footer = (RelativeLayout) view.findViewById(R.id.footer);
        draw_toolbar = (RelativeLayout) view.findViewById(R.id.draw_toolbar);
        txt_clear = (RobotoTextView) view.findViewById(R.id.txt_clear);
        txt_back = (RobotoTextView) view.findViewById(R.id.txt_back);
        txt_apply = (RobotoTextView) view.findViewById(R.id.txt_apply);
        rlfilterview = (RelativeLayout) view.findViewById(R.id.filter_view);
        viewpager = (ViewPager) view.findViewById(R.id.viewpager);

        progressBar = (ProgressBar) view.findViewById(R.id.reesmap_progressbar);

        final MySupportMapFragment customMapFragment = ((MySupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));
        customMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                mMap = googleMap;
                mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.style_json));
                mMap.setOnMarkerClickListener(NearByFragment.this);
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION);
                    return;
                }

                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                initiateLocationExecution();
                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    mLastLocation = new LatLng(latitude, longitude);
                    preference.saveLatestLocation(mLastLocation);
                    actionGetNearestProperties(selectedradius, String.valueOf(latitude), String.valueOf(longitude));
                }
                mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                    @Override
                    public void onCameraIdle() {
                        if (!ispageerchange) {
                            VisibleRegion vr = mMap.getProjection().getVisibleRegion();
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

                            dis = center.distanceTo(MiddleLeftCornerLocation) / 1000;
                            selectedradius = String.valueOf(dis);
                            if (moveForCall)
                                actionGetNearestProperties("" + dis, "" + center.getLatitude(), "" + center.getLongitude());
                            moveForCall = true;

                        }

                    }
                });


            }
        });

        FrameLayout fram_map = (FrameLayout) view.findViewById(R.id.fram_map);
        //final FloatingActionButton btn_draw_State = (FloatingActionButton) view.findViewById(R.id.fab_free_draw);
        //FloatingActionButton fab_user_define_pin = (FloatingActionButton) view.findViewById(R.id.fab_user_define_pin);
        final FloatingActionButton fab_drive_mode = (FloatingActionButton) view.findViewById(R.id.fab_drive_mode);
        FloatingActionButton fab_map_view = (FloatingActionButton) view.findViewById(R.id.fab_map_view);
        FloatingActionButton fab_listing = (FloatingActionButton) view.findViewById(R.id.fab_listing);
        // FloatingActionButton fab_filter = (FloatingActionButton) view.findViewById(R.id.fab_filter);

        ImageView fab_user_define_pin = (ImageView) view.findViewById(R.id.img_user_define_pin);
        ImageView ivsearch = (ImageView) view.findViewById(R.id.img_search);
        final ImageView img_free_draw = (ImageView) view.findViewById(R.id.img_free_draw);

        ivsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstant.hidesoftkeyboard(getActivity());
                LatLng latLng = null;
                String address = "";
                if (!searchAutoComplete.getText().toString().equals("")) {
                    address = searchAutoComplete.getText().toString();
                    Log.e("address", "" + address);
                    GetlatlangbyAddress getlatlangbyAddress = new GetlatlangbyAddress();
                    latLng = getlatlangbyAddress.getLocationFromAddress(getActivity(), address);
                    Movecameraonlatlang(latLng);
                }


            }
        });

        searchAutoComplete = (AutoCompleteTextView) view.findViewById(txt_serch);
        searchAutoComplete.setHintTextColor(getResources().getColor(R.color.txtcolor_gray));
        searchAutoComplete.setTextColor(getResources().getColor(R.color.colorBlack));
        searchAutoComplete.setAdapter(new PlacesAutoCompleteAdapter(getActivity(), R.layout.autocomplete_list_item));
        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppConstant.hidesoftkeyboard(getActivity());
                ispageerchange = false;
                SearchPlaceModel searchPlaceModel = AppConstant.searchPlaceModel.get(position);
                searchAutoComplete.setText(searchPlaceModel.getDescription());
                searchandgo(searchPlaceModel.getId());
            }
        });
//        txt_serch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                try {
//////                    Intent intent = null;
//////                    intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(getActivity());
//////                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
////                } catch (GooglePlayServicesRepairableException e) {
////                    e.printStackTrace();
////                } catch (GooglePlayServicesNotAvailableException e) {
////                    e.printStackTrace();
////                }
//            }
//        });
        fab_map_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                } else {
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }
            }
        });
        fab_listing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!propertiesModels.isEmpty()) {
                    SearchListingFragment.newInstance(propertiesModels).show(getActivity().getSupportFragmentManager(), "SHARE-VOICE");
                } else {
                    Toast.makeText(getActivity(), "Data Not Found", Toast.LENGTH_SHORT).show();
                }
            }
        });


    /*    fab_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PropertyFilterActivity.class));
            }
        });*/

        fab_user_define_pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current != null) {
                    current.remove();
                }
                mLastLocation = preference.getLatestLocation();
                Movecameraonlatlang(mLastLocation);
//                MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).position(mLastLocation);
//                current = mMap.addMarker(markerOptions);
//                current.setTag("current");
//                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(mLastLocation, zoomlevel);
//                mMap.animateCamera(cameraUpdate);
            }
        });
        fab_drive_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Drivebymapactivity.class);
                startActivity(i);
            }
        });

        img_free_draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (!isMapDrawable) {
                    if (Is_MAP_Moveable != true) {
                        Is_MAP_Moveable = true;
                        IS_POLIGONE_EXIST = false;
                    } else {
                        Is_MAP_Moveable = false;
                    }
                    mMap.clear();
                    ((MainActivity) getActivity()).drawMapView(true);
                    drawMapView(true);
                    isMapDrawable = true;
                    IS_MAP_DRAW_MODE = true;
                } else {
                    //isMapDrawable = false;
                    img_free_draw.setImageResource(R.drawable.icn_draw_loc);
                    if (polygon != null) {
                        polygon.remove();
                        polygon = null;
                    }
                    mMap.clear();
                    addMarkers(mMap);
                    resetMapzoom();
                }
            }
        });

        txt_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                val = new ArrayList<>();
                Is_MAP_Moveable = true; //dr 122517
                IS_POLIGONE_EXIST = false;
            }
        });

        txt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                val = new ArrayList<>();
                Is_MAP_Moveable = false;
                isMapDrawable = false;
                drawMapView(false);
                IS_MAP_DRAW_MODE = false;
                ((MainActivity) getActivity()).drawMapView(false);
                addMarkers(mMap);
            }
        });

        txt_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (polygon != null && polygon.getPoints().size() > 0) {
                    Is_MAP_Moveable = false;
                    drawMapView(false);
                    IS_MAP_DRAW_MODE = false;
                    ((MainActivity) getActivity()).drawMapView(false);
                    addMarkersonPolyline();
                    img_free_draw.setImageResource(R.drawable.icn_draw_cancel);
                    isMapDrawable = true;
                    moveForCall = false;

                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    for (LatLng marker : polygon.getPoints()) {
                        builder.include(marker);
                    }
                    LatLngBounds bounds = builder.build();
                    int padding = 30; // offset from edges of the map in pixels
                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                    mMap.animateCamera(cu);
                } else {
                    ToastUtil.makeText(getActivity(), "Please select area", Toast.LENGTH_SHORT).show();
                }
            }
        });


        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                ispageerchange = true;
            }

            @Override
            public void onPageSelected(int position) {
                ispageerchange = true;
                try {
                    if (!isMapDrawable) {
                        for (int i = 0; i < m.size(); i++) {
//                        Log.e("i", "" + i);
                            if (position == i) {
                                m.get(i).setIcon(iconRed);
                                LatLng latLng = new LatLng(Double.parseDouble(propertiesModels.get(i).getLatitude()), Double.parseDouble(propertiesModels.get(i).getLongitude()));
                                CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(zoomlevel).build();
                                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                            } else {
                                if (propertiesModels.get(i).getPropertyFor().equals("1")) {
                                    m.get(i).setIcon(iconorange_buy);
                                } else if (propertiesModels.get(i).getPropertyFor().equals("2")) {
                                    m.get(i).setIcon(iconbluecyan_rent);
                                }
                            }
                        }
                    } else {
                        for (int i = 0; i < m.size(); i++) {
                            if (position == i) {
                                m.get(i).setIcon(iconRed);
                                LatLng latLng = new LatLng(Double.parseDouble(propertiesModels.get(i).getLatitude()), Double.parseDouble(propertiesModels.get(i).getLongitude()));
                                CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(zoomlevel).build();
                                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                            } else {
                                if (propertiesModels.get(i).getPropertyFor().equals("1")) {
                                    m.get(i).setIcon(iconorange_buy);
                                } else if (propertiesModels.get(i).getPropertyFor().equals("2")) {
                                    m.get(i).setIcon(iconbluecyan_rent);
                                }

                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        fram_map.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ispageerchange = false;

                if (Is_MAP_Moveable == true && IS_POLIGONE_EXIST == false) {
                    float x = event.getX();
                    float y = event.getY();

                    int x_co = Math.round(x);
                    int y_co = Math.round(y);

                    projection = mMap.getProjection();
                    Point x_y_points = new Point(x_co, y_co);

                    LatLng latLng = mMap.getProjection().fromScreenLocation(x_y_points);
                    latitude = latLng.latitude;
                    longitude = latLng.longitude;

                    int eventaction = event.getAction();
                    switch (eventaction) {
                        case MotionEvent.ACTION_DOWN:
                            // finger touches the screen
                            lastpoint = new LatLng(latitude, longitude);
                            val.add(new LatLng(latitude, longitude));
                            break;
                        case MotionEvent.ACTION_MOVE:
                            // finger moves on the screen
                            val.add(new LatLng(latitude, longitude));
                            Currentpoint = new LatLng(latitude, longitude);
                            Draw_MapLine();
                            lastpoint = Currentpoint;

                            break;
                        case MotionEvent.ACTION_UP:
                            // finger leaves the screen
                            if (Is_MAP_Moveable == true && IS_POLIGONE_EXIST == false)
                                Draw_Map();
                            break;

                        case MotionEvent.ACTION_CANCEL:
                            // finger leaves the screen
                            if (Is_MAP_Moveable == true && IS_POLIGONE_EXIST == false)
                                Draw_Map();
                            break;
                        case MotionEvent.ACTION_POINTER_UP:
                            if (Is_MAP_Moveable == true && IS_POLIGONE_EXIST == false)
                                Draw_Map();
                            break;
                    }
                }

                if (Is_MAP_Moveable == true && IS_POLIGONE_EXIST == false) {
                    return true;
                } else if (IS_MAP_DRAW_MODE == true) {
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    public void Draw_Map() {
        rectOptions = new PolygonOptions();
        rectOptions.addAll(val);
        rectOptions.strokeColor(Color.BLUE);
        rectOptions.strokeWidth(7);
        rectOptions.fillColor(Color.parseColor("#805d5dff"));
        polygon = mMap.addPolygon(rectOptions);
        // Is_MAP_Moveable = false;
        val = new ArrayList<>();
        rectOptions = null;
        Is_MAP_Moveable = true; //dr 122517
        IS_POLIGONE_EXIST = true;
    }

    public void Draw_MapLine() {
        Polyline line = mMap.addPolyline(new PolylineOptions()
                .add(lastpoint, Currentpoint)
                .width(5)
                .color(Color.RED));
    }

    private void resetMapzoom() {
        VisibleRegion vr = mMap.getProjection().getVisibleRegion();
        Location center = new Location("center");
        center.setLatitude(vr.latLngBounds.getCenter().latitude);
        center.setLongitude(vr.latLngBounds.getCenter().longitude);

        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(center.getLatitude(), center.getLongitude())).zoom(zoomlevel).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        isMapDrawable = false;
    }

    private void initiateLocationExecution() {
        buildGoogleApiClient();
        createLocationRequest();
        buildLocationSettingsRequest();
    }

    protected synchronized void buildGoogleApiClientforsearch() {
        LogHelper.e(TAG, "Building GoogleApiClient");
        //  if (mGoogleApiClient == null) {
        mGoogleApiClient = new GoogleApiClient
                .Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        //  }
        if (!mGoogleApiClient.isConnected() && !mGoogleApiClient.isConnecting()) {
            mGoogleApiClient.connect();
        }
    }

    /**
     * Builds a GoogleApiClient. Uses the {@code #addApi} method to request the
     * LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        LogHelper.e(TAG, "Building GoogleApiClient");
        //  if (mGoogleApiClient == null) {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
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
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();

        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                LogHelper.d(TAG, "All location settings are satisfied." + location);
                if (location != null) {
                    mMap.clear();

                    mLastLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    preference.saveLatestLocation(mLastLocation);
                    MarkerOptions markerOptions = new MarkerOptions().position(mLastLocation).title("I am hire !");
                    current = mMap.addMarker(markerOptions);
                    current.setTag("current");
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(mLastLocation).zoom(zoomlevel).build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//                    actionGetNearestProperties(selectedradius, String.valueOf(mMap.getMyLocation().getLatitude()), String.valueOf(mMap.getMyLocation().getLongitude()));
                } else {
                    com.google.android.gms.location.LocationListener listener = new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            try {
                                if (location != null) {
                                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                                    mLastLocation = latLng;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    LocationRequest request = LocationRequest.create();
                    request.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
                    request.setNumUpdates(1);
                    request.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    } else {
                        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, request, listener);
                    }
                }
                break;

            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                LogHelper.i(TAG, "Location settings are not satisfied. Show the user a dialog to" + "upgrade location settings ");
                try {
                    if (getActivity() instanceof Activity && isAdded())
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        LogHelper.e("onActivityResult", resultCode + "");
        if (resultCode == getActivity().RESULT_OK && requestCode == REQUEST_CHECK_SETTINGS) {
            LogHelper.d("onActivityResult", resultCode + "");
            LocationRequest request = LocationRequest.create();
            request.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
            request.setNumUpdates(1);
            request.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, request, this);
        } else if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == getActivity().RESULT_OK) {

                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                String latlang = (place.getLatLng().latitude) + "," + (place.getLatLng().longitude);
                EventBus.getDefault().post(new SearchEvent(SearchEvent.ACTION_SEARCH, latlang));

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                // TODO: Handle the error.
                Log.e("status", status.getStatusMessage());

            } else if (resultCode == getActivity().RESULT_CANCELED) {
                // The user canceled the operation.
            }

        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            mLastLocation = latLng;
            mMap.clear();
            mLastLocation = new LatLng(location.getLatitude(), location.getLongitude());
            preference.saveLatestLocation(mLastLocation);
            MarkerOptions markerOptions = new MarkerOptions().position(mLastLocation).title("I am hire !");
            current = mMap.addMarker(markerOptions);
            current.setTag("current");
            CameraPosition cameraPosition = new CameraPosition.Builder().target(mLastLocation).zoom(zoomlevel).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//            actionGetNearestProperties(selectedradius,location.getLatitude(), location.getLongitude());

        }
    }

    private void actionGetNearestProperties(String radius, String lat, String lang) {
        progressBar.setVisibility(View.VISIBLE);
        RequestBody requestBody = new FormBody.Builder()
                .addEncoded("radius", radius)
                .addEncoded("latitude", lat)
                .addEncoded("longitude", lang).build();
        lastCall = MyHttpClientHelper.getInstance().enqueueCall(getActivity(), UrlConstant.API_GET_NEAREST_PROPERTIES_URL, requestBody, mNearestPropertyResponseHelper);
    }

    private void addMarkers(final GoogleMap gmap) {
        propertiesModels.clear();
//        propertiesModels = new ArrayList<>();
        if (m != null && m.size() > 0) {
            for (Marker marker : m) {
                marker.remove();
            }
        }

        if (!filterarray(Mainlist).isEmpty()) {
            propertiesModels = filterarray(Mainlist);
            Log.e("get property size", "" + propertiesModels.size());
            Log.e("get Mainproperty size", "" + Mainlist.size());
        } else {
            Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return;
        }


        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
//                    Log.e("pmlist", "" + propertiesModels);
                    m = new ArrayList<>();
                    if (gmap != null && propertiesModels.size() > 0) {
                        nearestPropertyAdapter = new NearestPropertyAdapter(getActivity(), propertiesModels);
                        //gmap.clear();
                        if (current == null) {
                            mLastLocation = new LatLng(gmap.getMyLocation().getLatitude(), gmap.getMyLocation().getLongitude());
                            MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).position(mLastLocation).title("I am hire !");
                            current = gmap.addMarker(markerOptions);
                            current.setTag("current");
                        }
                        //gmap.addMarker(markerOptions);

                        for (int i = 0; i < propertiesModels.size(); i++) {
                            Marker mar = null;
                            if (propertiesModels.get(i).getPropertyFor().equals("1")) {
                                mar = gmap.addMarker(new MarkerOptions()
                                        .position(new LatLng(Float.parseFloat(propertiesModels.get(i).getLatitude()), Float.parseFloat(propertiesModels.get(i).getLongitude())))
                                        .icon(iconorange_buy)
                                        .title(propertiesModels.get(i).getTitle()));
                            } else if (propertiesModels.get(i).getPropertyFor().equals("2")) {
                                mar = gmap.addMarker(new MarkerOptions()
                                        .position(new LatLng(Float.parseFloat(propertiesModels.get(i).getLatitude()), Float.parseFloat(propertiesModels.get(i).getLongitude())))
                                        .icon(iconbluecyan_rent)
                                        .title(propertiesModels.get(i).getTitle()));
                            } else if (propertiesModels.get(i).getPropertyFor().equals("0")) {
                                if (propertiesModels.get(i).getPropertyFor().equals("1")) {
                                    mar = gmap.addMarker(new MarkerOptions()
                                            .position(new LatLng(Float.parseFloat(propertiesModels.get(i).getLatitude()), Float.parseFloat(propertiesModels.get(i).getLongitude())))
                                            .icon(iconorange_buy)
                                            .title(propertiesModels.get(i).getTitle()));
                                } else if (propertiesModels.get(i).getPropertyFor().equals("2")) {
                                    mar = gmap.addMarker(new MarkerOptions()
                                            .position(new LatLng(Float.parseFloat(propertiesModels.get(i).getLatitude()), Float.parseFloat(propertiesModels.get(i).getLongitude())))
                                            .icon(iconbluecyan_rent)
                                            .title(propertiesModels.get(i).getTitle()));
                                }
                            } else {
                                propertiesModels.remove(i);
                            }
//                else if (propertiesModels.get(i).getPropertyFor().equals("0") && !f.isChkRent() && !f.isChkBuy()) {
//                    mar = gmap.addMarker(new MarkerOptions()
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
                        if (driveby) {
                            viewpager.setVisibility(View.VISIBLE);

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
                                        Log.e("plist", "" + pid);
                                        Log.e("dis", "" + list);
                                        int minIndex = list.indexOf(Collections.min(list));
                                        viewpager.setCurrentItem(minIndex);
                                        Log.e("dis", "" + minIndex);
                                        Log.e("getmindis", "" + list.get(minIndex));
                                        Log.e("getpid", "" + "" + pid.get(minIndex));

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void addMarkersonPolyline() {

        m = new ArrayList<>();
        propertiesModelsSelected = new ArrayList<>();
        if (mMap != null && propertiesModels.size() > 0) {
            for (int i = 0; i < propertiesModels.size(); i++) {
                LatLng latLng = new LatLng(Double.valueOf(propertiesModels.get(i).getLatitude()), Double.valueOf(propertiesModels.get(i).getLongitude()));
                if (PolyUtil.containsLocation(latLng, polygon.getPoints(), false)) {
                    Marker mar = null;
                    if (propertiesModels.get(i).getPropertyFor().equals("1") && f.isChkBuy()) {
                        mar = mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(Float.parseFloat(propertiesModels.get(i).getLatitude()), Float.parseFloat(propertiesModels.get(i).getLongitude())))
                                .icon(iconorange_buy)
                                .title(propertiesModels.get(i).getTitle()));
                    } else if (propertiesModels.get(i).getPropertyFor().equals("2") && f.isChkRent()) {
                        mar = mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(Float.parseFloat(propertiesModels.get(i).getLatitude()), Float.parseFloat(propertiesModels.get(i).getLongitude())))
                                .icon(iconbluecyan_rent)
                                .title(propertiesModels.get(i).getTitle()));
                    } else {
//                        propertiesModels.remove(i);
                        mar = mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(Float.parseFloat(propertiesModels.get(i).getLatitude()), Float.parseFloat(propertiesModels.get(i).getLongitude())))
                                .icon(iconRed)
                                .title(propertiesModels.get(i).getTitle()));
                    }

                    propertiesModelsSelected.add(propertiesModels.get(i));
                    mar.setTag(String.valueOf(propertiesModelsSelected.size() - 1));
                    m.add(mar);

                    nearestPropertyAdapter = new NearestPropertyAdapter(getActivity(), propertiesModelsSelected);
                    viewpager.setAdapter(nearestPropertyAdapter);
                }
            }

          /*  //Build camera position
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(Double.valueOf(propertiesModels.get(0).getLatitude()), Double.valueOf(propertiesModels.get(0).getLongitude())))
                    .zoom((float) lastdistance).build();
            //Zoom in and animate the camera.
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));*/
        }
    }

    //    public double getZoomForMetersWide(double desiredMeters, double latitude) {
//        final double latitudinalAdjustment = Math.cos(Math.PI * latitude / 180);
//        final double arg = EQUATOR_LENGTH * getScreenWidth() * latitudinalAdjustment / (desiredMeters * 256);
//        return Math.log(arg) / Math.log(2);
//    }
    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                LocationRequest request = LocationRequest.create();
                request.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
                request.setNumUpdates(1);
                request.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                initiateLocationExecution();
                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    mLastLocation = new LatLng(latitude, longitude);
                    preference.saveLatestLocation(mLastLocation);
                    actionGetNearestProperties(selectedradius, String.valueOf(latitude), String.valueOf(longitude));
                }
                mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                    @Override
                    public void onCameraIdle() {

                        VisibleRegion vr = mMap.getProjection().getVisibleRegion();
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
            } else {
                Toast.makeText(getActivity(), "You didn't give permission to access device location", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void drawMapView(Boolean flage) {

        if (flage) {
            footer.setVisibility(View.GONE);
            viewpager.setVisibility(View.GONE);
            draw_toolbar.setVisibility(View.VISIBLE);
            txt_clear.setVisibility(View.VISIBLE);
        } else {
            footer.setVisibility(View.VISIBLE);
            draw_toolbar.setVisibility(View.GONE);
            txt_clear.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (viewpager != null && !marker.getTag().equals("current")) {
            if (viewpager.getVisibility() == View.GONE)
                viewpager.setVisibility(View.VISIBLE);
            viewpager.setCurrentItem(Integer.valueOf(marker.getTag().toString()));
        }
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SearchEvent event) {
        if (event.getAction() == SearchEvent.ACTION_FILTER) {
//            Log.e("list", "" + propertiesModels);
            //showPreferencePopup(views);
            //startActivity(new Intent(getActivity(), PropertyFilterActivity.class));
            if (Mainlist.size() > 0) {
                PropertyFilterDialogFragmnet.newInstance(Mainlist, mLastLocation, String.valueOf(dis)).show(getActivity().getSupportFragmentManager(), "SHARE-VOICE");
            } else {
                Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
            }

        } else if (event.getAction() == SearchEvent.ACTION_SEARCH) {
            if (m != null && m.size() > 0) {
                for (Marker marker : m) {
                    marker.remove();
                }
                m.clear();
            }

            if (current != null) {
                current.remove();
            }
            String[] eve = event.getData().split(",");
            double lat = Double.parseDouble(eve[0]);
            double lng = Double.parseDouble(eve[1]);
            targetLocation = new Location("");//provider name is unnecessary
            targetLocation.setLatitude(lat);//your coords of course
            targetLocation.setLongitude(lng);
            mLastLocation = new LatLng(targetLocation.getLatitude(), targetLocation.getLongitude());
//            preference.saveLatestLocation(new LatLng(targetLocation.getLatitude(), targetLocation.getLongitude()));
            MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).position(mLastLocation);
            current = mMap.addMarker(markerOptions);
            current.setTag("current");
            CameraPosition cameraPosition = new CameraPosition.Builder().target(mLastLocation).zoom(zoomlevel).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        } else if (event.getAction() == SearchEvent.ACTION_FILTER_RESULT) {
            addMarkers(mMap);
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


    public ArrayList<NearestPropertiesModel> filterarray(List<NearestPropertiesModel> listn) {
        ArrayList<NearestPropertiesModel> filterlist = new ArrayList<>();

        Gson gson = new Gson();
        String json = preference.getFilter();
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

    //    public class GooglePlacesReadTask extends AsyncTask<Object, Integer, String> {
//        String googlePlacesData = null;
//        GoogleMap googleMap;
//
//        @Override
//        protected String doInBackground(Object... inputObj) {
//            try {
//                googleMap = (GoogleMap) inputObj[0];
//                String googlePlacesUrl = (String) inputObj[1];
//                Http http = new Http();
//                googlePlacesData = http.read(googlePlacesUrl);
//            } catch (Exception e) {
//                Log.d("Google Place Read Task", e.toString());
//            }
//            return googlePlacesData;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            Log.e("result", "" + result);
//// HHARS
//            //rash
//            JSONObject jsonObject = null;
//            try {
//                jsonObject = new JSONObject(result);
//                JSONArray jsonArray = jsonObject.getJSONArray("results");
//                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
//                String placeid = jsonObject1.getString("place_id");
//                searchandgo(placeid);
//                Log.e("placeid", "" + placeid);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            Log.e("result", result);
//
//        }
//    }
    public void Movecameraonlatlang(LatLng latLng) {
        if (current != null) {
            current.remove();
        }
        mLastLocation = new LatLng(latLng.latitude, latLng.longitude);
        MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).position(mLastLocation);
        current = mMap.addMarker(markerOptions);
        current.setTag("current");
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(mLastLocation, zoomlevel);
        mMap.animateCamera(cameraUpdate);
    }

    public void searchandgo(String place_id) {
        buildGoogleApiClientforsearch();
        Places.GeoDataApi.getPlaceById(mGoogleApiClient, place_id)
                .setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(PlaceBuffer places) {
                        if (places.getStatus().isSuccess()) {
                            final Place myPlace = places.get(0);
                            LatLng queriedLocation = myPlace.getLatLng();
                            Movecameraonlatlang(queriedLocation);
                        }
                        places.release();
                    }
                });
    }


}


/*    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == getActivity().RESULT_OK) {

                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                String latlang = (place.getLatLng().latitude) + "," + (place.getLatLng().longitude);
                EventBus.getDefault().post(new SearchEvent(SearchEvent.ACTION_SEARCH, latlang));

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                // TODO: Handle the error.
                Log.e("status", status.getStatusMessage());

            } else if (resultCode == getActivity().RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }*/



