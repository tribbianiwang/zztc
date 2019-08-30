package com.yascn.smartpark.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

import com.gyf.barlibrary.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yascn.smartpark.R;
import com.yascn.smartpark.activity.AppointActivity;
import com.yascn.smartpark.activity.BNDemoMainActivity;
import com.yascn.smartpark.activity.MainActivity;
import com.yascn.smartpark.activity.ParkDetailActivity;
import com.yascn.smartpark.activity.SearchGaodeActivity;
import com.yascn.smartpark.adapter.RvMapFeeDetailAdapter;
import com.yascn.smartpark.bean.CarLicense;
import com.yascn.smartpark.bean.ParkComment;
import com.yascn.smartpark.bean.ParkPointBean;
import com.yascn.smartpark.bean.YascnParkListBean;
import com.yascn.smartpark.contract.CarLicenseContract;
import com.yascn.smartpark.contract.ParkCommentContract;
import com.yascn.smartpark.contract.StartPointParkContract;
import com.yascn.smartpark.contract.YascnParkIndexContract;
import com.yascn.smartpark.dialog.NaviChooseDialog;
import com.yascn.smartpark.presenter.ParkCommentPresenterImpl;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.DensityUtils;
import com.yascn.smartpark.utils.GpsTransUtils;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.LoginStatusUtils;
import com.yascn.smartpark.utils.MapUtils;
import com.yascn.smartpark.utils.NoScrollLinearLayoutManager;
import com.yascn.smartpark.utils.NotchScreenUtils;
import com.yascn.smartpark.utils.OnSwitchFragment;
import com.yascn.smartpark.utils.SPUtils;
import com.yascn.smartpark.utils.ScreenUtil;
import com.yascn.smartpark.utils.ScreenUtils;
import com.yascn.smartpark.utils.SensorEventHelper;
import com.yascn.smartpark.utils.StringUtils;
import com.yascn.smartpark.utils.T;
import com.yascn.smartpark.utils.ViewUtils;
import com.yascn.smartpark.viewmodel.YascnParkIndexViewModel;
import com.yinglan.scrolllayout.ScrollLayout;
import com.yinglan.scrolllayout.content.ContentRecyclerView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yascn.smartpark.utils.AppContants.BROADCASTACTIONSEARCHPARK;
import static com.yascn.smartpark.utils.AppContants.BROADCASTAPPOINTNOW;
import static com.yascn.smartpark.utils.AppContants.BROADCASTAPPOINTNOWID;
import static com.yascn.smartpark.utils.AppContants.BROADCASTDATASEARCHPARK;
import static com.yascn.smartpark.utils.AppContants.BROADCASTGAODE;
import static com.yascn.smartpark.utils.AppContants.BROADCASTGAODELAT;
import static com.yascn.smartpark.utils.AppContants.BROADCASTGAODELON;
import static com.yascn.smartpark.utils.AppContants.BROADCASTGAODENAME;
import static com.yascn.smartpark.utils.AppContants.BROADCASTGAODESNIPPT;
import static com.yascn.smartpark.utils.AppContants.BROADCASTMAINTOMAPENTRANCE;
import static com.yascn.smartpark.utils.AppContants.BROADCASTNOWLOCATION;
import static com.yascn.smartpark.utils.AppContants.ISBAIDUNAVIAVAILABLE;
import static com.yascn.smartpark.utils.AppContants.ISGAODENAVIAVAILABLE;
import static com.yascn.smartpark.utils.AppContants.LOCATIONPERMISSIONSUCCESS;
import static com.yascn.smartpark.utils.AppContants.MARKERTITLEBLUE;
import static com.yascn.smartpark.utils.AppContants.MARKERTITLERECOMMAND;
import static com.yascn.smartpark.utils.AppContants.MARKERTITLERED;
import static com.yascn.smartpark.utils.AppContants.MARKERlOCATIONlAT;
import static com.yascn.smartpark.utils.AppContants.MARKERlOCATIONlon;
import static com.yascn.smartpark.utils.AppContants.NAVITAPEBAIDU;
import static com.yascn.smartpark.utils.AppContants.NAVITYPEGAODE;
import static com.yascn.smartpark.utils.AppContants.NAVITYPELOCAL;
import static com.yascn.smartpark.utils.AppContants.NOWLOCATIONLAT;
import static com.yascn.smartpark.utils.AppContants.NOWlOCATIONlAT;
import static com.yascn.smartpark.utils.AppContants.NOWlOCATIONlON;
import static com.yascn.smartpark.utils.AppContants.PARKFREENUMREDLINE;
import static com.yascn.smartpark.utils.AppContants.PARKFREENUMS;
import static com.yascn.smartpark.utils.AppContants.PARKID;
import static com.yascn.smartpark.utils.AppContants.PARKNAMETRANS;
import static com.yascn.smartpark.utils.AppContants.PARKPOINTRESULT;
import static com.yascn.smartpark.utils.AppContants.PARKSCALENUM;
import static com.yascn.smartpark.utils.AppContants.REFRESHCARNUMBER;
import static com.yascn.smartpark.utils.AppContants.SELECTPARKDETAILBEAN;
import static com.yascn.smartpark.utils.NotchScreenUtils.isXiaomiDevice;
import static com.yascn.smartpark.utils.StringUtils.getRColor;
import static com.yascn.smartpark.utils.StringUtils.setEmptyViewBgAndImg;
import static com.yascn.smartpark.utils.StringUtils.setParkFreeNum;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends HomeMapBaseFragment implements AMapLocationListener, YascnParkIndexContract.View,  StartPointParkContract.View, NaviChooseDialog.chooseNaviListener,ParkCommentContract.View {

    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    private static final String LOCATION_MARKER_FLAG = "locationmarker";
    @BindView(R.id.mapview)
    TextureMapView mMapView;
    @BindView(R.id.iv_relocate)
    ImageView ivRelocate;
    @BindView(R.id.ll_point_layout)
    LinearLayout llPointLayout;
    @BindView(R.id.tv_appoint)
    TextView tvAppoint;
    @BindView(R.id.tv_park_detail)
    TextView tvParkDetail;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.tv_park_name)
    TextView tvParkName;
    @BindView(R.id.tv_park_address)
    TextView tvParkAddress;
    @BindView(R.id.iv_to_navi)
    ImageView ivToNavi;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    @BindView(R.id.tv_empty_number)
    TextView tvEmptyNumber;
    @BindView(R.id.ll_navi_hint)
    LinearLayout llNaviHint;
    @BindView(R.id.tv_navi_hint_text)
    TextView tvNaviHintText;

    @BindView(R.id.iv_compass)
    ImageView ivCompass;
    @BindView(R.id.iv_zoom_large)
    ImageView ivZoomLarge;
    @BindView(R.id.iv_zoom_small)
    ImageView ivZoomSmall;
    @BindView(R.id.rl_scale_loc_compass)
    LinearLayout rlScaleLocCompass;
    @BindView(R.id.iv_refresh)
    ImageView ivRefresh;
    @BindView(R.id.ll_freenum_root)
    LinearLayout llFreeNumRoot;
    @BindView(R.id.rv_content)
    ContentRecyclerView rvContent;
    @BindView(R.id.scroll_down_layout)
    ScrollLayout scrollDownLayout;

    @BindView(R.id.tv_bar_title)
    TextView tvBarTitle;
    @BindView(R.id.index_toolbar)
    RelativeLayout indexToolbar;

    @BindView(R.id.iv_close)
    ImageView ivClose;

    private YascnParkIndexViewModel yascnParkIndexViewModel;



    private AMapLocationClient mlocationClient;
    private Circle mCircle;//定位出来的点周围圈
    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);
    private Marker mLocMarker;
    public AMap aMap;
    private SensorEventHelper mSensorHelper;
    private LatLng nowLocation;

    private MainActivity mainActivity;
    private boolean isFisrtLocate = true;
    private AlertDialog appointDialog;

    private OnSwitchFragment mListener;
    private LinkedList<Marker> yascnMarkers = new LinkedList<>();
    private LinkedList<Marker> yascnExportMarkers = new LinkedList<>();
    private LinkedList<String> yascnParkId = new LinkedList<>();//
    private LinkedList<String> yascnEntranceId = new LinkedList<>();

    private static final int SCREEN_SCALE_MULTIPLE_SMALL = 15;
    private static final int SCREEN_SCALE_MULTIPLE_LARGE = 16;
    private static int CONFIRM_SCALE_MULTIPLE = 15;
    private static int CONFIRM_CLICK_SCREEN_SCALE = 17;

    private static final int CLICK_SCREEN_SCALE_SMALL = 17;
    private static final int CLICK_SCREEN_SCALE_LARGE = 18;

    private int screenHeight;
    private YascnParkListBean.ObjectBean selectedObjectBean;
    private AlertDialog gpsshowDialog;
    private CarLicenseContract.Presenter carLicensePresenter;
    private String userId;
    private RotateAnimation rotateAnimation;
    private CameraPosition cameraPosition;
    private String gaodeSearchTitle;
    private String gaodeSearchAddress;
    private double gaodeSearchLat;
    private double gaodeSearchLon;
    private NoScrollLinearLayoutManager noScrollLinearLayoutManager;


    //REFRESHCARNUMBER

    public MapFragment() {
        // Required empty public constructor
    }

    public BroadcastReceiver mapReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(REFRESHCARNUMBER)) {
                if (isOnstart) {
                    showAppointDailog();
                }
            } else if (intent.getAction().equals(LocationManager.PROVIDERS_CHANGED_ACTION)) {
                boolean currentGPSState = getGPSState(context);
                if (currentGPSState) {
                    if (gpsshowDialog != null) {
                        if (gpsshowDialog.isShowing()) {
                            gpsshowDialog.dismiss();
                        }
                    }
                } else {
                    checkGpsOpen();
                }

            } else if (intent.getAction().equals(BROADCASTACTIONSEARCHPARK)) {
                String parkId = intent.getStringExtra(BROADCASTDATASEARCHPARK);
                showSelectPark(parkId);

            } else if (intent.getAction().equals(BROADCASTMAINTOMAPENTRANCE)) {
                String parkId = intent.getStringExtra(SELECTPARKDETAILBEAN);
                setSelectParkBean(parkId);
                showEntrance(parkId);
            } else if (intent.getAction().equals(BROADCASTAPPOINTNOW)) {
                String parkId = intent.getStringExtra(BROADCASTAPPOINTNOWID);
                setSelectParkBean(parkId);
                LogUtil.d(TAG, "onReceive: now" + parkId + (selectedObjectBean == null));
                startAppointPark();
            } else if (intent.getAction().equals(BROADCASTGAODE)) {

                gaodeSearchTitle = intent.getStringExtra(BROADCASTGAODENAME);
                gaodeSearchAddress = intent.getStringExtra(BROADCASTGAODESNIPPT);
                gaodeSearchLat = intent.getDoubleExtra(BROADCASTGAODELAT, 0);
                gaodeSearchLon = intent.getDoubleExtra(BROADCASTGAODELON, 0);

                showSearhMarker();

            }else if(intent.getAction().equals(LOCATIONPERMISSIONSUCCESS)){
                LogUtil.d(TAG, "onReceive: locationpermissionsuccess");
                startLocation();
            }
        }
    };

    private void showSearhMarker() {
        showHideBotomView(false);
        Bitmap bMap = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.icon_location_pink);
        BitmapDescriptor des = BitmapDescriptorFactory.fromBitmap(bMap);

        MarkerOptions options = new MarkerOptions();
        options.icon(des);
        options.anchor(0.5f, 0.5f);
        options.position(new LatLng(gaodeSearchLat, gaodeSearchLon));
        Marker searchGaodeMarker = aMap.addMarker(options);
        searchGaodeMarker.setInfoWindowEnable(false);
        searchGaodeMarker.setTitle(gaodeSearchTitle);
        searchGaodeMarker.setClickable(false);
        scaleLargeMap(new LatLng(gaodeSearchLat, gaodeSearchLon));

        int minDistance = Integer.MAX_VALUE;
        for (int i = 0; i < yascnMarkers.size(); i++) {
            int singleDistance = MapUtils.calculateIntDistance(yascnMarkers.get(i).getPosition(), new LatLng(gaodeSearchLat, gaodeSearchLon));
            if (minDistance > singleDistance) {
                minDistance = singleDistance;
            }
        }
        if (minDistance > AppContants.LASTARROUNDDISTANCE) {

            tvNaviHintText.setText(getString(R.string.nopark_around));
            showHideHint();
        }


    }


    private void showSelectPark(String parkId) {
        for (Marker marker : yascnMarkers) {
            YascnParkListBean.ObjectBean yascnParkBean = (YascnParkListBean.ObjectBean) marker.getObject();
            if (yascnParkBean.getPARKING_ID().equals(parkId)) {
                selectedObjectBean = yascnParkBean;
                showSelectPark(yascnParkBean, marker);

            }
        }
    }

    private void setSelectParkBean(String parkId) {
        for (Marker marker : yascnMarkers) {
            YascnParkListBean.ObjectBean yascnParkBean = (YascnParkListBean.ObjectBean) marker.getObject();
            LogUtil.d(TAG, "setSelectParkBean: markid" + yascnParkBean.getPARKING_ID());
            if (yascnParkBean.getPARKING_ID().equals(parkId)) {
                selectedObjectBean = yascnParkBean;

            }
        }
    }

    /**
     * 获取ＧＰＳ当前状态
     *
     * @param context
     * @return
     */
    private boolean getGPSState(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean on = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return on;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext




        View view = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, view);
        // Inflate the layout for this fragment
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
//        uiSettings.setScaleControlsEnabled(false);
        uiSettings.setZoomControlsEnabled(false);
        uiSettings.setCompassEnabled(false);

        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                LogUtil.d(TAG, "onMapClick: ");

                showHideBotomView(false);

            }
        });
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                startIvCompass(cameraPosition.bearing);
            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                stopIvCompass(cameraPosition.bearing);

            }
        });
        initToolbar();

        return view;
    }

    private void setBarColor(final int color){

        ImmersionBar.with(mainActivity)
                .statusBarColor(color)  //指定状态栏颜色,根据情况是否设置
                .init();

    }


    private int nowMaxOffset;
    private int bigMaxOffset;
    private int smallMaxOffset;
    private ScrollLayout.OnScrollChangedListener mOnScrollChangedListener = new ScrollLayout.OnScrollChangedListener() {
        @Override
        public void onScrollProgressChanged(float currentProgress) {
            LogUtil.d(TAG,"onScrollProgressChanged: cureetprogress:%f"+currentProgress);
            if (currentProgress == 1.0) {

                //关闭或者刚点开
                LogUtil.d( "onScrollProgressChanged: now%d" ,nowMaxOffset + "big" + bigMaxOffset + "small" + smallMaxOffset);
                if (nowMaxOffset == bigMaxOffset) {
                    nowMaxOffset = smallMaxOffset;
                } else {
                    nowMaxOffset = bigMaxOffset;
                }
                scrollDownLayout.setMaxOffset(nowMaxOffset);
                scrollDownLayout.invalidate();
                setHeadMargin(true);
                setBarColor(R.color.transparent);
                indexToolbar.setVisibility(View.GONE);
                mMapView.setForeground(new ColorDrawable(getRColor(mainActivity,R.color.transparent)));

            } else if (currentProgress == 0) {
                //全部显示
                setHeadMargin(false);

                indexToolbar.setVisibility(View.VISIBLE);
                mMapView.setForeground(new ColorDrawable(getRColor(mainActivity,R.color.gray_white)));
              setRvContentScrollAble(true);
                setBarColor(R.color.primary_blue);


            } else {

                setHeadMargin(true);
                indexToolbar.setVisibility(View.GONE);
                mMapView.setForeground(new ColorDrawable(getRColor(mainActivity,R.color.transparent)));
                setRvContentScrollAble(false);
                if(nowMaxOffset==bigMaxOffset){
                    LogUtil.d("timetoshowanim",""+currentProgress);
                }

            }


        }





        private void setRvContentScrollAble(final boolean isScrollAble){
            noScrollLinearLayoutManager.setScrollEnabled(isScrollAble);
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()) { @Override public boolean canScrollVertically() { return isScrollAble; } };
            rvContent.setLayoutManager(noScrollLinearLayoutManager);
        }

        @Override
        public void onScrollFinished(ScrollLayout.Status currentStatus) {
            LogUtil.d(TAG, "onScrollFinished: status"+currentStatus);


        }

        @Override
        public void onChildScroll(int top) {
            LogUtil.d(TAG, "onChildScroll: "+top);
        }
    };


    private void initBottomView() {


        /**设置 setting*/
        scrollDownLayout.setMinOffset(0);
        nowMaxOffset = smallMaxOffset;
        scrollDownLayout.setMaxOffset(nowMaxOffset);
//        scrollDownLayout.setMaxOffset((int) (ScreenUtil.getScreenHeight(mainActivity) * 0.5));
//        scrollDownLayout.setExitOffset(ScreenUtil.dip2px(mainActivity, 50));
        scrollDownLayout.setExitOffset(0);
        scrollDownLayout.setIsSupportExit(true);
        scrollDownLayout.setAllowHorizontalScroll(true);
        scrollDownLayout.setOnScrollChangedListener(mOnScrollChangedListener);

        scrollDownLayout.setToExit();

        scrollDownLayout.getBackground().setAlpha(0);

    }


    private float lastBearing = 0;

    private void startIvCompass(float bearing) {
        bearing = 360 - bearing;
        rotateAnimation = new RotateAnimation(lastBearing, bearing, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setFillAfter(true);

        ivCompass.startAnimation(rotateAnimation);
        lastBearing = bearing;
    }

    private void stopIvCompass(float bearing) {
//        rotateAnimation.pau
    }

    private void setHeadMargin(boolean needhead) {


        LogUtil.d(TAG, "onCreate:setheadmargin "+ NotchScreenUtils.getIntchHeight(mainActivity));
        LogUtil.d(TAG, "setHeadMargin: "+(int) DensityUtils.dp2px(mainActivity,NotchScreenUtils.getIntchHeight(mainActivity)));
        ViewUtils.setMargins(llSearch,0, (int) DensityUtils.dp2px(mainActivity,NotchScreenUtils.getIntchHeight(mainActivity)),0,0);
        if(needhead){
            if(NotchScreenUtils.getIntchHeight(mainActivity)!=20 && isXiaomiDevice()){

                scrollDownLayout.setPadding(0, (int) DensityUtils.dp2px(mainActivity,NotchScreenUtils.getIntchHeight(mainActivity)+35),0,0);
            }else{
                scrollDownLayout.setPadding(0, (int) DensityUtils.dp2px(mainActivity,NotchScreenUtils.getIntchHeight(mainActivity)),0,0);
            }

        }else{
                scrollDownLayout.setPadding(0, (int) DensityUtils.dp2px(mainActivity,NotchScreenUtils.getIntchHeight(mainActivity)),0,0);

        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
//        bigMaxOffset = (int) (ScreenUtil.getScreenHeight(mainActivity) * 0.68);
//        smallMaxOffset = (int) (ScreenUtil.getScreenHeight(mainActivity) * 0.29);
        LogUtil.d(TAG, "onActivityCreated: big"+bigMaxOffset);
        LogUtil.d(TAG, "onActivityCreated: second:"+smallMaxOffset);
        setHeadMargin(true);

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
        setScaleOptions();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(REFRESHCARNUMBER);
        intentFilter.addAction(LocationManager.PROVIDERS_CHANGED_ACTION);
        intentFilter.addAction(BROADCASTACTIONSEARCHPARK);
        intentFilter.addAction(BROADCASTMAINTOMAPENTRANCE);
        intentFilter.addAction(BROADCASTAPPOINTNOW);
        intentFilter.addAction(BROADCASTGAODE);
        intentFilter.addAction(LOCATIONPERMISSIONSUCCESS);
        mainActivity.registerReceiver(mapReceiver, intentFilter);
        initLocationOption();
        initBottomView();
    }

    private void setScaleOptions() {
        WindowManager windowManager = getActivity().getWindowManager();
        Display display = windowManager.getDefaultDisplay();

        screenHeight = display.getHeight();

        if (screenHeight < 1281) {
            CONFIRM_SCALE_MULTIPLE = SCREEN_SCALE_MULTIPLE_SMALL;
            CONFIRM_CLICK_SCREEN_SCALE = CLICK_SCREEN_SCALE_SMALL;

        } else {
            CONFIRM_CLICK_SCREEN_SCALE = CLICK_SCREEN_SCALE_LARGE;
            CONFIRM_SCALE_MULTIPLE = SCREEN_SCALE_MULTIPLE_LARGE;
        }

    }

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


    private static final String TAG = "MapFragment";


    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        mainActivity.unregisterReceiver(mapReceiver);


    }

    @Override
    public void onStop() {
        //取消注册传感器监听

        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();

        if (mSensorHelper != null) {
            mSensorHelper.registerSensorListener();
        } else {
            mSensorHelper = new SensorEventHelper(getActivity());
            if (mSensorHelper != null) {
                mSensorHelper.registerSensorListener();

                if (mSensorHelper.getCurrentMarker() == null && mLocMarker != null) {
                    mSensorHelper.setCurrentMarker(mLocMarker);
                }
            }
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
        if (mSensorHelper != null) {
            mSensorHelper.unRegisterSensorListener();
            mSensorHelper.setCurrentMarker(null);
            mSensorHelper = null;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    private boolean isOnstart;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // 相当于onResume()方法
            if (getActivity() != null) {
                isOnstart = true;
                checkGpsOpen();
            }

        } else {
            isOnstart = false;
            // 相当于onpause()方法
        }
    }

    private void checkGpsOpen() {
        LocationManager locationManager = (LocationManager)
                getActivity().getSystemService(Context.LOCATION_SERVICE);
        boolean providerEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!providerEnabled) {
//            showOpenGpsDialog();
        }
    }



    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        LogUtil.d(TAG, "onLocationChanged: " + amapLocation.getCity());
//        amapLocation.setLatitude(34.8208900000);
//        amapLocation.setLongitude(113.6358800000);

        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {

                Intent intent = new Intent();
                intent.setAction(BROADCASTNOWLOCATION);
                intent.putExtra(NOWLOCATIONLAT, amapLocation.getLatitude());
                intent.putExtra(NOWlOCATIONlON, amapLocation.getLongitude());
                getActivity().sendBroadcast(intent);
                amapLocation.getCity();

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
                scaleMap(nowLocation);
//                mainActivity.hidProgressDialog();
                isFisrtLocate = false;


                if (yascnParkIndexViewModel != null) {
                    yascnParkIndexViewModel.getYascnParkData();
                }
                nowLocation = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
                scaleLargeMap(nowLocation);

                startLocateRefresh(false);

            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                LogUtil.e(TAG, "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
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

    public void startLocation() {


        if (mlocationClient != null) {
            mlocationClient.startLocation();
            startLocateRefresh(true);
        }
    }

    @OnClick({R.id.iv_relocate})
    public void onClick() {

        showHideBotomView(false);
        startLocation();


    }


    private void startLocateRefresh(boolean isRefresh) {
        LogUtil.d(TAG, "startLocateRefresh: "+isRefresh);
        if (isRefresh) {
            ivRelocate.setImageResource(R.drawable.icon_refresh_gray);
            ivRelocate.setBackgroundResource(R.drawable.white_circle);
            startRefreshAnim();
        } else {
            ivRelocate.setImageResource(R.drawable.icon_loc_black);
            ivRelocate.setBackgroundColor(Color.TRANSPARENT);
            stopRefreshAnim();

        }

    }

    private boolean hasNetWrokedFailed = false;

    @Override
    public void netWordIsFailed() {
        super.netWordIsFailed();
        hasNetWrokedFailed = true;
    }

    @Override
    public void netWorkIsSuccess() {
        super.netWorkIsSuccess();
        if (hasNetWrokedFailed) {
            showHideBotomView(false);
            startLocation();
            hasNetWrokedFailed = false;
        }


    }


    public void showHideBotomView(boolean isVisible) {
        LogUtil.d(TAG, "showHideBotomView: isVisible" + isVisible);


        if (isVisible) {
            LogUtil.d(TAG, "showHideBotomView: "+smallMaxOffset);
            if(smallMaxOffset==0){
                setHidePointBottomView(selectedObjectBean);
//        smallMaxOffset = (int) (ScreenUtil.getScreenHeight(mainActivity) * 0.29);
            }else{
                scrollDownLayout.setClickable(true);
                nowMaxOffset = smallMaxOffset;
                scrollDownLayout.setMaxOffset(nowMaxOffset);
                scrollDownLayout.invalidate();
                scrollDownLayout.setToOpen();

            }
            if(bigMaxOffset==0){
                bigMaxOffset = (int) (ScreenUtil.getScreenHeight(mainActivity) * 0.68);
            }



        } else {
            smallMaxOffset=0;
            bigMaxOffset = 0;
            scrollDownLayout.setClickable(false);
            scrollDownLayout.scrollToExit();

        }
    }



    @OnClick({R.id.tv_appoint, R.id.tv_park_detail, R.id.ll_search, R.id.iv_to_navi, R.id.iv_zoom_large, R.id.iv_zoom_small, R.id.rl_scale_loc_compass, R.id.iv_refresh})
    public void onClick(View view) {
        Intent intent;
        CameraPosition cameraPosition;
        float mapZoom;
        LatLng mapTarget;
        switch (view.getId()) {
            case R.id.tv_appoint:
                startAppointPark();


                break;
            case R.id.tv_park_detail:
                if (selectedObjectBean != null) {
                }


                intent = new Intent(getActivity(), ParkDetailActivity.class);
                intent.putExtra(SELECTPARKDETAILBEAN, selectedObjectBean.getPARKING_ID());

                getActivity().startActivity(intent);

                break;
            case R.id.ll_search:
                intent = new Intent(getActivity(), SearchGaodeActivity.class);
                getActivity().startActivity(intent);


                break;
            case R.id.iv_to_navi:
//                T.showShort(getActivity(),"tonavi");
                int size = selectedObjectBean.getET_LIST().size();

                if (size == 0) {
                    skipToNavi(StringUtils.emptyParseDouble(selectedObjectBean.getLAT()), StringUtils.emptyParseDouble(selectedObjectBean.getLNG()), "", selectedObjectBean.getNAME());
                } else if (size == 1) {
                    showEntrance(selectedObjectBean.getPARKING_ID());
                    skipToNavi(StringUtils.emptyParseDouble(selectedObjectBean.getET_LIST().get(0).getLAT()), StringUtils.emptyParseDouble(selectedObjectBean.getET_LIST().get(0).getLNG()), "", selectedObjectBean.getNAME() + selectedObjectBean.getET_LIST().get(0).getNAME());

                } else {
                    showEntrance(selectedObjectBean.getPARKING_ID());
                }


                break;

            case R.id.iv_zoom_large:
                cameraPosition = aMap.getCameraPosition();
                mapZoom = cameraPosition.zoom;
                mapTarget = cameraPosition.target;
                scaleLargeMap(mapTarget, ++mapZoom);
                break;
            case R.id.iv_zoom_small:

                cameraPosition = aMap.getCameraPosition();
                mapZoom = cameraPosition.zoom;
                mapTarget = cameraPosition.target;

                scaleLargeMap(mapTarget, --mapZoom);
                break;
            case R.id.rl_scale_loc_compass:
                break;

            case R.id.iv_refresh:
                showHideBotomView(false);
                startLocation();
                break;


        }
    }

    /**
     * 开始预约
     */
    private void startAppointPark() {
        if(selectedObjectBean==null){
            return;
        }
        Intent intent;//预约
        userId = (String) SPUtils.get(getActivity(), AppContants.KEY_USERID, "");
        if (LoginStatusUtils.isLogin(getActivity())) {
            intent = new Intent(getActivity(), AppointActivity.class);
            intent.putExtra(PARKNAMETRANS, selectedObjectBean.getNAME());
            intent.putExtra(PARKFREENUMS, selectedObjectBean.getFREE_NUM());
            intent.putExtra(PARKID, selectedObjectBean.getPARKING_ID());
            startActivity(intent);

        } else {
           mainActivity.showInputPhoneDialog();
        }
    }

    Animation rotate;

    private void startRefreshAnim() {
        rotate = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_anim);//创建动画
        rotate.setInterpolator(new LinearInterpolator());//设置为线性旋转
        rotate.setFillAfter(!rotate.getFillAfter());
        ivRelocate.startAnimation(rotate);
    }

    private void stopRefreshAnim() {

        ivRelocate.clearAnimation();
    }


    private boolean hasMovedMap = false;
    private boolean hasFindPark = false;

    private void showEntrance(String parkId) {
//        selectedObjectBean
        for (int i = 0; i < yascnExportMarkers.size(); i++) {
            YascnParkListBean.ObjectBean.ETLISTBean etlistBean = (YascnParkListBean.ObjectBean.ETLISTBean) yascnExportMarkers.get(i).getObject();
//            LogUtil.d(TAG, "showEntrance:"+etlistBean.getPARKING_ID());
            if (parkId.equals(etlistBean.getPARKING_ID())) {
                hasFindPark = true;

                if (!hasMovedMap) {

//                    double[] locats = GpsTransUtils.bd09_To_Gcj02(getActivity(), StringUtils.emptyParseDouble(etlistBean.getLAT()), StringUtils.emptyParseDouble(etlistBean.getLNG()));
                    double[] transLocation = new double[]{StringUtils.emptyParseDouble(etlistBean.getLAT()), StringUtils.emptyParseDouble(etlistBean.getLNG())};
//                    double[] transLocation = GpsTransUtils.bd09_To_Gcj02(getActivity(), StringUtils.emptyParseDouble(etlistBean.getLAT()), StringUtils.emptyParseDouble(etlistBean.getLNG()));
                    scaleLargeMap(new LatLng(transLocation[0], transLocation[1]));
                    hasMovedMap = true;
                }
                yascnExportMarkers.get(i).setVisible(true);
                tvNaviHintText.setText(StringUtils.getRString(getActivity(), R.string.start_navi_hint));
            } else {

                if (!hasFindPark && i == (yascnExportMarkers.size() - 1)) {
                    showSelectPark(parkId);
                    tvNaviHintText.setText(StringUtils.getRString(getActivity(), R.string.start_navi_park_hint));
                }
                yascnExportMarkers.get(i).setVisible(false);
            }
        }
        hasFindPark = false;

        hasMovedMap = false;


//        tvNaviHintText.setText();
        showHideHint();

        showHideBotomView(false);

    }


    private void showHideHint() {
        llNaviHint.setVisibility(View.VISIBLE);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                /**
                 *要执行的操作
                 *
                 */
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        llNaviHint.setVisibility(View.GONE);
                    }
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 3000);//3秒后执行TimeTask的run方法
    }

    /**
     * 预约对话框
     */
    private void showAppointDailog() {
        LogUtil.d(TAG, "showAppointDailog: " + userId);




    }

    private void dismissDialog() {
        if (appointDialog != null) {
            if (appointDialog.isShowing()) {
                appointDialog.dismiss();
            }
        }
    }


    @Override
    public void serverError(String errorMsg) {
        T.showShort(mainActivity, errorMsg);

    }

    @Override
    public void setparkCommentData(ParkComment parkComment) {

    }

    @Override
    public void setParkPointResult(ParkPointBean parkPointBean) {

        String pointResultFlag = parkPointBean.getObject().getFlag();
        int pointResultIndex = StringUtils.emptyParseInt(pointResultFlag);
        Intent intent = new Intent();
        intent.setAction(AppContants.REFLESHORDER);
        getActivity().sendBroadcast(intent);
        mListener.switchFragment(0);
        dismissDialog();
        T.showShort(getActivity(), PARKPOINTRESULT[pointResultIndex]);

    }






    private boolean isfirstClickMarker = true;
    @Override
    public void setYascnParkData(final YascnParkListBean yascnParkListBean) {
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


        AMap.OnMarkerClickListener listener = new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (yascnMarkers.contains(marker)) {
                    selectedObjectBean = (YascnParkListBean.ObjectBean) marker.getObject();
                    if(isfirstClickMarker){
                        showSelectPark(selectedObjectBean, marker);
//                        showSelectPark(selectedObjectBean, marker);
                        isfirstClickMarker = false;
                    }else{
                        showSelectPark(selectedObjectBean, marker);
                    }


                } else if (yascnExportMarkers.contains(marker)) {

                    //nowlocation
                    YascnParkListBean.ObjectBean.ETLISTBean etlistBean = (YascnParkListBean.ObjectBean.ETLISTBean) marker.getObject();
                    String parkName = marker.getSnippet();

                    skipToNavi(marker.getPosition().latitude, marker.getPosition().longitude, "", parkName + etlistBean.getNAME());


                }

                return true;
            }
        };


        aMap.setOnMarkerClickListener(listener);


    }

    private double aimLat;
    private double aimLon;
    private String aimName;

    private void skipToNavi(double aimLat, double aimLon, String exportId, String aimName) {
        boolean isbaiduAvailible = false;
        boolean isGaodeAvailible = false;
        this.aimLat = aimLat;
        this.aimLon = aimLon;
        this.aimName = aimName;


        isbaiduAvailible = MapUtils.isAppAvilible(getActivity(), "com.baidu.BaiduMap");
        isGaodeAvailible = MapUtils.isAppAvilible(getActivity(), "com.autonavi.minimap");

        if (!isbaiduAvailible && !isGaodeAvailible) {
            Intent intent = new Intent(getActivity(), BNDemoMainActivity.class);
            intent.putExtra(NOWlOCATIONlAT, nowLocation.latitude);
            intent.putExtra(NOWlOCATIONlON, nowLocation.longitude);
//                intent.putExtra(EXPORTID, exportId);
            intent.putExtra(MARKERlOCATIONlAT, aimLat);
            intent.putExtra(MARKERlOCATIONlon, aimLon);
//
//
            getActivity().startActivity(intent);
        } else {

            naviChooseDialog = new NaviChooseDialog();
            Bundle bundle = new Bundle();
            bundle.putBoolean(ISBAIDUNAVIAVAILABLE, isbaiduAvailible);
            bundle.putBoolean(ISGAODENAVIAVAILABLE, isGaodeAvailible);
            naviChooseDialog.setArguments(bundle);
            naviChooseDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomDialogStyle);
            naviChooseDialog.setOnChooseNaviListener(this);
            naviChooseDialog.show(getChildFragmentManager(), "zhifuDialog");
        }


    }

    private NaviChooseDialog naviChooseDialog;


    private void showSelectPark(YascnParkListBean.ObjectBean parkBean, Marker marker) {

        smallMaxOffset = 0;
        scaleLargeMap(marker.getPosition());


        selectedObjectBean = parkBean;
        setHidePointBottomView(parkBean);

        jumpPoint(marker);
    }

    String parkId;
    ParkCommentContract.Presenter commentPresenter;
    int parkFreeNUm;


    @BindView(R.id.ll_error)
    LinearLayout llError;


    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.ll_nodata)
    LinearLayout llNodata;
    @BindView(R.id.iv_empty_view)
    ImageView ivEmptyView;

    private void setHidePointBottomView(YascnParkListBean.ObjectBean objectBean) {


        noScrollLinearLayoutManager = new NoScrollLinearLayoutManager(getActivity());

        rvContent.setLayoutManager(noScrollLinearLayoutManager);

        View mainRvContentHeader = View.inflate(mainActivity, R.layout.layout_map_bottom_header, null);
        TextView tvParkName = (TextView) mainRvContentHeader.findViewById(R.id.tv_park_name);
        TextView tvAddress = (TextView) mainRvContentHeader.findViewById(R.id.tv_address);
        LinearLayout llNavi = (LinearLayout) mainRvContentHeader.findViewById(R.id.ll_navi);
        final LinearLayout llFeeDetail = (LinearLayout)mainRvContentHeader.findViewById(R.id.ll_fee_detail);
        TextView tvDistance = (TextView) mainRvContentHeader.findViewById(R.id.tv_distance);
        TextView tvFreeNum = (TextView) mainRvContentHeader.findViewById(R.id.tv_free_num);
        LinearLayout llAppoint = (LinearLayout)mainRvContentHeader.findViewById(R.id.ll_appoint);
        RecyclerView rvFeeDetail = (RecyclerView)mainRvContentHeader.findViewById(R.id.rv_fee_detail);
        rvFeeDetail.setLayoutManager(new LinearLayoutManager(mainActivity));
        final LinearLayout llFirstMax = (LinearLayout) mainRvContentHeader.findViewById(R.id.ll_first_max);
        final LinearLayout llSecondMax = (LinearLayout) mainRvContentHeader.findViewById(R.id.ll_second_max);


        LogUtil.d(TAG, "setHidePointBottomView: screen"+ScreenUtils.getScreenHeight(mainActivity));


        ViewTreeObserver fisrMaxTreeObserver = llFirstMax.getViewTreeObserver();
        fisrMaxTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                llFirstMax.getHeight();
                LogUtil.d(TAG, "setHidePointBottomView: first"+llFirstMax.getHeight());


                llFeeDetail.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        smallMaxOffset = llFirstMax.getHeight()+llFeeDetail.getHeight()+DensityUtils.dp2px(mainActivity,16);
                        LogUtil.d(TAG, "onGlobalLayout:smallmaxoffset "+smallMaxOffset);
                        llFeeDetail.getViewTreeObserver().removeOnGlobalLayoutListener(this);//Added in API level 16
                    }
                });


                llFirstMax.getViewTreeObserver().removeOnGlobalLayoutListener(this);//Added in API level 16

            }
        });

        ViewTreeObserver secondMaxTreeObserver = llSecondMax.getViewTreeObserver();
        secondMaxTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                llSecondMax.getHeight();
                bigMaxOffset = llSecondMax.getHeight()+DensityUtils.dp2px(mainActivity,102);


                llSecondMax.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                LogUtil.d(TAG, "onGlobalLayout: "+DensityUtils.dp2px(mainActivity,108));

            }
        });




        tvParkName.setText(objectBean.getNAME());
        tvAddress.setText(objectBean.getADDRESS());
        double[] locats = new double[]{StringUtils.emptyParseDouble(objectBean.getLAT()), StringUtils.emptyParseDouble(objectBean.getLNG())};
        tvDistance.setText(MapUtils.calculateDistance(nowLocation, new LatLng(locats[0], locats[1])));
        setParkFreeNum(tvFreeNum, objectBean.getFREE_NUM());
