package com.yascn.smartpark.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.yascn.smartpark.R;
import com.yascn.smartpark.adapter.RvCarLicenseAdapter;
import com.yascn.smartpark.base.BaseActivity;
import com.yascn.smartpark.bean.CarLicense;
import com.yascn.smartpark.bean.ParkPointBean;
import com.yascn.smartpark.contract.CarLicenseContract;
import com.yascn.smartpark.contract.StartPointParkContract;

import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.RvItemClickListener;
import com.yascn.smartpark.utils.SPUtils;
import com.yascn.smartpark.utils.StringUtils;
import com.yascn.smartpark.utils.T;
import com.yascn.smartpark.viewmodel.CarLicenseViewModel;
import com.yascn.smartpark.viewmodel.StartPointViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yascn.smartpark.utils.AppContants.CARDEFULTRESERVETIME;
import static com.yascn.smartpark.utils.AppContants.PARKFREENUMS;
import static com.yascn.smartpark.utils.AppContants.PARKID;
import static com.yascn.smartpark.utils.AppContants.PARKNAMETRANS;
import static com.yascn.smartpark.utils.AppContants.PARKPOINTRESULT;
import static com.yascn.smartpark.utils.AppContants.REFRESHCARNUMBER;

public class AppointActivity extends BaseActivity implements CarLicenseContract.View, StartPointParkContract.View {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.index_toolbar)
    Toolbar indexToolbar;
    @BindView(R.id.activity_introduction)
    LinearLayout activityIntroduction;
    @BindView(R.id.tv_free_park_nums)
    TextView tvFreeParkNums;
    @BindView(R.id.tv_reserve_time)
    TextView tvReserveTime;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_car_license)
    TextView tvCarLicense;
    @BindView(R.id.iv_arrow)
    ImageView ivArrow;

    private static final String TAG = "AppointActivity";
    @BindView(R.id.tv_park_name)
    TextView tvParkName;
    @BindView(R.id.bt_appoint_now)
    Button btAppointNow;
    @BindView(R.id.ll_car_license)
    LinearLayout llCarLicense;

    private CarLicenseContract.Presenter carLicensePresenter;
    private String userId;

    public BroadcastReceiver mainReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(REFRESHCARNUMBER)) {
                carLicensePresenter.getCarLicense(userId);


            }
        }
    };
    private PopupWindow popupCarNums;
    private String phone;
    private String parkName;
    private String freeNums;
    private String parkid;
    private StartPointViewModel startPointViewModel;
    private CarLicenseViewModel carLicenseViewModel;
    //    private StartPointParkContract.Presenter startParkPointPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint);
        ButterKnife.bind(this);
        initTitle(getString(R.string.string_appoint_title));



        parkName = getIntent().getStringExtra(PARKNAMETRANS);
        freeNums = getIntent().getStringExtra(PARKFREENUMS);
        parkid = getIntent().getStringExtra(PARKID);

        userId = (String) SPUtils.get(this, AppContants.KEY_USERID, "");
        phone = (String) SPUtils.get(this, AppContants.KEY_PHONE, "");
        tvPhone.setText(phone);
        tvReserveTime.setText(CARDEFULTRESERVETIME);
        tvFreeParkNums.setText(freeNums + "个");
        tvParkName.setText(parkName);


        startPointViewModel = ViewModelProviders.of(this).get(StartPointViewModel.class);


        getLifecycle().addObserver(startPointViewModel);

        Observer<ParkPointBean> parkPointBeanObserver = new Observer<ParkPointBean>() {
            @Override
            public void onChanged(@Nullable ParkPointBean parkPointBean) {
                setParkPointResult(parkPointBean);

            }
        };

        startPointViewModel.getMutableLiveDataEntry().observe(this,parkPointBeanObserver);
        startPointViewModel.getQueryStatus().observe(this,statusObserver);



        //1
        carLicenseViewModel = ViewModelProviders.of(this).get(CarLicenseViewModel.class);

        //2
        getLifecycle().addObserver(carLicenseViewModel);

        //3
        Observer<CarLicense> carLicenseObserver = new Observer<CarLicense>() {
            @Override
            public void onChanged(@Nullable CarLicense carLicense) {
                setCarLicense(carLicense);
            }
        };

        //4

        carLicenseViewModel.getMutableLiveDataEntry().observe(this,carLicenseObserver);
        carLicenseViewModel.getQueryStatus().observe(this,statusObserver);

        if(carLicenseViewModel !=null){
            carLicenseViewModel.getCarLicense(userId);
        }




        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(REFRESHCARNUMBER);

        registerReceiver(mainReceiver, intentFilter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


        unregisterReceiver(mainReceiver);
    }

    @Override
    public void serverError(String errorMsg) {
        T.showShort(this, errorMsg);
    }

    @Override
    public void setParkPointResult(ParkPointBean parkPointBean) {
        String pointResultFlag = parkPointBean.getObject().getFlag();



        Intent intent = new Intent(AppointActivity.this, OrderActivity.class);
        startActivity(intent);

            int pointResultIndex = StringUtils.emptyParseInt(pointResultFlag);
            T.showShort(this, PARKPOINTRESULT[pointResultIndex]);

        finish();

    }

    @Override
    public void setCarLicense(final CarLicense carLicense) {


        if (carLicense.getObject().size() == 0) {
            ivArrow.setVisibility(View.GONE);

            tvCarLicense.setText(getString(R.string.string_bind_carnumber_warning));
            tvCarLicense.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AppointActivity.this, DiyParkLicenseActivity.class);
                    startActivity(intent);
                }
            });


        } else {


            for (int i = 0; i < carLicense.getObject().size(); i++) {
                if (carLicense.getObject().get(i).getIS_DEFAULT().equals("1")) {
                    tvCarLicense.setText(carLicense.getObject().get(i).getNUMBER());
                } else if (i == carLicense.getObject().size() - 1) {
                    tvCarLicense.setText(carLicense.getObject().get(0).getNUMBER());

                }
            }

            if (carLicense.getObject().size() == 1) {
                ivArrow.setVisibility(View.GONE);
            } else {
                ivArrow.setVisibility(View.VISIBLE);
                llCarLicense.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPopupWindow(carLicense);
                    }
                });

            }

        }


    }


    private void showPopupWindow(final CarLicense carLicense) {
        if (popupCarNums == null) {
            popupCarNums = new PopupWindow(this);
            popupCarNums.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            popupCarNums.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            View popupView = LayoutInflater.from(this).inflate(R.layout.layout_popupwindow_carlicense, null);
            RecyclerView rvPopName = (RecyclerView) popupView.findViewById(R.id.rv_car_license);
            rvPopName.setLayoutManager(new LinearLayoutManager(this));
            rvPopName.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            RvCarLicenseAdapter rvCarLicenseAdapter = new RvCarLicenseAdapter(this, carLicense);
            rvPopName.setAdapter(rvCarLicenseAdapter);
            popupCarNums.setContentView(popupView);
            popupCarNums.setBackgroundDrawable(new ColorDrawable(0x00000000));
            popupCarNums.showAsDropDown(tvCarLicense);
            popupCarNums.setOutsideTouchable(true);
            popupCarNums.setTouchable(true);
            popupCarNums.setFocusable(false);
            ivArrow.setImageResource(R.drawable.icon_arrow_up_gray);
            popupCarNums.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    ivArrow.setImageResource(R.drawable.icon_arrow_down_gray);
                }
            });
            rvCarLicenseAdapter.setOnItemClickListener(new RvItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    tvCarLicense.setText(carLicense.getObject().get(position).getNUMBER());
                    popupCarNums.dismiss();
                }
            });

        } else if (!popupCarNums.isShowing()) {
            popupCarNums.showAsDropDown(tvCarLicense);
            ivArrow.setImageResource(R.drawable.icon_arrow_up_gray);
        }  else {
            popupCarNums.dismiss();
        }



    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
// TODO Auto-generated method stub
        if (popupCarNums != null && popupCarNums.isShowing()) {
            popupCarNums.dismiss();
            popupCarNums = null;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void showProgress() {
        showProgressDialog(getString(R.string.loadingProgress));

    }

    @Override
    public void hideProgress() {
        hidProgressDialog();
    }

    @Override
    public Context getContext() {
        return this;
    }


    @OnClick(R.id.bt_appoint_now)
    public void onViewClicked() {
        if (!tvCarLicense.getText().toString().trim().equals(StringUtils.getRString(AppointActivity.this, R.string.string_bind_carnumber_warning))) {
            if(startPointViewModel!=null){
                startPointViewModel.startParkPoint(parkid, userId, tvCarLicense.getText().toString());
            }

        } else {
            T.showShort(AppointActivity.this, StringUtils.getRString(AppointActivity.this, R.string.string_carlicense_null));
            Intent intent = new Intent(AppointActivity.this, DiyParkLicenseActivity.class);
            startActivity(intent);

        }

    }
}
