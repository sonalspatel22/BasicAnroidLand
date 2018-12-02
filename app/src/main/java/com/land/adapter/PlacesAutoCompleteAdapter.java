package com.land.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.land.Place.PlaceAPI;
import com.land.api.model.SearchPlaceModel;

import java.util.ArrayList;

/**
 * Created by Alpesh on 1/16/2018.
 */

public class PlacesAutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {
    Context mContext;
    int mResource;
    ArrayList<SearchPlaceModel> resultList;
    PlaceAPI mPlaceAPI = new PlaceAPI();

    public PlacesAutoCompleteAdapter(Context context, int resource) {
        super(context, resource);
        mContext = context;
        mResource = resource;
    }

    @Override
    public int getCount() {
        // Last item will be the footer
        return resultList.size();
    }

    @Override
    public String getItem(int position) {
        return resultList.get(position).getDescription();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    resultList = mPlaceAPI.autocomplete(constraint.toString());
                    filterResults.values = resultList;
                    filterResults.count = resultList.size();
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };

        return filter;
    }
}