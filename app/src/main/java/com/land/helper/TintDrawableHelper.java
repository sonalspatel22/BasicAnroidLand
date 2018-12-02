package com.land.helper;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatDelegate;

import com.land.R;

/**
 * Created by Dashrath on 11/8/2017.
 */

public class TintDrawableHelper {

    public static Drawable getTintedResource(Context context, int drawableId, int colorId) {
        Drawable drawable = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = context.getResources().getDrawable(android.support.design.R.drawable.abc_ic_ab_back_material, context.getTheme());
        } else {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
            //noinspection deprecation
            drawable = context.getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            DrawableCompat.setTint(drawable, context.getResources().getColor(colorId, context.getTheme()));
        } else {
            DrawableCompat.setTint(drawable, context.getResources().getColor(colorId));
        }

        return drawable;
    }
}
