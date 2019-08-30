//
//  Created by  fred on 2016/10/26.
//  Copyright © 2016年 Alibaba. All rights reserved.
//

package com.yascn.smartpark.utils.aliutils;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.alibaba.cloudapi.sdk.BaseApiClientBuildParam;
import com.alibaba.cloudapi.sdk.SdkConstant;

import com.yascn.smartpark.utils.LogUtil;


import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.yascn.smartpark.utils.AppContants.AFTERNOTIFYRESULTBROADCAST;
import static com.yascn.smartpark.utils.AppContants.AFTERNOTIFYRESULTRESULT;


public class AliNotifyDemo {


    private static AsyncApiClient asyncClient = null;
    private static SyncApiClient syncClient = null;

    static{
        BaseApiClientBuildParam param = new BaseApiClientBuildParam();
        param.setAppKey("24672965");
        param.setAppSecret("6a0860093310347693d0844107923a60");

        /**
         * 以HTTPS方式提交请求
         * 本DEMO采取忽略证书的模式,目的是方便大家的调试
         * 为了安全起见,建议采取证书校验方式
         */
        X509TrustManager xtm = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                X509Certificate[] x509Certificates = new X509Certificate[0];
                return x509Certificates;
            }
        };

        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{xtm}, new SecureRandom());

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        }
        HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        param.setSslSocketFactory(sslContext.getSocketFactory());
        param.setX509TrustManager(xtm);
        param.setHostnameVerifier(DO_NOT_VERIFY);


        asyncClient = AsyncApiClient.build(param);
        syncClient = SyncApiClient.build(param);

    }


    private static final String TAG = "AliNotifyDemo";


    public static void asyncTest(String img_base64, final Activity mainActivity){


        asyncClient.startCardNotify(img_base64.getBytes(SdkConstant.CLOUDAPI_ENCODING) , new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
               String result = getResultString(response);

                LogUtil.d(TAG, "onResponse: "+ result);
               ;
                LogUtil.d(TAG, "onResponse: "+ result);
                Intent intent =new Intent();
                intent.setAction(AFTERNOTIFYRESULTBROADCAST);
                intent.putExtra(AFTERNOTIFYRESULTRESULT,result);
                mainActivity.sendBroadcast(intent);


            }
        });


    }

    public static void syncTest(String img_base64){
        asyncClient.startCardNotify(img_base64.getBytes(SdkConstant.CLOUDAPI_ENCODING) , new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String result = e.getMessage();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result= getResultString(response);

            }
        });
    }


    private static String getResultString(Response response) throws IOException {
        StringBuilder result = new StringBuilder();
//        result.append("【服务器返回结果为】").append(SdkConstant.CLOUDAPI_LF).append(SdkConstant.CLOUDAPI_LF);
//        result.append("ResultCode:").append(SdkConstant.CLOUDAPI_LF).append(response.code()).append(SdkConstant.CLOUDAPI_LF).append(SdkConstant.CLOUDAPI_LF);
//        if(response.code() != 200){
//            result.append("错误原因：").append(response.header("X-Ca-Error-Message")).append(SdkConstant.CLOUDAPI_LF).append(SdkConstant.CLOUDAPI_LF);
//        }
//
//        result.append("ResultBody:").append(SdkConstant.CLOUDAPI_LF).append(new String(response.body().bytes() , SdkConstant.CLOUDAPI_ENCODING));
//
        if(response.code()==200){
            result.append(new String(response.body().bytes(), SdkConstant.CLOUDAPI_ENCODING));
        }

        return result.toString();
    }
}
