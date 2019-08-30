package com.yascn.smartpark.presenter;


import com.yascn.smartpark.bean.OrderList;
import com.yascn.smartpark.contract.MyRecordContract;
import com.yascn.smartpark.model.MyRecordModel;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;

/**
 * Created by YASCN on 2017/9/14.
 */

public class MyRecordPresenter implements MyRecordContract.MyRecordPresenter, MyRecordContract.MyRecordInteractor.GetDataCallback, MyRecordContract.MyRecordInteractor.RefleshCallback, MyRecordContract.MyRecordInteractor.AddDataCallback {
    private MyRecordContract.MyRecordView myRecordView;
    private MyRecordContract.MyRecordInteractor myRecordInteractor;

    public MyRecordPresenter(MyRecordContract.MyRecordView myRecordView) {
        this.myRecordView = myRecordView;
        myRecordInteractor = new MyRecordModel();
    }

    @Override
    public void getData(String userID, String pageNum) {
        myRecordView.showView(AppContants.PROGRESS);
        myRecordInteractor.getData(userID, pageNum, this);
    }

    @Override
    public void onGetDataFinish(OrderList orderList) {
        LogUtil.d(AppContants.TAG, "orderList = " + orderList.toString());
        if (orderList.getObject().getOrder_list().size() == 0) {
            myRecordView.showView(AppContants.NODATA);
        } else {
            myRecordView.showView(AppContants.SUCCESS);
            myRecordView.setAdatper(orderList);
        }
    }

    @Override
    public void onGetDataError() {
        myRecordView.showView(AppContants.ERROR);
    }

    @Override
    public void reflesh(String userID, String pageNum) {
        myRecordInteractor.reflesh(userID, pageNum, this);
    }

    @Override
    public void onRefleshFinish(OrderList orderList) {
        myRecordView.refreshComplete(true);
        myRecordView.setAdatper(orderList);
    }

    @Override
    public void onRefleshError() {
        myRecordView.refreshComplete(false);
    }

    @Override
    public void addData(String userID, String pageNum) {
        myRecordInteractor.addData(userID, pageNum, this);
    }

    @Override
    public void onAddDataFinish(OrderList orderList) {
        myRecordView.loadmoreComplete(true);
        myRecordView.addData(orderList);
    }

    @Override
    public void onAddDataError() {
        myRecordView.loadmoreComplete(false);
    }

    @Override
    public void onDestroy() {
        myRecordInteractor.onDestroy();
    }
}
