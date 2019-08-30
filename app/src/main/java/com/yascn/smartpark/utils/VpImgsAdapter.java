package com.yascn.smartpark.utils;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;



import com.yascn.smartpark.R;
import com.yascn.smartpark.activity.BigImgsActivity;
import com.yascn.smartpark.bean.ParkComment;

import java.util.List;


/**
 * Created by YASCN on 2017/1/7.
 */

public class VpImgsAdapter extends PagerAdapter {
    private Activity bigImgsActivity;
    private List<ParkComment.ObjectBean.Img> commentImgs;

    public VpImgsAdapter(BigImgsActivity bigImgsActivity, List<ParkComment.ObjectBean.Img> commentImgs) {
        this.bigImgsActivity = bigImgsActivity;
        this.commentImgs = commentImgs;
    }

    @Override
    public int getCount() {
        return commentImgs.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = View.inflate(bigImgsActivity, R.layout.vp_imgs_item,null);
        ScaleImageView iv = (ScaleImageView) view.findViewById(R.id.iv);
        LogUtil.d("bigimg %s",commentImgs.get(position).getIMG());
        ImageViewUtils.showImage(bigImgsActivity,commentImgs.get(position).getIMG(),iv,R.drawable.icon_place_holder_large,R.drawable.icon_laod_img_error);
        if(iv.getParent()!=null){
            ((LinearLayout)iv.getParent()).removeView(iv);
        }
        container.addView(iv, 0);

        return iv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
