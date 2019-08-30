package com.yascn.smartpark.model;

import android.util.Log;


import com.yascn.smartpark.bean.DelNumber;
import com.yascn.smartpark.bean.NumberList;
import com.yascn.smartpark.bean.SetAutoPay;
import com.yascn.smartpark.bean.SetDefaultNo;
import com.yascn.smartpark.contract.MyCarContract;
import com.yascn.smartpark.retrofit.GetRetrofitService;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YASCN on 2017/9/7.
 */

public class MyCarModel implements MyCarContract.MyCarInteractor {

    private Subscription subscription1;
    private Observable<DelNumber> observable1;

    private Subscription subscription2;
    private Observable<SetDefaultNo> observable2;

    private Subscription subscription3;
    private Observable<SetAutoPay> observable3;

    private Subscription subscription4;
    private Observable<NumberList> observable4;

    @Override
    public void numberList(String userID, final NumberlistCallback numberlistCallback) {
        observable4 = GetRetrofitService.getRetrofitService().numberList(userID);
        subscription4 = observable4
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NumberList>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(AppContants.TAG, "e = " + e.toString());
                        numberlistCallback.onNumberlistError();
                    }

                    @Override
                    public void onNext(NumberList numberList) {
                        numberlistCallback.onFinish(numberList);
                    }
                });
    }

    @Override
    public void deleteCar(String numberID, final DeleteCallback deleteCallback) {
        observable1 = GetRetrofitService.getRetrofitService().delNumber(numberID);
        subscription1 = observable1
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DelNumber>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(AppContants.TAG, "e = " + e.toString());
                        deleteCallback.onDeleteError();
                    }

                    @Override
                    public void onNext(DelNumber delNumber) {
                        deleteCallback.onFinish(delNumber);
                    }
                });
    }

    @Override
    public void setDefault(String numberID, String userID, final DefaultCallback defaultCallback) {

        observable2 = GetRetrofitService.getRetrofitService().setDefaultNo(numberID, userID);
        subscription2 = observable2
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SetDefaultNo>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(AppContants.TAG, "e = " + e.toString());
                        defaultCallback.onDefaultError();
                    }

                    @Override
                    public void onNext(SetDefaultNo setDefaultNo) {
                        defaultCallback.onFinish(setDefaultNo);
                    }
                });
    }

    @Override
    public void setPay(final String numberID, String cate, final PayCallback payCallback) {
        observable3 = GetRetrofitService.getRetrofitService().setAutoPay(numberID, cate);
        subscription3 = observable3
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SetAutoPay>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(AppContants.TAG, "e = " + e.toString());
                        payCallback.onPayError();
                    }

                    @Override
                    public void onNext(SetAutoPay setAutoPay) {
                        payCallback.onFinish(numberID,setAutoPay);
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

        if (subscription3 != null) {
            subscription3.unsubscribe();
        }

        if (observable3 != null) {
            observable3.unsubscribeOn(Schedulers.io());
        }
    }
}
