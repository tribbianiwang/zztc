package com.yascn.smartpark.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yascn.smartpark.R;
import com.yascn.smartpark.adapter.RvWalletCouponingAdapter;
import com.yascn.smartpark.base.BaseActivity;
import com.yascn.smartpark.bean.CouponingBean;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.RvItemClickListener;
import com.yascn.smartpark.utils.SPUtils;
import com.yascn.smartpark.viewmodel.CouponViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yascn.smartpark.utils.AppContants.COUPONINGBEAN;
import static com.yascn.smartpark.utils.AppContants.COUPONINGBROADCAST;
import static com.yascn.smartpark.utils.AppContants.ISNEEDRETURNCOUPON;
import static com.yascn.smartpark.utils.AppContants.VIEWTYPECONTENT;
import static com.yascn.smartpark.utils.AppContants.VIEWTYPENODATA;

public class PickCouponingActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.index_toolbar)
    Toolbar indexToolbar;
    @BindView(R.id.activity_introduction)
    LinearLayout activityIntroduction;

    @BindView(R.id.rv_couponing)
    RecyclerView rvCouponing;
    @BindView(R.id.iv_empty_view)
    ImageView ivEmptyView;
    @BindView(R.id.ll_nodata)
    LinearLayout llNodata;
    @BindView(R.id.ll_error)
    LinearLayout llError;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;


    private String userId;
    private static final String TAG = "PickCouponingActivity";
    private boolean isNeedReturnCoupon;
    private CouponViewModel couponViewModel;
    private RvWalletCouponingAdapter rvWalletCouponingAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_couponing);
        ButterKnife.bind(this);


        userId = (String) SPUtils.get(this, AppContants.KEY_USERID, "");

        initChildView(refreshLayout,refreshLayout,llError,llNodata);

        initTitle(getString(R.string.string_pick_coupoing));
        isNeedReturnCoupon = getIntent().getBooleanExtra(ISNEEDRETURNCOUPON, true);



        //1.获取viewmodel
        couponViewModel = ViewModelProviders.of(this).get(CouponViewModel.class);

        //2.给声明周期添加观察者
        getLifecycle().addObserver(couponViewModel);

        //3.创建bean的观察者
        Observer<CouponingBean> couponingBeanObserver = new Observer<CouponingBean>() {
            @Override
            public void onChanged(@Nullable CouponingBean couponingBean) {
                setCouponing(couponingBean);
            }
        };

        //4.viewmodel绑定bean观察者
        couponViewModel.getMutableLiveDataEntry().observe(this, couponingBeanObserver);
        couponViewModel.getQueryStatus().observe(this, statusObserver);

        rvCouponing.setLayoutManager(new LinearLayoutManager(this));
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                isPullRefresh = true;
                refreshData();
            }
        });

        refreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.white, R.color.design_text_gray);//全局设置主题颜色
                return new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Scale);//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });

        refreshData();
    }


    @Override
    public void refreshData() {

        if(couponViewModel!=null&&userId!=null){

            couponViewModel.getCouponingDatas(userId);
        }
    }

    public void setCouponing(final CouponingBean couponingBean) {
        if (couponingBean.getObject().size() == 0) {
            showTypeView(VIEWTYPENODATA);
        } else {
            showTypeView(VIEWTYPECONTENT);
        }

            rvWalletCouponingAdapter = new RvWalletCouponingAdapter(this, couponingBean.getObject());
            rvCouponing.setAdapter(rvWalletCouponingAdapter);




        rvWalletCouponingAdapter.setOnItemClickListener(new RvItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (isNeedReturnCoupon) {
                    Intent intent = new Intent();
                    intent.setAction(COUPONINGBROADCAST);
                    intent.putExtra(COUPONINGBEAN, couponingBean.getObject().get(position));
                    sendBroadcast(intent);
                    finish();
                }


            }
        });

    }






}
