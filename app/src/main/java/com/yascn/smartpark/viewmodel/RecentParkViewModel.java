package com.yascn.smartpark.viewmodel;

import android.provider.ContactsContract;

import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.RecentParkBean;
import com.yascn.smartpark.model.RecentParkModelImpl;

public class RecentParkViewModel extends BaseViewModel<RecentParkBean> implements BaseModel.DataResultListener<RecentParkBean> {
    RecentParkModelImpl recentParkModel;


    public void getRecentPark(String userId) {
        if(recentParkModel==null){
            recentParkModel = new RecentParkModelImpl(this);
            baseModel = recentParkModel;
        }

        recentParkModel.getRecentPark(userId);


    }

}
