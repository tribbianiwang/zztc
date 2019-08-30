package com.yascn.smartpark.model;

import android.content.Context;


import com.yascn.smartpark.R;
import com.yascn.smartpark.base.AndroidApplication;
import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.Comment;
import com.yascn.smartpark.contract.EvaluateContract;
import com.yascn.smartpark.retrofit.GetRetrofitService;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.StringUtils;
import com.yascn.smartpark.viewmodel.BaseViewModel;
import com.yascn.smartpark.viewmodel.EvaluateViewModel;

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

import static com.yascn.smartpark.utils.AppContants.SECONDURL;

/**
 * Created by YASCN on 2018/7/18.
 */

public class EvaluateModel extends BaseModel{

    private Subscription subscription1;
    private Observable<Comment> observable1;
    private static final String TAG = "EvaluateInteractorImpl";

    public EvaluateModel(EvaluateViewModel evaluateViewModel) {
        dataResultListener = evaluateViewModel;
    }


    public void sumbitEvaluate( String userid, String orderFormId, String star, final String content, ArrayList<String> imgs) {
        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSLOADING);

        //构建body
        final MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("STAR", star)
                .addFormDataPart("CONTENT", content)
                .addFormDataPart("ORDER_FORM_ID", orderFormId);
        final ArrayList<File> files = new ArrayList<File>();
        for (int i = 0; i < imgs.size(); i++) {
            final File imgFile = new File(imgs.get(i));
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
                            observable1 = GetRetrofitService.getRetrofitService().comment(SECONDURL + AppContants.COMMENT, requestBody);
                            subscription1 = observable1
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Subscriber<Comment>() {
                                        @Override
                                        public void onCompleted() {

                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSFIILED);
                                        }

                                        @Override
                                        public void onNext(Comment comment) {
                                            LogUtil.d(TAG, "onNext:1 "+comment.getMsg()+comment.getObject().getFlag());
                                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSSUCCESS);
                                            dataResultListener.dataSuccess(comment);
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
                            GetRetrofitService.getRetrofitService().comment(SECONDURL + AppContants.COMMENT, requestBody)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Subscriber<Comment>() {
                                        @Override
                                        public void onCompleted() {

                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSFIILED);
                                            LogUtil.d(TAG, e.toString());
                                        }

                                        @Override
                                        public void onNext(Comment comment) {
                                            LogUtil.d(TAG, "onNext:2 "+comment.getMsg()+comment.getObject().getFlag());
                                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSSUCCESS);
                                            dataResultListener.dataSuccess(comment);
                                        }
                                    });
                        }
                    });
        } else if (files.size() == 0) {
            MultipartBody requestBody = builder.build();
            GetRetrofitService.getRetrofitService().comment(SECONDURL + AppContants.COMMENT, requestBody)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Comment>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSFIILED);
                            LogUtil.d(TAG, e.toString());
                        }

                        @Override
                        public void onNext(Comment comment) {
                            LogUtil.d(TAG, "onNext: "+comment.getMsg()+comment.getObject().getFlag());
                            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSSUCCESS);
                            dataResultListener.dataSuccess(comment);
                        }
                    });
        }

    }

    @Override
    public void onDestory() {
        if (subscription1 != null) {
            subscription1.unsubscribe();
        }
        if (observable1 != null) {
            observable1.unsubscribeOn(Schedulers.io());
        }
    }



}

