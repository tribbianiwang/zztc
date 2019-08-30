package com.yascn.smartpark.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yascn.smartpark.R;
import com.yascn.smartpark.adapter.RvMapContentBottomAdapter;
import com.yascn.smartpark.adapter.XrvCommentAdapter;
import com.yascn.smartpark.bean.ParkComment;
import com.yascn.smartpark.contract.ParkCommentContract;
import com.yascn.smartpark.fragment.MapFragment;
import com.yascn.smartpark.model.ParkCommentModelImpl;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.T;
import com.yinglan.scrolllayout.ScrollLayout;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



/**
 * Created by MVPHelper on 2017/09/09
 */

public class ParkCommentPresenterImpl implements ParkCommentContract.Presenter {
    private MapFragment mapFragment;
    private  RecyclerView xrvComment;
    private LinearLayout llError,llNodata;
    private String parkId;
    private ParkCommentContract.Model model;
    //下拉刷新，上拉加载更多状态相关
    private List<ParkComment.ObjectBean> totalComments = new LinkedList<>();


    private int page = 1;//页数
    /**
     * 是否加载数据
     */
    private boolean isLoadingData = false;
    private RvMapContentBottomAdapter parkCommentAdapter;


    private boolean isFirstLoading = true;
    private SmartRefreshLayout swiperefreshlayout;
    private boolean isServerError = false;
    private View headerView;
    private ScrollLayout scrollDownLayout;


    public ParkCommentPresenterImpl(MapFragment mapFragment, SmartRefreshLayout swiperefreshlayout, RecyclerView xrvComment, LinearLayout llError, LinearLayout llNodata, String parkId, View mainRvContentHeader, ScrollLayout scrollDownLayout) {
        this.mapFragment = mapFragment;
        this.xrvComment = xrvComment;
        this.llError = llError;
        this.parkId = parkId;
        this.model = new ParkCommentModelImpl();
        this.swiperefreshlayout = swiperefreshlayout;
        this.llNodata = llNodata;
        this.headerView = mainRvContentHeader;
        this.scrollDownLayout = scrollDownLayout;

        this.llError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFirstLoading = true;
                parkCommentAdapter = null;
                totalComments.clear();
                page = 1;
                queryList();
            }
        });

        swiperefreshlayout.setEnableLoadmore(true);
        swiperefreshlayout.setEnableRefresh(false);
//        swiperefreshlayout.setRefreshEnabled(true);
//        swiperefreshlayout.setLoadMoreEnabled(true);

    }

    @Override
    public void serverError(String errorMsg) {

        if(!errorMsg.equals(AppContants.NOMOREDATA)){
            isServerError = true;
        }

        LogUtil.d(TAG, "serverError: "+errorMsg.equals(AppContants.NOMOREDATA)+isFirstLoading);
//        if(!errorMsg.equals(AppContants.NOMOREDATA)&&isFirstLoading){
            if(isFirstLoading){
            isShowComment(false);
        }else {
                mapFragment.serverError(errorMsg);
            }
        mapFragment.hideProgress();

        isLoadingData = false;
    }

    @Override
    public void getParCommentkData(String parkId, int pageNumber) {

        LogUtil.d(TAG, "getParCommentkData: "+parkId+pageNumber);

        if (mapFragment != null) {
            if(isFirstLoading){
                mapFragment.showProgress();
            }


            this.model.getParCommentkData(this,parkId, pageNumber);
        }
    }

    private static final String TAG = "ParkCommentPresenterImp";

    @Override
    public void setparkCommentData(ParkComment parkComment) {
        isServerError = false;
        LogUtil.d(TAG, "setparkCommentData: "+parkComment.getObject().get(0).getUSERNAME());

        mapFragment.hideProgress();

        final List<ParkComment.ObjectBean> object = parkComment.getObject();
        LogUtil.d(TAG, "setparkCommentData: objectsize"+object.size()+isFirstLoading);

        if (object.size() == 0) {

//            if (isFirstLoading) {
                isShowComment(false);

//            } else {
//                isShowComment(true);
//                T.showShort(commentActivity, "没有更多评论了");
//            }
        } else {
            isShowComment(true);
        }


        final List<ParkComment.ObjectBean> resultInfos = new ArrayList<>();
        for (int i = 0; i < object.size(); i++) {
            resultInfos.add(object.get(i));

        }

        if (parkCommentAdapter == null) {
            totalComments = resultInfos;
            parkCommentAdapter = new RvMapContentBottomAdapter( totalComments,mapFragment.getActivity());
            parkCommentAdapter.setHeaderView(headerView);

            xrvComment.setAdapter(parkCommentAdapter);
        } else {
            totalComments.addAll(resultInfos);
            parkCommentAdapter.notifyDataSetChanged();
        }
        isFirstLoading = false;

        isLoadingData = false;
        swiperefreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                totalComments.clear();
                parkCommentAdapter = null;
                page = 1;
                queryList();
                swiperefreshlayout.finishRefresh(2000);
            }
        });
        swiperefreshlayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (object.size() != 0) {
                    // 上拉的时候添加选项
                    page++;
                    queryList();
                } else {

                    T.showShort(mapFragment.getActivity(), "没有更多评论了");

                }

                refreshlayout.finishLoadmore(2000);
