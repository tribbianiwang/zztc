package com.yascn.smartpark.contract;

import com.yascn.smartpark.bean.OrderList;

/**
 * Created by YASCN on 2018/4/25.
 */

public interface OrderFinishContract {
    public interface View extends BaseNormalContract.View{
        public void setOrderList(OrderList orderList);
    }

    public interface Presenter extends BaseNormalContract.Presenter{
        public void getOrderList(String userId,int pageNumber);
        public void setOrderList(OrderList orderList);
        public void reloadData();
        void queryList();
    }

    public interface Model extends BaseNormalContract.Model{
        public void getOrderList(OrderFinishContract.Presenter presenter,String userId,int pageNumber);
    }

}
