package com.yascn.smartpark.model;


import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.Login;
import com.yascn.smartpark.bean.PhoneSmsBean;
import com.yascn.smartpark.contract.LoginContract;
import com.yascn.smartpark.retrofit.GetRetrofitService;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.viewmodel.LoginViewModel;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.yascn.smartpark.utils.AppContants.NOMOREDATA;
import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSFIILED;
import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSLOADING;
import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSSUCCESS;
import static com.yascn.smartpark.utils.AppContants.SERVERERROR;

/**
 * Created by YASCN on 2018/7/20.
 */

public class LoginModel {
    private Subscription subscription, phoneSmsSubscription;
    private Observable<Login> observable;
    private Observable<PhoneSmsBean> phoneSmsObservable;

    ;

    public LoginModel(LoginViewModel loginViewModel) {
        dataResultListener = loginViewModel;
    }

    public DataResultListener dataResultListener;
    public interface DataResultListener {
        void smsCodeResult(PhoneSmsBean phoneSmsBean);

        void setLoginResult(Login login);

        void setQueryStatus(String status);
    }


    public void startLogin(String phoneNumber) {
        dataResultListener.setQueryStatus(QUERYSTATUSLOADING);
        observable = GetRetrofitService.getRetrofitService().login(phoneNumber);
        subscription = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Login>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(AppContants.TAG, "e = " + e.toString());
                        dataResultListener.setQueryStatus(QUERYSTATUSFIILED);
                    }

                    @Override
                    public void onNext(Login login) {
                        if (login.getStatusCode() != 1) {
                            dataResultListener.setQueryStatus(QUERYSTATUSFIILED);
                        } else {
                            dataResultListener.setQueryStatus(QUERYSTATUSSUCCESS);
                            dataResultListener.setLoginResult(login);
                        }


                    }
                });
    }


    public void onDestory() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
        if(phoneSmsSubscription!=null){
            phoneSmsSubscription.unsubscribe();
        }

        if (observable != null) {
            observable.unsubscribeOn(Schedulers.io());
        }

        if(phoneSmsObservable!=null){
            phoneSmsObservable.unsubscribeOn(Schedulers.io());
        }

    }

    private static final String TAG = "LoginInteractorImpl";


    public void getSmsCode(final String phone, final String code) {
        dataResultListener.setQueryStatus(QUERYSTATUSLOADING);
        phoneSmsObservable = GetRetrofitService.getRetrofitService().getPhoneSmsBean(phone, code);
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
                        dataResultListener.setQueryStatus(QUERYSTATUSFIILED);
                    }

                    @Override
                    public void onNext(PhoneSmsBean phoneSmsBean) {

                        if (phoneSmsBean.getStatusCode() != 1) {
                            dataResultListener.setQueryStatus(QUERYSTATUSFIILED);;
                        } else {
                            dataResultListener.setQueryStatus(QUERYSTATUSSUCCESS);
                            dataResultListener.smsCodeResult(phoneSmsBean);
                        }


                    }
                });
    }
}
