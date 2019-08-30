package com.yascn.smartpark.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yascn.smartpark.R;
import com.yascn.smartpark.activity.PaySuccessActivity;
import com.yascn.smartpark.activity.PickCouponingActivity;
import com.yascn.smartpark.bean.ALIPay;
import com.yascn.smartpark.bean.CouponingBean;
import com.yascn.smartpark.bean.Userinfo;
import com.yascn.smartpark.bean.WXPay;
import com.yascn.smartpark.bean.WalletPayBean;
import com.yascn.smartpark.contract.MineContract;
import com.yascn.smartpark.contract.OrderContract;
import com.yascn.smartpark.model.Pay0Bean;
import com.yascn.smartpark.presenter.OrderPresenter;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.ClickUtil;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.SPUtils;
import com.yascn.smartpark.utils.StringUtils;
import com.yascn.smartpark.utils.T;
import com.yascn.smartpark.viewmodel.MineViewModel;

import static com.yascn.smartpark.utils.AppContants.COUPONINGBEAN;
import static com.yascn.smartpark.utils.AppContants.COUPONINGBROADCAST;
import static com.yascn.smartpark.utils.AppContants.ISSUPPORTCOUPON;
import static com.yascn.smartpark.utils.AppContants.ISSUPPORTWALLETPAY;
import static com.yascn.smartpark.utils.AppContants.ORDERFORMID;
import static com.yascn.smartpark.utils.AppContants.ORDERTYPE;
import static com.yascn.smartpark.utils.AppContants.ORDERTYPEQRCODE;
import static com.yascn.smartpark.utils.AppContants.PARKDURINGTIME;
import static com.yascn.smartpark.utils.AppContants.PAYDIALOGMONEY;
import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSFIILED;
import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSLOADING;
import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSSUCCESS;
import static com.yascn.smartpark.utils.AppContants.REFLESHORDER;

/**
 * Created by YASCN on 2017/1/10.
 */

public class PayDialog extends android.support.v4.app.DialogFragment implements View.OnClickListener ,MineContract.View,OrderContract.OrderView {

    RelativeLayout rlAlipay;

    RelativeLayout rlWechat;

    RelativeLayout rlWallet;
    SmartRefreshLayout refreshLayout;
    TextView tvCancel;
    private Window dialogWindow;
    private Dialog dialog;
    private boolean isSupportWalletPay;

    private static final String TAG = "PayDialog";
    private ImageView ivClose;
    private TextView tvPayNow;
    private ImageView ivAlipayPay;
    private ImageView ivWxPay;
    private ImageView ivWalletPay;
    private String payMoney;
    private TextView tvBalance;
    private TextView tvPickCouponing;
    private RelativeLayout rlPickCouponing;



    public BroadcastReceiver PayReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(COUPONINGBROADCAST)) {
                couponingItemBean = (CouponingBean.ObjectBean) intent.getSerializableExtra(COUPONINGBEAN);
                couponId = couponingItemBean.getCOUPON_ID();
                tvPickCouponing.setText(couponingItemBean.getNAME());

                LogUtil.d(TAG, "onReceive: "+orderFormId+"----"+couponingItemBean.getCOUPON_ID());

                orderPresenter.getPaymoney(orderFormId,couponingItemBean.getCOUPON_ID());


            }
        }
    };
    private CouponingBean.ObjectBean couponingItemBean;
    private TextView tvParkDuringTime;
    private String parkDuringTime;
    private TextView tvTotalMoney;
    private String orderFormId;
    private OrderContract.OrderPresenter orderPresenter;
    private String couponId;
    private TextView tvPayType;
    private boolean isSupportCoupon;
    private String orderType;

    private MineViewModel mineViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        orderPresenter =  new OrderPresenter(this);
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        isSupportWalletPay = getArguments().getBoolean(ISSUPPORTWALLETPAY);
        payMoney = getArguments().getString(  PAYDIALOGMONEY);
        isSupportCoupon = getArguments().getBoolean(ISSUPPORTCOUPON);
        parkDuringTime = getArguments().getString(PARKDURINGTIME);
        orderType = getArguments().getString(ORDERTYPE);

        orderFormId = getArguments().getString(ORDERFORMID);
        tvTotalMoney.setText(payMoney+"元");
