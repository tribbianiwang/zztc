package com.yascn.smartpark.model;


import android.util.Log;

import com.yascn.smartpark.bean.AliRecharge;
import com.yascn.smartpark.bean.Login;
import com.yascn.smartpark.bean.PhoneSmsBean;
import com.yascn.smartpark.bean.Userinfo;
import com.yascn.smartpark.bean.WxRecharge;
import com.yascn.smartpark.contract.WalletContract;
import com.yascn.smartpark.retrofit.GetRetrofitService;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.viewmodel.WalletViewModel;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSSUCCESS;

/**
 * Created by YASCN on 2018/7/24.
 */

public class WalletModel {

    private static final String TAG = "WalletModel";
    private Subscription userinfoSubscript;
    private Observable<Userinfo> userinfoObservable;

    private Subscription aliRechargeSubscription;
    private Observable<AliRecharge> aliRechargeObservable;

    private Subscription wxRechargeSubscription;
    private Observable<WxRecharge> wxRechargeObservable;

    public DataResultListener dataResultListener;

    public WalletModel(WalletViewModel walletViewModel) {
        dataResultListener =  walletViewModel;
    }

    public interface DataResultListener {
        void setAliRecharge(AliRecharge aliRecharge) ;

         void setWxRecharge(WxRecharge wxRecharge);

        void setUserInfo(Userinfo userinfo);

        void setQueryStatus(String status);
    }





    public void onDestory() {
        Log.d(TAG, "onDestory: ");
        if (userinfoSubscript != null) {
            userinfoSubscript.unsubscribe();
        }
        if (userinfoObservable != null) {
            userinfoObservable.unsubscribeOn(Schedulers.io());
        }

        if (aliRechargeSubscription != null) {
            aliRechargeSubscription.unsubscribe();
        }
        if (aliRechargeObservable != null) {
            aliRechargeObservable.unsubscribeOn(Schedulers.io());
        }

        if (wxRechargeSubscription != null) {
            wxRechargeSubscription.unsubscribe();
        }
        if (wxRechargeObservable != null) {
            wxRechargeObservable.unsubscribeOn(Schedulers.io());
        }
    }


    public void startAliRecharge( String money, String userID) {
        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSLOADING);
        aliRechargeObservable = GetRetrofitService.getRetrofitService().aliRecharge(money, userID);

        aliRechargeSubscription = aliRechargeObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AliRecharge>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSFIILED);
                        LogUtil.d(TAG, e.toString());
                    }

                    @Override
                    public void onNext(AliRecharge aliRecharge) {
                        if (aliRecharge.getStatusCode() != 1) {
//                            presenter.serverError(aliRecharge.getMsg());
                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSFIILED);
                        } else {
                            dataResultListener.setQueryStatus(QUERYSTATUSSUCCESS);
                            dataResultListener.setAliRecharge(aliRecharge);
                        }

                    }
                });
    }


    public void startWxRecharge( String money, String userID) {
        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSLOADING);
        wxRechargeObservable = GetRetrofitService.getRetrofitService().wxRecharge(money, userID);

        wxRechargeSubscription = wxRechargeObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WxRecharge>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(TAG, e.toString());
                        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSFIILED);
                    }

                    @Override
                    public void onNext(WxRecharge wxRecharge) {
                        if (wxRecharge.getStatusCode() != 1) {
//                            presenter.serverError(wxRecharge.getMsg());
                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSFIILED);
                        } else {
                            dataResultListener.setQueryStatus(QUERYSTATUSSUCCESS);
                            dataResultListener.setWxRecharge(wxRecharge);
                        }
                    }
                });
    }


    public void getUserInfo( String userId) {
        userinfoObservable = GetRetrofitService.getRetrofitService().userinfo(userId);
        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSLOADING);
        userinfoSubscript = userinfoObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Userinfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSFIILED);
                        LogUtil.d(AppContants.TAG, e.toString());
                    }

                    @Override
                    public void onNext(Userinfo userinfo) {
                        if (userinfo.getStatusCode() != 1) {
                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSFIILED);
//                            presenter.serverError(userinfo.getMsg());
                        } else {

                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSSUCCESS);
                            dataResultListener.setUserInfo(userinfo);
                        }
                    }
                });
    }
}
