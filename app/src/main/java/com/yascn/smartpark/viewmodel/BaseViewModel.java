package com.yascn.smartpark.viewmodel;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;

import com.yascn.smartpark.base.BaseModel;

import com.yascn.smartpark.utils.LogUtil;

public class BaseViewModel<T> extends ViewModel implements LifecycleObserver {

    private MutableLiveData<T> mutableLiveDataEntry;
    private MutableLiveData<String> queryStatus;

    public BaseModel baseModel;

    public MutableLiveData<T> getMutableLiveDataEntry(){
        if(mutableLiveDataEntry ==null){
            mutableLiveDataEntry = new MutableLiveData<>();
        }
        return mutableLiveDataEntry;
    }

    public MutableLiveData<String> getQueryStatus() {
        if(queryStatus==null){
            queryStatus = new MutableLiveData<>();
        }
        return queryStatus;
    }

    private static final String TAG = "BaseViewModel";

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestory(){
        LogUtil.d(TAG, "onDestory: ");
        if(baseModel!=null){
            baseModel.onDestory();
        }

    }


    public void dataSuccess(T t) {

        getMutableLiveDataEntry().setValue(t);
    }


    public void setQueryStatus(String status) {
        getQueryStatus().setValue(status);


    }
}
