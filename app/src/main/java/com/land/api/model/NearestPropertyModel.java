package com.land.api.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Alpesh on 12/18/2017.
 */

public class NearestPropertyModel implements JsonModel {
    String Flag;
    String Distance;
    String PropertyID;
    String Title;
    String AgentId;
    String UserId;
    String Description;
    String Url;
    String PropertyFor;
    String PropertyType;
    String ViewType;
    String FloorNumber;
    String PropertySubType;
    String KeyLandmark;
    String LandArea;
    String LandAreaUnit;
    String BuiltArea;
    String BuiltAreaUnit;
    String Possession;
    String TransactionType;
    String NoofBedrooms;
    String NoofBathrooms;
    String NoofKitchen;
    String NoofBalcony;
    String FaceView;
    String PropertyAge;
    String Location;
    String City;
    String Postcode;
    String DistanceDetails;
    String AccountNo;
    String MonthlyRent;
    String SalePrice;
    String Deposit;
    String Furnished;
    String FuseboxLocation;
    String StopcockLocation;
    String HomeFeaturesList;
    String SocietyFeaturesList;
    String OtherFeaturesList;
    String Latitude;
    String Longitude;
    String ZoomValue;
    String currencyunit;
    String UsdSalePrice;
    String UsdMonthlyRent;
    String UsdDeposit;
    String IsPublished;
    String Property_QRCode;
    String GeoLocation_QRCode;
    String KeyName;
    String PropertyImage;


    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }

    public String getDistance() {
        return Distance;
    }

    public void setDistance(String distance) {
        Distance = distance;
    }

    public String getPropertyID() {
        return PropertyID;
    }

    public void setPropertyID(String propertyID) {
        PropertyID = propertyID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAgentId() {
        return AgentId;
    }

    public void setAgentId(String agentId) {
        AgentId = agentId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getPropertyFor() {
        return PropertyFor;
    }

    public void setPropertyFor(String propertyFor) {
        PropertyFor = propertyFor;
    }

    public String getPropertyType() {
        return PropertyType;
    }

    public void setPropertyType(String propertyType) {
        PropertyType = propertyType;
    }

    public String getViewType() {
        return ViewType;
    }

    public void setViewType(String viewType) {
        ViewType = viewType;
    }

    public String getFloorNumber() {
        return FloorNumber;
    }

    public void setFloorNumber(String floorNumber) {
        FloorNumber = floorNumber;
    }

    public String getPropertySubType() {
        return PropertySubType;
    }

    public void setPropertySubType(String propertySubType) {
        PropertySubType = propertySubType;
    }

    public String getKeyLandmark() {
        return KeyLandmark;
    }

    public void setKeyLandmark(String keyLandmark) {
        KeyLandmark = keyLandmark;
    }

    public String getLandArea() {
        return LandArea;
    }

    public void setLandArea(String landArea) {
        LandArea = landArea;
    }

    public String getLandAreaUnit() {
        return LandAreaUnit;
    }

    public void setLandAreaUnit(String landAreaUnit) {
        LandAreaUnit = landAreaUnit;
    }

    public String getBuiltArea() {
        return BuiltArea;
    }

    public void setBuiltArea(String builtArea) {
        BuiltArea = builtArea;
    }

    public String getBuiltAreaUnit() {
        return BuiltAreaUnit;
    }

    public void setBuiltAreaUnit(String builtAreaUnit) {
        BuiltAreaUnit = builtAreaUnit;
    }

    public String getPossession() {
        return Possession;
    }

    public void setPossession(String possession) {
        Possession = possession;
    }

    public String getTransactionType() {
        return TransactionType;
    }

    public void setTransactionType(String transactionType) {
        TransactionType = transactionType;
    }

    public String getNoofBedrooms() {
        return NoofBedrooms;
    }

    public void setNoofBedrooms(String noofBedrooms) {
        NoofBedrooms = noofBedrooms;
    }

    public String getNoofBathrooms() {
        return NoofBathrooms;
    }

    public void setNoofBathrooms(String noofBathrooms) {
        NoofBathrooms = noofBathrooms;
    }

    public String getNoofKitchen() {
        return NoofKitchen;
    }

    public void setNoofKitchen(String noofKitchen) {
        NoofKitchen = noofKitchen;
    }

    public String getNoofBalcony() {
        return NoofBalcony;
    }

    public void setNoofBalcony(String noofBalcony) {
        NoofBalcony = noofBalcony;
    }

    public String getFaceView() {
        return FaceView;
    }

    public void setFaceView(String faceView) {
        FaceView = faceView;
    }

    public String getPropertyAge() {
        return PropertyAge;
    }

    public void setPropertyAge(String propertyAge) {
        PropertyAge = propertyAge;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getPostcode() {
        return Postcode;
    }

    public void setPostcode(String postcode) {
        Postcode = postcode;
    }

    public String getDistanceDetails() {
        return DistanceDetails;
    }

    public void setDistanceDetails(String distanceDetails) {
        DistanceDetails = distanceDetails;
    }

    public String getAccountNo() {
        return AccountNo;
    }

    public void setAccountNo(String accountNo) {
        AccountNo = accountNo;
    }

    public String getMonthlyRent() {
        return MonthlyRent;
    }

    public void setMonthlyRent(String monthlyRent) {
        MonthlyRent = monthlyRent;
    }

    public String getSalePrice() {
        return SalePrice;
    }

    public void setSalePrice(String salePrice) {
        SalePrice = salePrice;
    }

    public String getDeposit() {
        return Deposit;
    }

    public void setDeposit(String deposit) {
        Deposit = deposit;
    }

    public String getFurnished() {
        return Furnished;
    }

    public void setFurnished(String furnished) {
        Furnished = furnished;
    }

    public String getFuseboxLocation() {
        return FuseboxLocation;
    }

    public void setFuseboxLocation(String fuseboxLocation) {
        FuseboxLocation = fuseboxLocation;
    }

    public String getStopcockLocation() {
        return StopcockLocation;
    }

    public void setStopcockLocation(String stopcockLocation) {
        StopcockLocation = stopcockLocation;
    }

    public String getHomeFeaturesList() {
        return HomeFeaturesList;
    }

    public void setHomeFeaturesList(String homeFeaturesList) {
        HomeFeaturesList = homeFeaturesList;
    }

    public String getSocietyFeaturesList() {
        return SocietyFeaturesList;
    }

    public void setSocietyFeaturesList(String societyFeaturesList) {
        SocietyFeaturesList = societyFeaturesList;
    }

    public String getOtherFeaturesList() {
        return OtherFeaturesList;
    }

    public void setOtherFeaturesList(String otherFeaturesList) {
        OtherFeaturesList = otherFeaturesList;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getZoomValue() {
        return ZoomValue;
    }

    public void setZoomValue(String zoomValue) {
        ZoomValue = zoomValue;
    }

    public String getCurrencyunit() {
        return currencyunit;
    }

    public void setCurrencyunit(String currencyunit) {
        this.currencyunit = currencyunit;
    }

    public String getUsdSalePrice() {
        return UsdSalePrice;
    }

    public void setUsdSalePrice(String usdSalePrice) {
        UsdSalePrice = usdSalePrice;
    }

    public String getUsdMonthlyRent() {
        return UsdMonthlyRent;
    }

    public void setUsdMonthlyRent(String usdMonthlyRent) {
        UsdMonthlyRent = usdMonthlyRent;
    }

    public String getUsdDeposit() {
        return UsdDeposit;
    }

    public void setUsdDeposit(String usdDeposit) {
        UsdDeposit = usdDeposit;
    }

    public String getIsPublished() {
        return IsPublished;
    }

    public void setIsPublished(String isPublished) {
        IsPublished = isPublished;
    }

    public String getProperty_QRCode() {
        return Property_QRCode;
    }

    public void setProperty_QRCode(String property_QRCode) {
        Property_QRCode = property_QRCode;
    }

    public String getGeoLocation_QRCode() {
        return GeoLocation_QRCode;
    }

    public void setGeoLocation_QRCode(String geoLocation_QRCode) {
        GeoLocation_QRCode = geoLocation_QRCode;
    }

    public String getKeyName() {
        return KeyName;
    }

    public void setKeyName(String keyName) {
        KeyName = keyName;
    }

    public String getPropertyImage() {
        return PropertyImage;
    }

    public void setPropertyImage(String propertyImage) {
        PropertyImage = propertyImage;
    }


    @Override
    public void fromJson(JSONObject object) {

        try {
            this.setFlag(object.getString("Flag"));
            this.setDistance(object.getString("Distance"));
            this.setPropertyID(object.getString("PropertyID"));
            this.setTitle(object.getString("Title"));
            this.setAgentId(object.getString("AgentId"));
            this.setUserId(object.getString("UserId"));
            this.setDescription(object.getString("Description"));
            this.setUrl(object.getString("Url"));
            this.setPropertyFor(object.getString("PropertyFor"));
            this.setPropertyType(object.getString("PropertyType"));
            this.setViewType(object.getString("ViewType"));
            this.setFloorNumber(object.getString("FloorNumber"));
            this.setPropertySubType(object.getString("PropertySubType"));
            this.setKeyLandmark(object.getString("KeyLandmark"));
            this.setLandArea(object.getString("LandArea"));
            this.setLandAreaUnit(object.getString("LandAreaUnit"));
            this.setBuiltArea(object.getString("BuiltArea"));
            this.setBuiltAreaUnit(object.getString("BuiltAreaUnit"));
            this.setPossession(object.getString("Possession"));
            this.setTransactionType(object.getString("TransactionType"));
            this.setNoofBedrooms(object.getString("NoofBedrooms"));
            this.setNoofBathrooms(object.getString("NoofBathrooms"));
            this.setNoofKitchen(object.getString("NoofKitchen"));
            this.setNoofBalcony(object.getString("NoofBalcony"));
            this.setFaceView(object.getString("FaceView"));
            this.setPropertyAge(object.getString("PropertyAge"));
            this.setLocation(object.getString("Location"));
            this.setCity(object.getString("City"));
            this.setPostcode(object.getString("Postcode"));
            this.setDistanceDetails(object.getString("DistanceDetails"));
            this.setAccountNo(object.getString("AccountNo"));
            this.setMonthlyRent(object.getString("MonthlyRent"));
            this.setSalePrice(object.getString("SalePrice"));
            this.setDeposit(object.getString("Deposit"));
            this.setFurnished(object.getString("Furnished"));
            this.setFuseboxLocation(object.getString("FuseboxLocation"));
            this.setStopcockLocation(object.getString("StopcockLocation"));
            this.setHomeFeaturesList(object.getString("HomeFeaturesList"));
            this.setSocietyFeaturesList(object.getString("SocietyFeaturesList"));
            this.setOtherFeaturesList(object.getString("OtherFeaturesList"));
            this.setLatitude(object.getString("Latitude"));
            this.setLongitude(object.getString("Longitude"));
            this.setZoomValue(object.getString("ZoomValue"));
            this.setCurrencyunit(object.getString("currencyunit"));
            this.setUsdSalePrice(object.getString("UsdSalePrice"));
            this.setUsdMonthlyRent(object.getString("UsdMonthlyRent"));
            this.setUsdDeposit(object.getString("UsdDeposit"));
            this.setIsPublished(object.getString("IsPublished"));
            this.setProperty_QRCode(object.getString("Property_QRCode"));
            this.setGeoLocation_QRCode(object.getString("GeoLocation_QRCode"));
            this.setKeyName(object.getString("KeyName"));
            this.setPropertyImage(object.getString("PropertyImage"));

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public JSONObject toJson() {
        return null;
    }
}
