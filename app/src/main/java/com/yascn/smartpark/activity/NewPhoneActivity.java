package com.yascn.smartpark.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import com.yascn.smartpark.bean.ChangePhoneBean;
import com.yascn.smartpark.bean.PhoneSmsBean;
import com.yascn.smartpark.contract.ChangePhoneContract;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.DoubleClickUtil;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.LoginStatusUtils;
import com.yascn.smartpark.utils.NetUtils;
import com.yascn.smartpark.utils.SPUtils;
import com.yascn.smartpark.utils.StringUtils;
import com.yascn.smartpark.utils.T;
import com.yascn.smartpark.utils.TimeService;
import com.yascn.smartpark.utils.Validator;
import com.yascn.smartpark.viewmodel.ChangePhoneViewModel;
import com.yascn.smartpark.viewmodel.SmsCodeViewModel;

import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import static com.yascn.smartpark.utils.AppContants.GETSMSCODESUCCEED;
import static com.yascn.smartpark.utils.AppContants.KEY_JPUSH;
import static com.yascn.smartpark.utils.AppContants.PHONECODEINPUTERROR;
import static com.yascn.smartpark.utils.AppContants.PHONECODEOVERLIMITWARNING;
import static com.yascn.smartpark.utils.AppContants.REFRESHUSERINFO;

public class NewPhoneActivity extends SmsCodeBaseActivity  implements ChangePhoneContract.View{

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.index_toolbar)
    Toolbar indexToolbar;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_smscode)
    EditText etSmscode;
    @BindView(R.id.tv_get_sms)
    TextView tvGetSms;
    @BindView(R.id.bt_finish)
    Button btFinish;
    @BindView(R.id.activity_introduction)
    LinearLayout activityIntroduction;

    private int number;

    private String des;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case AppContants.SMS_GETCODE_SUCCESS:

                    LogUtil.d(AppContants.TAG, "获取验证码成功");
                    T.showShort(NewPhoneActivity.this, getResources().getString(R.string.get_sms_code_success));
                    tvGetSms.setClickable(false);

                    if (number == 0) {
                        number = 60;
                        tvGetSms.setText(number + getResources().getString(R.string.sms_code_gettips));
                        Intent intent = new Intent();
                        intent.putExtra("number", number);
                        intent.setClass(NewPhoneActivity.this, TimeService.class);
                        startService(intent);
                        LogUtil.i(AppContants.TAG, "启动服务");
                    } else {
                        tvGetSms.setText(number + getResources().getString(R.string.sms_code_gettips));
                    }
                    handler.sendEmptyMessageDelayed(AppContants.SMS_COUNTDOWN, 1000);
                    break;
                case AppContants.SMS_SUBMIT_SUCCESS:
                    LogUtil.d(AppContants.TAG, "验证成功");
