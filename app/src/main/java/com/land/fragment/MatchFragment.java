package com.land.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.land.R;
import com.land.constant.UrlConstant;


public class MatchFragment extends Fragment {

    public MatchFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MatchFragment newInstance() {
        MatchFragment fragment = new MatchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        UrlConstant.fragmenttag = 4;
        return inflater.inflate(R.layout.fragment_match, container, false);
    }

}
