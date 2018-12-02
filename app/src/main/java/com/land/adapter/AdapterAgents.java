package com.land.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.land.AgentDetailActivity;
import com.land.R;
import com.land.api.model.ACPModel;
import com.land.api.model.AgentModel;
import com.land.constant.UrlConstant;
import com.land.custom.RobotoTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.land.helper.StringHelper.isEmptyString;

/**
 * Created by Dashrath on 10/11/2017.
 */

public class AdapterAgents extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<AgentModel> listofallagent = new ArrayList<>();
    ArrayList<AgentModel> list_associateagent = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;
    private String tag = null;

    // AgentModel agentModel = new AgentModel();
    public AdapterAgents(Context context, ArrayList<AgentModel> listallagent) {
        this.listofallagent = listallagent;
        this.context = context;
        this.tag = "";
        mInflater = android.view.LayoutInflater.from(context);
    }

    public AdapterAgents(AgentDetailActivity context, ArrayList<AgentModel> list_al, String tag) {
        this.list_associateagent = list_al;
        this.context = context;
        this.tag = tag;
        mInflater = android.view.LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == AdapterUtil.VIEW_EMPTY) {
            return new AdapterUtil.EmptyViewHolder(mInflater.inflate(viewType, parent, false), "No post to show");
        } else {
            return new AgentHolder(mInflater.inflate(R.layout.items_ajents, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (tag.equals("")) {
            if (holder.getItemViewType() == AdapterUtil.VIEW_EMPTY) {
                return;
            } else {
                ((AgentHolder) holder).bind(listofallagent.get(position));
            }
        } else if (tag.equals("AgentDetailActivity")) {
            if (holder.getItemViewType() == AdapterUtil.VIEW_EMPTY) {
                return;
            } else {
                ((AgentHolder) holder).bind(list_associateagent.get(position));
            }
        }
    }


    @Override
    public int getItemViewType(int position) {

        if (tag.equals("")) {
            if (listofallagent.size() == 0) {
                return AdapterUtil.VIEW_EMPTY;
            } else {
                return super.getItemViewType(position);
            }
        } else if (tag.equals("AgentDetailActivity")) {
            if (list_associateagent.size() == 0) {
                return AdapterUtil.VIEW_EMPTY;
            } else {
                return super.getItemViewType(position);
            }
        } else {
            return 0;
        }
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (tag.equals("")) {
            size = listofallagent.size();
            if (size == 0) {
                size = 1;
            }
        } else if (tag.equals("AgentDetailActivity")) {
            size = list_associateagent.size();
            if (size == 0) {
                size = 1;
            }
        }

        return size;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class AgentHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private View itemView;
        private CardView cardOne;
        private RobotoTextView txtAjentsName;
        private RobotoTextView txtAgentsPhone;
        private RobotoTextView txtAgentsEmail;
        private ImageView ajentImage;


        public AgentHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            itemView.setOnClickListener(this);
            cardOne = (CardView) itemView.findViewById(R.id.card_one);
            txtAjentsName = (RobotoTextView) itemView.findViewById(R.id.txt_ajents_name);
            txtAgentsPhone = (RobotoTextView) itemView.findViewById(R.id.txt_agents_phone);
            txtAgentsEmail = (RobotoTextView) itemView.findViewById(R.id.txt_agents_email);
            ajentImage = (ImageView) itemView.findViewById(R.id.ajent_image);
        }

        public void bind(AgentModel a) {
            Picasso.with(ajentImage.getContext()).load(UrlConstant.APPLICATION_URL + a.getA_UserImage()).fit().centerCrop().placeholder(R.drawable.placeholder).into(ajentImage);
            if (!isEmptyString(a.getA_FirstName())) {
                if (!isEmptyString(a.getA_LastName())) {
                    txtAjentsName.setText("" + a.getA_FirstName() + ", " + a.getA_LastName());
                } else {
                    txtAjentsName.setText("" + a.getA_FirstName());
                }
            }

            txtAgentsPhone.setText("" + a.getA_Telephone());
            txtAgentsEmail.setText("" + a.getA_EmailID());
            itemView.setTag(getLayoutPosition());
        }

        public void bind(ACPModel a) {
            Picasso.with(ajentImage.getContext()).load(UrlConstant.APPLICATION_URL + a.getA_UserImage()).fit().centerCrop().placeholder(R.drawable.placeholder).into(ajentImage);
            txtAjentsName.setText("" + a.getA_FirstName() + ", " + a.getA_LastName());
            txtAgentsPhone.setText("" + a.getA_Telephone());
            txtAgentsEmail.setText("" + a.getA_EmailID());
            itemView.setTag(getLayoutPosition());
        }

        @Override
        public void onClick(View v) {
            int pos = (int) v.getTag();
            Intent i = new Intent(context, AgentDetailActivity.class);
            if (tag.equals("")) {
                AgentModel agentModel = listofallagent.get(pos);
                i.putExtra("agentid", "" + agentModel.getAgentID());
//                i.putExtra("agentid", "" +"8");

            } else if (tag.equals("AgentDetailActivity")) {
                AgentModel agentModel = list_associateagent.get(pos);
                i.putExtra("agentid", "" + agentModel.getAgentID());
                Log.e("agentid", "" + agentModel.getAgentID());
//                i.putExtra("agentid", "" +"8");
            }
            context.startActivity(i);

        }


    }
}
