package com.yascn.smartpark.viewmodel;

import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.Ordering;
import com.yascn.smartpark.model.OrderOngoingModel;

public class OrderOngoingViewModel extends BaseViewModel<Ordering> implements BaseModel.DataResultListener<Ordering> {
    private OrderOngoingModel orderOngoingModel;

    public void getOrdering(String userId) {
        if(orderOngoingModel==null){
            orderOngoingModel = new OrderOngoingModel(this);
            baseModel = orderOngoingModel;
        }

        orderOngoingModel.getOrdering(userId);


    }


}
