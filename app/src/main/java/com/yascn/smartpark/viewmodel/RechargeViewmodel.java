package com.yascn.smartpark.viewmodel;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;

import com.yascn.smartpark.bean.ReRecordList;
import com.yascn.smartpark.model.WalletDetailRechargeModel;
import com.yascn.smartpark.utils.AppContants;

public class RechargeViewmodel extends ViewModel implements WalletDetailRechargeModel.DataResultListener, LifecycleObserver {
    private MutableLiveData<ReRecordList> finishRechargeLiveData;
    private MutableLiveData<ReRecordList> loadMoreRechargeLiveData;
    private MutableLiveData<ReRecordList> refreshRechargeLiveData;
    private MutableLiveData<String> queryStatusMutableLiveData;
    private WalletDetailRechargeModel walletDetailRechargeModel;

    public MutableLiveData<ReRecordList> getLoadMoreRechargeLiveData() {
        if(loadMoreRechargeLiveData==null){
            loadMoreRechargeLiveData = new MutableLiveData<>();
        }

        return loadMoreRechargeLiveData;
    }

    public MutableLiveData<ReRecordList> getRefreshRechargeLiveData() {

        if(refreshRechargeLiveData==null){
            refreshRechargeLiveData = new MutableLiveData<>();
        }

        return refreshRechargeLiveData;
    }





    public MutableLiveData<ReRecordList> getFinishRechargeLiveData() {
        if(finishRechargeLiveData ==null){
            finishRechargeLiveData = new MutableLiveData<>();
        }
        return finishRechargeLiveData;
    }

    public MutableLiveData<String> getQueryStatusMutableLiveData() {
        if(queryStatusMutableLiveData==null){
            queryStatusMutableLiveData = new MutableLiveData<>();
        }

        return queryStatusMutableLiveData;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestory(){
        if(walletDetailRechargeModel!=null){
            walletDetailRechargeModel.onDestroy();
        }
    }


    public void loadmore(int pageNum, String userID, String cate) {
        if(walletDetailRechargeModel==null){
            walletDetailRechargeModel = new WalletDetailRechargeModel(this);
        }
        walletDetailRechargeModel.loadmore(pageNum, userID, cate);
    }

    public void reflesh(int pageNum, String userID, String cate) {
        if(walletDetailRechargeModel==null){
            walletDetailRechargeModel = new WalletDetailRechargeModel(this);

        }
        walletDetailRechargeModel.reflesh(pageNum, userID, cate);
    }


    public void getRecharge(int pageNum, String userID, String cate) {
        if(walletDetailRechargeModel==null){
            walletDetailRechargeModel = new WalletDetailRechargeModel(this);

        }
        walletDetailRechargeModel.getRecharge(pageNum, userID, cate);
    }



    @Override
    public void onRefleshFinish(ReRecordList reRecordList) {
            getRefreshRechargeLiveData().setValue(reRecordList);
    }

    @Override
    public void onLoadmoreFinish(ReRecordList reRecordList) {
        getLoadMoreRechargeLiveData().setValue(reRecordList);

    }

    @Override
    public void onFinish(ReRecordList reRecordList) {
        getFinishRechargeLiveData().setValue(reRecordList);

    }

    @Override
    public void setQueryStatus(String status) {
        getQueryStatusMutableLiveData().setValue(status);
    }



}