//                    T.showShort(NewPhoneActivity.this,"验证成功");
                    String userid = (String) SPUtils.get(NewPhoneActivity.this,AppContants.KEY_USERID,"");
                    if(changePhoneViewModel!=null){
                        changePhoneViewModel.getChangePhoneBean(userid,getPhone());
                    }


                    break;
                case AppContants.SMS_FAIL:
                    T.showShort(NewPhoneActivity.this, des);
                    break;
                case AppContants.SMS_COUNTDOWN:
                    number--;
                    if (number == 0) {
                        handler.removeMessages(AppContants.SMS_COUNTDOWN);
                        tvGetSms.setClickable(true);
                        tvGetSms.setText("获取验证码");
                        SPUtils.put(NewPhoneActivity.this, AppContants.KEY_NUMBER, 0);
                    } else {
                        tvGetSms.setClickable(false);
                        tvGetSms.setText(number + getResources().getString(R.string.sms_code_gettips));
                        handler.sendEmptyMessageDelayed(AppContants.SMS_COUNTDOWN, 1000);
                    }
                    break;
            }
        }
    };

    private String randomCode;
    private static final String TAG = "NewPhoneActivity";
    private SmsCodeViewModel smsCodeViewModel;
    private ChangePhoneViewModel changePhoneViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_phone);
        ButterKnife.bind(this);

        initTitle(getString(R.string.string_validation_new_phone));
        //1
        smsCodeViewModel = ViewModelProviders.of(this).get(SmsCodeViewModel.class);
        changePhoneViewModel = ViewModelProviders.of(this).get(ChangePhoneViewModel.class);


        //2
        getLifecycle().addObserver(smsCodeViewModel);
        getLifecycle().addObserver(changePhoneViewModel);

        //3
        android.arch.lifecycle.Observer<PhoneSmsBean> phoneSmsBeanObserver = new android.arch.lifecycle.Observer<PhoneSmsBean>() {
            @Override
            public void onChanged(@Nullable PhoneSmsBean phoneSmsBean) {
                setPhoneSms(phoneSmsBean);

            }
        };

        android.arch.lifecycle.Observer<ChangePhoneBean> changePhoneBeanObserver = new Observer<ChangePhoneBean>() {
            @Override
            public void onChanged(@Nullable ChangePhoneBean changePhoneBean) {

                setChangephoneBean(changePhoneBean);
            }
        };


        //4
        smsCodeViewModel.getMutableLiveDataEntry().observe(this,phoneSmsBeanObserver);
        changePhoneViewModel.getMutableLiveDataEntry().observe(this,changePhoneBeanObserver);

        smsCodeViewModel.getQueryStatus().observe(this,statusObserver);




    }




    @OnClick({R.id.tv_get_sms, R.id.bt_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_sms:


                if (DoubleClickUtil.isFastDoubleClick()) {
                    return;
                }
                if (TextUtils.isEmpty(getPhone())) {
                    T.showShort(this, "手机号为空");
                    return;
                }

                LogUtil.d(TAG, "onViewClicked: "+ StringUtils.isPhoneString(getPhone()));
                if (!Validator.isMobile(getPhone())){

                    T.showShort(this, "手机号格式不正确");
                    return;
                }
                LogUtil.d(AppContants.TAG, "获取验证码");
                if (NetUtils.isConnected(this)) {
                    randomCode = StringUtils.getRandomCode();
                    if(smsCodeViewModel!=null){
                        smsCodeViewModel.getPhoneSms(getPhone(),randomCode);
                    }


//                    loginPresenter.getPhoneSms(etSmscode.getText().toString().trim());

                    //获取验证码成功

                } else {
                    T.showShort(this, "网络错误");
                }



                break;
            case R.id.bt_finish:

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

    private String getPhone(){
        return etPhone.getText().toString().trim();
    }

    @Override
    public void serverError(String errorMsg) {
        T.showShort(this,errorMsg);
    }

    @Override
    public void setChangephoneBean(ChangePhoneBean changePhoneBean) {
        if(changePhoneBean.getObject().getFlag().equals(AppContants.CHANGEPHONESUCCESS)){
            //修改成功
            serverError(getString(R.string.string_change_phone_success));
            SPUtils.put(this,AppContants.KEY_PHONE,getPhone());
            
            //sputils中的手机号保存
            //重新注册激光
            checkJPush();
            
            //发送刷新广播


            Intent intent = new Intent();
            intent.setAction(REFRESHUSERINFO);
            sendBroadcast(intent);
            //finish界面
            finish();
            
        }else{
            //修改失败
            serverError(getString(R.string.string_change_phone_failed));
            
        }


    }


    private void checkJPush() {
        if(!LoginStatusUtils.isLogin(this)){
            return;
        }

//        boolean hasSighed = (boolean) SPUtils.get(this,KEY_JPUSH,false);
//        LogUtil.d(TAG, "signJPush: hasSigned"+hasSighed);
//        if(hasSighed){
//            return;
//        }

        JPushInterface.resumePush(this);
//        LogUtil.d(TAG, "onSuccess: "+phone);
        String phone = getPhone();


        JPushInterface.setAlias(this, phone, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                if (i == 0) {
                    LogUtil.d(TAG, "gotResultlogin:jpushsuccess ");
                    SPUtils.put(NewPhoneActivity.this,KEY_JPUSH,false);

                } else {
                    LogUtil.d(TAG, "gotResultlogin:jpushfailed"+i+"--"+s);
                    LogUtil.d(AppContants.TAG, "i = " + i);
                    LogUtil.d(AppContants.TAG, "s = " + s);
//                    T.showShort(loginView.getContext(), "登录失败,请重试" );
//                    loginView.hideDialog();
                    SPUtils.put(NewPhoneActivity.this,KEY_JPUSH,true);
                }
            }
        });



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
        showProgressDialog(StringUtils.getRString(this,R.string.loadingProgress));
    }

    @Override
    public void hideProgress() {
        hidProgressDialog();
    }

    @Override
    public int[] hideSoftByEditViewIds() {
        return new int[]{R.id.et_smscode,R.id.et_phone};
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
