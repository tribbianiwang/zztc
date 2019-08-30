package com.yascn.smartpark.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yascn.smartpark.R;
import com.yascn.smartpark.utils.RvItemClickListener;
import com.yascn.smartpark.utils.RvItemLongClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by YASCN on 2017/9/6.
 */


public class RvPayRulesAdapter extends RecyclerView.Adapter<RvPayRulesAdapter.ViewHolder> {



            private Activity mActivity;


            private RvItemClickListener mItemClickListener;
            private RvItemLongClickListener mItemLongListener;
            private List<String> costDetail;

    public RvPayRulesAdapter(Activity activity, List<String> costDetail) {

                this.mActivity = activity;
                this.costDetail = costDetail;

            }

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_fee_rules_item, parent, false);
                ButterKnife.bind(this, view);
                return new ViewHolder(view, mItemClickListener, mItemLongListener);
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {

                holder.tvCostDetail.setText((position+1)+"."+costDetail.get(position));
//        holder.tv_author.setText(userName);
//        holder.tvCarType.setText("A"+position);


            }

            @Override
            public int getItemCount() {
                return costDetail.size();
            }

            public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
                @BindView(R.id.tv_cost_detail)
                TextView tvCostDetail;

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