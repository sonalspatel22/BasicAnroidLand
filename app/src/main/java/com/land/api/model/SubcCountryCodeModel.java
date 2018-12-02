package com.land.api.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dashrath on 9/28/2017.
 */

/*{
"country_id":"238",
"country_iso":"ZM",
"country_name":"ZAMBIA",
"country_nicename":"Zambia",
"country_iso3":"ZMW",
"country_numcode":"894",
"country_phonecode":"260",
"country_digits":"9",
"opcolid":"26001"
}*/


public class SubcCountryCodeModel implements JsonModel {

    private String country_id;
    private String country_iso;
    private String country_name;
    private String country_nicename;
    private String country_iso3;
    private String country_numcode;
    private String country_phonecode;
    private String opcolid;

    private int validationLength;


    public String getCountry_id() {
        return country_id;
    }

    public String getCountry_iso() {
        return country_iso;
    }

    public String getCountry_name() {
        return country_name;
    }

    public String getCountry_nicename() {
        return country_nicename;
    }

    public String getCountry_iso3() {
        return country_iso3;
    }

    public String getCountry_numcode() {
        return country_numcode;
    }

    public String getCountry_phonecode() {
        return "+ " + country_phonecode;
    }

    public String getOpcolid() {
        return opcolid;
    }

    public int getValidationLength() {
        return validationLength;
    }

    @Override
    public void fromJson(JSONObject object) {

        try {
            this.country_id = object.getString("country_id");
            this.country_iso = object.getString("country_iso");
            this.country_name = object.getString("country_name");
            this.country_nicename = object.getString("country_nicename");
            this.country_iso3 = object.getString("country_iso3");
            this.country_numcode = object.getString("country_numcode");
            this.country_phonecode = object.getString("country_phonecode");
            this.opcolid = object.getString("opcolid");
            this.validationLength = object.getInt("country_digits");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject toJson() {
        return null;
    }
}
