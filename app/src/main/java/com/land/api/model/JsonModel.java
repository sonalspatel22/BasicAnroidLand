package com.land.api.model;

import org.json.JSONObject;

/**
 * Created by Dashrath on 10/26/2017.
 */

public interface JsonModel {

    public void fromJson(JSONObject object);

    public JSONObject toJson();
}
