package com.yascn.smartpark.contract;

import com.yascn.smartpark.bean.MonthCardListBean;

public interface MonthCardListContract {
    interface View extends BaseNormalContract.View{
        public void setMonthCardList(MonthCardListBean monthCardListBean);
    }

    interface Presenter extends BaseNormalContract.Presenter{
        public void setMonthCardList(MonthCardListBean monthCardListBean);
        public void getMonthCardList();
    }

    interface Model extends BaseNormalContract.Model{
        public void getMonthCardList(MonthCardListContract.Presenter presenter);

    }
}
