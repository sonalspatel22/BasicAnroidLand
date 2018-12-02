package com.land.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;

import com.land.R;

/**
 * Created by Dashrath on 10/4/2017.
 */

public class RobotoRadioButton extends AppCompatRadioButton {

    private static final String sScheme = "http://schemas.android.com/apk/res-auto";

    public RobotoRadioButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public RobotoRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        try {
            TypedArray attributeArray = getContext().obtainStyledAttributes(
                    attrs,
                    R.styleable.CustomFont);
            if (isInEditMode()) {
                return;
            } else {
                final String fontName = attributeArray.getString(R.styleable.CustomFont_font);

                if (fontName == null) {
                    throw new IllegalArgumentException("You must provide \"" + attributeArray.getString(R.styleable.CustomFont_font) + "\" for your text view");
                } else {
                    Typeface customTypeface = Typeface.createFromAsset(context.getAssets(), fontName);
                    ;
                    setTypeface(customTypeface);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

