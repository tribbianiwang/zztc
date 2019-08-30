package com.yascn.smartpark.model;

import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;

import com.yascn.smartpark.viewmodel.WeatherViewModel;
import com.yascn.smartpark.base.AndroidApplication;
import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.WeatherTotalBean;
import com.yascn.smartpark.utils.LogUtil;

import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSFIILED;
import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSLOADING;
import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSSUCCESS;

/**
 * Created by YASCN on 2018/8/2.
 */

public class WeatherModel extends BaseModel implements WeatherSearch.OnWeatherSearchListener {

    private static final String TAG = "WeatherModel";
    private  WeatherTotalBean weatherTotalBean;
    private String transWeatherCIty;


    public WeatherModel(WeatherViewModel weatherViewModel) {
        dataResultListener = weatherViewModel;
        weatherTotalBean = new WeatherTotalBean();
    }

    @Override
    public void onDestory() {


    }

    public void getLiveWeather(String weatherCity) {
        LogUtil.d(TAG, "getLiveWeather: "+weatherCity);
        dataResultListener.setQueryStatus(QUERYSTATUSLOADING);
        transWeatherCIty = weatherCity;

        //检索参数为城市和天气类型，实况天气为WEATHER_TYPE_LIVE、天气预报为WEATHER_TYPE_FORECAST
        WeatherSearchQuery liveQuery = new WeatherSearchQuery(weatherCity, WeatherSearchQuery.WEATHER_TYPE_LIVE);
        WeatherSearch lifeSearch = new WeatherSearch(AndroidApplication.getInstances().getApplicationContext());
        lifeSearch.setOnWeatherSearchListener(this);
        lifeSearch.setQuery(liveQuery);
        lifeSearch.searchWeatherAsyn(); //异步搜索
    }


    public void getForecastWeather() {
        WeatherSearchQuery forecastquery = new WeatherSearchQuery(transWeatherCIty, WeatherSearchQuery.WEATHER_TYPE_FORECAST);
        WeatherSearch forecastSearch = new WeatherSearch(AndroidApplication.getInstances().getApplicationContext());
        forecastSearch.setOnWeatherSearchListener(this);
        forecastSearch.setQuery(forecastquery);
        forecastSearch.searchWeatherAsyn(); //异步搜索
    }

    @Override
    public void onWeatherLiveSearched(LocalWeatherLiveResult weatherLiveResult, int i) {
        if (i == 1000) {
            if (weatherLiveResult != null && weatherLiveResult.getLiveResult() != null) {
                weatherTotalBean.setWeatherLiveResult(weatherLiveResult);
                getForecastWeather();
                LogUtil.d(TAG, "onWeatherLiveSearched: ");

            } else {
                dataResultListener.setQueryStatus(QUERYSTATUSFIILED);
                ;
            }
        } else {
            dataResultListener.setQueryStatus(QUERYSTATUSFIILED);
            ;
        }
    }

    @Override
    public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int i) {
        if (i == 1000) {
            if (localWeatherForecastResult != null && localWeatherForecastResult.getForecastResult() != null) {
                LogUtil.d(TAG, "onWeatherForecastSearched: ");
                weatherTotalBean.setWeatherForecastResult(localWeatherForecastResult);
                dataResultListener.dataSuccess(weatherTotalBean);
                dataResultListener.setQueryStatus(QUERYSTATUSSUCCESS);

            } else {

                dataResultListener.setQueryStatus(QUERYSTATUSFIILED);
            }
        } else {
            dataResultListener.setQueryStatus(QUERYSTATUSFIILED);
        }
    }


}
