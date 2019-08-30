package com.yascn.smartpark.model;

import android.util.Log;


import com.yascn.smartpark.bean.OrderList;
import com.yascn.smartpark.bean.Ordering;
import com.yascn.smartpark.contract.OrderFinishContract;
import com.yascn.smartpark.retrofit.GetRetrofitService;
import com.yascn.smartpark.retrofit.RetrofitService;
import com.yascn.smartpark.utils.LogUtil;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.yascn.smartpark.utils.AppContants.NOMOREDATA;
import static com.yascn.smartpark.utils.AppContants.SERVERERROR;

/**
 * Created by YASCN on 2018/4/25.
 */

public class OrderFinishModel implements OrderFinishContract.Model {
    private Observable<OrderList> orderingObservable;
    private static final String TAG = "OrderingModelImpl";
    private Subscription subscribe;

    @Override
    public void getOrderList(final OrderFinishContract.Presenter presenter, String userId, int pageNumber) {
        RetrofitService retrofitService = GetRetrofitService.getRetrofitService();

        orderingObservable = retrofitService.orderList(userId,pageNumber+"");

        subscribe = orderingObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<OrderList>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.d(TAG, "onCompleted: ");
                        LogUtil.i("yascnparkdetail", "onCliplete");
                    }

                    @Override
                    public void onError(Throwable e) {
//

                        LogUtil.d(TAG, "onError: "+e.toString());
                        if(e instanceof IndexOutOfBoundsException){
                            presenter.serverError(NOMOREDATA);
                        }else{
                            presenter.serverError(SERVERERROR);
                        }

                    }

                    @Override
                    public void onNext(OrderList OrderingBean) {
                        LogUtil.d(TAG, "onNext: "+OrderingBean.getStatusCode());

                        if (OrderingBean.getStatusCode() != 1) {
                            presenter.serverError(OrderingBean.getMsg());
                        } else {
                            presenter.setOrderList(OrderingBean);
                        }


                    }
                });
    }

    @Override
    public void onDestory() {
        if(orderingObservable !=null){
            orderingObservable.unsubscribeOn(Schedulers.io());
        }
        if(subscribe!=null){

            subscribe.unsubscribe();


        }
    }
}
