package com.yascn.smartpark.retrofit;

import android.util.Log;

import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by YASCN on 2017/1/4.
 */

public class GetRetrofitService {
    private static OkHttpClient client;

    public static void init() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印retrofit日志
                LogUtil.d("retrofit", "log: "+message);

            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(AppContants.TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(AppContants.TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(AppContants.TIMEOUT, TimeUnit.SECONDS)
                .build();
    }





    public static RetrofitService getRetrofitService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppContants.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        return retrofitService;
    }


    public static RetrofitService getSecondRetrofitService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppContants.SECONDURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        return retrofitService;
    }

}
