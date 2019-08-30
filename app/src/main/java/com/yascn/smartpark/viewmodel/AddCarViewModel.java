package com.yascn.smartpark.viewmodel;

import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.BandNumber;
import com.yascn.smartpark.bean.CouponingBean;
import com.yascn.smartpark.model.AddCarModel;
import com.yascn.smartpark.model.CouponingModel;

public class AddCarViewModel extends BaseViewModel<BandNumber> implements BaseModel.DataResultListener<BandNumber> {
    private AddCarModel addCarModel;

    public void addCar(String userID, String number) {
        if(addCarModel==null){
            addCarModel = new AddCarModel(this);
            baseModel = addCarModel;
        }

        addCarModel.addCar(userID, number);
    }

}
