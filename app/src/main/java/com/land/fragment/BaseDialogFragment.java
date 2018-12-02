package com.land.fragment;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

public class BaseDialogFragment extends DialogFragment {

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
    }


    @Override
    public void dismiss() {


        if (isResumed()) {
            super.dismiss();
        } else {
            dismissAllowingStateLoss();
        }
    }
}
