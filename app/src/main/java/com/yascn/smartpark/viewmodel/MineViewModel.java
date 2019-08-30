package com.yascn.smartpark.viewmodel;

import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.Userinfo;
import com.yascn.smartpark.model.MineModel;

public class MineViewModel extends BaseViewModel<Userinfo> implements BaseModel.DataResultListener<Userinfo> {
    private MineModel mineModel;


    public  void getUserInfo(String userid){
        if(mineModel==null){
            mineModel = new MineModel(this);
            baseModel = mineModel;
        }
        mineModel.getUserInfo(userid);

    }

}
