package com.yascn.smartpark.viewmodel;

import android.util.Log;

import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.HomeBean;
import com.yascn.smartpark.bean.OpinionBean;
import com.yascn.smartpark.model.HomeIndexModel;
import com.yascn.smartpark.model.SendOpinionModel;

public class HomeIndexViewModel extends BaseViewModel<HomeBean> implements  BaseModel.DataResultListener<HomeBean> {
    private HomeIndexModel homeIndexModel;
    private static final String TAG = "HomeIndexViewModel";

    public void getHomeIndex(String userId) {
        if(homeIndexModel==null){
            homeIndexModel = new HomeIndexModel(this);
            baseModel = homeIndexModel;
        }
        Log.d(TAG, "getHomeIndex: ");


        homeIndexModel.getHomeIndex(userId);
    }

}
