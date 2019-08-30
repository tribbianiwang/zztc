package com.yascn.smartpark.fragment;


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
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.yascn.smartpark.activity.NewWalletActivity;
import com.yascn.smartpark.activity.WalletDetailActivity;
import com.yascn.smartpark.base.BaseFragment;
import com.yascn.smartpark.bean.AliRecharge;
import com.yascn.smartpark.bean.PayResult;
import com.yascn.smartpark.bean.Userinfo;
import com.yascn.smartpark.bean.WxRecharge;
import com.yascn.smartpark.contract.WalletContract;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.SPUtils;
import com.yascn.smartpark.utils.StringUtils;
import com.yascn.smartpark.utils.T;
import com.yascn.smartpark.viewmodel.WalletViewModel;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSFIILED;
import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSLOADING;
import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSSUCCESS;
import static com.yascn.smartpark.utils.AppContants.REFLESHORDER;

/**
 * A simple {@link Fragment} subclass.
 */
public class WalletBalanceFragment extends BaseFragment implements WalletContract.View, TextWatcher {


    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private NewWalletActivity newWalletActivity;
    private String userId;
    private WalletViewModel walletViewModel;

    public WalletBalanceFragment() {
        // Required empty public constructor
    }


    private static final int SDK_PAY_FLAG = 1;


    @BindView(R.id.money_20)
    LinearLayout money_20;

    @BindView(R.id.money_50)
    LinearLayout money_50;

    @BindView(R.id.money_100)
    LinearLayout money_100;

    @BindView(R.id.money_200)
    LinearLayout money_200;

    @BindView(R.id.money_500)
    LinearLayout money_500;

    @BindView(R.id.money_1000)
    LinearLayout money_1000;


    @BindView(R.id.money20)
    TextView money20;


    @BindView(R.id.money50)
    TextView money50;

    @BindView(R.id.money100)
    TextView money100;

    @BindView(R.id.money200)
    TextView money200;

    @BindView(R.id.money500)
    TextView money500;

    @BindView(R.id.money1000)
    TextView money1000;


    @BindView(R.id.inputMoneyView)
    LinearLayout inputMoneyView;

    @BindView(R.id.ll_error)
    LinearLayout error;


    @BindView(R.id.content)
    LinearLayout content;

    @BindView(R.id.inputMoney)
    EditText inputMoney;

    @BindView(R.id.wechatPay)
    LinearLayout wechatPay;

    @BindView(R.id.aliPay)
    LinearLayout aliPay;

    @BindView(R.id.money)
    TextView money;


    private int chargeMoney;

    private Userinfo userinfo;


//    private WalletContract.Presenter walletPresenter;


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
                        if(walletViewModel!=null){
                            walletViewModel.getUserInfo(userId);
                        }

