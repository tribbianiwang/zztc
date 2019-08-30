package com.yascn.smartpark.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.yascn.smartpark.R;
import com.yascn.smartpark.base.BaseActivity;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.StringUtils;
import com.yascn.smartpark.utils.T;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yascn.smartpark.utils.StringUtils.shareAppShop;

/**
 * Created by YASCN on 2017/8/29.
 */

public class OnActivity extends BaseActivity {

    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.rl_to_app_market)
    RelativeLayout rlToAppMarket;
    @BindView(R.id.tv_version_code)
    TextView tv_version_code;
    private static final String TAG = "OnActivity";
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on);
        ButterKnife.bind(this);


        initTitle(getResources().getString(R.string.on));
        LogUtil.d(TAG, "onCreate: " + StringUtils.getAppVersionName(this));
        tv_version_code.setText(StringUtils.getAppVersionName(this));
    }

    @OnClick({R.id.riv_head, R.id.rl_to_app_market, R.id.tv_phone, R.id.tv_url})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_to_app_market:
                shareAppShop(OnActivity.this, getPackageName());
                break;
            case R.id.riv_head:
//                T.showShort(this,"弹吐司");

                break;

            case R.id.tv_url:
                Uri uri = Uri.parse("http://"+getString(R.string.official_webside));
                 intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;

            case R.id.tv_phone:
                intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+StringUtils.getRString(this,R.string.nation_hotline)));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                break;
        }


    }



}
