package com.yascn.smartpark.viewmodel;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;

import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.CouponingBean;
import com.yascn.smartpark.model.CouponingModel;

public class CouponViewModel extends BaseViewModel<CouponingBean> implements BaseModel.DataResultListener<CouponingBean> {
    private CouponingModel couponingModel;

    public void getCouponingDatas(String userId) {
        if(couponingModel==null){
            couponingModel = new CouponingModel(this);
            baseModel = couponingModel;
        }
        couponingModel.getCouponing(userId);
    }




}
