package com.land.api.model;

import org.json.JSONException;
import org.json.JSONObject;

public class NearestPropertiesModel implements JsonModel {
    private String DISTANCE;
    private String PropertyID;
    private String Title;
    private String Latitude;
    private String Longitude;
    private String Location;
    private String UsdSalePrice;
    private String BuiltArea;
    private String GroupID;
    private String City;
    private String Deposit;
    private String Description;
    private String DistanceDetails;
    private String KeyLandmark;
    private String LandArea;
    private String MonthlyRent;
    private String NoofBalcony;
    private String NoofBedrooms;
    private String NoofBathrooms;
    private String NoofKitchen;
    private String PropertyFor;
    private String SalePrice;
    private String UsdDeposit;
    private String UsdMonthlyRent;
    private String ZoomValue;
    private String C_CompnayName;
    private String C_FName;
    private String PropertyType;
    private String A_AverageAgentRating;
    private String A_ReviewsCount;
    private String C_LName;
    private String C_RegisterNo;
    private String C_RegisterAddress;
    private String C_EmailID;
    private String C_City;
    private String C_State;
    private String C_Mobile;
    private String C_LandLine;
    private String C_PostalCode;
    private String C_UserImage;
    private String AgentDetailID;
    private String UserID;
    private String AgentID;
    private String A_FirstName;
    private String A_LastName;
    private String A_Description;
    private String A_Address;
    private String A_City;
    private String A_State;
    private String A_Country;
    private String A_PostalCode;
    private String A_EmailID;
    private String A_Telephone;
    private String A_Cell;
    private String A_Password;
    private String A_UserImage;
    private String A_IsWhatsupEnable;
    private String PropertyImage;
    private String TransactionType;

    public String getA_AverageAgentRating() {
        return A_AverageAgentRating;
    }

    public String getA_ReviewsCount() {
        return A_ReviewsCount;
    }

    public String getPropertyType() {
        return PropertyType;
    }

    public String getDISTANCE() {
        return DISTANCE;
    }

    public String getPropertyID() {
        return PropertyID;
    }

    public String getTitle() {
        return Title;
    }

    public String getLatitude() {
        return Latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public String getLocation() {
        return Location;
    }

    public String getUsdSalePrice() {
        return UsdSalePrice;
    }

    public String getBuiltArea() {
        return BuiltArea;
    }

    public String getGroupID() {
        return GroupID;
    }

    public String getCity() {
        return City;
    }

    public String getDeposit() {
        return Deposit;
    }

    public String getDescription() {
        return Description;
    }

    public String getDistanceDetails() {
        return DistanceDetails;
    }

    public String getKeyLandmark() {
        return KeyLandmark;
    }

    public String getLandArea() {
        return LandArea;
    }

    public String getMonthlyRent() {
        return MonthlyRent;
    }

    public String getNoofBalcony() {
        return NoofBalcony;
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

    public String getPropertyFor() {
        return PropertyFor;
    }

    public String getSalePrice() {
        return SalePrice;
    }

    public String getUsdDeposit() {
        return UsdDeposit;
    }

    public String getUsdMonthlyRent() {
        return UsdMonthlyRent;
    }

    public String getZoomValue() {
        return ZoomValue;
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

    public String getAgentDetailID() {
        return AgentDetailID;
    }

    public String getUserID() {
        return UserID;
    }

    public String getAgentID() {
        return AgentID;
    }

    public String getA_FirstName() {
        return A_FirstName;
    }

    public String getA_LastName() {
        return A_LastName;
    }

    public String getA_Description() {
        return A_Description;
    }

    public String getA_Address() {
        return A_Address;
    }

    public String getA_City() {
        return A_City;
    }

    public String getA_State() {
        return A_State;
    }

    public String getA_Country() {
        return A_Country;
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

    public String getA_Cell() {
        return A_Cell;
    }

    public String getA_Password() {
        return A_Password;
    }

    public String getA_UserImage() {
        return A_UserImage;
    }

    public String getA_IsWhatsupEnable() {
        return A_IsWhatsupEnable;
    }

    public String getPropertyImage() {
        return PropertyImage;
    }

    public String getTransactionType() {
        return TransactionType;
    }

    @Override
    public void fromJson(JSONObject object) {

        try {
            if (object.has("Distance")) {
                this.DISTANCE = object.getString("Distance");
            }
            if (object.has("DISTANCE")) {
                this.DISTANCE = object.getString("DISTANCE");
            }
            if (object.has("PropertyType")) {
                this.PropertyType = object.getString("PropertyType");
            }


            this.PropertyID = object.getString("PropertyID");
            this.Title = object.getString("Title");
            this.Description = object.getString("Description");
            this.PropertyFor = object.getString("PropertyFor");
            this.KeyLandmark = object.getString("KeyLandmark");
            this.LandArea = object.getString("LandArea");
            this.BuiltArea = object.getString("BuiltArea");
            this.NoofBedrooms = object.getString("NoofBedrooms");
            this.NoofBathrooms = object.getString("NoofBathrooms");
            this.NoofKitchen = object.getString("NoofKitchen");
            this.NoofBalcony = object.getString("NoofBalcony");
            this.Location = object.getString("Location");
            this.City = object.getString("City");
            this.DistanceDetails = object.getString("DistanceDetails");
            this.MonthlyRent = object.getString("MonthlyRent");
            this.SalePrice = object.getString("SalePrice");
            this.Deposit = object.getString("Deposit");
            this.Latitude = object.getString("Latitude");
            this.Longitude = object.getString("Longitude");
            this.ZoomValue = object.getString("ZoomValue");
            this.UsdSalePrice = object.getString("UsdSalePrice");
            this.UsdMonthlyRent = object.getString("UsdMonthlyRent");
            this.UsdDeposit = object.getString("UsdDeposit");
            this.TransactionType = object.getString("TransactionType");
            if (object.has("GroupID")) {
                this.GroupID = object.getString("GroupID");
            } else {
                this.GroupID = "0";
            }

            this.PropertyImage = object.getString("PropertyImage");
            this.AgentID = object.getString("AgentId");

            if (object.has("A_FirstName")) {
                this.AgentDetailID = object.getString("AgentDetailID");
                if (object.has("UserID")) {
                    this.UserID = object.getString("UserID");
                } else if (object.has("UserId")) {
                    this.UserID = object.getString("UserID");
                }

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
//                this.A_AverageAgentRating = object.getString("A_AverageAgentRating");
//                this.A_ReviewsCount = object.getString("A_ReviewsCount");
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


