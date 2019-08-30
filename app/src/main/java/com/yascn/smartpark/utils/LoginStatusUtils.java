package com.yascn.smartpark.utils;

import android.content.Context;
import android.text.TextUtils;

/**
 * Created by YASCN on 2018/5/4.
 */

public class LoginStatusUtils {
    public static boolean isLogin(Context context){
        return TextUtils.isEmpty((CharSequence) SPUtils.get(context,AppContants.KEY_USERID,""))?false:true;

    }

    public static String getUserId(Context context){
        return (String) SPUtils.get(context,AppContants.KEY_USERID,"");
    }

}
