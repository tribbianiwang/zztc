package com.yascn.smartpark.model;
import android.util.Log;


import com.yascn.smartpark.bean.ParkComment;
import com.yascn.smartpark.contract.ParkCommentContract;
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
* Created by MVPHelper on 2017/09/09
*/

public class ParkCommentModelImpl implements ParkCommentContract.Model{

    private Observable<ParkComment> parkDetail;
    private static final String TAG = "ParkCommentModelImpl";
    private Subscription subscribe;

    @Override
    public void getParCommentkData(final ParkCommentContract.Presenter presenter, String parkId, int pageNumber) {
        RetrofitService retrofitService = GetRetrofitService.getRetrofitService();

        parkDetail = retrofitService.getParkComment(parkId, pageNumber);

        subscribe = parkDetail.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ParkComment>() {
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
                    public void onNext(ParkComment parkCommentBean) {
                        LogUtil.d(TAG, "onNext: "+parkCommentBean.getStatusCode());

                        if (parkCommentBean.getStatusCode() != 1) {
                            presenter.serverError(parkCommentBean.getMsg());
                        } else {
                            presenter.setparkCommentData(parkCommentBean);
                        }


                    }
                });
    }

    @Override
    public void onDestory() {
        if(parkDetail!=null){
            parkDetail.unsubscribeOn(Schedulers.io());
        }
        if(subscribe!=null){

            subscribe.unsubscribe();


        }
    }
}