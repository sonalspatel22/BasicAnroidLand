package com.land.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.land.R;

import java.util.ArrayList;

/**
 * Created by Alpesh on 12/26/2017.
 */

public class CallAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<String> a_calllist = null;
    Activity activity;
    private LayoutInflater mInflater;

    public CallAdapter(Activity context, ArrayList<String> calllist) {
        this.activity = context;
        this.a_calllist = calllist;
        mInflater = android.view.LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == AdapterUtil.VIEW_EMPTY) {
            return new AdapterUtil.EmptyViewHolder(mInflater.inflate(viewType, parent, false), "No post to show");
        } else {
            return new CallsHolder(mInflater.inflate(R.layout.items_callist, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == AdapterUtil.VIEW_EMPTY) {
            return;
        } else {
            ((CallAdapter.CallsHolder) holder).bind(a_calllist.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return a_calllist.size();
    }


    public class CallsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private View itemView;
        private TextView textView;


        public CallsHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            itemView.setOnClickListener(this);
            textView = (TextView) itemView.findViewById(R.id.tv_product);

        }

        public void bind(String product) {
            this.textView.setText(product);
            itemView.setTag(getLayoutPosition());
        }


        @Override
        public void onClick(View v) {
            int pos = (int) v.getTag();
            if (Build.VERSION.SDK_INT >= 23) {
                if (activity.checkSelfPermission(android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    Log.v("TAG", "Permission is granted");
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + a_calllist.get(pos)));
                    activity.startActivity(callIntent);

                } else {
                    Log.v("TAG", "Permission is revoked");
                    ActivityCompat.requestPermissions((Activity) activity, new String[]{Manifest.permission.CALL_PHONE}, 1);
                    return;
                }
            } else { //permission is automatically granted on sdk<23 upon installation
                Log.v("TAG", "Permission is granted");
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(a_calllist.get(pos)));
                activity.startActivity(callIntent);
                return;
            }
        }
    }

}