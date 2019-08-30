package com.yascn.smartpark.viewmodel;

import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.ParkDetailBean;
import com.yascn.smartpark.model.ParkDetailModelImpl;

public class ParkDetailViewModel extends BaseViewModel<ParkDetailBean> implements BaseModel.DataResultListener<ParkDetailBean> {
    ParkDetailModelImpl parkDetailModel;

    public void getParkDetail(String parkId) {
        if(parkDetailModel==null){
            parkDetailModel = new ParkDetailModelImpl(this);
            baseModel = parkDetailModel;
        }

        parkDetailModel.getParkDetail(parkId);

    }

}
