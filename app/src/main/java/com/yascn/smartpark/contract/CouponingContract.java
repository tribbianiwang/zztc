package com.yascn.smartpark.contract;

import android.content.Context;

import com.yascn.smartpark.bean.CouponingBean;

/**
 * Created by YASCN on 2018/5/7.
 */

public interface CouponingContract {
    public interface View extends BaseNormalContract.View{
        public void setCouponing(CouponingBean couponingBean);

    }

    public interface Presenter extends BaseNormalContract.Presenter{
        public void getCouponing(String userId);
        public void setCouponing(CouponingBean couponingBean);

    }

    public interface Model extends BaseNormalContract.Model{
        public void getCouponing(CouponingContract.Presenter presenter, String userId);
    }
}
