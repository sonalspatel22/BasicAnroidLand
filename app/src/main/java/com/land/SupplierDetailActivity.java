package com.land;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.land.adapter.AdapterUtil;
import com.land.adapter.GalleryvpAdaptor;
import com.land.constant.ApiConstant;
import com.land.constant.AppConstant;
import com.land.constant.UrlConstant;
import com.land.custom.RobotoTextView;
import com.land.events.ReviewsEvent;
import com.land.fragment.AddReviewSupplierDialogFragment;
import com.land.helper.LogHelper;
import com.land.helper.MyHttpClientHelper;
import com.land.helper.TintDrawableHelper;
import com.land.view.ViewDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.RequestBody;

import static com.land.helper.StringHelper.isEmptyString;

public class SupplierDetailActivity extends AppCompatActivity implements View.OnClickListener {

    JSONArray imagearray;
    String gallaryimages, name, mobile, mobile2, mobile3, mobile4, address, email, rate, product, octime, detail, image, jointId;

    ArrayList<String> images;
    ViewPager viewpager;
    String[] products;
    String[] galleryimage;
    String[] separated;
    RecyclerView rv_product;
    ImageView ivstar;
    ArrayList<String> list_call = new ArrayList<>();
    String total_review = "0";
    String quality_star = "0";
    String services_star = "0";
    String convienancy_star = "0";
    LayoutInflater linf;
    Toolbar toolbar;
    GridLayoutManager mLayoutManager;
    private RobotoTextView rtv_imagenumber;
    private RobotoTextView tvtoolbartitle;
    private CardView profileLayout;
    private LinearLayout lv_reviews;
    private RobotoTextView userProfileName;
    private LinearLayout llrate;
    private TextView tvrate;
    private RobotoTextView rtvreviewscount;
    private RelativeLayout relativelayout;
    private RobotoTextView ivcall;
    private RobotoTextView ivemail;
    private TextView tv;
    private RobotoTextView ivmap;
    private RobotoTextView ivaddreview;
    private CardView cardCompanyinfo;
    private RelativeLayout rlcompanyinfo;
    private TextView tvCompanydetaillabel;
    private LinearLayout llcontactinfo;
    private RobotoTextView tvNumber;
    private RobotoTextView tvEmail;
    private RobotoTextView tvcomapnycontact;
    private CardView cardAgentdesc;
    private LinearLayout rlagentdesc;
    private RobotoTextView tvagentdesclabel;
    private RobotoTextView tvagentdesc;
    private CardView cardAssociates;
    private LinearLayout llassociates;
    private RobotoTextView tvassociateslabel;
    private RecyclerView rvassociateslist;
    private ImageView ivactivelist;
    private ViewPager viewpageractive;
    private RecyclerView recyclerViewactive;
    private ImageView ivsalelist;
    private SwipeRefreshLayout swiper;
    private RobotoTextView textViewOpenClosing;
    private TableRow row0;
    private TableRow row1;
    private RobotoTextView textViewOpen1;
    private RobotoTextView textViewClose1;
    private TableRow row2;
    private RobotoTextView textViewOpen2;
    private RobotoTextView textViewClose2;
    private TableRow row3;
    private RobotoTextView textViewOpen3;
    private RobotoTextView textViewClose3;
    private TableRow row4;
    private RobotoTextView textViewOpen4;
    private RobotoTextView textViewClose4;
    private TableRow row5;
    private RobotoTextView textViewOpen5;
    private RobotoTextView textViewClose5;
    private TableRow row6;
    private RobotoTextView textViewOpen6;
    private RobotoTextView textViewClose6;
    private TableRow row7;
    private RobotoTextView textViewOpen7;
    private RobotoTextView textViewClose7;
    private TableRow row8;
    private RobotoTextView textViewOpen8;
    private RobotoTextView textViewClose8;
    private int addreviewreq = 89;
    private View[] openClosingTimeViews;
    private RobotoTextView[] openTimeViews;
    private RobotoTextView[] closeTimeViews;
    private Handler mHandler;
    private LayoutInflater mInflater;
    private MyHttpClientHelper.ProcessResponseHelper mResponseHelper = new MyHttpClientHelper.ProcessResponseHelper() {
        @Override
        public void onRequest() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                   /* progressDialog = new ProgressDialog(SupplierDetailActivity.this);
                    progressDialog.setIcon(R.drawable.icn_draw);
                    progressDialog.setCancelable(false);
                    progressDialog.setTitle(R.string.app_name);
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();*/
                }
            });
        }

        @Override
        public void onFinish() {

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    swiper.setRefreshing(false);
                    swiper.setEnabled(false);
                    /*if (progressDialog != null && progressDialog.isShowing())
                        progressDialog.dismiss();*/
                }
            });
        }

        @Override
        public void onFail() throws Exception {

            swiper.setRefreshing(false);
            swiper.setEnabled(false);
           /* if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();*/
        }

        @Override
        public void onResponse(JSONObject jsonObject) {
            try {
                final String msg = jsonObject.getString(ApiConstant.JSON_KEY_MSG);
                if (jsonObject.getInt(ApiConstant.JSON_KEY_CODE) == 1) {
                    JSONObject objectData = jsonObject.getJSONObject(ApiConstant.JSON_KEY_DATA);
                    JSONArray jsonArray = objectData.getJSONArray("review_list");
                    final JSONArray average_reviews = objectData.getJSONArray("average_review");
                    if (average_reviews.length() > 0) {
                        final JSONObject average_review = average_reviews.getJSONObject(0);

                        total_review = average_review.getString("total_count");
                        quality_star = average_review.getString("quality_star");
                        services_star = average_review.getString("services_star");
                        convienancy_star = average_review.getString("convienancy_star");
                    }

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {

                            Double treviews = (Double.parseDouble(quality_star) + Double.parseDouble(services_star) + Double.parseDouble(convienancy_star)) / 3;
                            tvrate.setText("" + String.format("%.1f", treviews) + "");

                        }
                    });

                } else {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SupplierDetailActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            } catch (JSONException e) {
                e.printStackTrace();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SupplierDetailActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    };

    private void findViews() {
        rtv_imagenumber = (RobotoTextView) findViewById(R.id.rtv_imagenumber);

        viewpager = (ViewPager) findViewById(R.id.vpgallery);
        ivstar = (ImageView) findViewById(R.id.ivstar);
        rv_product = (RecyclerView) findViewById(R.id.rv_products);
        rv_product.setNestedScrollingEnabled(false);
        profileLayout = (CardView) findViewById(R.id.profile_layout);
        lv_reviews = (LinearLayout) findViewById(R.id.lv_reviews);
        lv_reviews.setOnClickListener(this);

        userProfileName = (RobotoTextView) findViewById(R.id.user_profile_name);
        llrate = (LinearLayout) findViewById(R.id.llrate);
        tvrate = (TextView) findViewById(R.id.tvrate);
        rtvreviewscount = (RobotoTextView) findViewById(R.id.rtvreviewscount);
        relativelayout = (RelativeLayout) findViewById(R.id.relativelayout);
        ivcall = (RobotoTextView) findViewById(R.id.ivcall);
        ivemail = (RobotoTextView) findViewById(R.id.ivemail);
        tv = (TextView) findViewById(R.id.tv);
        ivmap = (RobotoTextView) findViewById(R.id.ivmap);
        ivaddreview = (RobotoTextView) findViewById(R.id.ivaddreview);
        cardCompanyinfo = (CardView) findViewById(R.id.card_companyinfo);
        rlcompanyinfo = (RelativeLayout) findViewById(R.id.rlcompanyinfo);
        tvCompanydetaillabel = (TextView) findViewById(R.id.tvCompanydetaillabel);
        llcontactinfo = (LinearLayout) findViewById(R.id.llcontactinfo);
        tvNumber = (RobotoTextView) findViewById(R.id.tvNumber);
        tvEmail = (RobotoTextView) findViewById(R.id.tvEmail);
        tvcomapnycontact = (RobotoTextView) findViewById(R.id.tvcomapnycontact);
        cardAgentdesc = (CardView) findViewById(R.id.card_agentdesc);
        rlagentdesc = (LinearLayout) findViewById(R.id.rlagentdesc);
        tvagentdesclabel = (RobotoTextView) findViewById(R.id.tvagentdesclabel);
        tvagentdesc = (RobotoTextView) findViewById(R.id.tvagentdesc);
        cardAssociates = (CardView) findViewById(R.id.card_associates);
        llassociates = (LinearLayout) findViewById(R.id.llassociates);
        tvassociateslabel = (RobotoTextView) findViewById(R.id.tvassociateslabel);
        rvassociateslist = (RecyclerView) findViewById(R.id.rvassociateslist);
        ivactivelist = (ImageView) findViewById(R.id.ivactivelist);
        viewpageractive = (ViewPager) findViewById(R.id.viewpageractive);
        ivsalelist = (ImageView) findViewById(R.id.ivsalelist);
//        table11 = (LinearLayout)findViewById( R.id.table_11 );
//        table12 = (LinearLayout)findViewById( R.id.table_12 );
//        table13 = (LinearLayout)findViewById( R.id.table_13 );
        swiper = (SwipeRefreshLayout) findViewById(R.id.swiper);
        textViewOpenClosing = (RobotoTextView) findViewById(R.id.textView_open_closing);
        row0 = (TableRow) findViewById(R.id.row_0);
        row1 = (TableRow) findViewById(R.id.row_1);
        textViewOpen1 = (RobotoTextView) findViewById(R.id.textView_open_1);
        textViewClose1 = (RobotoTextView) findViewById(R.id.textView_close_1);
        row2 = (TableRow) findViewById(R.id.row_2);
        textViewOpen2 = (RobotoTextView) findViewById(R.id.textView_open_2);
        textViewClose2 = (RobotoTextView) findViewById(R.id.textView_close_2);
        row3 = (TableRow) findViewById(R.id.row_3);
        textViewOpen3 = (RobotoTextView) findViewById(R.id.textView_open_3);
        textViewClose3 = (RobotoTextView) findViewById(R.id.textView_close_3);
        row4 = (TableRow) findViewById(R.id.row_4);
        textViewOpen4 = (RobotoTextView) findViewById(R.id.textView_open_4);
        textViewClose4 = (RobotoTextView) findViewById(R.id.textView_close_4);
        row5 = (TableRow) findViewById(R.id.row_5);
        textViewOpen5 = (RobotoTextView) findViewById(R.id.textView_open_5);
        textViewClose5 = (RobotoTextView) findViewById(R.id.textView_close_5);
        row6 = (TableRow) findViewById(R.id.row_6);
        textViewOpen6 = (RobotoTextView) findViewById(R.id.textView_open_6);
        textViewClose6 = (RobotoTextView) findViewById(R.id.textView_close_6);
        row7 = (TableRow) findViewById(R.id.row_7);
        textViewOpen7 = (RobotoTextView) findViewById(R.id.textView_open_7);
        textViewClose7 = (RobotoTextView) findViewById(R.id.textView_close_7);
        row8 = (TableRow) findViewById(R.id.row_8);
        textViewOpen8 = (RobotoTextView) findViewById(R.id.textView_open_8);
        textViewClose8 = (RobotoTextView) findViewById(R.id.textView_close_8);
        openClosingTimeViews = new View[]{row1, row2, row3, row4, row5, row6, row7, row8};
        openTimeViews = new RobotoTextView[]{textViewOpen1, textViewOpen2, textViewOpen3, textViewOpen4, textViewOpen5, textViewOpen6, textViewOpen7, textViewOpen8};
        closeTimeViews = new RobotoTextView[]{textViewClose1, textViewClose2, textViewClose3, textViewClose4, textViewClose5, textViewClose6, textViewClose7, textViewClose8};

        ivstar.setOnClickListener(this);
        ivactivelist.setOnClickListener(this);
        ivcall.setOnClickListener(this);
        ivemail.setOnClickListener(this);
        ivmap.setOnClickListener(this);
        ivaddreview.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplierdetail);
        findViews();
        mHandler = new Handler(getMainLooper());

        tvtoolbartitle = (RobotoTextView) findViewById(R.id.tvtoolbartitle);
        toolbar = (Toolbar) findViewById(R.id.maintoolbar);
        toolbar.setNavigationIcon(TintDrawableHelper.getTintedResource(this, android.support.design.R.drawable.abc_ic_ab_back_material, R.color.colorWhite));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tvtoolbartitle.setText("Supplier Detail");


