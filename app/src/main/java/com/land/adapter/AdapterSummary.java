package com.land.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.land.R;
import com.land.custom.RobotoTextView;

import java.util.HashMap;

import static com.land.helper.StringHelper.isEmptyString;

public class AdapterSummary extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public HashMap<String, String> Summery = new HashMap<>();
    private LayoutInflater mInflater;
    private Context context;
    private String SummeryArray[] = {"Price", "Property Type", "View Type", "City", "Location", "Key Landmark", "Land/Carpet Area", "Bedrooms", "Bathrooms", "Kitchen", "Balcony", "Floor Number", "Residential Type", "Possession"};


    public AdapterSummary(Context context, HashMap<String, String> Summery) {
        this.context = context;
        this.Summery = Summery;
        mInflater = android.view.LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == AdapterUtil.VIEW_EMPTY) {
            return new AdapterUtil.EmptyViewHolder(mInflater.inflate(viewType, parent, false), "No Summery to show");
        } else {
            return new SummaryHolder(mInflater.inflate(R.layout.items_summery, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder.getItemViewType() == AdapterUtil.VIEW_EMPTY) {
            return;
        } else {

            ((SummaryHolder) holder).bind(Summery);
        }
    }

    @Override
    public int getItemViewType(int position) {

       /* if (mItems.size() == 0) {
            return AdapterUtil.VIEW_EMPTY;
        } else {*/
        return super.getItemViewType(position);
        //}
    }

    @Override
    public int getItemCount() {
        int size = Summery.size();
        if (size == 0) {
            size = 1;
        }
        return size;
    }


    class SummaryHolder extends RecyclerView.ViewHolder {
        private View itemView;
        private RobotoTextView txt_key, txt_value;

        public SummaryHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.txt_key = (RobotoTextView) itemView.findViewById(R.id.txt_key);
            this.txt_value = (RobotoTextView) itemView.findViewById(R.id.txt_value);

        }

        public void bind(HashMap<String, String> Summery) {
            this.itemView.setTag(getLayoutPosition());

            String firstKey = SummeryArray[getLayoutPosition()];
            String valueForFirstKey = Summery.get(firstKey);
            this.txt_key.setText("" + firstKey);
            if (isEmptyString(valueForFirstKey)) {
                this.txt_value.setText("-");
            } else {
                this.txt_value.setText("" + valueForFirstKey);
            }

            int color = getLayoutPosition() % 2 == 0 ? itemView.getContext().getResources().getColor(R.color.summery_item_dark) : itemView.getContext().getResources().getColor(R.color.summery_item_light);
            itemView.setBackgroundColor(color);

        }


    }
}
