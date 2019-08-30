package com.yascn.smartpark.model;
import android.util.Log;


import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.ParkDetailBean;
import com.yascn.smartpark.bean.RecentParkBean;
import com.yascn.smartpark.contract.ParkDetailContract;
import com.yascn.smartpark.retrofit.GetRetrofitService;
import com.yascn.smartpark.retrofit.RetrofitService;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.viewmodel.ParkDetailViewModel;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
* Created by MVPHelper on 2017/09/16
*/

public class ParkDetailModelImpl extends BaseModel {
    private Observable<ParkDetailBean> parkDetailObservable;
    private Subscription subscribe;
    private static final String TAG = "ParkDetailModelImpl";

    public ParkDetailModelImpl(ParkDetailViewModel parkDetailViewModel) {
        dataResultListener = parkDetailViewModel;

    }


    public void getParkDetail( String parkId) {
        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSLOADING);
        RetrofitService retrofitService = GetRetrofitService.getRetrofitService();

        parkDetailObservable = retrofitService.getParkDetail(parkId);


        subscribe = parkDetailObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ParkDetailBean>() {


                    @Override
                    public void onCompleted() {
                        LogUtil.d(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG, "onError: "+e.getMessage() );
                        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSFIILED);

                    }

                    @Override
                    public void onNext(ParkDetailBean parkDetailBean) {
                        LogUtil.d(TAG, "onNext: "+parkDetailBean.getStatusCode());
//                        LogUtil.d("errorfeebean", "model" + parkDetailBean.toString());
                        if (parkDetailBean.getStatusCode() != 1) {
                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSFIILED);
                            dataResultListener.setQueryStatus(parkDetailBean.getMsg());
                        } else {
                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSSUCCESS);
                            dataResultListener.dataSuccess(parkDetailBean);
                        }


                    }
                });
    }

    @Override
    public void onDestory() {
        if(parkDetailObservable!=null){
            parkDetailObservable.unsubscribeOn(Schedulers.io());
        }
        if(subscribe!=null){

            subscribe.unsubscribe();


        }
    }
}