package com.yascn.smartpark.viewmodel;

import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.PhoneSmsBean;
import com.yascn.smartpark.model.SmsCodeModel;

public class SmsCodeViewModel extends BaseViewModel<PhoneSmsBean> implements BaseModel.DataResultListener<PhoneSmsBean> {
    private SmsCodeModel smsCodeModel;

    public void getPhoneSms(String phoneNumber,String code) {
        if(smsCodeModel==null){
            smsCodeModel = new SmsCodeModel(this);
            baseModel = smsCodeModel;
        }

        smsCodeModel.getPhoneSms(phoneNumber,code);


    }


}
