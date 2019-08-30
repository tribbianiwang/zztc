package com.yascn.smartpark.bean;

import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLiveResult;

public class WeatherTotalBean {
    private LocalWeatherLiveResult weatherLiveResult;
    private LocalWeatherForecastResult weatherForecastResult;

    public LocalWeatherLiveResult getWeatherLiveResult() {
        return weatherLiveResult;
    }

    public void setWeatherLiveResult(LocalWeatherLiveResult weatherLiveResult) {
        this.weatherLiveResult = weatherLiveResult;
    }

    public LocalWeatherForecastResult getWeatherForecastResult() {
        return weatherForecastResult;
    }

    public void setWeatherForecastResult(LocalWeatherForecastResult weatherForecastResult) {
        this.weatherForecastResult = weatherForecastResult;
    }



}
