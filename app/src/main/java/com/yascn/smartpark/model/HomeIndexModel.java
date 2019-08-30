package com.yascn.smartpark.model;

import android.util.Log;


import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.ChangePhoneBean;
import com.yascn.smartpark.bean.HomeBean;
import com.yascn.smartpark.contract.HomeIndexContract;
import com.yascn.smartpark.retrofit.GetRetrofitService;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.viewmodel.BaseViewModel;
import com.yascn.smartpark.viewmodel.HomeIndexViewModel;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSFIILED;
import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSLOADING;
import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSSUCCESS;
import static com.yascn.smartpark.utils.AppContants.SERVERERROR;

/**
 * Created by YASCN on 2018/6/1.
 */

public class HomeIndexModel extends BaseModel {




    private Observable<HomeBean> homeIndexObservable;
    private Subscription homeIndexSubscription;


    private static final String TAG = "HomeIndexModel";

    public HomeIndexModel(HomeIndexViewModel homeIndexViewModel) {
        dataResultListener = homeIndexViewModel;

    }


    public void getHomeIndex( String userId) {
        dataResultListener.setQueryStatus(QUERYSTATUSLOADING);
        homeIndexObservable = GetRetrofitService.getSecondRetrofitService().getHomeIndex(userId);
        homeIndexSubscription = homeIndexObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HomeBean>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(AppContants.TAG, "e = " + e.toString());
                        dataResultListener.setQueryStatus(QUERYSTATUSFIILED);
                    }

                    @Override
                    public void onNext(HomeBean homeBean) {
                        LogUtil.d(TAG, "onNext: "+homeBean.getStatusCode());
//                        LogUtil.d("errorfeebean", "model" + carLicense.toString());
                        if (homeBean.getStatusCode() != 1) {
                            dataResultListener.setQueryStatus(QUERYSTATUSFIILED);
                        } else {
                            dataResultListener.dataSuccess(homeBean);
                            dataResultListener.setQueryStatus(QUERYSTATUSSUCCESS);
                        }


                    }

                });
    }

    @Override
    public void onDestory() {
        if(homeIndexObservable !=null){
            homeIndexObservable.unsubscribeOn(Schedulers.io());
        }
        if(homeIndexSubscription !=null){

            homeIndexSubscription.unsubscribe();


        }
    }
}