//        linf = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        linf = LayoutInflater.from(this);
        Intent i = getIntent();
        try {
            gallaryimages = i.getStringExtra("gallaryimage");
            galleryimage = gallaryimages.split("\\,");
            imagearray = new JSONArray();
            images = new ArrayList<>();
            for (String name : galleryimage) {
                imagearray.put(name);
                images.add(name);
            }

            name = i.getStringExtra("name");
            jointId = i.getStringExtra("jointId");
            address = i.getStringExtra("adress");
            email = i.getStringExtra("email");
            mobile = i.getStringExtra("mobile1");
            mobile2 = i.getStringExtra("mobile2");
            mobile3 = i.getStringExtra("mobile3");
            mobile4 = i.getStringExtra("mobile4");

            detail = i.getStringExtra("detail");
            rate = i.getStringExtra("rate");
            product = i.getStringExtra("products");
            products = product.split("\\,");
            octime = i.getStringExtra("octime");
            separated = octime.split("\\,");
            image = i.getStringExtra("image");


            mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
            rv_product.setLayoutManager(mLayoutManager);

            ProductsAdapter productsAdapter = new ProductsAdapter(getApplicationContext(), products);
            rv_product.setAdapter(productsAdapter);

            viewpager.setAdapter(new GalleryvpAdaptor(getApplicationContext(), images, "true", "SupplierDetailActivity"));
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

            if (!isEmptyString(mobile)) {
                list_call.add(mobile);
            }
            if (!isEmptyString(mobile2)) {
                list_call.add(mobile2);
            }
            if (!isEmptyString(mobile3)) {
                list_call.add(mobile3);
            }
            if (!isEmptyString(mobile4)) {
                list_call.add(mobile4);
            }

        } catch (Exception e) {

        }

        userProfileName.setText(name);
        tvagentdesc.setText(detail);
        tvEmail.setText(email);
        tvNumber.setText(mobile);
        tvcomapnycontact.setText(address);

