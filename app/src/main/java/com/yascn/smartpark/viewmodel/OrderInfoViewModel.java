package com.yascn.smartpark.viewmodel;

import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.OrderInfoBean;
import com.yascn.smartpark.model.OrderInfoModel;
import com.yascn.smartpark.utils.LoginStatusUtils;

public class OrderInfoViewModel extends BaseViewModel<OrderInfoBean> implements BaseModel.DataResultListener<OrderInfoBean> {

    OrderInfoModel orderInfoModel;

    public void getOrderInfo(String orderId,String userId) {
        if(orderInfoModel==null){
            orderInfoModel = new OrderInfoModel(this);
            baseModel = orderInfoModel;
        }
        orderInfoModel.getOrderInfo(orderId, userId);


    }

}
