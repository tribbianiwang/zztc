package com.yascn.smartpark.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yascn.smartpark.R;
import com.yascn.smartpark.base.BaseActivity;
import com.yascn.smartpark.bean.ALIPay;
import com.yascn.smartpark.bean.OrderInfoBean;
import com.yascn.smartpark.bean.PayResult;
import com.yascn.smartpark.bean.QueryCarLicenseFeeBean;
import com.yascn.smartpark.bean.WXPay;
import com.yascn.smartpark.bean.WalletPayBean;
import com.yascn.smartpark.contract.CarLicenseFeeContract;
import com.yascn.smartpark.contract.OrderContract;
import com.yascn.smartpark.contract.OrderInfoContract;
import com.yascn.smartpark.dialog.LoginDialog;
import com.yascn.smartpark.dialog.PayDialog;
import com.yascn.smartpark.model.Pay0Bean;
import com.yascn.smartpark.presenter.OrderPresenter;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.ClickUtil;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.LoginStatusUtils;
import com.yascn.smartpark.utils.StringUtils;
import com.yascn.smartpark.utils.T;
import com.yascn.smartpark.viewmodel.CarlicenseFeeViewModel;
import com.yascn.smartpark.viewmodel.OrderInfoViewModel;

import java.text.DecimalFormat;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yascn.smartpark.utils.AppContants.CARLICENSEBEAN;
import static com.yascn.smartpark.utils.AppContants.ISSUPPORTCOUPON;
import static com.yascn.smartpark.utils.AppContants.ORDERINFOBEAN;
import static com.yascn.smartpark.utils.AppContants.ORDERINFOID;
import static com.yascn.smartpark.utils.AppContants.ORDERTYPE;
import static com.yascn.smartpark.utils.AppContants.ORDERTYPECARLICENSE;
import static com.yascn.smartpark.utils.AppContants.ORDERTYPEQRCODE;
import static com.yascn.smartpark.utils.AppContants.PARKDURINGTIME;
import static com.yascn.smartpark.utils.AppContants.PAYDIALOGMONEY;
import static com.yascn.smartpark.utils.AppContants.PAYSUCCESSBROADCAST;
import static com.yascn.smartpark.utils.AppContants.PAYTYPEAPP;
import static com.yascn.smartpark.utils.AppContants.PAYTYPEQRCODE;
import static com.yascn.smartpark.utils.AppContants.QUERYCARLICENSEPAID;
import static com.yascn.smartpark.utils.AppContants.REFLESHORDER;
import static com.yascn.smartpark.utils.AppContants.WALLETMONEYZERO;
import static com.yascn.smartpark.utils.AppContants.WALLETMONEYZEROSTATUS;
import static com.yascn.smartpark.utils.AppContants.WALLETPAYMONEYNOTENOUGH;
import static com.yascn.smartpark.utils.AppContants.WALLETPAYMONEYNOTENOUGHSTATUS;
import static com.yascn.smartpark.utils.AppContants.WALLETPAYNOTINPARK;
import static com.yascn.smartpark.utils.AppContants.WALLETPAYNOTINPARKSTATUS;
import static com.yascn.smartpark.utils.AppContants.WALLETPAYPASSWORDWRONG;
import static com.yascn.smartpark.utils.AppContants.WALLETPAYPASSWORDWRONGSTATUS;
import static com.yascn.smartpark.utils.AppContants.WALLETPAYSUCCESS;
import static com.yascn.smartpark.utils.AppContants.WALLETPAYSUCCESSSTATUS;

