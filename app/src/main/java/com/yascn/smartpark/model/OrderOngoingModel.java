package com.yascn.smartpark.model;

import android.util.Log;


import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.Ordering;
import com.yascn.smartpark.bean.Ordering;
import com.yascn.smartpark.contract.OrderOngoingContract;
import com.yascn.smartpark.retrofit.GetRetrofitService;
import com.yascn.smartpark.retrofit.RetrofitService;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.viewmodel.OrderOngoingViewModel;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.yascn.smartpark.utils.AppContants.NOMOREDATA;
import static com.yascn.smartpark.utils.AppContants.SERVERERROR;

/**
 * Created by YASCN on 2018/4/23.
 */

public class OrderOngoingModel extends BaseModel {

    private Observable<Ordering> orderingObservable;
    private static final String TAG = "OrderingModelImpl";
    private Subscription subscribe;

    public OrderOngoingModel(OrderOngoingViewModel orderOngoingViewModel) {
        dataResultListener = orderOngoingViewModel;
    }

    /**
     * 查询正在进行订单
     *
     * @param userId    用户id
     */

    public void getOrdering( String userId) {
        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSLOADING);

        RetrofitService retrofitService = GetRetrofitService.getRetrofitService();

        orderingObservable = retrofitService.ordering(userId);

        subscribe = orderingObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Ordering>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.d(TAG, "onCompleted: ");
                    }
                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(TAG, "onError: " + e.toString());
                        if (e instanceof IndexOutOfBoundsException) {
                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSNOMOREDATA);
                        } else {
                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSFIILED);

                        }
                    }
                    @Override
                    public void onNext(Ordering orderingBean) {
                        LogUtil.d(TAG, "onNext: " + orderingBean.getStatusCode());

                        if (orderingBean.getStatusCode() != 1) {
                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSFIILED);
//                            presenter.serverError(orderingBean.getMsg());
                        } else {
                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSSUCCESS);
                            dataResultListener.dataSuccess(orderingBean);
                        }


                    }
                });
    }

    @Override
    public void onDestory() {
        if (orderingObservable != null) {
            orderingObservable.unsubscribeOn(Schedulers.io());
        }
        if (subscribe != null) {

            subscribe.unsubscribe();


        }
    }
}
