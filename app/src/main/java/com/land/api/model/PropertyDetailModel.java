package com.land.api.model;

import org.json.JSONObject;

import java.util.HashMap;

public class PropertyDetailModel implements JsonModel {

    public Integer flag;
    public Integer propertyID;
    public String title;
    public Integer agentId;
    public Integer userId;
    public String description;
    public String url;
    public Integer propertyFor;
    public Integer propertyType;
    public String viewType;
    public Integer floorNumber;
    public Integer propertySubType;
    public String keyLandmark;
    public Double landArea;
    public String landAreaUnit;
    public Double builtArea;
    public String builtAreaUnit;
    public String possession;
    public Integer transactionType;
    public Integer noofBedrooms;
    public Integer noofBathrooms;
    public Integer noofKitchen;
    public Integer noofBalcony;
    public String faceView;
    public Integer propertyAge;
    public String location;
    public String city;
    public String postcode;
    public String distanceDetails;
    public String accountNo;
    public Double monthlyRent;
    public Double salePrice;
    public Double deposit;
    public String furnished;
    public String fuseboxLocation;
    public String stopcockLocation;
    public String homeFeaturesList;
    public String societyFeaturesList;
    public String otherFeaturesList;
    public Double latitude;
    public Double longitude;
    public Integer zoomValue;
    public String currencyunit;
    public Double usdSalePrice;
    public Double usdMonthlyRent;
    public Double usdDeposit;
    public Boolean isPublished;
    public String propertyQRCode;
    public String geoLocationQRCode;
    public String propertyIdser;
    public String transactionTypeName;
    public String propertyTypeName;
    public String propertySubTypeeName;
    public String propertyImage;

    public String homeFeaturesListKeyName;
    public String societyFeaturesListKeyName;
    public String otherFeaturesListKeyName;

    public HashMap<String, String> Summery = new HashMap<>();


    public Integer getFlag() {
        return flag;
    }

    public Integer getPropertyID() {
        return propertyID;
    }

