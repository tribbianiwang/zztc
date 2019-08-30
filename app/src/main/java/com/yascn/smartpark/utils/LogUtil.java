package com.yascn.smartpark.utils;

import android.util.Log;

public final class LogUtil {
    private static boolean DEBUG = true;
    public static void v(String tag, String msg){
        logger("v",tag,msg);
    }

    public static void d(String tag, String msg){
        logger("d",tag,msg);
    }

    public static void i(String tag, String msg){
        logger("i",tag,msg);
    }

    public static void w(String tag, String msg){
        logger("w",tag,msg);
    }

    public static void e(String tag, String msg){
        logger("e",tag,msg);
    }

    private static void logger(String priority, String tag, String msg){
        if (!DEBUG){
            return;
        }
        switch (priority){
            case "v":
                Log.v(tag,msg);
                break;
            case "d":
                Log.d(tag,msg);
                break;
            case "i":
                Log.i(tag,msg);
                break;
            case "w":
                Log.w(tag,msg);
                break;
            default:
                Log.e(tag,msg);
        }
    }
}
