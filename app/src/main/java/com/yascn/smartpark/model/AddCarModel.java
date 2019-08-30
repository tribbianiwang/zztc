package com.yascn.smartpark.model;

import android.util.Log;


import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.BandNumber;
import com.yascn.smartpark.contract.AddCarContract;
import com.yascn.smartpark.retrofit.GetRetrofitService;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.viewmodel.AddCarViewModel;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSFIILED;
import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSSUCCESS;

/**
 * Created by YASCN on 2017/9/9.
 */

public class AddCarModel extends BaseModel {

    private Subscription subscription;
    private Observable<BandNumber> observable;

    public AddCarModel(AddCarViewModel addCarViewModel) {
        dataResultListener  = addCarViewModel;
    }

    /**
     * 添加车牌
     * @param userID 用户id
     * @param number 车牌号码
     *
     */

    public void addCar(String userID, String number) {
        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSLOADING);
        observable = GetRetrofitService.getRetrofitService().bandNumber(userID, number);
        subscription = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BandNumber>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(AppContants.TAG, "e = " + e.toString());
                        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSFIILED);

                    }

                    @Override
                    public void onNext(BandNumber bandNumber){
                        if (bandNumber.getStatusCode() != 1) {
                            dataResultListener.setQueryStatus("车牌添加失败，可能已被他人绑定");
                        } else {
                            dataResultListener.dataSuccess(bandNumber);
                            dataResultListener.setQueryStatus(QUERYSTATUSSUCCESS);
                        }

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
