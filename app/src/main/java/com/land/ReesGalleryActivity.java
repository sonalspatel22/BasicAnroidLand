package com.land;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.land.adapter.AdapterUtil;
import com.land.constant.UrlConstant;
import com.land.helper.TintDrawableHelper;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ReesGalleryActivity extends AppCompatActivity {

    String ja;
    ArrayList<String> images = new ArrayList<>();
    String[] imgarray;
    RecyclerView recyclerView;
    GalleryAdapter listAdapter;
    ImageView menu_image;
    String s = null;

    ImageView ivshare;
    Toolbar toolbar;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agallery);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(TintDrawableHelper.getTintedResource(this, android.support.design.R.drawable.abc_ic_ab_back_material, R.color.colorWhite));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        menu_image = (ImageView) findViewById(R.id.menu_image);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        ivshare = (ImageView) findViewById(R.id.ivshare);
        Intent i = getIntent();

        try {
            ja = i.getStringExtra("images");
            JSONArray jsonArray = new JSONArray(ja);
            for (int j = 0; j < jsonArray.length(); j++) {
                String s = String.valueOf(jsonArray.get(j));
                images.add(s);
            }
            recyclerView.setLayoutManager(manager);
            listAdapter = new GalleryAdapter(this, images);
            recyclerView.setAdapter(listAdapter);
            s = i.getStringExtra("tag");
            if (s != null) {
                Picasso.with(menu_image.getContext()).load(images.get(0)).fit().centerCrop().placeholder(R.drawable.placeholder).into(menu_image);
            } else {
                Picasso.with(menu_image.getContext()).load(UrlConstant.APPLICATION_URL + images.get(0)).fit().placeholder(R.drawable.placeholder).into(menu_image);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public class GalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        Context context;
        private LayoutInflater mInflater;
        private ArrayList<String> imagelist;

        public GalleryAdapter(

                ReesGalleryActivity reesGallery_activity, ArrayList<String> images) {
            this.context = reesGallery_activity;
            this.imagelist = images;
            mInflater = android.view.LayoutInflater.from(context);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == AdapterUtil.VIEW_EMPTY) {
                return new AdapterUtil.EmptyViewHolder(mInflater.inflate(viewType, parent, false), "No post to show");
            } else {
                return new GalleryHolder(mInflater.inflate(R.layout.item_gallery, parent, false));
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder.getItemViewType() == AdapterUtil.VIEW_EMPTY) {
                return;
            } else {
                ((GalleryHolder) holder).bind(imagelist.get(position));
            }
        }

        @Override
        public int getItemViewType(int position) {


            if (imagelist.size() == 0) {
                return AdapterUtil.VIEW_EMPTY;
            } else {
                return super.getItemViewType(position);
            }

        }

        @Override
        public int getItemCount() {
            return images.size();
        }


        public class GalleryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            ImageView imageView;
            View itemView;

            public GalleryHolder(View itemView) {
                super(itemView);
                this.itemView = itemView;
                itemView.setOnClickListener(this);
                imageView = (ImageView) itemView.findViewById(R.id.ivfullimageitem);
            }

            public void bind(String url) {
                if (s != null) {
                    Picasso.with(imageView.getContext()).load(url).fit().centerCrop().placeholder(R.drawable.placeholder).into(imageView);
                } else {
                    Picasso.with(imageView.getContext()).load(UrlConstant.APPLICATION_URL + url).fit().placeholder(R.drawable.placeholder).into(imageView);

                }
                itemView.setTag(getLayoutPosition());
            }

            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                if (s != null) {
                    Picasso.with(menu_image.getContext()).load(images.get(pos)).fit().centerCrop().placeholder(R.drawable.placeholder).into(menu_image);
                } else {
                    Picasso.with(menu_image.getContext()).load(UrlConstant.APPLICATION_URL + images.get(pos)).fit().placeholder(R.drawable.placeholder).into(menu_image);

                }
            }
        }

    }

}
