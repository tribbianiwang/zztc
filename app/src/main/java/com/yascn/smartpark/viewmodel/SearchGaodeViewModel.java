package com.yascn.smartpark.viewmodel;

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.ViewModel;

import com.amap.api.services.poisearch.PoiResult;
import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.model.SearchGaodeModel;
import com.yascn.smartpark.utils.LogUtil;

public class SearchGaodeViewModel extends BaseViewModel<PoiResult> implements BaseModel.DataResultListener<PoiResult> {
    private SearchGaodeModel searchGaodeModel;



    public void getGaodeResultBean(String key) {
        if(searchGaodeModel==null){
            searchGaodeModel = new SearchGaodeModel(this);
            baseModel = searchGaodeModel;
        }


        searchGaodeModel.getGaodeResultBean(key);


    }





}
