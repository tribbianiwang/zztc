package com.yascn.smartpark.contract;

import com.yascn.smartpark.bean.OrderList;

/**
 * Created by YASCN on 2018/7/25.
 */

public class MyRecordContract
{
    public interface MyRecordInteractor {

        interface GetDataCallback {
            void onGetDataFinish(OrderList orderList);

            void onGetDataError();
        }

        interface RefleshCallback {
            void onRefleshFinish(OrderList orderList);

            void onRefleshError();
        }

        interface AddDataCallback {
            void onAddDataFinish(OrderList orderList);

            void onAddDataError();
        }

        void getData(String userID, String pageNum,MyRecordContract.MyRecordInteractor.GetDataCallback getDataCallback);

        void reflesh(String userID, String pageNum,MyRecordContract.MyRecordInteractor.RefleshCallback refleshCallback);

        void addData(String userID, String pageNum,MyRecordContract.MyRecordInteractor.AddDataCallback addDataCallback);

        void onDestroy();
    }


    public interface MyRecordPresenter {
        void getData(String userID, String pageNum);

        void reflesh(String userID, String pageNum);

        void addData(String userID, String pageNum);

        void onDestroy();
    }


    public interface MyRecordView {
        void showView(int type);

        void setAdatper(OrderList orderList);

        void refreshComplete(boolean success);

        void loadmoreComplete(boolean success);

        void addData(OrderList orderList);

        void nomore(boolean isLastPage);

        void showProgress();
        void hideProgress();
    }


}
