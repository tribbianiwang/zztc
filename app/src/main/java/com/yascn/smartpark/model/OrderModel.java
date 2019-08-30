package com.yascn.smartpark.model;

import android.text.TextUtils;


import com.yascn.smartpark.bean.ALIPay;
import com.yascn.smartpark.bean.CancelOrder;
import com.yascn.smartpark.bean.GetPayMoney;
import com.yascn.smartpark.bean.WXPay;
import com.yascn.smartpark.bean.WalletPayBean;
import com.yascn.smartpark.contract.OrderContract;
import com.yascn.smartpark.retrofit.GetRetrofitService;
import com.yascn.smartpark.retrofit.RetrofitService;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YASCN on 2017/9/13.
 */

public class OrderModel implements OrderContract.OrderInteractor {

    private Subscription subscription1;
    private Observable<CancelOrder> observable1;

    private Subscription subscription2;
    private Observable<GetPayMoney> observable2;

    private Subscription subscription3;
    private Observable<WXPay> observable3;

    private Subscription subscription4;
    private Observable<ALIPay> observable4;

    private Subscription walletPaySubscription;
    private Observable<WalletPayBean> walletPayobservable;

    private Subscription payFreenSubscription;
    private Observable<Pay0Bean> payFreeobservable;

    private static final String TAG = "OrderModel";
    @Override
    public void cancel(String orderID, final CancelCallback cancelCallback) {
        observable1 = GetRetrofitService.getRetrofitService().cancelOrder(orderID);

        subscription1 = observable1
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CancelOrder>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        cancelCallback.cancleError();
                        LogUtil.d(TAG,e.toString());
                    }

                    @Override
                    public void onNext(CancelOrder cancelOrder) {
                        cancelCallback.cancleFinish(cancelOrder);
                    }
                });
    }

    @Override
    public void getPaymoney(String orderID,String couponId,final GetPaymoneyCallback getPaymoneyCallback) {
        LogUtil.d(TAG,"getPaymoney");
        if(TextUtils.isEmpty(couponId)){
            observable2 = GetRetrofitService.getRetrofitService().getPayMoney(orderID,couponId);
        }else{
            observable2 = GetRetrofitService.getSecondRetrofitService().getPayMoney(orderID,couponId);
        }


        subscription2 = observable2
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetPayMoney>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getPaymoneyCallback.getPaymoneyError();
                        LogUtil.d(TAG,e.toString());
                    }

                    @Override
                    public void onNext(GetPayMoney getPayMoney) {
                        getPaymoneyCallback.getPaymoneyFinish(getPayMoney);
                    }
                });
    }

    @Override
    public void getWXPay(String orderID,String couponId, String type,final WXPayFinishCallback wxPayFinishCallback) {
        observable3 = GetRetrofitService.getSecondRetrofitService().wxPay(orderID,couponId,type);
        subscription3 = observable3
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WXPay>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        wxPayFinishCallback.onWxError();
                        LogUtil.d(TAG,e.toString());
                    }

                    @Override
                    public void onNext(WXPay wxPay) {
                        wxPayFinishCallback.onWxSuccess(wxPay);

                    }
                });
    }

    @Override
    public void getALIPay(String orderID, String couponId,String type,final ALIPayFinishCallback aliPayFinishCallback) {
        observable4 = GetRetrofitService.getSecondRetrofitService().aliPay(orderID,couponId,type);
        subscription4 = observable4
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ALIPay>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        aliPayFinishCallback.onAliError();
                        LogUtil.d(TAG,e.toString());
                    }

                    @Override
                    public void onNext(ALIPay aliPay) {
                        aliPayFinishCallback.onAliSuccess(aliPay);
                    }
                });
    }


    @Override
    public void startWalletPay(final OrderContract.OrderPresenter orderPresenter, String orderId, String couponId, String type) {
        RetrofitService retrofitService = GetRetrofitService.getSecondRetrofitService();

        LogUtil.d( "startWalletPay: ",orderId+"---type"+type);

        walletPayobservable = retrofitService.getwalletPayResult(orderId,couponId,type);


        walletPaySubscription = walletPayobservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WalletPayBean>() {


                    @Override
                    public void onCompleted() {
                        LogUtil.d("ordemomodel","onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e( "onError: ",e.getMessage() );
                        orderPresenter.serverError(AppContants.SERVERERROR);

                    }

                    @Override
                    public void onNext(WalletPayBean walletPayBean) {
                        LogUtil.d( "onNext: ","code"+walletPayBean.getStatusCode());
//                        LogUtil.d("errorfeebean", "model" + carLimitBean.toString());
                        if (walletPayBean.getStatusCode() != 1) {
                            orderPresenter.serverError(walletPayBean.getMsg());
                        } else {


                            orderPresenter.setWalletPayResult(walletPayBean);
                        }


                    }
                });
    }

    @Override
    public void startPayFree(final OrderContract.OrderPresenter presenter, String orderFormId, String couponId) {
        RetrofitService retrofitService = GetRetrofitService.getSecondRetrofitService();


        payFreeobservable = retrofitService.startPayFree(orderFormId,couponId);


        payFreenSubscription = payFreeobservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Pay0Bean>() {


                    @Override
                    public void onCompleted() {
                        LogUtil.d( TAG,"onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e( "onError: ",e.getMessage() );
                        presenter.serverError(AppContants.SERVERERROR);

                    }

                    @Override
                    public void onNext(Pay0Bean pay0Bean) {
                        LogUtil.d( "onNext: ","code"+pay0Bean.getStatusCode());
//                        LogUtil.d("errorfeebean", "model" + carLimitBean.toString());
                        if (pay0Bean.getStatusCode() != 1) {
                            presenter.serverError(pay0Bean.getMsg());
                        } else {


                            presenter.setPayFreeResult(pay0Bean);
                        }


                    }
                });
    }

    @Override
    public void onDestroy() {
        if (subscription1 != null) {
            subscription1.unsubscribe();
        }
        if (observable1 != null) {
            observable1.unsubscribeOn(Schedulers.io());
        }

        if (subscription2 != null) {
            subscription2.unsubscribe();
        }
        if (observable2 != null) {
            observable2.unsubscribeOn(Schedulers.io());
        }

        if(payFreenSubscription!=null){
            payFreenSubscription.unsubscribe();
        }

        if(payFreeobservable!=null){
            payFreeobservable.unsubscribeOn(Schedulers.io());
        }
    }
}
