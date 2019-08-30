package com.yascn.smartpark.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.github.promeg.pinyinhelper.Pinyin;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tmall.ultraviewpager.UltraViewPager;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yascn.smartpark.JPushDao;
import com.yascn.smartpark.R;
import com.yascn.smartpark.activity.CarLicenseFeeActivity;
import com.yascn.smartpark.activity.MainActivity;
import com.yascn.smartpark.activity.MyCarActivity;
import com.yascn.smartpark.activity.OrderActivity;
import com.yascn.smartpark.activity.QrCodeActivity;
import com.yascn.smartpark.activity.RecentParkActivity;
import com.yascn.smartpark.activity.SearchGaodeActivity;
import com.yascn.smartpark.activity.WeatherActivity;
import com.yascn.smartpark.adapter.OrderAdapter;
import com.yascn.smartpark.adapter.VpBannerAdapter;
import com.yascn.smartpark.base.AndroidApplication;
import com.yascn.smartpark.bean.HomeBean;
import com.yascn.smartpark.bean.JPush;
import com.yascn.smartpark.bean.YascnParkListBean;
import com.yascn.smartpark.contract.HomeIndexContract;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.DensityUtils;
import com.yascn.smartpark.utils.ImageViewUtils;
import com.yascn.smartpark.utils.KeyBoardUtils;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.LoginStatusUtils;
import com.yascn.smartpark.utils.MapUtils;
import com.yascn.smartpark.utils.NotchScreenUtils;
import com.yascn.smartpark.utils.OnSwitchFragment;
import com.yascn.smartpark.utils.SPUtils;
import com.yascn.smartpark.utils.SensorEventHelper;
import com.yascn.smartpark.utils.StringUtils;
import com.yascn.smartpark.utils.T;
import com.yascn.smartpark.utils.ViewUtils;
import com.yascn.smartpark.viewmodel.HomeIndexViewModel;
import com.yascn.smartpark.viewmodel.YascnParkIndexViewModel;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocatedCity;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yascn.smartpark.utils.AppContants.BROADCASTACTIONSEARCHPARK;
import static com.yascn.smartpark.utils.AppContants.BROADCASTDATASEARCHPARK;
import static com.yascn.smartpark.utils.AppContants.BROADCASTNOWLOCATION;
import static com.yascn.smartpark.utils.AppContants.LOCATIONPERMISSIONSUCCESS;
import static com.yascn.smartpark.utils.AppContants.MARKERTITLEBLUE;
import static com.yascn.smartpark.utils.AppContants.MARKERTITLERECOMMAND;
import static com.yascn.smartpark.utils.AppContants.MARKERTITLERED;
import static com.yascn.smartpark.utils.AppContants.NOWLOACTIONLON;
import static com.yascn.smartpark.utils.AppContants.NOWLOCATIONLAT;
import static com.yascn.smartpark.utils.AppContants.NOWlOCATIONlON;
import static com.yascn.smartpark.utils.AppContants.PARKFREENUMREDLINE;
import static com.yascn.smartpark.utils.AppContants.WEATHERCITY;
import static com.yascn.smartpark.utils.LoginStatusUtils.getUserId;

/**
 * Created by YASCN on 2017/9/2.
 */

public class HomeFragment extends HomeMapBaseFragment implements AMapLocationListener, WeatherSearch.OnWeatherSearchListener, HomeIndexContract.View {

    @BindView(R.id.ll_toolbar)
    RelativeLayout toolbar;


    @BindView(R.id.tv_location)
    TextView tvLocation;


    @BindView(R.id.mapview)
    TextureMapView mMapView;
    @BindView(R.id.iv_weather)
    ImageView ivWeather;
    @BindView(R.id.tv_weather_temperature)
    TextView tvWeatherTemperature;
    @BindView(R.id.ll_qr_code)
    LinearLayout llQrCode;
    @BindView(R.id.ll_license_fee)
    LinearLayout llLicenseFee;
    @BindView(R.id.ll_month_card)
    LinearLayout llMonthCard;
    @BindView(R.id.ll_my_order)
    LinearLayout llMyOrder;