    public String getTitle() {
        return title;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public Integer getPropertyFor() {
        return propertyFor;
    }

    public Integer getPropertyType() {
        return propertyType;
    }

    public String getViewType() {
        return viewType;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public Integer getPropertySubType() {
        return propertySubType;
    }

    public String getKeyLandmark() {
        return keyLandmark;
    }

    public Double getLandArea() {
        return landArea;
    }

    public String getLandAreaUnit() {
        return landAreaUnit;
    }

    public Double getBuiltArea() {
        return builtArea;
    }

    public String getBuiltAreaUnit() {
        return builtAreaUnit;
    }

    public String getPossession() {
        return possession;
    }

    public Integer getTransactionType() {
        return transactionType;
    }

    public Integer getNoofBedrooms() {
        return noofBedrooms;
    }

    public Integer getNoofBathrooms() {
        return noofBathrooms;
    }

    public Integer getNoofKitchen() {
        return noofKitchen;
    }

    public Integer getNoofBalcony() {
        return noofBalcony;
    }

    public String getFaceView() {
        return faceView;
    }

    public Integer getPropertyAge() {
        return propertyAge;
    }

    public String getLocation() {
        return location;
    }

    public String getCity() {
        return city;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getDistanceDetails() {
        return distanceDetails;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public Double getMonthlyRent() {
        return monthlyRent;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public Double getDeposit() {
        return deposit;
    }

    public String getFurnished() {
        return furnished;
    }

    public String getFuseboxLocation() {
        return fuseboxLocation;
    }

    public String getStopcockLocation() {
        return stopcockLocation;
    }

    public String getHomeFeaturesList() {
        return homeFeaturesList;
    }

    public String getSocietyFeaturesList() {
        return societyFeaturesList;
    }

    public String getOtherFeaturesList() {
        return otherFeaturesList;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Integer getZoomValue() {
        return zoomValue;
    }

    public String getCurrencyunit() {
        return currencyunit;
    }

    public Double getUsdSalePrice() {
        return usdSalePrice;
    }

    public Double getUsdMonthlyRent() {
        return usdMonthlyRent;
    }

    public Double getUsdDeposit() {
        return usdDeposit;
    }

    public Boolean getPublished() {
        return isPublished;
    }

    public String getPropertyQRCode() {
        return propertyQRCode;
    }

    public String getGeoLocationQRCode() {
        return geoLocationQRCode;
    }

    public String getPropertyIdser() {
        return propertyIdser;
    }

    public String getTransactionTypeName() {
        return transactionTypeName;
    }

    public String getPropertyTypeName() {
        return propertyTypeName;
    }

    public String getPropertySubTypeeName() {
        return propertySubTypeeName;
    }

    public String getPropertyImage() {
        return propertyImage;
    }

    public String getHomeFeaturesListKeyName() {
        return homeFeaturesListKeyName;
    }

    public String getSocietyFeaturesListKeyName() {
        return societyFeaturesListKeyName;
    }

    public String getOtherFeaturesListKeyName() {
        return otherFeaturesListKeyName;
    }

    public HashMap<String, String> getSummery() {
        return Summery;
    }


    @Override
    public void fromJson(JSONObject object) {
        try {

            this.flag = object.getInt("Flag");
            this.propertyID = object.getInt("PropertyID");
            this.title = object.getString("Title");
            this.agentId = object.getInt("AgentId");
            this.userId = object.getInt("UserId");
            this.description = object.getString("Description");
            this.url = object.getString("Url");
            this.viewType = object.getString("ViewType");
            this.title = object.getString("Title");
            this.keyLandmark = object.getString("KeyLandmark");
            this.landAreaUnit = object.getString("LandAreaUnit");
            this.builtAreaUnit = object.getString("BuiltAreaUnit");
            this.possession = object.getString("Possession");
            this.faceView = object.getString("FaceView");
            this.location = object.getString("Location");
            this.city = object.getString("City");
            this.postcode = object.getString("Postcode");
            this.distanceDetails = object.getString("DistanceDetails");
            this.accountNo = object.getString("AccountNo");
            this.furnished = object.getString("Furnished");
            this.fuseboxLocation = object.getString("FuseboxLocation");
            this.stopcockLocation = object.getString("StopcockLocation");
            this.homeFeaturesList = object.getString("HomeFeaturesList");
            this.societyFeaturesList = object.getString("SocietyFeaturesList");
            this.otherFeaturesList = object.getString("OtherFeaturesList");
            this.currencyunit = object.getString("currencyunit");
            this.propertyQRCode = object.getString("Property_QRCode");
            this.geoLocationQRCode = object.getString("GeoLocation_QRCode");
            this.propertyIdser = object.getString("PropertyIdser");
            this.transactionTypeName = object.getString("TransactionTypeName");
            this.propertyTypeName = object.getString("PropertyTypeName");
            this.propertySubTypeeName = object.getString("PropertySubTypeeName");
            this.propertyImage = object.getString("PropertyImage");
            this.homeFeaturesListKeyName = object.getString("HomeFeaturesListKeyName");
            this.societyFeaturesListKeyName = object.getString("SocietyFeaturesListKeyName");
            this.otherFeaturesListKeyName = object.getString("OtherFeaturesListKeyName");
            this.propertyFor = object.getInt("PropertyFor");
            this.propertyType = object.getInt("PropertyType");
            this.floorNumber = object.getInt("FloorNumber");
            this.propertySubType = object.getInt("PropertySubType");
            this.transactionType = object.getInt("TransactionType");
            this.noofBedrooms = object.getInt("NoofBedrooms");
            this.noofBathrooms = object.getInt("NoofBathrooms");
            this.noofKitchen = object.getInt("NoofKitchen");
            this.noofBalcony = object.getInt("NoofBalcony");
            this.propertyAge = object.getInt("PropertyAge");
            this.zoomValue = object.getInt("ZoomValue");
            this.landArea = object.getDouble("LandArea");
            this.builtArea = object.getDouble("BuiltArea");
            this.monthlyRent = object.getDouble("MonthlyRent");
            this.salePrice = object.getDouble("SalePrice");
            this.deposit = object.getDouble("Deposit");
            this.latitude = object.getDouble("Latitude");
            this.longitude = object.getDouble("Longitude");
            this.longitude = object.getDouble("Longitude");
            this.usdSalePrice = object.getDouble("UsdSalePrice");
            this.usdMonthlyRent = object.getDouble("UsdMonthlyRent");
            this.usdDeposit = object.getDouble("UsdDeposit");
            this.isPublished = object.getBoolean("IsPublished");

            Summery = new HashMap<>();

            this.Summery.put("Price", "" + this.salePrice);
            this.Summery.put("Property Type", "" + this.propertyTypeName);
            this.Summery.put("View Type", "" + this.viewType);
            this.Summery.put("City", "" + this.city);
            this.Summery.put("Location", "" + this.location);
            this.Summery.put("Key Landmark", "" + this.keyLandmark);
            this.Summery.put("Land/Carpet Area", "" + this.landArea);
            this.Summery.put("Bedrooms", "" + this.noofBedrooms);
            this.Summery.put("Bathrooms", "" + this.noofBathrooms);
            this.Summery.put("Kitchen", "" + this.noofKitchen);
            this.Summery.put("Balcony", "" + this.noofBalcony);
            this.Summery.put("Floor Number", "" + this.floorNumber);
            this.Summery.put("Residential Type", "" + this.viewType);
            this.Summery.put("Possession", "" + this.possession);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject toJson() {
        return null;
    }
}
