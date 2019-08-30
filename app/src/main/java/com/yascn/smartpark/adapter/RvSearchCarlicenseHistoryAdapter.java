package com.yascn.smartpark.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yascn.smartpark.R;
import com.yascn.smartpark.bean.GreenDaoCarLicense;
import com.yascn.smartpark.utils.RvItemClickListener;
import com.yascn.smartpark.utils.RvItemLongClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by YASCN on 2017/9/6.
 */


public class RvSearchCarlicenseHistoryAdapter extends RecyclerView.Adapter<RvSearchCarlicenseHistoryAdapter.ViewHolder> {


    private Activity mActivity;


    private RvItemClickListener mItemClickListener;
    private RvItemLongClickListener mItemLongListener;
    private List<GreenDaoCarLicense> greenDaoCarLicenses;


    public RvSearchCarlicenseHistoryAdapter(Activity activity, List<GreenDaoCarLicense> greenDaoCarLicenses) {

        this.mActivity = activity;
        this.greenDaoCarLicenses = greenDaoCarLicenses;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_carlicense_search_history, parent, false);
        return new ViewHolder(view, mItemClickListener, mItemLongListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            holder.tvCarLicense.setText(greenDaoCarLicenses.get(position).getCarLicense());

//        holder.tv_author.setText(userName);
//        holder.tvCarType.setText("A"+position);


    }

    @Override
    public int getItemCount() {
        if(greenDaoCarLicenses.size()>3){
            return 3;
        }else{
            return greenDaoCarLicenses.size();
        }

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