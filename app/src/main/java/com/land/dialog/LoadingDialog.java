package com.land.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.land.R;


/**
 * Created by akshay on 12/12/16.
 */

public class LoadingDialog extends Dialog {

    private Animation mAnimeHeartBeat;
    private ImageView mImageView;
    private TextView mTextView;

    public LoadingDialog(Context context) {
        super(context, R.style.My_DialogTheme);
        setContentView(R.layout.dialog_loading);
        mTextView = (TextView) findViewById(R.id.textView);
        mImageView = (ImageView) findViewById(R.id.imageView);
        mAnimeHeartBeat = AnimationUtils.loadAnimation(context,
                R.anim.hearbeat);
    }

    public void setMessage(String message) {
        mTextView.setText(message);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mImageView.startAnimation(mAnimeHeartBeat);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mImageView.clearAnimation();
        mAnimeHeartBeat.cancel();
        mAnimeHeartBeat.reset();
    }

    @Override
    public void dismiss() {
        try {
            super.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cancel() {
        super.cancel();
    }
}
