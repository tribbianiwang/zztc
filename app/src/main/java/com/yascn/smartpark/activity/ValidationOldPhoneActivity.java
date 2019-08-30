package com.yascn.smartpark.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.yascn.smartpark.R;

import com.yascn.smartpark.bean.PhoneSmsBean;
import com.yascn.smartpark.contract.SmsCodeContract;

import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.DoubleClickUtil;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.NetUtils;
import com.yascn.smartpark.utils.SPUtils;
import com.yascn.smartpark.utils.StringUtils;
import com.yascn.smartpark.utils.T;
import com.yascn.smartpark.utils.TimeService;
import com.yascn.smartpark.utils.Validator;
import com.yascn.smartpark.viewmodel.SmsCodeViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yascn.smartpark.utils.AppContants.GETSMSCODESUCCEED;
import static com.yascn.smartpark.utils.AppContants.PHONECODEINPUTERROR;
import static com.yascn.smartpark.utils.AppContants.PHONECODEOVERLIMITWARNING;

public class ValidationOldPhoneActivity extends SmsCodeBaseActivity implements SmsCodeContract.View{

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.index_toolbar)
    Toolbar indexToolbar;
    @BindView(R.id.activity_introduction)
    LinearLayout activityIntroduction;
    @BindView(R.id.et_smscode)
    EditText etSmscode;
    @BindView(R.id.tv_get_sms)
    TextView tvGetSms;
    @BindView(R.id.bt_next)
    Button btNext;
    
    @BindView(R.id.tv_phone_title)
    TextView tvPhoneTitle;

    private int number;

    private String des;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case AppContants.SMS_GETCODE_SUCCESS:

                    LogUtil.d(AppContants.TAG, "获取验证码成功");
                    T.showShort(ValidationOldPhoneActivity.this, getResources().getString(R.string.get_sms_code_success));
                    tvGetSms.setClickable(false);

                    if (number == 0) {
                        number = 60;
                        tvGetSms.setText(number + getResources().getString(R.string.sms_code_gettips));
                        Intent intent = new Intent();
                        intent.putExtra("number", number);
                        intent.setClass(ValidationOldPhoneActivity.this, TimeService.class);
                        startService(intent);
                        LogUtil.d(AppContants.TAG, "启动服务");
                    } else {
                        tvGetSms.setText(number + getResources().getString(R.string.sms_code_gettips));
                    }
                    handler.sendEmptyMessageDelayed(AppContants.SMS_COUNTDOWN, 1000);
                    break;
                case AppContants.SMS_SUBMIT_SUCCESS:
                    LogUtil.d(AppContants.TAG, "验证成功");
                    skipActivity(NewPhoneActivity.class);
                    finish();
                    break;
                case AppContants.SMS_FAIL:
                    T.showShort(ValidationOldPhoneActivity.this, des);
                    break;
                case AppContants.SMS_COUNTDOWN:
                    number--;
                    if (number == 0) {
                        handler.removeMessages(AppContants.SMS_COUNTDOWN);
                        tvGetSms.setClickable(true);
                        tvGetSms.setText("获取验证码");
                        SPUtils.put(ValidationOldPhoneActivity.this, AppContants.KEY_NUMBER, 0);
                    } else {
                        tvGetSms.setClickable(false);
                        tvGetSms.setText(number + getResources().getString(R.string.sms_code_gettips));
                        handler.sendEmptyMessageDelayed(AppContants.SMS_COUNTDOWN, 1000);
                    }
                    break;
            }
        }
    };
    private String phone;
    private String randomCode;
    private SmsCodeViewModel smsCodeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation_old_phone);
//
//
        ButterKnife.bind(this);
       phone = (String) SPUtils.get(this, AppContants.KEY_PHONE, "");
        LogUtil.d(TAG, "onCreate: valid"+phone);
        tvPhoneTitle.setText(getString(R.string.now_phone_number)+ phone);
        initTitle(getString(R.string.string_validation_old_phone));
        //1
        smsCodeViewModel = ViewModelProviders.of(this).get(SmsCodeViewModel.class);

        //2
        getLifecycle().addObserver(smsCodeViewModel);

        //3
        android.arch.lifecycle.Observer<PhoneSmsBean> phoneSmsBeanObserver = new android.arch.lifecycle.Observer<PhoneSmsBean>() {
            @Override
            public void onChanged(@Nullable PhoneSmsBean phoneSmsBean) {
                setPhoneSms(phoneSmsBean);

            }
        };

        //4
        smsCodeViewModel.getMutableLiveDataEntry().observe(this,phoneSmsBeanObserver);
        smsCodeViewModel.getQueryStatus().observe(this,statusObserver);



    }



    private static final String TAG = "ValidationOldPhoneActiv";

    @OnClick({R.id.tv_get_sms, R.id.bt_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_sms:

                if (DoubleClickUtil.isFastDoubleClick()) {
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    T.showShort(this, "手机号为空");
                    return;
                }

                LogUtil.d(TAG, "onViewClicked: "+ StringUtils.isPhoneString(phone));
                if (!Validator.isMobile(phone)){

                    T.showShort(this, "手机号格式不正确");
                    return;
                }
                LogUtil.d(AppContants.TAG, "获取验证码");
                if (NetUtils.isConnected(this)) {
                    randomCode = StringUtils.getRandomCode();
                    if(smsCodeViewModel!=null){
                        smsCodeViewModel.getPhoneSms(phone,randomCode);
                    }


//                    loginPresenter.getPhoneSms(etSmscode.getText().toString().trim());

                    //获取验证码成功

                } else {
                    T.showShort(this, "网络错误");
                }
                
                
                
                break;
            case R.id.bt_next:
//                if(TextUtils.isEmpty(phoneSmsCode)){
//                    T.showShort(this, "未获取验证码");
//                }


                if (NetUtils.isConnected(this)) {


                    if (etSmscode.getText().toString().trim().equals(randomCode)) {
                        handler.sendEmptyMessage(AppContants.SMS_SUBMIT_SUCCESS);
                    } else {
                        des = PHONECODEINPUTERROR;
                        handler.sendEmptyMessage(AppContants.SMS_FAIL);
                    }
//                    SMSSDK.submitVerificationCode("86", phone, code);
                } else {

                    T.showShort(this, "网络错误");
                }
                break;
        }
    }



    @Override
    public int[] hideSoftByEditViewIds() {
        return new int[]{R.id.et_smscode};
    }

    @Override
    public void serverError(String errorMsg) {
        T.showShort(this,errorMsg);
    }
    String phoneSmsCode;
    @Override
    public void setPhoneSms(PhoneSmsBean smsBean) {
        LogUtil.d(TAG, "setPhoneSms: flag"+smsBean.getObject().getFlag());
        if(smsBean.getObject().getFlag().equals(GETSMSCODESUCCEED)){

            handler.sendEmptyMessage(AppContants.SMS_GETCODE_SUCCESS);
        }else{
            des = PHONECODEOVERLIMITWARNING;
            handler.sendEmptyMessage(AppContants.SMS_FAIL);
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
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public Context getContext() {
        return this;
    }
}
