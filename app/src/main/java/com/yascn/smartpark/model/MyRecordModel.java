package com.yascn.smartpark.model;


import com.yascn.smartpark.bean.OrderList;
import com.yascn.smartpark.contract.MyRecordContract;
import com.yascn.smartpark.retrofit.GetRetrofitService;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YASCN on 2017/9/14.
 */

public class MyRecordModel implements MyRecordContract.MyRecordInteractor {

    private Subscription subscription1;
    private Observable<OrderList> observable1;

    private Subscription subscription2;
    private Observable<OrderList> observable2;

    private Subscription subscription3;
    private Observable<OrderList> observable3;

    @Override
    public void getData(String userID, String pageNum, final GetDataCallback getDataCallback) {
        observable1 = GetRetrofitService.getRetrofitService().orderList(userID, pageNum);

        subscription1 = observable1
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<OrderList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getDataCallback.onGetDataError();
                        LogUtil.d(AppContants.TAG, e.toString());
                    }

                    @Override
                    public void onNext(OrderList orderList) {
                        getDataCallback.onGetDataFinish(orderList);
                    }
                });
    }

    @Override
    public void reflesh(String userID, String pageNum, final RefleshCallback refleshCallback) {
        observable1 = GetRetrofitService.getRetrofitService().orderList(userID, pageNum);

        subscription1 = observable1
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<OrderList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        refleshCallback.onRefleshError();
                        LogUtil.d(AppContants.TAG, e.toString());
                    }

                    @Override
                    public void onNext(OrderList orderList) {
                        refleshCallback.onRefleshFinish(orderList);
                    }
                });
    }

    @Override
    public void addData(String userID, String pageNum, final AddDataCallback addDataCallback) {
        observable1 = GetRetrofitService.getRetrofitService().orderList(userID, pageNum);

        subscription1 = observable1
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<OrderList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        addDataCallback.onAddDataError();
                        LogUtil.d(AppContants.TAG, e.toString());
                    }

                    @Override
                    public void onNext(OrderList orderList) {
                        addDataCallback.onAddDataFinish(orderList);
                    }
                });
    }

    @Override
    public void onDestroy() {
        if (subscription1 != null) {
            subscription1.unsubscribe();
        }
        if (observable1 != null) {
            observable1.unsubscribeOn(Schedulers.io());
        }

        if (subscription2 != null) {
            subscription2.unsubscribe();
        }
        if (observable2 != null) {
            observable2.unsubscribeOn(Schedulers.io());
        }

        if (subscription3 != null) {
            subscription3.unsubscribe();
        }
        if (observable3 != null) {
            observable3.unsubscribeOn(Schedulers.io());
        }
    }
}
