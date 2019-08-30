package com.yascn.smartpark.contract;

import com.yascn.smartpark.bean.ReRecordList;

/**
 * Created by YASCN on 2018/7/25.
 */

public class WalletRechargeDetailContract {
    public interface RechargeView {
        void showView(int type);

        void setAdatper(ReRecordList reRecordList);

        void refreshComplete(boolean success);

        void loadmoreComplete(boolean success);

        void addData(ReRecordList reRecordList);

        void nomore(boolean isLastPage);
    }

    public interface RechargePresenter {
        void getRecharge(int pageNum, String userID, String cate);

        void reflesh(int pageNum, String userID, String cate);

        void loadmore(int pageNum, String userID, String cate);

        void onDestroy();
    }

    public interface RechargeInteractor {
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

        void getRecharge(int pageNumber, String userID, String cate,WalletRechargeDetailContract.RechargeInteractor.RechargeCallback rechargeCallback);

        void reflesh(int pageNumber, String userID, String cate,WalletRechargeDetailContract.RechargeInteractor.RefleshCallback refleshCallback);

        void loadmore(int pageNumber, String userID, String cate,WalletRechargeDetailContract.RechargeInteractor.LoadmoreCallback loadmoreCallback);

        void onDestroy();
    }


}
