package com.yascn.smartpark.model;

import android.util.Log;


import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.OrderInfoBean;
import com.yascn.smartpark.bean.QueryCarLicenseFeeBean;
import com.yascn.smartpark.contract.OrderInfoContract;
import com.yascn.smartpark.retrofit.GetRetrofitService;
import com.yascn.smartpark.retrofit.RetrofitService;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.viewmodel.OrderInfoViewModel;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by YASCN on 2018/5/7.
 */

public class OrderInfoModel extends BaseModel {

    private Observable<OrderInfoBean> orderinfoObservable;
    private Subscription orderInfoSubscription;
    private static final String TAG = "OrderInfoModel";

    public OrderInfoModel(OrderInfoViewModel orderInfoViewModel) {
        dataResultListener = orderInfoViewModel;
    }

    public void getOrderInfo( String orderId, String userId) {

        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSLOADING);

        RetrofitService retrofitService = GetRetrofitService.getRetrofitService();

        orderinfoObservable = retrofitService.getOrderInfo(orderId,userId);


        orderInfoSubscription = orderinfoObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<OrderInfoBean>() {


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
                    public void onNext(OrderInfoBean orderInfoBean) {
                        LogUtil.d(TAG, "onNext: "+orderInfoBean.getStatusCode());
//                        LogUtil.d("errorfeebean", "model" + carLicense.toString());
                        if (orderInfoBean.getStatusCode() != 1) {
                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSFIILED);
                            dataResultListener.setQueryStatus(orderInfoBean.getMsg());
                        } else {

                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSSUCCESS);
                            dataResultListener.dataSuccess(orderInfoBean);
                        }


                    }
                });
    }



    @Override
    public void onDestory() {
        if(orderinfoObservable !=null){
            orderinfoObservable.unsubscribeOn(Schedulers.io());
        }
        if(orderInfoSubscription !=null){

            orderInfoSubscription.unsubscribe();


        }
    }



}
