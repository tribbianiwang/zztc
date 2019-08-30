package com.yascn.smartpark.utils;

/**
 * Created by YASCN on 2017/1/19.
 */

public class MathUtil {
    /**
     * 将米四舍五入转为千米
     * @param distance
     * @return
     */
    public static long mToK(double distance){
      return (long) (Math.round(distance / 100d) / 10d);

    }

    /**
     * 将米四舍五入
     * @param distance
     * @return
     */
    public static  long roundM(double distance){
        return  Math.round(distance);
    }

}
