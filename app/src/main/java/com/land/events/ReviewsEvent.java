package com.land.events;

/**
 * Created by Dashrath on 11/18/2017.
 */

public class ReviewsEvent {

    public static final int ACTION_ADD = 0;


    private int action;

    public ReviewsEvent(int action) {
        this.action = action;
    }

    public int getAction() {
        return action;
    }
}
