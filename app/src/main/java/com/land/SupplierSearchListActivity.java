package com.land;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.land.adapter.AdaptersupplierCategory;
import com.land.adapter.PlacesAutoCompleteAdapter;
import com.land.api.model.SearchPlaceModel;
import com.land.api.model.SupplierCategoryItemsModel;
import com.land.constant.AppConstant;
import com.land.custom.RecycleViewItemDivider;
import com.land.events.SearchEvent;
import com.land.helper.LogHelper;
import com.land.helper.TintDrawableHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import okhttp3.Call;

public class SupplierSearchListActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private final static String TAG = SupplierSearchListActivity.class.getSimpleName();
    public static ArrayList<SupplierCategoryItemsModel> mItems = new ArrayList<>();
    protected Toolbar mToolbar;
    protected RecyclerView recyclerView;
    protected AdaptersupplierCategory mAdapter;
    protected SwipeRefreshLayout swipeRefreshLayout;

    // private ArrayList<SupplierCategoryItemsModel> items = new ArrayList<>();
    // private ArrayList<Integer> hasdata = new ArrayList<>();
    Menu search_menu;
    MenuItem item_search;
    Toolbar searchtollbar;

    private Call lastCall;
    private Handler handler;
    private GoogleApiClient mGoogleApiClient;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        setContentView(R.layout.activity_supplier_search_list);
        handler = new Handler(Looper.getMainLooper());
        initView();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                openserch();
            }
        }, 200);
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        searchtollbar = (Toolbar) findViewById(R.id.search_toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        mToolbar.setNavigationIcon(TintDrawableHelper.getTintedResource(this, android.support.design.R.drawable.abc_ic_ab_back_material, R.color.white));

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.black));
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(manager);

        //add ItemDecoration
        recyclerView.addItemDecoration(new RecycleViewItemDivider(this));
       /* hasdata = new ArrayList<>();
        for (int i = 0; i < mItems.size(); i++) {
            if (hasdata.contains(mItems.get(i).getId())) {
                mItems.remove(mItems.get(i));
            } else {
                hasdata.add(mItems.get(i).getId());
            }
        }*/

        mAdapter = new AdaptersupplierCategory(this, mItems);
        recyclerView.setAdapter(mAdapter);

        setSearchtollbar();
        //openserch();
    }

    public void setSearchtollbar() {

        if (searchtollbar != null) {
            searchtollbar.inflateMenu(R.menu.menu_search);
            search_menu = searchtollbar.getMenu();

            searchtollbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        circleReveal(R.id.search_toolbar, 1, true, true);
                    else
                        searchtollbar.setVisibility(View.GONE);
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
                    resetAdapter();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        if (id == R.id.action_search) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                circleReveal(R.id.search_toolbar, 1, true, true);
            else
                searchtollbar.setVisibility(View.VISIBLE);
            item_search.expandActionView();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initSearchView() {
        final SearchView searchView = (SearchView) search_menu.findItem(R.id.action_filter_search).getActionView();
        final SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setHintTextColor(getResources().getColor(R.color.colorWhite));
        searchAutoComplete.setTextColor(getResources().getColor(R.color.colorWhite));
        searchAutoComplete.setAdapter(new PlacesAutoCompleteAdapter(SupplierSearchListActivity.this, R.layout.autocomplete_list_item));
        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String description = (String) parent.getItemAtPosition(position);
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
        searchView.setSubmitButtonEnabled(false);
        // Change search close button image
        ImageView closeButton = (ImageView) searchView.findViewById(R.id.search_close_btn);
        //closeButton.setImageResource(R.drawable.ic_close);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = (EditText) findViewById(R.id.search_src_text);
                //Clear the text from EditText view
                et.setText("");
                //Clear query
                searchView.setQuery("", false);
                //Collapse the action view
                resetAdapter();
            }


        });

        // set hint and the text colors

//        EditText txtSearch = ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));
//        txtSearch.setHint("Search");
//        txtSearch.setHintTextColor(Color.DKGRAY);
//        txtSearch.setTextColor(getResources().getColor(R.color.colorPrimary));
//
//        // set the cursor
//        AutoCompleteTextView searchTextView = (AutoCompleteTextView) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
//        try {
//            Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
//            mCursorDrawableRes.setAccessible(true);
//            //mCursorDrawableRes.set(searchTextView, R.drawable.search_cursor); //This sets the cursor resource ID to 0 or @null which will make it visible on white background
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                callSearch(query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                callSearch(newText);

                return true;
            }

            public void callSearch(String query) {
                //Do searching
                ArrayList<SupplierCategoryItemsModel> tempArrayList = new ArrayList<SupplierCategoryItemsModel>();
                for (SupplierCategoryItemsModel c : mItems) {
                    if (query.length() <= c.getL_name().length()) {
                        if (c.getL_name().toLowerCase().contains(query.toString().toLowerCase())) {
                            tempArrayList.add(c);
                        } else if (c.getL_mingle_category().toLowerCase().contains(query.toString().toLowerCase())) {
                            tempArrayList.add(c);
                        } else if (c.getL_address().toLowerCase().contains(query.toString().toLowerCase())) {
                            tempArrayList.add(c);
                        }
                    }
                }
                mAdapter = new AdaptersupplierCategory(SupplierSearchListActivity.this, tempArrayList);
                recyclerView.setAdapter(mAdapter);
               /* if (query.length() >= 3) {
                    actionGetJoints(query);
                }*/
            }
        });
    }

    private void resetAdapter() {
        mAdapter = new AdaptersupplierCategory(SupplierSearchListActivity.this, mItems);
        recyclerView.setAdapter(mAdapter);
    }

    private void openserch() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            circleReveal(R.id.search_toolbar, 1, true, true);
        else
            searchtollbar.setVisibility(View.VISIBLE);
        item_search.expandActionView();
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
}
