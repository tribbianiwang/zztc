package com.yascn.smartpark.model;
import android.util.Log;


import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.YascnParkListBean;
import com.yascn.smartpark.contract.YascnParkIndexContract;
import com.yascn.smartpark.retrofit.GetRetrofitService;
import com.yascn.smartpark.retrofit.RetrofitService;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.viewmodel.YascnParkIndexViewModel;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSFIILED;
import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSLOADING;
import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSSUCCESS;

/**
* Created by MVPHelper on 2017/09/07
*/

public class YascnParkIndexModelImpl extends BaseModel {

    private Observable<YascnParkListBean> yascnParkListBeanObservable;
    private Subscription subscribe;
    private static final String TAG = "YascnParkIndexModelImpl";

    public YascnParkIndexModelImpl(YascnParkIndexViewModel yascnParkIndexViewModel) {
        dataResultListener = yascnParkIndexViewModel;
    }


    public void getYascnParkData() {
        dataResultListener.setQueryStatus(QUERYSTATUSLOADING);
        RetrofitService retrofitService = GetRetrofitService.getRetrofitService();

        yascnParkListBeanObservable = retrofitService.getYascnParkList();


        subscribe = yascnParkListBeanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<YascnParkListBean>() {


                    @Override
                    public void onCompleted() {
                        LogUtil.d(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(TAG, "e = " + e.toString());
                        dataResultListener.setQueryStatus(QUERYSTATUSFIILED);
                     
                    }

                    @Override
                    public void onNext(YascnParkListBean yascnParkListBean) {
                        LogUtil.d(TAG, "onNext: "+yascnParkListBean.getStatusCode());
//                        LogUtil.d("errorfeebean", "model" + yascnParkListBean.toString());
                        if (yascnParkListBean.getStatusCode() != 1) {
                            dataResultListener.setQueryStatus(QUERYSTATUSFIILED);
                        } else {

                            dataResultListener.dataSuccess(yascnParkListBean);
                            dataResultListener.setQueryStatus(QUERYSTATUSSUCCESS);
                        }


                    }
                });
    }


    public void onDestory() {
        if(yascnParkListBeanObservable!=null){
            yascnParkListBeanObservable.unsubscribeOn(Schedulers.io());
        }
        if(subscribe!=null){

            subscribe.unsubscribe();


        }
    }
}