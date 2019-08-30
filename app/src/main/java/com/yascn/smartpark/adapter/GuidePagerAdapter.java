package com.yascn.smartpark.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yascn.smartpark.R;
import com.yascn.smartpark.activity.GuideActivity;

/**
 * Created by YASCN on 2017/4/20.
 */

public class GuidePagerAdapter extends PagerAdapter {
    private GuideActivity guideActivity;
    private int[] bgArray = new int[]{R.drawable.bg_guideview_first, R.drawable.bg_guideview_second, R.drawable.bg_guideview_third};


    public GuidePagerAdapter(GuideActivity guideActivity) {
     this.guideActivity = guideActivity;
    }

    @Override
    public int getCount() {
        return bgArray.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = View.inflate(guideActivity, R.layout.vp_guide_item,null);
        ImageView ivBg = (ImageView) view.findViewById(R.id.iv_bg);

        Glide.with(guideActivity).load(bgArray[position]).into(ivBg);




        if(ivBg.getParent()!=null){
            ((FrameLayout)ivBg.getParent()).removeView(ivBg);
        }

        container.addView(ivBg);

        return ivBg;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
