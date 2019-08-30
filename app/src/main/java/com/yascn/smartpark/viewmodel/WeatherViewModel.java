package com.yascn.smartpark.viewmodel;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.WeatherTotalBean;
import com.yascn.smartpark.model.WeatherModel;
import com.yascn.smartpark.utils.LogUtil;

public class WeatherViewModel extends BaseViewModel<WeatherTotalBean> implements BaseModel.DataResultListener<WeatherTotalBean> {


    private WeatherModel weatherModel;


     public void getWeatherData(String city){
        if(weatherModel==null){
            weatherModel = new WeatherModel(this);
            baseModel = weatherModel;
        }
        weatherModel.getLiveWeather(city);
     }




}