    @BindView(R.id.ll_to_map)
    LinearLayout llToMap;
    @BindView(R.id.tv_order_nums)
    TextView tvOrderNums;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.ultra_banner)
    UltraViewPager ultraBanner;

    @BindView(R.id.iv_refresh_location)
    ImageView ivRefreshLocation;

    @BindView(R.id.tv_recommand_title)
    TextView tvRecommandTitle;

    @BindView(R.id.tv_park_nums)
    TextView tvParkNums;

    @BindView(R.id.tv_recommand_unit)
    TextView tvRecommandUnit;

    @BindView(R.id.rl_recommand_bottom)
    RelativeLayout rlRecommandBottom;

    @BindView(R.id.iv_no_recommand)
    ImageView ivNoRecommand;



    private OrderAdapter orderAdapter;
    private List<Fragment> orders;

    @BindView(R.id.ll_search)
    LinearLayout search;


    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);

    private static int CONFIRM_CLICK_SCREEN_SCALE = 17;
    private String userID;
    private double nowlat, nowLon;

    private JPush jPush;
    private JPushDao jPushDao;


    @BindView(R.id.refreshLayout)
    SmartRefreshLayout smartRefreshLayout;


    @BindView(R.id.tv_park_name)
    TextView  tvParkName;

    @BindView(R.id.tv_park_nums_bottom)
    TextView tvParkNumsBottom;

    @BindView(R.id.tv_distance)
    TextView tvParkDistance;



    public AMap aMap;
    private SensorEventHelper mSensorHelper;
    private LatLng nowLocation;
    private Circle mCircle;//定位出来的点周围圈
    private Marker mLocMarker;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(AppContants.REFLESHORDER)) {
                LogUtil.d(AppContants.TAG, "刷新数据" + userID);
                userID = getUserId(mainActivity);

                homeIndexViewModel.getHomeIndex(userID);
            }

            if (intent.getAction().equals(BROADCASTNOWLOCATION)) {
                nowlat = intent.getDoubleExtra(NOWLOCATIONLAT, 0);
                nowLon = intent.getDoubleExtra(NOWLOACTIONLON, 0);
                LogUtil.d(AppContants.TAG, "onReceive: local" + nowLon + ":" + nowLon);
            }

            if (intent.getAction().equals(AppContants.REFLESHOUT)) {
                jPush = (JPush) intent.getSerializableExtra("jpush");
                jPush.setTime(new Date().getTime());
//                jPush.setId(StringUtils.getId());
                LogUtil.d(AppContants.TAG, "jPush = " + jPush);
                String carNumber = jPush.getNUMBER();
                jPushDao = AndroidApplication.getInstances().getDaoSession().getJPushDao();
                List<JPush> jPushs = jPushDao.loadAll();
                if (jPushs.size() == 0) {
                    jPushDao.insert(jPush);
                } else {
                    for (int i = 0; i < jPushs.size(); i++) {
                        if (carNumber.equals(jPushs.get(i).getNUMBER())) {
                            break;
                        } else if (i == (jPushs.size() - 1)) {
//                            jPushDao.insert(jPush);
                        }
//                        if (!carNumber.equals(jPushs.get(i).getNUMBER())) {
//
//                        }
                    }
                }

            } else if (intent.getAction().equals(LOCATIONPERMISSIONSUCCESS)) {
                startLocation();
            }

        }
    };
    private MainActivity mainActivity;


    private YascnParkIndexViewModel yascnParkIndexViewModel;
    private HomeIndexViewModel homeIndexViewModel;

    private void setHeadMargin() {
        LogUtil.d(TAG, "onCreate: " + NotchScreenUtils.getIntchHeight(mainActivity));

//        toolbar.setPadding(0, (int) DensityUtils.dp2px(mainActivity,NotchScreenUtils.getIntchHeight(mainActivity)),0,0);
        ViewUtils.setMargins(toolbar, 0, (int) DensityUtils.dp2px(mainActivity, NotchScreenUtils.getIntchHeight(mainActivity)), 0, 0);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        mainActivity.initChildView(smartRefreshLayout,null,null,null);


        yascnParkIndexViewModel = ViewModelProviders.of(this).get(YascnParkIndexViewModel.class);
        getLifecycle().addObserver(yascnParkIndexViewModel);
        Observer<YascnParkListBean> yascnParkListBeanObserver = new Observer<YascnParkListBean>() {
            @Override
            public void onChanged(@Nullable YascnParkListBean yascnParkListBean) {
                setYascnParkData(yascnParkListBean);
            }
        };
        
        yascnParkIndexViewModel.getMutableLiveDataEntry().observe(this,yascnParkListBeanObserver);
        yascnParkIndexViewModel.getQueryStatus().observe(this,statusObserver);


        homeIndexViewModel = ViewModelProviders.of(this).get(HomeIndexViewModel.class);
        getLifecycle().addObserver(homeIndexViewModel);
        Observer<HomeBean> homeBeanObserver = new Observer<HomeBean>() {
            @Override
            public void onChanged(@Nullable HomeBean homeBean) {
                setHomeIndex(homeBean);
            }
        };
        
        homeIndexViewModel.getMutableLiveDataEntry().observe(this,homeBeanObserver);
        homeIndexViewModel.getQueryStatus().observe(this,statusObserver);

  
        

        setHeadMargin();
        userID = getUserId(mainActivity);

        homeIndexViewModel.getHomeIndex(userID);


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(AppContants.REFLESHORDER);
        intentFilter.addAction(AppContants.BROADCASTNOWLOCATION);
        intentFilter.addAction(AppContants.REFLESHOUT);
        intentFilter.addAction(LOCATIONPERMISSIONSUCCESS);
        getActivity().registerReceiver(broadcastReceiver, intentFilter);


        smartRefreshLayout.setEnableLoadmore(false);
        smartRefreshLayout.setEnableRefresh(true);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                isPullRefresh = true;

                userID = (String) SPUtils.get(getContext(), AppContants.KEY_USERID, "");

                if(yascnParkIndexViewModel!=null){
                    yascnParkIndexViewModel.getYascnParkData();
                }

                if(homeIndexViewModel!=null){
                    homeIndexViewModel.getHomeIndex(userID);
                }

                startQueryWeather(locationCity);
            }
        });

        initLocationOption();
    }

    private AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;

    private void initLocationOption() {
        mlocationClient = new AMapLocationClient(getActivity());
//初始化定位参数
        mLocationOption = new AMapLocationClientOption();
//设置定位监听
        mlocationClient.setLocationListener(this);
//设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//设置定位间隔,单位毫秒,默认为2000ms
//        mLocationOption.setInterval(2000);
        mLocationOption.setOnceLocation(true);
        mLocationOption.setOnceLocationLatest(true);
//设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
// 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
// 在定位结束后，在合适的生命周期调用onDestroy()方法
// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
//启动定位
        startLocation();

    }

    public void startLocation() {
        LogUtil.d(TAG, "startlocation");

        if (mlocationClient != null) {
            mlocationClient.startLocation();
            startLocateRefresh(true);
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        mMapView.onCreate(savedInstanceState);
        //设置陀螺仪
        mSensorHelper = new SensorEventHelper(getActivity());
        if (mSensorHelper != null) {
            mSensorHelper.registerSensorListener();
        }
        //-----定位
        // 设置定位监听

        aMap = mMapView.getMap();


        UiSettings uiSettings = aMap.getUiSettings();


        uiSettings.setLogoBottomMargin(-50);
        uiSettings.setZoomControlsEnabled(false);
        uiSettings.setAllGesturesEnabled(false);

        return view;
    }

    boolean isPullRefresh = false;

    @Override
    public void showProgress() {
        if (isPullRefresh) {
            return;
        }

        super.showProgress();
        if (mainActivity != null) {

            LogUtil.d(TAG, "showProgress:in ");
            mainActivity.showProgressDialog(StringUtils.getRString(mainActivity, R.string.loadingProgress));
        }

    }

    @Override
    public void hideProgress() {
        if (isPullRefresh) {
            smartRefreshLayout.finishRefresh();
            return;
        }

        super.hideProgress();
        if (mainActivity != null) {
            LogUtil.d(TAG, "showProgress: hideprogressin");
            mainActivity.hidProgressDialog();
        }
    }


    private static final String TAG = "HomeFragment%s";


    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();

    }


    @OnClick({R.id.tv_to_map,R.id.ll_search, R.id.ll_qr_code, R.id.ll_license_fee, R.id.ll_month_card, R.id.ll_my_order, R.id.iv_weather, R.id.tv_weather_temperature, R.id.tv_city,R.id.iv_refresh_location,R.id.tv_location})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_search:


                intent = new Intent(getActivity(), SearchGaodeActivity.class);
                getActivity().startActivity(intent);
                break;


            case R.id.ll_qr_code:
                showClickAnim(llQrCode);
                intent = new Intent(mainActivity, QrCodeActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;

            case R.id.ll_license_fee:
                showClickAnim(llLicenseFee);
                intent = new Intent(mainActivity, CarLicenseFeeActivity.class);
                startActivity(intent);


                break;

            case R.id.ll_month_card:
                showClickAnim(llMonthCard);
                if (LoginStatusUtils.isLogin(mainActivity)) {
                    intent = new Intent(mainActivity,MyCarActivity.class);
                    startActivity(intent);
//                    T.showShort(mainActivity, "月卡购买");
                } else {
                    mainActivity.showInputPhoneDialog();
                }
                break;

            case R.id.ll_my_order:
                showClickAnim(llMyOrder);
                if (LoginStatusUtils.isLogin(mainActivity)) {
                    intent = new Intent(mainActivity, OrderActivity.class);
//                                        intent = new Intent(mainActivity, RecentParkActivity.class);
                    startActivity(intent);
                } else {
                    mainActivity.showInputPhoneDialog();
                }

                break;


            case R.id.iv_weather:
            case R.id.tv_weather_temperature:
                if (!TextUtils.isEmpty(locationCity)) {
                    intent = new Intent(getActivity(), WeatherActivity.class);
                    intent.putExtra(WEATHERCITY, locationCity);
                    startActivity(intent);

                } else {
                    T.showShort(getActivity(), getString(R.string.string_locating));
                }


                break;

            case R.id.tv_city:

                if (!TextUtils.isEmpty(locationCity)) {
                    pickCity();
                } else {
                    T.showShort(getActivity(), getString(R.string.string_locating));
                }


                break;

            case R.id.iv_refresh_location:
            case R.id.tv_location:
                startLocation();

                break;

            case R.id.tv_to_map:
                if(mListener!=null){
                    if(recommandPark!=null){
                        intent = new Intent();
                        intent.setAction(BROADCASTACTIONSEARCHPARK);
                        intent.putExtra(BROADCASTDATASEARCHPARK,recommandPark.getPARKING_ID());
                        mainActivity.sendBroadcast(intent);
                    }else{
                        T.showShort(mainActivity,getString(R.string.get_recommand_error));
                    }

                    mListener.switchFragment(1);
                }

                break;


        }
    }


    private void startLocateRefresh(boolean isRefresh) {
        LogUtil.d(TAG, "startLocateRefresh: "+isRefresh);
        if (isRefresh) {

            startRefreshAnim();
        } else {

            stopRefreshAnim();

        }

    }

    Animation rotateAnim;
    private void startRefreshAnim() {
        rotateAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_anim);//创建动画
        rotateAnim.setInterpolator(new LinearInterpolator());//设置为线性旋转
        rotateAnim.setFillAfter(!rotateAnim.getFillAfter());
        ivRefreshLocation.startAnimation(rotateAnim);
    }

    private void stopRefreshAnim() {

        ivRefreshLocation.clearAnimation();
    }




    private void pickCity() {
        List<HotCity> hotCities = new ArrayList<>();
        hotCities.add(new HotCity("北京", "北京", "101010100"));
        hotCities.add(new HotCity("上海", "上海", "101020100"));
        hotCities.add(new HotCity("广州", "广东", "101280101"));
        hotCities.add(new HotCity("深圳", "广东", "101280601"));
        hotCities.add(new HotCity("杭州", "浙江", "101210101"));

        CityPicker.getInstance()
                .setFragmentManager(getActivity().getSupportFragmentManager())    //此方法必须调用
                .enableAnimation(true)    //启用动画效果
                .setLocatedCity(new LocatedCity(locationCity, locationProvince, locationCode)) //APP 自身已定位的城市，默认为 null（定位失败）
                .setHotCities(hotCities)    //指定热门城市
                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City data) {
                        KeyBoardUtils.hideInputForce(getActivity());
                        if (data != null) {
                            if (!data.getName().equals(getString(R.string.string_city_zhengzhou))) {
                                T.showShort(getActivity(), getString(R.string.string_city_not_support));
                            }

//                            Toast.makeText(getActivity(), data.getName(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onLocate() {
                        startLocation();

                    }
                })
                .show();
    }

    public void showClickAnim(final View view) {

        final Animation logoAnimation = new ScaleAnimation(1.0F, 1.2F, 1.0F, 1.2F, 1, 0.5F, 1, 0.5F);
        //设置动画间隔
        logoAnimation.setDuration(100);

        view.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //开始动画
                view.startAnimation(logoAnimation);
                return false;
            }
        });
    }


    private static final int REQUEST_CODE = 10010;
    private OnSwitchFragment mListener;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /**
         * 处理二维码扫描结果
         *
         */

        LogUtil.d(TAG, "onActivityResult: " + requestCode);
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                LogUtil.d(TAG, "onActivityResult: datenotnull");
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(mainActivity, "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(mainActivity, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnSwitchFragment) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(broadcastReceiver);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    private boolean networkFailedBefore = false;

    @Override
    public void netWordIsFailed() {
        super.netWordIsFailed();
        networkFailedBefore = true;
    }

    @Override
    public void netWorkIsSuccess() {
        super.netWorkIsSuccess();
        LogUtil.d(TAG, "netWorkIsSuccess: " + networkFailedBefore);
        if (networkFailedBefore) {
            userID = (String) SPUtils.get(getContext(), AppContants.KEY_USERID, "");
            LogUtil.d(AppContants.TAG, "userID = " + userID);
            if (homeIndexViewModel != null) {
                homeIndexViewModel.getHomeIndex(userID);
            }
            startLocation();

        }

    }

    private boolean isFisrtLocate = true;
    private String locationCity;
    private String locationCode;
    private String locationProvince;


    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        LogUtil.d(TAG, "onLocationChanged: ");
        stopRefreshAnim();
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {

                Intent intent = new Intent();
                intent.setAction(BROADCASTNOWLOCATION);
                intent.putExtra(NOWLOCATIONLAT, amapLocation.getLatitude());
                intent.putExtra(NOWlOCATIONlON, amapLocation.getLongitude());
                getActivity().sendBroadcast(intent);
                locationCity = amapLocation.getCity();
                tvCity.setText(locationCity);
                tvLocation.setText(amapLocation.getDescription());

                locationProvince = amapLocation.getProvince();


                startQueryWeather(locationCity);
                locationCode = amapLocation.getCityCode();
//                tvCity.setText(locationCity);

//                CityPicker.getInstance()
//                        .locateComplete(new LocatedCity(locationCity, locationProvince, locationCode), LocateState.SUCCESS);
                LogUtil.d(TAG, "onLocationChanged:" + amapLocation.getCity() + "code:" + amapLocation.getCityCode());

                if (mCircle != null) {
                    mCircle.remove();
                    mCircle = null;

                }
                if (mLocMarker != null) {
                    mLocMarker.remove();
                    mLocMarker = null;
                }
                nowLocation = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
                addCircle(nowLocation, amapLocation.getAccuracy());//添加定位精度圆
                addLocMarker(nowLocation);//添加定位图标
                mSensorHelper.setCurrentMarker(mLocMarker);//定位图标旋转
//                scaleMap(nowLocation);
                mainActivity.hidProgressDialog();
                isFisrtLocate = false;


                if (yascnParkIndexViewModel != null) {
                    yascnParkIndexViewModel.getYascnParkData();

                }
                LogUtil.d(TAG, "onLocationChanged: lat:" + amapLocation.getLatitude() + "----" + amapLocation.getLongitude());
                nowLocation = new LatLng(amapLocation.getLatitude() - 0.004, amapLocation.getLongitude() + 0.007);
                scaleMap(nowLocation);
//                scaleLargeMap(nowLocation);

//                startLocateRefresh(false);

            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                LogUtil.e(TAG, "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());

            }
        }
    }

    private void startQueryWeather(String locationCity) {
        if (TextUtils.isEmpty(locationCity)) {
            locationCity = "郑州";
        }
        //检索参数为城市和天气类型，实况天气为WEATHER_TYPE_LIVE、天气预报为WEATHER_TYPE_FORECAST
        WeatherSearchQuery mquery = new WeatherSearchQuery(locationCity, WeatherSearchQuery.WEATHER_TYPE_LIVE);
        WeatherSearch mweathersearch = new WeatherSearch(mainActivity);
        mweathersearch.setOnWeatherSearchListener(this);
        mweathersearch.setQuery(mquery);
        mweathersearch.searchWeatherAsyn(); //异步搜索
    }


    @Override
    public void onWeatherLiveSearched(LocalWeatherLiveResult weatherLiveResult, int rCode) {


        if (rCode == 1000) {
            if (weatherLiveResult != null && weatherLiveResult.getLiveResult() != null) {
                LocalWeatherLive weatherlive = weatherLiveResult.getLiveResult();
                tvWeatherTemperature.setText(weatherlive.getTemperature() + "°C");
                LogUtil.d(TAG, "onWeatherLiveSearched: weather:" + weatherlive.getWeather() + "度数:" + weatherlive.getTemperature());
                LogUtil.d(TAG, "onWeatherLiveSearched: pinyin" + "icon_" + Pinyin.toPinyin("大雨-暴雨", "").toLowerCase());


                ImageViewUtils.setImageFromAssetsFile(mainActivity, "weathericon/icon_" + Pinyin.toPinyin(weatherlive.getWeather(), "").toLowerCase() + ".png", ivWeather);


            } else {
//                T.showShort(mActivity, R.string.string_weather_error);
                LogUtil.d(TAG, "onWeatherLiveSearched: 天气获取失败");
            }
        } else {
            LogUtil.d(TAG, "onWeatherLiveSearched: errorcode" + rCode);
//            ToastUtil.showerror(WeatherSearchActivity.this, rCode);

        }
    }

    @Override
    public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int i) {

    }


    public void scaleLargeMap(LatLng nowLocation) {

        cameraPosition = aMap.getCameraPosition();
        aMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(nowLocation, CONFIRM_CLICK_SCREEN_SCALE, cameraPosition.tilt, bearing)));
    }

    private CameraPosition cameraPosition;
    private static int CONFIRM_SCALE_MULTIPLE = 14;

    float bearing = 0.0f;  // 地图默认方向

    public void scaleMap(LatLng nowLocation) {
        cameraPosition = aMap.getCameraPosition();
        aMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(nowLocation, CONFIRM_SCALE_MULTIPLE, cameraPosition.tilt, bearing)));
    }

    private void addLocMarker(LatLng latlng) {
        LogUtil.d("addlocationsmarker", latlng.latitude + "\n" + latlng.longitude);
        if (mLocMarker != null) {
            return;
        }
        Bitmap bMap = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.navi_map_gps_locked);
        BitmapDescriptor des = BitmapDescriptorFactory.fromBitmap(bMap);

        MarkerOptions options = new MarkerOptions();
        options.icon(des);
        options.anchor(0.5f, 0.5f);
        options.position(latlng);
        mLocMarker = aMap.addMarker(options);
        mLocMarker.setInfoWindowEnable(false);
        mLocMarker.setTitle(LOCATION_MARKER_FLAG);
        mLocMarker.setClickable(false);


    }

    private static final String LOCATION_MARKER_FLAG = "locationmarker";

    private void addCircle(LatLng latlng, double radius) {
        CircleOptions options = new CircleOptions();
        options.strokeWidth(1f);
        options.fillColor(FILL_COLOR);
        options.strokeColor(STROKE_COLOR);
        options.center(latlng);
        options.radius(radius);
        if (mCircle == null) {
            mCircle = aMap.addCircle(options);
        }

    }

    @Override
    public void serverError(String msg) {
        T.showShort(mainActivity, msg);
    }


    @Override
    public void setHomeIndex(HomeBean homeBean) {

        String limitCar = "";
        if (homeBean.getObject().getCar_limit().getFlag().equals(AppContants.NOCARLIMIT)) {
//            T.showShort(mainActivity,"不限号");

        } else if (homeBean.getObject().getCar_limit().getFlag().equals(AppContants.CARLIMIT)) {
//            T.showShort(mainActivity,"限号:"+carLimitBean.getObject().getLimitNo());
            limitCar = homeBean.getObject().getCar_limit().getLimitNo();
        }

        if (homeBean.getObject().getOrderNum().equals("0")) {
            tvOrderNums.setVisibility(View.GONE);
        } else {
            tvOrderNums.setVisibility(View.VISIBLE);
            tvOrderNums.setText(homeBean.getObject().getOrderNum());
        }


        ultraBanner.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        LogUtil.d(TAG, "setHomeIndex: " + homeBean.getObject().getBanner_list().size());
        ultraBanner.setAdapter(new VpBannerAdapter(getActivity(), homeBean.getObject()));


//设定页面循环播放
        if (homeBean.getObject().getBanner_list() != null) {
            if (homeBean.getObject().getBanner_list().size() > 1) {
                ultraBanner.setInfiniteLoop(true);
            }
        }

//设定页面自动切换  间隔2秒
        ultraBanner.setAutoScroll(2000);

    }


    @Override
    public void setYascnParkData(YascnParkListBean yascnParkListBean) {
        List<YascnParkListBean.ObjectBean> yascnParkObjectList = yascnParkListBean.getObject();

        YascnParkListBean.ObjectBean recommandObjectBean = findRecommandPark(yascnParkListBean);
        if (recommandObjectBean != null) {
            addBlueRedParkMarker(false, recommandObjectBean, true);
        }
//

        for (int i = 0; i < yascnParkObjectList.size(); i++) {

            YascnParkListBean.ObjectBean yascnObjectBean = yascnParkObjectList.get(i);
            if (recommandObjectBean != null) {

                if (yascnObjectBean.getPARKING_ID().equals(recommandObjectBean.getPARKING_ID())) {
                    continue;
                }
            }

            int parkFreeNum = StringUtils.emptyParseInt(yascnObjectBean.getFREE_NUM());
            if (parkFreeNum < PARKFREENUMREDLINE) {
                addBlueRedParkMarker(false, yascnObjectBean, false);

            } else {
                addBlueRedParkMarker(true, yascnObjectBean, false);
            }

        }

    }

    private LinkedList<String> yascnParkId = new LinkedList<>();//

    private void addBlueRedParkMarker(boolean isBlue, YascnParkListBean.ObjectBean yascnObjectBean, boolean isRecommand) {


        int parkFreeNum = StringUtils.emptyParseInt(yascnObjectBean.getFREE_NUM());


        double[] locats = new double[]{StringUtils.emptyParseDouble(yascnObjectBean.getLAT()), StringUtils.emptyParseDouble(yascnObjectBean.getLNG())};

        if (yascnParkId.contains(yascnObjectBean.getPARKING_ID())) {
            return;
        }

        LatLng latLng = new LatLng(locats[0], locats[1]);
        Marker marker = aMap.addMarker(new MarkerOptions().
                position(latLng));
        if (isRecommand) {
            marker.setTitle(MARKERTITLERECOMMAND);
            setPoiMarkerIcon(marker, R.drawable.icon_park_marker_recommand);
        } else {
            marker.setTitle(isBlue ? MARKERTITLEBLUE : MARKERTITLERED);
            setPoiMarkerIcon(marker, isBlue ? R.drawable.icon_park_marker_blue : R.drawable.icon_park_marker_red);
        }

        marker.setObject(yascnObjectBean);
        marker.setClickable(false);
//        marker.setInfoWindowEnable(false);
        yascnMarkers.add(marker);
        yascnParkId.add(yascnObjectBean.getPARKING_ID());


        for (int i = 0; i < yascnObjectBean.getET_LIST().size(); i++) {

            YascnParkListBean.ObjectBean.ETLISTBean etlistBean = yascnObjectBean.getET_LIST().get(i);
            addBlueYascnParkEntranceMarker(parkFreeNum < PARKFREENUMREDLINE ? false : true, etlistBean, yascnObjectBean.getNAME());
        }


    }

    private LinkedList<Marker> yascnMarkers = new LinkedList<>();

    private void setPoiMarkerIcon(Marker marker, int drawableId) {


        View markericonView = View.inflate(getActivity(), R.layout.map_marker_icon_layout, null);
        ImageView ivIcon = (ImageView) markericonView.findViewById(R.id.iv_icon);
        ivIcon.setImageResource(drawableId);

        BitmapDescriptor markerIcon = BitmapDescriptorFactory
                .fromView(markericonView);
        marker.setIcon(markerIcon);


    }

    private LinkedList<Marker> yascnExportMarkers = new LinkedList<>();
    private LinkedList<String> yascnEntranceId = new LinkedList<>();

    private void addBlueYascnParkEntranceMarker(boolean isBlue, YascnParkListBean.ObjectBean.ETLISTBean etlistBean, String name) {


        double[] locats = new double[]{StringUtils.emptyParseDouble(etlistBean.getLAT()), StringUtils.emptyParseDouble(etlistBean.getLNG())};
//        double[] locats = GpsTransUtils.bd09_To_Gcj02(getActivity(), StringUtils.emptyParseDouble(etlistBean.getLAT()), StringUtils.emptyParseDouble(etlistBean.getLNG()));

        if (yascnEntranceId.contains(etlistBean.getP_ENTRANCE_ID())) {
            return;
        }

        LatLng latLng = new LatLng(locats[0], locats[1]);

        final Marker marker = aMap.addMarker(new MarkerOptions().
                position(latLng));
        marker.setClickable(false);
        setPoiMarkerIcon(marker, isBlue ? R.drawable.icon_park_entrance_blue : R.drawable.icon_park_entrance_red);

        marker.setObject(etlistBean);
        marker.setInfoWindowEnable(false);
        marker.setVisible(false);
        marker.setSnippet(name);

        yascnExportMarkers.add(marker);
        yascnEntranceId.add(etlistBean.getP_ENTRANCE_ID());

    }
    YascnParkListBean.ObjectBean recommandPark = null;

    private YascnParkListBean.ObjectBean  findRecommandPark(YascnParkListBean yascnParkListBean) {
        LinkedList<YascnParkListBean.ObjectBean> yascnParkBeans = new LinkedList<>();

        if (yascnParkListBean != null) {
//            tvParkNums.setVisibility(View.VISIBLE);
//            tvParkNums.setText(yascnParkListBean.getObject().size() == 0 ? "暂无" : yascnParkListBean.getObject().size() + "");
            if (yascnParkListBean.getObject().size() != 0) {


                //将亚视所有的停车场中筛选出有空余车位的部分
                for (YascnParkListBean.ObjectBean parkBean : yascnParkListBean.getObject()) {

                    if (!"0".equals(parkBean.getFREE_NUM())) {
                        yascnParkBeans.add(parkBean);

                    }
                }


                //通过比较选出距离搜索的位置距离最小的停车场
                float minDistance = 0;
                for (int i = 0; i < yascnParkBeans.size(); i++) {

                    YascnParkListBean.ObjectBean objectBean = yascnParkBeans.get(i);


                    double[] locats = new double[]{StringUtils.emptyParseDouble(objectBean.getLAT()), StringUtils.emptyParseDouble(objectBean.getLNG())};
//                    double[] locats = GpsTransUtils.bd09_To_Gcj02(getActivity(), StringUtils.emptyParseDouble(objectBean.getLAT()), StringUtils.emptyParseDouble(objectBean.getLNG()));

                    float distance = AMapUtils.calculateLineDistance(nowLocation, new LatLng(locats[0], locats[1]));
                    if (i == 0) {
                        minDistance = distance;
                    } else if (distance < minDistance) {
                        minDistance = distance;
                    }

                }


                /**
                 * 判断最小距离是否大于3公里
                 */
                if (minDistance < AppContants.MIN_RECOMMAND_DISTANCE) {

                    for (int i = 0; i < yascnParkBeans.size(); i++) {


                        double[] locats = new double[]{StringUtils.emptyParseDouble(yascnParkBeans.get(i).getLAT()), StringUtils.emptyParseDouble(yascnParkBeans.get(i).getLNG())};
//                        double[] locats = GpsTransUtils.bd09_To_Gcj02(getActivity(), StringUtils.emptyParseDouble(yascnParkBeans.get(i).getLAT()), StringUtils.emptyParseDouble(yascnParkBeans.get(i).getLNG()));

                        float distance = AMapUtils.calculateLineDistance(nowLocation, new LatLng(locats[0], locats[1]));
                        if (minDistance == distance) {
                            recommandPark = yascnParkBeans.get(i);
                        }

                    }



                    setRecommandParkNumber(yascnParkListBean.getObject().size());
                    setrecommandParkInfo(minDistance);

                }else{
                    setRecommandParkNumber(0);
                    setrecommandParkInfo(0);
                }




            }
        } else {
            setRecommandParkNumber(0);
            setrecommandParkInfo(0);
//            tvParkNums.setVisibility(View.GONE);
        }

     ;
        return recommandPark;

    }

    private void setrecommandParkInfo(float parkDistance) {
        if(parkDistance!=0){
            if(recommandPark!=null){
                setRecommandBottomVisibility(true);
//                rlRecommandBottom.setVisibility(View.VISIBLE);
                tvParkName.setText(recommandPark.getNAME());
                tvParkNumsBottom.setText(getString(R.string.string_park_nums_title)+recommandPark.getFREE_NUM()+StringUtils.getRString(mainActivity,R.string.string_unit_ge));
                LogUtil.d(TAG, "setrecommandParkInfo: "+parkDistance);
                tvParkDistance.setText(getString(R.string.string_park_distance)+MapUtils.calculateDistanceZn(parkDistance));

            }else{
//                rlRecommandBottom.setVisibility(View.GONE);
                setRecommandBottomVisibility(false);
            }

        }else{
            //没有推荐停车场
            setRecommandBottomVisibility(false);
//            rlRecommandBottom.setVisibility(View.GONE);
        }


    }


    private void setRecommandBottomVisibility(boolean isVisible){
        rlRecommandBottom.setVisibility(isVisible?View.VISIBLE:View.GONE);
        ivNoRecommand.setVisibility(isVisible?View.GONE:View.VISIBLE);
    }

    private void setRecommandParkNumber(int recommandNumber){
        if(recommandNumber==0){

            tvRecommandTitle.setText(getString(R.string.string_no_recommand_park));
            tvParkNums.setVisibility(View.GONE);
            tvRecommandUnit.setVisibility(View.GONE);

        }else{
            tvRecommandTitle.setText(getString(R.string.string_recommand_neighbor_parkinglot));
            tvParkNums.setVisibility(View.VISIBLE);
            tvParkNums.setText(recommandNumber+"");
            tvRecommandUnit.setVisibility(View.VISIBLE);

        }


    }


}
