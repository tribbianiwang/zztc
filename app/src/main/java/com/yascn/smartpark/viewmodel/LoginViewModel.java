package com.yascn.smartpark.viewmodel;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;

import com.yascn.smartpark.bean.Login;
import com.yascn.smartpark.bean.PhoneSmsBean;
import com.yascn.smartpark.model.LoginModel;

public class LoginViewModel extends ViewModel implements LifecycleObserver ,LoginModel.DataResultListener{
    private MutableLiveData<Login> loginMutableLiveData;
    private MutableLiveData<PhoneSmsBean> phoneSmsBeanMutableLiveData;
    private MutableLiveData<String> queryStatus;

    private LoginModel loginModel;
    private static final String TAG = "LoginViewModel";

    public MutableLiveData<Login> getLoginMutableLiveData(){
        if(loginMutableLiveData==null){
            loginMutableLiveData = new MutableLiveData<>();
        }
        return loginMutableLiveData;
    }

    public MutableLiveData<PhoneSmsBean> getPhoneSmsBeanMutableLiveData(){
        if(phoneSmsBeanMutableLiveData==null){
            phoneSmsBeanMutableLiveData = new MutableLiveData<>();
        }
        return phoneSmsBeanMutableLiveData;
    }

    public MutableLiveData<String> getQueryStatus(){
        if(queryStatus==null){
            queryStatus = new MutableLiveData<>();
        }
        return queryStatus;

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestory(){
        if(loginModel!=null){
            loginModel.onDestory();

        }

    }


    public void getSmsCode(String phone, String code) {

        if(loginModel==null){
            loginModel = new LoginModel(this);
        }
        loginModel.getSmsCode(phone,code);
    }


    public void startLogin(String phone) {
        if(loginModel==null){
            loginModel = new LoginModel(this);
        }
        loginModel.startLogin(phone);
    }



    @Override
    public void smsCodeResult(PhoneSmsBean phoneSmsBean){
        getPhoneSmsBeanMutableLiveData().setValue(phoneSmsBean);
    }

    @Override
    public void setLoginResult(Login login) {
        getLoginMutableLiveData().setValue(login);
    }



    @Override
    public void setQueryStatus(String status){
        getQueryStatus().setValue(status);
    }



}
