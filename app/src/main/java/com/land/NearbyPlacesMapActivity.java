package com.land;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.land.adapter.AdapterUtil;
import com.land.custom.RobotoTextView;
import com.land.fragment.MySupportMapFragment;
import com.land.helper.LogHelper;
import com.land.helper.TintDrawableHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NearbyPlacesMapActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<LocationSettingsResult>, LocationListener, GoogleMap.OnMarkerClickListener {

    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 5 * 1000;  //min*sec*millisecond
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    private final static String TAG = NearbyPlacesMapActivity.class.getSimpleName();
    private static final int REQUEST_PERMISSION = 2001;
    private static ImageView lastChecked = null;
    private static TextView lastCheckedtv = null;
    public ArrayList<MapNearbyplacesModel> mapNearbyplacesModels;
    protected LocationRequest mLocationRequest;
    protected LocationSettingsRequest mLocationSettingsRequest;
    String strLatitude;
    String strLongitude;
    GoogleMap mMap;
    Context context;
    BitmapDescriptor bitmap;
    RecyclerView recyclerView;
    Toolbar toolbar;
    int[] imagearray = {R.drawable.icn_airport, R.drawable.icn_restaurants, R.drawable.icn_hospitals,
            R.drawable.icn_shopping, R.drawable.icn_bus, R.drawable.icn_train,
            R.drawable.icn_schools, R.drawable.icn_atm, R.drawable.icn_park,
            R.drawable.icn_gas, R.drawable.icn_worship, R.drawable.icn_grocery, R.drawable.icn_bank};
    int[] imagedrawable = {R.drawable.selector_airport, R.drawable.selector_restaurent, R.drawable.selector_hospital, R.drawable.selector_shopping,
            R.drawable.selector_bus, R.drawable.selector_train, R.drawable.selector_school, R.drawable.selector_atm,
            R.drawable.selector_park, R.drawable.selector_gas, R.drawable.selector_worship, R.drawable.selector_grocery, R.drawable.selector_bank};
    String[] mapplaces = {"Airport", "Restaurants", "Hospitals", "Shopping", "Bus", "Train", "Schools", "Atm", "Park", "Gas station", "Worship", "Grocery", "Bank"};
    RobotoTextView tvtitle;
    String placescategory;
    FloatingActionButton fbtn_list;
    List<Marker> m = new ArrayList<>();
    private String drlast = "";
    private GoogleApiClient mGoogleApiClient;
    private LatLng mLastLocation;
    private LinearLayout lllist;
    private TextView tvheadername;
    private TextView tvheaderdistance;
    private ListView list;
    private ImageView ivdownlist;
    private ImageView ivheaderlogo;
    private int PROXIMITY_RADIUS = 5000;

    private void findViews() {
        tvtitle = (RobotoTextView) findViewById(R.id.tvtoolbartitle);
        tvtitle.setText("Nearest places");
        lllist = (LinearLayout) findViewById(R.id.lllist);
        lllist.setVisibility(View.GONE);
        tvheadername = (TextView) findViewById(R.id.tvname);
        tvheaderdistance = (TextView) findViewById(R.id.tvdistance);
        ivdownlist = (ImageView) findViewById(R.id.ivdownlist);
        ivheaderlogo = (ImageView) findViewById(R.id.ivlogoplace);
        list = (ListView) findViewById(R.id.list);
        fbtn_list = (FloatingActionButton) findViewById(R.id.fbtn_list);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        findViews();
        toolbar = (Toolbar) findViewById(R.id.maintoolbar);
        toolbar.setNavigationIcon(TintDrawableHelper.getTintedResource(this, android.support.design.R.drawable.abc_ic_ab_back_material, R.color.colorWhite));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lllist.getVisibility() == View.VISIBLE) {
                    lllist.setVisibility(View.GONE);
                } else {
                    finish();
                }

            }
        });
        ivdownlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lllist.setVisibility(View.GONE);
            }
        });
        fbtn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lllist.getVisibility() == View.GONE) {
                    lllist.setVisibility(View.VISIBLE);
                    fbtn_list.setImageResource(R.drawable.icn_list_close);
                } else {
                    fbtn_list.setImageResource(R.drawable.icn_list);
                    lllist.setVisibility(View.GONE);
                }

            }
        });
        context = NearbyPlacesMapActivity.this;
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        rvAdapter rvAdapter = new rvAdapter(NearbyPlacesMapActivity.this);
        recyclerView.setAdapter(rvAdapter);
        Intent i = getIntent();
        try {
            String[] latlang = i.getStringExtra("Latlang").split(",");
            strLatitude = latlang[0];
            strLongitude = latlang[1];
        } catch (Exception e) {

        }
        final MySupportMapFragment customMapFragment = ((MySupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
        customMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.style_json));
                mMap.setOnMarkerClickListener(NearbyPlacesMapActivity.this);
                if (ActivityCompat.checkSelfPermission(NearbyPlacesMapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(NearbyPlacesMapActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION);
                    }
                    return;
                }

                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                initiateLocationExecution();
                mLastLocation = new LatLng(Double.parseDouble(strLatitude), Double.parseDouble(strLongitude));
                MarkerOptions markerOptions = new MarkerOptions().position(mLastLocation);
                mMap.addMarker(markerOptions);
                CameraPosition cameraPosition = new CameraPosition.Builder().target(mLastLocation).zoom(16).build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//                }
            }
        });
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

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            mLastLocation = latLng;
            mMap.clear();
            mLastLocation = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions().position(mLastLocation).title("I am hire !");
            mMap.addMarker(markerOptions);
            CameraPosition cameraPosition = new CameraPosition.Builder().target(mLastLocation).zoom(16).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//            actionGetNearestProperties();
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    public class rvAdapter extends RecyclerView.Adapter<rvAdapter.MapHolder> {

        private LayoutInflater mInflater;
        private Context context;

        // AgentModel agentModel = new AgentModel();


        public rvAdapter(Context context) {
            this.context = context;
            mInflater = android.view.LayoutInflater.from(context);

        }


        @Override
        public MapHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MapHolder(mInflater.inflate(R.layout.item_mapview, parent, false));
//
        }


        @Override
        public void onBindViewHolder(final MapHolder holder, final int position) {

            if (holder.getItemViewType() == AdapterUtil.VIEW_EMPTY) {
                return;
            } else {
                holder.ivNearbyplaces.setImageResource(imagearray[position]);
                holder.tvmapview.setText(mapplaces[position]);
                holder.itemView.setTag(mapplaces[position]);
                holder.ivNearbyplaces.setImageDrawable(getResources().getDrawable(imagedrawable[position]));
                if (drlast.equals(mapplaces[position])) {
                    holder.tvmapview.setTextColor(getResources().getColor(R.color.colorPrimary));
                    holder.ivNearbyplaces.setSelected(true);
                } else {
                    holder.tvmapview.setTextColor(getResources().getColor(R.color.colorWhite));
                    holder.ivNearbyplaces.setSelected(false);
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fbtn_list.setImageResource(R.drawable.icn_list);
                        lllist.setVisibility(View.GONE);
                        drlast = (String) v.getTag();
                        if (lastChecked != null) {
                            lastChecked.setSelected(false);
                            lastCheckedtv.setTextColor(getResources().getColor(R.color.colorWhite));
                        }

                        int pos = position;
                        String type = null;
                        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
                        googlePlacesUrl.append("location=" + strLatitude + "," + strLongitude);
                        googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);

                        if (pos == 0 || pos == 7) {
                            bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icn_pina);
                            if (pos == 0) {
                                tvtitle.setText("AIRPORT");
                                ivheaderlogo.setImageResource(R.drawable.icnlst_airport);
                                type = "airport";
                            } else {
                                tvtitle.setText("ATM");
                                ivheaderlogo.setImageResource(R.drawable.icnlst_atm);
                                type = "atm";
                            }
                        } else if (pos == 1) {
                            bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icn_pinr);
                            tvtitle.setText("RESTAURANT");
                            ivheaderlogo.setImageResource(R.drawable.icnlst_restaurants);
                            type = "restaurant";
                        } else if (pos == 2) {
                            bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icn_pinh);
                            tvtitle.setText("HOSPITAL");
                            ivheaderlogo.setImageResource(R.drawable.icnlst_hospitals);
                            type = "hospital";
                        } else if (pos == 3) {
                            bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icn_pins);
                            tvtitle.setText("SHOPPING MALL");
                            ivheaderlogo.setImageResource(R.drawable.icnlst_shopping);
                            type = "shopping_mall";
                        } else if (pos == 4) {
                            bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icn_pinb);
                            tvtitle.setText("BUS STATION");
                            ivheaderlogo.setImageResource(R.drawable.icnlst_bus);
                            type = "bus_station";
                        } else if (pos == 5) {
                            bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icn_pint);
                            tvtitle.setText("TRAIN STATION");
                            ivheaderlogo.setImageResource(R.drawable.icnlst_train);
                            type = "train_station";
                        } else if (pos == 6) {
                            bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icn_pins);
                            tvtitle.setText("SCHOOL");
                            ivheaderlogo.setImageResource(R.drawable.icnlst_schools);
                            type = "school";
                        } else if (pos == 8) {
                            bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icn_pinp);
                            tvtitle.setText("PARK");
                            ivheaderlogo.setImageResource(R.drawable.icnlst_park);
                            type = "park";
                        } else if (pos == 9) {
                            bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icn_ping);
                            tvtitle.setText("GAS STATION");
                            ivheaderlogo.setImageResource(R.drawable.icnlst_gas);
                            type = "gas_station";
                        } else if (pos == 10) {
                            bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icn_pinw);
                            tvtitle.setText("WORSHIP");
                            ivheaderlogo.setImageResource(R.drawable.icnlst_worship);
                            type = "place_of_worship";
                        } else if (pos == 11) {
                            bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icn_ping);
                            tvtitle.setText("GROCERY");
                            ivheaderlogo.setImageResource(R.drawable.icnlst_grocery);
                            type = "grocery_or_supermarket";
                        } else if (pos == 12) {
                            bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icn_pinb);
                            tvtitle.setText("BANK");
                            ivheaderlogo.setImageResource(R.drawable.icnlst_bank);
                            type = "bank";
                        }
                        placescategory = tvheadername.getText().toString();
                        holder.tvmapview.setTextColor(getResources().getColor(R.color.colorPrimary));
                        holder.ivNearbyplaces.setSelected(true);
                        lastChecked = holder.ivNearbyplaces;
                        lastCheckedtv = holder.tvmapview;

                        googlePlacesUrl.append("&types=" + type);
                        googlePlacesUrl.append("&sensor=true");
                        googlePlacesUrl.append("&key=AIzaSyA-H1AKwdFYDFZl91-qzieFJH16t3gHmrA");
                        GooglePlacesReadTask googlePlacesReadTask = new GooglePlacesReadTask();
                        Object[] toPass = new Object[2];
                        toPass[0] = mMap;
                        toPass[1] = googlePlacesUrl.toString();
                        googlePlacesReadTask.execute(toPass);

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
//                                if (mapNearbyplacesModels != null) {
//                                    if (!mapNearbyplacesModels.isEmpty()) {
                                list_Adapter list_adapter = new list_Adapter(NearbyPlacesMapActivity.this, mapNearbyplacesModels);
                                list.setAdapter(list_adapter);
