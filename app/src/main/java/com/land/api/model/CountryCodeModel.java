package com.land.api.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by akshay on 18/10/16.
 */

/*
{
      "code": "+355",
      "name": "Albania"
    }
 */

public class CountryCodeModel implements JsonModel {


    private String name;
    private String code;
    private String isoCode;
    private int validationLength;


    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public int getValidationLength() {
        return validationLength;
    }

    @Override
    public void fromJson(JSONObject object) {

        try {
            this.name = object.getString("name");
            this.code = object.getString("code");
            this.isoCode = object.getString("iso");
            this.validationLength = object.getInt("validation_length");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public JSONObject toJson() {
        return null;
    }
}
