package com.land.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.land.PropertyDetailActivity;
import com.land.R;
import com.land.api.model.ACPModel;
import com.land.api.model.NearestPropertyModel;
import com.land.constant.UrlConstant;
import com.land.custom.RobotoTextView;
import com.land.helper.LogHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.land.helper.StringHelper.isEmptyString;

public class SimilarPropertyAdapter extends PagerAdapter {
    String tag;
    ArrayList<NearestPropertyModel> npropertylist = new ArrayList<>();
    ArrayList<ACPModel> list_pdm = new ArrayList<>();
    private ImageView propertyImage;
    private RobotoTextView txtAddress;
    private RobotoTextView txtPrice;
    private RobotoTextView txtBed;
    private RobotoTextView txtbathroom;
    private RobotoTextView txtPropertySize;
    private Context mContext;

    public SimilarPropertyAdapter(Context context, ArrayList<NearestPropertyModel> list, String propertyDetailActivity) {
        mContext = context;
        npropertylist = list;
        this.tag = propertyDetailActivity;
    }

    public SimilarPropertyAdapter(Activity context, ArrayList<ACPModel> list_pd, String agentDetailActivity) {
        this.mContext = context;
        this.list_pdm = list_pd;
        this.tag = agentDetailActivity;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, final int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup itemView = (ViewGroup) inflater.inflate(R.layout.items_similar_homes, collection, false);


        propertyImage = (ImageView) itemView.findViewById(R.id.property_image);
        txtAddress = (RobotoTextView) itemView.findViewById(R.id.txt_address);
        txtPrice = (RobotoTextView) itemView.findViewById(R.id.txt_price);
        txtBed = (RobotoTextView) itemView.findViewById(R.id.txt_bed);
        txtbathroom = (RobotoTextView) itemView.findViewById(R.id.txt_Bathroom);
        txtPropertySize = (RobotoTextView) itemView.findViewById(R.id.txt_property_size);

        if (tag.equals("AgentDetailActivity")) {
            final ACPModel acpmodel = list_pdm.get(position);
            if (acpmodel.getPropertyImage() != null) {
                String[] imagess = acpmodel.getPropertyImage().split(",");
                LogHelper.e("imagess", "" + imagess[0]);
                Picasso.with(propertyImage.getContext()).load(UrlConstant.APPLICATION_URL + imagess[0]).fit().placeholder(R.drawable.placeholder).into(propertyImage);
            }
//            txtAddress.setText(acpmodel.getLocation() + "," + acpmodel.getCity());
//            txtPrice.setText(acpmodel.getSalePrice());
//            txtBed.setText(acpmodel.getNoofBedrooms());
//            txtbathroom.setText(acpmodel.getNoofBathrooms());
//            txtPropertySize.setText(acpmodel.getBuiltArea());

            if (!isEmptyString(acpmodel.getTitle())) {
                if (!isEmptyString(acpmodel.getKeyLandmark())) {
                    txtAddress.setText("" + acpmodel.getTitle() + ", " + acpmodel.getKeyLandmark());
                } else {
                    txtAddress.setText("" + acpmodel.getTitle());
                }
            } else {
                txtAddress.setText("");
            }
            if (!isEmptyString(acpmodel.getSalePrice())) {
                txtPrice.setText("$" + acpmodel.getSalePrice());
            } else {
                txtPrice.setText("");
            }
            if (!isEmptyString(acpmodel.getNoofBedrooms())) {
                txtBed.setText(acpmodel.getNoofBedrooms());
            } else {
                txtBed.setText("");
            }
            if (!isEmptyString(acpmodel.getNoofBathrooms())) {
                txtbathroom.setText(acpmodel.getNoofBathrooms());
            } else {
                txtbathroom.setText("");
            }
            if (!isEmptyString(acpmodel.getBuiltArea())) {
                txtPropertySize.setText(acpmodel.getBuiltArea());
            } else {
                txtPropertySize.setText("");
            }

            collection.addView(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, PropertyDetailActivity.class);
                    i.putExtra("pid", acpmodel.getPropertyID());
                    i.putExtra("groupid", String.valueOf(acpmodel.getGroupID()));
//                    Log.e("propertyid", "" + acpmodel.getPropertyID());
//                    Log.e("groupid", String.valueOf(acpmodel.getGroupID()));
                    mContext.startActivity(i);
                }
            });

        } else if (tag.equals("PropertyDetailActivity")) {

            final NearestPropertyModel npmodel = npropertylist.get(position);
            Picasso.with(propertyImage.getContext()).load(UrlConstant.APPLICATION_URL + npmodel.getPropertyImage()).fit().placeholder(R.drawable.placeholder).into(propertyImage);
            if (!isEmptyString(npmodel.getKeyLandmark())) {
                if (!isEmptyString(npmodel.getCity())) {
                    txtAddress.setText("" + npmodel.getTitle() + ", " + npmodel.getKeyLandmark());
                } else {
                    txtAddress.setText("" + npmodel.getTitle());
                }
            } else {
                txtAddress.setText("");
            }
            if (!isEmptyString(npmodel.getSalePrice())) {
                txtPrice.setText("$" + npmodel.getSalePrice());
            } else {
                txtPrice.setText("");
            }
            if (!isEmptyString(npmodel.getNoofBedrooms())) {
                txtBed.setText(npmodel.getNoofBedrooms());
            } else {
                txtBed.setText("");
            }
            if (!isEmptyString(npmodel.getNoofBathrooms())) {
                txtbathroom.setText(npmodel.getNoofBathrooms());
            } else {
                txtbathroom.setText("");
            }
            if (!isEmptyString(npmodel.getBuiltArea())) {
                txtPropertySize.setText(npmodel.getBuiltArea());
            } else {
                txtPropertySize.setText("");
            }

            collection.addView(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity) mContext).finish();
                    Intent i = new Intent(mContext, PropertyDetailActivity.class);
                    i.putExtra("pid", npmodel.getPropertyID());
                    i.putExtra("groupid", "0");
                    mContext.startActivity(i);
                }
            });
        }
        return itemView;
    }


    @Override
    public int getCount() {
        if (tag.equals("AgentDetailActivity")) {
            return list_pdm.size();
        } else {
            return npropertylist.size();
        }


    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }
}