public class PayParkFeeActivity extends BaseActivity implements CarLicenseFeeContract.View, OrderContract.OrderView, PayDialog.PayClickListener, OrderInfoContract.View {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.index_toolbar)
    Toolbar indexToolbar;
    @BindView(R.id.tv_car_license)
    TextView tvCarLicense;
    @BindView(R.id.tv_see_picture)
    TextView tvSeePicture;
    @BindView(R.id.tv_park_name)
    TextView tvParkName;
    @BindView(R.id.tv_start_time_title)
    TextView tvStartTimeTitle;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_coupons_title)
    TextView tvCouponsTitle;
    @BindView(R.id.tv_coupons)
    TextView tvCoupons;
    @BindView(R.id.tv_park_during_time_title)
    TextView tvParkDuringTimeTitle;
    @BindView(R.id.tv_park_during_time)
    TextView tvParkDuringTime;
    @BindView(R.id.tv_total_money_title)
    TextView tvTotalMoneyTitle;
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;
    @BindView(R.id.tv_paid_money_title)
    TextView tvPaidMoneyTitle;
    @BindView(R.id.tv_paid_money)
    TextView tvPaidMoney;
    @BindView(R.id.tv_should_pay_title)
    TextView tvShouldPayTitle;
    @BindView(R.id.tv_should_pay)
    TextView tvShouldPay;
    @BindView(R.id.bt_pay_now)
    Button btPayNow;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rl_already_pay)
    RelativeLayout rlAlreadyPay;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    boolean isPullRefresh = false;

    private String carLicense;

    private PayDialog payDialog;

    private String tip = "";
    private static final int SDK_PAY_FLAG = 1;
    private OrderContract.OrderPresenter orderPresenter;



    private CarlicenseFeeViewModel carlicenseFeeViewModel;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(PAYSUCCESSBROADCAST)) {


                if (TextUtils.isEmpty(orderInfoId)) {
                    reGetInfo();
                }
                LogUtil.d(TAG, "onReceive: paysuccess");

                showPaySuccessDialog();
                if(!PayParkFeeActivity.this.isFinishing()){

                    hidePayDialog();
                }


            }

            if (intent.getAction().equals(REFLESHORDER)) {
                LogUtil.d(AppContants.TAG, "刷新数据");

                if (TextUtils.isEmpty(orderInfoId)) {
                    reGetInfo();
                }


            }
        }

    };
    private AlertDialog paySuccessDialog;
    private String parkDuringTime;
    private OrderInfoBean transOrderInfoBean;
    private QueryCarLicenseFeeBean transQueryCarLicense;
    private OrderInfoViewModel orderInfoViewModel;

    private void showPaySuccessDialog() {

//        LogUtil.d(TAG, "showPaySuccessDialog: ");
//
//        android.support.v7.app.AlertDiaLogger.Builder builder = new android.support.v7.app.AlertDiaLogger.Builder(this);
//        View smsDialogView = View.inflate(this,R.layout.layout_dialog_pay_success,null);
//
//        ImageView ivClose = (ImageView) smsDialogView.findViewById(R.id.iv_close);
//
//        builder.setView(smsDialogView);
//        paySuccessDialog = builder.show();
//        paySuccessDiaLogger.setCancelable(false);
//        paySuccessDiaLogger.setCanceledOnTouchOutside(false);
//       ivClose.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//               if(!TextUtils.isEmpty(orderInfoId)){
//                   PayParkFeeActivity.this.finish();
//               }else{
//                   paySuccessDiaLogger.dismiss();
//               }
//           }
//       });
        if (!TextUtils.isEmpty(orderInfoId)) {
            PayParkFeeActivity.this.finish();
        }
        skipActivity(PaySuccessActivity.class);


    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(PayParkFeeActivity.this, getString(R.string.payed_success_leave_now), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.setAction(AppContants.PAYSUCCESSBROADCAST);
                        PayParkFeeActivity.this.sendBroadcast(intent);


                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        T.showShort(mActivity, mActivity.getString(R.string.string_pay_error));
                    }
                    break;


            }
        }
    };
    private String orderInfoId;
    private String couponingId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_park_fee);
        ButterKnife.bind(this);
        initTitle(getString(R.string.string_parkpay_fee));
        carLicense = getIntent().getStringExtra(AppContants.CARLICENSE);
        orderInfoId = getIntent().getStringExtra(ORDERINFOID);

        transOrderInfoBean = (OrderInfoBean) getIntent().getSerializableExtra(ORDERINFOBEAN);
        transQueryCarLicense = (QueryCarLicenseFeeBean) getIntent().getSerializableExtra(CARLICENSEBEAN);

        if(transOrderInfoBean!=null){
            setOrderInfo(transOrderInfoBean);
        }
        if(transQueryCarLicense!=null){
            setLicenseFee(transQueryCarLicense);
        }

        orderPresenter = new OrderPresenter(this);
        if (orderInfoId != null) {

            //1
            orderInfoViewModel = ViewModelProviders.of(this).get(OrderInfoViewModel.class);

            //2
            getLifecycle().addObserver(orderInfoViewModel);

            //3
            Observer<OrderInfoBean> orderInfoBeanObserver = new Observer<OrderInfoBean>() {
                @Override
                public void onChanged(@Nullable OrderInfoBean orderInfoBean) {
                    setOrderInfo(orderInfoBean);
                }
            };

            //4
            orderInfoViewModel.getMutableLiveDataEntry().observe(this,orderInfoBeanObserver);
            orderInfoViewModel.getQueryStatus().observe(this,statusObserver);




//            orderInfoPresenter.getOrderInfo(orderInfoId);
//

        } else {
            if (!TextUtils.isEmpty(carLicense)) {

//                carLicenseFeePresenter.getLicenseFee(carLicense);

                //1
                carlicenseFeeViewModel = ViewModelProviders.of(this).get(CarlicenseFeeViewModel.class);

                //2
                getLifecycle().addObserver(carlicenseFeeViewModel);

                //3
                Observer<QueryCarLicenseFeeBean> queryCarLicenseFeeBeanObserver = new Observer<QueryCarLicenseFeeBean>() {
                    @Override
                    public void onChanged(@Nullable QueryCarLicenseFeeBean queryCarLicenseFeeBean) {

                        setLicenseFee(queryCarLicenseFeeBean);
                    }
                };

                //4
                carlicenseFeeViewModel.getMutableLiveDataEntry().observe(this,queryCarLicenseFeeBeanObserver);
                carlicenseFeeViewModel.getQueryStatus().observe(this,statusObserver);


            }

        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PAYSUCCESSBROADCAST);
        intentFilter.addAction(REFLESHORDER);
        registerReceiver(broadcastReceiver, intentFilter);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                isPullRefresh = true;
                reGetInfo();
            }
        });
    }


    private void reGetInfo() {
        Log.d(TAG, "reGetInfo: ");
        if (TextUtils.isEmpty(orderInfoId)) {
            if(ClickUtil.isFastClick()){
                T.showShort(this,StringUtils.getRString(getContext(),R.string.string_click_to_quick));
                return;
            }

            if(carlicenseFeeViewModel!=null){
                carlicenseFeeViewModel.getLicenseFee(carLicense,LoginStatusUtils.getUserId(PayParkFeeActivity.this));
            }



        } else {
            if(orderInfoViewModel!=null){
                orderInfoViewModel.getOrderInfo(orderInfoId,LoginStatusUtils.getUserId(PayParkFeeActivity.this));
            }


        }

    }


    @Override
    public void serverError(String errorMsg) {
        T.showShort(this, errorMsg);
    }

    @Override
    public void setOrderInfo(OrderInfoBean orderInfoBean) {
//        T.showShort(this,orderInfoBean.getObject().getNumber());
        Log.d(TAG, "setOrderInfo: "+orderInfoBean.toString());
        LogUtil.d(TAG, "setOrderInfo: " + orderInfoId + "---flag---" + orderInfoBean.getObject().getFlag() + "isbalancepay" + orderInfoBean.getObject().getIsBalancePay());
        if (orderInfoBean.getObject().getFlag().equals("0")) {
            T.showShort(this, getString(R.string.string_qr_code_outoftime));
            this.finish();
        }
        QueryCarLicenseFeeBean queryCarLicenseFeeBean = new QueryCarLicenseFeeBean();
        QueryCarLicenseFeeBean.ObjectBean objectBean = new QueryCarLicenseFeeBean.ObjectBean();
        objectBean.setNumber(orderInfoBean.getObject().getNumber());
        objectBean.setPark_name(orderInfoBean.getObject().getPname());
        objectBean.setStart_time(orderInfoBean.getObject().getStime());
        objectBean.setStay_time(orderInfoBean.getObject().getStay_time());
        objectBean.setAmount(orderInfoBean.getObject().getMoney());
        objectBean.setPaid("0");
        objectBean.setStatus(QUERYCARLICENSEPAID);
        objectBean.setORDER_FORM_ID(orderInfoId);
        objectBean.setIsBalancePay(orderInfoBean.getObject().getIsBalancePay());
        queryCarLicenseFeeBean.setObject(objectBean);
        this.queryCarLicenseFeeBean = queryCarLicenseFeeBean;
        setLicenseFee(this.queryCarLicenseFeeBean);
    }

    @Override
    public void setLicenseFee(QueryCarLicenseFeeBean queryCarLicenseFeeBean) {
        if (TextUtils.isEmpty(queryCarLicenseFeeBean.getObject().getPark_name())) {
            T.showShort(this, StringUtils.getRString(this, R.string.string_no_payinfo));
            finish();
        }
        this.queryCarLicenseFeeBean = queryCarLicenseFeeBean;

        if (TextUtils.isEmpty(orderInfoId)) {
            rlAlreadyPay.setVisibility(View.VISIBLE);
        } else {
            rlAlreadyPay.setVisibility(View.GONE);
        }

        tvCarLicense.setText(queryCarLicenseFeeBean.getObject().getNumber());
        tvParkName.setText(queryCarLicenseFeeBean.getObject().getPark_name());
        tvStartTime.setText(queryCarLicenseFeeBean.getObject().getStart_time());
        tvParkDuringTime.setText(queryCarLicenseFeeBean.getObject().getStay_time());
        tvTotalMoney.setText(queryCarLicenseFeeBean.getObject().getAmount() + getString(R.string.string_rmb_unit));
        

        tvShouldPay.setText(new DecimalFormat("0.00").format(StringUtils.emptyParseDouble(queryCarLicenseFeeBean.getObject().getAmount()) - StringUtils.emptyParseDouble(queryCarLicenseFeeBean.getObject().getPaid())) + getString(R.string.string_rmb_unit));
        tvPaidMoney.setText(queryCarLicenseFeeBean.getObject().getPaid() + getString(R.string.string_rmb_unit));


    }

    @Override
    public void showProgress() {
        if(isPullRefresh){
            return;
        }
        showProgressDialog(getString(R.string.loadingProgress));
    }

    @Override
    public void hideProgress() {
        if(isPullRefresh){
            refreshLayout.finishRefresh();
            return;
        }
        hidProgressDialog();

    }

    @Override
    public void setWalletPayResult(WalletPayBean walletPayBean) {
        String flag = walletPayBean.getObject().getFlag();
        Intent intent = new Intent();
        intent.setAction(REFLESHORDER);
        if (mActivity != null) {
            mActivity.sendBroadcast(intent);
        }

        switch (flag) {
            case WALLETPAYNOTINPARKSTATUS:
                T.showShort(mActivity, WALLETPAYNOTINPARK);

                break;

            case WALLETPAYSUCCESSSTATUS:
                T.showShort(mActivity, WALLETPAYSUCCESS);
                Intent intentpaySuccessIntent = new Intent();
                intentpaySuccessIntent.setAction(PAYSUCCESSBROADCAST);
                PayParkFeeActivity.this.sendBroadcast(intentpaySuccessIntent);


                break;

            case WALLETPAYMONEYNOTENOUGHSTATUS:
                T.showShort(mActivity, WALLETPAYMONEYNOTENOUGH);

                break;


            case WALLETPAYPASSWORDWRONGSTATUS:
                T.showShort(mActivity, WALLETPAYPASSWORDWRONG);

                break;

            case WALLETMONEYZEROSTATUS:
                T.showShort(mActivity, WALLETMONEYZERO);

                break;


        }

    }

    @Override
    public void setPayFreeResult(Pay0Bean pay0Bean) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


        if (paySuccessDialog != null) {
            paySuccessDialog.cancel();
            paySuccessDialog = null;
        }
        unregisterReceiver(broadcastReceiver);
    }

    private static final String TAG = "PayParkFeeActivity";

    @OnClick(R.id.bt_pay_now)
    public void onViewClicked() {
        if (queryCarLicenseFeeBean != null) {
            if(ClickUtil.isFastClick()){
                T.showShort(this,StringUtils.getRString(this,R.string.string_click_to_quick));
                return ;
            }
            LogUtil.d(TAG, "onViewClicked: " + queryCarLicenseFeeBean.getObject().getORDER_FORM_ID());

            if (!TextUtils.isEmpty(orderInfoId)) {
                LogUtil.d(TAG, "onViewClicked: orderinfoempty");
//                LogUtil.d(TAG, "onViewClicked: "+orderInfoBean.getObject().getMoney());
                showPayDialog(queryCarLicenseFeeBean.getObject().getAmount(),queryCarLicenseFeeBean.getObject().getAmount(),queryCarLicenseFeeBean.getObject().getStay_time());
            } else {
                LogUtil.d(TAG, "onViewClicked: orderinfo");
                parkDuringTime = queryCarLicenseFeeBean.getObject().getStay_time();
                orderPresenter.getPaymoney(queryCarLicenseFeeBean.getObject().getORDER_FORM_ID(),"");
            }


        }


    }

    @Override
    public void showDefaultDialog() {
        showProgressDialog(getString(R.string.loadingProgress));
    }

    @Override
    public void hideDefaultDialog() {
        hidProgressDialog();
    }

    @Override
    public void refleshOrder() {
        Intent intent = new Intent();
        intent.setAction(REFLESHORDER);
        sendBroadcast(intent);
    }

    @Override
    public Context getContext() {
        return this;
    }


    private QueryCarLicenseFeeBean queryCarLicenseFeeBean;

    @Override
    public void showPayDialog(String totalMoney,String payMoney,String parkDuringTime) {
        payDialog = new PayDialog();
        Bundle bundle = new Bundle();
        bundle.putString(PAYDIALOGMONEY, payMoney);

        LogUtil.d(TAG, "showPayDialog: status"+queryCarLicenseFeeBean.getObject().getStatus());
        if(queryCarLicenseFeeBean.getObject().getStatus().equals(QUERYCARLICENSEPAID)){
            bundle.putBoolean(ISSUPPORTCOUPON,false);
        }else{

            bundle.putBoolean(ISSUPPORTCOUPON,true);
        }




        if (queryCarLicenseFeeBean.getObject().getIsBalancePay().equals("0")) {
            bundle.putBoolean(AppContants.ISSUPPORTWALLETPAY, false);
//            bundle.putBoolean(ISSUPPORTCOUPON,false);
        } else {
            bundle.putBoolean(AppContants.ISSUPPORTWALLETPAY, true);
//            bundle.putBoolean(ISSUPPORTCOUPON,true);
        }
        if (!TextUtils.isEmpty(orderInfoId)) {
            bundle.putString(PARKDURINGTIME,parkDuringTime);
            bundle.putString(ORDERTYPE,ORDERTYPEQRCODE);
        } else {
            LogUtil.d(TAG, "onViewClicked: orderinfo");
            this.parkDuringTime = queryCarLicenseFeeBean.getObject().getStay_time();
            bundle.putString(PARKDURINGTIME,this.parkDuringTime);
            bundle.putString(ORDERTYPE,ORDERTYPECARLICENSE);
        }

        bundle.putString(AppContants.ORDERFORMID,queryCarLicenseFeeBean.getObject().getORDER_FORM_ID());


//        bundle.putString("address", queryCarLicenseFeeBean.getADDRESS());
//        bundle.putString("intime", ordering.getSTART_TIME());
//        bundle.putString("paymoney", payMoney);
        payDialog.setArguments(bundle);
        payDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomDialogStyle);
        payDialog.setOnPayClickListener(this);
        payDialog.show(getSupportFragmentManager(), "zhifuDialog");
    }

    @Override
    public void hidePayDialog() {
        if (payDialog != null) {
            payDialog.dismissAllowingStateLoss();
//            payDiaLogger.dismissAllowingStateLoss();
        }
    }

    @Override
    public void wxPay(WXPay wxPay) {
        LogUtil.d(AppContants.TAG, wxPay.toString());
        tip = wxPay.getObject().getTip();
        IWXAPI api = WXAPIFactory.createWXAPI(this, AppContants.WXAPPID);
        api.registerApp(AppContants.WXAPPID);

        boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
        if (isPaySupported) {
            PayReq req = new PayReq();
            req.appId = wxPay.getObject().getAppid();
            req.partnerId = wxPay.getObject().getPartnerid();
            req.prepayId = wxPay.getObject().getPrepayid();
            req.nonceStr = wxPay.getObject().getNoncestr();
            req.timeStamp = wxPay.getObject().getTimestamp();
            req.sign = wxPay.getObject().getSign();
            req.packageValue = wxPay.getObject().getPackageX();
            boolean bol = api.sendReq(req);
            if (bol) {
                LogUtil.d(AppContants.TAG, "微信跳转成功");
            } else {
                LogUtil.d(AppContants.TAG, "微信跳转失败");
                T.showShort(mActivity, mActivity.getString(R.string.string_pay_error));
            }
        } else {
            T.showShort(this, "未安装微信程序或版本号太低");
        }
    }

    @Override
    public void aliPay(ALIPay aliPay) {
        LogUtil.d(AppContants.TAG, aliPay.toString());
        tip = aliPay.getObject().getTip();
        final String orderInfo = aliPay.getObject().getOrderStr();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(PayParkFeeActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                LogUtil.d(AppContants.TAG, result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @Override
    public void zhifu(int type,String couponingId) {
        LogUtil.d(TAG, "zhifu: " + queryCarLicenseFeeBean.getObject().getORDER_FORM_ID());
        LogUtil.d(TAG, "zhifu: "+type);
//        payDiaLogger.dismiss();
        String zhifuType;
        if (!TextUtils.isEmpty(orderInfoId)) {
            zhifuType = PAYTYPEQRCODE;
        }else {
            zhifuType = PAYTYPEAPP;
        }


        if (type == 1) { // 微信支付

            orderPresenter.getWXPay(queryCarLicenseFeeBean.getObject().getORDER_FORM_ID(),couponingId,zhifuType);
        }

        if (type == 2) { // 支付宝支付
            orderPresenter.getALIPay(queryCarLicenseFeeBean.getObject().getORDER_FORM_ID(),couponingId,zhifuType);
        }

        if (type == 3) {
//            T.showShort(mActivity,"余额支付");
            orderPresenter.startWalletPay(queryCarLicenseFeeBean.getObject().getORDER_FORM_ID(),couponingId,zhifuType);
        }
    }

    @Override
    public void showDetail() {

    }
}
