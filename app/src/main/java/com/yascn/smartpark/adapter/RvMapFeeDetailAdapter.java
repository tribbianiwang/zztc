package com.yascn.smartpark.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yascn.smartpark.R;
import com.yascn.smartpark.utils.DensityUtils;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.RvItemClickListener;
import com.yascn.smartpark.utils.RvItemLongClickListener;
import com.yascn.smartpark.utils.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yascn.smartpark.utils.AppContants.SPLITSYMBOL;

public class RvMapFeeDetailAdapter extends RecyclerView.Adapter<RvMapFeeDetailAdapter.ViewHolder> {



//
//    private Activity mActivity;


    private RvItemClickListener mItemClickListener;
    private RvItemLongClickListener mItemLongListener;


    private Context context;
    private List<String> costDetails;


    public RvMapFeeDetailAdapter(Context context, List<String> costDetails) {
        this.context = context;
        this.costDetails = costDetails;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_rv_map_fee_detail_item, parent, false);
        return new ViewHolder(view, mItemClickListener, mItemLongListener);
    }

    private static final String TAG = "RvMapFeeDetailAdapter";

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String[] costArray=costDetails.get(position).split(SPLITSYMBOL);
        for(int i=0;i<costArray.length;i++){
            LogUtil.d(TAG, "onBindViewHolder: "+costArray[i]+"---"+position);
        }

        if(costArray.length==1){
            holder.tvFeeTitle.setText(costArray[0]);
        }

        if(costArray.length>1){
            holder.tvFeeTitle.setText(costArray[0]);
            holder.tvFeeMoney.setText(costArray[1]);
        }


        if(position!=0){
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.topMargin = DensityUtils.dp2px(context,-1);
            holder.llRoot.setLayoutParams(layoutParams);
        }


    }

    @Override
    public int getItemCount() {

        return costDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.tv_fee_title)
        TextView tvFeeTitle;
        @BindView(R.id.tv_fee_money)
        TextView tvFeeMoney;
        @BindView(R.id.ll_root)
        LinearLayout llRoot;
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