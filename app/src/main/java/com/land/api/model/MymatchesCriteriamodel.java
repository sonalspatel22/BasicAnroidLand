package com.land.api.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Alpesh on 1/11/2018.
 */

public class MymatchesCriteriamodel implements JsonModel {
    String USerCiteria;
    String UserID;
    String Title;
    String location;
    String PropertyFor;
    String propertyType;
    String bathroom;
    String bedroom;
    String minprice;
    String maxprice;
    String mincoverarea;
    String maxcoverarea;
    String minplotarea;
    String maxplotarea;
    String usdtoaed;
    String usdtogbp;
    String usdtoeur;
    String usdtozar;
    String usdtozmw;
    String meter;
    String yard;
    String acres;
    String Possession;
    String TransactionType;
    String orderby;
    String orderto;
    String distance;
    String center_latitude;
    String center_longitude;
    String currencyto;
    String CreatedDate;
    String ModifiedDate;

    public String getUSerCiteria() {
        return USerCiteria;
    }

    public String getUserID() {
        return UserID;
    }

    public String getTitle() {
        return Title;
    }

    public String getLocation() {
        return location;
    }

    public String getPropertyFor() {
        return PropertyFor;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public String getBathroom() {
        return bathroom;
    }

    public String getBedroom() {
        return bedroom;
    }

    public String getMinprice() {
        return minprice;
    }

    public String getMaxprice() {
        return maxprice;
    }

    public String getMincoverarea() {
        return mincoverarea;
    }

    public String getMaxcoverarea() {
        return maxcoverarea;
    }

    public String getMinplotarea() {
        return minplotarea;
    }

    public String getMaxplotarea() {
        return maxplotarea;
    }

    public String getUsdtoaed() {
        return usdtoaed;
    }

    public String getUsdtogbp() {
        return usdtogbp;
    }

    public String getUsdtoeur() {
        return usdtoeur;
    }

    public String getUsdtozar() {
        return usdtozar;
    }

    public String getUsdtozmw() {
        return usdtozmw;
    }

    public String getMeter() {
        return meter;
    }

    public String getYard() {
        return yard;
    }

    public String getAcres() {
        return acres;
    }

    public String getPossession() {
        return Possession;
    }

    public String getTransactionType() {
        return TransactionType;
    }

    public String getOrderby() {
        return orderby;
    }

    public String getOrderto() {
        return orderto;
    }

    public String getDistance() {
        return distance;
    }

    public String getCenter_latitude() {
        return center_latitude;
    }

    public String getCenter_longitude() {
        return center_longitude;
    }

    public String getCurrencyto() {
        return currencyto;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public String getModifiedDate() {
        return ModifiedDate;
    }

    @Override
    public void fromJson(JSONObject object) {
        try {
            this.USerCiteria = object.getString("USerCiteria");
            this.UserID = object.getString("UserID");
            this.Title = object.getString("Title");
            this.location = object.getString("location");
            this.PropertyFor = object.getString("PropertyFor");
            this.propertyType = object.getString("propertyType");
            this.bathroom = object.getString("bathroom");
            this.bedroom = object.getString("bedroom");
            this.minprice = object.getString("minprice");
            this.maxprice = object.getString("maxprice");
            this.mincoverarea = object.getString("mincoverarea");
            this.maxcoverarea = object.getString("maxcoverarea");
            this.minplotarea = object.getString("maxcoverarea");
            this.maxplotarea = object.getString("maxplotarea");
            this.usdtoaed = object.getString("usdtoaed");
            this.usdtogbp = object.getString("usdtogbp");
            this.usdtoeur = object.getString("usdtoeur");
            this.usdtozar = object.getString("usdtozar");
            this.usdtozmw = object.getString("usdtozmw");
            this.meter = object.getString("meter");
            this.yard = object.getString("yard");
            this.acres = object.getString("acres");
            this.Possession = object.getString("Possession");
            this.TransactionType = object.getString("TransactionType");
            this.orderby = object.getString("orderby");
            this.orderto = object.getString("orderto");
            this.distance = object.getString("distance");
            this.center_latitude = object.getString("center_latitude");
            this.center_longitude = object.getString("center_longitude");
            this.currencyto = object.getString("currencyto");
            this.CreatedDate = object.getString("currencyto");
            this.ModifiedDate = object.getString("ModifiedDate");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject toJson() {
        return null;
    }
}