//        StringBuffer costDetailSb = new StringBuffer();
//        for (int i = 0; i < objectBean.getCostDetail().size(); i++) {
//            costDetailSb.append((i + 1) + objectBean.getCostDetail().get(i) + "\n");
//        }

        tvBarTitle.setText(objectBean.getNAME());

        rvFeeDetail.setAdapter(new RvMapFeeDetailAdapter(mainActivity,objectBean.getCostDetail()));




        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mainRvContentHeader.setLayoutParams(layoutParams);
        commentPresenter = new ParkCommentPresenterImpl(this, refreshLayout, rvContent, llError, llNodata, objectBean.getPARKING_ID(), mainRvContentHeader,scrollDownLayout);

        commentPresenter.queryList();


        setEmptyViewBgAndImg(mainActivity, false, llNodata, ivEmptyView);
//        showHideBotomView(true);
        llNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = selectedObjectBean.getET_LIST().size();

                if (size == 0) {
                    skipToNavi(StringUtils.emptyParseDouble(selectedObjectBean.getLAT()), StringUtils.emptyParseDouble(selectedObjectBean.getLNG()), "", selectedObjectBean.getNAME());
                } else if (size == 1) {
                    showEntrance(selectedObjectBean.getPARKING_ID());
                    skipToNavi(StringUtils.emptyParseDouble(selectedObjectBean.getET_LIST().get(0).getLAT()), StringUtils.emptyParseDouble(selectedObjectBean.getET_LIST().get(0).getLNG()), "", selectedObjectBean.getNAME() + selectedObjectBean.getET_LIST().get(0).getNAME());

                } else {
                    showEntrance(selectedObjectBean.getPARKING_ID());
                }

            }
        });

        llAppoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAppointPark();
            }
        });





    }



    private void initToolbar() {



        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(scrollDownLayout!=null){
                    scrollDownLayout.scrollToExit();
                    setBarColor(R.color.transparent);

                }
            }
        });







    }


    private Bitmap lastMarkerBitMap;
    private Marker lastMarker;

    /**
     * marker点击时跳动一下
     */
    public void jumpPoint(final Marker marker) {
        if (lastMarker != null) {
            YascnParkListBean.ObjectBean lastMarkerParkBean = (YascnParkListBean.ObjectBean) lastMarker.getObject();
            YascnParkListBean.ObjectBean nowParkBean = (YascnParkListBean.ObjectBean) marker.getObject();
            if (lastMarkerParkBean == nowParkBean) {
                return;
            }

        }
        final Bitmap bitMap = marker.getIcons().get(0).getBitmap();// BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
        final int width = bitMap.getWidth();
        final int height = bitMap.getHeight();
        final float t = PARKSCALENUM;
        if (lastMarker != null) {
            String title = lastMarker.getTitle();
            switch (title) {
                case MARKERTITLERECOMMAND:
                    setPoiMarkerIcon(lastMarker, R.drawable.icon_park_marker_recommand);
                    break;
                case MARKERTITLEBLUE:
                    setPoiMarkerIcon(lastMarker, R.drawable.icon_park_marker_blue);
                    break;

                case MARKERTITLERED:
                    setPoiMarkerIcon(lastMarker, R.drawable.icon_park_marker_red);
                    break;
            }

        }


        // 计算缩放比例
        int scaleWidth = (int) (t * width);
        int scaleHeight = (int) (t * height);


        // 使用最原始的图片进行大小计算
        marker.setIcon(BitmapDescriptorFactory.fromBitmap(Bitmap
                .createScaledBitmap(bitMap, scaleWidth,
                        scaleHeight, true)));
        marker.setVisible(true);

        // 因为替换了新的图片，所以把旧的图片销毁掉，注意在设置新的图片之后再销毁
        if (lastMarkerBitMap != null
                && !lastMarkerBitMap.isRecycled()) {
            lastMarkerBitMap.recycle();
        }

        //第一次得到的缩放图片，在第二次回收，最后一次的缩放图片，在动画结束时回收
        ArrayList<BitmapDescriptor> list = marker.getIcons();
        if (list != null && list.size() > 0) {
            // 保存旧的图片
            lastMarkerBitMap = marker.getIcons().get(0).getBitmap();
        }


        lastMarker = marker;

    }

    private YascnParkListBean.ObjectBean findRecommandPark(YascnParkListBean yascnParkListBean) {
        LinkedList<YascnParkListBean.ObjectBean> yascnParkBeans = new LinkedList<>();
        YascnParkListBean.ObjectBean recommandPark = null;
        if (yascnParkListBean != null) {
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


                }


            }
        }

        return recommandPark;

    }




    private void addBlueRedParkMarker(boolean isBlue, YascnParkListBean.ObjectBean yascnObjectBean, boolean isRecommand) {

        int parkFreeNum = StringUtils.emptyParseInt(yascnObjectBean.getFREE_NUM());


//        double[] locats = GpsTransUtils.bd09_To_Gcj02(geonScrollProgressChangedtActivity(), StringUtils.emptyParseDouble(yascnObjectBean.getLAT()), StringUtils.emptyParseDouble(yascnObjectBean.getLNG()));
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

//        marker.setInfoWindowEnable(false);
        yascnMarkers.add(marker);
        yascnParkId.add(yascnObjectBean.getPARKING_ID());


        for (int i = 0; i < yascnObjectBean.getET_LIST().size(); i++) {

            YascnParkListBean.ObjectBean.ETLISTBean etlistBean = yascnObjectBean.getET_LIST().get(i);
            addBlueYascnParkEntranceMarker(parkFreeNum < PARKFREENUMREDLINE ? false : true, etlistBean, yascnObjectBean.getNAME());
        }

    }

    private void addBlueYascnParkEntranceMarker(boolean isBlue, YascnParkListBean.ObjectBean.ETLISTBean etlistBean, String name) {

        double[] locats = new double[]{StringUtils.emptyParseDouble(etlistBean.getLAT()), StringUtils.emptyParseDouble(etlistBean.getLNG())};
//        double[] locats = GpsTransUtils.bd09_To_Gcj02(getActivity(), StringUtils.emptyParseDouble(etlistBean.getLAT()), StringUtils.emptyParseDouble(etlistBean.getLNG()));

        if (yascnEntranceId.contains(etlistBean.getP_ENTRANCE_ID())) {
            return;
        }

        LatLng latLng = new LatLng(locats[0], locats[1]);
        final Marker marker = aMap.addMarker(new MarkerOptions().
                position(latLng));
        marker.setClickable(true);
        setPoiMarkerIcon(marker, isBlue ? R.drawable.icon_park_entrance_blue : R.drawable.icon_park_entrance_red);

        marker.setObject(etlistBean);
        marker.setInfoWindowEnable(false);
        marker.setVisible(false);
        marker.setSnippet(name);

        yascnExportMarkers.add(marker);
        yascnEntranceId.add(etlistBean.getP_ENTRANCE_ID());

    }

    private void setPoiMarkerIcon(Marker marker, int drawableId) {


        View markericonView = View.inflate(getActivity(), R.layout.map_marker_icon_layout, null);
        ImageView ivIcon = (ImageView) markericonView.findViewById(R.id.iv_icon);
        ivIcon.setImageResource(drawableId);

        BitmapDescriptor markerIcon = BitmapDescriptorFactory
                .fromView(markericonView);
        marker.setIcon(markerIcon);


    }


    @Override
    public void showProgress() {
        if (mainActivity != null) {
            mainActivity.showProgressDialog(StringUtils.getRString(mainActivity, R.string.loadingProgress));
        }
    }

    @Override
    public void hideProgress() {
        if (mainActivity != null) {
            mainActivity.hidProgressDialog();
        }
    }

    float bearing = 0.0f;  // 地图默认方向

    public void scaleMap(LatLng nowLocation) {
        cameraPosition = aMap.getCameraPosition();
        aMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(nowLocation, CONFIRM_SCALE_MULTIPLE, cameraPosition.tilt, bearing)));
    }

    public void scaleLargeMap(LatLng nowLocation) {

        cameraPosition = aMap.getCameraPosition();
        aMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(nowLocation, CONFIRM_CLICK_SCREEN_SCALE, cameraPosition.tilt, bearing)));
    }

    public void scaleLargeMap(LatLng nowLocation, float scaleValue) {
        cameraPosition = aMap.getCameraPosition();
        aMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(nowLocation, scaleValue, cameraPosition.tilt, bearing)));

    }


    @Override
    public void choseNavi(int type) {
        Intent intent;
        switch (type) {
            case NAVITAPEBAIDU:
                double[] transBaidu = GpsTransUtils.gcj02_To_Bd09(aimLat, aimLon);

                MapUtils.turnToBaiduNavi(getActivity(), transBaidu[0], transBaidu[1], aimName);
                naviChooseDialog.dismiss();


                break;

            case NAVITYPEGAODE:
                MapUtils.turnToGaodeNavi(getActivity(), aimLat, aimLon, aimName);
                naviChooseDialog.dismiss();
                break;

            case NAVITYPELOCAL:

                intent = new Intent(getActivity(), BNDemoMainActivity.class);
                intent.putExtra(NOWlOCATIONlAT, nowLocation.latitude);
                intent.putExtra(NOWlOCATIONlON, nowLocation.longitude);
//                intent.putExtra(EXPORTID, exportId);
                intent.putExtra(MARKERlOCATIONlAT, aimLat);
                intent.putExtra(MARKERlOCATIONlon, aimLon);
//
//
                getActivity().startActivity(intent);
                naviChooseDialog.dismiss();

                break;
        }

    }






    private boolean isVisibleToUser;


    @Override
    public void onDestroyView() {
        super.onDestroyView();


    }
}
