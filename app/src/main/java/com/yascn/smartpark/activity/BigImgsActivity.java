package com.yascn.smartpark.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.yascn.smartpark.R;
import com.yascn.smartpark.base.BaseActivity;
import com.yascn.smartpark.bean.ParkComment;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.DownLoadImageService;
import com.yascn.smartpark.utils.ImageDownLoadCallBack;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.T;
import com.yascn.smartpark.utils.VpImgsAdapter;
import com.yascn.smartpark.view.ViewPagerFixed;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class BigImgsActivity extends BaseActivity {

    @BindView(R.id.vp_imgs)
    ViewPagerFixed vpImgs;
    @BindView(R.id.activity_big_imgs)
    FrameLayout activityBigImgs;
    @BindView(R.id.tv_page_index)
    TextView tvPageIndex;
    @BindView(R.id.index_toolbar)
    Toolbar indexToolbar;
    @BindView(R.id.bt_save)
    TextView btSave;


    private ParkComment.ObjectBean parkObjectBean;
    private int imgIndex;
    private List<ParkComment.ObjectBean.Img> imgs;

    private static final String TAG = "BigImgsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        // 隐藏标题栏
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_big_imgs);
//        intent.putExtra(AppContants.COMMMENTIMGS,parkObjectBean);
//        intent.putExtra(AppContants.COMMENTIMGINDEX,position);
        parkObjectBean = (ParkComment.ObjectBean) getIntent().getSerializableExtra(AppContants.COMMMENTIMGS);
        imgIndex = getIntent().getIntExtra(AppContants.COMMENTIMGINDEX, 0);
        imgs = parkObjectBean.getImgs();
        LogUtil.d(TAG, "onCreate:img %s" + imgIndex + parkObjectBean.getImgs().get(0));


        ButterKnife.bind(this);

        initToolbar();
        vpImgs.setAdapter(new VpImgsAdapter(this, imgs));

        vpImgs.setCurrentItem(imgIndex);
        tvPageIndex.setText(imgIndex + 1 + "/" + parkObjectBean.getImgs().size());

        vpImgs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;
                tvPageIndex.setText(position + 1 + "/" + parkObjectBean.getImgs().size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initToolbar() {

        indexToolbar.setNavigationIcon(R.drawable.icon_back);

        indexToolbar.setTitleTextColor(Color.WHITE);
        indexToolbar.setTitle("");
        indexToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    int mCurrentPosition;

    @OnClick(R.id.bt_save)
    public void onClick() {
        onDownLoad(parkObjectBean.getImgs().get(mCurrentPosition).getIMG());

    }


    /**
     * 启动图片下载线程
     */
    private void onDownLoad(String url) {
        LogUtil.d("downloadimg", "进入");
        DownLoadImageService service = new DownLoadImageService(getApplicationContext(),
                url,
                new ImageDownLoadCallBack() {

                    @Override
                    public void onDownLoadSuccess(File file) {
                        showToast("图片保存成功");

                    }

                    @Override
                    public void onDownLoadSuccess(Bitmap bitmap) {

                        showToast("图片保存成功");
                    }

                    @Override
                    public void onDownLoadFailed() {
                        showToast("图片保存失败");
                    }
                });
        //启动图片下载线程
        new Thread(service).start();
    }


    public void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                T.showShort(BigImgsActivity.this, msg);
            }
        });
    }
}
