package com.yascn.smartpark.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.gyf.barlibrary.ImmersionBar;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;
import com.yascn.smartpark.R;
import com.yascn.smartpark.adapter.HomeVpAdapter;
import com.yascn.smartpark.base.BaseActivity;
import com.yascn.smartpark.base.BaseFragment;
import com.yascn.smartpark.bean.Login;
import com.yascn.smartpark.bean.PhoneSmsBean;
import com.yascn.smartpark.bean.UpdateBean;
import com.yascn.smartpark.contract.LoginContract;
import com.yascn.smartpark.contract.UpdateContract;
import com.yascn.smartpark.fragment.HomeFragment;
import com.yascn.smartpark.fragment.MapFragment;
import com.yascn.smartpark.fragment.MineFragment;


import com.yascn.smartpark.utils.ActivityUtil;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.KeyBoardUtils;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.NetUtils;
import com.yascn.smartpark.utils.OnSwitchFragment;
import com.yascn.smartpark.utils.SPUtils;
import com.yascn.smartpark.utils.StringUtils;
import com.yascn.smartpark.utils.T;
import com.yascn.smartpark.utils.TimeService;
import com.yascn.smartpark.utils.Validator;
import com.yascn.smartpark.view.NoTouchViewPager;
import com.yascn.smartpark.viewmodel.LoginViewModel;
import com.yascn.smartpark.viewmodel.OpinionViewModel;
import com.yascn.smartpark.viewmodel.UpdateViewModel;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
import me.majiajie.pagerbottomtabstrip.item.NormalItemView;
import pub.monkeyboss.widget.CodeInputView;

import static com.yascn.smartpark.utils.AppContants.BROADCASTACTIONSEARCHPARK;
import static com.yascn.smartpark.utils.AppContants.BROADCASTGAODE;
import static com.yascn.smartpark.utils.AppContants.BROADCASTNOWLOCATION;
import static com.yascn.smartpark.utils.AppContants.DEFAULT_OFFSCREEN_PAGES;
import static com.yascn.smartpark.utils.AppContants.DOWNLOADAPKNAME;
import static com.yascn.smartpark.utils.AppContants.DOWNLOADLOCALURL;
import static com.yascn.smartpark.utils.AppContants.GETSMSCODESUCCEED;
import static com.yascn.smartpark.utils.AppContants.KEY_JPUSH;
import static com.yascn.smartpark.utils.AppContants.KEY_USERID;
import static com.yascn.smartpark.utils.AppContants.NOWLOACTIONLON;
import static com.yascn.smartpark.utils.AppContants.NOWLOCATIONLAT;
import static com.yascn.smartpark.utils.AppContants.PHONECODEINPUTERROR;
import static com.yascn.smartpark.utils.AppContants.PHONECODEOVERLIMITWARNING;
import static com.yascn.smartpark.utils.AppContants.PRIVACYAGREEMENT;
import static com.yascn.smartpark.utils.AppContants.REFRESHUSERINFO;
import static com.yascn.smartpark.utils.StringUtils.sendRefreshOrderReceiver;

public class MainActivity extends BaseActivity implements OnSwitchFragment,UpdateContract.View,LoginContract.View ,ActivityCompat.OnRequestPermissionsResultCallback {


