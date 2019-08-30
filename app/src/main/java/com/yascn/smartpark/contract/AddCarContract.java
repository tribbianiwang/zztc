package com.yascn.smartpark.contract;

import android.content.Context;

import com.yascn.smartpark.bean.BandNumber;

/**
 * Created by YASCN on 2018/7/25.
 */

public class AddCarContract {
    public interface AddCarInteractor {
        interface AddCallback {
            void onFinish(BandNumber bandNumber);

            void onAddError();
        }

        void addCar(String userID, String number,AddCarContract.AddCarInteractor.AddCallback addCallback);

        void onDestroy();
    }
    public interface AddCarPresenter {
        void AddCar(String userID, String number);

        void onDestroy();
    }

    public interface AddCarView {
        Context getContext();

        void showDialog();

        void hideDialog();

        String getAddCar();

        void backActivity();

        void setKeyboardAdapter();

        void setSelect(int i);

        void sendRefleshBroadcast();
    }

}
