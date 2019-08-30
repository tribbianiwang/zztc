package com.yascn.smartpark.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yascn.smartpark.R;
import com.yascn.smartpark.activity.NewWalletActivity;
import com.yascn.smartpark.adapter.RvWalletCouponingAdapter;
import com.yascn.smartpark.base.BaseFragment;
import com.yascn.smartpark.bean.CouponingBean;
import com.yascn.smartpark.contract.CouponingContract;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.SPUtils;
import com.yascn.smartpark.utils.T;
import com.yascn.smartpark.viewmodel.CouponViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSFIILED;
import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSLOADING;
import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSSUCCESS;

/**
 * A simple {@link Fragment} subclass.
 */
public class WalletCouponingFragment extends BaseFragment implements CouponingContract.View {


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
    private NewWalletActivity newWalletActivity;

    private String userId;
    private CouponViewModel couponViewModel;

    public WalletCouponingFragment() {
        // Required empty public constructor
    }

    private static final String TAG = "WalletCouponingFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wallet_couponing, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        newWalletActivity = (NewWalletActivity) getActivity();
        userId = (String) SPUtils.get(newWalletActivity, AppContants.KEY_USERID, "");


        couponViewModel = ViewModelProviders.of(this).get(CouponViewModel.class);
        newWalletActivity.getLifecycle().addObserver(couponViewModel);
        Observer<CouponingBean> couponingBeanObserver = new Observer<CouponingBean>() {
            @Override
            public void onChanged(@Nullable CouponingBean couponingBean) {
                setCouponing(couponingBean);
            }
        };

        Observer<String> statusObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.d(TAG, "onChanged: status"+s);

                //设置状态

                switch(s){
                    case QUERYSTATUSLOADING:
                        Log.d(TAG, "status: 加载中");
                        showProgress();

                        break;
                    case QUERYSTATUSFIILED:
                        Log.d(TAG, "status: 加载失败");
                        hideProgress();
                        serverError(AppContants.SERVERERROR);

                        break;

                    case QUERYSTATUSSUCCESS:
                        Log.d(TAG,"status:加载成功");
                        hideProgress();


                        break;
                }

            }
        };

        couponViewModel.getMutableLiveDataEntry().observe(this,couponingBeanObserver);
        couponViewModel.getQueryStatus().observe(this,statusObserver);
        

        rvCouponing.setLayoutManager(new LinearLayoutManager(newWalletActivity));


//        rvCouponing.setAdapter(new RvWalletCouponingAdapter(newWalletActivity));

        couponViewModel.getCouponingDatas(userId);
        LogUtil.d(TAG, "onActivityCreated: " + userId);

        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                isPullRefresh = true;
                couponViewModel.getCouponingDatas(userId);
            }
        });

        refreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.white, R.color.design_text_gray);//全局设置主题颜色
                return new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Scale);//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });

    }



    private boolean isNetFialed = false;

    @Override
    public void netWordIsFailed() {
        super.netWordIsFailed();
        isNetFialed = true;
    }

    boolean isPullRefresh = false;

    @Override
    public void netWorkIsSuccess() {
        super.netWorkIsSuccess();

        if (!TextUtils.isEmpty(userId) && isNetFialed) {
            couponViewModel.getCouponingDatas(userId);
            isNetFialed = false;
        }

    }

    @Override
    public void showProgress() {
        if (isPullRefresh) {
            return;
        }
        super.showProgress();
    }

    @Override
    public void hideProgress() {

        if (isPullRefresh) {
            refreshLayout.finishRefresh();
            return;
        }

        super.hideProgress();
    }

    public void setVisibility(View view) {
        llNodata.setVisibility(View.GONE);
        llError.setVisibility(View.GONE);
        rvCouponing.setVisibility(View.GONE);
        view.setVisibility(View.VISIBLE);

    }

    @Override
    public void serverError(String errorMsg) {
        setVisibility(llError);
        T.showShort(newWalletActivity, errorMsg);
    }

    @Override
    public void setCouponing(CouponingBean couponingBean) {
        if (couponingBean.getObject().size() == 0) {
            setVisibility(llNodata);
        } else {
            setVisibility(rvCouponing);
        }

//        LogUtil.d(TAG, "setCouponing: "+couponingBean.getObject().size());
        RvWalletCouponingAdapter rvWalletCouponingAdapter = new RvWalletCouponingAdapter(newWalletActivity, couponingBean.getObject());
        rvCouponing.setAdapter(rvWalletCouponingAdapter);

    }

    @Override
    public Context getContext() {
        return getActivity();

    }


    @OnClick({R.id.ll_nodata, R.id.ll_error})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_nodata:
                if(couponViewModel!=null){
                    couponViewModel.getCouponingDatas(userId);
                }

                break;
            case R.id.ll_error:
                if(couponViewModel!=null){
                    couponViewModel.getCouponingDatas(userId);
                }
                break;
        }
    }
}
