package com.yascn.smartpark.contract;

import android.content.Context;

import com.yascn.smartpark.bean.Comment;
import com.yascn.smartpark.bean.Userinfo;

import java.util.ArrayList;

/**
 * Created by YASCN on 2018/7/21.
 */

public interface MineContract {


    interface View extends BaseNormalContract.View{
        public void setUserInfo(Userinfo userinfo);
    }

    interface Presenter extends BaseNormalContract.Presenter{
        public void setUserInfo(Userinfo userinfo);
        public void getUserInfo(String userid);
    }

    interface Model extends BaseNormalContract.Model{
        public void getUserInfo(MineContract.Presenter presenter,String userid);

    }
}
