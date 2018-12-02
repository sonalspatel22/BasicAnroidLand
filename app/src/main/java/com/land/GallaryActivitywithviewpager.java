package com.land;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.land.adapter.GalleryvpAdaptor;
import com.land.custom.RobotoTextView;
import com.land.helper.TintDrawableHelper;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class GallaryActivitywithviewpager extends AppCompatActivity {
    ViewPager viewpager;
    RobotoTextView rtv_imagenumber;
    String ja;
    ArrayList<String> images = new ArrayList<>();
    Toolbar toolbar;
    String pos;
    String activity;
    String clickdisable = "false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallary_activitywithviewpager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(TintDrawableHelper.getTintedResource(this, android.support.design.R.drawable.abc_ic_ab_back_material, R.color.colorWhite));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        rtv_imagenumber = (RobotoTextView) findViewById(R.id.rtv_imagenumber);
        viewpager = (ViewPager) findViewById(R.id.vpgallery);
        Intent i = getIntent();

        try {
            ja = i.getStringExtra("images");
            pos = i.getStringExtra("position");
            activity = i.getStringExtra("activity");
            JSONArray jsonArray = new JSONArray(ja);
            for (int j = 0; j < jsonArray.length(); j++) {
                String s = String.valueOf(jsonArray.get(j));
                images.add(s);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        viewpager.setAdapter(new GalleryvpAdaptor(getApplicationContext(), images, clickdisable, activity));
        viewpager.setCurrentItem(Integer.parseInt(pos));
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                rtv_imagenumber.setText(position + 1 + "/" + images.size());
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
