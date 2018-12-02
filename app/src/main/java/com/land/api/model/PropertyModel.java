package com.land.api.model;

import org.json.JSONException;
import org.json.JSONObject;

public class PropertyModel implements JsonModel {

    String PropertyID;
    String PropertyIdser;
    String UserId;
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
    String PropertyOwnership;
    String DistanceDetails;
    String AccountNo;
    String MonthlyRent;
    String SalePrice;
    String Deposit;
    String RentDueDate;
    String LengthOfTenancy;
    String StartTenancyDate;
    String EndTenancyDate;
    String GasMeterNo;
    String ElectricityMeterNo;
    String Furnished;
    String GasCertExpiryDate;
    String ElectricityCertExpiryDate;
    String GasMeterNoLocation;
    String ElectricityMeterNoLocation;
    String FuseboxLocation;
    String StopcockLocation;
    String HomeFeaturesList;
    String SocietyFeaturesList;
    String OtherFeaturesList;
    String Latitude;
    String Longitude;
    String ZoomValue;
    String IsDeleted;
    String upsize_ts;
    String PortalId;
    String currencyunit;
    String UsdSalePrice;
    String UsdMonthlyRent;
    String UsdDeposit;
    String IsPublished;
    String Property_QRCode;
    String GeoLocation_QRCode;
    String Parent;
    String GroupID;
    String IsFeatured;
    String Title;
    String Description;
    String Url;
    String PropertyFor;
    String PropertyType;
    String ViewType;
    String FloorNumber;
    String TotalFloor;
    String PropertySubType;
    String KeyLandmark;
    String LandArea;
    String LandAreaUnit;
    String BuiltArea;
    String BuiltAreaUnit;
    String[] strings = {"PropertyID", "PropertyIdser", "UserId", "Title", "Description", "Url", "PropertyFor", "PropertyType",
            "ViewType", "FloorNumber", "TotalFloor", "PropertySubType", "KeyLandmark", "LandArea", "LandAreaUnit", "BuiltArea",
            "BuiltAreaUnit", "Possession", "TransactionType", "NoofBedrooms", "NoofBathrooms", "NoofKitchen", "NoofBalcony", "FaceView",
            "PropertyAge", "Location", "City", "Postcode", "PropertyOwnership", "DistanceDetails", "AccountNo", "MonthlyRent", "SalePrice",
            "Deposit", "RentDueDate", "LengthOfTenancy", "StartTenancyDate", "EndTenancyDate", "GasMeterNo", "ElectricityMeterNo", "Furnished",
            "GasCertExpiryDate", "ElectricityCertExpiryDate", "GasMeterNoLocation", "ElectricityMeterNoLocation", "FuseboxLocation",
            "StopcockLocation", "HomeFeaturesList", "SocietyFeaturesList", "OtherFeaturesList", "Latitude", "Longitude", "ZoomValue",
            "IsDeleted", "upsize_ts", "PortalId", "currencyunit", "UsdSalePrice", "UsdMonthlyRent", "UsdDeposit", "IsPublished",
            "Property_QRCode", "GeoLocation_QRCode", "Parent", "GroupID", "IsFeatured"};

    public String getPropertyID() {
        return PropertyID;
    }

    public String getPropertyIdser() {
        return PropertyIdser;
    }

