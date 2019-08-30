package com.yascn.smartpark.contract;

import com.yascn.smartpark.bean.CarLicense;
import com.yascn.smartpark.bean.RecentParkBean;

/**
 * Created by YASCN on 2017/9/14.
 */

public class RecentParkContract {

    public interface View extends BaseNormalContract.View{
        public void setRecentPark(RecentParkBean recentParkBean);
    }

    public interface Presenter extends BaseNormalContract.Presenter{
        public void getRecentPark(String userId);
        public void setRecentPark(RecentParkBean recentParkBean);
    }

    public interface Model extends BaseNormalContract.Model{
        public void getRecentPark(RecentParkContract.Presenter presenter,String userId);
    }

}