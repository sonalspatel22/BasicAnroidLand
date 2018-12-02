package com.land.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.land.PropertyDetailActivity;
import com.land.R;
import com.land.api.model.NearestPropertiesModel;
import com.land.constant.UrlConstant;
import com.land.custom.RobotoTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.land.helper.StringHelper.isEmptyString;

/**
 * Created by Dashrath on 11/9/2017.
 */


public class NearestPropertyAdapter extends PagerAdapter {
    private Context mContext;
    private List<NearestPropertiesModel> propertiesModels = new ArrayList<>();

    private ImageView property_image;
    private TextView txt_address, txt_price, txt_bed, txt_Bathroom, txt_property_size;


    public NearestPropertyAdapter(Context context, List<NearestPropertiesModel> propertiesModels) {
        mContext = context;
        this.propertiesModels = propertiesModels;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, final int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup itemView = (ViewGroup) inflater.inflate(R.layout.items_similar_homes, collection, false);
        collection.addView(itemView);

        final NearestPropertiesModel mItem = propertiesModels.get(position);
        Log.e("List", "" + mItem);

        property_image = (ImageView) itemView.findViewById(R.id.property_image);
        txt_address = (RobotoTextView) itemView.findViewById(R.id.txt_address);
        txt_price = (RobotoTextView) itemView.findViewById(R.id.txt_price);
        txt_bed = (RobotoTextView) itemView.findViewById(R.id.txt_bed);
        txt_property_size = (RobotoTextView) itemView.findViewById(R.id.txt_property_size);
        txt_Bathroom = (RobotoTextView) itemView.findViewById(R.id.txt_Bathroom);
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
            txt_price.setText("$");
        }
        if (!isEmptyString(mItem.getNoofBedrooms())) {
            txt_bed.setText("" + mItem.getNoofBedrooms());
        } else {
            txt_bed.setText("");
        }
        if (!isEmptyString(mItem.getNoofBathrooms())) {
            txt_Bathroom.setText("" + mItem.getNoofBathrooms());
        } else {
            txt_Bathroom.setText("");
        }
        if (!isEmptyString(mItem.getBuiltArea())) {
            txt_property_size.setText("" + mItem.getLandArea() + " Sq");
        } else {
            txt_property_size.setText("");
        }
        Picasso.with(property_image.getContext()).load(UrlConstant.APPLICATION_URL + mItem.getPropertyImage()).fit().placeholder(R.drawable.placeholder).into(property_image);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, PropertyDetailActivity.class);
                i.putExtra("pid", String.valueOf(mItem.getPropertyID()));
                i.putExtra("groupid", String.valueOf(mItem.getGroupID()));
                mContext.startActivity(i);
            }
        });
      /*  Picasso.with(imageView.getContext()).load(UrlConstant.JOINT_IMAGE_PREFIX + mItems.get(position).getImage()).placeholder(R.drawable.img_placeholder_small).into(imageView);
        textViewName.setText(mItems.get(position).getName());*/

       /* imageButtonHangout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckSusbcribtion())
                    JointDetailDialogFragment.instantiate(mItems.get(position)).show(getSupportFragmentManager(), "JOINT");
                else
                    CreateJointDialogFragment.instantiate(mItems.get(position)).show(getSupportFragmentManager(), "CREATE-ACTIVITY");
            }
        });*/

        return itemView;
    }


    @Override
    public int getCount() {
        return propertiesModels.size();
        // return 10;
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
