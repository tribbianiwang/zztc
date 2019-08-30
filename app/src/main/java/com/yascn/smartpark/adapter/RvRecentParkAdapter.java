package com.yascn.smartpark.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;

import com.yascn.smartpark.R;
import com.yascn.smartpark.bean.RecentParkBean;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.MapUtils;
import com.yascn.smartpark.utils.RvItemClickListener;
import com.yascn.smartpark.utils.RvItemLongClickListener;
import com.yascn.smartpark.utils.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yascn.smartpark.utils.AppContants.EMPTYPARKNUM;
import static com.yascn.smartpark.utils.AppContants.ENOUGHPARKNUM;
import static com.yascn.smartpark.utils.AppContants.MAXPARKNUMLINE;
import static com.yascn.smartpark.utils.AppContants.PARKFREENUMREDLINE;

/**
 * Created by YASCN on 2017/9/6.
 */


public class RvRecentParkAdapter extends RecyclerView.Adapter<RvRecentParkAdapter.ViewHolder> {


    private Activity mActivity;


    private RvItemClickListener mItemClickListener;
    private RvItemLongClickListener mItemLongListener;

    List<RecentParkBean.ObjectBean> recentParkBeanObjects;
    private static final String TAG = "RvCarLicenseAdapter";
    private double nowlocationLat;
    private double nowlocationLon;

    public RvRecentParkAdapter(Activity activity, double nowlocationLat, double nowlocationLon, List<RecentParkBean.ObjectBean> recentParkBeanObjects) {

        this.mActivity = activity;
        this.nowlocationLat = nowlocationLat;
        this.nowlocationLon = nowlocationLon;
        LogUtil.d(TAG, "RvRecentParkAdapter: " + nowlocationLat + ":" + nowlocationLon);
        this.recentParkBeanObjects = recentParkBeanObjects;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.rv_recent_park_item, parent, false);
        return new ViewHolder(view, mItemClickListener, mItemLongListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RecentParkBean.ObjectBean objectBean = recentParkBeanObjects.get(position);

            int parkFreeNum = StringUtils.emptyParseInt(objectBean.getFREE_NUM());


        holder.tvParkName.setText(objectBean.getNAME());
        holder.tvAddress.setText(objectBean.getADDRESS());

        if (nowlocationLat == 0.0 || nowlocationLat == 0.0) {
            holder.tvDistance.setText("暂无距离");
        }else{
            holder.tvDistance.setText(MapUtils.calculateServerDistance(mActivity, objectBean.getLAT(), objectBean.getLNG(), new LatLng(nowlocationLat, nowlocationLon)));
        }

        if(parkFreeNum>PARKFREENUMREDLINE){
            //车位充足
            holder.tvParkName.setTextColor(StringUtils.getRColor(mActivity,R.color.design_main_blue));
            if(parkFreeNum>MAXPARKNUMLINE){
                holder.tvFreeNum.setText(ENOUGHPARKNUM);
            }else{
                holder.tvFreeNum.setText(objectBean.getFREE_NUM());
            }
            holder.tvFreeNum.setTextColor(StringUtils.getRColor(mActivity,R.color.design_main_blue));
            holder.tvFreeNumBottomTitle.setTextColor(StringUtils.getRColor(mActivity,R.color.design_main_blue));
            holder.tvDistance.setBackgroundResource(R.drawable.rounded_blue_bg);
            holder.rlRecentItemRoot.setBackgroundResource(R.drawable.rounded_white_bg);
            holder.tvFreeNum.setVisibility(View.VISIBLE);
            holder.tvFreeNumBottomTitle.setVisibility(View.VISIBLE);
            holder.tvNoFreeNum.setVisibility(View.GONE);

        }else if(parkFreeNum==0||parkFreeNum<0){
            //车位已满
            holder.tvParkName.setTextColor(StringUtils.getRColor(mActivity,R.color.gray_deep));
//            holder.tvFreeNum.setTextColor(StringUtils.getRColor(mActivity,R.color.design_bg_blue));
//            holder.tvFreeNumBottomTitle.setText(StringUtils.getRColor(mActivity,R.color.design_bg_blue));
            holder.tvDistance.setBackgroundResource(R.drawable.rounded_deepgray_bg);
            holder.rlRecentItemRoot.setBackgroundResource(R.drawable.rounded_gray_bg);
            holder.tvFreeNum.setVisibility(View.GONE);
            holder.tvFreeNumBottomTitle.setVisibility(View.GONE);
            holder.tvNoFreeNum.setVisibility(View.VISIBLE);
            holder.tvFreeNum.setText(EMPTYPARKNUM);


        }else{
            //车位剩余不多
            holder.tvFreeNum.setText(objectBean.getFREE_NUM());
            holder.tvParkName.setTextColor(StringUtils.getRColor(mActivity,R.color.design_main_orange));
            holder.tvFreeNum.setTextColor(StringUtils.getRColor(mActivity,R.color.design_main_orange));
            holder.tvFreeNumBottomTitle.setTextColor(StringUtils.getRColor(mActivity,R.color.design_main_orange));
            holder.tvDistance.setBackgroundResource(R.drawable.rounded_red_bg);
            holder.rlRecentItemRoot.setBackgroundResource(R.drawable.rounded_white_bg);
            holder.tvFreeNum.setVisibility(View.VISIBLE);
            holder.tvFreeNumBottomTitle.setVisibility(View.VISIBLE);
            holder.tvNoFreeNum.setVisibility(View.GONE);
        }





    }




    @Override
    public int getItemCount() {

        return recentParkBeanObjects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        @BindView(R.id.tv_free_num)
        TextView tvFreeNum;
        @BindView(R.id.tv_free_num_Bottom_title)
        TextView tvFreeNumBottomTitle;
        @BindView(R.id.tv_no_free_num)
        TextView tvNoFreeNum;
        @BindView(R.id.tv_park_name)
        TextView tvParkName;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_distance)
        TextView tvDistance;
        @BindView(R.id.rl_recent_item_root)
        RelativeLayout rlRecentItemRoot;
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