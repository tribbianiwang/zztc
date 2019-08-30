package com.yascn.smartpark.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yascn.smartpark.R;
import com.yascn.smartpark.activity.WalletDetailActivity;
import com.yascn.smartpark.adapter.RechargeAdapter;
import com.yascn.smartpark.base.BaseFragment;
import com.yascn.smartpark.bean.ReRecordList;
import com.yascn.smartpark.contract.WalletConsumptionDetailContract;
import com.yascn.smartpark.presenter.ConsumptionPresenter;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.DateUtils;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.SPUtils;
import com.yascn.smartpark.utils.StringUtils;
import com.yascn.smartpark.utils.T;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YASCN on 2017/8/30.
 */

public class ConsumptionFragment extends BaseFragment implements WalletConsumptionDetailContract.ConsumptionView, View.OnClickListener {


    private RecyclerView recyclerView;
    private RechargeAdapter rechargeAdapter;
    private List<ReRecordList.ObjectBean> reRecordLists;
    private List<String> monthList;
    private LinearLayout error, content, nodata,llNodata;
    private ImageView ivEmptyView;
    private int page = 1;
    private String userID;
    private SmartRefreshLayout refreshLayout;


    private WalletConsumptionDetailContract.ConsumptionPresenter consumptionPresenter;


    private boolean networkFailedBefore = false;
    private WalletDetailActivity walletDetailActivity;

    @Override
    public void netWordIsFailed() {
        super.netWordIsFailed();
        networkFailedBefore = true;
    }

    @Override
    public void netWorkIsSuccess() {
        super.netWorkIsSuccess();
        LogUtil.d(TAG, "netWorkIsSuccess: "+networkFailedBefore);
        if(networkFailedBefore){
            userID = (String) SPUtils.get(getContext(), AppContants.KEY_USERID, "");
            LogUtil.d(AppContants.TAG, "userID = " + userID);
            if(consumptionPresenter!=null){
                consumptionPresenter.getRecharge(page, userID, AppContants.ZHICHU);
            }


        }

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recharge, container, false);

        init(view);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        walletDetailActivity = (WalletDetailActivity) getActivity();
        userID = (String) SPUtils.get(getContext(), AppContants.KEY_USERID, "");

        error.setOnClickListener(this);

        nodata.setOnClickListener(this);

        consumptionPresenter = new ConsumptionPresenter(this);
        consumptionPresenter.getRecharge(page, userID, AppContants.ZHICHU);

        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadmore(true);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                isPullRefresh = true;
                page = 1;
                consumptionPresenter.reflesh(page, userID, AppContants.ZHICHU);

            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                isPullRefresh = true;
                page++;
                consumptionPresenter.loadmore(page, userID, AppContants.ZHICHU);

            }
        });

    }

    public void init(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        content = (LinearLayout) view.findViewById(R.id.content);
        error = (LinearLayout) view.findViewById(R.id.ll_error);
        nodata = (LinearLayout) view.findViewById(R.id.ll_nodata);
        refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout);
        llNodata = (LinearLayout) view.findViewById(R.id.ll_nodata);
        ivEmptyView = (ImageView) view.findViewById(R.id.iv_empty_view);


//        setEmptyViewBgAndImg(getActivity(),false,llNodata,ivEmptyView);
    }

    @Override
    public void setAdatper(ReRecordList reRecordList) {

        page = 1;
//        pdrAndPulView.setNomore(false);

        reRecordLists = new ArrayList<ReRecordList.ObjectBean>();
        monthList = new ArrayList<String>();

        reRecordLists.addAll(reRecordList.getObject());
        monthList = DateUtils.getMonthList(reRecordLists);

        rechargeAdapter = new RechargeAdapter((AppCompatActivity) getActivity(), reRecordLists, monthList,false);
        recyclerView.setAdapter(rechargeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        refreshComplete(true);
    }



    @Override
    public void refreshComplete(boolean success) {
        refreshLayout.finishRefresh();
//        pdrAndPulView.refreshComplete(success);
    }

    @Override
    public void loadmoreComplete(boolean success) {
        refreshLayout.finishLoadmore();
//        pdrAndPulView.loadmoreComplete(success);
        if (success) {

        } else {
            page--;
        }


    }

    @Override
    public void addData(ReRecordList reRecordList) {
        if (reRecordList.getObject().size() == 0) {
            nomore(true);
            return;
        }

        if (reRecordList.getObject().size() < 10) {
            nomore(false);
            List<String> months = DateUtils.getMonthList(reRecordList.getObject());
            monthList.addAll(months);
            reRecordLists.addAll(reRecordList.getObject());
            rechargeAdapter.notifyDataSetChanged();
            return;
        }

        if (reRecordList.getObject().size() == 10) {
            List<String> months = DateUtils.getMonthList(reRecordList.getObject());
            monthList.addAll(months);
            reRecordLists.addAll(reRecordList.getObject());
            rechargeAdapter.notifyDataSetChanged();
            return;
        }


    }

    @Override
    public void nomore(boolean isLastPage) {
//        pdrAndPulView.setNomore(true);
        refreshLayout.finishLoadmore();
        if(isLastPage){
            T.showShort(getActivity(), StringUtils.getRString(getActivity(),R.string.no_more_data));
        }
//
    }



    @Override
    public void showView(int type) {
        switch (type) {
            case AppContants.ERROR:
                error.setVisibility(View.VISIBLE);
                content.setVisibility(View.INVISIBLE);

                nodata.setVisibility(View.INVISIBLE);
                hideProgress();
                break;
            case AppContants.SUCCESS:
                error.setVisibility(View.INVISIBLE);
                content.setVisibility(View.VISIBLE);

                nodata.setVisibility(View.INVISIBLE);
                hideProgress();
                break;
            case AppContants.PROGRESS:
                error.setVisibility(View.INVISIBLE);
                content.setVisibility(View.INVISIBLE);

                showProgress();
                nodata.setVisibility(View.INVISIBLE);
                break;
            case AppContants.NODATA:

                error.setVisibility(View.INVISIBLE);
                content.setVisibility(View.INVISIBLE);
//
                hideProgress();
                nodata.setVisibility(View.VISIBLE);
                break;
        }
    }
    boolean isPullRefresh = false;
    private static final String TAG = "ConsumptionFragment%s";
    @Override
    public void showProgress() {
        if(isPullRefresh){
            return;
        }

        if(walletDetailActivity!=null){
            LogUtil.d(TAG, "showProgress: ");
            walletDetailActivity.showProgressDialog(StringUtils.getRString(walletDetailActivity,R.string.loadingProgress));
        }

    }

    @Override
    public void hideProgress() {
        if(isPullRefresh){
            refreshLayout.finishRefresh();
            return;
        }

        if(walletDetailActivity!=null){
            LogUtil.d(TAG, "hideProgress: ");
            walletDetailActivity.hidProgressDialog();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_error:

                consumptionPresenter.getRecharge(page, userID, AppContants.SHOURU);
                break;
            case R.id.ll_nodata:
                LogUtil.d(TAG,"nodatareget");
                consumptionPresenter.getRecharge(page, userID, AppContants.SHOURU);
                break;
        }
    }
}
