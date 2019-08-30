package com.yascn.smartpark.adapter;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.yascn.smartpark.R;
import com.yascn.smartpark.activity.MyRecordActivity;
import com.yascn.smartpark.bean.Record;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.ClickUtil;
import com.yascn.smartpark.utils.DateUtils;
import com.yascn.smartpark.utils.ImageViewUtils;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.StringUtils;
import com.yascn.smartpark.utils.T;

import org.androidannotations.annotations.App;

import java.util.List;

import static com.yascn.smartpark.utils.AppContants.BROADCASTACTIONSEARCHPARK;
import static com.yascn.smartpark.utils.AppContants.BROADCASTDATASEARCHPARK;
import static com.yascn.smartpark.utils.AppContants.RECORD_BOTTOM_HEIGHT;

/**
 * Created by YASCN on 2017/8/30.
 */

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {

    private static final String TAG = "RecordAdapter";
    private MyRecordActivity context;
    private List<Record> records;
    private View newOpenView, oldOpenView;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == AppContants.CLOSE_RECORD_ITEM) {
                if (oldOpenView != null) {
                    closeAnim(oldOpenView, 0, RECORD_BOTTOM_HEIGHT);
                }
            }
        }
    };

    public RecordAdapter(MyRecordActivity context, List<Record> records) {
        this.context = context;
        this.records = records;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_record, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (records.get(position).isOpen()) {
            holder.bottomView.setVisibility(View.VISIBLE);
        } else {
            holder.bottomView.setVisibility(View.GONE);
        }

        holder.topView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ClickUtil.isFastClick()) {
                    if (records.get(position).isOpen()) {
                        newOpenView = null;
                        records.get(position).setOpen(false);
                        closeAnim(holder.bottomView, 0, RECORD_BOTTOM_HEIGHT);
                    } else {
                        oldOpenView = newOpenView;
                        newOpenView = holder.bottomView;
                        for (int i = 0; i < records.size(); i++) {
                            records.get(i).setOpen(false);
                        }
                        records.get(position).setOpen(true);
                        openAnim(holder.bottomView, 0, RECORD_BOTTOM_HEIGHT, position);
                    }
                }
            }
        });

        String startTime = DateUtils.getTime(records.get(position).getOrderListBean().getSTART_TIME());
        String endTime = DateUtils.getTime(records.get(position).getOrderListBean().getEND_TINE());
        String startDate = DateUtils.getDate(records.get(position).getOrderListBean().getSTART_TIME());

        String finalTime;
        if(startDate.equals(DateUtils.getOldDate(-1))){
            finalTime = "昨天" + " " + startTime;

        }else if(startDate.equals(DateUtils.getOldDate(0))){
            finalTime = "今天" + " " + startTime;

        }else{
            finalTime = startDate + " " + startTime;

        }
        holder.time.setText(finalTime);
        LogUtil.d(TAG, "onBindViewHolder: "+startDate+":"+DateUtils.getOldDate(0)+":"+DateUtils.getOldDate(-1));

        holder.parkName.setText(records.get(position).getOrderListBean().getNAME());

        String money = records.get(position).getOrderListBean().getMONEY();
        if(TextUtils.isEmpty(money)){
            holder.money.setText(StringUtils.getRString(context,R.string.money_free));
        }else if("0".equals(money)){
            holder.money.setText(StringUtils.getRString(context,R.string.money_free));
        }else{
            holder.money.setText("￥" + records.get(position).getOrderListBean().getMONEY());
        }



        holder.parkTime.setText("停车时长:" + records.get(position).getOrderListBean().getTIME());
        holder.carNumber.setText(records.get(position).getOrderListBean().getNUMBER());
        holder.period.setText(startTime + "~" + endTime);
//        holder.time.setText(startDate + " " + startTime);
        ImageViewUtils.showImage(context,records.get(position).getOrderListBean().getZB_PIC(),holder.img,R.drawable.icon_map_defult);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                sendSearchBroadcast(records.get(position).getOrderListBean());
            }
        });
    }



    private void sendSearchBroadcast(String parkId){
        Intent intent = new Intent();
        intent.putExtra(BROADCASTDATASEARCHPARK,parkId);
        intent.setAction(BROADCASTACTIONSEARCHPARK);
        context.sendBroadcast(intent);
        context.finish();
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public void openAnim(final View v, int start, int end, final int position) {
        float mDensity = context.getResources().getDisplayMetrics().density;
        int height = (int) (mDensity * end);
        ValueAnimator animator = ValueAnimator.ofInt(start, height);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                int value = (int) arg0.getAnimatedValue();

                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                layoutParams.height = value;
                v.setLayoutParams(layoutParams);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                v.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                handler.sendEmptyMessage(AppContants.CLOSE_RECORD_ITEM);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.setDuration(AppContants.RECORD_ITEM_ANIM_DURATION);
        animator.start();


    }

    public void closeAnim(final View v, int start, int end) {
        float mDensity = context.getResources().getDisplayMetrics().density;
        int height = (int) (mDensity * end);//伸展高度
        ValueAnimator animator = ValueAnimator.ofInt(height, start);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                int value = (int) arg0.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                layoutParams.height = value;
                v.setLayoutParams(layoutParams);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                v.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.setDuration(AppContants.RECORD_ITEM_ANIM_DURATION);
        animator.start();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout topView, bottomView;
        private TextView parkName, time, money, period, parkTime, carNumber;
        private ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);

            topView = (LinearLayout) itemView.findViewById(R.id.topView);
            bottomView = (LinearLayout) itemView.findViewById(R.id.bottomView);

            parkName = (TextView) itemView.findViewById(R.id.parkName);
            time = (TextView) itemView.findViewById(R.id.time);
            money = (TextView) itemView.findViewById(R.id.money);
            period = (TextView) itemView.findViewById(R.id.period);
            parkTime = (TextView) itemView.findViewById(R.id.parkTime);
            carNumber = (TextView) itemView.findViewById(R.id.carNumber);

            img = (ImageView) itemView.findViewById(R.id.img);
        }
    }
}
