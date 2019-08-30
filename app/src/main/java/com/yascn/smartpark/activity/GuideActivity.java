package com.yascn.smartpark.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yascn.smartpark.R;
import com.yascn.smartpark.adapter.GuidePagerAdapter;
import com.yascn.smartpark.base.BaseActivity;
import com.yascn.smartpark.utils.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

import static com.yascn.smartpark.utils.AppContants.ISFIRSTLOADAPP;

public class GuideActivity extends BaseActivity {


    @BindView(R.id.vp_guide)
    ViewPager vpGuide;
    @BindView(R.id.bt_start)
    Button btStart;
    @BindView(R.id.indicator_custom)
    CircleIndicator indicator_custom;
    @BindView(R.id.tv_skip)
    TextView tvSkip;
    @BindView(R.id.activity_guide)
    FrameLayout activityGuide;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        SPUtils.put(this, ISFIRSTLOADAPP, false);

        vpGuide.setAdapter(new GuidePagerAdapter(this));
        indicator_custom.setViewPager(vpGuide);

        vpGuide.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:

                    case 1:
                        btStart.setVisibility(View.GONE);
                        break;
                    case 2:
                        btStart.setVisibility(View.VISIBLE);
                        btStart.setText("开始体验");
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @OnClick(R.id.bt_start)
    public void onClick() {
        int currentItemIndex = vpGuide.getCurrentItem();
        switch (currentItemIndex) {
            case 0:
                vpGuide.setCurrentItem(1);
                break;

            case 1:
                vpGuide.setCurrentItem(2);
                break;


            case 2:

                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

                break;

        }


    }

    @OnClick(R.id.tv_skip)
    public void onViewClicked() {
        Intent intent = new Intent(GuideActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}
