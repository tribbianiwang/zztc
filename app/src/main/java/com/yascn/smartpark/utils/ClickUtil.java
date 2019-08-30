package com.yascn.smartpark.utils;

import android.util.Log;

import static com.yascn.smartpark.utils.AppContants.TOAST_DOUBLE_TIME_LIMIT;

/**
 * Created by Administrator on 2017/8/31.
 */

public class ClickUtil {
    private static final String TAG = "ClickUtil";


    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        LogUtil.d(TAG, "isFastClick: current:"+currentClickTime);
        LogUtil.d(TAG, "isFastClick: lastclick"+lastClickTime);
        LogUtil.d(TAG, "isFastClick: "+(currentClickTime-lastClickTime));
        if ((currentClickTime - lastClickTime) >= TOAST_DOUBLE_TIME_LIMIT) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        LogUtil.d(TAG, "isFastClick:flag:"+flag);

        return flag;
    }





}
