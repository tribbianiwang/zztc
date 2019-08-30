package com.yascn.smartpark.contract;

import com.yascn.smartpark.bean.ParkDetailBean;
import com.yascn.smartpark.bean.PhoneSmsBean;

/**
 * Created by YASCN on 2018/5/5.
 */

public interface SmsCodeContract {
    public interface View extends BaseNormalContract.View{
        public void setPhoneSms(PhoneSmsBean smsBean);
    }

    public interface Presenter extends BaseNormalContract.Presenter{
        public void getPhoneSms(String phoneNumber,String code);
        public void setPhoneSms(PhoneSmsBean smsBean);
    }

    public interface Model extends BaseNormalContract.Model{
        public void getPhoneSms(SmsCodeContract.Presenter presenter,String phoneNumber,String code);
    }

}