    public String getUserId() {
        return UserId;
    }

    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }

    public String getUrl() {
        return Url;
    }

    public String getPropertyFor() {
        return PropertyFor;
    }

    public String getPropertyType() {
        return PropertyType;
    }

    public String getViewType() {
        return ViewType;
    }

    public String getFloorNumber() {
        return FloorNumber;
    }

    public String getTotalFloor() {
        return TotalFloor;
    }

    public String getPropertySubType() {
        return PropertySubType;
    }

    public String getKeyLandmark() {
        return KeyLandmark;
    }

    public String getLandArea() {
        return LandArea;
    }

    public String getLandAreaUnit() {
        return LandAreaUnit;
    }

    public String getBuiltArea() {
        return BuiltArea;
    }

    public String getBuiltAreaUnit() {
        return BuiltAreaUnit;
    }

    public String getPossession() {
        return Possession;
    }

    public String getTransactionType() {
        return TransactionType;
    }

    public String getNoofBedrooms() {
        return NoofBedrooms;
    }

    public String getNoofBathrooms() {
        return NoofBathrooms;
    }

    public String getNoofKitchen() {
        return NoofKitchen;
    }

    public String getNoofBalcony() {
        return NoofBalcony;
    }

    public String getFaceView() {
        return FaceView;
    }

    public String getPropertyAge() {
        return PropertyAge;
    }

    public String getLocation() {
        return Location;
    }

    public String getCity() {
        return City;
    }

    public String getPostcode() {
        return Postcode;
    }

    public String getPropertyOwnership() {
        return PropertyOwnership;
    }

    public String getDistanceDetails() {
        return DistanceDetails;
    }

    public String getAccountNo() {
        return AccountNo;
    }

    public String getMonthlyRent() {
        return MonthlyRent;
    }

    public String getSalePrice() {
        return SalePrice;
    }

    public String getDeposit() {
        return Deposit;
    }

    public String getRentDueDate() {
        return RentDueDate;
    }

    public String getLengthOfTenancy() {
        return LengthOfTenancy;
    }

    public String getStartTenancyDate() {
        return StartTenancyDate;
    }

    public String getEndTenancyDate() {
        return EndTenancyDate;
    }

    public String getGasMeterNo() {
        return GasMeterNo;
    }

    public String getElectricityMeterNo() {
        return ElectricityMeterNo;
    }

    public String getFurnished() {
        return Furnished;
    }

    public String getGasCertExpiryDate() {
        return GasCertExpiryDate;
    }

    public String getElectricityCertExpiryDate() {
        return ElectricityCertExpiryDate;
    }

    public String getGasMeterNoLocation() {
        return GasMeterNoLocation;
    }

    public String getElectricityMeterNoLocation() {
        return ElectricityMeterNoLocation;
    }

    public String getFuseboxLocation() {
        return FuseboxLocation;
    }

    public String getStopcockLocation() {
        return StopcockLocation;
    }

    public String getHomeFeaturesList() {
        return HomeFeaturesList;
    }

    public String getSocietyFeaturesList() {
        return SocietyFeaturesList;
    }

    public String getOtherFeaturesList() {
        return OtherFeaturesList;
    }

    public String getLatitude() {
        return Latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public String getZoomValue() {
        return ZoomValue;
    }

    public String getIsDeleted() {
        return IsDeleted;
    }

    public String getUpsize_ts() {
        return upsize_ts;
    }

    public String getPortalId() {
        return PortalId;
    }

    public String getCurrencyunit() {
        return currencyunit;
    }

    public String getUsdSalePrice() {
        return UsdSalePrice;
    }

    public String getUsdMonthlyRent() {
        return UsdMonthlyRent;
    }

    public String getUsdDeposit() {
        return UsdDeposit;
    }

    public String getIsPublished() {
        return IsPublished;
    }

    public String getProperty_QRCode() {
        return Property_QRCode;
    }

    public String getGeoLocation_QRCode() {
        return GeoLocation_QRCode;
    }

    public String getParent() {
        return Parent;
    }

    public String getGroupID() {
        return GroupID;
    }

    public String getIsFeatured() {
        return IsFeatured;
    }

    public String[] getStrings() {
        return strings;
    }

    @Override
    public void fromJson(JSONObject object) {
        try {
//            for (int i = 0; i < strings.length; i++) {
//                if(isEmptyString(strings[i])){
//                    this.strings[i] = "";
//                }else {
//                    this.strings[i] = "" + object.getString(strings[i]);
//                }
//            }
            this.PropertyID = object.getString("PropertyID");
            this.PropertyIdser = object.getString("PropertyIdser");
            this.UserId = object.getString("UserId");
            this.Title = object.getString("Title");
            this.Description = object.getString("Description");
            this.Url = object.getString("Url");
            this.PropertyFor = object.getString("PropertyFor");
            this.PropertyType = object.getString("PropertyType");
            this.ViewType = object.getString("ViewType");
            this.FloorNumber = object.getString("FloorNumber");
            this.TotalFloor = object.getString("TotalFloor");
            this.PropertySubType = object.getString("PropertySubType");
            this.KeyLandmark = object.getString("KeyLandmark");
            this.LandArea = object.getString("LandArea");
            this.LandAreaUnit = object.getString("LandAreaUnit");
            this.BuiltArea = object.getString("BuiltArea");
            this.BuiltAreaUnit = object.getString("BuiltAreaUnit");
            this.Possession = object.getString("Possession");
            this.TransactionType = object.getString("TransactionType");
            this.NoofBedrooms = object.getString("NoofBedrooms");
            this.NoofBathrooms = object.getString("NoofBathrooms");
            this.NoofKitchen = object.getString("NoofKitchen");
            this.NoofBalcony = object.getString("NoofBalcony");
            this.FaceView = object.getString("FaceView");
            this.PropertyAge = object.getString("PropertyAge");
            this.Location = object.getString("Location");
            this.City = object.getString("City");
            this.Postcode = object.getString("Postcode");
            this.PropertyOwnership = object.getString("PropertyOwnership");
            this.DistanceDetails = object.getString("DistanceDetails");
            this.AccountNo = object.getString("AccountNo");
            this.MonthlyRent = object.getString("MonthlyRent");
            this.SalePrice = object.getString("SalePrice");
            this.Deposit = object.getString("Deposit");
            this.RentDueDate = object.getString("RentDueDate");
            this.LengthOfTenancy = object.getString("LengthOfTenancy");
            this.StartTenancyDate = object.getString("StartTenancyDate");
            this.EndTenancyDate = object.getString("EndTenancyDate");
            this.GasMeterNo = object.getString("GasMeterNo");
            this.ElectricityMeterNo = object.getString("ElectricityMeterNo");
            this.Furnished = object.getString("Furnished");
            this.GasCertExpiryDate = object.getString("GasCertExpiryDate");
            this.ElectricityCertExpiryDate = object.getString("ElectricityCertExpiryDate");
            this.GasMeterNoLocation = object.getString("GasMeterNoLocation");
            this.ElectricityMeterNoLocation = object.getString("ElectricityMeterNoLocation");
            this.FuseboxLocation = object.getString("FuseboxLocation");
            this.StopcockLocation = object.getString("StopcockLocation");
            this.HomeFeaturesList = object.getString("HomeFeaturesList");
            this.SocietyFeaturesList = object.getString("SocietyFeaturesList");
            this.OtherFeaturesList = object.getString("OtherFeaturesList");
            this.Latitude = object.getString("Latitude");
            this.Longitude = object.getString("Longitude");
            this.ZoomValue = object.getString("ZoomValue");
            this.IsDeleted = object.getString("IsDeleted");
            this.upsize_ts = object.getString("upsize_ts");
            this.PortalId = object.getString("PortalId");
            this.currencyunit = object.getString("currencyunit");
            this.UsdSalePrice = object.getString("UsdSalePrice");
            this.UsdMonthlyRent = object.getString("UsdMonthlyRent");
            this.UsdDeposit = object.getString("UsdDeposit");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject toJson() {
        return null;
    }


}