//                scrollDownLayout.scrollToOpen();
            }
        });



        mapFragment.setparkCommentData(parkComment);

    }

    @Override
    public void onDestory() {
        model.onDestory();
    }

    @Override
    public void reloadData() {
        isFirstLoading = true;
        totalComments.clear();

        parkCommentAdapter = null;

        page = 1;
        queryList();
    }

    @Override
    public void queryList() {
        LogUtil.d(TAG, "queryList: ");
        if (isLoadingData) {

            return;

        }

        this.getParCommentkData(parkId, page);
        isLoadingData = true;
    }


    public void isShowComment(boolean hasComment) {
//        LogUtil.d(TAG, "isShowComment: "+hasComment+":"+isServerError);
        llNodata.setVisibility(View.GONE);
        llError.setVisibility(View.GONE);
        xrvComment.setVisibility(View.VISIBLE);
//        if(!hasComment){
//            parkCommentAdapter = new RvMapContentBottomAdapter(totalComments,mapFragment.getActivity());
//            parkCommentAdapter.setHeaderView(headerView);
//            xrvComment.setAdapter(parkCommentAdapter);
//        }

        if(!hasComment){

            swiperefreshlayout.setEnableLoadmore(false);
            View errorPage = View.inflate(mapFragment.getActivity(), R.layout.layout_error_page,null);
            View emptyPage = View.inflate(mapFragment.getActivity(), R.layout.layout_nodata_page,null);
            emptyPage.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            errorPage.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            parkCommentAdapter = new RvMapContentBottomAdapter(totalComments,mapFragment.getActivity());
            parkCommentAdapter.setHeaderView(headerView);
            if(isServerError){
            errorPage.setVisibility(View.VISIBLE);
            parkCommentAdapter.setErrorView(errorPage);
            }else{
                emptyPage.setVisibility(View.VISIBLE);
                parkCommentAdapter.setEmptyView(emptyPage);
            }

            xrvComment.setAdapter(parkCommentAdapter);
//            llNodata.setVisibility(isServerError ? View.GONE : View.VISIBLE);
//            llError.setVisibility(isServerError ? View.VISIBLE : View.GONE);
//            xrvComment.setVisibility(View.GONE);
        }else{
            swiperefreshlayout.setEnableLoadmore(true);
        }
        LogUtil.d(TAG, "isShowComment: "+llNodata.getVisibility()+":"+llError.getVisibility()+":"+xrvComment.getVisibility());


//        if(scrollDownLayout.is)
            if(!isScrollDownLayoutShowing){
                mapFragment.showHideBotomView(true);
                mapFragment.showHideBotomView(true);
                isScrollDownLayoutShowing = true;
            }


    }


    boolean isScrollDownLayoutShowing = false;




}