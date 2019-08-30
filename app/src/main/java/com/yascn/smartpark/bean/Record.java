package com.yascn.smartpark.bean;

/**
 * Created by YASCN on 2017/8/30.
 */

public class Record {
    private boolean open;

    private OrderList.ObjectBean.OrderListBean orderListBean;

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public OrderList.ObjectBean.OrderListBean getOrderListBean() {
        return orderListBean;
    }

    public void setOrderListBean(OrderList.ObjectBean.OrderListBean orderListBean) {
        this.orderListBean = orderListBean;
    }

    @Override
    public String toString() {
        return "Record{" +
                "open=" + open +
                ", orderListBean=" + orderListBean +
                '}';
    }
}
