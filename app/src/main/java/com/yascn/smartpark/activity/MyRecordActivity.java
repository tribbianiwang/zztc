package com.yascn.smartpark.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yascn.smartpark.R;
import com.yascn.smartpark.adapter.RecordAdapter;
import com.yascn.smartpark.base.BaseActivity;
import com.yascn.smartpark.bean.OrderList;
import com.yascn.smartpark.bean.Record;
import com.yascn.smartpark.contract.MyRecordContract;
import com.yascn.smartpark.presenter.MyRecordPresenter;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.SPUtils;
import com.yascn.smartpark.utils.StringUtils;
import com.yascn.smartpark.utils.T;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by YASCN on 2017/8/29.
 */

public class MyRecordActivity extends BaseActivity implements MyRecordContract.MyRecordView {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    @BindView(R.id.content)
    LinearLayout content;


    @BindView(R.id.ll_nodata)
    LinearLayout nodata;

    @BindView(R.id.ll_error)
    LinearLayout error;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.iv_empty_view)
    ImageView ivEmptyView;

    private RecordAdapter recordAdapter;
    private List<Record> records;

    private int page = 1;
    private String userID;

    private MyRecordContract.MyRecordPresenter myRecordPresenter;
    private boolean isNetFailed = false;

    @Override
    public void netWordIsFailed() {
        super.netWordIsFailed();
        isNetFailed = true;
    }

    @Override
    public void netWorkIsSuccess() {
        super.netWorkIsSuccess();

        if (myRecordPresenter != null && isNetFailed && !TextUtils.isEmpty(userID)) {
            myRecordPresenter.getData(userID, page + "");

        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_record);

        ButterKnife.bind(this);

        initTitle(getResources().getString(R.string.record));


        userID = (String) SPUtils.get(this, AppContants.KEY_USERID, "");


        refreshLayout.setEnableLoadmore(true);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                myRecordPresenter.addData(userID, page + "");
            }
        });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                myRecordPresenter.reflesh(userID, page + "");
            }
        });


        myRecordPresenter = new MyRecordPresenter(this);
        myRecordPresenter.getData(userID, page + "");
//        StringUtils.setEmptyViewBgAndImg(this,true,nodata,ivEmptyView);

    }

    private static final String TAG = "MyRecordActivity";
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
    public void showView(int type) {
        switch (type) {
            case AppContants.ERROR:

                setProgressVisibility(error);
                hideProgress();

                break;
            case AppContants.SUCCESS:
                hideProgress();
                setProgressVisibility(content);

                break;
            case AppContants.PROGRESS:
                showProgress();


                break;
            case AppContants.NODATA:
                hideProgress();
                setProgressVisibility(nodata);


                break;
        }
    }

    public void setProgressVisibility(View view) {
        error.setVisibility(View.GONE);
        content.setVisibility(View.GONE);
        nodata.setVisibility(View.GONE);
        view.setVisibility(View.VISIBLE);
    }


    @Override
    public void setAdatper(OrderList orderList) {
        page = 1;
//        pdrAndPulView.setNomore(false);

        records = new ArrayList<Record>();

        for (int i = 0; i < orderList.getObject().getOrder_list().size(); i++) {
            Record record = new Record();
            record.setOpen(false);
            record.setOrderListBean(orderList.getObject().getOrder_list().get(i));
            records.add(record);
        }

        recordAdapter = new RecordAdapter(this, records);
        recyclerView.setAdapter(recordAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        refreshComplete(true);
    }


    @Override
    public void refreshComplete(boolean success) {
//        pdrAndPulView.refreshComplete(success);
        refreshLayout.finishRefresh();
    }

    @Override
    public void loadmoreComplete(boolean success) {
        refreshLayout.finishLoadmore();
        if (success) {

        } else {
            page--;
        }
    }

    @Override
    public void addData(OrderList orderList) {
        if (orderList.getObject().getOrder_list().size() == 0) {
            nomore(true);
            return;
        }

        if (orderList.getObject().getOrder_list().size() < 10) {
            nomore(false);
            for (int i = 0; i < orderList.getObject().getOrder_list().size(); i++) {
                Record record = new Record();
                record.setOpen(false);
                record.setOrderListBean(orderList.getObject().getOrder_list().get(i));
                records.add(record);
            }

            recordAdapter.notifyDataSetChanged();
            return;
        }

        if (orderList.getObject().getOrder_list().size() == 10) {
            for (int i = 0; i < orderList.getObject().getOrder_list().size(); i++) {
                Record record = new Record();
                record.setOpen(false);
                record.setOrderListBean(orderList.getObject().getOrder_list().get(i));
                records.add(record);
            }
            recordAdapter.notifyDataSetChanged();
            return;
        }
    }

    @Override
    public void nomore(boolean isLastPage) {
        refreshLayout.finishLoadmore();
        if (isLastPage) {
            T.showShort(this, StringUtils.getRString(this, R.string.no_more_data));
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        myRecordPresenter.onDestroy();
    }

    @OnClick({R.id.ll_error, R.id.ll_nodata})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_error:
                myRecordPresenter.getData(userID, page + "");
                break;
            case R.id.ll_nodata:
                myRecordPresenter.getData(userID, page + "");
                break;
        }
    }
}
