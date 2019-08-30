package com.yascn.smartpark.model;

import android.util.Log;


import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.Login;
import com.yascn.smartpark.bean.PhoneSmsBean;
import com.yascn.smartpark.contract.SmsCodeContract;
import com.yascn.smartpark.retrofit.GetRetrofitService;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.viewmodel.SmsCodeViewModel;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.yascn.smartpark.utils.AppContants.SERVERERROR;

/**
 * Created by YASCN on 2018/5/5.
 */

public class SmsCodeModel extends BaseModel {

    private Subscription phoneSmsSubscription;
    private Observable<PhoneSmsBean> phoneSmsObservable;

    public SmsCodeModel(SmsCodeViewModel smsCodeViewModel) {
        dataResultListener = smsCodeViewModel;
    }

    public void getPhoneSms( String phoneNumber,String code) {
        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSLOADING);
        phoneSmsObservable = GetRetrofitService.getRetrofitService().getPhoneSmsBean(phoneNumber,code);
        phoneSmsSubscription = phoneSmsObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PhoneSmsBean>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(AppContants.TAG, "e = " + e.toString());
                        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSFIILED);
//                        presenter.serverError(SERVERERROR);
                    }

                    @Override
                    public void onNext(PhoneSmsBean phoneSmsBean) {
                        LogUtil.d(TAG, "onNext: "+phoneSmsBean.getStatusCode());
//                        LogUtil.d("errorfeebean", "model" + carLicense.toString());
                        if (phoneSmsBean.getStatusCode() != 1) {
                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSFIILED);
//                            presenter.serverError(phoneSmsBean.getMsg());
                        } else {

                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSSUCCESS);
                            dataResultListener.dataSuccess(phoneSmsBean);
                        }


                    }

                });
    }


    private static final String TAG = "SmsCodeModel";
    @Override
    public void onDestory() {
        if(phoneSmsObservable!=null){
            phoneSmsObservable.unsubscribeOn(Schedulers.io());
        }
        if(phoneSmsSubscription!=null){

            phoneSmsSubscription.unsubscribe();


        }
    }
}
