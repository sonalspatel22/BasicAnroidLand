package com.land;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.land.adapter.PlacesAutoCompleteAdapter;
import com.land.api.model.SearchPlaceModel;
import com.land.api.model.UserModel;
import com.land.constant.ApiConstant;
import com.land.constant.AppConstant;
import com.land.constant.UrlConstant;
import com.land.custom.RobotoRadioButton;
import com.land.custom.RobotoTextView;
import com.land.events.SearchEvent;
import com.land.fragment.AjentsFragment;
import com.land.fragment.ListingFragment;
import com.land.fragment.MyMatchesFragment;
import com.land.fragment.NavigationDrawerFragment;
import com.land.fragment.NearByFragment;
import com.land.fragment.SuppliersFragment;
import com.land.helper.LogHelper;
import com.land.helper.MyHttpClientHelper;
import com.land.utils.MyPreference;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class MainActivity extends BaseActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks, RadioButton.OnCheckedChangeListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    //int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private final static String TAG = MainActivity.class.getSimpleName();
    public static String searchlocation;
    public static ImageView ivfilter;
    public EditText txtSearch;
    RobotoRadioButton radio_dashboard_post;
    RobotoRadioButton radio_dashboard_agent;
    RobotoRadioButton radio_dashboard_near_by;
    RobotoRadioButton radio_dashboard_matches;
    RobotoRadioButton radio_dashboard_supplier;
    RadioGroup dashbord_tab;
    FrameLayout layout_title;
    RobotoTextView txt_title;
    MenuItem item_search;
    Toolbar searchtollbar;
    Menu search_menu;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 81;
    MyHttpClientHelper.ProcessResponseHelper mLoginResponseHelper = new MyHttpClientHelper.ProcessResponseHelper() {
        @Override
        public void onRequest() {
//
//            mHandler.post(new Runnable() {
//                @Override
//                public void run() {
//                    loadingDialog.show();
//                }
//            });

        }

        @Override
        public void onFinish() {

//            mHandler.post(new Runnable() {
//                @Override
//                public void run() {
//                    loadingDialog.dismiss();
//                }
//            });
        }

        @Override
        public void onFail() throws Exception {


        }

        @Override
        public void onResponse(JSONObject jsonObject) {
            try {
                int code = jsonObject.getInt(ApiConstant.JSON_KEY_CODE);
                final String message = jsonObject.getString(ApiConstant.JSON_KEY_MSG) + "";
                if (code == 1) {
                    MyPreference preference = new MyPreference(getApplicationContext());
                    EventBus.getDefault().post(new SearchEvent(SearchEvent.ACTION_USER, "false"));
                    AppConstant.isuser = false;
                    preference.clearuser();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    private Call lastCall;
    private NearByFragment nearByFragment;
    private ListingFragment listingFragment;
    private AjentsFragment ajentsFragment;
    private MyMatchesFragment myMatchesFragment;
    private SuppliersFragment suppliersFragment;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;
    private ImageView imageView;
    private GoogleApiClient mGoogleApiClient;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSearch();
        init();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, mDrawerLayout);
//        txtSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//
//            }
//        });
        if (AppConstant.isuser) {
            NavigationDrawerFragment.navSigninregister.setText("Sign Out");
        } else {
            NavigationDrawerFragment.navSigninregister.setText("SignIn/Register");
        }

    }

    private void initSearch() {
        searchtollbar = (Toolbar) findViewById(R.id.search_toolbar);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        setSearchtollbar();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, mDrawerLayout);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

                View mainView = findViewById(R.id.main_content);
                mainView.setTranslationX(slideOffset * drawerView.getWidth());
                mDrawerLayout.bringChildToFront(drawerView);
                mDrawerLayout.requestLayout();
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();
    }

    private void init() {

        txt_title = (RobotoTextView) findViewById(R.id.txt_title);
        radio_dashboard_post = (RobotoRadioButton) findViewById(R.id.radio_dashboard_listing);
        radio_dashboard_agent = (RobotoRadioButton) findViewById(R.id.radio_dashboard_agent);
        radio_dashboard_near_by = (RobotoRadioButton) findViewById(R.id.radio_dashboard_near_by);
        radio_dashboard_matches = (RobotoRadioButton) findViewById(R.id.radio_dashboard_matches);
        radio_dashboard_supplier = (RobotoRadioButton) findViewById(R.id.radio_dashboard_supplier);

        dashbord_tab = (RadioGroup) findViewById(R.id.dashbord_tab);
        layout_title = (FrameLayout) findViewById(R.id.layout_title);

        radio_dashboard_post.setOnCheckedChangeListener(this);
        radio_dashboard_agent.setOnCheckedChangeListener(this);
        radio_dashboard_near_by.setOnCheckedChangeListener(this);
        radio_dashboard_matches.setOnCheckedChangeListener(this);
        radio_dashboard_supplier.setOnCheckedChangeListener(this);
//        ivfilter = (ImageView) findViewById(R.id.ivfilter);
        prepareUi();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        MenuItem item1 = menu.findItem(R.id.action_filter);
        MenuItem item2 = menu.findItem(R.id.action_curr);
        if (UrlConstant.fragmenttag == 1) {
            item.setVisible(true);
            item1.setVisible(true);
            item2.setVisible(true);
        } else if (UrlConstant.fragmenttag == 3) {
            item1.setVisible(true);
            item.setVisible(false);
            item2.setVisible(false);
        } else if (UrlConstant.fragmenttag == 4) {
            item1.setVisible(false);
            item.setVisible(false);
            item2.setVisible(false);
        } else {
            item1.setVisible(false);
            item.setVisible(true);
            item2.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.action_search:
                if (UrlConstant.fragmenttag == 1 || UrlConstant.fragmenttag == 2 || UrlConstant.fragmenttag == 5) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        circleReveal(R.id.search_toolbar, 1, true, true);
                    else
                        searchtollbar.setVisibility(View.VISIBLE);
                    item_search.expandActionView();

                }
                break;
            case R.id.action_filter:
                searchtollbar.setVisibility(View.GONE);
                EventBus.getDefault().post(new SearchEvent(SearchEvent.ACTION_FILTER, ""));
                break;
            case R.id.action_curr:
                searchtollbar.setVisibility(View.GONE);
                EventBus.getDefault().post(new SearchEvent(SearchEvent.ACTION_CURR, ""));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (searchtollbar.getVisibility() == View.VISIBLE) {
            searchtollbar.setVisibility(View.GONE);
        }
        switch (buttonView.getId()) {

            case R.id.radio_dashboard_listing:

                if (isChecked) {
                    selecteditem(0);
                }
                break;

            case R.id.radio_dashboard_agent:
                if (isChecked) {
                    selecteditem(1);
                }
                break;

            case R.id.radio_dashboard_near_by:
                if (isChecked) {

                    selecteditem(2);
                }
                break;

            case R.id.radio_dashboard_matches:
                if (isChecked) {

                    selecteditem(3);
                }
                break;

            case R.id.radio_dashboard_supplier:
                if (isChecked) {
                    selecteditem(4);
                }
                break;
        }
    }

    private void prepareUi() {
        try {
            if (nearByFragment == null) {
                nearByFragment = new NearByFragment();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container, nearByFragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSearchtollbar() {
        searchtollbar = (Toolbar) findViewById(R.id.search_toolbar);
        if (searchtollbar != null) {
            searchtollbar.inflateMenu(R.menu.menu_search);
            search_menu = searchtollbar.getMenu();

            searchtollbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(MainActivity.this, "back", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        circleReveal(R.id.search_toolbar, 1, true, false);
                    else
                        searchtollbar.setVisibility(View.GONE);

//                    EventBus.getDefault().post(new SearchEvent(SearchEvent.ACTION_SEARCH, searchlocation));
                }
            });

            item_search = search_menu.findItem(R.id.action_filter_search);

            MenuItemCompat.setOnActionExpandListener(item_search, new MenuItemCompat.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionCollapse(MenuItem item) {
                    // Do something when collapsed
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        circleReveal(R.id.search_toolbar, 1, true, false);
                    } else
                        searchtollbar.setVisibility(View.GONE);
                    EventBus.getDefault().post(new SearchEvent(SearchEvent.ACTION_BACKPRESSED, ""));
                    return true;
                }

                @Override
                public boolean onMenuItemActionExpand(MenuItem item) {
                    // Do something when expanded
                    return true;
                }
            });
            initSearchView();
        }
    }

    public void initSearchView() {

        final SearchView searchView = (SearchView) search_menu.findItem(R.id.action_filter_search).getActionView();
//        searchView.setIconified(false);
//        searchView.setIconifiedByDefault(false);

        /* Code for changing the textcolor and hint color for the search view */
        final SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setHintTextColor(getResources().getColor(R.color.colorWhite));
        searchAutoComplete.setTextColor(getResources().getColor(R.color.colorWhite));
        searchAutoComplete.setAdapter(new PlacesAutoCompleteAdapter(MainActivity.this, R.layout.autocomplete_list_item));
        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager inputManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                SearchPlaceModel searchPlaceModel = AppConstant.searchPlaceModel.get(position);
                searchAutoComplete.setText(searchPlaceModel.getDescription());
                buildGoogleApiClient();

                Places.GeoDataApi.getPlaceById(mGoogleApiClient, searchPlaceModel.getId())
                        .setResultCallback(new ResultCallback<PlaceBuffer>() {
                            @Override
                            public void onResult(PlaceBuffer places) {
                                if (places.getStatus().isSuccess()) {
                                    final Place myPlace = places.get(0);
                                    LatLng queriedLocation = myPlace.getLatLng();
                                    Log.v("Latitude is", "" + queriedLocation.latitude);
                                    Log.v("Longitude is", "" + queriedLocation.longitude);
                                    String latlang = (myPlace.getLatLng().latitude) + "," + (myPlace.getLatLng().longitude);
                                    EventBus.getDefault().post(new SearchEvent(SearchEvent.ACTION_SEARCH, latlang));
                                }
                                places.release();
                            }
                        });
            }
        });

        // Enable/Disable Submit button in the keyboard
