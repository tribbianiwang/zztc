package com.yascn.smartpark.viewmodel;

import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.MonthCardDetailBean;
import com.yascn.smartpark.model.MonthCardDetailModel;

public class MonthCardDetailViewModel extends BaseViewModel<MonthCardDetailBean> implements BaseModel.DataResultListener<MonthCardDetailBean> {

    MonthCardDetailModel monthCardDetailModel;

    public void getMonthCardDetail(String parkId) {
        if(monthCardDetailModel==null){
            monthCardDetailModel = new MonthCardDetailModel(this);
            baseModel = monthCardDetailModel;
        }

        monthCardDetailModel.getMonthCardDetail( parkId);
    }


}
