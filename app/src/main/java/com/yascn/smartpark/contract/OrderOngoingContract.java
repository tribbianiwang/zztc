package com.yascn.smartpark.contract;

import com.yascn.smartpark.bean.CarLicense;
import com.yascn.smartpark.bean.Ordering;

/**
 * Created by YASCN on 2018/4/23.
 */

public interface OrderOngoingContract {
    public interface View extends BaseNormalContract.View{
        public void setOrdering(Ordering ordering);

    }

    public interface Presenter extends BaseNormalContract.Presenter{
        public void getOrdering(String userId);
        public void setOrdering(Ordering ordering);


    }

    public interface Model extends BaseNormalContract.Model{
        public void getOrdering(OrderOngoingContract.Presenter presenter,String userId);
    }
}
