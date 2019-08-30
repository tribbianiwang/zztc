package com.yascn.smartpark.contract;

import android.content.Context;

import com.yascn.smartpark.bean.CarLicense;
import com.yascn.smartpark.bean.QueryCarLicenseFeeBean;

/**
 * Created by YASCN on 2018/5/5.
 */

public interface CarLicenseFeeContract {

    public interface View extends BaseNormalContract.View{
        public void setLicenseFee(QueryCarLicenseFeeBean carLicense);
    }

    public interface Presenter extends BaseNormalContract.Presenter{

        public void getLicenseFee(String carLicense);
        public void setLicenseFee(QueryCarLicenseFeeBean carLicense);


    }

    public interface Model extends BaseNormalContract.Model{
        public void getLicenseFee(CarLicenseFeeContract.Presenter presenter,String carLicense,String userId);

    }
}
