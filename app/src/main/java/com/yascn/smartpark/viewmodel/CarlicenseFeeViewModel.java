package com.yascn.smartpark.viewmodel;

import android.provider.ContactsContract;

import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.QueryCarLicenseFeeBean;
import com.yascn.smartpark.model.CarLicenseFeeModel;
import com.yascn.smartpark.utils.LoginStatusUtils;

public class CarlicenseFeeViewModel extends BaseViewModel<QueryCarLicenseFeeBean> implements BaseModel.DataResultListener<QueryCarLicenseFeeBean> {

    private CarLicenseFeeModel carLicenseFeeModel;

    public void getLicenseFee(String carLicense,String userId) {
        if(carLicenseFeeModel==null){
            carLicenseFeeModel = new CarLicenseFeeModel(this);
            baseModel = carLicenseFeeModel;

        }
//        carLicenseFeeModel.getLicenseFee(carLicense, LoginStatusUtils.getUserId(view.getContext()));
        carLicenseFeeModel.getLicenseFee(carLicense,userId);


    }



}
