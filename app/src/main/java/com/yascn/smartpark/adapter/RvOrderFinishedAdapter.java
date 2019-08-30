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
import com.yascn.smartpark.activity.OrderDetailActivity;
import com.yascn.smartpark.bean.OrderList;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.RvItemClickListener;
import com.yascn.smartpark.utils.RvItemLongClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yascn.smartpark.utils.AppContants.ORDERFORMID;

/**
 * Created by YASCN on 2018/4/25.
 */

public class RvOrderFinishedAdapter extends RecyclerView.Adapter<RvOrderFinishedAdapter.ViewHolder> {



    private Activity mActivity;


    private RvItemClickListener mItemClickListener;
    private RvItemLongClickListener mItemLongListener;
    List<OrderList.ObjectBean.OrderListBean> orderListBeans;

    public RvOrderFinishedAdapter(Activity activity, List<OrderList.ObjectBean.OrderListBean> costDetail) {

        this.mActivity = activity;
        this.orderListBeans = costDetail;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_order_finish, parent, false);
        ButterKnife.bind(this, view);
        return new ViewHolder(view, mItemClickListener, mItemLongListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvPayMethod.setText(orderListBeans.get(position).getPAY_METHOD());
        holder.tvParkName.setText(orderListBeans.get(position).getNAME());
        holder.tvCarLicense.setText(orderListBeans.get(position).getNUMBER());
        holder.tvInTime.setText(orderListBeans.get(position).getSTART_TIME());
        holder.tvParkDuringTime.setText(mActivity.getString(R.string.string_park_during_title_short) + orderListBeans.get(position).getTIME());
//        holder.tvInTime.setText(orderListBeans.get(position).getSTART_TIME()+mActivity.getString(R.string.string_title_in));
//        holder.tvOutTime.setText(orderListBeans.get(position).getEND_TINE()+mActivity.getString(R.string.string_title_out));
        holder.tvMoney.setText(mActivity.getString(R.string.string_title_money) + orderListBeans.get(position).getMONEY());
        if (orderListBeans.get(position).getIS_COM().equals(AppContants.HASCOMMENT)) {
//                holder.tvCommentStatus.setClickable(false);
//                holder.tvCommentStatus.setText(StringUtils.getRString(mActivity,R.string.string_hascomment));
//                holder.tvCommentStatus.setTextColor(StringUtils.getRColor(mActivity,R.color.design_text_gray));

        } else if (orderListBeans.get(position).getIS_COM().equals(AppContants.NOTCOMMENT)) {
//            holder.tvCommentStatus.setClickable(true);
//            holder.tvCommentStatus.setText(StringUtils.getRString(mActivity,R.string.string_not_comment));
//            holder.tvCommentStatus.setTextColor(StringUtils.getRColor(mActivity,R.color.design_main_blue));
//            holder.tvCommentStatus.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(mActivity, EvaluateActivity.class);
            LogUtil.d(TAG, "onClick:orderformid" + orderListBeans.get(position).getORDER_FORM_ID());
//                    intent.putExtra(ORDERFORMID,orderListBeans.get(position).getORDER_FORM_ID());
//                    mActivity.startActivity(intent);
//
//
//                }
//            });

        }


        //        holder.tv_author.setText(userName);
//        holder.tvCarType.setText("A"+position);

//        holder.tvOrderDetail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(mActivity, OrderDetailActivity.class);
//                intent.putExtra(AppContants.ORDERDETAILBEAN,orderListBeans.get(position));
//                mActivity.startActivity(intent);
//
//            }
//        });

    }

    private static final String TAG = "RvOrderFinishedAdapter";

    @Override
    public int getItemCount() {
        LogUtil.d(TAG, "getItemCount: " + orderListBeans.size());
        return orderListBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        @BindView(R.id.tv_park_name)
        TextView tvParkName;
        @BindView(R.id.tv_car_license)
        TextView tvCarLicense;
        @BindView(R.id.tv_park_during_time)
        TextView tvParkDuringTime;
        @BindView(R.id.tv_pay_method)
        TextView tvPayMethod;
        @BindView(R.id.tv_money)
        TextView tvMoney;

        @BindView(R.id.tv_in_time)
        TextView tvInTime;

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
