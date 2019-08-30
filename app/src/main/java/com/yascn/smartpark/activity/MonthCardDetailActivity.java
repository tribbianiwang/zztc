package com.yascn.smartpark.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;

import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yascn.smartpark.R;
import com.yascn.smartpark.base.BaseActivity;
import com.yascn.smartpark.bean.AliRecharge;
import com.yascn.smartpark.bean.CarLicense;
import com.yascn.smartpark.bean.MonthCardDetailBean;
import com.yascn.smartpark.bean.PayResult;
import com.yascn.smartpark.bean.WxRecharge;
import com.yascn.smartpark.contract.CarLicenseContract;
import com.yascn.smartpark.contract.MonthCardDetailContract;
import com.yascn.smartpark.contract.MonthCardPayInfoContract;
import com.yascn.smartpark.dialog.CarLicenseListDialog;
import com.yascn.smartpark.dialog.ChooseCardTypeDialog;

import com.yascn.smartpark.presenter.MonthCardPayInfoPresenter;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.ClickUtil;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.SPUtils;
import com.yascn.smartpark.utils.StringUtils;
import com.yascn.smartpark.utils.T;
import com.yascn.smartpark.viewmodel.CarLicenseViewModel;
import com.yascn.smartpark.viewmodel.MonthCardDetailViewModel;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yascn.smartpark.utils.AppContants.CARDTYPEMOENTH;
import static com.yascn.smartpark.utils.AppContants.CARDTYPESEASON;
import static com.yascn.smartpark.utils.AppContants.CARDTYPEYEAR;
import static com.yascn.smartpark.utils.AppContants.ISREFILL;
import static com.yascn.smartpark.utils.AppContants.MONTHCARDCARNUMBER;
import static com.yascn.smartpark.utils.AppContants.MONTHCARDDETAILBEAN;
import static com.yascn.smartpark.utils.AppContants.MONTHCARDPAYTYPEALI;
import static com.yascn.smartpark.utils.AppContants.MONTHCARDPAYTYPEWECHAT;
import static com.yascn.smartpark.utils.AppContants.MONTHCARDRECORDID;
import static com.yascn.smartpark.utils.AppContants.MONTHCARDREMAINDAYS;
import static com.yascn.smartpark.utils.AppContants.MONTHCARDTYPEBUY;
import static com.yascn.smartpark.utils.AppContants.MONTHCARDTYPEREFILL;
import static com.yascn.smartpark.utils.AppContants.PARKIDTRANS;
import static com.yascn.smartpark.utils.AppContants.PARKNAMETRANS;
import static com.yascn.smartpark.utils.AppContants.PAYSUCCESSBROADCAST;
import static com.yascn.smartpark.utils.AppContants.REFILLABLE;
import static com.yascn.smartpark.utils.AppContants.REFLESHORDER;
import static com.yascn.smartpark.utils.AppContants.REFRESHCARNUMBER;

