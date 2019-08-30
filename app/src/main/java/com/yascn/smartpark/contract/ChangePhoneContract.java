package com.yascn.smartpark.contract;

import com.yascn.smartpark.bean.ChangePhoneBean;
import com.yascn.smartpark.bean.PhoneSmsBean;

/**
 * Created by YASCN on 2018/5/5.
 */

public interface ChangePhoneContract {
    public interface View extends BaseNormalContract.View {

        public void setChangephoneBean(ChangePhoneBean changePhoneBean);


    }

    public interface Presenter extends BaseNormalContract.Presenter {

        public void getChangePhoneBean(String userId, String phone);

        public void setChangephoneBean(ChangePhoneBean changePhoneBean);


    }

    public interface Model extends BaseNormalContract.Model {
        public void getChangePhoneBean(ChangePhoneContract.Presenter presenter, String userId, String phone);

    }
}
