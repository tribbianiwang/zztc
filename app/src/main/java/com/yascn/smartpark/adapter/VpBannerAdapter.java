package com.yascn.smartpark.adapter;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.yascn.smartpark.R;
import com.yascn.smartpark.bean.HomeBean;
import com.yascn.smartpark.utils.ImageViewUtils;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.StringUtils;
import com.yascn.smartpark.view.StrokeTextView;

import java.util.List;

/**
 * Created by YASCN on 2018/5/5.
 */

public class VpBannerAdapter extends PagerAdapter {
    private Activity activity;

    private static final String TAG = "VpBannerAdapter%s";
    private HomeBean.ObjectBean object;


    public VpBannerAdapter(FragmentActivity activity, HomeBean.ObjectBean object) {
        this.object = object;
        this.activity = activity;


    }

    @Override
    public int getCount() {
        return object.getBanner_list().size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = View.inflate(activity, R.layout.layout_banner_item,null);
        ImageView iv = (ImageView) view.findViewById(R.id.iv_banner);
        LinearLayout llCarLicenseLimit = view.findViewById(R.id.ll_car_license_limit);
        StrokeTextView tvLimitCar = (StrokeTextView) view.findViewById(R.id.tv_limit_car);
        TextView tvCarLimitBottom = view.findViewById(R.id.tv_car_limit_bottom);
        TextView tvDayOfToday = view.findViewById(R.id.tv_day_of_today);
        //周几
        if(!TextUtils.isEmpty(object.getCar_limit().getDay_week())){
            tvDayOfToday.setText(object.getCar_limit().getDay_week());
        }

        if(!TextUtils.isEmpty(object.getCar_limit().getLimitNo())){
            tvLimitCar.setText(object.getCar_limit().getLimitNo());
        }

        if(!TextUtils.isEmpty(object.getCar_limit().getLimitCon())){
            tvCarLimitBottom.setText(object.getCar_limit().getLimitCon());
        }



        if(position!=0){
            llCarLicenseLimit.setVisibility(View.GONE);
        }else{
            llCarLicenseLimit.setVisibility(View.VISIBLE);
        }


        ImageViewUtils.showImage(activity, object.getBanner_list().get(position).getPIC(),iv,R.drawable.icon_plach_hoder_landscape,R.drawable.icon_plach_hoder_landscape);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}