//        T.showShort(getActivity(),orderFormId);
        showPayMoney();
        tvParkDuringTime.setText(parkDuringTime);
        rlWallet.setVisibility(isSupportWalletPay?View.VISIBLE:View.GONE);




        //minePresenter = new MinePresenter(this);


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



        mineViewModel = ViewModelProviders.of(this).get(MineViewModel.class);
        getLifecycle().addObserver(mineViewModel);
        Observer<Userinfo> userinfoObserver = new Observer<Userinfo>() {
            @Override
            public void onChanged(@Nullable Userinfo userinfo) {
                setUserInfo(userinfo);

            }
        };

        mineViewModel.getMutableLiveDataEntry().observe(this,userinfoObserver);
        mineViewModel.getQueryStatus().observe(this,statusObserver);






        if(isSupportWalletPay){
            getuserInfo();
        }

        LogUtil.d(TAG,"issupportcoupon"+isSupportCoupon);
        LogUtil.d(TAG,"issupportwallet"+isSupportWalletPay);
        if(isSupportCoupon&&isSupportWalletPay){
            rlPickCouponing.setVisibility(View.VISIBLE);
        }else{
            rlPickCouponing.setVisibility(View.GONE);
        }
        if(ORDERTYPEQRCODE.equals(orderType)){
            refreshLayout.setEnableRefresh(false);
        }else{
            refreshLayout.setEnableRefresh(true);
        }

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                isPullRefresh = true;
                String couponid = null;
                if(couponingItemBean!=null){
                    if(couponingItemBean.getCOUPON_ID()!=null){
                        couponid = couponingItemBean.getCOUPON_ID();
                    }
                }
                orderPresenter.getPaymoney(orderFormId,couponid);
//                refreshLayout.finishRefresh(2000);
            }
        });

        return rootView;
    }

    private void showPayMoney() {

        setPayTypeVisible(!(0.0== StringUtils.emptyParseDouble(payMoney)));
        tvPayNow.setText(getString(R.string.confirm_pay_another)+payMoney+getString(R.string.string_money_unit));

    }


    public Observer<String> statusObserver;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);




    }

    int payType = 1;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_alipay:
                setPayType(ivAlipayPay);
                payType=2;
//                payClickListener.zhifu(2);

                break;
            case R.id.rl_wechat:
                setPayType(ivWxPay);
                payType = 1;
//                payClickListener.zhifu(1);
                break;
            case R.id.iv_close:
                if(dialog!=null){
                    if(dialog.isShowing()){
                        dialog.dismiss();
                    }
                }

                break;
            case R.id.rl_wallet:
                payType = 3;
                setPayType(ivWalletPay);
