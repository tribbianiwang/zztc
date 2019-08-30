package com.yascn.smartpark.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;

import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yascn.smartpark.R;
import com.yascn.smartpark.base.BaseActivity;
import com.yascn.smartpark.bean.ALIPay;
import com.yascn.smartpark.bean.Ordering;
import com.yascn.smartpark.bean.PayResult;
import com.yascn.smartpark.bean.WXPay;
import com.yascn.smartpark.bean.WalletPayBean;
import com.yascn.smartpark.contract.OrderContract;
import com.yascn.smartpark.dialog.CancelDialog;
import com.yascn.smartpark.dialog.DefaultDialog;
import com.yascn.smartpark.dialog.PayDialog;
import com.yascn.smartpark.fragment.OrderOngoingFragment;
import com.yascn.smartpark.model.Pay0Bean;
import com.yascn.smartpark.presenter.OrderPresenter;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.ClickUtil;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.RvItemClickListener;
import com.yascn.smartpark.utils.RvItemLongClickListener;
import com.yascn.smartpark.utils.StringUtils;
import com.yascn.smartpark.utils.T;
import com.yascn.smartpark.utils.TimeUtils;

import java.text.ParseException;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mob.tools.utils.Strings.getString;
import static com.yascn.smartpark.utils.AppContants.ISSUPPORTCOUPON;
import static com.yascn.smartpark.utils.AppContants.ORDERIN;
import static com.yascn.smartpark.utils.AppContants.ORDERLEFTTIME;
import static com.yascn.smartpark.utils.AppContants.ORDERPAIDNOTOUT;
import static com.yascn.smartpark.utils.AppContants.ORDERPAIDTIMEOUT;
import static com.yascn.smartpark.utils.AppContants.ORDERRESERVE;
import static com.yascn.smartpark.utils.AppContants.ORDERTYPE;
import static com.yascn.smartpark.utils.AppContants.ORDERTYPELIST;
import static com.yascn.smartpark.utils.AppContants.PARKDURINGTIME;
import static com.yascn.smartpark.utils.AppContants.PAYDIALOGMONEY;
import static com.yascn.smartpark.utils.AppContants.PAYSUCCESSBROADCAST;
import static com.yascn.smartpark.utils.AppContants.REFLESHORDER;
import static com.yascn.smartpark.utils.AppContants.TOAST_DOUBLE_TIME_LIMIT;
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
import static com.yascn.smartpark.utils.TimeUtils.toTimeStr;

/**
 * Created by YASCN on 2017/9/6.
 */


public class RvOrderOngoingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OrderContract.OrderView, PayDialog.PayClickListener {

    private OrderContract.OrderPresenter orderPresenter;
    private static final int SDK_PAY_FLAG = 1;
    private BaseActivity mActivity;
    private DefaultDialog defaultDialog;
    private PayDialog payDialog;

    private String tip = "";

    private RvItemClickListener mItemClickListener;
    private RvItemLongClickListener mItemLongListener;


    private static final String TAG = "RvCarLicenseAdapter%s";
    private Ordering ordering;
    private Ordering.ObjectBean singleOrdering;
    
    private String time;
    private OrderOngoingFragment orderOngoingFragment;
    private String parkDuringTime;
    private String orderFormId;

    public RvOrderOngoingAdapter(BaseActivity activity, OrderOngoingFragment orderOngoingFragment, Ordering ordering) {

        this.mActivity = activity;
        this.ordering = ordering;
        this.orderOngoingFragment = orderOngoingFragment;
        orderPresenter = new OrderPresenter(this);
        
//        LogUtil.d(TAG, "RvCarLicenseAdapter: "+carLicense.getObject().size());

    }

    private static final int PAID_NOTOUT_NOTTIMEOUT = 0; //已支付，未出场，未超时
    private static final int NOTIN = 1;//未进场
    private static final int UNPAY_IN = 2;//已入场,未支付
    private static final int PAID_NOTOUT_TIMEOUT = 3;//已支付，未出场,已超时


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        handler.removeMessages(AppContants.ORDERLEFTTIME);

