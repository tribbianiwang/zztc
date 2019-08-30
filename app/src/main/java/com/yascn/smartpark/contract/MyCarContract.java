package com.yascn.smartpark.contract;

import android.content.Context;

import com.yascn.smartpark.bean.DelNumber;
import com.yascn.smartpark.bean.NumberList;
import com.yascn.smartpark.bean.SetAutoPay;
import com.yascn.smartpark.bean.SetDefaultNo;

import java.util.List;

/**
 * Created by YASCN on 2018/7/25.
 */

public class MyCarContract {
    public interface MyCarInteractor {

        interface DeleteCallback {
            void onFinish(DelNumber delNumber);

            void onDeleteError();
        }

        interface DefaultCallback {
            void onFinish(SetDefaultNo setDefaultNo);

            void onDefaultError();
        }

        interface PayCallback {
            void onFinish(String numberID,SetAutoPay setAutoPay);

            void onPayError();
        }

        interface NumberlistCallback {
            void onFinish(NumberList numberList);

            void onNumberlistError();
        }

        void numberList(String userID, MyCarContract.MyCarInteractor.NumberlistCallback numberlistCallback);

        void deleteCar(String numberID, MyCarContract.MyCarInteractor.DeleteCallback deleteCallback);

        void setDefault(String numberID, String userID, MyCarContract.MyCarInteractor.DefaultCallback defaultCallback);

        void setPay(String numberID, String cate, MyCarContract.MyCarInteractor.PayCallback payCallback);

        void onDestroy();
    }

    public interface MyCarPresenter {

        void numberList(String userID);

        void deleteCar(String numberID);

        void setDefault(String numberID, String userID);

        void setPay(String numberID, String cate);

        void onDestroy();
    }

    public interface MyCarView {
        void showView(int type);

        Context getContext();

        void showDialog();

        void hideDialog();

        void setCarAdapterForLicenseListBean(List<SetDefaultNo.ObjectBean.LicenseListBean> numberList);

        void setCarAdapterForNumberList(NumberList numberList);

        void notifyData();

        void setDefaultNotify();

        void setAutoNotify();

        void removeItem();
    }
}
