package com.land.api.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alpesh on 12/23/2017.
 */

public class SupplierModel implements JsonModel {

    String sc_id;
    String sc_name;
    List<SupplierCategoryItemsModel> Joints = new ArrayList<>();

    public String getSc_id() {
        return sc_id;
    }

    public String getSc_name() {
        return sc_name;
    }

    public List<SupplierCategoryItemsModel> getJoints() {
        return Joints;
    }


    @Override
    public void fromJson(JSONObject object) {
        try {
            this.sc_id = object.getString("sc_id");
            this.sc_name = object.getString("sc_name");

            if (object.has("Joints")) {
                JSONArray jsonArray = new JSONArray(object.getString("Joints"));
                for (int j = 0; j < jsonArray.length(); j++) {
                    SupplierCategoryItemsModel sm = new SupplierCategoryItemsModel();
                    sm.fromJson(jsonArray.getJSONObject(j));
                    this.Joints.add(sm);
                }
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
