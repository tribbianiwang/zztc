package com.yascn.smartpark.model;

import android.util.Log;


import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.ReRecordList;
import com.yascn.smartpark.contract.WalletRechargeDetailContract;
import com.yascn.smartpark.retrofit.GetRetrofitService;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.viewmodel.RechargeViewmodel;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/9/10.
 */

public class WalletDetailRechargeModel {

    private static final String TAG = "WalletDetailRechargeMod";
    private Subscription subscription;
    private Observable<ReRecordList> observable;

    public DataResultListener dataResultListener;

    public WalletDetailRechargeModel(RechargeViewmodel rechargeViewmodel) {
        dataResultListener = rechargeViewmodel;
    }

    public  interface DataResultListener{
        void onRefleshFinish(ReRecordList reRecordList);
        void onLoadmoreFinish(ReRecordList reRecordList);
         void onFinish(ReRecordList reRecordList);
        void setQueryStatus(String status);
    }



    public void getRecharge(int pageNumber, String userID, String cate) {
        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSLOADING);
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
                        LogUtil.d(TAG, "e = " + e.toString());
                        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSFIILED);
//                        rechargeCallback.onError();
                    }

                    @Override
                    public void onNext(ReRecordList reRecordList) {
                        LogUtil.d(TAG, "onNext: "+reRecordList.getObject().size());
                        if (reRecordList.getObject().size() == 0) {
                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSNODATA);
                        } else {
                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSSUCCESS);
                            dataResultListener.onFinish(reRecordList);
                        }

                    }
                });
    }


    public void reflesh(int pageNumber, String userID, String cate) {
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
                        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSREFRESHERROR);
//                        refleshCallback.onRefleshError();
                    }

                    @Override
                    public void onNext(ReRecordList reRecordList) {
                        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSREFRESHFINISH);
                        dataResultListener.onRefleshFinish(reRecordList);
                    }
                });
    }


    public void loadmore(int pageNumber, String userID, String cate) {
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
                        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSLOADMOREERROR);
                    }

                    @Override
                    public void onNext(ReRecordList reRecordList) {
                        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSLOADMOREFINISH);
                        dataResultListener.onLoadmoreFinish(reRecordList);
                    }
                });
    }


    public void onDestroy() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
        if (observable != null) {
            observable.unsubscribeOn(Schedulers.io());
        }
    }
}
