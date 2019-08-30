package com.yascn.smartpark.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.amap.api.location.CoordinateConverter;
import com.amap.api.location.DPoint;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;


import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by YASCN on 2017/9/7.
 */

public class MapUtils {

    private static final String TAG = "MapUtils";

    public static String calculateDistance(LatLng starPosition, LatLng endPosition) {


        float distance = AMapUtils.calculateLineDistance(starPosition, endPosition);
        int roundDistance = Math.round(distance);
        LogUtil.d(TAG, "calculateDistance: " + roundDistance);

        if (roundDistance > 1000) {
            return MathUtil.mToK(roundDistance) + "km";
        } else {
            return roundDistance + "m";
        }
    }


    public static String calculateDistanceZn(float floatDiatance) {




        if (floatDiatance > 1000) {
            return MathUtil.mToK(floatDiatance) + "km";
        } else {
            return MathUtil.roundM(floatDiatance) + "m";
        }
    }

    public static int calculateIntDistance(LatLng starPosition, LatLng endPosition) {
        float distance = AMapUtils.calculateLineDistance(starPosition, endPosition);
        int roundDistance = Math.round(distance);
        LogUtil.d(TAG, "calculateDistance: " + roundDistance);
        return roundDistance;
    }


    public static String calculateServerDistance(Context context, String serverLat, String serverLon, LatLng nowLocation){
//        double[] locats = GpsTransUtils.bd09_To_Gcj02(context, StringUtils.emptyParseDouble(serverLat), StringUtils.emptyParseDouble(serverLon));
        double [] locats = new double[]{StringUtils.emptyParseDouble(serverLat), StringUtils.emptyParseDouble(serverLon)};
        return calculateDistance(nowLocation,new LatLng(locats[0], locats[1]));

    }



    public static boolean isAppAvilible(Context context, String packageName){
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if(packageInfos != null){
            for(int i = 0; i < packageInfos.size(); i++){
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    public static void turnToBaiduNavi(Activity activity,double lat,double lon,String end){
        try {
//                          intent = Intent.getIntent("intent://map/direction?origin=latlng:34.264642646862,108.95108518068|name:我家&destination=大雁塔&mode=driving®ion=西安&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
          Intent intent = Intent.getIntent("intent://map/direction?" +
                    //"origin=latlng:"+"34.264642646862,108.95108518068&" +   //起点  此处不传值默认选择当前位置
                    "destination=latlng:"+lat+","+lon+"|name:"+end+        //终点
                    "&mode=driving&" +          //导航路线方式
                    "region=" +           //
                    "&src=#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
            activity.startActivity(intent); //启动调用
        } catch (URISyntaxException e) {
            LogUtil.e("intent", e.getMessage());
        }

    }

    public static void turnToGaodeNavi(Activity activity,double lat,double lon,String end){
        Intent intent;
        try{
            intent = Intent.getIntent("androidamap://navi?sourceApplication=&poiname="+end+"&lat="+lat+"&lon="+lon+"&dev=0");
            activity.startActivity(intent);
        } catch (URISyntaxException e)
        {e.printStackTrace(); }

    }


    public static double[] transLoactionToBaidu(Context context,double lat,double lon) throws Exception {
        CoordinateConverter converter  = new CoordinateConverter(context);
// CoordType.GPS 待转换坐标类型
        converter.from(CoordinateConverter.CoordType.BAIDU);
// sourceLatLng待转换坐标点 LatLng类型
        converter.coord(new DPoint(lat,lon));
// 执行转换操作
        DPoint convert = converter.convert();

        return  new double[]{convert.getLatitude(),convert.getLongitude()};


    }

}
