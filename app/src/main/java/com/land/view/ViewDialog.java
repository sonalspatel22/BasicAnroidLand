package com.land.view;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.land.R;
import com.land.adapter.CallAdapter;

import java.util.ArrayList;

/**
 * Created by Alpesh on 12/26/2017.
 */

public class ViewDialog {
    Activity acontext;
    ArrayList<String> acalllist;
    GridLayoutManager mLayoutManager;

    public ViewDialog(Activity activity, ArrayList<String> calllist) {
        this.acalllist = calllist;
        this.acontext = activity;
    }

    public void showDialog() {
        final Dialog dialog = new Dialog(acontext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_calllist);
        ImageView buttonClose;
        RecyclerView rvCalllist;
        buttonClose = (ImageView) dialog.findViewById(R.id.button_close);
        rvCalllist = (RecyclerView) dialog.findViewById(R.id.rv_calllist);
        mLayoutManager = new GridLayoutManager(acontext, 2);
        rvCalllist.setLayoutManager(mLayoutManager);
        CallAdapter callAdapter = new CallAdapter(acontext, acalllist);
        rvCalllist.setAdapter(callAdapter);

        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

}
