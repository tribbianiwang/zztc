package com.yascn.smartpark.contract;

import com.yascn.smartpark.bean.HomeBean;
import com.yascn.smartpark.bean.Login;
import com.yascn.smartpark.bean.PhoneSmsBean;

/**
 * Created by YASCN on 2018/7/20.
 */

public interface LoginContract {


    public interface View extends BaseNormalContract.View{
        public void setSmsCode(PhoneSmsBean smsCodeBean);
        public void setLoginResult(Login login);
    }

    public interface Presenter extends BaseNormalContract.Presenter{
        public void getSmsCode(String phone,String code);
        public void startLogin(String phone);
        public void setSmsCode(PhoneSmsBean smsCodeBean);
        public void setLoginResult(Login login);
    }

    public interface Model extends BaseNormalContract.Model{
        public void getSmsCode(LoginContract.Presenter presenter,String phone,String code);
        public void startLogin(LoginContract.Presenter presenter,String phone);

    }
}
