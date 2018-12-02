package com.land.api.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Alpesh on 12/23/2017.
 */

public class SupplierCategoryItemsModel implements JsonModel {

    String l_id;
    String l_name;
    String l_detail;
    String l_mobile_no2;
    String l_mobile_no3;
    String l_mobile_no4;
    String l_contact_person;
    String l_c_code1;
    String l_c_code2;
    String l_c_code3;
    String l_c_code4;
    String l_mobile_no1;
    String l_email;
    String l_website;
    String l_address;
    String l_country;

    String l_state;
    String l_city;
    String l_zipcode;
    String l_main_image;
    String l_all_image;
    String l_mc_id;
    String l_c_id;
    String l_sc_id;
    String l_lp_id;
    String l_status;
    String l_user_id;
    //String l_on_mingle;
    String l_on_rees;
    String l_on_focus;
    String l_oc_time;
    String l_keywords;
    String l_keywords_all;
    String l_mingle_category;
    String l_latitude;
    String l_longitude;
    String l_confirmation_discount;
    String l_created_at;
    // String l_updated_at;


    public String getL_id() {
        return l_id;
    }

    public String getL_name() {
        return l_name;
    }

    public String getL_detail() {
        return l_detail;
    }

    public String getL_mobile_no2() {
        return l_mobile_no2;
    }

    public String getL_mobile_no3() {
        return l_mobile_no3;
    }

    public String getL_mobile_no4() {
        return l_mobile_no4;
    }

    public String getL_contact_person() {
        return l_contact_person;
    }

    public String getL_c_code1() {
        return l_c_code1;
    }

    public String getL_c_code2() {
        return l_c_code2;
    }

    public String getL_c_code3() {
        return l_c_code3;
    }

    public String getL_c_code4() {
        return l_c_code4;
    }

    public String getL_mobile_no1() {
        return l_mobile_no1;
    }

    public String getL_email() {
        return l_email;
    }

    public String getL_website() {
        return l_website;
    }

    public String getL_address() {
        return l_address;
    }

    public String getL_country() {
        return l_country;
    }

    public String getL_state() {
        return l_state;
    }

    public String getL_city() {
        return l_city;
    }

    public String getL_zipcode() {
        return l_zipcode;
    }

    public String getL_main_image() {
        return l_main_image;
    }

    public String getL_all_image() {
        return l_all_image;
    }

    public String getL_mc_id() {
        return l_mc_id;
    }

    public String getL_c_id() {
        return l_c_id;
    }

    public String getL_sc_id() {
        return l_sc_id;
    }

    public String getL_lp_id() {
        return l_lp_id;
    }

    public String getL_status() {
        return l_status;
    }

    public String getL_user_id() {
        return l_user_id;
    }

   /* public String getL_on_mingle() {
        return l_on_mingle;
    }*/

    public String getL_on_rees() {
        return l_on_rees;
    }

    public String getL_on_focus() {
        return l_on_focus;
    }

    public String getL_oc_time() {
        return l_oc_time;
    }

    public String getL_keywords() {
        return l_keywords;
    }

    public String getL_keywords_all() {
        return l_keywords_all;
    }

    public String getL_mingle_category() {
        return l_mingle_category;
    }

    public String getL_latitude() {
        return l_latitude;
    }

    public String getL_longitude() {
        return l_longitude;
    }

    public String getL_confirmation_discount() {
        return l_confirmation_discount;
    }

    public String getL_created_at() {
        return l_created_at;
    }

    /*public String getL_updated_at() {
        return l_updated_at;
    }*/

    @Override
    public void fromJson(JSONObject object) {
        try {
            this.l_id = object.getString("l_id");
            this.l_name = object.getString("l_name");
            this.l_detail = object.getString("l_detail");
            this.l_mobile_no2 = object.getString("l_mobile_no2");
            this.l_mobile_no3 = object.getString("l_mobile_no3");
            this.l_mobile_no4 = object.getString("l_mobile_no4");
            this.l_contact_person = object.getString("l_contact_person");
            this.l_c_code1 = object.getString("l_c_code1");
            this.l_c_code2 = object.getString("l_c_code2");
            this.l_c_code3 = object.getString("l_c_code3");
            this.l_c_code4 = object.getString("l_c_code4");
            this.l_mobile_no1 = object.getString("l_mobile_no1");
            this.l_email = object.getString("l_email");
            this.l_website = object.getString("l_website");
            this.l_address = object.getString("l_address");
            this.l_country = object.getString("l_country");
            this.l_state = object.getString("l_state");
            this.l_city = object.getString("l_city");
            this.l_zipcode = object.getString("l_zipcode");
            this.l_main_image = object.getString("l_main_image");
            this.l_all_image = object.getString("l_all_image");
            this.l_mc_id = object.getString("l_mc_id");
            this.l_c_id = object.getString("l_c_id");
            this.l_sc_id = object.getString("l_sc_id");
            this.l_lp_id = object.getString("l_lp_id");
            this.l_status = object.getString("l_status");
            this.l_user_id = object.getString("l_user_id");
            //this.l_on_mingle = object.getString("l_on_mingle");
            this.l_on_rees = object.getString("l_on_rees");
            this.l_on_focus = object.getString("l_on_focus");
            this.l_on_focus = object.getString("l_on_focus");
            this.l_oc_time = object.getString("l_oc_time");
            this.l_keywords = object.getString("l_keywords");
            this.l_keywords_all = object.getString("l_keywords_all");
            this.l_mingle_category = object.getString("l_mingle_category");
            this.l_latitude = object.getString("l_latitude");
            this.l_longitude = object.getString("l_longitude");
            this.l_confirmation_discount = object.getString("l_confirmation_discount");
            this.l_created_at = object.getString("l_created_at");
            //this.l_updated_at = object.getString("l_updated_at");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject toJson() {
        return null;
    }
}
