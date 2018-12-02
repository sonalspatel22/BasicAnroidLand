package com.land.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.land.api.model.FilterModel;
import com.land.api.model.UserModel;

public class MyPreference {

    public static final String KEY_FILTER = "KEY_FILTER";
    public static final String KEY_USER = "KEY_USER";
    public static final String KEY_LAST_LOCATION_LAT = "KEY_LAST_LOCATION_LAT";
    public static final String KEY_LAST_LOCATION_LNG = "KEY_LAST_LOCATION_LNG";
    public SharedPreferences mPreferences;

    public FilterModel filterModel = new FilterModel();
    public UserModel userModel = new UserModel();
    private Context mContext;

    public MyPreference(Context mContext) {
        this.mPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public void saveFilter(String filter) {
        this.mPreferences.edit().putString(KEY_FILTER, filter).commit();
    }

    public String getFilter() {
        Gson gson = new Gson();
        String defoutValue = gson.toJson(filterModel);
        return this.mPreferences.getString(KEY_FILTER, defoutValue);
    }

    public void saveLatestLocation(LatLng latLng) {

        this.mPreferences.edit().putString(KEY_LAST_LOCATION_LAT, "" + latLng.latitude).commit();
        this.mPreferences.edit().putString(KEY_LAST_LOCATION_LNG, "" + latLng.longitude).commit();
    }

    public LatLng getLatestLocation() {

        LatLng latLng = null;
        try {
            String lat = this.mPreferences.getString(KEY_LAST_LOCATION_LAT, "" + 21.184959);
            String Lng = this.mPreferences.getString(KEY_LAST_LOCATION_LNG, "" + 72.791848);
            latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(Lng));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return latLng;
    }

    public void setuser(String userModel) {
        this.mPreferences.edit().putString(KEY_USER, userModel).commit();
    }

    public String getuser() {
        Gson gson = new Gson();
        String defoutValue = gson.toJson(userModel);
        return this.mPreferences.getString(KEY_USER, defoutValue);
    }

    public void clearuser() {
        this.mPreferences.edit().remove(KEY_USER);
        this.mPreferences.edit().commit();
    }

}
