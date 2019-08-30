package com.yascn.smartpark.model;

import android.util.Log;


import com.yascn.smartpark.bean.AliRecharge;
import com.yascn.smartpark.bean.MonthCardListBean;
import com.yascn.smartpark.bean.WxRecharge;
import com.yascn.smartpark.contract.MonthCardPayInfoContract;
import com.yascn.smartpark.retrofit.GetRetrofitService;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.yascn.smartpark.utils.AppContants.SERVERERROR;

public class MonthCardPayInfoModel implements MonthCardPayInfoContract.Model {

    private Observable<AliRecharge> aliRechargeObservable,aliBuyMonthCardObservable,aliRefillMonthCardObservable;
    private Observable<WxRecharge> wxRechargeObservable,wechatBuyMothCardObservable,wechatRefillMonthCardObservable;
    private Subscription aliSubscription,wechatSubscription,aliBuyMonthcardSubscription,aliRefillMonthcardSubscription,wechatBuyMonthcardSubscription,wechatRefillMothCardSubscription;
    private static final String TAG = "MonthCardPayInfoModel";

    @Override
    public void getAliRechage(final MonthCardPayInfoContract.Presenter presenter, String monthcardId, String userId, String carNumber, String startTime, String aliFlag) {
        aliRechargeObservable = GetRetrofitService.getSecondRetrofitService().getAliMonthCardPayInfo(monthcardId,userId,carNumber,startTime,aliFlag);
        aliSubscription = aliRechargeObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AliRecharge>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(AppContants.TAG, "e = " + e.toString());
                        presenter.serverError(SERVERERROR);
                    }

