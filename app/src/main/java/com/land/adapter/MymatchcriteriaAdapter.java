package com.land.adapter;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.land.R;
import com.land.api.model.MymatchesCriteriamodel;
import com.land.api.model.NearestPropertiesModel;
import com.land.constant.ApiConstant;
import com.land.constant.UrlConstant;
import com.land.custom.RobotoTextView;
import com.land.fragment.SearchListingFragment;
import com.land.helper.LogHelper;
import com.land.helper.MyHttpClientHelper;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by Alpesh on 1/11/2018.
 */

public class MymatchcriteriaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Float lat;
    Float lang;
    Handler handler;
    ArrayList<NearestPropertiesModel> propertiesModels;
    Call lastCall;
    private LayoutInflater mInflater;
    private Activity context;
    private List<MymatchesCriteriamodel> mItems = new ArrayList<>();
    private String tag = null;
    private MyHttpClientHelper.ProcessResponseHelper mPropertiesListingResponseHelper = new MyHttpClientHelper.ProcessResponseHelper() {
        @Override
        public void onRequest() {
        }

        @Override
        public void onResponse(JSONObject object) {
            try {
                int code = object.getInt(ApiConstant.JSON_KEY_CODE);

                if (code == 1) {
                    JSONArray dataObject = object.getJSONArray(ApiConstant.JSON_KEY_DETAIL_DATA);
                    propertiesModels = new ArrayList<>();
                    //Log.e("propertylist", "" + dataObject.getJSONObject(3));
                    for (int i = 0; i < dataObject.length(); i++) {
                        JSONObject property = dataObject.getJSONObject(i);
                        NearestPropertiesModel propertiesModel = new NearestPropertiesModel();
                        propertiesModel.fromJson(property);
                        propertiesModels.add(propertiesModel);
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (!propertiesModels.isEmpty()) {
                                    SearchListingFragment.newInstance(propertiesModels).show(((AppCompatActivity) context).getSupportFragmentManager(), "SHARE-VOICE");
                                } else {
                                    Toast.makeText(context, "Data Not Found", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFinish() {

        }

        @Override
        public void onFail() throws Exception {

        }
    };


    public MymatchcriteriaAdapter(Activity context, List<MymatchesCriteriamodel> propertiesModels) {
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
            return new MymatchcriteriaAdapter.MymatchcriteriaHolder(mInflater.inflate(R.layout.item_mymatchcriteria, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder.getItemViewType() == AdapterUtil.VIEW_EMPTY) {
            return;
        } else {
            ((MymatchcriteriaAdapter.MymatchcriteriaHolder) holder).bind(mItems.get(position));
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

    private void actionGetPropertiesListing(String id) {
        RequestBody requestBody = new FormBody.Builder().addEncoded("USerCiteria", id).build();
        lastCall = MyHttpClientHelper.getInstance().enqueueCall(context, UrlConstant.API_GET_MYMATCHES_CRITERIA_PROPERTY_LISTING_URL, requestBody, mPropertiesListingResponseHelper);
    }

    class MymatchcriteriaHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private View itemView;

        private CardView cardOne;
        private LinearLayout ll1;
        private ImageView image;
        private RobotoTextView txtName;
        private RobotoTextView txtAddress;
        private RobotoTextView txtOtherdetail;


        public MymatchcriteriaHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.itemView.setOnClickListener(this);
            cardOne = (CardView) itemView.findViewById(R.id.card_one);
            ll1 = (LinearLayout) itemView.findViewById(R.id.ll1);
            image = (ImageView) itemView.findViewById(R.id.image);
            txtName = (RobotoTextView) itemView.findViewById(R.id.txt_name);
            txtAddress = (RobotoTextView) itemView.findViewById(R.id.txt_address);
            txtOtherdetail = (RobotoTextView) itemView.findViewById(R.id.txt_otherdetail);
        }

        public void bind(MymatchesCriteriamodel mItem) {
            LogHelper.e("text", "" + mItem.getTitle());
            if (mItem.getTitle().equals(null)) {
                txtName.setText("");
            } else {
                txtName.setText(mItem.getTitle());
            }
            lat = Float.valueOf(mItem.getCenter_latitude());
            lang = Float.valueOf(mItem.getCenter_longitude());
            String mapUrl = UrlConstant.StaticMapUrl;
            mapUrl = mapUrl + lat + "," + lang + "&center=" + lat + "," + lang;
            Picasso.with(image.getContext()).load(mapUrl).fit().centerCrop().placeholder(R.color.colorAccent).into(image);
            itemView.setTag(getLayoutPosition());
        }


        @Override
        public void onClick(View v) {
            int pos = (int) v.getTag();
            String id = mItems.get(pos).getUSerCiteria();
            actionGetPropertiesListing(id);
//            Intent i = null;
//
//            context.startActivity(i);

        }
    }
}
