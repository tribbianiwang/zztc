package com.yascn.smartpark.model;

import android.util.Log;


import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.CarLicense;
import com.yascn.smartpark.bean.QueryCarLicenseFeeBean;
import com.yascn.smartpark.contract.CarLicenseFeeContract;
import com.yascn.smartpark.retrofit.GetRetrofitService;
import com.yascn.smartpark.retrofit.RetrofitService;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.viewmodel.CarlicenseFeeViewModel;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by YASCN on 2018/5/5.
 */

public class CarLicenseFeeModel extends BaseModel {

    private Observable<QueryCarLicenseFeeBean> carLicenseFeeObservable;
    private Subscription subscribe;
    private static final String TAG = "CarLicenseFeeModel";

    public CarLicenseFeeModel(CarlicenseFeeViewModel carlicenseFeeViewModel) {
        dataResultListener = carlicenseFeeViewModel;
    }


    public void getLicenseFee(final String carLicense, String userId) {
        LogUtil.d(TAG,"getlicensefee");
        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSLOADING);

        RetrofitService retrofitService = GetRetrofitService.getRetrofitService();

        carLicenseFeeObservable = retrofitService.queryCarLicenseFee(carLicense,userId);


        subscribe = carLicenseFeeObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<QueryCarLicenseFeeBean>() {


                    @Override
                    public void onCompleted() {
                        LogUtil.d(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG, "onError: "+e.getMessage() );

                        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSFIILED);
//                        presenter.serverError(AppContants.SERVERERROR);

                    }

                    @Override
                    public void onNext(QueryCarLicenseFeeBean carLicenseFeeBean) {
                        LogUtil.d(TAG, "onNext: "+carLicenseFeeBean.getStatusCode());
//                        LogUtil.d("errorfeebean", "model" + carLicense.toString());
                        if (carLicenseFeeBean.getStatusCode() != 1) {
                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSFIILED);
//                            presenter.serverError(carLicenseFeeBean.getMsg());
                        } else {

                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSSUCCESS);
                            dataResultListener.dataSuccess(carLicenseFeeBean);
//                            presenter.setLicenseFee(carLicenseFeeBean);
                        }


                    }
                });
    }

    @Override
    public void onDestory() {
        if(carLicenseFeeObservable !=null){
            carLicenseFeeObservable.unsubscribeOn(Schedulers.io());
        }
        if(subscribe!=null){

            subscribe.unsubscribe();


        }
    }

}
