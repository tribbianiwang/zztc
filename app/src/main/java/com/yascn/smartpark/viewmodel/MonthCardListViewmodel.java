package com.yascn.smartpark.viewmodel;

import android.provider.ContactsContract;

import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.MonthCardListBean;
import com.yascn.smartpark.model.MonthCardListModel;

public class MonthCardListViewmodel extends BaseViewModel<MonthCardListBean> implements BaseModel.DataResultListener<MonthCardListBean> {
    MonthCardListModel monthCardListModel;
    public void getMonthCardList() {
        if(monthCardListModel==null){
            monthCardListModel = new MonthCardListModel(this);
            baseModel = monthCardListModel;
        }
        monthCardListModel.getMonthCardList();

    }

}
