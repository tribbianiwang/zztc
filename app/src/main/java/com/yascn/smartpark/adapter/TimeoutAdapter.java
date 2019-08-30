package com.yascn.smartpark.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yascn.smartpark.R;
import com.yascn.smartpark.bean.ReRecordList;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.DateUtils;
import com.yascn.smartpark.utils.T;

import java.util.List;

/**
 * Created by YASCN on 2017/8/30.
 */

public class TimeoutAdapter extends RecyclerView.Adapter<TimeoutAdapter.ViewHolder> {

    private List<ReRecordList.ObjectBean.RListBean> rListBeen;
    private Context context;

    public TimeoutAdapter(Context context, List<ReRecordList.ObjectBean.RListBean> rListBeen) {
        this.context = context;
        this.rListBeen = rListBeen;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_timeout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        switch (rListBeen.get(position).getTYPE()) {
            case AppContants.ZHIFUBAO:
                holder.img.setImageResource(R.drawable.icon_pay_detail_alipay);
                break;
            case AppContants.WEIXIN:
                holder.img.setImageResource(R.drawable.icon_pay_detail_wechat);
                break;
            case AppContants.YVE:
                holder.img.setImageResource(R.drawable.icon_pay_detail_balance);
                break;
        }

        holder.date.setText(DateUtils.getDate(rListBeen.get(position).getTIME()));
        holder.time.setText(DateUtils.getTime(rListBeen.get(position).getTIME()));
        if (rListBeen.get(position).getCATE().equals(AppContants.SHOURU)) {
            holder.money.setText("+" + rListBeen.get(position).getMONEY());
        } else {
            holder.money.setText("-" + rListBeen.get(position).getMONEY());
        }
    }

    @Override
    public int getItemCount() {
        return rListBeen.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView date, time, money;
        private ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);

            date = (TextView) itemView.findViewById(R.id.date);
            time = (TextView) itemView.findViewById(R.id.time);
            money = (TextView) itemView.findViewById(R.id.money);
            img = (ImageView) itemView.findViewById(R.id.img);
        }
    }
}
