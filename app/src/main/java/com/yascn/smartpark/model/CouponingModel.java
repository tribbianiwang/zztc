package com.yascn.smartpark.model;

import android.util.Log;


import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.ChangePhoneBean;
import com.yascn.smartpark.bean.CouponingBean;
import com.yascn.smartpark.contract.CouponingContract;
import com.yascn.smartpark.retrofit.GetRetrofitService;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.viewmodel.CouponViewModel;

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
 * Created by YASCN on 2018/5/7.
 */

public class CouponingModel extends BaseModel {

    private Observable<CouponingBean> couponingObservable;
    private Subscription couponingSubscription;

    private static final String TAG = "CouponingModel";

    public CouponingModel(CouponViewModel couponViewModel) {
            dataResultListener = couponViewModel;
    }


    public void getCouponing( String userId) {

        dataResultListener.setQueryStatus(QUERYSTATUSLOADING);
        couponingObservable = GetRetrofitService.getSecondRetrofitService().getCouponing(userId);
        couponingSubscription = couponingObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CouponingBean>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(TAG, "e = " + e.toString());
                        dataResultListener.setQueryStatus(QUERYSTATUSFIILED);
                    }

                    @Override
                    public void onNext(CouponingBean couponingBean) {
                        LogUtil.d(TAG, "onNext: "+couponingBean.getStatusCode());
//                        LogUtil.d("errorfeebean", "model" + carLicense.toString());
                        if (couponingBean.getStatusCode() != 1) {
                            dataResultListener.setQueryStatus(QUERYSTATUSFIILED);
                        } else {

                            dataResultListener.dataSuccess(couponingBean);
                            dataResultListener.setQueryStatus(QUERYSTATUSSUCCESS);
                        }


                    }

                });
    }


    public void onDestory() {
        Log.d(TAG, "onDestory: couponing");
        if(couponingObservable !=null){
            couponingObservable.unsubscribeOn(Schedulers.io());
        }
        if(couponingSubscription !=null){

            couponingSubscription.unsubscribe();


        }
    }
}
