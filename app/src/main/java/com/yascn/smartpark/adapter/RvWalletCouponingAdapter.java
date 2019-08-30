package com.yascn.smartpark.adapter;

import android.app.Activity;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.yascn.smartpark.R;
import com.yascn.smartpark.bean.CouponingBean;
import com.yascn.smartpark.databinding.LayoutRvItemWalletComponingBinding;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.DateUtils;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.RvItemClickListener;
import com.yascn.smartpark.utils.RvItemLongClickListener;
import com.yascn.smartpark.utils.StringUtils;

import org.androidannotations.annotations.App;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by YASCN on 2017/9/6.
 */


public class RvWalletCouponingAdapter extends RecyclerView.Adapter<RvWalletCouponingAdapter.ViewHolder> {



    private Activity mActivity;


    private RvItemClickListener mItemClickListener;
    private RvItemLongClickListener mItemLongListener;
    private List<CouponingBean.ObjectBean> couponintBeans;


    public RvWalletCouponingAdapter(Activity activity, List<CouponingBean.ObjectBean> couponintBeans) {

        this.mActivity = activity;
        this.couponintBeans = couponintBeans;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_rv_item_wallet_componing, parent, false);
        return new ViewHolder(view, mItemClickListener, mItemLongListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.bind(couponintBeans.get(position));
        holder.layoutRvItemWalletComponingBinding.executePendingBindings();//加一行，问题解决
    }

    @BindingAdapter(value = {"originaldata"})
    public static void setCouponDuedate(TextView tvDuedate,String originalData){
        tvDuedate.setText(DateUtils.getTransZnDate(originalData)+ StringUtils.getRString(tvDuedate.getContext(),R.string.string_time_bottom));

    }



    private static final String TAG = "RvWalletCouponingAdapte";

    @Override
    public int getItemCount() {
        LogUtil.d(TAG, "getItemCount: "+couponintBeans.size());
        return couponintBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {



        private RvItemClickListener mListener;
        private RvItemLongClickListener mLongListener;
        public LayoutRvItemWalletComponingBinding layoutRvItemWalletComponingBinding;


        public ViewHolder(final View couponItemView, RvItemClickListener listener, RvItemLongClickListener longListener) {
            super(couponItemView);

            layoutRvItemWalletComponingBinding = DataBindingUtil.bind(couponItemView);

            this.mListener = listener;
            this.mLongListener = longListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);


        }

        public void bind(@NonNull CouponingBean.ObjectBean couponBean ) {
            layoutRvItemWalletComponingBinding.setCouponbean(couponBean);
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