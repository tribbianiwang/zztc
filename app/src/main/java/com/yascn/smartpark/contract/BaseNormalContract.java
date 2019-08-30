package com.yascn.smartpark.contract;

import android.content.Context;

import com.yascn.smartpark.bean.CarLicense;

/**
 * Created by YASCN on 2018/7/16.
 */

public interface BaseNormalContract {

    public interface View{
        public void serverError(String errorMsg);
        public void showProgress();
        public void hideProgress();
        public Context getContext();
    }

    public interface Presenter{
        public void serverError(String errorMsg);
        public void onDestory();

    }

    public interface Model{
        public void onDestory();
    }
}
