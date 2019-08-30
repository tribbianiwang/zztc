package com.yascn.smartpark.viewmodel;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;

import com.yascn.smartpark.bean.AliRecharge;
import com.yascn.smartpark.bean.Userinfo;
import com.yascn.smartpark.bean.WxRecharge;
import com.yascn.smartpark.model.WalletModel;

public class WalletViewModel extends ViewModel implements LifecycleObserver ,WalletModel.DataResultListener{
    private MutableLiveData<Userinfo> userinfoMutableLiveData;
    private MutableLiveData<AliRecharge> aliRechargeMutableLiveData;
    private MutableLiveData<WxRecharge> wxRechargeMutableLiveData;
    private MutableLiveData<String> queryStatusMutableLiveData;


    private WalletModel walletModel;
    private static final String TAG = "WalletViewModel";

    public MutableLiveData<Userinfo> getUserinfoMutableLiveData() {
        if(userinfoMutableLiveData==null){
            userinfoMutableLiveData = new MutableLiveData<>();
        }
        return userinfoMutableLiveData;
    }

    public MutableLiveData<AliRecharge> getAliRechargeMutableLiveData() {
        if(aliRechargeMutableLiveData==null){
            aliRechargeMutableLiveData = new MutableLiveData<>();
        }

        return aliRechargeMutableLiveData;
    }

    public MutableLiveData<WxRecharge> getWxRechargeMutableLiveData() {
        if(wxRechargeMutableLiveData==null){
            wxRechargeMutableLiveData = new MutableLiveData<>();
        }

        return wxRechargeMutableLiveData;
    }

    public MutableLiveData<String> getQueryStatusMutableLiveData() {
        if(queryStatusMutableLiveData==null){
            queryStatusMutableLiveData = new MutableLiveData<>();
        }

        return queryStatusMutableLiveData;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestory(){
        if(walletModel!=null){
            walletModel.onDestory();
        }
    }



    public void startAliRecharge(String money, String userID) {
        if(walletModel==null){
            walletModel = new WalletModel(this);
        }
        walletModel.startAliRecharge(money,userID);

    }

    public void startWxRecharge(String money, String userID) {

        if(walletModel==null){
            walletModel = new WalletModel(this);
        }
        walletModel.startWxRecharge(money,userID);

    }

    public void getUserInfo(String userId) {
        if(walletModel==null){
            walletModel = new WalletModel(this);
        }
        walletModel.getUserInfo(userId);

    }


    @Override
    public void setAliRecharge(AliRecharge aliRecharge) {
        getAliRechargeMutableLiveData().setValue(aliRecharge);

    }

    @Override
    public void setWxRecharge(WxRecharge wxRecharge) {
        getWxRechargeMutableLiveData().setValue(wxRecharge);
    }

    @Override
    public void setUserInfo(Userinfo userinfo) {
        getUserinfoMutableLiveData().setValue(userinfo);

    }

    @Override
    public void setQueryStatus(String status) {
        getQueryStatusMutableLiveData().setValue(status);
    }
}