        if (viewType == PAID_NOTOUT_NOTTIMEOUT) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.layout_order_ongoing_paid_notout_nottimeout, parent, false);
            return new PaidNotoutNottimeoutViewHoder(view, mItemClickListener, mItemLongListener);
        } else if (viewType == NOTIN) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.layout_order_not_in, parent, false);
            return new NotInViewHolder(view, mItemClickListener, mItemLongListener);
        } else if (viewType == UNPAY_IN) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.layout_order_unpay_in, parent, false);
            return new UnpayInViewHolder(view, mItemClickListener, mItemLongListener);
        } else if (viewType == PAID_NOTOUT_TIMEOUT) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.layout_order_paid_notout_timeout, parent, false);
            return new PaidNotoutTimeoutViewHolder(view, mItemClickListener, mItemLongListener);
        } else {
            return null;
        }


    }

    // 订单有效总时间
    private long daojishi;
    // 订单有效总时间（接口获取）
    private String yxTime;

    // 下单时间与当前时间的时间差
    private long diffTime;

    // 下单时间
    private String xiadantime = "";

    private String orderStatus = "";




    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof PaidNotoutNottimeoutViewHoder) {
            ((PaidNotoutNottimeoutViewHoder) holder).tvParkName.setText(ordering.getObject().get(position).getNAME());
            ((PaidNotoutNottimeoutViewHoder) holder).tvCarLicense.setText(ordering.getObject().get(position).getNUMBER());
            ((PaidNotoutNottimeoutViewHoder) holder).tvOrderStatus.setText(mActivity.getString(R.string.string_paid_notout_nottimeout));
            ((PaidNotoutNottimeoutViewHoder) holder).tvInTime.setText(ordering.getObject().get(position).getSTART_TIME()+mActivity.getString(R.string.string_in_park));
            ((PaidNotoutNottimeoutViewHoder) holder).tvParkDuringTime.setText(mActivity.getString(R.string.string_park_during_title)+ordering.getObject().get(position).getPARK_TIME());
            ((PaidNotoutNottimeoutViewHoder)holder).tvMoney.setText(mActivity.getString(R.string.string_already_pay_fee_title)+mActivity.getString(R.string.cny_unit)+ordering.getObject().get(position).getYJ_MONEY());

        }else if(holder instanceof NotInViewHolder){
               ((NotInViewHolder)holder).handler.removeMessages(ORDERLEFTTIME);
            ((NotInViewHolder)holder).tvParkName.setText(ordering.getObject().get(position).getNAME());
            ((NotInViewHolder)holder).tvOrderStatus.setText(mActivity.getString(R.string.string_status_notin));
            ((NotInViewHolder)holder).tvCarLicense.setText(ordering.getObject().get(position).getNUMBER());
            ((NotInViewHolder)holder).tvRemainTime.setText("剩:"+time);
            yxTime = ordering.getObject().get(position).getYX_TIME();
            xiadantime = ordering.getObject().get(position).getORDER_TIME();

            LogUtil.d(TAG, "onBindViewHolder:yxtime "+yxTime);
            LogUtil.d(TAG,"ordertime"+xiadantime);
            try {
                diffTime = TimeUtils.getDiffTime(xiadantime, ordering.getObject().get(position).getSERVER_TIME());
                LogUtil.d(TAG, "时间差 = " + diffTime);
            } catch (ParseException e) {
                LogUtil.d(TAG, e.toString());
                e.printStackTrace();
            }

            // 倒计时增加1秒，防止服务器数据未及时刷新

                daojishi = StringUtils.emptyParseLong(yxTime) * 60 * 1000 + 2000;


            LogUtil.d(TAG, "倒计时 = " + daojishi);
            // 时间差在大于0小于倒计时为有效订单
            if (diffTime < daojishi && diffTime >= 0) { // 订单有效
                ((NotInViewHolder)holder).handler.sendEmptyMessage(ORDERLEFTTIME);
            } else { // 订单失效
                LogUtil.d(TAG, "订单失效");
                Intent intent = new Intent();
                intent.setAction(REFLESHORDER);
                if (mActivity != null) {
                    mActivity.sendBroadcast(intent);
                }
            }

            ((NotInViewHolder)holder).tvCancelOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CancelDialog cancelDialog = new CancelDialog();
                    cancelDialog.setOnCancelClick(new CancelDialog.OnCancelClick() {
                        @Override
                        public void onCancel() {
                            orderPresenter.cancel(ordering.getObject().get(position).getORDER_FORM_ID());
                        }
                    });
                    cancelDialog.show(orderOngoingFragment.getFragmentManager(), "CancelDialog");
                }
            });


        }else if(holder instanceof UnpayInViewHolder){

            ((UnpayInViewHolder)holder).tvParkName.setText(ordering.getObject().get(position).getNAME());
            ((UnpayInViewHolder)holder).tvCarLicense.setText(ordering.getObject().get(position).getNUMBER());
            ((UnpayInViewHolder)holder).tvInTime.setText(ordering.getObject().get(position).getSTART_TIME()+mActivity.getString(R.string.string_in_park));
            ((UnpayInViewHolder)holder).tvMoney.setText(mActivity.getString(R.string.string_should_pay_title)+mActivity.getString(R.string.cny_unit)+ordering.getObject().get(position).getMONEY());
            ((UnpayInViewHolder)holder).tvOrderStatus.setText(mActivity.getString(R.string.string_unpayin_status));
            ((UnpayInViewHolder)holder).tvParkDuringTime.setText(mActivity.getString(R.string.string_park_during_title)+ordering.getObject().get(position).getPARK_TIME());
            ((UnpayInViewHolder)holder).tvPay.setOnClickListener(new View.OnClickListener() {
                long lastClickTime=0;
                @Override
                public void onClick(View v) {
                    boolean flag = true;
                    long currentClickTime = System.currentTimeMillis();

                    if ((currentClickTime - lastClickTime) >= TOAST_DOUBLE_TIME_LIMIT) {
                        flag = false;
                    }
                    lastClickTime = currentClickTime;
                    if(!flag){
                        singleOrdering = ordering.getObject().get(position);
                        parkDuringTime = singleOrdering.getPARK_TIME();
                        orderFormId = ordering.getObject().get(position).getORDER_FORM_ID();
                        orderPresenter.getPaymoney(ordering.getObject().get(position).getORDER_FORM_ID(),"");
                    }else{
                        T.showShort(mActivity,StringUtils.getRString(mActivity,R.string.string_click_to_quick));
                    }




                }
            });
        }else if(holder instanceof PaidNotoutTimeoutViewHolder){

            ((PaidNotoutTimeoutViewHolder)holder).tvParkName.setText(ordering.getObject().get(position).getNAME());
            ((PaidNotoutTimeoutViewHolder)holder).tvCarLicense.setText(ordering.getObject().get(position).getNUMBER());
            ((PaidNotoutTimeoutViewHolder)holder).tvInTime.setText(ordering.getObject().get(position).getSTART_TIME()+mActivity.getString(R.string.string_in_park));
            ((PaidNotoutTimeoutViewHolder)holder).tvOrderStatus.setText(mActivity.getString(R.string.string_paid_notout_timeout));

                ((PaidNotoutTimeoutViewHolder)holder).tvTimeOut.setText(mActivity.getString(R.string.string_time_out)+ toTimeStr(StringUtils.emptyParseInt(ordering.getObject().get(position).getTIMEOUT())));


            ((PaidNotoutTimeoutViewHolder)holder).tvParkDuringTime.setText(mActivity.getString(R.string.string_park_during_title)+ordering.getObject().get(position).getPARK_TIME());
            ((PaidNotoutTimeoutViewHolder)holder).tvMoney.setText(mActivity.getString(R.string.string_already_pay_fee_title)+mActivity.getString(R.string.cny_unit)+ordering.getObject().get(position).getYJ_MONEY());
            ((PaidNotoutTimeoutViewHolder)holder).tvTimeoutMoney.setText(mActivity.getString(R.string.cny_unit)+ordering.getObject().get(position).getMONEY());
            ((PaidNotoutTimeoutViewHolder)holder).tvPay.setOnClickListener(new View.OnClickListener() {
                long lastClickTime=0;
                @Override
                public void onClick(View v) {
                    boolean flag = true;
                    long currentClickTime = System.currentTimeMillis();

                    if ((currentClickTime - lastClickTime) >= TOAST_DOUBLE_TIME_LIMIT) {
                        flag = false;
                    }
                    lastClickTime = currentClickTime;
                    if(!flag) {

                        singleOrdering = ordering.getObject().get(position);
                        parkDuringTime = singleOrdering.getPARK_TIME();
                        orderFormId = ordering.getObject().get(position).getORDER_FORM_ID();
                        orderPresenter.getPaymoney(ordering.getObject().get(position).getORDER_FORM_ID(), "");

                    }else{
                        T.showShort(mActivity,StringUtils.getRString(mActivity,R.string.string_click_to_quick));
                    }

                }
            });
        }


    }

    @Override
    public int getItemViewType(int position) {
        String orderStatus = ordering.getObject().get(position).getSTATUS();

        switch (orderStatus) {

            case ORDERRESERVE:
                //预约订单--
                return NOTIN;


            case ORDERIN:
                //进场--
                return UNPAY_IN;


            case ORDERPAIDNOTOUT:
                //支付未出场--
                return PAID_NOTOUT_NOTTIMEOUT;


            case ORDERPAIDTIMEOUT:

                return PAID_NOTOUT_TIMEOUT;


            default:
                return 0;

        }

    }

    @Override
    public int getItemCount() {
        return ordering.getObject().size();
    }

    @Override
    public void showDefaultDialog() {
        mActivity.showProgressDialog(mActivity.getString(R.string.loadingProgress));
    }

    @Override
    public void hideDefaultDialog() {
        mActivity.hidProgressDialog();
    }

    @Override
    public void refleshOrder() {
        Intent intent = new Intent();
        intent.setAction(AppContants.REFLESHORDER);
        mActivity.sendBroadcast(intent);
    }

    @Override
    public Context getContext() {
        return mActivity;
    }

    @Override
    public void showPayDialog(String totalMoney,String payMoney,String parkDuringTime) {
//        if(ClickUtil.isFastClick()){
//            T.showShort(mActivity,StringUtils.getRString(mActivity,R.string.string_click_to_quick));
//            return;
//        }

            payDialog = new PayDialog();
        payDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomDialogStyle);
        payDialog.setOnPayClickListener(this);
        Bundle args = new Bundle();
        args.putString(PAYDIALOGMONEY,payMoney);
        args.putBoolean(AppContants.ISSUPPORTWALLETPAY, true);
        args.putString(PARKDURINGTIME,this.parkDuringTime);
        args.putString(AppContants.ORDERFORMID,this.orderFormId);
        args.putString(ORDERTYPE,ORDERTYPELIST);
        if(singleOrdering.getSTATUS().equals(ORDERPAIDTIMEOUT)){
            args.putBoolean(ISSUPPORTCOUPON,false);
        }else{
            args.putBoolean(ISSUPPORTCOUPON,true);
        }

        payDialog.setArguments(args);

        payDialog.show(orderOngoingFragment.getChildFragmentManager(), "zhifuDialog");
    }

    @Override
    public void hidePayDialog() {
        if (payDialog != null) {
            payDialog.dismissAllowingStateLoss();
        }
    }

    @Override
    public void wxPay(WXPay wxPay) {
        LogUtil.d(AppContants.TAG, wxPay.toString());
        tip = wxPay.getObject().getTip();
        IWXAPI api = WXAPIFactory.createWXAPI(mActivity, AppContants.WXAPPID);
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
            T.showShort(mActivity, "未安装微信程序或版本号太低");
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
                PayTask alipay = new PayTask(mActivity);
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
    public void serverError(String errormsg) {
        T.showShort(mActivity,errormsg);
    }

    @Override
    public void showProgress() {
        mActivity.showProgressDialog(mActivity.getString(R.string.loadingProgress));
    }

    @Override
    public void hideProgress() {
        mActivity.hidProgressDialog();
    }

    @Override
    public void setWalletPayResult(WalletPayBean walletPayBean) {
        String flag= walletPayBean.getObject().getFlag();
        Intent intent = new Intent();
        intent.setAction(REFLESHORDER);
        if (mActivity != null) {
            mActivity.sendBroadcast(intent);
        }

        Intent paySuccessIntent = new Intent();
        paySuccessIntent.setAction(AppContants.PAYSUCCESSBROADCAST);
        if(mActivity!=null){
            mActivity.sendBroadcast(paySuccessIntent);
        }


        switch (flag){
            case WALLETPAYNOTINPARKSTATUS:
                T.showShort(mActivity,WALLETPAYNOTINPARK);

                break;

            case WALLETPAYSUCCESSSTATUS:
                T.showShort(mActivity,WALLETPAYSUCCESS);

                Intent intentpaySuccessIntent = new Intent();
                intentpaySuccessIntent.setAction(PAYSUCCESSBROADCAST);
                mActivity.sendBroadcast(intentpaySuccessIntent);

                break;

            case WALLETPAYMONEYNOTENOUGHSTATUS:
                T.showShort(mActivity,WALLETPAYMONEYNOTENOUGH);

                break;


            case WALLETPAYPASSWORDWRONGSTATUS:
                T.showShort(mActivity,WALLETPAYPASSWORDWRONG);

                break;

            case WALLETMONEYZEROSTATUS:
                LogUtil.d(TAG, "setWalletPayResult: 费用为0无需支付");
                T.showShort(mActivity,WALLETMONEYZERO);

                break;




        }

    }

    @Override
    public void setPayFreeResult(Pay0Bean pay0Bean) {

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
                        Toast.makeText(mActivity, "支付成功", Toast.LENGTH_SHORT).show();
                        Intent refreshIntent = new Intent();
                        refreshIntent.setAction(AppContants.REFLESHORDER);
                        mActivity.sendBroadcast(refreshIntent);

                        Intent paySuccessIntent = new Intent();
                        paySuccessIntent.setAction(AppContants.PAYSUCCESSBROADCAST);
                        mActivity.sendBroadcast(paySuccessIntent);

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        T.showShort(mActivity, mActivity.getString(R.string.string_pay_error));
                    }
                    break;



            }
        }
    };



    @Override
    public void zhifu(int type,String couponId) {
        LogUtil.d(TAG, "zhifu: "+type);
//        payDiaLogger.dismiss();
//        T.showShort(mActivity,singleOrdering.getNUMBER()+singleOrdering.getSTART_TIME()+singleOrdering.getNAME());
//        T.showShort(mActivity,couponId+"this is couponId:");
        if (type == 1) { // 微信支付
            orderPresenter.getWXPay(singleOrdering.getORDER_FORM_ID(),couponId,AppContants.PAYTYPEAPP);
        }else if (type == 2) { // 支付宝支付
            orderPresenter.getALIPay(singleOrdering.getORDER_FORM_ID(),couponId,AppContants.PAYTYPEAPP);
        }else if(type ==3){
//            T.showShort(mActivity,"余额支付");
            orderPresenter.startWalletPay(singleOrdering.getORDER_FORM_ID(),couponId,AppContants.PAYTYPEAPP);
        }


    }

    @Override
    public void showDetail() {

    }


    public class PaidNotoutNottimeoutViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        //  已支付，未出场，未超时

        @BindView(R.id.tv_park_name)
        TextView tvParkName;
        @BindView(R.id.tv_car_license)
        TextView tvCarLicense;
        @BindView(R.id.tv_in_time)
        TextView tvInTime;
        @BindView(R.id.tv_order_status)
        TextView tvOrderStatus;
        @BindView(R.id.tv_park_during_time)
        TextView tvParkDuringTime;
        @BindView(R.id.tv_money)
        TextView tvMoney;

        private RvItemClickListener mListener;
        private RvItemLongClickListener mLongListener;


        public PaidNotoutNottimeoutViewHoder(final View itemView, RvItemClickListener listener, RvItemLongClickListener longListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.mListener = listener;
            this.mLongListener = longListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);


        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (mLongListener != null) {
                mLongListener.onItemLongClick(v, getAdapterPosition());
            }
            return true;
        }

    }


    public class NotInViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        //未进场
        @BindView(R.id.tv_park_name)
        TextView tvParkName;
        @BindView(R.id.tv_car_license)
        TextView tvCarLicense;
        @BindView(R.id.tv_order_status)
        TextView tvOrderStatus;
        @BindView(R.id.tv_remain_time)
        TextView tvRemainTime;
        @BindView(R.id.tv_cancel_order)
        TextView tvCancelOrder;
        private RvItemClickListener mListener;
        private RvItemLongClickListener mLongListener;

        public Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case ORDERLEFTTIME:
                        time = TimeUtils.getTime(daojishi, diffTime);
                    tvRemainTime.setText("剩:" + time);
