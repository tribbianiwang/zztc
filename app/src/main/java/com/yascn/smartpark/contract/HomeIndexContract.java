package com.yascn.smartpark.contract;

import com.yascn.smartpark.bean.HomeBean;
import com.yascn.smartpark.bean.OrderList;

/**
 * Created by YASCN on 2018/6/1.
 */

public interface HomeIndexContract {
    public interface View extends BaseNormalContract.View{
        public void setHomeIndex(HomeBean homeBean);
    }

    public interface Presenter extends BaseNormalContract.Presenter{
        public void getHomeIndex(String userId);
        public void setHomeIndex(HomeBean homeBean);
    }

    public interface Model extends BaseNormalContract.Model{
        public void getHomeIndex(HomeIndexContract.Presenter presenter,String userId);
    }
}
