package com.yascn.smartpark.contract;

import android.content.Context;

import com.yascn.smartpark.bean.ALIPay;
import com.yascn.smartpark.bean.CancelOrder;
import com.yascn.smartpark.bean.GetPayMoney;
import com.yascn.smartpark.bean.WXPay;
import com.yascn.smartpark.bean.WalletPayBean;
import com.yascn.smartpark.model.Pay0Bean;

/**
 * Created by YASCN on 2018/7/26.
 */

public class OrderContract {
    public interface OrderInteractor {
        interface CancelCallback {
            void cancleFinish(CancelOrder cancelOrder);

            void cancleError();
        }

        interface GetPaymoneyCallback {
            void getPaymoneyFinish(GetPayMoney getPayMoney);

            void getPaymoneyError();
        }

        interface WXPayFinishCallback {
            void onWxSuccess(WXPay wxPay);

            void onWxError();
        }

        interface ALIPayFinishCallback {
            void onAliSuccess(ALIPay aliPay);

            void onAliError();
        }



        void cancel(String orderID, OrderContract.OrderInteractor.CancelCallback cancelCallback);

        void getPaymoney(String orderID, String couponId, OrderContract.OrderInteractor.GetPaymoneyCallback getPaymoneyCallback);

        void getWXPay(String orderID,String couponId,String type,OrderContract.OrderInteractor.WXPayFinishCallback wxPayFinishCallback);

        void getALIPay(String orderID,String couponId,String type,OrderContract.OrderInteractor.ALIPayFinishCallback aliPayFinishCallback);

        void startWalletPay(OrderPresenter orderPresenter, String orderId, String couponId, String type);

        void startPayFree(OrderPresenter orderPresenter,String orderFormId,String couponId);

        void onDestroy();
    }
    public interface OrderPresenter {
        void cancel(String orderID);

        void getPaymoney(String orderID,String couponId);

        void getWXPay(String orderID,String CouponId,String type);

        void getALIPay(String orderID,String CouponId,String type);

        void startWalletPay(String orderId,String CouponId,String type);

        void setWalletPayResult(WalletPayBean walletPayResult);

        void setPayFreeResult(Pay0Bean pay0Bean);

        void serverError(String errorMsg);

        void startPayFree(String orderFormId, String couponId);
        void onDestroy();
    }


    public interface OrderView {
        void showDefaultDialog();

        void hideDefaultDialog();

        void refleshOrder();

        Context getContext();

        void showPayDialog(String totalMoney,String payMoney,String parkDuringTime);

        void hidePayDialog();

        void wxPay(WXPay wxPay);

        void aliPay(ALIPay aliPay);

        void serverError(String errormsg);
        public void showProgress();
        public void hideProgress();

        void setWalletPayResult(WalletPayBean walletPayBean);

        void setPayFreeResult(Pay0Bean pay0Bean);

    }

}
