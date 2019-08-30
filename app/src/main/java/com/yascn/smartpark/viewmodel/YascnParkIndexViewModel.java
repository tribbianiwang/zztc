package com.yascn.smartpark.viewmodel;

import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.CouponingBean;
import com.yascn.smartpark.bean.YascnParkListBean;
import com.yascn.smartpark.model.CouponingModel;
import com.yascn.smartpark.model.YascnParkIndexModelImpl;

public class YascnParkIndexViewModel extends BaseViewModel<YascnParkListBean> implements BaseModel.DataResultListener<YascnParkListBean> {
    private YascnParkIndexModelImpl yascnParkIndexModel;

    public void getYascnParkData() {
        if(yascnParkIndexModel==null){
//            mapFragment.showProgress();
            yascnParkIndexModel = new YascnParkIndexModelImpl(this);
            baseModel = yascnParkIndexModel;
        }
        yascnParkIndexModel.getYascnParkData();
    }



}
