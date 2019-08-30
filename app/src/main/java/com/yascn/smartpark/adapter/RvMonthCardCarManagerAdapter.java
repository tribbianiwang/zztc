package com.yascn.smartpark.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yascn.smartpark.R;
import com.yascn.smartpark.activity.MonthCardActivity;
import com.yascn.smartpark.activity.MonthCardDetailActivity;
import com.yascn.smartpark.bean.NumberList;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.RvItemClickListener;
import com.yascn.smartpark.utils.RvItemLongClickListener;
import com.yascn.smartpark.utils.StringUtils;
import com.yascn.smartpark.utils.T;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yascn.smartpark.utils.AppContants.ISREFILL;
import static com.yascn.smartpark.utils.AppContants.MONTHCARDCARNUMBER;
import static com.yascn.smartpark.utils.AppContants.MONTHCARDRECORDID;
import static com.yascn.smartpark.utils.AppContants.MONTHCARDREMAINDAYS;
import static com.yascn.smartpark.utils.AppContants.PARKIDTRANS;
import static com.yascn.smartpark.utils.AppContants.PARKNAMETRANS;

/**
 * Created by YASCN on 2017/9/6.
 */


public class RvMonthCardCarManagerAdapter extends RecyclerView.Adapter<RvMonthCardCarManagerAdapter.ViewHolder> {


    @BindView(R.id.tv_park_name)
    TextView tvParkName;
    @BindView(R.id.tv_remain_days)
    TextView tvRemainDays;
    @BindView(R.id.tv_refill)
    TextView tvRefill;
    private Activity mActivity;

    private static final String TAG = "RvMonthCardCarManagerAd";

    private RvItemClickListener mItemClickListener;
    private RvItemLongClickListener mItemLongListener;

    private List<NumberList.ObjectBean.MCARDLISTBean> mcardList;
    private String carNumber;

    public RvMonthCardCarManagerAdapter(Activity activity, List<NumberList.ObjectBean.MCARDLISTBean> mcardList, String number) {

        this.mActivity = activity;
        this.mcardList = mcardList;
        this.carNumber = number;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_month_card_carmanager_item, parent, false);
        return new ViewHolder(view, mItemClickListener, mItemLongListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if(AppContants.REFILLABLE.equals(mcardList.get(position).getFLAG())){
            holder.tvRefill.setVisibility(View.VISIBLE);
        }else if(AppContants.REFILLUNABLE.equals(mcardList.get(position).getFLAG())){
            holder.tvRefill.setVisibility(View.GONE);
        }
//        holder.tv_author.setText(userName);
//        holder.tvCarType.setText("A"+position);
        holder.tvParkName.setText(mcardList.get(position).getPARK_NAME());
        holder.tvRemainDays.setText(StringUtils.getRString(mActivity,R.string.string_remain_days)+mcardList.get(position).getSY_DAYS()+StringUtils.getRString(mActivity,R.string.string_day));
        holder.tvRefill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity,MonthCardDetailActivity.class);
                intent.putExtra(PARKNAMETRANS,mcardList.get(position).getPARK_NAME());
                intent.putExtra(PARKIDTRANS,mcardList.get(position).getPARKING_ID());
                intent.putExtra(ISREFILL,true);
                intent.putExtra(MONTHCARDREMAINDAYS,mcardList.get(position).getSY_DAYS());
                intent.putExtra(MONTHCARDRECORDID,mcardList.get(position).getMCARDRECORD_ID());
                intent.putExtra(MONTHCARDCARNUMBER,carNumber);
                LogUtil.d(TAG, "onClick: carnumber"+carNumber);
                mActivity.startActivity(intent);
//                T.showShort(mActivity,mcardList.get(position).getPARK_NAME()+"--"+mcardList.get(position).getSY_DAYS());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mcardList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        @BindView(R.id.tv_park_name)
        TextView tvParkName;
        @BindView(R.id.tv_remain_days)
        TextView tvRemainDays;
        @BindView(R.id.tv_refill)
        TextView tvRefill;
        private RvItemClickListener mListener;
        private RvItemLongClickListener mLongListener;


        public ViewHolder(final View itemView, RvItemClickListener listener, RvItemLongClickListener longListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.mListener = listener;
            this.mLongListener = longListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);


        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (mLongListener != null) {
                mLongListener.onItemLongClick(v, getAdapterPosition());
            }
            return true;
        }

    }


    public void setOnItemClickListener(RvItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public void setOnItemLongClickListener(RvItemLongClickListener listener) {
        this.mItemLongListener = listener;
    }

}