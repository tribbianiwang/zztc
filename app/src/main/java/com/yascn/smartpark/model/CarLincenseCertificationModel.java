package com.yascn.smartpark.model;

import android.text.TextUtils;
import android.util.Log;


import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.EditU;
import com.yascn.smartpark.bean.LicenseCertificationResultBean;
import com.yascn.smartpark.contract.CarLincenseCertificationContract;
import com.yascn.smartpark.retrofit.GetRetrofitService;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.viewmodel.CarCertificationViewModel;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.yascn.smartpark.utils.AppContants.LINCENSECERTIFICATIONPARAMIMG;
import static com.yascn.smartpark.utils.AppContants.LINCENSECERTIFICATIONPARAMLICENSEID;
import static com.yascn.smartpark.utils.AppContants.SERVERERROR;
import static com.yascn.smartpark.utils.AppContants.SERVERSUCCESSSTATUSCODE;

/**
 * Created by YASCN on 2017/12/1.
 */

public class CarLincenseCertificationModel extends BaseModel {

    private Subscription subscription;
    private Observable<LicenseCertificationResultBean> observable;

    public CarLincenseCertificationModel(CarCertificationViewModel carCertificationViewModel) {
        this.dataResultListener = carCertificationViewModel;

    }


    public void postCarLicense( String carId, String filepath) {
        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSLOADING);

        RequestBody requestBody;

            File imgFile = new File(filepath);
            //构建body
            requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart(LINCENSECERTIFICATIONPARAMLICENSEID, carId)
                    .addFormDataPart(LINCENSECERTIFICATIONPARAMIMG, imgFile.getName(), RequestBody.create(MediaType.parse("image/*"), imgFile))
                    .build();


        observable = GetRetrofitService.getRetrofitService().uploadLincense(AppContants.URL + AppContants.LINCENSECERTIFICATIONURL, requestBody);


        subscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LicenseCertificationResultBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(AppContants.TAG, e.toString());
                        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSFIILED);

                    }

                    @Override
                    public void onNext(LicenseCertificationResultBean licenseCertificationResultBean) {
                        if(licenseCertificationResultBean.getStatusCode()!=SERVERSUCCESSSTATUSCODE){
                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSFIILED);
//                            presenter.serverError(licenseCertificationResultBean.getMsg());
                        }else{
                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSSUCCESS);
                            dataResultListener.dataSuccess(licenseCertificationResultBean);
                        }

                    }
                });
    }


    public void onDestory() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
        if (observable != null) {
            observable.unsubscribeOn(Schedulers.io());
        }
    }



}
