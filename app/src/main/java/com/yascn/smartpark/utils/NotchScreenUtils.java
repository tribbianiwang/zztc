package com.yascn.smartpark.utils;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.logging.Logger;

/**
 * Created by YASCN on 2018/8/3.
 */

public class NotchScreenUtils {

    public static boolean isXiaomiNotchScreen(Context context) {
        LogUtil.d("isxiaomi:", "isXiaoMiNotchScreen: " + PropertyUtils.get("ro.miui.notch", "0"));

        //MIUI 10 新增了获取刘海宽和高的方法，需升级至8.6.26开发版及以上版本。
        LogUtil.d("isxiaomi:", "notscreenHeight:" + getXiaomiNotchScreenHeight(context));
        if (PropertyUtils.get("ro.miui.notch", "0").equals("0")) {
            return false;
        } else {
            return true;
        }

    }


    public static int getXiaomiNotchScreenHeight(Context context) {
        int result;
        int resourceId = context.getResources().getIdentifier("notch_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
            return result;
        } else {
            result = 0;
            return result;
        }

    }


    public static boolean hasNotchInHuawei(Context context) {
        boolean hasNotch = false;
        try {
            ClassLoader cl = context.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method hasNotchInScreen = HwNotchSizeUtil.getMethod("hasNotchInScreen");
            if (hasNotchInScreen != null) {
                hasNotch = (boolean) hasNotchInScreen.invoke(HwNotchSizeUtil);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasNotch;
    }

    public static int[] getHuaWeiNotchSize(Context context) {
        int[] ret = new int[]{0, 0};
        try {
            ClassLoader cl = context.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("getNotchSize");
            ret = (int[]) get.invoke(HwNotchSizeUtil);
        } catch (ClassNotFoundException e) {
            Log.e("test", "getNotchSize ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            Log.e("test", "getNotchSize NoSuchMethodException");
        } catch (Exception e) {
            Log.e("test", "getNotchSize Exception");
        } finally {
            return ret;
        }

    }

    public static boolean hasNotchInOppo(Context context) {
        return context.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
    }

    public static int getOppoStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    public static boolean hasNotchInVivo(Context context) {
        boolean hasNotch = false;
        try {
            ClassLoader cl = context.getClassLoader();
            Class ftFeature = cl.loadClass("android.util.FtFeature");
            Method[] methods = ftFeature.getDeclaredMethods();
            if (methods != null) {
                for (int i = 0; i < methods.length; i++) {
                    Method method = methods[i];
                    if (method != null) {
                        if (method.getName().equalsIgnoreCase("isFeatureSupport")) {
                            hasNotch = (boolean) method.invoke(ftFeature, 0x00000020);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            hasNotch = false;
        }
        return hasNotch;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {

        return android.os.Build.BRAND;
    }

//
//    public static boolean  isHuaWeiDevices(){
//
//    }

    private static final String TAG = "NotchScreenUtils";

    public static int getIntchHeight(Context context) {
        int intchHeight = 20;
        switch (getDeviceBrand().toLowerCase()) {
            case "xiaomi":
                if (isXiaomiNotchScreen(context)) {
//                     intchHeight = getXiaomiNotchScreenHeight(context);
//                    intchHeight = (int) DensityUtils.px2dp(context, 89);
                    intchHeight = 32;
                }


                break;
            case "huawei":
                LogUtil.d(TAG, "getIntchHeight: ");
                if (hasNotchInHuawei(context)) {
                    LogUtil.d(TAG, "getIntchHeight: " + getHuaWeiNotchSize(context)[0] + "----" + DensityUtils.px2dp(context, getHuaWeiNotchSize(context)[1]));
                    intchHeight = (int) DensityUtils.px2dp(context, getHuaWeiNotchSize(context)[1]);
//                     intchHeight = 35;
                }


                break;

            case "vivo":
                if (hasNotchInVivo(context)) {
                    intchHeight = 35;
                }

                break;

            case "oppo":
                if (hasNotchInOppo(context)) {
                    intchHeight = getOppoStatusBarHeight(context);
                }


                break;

        }

        return intchHeight;
    }

    public static boolean isXiaomiDevice() {
//        switch (getDeviceBrand().toLowerCase()){
//        case "xiaomi":

        return "xiaomi".equals(getDeviceBrand().toLowerCase());
    }


}