public class MonthCardDetailActivity extends BaseActivity implements CarLicenseContract.View, MonthCardDetailContract.View, MonthCardPayInfoContract.View {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.index_toolbar)
    Toolbar indexToolbar;
    @BindView(R.id.tv_park_name)
    TextView tvParkName;
    @BindView(R.id.tv_card_type_head)
    TextView tvTotalDays;
    @BindView(R.id.iv_carlicense_left)
    ImageView ivCarlicenseLeft;
    @BindView(R.id.iv_carlicense_right)
    ImageView ivCarlicenseRight;
    @BindView(R.id.tv_carlicense_right)
    TextView tvCarlicenseRight;
    @BindView(R.id.iv_cardtype_left)
    ImageView ivCardtypeLeft;
    @BindView(R.id.iv_cardtype_right)
    ImageView ivCardtypeRight;
    @BindView(R.id.tv_cardtype_right)
    TextView tvCardtypeRight;
    @BindView(R.id.iv_wechat_left)
    ImageView ivWechatLeft;
    @BindView(R.id.iv_wechat_right)
    ImageView ivWechatRight;
    @BindView(R.id.iv_alipay_left)
    ImageView ivAlipayLeft;
    @BindView(R.id.iv_alipay_right)
    ImageView ivAlipayRight;
    @BindView(R.id.bt_pay)
    Button btPay;
    @BindView(R.id.tv_carlicense_top)
    TextView tvCarlicenseTop;
    @BindView(R.id.rl_car_license)
    RelativeLayout rlCarLicense;
    @BindView(R.id.iv_card_type)
    ImageView ivCardType;

    @BindView(R.id.rl_car_type)
    RelativeLayout rlCarType;
    @BindView(R.id.rl_wechat)
    RelativeLayout rlWechat;
    @BindView(R.id.rl_alipay)
    RelativeLayout rlAlipay;

    @BindView(R.id.tv_take_effort)
    TextView tvRemainDays;


    private MonthCardPayInfoContract.Presenter monthCardPayInfoPresenter;

    private String userId;
    private MonthCardDetailBean monthCardDetailBean;

    private String ALIPAY = "alipay";
    private String WECHAT = "wechat";
    private String payType = WECHAT;

    private static final String TAG = "MonthCardDetailActivity";


    private CarLicenseListDialog carLicenseListDialog;
    private String parkName;
    private String parkIdTrans;
    private int monthCardSelectedPosition = -1;

    private static final int SDK_PAY_FLAG = 1;

    private CarLicense.ObjectBean carLicense;


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(PAYSUCCESSBROADCAST)) {
                T.showShort(MonthCardDetailActivity.this,getString(R.string.string_pay_success));
                skipActivity(MyCarActivity.class);
                finish();

            }else  if (intent.getAction().equals(REFRESHCARNUMBER)) {
                if(carLicenseViewModel!=null){
                    carLicenseViewModel.getCarLicense(userId);
                }

            }

        }

    };

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。


                        Toast.makeText(getContext(), "支付成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.setAction(AppContants.PAYSUCCESSBROADCAST);
                        MonthCardDetailActivity.this.sendBroadcast(intent);

//                        walletPresenter.getUserInfo(userId);
                        LogUtil.d(TAG, "handleMessage: success");
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(getContext(), "支付失败", Toast.LENGTH_SHORT).show();
                        LogUtil.d(TAG, "handleMessage: error" + payResult.getResult());

                    }
                    break;
                }
                default:
                    break;
            }
        }
    };
    private boolean isReFill;
    private int monthcardRemainDays;
    private String monthCardRecordId;
    private String monthCardCarNumber;
    private CarLicenseViewModel carLicenseViewModel;
    private MonthCardDetailViewModel monthCardDetailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_card_detail);
        ButterKnife.bind(this);
        initToolbar();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PAYSUCCESSBROADCAST);
        intentFilter.addAction(REFLESHORDER);
        intentFilter.addAction(REFRESHCARNUMBER);
        registerReceiver(broadcastReceiver, intentFilter);


        userId = (String) SPUtils.get(this, AppContants.KEY_USERID, "");
        parkName = getIntent().getStringExtra(PARKNAMETRANS);
        parkIdTrans = getIntent().getStringExtra(PARKIDTRANS);


        isReFill = getIntent().getBooleanExtra(ISREFILL,false);
        monthcardRemainDays = getIntent().getIntExtra(MONTHCARDREMAINDAYS,0);
        monthCardRecordId = getIntent().getStringExtra(MONTHCARDRECORDID);
        monthCardCarNumber = getIntent().getStringExtra(MONTHCARDCARNUMBER);

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
        carLicenseViewModel.getQueryStatus().observe(this,statusObserver)

        ;
        monthCardPayInfoPresenter = new MonthCardPayInfoPresenter(this);








        //1
        monthCardDetailViewModel = ViewModelProviders.of(this).get(MonthCardDetailViewModel.class);


        //2
        getLifecycle().addObserver(monthCardDetailViewModel);

        //3
        Observer<MonthCardDetailBean> monthCardDetailBeanObserver = new Observer<MonthCardDetailBean>() {
            @Override
            public void onChanged(@Nullable MonthCardDetailBean monthCardDetailBean) {
                setMonthCardDetail(monthCardDetailBean);
            }
        };

        //4

        monthCardDetailViewModel.getMutableLiveDataEntry().observe(this,monthCardDetailBeanObserver);
        monthCardDetailViewModel.getQueryStatus().observe(this,statusObserver);

        if(monthCardDetailViewModel!=null){
            monthCardDetailViewModel.getMonthCardDetail(parkIdTrans);
        }






        if(isReFill){
            tvRemainDays.setVisibility(View.VISIBLE);
            tvRemainDays.setText(StringUtils.getRString(mActivity,R.string.string_remain_days)+monthcardRemainDays+StringUtils.getRString(mActivity,R.string.string_day));
            ivCarlicenseRight.setVisibility(View.INVISIBLE);
            setCarLicense(monthCardCarNumber);
        }else{
            tvRemainDays.setVisibility(View.GONE);
            ivCarlicenseRight.setVisibility(View.VISIBLE);
            setCarLicense(getString(R.string.loading_carlicense));
            if(carLicenseViewModel!=null){
                carLicenseViewModel.getCarLicense(userId);
            }
        }

        tvParkName.setText(parkName);


        setPayTypeStatus(WECHAT, ivWechatRight);


    }





    private void initToolbar() {
        indexToolbar.setNavigationIcon(R.drawable.icon_back);
        indexToolbar.setTitleTextColor(Color.WHITE);
        indexToolbar.setTitle("");
        indexToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);


        if (monthCardPayInfoPresenter != null) {
            monthCardPayInfoPresenter.onDestory();
        }

    }


    @OnClick({ R.id.rl_car_type, R.id.rl_alipay, R.id.rl_wechat, R.id.bt_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {


            case R.id.rl_car_type:
                if (monthCardDetailBean == null) {
                    T.showShort(MonthCardDetailActivity.this, R.string.monthcard_type_loading);
                    return;
                } else if (monthCardDetailBean.getObject().size() == 0) {
                    T.showShort(MonthCardDetailActivity.this, R.string.monthcard_type_empty);
                    return;
                }
                ChooseCardTypeDialog chooseCardTypeDialog = new ChooseCardTypeDialog();
                Bundle data = new Bundle();
                data.putSerializable(MONTHCARDDETAILBEAN, monthCardDetailBean);
                chooseCardTypeDialog.setArguments(data);

                chooseCardTypeDialog.show(getSupportFragmentManager(), "choosecartypedialog");
                chooseCardTypeDialog.setOnCardTypeChange(new ChooseCardTypeDialog.SetOnCardTypeChangeListener() {
                    @Override
                    public void setCartype(String cartype, int position) {


                        setCardType(position, cartype);


                    }
                });

                break;

            case R.id.rl_wechat:

                setPayTypeStatus(WECHAT, ivWechatRight);
                break;

            case R.id.rl_alipay:
                setPayTypeStatus(ALIPAY, ivAlipayRight);
                break;

            case R.id.bt_pay:


                if (tvCarlicenseTop.getText().equals(getString(R.string.loading_carlicense))) {
                    T.showShort(MonthCardDetailActivity.this, getString(R.string.loading_carlicense));
                    return;
                }
                if (tvCarlicenseTop.getText().equals(getString(R.string.string_no_bind_carnumber))) {
                    T.showShort(MonthCardDetailActivity.this, getString(R.string.string_no_bind_carnumber));
                    return;
                }
                if (tvCarlicenseTop.getText().equals(getString(R.string.string_choose_carlicense))) {
                    T.showShort(MonthCardDetailActivity.this, getString(R.string.string_choose_carlicense_warning));
                    return;
                }

                if (monthCardDetailBean == null) {
                    T.showShort(MonthCardDetailActivity.this, R.string.monthcard_type_loading);
                    return;
                } else if (monthCardDetailBean.getObject().size() == 0) {
                    T.showShort(MonthCardDetailActivity.this, R.string.monthcard_type_empty);
                    return;
                }

                if (monthCardSelectedPosition == -1) {

                    T.showShort(MonthCardDetailActivity.this, getString(R.string.string_pick_card_type));
                }

                if (ClickUtil.isFastClick()) {
                    T.showShort(MonthCardDetailActivity.this, getString(R.string.string_click_to_quick));
                    return;
                }


                //String monthcardId,String userId,String carNumber,String startTime,String wxFlag
                if (WECHAT.equals(payType)) {
                    if(isReFill){
                        if(!isReFillAble){
                            T.showShort(MonthCardDetailActivity.this,getString(R.string.string_refill_unable));
                            return;
                        }
                        monthCardPayInfoPresenter.getWechatRefillMonthCardPayInfo(monthCardDetailBean.getObject().get(monthCardSelectedPosition).getMonthcard_id(),monthCardRecordId,MONTHCARDPAYTYPEWECHAT,MONTHCARDTYPEREFILL);
                    }else{
                        monthCardPayInfoPresenter.getWeCahtBuyMonthCardPayInfo(monthCardDetailBean.getObject().get(monthCardSelectedPosition).getMonthcard_id(),userId,tvCarlicenseTop.getText().toString(),MONTHCARDPAYTYPEWECHAT,MONTHCARDTYPEBUY);
                    }
                } else if (ALIPAY.equals(payType)) {
                    if(isReFill){
                        if(!isReFillAble){
                            T.showShort(MonthCardDetailActivity.this,getString(R.string.string_refill_unable));
                            return;
                        }
                        monthCardPayInfoPresenter.getAliRefillMonthCardPayInfo(monthCardDetailBean.getObject().get(monthCardSelectedPosition).getMonthcard_id(),monthCardRecordId,MONTHCARDPAYTYPEALI,MONTHCARDTYPEREFILL);
                    }else{
                        monthCardPayInfoPresenter.getAliBuyMonthCardPayInfo(monthCardDetailBean.getObject().get(monthCardSelectedPosition).getMonthcard_id(),userId,tvCarlicenseTop.getText().toString(),MONTHCARDPAYTYPEALI,MONTHCARDTYPEBUY);
                    }
                }


                break;


        }
    }

    private void setCardType(int position, String cartype) {
        monthCardSelectedPosition = position;
        LogUtil.d(TAG, "setCardType: "+cartype);
        switch (cartype){
            case CARDTYPEMOENTH:
                ivCardType.setImageResource(R.drawable.icon_month_card_bg);
                btPay.setBackgroundResource(R.drawable.bg_selector_month);
                indexToolbar.setBackgroundColor(StringUtils.getRColor(this,R.color.color_card_type_bg_month));
                setTintBarColor(R.color.color_card_type_bg_month);
                break;
            case CARDTYPEYEAR:
                ivCardType.setImageResource(R.drawable.icon_year_card_bg);
                btPay.setBackgroundResource(R.drawable.bg_selector_year);
                indexToolbar.setBackgroundColor(StringUtils.getRColor(this,R.color.color_card_type_bg_year));
                setTintBarColor(R.color.color_card_type_bg_year);
                break;
            case CARDTYPESEASON:
                ivCardType.setImageResource(R.drawable.icon_season_card_bg);
                btPay.setBackgroundResource(R.drawable.bg_selector_season);
                indexToolbar.setBackgroundColor(StringUtils.getRColor(this,R.color.color_card_type_bg_season));
                setTintBarColor(R.color.color_card_type_bg_season);
                break;

        }
        tvCardtypeRight.setText(cartype+":"+monthCardDetailBean.getObject().get(position).getDays()+StringUtils.getRString(MonthCardDetailActivity.this,R.string.string_day));
        btPay.setText(getString(R.string.string_pay_another) + ":" + monthCardDetailBean.getObject().get(position).getMoney() + getString(R.string.string_yuan));
        tvTotalDays.setText(isReFill ?  getString(R.string.string_refill)+"\n"+monthCardDetailBean.getObject().get(position).getDays()+getString(R.string.string_day):monthCardDetailBean.getObject().get(position).getType());


    }

    private void setTintBarColor(int selectedColor) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(StringUtils.getRColor(this,selectedColor));
        }

    }

    private void setPayTypeStatus(String payType, ImageView ivselected) {
        this.payType = payType;
        this.ivAlipayRight.setImageResource(R.drawable.icon_check_blue_noraml);
        this.ivWechatRight.setImageResource(R.drawable.icon_check_blue_noraml);
        ivselected.setImageResource(R.drawable.icon_check_blue_selected);

    }

    /**
     * 缴费或是续费
     * @param carLicense
     */
    private void setPayTimesStatus(CarLicense.ObjectBean carLicense){


        if(carLicense.getMCARD_LIST()==null){

            //第一次缴费
            setPayTimesFisrtStatus(carLicense);
        }else  if(carLicense.getMCARD_LIST().size()==0){
            //第一次缴费
            setPayTimesFisrtStatus(carLicense);
        } else {


            for(int i=0;i<carLicense.getMCARD_LIST().size();i++){
                if(carLicense.getMCARD_LIST().get(i).getPARKING_ID().equals(parkIdTrans)){
                    //该停车场续费
                    setPayTimesNotFirstStatus(carLicense,carLicense.getMCARD_LIST().get(i));

                    break;
                }else if(i==(carLicense.getMCARD_LIST().size()-1)){
                    //该停车场第一次缴费
                    setPayTimesFisrtStatus(carLicense);
                    break;
                }
            }
        }

        setMonthCardDetailBean();
    }

    private void setPayTimesFisrtStatus(CarLicense.ObjectBean carLicense){
        isReFill = false;
        tvRemainDays.setVisibility(View.GONE);
        monthcardRemainDays = 0;
        monthCardRecordId = null;
        monthCardCarNumber =  null;
        setCarLicense(carLicense.getNUMBER());

    }

    private  boolean isReFillAble = false;//判断是否可以续费
    private void setPayTimesNotFirstStatus(CarLicense.ObjectBean license, CarLicense.ObjectBean.MCARDLISTBean carLicense){
        isReFill = true;
        monthcardRemainDays = carLicense.getSY_DAYS();
        monthCardRecordId = carLicense.getMCARDRECORD_ID();
        monthCardCarNumber =  license.getNUMBER();

        isReFillAble = REFILLABLE.equals(carLicense.getFLAG())? true:false;

        tvRemainDays.setVisibility(View.VISIBLE);
        tvRemainDays.setText(StringUtils.getRString(mActivity,R.string.string_remain_days)+monthcardRemainDays+StringUtils.getRString(mActivity,R.string.string_day));
        setCarLicense(monthCardCarNumber);

    }


    @Override
    public void setCarLicense(final CarLicense carlicenses) {

        if (carlicenses.getObject().size() == 0) {
            ivCarlicenseRight.setVisibility(View.INVISIBLE);
            tvCarlicenseRight.setText(getString(R.string.string_bind_carnumber_warning));
            tvCarlicenseTop.setText(getString(R.string.string_no_bind_carnumber));

                rlCarLicense.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MonthCardDetailActivity.this, DiyParkLicenseActivity.class);
                        startActivity(intent);
                    }
                });


        } else if (carlicenses.getObject().size() == 1) {
            setCarLicense(carlicenses.getObject().get(0).getNUMBER());
            setPayTimesStatus(carlicenses.getObject().get(0));

            rlCarLicense.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MonthCardDetailActivity.this, DiyParkLicenseActivity.class);
                    startActivity(intent);
                }
            });

        } else {
            setCarLicense(carlicenses.getObject().get(0).getNUMBER());
            setPayTimesStatus(carlicenses.getObject().get(0));
            carLicenseListDialog = new CarLicenseListDialog();
            Bundle data = new Bundle();
            data.putSerializable(PARKIDTRANS, parkIdTrans);
            carLicenseListDialog.setArguments(data);

            carLicenseListDialog.setOnCarLicenseChooseListener(new CarLicenseListDialog.setCarLicenseChooseListener() {
                @Override
                public void setChooseCarLicense(String carLicense) {
                    setCarLicense(carLicense);
                    for(int i=0;i<carlicenses.getObject().size();i++){
                        if(carLicense.equals(carlicenses.getObject().get(i).getNUMBER())){
                            setPayTimesStatus(carlicenses.getObject().get(i));
                            break;
                        }
                    }

                }
            });



                rlCarLicense.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        LogUtil.d(TAG, "onClick: rlcarlicenseonclicklistener");
                        carLicenseListDialog.show(getSupportFragmentManager(), "CarLicenseListDialog");
                    }
                });

        }


    }

    private void setCarLicense(String carLicense) {

        tvCarlicenseRight.setText(carLicense);
        tvCarlicenseTop.setText(carLicense);

    }


    @Override
    public void serverError(String errorMsg) {
        T.showShort(this, errorMsg);
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


    @Override
    public void setMonthCardDetail(MonthCardDetailBean monthCardDetailBean) {
        this.monthCardDetailBean = monthCardDetailBean;

        setMonthCardDetailBean();
    }

    private void setMonthCardDetailBean() {
        if (monthCardDetailBean.getObject() != null) {
            if (monthCardDetailBean.getObject().size() != 0) {
                setCardType(0, this.monthCardDetailBean.getObject().get(0).getType());
            }
        }
    }

    @Override
    public void setAliPayInfo(AliRecharge aliRecharge) {
        LogUtil.d(TAG, "setAliPayInfo: " + aliRecharge.getObject().getOrderStr());
        LogUtil.d(AppContants.TAG, aliRecharge.toString());
        final String orderInfo = aliRecharge.getObject().getOrderStr();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(MonthCardDetailActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                LogUtil.d(AppContants.TAG, result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();

    }

    @Override
    public void setWxPayInfo(WxRecharge wxRecharge) {
        LogUtil.d(TAG, "setWxPayInfo: " + wxRecharge.toString());

        LogUtil.d(AppContants.TAG, wxRecharge.toString());
        IWXAPI api = WXAPIFactory.createWXAPI(MonthCardDetailActivity.this, AppContants.WXAPPID);
        api.registerApp(AppContants.WXAPPID);

        boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
        if (isPaySupported) {
            PayReq req = new PayReq();
            req.appId = wxRecharge.getObject().getAppid();
            req.partnerId = wxRecharge.getObject().getPartnerid();
            req.prepayId = wxRecharge.getObject().getPrepayid();
            req.nonceStr = wxRecharge.getObject().getNoncestr();
            req.timeStamp = wxRecharge.getObject().getTimestamp();
            req.sign = wxRecharge.getObject().getSign();
            req.packageValue = wxRecharge.getObject().getPackageX();
            boolean bol = api.sendReq(req);
            if (bol) {
                LogUtil.d(AppContants.TAG, "微信跳转成功");
            } else {
                LogUtil.d(AppContants.TAG, "微信跳转失败");
                T.showShort(MonthCardDetailActivity.this, "服务器获取数据失败，请重试");
            }
        } else {
            T.showShort(MonthCardDetailActivity.this, "未安装微信程序或版本号太低");
        }
    }
}
