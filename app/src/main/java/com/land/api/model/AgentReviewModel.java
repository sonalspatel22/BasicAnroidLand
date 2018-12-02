package com.land.api.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Alpesh on 1/11/2018.
 */

public class AgentReviewModel implements JsonModel {

    String RatingID;
    String AgentID;
    String Comments;
    String UserID;
    String RatingStars;
    String CreatedDate;
    String CreatedBy;
    String UpdateDate;
    String UpdateBy;
    String UserID1;
    String Username;


    public String getRatingID() {
        return RatingID;
    }

    public String getAgentID() {
        return AgentID;
    }

    public String getComments() {
        return Comments;
    }

    public String getUserID() {
        return UserID;
    }

    public String getRatingStars() {
        return RatingStars;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public String getUpdateDate() {
        return UpdateDate;
    }

    public String getUpdateBy() {
        return UpdateBy;
    }

    public String getUserID1() {
        return UserID1;
    }

    public String getUsername() {
        return Username;
    }

    @Override
    public void fromJson(JSONObject object) {
        try {
            this.RatingID = object.getString("RatingID");

            this.AgentID = object.getString("AgentId");
            this.Comments = object.getString("Comments");
            this.UserID = object.getString("UserID");
            this.RatingStars = object.getString("RatingStars");
            this.CreatedDate = object.getString("CreatedDate");
            this.UpdateDate = object.getString("UpdateDate");
            this.Username = object.getString("username");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject toJson() {
        return null;
    }
}