                        LogUtil.d(TAG, "handleMessage: success");
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(getContext(), "支付失败", Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    public BroadcastReceiver mainReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            {


                switch (intent.getAction()) {
                    case REFLESHORDER:
                        LogUtil.d(TAG, "onReceive: wechatrefreshmoney");
                        LogUtil.d(AppContants.TAG, "刷新钱包数据");
                        if(walletViewModel!=null){
                            walletViewModel.getUserInfo(userId);
                        }

                        break;
                }

            }
        }
    };

    private boolean isNetFailed = false;

    @Override
    public void netWordIsFailed() {
        super.netWordIsFailed();
        isNetFailed = true;
    }

    @Override
    public void netWorkIsSuccess() {
        super.netWorkIsSuccess();

        if (walletViewModel != null && isNetFailed) {
            walletViewModel.getUserInfo(userId);
        }

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wallet_balance, container, false);

        view.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (getActivity().getCurrentFocus() != null && getActivity().getCurrentFocus().getWindowToken() != null) {
                        manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
                return false;
            }
        });


        ButterKnife.bind(this, view);

        return view;
    }

    public Observer<String> statusObserver;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        newWalletActivity = (NewWalletActivity) getActivity();


        //设置状态
        statusObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.d(TAG, "onChanged: status"+s);

                //设置状态

                switch(s){
                    case QUERYSTATUSLOADING:
                        Log.d(TAG, "status: 加载中");
                        showProgress();

                        break;
                    case QUERYSTATUSFIILED:
                        Log.d(TAG, "status: 加载失败");
                        hideProgress();
                        serverError(AppContants.SERVERERROR);


                        break;

                    case QUERYSTATUSSUCCESS:
                        Log.d(TAG,"status:加载成功--");
                        hideProgress();


                        break;
                }

            }
        };

        userId = (String) SPUtils.get(newWalletActivity, AppContants.KEY_USERID,"");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(REFLESHORDER);

        newWalletActivity.registerReceiver(mainReceiver, intentFilter);

        selectMoney(20);


        walletViewModel = ViewModelProviders.of(this).get(WalletViewModel.class);

        getLifecycle().addObserver(walletViewModel);

        Observer<Userinfo> userinfoObserver = new Observer<Userinfo>() {
            @Override
            public void onChanged(@Nullable Userinfo userinfo) {
                setUserInfo(userinfo);
            }
        };


        Observer<AliRecharge> aliRechargeObserver = new Observer<AliRecharge>() {
            @Override
            public void onChanged(@Nullable AliRecharge aliRecharge) {
                setAliRecharge(aliRecharge);

            }
        };


        Observer<WxRecharge> wxRechargeObserver = new Observer<WxRecharge>() {
            @Override
            public void onChanged(@Nullable WxRecharge wxRecharge) {
                setWxRecharge(wxRecharge);
            }
        };

        walletViewModel.getQueryStatusMutableLiveData().observe(this,statusObserver);
        walletViewModel.getUserinfoMutableLiveData().observe(this,userinfoObserver);
        walletViewModel.getAliRechargeMutableLiveData().observe(this,aliRechargeObserver);
        walletViewModel.getWxRechargeMutableLiveData().observe(this,wxRechargeObserver);








        inputMoney.addTextChangedListener(this);
        inputMoney.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                selectMoney(0);
                chargeMoney = -1;
                return false;
            }
        });


        walletViewModel.getUserInfo(userId);
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                isPullRefresh = true;
                if(walletViewModel!=null){
                    walletViewModel.getUserInfo(userId);
                }

            }
        });
    }


    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) newWalletActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(inputMoney.getWindowToken(), 0); //强制隐藏键盘

        inputMoney.setText("");
    }

    public void setMoneyBgAndColor(LinearLayout llMoneyBg, TextView tvMoney, boolean isInputMoney) {

        money_20.setBackgroundResource(R.drawable.bg_money_normal);
        money20.setTextColor(getResources().getColor(R.color.design_text_gray));

        money_50.setBackgroundResource(R.drawable.bg_money_normal);
        money50.setTextColor(getResources().getColor(R.color.design_text_gray));

        money_100.setBackgroundResource(R.drawable.bg_money_normal);
        money100.setTextColor(getResources().getColor(R.color.design_text_gray));

        money_200.setBackgroundResource(R.drawable.bg_money_normal);
        money200.setTextColor(getResources().getColor(R.color.design_text_gray));

        money_500.setBackgroundResource(R.drawable.bg_money_normal);
        money500.setTextColor(getResources().getColor(R.color.design_text_gray));


        money_1000.setBackgroundResource(R.drawable.bg_money_normal);
        money1000.setTextColor(getResources().getColor(R.color.design_text_gray));


        inputMoneyView.setBackgroundResource(isInputMoney ? R.drawable.bg_inputmoney_select : R.drawable.bg_inputmoney_normal);
        if (llMoneyBg != null && tvMoney != null) {
            llMoneyBg.setBackgroundResource(R.drawable.bg_money_select);

            tvMoney.setTextColor(getResources().getColor(R.color.white));
        }


    }

    public void selectMoney(int money) {
        switch (money) {
            case 20:
                setMoneyBgAndColor(money_20, money20, false);
                chargeMoney = 20;
                break;
            case 50:
                setMoneyBgAndColor(money_50, money50, false);
                chargeMoney = 50;
                break;
            case 100:
                setMoneyBgAndColor(money_100, money100, false);
                chargeMoney = 100;
                break;
            case 200:
                setMoneyBgAndColor(money_200, money200, false);
                chargeMoney = 200;
                break;
            case 500:
                setMoneyBgAndColor(money_500, money500, false);
                chargeMoney = 500;
                break;

            case 1000:
                setMoneyBgAndColor(money_1000, money1000, false);
                chargeMoney = 1000;

                break;


            case 0:
                setMoneyBgAndColor(null, null, true);
                chargeMoney = 0;
                break;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.detail:
                Intent intent = new Intent();
                intent.setClass(newWalletActivity, WalletDetailActivity.class);
                startActivity(intent);
                return true;
            default:
                // If we got here, the us
                return super.onOptionsItemSelected(item);

        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        LogUtil.d(AppContants.TAG, "s = " + s.toString());

        if (!TextUtils.isEmpty(s.toString())) {
//            LogUtil.d(AppContants.TAG, "付款金额：" + StringUtils.emptyParseInt(s.toString()));
            if (StringUtils.emptyParseDouble(s.toString()) <= 0) {

                chargeMoney = 0;
            } else {
                chargeMoney = StringUtils.emptyParseInt(s.toString());
            }

            if (StringUtils.emptyParseInt(s.toString()) > 1000) {
                inputMoney.setText(s.subSequence(0, 3));
                T.showShort(newWalletActivity, "充值金额不能大于1000元");
                inputMoney.setSelection(inputMoney.getText().length());
                chargeMoney = StringUtils.emptyParseInt(inputMoney.getText().toString().trim());
            }
        } else {
            LogUtil.d(AppContants.TAG, "money为空");
            chargeMoney = -1;
        }
    }




    private static final String TAG = "WalletActivity";


    public void setMoney(String money) {
        LogUtil.d(TAG, "setMoney: " + money);

        this.money.setText(money);
    }

    @Override
    public void serverError(String errorMsg) {

    }

    @Override
    public Context getContext() {
        return newWalletActivity;
    }

    @Override
    public void setAliRecharge(AliRecharge aliRecharge) {
        LogUtil.d(AppContants.TAG, aliRecharge.toString());
        final String orderInfo = aliRecharge.getObject().getOrderStr();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(newWalletActivity);
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
    public void setWxRecharge(WxRecharge wxRecharge) {
        LogUtil.d(AppContants.TAG, wxRecharge.toString());
        IWXAPI api = WXAPIFactory.createWXAPI(newWalletActivity, AppContants.WXAPPID);
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
                T.showShort(newWalletActivity, "服务器获取数据失败，请重试");
            }
        } else {
            T.showShort(newWalletActivity, "未安装微信程序或版本号太低");
        }
    }
    boolean isPullRefresh = false;

    @Override
    public void showProgress() {
        if(isPullRefresh){
            return;
        }
        super.showProgress();
    }

    @Override
    public void hideProgress() {

        if(isPullRefresh){
            refreshLayout.finishRefresh();
            return;
        }

        super.hideProgress();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        newWalletActivity.unregisterReceiver(mainReceiver);
    }

    @OnClick({R.id.ll_error, R.id.money_20, R.id.money_50, R.id.money_200, R.id.money_100, R.id.money_500, R.id.aliPay, R.id.wechatPay, R.id.money_1000,R.id.ll_money_title})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.ll_money_title:
                hideKeyboard();
                break;


            case R.id.money_20:
                hideKeyboard();
                selectMoney(20);
                break;
            case R.id.money_50:
                hideKeyboard();
                selectMoney(50);
                break;
            case R.id.money_100:
                hideKeyboard();
                selectMoney(100);
                break;
            case R.id.money_200:
                hideKeyboard();
                selectMoney(200);
                break;
            case R.id.money_500:
                hideKeyboard();
                selectMoney(500);
                break;

            case R.id.money_1000:
                hideKeyboard();
                selectMoney(1000);
                break;

            case R.id.wechatPay:
                if (chargeMoney == -1) {
                    T.showShort(newWalletActivity, "请选择支付金额");
                } else if (chargeMoney == 0) {
                    T.showShort(newWalletActivity, "最低充值一元");
                } else {
                    if(walletViewModel!=null){
                        walletViewModel.startWxRecharge(chargeMoney + "",userId);
//                        walletViewModel.startWxRecharge(0.01 + "",userId);

                    }

//                    walletPresenter.wxRecharge("0.01");
                }

                break;
            case R.id.aliPay:
                if (chargeMoney == -1) {
                    T.showShort(newWalletActivity, "请选择支付金额");
                } else if (chargeMoney == 0) {
                    T.showShort(newWalletActivity, "最低充值一元");
                } else {
                    if(walletViewModel!=null){
                        walletViewModel.startAliRecharge(chargeMoney + "",userId);

                    }

//                    walletPresenter.aliRecharge("0.01");
                }
                break;
            case R.id.ll_error:
                if(walletViewModel!=null){
                    walletViewModel.getUserInfo(userId);
                }

                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }



    @Override
    public void setUserInfo(Userinfo userinfo) {
        setMoney(userinfo.getObject().getMoney());
    }
}
