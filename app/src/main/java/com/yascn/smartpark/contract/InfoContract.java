package com.yascn.smartpark.contract;

import com.yascn.smartpark.bean.EditU;
import com.yascn.smartpark.bean.HomeBean;

/**
 * Created by YASCN on 2018/7/21.
 */

public interface InfoContract {
    public interface View extends BaseNormalContract.View{
        public void setUserinfoResult(EditU editU);
    }

    public interface Presenter extends BaseNormalContract.Presenter{
       public void setUserinfo(String userid, String file, String name, String gender, String birthday, String phone);
        public void setUserinfoResult(EditU editU);
    }

    public interface Model extends BaseNormalContract.Model{
        public void setUserinfo(InfoContract.Presenter presenter,String userid, String file, String name, String gender, String birthday, String phone);
    }
}
