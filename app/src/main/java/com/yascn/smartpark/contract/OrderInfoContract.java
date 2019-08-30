package com.yascn.smartpark.contract;

import android.content.Context;

import com.yascn.smartpark.bean.CarLicense;
import com.yascn.smartpark.bean.OrderInfoBean;

/**
 * Created by YASCN on 2018/5/7.
 */

public interface OrderInfoContract {
    public interface View extends BaseNormalContract.View{
        public void setOrderInfo(OrderInfoBean orderInfoBean);

    }

    public interface Presenter extends BaseNormalContract.Presenter{
        public void getOrderInfo(String orderId);
        public void setOrderInfo(OrderInfoBean orderInfoBean);
    }

    public interface Model extends BaseNormalContract.Model{
        public void getOrderInfo(OrderInfoContract.Presenter presenter,String orderId,String userId);
    }
}
