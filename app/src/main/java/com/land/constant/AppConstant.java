package com.land.constant;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import com.land.api.model.FilterModel;
import com.land.api.model.SearchPlaceModel;

import java.util.ArrayList;

/**
 * Created by Dashrath on 12/29/2017.
 */

public class AppConstant {
    public static ArrayList<SearchPlaceModel> searchPlaceModel = new ArrayList<>();
    public static FilterModel filterModel = new FilterModel();
    public static int MODEPRIVEATE = 0;
    public static boolean isuser = false;

    public static void hidesoftkeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }


}
