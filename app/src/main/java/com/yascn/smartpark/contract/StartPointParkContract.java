package com.yascn.smartpark.contract;

import com.yascn.smartpark.bean.ParkPointBean;

/**
 * Created by YASCN on 2017/9/12.
 */

public class StartPointParkContract {

    public interface View extends BaseNormalContract.View{
        public void setParkPointResult(ParkPointBean parkPointBean);
    }

    public interface Presenter extends BaseNormalContract.Presenter{
        public void startParkPoint( String parkId,String USER_ID,String carLicense);
        public void setParkPointResult(ParkPointBean parkPointBean);
    }

    public interface Model extends BaseNormalContract.Model{
        public void startParkPoint(StartPointParkContract.Presenter presenter, String parkId,String USER_ID,String carLicense);
    }
}