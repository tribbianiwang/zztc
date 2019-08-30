package com.yascn.smartpark.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.yascn.smartpark.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Decoder.BASE64Encoder;

import static android.os.Environment.getExternalStorageDirectory;
import static com.yascn.smartpark.utils.AppContants.EMPTYPARKNUM;
import static com.yascn.smartpark.utils.AppContants.ENOUGHPARKNUM;
import static com.yascn.smartpark.utils.AppContants.MAXPARKNUMLINE;
import static com.yascn.smartpark.utils.AppContants.REFLESHORDER;

/**
 * Created by YASCN on 2017/7/17.
 */

public class StringUtils {

    public static String getRString(Context context,int id){
        return context.getResources().getString(id);
    }

    public static int getRColor(Context context,int id){
        return context.getResources().getColor(id);
    }


    public static String getStorageFileName(Context context,boolean isImg){

            SimpleDateFormat simpleDateFromat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
            String data = simpleDateFromat.format(new Date());
        if(isImg){
            return "YASCN" + data+ ".jpg";
        }else{
            return "YASCN" + data+ ".mp4";
        }

    }


    public static File getStoragePathFile(boolean isImg){
        String fileDirName;
        if(isImg){
            fileDirName = "监控截屏";
        }else{
            fileDirName = "监控录像";
        }
        File appDir = new File(getExternalStorageDirectory(), fileDirName);
        return appDir;

    }

    public static boolean isPhoneString(String userName) {
        Pattern p = Pattern.compile("[1][34578]\\d{9}");
        Matcher m = p.matcher(userName);
        return m.matches();

    }


    public static String isEmpty(String string){
        if(TextUtils.isEmpty(string)){
            return "null";
        }else{
            return string;
        }
    }



