package com.land.api.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dashrath on 1/3/2018.
 */

public class SupplierReviewModel implements JsonModel {

    private String lr_id;
    private String lr_joints_id;
    private String lr_user_id;
    private String lr_mingle_user_id;
    private String lr_quality_star;
    private String lr_services_star;
    private String lr_convienancy_star;
    private String lr_message;
    private String lr_user_image;
    private String lr_user_name;
    private String lr_created_at;
    // private UserModel userModel;


    public String getLr_id() {
        return lr_id;
    }

    public String getLr_joints_id() {
        return lr_joints_id;
    }

    public String getLr_user_id() {
        return lr_user_id;
    }

    public String getLr_mingle_user_id() {
        return lr_mingle_user_id;
    }

    public String getLr_quality_star() {
        return lr_quality_star;
    }

    public String getLr_services_star() {
        return lr_services_star;
    }

    public String getLr_convienancy_star() {
        return lr_convienancy_star;
    }

    public String getLr_message() {
        return lr_message;
    }

    public String getLr_created_at() {
        return lr_created_at;
    }

    public String getLr_user_image() {
        return lr_user_image;
    }

    public String getLr_user_name() {
        return lr_user_name;
    }

    /* public UserModel getUserModel() {
        return userModel;
    }*/

    @Override
    public void fromJson(JSONObject object) {
        try {

            this.lr_id = object.getString("lr_id");
            this.lr_joints_id = object.getString("lr_joints_id");
            this.lr_user_id = object.getString("lr_user_id");
            this.lr_mingle_user_id = object.getString("lr_mingle_user_id");
            this.lr_quality_star = object.getString("lr_quality_star");
            this.lr_services_star = object.getString("lr_services_star");
            this.lr_convienancy_star = object.getString("lr_convienancy_star");
            this.lr_message = object.getString("lr_message");
            this.lr_created_at = object.getString("lr_created_at");

            this.lr_user_image = object.getString("lr_user_image");
            this.lr_user_name = object.getString("lr_user_name");
            Log.e("username", "" + object.getString("lr_user_name"));

            // this.userModel = new UserModel();
            //this.userModel.fromJson(object.getJSONObject("user_detail"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject toJson() {
        return null;
    }
}

