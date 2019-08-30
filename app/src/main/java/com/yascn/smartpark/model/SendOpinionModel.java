package com.yascn.smartpark.model;

import android.text.TextUtils;


import com.yascn.smartpark.base.AndroidApplication;
import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.OpinionBean;
import com.yascn.smartpark.retrofit.GetRetrofitService;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.viewmodel.OpinionViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnMultiCompressListener;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;
import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSFIILED;
import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSLOADING;
import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSSUCCESS;
import static com.yascn.smartpark.utils.AppContants.SENDOPINIONURL;
import static com.yascn.smartpark.utils.AppContants.URL;

/**
 * Created by YASCN on 2018/5/8.
 */

public class SendOpinionModel extends BaseModel  {
    private Subscription subscription;
    private Observable<OpinionBean> observable;


    public SendOpinionModel(OpinionViewModel opinionViewModel) {
        dataResultListener = opinionViewModel;
    }

    public void sendOpinion(String userId, String type, final String content, String img1, String img2, String img3) {
        dataResultListener.setQueryStatus(QUERYSTATUSLOADING);

        //构建body
        final MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("USER_ID", userId)
                .addFormDataPart("TYPE", type)
                .addFormDataPart("CONTENT", content);
        final ArrayList<File> files = new ArrayList<File>();
        if(!TextUtils.isEmpty(img1)){
            final File imgFile = new File(img1);
            files.add(imgFile);
        }
        if(!TextUtils.isEmpty(img2)){
            final File imgFile = new File(img2);
            files.add(imgFile);
        }

        if(!TextUtils.isEmpty(img3)){
            final File imgFile = new File(img3);
            files.add(imgFile);
        }

        if (files.size() > 0) {
            Luban.compress(AndroidApplication.getInstances().getApplicationContext(), files)
                    .setMaxSize(500)
                    .putGear(Luban.CUSTOM_GEAR)
                    .launch(new OnMultiCompressListener() {
                        @Override
                        public void onStart() {
                            LogUtil.d(TAG, "start");
                        }

                        @Override
                        public void onSuccess(List<File> fileList) {
                            for (int i = 0; i < fileList.size(); i++) {
                                LogUtil.d(TAG, "压缩后的路径：" + fileList.get(i).getAbsolutePath());
                                LogUtil.d(TAG, "压缩后的大小：" + fileList.get(i).length());
                                builder.addFormDataPart("imgs", fileList.get(i).getName(), RequestBody.create(MediaType.parse("image/*"), fileList.get(i)));
                            }

                            MultipartBody requestBody = builder.build();
                            observable = GetRetrofitService.getRetrofitService().sentOpinion(URL+SENDOPINIONURL , requestBody);
                            subscription = observable
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Subscriber<OpinionBean>() {
                                        @Override
                                        public void onCompleted() {

                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            dataResultListener.setQueryStatus(QUERYSTATUSFIILED);
                                            LogUtil.d(TAG, e.toString());
                                        }

                                        @Override
                                        public void onNext(OpinionBean OpinionBean) {
                                            if (OpinionBean.getStatusCode() != 1) {
                                                LogUtil.d(TAG, "onNext: "+OpinionBean.getMsg());
                                                dataResultListener.setQueryStatus(QUERYSTATUSFIILED);
                                            } else {

                                                dataResultListener.setQueryStatus(QUERYSTATUSSUCCESS);
                                                dataResultListener.dataSuccess(OpinionBean);
                                            }
                                            LogUtil.d(TAG, "onNext:1 "+OpinionBean.getMsg()+OpinionBean.getObject().getFlag());
//                                            submitEvaluateFinishCallback.onSuccess(OpinionBean);
                                        }
                                    });
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            for (int i = 0; i < files.size(); i++) {
                                LogUtil.d(TAG, "压缩失败：" + files.get(i).getAbsolutePath());
                                builder.addFormDataPart("imgs", files.get(i).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(i)));
                            }

                            MultipartBody requestBody = builder.build();
                            observable = GetRetrofitService.getRetrofitService().sentOpinion(URL+SENDOPINIONURL , requestBody);
                            observable.subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Subscriber<OpinionBean>() {
                                        @Override
                                        public void onCompleted() {

                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            dataResultListener.setQueryStatus(QUERYSTATUSFIILED);
                                            LogUtil.d(TAG, e.toString());
                                        }

                                        @Override
                                        public void onNext(OpinionBean OpinionBean) {
                                            LogUtil.d(TAG, "onNext:2 "+OpinionBean.getMsg()+OpinionBean.getObject().getFlag());
                                            if (OpinionBean.getStatusCode() != 1) {
                                                LogUtil.d(TAG, "onNext: "+OpinionBean.getMsg());
                                                dataResultListener.setQueryStatus(QUERYSTATUSFIILED);
                                            } else {


                                                dataResultListener.setQueryStatus(QUERYSTATUSSUCCESS);
                                                dataResultListener.dataSuccess(OpinionBean);
                                            }
                                        }
                                    });
                        }
                    });
        } else if (files.size() == 0) {
            MultipartBody requestBody = builder.build();
            observable = GetRetrofitService.getRetrofitService().sentOpinion(URL+SENDOPINIONURL , requestBody);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<OpinionBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            dataResultListener.setQueryStatus(QUERYSTATUSFIILED);
                            LogUtil.d(TAG, e.toString());
                        }

                        @Override
                        public void onNext(OpinionBean OpinionBean) {
                            LogUtil.d(TAG, "onNext: "+OpinionBean.getMsg()+OpinionBean.getObject().getFlag());
                            if (OpinionBean.getStatusCode() != 1) {
                                dataResultListener.setQueryStatus(QUERYSTATUSFIILED);
                            } else {
                                dataResultListener.dataSuccess(OpinionBean);
                            }

                        }
                    });
        }

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
}
