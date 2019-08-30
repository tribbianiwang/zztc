package com.yascn.smartpark.model;


import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.MonthCardDetailBean;
import com.yascn.smartpark.bean.MonthCardListBean;
import com.yascn.smartpark.contract.MonthCardDetailContract;
import com.yascn.smartpark.retrofit.GetRetrofitService;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.viewmodel.MonthCardDetailViewModel;


import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.yascn.smartpark.utils.AppContants.SERVERERROR;

public class MonthCardDetailModel extends BaseModel {
    private Observable<MonthCardDetailBean> monthCardDetailBeanObservable;
    private Subscription monthCardDetailSubscription;
    private static final String TAG = "MonthCardDetailModel";

    public MonthCardDetailModel(MonthCardDetailViewModel monthCardDetailViewModel) {
        dataResultListener = monthCardDetailViewModel;
    }


    public void getMonthCardDetail( String parId) {
        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSLOADING);
        monthCardDetailBeanObservable = GetRetrofitService.getSecondRetrofitService().getMonthCardDetail(parId);
        monthCardDetailSubscription = monthCardDetailBeanObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MonthCardDetailBean>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(AppContants.TAG, "e = " + e.toString());
                        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSFIILED);
                    }

                    @Override
                    public void onNext(MonthCardDetailBean monthCardDetailBean) {
                        LogUtil.d(TAG, "onNext: "+monthCardDetailBean.getStatusCode());
//                        LogUtil.d("errorfeebean", "model" + carLicense.toString());
                        if (monthCardDetailBean.getStatusCode() != 1) {
                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSFIILED);
                            dataResultListener.setQueryStatus(monthCardDetailBean.getMsg());
                        } else {
                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSSUCCESS);
                            dataResultListener.dataSuccess(monthCardDetailBean);
                        }


                    }

                });
    }

    @Override
    public void onDestory() {
        if(monthCardDetailBeanObservable !=null){
            monthCardDetailBeanObservable.unsubscribeOn(Schedulers.io());
        }
        if(monthCardDetailSubscription !=null){

            monthCardDetailSubscription.unsubscribe();


        }
    }
}