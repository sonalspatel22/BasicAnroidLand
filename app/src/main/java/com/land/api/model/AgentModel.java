package com.land.api.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Alpesh on 12/1/2017.
 */

public class AgentModel implements JsonModel {


    String C_ID;
    //String C_ID1;
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
    String AgentDetailID;
    String UserID;

    /*   public String getC_ID1() {
           return C_ID1;
       }*/
    String AgentID;
    String A_FirstName;
    String A_LastName;
    String A_Address;
    String A_City;
    String A_PostalCode;
    String A_EmailID;
    String A_Telephone;
    String A_Cell;
    String A_Password;
    String A_UserImage;
    String A_IsWhatsupEnable;
    String DISTANCE;
    String A_Latitude;
    String A_Longitude;
    String A_ZoomValue;
    String C_ID1;
    String C_Country;

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

    public String getC_ID() {
        return C_ID;
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

    public String getDISTANCE() {
        return DISTANCE;
    }

    public String getA_Latitude() {
        return A_Latitude;
    }

    public String getA_Longitude() {
        return A_Longitude;
    }

    public String getA_ZoomValue() {
        return A_ZoomValue;
    }

    public String getC_ID1() {
        return C_ID1;
    }

    public String getC_Country() {
        return C_Country;
    }

    @Override
    public void fromJson(JSONObject object) {

        try {
            //agent detail
            this.AgentDetailID = object.getString("AgentDetailID");
            this.UserID = object.getString("UserID");
            this.AgentID = object.getString("AgentId");
            this.A_FirstName = object.getString("A_FirstName");
            this.A_LastName = object.getString("A_LastName");
            this.A_Address = object.getString("A_Address");
            this.A_City = object.getString("A_City");
            this.A_PostalCode = object.getString("A_PostalCode");
            this.A_EmailID = object.getString("A_EmailID");
            this.A_Telephone = object.getString("A_Telephone");
            this.A_Cell = object.getString("A_Cell");
            this.A_Password = object.getString("A_Password");
            this.A_UserImage = object.getString("A_UserImage");
            this.A_IsWhatsupEnable = object.getString("A_IsWhatsupEnable");
            this.A_Latitude = object.getString("A_Latitude");
            this.A_Longitude = object.getString("A_Longitude");
            this.A_ZoomValue = object.getString("A_ZoomValue");
            this.C_ID = object.getString("C_ID");
            if (object.has("DISTANCE")) {
                this.DISTANCE = object.getString("DISTANCE");
            }
            if (object.has(object.getString("C_CompnayName"))) {
                //company
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
                this.C_Country = object.getString("C_Country");
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
