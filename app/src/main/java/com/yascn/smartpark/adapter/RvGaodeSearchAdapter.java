package com.yascn.smartpark.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.yascn.smartpark.R;
import com.yascn.smartpark.utils.RvItemClickListener;
import com.yascn.smartpark.utils.RvItemLongClickListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by YASCN on 2017/9/6.
 */


public class RvGaodeSearchAdapter extends RecyclerView.Adapter<RvGaodeSearchAdapter.ViewHolder> {



    private Activity mActivity;


    private RvItemClickListener mItemClickListener;
    private RvItemLongClickListener mItemLongListener;
    private ArrayList<PoiItem> pois;

    private static final String TAG = "RvCarLicenseAdapter";

    public RvGaodeSearchAdapter(Activity activity, ArrayList<PoiItem> pois) {

        this.mActivity = activity;
        this.pois = pois;
//        LogUtil.d(TAG, "RvCarLicenseAdapter: "+carLicense.getObject().size());

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.rv_gaode_item, parent, false);
        return new ViewHolder(view, mItemClickListener, mItemLongListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

//        holder.tv_author.setText(userName);
//        holder.tvCarType.setText("A"+position);
        holder.tvLocation.setText(pois.get(position).getTitle());
        holder.tvAddress.setText(pois.get(position).getSnippet());

    }

    @Override
    public int getItemCount() {
        return pois.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        @BindView(R.id.tv_location)
        TextView tvLocation;

        @BindView(R.id.tv_address)
        TextView tvAddress;


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