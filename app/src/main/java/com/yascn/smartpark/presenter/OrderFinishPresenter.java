package com.yascn.smartpark.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;


import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yascn.smartpark.R;
import com.yascn.smartpark.activity.OrderDetailActivity;
import com.yascn.smartpark.adapter.RvOrderFinishedAdapter;
import com.yascn.smartpark.bean.OrderList;
import com.yascn.smartpark.bean.ParkComment;
import com.yascn.smartpark.contract.OrderFinishContract;
import com.yascn.smartpark.fragment.OrderFinishFragment;
import com.yascn.smartpark.model.OrderFinishModel;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.RvItemClickListener;
import com.yascn.smartpark.utils.T;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YASCN on 2DEFAULTPAGENUMBER18/4/25.
 */

public class OrderFinishPresenter implements OrderFinishContract.Presenter {
    private OrderFinishFragment orderFinishFragment;
    private SmartRefreshLayout refreshLayout;
    private LinearLayout llNodata,llError;
    private RecyclerView rvFinish;
    private String userID;
    private OrderFinishContract.Model model;
    private int pageNum = DEFAULTPAGENUMBER;
    private static final int DEFAULTPAGENUMBER=   1;
    
    private boolean isFirstLoading = true;
    private RvOrderFinishedAdapter rvOrderFinishedAdapter;
    List<OrderList.ObjectBean.OrderListBean> totalOrders;
    private boolean isLoadingData = false;

    public OrderFinishPresenter(OrderFinishFragment orderFinishFragment, SmartRefreshLayout refreshLayout, RecyclerView rvFinish, LinearLayout llNodata, String userID, LinearLayout llError) {
        this.orderFinishFragment = orderFinishFragment;
        this.refreshLayout = refreshLayout;
        this.llNodata = llNodata;
        this.llError = llError;
        this.rvFinish = rvFinish;
        this.userID = userID;
        this.model = new OrderFinishModel();
        this.llNodata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFirstLoading = true;
                rvOrderFinishedAdapter = null;
                totalOrders.clear();
                pageNum = DEFAULTPAGENUMBER;
                queryList();
            }
        });
        this.llError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFirstLoading = true;
                rvOrderFinishedAdapter = null;
                totalOrders.clear();
                pageNum = DEFAULTPAGENUMBER;
                queryList();
            }
        });
    }

    @Override
    public void serverError(String errorMsg) {

        if(isFirstLoading){
            isShowContent(llError);
        }else {
            orderFinishFragment.serverError(errorMsg);
        }

        isLoadingData = false;

        orderFinishFragment.hideProgress();
        orderFinishFragment.serverError(errorMsg);

    }


    public void isShowContent(View view) {
        LogUtil.d(TAG, "isShowContent: "+view.getId());
        llNodata.setVisibility(View.GONE);
        rvFinish.setVisibility(View.GONE);
        llError.setVisibility(View.GONE);
        view.setVisibility(View.VISIBLE);

//        llNodata.setVisibility(hasComment ? View.GONE : View.VISIBLE);
//        rvFinish.setVisibility(hasComment ? View.VISIBLE : View.GONE);
    }


    @Override
    public void getOrderList(String userId, int pageNumber) {
        if (orderFinishFragment != null) {
            if(isFirstLoading){
                orderFinishFragment.showProgress();
            }


            this.model.getOrderList(this,userId, pageNumber);
        }
    }

    private static final String TAG = "OrderFinishPresenter";
    @Override
    public void setOrderList(OrderList orderList) {
        orderFinishFragment.hideProgress();

        final List<OrderList.ObjectBean.OrderListBean> object = orderList.getObject().getOrder_list();
        LogUtil.d(TAG, "setparkCommentData: objectsize"+object.size()+isFirstLoading);
        if (object.size() == 0) {

            if (isFirstLoading) {
                isShowContent(llNodata);

            } else {
                isShowContent(rvFinish);
                T.showShort(orderFinishFragment.getContext(),orderFinishFragment.getString(R.string.no_more_data));
            }
        } else {
            isShowContent(rvFinish);
        }


        final List<OrderList.ObjectBean.OrderListBean> resultInfos = new ArrayList<>();
        for (int i = 0; i < object.size(); i++) {
            resultInfos.add(object.get(i));

        }

        if (rvOrderFinishedAdapter == null) {
            totalOrders = resultInfos;
            rvOrderFinishedAdapter = new RvOrderFinishedAdapter(orderFinishFragment.getActivity(), totalOrders);
            rvFinish.setAdapter(rvOrderFinishedAdapter);
        } else {
            totalOrders.addAll(resultInfos);
            rvOrderFinishedAdapter.notifyDataSetChanged();
        }

        LogUtil.d(TAG, "setOrderList: "+totalOrders.size());
        isFirstLoading = false;

        isLoadingData = false;
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                totalOrders.clear();
                rvOrderFinishedAdapter = null;
                pageNum = DEFAULTPAGENUMBER;
                queryList();
                refreshlayout.finishRefresh(2000);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (object.size() != 0) {
                    // 上拉的时候添加选项
                    pageNum++;
                    queryList();
                } else {

                    T.showShort(orderFinishFragment.getContext(),orderFinishFragment.getString(R.string.no_more_data));

                }

                refreshlayout.finishLoadmore(2000);
            }
        });

        rvOrderFinishedAdapter.setOnItemClickListener(new RvItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(totalOrders!=null){
                    if(totalOrders.size()>position){
                        Intent intent = new Intent(orderFinishFragment.getActivity(), OrderDetailActivity.class);

                        intent.putExtra(AppContants.ORDERDETAILBEAN,totalOrders.get(position));
                        orderFinishFragment.startActivity(intent);
                    }
                }

            }
        });


    }

    @Override
    public void onDestory() {
        model.onDestory();
    }



    @Override
    public void reloadData() {
        LogUtil.d(TAG, "reloadData: ");
        isFirstLoading = true;
        if(totalOrders!=null){
            if(totalOrders.size()!=0){
                totalOrders.clear();
            }
        }

        rvOrderFinishedAdapter = null;

        pageNum = 1;
        queryList();
    }

    @Override
    public void queryList() {
        if (isLoadingData) {

            return;


        }

        this.getOrderList(userID, pageNum);
        isLoadingData = true;
    }
}