                    @Override
                    public void onNext(AliRecharge aliRecharge) {
                        LogUtil.d(TAG, "onNext: "+aliRecharge.getStatusCode());
//                        LogUtil.d("errorfeebean", "model" + carLicense.toString());
                        if (aliRecharge.getStatusCode() != 1) {
                            presenter.serverError(aliRecharge.getMsg());
                        } else {
                            LogUtil.d(TAG, "onNext: "+aliRecharge.getObject().getOrderStr()+"---"+aliRecharge.getObject().getFlag());

                            presenter.setAliPayInfo(aliRecharge);
                        }


                    }

                });


    }

    @Override
    public void getWxRechage(final MonthCardPayInfoContract.Presenter presenter, String monthcardId, String userId, String carNumber, String startTime, String wxFlag) {
        wxRechargeObservable = GetRetrofitService.getSecondRetrofitService().getWeChatMonthCardPayInfo(monthcardId,userId,carNumber,startTime,wxFlag);
        wechatSubscription = wxRechargeObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WxRecharge>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(AppContants.TAG, "e = " + e.toString());
                        presenter.serverError(SERVERERROR);
                    }

                    @Override
                    public void onNext(WxRecharge wxRecharge) {
                        LogUtil.d(TAG, "onNext: "+wxRecharge.getStatusCode());
//                        LogUtil.d("errorfeebean", "model" + carLicense.toString());
                        if (wxRecharge.getStatusCode() != 1) {
                            presenter.serverError(wxRecharge.getMsg());
                        } else {


                            presenter.setWxPayInfo(wxRecharge);
                        }


                    }

                });

    }

    @Override
    public void getAliBuyMonthCardPayInfo(final MonthCardPayInfoContract.Presenter presenter, String monthcardId, String userId, String carNumber, String aliFlag, String type) {

        aliBuyMonthCardObservable = GetRetrofitService.getSecondRetrofitService().getAliBuyMonthCardPayInfo(monthcardId,userId,carNumber,aliFlag,type);
        aliBuyMonthcardSubscription = aliBuyMonthCardObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AliRecharge>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(AppContants.TAG, "e = " + e.toString());
                        presenter.serverError(SERVERERROR);
                    }

                    @Override
                    public void onNext(AliRecharge aliRecharge) {
                        LogUtil.d(TAG, "onNext: "+aliRecharge.getStatusCode());
//                        LogUtil.d("errorfeebean", "model" + carLicense.toString());
                        if (aliRecharge.getStatusCode() != 1) {
                            presenter.serverError(aliRecharge.getMsg());
                        } else {
                            LogUtil.d(TAG, "onNext: "+aliRecharge.getObject().getOrderStr()+"---"+aliRecharge.getObject().getFlag());

                            presenter.setAliPayInfo(aliRecharge);
                        }


                    }

                });




    }

    @Override
    public void getWeCahtBuyMonthCardPayInfo(final MonthCardPayInfoContract.Presenter presenter, String monthcardId, String userId, String carNumber, String wechatFlag, String type) {
        wechatBuyMothCardObservable = GetRetrofitService.getSecondRetrofitService().getWeChatBuyMonthCardPayInfo(monthcardId,userId,carNumber,wechatFlag,type);
        wechatBuyMonthcardSubscription = wechatBuyMothCardObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WxRecharge>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(AppContants.TAG, "e = " + e.toString());
                        presenter.serverError(SERVERERROR);
                    }

                    @Override
                    public void onNext(WxRecharge wxRecharge) {
                        LogUtil.d(TAG, "onNext: "+wxRecharge.getStatusCode());
//                        LogUtil.d("errorfeebean", "model" + carLicense.toString());
                        if (wxRecharge.getStatusCode() != 1) {
                            presenter.serverError(wxRecharge.getMsg());
                        } else {


                            presenter.setWxPayInfo(wxRecharge);
                        }


                    }

                });

    }

    @Override
    public void getAliRefillMonthCardPayInfo(final MonthCardPayInfoContract.Presenter presenter, String monthcardId, String monthCardRecordId, String aliFlag, String type) {



        aliRefillMonthCardObservable = GetRetrofitService.getSecondRetrofitService().getAliRefillMonthCardPayInfo(monthcardId,monthCardRecordId,aliFlag,type);
        aliRefillMonthcardSubscription = aliRefillMonthCardObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AliRecharge>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(AppContants.TAG, "e = " + e.toString());
                        presenter.serverError(SERVERERROR);
                    }

                    @Override
                    public void onNext(AliRecharge aliRecharge) {
                        LogUtil.d(TAG, "onNext: "+aliRecharge.getStatusCode());
//                        LogUtil.d("errorfeebean", "model" + carLicense.toString());
                        if (aliRecharge.getStatusCode() != 1) {
                            presenter.serverError(aliRecharge.getMsg());
                        } else {
                            LogUtil.d(TAG, "onNext: "+aliRecharge.getObject().getOrderStr()+"---"+aliRecharge.getObject().getFlag());

                            presenter.setAliPayInfo(aliRecharge);
                        }


                    }

                });


    }

    @Override
    public void getWechatRefillMonthCardPayInfo(final MonthCardPayInfoContract.Presenter presenter, String monthcardId, String monthCardRecordId, String wechatFlag, String type) {
        wechatRefillMonthCardObservable = GetRetrofitService.getSecondRetrofitService().getWeChatRefillMonthCardPayInfo(monthcardId,monthCardRecordId,wechatFlag,type);
        wechatRefillMothCardSubscription = wechatRefillMonthCardObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WxRecharge>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(AppContants.TAG, "e = " + e.toString());
                        presenter.serverError(SERVERERROR);
                    }

                    @Override
                    public void onNext(WxRecharge wxRecharge) {
                        LogUtil.d(TAG, "onNext: "+wxRecharge.getStatusCode());
//                        LogUtil.d("errorfeebean", "model" + carLicense.toString());
                        if (wxRecharge.getStatusCode() != 1) {
                            presenter.serverError(wxRecharge.getMsg());
                        } else {


                            presenter.setWxPayInfo(wxRecharge);
                        }


                    }

                });

    }

    @Override
    public void onDestory() {
        if(wxRechargeObservable !=null){
            wxRechargeObservable.unsubscribeOn(Schedulers.io());
        }
        if(wechatSubscription !=null){

            wechatSubscription.unsubscribe();
        }

        if(aliRechargeObservable !=null){
            aliRechargeObservable.unsubscribeOn(Schedulers.io());
        }
        if(aliSubscription !=null){

            aliSubscription.unsubscribe();
        }

        if(aliBuyMonthCardObservable !=null){
            aliBuyMonthCardObservable.unsubscribeOn(Schedulers.io());
        }
        if(aliBuyMonthcardSubscription !=null){

            aliBuyMonthcardSubscription.unsubscribe();
        }


        if(aliRefillMonthCardObservable !=null){
            aliRefillMonthCardObservable.unsubscribeOn(Schedulers.io());
        }
        if(aliRefillMonthcardSubscription !=null){

            aliRefillMonthcardSubscription.unsubscribe();
        }


        if(wechatBuyMothCardObservable !=null){
            wechatBuyMothCardObservable.unsubscribeOn(Schedulers.io());
        }
        if(wechatBuyMonthcardSubscription !=null){

            wechatBuyMonthcardSubscription.unsubscribe();
        }


        if(wechatRefillMonthCardObservable !=null){
            wechatRefillMonthCardObservable.unsubscribeOn(Schedulers.io());
        }
        if(wechatRefillMothCardSubscription !=null){

            wechatRefillMothCardSubscription.unsubscribe();
        }


    }
}
