package com.yascn.smartpark.viewmodel;

import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.CarLicense;
import com.yascn.smartpark.model.CarLicenseModelImpl;

public class CarLicenseViewModel extends BaseViewModel<CarLicense> implements BaseModel.DataResultListener<CarLicense> {
    CarLicenseModelImpl carLicenseModel;



    public void getCarLicense(String userId) {
        if(carLicenseModel==null){
            carLicenseModel = new CarLicenseModelImpl(this);
            baseModel = carLicenseModel;
        }
        carLicenseModel.getCarLicense(userId);


    }


}
