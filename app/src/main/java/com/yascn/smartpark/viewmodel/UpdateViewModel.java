package com.yascn.smartpark.viewmodel;

import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.UpdateBean;
import com.yascn.smartpark.model.UpdateModel;

public class UpdateViewModel extends BaseViewModel<UpdateBean> implements BaseModel.DataResultListener<UpdateBean> {
    private UpdateModel updateModel;

    public void getUpdateBean(){
        if(updateModel==null){
            updateModel = new UpdateModel(this);
            baseModel = updateModel;
        }
        updateModel.getUpdateBean();

    }


}