    public static String getHiddenPhone(String userName) {
        if (!isPhoneString(userName)) {
            return userName;
        } else {
            if (!TextUtils.isEmpty(userName) && userName.length() > 6) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < userName.length(); i++) {
                    char c = userName.charAt(i);
                    if (i >= 3 && i <= 6) {
                        sb.append('*');
                    } else {
                        sb.append(c);
                    }
                }

                return sb.toString();
            } else {
                return userName;
            }
        }
    }


    public static Double emptyParseDouble(String number){
        try {
            return Double.parseDouble(number);
        }catch (Exception e){
            return 0.0;
        }

    }

    public static Long emptyParseLong(String number){
        try {
            return Long.parseLong(number);
        }catch (Exception e){
            return Long.valueOf(0);
        }

    }


    public static int emptyParseInt(String number){
        try {
            return Integer.parseInt(number);
        }catch (Exception e){
           return 0;
        }

    }


    public static void setParkFreeNum(TextView tvParkNum,String parkNum){
        if(StringUtils.emptyParseInt(parkNum)<0||StringUtils.emptyParseInt(parkNum)==0){
            tvParkNum.setText(EMPTYPARKNUM);
        }else if(StringUtils.emptyParseInt(parkNum)>MAXPARKNUMLINE){
            tvParkNum.setText(ENOUGHPARKNUM);
            SpannableStringBuilder style = new SpannableStringBuilder("目前剩余:"+ENOUGHPARKNUM+"个车位");
            style.setSpan(new ForegroundColorSpan(Color.GRAY), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            style.setSpan(new ForegroundColorSpan(Color.GRAY), 5, ENOUGHPARKNUM.length()+5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            style.setSpan(new ForegroundColorSpan(Color.GRAY), ENOUGHPARKNUM.length()+5, ENOUGHPARKNUM.length()+8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvParkNum.setText(style);


        }else{

            SpannableStringBuilder style = new SpannableStringBuilder("目前剩余:"+parkNum+"个车位");
            style.setSpan(new ForegroundColorSpan(Color.GRAY), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            style.setSpan(new ForegroundColorSpan(Color.GRAY), 5, parkNum.length()+5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            style.setSpan(new ForegroundColorSpan(Color.GRAY), parkNum.length()+5, parkNum.length()+8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvParkNum.setText(style);
        }
    }

    public static void setEmptyViewBgAndImg(Context context, boolean isGRay, LinearLayout llNodata, ImageView ivEmptyView){
        if(isGRay){
            llNodata.setBackgroundColor(StringUtils.getRColor(context, R.color.design_main_black));
            ivEmptyView.setImageResource(R.drawable.icon_empty_gray);
        }else{
            llNodata.setBackgroundColor(StringUtils.getRColor(context,R.color.white));
            ivEmptyView.setImageResource(R.drawable.icon_empty_blue);
        }

    }

    /**
     * 根据应用包名，跳转到应用市场
     *
     * @param activity    承载跳转的Activity
     * @param packageName 所需下载（评论）的应用包名
     */
    public static void shareAppShop(Activity activity, String packageName) {
        try {
            Uri uri = Uri.parse("market://details?id="+ packageName);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(activity, "您没有安装应用市场", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;

            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            LogUtil.e("VersionInfo", "Exception"+ e);
        }
        return versionName;
    }

    private static final String TAG = "StringUtils";
    /**
     * 利用正则表达式判断字符串是否是数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        if(TextUtils.isEmpty(str)){
            return false;
        }
        if(str.length()>1){
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    public static String getSecretPhoneNum(String pNumber){
        if(!TextUtils.isEmpty(pNumber) && pNumber.length() > 6 ){
            StringBuilder sb  =new StringBuilder();
            for (int i = 0; i < pNumber.length(); i++) {
                char c = pNumber.charAt(i);
                if (i >= 3 && i <= 6) {
                    sb.append('*');
                } else {
                    sb.append(c);
                }
            }

          return sb.toString();
        }else{
            return pNumber;
        }
    }


    public static String getAliCertificationReqBody(String filePath){
        LogUtil.d(TAG, "onClick: "+filePath);
        String img_file =filePath;
        // 对图像进行base64编码
        String img_base64 = "";
        try {
            File file = new File(img_file);
            byte[] content = new byte[(int) file.length()];
            FileInputStream finputstream = new FileInputStream(file);
            finputstream.read(content);
            finputstream.close();
            img_base64 = new BASE64Encoder().encode(content);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        // 拼装请求body的json字符串
        JSONObject request_obj = new JSONObject();
        try {
            JSONObject obj = new JSONObject();
            JSONArray input_array = new JSONArray();
            obj.put("image", StringUtils.getParam(50, img_base64));
            input_array.put(obj);
            request_obj.put("inputs", input_array);
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
        String body = request_obj.toString();

        return body;

    }

    /*
* 获取参数的json对象
*/
    public static JSONObject getParam(int type, String dataValue) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("dataType", type);
            obj.put("dataValue", dataValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static int getLocalVersion(Context ctx) {
        int localVersion = 0;
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    /**
     * 产生随机的六位数
     * @return
     */
    public static String getRandomCode(){

        String str="0123456789";
        StringBuilder sb=new StringBuilder(4);
        for(int i=0;i<4;i++)
        {
            char ch=str.charAt(new Random().nextInt(str.length()));
            sb.append(ch);
        }
        LogUtil.d(TAG, "getRandomCode: "+sb.toString());


        return sb.toString();

    }
    private static long tmpID = 0;

    private static boolean tmpIDlocked = false;

    public static long getId()
    {
        long ltime = 0;
        while (true)
        {
            if(tmpIDlocked == false)
            {
                tmpIDlocked = true;
                //当前：（年、月、日、时、分、秒、毫秒）*10000
                ltime = Long.valueOf(new SimpleDateFormat("yyMMddhhmmssSSS").format(new Date()).toString()) * 10000;
                if(tmpID < ltime)
                {
                    tmpID = ltime;
                }
                else
                {
                    tmpID = tmpID + 1;
                    ltime = tmpID;
                }
                tmpIDlocked = false;
                return ltime;
            }
        }
    }


    public static void sendRefreshOrderReceiver(Context context){
        Intent intent = new Intent();
        intent.setAction(REFLESHORDER);
        context.sendBroadcast(intent);
    }

    private static String getUserId(Context context){
        return (String) SPUtils.get(context, AppContants.KEY_USERID, "");
    }
}
