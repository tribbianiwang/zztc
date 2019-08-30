package com.yascn.smartpark.contract;

import com.yascn.smartpark.bean.CarLicense;

/**
 * Created by YASCN on 2017/9/11.
 */

public class CarLicenseContract{



    public interface View extends BaseNormalContract.View{
        public void setCarLicense(CarLicense carLicense);
    }

    public interface Presenter extends BaseNormalContract.Presenter{

        public void getCarLicense(String userId);
        public void setCarLicense(CarLicense carLicense);


    }

    public interface Model extends BaseNormalContract.Model{
        public void getCarLicense(CarLicenseContract.Presenter presenter,String userId);

    }
}