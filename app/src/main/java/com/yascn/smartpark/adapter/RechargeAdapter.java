package com.yascn.smartpark.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.yascn.smartpark.dialog.TimeoutDialog;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.DateUtils;
import com.yascn.smartpark.utils.T;

import java.io.Serializable;
import java.util.List;

/**
 * Created by YASCN on 2017/8/30.
 */

public class RechargeAdapter extends RecyclerView.Adapter<RechargeAdapter.ViewHolder> {

    private List<ReRecordList.ObjectBean> reRecordLists;
    private List<String> monthList;
    private AppCompatActivity context;
    private float density;
    private boolean isRechage;

    public RechargeAdapter(AppCompatActivity context, List<ReRecordList.ObjectBean> reRecordLists, List<String> monthList,boolean isRechage) {
        this.context = context;
        this.reRecordLists = reRecordLists;
        this.monthList = monthList;
        this.isRechage = isRechage;

        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();

        density = dm.density;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recharge, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if (position == 0) {
            holder.monthVew.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (100 * density));
            holder.contentView.setLayoutParams(layoutParams);
        } else {
            if (monthList.get(position).equals(monthList.get(position - 1))) {
                holder.monthVew.setVisibility(View.GONE);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (70 * density));
                holder.contentView.setLayoutParams(layoutParams);
            } else {
                holder.monthVew.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (100 * density));
                holder.contentView.setLayoutParams(layoutParams);
            }
        }

        if (reRecordLists.get(position).getR_list().size() == 0) {
            holder.timeout.setVisibility(View.GONE);
        } else {
            holder.timeout.setVisibility(View.VISIBLE);
            holder.contentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("rListBeen", (Serializable) reRecordLists.get(position).getR_list());
                    TimeoutDialog timeoutDialog = new TimeoutDialog();
                    timeoutDialog.setArguments(bundle);
                    timeoutDialog.show(context.getSupportFragmentManager(), "TimeoutDialog");
                }
            });
        }

        holder.img.setImageResource(isRechage?R.drawable.icon_check_large_green:R.drawable.icon_check_large);
        switch (reRecordLists.get(position).getTYPE()) {

//            case AppContants.ZHIFUBAO:
//                holder.img.setImageResource(R.drawable.icon_pay_detail_alipay);
//                break;
//            case AppContants.WEIXIN:
//                holder.img.setImageResource(R.drawable.icon_pay_detail_wechat);
//                break;
//            case AppContants.YVE:
//                holder.img.setImageResource(R.drawable.icon_pay_detail_balance);
//                break;
        }

        holder.month.setText(DateUtils.monthToChinese(monthList.get(position)));
        holder.date.setText(DateUtils.getDate(reRecordLists.get(position).getTIME()));
        holder.time.setText(DateUtils.getTime(reRecordLists.get(position).getTIME()));
        if (reRecordLists.get(position).getCATE().equals(AppContants.SHOURU)) {
            holder.money.setText("+" + reRecordLists.get(position).getMONEY());
        } else {
            holder.money.setText("-" + reRecordLists.get(position).getMONEY());
        }
    }

    @Override
    public int getItemCount() {
        return reRecordLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout monthVew, contentView;
        private TextView month;
        private TextView date, time, money;
        private ImageView img;
        private TextView timeout;

        public ViewHolder(View itemView) {
            super(itemView);

            monthVew = (LinearLayout) itemView.findViewById(R.id.monthView);
            contentView = (LinearLayout) itemView.findViewById(R.id.contentView);
            month = (TextView) itemView.findViewById(R.id.month);
            date = (TextView) itemView.findViewById(R.id.date);
            time = (TextView) itemView.findViewById(R.id.time);
            money = (TextView) itemView.findViewById(R.id.money);
            img = (ImageView) itemView.findViewById(R.id.img);
            timeout = (TextView) itemView.findViewById(R.id.timeout);

        }
    }
}