//                    LogUtil.d(AppConstants.TAG, "订单剩余时间\n" + time);
                        if (diffTime < daojishi && diffTime >= 0) { // 订单有效
                            handler.sendEmptyMessageDelayed(ORDERLEFTTIME, 1000);
                            diffTime += 1000;
                            LogUtil.d(TAG, "handleMessage: 1000" +
                                    "" +
                                    "");
                        } else { // 订单失效
                            LogUtil.d(TAG, "订单失效");
                        Intent intent = new Intent();
                        intent.setAction(REFLESHORDER);
                        if (mActivity != null) {
                            mActivity.sendBroadcast(intent);
                        }
                        }
                        break;
                }
            }
        };


        public NotInViewHolder(final View itemView, RvItemClickListener listener, RvItemLongClickListener longListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.mListener = listener;
            this.mLongListener = longListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);


        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (mLongListener != null) {
                mLongListener.onItemLongClick(v, getAdapterPosition());
            }
            return true;
        }

    }

    public class UnpayInViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
//已入场,未支付

        @BindView(R.id.tv_park_name)
        TextView tvParkName;
        @BindView(R.id.tv_car_license)
        TextView tvCarLicense;
        @BindView(R.id.tv_in_time)
        TextView tvInTime;
        @BindView(R.id.tv_order_status)
        TextView tvOrderStatus;
        @BindView(R.id.tv_park_during_time)
        TextView tvParkDuringTime;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_pay)
        TextView tvPay;
        private RvItemClickListener mListener;
        private RvItemLongClickListener mLongListener;


        public UnpayInViewHolder(final View itemView, RvItemClickListener listener, RvItemLongClickListener longListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.mListener = listener;
            this.mLongListener = longListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);


        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (mLongListener != null) {
                mLongListener.onItemLongClick(v, getAdapterPosition());
            }
            return true;
        }

    }


    public class PaidNotoutTimeoutViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
//已支付，未出场,已超时

        @BindView(R.id.tv_park_name)
        TextView tvParkName;
        @BindView(R.id.tv_car_license)
        TextView tvCarLicense;
        @BindView(R.id.tv_in_time)
        TextView tvInTime;
        @BindView(R.id.tv_order_status)
        TextView tvOrderStatus;
        @BindView(R.id.tv_park_during_time)
        TextView tvParkDuringTime;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_timeout_money)
        TextView tvTimeoutMoney;
        @BindView(R.id.tv_pay)
        TextView tvPay;
        @BindView(R.id.tv_time_out)
        TextView tvTimeOut;


        private RvItemClickListener mListener;
        private RvItemLongClickListener mLongListener;


        public PaidNotoutTimeoutViewHolder(final View itemView, RvItemClickListener listener, RvItemLongClickListener longListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.mListener = listener;
            this.mLongListener = longListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);


        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (mLongListener != null) {
                mLongListener.onItemLongClick(v, getAdapterPosition());
            }
            return true;
        }

    }


    public void setOnItemClickListener(RvItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public void setOnItemLongClickListener(RvItemLongClickListener listener) {
        this.mItemLongListener = listener;
    }


}