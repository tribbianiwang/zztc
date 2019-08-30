package com.yascn.smartpark.activity;

import android.content.Context;

import com.yascn.smartpark.base.BaseActivity;
import com.yascn.smartpark.bean.PhoneSmsBean;
import com.yascn.smartpark.contract.SmsCodeContract;

/**
 * Created by YASCN on 2018/5/5.
 */

public class SmsCodeBaseActivity extends BaseActivity  implements SmsCodeContract.View{
    @Override
    public void serverError(String errorMsg) {

    }

    @Override
    public void setPhoneSms(PhoneSmsBean smsBean) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public Context getContext() {
        return this;
    }
}
