package com.yascn.smartpark.contract;

import com.yascn.smartpark.bean.ParkComment;

/**
 * Created by YASCN on 2017/9/9.
 */

public class ParkCommentContract {


    public interface View extends BaseNormalContract.View{
        public void setparkCommentData(ParkComment parkComment);
    }

    public interface Presenter extends BaseNormalContract.Presenter{
        public void getParCommentkData(String parkId,int pageNumber);
        public void setparkCommentData(ParkComment parkComment);
        public void reloadData();
        void queryList();
    }

    public interface Model extends BaseNormalContract.Model {
        public void getParCommentkData(ParkCommentContract.Presenter presenter,String parkId,int pageNumber);
    }

}