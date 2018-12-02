package com.land.api.model;

/**
 * Created by Dashrath on 12/29/2017.
 */

public class FilterModel {

    private boolean chkBuy = false;
    private boolean chkRent = false;
    private boolean chkProject = false;
    private boolean chkOpenhouse = false;
    private boolean chkAgents = false;
    private boolean chkcommercial = false;
    private boolean chkresident = false;
    private String possession = "0";
    private String transaction_type = "13";
    private String bathRooms = "0";
    private String bedRooms = "0";

    private String CurrencyUnit = "AED";
    private String CoverAreaUnit = "SqMeter";
    private String PlotAreaUnit = "SqMeter";
    private String minprice = "10000";
    private String maxprice = "1000000";
    private String mincoverarea = "0.0";
    private String maxcoverarea = "0.0";
    private String minplotarea = "0.0";
    private String maxplotarea = "0.0";

    public boolean isChkcommercial() {
        return chkcommercial;
    }

    public void setChkcommercial(boolean chkcommercial) {
        this.chkcommercial = chkcommercial;
    }

    public boolean isChkresident() {
        return chkresident;
    }

    public void setChkresident(boolean chkresident) {
        this.chkresident = chkresident;
    }

    public String getMinprice() {
        return minprice;
    }

    public void setMinprice(String minprice) {
        this.minprice = minprice;
    }

    public String getMaxprice() {
        return maxprice;
    }

    public void setMaxprice(String maxprice) {
        this.maxprice = maxprice;
    }

    public String getMincoverarea() {
        return mincoverarea;
    }

    public void setMincoverarea(String mincoverarea) {
        this.mincoverarea = mincoverarea;
    }

    public String getMaxcoverarea() {
        return maxcoverarea;
    }

    public void setMaxcoverarea(String maxcoverarea) {
        this.maxcoverarea = maxcoverarea;
    }

    public String getMinplotarea() {
        return minplotarea;
    }

    public void setMinplotarea(String minplotarea) {
        this.minplotarea = minplotarea;
    }

    public String getMaxplotarea() {
        return maxplotarea;
    }

    public void setMaxplotarea(String maxplotarea) {
        this.maxplotarea = maxplotarea;
    }


    public boolean isChkBuy() {
        return chkBuy;
    }

    public void setChkBuy(boolean chkBuy) {
        this.chkBuy = chkBuy;
    }

    public boolean isChkRent() {
        return chkRent;
    }

    public void setChkRent(boolean chkRent) {
        this.chkRent = chkRent;
    }

    public boolean isChkProject() {
        return chkProject;
    }

    public void setChkProject(boolean chkProject) {
        this.chkProject = chkProject;
    }

    public boolean isChkOpenhouse() {
        return chkOpenhouse;
    }

    public void setChkOpenhouse(boolean chkOpenhouse) {
        this.chkOpenhouse = chkOpenhouse;
    }

    public boolean isChkAgents() {
        return chkAgents;
    }

    public void setChkAgents(boolean chkAgents) {
        this.chkAgents = chkAgents;
    }

    public String getPossession() {
        return possession;
    }

    public void setPossession(String possession) {
        this.possession = possession;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public String getBathRooms() {
        return bathRooms;
    }

    public void setBathRooms(String bathRooms) {
        this.bathRooms = bathRooms;
    }

    public String getBedRooms() {
        return bedRooms;
    }

    public void setBedRooms(String bedRooms) {
        this.bedRooms = bedRooms;
    }

    public String getCurrencyUnit() {
        return CurrencyUnit;
    }

    public void setCurrencyUnit(String currencyUnit) {
        CurrencyUnit = currencyUnit;
    }

    public String getCoverAreaUnit() {
        return CoverAreaUnit;
    }

    public void setCoverAreaUnit(String coverAreaUnit) {
        CoverAreaUnit = coverAreaUnit;
    }

    public String getPlotAreaUnit() {
        return PlotAreaUnit;
    }

    public void setPlotAreaUnit(String plotAreaUnit) {
        PlotAreaUnit = plotAreaUnit;
    }
}