//                payClickListener.zhifu(3);
                break;
            case R.id.tv_pay_now:

                if(ClickUtil.isFastClick()){
                    T.showShort(getActivity(),getString(R.string.string_click_to_quick));
                }else{
                    if((0.0==StringUtils.emptyParseDouble(payMoney))){
                        orderPresenter.startPayFree(orderFormId,couponId);
                    }else{
                        payClickListener.zhifu(payType,couponId);
                    }

                }




                break;

            case R.id.rl_pick_couponing:
            case R.id.tv_pick_couponing:
                Intent intent = new Intent(getActivity(), PickCouponingActivity.class);
                intent.putExtra(AppContants.ISNEEDRETURNCOUPON,true);
                startActivity(intent);


                break;

        }
    }


    private void setPayType(View view){
        ivAlipayPay.setBackgroundResource(R.drawable.icon_pay_uncheck);

        ivWxPay.setBackgroundResource(R.drawable.icon_pay_uncheck);
        ivWalletPay.setBackgroundResource(R.drawable.icon_pay_uncheck);
        view.setBackgroundResource(R.drawable.icon_pay_check);
    }


    private Userinfo userinfo;
    private String userID;
    private void getuserInfo() {
        userID = (String) SPUtils.get(getContext(), AppContants.KEY_USERID, "");
        LogUtil.d(TAG, "getuserInfo: " + userID);
        if (!TextUtils.isEmpty(userID) && mineViewModel != null) {
            mineViewModel.getUserInfo(userID);
        }

    }





    boolean isPullRefresh = false;

    @Override
    public void showProgress() {
        LogUtil.d(TAG, "showProgress: ");

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

    }

    @Override
    public void setPayFreeResult(Pay0Bean pay0Bean) {
        if(AppContants.PAYFREESUCCESS.equals(pay0Bean.getObject().getFlag())){
            Intent intent = new Intent();
            intent.setAction(REFLESHORDER);

               getActivity().sendBroadcast(intent);
            Intent skipIntent = new Intent(getActivity(),PaySuccessActivity.class);
               getActivity().startActivity(skipIntent);
//            skipActivity(PaySuccessActivity.class);
            dismiss();
        }else{
            T.showShort(getActivity(),getString(R.string.string_pay_free_failed));
        }

    }

    @Override
    public void setUserInfo(Userinfo userinfo) {
        Log.d(TAG, "setUserInfo: ");

            Double userMoney = StringUtils.emptyParseDouble(userinfo.getObject().getMoney());
            Double doublePayMoney = StringUtils.emptyParseDouble(payMoney);
            if (userMoney < doublePayMoney) {
                //余额不足
                tvBalance.setText(getString(R.string.string_balance_not_enough));
                payType = 1;
                setPayType(ivWxPay);
//            rlWallet.setClickable(false);
//            ivWalletPay.setVisibility(View.GONE);
            } else {
//            rlWallet.setClickable(true);
//            ivWalletPay.setVisibility(View.VISIBLE);
                //余额充足
                tvBalance.setText(getString(R.string.string_balance_title) + ":" + userMoney + getString(R.string.string_money_unit));
                payType = 3;
                setPayType(ivWalletPay);
            }

    }


    public interface PayClickListener {
        void zhifu(int type,String couponId);

        void showDetail();
    }

    private PayClickListener payClickListener;

    public void setOnPayClickListener(PayClickListener payClickListener) {
        this.payClickListener = payClickListener;
    }

    @Override
    public void showDefaultDialog() {
        showProgress();
    }

    @Override
    public void hideDefaultDialog() {
        hideProgress();
    }

    @Override
    public void refleshOrder() {

    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void showPayDialog(String totalMoney,String payMoney, String parkDuringTime) {

        LogUtil.d(TAG+"paymoney%s", ""+payMoney);
        this.payMoney = payMoney;
        if(parkDuringTime!=null){
            tvParkDuringTime.setText(parkDuringTime);
        }

        if(totalMoney!=null){
            tvTotalMoney.setText(totalMoney+"元");
        }

        showPayMoney();

//        T.showShort(getActivity(),parkDuringTime);

    }

    @Override
    public void hidePayDialog() {

    }

    @Override
    public void wxPay(WXPay wxPay) {

    }

    @Override
    public void aliPay(ALIPay aliPay) {

    }

    @Override
    public void serverError(String errormsg) {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_pay_choose, null, false);
        tvPayType = (TextView) view.findViewById(R.id.tv_pay_type);
        refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout);
        rlAlipay = (RelativeLayout) view.findViewById(R.id.rl_alipay);
        rlWechat = (RelativeLayout) view.findViewById(R.id.rl_wechat);
        ivClose = (ImageView) view.findViewById(R.id.iv_close);
        rlWallet = (RelativeLayout) view.findViewById(R.id.rl_wallet);
        tvPayNow = (TextView) view.findViewById(R.id.tv_pay_now);
        tvBalance = (TextView) view.findViewById(R.id.tv_balanse);
        ivAlipayPay = (ImageView) view.findViewById(R.id.iv_alipay_pay);
        ivWxPay = (ImageView) view.findViewById(R.id.iv_wx_pay);
        ivWalletPay = (ImageView) view.findViewById(R.id.iv_wallet_pay);
        tvPickCouponing = (TextView) view.findViewById(R.id.tv_pick_couponing);
        tvParkDuringTime = (TextView) view.findViewById(R.id.tv_park_during_time);
        rlPickCouponing = (RelativeLayout) view.findViewById(R.id.rl_pick_couponing);
        tvTotalMoney = (TextView) view.findViewById(R.id.tv_total_money);


        rlAlipay.setOnClickListener(this);
        rlWechat.setOnClickListener(this);
        rlWallet.setOnClickListener(this);
        ivClose.setOnClickListener(this);
        tvPayNow.setOnClickListener(this);
        tvPickCouponing.setOnClickListener(this);
        rlPickCouponing.setOnClickListener(this);
    //
//        LogUtil.d(TAG, "onCreateDialog: "+isSupportWalletPay);
//        rlWallet.setVisibility(isSupportWalletPay?View.VISIBLE:View.GONE);


        setPayType(ivWxPay);


        dialog.addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialogWindow.setAttributes(lp);
        dialogWindow.setGravity(Gravity.CENTER);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(COUPONINGBROADCAST);

        getActivity().registerReceiver(PayReceiver, intentFilter);

        return dialog;
    }


    private void setPayTypeVisible(boolean isVisible) {
        tvPayType.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        rlWechat.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        rlAlipay.setVisibility(isVisible ? View.VISIBLE : View.GONE);

        if (isVisible && isSupportWalletPay) {
            rlWallet.setVisibility(View.VISIBLE);
        } else {
            rlWallet.setVisibility(View.GONE);
        }
    }

    private Dialog progressDialog;
    public void showProgressDialog(String loadingText){
        LogUtil.d(TAG, "showProgressDialog: ");
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        View view = View.inflate(getActivity(), R.layout.layout_progress_dialog,null);
        TextView tvLoading = (TextView) view.findViewById(R.id.tv_loading);
        tvLoading.setText(loadingText);
        builder.setView(view);
        if(progressDialog==null){
            progressDialog = builder.show();
        }else{
            progressDialog.show();
        }
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.setCancelable(false);


        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }



    public void hidProgressDialog(){
        LogUtil.d(TAG, "hidProgressDialog: ");
        if(progressDialog!=null){
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }

        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hidProgressDialog();
        getActivity().unregisterReceiver(PayReceiver);
    }
}
