package com.yascn.smartpark.utils;

import android.util.Log;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by YASCN on 2017/9/13.
 */

public class TimeUtils {
    /**
     * 计算剩余时间
     *
     * @param zongshijian 总的有效时间
     * @param yixiaohao   已过去的时间
     * @return 剩余时间
     */
    public static String getTime(long zongshijian, long yixiaohao) {
        long time = zongshijian - yixiaohao;
        int minute = (int) (time / 1000 / 60);
        int second = (int) ((time - minute * 60 * 1000) / 1000);
        return minute + "分" + second + "秒";
    }

    /**
     * 获取时间差
     *
     * @param startTime 开始时间
     * @return 时间差（毫秒）
     * @throws ParseException
     */
    public static long getDiffTime(String startTime, String nowTime) throws ParseException {
        String startT = startTime.replaceAll("/", "-");
        String nowT = nowTime.replaceAll("/", "-");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = sdf.parse(startT);
        Date nowDate = sdf.parse(nowT);
        long diffTime = nowDate.getTime() - startDate.getTime();
        return diffTime;
    }

    private static final String TAG = "TimeUtils";
    public static String toTimeStr(int mintotal){
        LogUtil.d(TAG, "toTimeStr: "+mintotal);
        String result = null;
        int secTotal = mintotal *60;
        int hour = secTotal / 3600;
        int min = ( secTotal%3600 ) / 60;
        int sec = ( secTotal%3600 ) % 60;
        if(hour==0){
            result =to2Str(min)+"分钟";
        }else{
            result = to2Str(hour)+"小时"+to2Str(min)+"分钟";
        }



        return result;

    }

    private static String to2Str(int intNum){
        return intNum+"";

    }
}
