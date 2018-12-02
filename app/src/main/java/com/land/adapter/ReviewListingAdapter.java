package com.land.adapter;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.land.R;
import com.land.api.model.AgentReviewModel;
import com.land.custom.RobotoTextView;
import com.land.view.CircleImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Alpesh on 1/11/2018.
 */

public class ReviewListingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Handler handler;
    Call lastCall;
    private LayoutInflater mInflater;
    private Activity context;
    private List<AgentReviewModel> mItems = new ArrayList<>();
    private String tag = null;

    public ReviewListingAdapter(Activity context, List<AgentReviewModel> propertiesModels) {
        this.context = context;
        this.mItems = propertiesModels;
        mInflater = android.view.LayoutInflater.from(context);
        handler = new Handler(Looper.getMainLooper());
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == AdapterUtil.VIEW_EMPTY) {
            return new AdapterUtil.EmptyViewHolder(mInflater.inflate(viewType, parent, false), "No post to show");
        } else {
            return new ReviewListingAdapter.ReviewListingHolder(mInflater.inflate(R.layout.item_reviewlistingagent, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder.getItemViewType() == AdapterUtil.VIEW_EMPTY) {
            return;
        } else {
            ((ReviewListingAdapter.ReviewListingHolder) holder).bind(mItems.get(position));
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
        if (mItems.size() > 0) {
            return mItems.size();
        }
        return 0;

    }

    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd MMM yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    class ReviewListingHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private View itemView;

        private CardView cardOne;
        private LinearLayout ll1;
        private CircleImageView image;
        private RobotoTextView txtName;
        private RobotoTextView txtdate;
        private RobotoTextView txtComment;
        private RobotoTextView txtOtherdetail;
        private RatingBar ratingBar;


        public ReviewListingHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.itemView.setOnClickListener(this);
            cardOne = (CardView) itemView.findViewById(R.id.card_one);
            ll1 = (LinearLayout) itemView.findViewById(R.id.ll1);
            image = (CircleImageView) itemView.findViewById(R.id.image);
            txtName = (RobotoTextView) itemView.findViewById(R.id.txt_name);
            txtdate = (RobotoTextView) itemView.findViewById(R.id.txt_date);
            txtComment = (RobotoTextView) itemView.findViewById(R.id.txt_comment);
            txtOtherdetail = (RobotoTextView) itemView.findViewById(R.id.txt_otherdetail);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingstar);
        }

        public void bind(AgentReviewModel mItem) {
            txtName.setText(mItem.getUsername());
            txtComment.setText(mItem.getComments());
            String[] date = mItem.getUpdateDate().split("T");

            txtdate.setText(parseDateToddMMyyyy(date[0]));
            ratingBar.setRating(Integer.parseInt(mItem.getRatingStars()));
            itemView.setTag(getLayoutPosition());
        }


        @Override
        public void onClick(View v) {
            int pos = (int) v.getTag();
        }
    }

}
