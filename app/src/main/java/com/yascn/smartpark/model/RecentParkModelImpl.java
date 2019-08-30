package com.yascn.smartpark.model;

import android.util.Log;


import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.CarLicense;
import com.yascn.smartpark.bean.RecentParkBean;
import com.yascn.smartpark.contract.RecentParkContract;
import com.yascn.smartpark.retrofit.GetRetrofitService;
import com.yascn.smartpark.retrofit.RetrofitService;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.viewmodel.RecentParkViewModel;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by MVPHelper on 2017/09/14
 */

public class RecentParkModelImpl extends BaseModel {
    private Observable<RecentParkBean> recentParkBeanObservable;
    private Subscription subscribe;
    private static final String TAG = "RecentParkModelImpl";

    public RecentParkModelImpl(RecentParkViewModel recentParkViewModel) {
        dataResultListener = recentParkViewModel;
    }


    public void getRecentPark( String userId) {
        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSLOADING);

        RetrofitService retrofitService = GetRetrofitService.getRetrofitService();

        recentParkBeanObservable = retrofitService.getRecentPark(userId);


        subscribe = recentParkBeanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RecentParkBean>() {


                    @Override
                    public void onCompleted() {
                        LogUtil.d(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG, "onError: " + e.getMessage());
                        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSFIILED);

                    }

                    @Override
                    public void onNext(RecentParkBean recentParkBean) {
                        LogUtil.d(TAG, "onNext: " + recentParkBean.getStatusCode());
//                        LogUtil.d("errorfeebean", "model" + recentParkBean.toString());
                        if (recentParkBean.getStatusCode() != 1) {
                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSFIILED);
                            dataResultListener.setQueryStatus(recentParkBean.getMsg());
                        } else {
                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSSUCCESS);
                            dataResultListener.dataSuccess(recentParkBean);
                        }
                    }
                });
    }


    @Override
    public void onDestory() {
        if (recentParkBeanObservable != null) {
            recentParkBeanObservable.unsubscribeOn(Schedulers.io());
        }
        if (subscribe != null) {
            subscribe.unsubscribe();
        }
    }
}