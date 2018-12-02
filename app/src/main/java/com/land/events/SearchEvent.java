package com.land.events;

/**
 * Created by Alpesh on 12/5/2017.
 */

public class SearchEvent {

    public static int ACTION_SEARCH = 0;
    public static int ACTION_BACKPRESSED = 1;
    public static int ACTION_FILTER = 2;
    public static int ACTION_FILTER_RESULT = 3;
    public static int ACTION_CURR = 4;
    public static int ACTION_USER = 5;
    private int action = 0;
    private String data;


    public SearchEvent(int action, String data) {
        this.action = action;
        this.data = data;
    }

    public int getAction() {
        return action;
    }

    public String getData() {
        return data;
    }
}
