package com.land.helper;

/**
 * Created by Alpesh on 12/26/2017.
 */

public class StringHelper {

    public static boolean isEmptyString(String text) {
        return (text == null || text.trim().equals("null") || text.trim().length() <= 0);
    }
}
