package com.yascn.smartpark.contract;

import com.yascn.smartpark.bean.CarLicense;
import com.yascn.smartpark.bean.LicenseCertificationResultBean;
import com.yascn.smartpark.bean.SetDefaultNo;

/**
 * Created by YASCN on 2017/12/1.
 */

public interface CarLincenseCertificationContract {
    public interface View extends BaseNormalContract.View{
        public void setCertificationResult(LicenseCertificationResultBean carLicenseResult);
    }

    public interface Presenter extends BaseNormalContract.Presenter{
        public void postCarLicense(String carId,String FilePath);
        public void setCertificationResult(LicenseCertificationResultBean carLicenseResult);

    }

    public interface Model extends BaseNormalContract.Model{
        public void postCarLicense(CarLincenseCertificationContract.Presenter presenter,String carId,String FilePath);
    }
}
