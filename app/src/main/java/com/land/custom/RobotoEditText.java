package com.land.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

import com.land.R;


/**
 * Created by akshay on 12/10/16.
 */

public class RobotoEditText extends EditText {

    private static final String sScheme = "http://schemas.android.com/apk/res-auto";

    public RobotoEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public RobotoEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        try {
            TypedArray attributeArray = getContext().obtainStyledAttributes(
                    attrs, R.styleable.CustomFont);
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

