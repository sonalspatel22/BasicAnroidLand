package com.land.api.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Alpesh on 12/14/2017.
 */

public class ACPModel implements JsonModel {
    //NearestPropertiesModel
    String DISTANCE;
    String UserID;
    String AgentID;
    String A_State;
    String A_Country;

    //NearestPropertyModel
    String Flag;
    String Distance;
    String KeyName;

    //PropertyModel
    //No Changes

    //ACP
    String PropertyID;
    String PropertyIdser;
    String AgentId;
    String UserId;
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


    String AgentDetailID;
    String AgentID1;
    String A_FirstName;
    String A_LastName;
    String A_Address;
    String A_City;
    String A_PostalCode;
    String A_EmailID;
    String A_Telephone;
    String A_Description;
    String A_AverageAgentRating;


    String A_ReviewsCount;
    String UserID1;
    String A_Cell;
    String A_Password;
    String A_UserImage;
    String C_ID;
    String A_IsWhatsupEnable;
    String C_ID1;
    String C_CompnayName;
    String C_FName;
    String C_LName;
    String C_RegisterNo;
    String C_RegisterAddress;
    String C_EmailID;
    String C_City;
    String C_State;
    String C_Mobile;
    String C_LandLine;
    String C_PostalCode;
    String C_UserImage;
    String PropertyImage;

    public String getPropertyImage() {
        return PropertyImage;
    }

    public String getPropertyID() {
        return PropertyID;
    }

    public String getPropertyIdser() {
        return PropertyIdser;
    }

    public String getAgentId() {
        return AgentId;
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

    public String getAgentDetailID() {
        return AgentDetailID;
    }

    public String getUserID1() {
        return UserID1;
    }

    public String getAgentID1() {
        return AgentID1;
    }

    public String getA_FirstName() {
        return A_FirstName;
    }

    public String getA_LastName() {
        return A_LastName;
    }

    public String getA_Address() {
        return A_Address;
    }

    public String getA_City() {
        return A_City;
    }

    public String getA_PostalCode() {
        return A_PostalCode;
    }

    public String getA_EmailID() {
        return A_EmailID;
    }

    public String getA_Telephone() {
        return A_Telephone;
    }

    public String getA_Description() {
        return A_Description;
    }

    public String getA_AverageAgentRating() {
        return A_AverageAgentRating;
    }

    public String getA_ReviewsCount() {
        return A_ReviewsCount;
    }


    public String getA_Cell() {
        return A_Cell;
    }

    public String getA_Password() {
        return A_Password;
    }

    public String getA_UserImage() {
        return A_UserImage;
    }

    public String getC_ID() {
        return C_ID;
    }

    public String getA_IsWhatsupEnable() {
        return A_IsWhatsupEnable;
    }

    public String getC_ID1() {
        return C_ID1;
    }

    public String getC_CompnayName() {
        return C_CompnayName;
    }

    public String getC_FName() {
        return C_FName;
    }

    public String getC_LName() {
        return C_LName;
    }

    public String getC_RegisterNo() {
        return C_RegisterNo;
    }

    public String getC_RegisterAddress() {
        return C_RegisterAddress;
    }

    public String getC_EmailID() {
        return C_EmailID;
    }

    public String getC_City() {
        return C_City;
    }

    public String getC_State() {
        return C_State;
    }

    public String getC_Mobile() {
        return C_Mobile;
    }

    public String getC_LandLine() {
        return C_LandLine;
    }

    public String getC_PostalCode() {
        return C_PostalCode;
    }

    public String getC_UserImage() {
        return C_UserImage;
    }

    @Override
    public void fromJson(JSONObject object) {
        try {
            if (object.has("PropertyImage")) {
                this.PropertyImage = object.getString("PropertyImage");
            }
            if (object.has("PropertyID")) {
                this.PropertyID = object.getString("PropertyID");
                this.PropertyIdser = object.getString("PropertyIdser");
                this.AgentId = object.getString("AgentId");
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
                this.ElectricityMeterNoLocation = object.getString("ElectricityMeterNoLocation");
                this.GasMeterNoLocation = object.getString("GasMeterNoLocation");
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
                this.IsPublished = object.getString("IsPublished");
                this.Property_QRCode = object.getString("Property_QRCode");
                this.GeoLocation_QRCode = object.getString("GeoLocation_QRCode");
                this.Parent = object.getString("Parent");
                this.GroupID = object.getString("GroupID");
                this.IsFeatured = object.getString("IsFeatured");
                //agent &company details
                this.AgentDetailID = object.getString("AgentDetailID");
                if (object.has(object.getString("UserID1"))) {
                    this.UserID1 = object.getString("UserID1");
                }
                this.AgentID1 = object.getString("AgentId");
                this.A_FirstName = object.getString("A_FirstName");
                this.A_LastName = object.getString("A_LastName");
                this.A_Address = object.getString("A_Address");
                this.A_City = object.getString("A_City");
                this.A_PostalCode = object.getString("A_PostalCode");
                this.A_EmailID = object.getString("A_EmailID");
                this.A_Telephone = object.getString("A_Telephone");
                this.A_Description = object.getString("A_Description");
                this.A_Cell = object.getString("A_Cell");
                this.A_Password = object.getString("A_Password");
                this.A_UserImage = object.getString("A_UserImage");
                this.A_IsWhatsupEnable = object.getString("A_IsWhatsupEnable");
                this.C_ID = object.getString("C_ID");
                this.C_ID1 = object.getString("C_ID1");
                this.C_CompnayName = object.getString("C_CompnayName");
                this.C_FName = object.getString("C_FName");
                this.C_LName = object.getString("C_LName");
                this.C_RegisterNo = object.getString("C_RegisterNo");
                this.C_RegisterAddress = object.getString("C_RegisterAddress");
                this.C_EmailID = object.getString("C_EmailID");
                this.C_City = object.getString("C_City");
                this.C_State = object.getString("C_State");
                this.C_Mobile = object.getString("C_Mobile");
                this.C_LandLine = object.getString("C_LandLine");
                this.C_PostalCode = object.getString("C_PostalCode");
                this.C_UserImage = object.getString("C_UserImage");
                this.A_AverageAgentRating = object.getString("A_AverageAgentRating");
                this.A_ReviewsCount = object.getString("A_ReviewsCount");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override
    public JSONObject toJson() {
        return null;
    }
}
