package com.yascn.smartpark.model;

import android.util.Log;


import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.ChangePhoneBean;
import com.yascn.smartpark.bean.PhoneSmsBean;
import com.yascn.smartpark.contract.ChangePhoneContract;
import com.yascn.smartpark.retrofit.GetRetrofitService;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.viewmodel.BaseViewModel;
import com.yascn.smartpark.viewmodel.ChangePhoneViewModel;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.yascn.smartpark.utils.AppContants.SERVERERROR;

/**
 * Created by YASCN on 2018/5/5.
 */

public class ChangePhoneModel extends BaseModel {

    private Observable<ChangePhoneBean> changePhoneObservable;
    private Subscription changePhoneSubscription;

    public ChangePhoneModel(ChangePhoneViewModel changePhoneViewModel) {
        this.dataResultListener = changePhoneViewModel;
    }


    public void getChangePhoneBean( String userId, String phone) {
        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSLOADING);
        changePhoneObservable = GetRetrofitService.getSecondRetrofitService().changePhone(userId,phone);
        changePhoneSubscription = changePhoneObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ChangePhoneBean>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(AppContants.TAG, "e = " + e.toString());
                        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSFIILED);
                    }

                    @Override
                    public void onNext(ChangePhoneBean changePhoneBean) {
                        LogUtil.d(TAG, "onNext: "+changePhoneBean.getStatusCode());
//                        LogUtil.d("errorfeebean", "model" + carLicense.toString());
                        if (changePhoneBean.getStatusCode() != 1) {
                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSFIILED);
//                            presenter.serverError(changePhoneBean.getMsg());
                        } else {
                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSSUCCESS);
                            dataResultListener.dataSuccess(changePhoneBean);
                        }


                    }

                });
    }


    private static final String TAG = "SmsCodeModel";
    @Override
    public void onDestory() {
        if(changePhoneObservable !=null){
            changePhoneObservable.unsubscribeOn(Schedulers.io());
        }
        if(changePhoneSubscription !=null){

            changePhoneSubscription.unsubscribe();


        }
    }
}
