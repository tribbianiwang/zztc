package com.yascn.smartpark.utils;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;



/**
 * Created by YASCN on 2017/3/24.
 */

public class TimeService extends Service {

    private int number;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == AppContants.SMS_COUNTDOWN) {

                if (number == 0) {
                    LogUtil.d(AppContants.TAG, "销毁服务");
                    stopSelf();
                    return;
                }

                number--;
                SPUtils.put(TimeService.this, AppContants.KEY_NUMBER, number);
                LogUtil.d(AppContants.TAG, "存储number = " + number);
                mHandler.sendEmptyMessageDelayed(AppContants.SMS_COUNTDOWN, 1000);
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.i(AppContants.TAG, "服务启动成功");
        if (intent != null) {
            number = intent.getIntExtra("number", 0);
            LogUtil.d(AppContants.TAG, "服务得到number = " + number);
            mHandler.sendEmptyMessage(AppContants.SMS_COUNTDOWN);
        }

        return super.onStartCommand(intent, Service.START_REDELIVER_INTENT, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d(AppContants.TAG, "服务destroy");
        mHandler.removeMessages(AppContants.SMS_COUNTDOWN);
        mHandler = null;
    }
}
