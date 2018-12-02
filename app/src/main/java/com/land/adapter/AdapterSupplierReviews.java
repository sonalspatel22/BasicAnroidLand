package com.land.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.land.R;
import com.land.api.model.SupplierReviewModel;
import com.land.custom.RobotoTextView;
import com.land.helper.LogHelper;
import com.land.utils.DateConvertUtil;
import com.land.view.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Dashrath on 1/3/2018.
 */

public class AdapterSupplierReviews extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private List<SupplierReviewModel> mItems;
    private LayoutInflater mInflater;


    public AdapterSupplierReviews(Context context, List<SupplierReviewModel> list) {
        mInflater = LayoutInflater.from(context);
        this.mItems = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == AdapterUtil.VIEW_EMPTY) {
            return new AdapterUtil.EmptyViewHolder(mInflater.inflate(viewType, parent, false), "No Review to show");
        } else {
            return new ReviewHolder(mInflater.inflate(R.layout.items_reviews, parent, false), this);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder.getItemViewType() == AdapterUtil.VIEW_EMPTY) {
            return;
        } else {
            ((ReviewHolder) holder).bind(mItems.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (mItems.size() == 0) {
            return AdapterUtil.VIEW_EMPTY;
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public int getItemCount() {
        int size = mItems.size();

        if (size == 0) {
            size = 0;
        }
        return size;
    }


    @Override
    public void onClick(View view) {

    }


    static class ReviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected CircleImageView imageView;
        protected RobotoTextView textViewName;
        protected RobotoTextView txt_date;
        protected RobotoTextView txt_quality;
        protected RobotoTextView txt_services;
        protected RobotoTextView txt_convienancy;
        protected RobotoTextView txt_review;


        public ReviewHolder(View itemView, View.OnClickListener listener) {
            super(itemView);

            imageView = (CircleImageView) itemView.findViewById(R.id.imageView);
            textViewName = (RobotoTextView) itemView.findViewById(R.id.textView_name);
            txt_date = (RobotoTextView) itemView.findViewById(R.id.txt_date);
            txt_quality = (RobotoTextView) itemView.findViewById(R.id.txt_quality);
            txt_services = (RobotoTextView) itemView.findViewById(R.id.txt_services);
            txt_convienancy = (RobotoTextView) itemView.findViewById(R.id.txt_convienancy);
            txt_review = (RobotoTextView) itemView.findViewById(R.id.txt_review);
        }

        public void bind(SupplierReviewModel model) {
            if (!model.getLr_user_image().equals(""))
                Picasso.with(this.imageView.getContext()).load(model.getLr_user_image()).placeholder(R.color.gray).fit().into(this.imageView);

            LogHelper.e("username", "" + model.getLr_user_name());
            this.textViewName.setText("" + model.getLr_user_name());
            this.imageView.setTag(getLayoutPosition());

            txt_date.setText("" + DateConvertUtil.convertToServerDatePostDate(model.getLr_created_at()));
            txt_quality.setText("" + model.getLr_quality_star());
            txt_services.setText("" + model.getLr_services_star());
            txt_convienancy.setText("" + model.getLr_convienancy_star());
            txt_review.setText("" + model.getLr_message());
        }

        @Override
        public void onClick(View view) {


        }
    }
}

