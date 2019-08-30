package com.yascn.smartpark.contract;

import com.yascn.smartpark.bean.AliRecharge;
import com.yascn.smartpark.bean.Userinfo;
import com.yascn.smartpark.bean.WxRecharge;

/**
 * Created by YASCN on 2018/7/24.
 */

public interface WalletContract {
    public interface View extends BaseNormalContract.View {
        void setAliRecharge(AliRecharge aliRecharge);

        void setWxRecharge(WxRecharge wxRecharge);

        void setUserInfo(Userinfo userinfo);

    }

    public interface Presenter extends BaseNormalContract.Presenter {

        void setAliRecharge(AliRecharge aliRecharge);

        void setWxRecharge(WxRecharge wxRecharge);

        void setUserInfo(Userinfo userinfo);

        void startAliRecharge(String money, String userID);

        void startWxRecharge(String money, String userID);

        void getUserInfo(String userId);


    }

    public interface Model extends BaseNormalContract.Model {
        void startAliRecharge(WalletContract.Presenter presenter,String money, String userID);

        void startWxRecharge(WalletContract.Presenter presenter, String money, String userID);

        void getUserInfo(WalletContract.Presenter presenter,String userId);

    }
}
