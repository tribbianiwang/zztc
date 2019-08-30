package com.yascn.smartpark.model;
import android.util.Log;


import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.ParkPointBean;
import com.yascn.smartpark.contract.StartPointParkContract;
import com.yascn.smartpark.retrofit.GetRetrofitService;
import com.yascn.smartpark.retrofit.RetrofitService;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.viewmodel.StartPointViewModel;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
* Created by MVPHelper on 2017/09/12
*/

public class StartPointParkModelImpl extends BaseModel {

    private Observable<ParkPointBean> startPointObservable;
    private Subscription subscribe;
    private static final String TAG = "StartPointParkModelImpl";

    public StartPointParkModelImpl(StartPointViewModel startPointViewModel) {
        this.dataResultListener = startPointViewModel;
    }


    public void startParkPoint( String parkId, String USER_ID, String carLicense) {
        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSLOADING);
        RetrofitService retrofitService = GetRetrofitService.getRetrofitService();
        LogUtil.d(TAG, "startParkPoint: "+parkId+"--"+USER_ID+"---"+carLicense);

        startPointObservable = retrofitService.startPointPark(parkId,USER_ID,carLicense);


        subscribe = startPointObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ParkPointBean>() {


                    @Override
                    public void onCompleted() {
                        LogUtil.d(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG, "onError: "+e.getMessage() );
//                        presenter.serverError(AppContants.SERVERERROR);
                        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSFIILED);

                    }

                    @Override
                    public void onNext(ParkPointBean parkPointBean) {
                        LogUtil.d(TAG, "onNext: "+parkPointBean.getStatusCode());
//                        LogUtil.d("errorfeebean", "model" + carLicense.toString());
                        if (parkPointBean.getStatusCode() != 1) {
//                            presenter.serverError(parkPointBean.getMsg());
                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSFIILED);
                        } else {
                            LogUtil.d(TAG, "onNext: "+parkPointBean.getObject().getFlag());
                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSSUCCESS);
                            dataResultListener.dataSuccess(parkPointBean);
//                            presenter.setParkPointResult(parkPointBean);
                        }


                    }
                });
    }

    @Override
    public void onDestory() {
        if (startPointObservable != null) {
            startPointObservable.unsubscribeOn(Schedulers.io());
        }
        if (subscribe != null) {
            subscribe.unsubscribe();
        }
    }
}