package com.yascn.smartpark.model;

import android.text.TextUtils;


import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.EditU;
import com.yascn.smartpark.contract.InfoContract;
import com.yascn.smartpark.retrofit.GetRetrofitService;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.viewmodel.InfoViewModel;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.yascn.smartpark.utils.AppContants.SERVERERROR;

/**
 * Created by YASCN on 2018/7/21.
 */

public class InfoModel extends BaseModel {

    private Subscription subscription;
    private Observable<EditU> observable;

    public InfoModel(InfoViewModel infoViewModel) {
        this.dataResultListener = infoViewModel;

    }

    @Override
    public void onDestory() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
        if (observable != null) {
            observable.unsubscribeOn(Schedulers.io());
        }
    }


    public void setUserinfo( String userid, String file, String name, String gender, String birthday, String phone) {
        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSLOADING);
        RequestBody requestBody;
        if (TextUtils.isEmpty(file)) {
            //构建body
            requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("USER_ID", userid)
                    .addFormDataPart("IMG", "")
                    .addFormDataPart("NAME", name)
                    .addFormDataPart("GENDER", gender)
                    .addFormDataPart("BIRTHDAY", birthday)
                    .addFormDataPart("PHONE", phone)
                    .build();
        } else {
            File imgFile = new File(file);
            //构建body
            requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("USER_ID", userid)
                    .addFormDataPart("IMG", imgFile.getName(), RequestBody.create(MediaType.parse("image/*"), imgFile))
                    .addFormDataPart("NAME", name)
                    .addFormDataPart("GENDER", gender)
                    .addFormDataPart("BIRTHDAYH", birthday)
                    .addFormDataPart("PHONE", phone)
                    .build();
        }

        observable = GetRetrofitService.getRetrofitService().upLoad(AppContants.URL + AppContants.UPLOAD, requestBody);


        subscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<EditU>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(AppContants.TAG, e.toString());
                        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSFIILED);

                    }

                    @Override
                    public void onNext(EditU editU) {
                        if (editU.getStatusCode() != 1) {
                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSFIILED);
                        } else {
                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSSUCCESS);
                            dataResultListener.dataSuccess(editU);
                        }

                    }
                });
    }
}
