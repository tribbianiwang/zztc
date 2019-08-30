package com.yascn.smartpark.model;

import android.content.Context;
import android.util.Log;


import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.UpdateBean;
import com.yascn.smartpark.contract.UpdateContract;
import com.yascn.smartpark.retrofit.GetRetrofitService;
import com.yascn.smartpark.retrofit.RetrofitService;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.viewmodel.UpdateViewModel;

import org.androidannotations.annotations.App;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YASCN on 2018/1/8.
 */

public class UpdateModel extends BaseModel {
    private Observable<UpdateBean> UpdateBeanObservable;
    private Subscription subscribe;
    private static final String TAG = "UpdateModel";

    public UpdateModel(UpdateViewModel updateViewModel) {
        this.dataResultListener = updateViewModel;

    }


    public void getUpdateBean() {
        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSLOADING);

        RetrofitService retrofitService = GetRetrofitService.getRetrofitService();

        UpdateBeanObservable = retrofitService.getUpdateData();


        subscribe = UpdateBeanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UpdateBean>() {


                    @Override
                    public void onCompleted() {
                        LogUtil.d(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG, "onError:" + e.getMessage());
//                        presenter.serverError(AppContants.SERVERERROR);
                        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSFIILED);

                    }


                    @Override
                    public void onNext(UpdateBean updateBean) {
                        LogUtil.d(TAG, "onNext: " + updateBean.getStatusCode());
//                        LogUtil.d("errorfeebean", "model" + loginBeawn.toString());
                        if (updateBean.getStatusCode() != 1) {
//                            presenter.serverError(updateBean.getMsg());
                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSFIILED);
                        } else {
                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSSUCCESS);
                            dataResultListener.dataSuccess(updateBean);
                        }


                    }
                });
    }

    @Override
    public void onDestory() {
        if (UpdateBeanObservable != null) {
            UpdateBeanObservable.unsubscribeOn(Schedulers.io());
        }
        if (subscribe != null) {
            subscribe.unsubscribe();
        }
    }
}
