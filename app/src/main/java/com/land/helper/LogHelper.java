package com.land.helper;

/**
 * Created by Dashrath on 10/10/2017.
 */

public class LogHelper {

    public static final boolean DEBUGGING_MODE = true;

    public static final void e(String tag, String message) {
        if (DEBUGGING_MODE) {
            android.util.Log.e(tag, message);
        }
    }

    public static final void d(String tag, String message) {
        if (DEBUGGING_MODE) {
            android.util.Log.d("" + tag, "" + message);
        }
    }

    public static final void v(String tag, String message) {
        if (DEBUGGING_MODE) {
            android.util.Log.v(tag, message);
        }
    }

    public static void i(String tag, String message) {
        if (DEBUGGING_MODE) {
            android.util.Log.i(tag, message);
        }

    }

}


