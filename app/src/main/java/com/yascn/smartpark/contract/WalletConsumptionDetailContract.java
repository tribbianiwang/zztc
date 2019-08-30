package com.yascn.smartpark.contract;

import com.yascn.smartpark.bean.ReRecordList;

/**
 * Created by YASCN on 2018/7/25.
 */

public class WalletConsumptionDetailContract {
    public interface ConsumptionInteractor {
        interface RechargeCallback {
            void onFinish(ReRecordList reRecordList);

            void onError();
        }

        interface RefleshCallback {
            void onRefleshFinish(ReRecordList reRecordList);

            void onRefleshError();
        }

        interface LoadmoreCallback {
            void onLoadmoreFinish(ReRecordList reRecordList);

            void onLoadmoreError();
        }

        void getRecharge(int pageNumber, String userID, String cate,WalletConsumptionDetailContract.ConsumptionInteractor.RechargeCallback rechargeCallback);

        void reflesh(int pageNumber, String userID, String cate,WalletConsumptionDetailContract.ConsumptionInteractor.RefleshCallback refleshCallback);

        void loadmore(int pageNumber, String userID, String cate,WalletConsumptionDetailContract.ConsumptionInteractor.LoadmoreCallback loadmoreCallback);

        void onDestroy();
    }


    public interface ConsumptionPresenter {
        void getRecharge(int pageNum, String userID, String cate);

        void reflesh(int pageNum, String userID, String cate);

        void loadmore(int pageNum, String userID, String cate);

        void onDestroy();
    }


    public interface ConsumptionView {
        void showView(int type);

        void setAdatper(ReRecordList reRecordList);

        void refreshComplete(boolean success);

        void loadmoreComplete(boolean success);

        void addData(ReRecordList reRecordList);

        void nomore(boolean isLastPage);

        void showProgress();
        void hideProgress();
    }

}
