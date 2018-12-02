package com.land.api.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Alpesh on 12/2/2017.
 */

public class AgentDetailModel implements JsonModel {

    String C_ID;
    String CompnayName;
    String C_FName;
    String C_LName;
    String RegisterNo;
    String RegisterAddress;
    String EmailID;
    String City;
    String State;
    String Mobile;
    String LandLine;
    String PostalCode;
    String UserImage;
    String AgentDetailID;
    String UserID;
    String AgentID;
    String FirstName;
    String LastName;
    String Address;
    String City1;
    String PostalCode1;
    String EmailID1;
    String Telephone;
    String Cell;
    String Password;
    String UserImage1;
    String C_ID1;
    String IsWhatsupEnable;

    @Override
    public void fromJson(JSONObject object) {
        try {
            this.CompnayName = object.getString("CompnayName");
            this.C_FName = object.getString("C_FName");
            this.C_LName = object.getString("C_LName");
            this.RegisterNo = object.getString("RegisterNo");
            this.RegisterAddress = object.getString("RegisterAddress");
            this.EmailID = object.getString("EmailID");
            this.City = object.getString("City");
            this.State = object.getString("State");
            this.Mobile = object.getString("Mobile");
            this.LandLine = object.getString("LandLine");
            this.PostalCode = object.getString("PostalCode");
            this.UserImage = object.getString("UserImage");
            this.AgentDetailID = object.getString("AgentDetailID");
            this.UserID = object.getString("UserID");
            this.AgentID = object.getString("AgentId");
            this.FirstName = object.getString("FirstName");
            this.LastName = object.getString("LastName");
            this.Address = object.getString("Address");
            this.City1 = object.getString("City1");
            this.PostalCode1 = object.getString("PostalCode1");
            this.EmailID1 = object.getString("EmailID1");
            this.Telephone = object.getString("Telephone");
            this.Cell = object.getString("Cell");
            this.Password = object.getString("Password");
            this.UserImage1 = object.getString("UserImage1");
            this.C_ID1 = object.getString("C_ID1");
            this.IsWhatsupEnable = object.getString("IsWhatsupEnable");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject toJson() {
        return null;
    }
}
