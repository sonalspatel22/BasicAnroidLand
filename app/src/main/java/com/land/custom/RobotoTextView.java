package com.land.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.land.R;


public class RobotoTextView extends AppCompatTextView {
    private static final String sScheme = "http://schemas.android.com/apk/res-auto";


    public RobotoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        try {
            TypedArray attributeArray = context.obtainStyledAttributes(attrs, R.styleable.CustomFont);
            if (isInEditMode()) {
                return;
            } else {
                final String fontName = attributeArray.getString(R.styleable.CustomFont_font);

                if (fontName == null) {
                    throw new IllegalArgumentException("You must provide \"" + attributeArray.getString(R.styleable.CustomFont_font) + "\" for your text view");
                } else {
                    Typeface customTypeface = Typeface.createFromAsset(context.getAssets(), fontName);
                    setTypeface(customTypeface);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
