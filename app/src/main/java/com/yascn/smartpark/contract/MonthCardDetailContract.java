package com.yascn.smartpark.contract;

import com.yascn.smartpark.bean.MonthCardDetailBean;

public interface MonthCardDetailContract {
    interface View extends BaseNormalContract.View{
        public void setMonthCardDetail(MonthCardDetailBean monthCardDetailBean);
    }

    interface Presenter extends BaseNormalContract.Presenter{
        public void setMonthCardDetail(MonthCardDetailBean monthCardDetailBean);
        public void getMonthCardDetail(String parkId);
    }

    interface Model extends BaseNormalContract.Model{
        public void getMonthCardDetail(MonthCardDetailContract.Presenter presenter,String parId);

    }
}
