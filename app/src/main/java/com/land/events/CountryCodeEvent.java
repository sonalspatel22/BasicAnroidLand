package com.land.events;


import com.land.api.model.CountryCodeModel;
import com.land.api.model.SubcCountryCodeModel;

/**
 * Created by akshay on 18/10/16.
 */

public class CountryCodeEvent {


    public static final int ACTION_SELECT_FOR_LOGIN = 0;
    public static final int ACTION_SELECT_FOR_SIGNUP = 1;
    public static final int ACTION_SELECT_FOR_FORGOT_PASSWORD = 2;

    private CountryCodeModel model;
    private SubcCountryCodeModel subcModel;

    public CountryCodeEvent(CountryCodeModel model) {
        this.model = model;
    }

    public CountryCodeEvent(SubcCountryCodeModel subcModel) {
        this.subcModel = subcModel;
    }

    public CountryCodeModel getModel() {
        return model;
    }

    public SubcCountryCodeModel getSubcModel() {
        return subcModel;
    }
}
