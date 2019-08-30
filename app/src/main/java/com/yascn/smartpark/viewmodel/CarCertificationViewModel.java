package com.yascn.smartpark.viewmodel;

import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.LicenseCertificationResultBean;
import com.yascn.smartpark.model.CarLincenseCertificationModel;

public class CarCertificationViewModel extends BaseViewModel<LicenseCertificationResultBean> implements BaseModel.DataResultListener<LicenseCertificationResultBean> {
    private CarLincenseCertificationModel carLincenseCertificationModel;


    public void postCarLicense(String carId, String filepath) {
        if(carLincenseCertificationModel==null){
            carLincenseCertificationModel = new CarLincenseCertificationModel(this);
            baseModel = carLincenseCertificationModel;
        }
        carLincenseCertificationModel.postCarLicense(carId,filepath);
    }


}