    @BindView(R.id.activity_material_design)
    RelativeLayout activityMaterialDesign;
    @BindView(R.id.viewPager)
    NoTouchViewPager viewPager;
    @BindView(R.id.tab)
    PageBottomTabLayout tab;
    private ArrayList<BaseFragment> mainFragments = new ArrayList<>();
    private HomeFragment homeFragment;
    private MapFragment mapFragment;
    private MineFragment mineFragment;
    private static final String TAG = "MainActivity%s";
    private NavigationController navigationController;
    private EditText etPhone;
    public BroadcastReceiver mainReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(BROADCASTACTIONSEARCHPARK)||intent.getAction().equals(BROADCASTGAODE)) {

                navigationController.setSelect(1);
                LogUtil.d(TAG, "onReceive: searchresult");

            }else if(intent.getAction().equals(BROADCASTNOWLOCATION)){
                nowlat = intent.getDoubleExtra(NOWLOCATIONLAT,0);
                nowLon = intent.getDoubleExtra(NOWLOACTIONLON,0);
                LogUtil.d(TAG, "onReceive: local"+nowLon+":"+nowLon);

            }else if(intent.getAction().equals(REFRESHUSERINFO)){
                                if (smsDialog != null) {
                    if (smsDialog.isShowing()) {
                        smsDialog.dismiss();
                        KeyBoardUtils.hideInputForce(MainActivity.this);
                    }
                }
            }
        }
    };
    private double nowlat;
    private double nowLon;
    private TextView tvSeconds;
    public AlertDialog smsDialog;
    private String randomCode;
    private AlertDialog inputPhoneDialog;
    private LoginViewModel loginViewModel;
    
    private String loginphone;
    private String des;
    private int number;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case AppContants.SMS_GETCODE_SUCCESS:

                    showSmsCodeDialog(etPhone.getText().toString());
                    inputPhoneDialog.dismiss();
                    LogUtil.d(AppContants.TAG, "获取验证码成功");
                    T.showShort(MainActivity.this, getResources().getString(R.string.get_sms_code_success));
                    tvSeconds.setClickable(false);


                    if (number == 0) {
                        number = 60;
                        tvSeconds.setText(number + getResources().getString(R.string.sms_code_gettips));
                        Intent intent = new Intent();
                        intent.putExtra("number", number);
                        intent.setClass(MainActivity.this, TimeService.class);
                        MainActivity.this.startService(intent);
                        LogUtil.d(AppContants.TAG, "启动服务");
                    } else {
                        tvSeconds.setText(number + getResources().getString(R.string.sms_code_gettips));
                    }
                    handler.sendEmptyMessageDelayed(AppContants.SMS_COUNTDOWN, 1000);
                    break;
                case AppContants.SMS_SUBMIT_SUCCESS:
                    LogUtil.d(AppContants.TAG, "验证成功");

                    if(loginViewModel!=null){
                        loginViewModel.startLogin(getPhoneNumber());    
                    }
                 
                    
                    
                    break;
                case AppContants.SMS_FAIL:

                    T.showShort(MainActivity.this, des);
                    break;
                case AppContants.SMS_COUNTDOWN:
                    number--;
                    if (number == 0) {
                        handler.removeMessages(AppContants.SMS_COUNTDOWN);
                        tvSeconds.setClickable(true);
                        tvSeconds.setText("获取验证码");
                        SPUtils.put(MainActivity.this, AppContants.KEY_NUMBER, 0);
                    } else {
                        tvSeconds.setClickable(false);
                        tvSeconds.setText(number + getResources().getString(R.string.sms_code_gettips));
                        handler.sendEmptyMessageDelayed(AppContants.SMS_COUNTDOWN, 1000);
                    }
                    break;
            }
        }
    };
    private ImmersionBar mImmersionBar;
    private AlertDialog privacyDialog;
    private AlertDialog installApkDialog;
    private UpdateViewModel updateViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注意该方法要再setContentView方法之前实现

        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();//隐藏掉整个ActionBar，包括下面的Tabs


        ButterKnife.bind(this);

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        
        getLifecycle().addObserver(loginViewModel);
        
        Observer<PhoneSmsBean> smsBeanObserver = new Observer<PhoneSmsBean>() {
            @Override
            public void onChanged(@Nullable PhoneSmsBean phoneSmsBean) {
                
                setSmsCode(phoneSmsBean);
                
                
            }
        };
        
        Observer<Login> loginObserver = new Observer<Login>() {
            @Override
            public void onChanged(@Nullable Login login) {
                
                setLoginResult(login);
                
            }
        };
        
        
        loginViewModel.getLoginMutableLiveData().observe(this,loginObserver);
        loginViewModel.getPhoneSmsBeanMutableLiveData().observe(this,smsBeanObserver);
        loginViewModel.getQueryStatus().observe(this,statusObserver);
        
        
        
        
        
        
        
        
        
        
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();   //所有子类都将继承这些相同的属性
        String userid = (String) SPUtils.get(this,KEY_USERID,"");
        LogUtil.d(TAG, "onCreate: "+userid);

        signJPush();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BROADCASTACTIONSEARCHPARK);
        intentFilter.addAction(BROADCASTNOWLOCATION);
        intentFilter.addAction(BROADCASTGAODE);
        intentFilter.addAction(REFRESHUSERINFO);
        registerReceiver(mainReceiver, intentFilter);
        initView();
        showPrivacyDialog();
    ;


    }

    private void signJPush() {
       boolean hasSighed = (boolean) SPUtils.get(this,KEY_JPUSH,false);
        LogUtil.d(TAG, "signJPush: hasSigned"+hasSighed);
       if(hasSighed){
           return;
       }

        JPushInterface.resumePush(this);
//        LogUtil.d(TAG, "onSuccess: "+phone);
         String phone = (String) SPUtils.get(this, AppContants.KEY_PHONE,"");


        JPushInterface.setAlias(this, phone, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                if (i == 0) {
                    LogUtil.d(TAG, "gotResult:jpushsuccess ");
                    SPUtils.put(MainActivity.this,KEY_JPUSH,true);

                } else {
                    LogUtil.d(TAG, "gotResult:jpushfailed"+i+"--"+s);
                    LogUtil.d(AppContants.TAG, "i = " + i);
                    LogUtil.d(AppContants.TAG, "s = " + s);
//                    T.showShort(this, "登录失败,请重试" );
//                    loginView.hideDialog();
                    SPUtils.put(MainActivity.this,KEY_JPUSH,false);
                }
            }
        });



    }


    private boolean hasUpdated = false;
    private void getUpdateInfo() {
        if(hasUpdated){
            return;
        }
       updateViewModel.getUpdateBean();
    }



    private void initView() {
        initFragments();

        LogUtil.d(TAG, "onCreate: " + tab.toString());

        navigationController = tab.custom()
                .addItem(newItem(R.drawable.icon_home_tab_normal, R.drawable.icon_home_tab_selected,"主页"))
                .addItem(newItem(R.drawable.icon_map_tab_normal, R.drawable.icon_map_tab_selected,"附近"))

                .addItem(newItem(R.drawable.icon_mine_tab_normal, R.drawable.icon_mine_tab_selected,"个人"))
                .build();
        //设置显示小圆点
        if(!(boolean)SPUtils.get(MainActivity.this,AppContants.HASCLICKMINE,false)){
            navigationController.setHasMessage(2,true);
        }

        viewPager.setOffscreenPageLimit(DEFAULT_OFFSCREEN_PAGES);
        viewPager.setAdapter(new HomeVpAdapter(getSupportFragmentManager(), mainFragments));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                LogUtil.d(TAG, "onPageSelected: "+position);
                if(position==2){
                    SPUtils.put(MainActivity.this,AppContants.HASCLICKMINE,true);
                    navigationController.setHasMessage(2,false);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //自动适配ViewPager页面切换
        navigationController.setupWithViewPager(viewPager);


    }


    private void initFragments() {
        homeFragment = new HomeFragment();
        mapFragment = new MapFragment();
        mineFragment = new MineFragment();

        mainFragments.add(0, homeFragment);
        mainFragments.add(1, mapFragment);
        mainFragments.add(2, mineFragment);
    }



    public void showInputPhoneDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View inputPhoneView = View.inflate(this, R.layout.layout_dialog_input_phone, null);
        ImageView ivClose = (ImageView) inputPhoneView.findViewById(R.id.iv_close);
        etPhone = (EditText) inputPhoneView.findViewById(R.id.et_phone);
        final Button btNext = (Button) inputPhoneView.findViewById(R.id.bt_next);
        etPhone.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 输入的内容变化的监听

                if (s.length() > 10 && Validator.isMobile(s.toString())) {
                    btNext.setBackgroundResource(R.drawable.selector_blue_deep);


                } else {
                    btNext.setBackgroundColor(StringUtils.getRColor(MainActivity.this, R.color.special_gray));
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                // 输入后的监听

            }
        });

        builder.setView(inputPhoneView);
        inputPhoneDialog = builder.show();
        inputPhoneDialog.setCancelable(false);
        inputPhoneDialog.setCanceledOnTouchOutside(false);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputPhoneDialog.dismiss();
                KeyBoardUtils.hideInputForce(MainActivity.this);
            }
        });
        btNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (Validator.isMobile(etPhone.getText().toString().trim())) {

                    if (NetUtils.isConnected(MainActivity.this)) {

                        randomCode = StringUtils.getRandomCode();
                        LogUtil.d(TAG, "onClick: " + randomCode);
                        loginphone = etPhone.getText().toString().trim();

                        if(loginViewModel!=null){
                            loginViewModel.getSmsCode(getPhoneNumber(),randomCode);
                        }
                     
                        //获取验证码成功


                    } else {
                        T.showShort(MainActivity.this, "网络错误");
                    }


                }

            }
        });
    }


    private void showSmsCodeDialog(final String phone) {
        LogUtil.d(TAG, "showSmsCodeDialog: " + phone);
        AlertDialog.Builder builder = new AlertDialog.Builder( MainActivity.this);
        View smsDialogView = View.inflate( MainActivity.this, R.layout.layout_dialog_sms, null);
        TextView tvPhone = (TextView) smsDialogView.findViewById(R.id.tv_phone);
        tvSeconds = (TextView) smsDialogView.findViewById(R.id.tv_seconds);
        tvSeconds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvSeconds.getText().equals(StringUtils.getRString( MainActivity.this, R.string.getSMS))) {
                    randomCode = StringUtils.getRandomCode();
                    if(loginViewModel!=null){
                        loginViewModel.getSmsCode(phone,randomCode);
                    }

                }
            }
        });
        ImageView ivClose = (ImageView) smsDialogView.findViewById(R.id.iv_close);
        CodeInputView inputView = (CodeInputView) smsDialogView.findViewById(R.id.code_input);

        tvPhone.setText(phone);
        builder.setView(smsDialogView);
        smsDialog = builder.show();
        smsDialog.setCancelable(false);
        smsDialog.setCanceledOnTouchOutside(false);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smsDialog.dismiss();
                KeyBoardUtils.hideInputForce( MainActivity.this);
            }
        });
        smsDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                InputMethodManager inputMgr = (InputMethodManager) MainActivity.this
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMgr.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
            }
        });


        inputView.setOnInputListener(new CodeInputView.OnInputListener() {
            @Override
            public void onInput(int i, char c, String s) {

            }

            @Override
            public void onDelete(String s) {

            }

            @Override
            public void onComplete(String s) {
                KeyBoardUtils.hideInputForce(MainActivity.this);


                if (NetUtils.isConnected(MainActivity.this)) {


                    LogUtil.d(TAG, "onComplete: s" + s + "---random---" + randomCode);


                    if (s.equals(randomCode)) {
                        handler.sendEmptyMessage(AppContants.SMS_SUBMIT_SUCCESS);
                    } else {
                        des = PHONECODEINPUTERROR;
                        handler.sendEmptyMessage(AppContants.SMS_FAIL);
                    }
//                    SMSSDK.submitVerificationCode("86", phone, code);
                } else {

                    T.showShort( MainActivity.this, "网络错误");
                }

            }
        });


    }






    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length()-1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void requestPermission() {
        // Fragment:
        AndPermission.with(this)
                .requestCode(100)
                .permission(
Permission.LOCATION,Permission.STORAGE,Permission.CAMERA
                )
                .callback(listener)
                .start();

    }


    private boolean hasRequestUrlsBefore = false;
    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // Successfully.

            if(requestCode == 100) {



                if(hasRequestUrlsBefore){
                    return;
                }else{

                    updateViewModel = ViewModelProviders.of(MainActivity.this).get(UpdateViewModel.class);

                    getLifecycle().addObserver(updateViewModel);

                    Observer<UpdateBean> updateBeanObserver = new Observer<UpdateBean>() {
                        @Override
                        public void onChanged(@Nullable UpdateBean updateBean) {
                            setUpdateBean(updateBean);
                        }
                    };

                    updateViewModel.getMutableLiveDataEntry().observe(MainActivity.this,updateBeanObserver);
                    updateViewModel.getQueryStatus().observe(MainActivity.this,statusObserver);

                    if(updateViewModel!=null){
                        getUpdateInfo();
                    }

                    Intent intent = new Intent();
                    intent.setAction(AppContants.LOCATIONPERMISSIONSUCCESS);
                    sendBroadcast(intent);
                    hasRequestUrlsBefore = true;
                }


                // TODO ...
//                T.showShort(MainActivity.this,"successful");
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // Failure.
            if(requestCode == 100) {
                // TODO ...
//                T.showShort(this,"failed");
                showToSettingDialog();
            }
        }
    };


    private void showPrivacyDialog() {
  /*
  这里使用了 android.support.v7.app.AlertDialog.Builder
  可以直接在头部写 import android.support.v7.app.AlertDialog
  那么下面就可以写成 AlertDialog.Builder


  */

        boolean hasSurePrivacy = (boolean) SPUtils.get(this,PRIVACYAGREEMENT,false);
        LogUtil.d(TAG, "showUpdateDialog: "+hasSurePrivacy);
        if(hasSurePrivacy){
            requestPermission();
//            getUpdateInfo();
            return;
        }
        LogUtil.d(TAG, "showPrivacyDialog: ");
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        View privacyDialogView = View.inflate(this,R.layout.layout_dialog_privacy,null);
            TextView tvGetMorePrivacy =  privacyDialogView.findViewById(R.id.tv_get_more_privacy);


         builder.setView(privacyDialogView);

         if(privacyDialog!=null){
             if(privacyDialog.isShowing()){
                 return;
             }
         }

        privacyDialog = builder.show();
        tvGetMorePrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skipActivity(PrivacyDetailActivity.class);
                privacyDialog.dismiss();
            }
        });
        privacyDialog.setCancelable(false);


    }



    private void showToSettingDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("友情提示");
        builder.setCancelable(false);
        builder.setMessage("运行缺少权限(定位,读取手机存储,相机)，请点击“设置”-“权限”打开所需权限");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                System.exit(0);
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ToSetting();
            }
        });
        builder.show();

    }

    public void ToSetting(){
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", this.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", this.getPackageName());
        }
        startActivity(localIntent);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
      showPrivacyDialog();
    }

    @Override
    public void switchFragment(int index) {
        if(navigationController!=null){
            navigationController.setSelect(index);
        }
    }

    private long firstTime = 0;
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        switch(keyCode)
        {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {  //如果两次按下退出键的时差超过了两秒
                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;//更新firstTime


                    return true;
                } else { //两次按下的时间差小于两秒时
                    //退出app
                    ActivityUtil.finishAll();
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mainReceiver);

        if (smsDialog != null) {
            smsDialog.cancel();
            smsDialog = null;
        }

        if (inputPhoneDialog != null) {
            inputPhoneDialog.cancel();
            inputPhoneDialog = null;
        }

        if(privacyDialog!=null){
            privacyDialog.cancel();
            privacyDialog = null;
        }

        if(installApkDialog!=null){
            installApkDialog.cancel();
            installApkDialog = null;

        }


        if (mImmersionBar != null)
            mImmersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
    }

    //创建一个Item
    private BaseTabItem newItem(int drawable,int checkedDrawable,String text){
        NormalItemView normalItemView = new NormalItemView(this);
        normalItemView.initialize(drawable,checkedDrawable,text);
        normalItemView.setTextDefaultColor(Color.GRAY);
        normalItemView.setTextCheckedColor(StringUtils.getRColor(this,R.color.design_main_blue));
        return normalItemView;
    }

    @Override
    public void serverError(String errorMsg) {

    }
    private AlertDialog updateAlertDialog;
    @Override
    public void setUpdateBean(UpdateBean updateBean) {

        int versonCode = StringUtils.getLocalVersion(this);
        String serverVersonCodeString = updateBean.getObject().getVs_no();
            if(versonCode<StringUtils.emptyParseInt(serverVersonCodeString.substring(1,serverVersonCodeString.length()))){
                showUpdateDialog(updateBean.getObject());
            }


    }

    private void showUpdateDialog(final UpdateBean.ObjectBean vsContentObject) {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("有新版本啦");
        builder.setMessage(vsContentObject.getVs_content());
        builder.setNegativeButton("下次再说吧", null);
        builder.setPositiveButton("我要更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                downloadApk(vsContentObject.getVs_url());//开启线程下载App
//                checkIfisAndroidM();
            }
        });
        updateAlertDialog = builder.show();
    }

    private ProgressBar pbDownload;
    private void downloadApk(String vs_url) {
        hasUpdated = true;
        updateAlertDialog.dismiss();

        View progressView = View.inflate(MainActivity.this, R.layout.download_dialog_view, null);
        pbDownload = (ProgressBar) progressView.findViewById(R.id.pb_downlaod);
        final TextView tvPersend = (TextView) progressView.findViewById(R.id.tv_persent);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("正在下载");
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.setView(progressView);


        final AlertDialog progressshow = builder.show();
        progressshow.setCancelable(false);
        progressshow.setCanceledOnTouchOutside(false);
        final NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(0);
        OkHttpUtils//
                .get()//
                .url(vs_url)//
                .build()//
                .execute(new FileCallBack(DOWNLOADLOCALURL, DOWNLOADAPKNAME)//
                {

                    @Override
                    public void onError(okhttp3.Call call, Exception e, int id) {
                        LogUtil.d(TAG, "onError: "+e.getMessage()+e.toString());

                    }

                    @Override
                    public void onResponse(File response, int id) {

                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        super.inProgress(progress, total, id);
                        LogUtil.e(TAG, "progress" + total + "+" + progress);
                        pbDownload.setProgress((int) progress);
                        pbDownload.setMax((int) total);


                        tvPersend.setText(numberFormat.format(progress * 100) + "%");
                        if ("100".equals(numberFormat.format(progress * 100))) {

                            T.showShort(MainActivity.this, "下载完成");
                            progressshow.dismiss();

                            checkIfisAndroidM();


//                            isFirstLoadInstall = false;
                        }
                    }
                });


    }

    private static final int INSTALL_PACKAGES_REQUESTCODE = 911;

    private void checkIfisAndroidM() {
        if (Build.VERSION.SDK_INT >= 26) {
            boolean b = getPackageManager().canRequestPackageInstalls();
            if (b) {
                openApk();;//安装应用的逻辑(写自己的就可以)
            } else {
                //请求安装未知应用来源的权限
                android.support.v7.app.AlertDialog.Builder getInstallApkDialogbuilder = new android.support.v7.app.AlertDialog.Builder(this);
                getInstallApkDialogbuilder.setTitle(getString(R.string.string_install_apk_title));
                getInstallApkDialogbuilder.setMessage(getString(R.string.string_install_apk_content));
                getInstallApkDialogbuilder.setNegativeButton("取消.", null);
                getInstallApkDialogbuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Uri packageURI = Uri.parse("package:"+MainActivity.this.getPackageName());
                        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,packageURI);
                        startActivityForResult(intent, INSTALL_PACKAGES_REQUESTCODE);
                    }
                });

                if(installApkDialog==null){
                    installApkDialog = getInstallApkDialogbuilder.show();
                }else{
                    installApkDialog.show();
                }
                installApkDialog.setCanceledOnTouchOutside(false);
                installApkDialog.setCancelable(false);

            }
        } else {
            openApk();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.d(TAG, "onActivityResult: "+resultCode+"requestcode"+requestCode);
        if (requestCode == INSTALL_PACKAGES_REQUESTCODE) {
            checkIfisAndroidM();
        }

    }


        private boolean hasOpenedApk = false;

    /**
     * 打开已经安装好的apk
     */
    private void openApk() {
        if(hasOpenedApk){
            return;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        File fileData=  new File(DOWNLOADLOCALURL, DOWNLOADAPKNAME);
        LogUtil.d(TAG, "openApk: "+fileData.getAbsolutePath()+"---"+fileData.getPath());
        Uri uriData;

        // 判断版本大于等于7.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // "net.csdn.bLogger.ruancoder.fileprovider"即是在清单文件中配置的authorities
            uriData = FileProvider.getUriForFile(this, "com.yascn.smartpark.fileProvider", fileData);
            // 给目标应用一个临时授权
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(uriData, "application/vnd.android.package-archive");
        } else {
            uriData = Uri.fromFile(fileData);
            intent.setDataAndType(uriData, "application/vnd.android.package-archive");
        }


        startActivity(intent);



        hasOpenedApk = true;


    }




    @Override
    public void showProgress() {
        showProgressDialog(StringUtils.getRString(this,R.string.loadingProgress));

    }

    @Override
    public void hideProgress() {
        hidProgressDialog();
    }


    public String getPhoneNumber() {
        return loginphone;
    }



    @Override
    public Context getContext() {
        return this;
    }



    @Override
    public void setSmsCode(PhoneSmsBean smsBean) {
        LogUtil.d(TAG, "setPhoneSms: flag" + smsBean.getObject().getFlag());
        if (smsBean.getObject().getFlag().equals(GETSMSCODESUCCEED)) {
            handler.sendEmptyMessage(AppContants.SMS_GETCODE_SUCCESS);
        } else {
            des = PHONECODEOVERLIMITWARNING;
            handler.sendEmptyMessage(AppContants.SMS_FAIL);
        }

    }

    @Override
    public void setLoginResult(Login login) {
//        0:用户名或密码错误;
//        1:登录成功;

        switch (login.getObject().getFlag()) {
            case "0":

                T.showShort(this, "登录失败,请重试");
                hideProgress();
                break;
            case "1":


//                final String phone = loginView.getPhoneNumber();
                SPUtils.put(this, AppContants.KEY_LOGIN, true);
                SPUtils.put(this,KEY_JPUSH,false);
                SPUtils.put(this, AppContants.KEY_USERID, login.getObject().getUser_id());
                SPUtils.put(this, AppContants.KEY_PHONE,getPhoneNumber());
                sendRefreshOrderReceiver(this);



                Intent intent = new Intent();
                intent.setAction(REFRESHUSERINFO);
                this.sendBroadcast(intent);

                break;
        }

    }
}
