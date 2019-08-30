package com.yascn.smartpark.viewmodel;

import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.ParkPointBean;
import com.yascn.smartpark.model.StartPointParkModelImpl;

public class StartPointViewModel extends BaseViewModel<ParkPointBean> implements BaseModel.DataResultListener<ParkPointBean> {
    private StartPointParkModelImpl startPointParkModel;

    public void startParkPoint(String parkId, String USER_ID, String carLicense) {
        if(startPointParkModel==null){
            startPointParkModel = new StartPointParkModelImpl(this);
            baseModel = startPointParkModel;
        }

        startPointParkModel.startParkPoint(parkId,USER_ID,carLicense);

    }


}
