package com.land.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.land.R;

/**
 * Created by Dashrath on 1/5/2018.
 */

public class ToastUtil {

    public static Toast makeText(Context context, String message, int duration) {
        Toast toast = new Toast(context);
        View view = LayoutInflater.from(context).inflate(R.layout.view_my_toast, null, false);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(message);
        toast.setView(view);
        toast.setDuration(duration);

        return toast;
    }
}
