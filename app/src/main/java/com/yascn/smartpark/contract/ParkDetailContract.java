package com.yascn.smartpark.contract;

import com.yascn.smartpark.bean.CarLicense;
import com.yascn.smartpark.bean.ParkDetailBean;

/**
 * Created by YASCN on 2017/9/16.
 */

public class ParkDetailContract {


    public interface View extends BaseNormalContract.View{
        public void setParkDetail(ParkDetailBean parkDetail);
    }

    public interface Presenter extends BaseNormalContract.Presenter{
        public void getParkDetail(String parkId);
        public void setParkDetail(ParkDetailBean parkDetail);
    }

    public interface Model extends BaseNormalContract.Model{
        public void getParkDetail(ParkDetailContract.Presenter presenter,String parkId);
    }

}