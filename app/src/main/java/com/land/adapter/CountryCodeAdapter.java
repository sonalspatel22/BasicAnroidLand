package com.land.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;


import com.land.R;
import com.land.api.model.CountryCodeModel;
import com.land.events.CountryCodeEvent;
import com.land.utils.CountryCodeUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by akshay on 18/10/16.
 */

public class CountryCodeAdapter extends RecyclerView.Adapter<CountryCodeAdapter.CountryCodeHolder> {

    private static String mSelectedItemName = "";
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<CountryCodeModel> mItems;

    public CountryCodeAdapter(Context mContext) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(this.mContext);

        mItems = CountryCodeUtil.getAllCountryCode(mContext);


    }

    @Override
    public CountryCodeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CountryCodeHolder(mInflater.inflate(R.layout.item_country_code, parent, false));
    }

    @Override
    public void onBindViewHolder(CountryCodeHolder holder, int position) {

        holder.onBind(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    static class CountryCodeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mNameText;
        private TextView mCodeText;
        private CheckBox mCheckBox;

        private CountryCodeModel mCountryCodeModel;

        public CountryCodeHolder(View itemView) {
            super(itemView);

            this.mNameText = (TextView) itemView.findViewById(R.id.textView_name);
            this.mCodeText = (TextView) itemView.findViewById(R.id.textView_code);
            this.mCheckBox = (CheckBox) itemView.findViewById(R.id.checkBox);

            itemView.setOnClickListener(this);


        }


        void onBind(CountryCodeModel model) {
            this.mCountryCodeModel = model;

            this.mNameText.setText(model.getName());
            this.mCodeText.setText(model.getCode());
            this.mCheckBox.setChecked(model.getName().equalsIgnoreCase(mSelectedItemName));

        }

        @Override
        public void onClick(View view) {

            CountryCodeEvent event = new CountryCodeEvent(mCountryCodeModel);
            EventBus.getDefault().post(event);
        }
    }


}
