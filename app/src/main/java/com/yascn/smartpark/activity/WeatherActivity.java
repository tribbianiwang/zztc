package com.yascn.smartpark.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.github.promeg.pinyinhelper.Pinyin;
import com.gyf.barlibrary.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yascn.smartpark.R;
import com.yascn.smartpark.viewmodel.WeatherViewModel;
import com.yascn.smartpark.adapter.RvWeatherAdapter;
import com.yascn.smartpark.base.BaseActivity;
import com.yascn.smartpark.bean.WeatherTotalBean;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.DensityUtils;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.NotchScreenUtils;
import com.yascn.smartpark.utils.T;
import com.yascn.smartpark.utils.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSFIILED;
import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSLOADING;
import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSSUCCESS;
import static com.yascn.smartpark.utils.AppContants.WEATHERCITY;

public class WeatherActivity extends BaseActivity  {

    private static final String TAG = "weatherActivity%s";
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.index_toolbar)
    Toolbar indexToolbar;

    @BindView(R.id.rv_weather)
    RecyclerView rvWeather;
    @BindView(R.id.iv_weather_bg)
    ImageView ivWeatherBg;
    @BindView(R.id.activity_introduction)
    FrameLayout activityIntroduction;
    @BindView(R.id.ll_error)
    LinearLayout llError;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;


    private String weatherCity;

    private WeatherViewModel weatherViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);
        initChildView(refreshLayout,refreshLayout,llError,null);





        weatherCity = getIntent().getStringExtra(WEATHERCITY);
        weatherCity = weatherCity.substring(0, weatherCity.length() - 1);

        tvTitle.setText(weatherCity);

        setToolbarMargin(indexToolbar);
        initToolbar();

        //1
        weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
        //2
        getLifecycle().addObserver(weatherViewModel);

        //3
        final Observer<WeatherTotalBean> weatherTotalBeanObserver=new Observer<WeatherTotalBean>() {
            @Override
            public void onChanged(@Nullable WeatherTotalBean weatherTotalBean) {
                setLiveWeather(weatherTotalBean.getWeatherLiveResult().getLiveResult());
                setForecastWeather(weatherTotalBean.getWeatherForecastResult());

                
            }
        };


        

        //4
        weatherViewModel.getMutableLiveDataEntry().observe(this,weatherTotalBeanObserver);
        weatherViewModel.getQueryStatus().observe(this,statusObserver);

       refreshData();
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                isPullRefresh = true;
              refreshData();


            }
        });
        
        
        
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void initToolbar() {

        indexToolbar.setNavigationIcon(R.drawable.icon_back_gray);

        indexToolbar.setTitleTextColor(Color.WHITE);
        indexToolbar.setTitle("");
        indexToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


    private LocalWeatherLive weatherLive;


    public void setLiveWeather(LocalWeatherLive weatherLive) {
        refreshLayout.finishRefresh();
        showTypeView(AppContants.VIEWTYPECONTENT);
        this.weatherLive = weatherLive;
        setWeatherBg(weatherLive.getWeather());

    }

    private void setWeatherBg(String weather) {
        String weatherType = Pinyin.toPinyin(weather, "").toLowerCase();
        if (weatherType.contains("duoyun")) {
            ivWeatherBg.setImageResource(R.drawable.icon_weather_bg_duoyun);
        } else if (weatherType.contains("qing")) {
            ivWeatherBg.setImageResource(R.drawable.icon_weather_bg_qing);
        } else if (weatherType.contains("wu")) {
            ivWeatherBg.setImageResource(R.drawable.icon_weather_bg_wu);
        } else if (weatherType.contains("xue")) {
            ivWeatherBg.setImageResource(R.drawable.icon_weather_bg_xue);
        } else if (weatherType.contains("yin")) {
            ivWeatherBg.setImageResource(R.drawable.icon_weather_bg_yin);
        } else if (weatherType.contains("yu")) {
            ivWeatherBg.setImageResource(R.drawable.icon_weather_bg_yu);
        } else {
            ivWeatherBg.setImageResource(R.drawable.icon_weather_bg_duoyun);
        }


    }



    public void setForecastWeather(LocalWeatherForecastResult forecastWeather) {

        rvWeather.setLayoutManager(new LinearLayoutManager(this));
        RvWeatherAdapter rvMainContentAdapter = new RvWeatherAdapter(this, forecastWeather.getForecastResult().getWeatherForecast());
        View mainRvContentHeader = View.inflate(this, R.layout.rv_weather_head, null);

        TextView tvTodaysTemp = mainRvContentHeader.findViewById(R.id.tv_todays_temp);
        TextView tvTodaysWeather = mainRvContentHeader.findViewById(R.id.tv_todays_weather);
        TextView tvTempMax = mainRvContentHeader.findViewById(R.id.tv_temp_max);
        TextView tvTempMin = mainRvContentHeader.findViewById(R.id.tv_temp_min);
        TextView tvHumidity = mainRvContentHeader.findViewById(R.id.tv_humidity);
        TextView tvWind = mainRvContentHeader.findViewById(R.id.tv_wind);
        TextView tvWindDirection = mainRvContentHeader.findViewById(R.id.tv_wind_direction);

        tvWindDirection.setText(weatherLive.getWindDirection() + getString(R.string.wind));
        tvTodaysTemp.setText(weatherLive.getTemperature() + getString(R.string.degree));
        tvTodaysWeather.setText(weatherLive.getWeather());
        tvTempMax.setText(getString(R.string.arrow_up) + forecastWeather.getForecastResult().getWeatherForecast().get(0).getDayTemp() + getString(R.string.degree));
        tvTempMin.setText(getString(R.string.arrow_down) + forecastWeather.getForecastResult().getWeatherForecast().get(0).getNightTemp() + getString(R.string.degree));
        tvHumidity.setText(weatherLive.getHumidity() + getString(R.string.percent));
        tvWind.setText(weatherLive.getWindPower() + getString(R.string.level));


        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mainRvContentHeader.setLayoutParams(layoutParams);
        rvMainContentAdapter.setHeaderView(mainRvContentHeader);


        rvWeather.setAdapter(rvMainContentAdapter);

    }

 
    @Override
    public void refreshData() {

        if(weatherViewModel!=null&&!TextUtils.isEmpty(weatherCity)){
            weatherViewModel.getWeatherData(weatherCity);
        }
    }

  
}
