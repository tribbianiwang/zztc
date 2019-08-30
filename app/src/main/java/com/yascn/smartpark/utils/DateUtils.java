package com.yascn.smartpark.utils;

import com.yascn.smartpark.bean.ReRecordList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by YASCN on 2017/9/11.
 */

public class DateUtils {



    public static List<String> getMonthList(List<ReRecordList.ObjectBean> objectBeenList) {
        List<String> strings = new ArrayList<String>();
        for (int i = 0; i < objectBeenList.size(); i++) {
            String newmonth = getYearMonth(objectBeenList.get(i).getTIME());
            strings.add(newmonth);
        }
        return strings;
    }

    public static String getDateDelay30(String d) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String result = "";
        try {
            Date date = sdf.parse(d);
            long time = date.getTime() + 30 * 60 * 1000;
            Date dateDelay30 = new Date(time);
            result = sdf.format(dateDelay30);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getMonth(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat msdf = new SimpleDateFormat("MM");
        String month = "";
        try {
            Date date1 = sdf.parse(date);
            month = msdf.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return month;
    }

    public static int getNowYear( ) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
//月
        return year;
    }

    public static int getNowMonth( ) {
        Calendar calendar = Calendar.getInstance();
//月
        int month = calendar.get(Calendar.MONTH)+1;
        return month;
    }

    public static String getDate(String str) {

        Date date;
        Date nowDate = new Date();
        long nowl = nowDate.getTime();
        long l = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat ysdf = new SimpleDateFormat("yyyy");
        SimpleDateFormat msdf = new SimpleDateFormat("MM");
        SimpleDateFormat dsdf = new SimpleDateFormat("dd");
        String nowY = ysdf.format(new Date());
        String nowM = msdf.format(new Date());
        String nowD = dsdf.format(new Date());
        String y = "";
        String m = "";
        String d = "";
        String time = "";
        try {
            date = sdf.parse(str);
            l = date.getTime();
            y = ysdf.format(date);
            m = msdf.format(date);
            d = dsdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (y.equals(nowY)) {
            time = m + "-" + d;

            if (m.equals(nowM) && (d.equals(nowD))) {
                time = "今天";
            }

            if ((nowl - l) > 24 * 60 * 60 * 1000 && (nowl - l) < 24 * 2 * 60 * 60 * 1000) {
                time = "昨天";
            }
        } else {
            time = y + "-" + m + "-" + d;
        }

        return time;
    }

    public static String getTime(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat msdf = new SimpleDateFormat("HH:mm");
        String month = "";
        try {
            Date date1 = sdf.parse(date);
            month = msdf.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return month;
    }

    public static String getYearMonth(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat msdf = new SimpleDateFormat("yyyy-MM");
        String month = "";
        try {
            Date date1 = sdf.parse(date);
            month = msdf.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return month;
    }

    public static String monthToChinese(String time) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat ysdf = new SimpleDateFormat("yyyy");
        SimpleDateFormat msdf = new SimpleDateFormat("MM");
        String nowY = ysdf.format(new Date());
        String nowM = msdf.format(new Date());
        String y = "";
        String m = "";
        String month = "";
        try {
            Date date = sdf.parse(time);
            y = ysdf.format(date);
            m = msdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (y.equals(nowY)) {

            if (nowM.equals(m)) {
                month = "本月";
            } else {
                switch (m) {
                    case "01":
                        month = "一月";
                        break;
                    case "02":
                        month = "二月";
                        break;
                    case "03":
                        month = "三月";
                        break;
                    case "04":
                        month = "四月";
                        break;
                    case "05":
                        month = "五月";
                        break;
                    case "06":
                        month = "六月";
                        break;
                    case "07":
                        month = "七月";
                        break;
                    case "08":
                        month = "八月";
                        break;
                    case "09":
                        month = "九月";
                        break;
                    case "10":
                        month = "十月";
                        break;
                    case "11":
                        month = "十一月";
                        break;
                    case "12":
                        month = "十二月";
                        break;
                }
            }
        } else {
            month = y + "-" + m;
        }

        return month;
    }


    public static String getNowDate() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("MM-dd");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }

    public static String getNowDateYmd() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }

    public static String getNowDateYmdhms() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }

    /**
     * 获取前n天日期、后n天日期
     *
     * @param distanceDay 前几天 如获取前7天日期则传-7即可；如果后7天则传7
     * @return
     */
    public static String getOldDate(int distanceDay) {
        SimpleDateFormat dft = new SimpleDateFormat("MM-dd");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) + distanceDay);
        Date endDate = null;
        try {
            endDate = dft.parse(dft.format(date.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        LogUtil.d("前7天==" + dft.format(endDate));
        return dft.format(endDate);
    }


    public static String getTransZnDate( String date) {
        SimpleDateFormat sdfToDate = new SimpleDateFormat("yyyy-MM-dd");//小写的MM表示的是分钟
        SimpleDateFormat sdfToString = null;
        String resultDate = null;
        Date stringParseDate = null;
        try {
            stringParseDate = sdfToDate.parse(date);
            sdfToString = new SimpleDateFormat("yyyy年MM月dd日");
            resultDate = sdfToString.format(stringParseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }



        return resultDate;

    }

    public static String getTransNormarDate( String date) {
        SimpleDateFormat sdfToDate = new SimpleDateFormat("yyyy年MM月dd日");//小写的MM表示的是分钟
        SimpleDateFormat sdfToString = null;
        String resultDate = null;
        Date stringParseDate = null;
        try {
            stringParseDate = sdfToDate.parse(date);
            sdfToString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            resultDate = sdfToString.format(stringParseDate);
        } catch (ParseException e) {
            e.printStackTrace();

        }



        return resultDate;

    }



    public static String getWeatherDate(String str){

        Date date;
        Date nowDate = new Date();
        long nowl = nowDate.getTime();
        long l = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat ysdf = new SimpleDateFormat("yyyy");
        SimpleDateFormat msdf = new SimpleDateFormat("MM");
        SimpleDateFormat dsdf = new SimpleDateFormat("dd");
        String nowY = ysdf.format(new Date());
        String nowM = msdf.format(new Date());
        String nowD = dsdf.format(new Date());
        String y = "";
        String m = "";
        String d = "";
        String time = "";
        try {
            date = sdf.parse(str);
            l = date.getTime();
            y = ysdf.format(date);
            m = msdf.format(date);
            d = dsdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

//        if (y.equals(nowY)) {
//            time = m + "-" + d;




        if (m.equals(nowM) && (d.equals(nowD))) {
            deleteMonthZero(m);
            time =m+"/"+d+"今日";
        }else{
            deleteMonthZero(m);
            time = m+"/"+d+dateToWeek(str);
        }


        return time;


    }

    public static String deleteMonthZero(String month){
        if("0".equals(month.substring(0,1))){
            month = month.substring(1,month.length());
        }else{
            month = month;
        }
        return month;
    }


    /**
     * 日期转周
     *
     * @param datetime
     * @return
     */
    public static String dateToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个周中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }


    public static boolean isEarlyDate(String date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date bt= null;
        Date et = null;
        try {
            bt = sdf.parse(getNowDateYmd());
            et = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (bt.before(et)){
            //表示bt小于et
            return false;
        }else if(bt.equals(et)){
            return false;
        }
        else{
            return true;
        }


    }

}
