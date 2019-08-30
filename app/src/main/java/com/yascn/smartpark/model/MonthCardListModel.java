package com.yascn.smartpark.model;


import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.CouponingBean;
import com.yascn.smartpark.bean.MonthCardListBean;
import com.yascn.smartpark.contract.MonthCardListContract;
import com.yascn.smartpark.retrofit.GetRetrofitService;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.viewmodel.MonthCardListViewmodel;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSFIILED;
import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSSUCCESS;
import static com.yascn.smartpark.utils.AppContants.SERVERERROR;

public class MonthCardListModel extends BaseModel {

    private static final String TAG = "MonthCardListModel%s";
    private Observable<MonthCardListBean> monthCardListBeanObservable;
    private Subscription monthCardListSubscription;

    public MonthCardListModel(MonthCardListViewmodel monthCardListViewmodel) {
        dataResultListener = monthCardListViewmodel;
    }

    public void getMonthCardList() {
        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSLOADING);

        monthCardListBeanObservable = GetRetrofitService.getSecondRetrofitService().getMonthCardList();
        monthCardListSubscription = monthCardListBeanObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MonthCardListBean>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(AppContants.TAG, "e = " + e.toString());
//                        presenter.serverError(SERVERERROR);
                        dataResultListener.setQueryStatus(QUERYSTATUSFIILED);
                    }

                    @Override
                    public void onNext(MonthCardListBean monthCardListBean) {
                        LogUtil.d(TAG, "onNext: "+monthCardListBean.getStatusCode());
//                        LogUtil.d("errorfeebean", "model" + carLicense.toString());
                        if (monthCardListBean.getStatusCode() != 1) {
                            dataResultListener.setQueryStatus(QUERYSTATUSFIILED);
                            dataResultListener.setQueryStatus(monthCardListBean.getMsg());
//                            presenter.serverError(monthCardListBean.getMsg());
                        } else {

                            dataResultListener.setQueryStatus(QUERYSTATUSSUCCESS);
                            dataResultListener.dataSuccess(monthCardListBean);
                        }


                    }

                });
    }

    @Override
    public void onDestory() {
        if(monthCardListBeanObservable !=null){
            monthCardListBeanObservable.unsubscribeOn(Schedulers.io());
        }
        if(monthCardListSubscription !=null){

            monthCardListSubscription.unsubscribe();


        }
    }
}
