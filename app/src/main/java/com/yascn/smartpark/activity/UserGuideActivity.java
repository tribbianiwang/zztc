package com.yascn.smartpark.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yascn.smartpark.R;
import com.yascn.smartpark.base.BaseActivity;
import com.yascn.smartpark.utils.AppContants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserGuideActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.index_toolbar)
    Toolbar indexToolbar;
    @BindView(R.id.activity_introduction)
    LinearLayout activityIntroduction;
    @BindView(R.id.rl_bind_carlincense)
    RelativeLayout rlBindCarlincense;
    @BindView(R.id.rl_ahead_pay)
    RelativeLayout rlAheadPay;
    @BindView(R.id.rl_appoint_pay)
    RelativeLayout rlAppointPay;
    @BindView(R.id.rl_carlince_pay)
    RelativeLayout rlCarlincePay;
    @BindView(R.id.rl_scan_pay)
    RelativeLayout rlScanPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guide);
        ButterKnife.bind(this);
        initTitle(getString(R.string.string_use_guide));
    }


    @OnClick({R.id.rl_bind_carlincense, R.id.rl_ahead_pay, R.id.rl_appoint_pay, R.id.rl_carlince_pay, R.id.rl_scan_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_bind_carlincense:
                skipUserGuideDetail(R.layout.activity_user_guide_detail_bind_car);
                break;
            case R.id.rl_ahead_pay:
                skipUserGuideDetail(R.layout.activity_user_guide_detail_ahead_pay);
                break;
            case R.id.rl_appoint_pay:
                skipUserGuideDetail(R.layout.activity_user_guide_detail_appoint_pay);
                break;
            case R.id.rl_carlince_pay:
                skipUserGuideDetail(R.layout.activity_user_guide_detail_carlicense_pay);
                break;
            case R.id.rl_scan_pay:
                skipUserGuideDetail(R.layout.activity_user_guide_detail_scan_pay);
                break;
        }
    }

    private void skipUserGuideDetail(int layoutId){
        Intent intent = new Intent(this,UserGuideDetailActivity.class);
        intent.putExtra(AppContants.LAYOUTID,layoutId);
        startActivity(intent);
    }
}
