package com.yascn.smartpark.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yascn.smartpark.R;
import com.yascn.smartpark.activity.DiyParkLicenseActivity;
import com.yascn.smartpark.adapter.CarAdapter;
import com.yascn.smartpark.base.BaseActivity;
import com.yascn.smartpark.bean.CarNumber;
import com.yascn.smartpark.bean.NumberList;
import com.yascn.smartpark.bean.SetDefaultNo;
import com.yascn.smartpark.bean.Userinfo;
import com.yascn.smartpark.contract.MyCarContract;
import com.yascn.smartpark.dialog.DefaultDialog;
import com.yascn.smartpark.dialog.DeleteCarNumberDialog;
import com.yascn.smartpark.presenter.MyCarPresenter;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.SPUtils;
import com.yascn.smartpark.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yascn.smartpark.utils.AppContants.AUTOPAYDATAUPDATEBROADCAST;
import static com.yascn.smartpark.utils.AppContants.AUTOPAYPARKID;
import static com.yascn.smartpark.utils.AppContants.AUTOPAYPARKSTATE;
import static com.yascn.smartpark.utils.AppContants.ITEM_CAR_CONTENT;
import static com.yascn.smartpark.utils.AppContants.REFRESHDEFULTNUMBER;
import static com.yascn.smartpark.utils.AppContants.REFRESHDEFULTNUMBERID;
import static com.yascn.smartpark.utils.AppContants.SETDEFULTCARLICENSE;

/**
 * Created by YASCN on 2017/8/29.
 */

public class MyCarActivity extends BaseActivity implements MyCarContract.MyCarView, CarAdapter.OnClickListener {

    @BindView(R.id.carRecyclerView)
    RecyclerView carRecyclerView;

    @BindView(R.id.ll_error)
    LinearLayout error;


    @BindView(R.id.content)
    LinearLayout content;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;


    private CarAdapter carAdapter;
    private List<CarNumber> carNumbers;

    private DefaultDialog dialog;
    private int deletePosition;
    private String defaultNumberID;
    private String userID;
    private int defaultPosition = 5;

    private MyCarContract.MyCarPresenter myCarPresenter;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == AppContants.REFRESH_MYCAR) {
                carAdapter.notifyDataSetChanged();
            }
        }
    };

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(AppContants.REFRESHCARNUMBER)) {
                LogUtil.d(AppContants.TAG, "刷新车牌");
                myCarPresenter.numberList(userID);
            } else if (intent.getAction().equals(AUTOPAYDATAUPDATEBROADCAST)) {
                String parkId = intent.getStringExtra(AUTOPAYPARKID);
                String state = intent.getStringExtra(AUTOPAYPARKSTATE);
                LogUtil.d(TAG, "onReceive: " + parkId + ":" + state);
                LogUtil.d(TAG, "onReceive: ");

                for (int i = 0; i < carNumbers.size(); i++) {
                    if (carNumbers.get(i).getViewType() == ITEM_CAR_CONTENT) {
                        if (carNumbers.get(i).getNumberListBean().getLICENSE_PLATE_ID().equals(parkId)) {
                            carNumbers.get(i).getNumberListBean().setAUTOPAY(state);
                        }
                    }


                }
//              carAdapter.notifyDataSetChanged();
            }else if(intent.getAction().equals(REFRESHDEFULTNUMBER)){
                if(myCarPresenter!=null&&!TextUtils.isEmpty(userID)){
                    String carlicenseId =  intent.getStringExtra(REFRESHDEFULTNUMBERID);
                    setDefaultCarLicense(carlicenseId);
                }

            }
        }
    };


    private boolean isNetFailed = false;
    private MenuItem defultLicenseButton;

    @Override
    public void netWordIsFailed() {
        super.netWordIsFailed();
        isNetFailed = true;
    }

    @Override
    public void netWorkIsSuccess() {
        super.netWorkIsSuccess();

        if (myCarPresenter != null && isNetFailed && !TextUtils.isEmpty(userID)) {
            myCarPresenter.numberList(userID);

        }

    }
    boolean isPullRefresh = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
        ButterKnife.bind(this);

        initTitle(getResources().getString(R.string.car));

        showView(AppContants.SUCCESS);

        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                isPullRefresh = true;
                if (myCarPresenter != null && !TextUtils.isEmpty(userID)) {
                    myCarPresenter.numberList(userID);


                }

