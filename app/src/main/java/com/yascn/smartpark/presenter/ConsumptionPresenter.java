package com.yascn.smartpark.presenter;


import com.yascn.smartpark.bean.ReRecordList;
import com.yascn.smartpark.contract.WalletConsumptionDetailContract;
import com.yascn.smartpark.model.ConsumptionModel;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;

/**
 * Created by Administrator on 2017/9/10.
 */

public class ConsumptionPresenter implements WalletConsumptionDetailContract.ConsumptionPresenter, WalletConsumptionDetailContract.ConsumptionInteractor.RechargeCallback, WalletConsumptionDetailContract.ConsumptionInteractor.RefleshCallback, WalletConsumptionDetailContract.ConsumptionInteractor.LoadmoreCallback {
    private WalletConsumptionDetailContract.ConsumptionView consumptionView;
    private WalletConsumptionDetailContract.ConsumptionInteractor consumptionInteractor;

    public ConsumptionPresenter(WalletConsumptionDetailContract.ConsumptionView consumptionView) {
        this.consumptionView = consumptionView;
        consumptionInteractor = new ConsumptionModel();
    }

    @Override
    public void getRecharge(int pageNum, String userID, String cate) {
        consumptionView.showView(AppContants.PROGRESS);
        consumptionInteractor.getRecharge(pageNum, userID, cate, this);
    }

    @Override
    public void onFinish(ReRecordList reRecordList) {
        LogUtil.d(AppContants.TAG, "reRecordList = " + reRecordList.toString());
        if (reRecordList.getObject().size() == 0) {
            consumptionView.showView(AppContants.NODATA);
        } else {
            consumptionView.showView(AppContants.SUCCESS);
            consumptionView.setAdatper(reRecordList);
        }
    }

    @Override
    public void onError() {
        consumptionView.showView(AppContants.ERROR);
    }

    @Override
    public void reflesh(int pageNum, String userID, String cate) {
        consumptionInteractor.reflesh(pageNum, userID, cate, this);
    }

    @Override
    public void onRefleshFinish(ReRecordList reRecordList) {
        consumptionView.refreshComplete(true);
        consumptionView.setAdatper(reRecordList);
    }

    @Override
    public void onRefleshError() {
        consumptionView.refreshComplete(false);
    }

    @Override
    public void loadmore(int pageNum, String userID, String cate) {
        consumptionInteractor.loadmore(pageNum, userID, cate, this);
    }

    @Override
    public void onLoadmoreFinish(ReRecordList reRecordList) {
        consumptionView.loadmoreComplete(true);
        consumptionView.addData(reRecordList);
    }

    @Override
    public void onLoadmoreError() {
        consumptionView.loadmoreComplete(false);

    }

    @Override
    public void onDestroy() {
        consumptionInteractor.onDestroy();
    }
}
