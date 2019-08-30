package com.yascn.smartpark.contract;

import android.content.Context;

import com.yascn.smartpark.bean.UpdateBean;

/**
 * Created by YASCN on 2018/1/8.
 */

public interface UpdateContract {
    public interface View extends BaseNormalContract.View{
        public void setUpdateBean(UpdateBean updateBean);
    }

    public interface Presenter extends BaseNormalContract.Presenter{
        public void getUpdateBean();
        public void setUpdateBean(UpdateBean updateBean);
    }

    public interface Model extends BaseNormalContract.Model{
        public void getUpdateBean(Context context, UpdateContract.Presenter presenter);
    }
}
