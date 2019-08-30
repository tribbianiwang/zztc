package com.yascn.smartpark.presenter;

import com.yascn.smartpark.activity.MonthCardDetailActivity;
import com.yascn.smartpark.bean.AliRecharge;
import com.yascn.smartpark.bean.WxRecharge;
import com.yascn.smartpark.contract.MonthCardPayInfoContract;
import com.yascn.smartpark.model.MonthCardPayInfoModel;

public class MonthCardPayInfoPresenter implements MonthCardPayInfoContract.Presenter {

    MonthCardPayInfoContract.View monthCardPayInfoView;
    MonthCardPayInfoContract.Model model;

    public MonthCardPayInfoPresenter(MonthCardPayInfoContract.View monthCardPayInfoView) {
    this.monthCardPayInfoView = monthCardPayInfoView;
    this.model = new MonthCardPayInfoModel();
    }

    @Override
    public void getAliBuyMonthCardPayInfo(String monthcardId, String userId, String carNumber, String aliFlag, String type) {
        monthCardPayInfoView.showProgress();
        this.model.getAliBuyMonthCardPayInfo(this,monthcardId,userId,carNumber,aliFlag,type);
    }

    @Override
    public void getWeCahtBuyMonthCardPayInfo(String monthcardId, String userId, String carNumber, String wechatFlag, String type) {
        monthCardPayInfoView.showProgress();
        this.model.getWeCahtBuyMonthCardPayInfo(this,monthcardId,userId,carNumber,wechatFlag,type);
    }

    @Override
    public void getAliRefillMonthCardPayInfo(String monthcardId, String monthCardRecordId, String aliFlag, String type) {
            this.model.getAliRefillMonthCardPayInfo(this,monthcardId,monthCardRecordId,aliFlag,type);
    }

    @Override
    public void getWechatRefillMonthCardPayInfo(String monthcardId, String monthCardRecordId, String wechatFlag, String type) {
            this.model.getWechatRefillMonthCardPayInfo(this,monthcardId,monthCardRecordId,wechatFlag,type);
    }

    @Override
    public void setAliPayInfo(AliRecharge aliRecharge) {
        monthCardPayInfoView.hideProgress();
        monthCardPayInfoView.setAliPayInfo(aliRecharge);

    }

    @Override
    public void getAliRechage(String monthcardId, String userId, String carNumber, String startTime, String aliFlag) {
        monthCardPayInfoView.showProgress();
        model.getAliRechage(this,monthcardId,userId,carNumber,startTime,aliFlag);

    }

    @Override
    public void setWxPayInfo(WxRecharge wxRecharge) {
        monthCardPayInfoView.hideProgress();
        monthCardPayInfoView.setWxPayInfo(wxRecharge);

    }

    @Override
    public void getWxPayInfo(String monthcardId, String userId, String carNumber, String startTime, String wxFlag) {
        monthCardPayInfoView.showProgress();
        model.getWxRechage(this,monthcardId,userId,carNumber,startTime,wxFlag);

    }

    @Override
    public void serverError(String errorMsg) {
        monthCardPayInfoView.hideProgress();
        monthCardPayInfoView.serverError(errorMsg);

    }

    @Override
    public void onDestory() {
        model.onDestory();
    }
}