//        searchView.setSubmitButtonEnabled(false);
        // Change search close button image
        ImageView closeButton = (ImageView) searchView.findViewById(R.id.search_close_btn);
        //closeButton.setImageResource(R.drawable.ic_close);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = (EditText) findViewById(R.id.search_src_text);
                et.setText("");
                searchView.setQuery("", false);
            }
        });

        // set hint and the text colors
//        txtSearch = ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));
//        txtSearch.setHint("Search..");
//        txtSearch.setHintTextColor(Color.DKGRAY);
//        txtSearch.setTextColor(getResources().getColor(R.color.colorPrimary));

//        txtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    // Your piece of code on keyboard search click
//                    searchlocation = txtSearch.getText().toString();
//                    EventBus.getDefault().post(new SearchEvent(SearchEvent.ACTION_SEARCH, searchlocation));
//                    return true;
//                }
//                return false;
//            }
//        });

        // set the cursor
//        AutoCompleteTextView searchTextView = (AutoCompleteTextView) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        try {
            Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            mCursorDrawableRes.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                callSearch(query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (searchtollbar.getVisibility() == View.VISIBLE)
                    callSearch(newText);
                return false;
            }

            public void callSearch(String query) {
//                EventBus.getDefault().post(new SearchEvent(SearchEvent.ACTION_SEARCH, query));
            }
        });
    }

    public void closeSearchView() {
        try {
            SearchView searchView = (SearchView) search_menu.findItem(R.id.action_filter_search).getActionView();
            searchView.setQuery("", false);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                circleReveal(R.id.search_toolbar, 1, true, false);
            else
                searchtollbar.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void circleReveal(int viewID, int posFromRight, boolean containsOverflow, final boolean isShow) {
        final View myView = findViewById(viewID);
        int width = myView.getWidth();

        if (posFromRight > 0)
            width -= (posFromRight * getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material)) - (getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) / 2);
        if (containsOverflow)
            width -= getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material);

        int cx = width;
        int cy = myView.getHeight() / 2;

        Animator anim;
        if (isShow)
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, (float) width);
        else
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, (float) width, 0);

        anim.setDuration((long) 220);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (!isShow) {
                    super.onAnimationEnd(animation);
                    myView.setVisibility(View.INVISIBLE);
                }
            }
        });
        // make the view visible and start the animation
        if (isShow)
            myView.setVisibility(View.VISIBLE);
        // start the animation
        anim.start();
    }

    public void selecteditem(int position) {
        switch (position) {
            case 0:
                radio_dashboard_post.setChecked(true);
                if (listingFragment == null) {
                    listingFragment = new ListingFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container, listingFragment).commit();
                radio_dashboard_agent.setChecked(false);
                radio_dashboard_near_by.setChecked(false);
                radio_dashboard_matches.setChecked(false);
                radio_dashboard_supplier.setChecked(false);
                txt_title.setText("Listing");

                break;
            case 1:
                radio_dashboard_agent.setChecked(true);
                if (ajentsFragment == null) {
                    ajentsFragment = new AjentsFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container, ajentsFragment).commit();

                radio_dashboard_post.setChecked(false);
                radio_dashboard_near_by.setChecked(false);
                radio_dashboard_matches.setChecked(false);
                radio_dashboard_supplier.setChecked(false);
                txt_title.setText("Agents");
                break;
            case 2:
                radio_dashboard_near_by.setChecked(true);
                if (nearByFragment == null) {
                    nearByFragment = new NearByFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container, nearByFragment).commit();
                radio_dashboard_post.setChecked(false);
                radio_dashboard_agent.setChecked(false);
                radio_dashboard_matches.setChecked(false);
                radio_dashboard_supplier.setChecked(false);
                txt_title.setText("ReEs Guru");
                break;
            case 3:
                radio_dashboard_matches.setChecked(true);
                if (myMatchesFragment == null) {
                    myMatchesFragment = new MyMatchesFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container, myMatchesFragment).commit();
                radio_dashboard_post.setChecked(false);
                radio_dashboard_agent.setChecked(false);
                radio_dashboard_near_by.setChecked(false);
                radio_dashboard_supplier.setChecked(false);
                txt_title.setText("My Matches");
                break;
            case 4:
                radio_dashboard_supplier.setChecked(true);
                if (suppliersFragment == null) {
                    suppliersFragment = new SuppliersFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container, suppliersFragment).commit();
                radio_dashboard_post.setChecked(false);
                radio_dashboard_agent.setChecked(false);
                radio_dashboard_near_by.setChecked(false);
                radio_dashboard_matches.setChecked(false);
                txt_title.setText("Supplier");
                break;
            case 5:

                break;

            case 6:
                if (!AppConstant.isuser) {
                    startActivity(new Intent(this, LoginActivity.class));
                } else {
                    MyPreference preference = new MyPreference(getApplicationContext());
                    Gson gson = new Gson();
                    String json = preference.getuser();
                    UserModel userModel = gson.fromJson(json, UserModel.class);
                    actionLogout(userModel.getU_name());
                }
                break;
        }
    }

    private void actionLogout(String UserName) {
        RequestBody requestBody = new FormBody.Builder()
                .add("UserName", UserName)
                .build();
        MyHttpClientHelper.getInstance().enqueueCall(MainActivity.this, UrlConstant.API_LOGOUT_URL, requestBody, mLoginResponseHelper);
    }

    public void drawMapView(Boolean flage) {
        if (flage) {
            dashbord_tab.setVisibility(View.GONE);
            layout_title.setVisibility(View.GONE);
        } else {
            dashbord_tab.setVisibility(View.VISIBLE);
            layout_title.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == this.RESULT_OK) {

//                Place place = PlaceAutocomplete.getPlace(MainActivity.this, data);
//                String latlang = (place.getLatLng().latitude) + "," + (place.getLatLng().longitude);
//                EventBus.getDefault().post(new SearchEvent(SearchEvent.ACTION_SEARCH, latlang));

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(MainActivity.this, data);
                // TODO: Handle the error.
                Log.e("status", status.getStatusMessage());

            } else if (resultCode == MainActivity.this.RESULT_CANCELED) {
                // The user canceled the operation.
            }

        }
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

    protected synchronized void buildGoogleApiClient() {
        LogHelper.e(TAG, "Building GoogleApiClient");
        //  if (mGoogleApiClient == null) {
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
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

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        switch (position) {
            case 0:
                selecteditem(0);
                break;
            case 1:
                selecteditem(1);
                break;
            case 2:
                selecteditem(2);
                break;
            case 3:
                selecteditem(3);
                break;
            case 4:
                selecteditem(4);
                break;
            case 5:
                selecteditem(5);
                break;
            case 6:
                selecteditem(6);
                break;
        }

    }

}



