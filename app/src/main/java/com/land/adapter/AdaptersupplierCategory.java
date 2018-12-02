package com.land.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.land.R;
import com.land.SupplierDetailActivity;
import com.land.api.model.SupplierCategoryItemsModel;
import com.land.custom.RobotoTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alpesh on 12/23/2017.
 */

public class AdaptersupplierCategory extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<SupplierCategoryItemsModel> listofallagent = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;
    private String tag = null;

    // AgentModel agentModel = new AgentModel();
    public AdaptersupplierCategory(Context context, List<SupplierCategoryItemsModel> listallagent) {
        this.listofallagent = listallagent;
        this.context = context;
        this.tag = "";
        mInflater = android.view.LayoutInflater.from(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == AdapterUtil.VIEW_EMPTY) {
            return new AdapterUtil.EmptyViewHolder(mInflater.inflate(viewType, parent, false), "No Suppliers to show");
        } else {
            return new SupplierHolder(mInflater.inflate(R.layout.items_suppliers_category, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {

        if (holder.getItemViewType() == AdapterUtil.VIEW_EMPTY) {
            return;
        } else {
            ((SupplierHolder) holder).bind(listofallagent.get(i));
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (listofallagent.size() == 0) {
            return AdapterUtil.VIEW_EMPTY;
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public int getItemCount() {
        int size = 0;

        size = listofallagent.size();
        if (size == 0) {
            size = 1;
        }
        return size;
    }


    public class SupplierHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private View itemView;

        private CardView cardOne;
        private ImageView supplierImage;
        private RobotoTextView txtName;
        private RobotoTextView txtPhone;
        private RobotoTextView txtAddress;
        private RobotoTextView txtEmail;
//        private RobotoTextView txtReadMore;


        public SupplierHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            itemView.setOnClickListener(this);
            cardOne = (CardView) itemView.findViewById(R.id.card_one);
            supplierImage = (ImageView) itemView.findViewById(R.id.supplier_image);
            txtName = (RobotoTextView) itemView.findViewById(R.id.txt_name);
            txtPhone = (RobotoTextView) itemView.findViewById(R.id.txt_phone);
            txtAddress = (RobotoTextView) itemView.findViewById(R.id.txt_address);
            txtEmail = (RobotoTextView) itemView.findViewById(R.id.txt_email);
//            txtReadMore = (RobotoTextView) itemView.findViewById(R.id.txt_read_more);
        }

        public void bind(SupplierCategoryItemsModel a) {
            Picasso.with(supplierImage.getContext()).load(a.getL_main_image()).fit().placeholder(R.drawable.placeholder).into(supplierImage);
            txtName.setText("" + a.getL_name());
            txtPhone.setText("" + a.getL_mobile_no1());
            txtEmail.setText("" + a.getL_email());
            txtAddress.setText("" + a.getL_address());
            itemView.setTag(getLayoutPosition());
        }


        @Override
        public void onClick(View v) {
            int pos = (int) v.getTag();
            SupplierCategoryItemsModel a = listofallagent.get(pos);
            Intent i = new Intent(context, SupplierDetailActivity.class);
            i.putExtra("gallaryimage", "" + a.getL_all_image());
            i.putExtra("image", "" + a.getL_main_image());
            i.putExtra("jointId", "" + a.getL_id());
            i.putExtra("name", "" + a.getL_name());
            i.putExtra("adress", "" + a.getL_address());
            i.putExtra("email", "" + a.getL_email());
            i.putExtra("mobile1", "" + a.getL_mobile_no1());
            i.putExtra("mobile2", "" + a.getL_mobile_no2());
            i.putExtra("mobile3", "" + a.getL_mobile_no3());
            i.putExtra("mobile4", "" + a.getL_mobile_no4());
            i.putExtra("detail", "" + a.getL_detail());
            i.putExtra("rate", "" + a.getL_on_rees());
            i.putExtra("products", "" + a.getL_keywords_all());
            i.putExtra("octime", "" + a.getL_oc_time());

            context.startActivity(i);

        }


    }
}
