package com.yascn.smartpark.model;

import android.util.Log;


import com.yascn.smartpark.bean.DeleteMessageBean;
import com.yascn.smartpark.bean.MessageBean;
import com.yascn.smartpark.contract.MessageContract;
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
 * Created by YASCN on 2018/7/6.
 */

public class MessageModel implements MessageContract.Model {

    private Observable<MessageBean> messageBeanObservable;
    private static final String TAG = "OrderingModelImpl";
    private Subscription messageBeanSubscribe;

    private Observable<DeleteMessageBean> deleteMessageBeanObservable;
    private Subscription deleteMessageBeanSubscribe;

    @Override
    public void getMessageBean(final MessageContract.Presenter presenter, String userId, int pageNumber) {
        RetrofitService retrofitService = GetRetrofitService.getSecondRetrofitService();

        messageBeanObservable = retrofitService.getMessage(userId, pageNumber + "");

        messageBeanSubscribe = messageBeanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MessageBean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.d(TAG, "onCompleted: ");
                        LogUtil.i("yascnparkdetail", "onCliplete");
                    }

                    @Override
                    public void onError(Throwable e) {
//

                        LogUtil.d(TAG, "onError: " + e.toString());
                        if (e instanceof IndexOutOfBoundsException) {
                            presenter.serverError(NOMOREDATA);
                        } else {
                            presenter.serverError(SERVERERROR);
                        }

                    }

                    @Override
                    public void onNext(MessageBean messageBean) {
                        LogUtil.d(TAG, "onNext: " + messageBean.getStatusCode());

                        if (messageBean.getStatusCode() != 1) {
                            presenter.serverError(messageBean.getMsg());
                        } else {
                            presenter.setMessageBean(messageBean);
                        }


                    }
                });
    }

    @Override
    public void getDeleteMessageBean(final MessageContract.Presenter presenter, String msgId, String flag) {
        RetrofitService retrofitService = GetRetrofitService.getSecondRetrofitService();

        deleteMessageBeanObservable = retrofitService.deleteMessage(msgId, flag);

        deleteMessageBeanSubscribe = deleteMessageBeanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DeleteMessageBean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.d(TAG, "onCompleted: ");
                        LogUtil.i("yascnparkdetail", "onCliplete");
                    }

                    @Override
                    public void onError(Throwable e) {
//

                        LogUtil.d(TAG, "onError: " + e.toString());
                        if (e instanceof IndexOutOfBoundsException) {
                            presenter.serverError(NOMOREDATA);
                        } else {
                            presenter.serverError(SERVERERROR);
                        }

                    }

                    @Override
                    public void onNext(DeleteMessageBean deletemessageBean) {
                        LogUtil.d(TAG, "onNext: " + deletemessageBean.getStatusCode());

                        if (deletemessageBean.getStatusCode() != 1) {
                            presenter.serverError(deletemessageBean.getMsg());
                        } else {
                            presenter.setDeleteMessageBean(deletemessageBean);
                        }


                    }
                });
    }

    @Override
    public void onDestory() {
        if (messageBeanObservable != null) {
            messageBeanObservable.unsubscribeOn(Schedulers.io());
        }
        if (deleteMessageBeanObservable != null) {
            deleteMessageBeanObservable.unsubscribeOn(Schedulers.io());
        }


        if (messageBeanSubscribe != null) {
            messageBeanSubscribe.unsubscribe();
        }

        if(deleteMessageBeanSubscribe !=null){
            deleteMessageBeanSubscribe.unsubscribe();
        }

    }
}
