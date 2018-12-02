package com.land.api.model;

import org.json.JSONObject;

/**
 * Created by Alpesh on 1/22/2018.
 */

public class CurrencyModel implements JsonModel {
    String Currencyname;
    String Currencyvalue;

    @Override
    public void fromJson(JSONObject object) {
        this.Currencyname = getCurrencyname();
        this.Currencyvalue = getCurrencyvalue();

    }

    public String getCurrencyname() {
        return Currencyname;
    }

    public void setCurrencyname(String currencyname) {
        Currencyname = currencyname;
    }

    public String getCurrencyvalue() {
        return Currencyvalue;
    }

    public void setCurrencyvalue(String currencyvalue) {
        Currencyvalue = currencyvalue;
    }

    @Override
    public JSONObject toJson() {
        return null;
    }
}
