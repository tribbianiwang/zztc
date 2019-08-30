package com.yascn.smartpark.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yascn.smartpark.R;
import com.yascn.smartpark.base.BaseActivity;
import com.yascn.smartpark.bean.OrderInfoBean;
import com.yascn.smartpark.contract.OrderInfoContract;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.LoginStatusUtils;
import com.yascn.smartpark.utils.T;
import com.yascn.smartpark.viewmodel.OrderInfoViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yascn.smartpark.utils.AppContants.ORDERINFOBEAN;
import static com.yascn.smartpark.utils.AppContants.ORDERINFOID;

public class QrCodeActivity extends BaseActivity implements OrderInfoContract.View {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.index_toolbar)
    Toolbar indexToolbar;
    @BindView(R.id.activity_introduction)
    LinearLayout activityIntroduction;
    @BindView(R.id.fl_my_container)
    FrameLayout flMyContainer;
    @BindView(R.id.ll_light_up)
    LinearLayout llLightUp;
    @BindView(R.id.tv_isLight_up)
    TextView tvIsLightUp;

    private String orderInfoId;

    private CaptureFragment captureFragment;
    private OrderInfoViewModel orderInfoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        ButterKnife.bind(this);


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



        initTitle(getString(R.string.string_qr_code));
        initQRcodeView();

    }

    private void initQRcodeView() {
        /**
         * 执行扫面Fragment的初始化操作
         */
//       if(captureFragment==null){
           captureFragment = new CaptureFragment();
           // 为二维码扫描界面设置定制化界面
           CodeUtils.setFragmentArgs(captureFragment, R.layout.layout_qrcode_view);
//       }


        captureFragment.setAnalyzeCallback(analyzeCallback);
        /**
         * 替换我们的扫描控件
         */
        getSupportFragmentManager().beginTransaction().add(R.id.fl_my_container, captureFragment).commit();


    }

    private static final String TAG = "QrCodeActivity";

    private static final int REQUEST_CODE = 10010;
    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {

        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
//            T.showShort(QrCodeActivity.this,result);
            char[] resultChars = result.toCharArray();
            int equalsIndex = 0;
            for(int i=0;i<resultChars.length;i++){
                if(resultChars[i]=='='){
                    equalsIndex = i;
                    break;
                }
            }

            LogUtil.d(TAG, "onAnalyzeSuccess: "+result);
            LogUtil.d(TAG, "onAnalyzeSuccess: "+result.substring(equalsIndex+1));
            LogUtil.d(TAG, "onAnalyzeSuccess: "+equalsIndex);
            if(equalsIndex==0){
                T.showShort(QrCodeActivity.this,getString(R.string.error_qr_code));
//                captureFragment = null;
                initQRcodeView();
            }else{
                orderInfoId = result.substring(equalsIndex+1);
                if(orderInfoViewModel!=null){
                    orderInfoViewModel.getOrderInfo(orderInfoId,LoginStatusUtils.getUserId(QrCodeActivity.this));
                }

            }

        }

        @Override
        public void onAnalyzeFailed() {
//            T.showShort(QrCodeActivity.this,"扫描失败");
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            resultIntent.putExtras(bundle);
            QrCodeActivity.this.setResult(RESULT_OK, resultIntent);
            QrCodeActivity.this.finish();

        }
    };


    boolean isLightUp = false;

    @OnClick(R.id.ll_light_up)
    public void onViewClicked() {
        if(isLightUp){
            tvIsLightUp.setText(getString(R.string.string_point_light));
            CodeUtils.isLightEnable(false);
            isLightUp = false;
        }else{
            tvIsLightUp.setText(getString(R.string.string_click_close));
            CodeUtils.isLightEnable(true);
            isLightUp = true;
        }


    }

    @Override
    public void serverError(String errorMsg) {
        T.showShort(this,errorMsg);
    }



    @Override
    public void setOrderInfo(OrderInfoBean orderInfoBean) {
        if(orderInfoBean.getObject()!=null){
            if(!TextUtils.isEmpty(orderInfoBean.getObject().getFlag())){
                if (orderInfoBean.getObject().getFlag().equals("0")) {
                    T.showShort(this, getString(R.string.string_qr_code_outoftime));
                }else{
                    Intent intent = new Intent(QrCodeActivity.this,PayParkFeeActivity.class);
//            intent.putExtra(CARLICENSE,carNumber);
//            intent.putExtra(ORDERBEAN,orderInfoBean);
                    intent.putExtra(ORDERINFOID,orderInfoId);
                    intent.putExtra(ORDERINFOBEAN,orderInfoBean);
                    startActivity(intent);
                    finish();
                }
            }else{
                initQRcodeView();
                T.showShort(this,getString(R.string.string_qr_code_not_work_in_this_park_now));
            }
        }else{
            initQRcodeView();
            T.showShort(this,getString(R.string.string_qr_code_not_work_in_this_park_now));
        }

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

}
