package com.yascn.smartpark.contract;

import com.yascn.smartpark.bean.AliRecharge;
import com.yascn.smartpark.bean.WxRecharge;

import retrofit2.http.Query;
import rx.Observable;

public interface MonthCardPayInfoContract {

    interface View extends BaseNormalContract.View{
        public void setAliPayInfo(AliRecharge aliRecharge);
        public void setWxPayInfo(WxRecharge wxRecharge);
    }

    interface Presenter extends BaseNormalContract.Presenter{

        public void  getAliBuyMonthCardPayInfo(String monthcardId,String userId,String carNumber,String aliFlag,String type);
        public void  getWeCahtBuyMonthCardPayInfo(String monthcardId,String userId,String carNumber,String wechatFlag,String type);

        public void getAliRefillMonthCardPayInfo(String monthcardId,String monthCardRecordId,String aliFlag,String type);
        public void getWechatRefillMonthCardPayInfo(String monthcardId,String monthCardRecordId,String wechatFlag,String type);



        public void setAliPayInfo(AliRecharge aliRecharge);
        public void setWxPayInfo(WxRecharge wxRecharge);

        public void getAliRechage(String monthcardId,String userId,String carNumber,String startTime,String aliFlag);
        public void getWxPayInfo(String monthcardId,String userId,String carNumber,String startTime,String wxFlag);

    }

    interface Model extends BaseNormalContract.Model{
        public void getAliRechage(MonthCardPayInfoContract.Presenter presenter,String monthcardId,String userId,String carNumber,String startTime,String aliFlag);
        public void getWxRechage(MonthCardPayInfoContract.Presenter presenter,String monthcardId,String userId,String carNumber,String startTime,String wxFlag);

        public void  getAliBuyMonthCardPayInfo(MonthCardPayInfoContract.Presenter presenter,String monthcardId,String userId,String carNumber,String aliFlag,String type);
        public void  getWeCahtBuyMonthCardPayInfo(MonthCardPayInfoContract.Presenter presenter,String monthcardId,String userId,String carNumber,String wechatFlag,String type);

        public void getAliRefillMonthCardPayInfo(MonthCardPayInfoContract.Presenter presenter,String monthcardId,String monthCardRecordId,String aliFlag,String type);
        public void getWechatRefillMonthCardPayInfo(MonthCardPayInfoContract.Presenter presenter,String monthcardId,String monthCardRecordId,String wechatFlag,String type);

    }
}
