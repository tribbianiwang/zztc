package com.yascn.smartpark.contract;

import com.yascn.smartpark.bean.YascnParkListBean;

/**
 * Created by YASCN on 2017/9/7.
 */

public class YascnParkIndexContract {


    public interface View extends BaseNormalContract.View{
        public void setYascnParkData(YascnParkListBean yascnParkListBean);
    }

    public interface Presenter extends BaseNormalContract.Presenter{
        public void getYascnParkData();
        public void setYascnParkData(YascnParkListBean yascnParkListBean);
    }

    public interface Model extends BaseNormalContract.Model{
        public void getYascnParkData(YascnParkIndexContract.Presenter presenter);
    }

}