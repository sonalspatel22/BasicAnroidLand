package com.land.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.land.R;
import com.land.adapter.ListingAdapter;
import com.land.api.model.NearestPropertiesModel;
import com.land.helper.TintDrawableHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchListingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchListingFragment extends BaseDialogFragment {

    protected RecyclerView recyclerView;
    protected ListingAdapter listingAdapter;
    Toolbar toolbar;
    private List<NearestPropertiesModel> propertiesModels = new ArrayList<>();

    public SearchListingFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SearchListingFragment newInstance(List<NearestPropertiesModel> propertiesModels) {
        SearchListingFragment fragment = new SearchListingFragment();
        fragment.propertiesModels = propertiesModels;
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.MyActivityTheme) {
            @Override
            public void onBackPressed() {
                super.onBackPressed();
            }
        };
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_listing, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View rootView) {

        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(TintDrawableHelper.getTintedResource(getActivity(), android.support.design.R.drawable.abc_ic_ab_back_material, R.color.colorWhite));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);

        listingAdapter = new ListingAdapter(getActivity(), propertiesModels);
        recyclerView.setAdapter(listingAdapter);

    }


}
