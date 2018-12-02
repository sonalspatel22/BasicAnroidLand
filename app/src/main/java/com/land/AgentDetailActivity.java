package com.land;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.land.adapter.AdapterAgents;
import com.land.adapter.AdapterUtil;
import com.land.adapter.ListingAdapter;
import com.land.adapter.SimilarPropertyAdapter;
import com.land.api.model.ACPModel;
import com.land.api.model.AgentModel;
import com.land.constant.ApiConstant;
import com.land.constant.AppConstant;
import com.land.constant.UrlConstant;
import com.land.custom.RobotoTextView;
import com.land.events.ReviewsEvent;
import com.land.fragment.AddReviewDialogFragment;
import com.land.helper.MyHttpClientHelper;
import com.land.helper.TintDrawableHelper;
import com.land.view.CircleImageView;
import com.land.view.ViewDialog;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;

import static com.land.helper.StringHelper.isEmptyString;

public class AgentDetailActivity extends BaseActivity implements View.OnClickListener {
    public String a_id;
    public String a_image;
    public String a_name;
    public String a_email;
    public String a_phone;
    public String c_name;
    public String c_email;
    public String c_address;
    public String c_mobile;
    public String c_landline;
    public String c_ompImage;
    public String a_intro;
    public String a_rate;
    public String a_reviewscount;
    public String a_salepropertycount;
    public String a_associates;
    public String a_activelistlable;
    public String a_salelistlable;
    public String UserId;
    public String address = "";
    ACPModel acpModel;
    AgentModel agentModel;
    //
    RobotoTextView tvtitle;
    Toolbar toolbar;
    //    public ListingAdapter listingAdapter;
    String tag = "AgentDetailActivity";
    int flag = 0;
    ArrayList<ACPModel> list_pd = new ArrayList<>();
    ArrayList<ACPModel> list_spl = new ArrayList<>();
    ArrayList<AgentModel> list_al = new ArrayList<>();
    ArrayList<String> list_call = new ArrayList<>();
    private LinearLayout ll_expand;
    private LinearLayout ll_adetail;
    private SwipeRefreshLayout swiper;
    private CardView profileLayout;
    private ImageView headerCoverImage;
    private CircleImageView userProfilePhoto;
    private RobotoTextView userProfileName;
    private RobotoTextView userProfileShortBio;
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
    private RobotoTextView tvcomapnyname;
    private CardView cardAgentdesc;
    private LinearLayout rlagentdesc;
    private RobotoTextView tvagentdesclabel;
    private RobotoTextView tvagentdesc;
    private CardView cardAssociates;
    private LinearLayout llassociates;
    private RobotoTextView tvassociateslabel;
    private RecyclerView rvassociateslist;
    private ImageView ivassociatelist;
    private RobotoTextView tvassociatesmembercount;
    private ImageView ivactivelist;
    private ViewPager viewpageractive;
    private ImageView ivsalelist;
    private ImageView ivstar;
    private ViewPager viewpagersale;
    private RecyclerView rv_expandelist;
    private ScrollView scrollView;
    private RobotoTextView tvactivepropertycount;
    private RobotoTextView tvsalepropertycount;
    //
    private Call lastCall;
    private Handler handler;
    private int addreviewreq = 89;
    private MyHttpClientHelper.ProcessResponseHelper mResponseHelperratinng = new MyHttpClientHelper.ProcessResponseHelper() {
        @Override
        public void onRequest() {
        }

        @Override
        public void onResponse(JSONObject object) {
            try {
                int code = object.getInt(ApiConstant.JSON_KEY_CODE);

                if (code == 1) {
                    JSONArray dataObject = object.getJSONArray(ApiConstant.JSON_KEY_DATA);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFinish() {

            handler.post(new Runnable() {
                @Override
                public void run() {
                    try {

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        public void onFail() throws Exception {

        }
    };
    private MyHttpClientHelper.ProcessResponseHelper mResponseHelper = new MyHttpClientHelper.ProcessResponseHelper() {
        @Override
        public void onRequest() {
        }

        @Override
        public void onResponse(JSONObject object) {
            try {
                int code = object.getInt(ApiConstant.JSON_KEY_CODE);

                if (code == 1) {
//                    JSONObject on=jsonobject.getJSONObject("data");
//                    jsonarray = on.getJSONArray("current_condition");
                    list_pd = new ArrayList<>();
                    list_spl = new ArrayList<>();
                    list_al = new ArrayList<>();
                    list_call = new ArrayList<>();
                    JSONObject dataObject = object.getJSONObject(ApiConstant.JSON_KEY_DATA);
                    JSONArray dataObjectRelatedAgentsToCompnay = null;
                    JSONArray dataObjectPastSales = null;
                    JSONArray dataObjectPropertyListing = dataObject.getJSONArray("PropertyListing");
                    if (dataObject.has("RelatedAgentsToCompnay")) {
                        dataObjectRelatedAgentsToCompnay = dataObject.getJSONArray("RelatedAgentsToCompnay");
                    }
                    if (dataObject.has("PastSales")) {
                        dataObjectPastSales = dataObject.getJSONArray("PastSales");
                    }


                    for (int i = 0; i < dataObjectPropertyListing.length(); i++) {
                        acpModel = new ACPModel();
                        JSONObject property = dataObjectPropertyListing.getJSONObject(i);
                        acpModel.fromJson(property);
                        if (i == 0) {
                            a_id = String.valueOf(acpModel.getAgentId());
                            Log.e("a_id", "" + a_id);
                            a_name = String.valueOf(acpModel.getA_FirstName() + " " + String.valueOf(acpModel.getA_LastName()));
                            a_email = String.valueOf(acpModel.getA_EmailID());
                            a_phone = String.valueOf(acpModel.getA_Telephone());
                            if (!isEmptyString(a_phone)) {
                                list_call.add(a_phone);
                            }
                            address = String.valueOf(acpModel.getA_Address());
                            a_image = String.valueOf(acpModel.getA_UserImage());
                            a_intro = String.valueOf(acpModel.getA_Description());

                            a_rate = String.valueOf(acpModel.getA_AverageAgentRating());
                            a_reviewscount = String.valueOf(acpModel.getA_ReviewsCount());
                            c_name = String.valueOf(acpModel.getC_CompnayName());
                            c_email = String.valueOf(acpModel.getC_EmailID());
                            c_address = String.valueOf(acpModel.getC_RegisterAddress());
                            c_mobile = String.valueOf(acpModel.getC_Mobile());
                            c_landline = String.valueOf(acpModel.getC_LandLine());
                            c_ompImage = String.valueOf(acpModel.getC_UserImage());

                            UserId = String.valueOf(acpModel.getUserId());
                        }
                        list_pd.add(acpModel);

                    }
                    if (dataObjectRelatedAgentsToCompnay != null) {
                        for (int i = 0; i < dataObjectRelatedAgentsToCompnay.length(); i++) {
                            agentModel = new AgentModel();
                            JSONObject property = dataObjectRelatedAgentsToCompnay.getJSONObject(i);
                            agentModel.fromJson(property);
                            list_al.add(agentModel);
                        }
                    }
                    if (dataObjectPastSales != null) {
                        for (int i = 0; i < dataObjectPastSales.length(); i++) {
                            acpModel = new ACPModel();
                            JSONObject property = dataObjectPropertyListing.getJSONObject(i);
                            acpModel.fromJson(property);
                            list_spl.add(acpModel);
                        }
                    }
                    a_salepropertycount = String.valueOf(list_spl.size());
                    a_associates = String.valueOf(list_al.size());

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                swiper.setRefreshing(false);
                                swiper.setEnabled(false);
                                Picasso.with(userProfilePhoto.getContext()).load(UrlConstant.APPLICATION_URL + a_image).fit().centerCrop().placeholder(R.drawable.placeholder).into(userProfilePhoto);
                                Picasso.with(headerCoverImage.getContext()).load(UrlConstant.APPLICATION_URL + a_image).fit().centerCrop().placeholder(R.drawable.placeholder).into(headerCoverImage);
                                userProfileName.setText(a_name);
                                tvtitle.setText(a_name);
                                userProfileShortBio.setText(c_name);
                                tvcomapnyname.setText(c_name);
                                tvcomapnycontact.setText(c_email);
//                                txtstar.setText("");
                                tvEmail.setText(a_email);
                                tvNumber.setText(a_phone);
                                tvagentdesc.setText(a_intro);
                                //sdfsdf
                                if (a_rate.equals("null"))
                                    a_rate = "0.0";
                                Float rating = Float.parseFloat(a_rate);
                                tvrate.setText(String.format("%.1f", rating));
                                tvassociatesmembercount.setText("(" + a_associates + ") " + "Associates");
                                rtvreviewscount.setText(a_reviewscount + " Reviews - " + a_salepropertycount + " Sales");
                                rvAdapter rvAdaptera = new rvAdapter(AgentDetailActivity.this, list_al);
                                rvassociateslist.setAdapter(rvAdaptera);
                                if (!list_al.isEmpty()) {
                                    viewpageractive.setAdapter(new SimilarPropertyAdapter(AgentDetailActivity.this, list_pd, "AgentDetailActivity"));
                                    a_activelistlable = "Active List by " + a_name + " (" + list_pd.size() + ")";
                                    tvactivepropertycount.setText(a_activelistlable);
                                } else {
                                    viewpageractive.setVisibility(View.GONE);
                                    a_activelistlable = "No Active List by " + a_name + " (" + list_pd.size() + ")";
                                    tvsalepropertycount.setText(a_activelistlable);
                                }
                                if (!list_spl.isEmpty()) {
                                    viewpagersale.setAdapter(new SimilarPropertyAdapter(AgentDetailActivity.this, list_spl, "AgentDetailActivity"));
                                    a_salelistlable = "Past Sale by " + a_name + " (" + list_spl.size() + ")";
                                    tvsalepropertycount.setText(a_salelistlable);
                                } else {
                                    viewpagersale.setVisibility(View.GONE);
                                    a_salelistlable = "No Past Sale by " + a_name + " (" + list_spl.size() + ")";
                                    tvsalepropertycount.setText(a_salelistlable);
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

            handler.post(new Runnable() {
                @Override
                public void run() {
                    try {

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        public void onFail() throws Exception {

        }
    };

    private void findViews() {
        ivstar = (ImageView) findViewById(R.id.ivstar);
        ll_expand = (LinearLayout) findViewById(R.id.ll_expand);
        ll_adetail = (LinearLayout) findViewById(R.id.ll_adetail);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        swiper = (SwipeRefreshLayout) findViewById(R.id.swiper);
        swiper.setColorSchemeResources(R.color.colorPrimary);
        profileLayout = (CardView) findViewById(R.id.profile_layout);
        headerCoverImage = (ImageView) findViewById(R.id.header_cover_image);
        userProfilePhoto = (CircleImageView) findViewById(R.id.user_profile_photo);
        userProfileName = (RobotoTextView) findViewById(R.id.user_profile_name);
        userProfileShortBio = (RobotoTextView) findViewById(R.id.user_profile_short_bio);
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
        tvcomapnyname = (RobotoTextView) findViewById(R.id.tvcomapnyname);
        cardAgentdesc = (CardView) findViewById(R.id.card_agentdesc);
        rlagentdesc = (LinearLayout) findViewById(R.id.rlagentdesc);
        tvagentdesclabel = (RobotoTextView) findViewById(R.id.tvagentdesclabel);
        tvagentdesc = (RobotoTextView) findViewById(R.id.tvagentdesc);
        cardAssociates = (CardView) findViewById(R.id.card_associates);
        llassociates = (LinearLayout) findViewById(R.id.llassociates);
        tvassociateslabel = (RobotoTextView) findViewById(R.id.tvassociateslabel);
        rvassociateslist = (RecyclerView) findViewById(R.id.rvassociateslist);
        tvassociatesmembercount = (RobotoTextView) findViewById(R.id.tvassociatesmembercount);
        tvactivepropertycount = (RobotoTextView) findViewById(R.id.tv_activeplist);
        tvsalepropertycount = (RobotoTextView) findViewById(R.id.tv_saleplist);

        ivassociatelist = (ImageView) findViewById(R.id.ivassociateslist);
        ivactivelist = (ImageView) findViewById(R.id.ivactivelist);
        viewpageractive = (ViewPager) findViewById(R.id.viewpageractive);

        ivsalelist = (ImageView) findViewById(R.id.ivsalelist);
        viewpagersale = (ViewPager) findViewById(R.id.viewpagersale);


        tvtitle = (RobotoTextView) findViewById(R.id.tvtoolbartitle);
        LinearLayoutManager llmanager_associate = new LinearLayoutManager(AgentDetailActivity.this);
        llmanager_associate.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvassociateslist.setLayoutManager(llmanager_associate);
        ivstar.setOnClickListener(this);
        ivactivelist.setOnClickListener(this);
        ivsalelist.setOnClickListener(this);
        ivassociatelist.setOnClickListener(this);
        ivcall.setOnClickListener(this);
        ivemail.setOnClickListener(this);
        ivmap.setOnClickListener(this);
        ivaddreview.setOnClickListener(this);
        llrate.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ivactivelist:
                rv_expandelist = (RecyclerView) findViewById(R.id.rv_expandlist);
                rv_expandelist.setVisibility(View.VISIBLE);
                ListingAdapter listingAdapter = new ListingAdapter(AgentDetailActivity.this, list_pd, tag);
                LinearLayoutManager llmanager_expandlist = new LinearLayoutManager(AgentDetailActivity.this);
                rv_expandelist.setLayoutManager(llmanager_expandlist);
                rv_expandelist.setAdapter(listingAdapter);
                ll_expand.setVisibility(View.VISIBLE);
                ll_adetail.setVisibility(View.GONE);
                break;
            case R.id.ivsalelist:
                rv_expandelist = (RecyclerView) findViewById(R.id.rv_expandlist);
                ListingAdapter listingAdapter1 = new ListingAdapter(AgentDetailActivity.this, list_spl, tag);
                LinearLayoutManager llmanager_expandlist1 = new LinearLayoutManager(AgentDetailActivity.this);
                rv_expandelist.setLayoutManager(llmanager_expandlist1);
                rv_expandelist.setAdapter(listingAdapter1);
                ll_expand.setVisibility(View.VISIBLE);
                ll_adetail.setVisibility(View.GONE);

                break;
            case R.id.ivassociateslist:
                rv_expandelist = (RecyclerView) findViewById(R.id.rv_expandlist);
                AdapterAgents adapterAgents = new AdapterAgents(AgentDetailActivity.this, list_al, tag);
                LinearLayoutManager llmanager_expandlist2 = new LinearLayoutManager(AgentDetailActivity.this);
                rv_expandelist.setLayoutManager(llmanager_expandlist2);
                rv_expandelist.setAdapter(adapterAgents);
                ll_expand.setVisibility(View.VISIBLE);
                ll_adetail.setVisibility(View.GONE);
                break;
            case R.id.llrate:
                Intent i = new Intent(this, AgentReviewActivity.class);
                i.putExtra("agentid", a_id);
                i.putExtra("userid", UserId);
                startActivityForResult(i, addreviewreq);
                break;
            case R.id.ivcall:
                if (list_call.size() > 0) {
                    new ViewDialog(AgentDetailActivity.this, list_call).showDialog();
                } else {
                    Toast.makeText(this, "No Contact Number is available", Toast.LENGTH_SHORT).show();
                }
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
                    AddReviewDialogFragment.instantiate(a_id, UserId).show(this.getSupportFragmentManager(), "POST");
                } else {
                    startActivity(new Intent(AgentDetailActivity.this, SignupActivity.class));
                }
                break;
        }
    }

    public void sentmail() {
        String strSub = "Inquiry from reesguru member";
        String strBody = "sent from reesguru.com";
        String toemail = a_email;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_detail);
        findViews();

        toolbar = (Toolbar) findViewById(R.id.maintoolbar);
        toolbar.setNavigationIcon(TintDrawableHelper.getTintedResource(this, android.support.design.R.drawable.abc_ic_ab_back_material, R.color.colorWhite));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ll_expand.getVisibility() == View.VISIBLE) {
                    ll_expand.setVisibility(View.GONE);
                    ll_adetail.setVisibility(View.VISIBLE);
                } else {
                    finish();
                }


            }
        });


        Intent i = getIntent();
        try {
            a_id = i.getStringExtra("agentid");
            Log.e("a_id", "" + a_id);
        } catch (Exception e) {
            return;
        }
        getdata();
    }

    public void getdata() {
        swiper.setEnabled(true);
        swiper.setRefreshing(true);
        handler = new Handler(Looper.getMainLooper());
        //replace 44 with id
        RequestBody requestBody = new FormBody.Builder().addEncoded("currentPage", "0").addEncoded("AgentID", "" + a_id).build();
        lastCall = MyHttpClientHelper.getInstance().enqueueCall(AgentDetailActivity.this, UrlConstant.API_GET_AGENTS_DETAIL_URL, requestBody, mResponseHelper);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == addreviewreq) {
            getdata();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ReviewsEvent event) {
        if (event.getAction() == ReviewsEvent.ACTION_ADD) {
            getdata();
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

    public class rvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private LayoutInflater mInflater;
        private Context context;
        private ArrayList<AgentModel> list_ofal = new ArrayList<>();

        // AgentModel agentModel = new AgentModel();


        public rvAdapter(Context c, ArrayList<AgentModel> list_al) {
            this.context = c;
            this.list_ofal = list_al;
            mInflater = LayoutInflater.from(context);

        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == AdapterUtil.VIEW_EMPTY) {
                return new AdapterUtil.EmptyViewHolder(mInflater.inflate(viewType, parent, false), "No post to show");
            } else {
                return new AssociatesHolder(mInflater.inflate(R.layout.item_agencyagentlist, parent, false));
            }
//
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder.getItemViewType() == AdapterUtil.VIEW_EMPTY) {
                return;
            } else {
                ((AssociatesHolder) holder).bind(list_ofal.get(position));
            }
        }


        @Override
        public int getItemViewType(int position) {

            return super.getItemViewType(position);

        }

        @Override
        public int getItemCount() {
            return list_ofal.size();
        }


        public class AssociatesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private View itemView;
            private LinearLayout ll1;
            private CircleImageView associates1Pic;
            private RobotoTextView rtvassociates1Name;

            public AssociatesHolder(View itemView) {
                super(itemView);
                this.itemView = itemView;
                itemView.setOnClickListener(this);
                ll1 = (LinearLayout) itemView.findViewById(R.id.ll1);
                associates1Pic = (CircleImageView) itemView.findViewById(R.id.associates1_pic);
                rtvassociates1Name = (RobotoTextView) itemView.findViewById(R.id.rtvassociates1_name);
                itemView.setTag(getLayoutPosition());
            }

            public void bind(AgentModel acpModel) {
                String photo = acpModel.getA_UserImage();
                String name = acpModel.getA_FirstName();
                rtvassociates1Name.setText(name);
                Picasso.with(associates1Pic.getContext().getApplicationContext()).load(UrlConstant.APPLICATION_URL + photo).fit().centerCrop().placeholder(R.drawable.placeholder).into(associates1Pic);
                itemView.setTag(getLayoutPosition());
            }


            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                Intent i = new Intent(context, AgentDetailActivity.class);
                AgentModel agentModel = list_ofal.get(pos);
                i.putExtra("agentid", "" + agentModel.getAgentID());
                context.startActivity(i);
            }
        }
    }
}
