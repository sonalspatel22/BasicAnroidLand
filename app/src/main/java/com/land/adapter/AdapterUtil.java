package com.land.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.land.R;

/**
 * Created by Dashrath on 10/8/2017.
 */

public class AdapterUtil {

    public static final int VIEW_EMPTY = R.layout.item_empty_view;

    public static class EmptyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        //for full page & centered text
        public EmptyViewHolder(View itemView, String message) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.textView);
            this.textView.setText(message);
        }


        //for only one line text
        public EmptyViewHolder(Context context, String message) {

            super(LayoutInflater.from(context).inflate(VIEW_EMPTY, null, false));
            this.textView = (TextView) itemView.findViewById(R.id.textView);
            this.textView.setText(message);
        }
    }
}
