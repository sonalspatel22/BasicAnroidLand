package com.land.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.land.GallaryActivitywithviewpager;
import com.land.R;
import com.land.constant.UrlConstant;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;


public class GalleryvpAdaptor extends PagerAdapter {
    Context c;
    ImageView imageView;
    ArrayList<String> images;
    JSONArray imagearray = new JSONArray();
    String activity = null;
    String clickdisable = null;

    private PhotoViewAttacher mAttacher;


    public GalleryvpAdaptor(Context applicationContext, ArrayList<String> images, String clickdisable, String activity) {
        this.c = applicationContext;
        this.images = images;
        for (String name : images) {
            imagearray.put(name);
        }
        this.clickdisable = clickdisable;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LayoutInflater inflater = LayoutInflater.from(c);
        ViewGroup itemView = (ViewGroup) inflater.inflate(R.layout.item_vpgallery, container, false);
        container.addView(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.vpimageview);

        if (activity.equals("PropertyDetailActivity")) {
            Picasso.with(imageView.getContext()).load(UrlConstant.APPLICATION_URL + images.get(position)).fit().placeholder(R.color.colorAccent).into(imageView);
        } else if (activity.equals("SupplierDetailActivity")) {
            Picasso.with(imageView.getContext()).load(images.get(position)).fit().placeholder(R.color.colorAccent).into(imageView);
        }
        if (clickdisable.equals("true")) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(c, GallaryActivitywithviewpager.class);
                    intent.putExtra("position", "" + position);
                    intent.putExtra("images", "" + imagearray);
                    intent.putExtra("clickdisable", "false");
                    if (activity.equals("PropertyDetailActivity")) {
                        intent.putExtra("activity", "PropertyDetailActivity");
                    } else if (activity.equals(("SupplierDetailActivity"))) {
                        intent.putExtra("activity", "SupplierDetailActivity");
                    }
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    c.startActivity(intent);
                }
            });
        } else {
            mAttacher = new PhotoViewAttacher(imageView);
            String url = null;
            if (activity.equals("PropertyDetailActivity")) {
                url = UrlConstant.APPLICATION_URL + images.get(position);
            } else if (activity.equals("SupplierDetailActivity")) {
                url = images.get(position);
            }

            Picasso.with(imageView.getContext()).load(url).centerInside().fit().placeholder(R.drawable.placeholder).into(imageView, new Callback() {
                @Override
                public void onSuccess() {
                    mAttacher.update();
                }

                @Override
                public void onError() {
                    mAttacher.update();

                }
            });
        }

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ((ViewPager) container).removeView((View) object);
    }
}