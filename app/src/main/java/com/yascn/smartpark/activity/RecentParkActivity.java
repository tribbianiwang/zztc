package com.yascn.smartpark.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.yascn.smartpark.R;
import com.yascn.smartpark.adapter.RvRecentParkAdapter;
import com.yascn.smartpark.base.BaseActivity;
import com.yascn.smartpark.bean.RecentParkBean;
import com.yascn.smartpark.contract.RecentParkContract;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.RvItemClickListener;
import com.yascn.smartpark.utils.SPUtils;
import com.yascn.smartpark.utils.StringUtils;
import com.yascn.smartpark.utils.T;
import com.yascn.smartpark.viewmodel.RecentParkViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yascn.smartpark.utils.AppContants.BROADCASTACTIONSEARCHPARK;
import static com.yascn.smartpark.utils.AppContants.BROADCASTDATASEARCHPARK;
import static com.yascn.smartpark.utils.AppContants.NOWlOCATIONlAT;
import static com.yascn.smartpark.utils.AppContants.NOWlOCATIONlON;

/**
 * Created by YASCN on 2017/9/6.
 */

public class RecentParkActivity extends BaseActivity implements RecentParkContract.View {


    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.index_toolbar)
    Toolbar indexToolbar;
    @BindView(R.id.rv_lately_park)
    RecyclerView rvLatelyPark;
    @BindView(R.id.ll_error)
    LinearLayout llError;
    @BindView(R.id.ll_nodata)
    LinearLayout llNodata;
    @BindView(R.id.iv_empty_view)
    ImageView ivEmptyView;
    private double nowlocationLat;
    private double nowlocationLon;

    private String userid;
    private RecentParkViewModel recentParkViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_park);
        ButterKnife.bind(this);
        nowlocationLat = getIntent().getDoubleExtra(NOWlOCATIONlAT, 0);
        nowlocationLon = getIntent().getDoubleExtra(NOWlOCATIONlON, 0);

        initTitle(getString(R.string.recent_park));

        //1
        recentParkViewModel = ViewModelProviders.of(this).get(RecentParkViewModel.class);

        //2
        getLifecycle().addObserver(recentParkViewModel);

        //3
        Observer<RecentParkBean> recentParkBeanObserver = new Observer<RecentParkBean>() {
            @Override
            public void onChanged(@Nullable RecentParkBean recentParkBean) {

                setRecentPark(recentParkBean);
            }
        };

        //4

        recentParkViewModel.getMutableLiveDataEntry().observe(this,recentParkBeanObserver);
        recentParkViewModel.getQueryStatus().observe(this,statusObserver);





        initRecyclerView();
        userid = (String) SPUtils.get(this, AppContants.KEY_USERID, "");
        if (!TextUtils.isEmpty(userid)) {
            if(recentParkViewModel!=null){
                recentParkViewModel.getRecentPark(userid);
            }

        }
        StringUtils.setEmptyViewBgAndImg(this,true,llNodata,ivEmptyView);

    }


    private void initRecyclerView() {

        rvLatelyPark.setLayoutManager(new LinearLayoutManager(this));
        rvLatelyPark.setItemAnimator(new DefaultItemAnimator());
//        rvLatelyPark.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.HORIZONTAL));


    }

    @Override
    public void serverError(String errorMsg) {
        isServerError = true;
        T.showShort(this, errorMsg);
        isShowData(false);

    }

    @Override
    public void setRecentPark(RecentParkBean recentParkBean) {
        isServerError = false;
        final List<RecentParkBean.ObjectBean> recentParkBeanObjects = recentParkBean.getObject();
        RvRecentParkAdapter rvRecentParkAdapter = new RvRecentParkAdapter(this, nowlocationLat, nowlocationLon, recentParkBeanObjects);
        rvLatelyPark.setAdapter(rvRecentParkAdapter);
        if (recentParkBean.getObject().size() == 0) {
            isShowData(false);
        } else {
            isShowData(true);
        }

        rvRecentParkAdapter.setOnItemClickListener(new RvItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.putExtra(BROADCASTDATASEARCHPARK, recentParkBeanObjects.get(position).getPARKING_ID());
                intent.setAction(BROADCASTACTIONSEARCHPARK);
                sendBroadcast(intent);
                finish();
            }
        });
    }

    @Override
    public void showProgress() {
        LogUtil.d(TAG, "showProgress: ");
        showProgressDialog(StringUtils.getRString(this, R.string.loadingProgress));
    }

    @Override
    public void hideProgress() {
        LogUtil.d(TAG, "hideProgress: ");
        hidProgressDialog();

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @OnClick({R.id.ll_error, R.id.ll_nodata})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_error:
                if (!TextUtils.isEmpty(userid) && recentParkViewModel != null) {
                    if(recentParkViewModel!=null){
                        recentParkViewModel.getRecentPark(userid);
                    }

                }

            case R.id.ll_nodata:
                if (!TextUtils.isEmpty(userid) && recentParkViewModel != null) {
                    if(recentParkViewModel!=null){
                        recentParkViewModel.getRecentPark(userid);
                    }
                }
                break;
        }
    }

    private boolean isNetFailed = false;

    @Override
    public void netWordIsFailed() {
        super.netWordIsFailed();
        isNetFailed = true;
    }

    @Override
    public void netWorkIsSuccess() {
        super.netWorkIsSuccess();

        if (recentParkViewModel != null && isNetFailed && !TextUtils.isEmpty(userid)) {
            recentParkViewModel.getRecentPark(userid);


        }

    }


    private boolean isServerError = false;

    private static final String TAG = "RecentParkActivity";

    public void isShowData(boolean hasData) {
        LogUtil.d(TAG, "isShowComment: " + hasData + ":" + isServerError);
        if (!hasData) {
            llNodata.setVisibility(isServerError ? View.GONE : View.VISIBLE);
            llError.setVisibility(isServerError ? View.VISIBLE : View.GONE);
            rvLatelyPark.setVisibility(View.GONE);
        } else {
            rvLatelyPark.setVisibility(View.VISIBLE);
            llNodata.setVisibility(View.GONE);
            llError.setVisibility(View.GONE);
        }

        LogUtil.d(TAG, "isShowComment: " + llNodata.getVisibility() + ":" + llError.getVisibility() + ":" + rvLatelyPark.getVisibility());


    }
}
