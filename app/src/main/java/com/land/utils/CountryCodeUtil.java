package com.land.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.telephony.TelephonyManager;


import com.land.api.model.CountryCodeModel;
import com.land.helper.LogHelper;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class CountryCodeUtil {

    public static ArrayList<CountryCodeModel> getAllCountryCode(Context context) {
        ArrayList<CountryCodeModel> mItems = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(getJsonArrayFromAssets("data/countrycode-final.json", context));

            for (int i = 0, lim = jsonArray.length(); i < lim; i++) {
                CountryCodeModel model = new CountryCodeModel();
                model.fromJson(jsonArray.getJSONObject(i));
                mItems.add(model);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mItems;
    }


    private static String getJsonArrayFromAssets(String filename, Context context) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream file = manager.open(filename);
        byte[] formArray = new byte[file.available()];
        file.read(formArray);
        file.close();

        return new String(formArray);
    }

    public static CountryCodeModel getSystemCountryCode(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String country = tm.getSimCountryIso();

        LogHelper.d("country :", "(" + country + ")");

        for (CountryCodeModel model : getAllCountryCode(context)) {
            LogHelper.d("country code:", "(" + model.getIsoCode() + ")");
            if (model.getIsoCode().equalsIgnoreCase(country)) {
                return model;
            }
        }
        return null;
    }

}
