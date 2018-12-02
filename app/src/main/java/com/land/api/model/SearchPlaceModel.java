package com.land.api.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Alpesh on 1/16/2018.
 */

public class SearchPlaceModel implements JsonModel {
    String description;
    String id;

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    @Override
    public void fromJson(JSONObject object) {
        try {
            this.description = object.getString("description");
            this.id = object.getString("place_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject toJson() {
        return null;
    }
}