//                refreshlayout.finishRefresh(2000);

            }
        });



    }


    @Override
    public void showView(int type) {
        switch (type) {
            case AppContants.ERROR:
                error.setVisibility(View.VISIBLE);
                content.setVisibility(View.INVISIBLE);
                hideDialog();
                break;
            case AppContants.SUCCESS:
                error.setVisibility(View.INVISIBLE);
                content.setVisibility(View.VISIBLE);
                hideDialog();
                break;
            case AppContants.PROGRESS:
                error.setVisibility(View.INVISIBLE);
//                content.setVisibility(View.INVISIBLE);
                showDialog();
                break;
        }
    }

    @Override
    public void setCarAdapterForLicenseListBean(List<SetDefaultNo.ObjectBean.LicenseListBean> numberList) {
        carNumbers = new ArrayList<CarNumber>();

        for (int i = 0; i < numberList.size(); i++) {
            CarNumber carNumber = new CarNumber();
            carNumber.setViewType(ITEM_CAR_CONTENT);
            carNumber.setSetting(false);
            Userinfo.ObjectBean.NumberListBean numberListBean = new Userinfo.ObjectBean.NumberListBean();
            SetDefaultNo.ObjectBean.LicenseListBean licenseListBean = numberList.get(i);
            numberListBean.setIS_DEFAULT(licenseListBean.getIS_DEFAULT());
            numberListBean.setLICENSE_PLATE_ID(licenseListBean.getLICENSE_PLATE_ID());
            numberListBean.setNUMBER(licenseListBean.getNUMBER());
            numberListBean.setIS_AUTH(licenseListBean.getIS_AUTH());
            LogUtil.d(TAG, "setCarAdapterForLicenseListBean: " + licenseListBean.getIS_AUTH());

            numberListBean.setAUTOPAY(licenseListBean.getAUTOPAY());
            carNumber.setNumberListBean(numberListBean);
            carNumbers.add(carNumber);

        }

        CarNumber carNumber3 = new CarNumber();
        carNumber3.setViewType(AppContants.ITEM_CAR_FOOTER);
        carNumbers.add(carNumber3);



        carAdapter = new CarAdapter(this, carNumbers);
        carAdapter.setOnDefaultClick(this);
        carRecyclerView.setAdapter(carAdapter);
        carRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setDefultLicenseButtonVisibility(boolean isVisible) {
        LogUtil.d(TAG, "setDefultLicenseButtonVisibility: " + defultLicenseButton);
        if (defultLicenseButton != null) {
            defultLicenseButton.setVisible(isVisible);
            LogUtil.d(TAG, "setDefultLicenseButtonVisibility: notnull");
        } else {
            LogUtil.d(TAG, "setDefultLicenseButtonVisibility: null");
        }
    }

    private void setDefultLicenseButtonText(String text) {
        if (defultLicenseButton != null) {
//            defultLicenseButton.setTitle(text);
            defultLicenseButton.setTitle("");
        }
    }

    private static final String TAG = "MyCarActivity";

    @Override
    public void setCarAdapterForNumberList(NumberList numberList) {

        carNumbers = new ArrayList<CarNumber>();



        if (numberList.getObject().size() == 0) {

            setDefultLicenseButtonVisibility(false);
        } else if (numberList.getObject().size() == 1) {
            defaultNumberID = numberList.getObject().get(0).getLICENSE_PLATE_ID();
            myCarPresenter.setDefault(defaultNumberID, userID);
            setDefultLicenseButtonVisibility(false);
        } else {
            setDefultLicenseButtonText(SETDEFULTCARLICENSE);
            setDefultLicenseButtonVisibility(true);
        }

        for (int i = 0; i < numberList.getObject().size(); i++) {
            CarNumber carNumber = new CarNumber();
            carNumber.setViewType(ITEM_CAR_CONTENT);
            carNumber.setSetting(false);

            Userinfo.ObjectBean.NumberListBean numberListBean = new Userinfo.ObjectBean.NumberListBean();
            NumberList.ObjectBean objectBean = numberList.getObject().get(i);
            numberListBean.setIS_DEFAULT(objectBean.getIS_DEFAULT());
            numberListBean.setLICENSE_PLATE_ID(objectBean.getLICENSE_PLATE_ID());
            numberListBean.setNUMBER(objectBean.getNUMBER());
            numberListBean.setAUTOPAY(objectBean.getAUTOPAY());
            numberListBean.setIS_AUTH(objectBean.getIS_AUTH());
            carNumber.setMcardList(objectBean.getMCARD_LIST());
            carNumber.setNumberListBean(numberListBean);
            carNumbers.add(carNumber);
        }

        CarNumber carNumber3 = new CarNumber();
        carNumber3.setViewType(AppContants.ITEM_CAR_FOOTER);
        carNumbers.add(carNumber3);

//        if(carAdapter==null){
            carAdapter = new CarAdapter(this, carNumbers);
            carAdapter.setOnDefaultClick(this);
            carRecyclerView.setAdapter(carAdapter);
            carRecyclerView.setLayoutManager(new LinearLayoutManager(this));

//        }else{
//            carAdapter.notifyDataSetChanged();
//        }

    }

    @Override
    public void notifyData() {
//        myCarPresenter.numberList(userID);
        carAdapter.notifyDataSetChanged();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showDialog() {
        if(isPullRefresh){
            return;
        }
        showProgressDialog(StringUtils.getRString(this, R.string.loadingProgress));
    }

    @Override
    public void hideDialog() {
        if(isPullRefresh){
            refreshLayout.finishRefresh();
            return;
        }

        hidProgressDialog();

    }

    @Override
    public void removeItem() {
        carNumbers.remove(deletePosition);
        carAdapter.notifyItemRemoved(deletePosition);
        if (carNumbers.size() == 1 && carNumbers.get(0).getViewType() == AppContants.ITEM_CAR_FOOTER) {
            setDefultLicenseButtonVisibility(false);
        } else if (carNumbers.size() == 2) {
            defaultNumberID = carNumbers.get(0).getNumberListBean().getLICENSE_PLATE_ID();
            myCarPresenter.setDefault(defaultNumberID, userID);
            setDefultLicenseButtonVisibility(false);


        } else {
            setDefultLicenseButtonVisibility(true);
            setDefultLicenseButtonText(SETDEFULTCARLICENSE);

        }
        handler.sendEmptyMessageDelayed(AppContants.REFRESH_MYCAR, AppContants.REFRESH_REMOVECAR_DELAY);

    }

    @Override
    public void onDefaultClick(Boolean isChecked, int position) {
        LogUtil.d(AppContants.TAG, "isCheckd = " + isChecked);
        LogUtil.d(AppContants.TAG, "position = " + position);

        for (int i = 0; i < carNumbers.size() - 1; i++) {

            if (carNumbers.get(i).getNumberListBean().getIS_DEFAULT().equals("1")) {
                defaultPosition = i;
            }

            if (i == position) {
                carNumbers.get(position).getNumberListBean().setIS_DEFAULT("1");
            } else {
                carNumbers.get(i).getNumberListBean().setIS_DEFAULT("0");
            }
        }

        setDefaultCarLicense(carNumbers.get(position).getNumberListBean().getLICENSE_PLATE_ID());

        handler.sendEmptyMessage(AppContants.REFRESH_MYCAR);

        LogUtil.d(AppContants.TAG, carNumbers.toString());
    }

    @Override
    public void setDefaultNotify() {
        for (int i = 0; i < carNumbers.size() - 1; i++) {
            carNumbers.get(i).setSetting(false);
            if (defaultPosition == i) {
                carNumbers.get(i).getNumberListBean().setIS_DEFAULT("1");
            } else {
                carNumbers.get(i).getNumberListBean().setIS_DEFAULT("0");
            }
        }
        defaultNumberID = "";
        notifyData();
    }

    @Override
    public void setAutoNotify() {
        LogUtil.d(AppContants.TAG, "carNumbers = " + carNumbers.toString());
        notifyData();
    }

    @Override
    public void onAddClick() {
        Intent intent = new Intent();
        intent.setClass(this, DiyParkLicenseActivity.class);
        startActivity(intent);
    }

    @Override
    public void onUnbindClick(final int position) {
        LogUtil.d(AppContants.TAG, "removePosition = " + position);
        deletePosition = position;
        DeleteCarNumberDialog deleteCarNumberDialog = new DeleteCarNumberDialog();
        deleteCarNumberDialog.setOnDeleteNumberClick(new DeleteCarNumberDialog.DeleteNumberClick() {
            @Override
            public void onDeleteNumberClick() {
                myCarPresenter.deleteCar(carNumbers.get(position).getNumberListBean().getLICENSE_PLATE_ID());
            }
        });
        deleteCarNumberDialog.show(getSupportFragmentManager(), "DeleteCarNumberDialog");
    }

    @Override
    public void onAutoPayClick(String carNumberID, String cate) {
        LogUtil.d(TAG, "onAutoPayClick: " + carNumberID + "---" + cate);
        myCarPresenter.setPay(carNumberID, cate);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_car, menu);
        defultLicenseButton = menu.getItem(0);

        userID = (String) SPUtils.get(this, AppContants.KEY_USERID, "");

        myCarPresenter = new MyCarPresenter(this);

        myCarPresenter.numberList(userID);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(AppContants.REFRESHCARNUMBER);
        intentFilter.addAction(REFRESHDEFULTNUMBER);
        intentFilter.addAction(AppContants.AUTOPAYDATAUPDATEBROADCAST);
        registerReceiver(broadcastReceiver, intentFilter);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setdufault:
//                if (item.getTitle().equals(getResources().getString(R.string.setdefault))) {
//                    item.setTitle(getResources().getString(R.string.confirm));
//                    for (CarNumber carNumber : carNumbers) {
//                        carNumber.setSetting(true);
//                    }
//                    carAdapter.notifyDataSetChanged();
//                } else {
//
//
//                    for (int i = 0; i < carNumbers.size() - 1; i++) {
//                        if (carNumbers.get(i).getNumberListBean().getIS_DEFAULT().equals("1")) {
//                            defaultNumberID = carNumbers.get(i).getNumberListBean().getLICENSE_PLATE_ID();
//                        }
//                    }
//
//                    if (TextUtils.isEmpty(defaultNumberID)) {
//                        T.showShort(this, "没有选择默认车牌");
//                        item.setTitle(getResources().getString(R.string.confirm));
//                    } else {
//                        item.setTitle(getResources().getString(R.string.setdefault));
//
////                        myCarPresenter.setDefault(defaultNumberID, userID);
//                        setDefaultCarLicense(defaultNumberID);
//                    }

//                }
                return true;
            default:
                // If we got here, the us
                return super.onOptionsItemSelected(item);
        }
    }


    private void setDefaultCarLicense(String carLicenseId){
        myCarPresenter.setDefault(carLicenseId, userID);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
        myCarPresenter.onDestroy();
    }

    @OnClick(R.id.ll_error)
    public void onViewClicked() {
        myCarPresenter.numberList(userID);
    }
}
