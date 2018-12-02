package com.land.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.land.R;
import com.land.adapter.AdaptersupplierCategory;
import com.land.api.model.SupplierCategoryItemsModel;

import java.util.ArrayList;
import java.util.List;


public class SupplierCategoryFragment extends Fragment {

    protected RecyclerView recyclerView;
    LinearLayoutManager manager;
    List<SupplierCategoryItemsModel> supplierCategoryItemsModel = new ArrayList<>();
    AdaptersupplierCategory adaptersupplierCategory;
    boolean visible = false;

    public SupplierCategoryFragment() {
        // Required empty public constructor
    }

    public static SupplierCategoryFragment newInstance(List<SupplierCategoryItemsModel> supplierCategoryItemsModels) {
        SupplierCategoryFragment fragment = new SupplierCategoryFragment();
        fragment.supplierCategoryItemsModel = new ArrayList<>();
        fragment.supplierCategoryItemsModel = supplierCategoryItemsModels;
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            visible = true;
        }
    }

    public void update() {
        manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        adaptersupplierCategory = new AdaptersupplierCategory(getActivity(), supplierCategoryItemsModel);
        recyclerView.setAdapter(adaptersupplierCategory);
        // do your stuff
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_supplier_category, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        initView();
        if (visible) {
            manager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(manager);
            adaptersupplierCategory = new AdaptersupplierCategory(getActivity(), supplierCategoryItemsModel);
            recyclerView.setAdapter(adaptersupplierCategory);
        }
    }

    private void initView() {
        manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        adaptersupplierCategory = new AdaptersupplierCategory(getActivity(), supplierCategoryItemsModel);
        recyclerView.setAdapter(adaptersupplierCategory);
    }

}