//        for (String name : products) {
//            LogHelper.e("name", name);
//            final TextView v = (TextView) linf.inflate(R.layout.items, null);
//            if (name.equals(null) || name.isEmpty() || name.equals("null")) {
//                v.setText(" ");
//            } else {
//                v.setText(name);
//            }
//            table1.addView(v);
//        }
        try {
            if (separated.length > 1) {
                for (int j = 0; j < separated.length; j++) {
                    String separate = separated[j].trim();
                    separate = separate.replace('|', ',');
                    LogHelper.e("Open Close Time Pair", separate);
                    String[] times = separate.split("\\,");
                    LogHelper.e("Open Close Time Pair", times[0] + "-" + times[1]);
                    openClosingTimeViews[j].setVisibility(View.VISIBLE);
                    openTimeViews[j].setText(times[0]);
                    closeTimeViews[j].setText(times[1]);
                    if (times[0].equals("00:00") && times[1].equals("00:00")) {
                        openTimeViews[j].setText("Close");
                        closeTimeViews[j].setText("Close");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        actionGetReviewList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == addreviewreq) {
            actionGetReviewList();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ivstar:
//                AddReviewDialogFragment.instantiate(acpModel.getAgentId(), acpModel.getUserId()).show(this.getSupportFragmentManager(), "POST");
                break;
            case R.id.ivcall:
                new ViewDialog(SupplierDetailActivity.this, list_call).showDialog();
                break;
            case R.id.ivemail:
                sentmail();
                break;
            case R.id.ivmap:
                try {
                    String map = "http://maps.google.co.in/maps?q=" + address;
                    Intent intents = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
                    startActivity(intents);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.ivaddreview:
                if (AppConstant.isuser) {
                    AddReviewSupplierDialogFragment.instantiate(jointId).show(getSupportFragmentManager(), "POST");
                } else {
                    startActivity(new Intent(SupplierDetailActivity.this, SignupActivity.class));
                }

                break;
            case R.id.user_profile_photo:
                break;
            case R.id.lv_reviews:
                Intent intents = new Intent(this, SupplierReviewsActivity.class);
                intents.putExtra("joint_id", jointId);
                startActivityForResult(intents, addreviewreq);
                break;
        }
    }

    public void sentmail() {
        String strSub = "Inquiry from reesguru member";
        String strBody = "sent from reesguru.com";
        String toemail = email;
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setAction(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{toemail});
        emailIntent.putExtra(android.content.Intent.EXTRA_CC, "");
        emailIntent.putExtra(android.content.Intent.EXTRA_BCC, "");
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, strSub);
        emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(strBody));
        emailIntent.setType("text/html");
        emailIntent.setPackage("com.google.android.gm");

// FOLLOWING STATEMENT CHECKS WHETHER THERE IS ANY APP THAT CAN HANDLE OUR EMAIL INTENT
        try {
            startActivity(Intent.createChooser(emailIntent,
                    "Send Email"));
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            //TODO: Handle case where no email app is available
        }
    }

    private void actionGetReviewList() {
        swiper.setEnabled(true);
        swiper.setRefreshing(true);
        RequestBody requestBody = new FormBody.Builder().add("joints_id", jointId).build();
        MyHttpClientHelper.getInstance().enqueueCall(this, UrlConstant.API_GET_SUPPLIER_DETAILS, requestBody, mResponseHelper);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ReviewsEvent event) {
        if (event.getAction() == ReviewsEvent.ACTION_ADD) {
            actionGetReviewList();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public class ProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        String[] pa_products = null;
        Context c;

        public ProductsAdapter(Context context, String[] product) {
            this.c = context;
            this.pa_products = product;
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
                return new ProductsHolder(mInflater.inflate(R.layout.item_product, parent, false));
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder.getItemViewType() == AdapterUtil.VIEW_EMPTY) {
                return;
            } else {
                ((ProductsAdapter.ProductsHolder) holder).bind(pa_products[position]);
            }
        }

        @Override
        public int getItemCount() {
            return pa_products.length;
        }


        public class ProductsHolder extends RecyclerView.ViewHolder {
            private View itemView;
            private TextView textView;


            public ProductsHolder(View itemView) {
                super(itemView);
                this.itemView = itemView;
                textView = (TextView) itemView.findViewById(R.id.tv_product);

            }

            public void bind(String product) {
                this.textView.setText(product);
            }
        }
    }


}
