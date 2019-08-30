package com.yascn.smartpark.viewmodel;

import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.ChangePhoneBean;
import com.yascn.smartpark.model.ChangePhoneModel;

public class ChangePhoneViewModel extends BaseViewModel<ChangePhoneBean>implements BaseModel.DataResultListener<ChangePhoneBean> {
    private ChangePhoneModel changePhoneModel;

    public void getChangePhoneBean(String userId, String phone) {
        if(changePhoneModel ==null){
            changePhoneModel = new ChangePhoneModel(this);
            baseModel = changePhoneModel;
        }

        changePhoneModel.getChangePhoneBean(userId,phone);
    }



}
