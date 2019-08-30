package com.yascn.smartpark.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.yascn.smartpark.R;
import com.yascn.smartpark.base.BaseActivity;
import com.yascn.smartpark.utils.DensityUtils;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.NotchScreenUtils;
import com.yascn.smartpark.utils.StringUtils;
import com.yascn.smartpark.utils.ViewUtils;
import com.yascn.smartpark.view.CameraSurfaceView;
import com.yascn.smartpark.view.CameraTopRectView;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yascn.smartpark.utils.AppContants.BROADCASTLICENSE;

public class CardCameraActivity extends BaseActivity {

    @BindView(R.id.cameraSurfaceView)
    CameraSurfaceView cameraSurfaceView;
    @BindView(R.id.rectOnCamera)
    CameraTopRectView rectOnCamera;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.index_toolbar)
    Toolbar indexToolbar;
    @BindView(R.id.iv_take_pic)
    ImageView ivTakePic;
    @BindView(R.id.fl_root)
    FrameLayout flRoot;
    @BindView(R.id.rl_img_toolbar)
    RelativeLayout rlImgToolbar;


    private static final String TAG = "CardCameraActivity";

    public BroadcastReceiver cardCameraReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(BROADCASTLICENSE)) {

                hidProgressDialog();
                CardCameraActivity.this.finish();

            }
        }
    };
    private ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_camera);


        ButterKnife.bind(this);
        initTitle(getString(R.string.driving_permit_img));

        initBroadcast();
        setHeadMargin();
    }


    private void setHeadMargin() {

        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();   //所有子类都将继承这些相同的属性
        LogUtil.d(TAG, "onCreate: " + NotchScreenUtils.getIntchHeight(this));

//        toolbar.setPadding(0, (int) DensityUtils.dp2px(mainActivity,NotchScreenUtils.getIntchHeight(mainActivity)),0,0);
        ViewUtils.setMargins(indexToolbar, 0, (int) DensityUtils.dp2px(this, NotchScreenUtils.getIntchHeight(this)), 0, 0);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(cardCameraReceiver);
    }

    private void initBroadcast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BROADCASTLICENSE);

        registerReceiver(cardCameraReceiver, intentFilter);
    }

    @OnClick(R.id.iv_take_pic)
    public void onViewClicked() {
        showProgressDialog(StringUtils.getRString(this,R.string.cameraProgress));
        cameraSurfaceView.takePicture();

    }



}
