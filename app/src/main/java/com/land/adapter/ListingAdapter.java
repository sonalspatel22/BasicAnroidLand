package com.land.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.land.PropertyDetailActivity;
import com.land.R;
import com.land.api.model.ACPModel;
import com.land.api.model.NearestPropertiesModel;
import com.land.constant.UrlConstant;
import com.land.custom.RobotoTextView;
import com.land.helper.LogHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.land.helper.StringHelper.isEmptyString;

/**
 * Created by Dashrath on 10/8/2017.
 */

public class ListingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mInflater;
    private Context context;
    private List<NearestPropertiesModel> mItems = new ArrayList<>();
    private List<ACPModel> mAdItems = new ArrayList<>();
    private String tag = null;

    public ListingAdapter(Context context, List<NearestPropertiesModel> propertiesModels) {
        this.context = context;
        setHasStableIds(true);
        this.mItems = propertiesModels;
        this.tag = "";
        mInflater = android.view.LayoutInflater.from(context);
    }

    public ListingAdapter(Context context, ArrayList<ACPModel> list, String tag) {
        this.context = context;
        this.mAdItems = list;
        this.tag = tag;
        mInflater = android.view.LayoutInflater.from(context);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == AdapterUtil.VIEW_EMPTY) {
            return new AdapterUtil.EmptyViewHolder(mInflater.inflate(viewType, parent, false), "No post to show");
        } else {
            return new ListingHolder(mInflater.inflate(R.layout.items_listing, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (tag.equals("")) {
            if (holder.getItemViewType() == AdapterUtil.VIEW_EMPTY) {
                return;
            } else {
                ((ListingHolder) holder).bind(mItems.get(position));
            }
        } else if (tag.equals("AgentDetailActivity")) {
            if (holder.getItemViewType() == AdapterUtil.VIEW_EMPTY) {
                return;
            } else {
                ((ListingHolder) holder).Adbind(mAdItems.get(position));
            }
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (tag.equals("")) {
            if (mItems.size() == 0) {
                return AdapterUtil.VIEW_EMPTY;
            } else {
                return super.getItemViewType(position);
            }
        } else if (tag.equals("AgentDetailActivity")) {
            if (mAdItems.size() == 0) {
                return AdapterUtil.VIEW_EMPTY;
            } else {
                return super.getItemViewType(position);
            }
        } else {
            return 0;
        }
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (tag.equals("")) {
            size = mItems.size();
            if (size == 0) {
                size = 1;
            }
        } else if (tag.equals("AgentDetailActivity")) {
            size = mAdItems.size();
            if (size == 0) {
                size = 1;
            }
        }
        return size;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ListingHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView property_image;
        RobotoTextView txt_address;
        RobotoTextView txt_price;
        RobotoTextView txt_bed;
        RobotoTextView txt_property_size;
        RobotoTextView txt_bathroom;
        RobotoTextView txt_saleorrent;
        private View itemView;

        public ListingHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.itemView.setOnClickListener(this);
            property_image = (ImageView) itemView.findViewById(R.id.property_image);
            txt_address = (RobotoTextView) itemView.findViewById(R.id.txt_address);
            txt_price = (RobotoTextView) itemView.findViewById(R.id.txt_price);
            txt_bed = (RobotoTextView) itemView.findViewById(R.id.txt_bed);
            txt_property_size = (RobotoTextView) itemView.findViewById(R.id.txt_property_size);
            txt_bathroom = (RobotoTextView) itemView.findViewById(R.id.txt_Bathroom);
            txt_saleorrent = (RobotoTextView) itemView.findViewById(R.id.txt_rentorsale);
        }

        public void bind(NearestPropertiesModel mItem) {

            // property_image = (ImageView) itemView.findViewById(R.id.property_image);
            Picasso.with(property_image.getContext()).load(UrlConstant.APPLICATION_URL + mItem.getPropertyImage()).fit().placeholder(R.drawable.placeholder).into(property_image);


//            txt_address.setText("" + mItem.getTitle() + ", " + mItem.getKeyLandmark());
//            txt_price.setText("$" + " " + mItem.getUsdSalePrice());
//            txt_bed.setText("" + mItem.getNoofBedrooms());
//            txt_property_size.setText("" + mItem.getLandArea() + " Sq");
//            txt_bathroom.setText("" + mItem.getNoofBathrooms());
            if (!isEmptyString(mItem.getTitle())) {
                if (!isEmptyString(mItem.getKeyLandmark())) {
                    txt_address.setText("" + mItem.getTitle() + ", " + mItem.getKeyLandmark());
                } else {
                    txt_address.setText("" + mItem.getTitle());
                }
            } else {
                txt_address.setText("");
            }
            if (!isEmptyString(mItem.getSalePrice())) {
                txt_price.setText("$" + mItem.getSalePrice());
            } else {
                txt_price.setText("");
            }
            if (!isEmptyString(mItem.getNoofBedrooms())) {
                txt_bed.setText("" + mItem.getNoofBedrooms());
            } else {
                txt_bed.setText("");
            }
            if (!isEmptyString(mItem.getNoofBathrooms())) {
                txt_bathroom.setText("" + mItem.getNoofBathrooms());
            } else {
                txt_bathroom.setText("");
            }
            if (!isEmptyString(mItem.getBuiltArea())) {
                txt_property_size.setText("" + mItem.getLandArea() + " Sq");
            } else {
                txt_property_size.setText("");
            }


            itemView.setTag(getLayoutPosition());
            String type = "" + mItem.getPropertyFor();
            if (type.equals("0")) {
                txt_saleorrent.setText("For (Sale|Rent)");
            } else if ((type.equals("1"))) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    txt_saleorrent.setTextColor(context.getColor(R.color.orange_color));
                } else {
                    txt_saleorrent.setTextColor(context.getResources().getColor(R.color.orange_color));
                }
                txt_saleorrent.setText("For Sale");
            } else if ((type.equals("2"))) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    txt_saleorrent.setTextColor(context.getColor(R.color.rent_color));
                } else {
                    txt_saleorrent.setTextColor(context.getResources().getColor(R.color.rent_color));
                }
                txt_saleorrent.setText("For Rent");
            }
        }

        public void Adbind(ACPModel mItem) {
            Log.e("propertyidadaptor", "" + mItem.getPropertyID());
            // property_image = (ImageView) itemView.findViewById(R.id.property_image);
            if (mItem.getPropertyImage() != null) {
                String[] imagess = mItem.getPropertyImage().split(",");
                LogHelper.e("imagess", "" + imagess[0]);
                Picasso.with(property_image.getContext()).load(UrlConstant.APPLICATION_URL + imagess[0]).fit().placeholder(R.drawable.placeholder).into(property_image);
            }
//            txt_address.setText("" + mItem.getTitle() + ", " + mItem.getKeyLandmark());
//            txt_price.setText("$" + " " + mItem.getUsdSalePrice());
//            txt_bed.setText("" + mItem.getNoofBedrooms());
//            txt_property_size.setText("" + mItem.getLandArea() + " Sq");
//            txt_bathroom.setText("" + mItem.getNoofBathrooms());
            if (!isEmptyString(mItem.getTitle())) {
                if (!isEmptyString(mItem.getKeyLandmark())) {
                    txt_address.setText("" + mItem.getTitle() + ", " + mItem.getKeyLandmark());
                } else {
                    txt_address.setText("" + mItem.getTitle());
                }
            } else {
                txt_address.setText("");
            }
            if (!isEmptyString(mItem.getSalePrice())) {
                txt_price.setText("$" + mItem.getSalePrice());
            } else {
                txt_price.setText("");
            }
            if (!isEmptyString(mItem.getNoofBedrooms())) {
                txt_bed.setText("" + mItem.getNoofBedrooms());
            } else {
                txt_bed.setText("");
            }
            if (!isEmptyString(mItem.getNoofBathrooms())) {
                txt_bathroom.setText("" + mItem.getNoofBathrooms());
            } else {
                txt_bathroom.setText("");
            }
            if (!isEmptyString(mItem.getBuiltArea())) {
                txt_property_size.setText("" + mItem.getLandArea() + " Sq");
            } else {
                txt_property_size.setText("");
            }
            String type = "" + mItem.getPropertyFor();
            if (type.equals("0")) {
                txt_saleorrent.setText("For (Sale|Rent)");
            } else if ((type.equals("1"))) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    txt_saleorrent.setTextColor(context.getColor(R.color.orange_color));
                } else {
                    txt_saleorrent.setTextColor(context.getResources().getColor(R.color.orange_color));
                }
                txt_saleorrent.setText("For Sale");
            } else if ((type.equals("2"))) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    txt_saleorrent.setTextColor(context.getColor(R.color.rent_color));
                } else {
                    txt_saleorrent.setTextColor(context.getResources().getColor(R.color.rent_color));
                }
                txt_saleorrent.setText("For Rent");
            }
            itemView.setTag(getLayoutPosition());
        }

        @Override
        public void onClick(View v) {
            int pos = (int) v.getTag();
            Intent i = null;
            if (tag.equals("")) {
                NearestPropertiesModel nearestPropertiesModel = mItems.get(pos);
                i = new Intent(context, PropertyDetailActivity.class);
                i.putExtra("pid", String.valueOf(nearestPropertiesModel.getPropertyID()));
                Log.e("propertyid", String.valueOf(nearestPropertiesModel.getPropertyID()));
                Log.e("groupid", String.valueOf(nearestPropertiesModel.getGroupID()));
                i.putExtra("groupid", String.valueOf(nearestPropertiesModel.getGroupID()));
            } else if (tag.equals("AgentDetailActivity")) {
                ACPModel propertyModel = mAdItems.get(pos);
                i = new Intent(context, PropertyDetailActivity.class);
                i.putExtra("pid", propertyModel.getPropertyID());
                Log.e("propertyid", "" + propertyModel.getPropertyID());
                i.putExtra("groupid", propertyModel.getGroupID());
                Log.e("groupid", propertyModel.getGroupID());
            }

            context.startActivity(i);

        }
    }
}
