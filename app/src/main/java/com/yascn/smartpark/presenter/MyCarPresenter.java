package com.yascn.smartpark.presenter;

import android.content.Intent;


import com.yascn.smartpark.bean.DelNumber;
import com.yascn.smartpark.bean.NumberList;
import com.yascn.smartpark.bean.SetAutoPay;
import com.yascn.smartpark.bean.SetDefaultNo;
import com.yascn.smartpark.contract.MyCarContract;
import com.yascn.smartpark.model.MyCarModel;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.T;

import static com.yascn.smartpark.utils.AppContants.AUTOPAYDATAUPDATEBROADCAST;
import static com.yascn.smartpark.utils.AppContants.AUTOPAYPARKID;
import static com.yascn.smartpark.utils.AppContants.AUTOPAYPARKSTATE;

/**
 * Created by YASCN on 2017/9/9.
 */

public class MyCarPresenter implements MyCarContract.MyCarPresenter, MyCarContract.MyCarInteractor.NumberlistCallback, MyCarContract.MyCarInteractor.DefaultCallback, MyCarContract.MyCarInteractor.DeleteCallback, MyCarContract.MyCarInteractor.PayCallback {
    private MyCarContract.MyCarView myCarView;
    private MyCarContract.MyCarInteractor myCarInteractor;

    public MyCarPresenter(MyCarContract.MyCarView myCarView) {
        this.myCarView = myCarView;
        myCarInteractor = new MyCarModel();
    }

    // 获取车牌列表
    @Override
    public void numberList(String userID) {
        myCarView.showView(AppContants.PROGRESS);
        myCarInteractor.numberList(userID, this);
    }

    @Override
    public void onFinish(NumberList numberList) {
        LogUtil.d(AppContants.TAG, "numberList = " + numberList);
        myCarView.showView(AppContants.SUCCESS);
        myCarView.setCarAdapterForNumberList(numberList);
    }

    @Override
    public void onNumberlistError() {
        myCarView.showView(AppContants.ERROR);
    }

    // 删除车牌
    @Override
    public void deleteCar(String numberID) {
        myCarView.showDialog();
        myCarInteractor.deleteCar(numberID, this);
    }

    @Override
    public void onFinish(DelNumber delNumber) {
//        1：成功
        myCarView.hideDialog();
        if (delNumber.getObject().getFlag().equals("1")) {
            T.showShort(myCarView.getContext(), "删除成功");
            myCarView.removeItem();
        } else if(delNumber.getObject().getFlag().equals("2")){
            T.showShort(myCarView.getContext(), "此车辆正在停车中");
        }else{
            T.showShort(myCarView.getContext(), "删除失败");
        }
    }

    @Override
    public void onDeleteError() {
        myCarView.hideDialog();
        T.showShort(myCarView.getContext(), "删除失败");
    }

    // 设置默认车牌
    @Override
    public void setDefault(String numberID, String userID) {
        LogUtil.d(AppContants.TAG, "numberID = " + numberID);
        LogUtil.d(AppContants.TAG, "userID = " + userID);
        myCarView.showDialog();
        myCarInteractor.setDefault(numberID, userID, this);
    }

    @Override
    public void onFinish(SetDefaultNo setDefaultNo) {
        LogUtil.d(AppContants.TAG, "setDefaultNo = " + setDefaultNo.toString());
        myCarView.hideDialog();
        if (setDefaultNo.getObject().getFlag().equals("1")) {
//            T.showShort(myCarView.getContext(), "设置成功");
            myCarView.setCarAdapterForLicenseListBean(setDefaultNo.getObject().getLicense_list());
        } else {
            T.showShort(myCarView.getContext(), "设置失败");
            myCarView.setDefaultNotify();
        }
    }

    @Override
    public void onDefaultError() {
        myCarView.hideDialog();
        T.showShort(myCarView.getContext(), "设置失败");
        myCarView.setDefaultNotify();
    }

    // 设置自动支付
    @Override
    public void setPay(String numberID, String cate) {
        myCarView.showDialog();
        myCarInteractor.setPay(numberID, cate, this);
    }

    @Override
    public void onFinish(String numberId,SetAutoPay setAutoPay) {
        myCarView.hideDialog();
        Intent intent;
        if (setAutoPay.getObject().getFlag().equals("0")) {
            T.showShort(myCarView.getContext(), "设置失败");
            myCarView.setAutoNotify();

        } else if(setAutoPay.getObject().getFlag().equals("1")){
            intent = new Intent();
            intent.setAction(AUTOPAYDATAUPDATEBROADCAST);
            intent.putExtra(AUTOPAYPARKID,numberId);
            intent.putExtra(AUTOPAYPARKSTATE,"1");
            myCarView.getContext().sendBroadcast(intent);
            T.showShort(myCarView.getContext(), "开通成功");

        }else if(setAutoPay.getObject().getFlag().equals("2")){

            T.showShort(myCarView.getContext(), "此车牌已开通免密支付");
            myCarView.setAutoNotify();


        }else if(setAutoPay.getObject().getFlag().equals("3")){
            intent = new Intent();
            intent.setAction(AUTOPAYDATAUPDATEBROADCAST);
            intent.putExtra(AUTOPAYPARKID,numberId);
            intent.putExtra(AUTOPAYPARKSTATE,"0");
            myCarView.getContext().sendBroadcast(intent);
            T.showShort(myCarView.getContext(), "取消成功");

        }
    }

    @Override
    public void onPayError() {
        myCarView.hideDialog();
        T.showShort(myCarView.getContext(), "设置失败");
        myCarView.setAutoNotify();
    }

    @Override
    public void onDestroy() {
        myCarInteractor.onDestroy();
    }
}
