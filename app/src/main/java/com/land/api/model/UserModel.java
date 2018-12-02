package com.land.api.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Alpesh on 1/19/2018.
 */

public class UserModel implements JsonModel {
    String U_name;
    String U_id;
    String U_mobileno;

    public String getU_name() {
        return U_name;
    }

    public void setU_name(String u_name) {
        U_name = u_name;
    }

    public String getU_id() {
        return U_id;
    }

    public void setU_id(String u_id) {
        U_id = u_id;
    }

    public String getU_mobileno() {
        return U_mobileno;
    }

    public void setU_mobileno(String u_mobileno) {
        U_mobileno = u_mobileno;
    }

    @Override
    public void fromJson(JSONObject object) {
        try {
            this.U_name = object.getString("");
            this.U_id = object.getString("");
            this.U_mobileno = object.getString("");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject toJson() {
        return null;
    }
}
