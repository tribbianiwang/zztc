package com.yascn.smartpark.utils;

/**
 * Created by YASCN on 2017/9/1.
 */

public class DoubleClickUtil {
    private static long lastClickTime;
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if ( time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