//                                    }
//                                } else {
//                                    mapNearbyplacesModels.clear();
//                                    list_Adapter list_adapter = new list_Adapter(NearbyPlacesMapActivity.this, mapNearbyplacesModels);
//                                    list.setAdapter(list_adapter);
//                                }
                            }
                        }, 1000);
                    }
                });
            }


        }


        @Override
        public int getItemViewType(int position) {

            return super.getItemViewType(position);

        }

        @Override
        public int getItemCount() {
            return imagearray.length;
        }


        public class MapHolder extends RecyclerView.ViewHolder {
            private View itemView;
            private ImageView ivNearbyplaces;
            private TextView tvmapview;


            public MapHolder(View itemView) {
                super(itemView);
                this.itemView = itemView;
                tvmapview = (TextView) itemView.findViewById(R.id.tvmapitem);
                ivNearbyplaces = (ImageView) itemView.findViewById(R.id.ivmapitem);

            }
        }
    }

    public class GooglePlacesReadTask extends AsyncTask<Object, Integer, String> {
        String googlePlacesData = null;
        GoogleMap googleMap;

        @Override
        protected String doInBackground(Object... inputObj) {
            try {
                googleMap = (GoogleMap) inputObj[0];
                String googlePlacesUrl = (String) inputObj[1];
                Http http = new Http();
                googlePlacesData = http.read(googlePlacesUrl);
            } catch (Exception e) {
                Log.d("Google Place Read Task", e.toString());
            }
            return googlePlacesData;
        }

        @Override
        protected void onPostExecute(String result) {
            PlacesDisplayTask placesDisplayTask = new PlacesDisplayTask();
            Object[] toPass = new Object[2];
            toPass[0] = googleMap;
            toPass[1] = result;
            placesDisplayTask.execute(toPass);
        }
    }

    public class PlacesDisplayTask extends AsyncTask<Object, Integer, List<HashMap<String, String>>> {

        JSONObject googlePlacesJson;
        GoogleMap googleMap;

        @Override
        protected List<HashMap<String, String>> doInBackground(Object... inputObj) {

            List<HashMap<String, String>> googlePlacesList = null;
            Places placeJsonParser = new Places();

            try {
                googleMap = (GoogleMap) inputObj[0];
                googlePlacesJson = new JSONObject((String) inputObj[1]);
                googlePlacesList = placeJsonParser.parse(googlePlacesJson);
            } catch (Exception e) {
                Log.d("Exception", e.toString());
            }
            return googlePlacesList;
        }


        @Override
        protected void onPostExecute(List<HashMap<String, String>> list) {
            m = new ArrayList<>();
            googleMap.clear();
            double mylat = Double.parseDouble(strLatitude);
            double mylng = Double.parseDouble(strLongitude);
            mapNearbyplacesModels = new ArrayList<>();
            LatLng mylatLng = new LatLng(mylat, mylng);
            MarkerOptions markerOptions1 = new MarkerOptions();
            markerOptions1.position(mylatLng);
            googleMap.addMarker(markerOptions1);
            for (int i = 0; i < list.size(); i++) {

                Marker mar;
                MarkerOptions markerOptions = new MarkerOptions();
                HashMap<String, String> googlePlace = list.get(i);
                double lat = Double.parseDouble(googlePlace.get("lat"));
                double lng = Double.parseDouble(googlePlace.get("lng"));
                String placeName = googlePlace.get("place_name");
                double distance = distance(mylat, mylng, lat, lng);
                MapNearbyplacesModel objmapNearbyplacesModel = new MapNearbyplacesModel();
                objmapNearbyplacesModel.setName(placeName);
                objmapNearbyplacesModel.setLat(lat);
                objmapNearbyplacesModel.setLang(lng);
                float fdistance = (float) distance;

                objmapNearbyplacesModel.setDistance(String.format("%.2f", fdistance));
                mapNearbyplacesModels.add(objmapNearbyplacesModel);
                String vicinity = googlePlace.get("vicinity");
                LatLng latLng = new LatLng(lat, lng);
//                MarkerOptions markerOptions = new MarkerOptions().position(mLastLocation).icon(iconBlue).title("I am hire !");
//                MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(placeName + " : " + vicinity).icon( bitmapDescriptorFromVector(context, R.drawable.ic_local_airport_black_24dp));
                markerOptions.position(latLng);
                markerOptions.title(placeName + " : " + vicinity);
                markerOptions.icon(bitmap);
                mar = googleMap.addMarker(markerOptions);
                mar.setTag(String.valueOf(i));
                m.add(mar);
            }
            if (!m.isEmpty()) {
                LatLngBounds.Builder builder = new LatLngBounds.Builder();

                for (Marker marker : m) {
                    builder.include(marker.getPosition());
                }
                LatLngBounds bounds = builder.build();
                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 200));
            }


        }

        private double distance(double lat1, double lon1, double lat2, double lon2) {
            double theta = lon1 - lon2;
            double dist = Math.sin(deg2rad(lat1))
                    * Math.sin(deg2rad(lat2))
                    + Math.cos(deg2rad(lat1))
                    * Math.cos(deg2rad(lat2))
                    * Math.cos(deg2rad(theta));
            dist = Math.acos(dist);
            dist = rad2deg(dist);
            dist = dist * 60 * 1.1515;
            return (dist);
        }

        private double deg2rad(double deg) {
            return (deg * Math.PI / 180.0);
        }

        private double rad2deg(double rad) {
            return (rad * 180.0 / Math.PI);
        }
    }

    public class Places {

        public List<HashMap<String, String>> parse(JSONObject jsonObject) {
            JSONArray jsonArray = null;
            try {
                jsonArray = jsonObject.getJSONArray("results");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return getPlaces(jsonArray);
        }

        private List<HashMap<String, String>> getPlaces(JSONArray jsonArray) {
            int placesCount = jsonArray.length();
            List<HashMap<String, String>> placesList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> placeMap = null;

            for (int i = 0; i < placesCount; i++) {
                try {
                    placeMap = getPlace((JSONObject) jsonArray.get(i));
                    placesList.add(placeMap);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return placesList;
        }

        private HashMap<String, String> getPlace(JSONObject googlePlaceJson) {
            HashMap<String, String> googlePlaceMap = new HashMap<String, String>();
            String placeName = "-NA-";
            String vicinity = "-NA-";
            String latitude = "";
            String longitude = "";
            String reference = "";

            try {
                if (!googlePlaceJson.isNull("name")) {
                    placeName = googlePlaceJson.getString("name");
                }
                if (!googlePlaceJson.isNull("vicinity")) {
                    vicinity = googlePlaceJson.getString("vicinity");
                }
                latitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
                longitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");
                reference = googlePlaceJson.getString("reference");
                googlePlaceMap.put("place_name", placeName);
                googlePlaceMap.put("vicinity", vicinity);
                googlePlaceMap.put("lat", latitude);
                googlePlaceMap.put("lng", longitude);
                googlePlaceMap.put("reference", reference);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return googlePlaceMap;
        }
    }

    public class Http {

        public String read(String httpUrl) throws IOException {
            String httpData = "";
            InputStream inputStream = null;
            HttpURLConnection httpURLConnection = null;
            try {
                URL url = new URL(httpUrl);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }
                httpData = stringBuffer.toString();
                bufferedReader.close();
            } catch (Exception e) {
                Log.d("Exception - reading Http url", e.toString());
            } finally {
                inputStream.close();
                httpURLConnection.disconnect();
            }
            return httpData;
        }
    }

    public class list_Adapter extends BaseAdapter {
        ArrayList<MapNearbyplacesModel> places = new ArrayList<>();
        Context co;
        private TextView tvname;
        private TextView tvdistance;
        private LinearLayout llmapitem;

        public list_Adapter(NearbyPlacesMapActivity nearbyPlacesMap_Activity, ArrayList<MapNearbyplacesModel> mapNearbyplacesModels) {
            this.places = mapNearbyplacesModels;
            co = nearbyPlacesMap_Activity;
        }

        @Override
        public int getCount() {
            if (places != null) {
                if (places.size() > 0) {
                    return places.size();
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {
            return places.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            LayoutInflater inflater = (LayoutInflater) co.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.items_mapnearbyitems, parent, false);
            tvname = (TextView) v.findViewById(R.id.tvnameitem);
            tvdistance = (TextView) v.findViewById(R.id.tvdistanceitem);
            llmapitem = (LinearLayout) v.findViewById(R.id.llmapitem);
            final MapNearbyplacesModel m = places.get(position);
            tvname.setText(m.getName());
            tvdistance.setText(m.getDistance());
            tvname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fbtn_list.setImageResource(R.drawable.icn_list);
                    lllist.setVisibility(View.GONE);
                    mLastLocation = new LatLng(m.getLat(), m.getLang());
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(mLastLocation).zoom(16).build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
            });
//            if (position % 2 == 0) {
//                llmapitem.setBackgroundColor(getResources().getColor(R.color.offwhite));
//            }

            return v;
        }
    }

    public class MapNearbyplacesModel {
        String name;
        double lat;
        double lang;
        String distance;

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLang() {
            return lang;
        }

        public void setLang(double lang) {
            this.lang = lang;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

    }
}
