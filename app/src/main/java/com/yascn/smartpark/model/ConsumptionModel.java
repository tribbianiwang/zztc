package com.yascn.smartpark.model;

import android.util.Log;


import com.yascn.smartpark.bean.ReRecordList;
import com.yascn.smartpark.contract.WalletConsumptionDetailContract;
import com.yascn.smartpark.retrofit.GetRetrofitService;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/9/10.
 */

public class ConsumptionModel implements WalletConsumptionDetailContract.ConsumptionInteractor {

    private Subscription subscription;
    private Observable<ReRecordList> observable;

    @Override
    public void getRecharge(int pageNumber, String userID, String cate, final RechargeCallback rechargeCallback) {
        observable = GetRetrofitService.getRetrofitService().reRecordList(pageNumber, userID, cate);
        subscription = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ReRecordList>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(AppContants.TAG, "e = " + e.toString());
                        rechargeCallback.onError();
                    }

                    @Override
                    public void onNext(ReRecordList reRecordList) {
                        rechargeCallback.onFinish(reRecordList);
                    }
                });
    }

    @Override
    public void reflesh(int pageNumber, String userID, String cate, final RefleshCallback refleshCallback) {
        observable = GetRetrofitService.getRetrofitService().reRecordList(pageNumber, userID, cate);
        subscription = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ReRecordList>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(AppContants.TAG, "e = " + e.toString());
                        refleshCallback.onRefleshError();
                    }

                    @Override
                    public void onNext(ReRecordList reRecordList) {
                        refleshCallback.onRefleshFinish(reRecordList);
                    }
                });
    }

    @Override
    public void loadmore(int pageNumber, String userID, String cate, final LoadmoreCallback loadmoreCallback) {
        observable = GetRetrofitService.getRetrofitService().reRecordList(pageNumber, userID, cate);
        subscription = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ReRecordList>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(AppContants.TAG, "e = " + e.toString());
                        loadmoreCallback.onLoadmoreError();
                    }

                    @Override
                    public void onNext(ReRecordList reRecordList) {
                        loadmoreCallback.onLoadmoreFinish(reRecordList);
                    }
                });
    }

    @Override
    public void onDestroy() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
        if (observable != null) {
            observable.unsubscribeOn(Schedulers.io());
        }
    }
}
