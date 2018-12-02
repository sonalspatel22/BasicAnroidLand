package com.land.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.land.R;
import com.land.adapter.CountryCodeAdapter;


/**
 * Created by akshay on 18/10/16.
 */

public class CountryPickerFragment extends BaseDialogFragment {


    protected View rootView;
    protected RecyclerView recyclerView;
    protected CountryCodeAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_country_picker, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        mAdapter = new CountryCodeAdapter(getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter);

    }

}
