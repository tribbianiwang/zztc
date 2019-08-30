package com.yascn.smartpark.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.yascn.smartpark.R;
import com.yascn.smartpark.bean.CarLicense;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.RvItemClickListener;
import com.yascn.smartpark.utils.RvItemLongClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by YASCN on 2017/9/6.
 */


public class RvCarLicenseAdapter extends RecyclerView.Adapter<RvCarLicenseAdapter.ViewHolder> {


    private Activity mActivity;


    private RvItemClickListener mItemClickListener;
    private RvItemLongClickListener mItemLongListener;
    private  CarLicense carLicense;

    private static final String TAG = "RvCarLicenseAdapter";
    public RvCarLicenseAdapter(Activity activity, CarLicense carLicense) {

        this.mActivity = activity;
        this.carLicense = carLicense;
        LogUtil.d(TAG, "RvCarLicenseAdapter: "+carLicense.getObject().size());

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.rv_change_car_license_item, parent, false);
        return new ViewHolder(view, mItemClickListener, mItemLongListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CarLicense.ObjectBean objectBean = carLicense.getObject().get(position);
        holder.tvCarLicense.setText(objectBean.getNUMBER());
        LogUtil.d(TAG, "onBindViewHolder: "+objectBean.getNUMBER());
//        holder.tv_author.setText(userName);
//        holder.tvCarType.setText("A"+position);



    }

    @Override
    public int getItemCount() {
        LogUtil.d(TAG, "getItemCount: "+carLicense.getObject().size());
        return carLicense.getObject().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.tv_car_license)
        TextView tvCarLicense;

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
            if (mLongListener!= null) {
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