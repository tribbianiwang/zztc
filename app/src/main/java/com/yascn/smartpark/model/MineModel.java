package com.yascn.smartpark.model;


import android.util.Log;

import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.Userinfo;
import com.yascn.smartpark.contract.MineContract;
import com.yascn.smartpark.retrofit.GetRetrofitService;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.viewmodel.MineViewModel;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.yascn.smartpark.utils.AppContants.NOMOREDATA;
import static com.yascn.smartpark.utils.AppContants.SERVERERROR;

/**
 * Created by YASCN on 2018/7/21.
 */

public class MineModel extends BaseModel {

    private static final String TAG = "MineModel";

    private Subscription subscription;
    private Observable<Userinfo> observable;

    public MineModel(MineViewModel mineViewModel) {
        dataResultListener = mineViewModel;


    }


    public void getUserInfo(final String userID) {
        LogUtil.d(TAG, "getUserInfo: ");
        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSLOADING);

        observable = GetRetrofitService.getRetrofitService().userinfo(userID);
        subscription = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Userinfo>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(TAG, "e = " + e.toString());
                        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSFIILED);
                    }

                    @Override
                    public void onNext(Userinfo userinfo) {
                        if (userinfo.getStatusCode() != 1) {
                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSFIILED);
                        } else {
                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSSUCCESS);
                            LogUtil.d(TAG, "onNext: "+userinfo.getObject().getPhone());
                            dataResultListener.dataSuccess(userinfo);
                        }
                    }
                });
    }

    @Override
    public void onDestory() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
        if (observable != null) {
            observable.unsubscribeOn(Schedulers.io());
        }
    }
}
