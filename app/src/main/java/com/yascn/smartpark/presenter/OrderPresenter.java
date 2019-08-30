package com.yascn.smartpark.presenter;

import android.text.TextUtils;


import com.yascn.smartpark.bean.ALIPay;
import com.yascn.smartpark.bean.CancelOrder;
import com.yascn.smartpark.bean.GetPayMoney;
import com.yascn.smartpark.bean.WXPay;
import com.yascn.smartpark.bean.WalletPayBean;
import com.yascn.smartpark.contract.OrderContract;
import com.yascn.smartpark.model.OrderModel;
import com.yascn.smartpark.model.Pay0Bean;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.StringUtils;
import com.yascn.smartpark.utils.T;

/**
 * Created by YASCN on 2017/9/13.
 */

public class OrderPresenter implements OrderContract.OrderPresenter, OrderContract.OrderInteractor.CancelCallback, OrderContract.OrderInteractor.GetPaymoneyCallback, OrderContract.OrderInteractor.WXPayFinishCallback, OrderContract.OrderInteractor.ALIPayFinishCallback {
    private OrderContract.OrderView orderView;
    private OrderContract.OrderInteractor orderInteractor;

    public OrderPresenter(OrderContract.OrderView orderView) {
        this.orderView = orderView;
        orderInteractor = new OrderModel();
    }

    @Override
    public void cancel(String orderID) {
        orderView.showDefaultDialog();
        orderInteractor.cancel(orderID, this);
    }

    @Override
    public void cancleFinish(CancelOrder cancelOrder) {
//        1：成功
//        0：本月已取消三次
//        2：已入场，不能取消
        orderView.hideDefaultDialog();
        switch (cancelOrder.getObject().getFlag()) {
            case "1":
                T.showShort(orderView.getContext(), "取消成功");
                orderView.refleshOrder();
                break;
            case "0":
                T.showShort(orderView.getContext(), "本月已取消三次");
                break;
            case "2":
                T.showShort(orderView.getContext(), "已入场，不能取消");
                break;
        }


    }

    @Override
    public void cancleError() {
        orderView.hideDefaultDialog();
        T.showShort(orderView.getContext(), "取消失败");
    }

    private String couponId="";
    @Override
    public void getPaymoney(String orderID,String couponId) {
        orderView.showDefaultDialog();
        this.couponId = couponId;
        orderInteractor.getPaymoney(orderID,couponId, this);
    }

    @Override
    public void getPaymoneyFinish(GetPayMoney getPayMoney) {
        LogUtil.d(AppContants.TAG+"%s", "getPayMoney = " + StringUtils.emptyParseDouble(getPayMoney.getObject().getMoney().toString()));
        LogUtil.d(AppContants.TAG+"%s", "getPayMoney = "+(0.00==StringUtils.emptyParseDouble(getPayMoney.getObject().getMoney().toString())));
        orderView.hideDefaultDialog();
        if(0.00==StringUtils.emptyParseDouble(getPayMoney.getObject().getMoney().toString())&& TextUtils.isEmpty(couponId)){
            T.showShort(orderView.getContext(),"费用为0无需支付");
        }else{
//
            LogUtil.d("moneyS", "getPaymoneyFinish: "+getPayMoney.getObject().getMoneyS()+"----"+getPayMoney.getObject().getMoney());
            orderView.showPayDialog(getPayMoney.getObject().getMoneyS(),getPayMoney.getObject().getMoney(),getPayMoney.getObject().getStay_time());
//            orderView.showPayDialog(getpay);
        }

        couponId = "";

    }

    @Override
    public void getPaymoneyError() {
        orderView.hideDefaultDialog();
        T.showShort(orderView.getContext(), "获取信息失败");
    }

    @Override
    public void getWXPay(String orderID,String couponId,String type) {
        orderView.showDefaultDialog();
        orderInteractor.getWXPay(orderID,couponId,type,this);
    }

    @Override
    public void onWxSuccess(WXPay wxPay) {
        orderView.hideDefaultDialog();
        orderView.wxPay(wxPay);
    }

    @Override
    public void onWxError() {
        orderView.hideDefaultDialog();
    }

    @Override
    public void getALIPay(String orderID,String couponId,String type) {
        orderView.showDefaultDialog();
        orderInteractor.getALIPay(orderID,couponId,type, this);
    }

    @Override
    public void startWalletPay(String orderId, String couponId,String type) {
        orderView.showProgress();

        orderInteractor.startWalletPay(this,orderId,couponId,type);
    }

    @Override
    public void setWalletPayResult(WalletPayBean walletPayResult) {
        orderView.hideProgress();
        orderView.setWalletPayResult(walletPayResult);
    }

    @Override
    public void setPayFreeResult(Pay0Bean pay0Bean) {
      orderView.setPayFreeResult(pay0Bean);
    }

    @Override
    public void serverError(String errorMsg) {
        orderView.hideProgress();
        orderView.serverError(errorMsg);
    }

    @Override
    public void startPayFree(String orderFormId, String couponId) {
        orderInteractor.startPayFree(this,orderFormId,couponId);
    }

    @Override
    public void onAliSuccess(ALIPay aliPay) {
        orderView.hideDefaultDialog();
        orderView.aliPay(aliPay);
    }

    @Override
    public void onAliError() {
        orderView.hideDefaultDialog();
    }

    @Override
    public void onDestroy() {
        orderInteractor.onDestroy();
    }
}
