package com.yascn.smartpark.viewmodel;

import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.EditU;
import com.yascn.smartpark.model.InfoModel;

public class InfoViewModel extends BaseViewModel<EditU> implements BaseModel.DataResultListener<EditU> {
    private InfoModel infoModel;

    public void setUserinfo(String userid, String file, String name, String gender, String birthday, String phone) {
        if(infoModel==null){
            infoModel = new InfoModel(this);
            baseModel = infoModel;
        }
        infoModel.setUserinfo(userid,file,name,gender,birthday,phone);
    }
}
