package com.yascn.smartpark.utils;

import android.app.Activity;
import android.util.Log;


import com.yascn.smartpark.activity.MainActivity;
import com.yascn.smartpark.activity.SplashActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YASCN on 2017/2/21.
 */

public class ActivityUtil {
    public static List<Activity>  activities = new ArrayList<>();

    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);

    }

    public static void finishMainActivity(){
        for(Activity activity:activities){
            if(activity instanceof MainActivity &&!activity.isFinishing()){
                activity.finish();
            }
        }
    }

    private static final String TAG = "ActivityUtil";
    public static void finishSplash(){
        LogUtil.d(TAG, "finishSplash: ");
        for(Activity activity:activities){
            if(activity instanceof SplashActivity&&!activity.isFinishing()){
                LogUtil.d(TAG, "finishSplash: true");
                activity.finish();
            }
        }
    }




    public static void finishAll(){
        for(Activity activity:activities){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }

    }

}
