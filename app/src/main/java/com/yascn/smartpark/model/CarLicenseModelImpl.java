package com.yascn.smartpark.model;
import android.util.Log;

import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.CarLicense;
import com.yascn.smartpark.contract.CarLicenseContract;
import com.yascn.smartpark.retrofit.GetRetrofitService;
import com.yascn.smartpark.retrofit.RetrofitService;
import com.yascn.smartpark.utils.AppContants;

import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.viewmodel.CarLicenseViewModel;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
* Created by MVPHelper on 2017/09/11
*/

public class CarLicenseModelImpl extends BaseModel {
    private Observable<CarLicense> carLicenseObservable;
    private Subscription subscribe;
    private static final String TAG = "CarLicenseModelImpl";

    public CarLicenseModelImpl(CarLicenseViewModel carLicenseViewModel) {
        dataResultListener = carLicenseViewModel;

    }


    public void getCarLicense(String userId) {
        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSLOADING);


        RetrofitService retrofitService = GetRetrofitService.getRetrofitService();

        carLicenseObservable = retrofitService.getCarLicense(userId);


        subscribe = carLicenseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CarLicense>() {


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
                    public void onNext(CarLicense carLicense) {
                        LogUtil.d(TAG, "onNext: "+carLicense.getStatusCode());
//                        LogUtil.d("errorfeebean", "model" + carLicense.toString());
                        if (carLicense.getStatusCode() != 1) {
                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSFIILED);
                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSFIILED);
                        } else {

                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSSUCCESS);
                            dataResultListener.dataSuccess(carLicense);
                        }


                    }
                });
    }

    @Override
    public void onDestory() {
        if(carLicenseObservable!=null){
            carLicenseObservable.unsubscribeOn(Schedulers.io());
        }
        if(subscribe!=null){

            subscribe.unsubscribe();


        }
    }
}