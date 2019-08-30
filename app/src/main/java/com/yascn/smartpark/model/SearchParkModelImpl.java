package com.yascn.smartpark.model;
import android.util.Log;


import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.SearchResult;
import com.yascn.smartpark.contract.SearchParkContract;
import com.yascn.smartpark.retrofit.GetRetrofitService;
import com.yascn.smartpark.retrofit.RetrofitService;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.viewmodel.SearchViewModel;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
* Created by MVPHelper on 2017/09/12
*/

public class SearchParkModelImpl extends BaseModel {
    private Observable<SearchResult> searchResultObservable;
    private Subscription subscribe;
    private static final String TAG = "CarLicenseModelImpl";

    public SearchParkModelImpl(SearchViewModel searchViewModel) {
        dataResultListener = searchViewModel;

    }

    public void getSearchResult( String keyWorld) {
        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSLOADING);
        RetrofitService retrofitService = GetRetrofitService.getRetrofitService();

        searchResultObservable = retrofitService.startSearch(keyWorld);


        subscribe = searchResultObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SearchResult>() {


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
                    public void onNext(SearchResult searchResult) {
                        LogUtil.d(TAG, "onNext: "+searchResult.getStatusCode());
//                        LogUtil.d("errorfeebean", "model" + carLicense.toString());
                        if (searchResult.getStatusCode() != 1) {
                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSFIILED);
                            dataResultListener.setQueryStatus(searchResult.getMsg());
                        } else {

                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSSUCCESS);
                            dataResultListener.dataSuccess(searchResult);
                        }


                    }
                });
    }

    @Override
    public void onDestory() {
        if(searchResultObservable!=null){
            searchResultObservable.unsubscribeOn(Schedulers.io());
        }
        if(subscribe!=null){

            subscribe.unsubscribe();


        }
    }
}