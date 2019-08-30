package com.yascn.smartpark.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yascn.smartpark.R;
import com.yascn.smartpark.base.BaseActivity;
import com.yascn.smartpark.bean.ParkComment;
import com.yascn.smartpark.contract.ParkCommentContract;
import com.yascn.smartpark.presenter.ParkCommentPresenterImpl;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.RecyclerViewDivider;
import com.yascn.smartpark.utils.StringUtils;
import com.yascn.smartpark.utils.T;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yascn.smartpark.utils.AppContants.PARKFREENUM;
import static com.yascn.smartpark.utils.AppContants.PARKFREENUMREDLINE;
import static com.yascn.smartpark.utils.StringUtils.setEmptyViewBgAndImg;

public class CommentActivity extends BaseActivity implements ParkCommentContract.View {


    @BindView(R.id.bt_todetail)
    Button btTodetail;
    @BindView(R.id.xrv_comment)
    RecyclerView xrvComment;
    @BindView(R.id.ll_error)
    LinearLayout llError;


    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.ll_nodata)
    LinearLayout llNodata;
    @BindView(R.id.iv_empty_view)
    ImageView ivEmptyView;
    private String parkId;
    private ParkCommentContract.Presenter presenter;
    private int parkFreeNUm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        parkId = getIntent().getStringExtra(AppContants.PARKID);


        parkFreeNUm = getIntent().getIntExtra(PARKFREENUM, 0);

        ButterKnife.bind(this);

        if (parkFreeNUm < PARKFREENUMREDLINE) {
            btTodetail.setBackgroundResource(R.drawable.selector_red_deep);
        } else {
            btTodetail.setBackgroundResource(R.drawable.selector_blue_deep);
        }

        initRv();
//        presenter = new ParkCommentPresenterImpl(this, refreshLayout, xrvComment, llError, llNodata, parkId, mainRvContentHeader);

        presenter.queryList();


        setEmptyViewBgAndImg(this,false,llNodata,ivEmptyView);

    }




    private void initRv() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrvComment.setLayoutManager(layoutManager);
        xrvComment.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.HORIZONTAL));


    }


    public void returntoParkDetail() {

        finish();
        overridePendingTransition(R.anim.top_in, R.anim.bottom_out);

    }

    @Override
    public void serverError(String errorMsg) {

        T.showShort(this, errorMsg);
    }

    @Override
    public void setparkCommentData(ParkComment parkComment) {

    }

    private static final String TAG = "CommentActivity";

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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            returntoParkDetail();

        }

        return false;


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

        if (presenter != null && isNetFailed) {
            presenter.reloadData();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestory();
        }
    }

    @OnClick({R.id.bt_todetail, R.id.ll_error, R.id.ll_nodata})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_todetail:
                returntoParkDetail();
                break;
            case R.id.ll_error:
                if (presenter != null) {
                    presenter.reloadData();
                }

                break;
            case R.id.ll_nodata:
                LogUtil.d(TAG, "onViewClicked: nodata");
                if (presenter != null) {
                    presenter.reloadData();
                }

                break;
        }
    }
}
