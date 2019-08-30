package com.yascn.smartpark.utils;

import android.content.Context;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CoordinateConverter;
import com.amap.api.maps.model.LatLng;

/**
 * Created by YASCN on 2017/1/14.
 */

public class GpsTransUtils {


    /**
     * * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换算法 * * 将 BD-09 坐标转换成GCJ-02 坐标 * * @param
     * bd_lat * @param bd_lon * @return
     */
    public static double[] bd09_To_Gcj02(Context context, double lat, double lon) {

        CoordinateConverter converter  = new CoordinateConverter(context);
// CoordType.GPS 待转换坐标类型
        converter.from(CoordinateConverter.CoordType.BAIDU);
// sourceLatLng待转换坐标点 LatLng类型
        converter.coord(new LatLng(lat,lon));
// 执行转换操作
        LatLng desLatLng = converter.convert();

        return new double[]{desLatLng.latitude,desLatLng.longitude};


    }

    public static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
    /**
     * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换算法 将 GCJ-02 坐标转换成 BD-09 坐标
     *
     * @param lat
     * @param lon
     */
    public static double[] gcj02_To_Bd09(double lat, double lon) {
        double x = lon, y = lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
        double tempLon = z * Math.cos(theta) + 0.0065;
        double tempLat = z * Math.sin(theta) + 0.006;
        double[] gps = {tempLat,tempLon};
        return gps;
    }


    public static float mapDistance(Context context,String serverLat,String serverLon,LatLng nowLocation){
        double[] locats = new double[]{StringUtils.emptyParseDouble(serverLat), StringUtils.emptyParseDouble(serverLon)};
//        double[] locats = GpsTransUtils.bd09_To_Gcj02(context, StringUtils.emptyParseDouble(serverLat), StringUtils.emptyParseDouble(serverLon));

        float distance = AMapUtils.calculateLineDistance(nowLocation, new LatLng(locats[0], locats[1]));
        return distance;
    }


}
