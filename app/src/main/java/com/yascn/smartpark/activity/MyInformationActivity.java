package com.yascn.smartpark.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.yascn.smartpark.R;
import com.yascn.smartpark.base.BaseActivity;
import com.yascn.smartpark.bean.EditU;
import com.yascn.smartpark.bean.Userinfo;
import com.yascn.smartpark.contract.InfoContract;
import com.yascn.smartpark.dialog.DefaultDialog;
import com.yascn.smartpark.dialog.SetBirthdayDialog;
import com.yascn.smartpark.dialog.SetGenderDialog;
import com.yascn.smartpark.dialog.SetNameDialog;
import com.yascn.smartpark.dialog.SetPhoneDialog;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.ImageViewUtils;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.NetUtils;
import com.yascn.smartpark.utils.SPUtils;
import com.yascn.smartpark.utils.T;
import com.yascn.smartpark.viewmodel.InfoViewModel;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import static com.yascn.smartpark.utils.AppContants.MINEDATADEFULT;
import static com.yascn.smartpark.utils.AppContants.REFRESHUSERINFO;
import static com.yascn.smartpark.utils.ImageViewUtils.getDefaultHead;
import static com.yascn.smartpark.utils.StringUtils.sendRefreshOrderReceiver;

/**
 * Created by YASCN on 2017/8/29.
 */

public class MyInformationActivity extends BaseActivity implements InfoContract.View {


    private String imgPath;
    private ImgSelConfig config;


    @BindView(R.id.userAvatar)
    ImageView userAvatar;

    @BindView(R.id.nicknameView)
    RelativeLayout nicknameView;

    @BindView(R.id.genderView)
    RelativeLayout genderView;

    @BindView(R.id.birthdayView)
    RelativeLayout birthdayView;

    @BindView(R.id.phonenumberView)
    RelativeLayout phonenumberView;

    @BindView(R.id.nickname)
    TextView nickname;

    @BindView(R.id.gender)
    TextView gender;

    @BindView(R.id.birthday)
    TextView birthday;


    @BindView(R.id.phoneNumber)
    TextView phoneNumber;

    @BindView(R.id.signout)
    Button signout;

    private DefaultDialog defaultDialog;

    private SetNameDialog setNameDialog;
    private SetGenderDialog setGenderDialog;
    private SetBirthdayDialog setBirthdayDialog;
    private SetPhoneDialog setPhoneDialog;

    private Userinfo userinfo;
    private Bundle bundle;

    // 自定义图片加载器
    private ImageLoader loader = new ImageLoader() {
        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    };

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 1000) {
                setAvatar(imgPath);
            }
        }
    };

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(REFRESHUSERINFO)) {
                LogUtil.d(TAG, "刷新");
             finish();
            }
        }
    };
    private InfoViewModel infoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_information);

        ButterKnife.bind(this);
        initTitle(getResources().getString(R.string.information));

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(REFRESHUSERINFO);
        registerReceiver(broadcastReceiver,intentFilter);


        userinfo = (Userinfo) getIntent().getSerializableExtra("userinfo");
//        LogUtil.d(AppContants.TAG, "传递的数据 = " + userinfo.toString());
        if(userinfo!=null){
            setData(userinfo);

        }

        // 自由配置选项
        config = new ImgSelConfig.Builder(this, loader)
                // 是否多选, 默认true
                .multiSelect(false)
                // 是否记住上次选中记录, 仅当multiSelect为true的时候配置，默认为true
                .rememberSelected(false)
                // “确定”按钮背景色
                .btnBgColor(Color.GRAY)
                // “确定”按钮文字颜色
                .btnTextColor(Color.BLUE)
                // 使用沉浸式状态栏
                .statusBarColor(getResources().getColor(R.color.colorPrimary))
                // 返回图标ResId
                .backResId(R.drawable.icon_back)
                // 标题
                .title("图片")
                // 标题文字颜色
                .titleColor(Color.WHITE)
                // TitleBar背景色
                .titleBgColor(getResources().getColor(R.color.colorPrimary))
                // 裁剪大小。needCrop为true的时候配置
                .cropSize(1, 1, 200, 200)
                .needCrop(true)
                // 第一个是否显示相机，默认true
                .needCamera(false)
                // 最大选择图片数量，默认9
                .maxNum(1)
                .build();

        //1
        infoViewModel = ViewModelProviders.of(this).get(InfoViewModel.class);

        //2
        getLifecycle().addObserver(infoViewModel);

        //3
        Observer<EditU> editUObserver = new Observer<EditU>() {
            @Override
            public void onChanged(@Nullable EditU editU) {
                setUserinfoResult(editU);
            }
        };

        //4
        infoViewModel.getMutableLiveDataEntry().observe(this,editUObserver);
        infoViewModel.getQueryStatus().observe(this,statusObserver);



        bundle = new Bundle();
        bundle.putString("name", userinfo.getObject().getName());
        bundle.putString("gender", userinfo.getObject().getGender());
        bundle.putString("birthday", (String) userinfo.getObject().getBirthday());
        bundle.putString("phone", userinfo.getObject().getPhone());
    }


    private static final String TAG = "MyInformationActivity";
    public void setData(Userinfo userinfo) {
        LogUtil.d(TAG, "setData: img"+userinfo.getObject().getImg());

        ImageViewUtils.showImage(this,userinfo.getObject().getImg(),userAvatar,getDefaultHead(),getDefaultHead());
        if (TextUtils.isEmpty(userinfo.getObject().getName())) {
            nickname.setText(getResources().getString(R.string.notset));
        } else {
            nickname.setText(userinfo.getObject().getName());
        }

        if (TextUtils.isEmpty(userinfo.getObject().getGender())) {
            gender.setText(getResources().getString(R.string.notset));
        } else {
            if (userinfo.getObject().getGender().equals("w")) {
                gender.setText(getResources().getString(R.string.woman));
            } else if (userinfo.getObject().getGender().equals("n")) {
                gender.setText(getResources().getString(R.string.man));
            }
        }

        if (TextUtils.isEmpty((String) userinfo.getObject().getBirthday())) {
            birthday.setText(getResources().getString(R.string.notset));
        } else {
            birthday.setText((String) userinfo.getObject().getBirthday());
        }

        phoneNumber.setText(userinfo.getObject().getPhone());
    }

    public void setAvatar(String imgPath) {
        ImageViewUtils.showImage(this,imgPath,userAvatar,getDefaultHead(),getDefaultHead());
    }

    public void sendRefresh() {

        SPUtils.put(this, AppContants.KEY_USERAVATAR, imgPath);

        Intent intent = new Intent();
        intent.setAction(REFRESHUSERINFO);
        sendBroadcast(intent);
    }



    @Override
    public void serverError(String errorMsg) {
        T.showShort(this,errorMsg);
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


    private void showOutLogDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("退出登录");
        builder.setMessage("确定退出登录?");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (NetUtils.isConnected(MyInformationActivity.this)) {



                   showProgress();
                    JPushInterface.setAlias(MyInformationActivity.this, "", new TagAliasCallback() {
                        @Override
                        public void gotResult(int i, String s, Set<String> set) {
                            LogUtil.d(AppContants.TAG, "i = " + i);
                           hideProgress();

                            Toast.makeText(MyInformationActivity.this, "退出登录成功", Toast.LENGTH_SHORT).show();
                            SPUtils.put(MyInformationActivity.this, AppContants.KEY_LOGIN, false);

                            if (i == 0) {
                                LogUtil.d(TAG, "gotResultoutlogin:success");
                                SPUtils.put(MyInformationActivity.this,AppContants.KEY_JPUSH,false);
                            } else {
                                LogUtil.d(TAG, "gotResultoutlogin:failed");
                                SPUtils.put(MyInformationActivity.this,AppContants.KEY_JPUSH,true);
//                                Toast.makeText(MyInformationActivity.this, "退出登录失败", Toast.LENGTH_SHORT).show();
                            }

                            SPUtils.put(MyInformationActivity.this, AppContants.KEY_LOGIN, false);
                            SPUtils.put(MyInformationActivity.this, AppContants.KEY_USERID,"");
                            SPUtils.put(MyInformationActivity.this, AppContants.KEY_PHONE,"");
                            Intent intent = new Intent();
                            intent.setAction(MINEDATADEFULT);
                            sendBroadcast(intent);
                            sendRefreshOrderReceiver(MyInformationActivity.this);
//                            ActivityUtil.finishMainActivity();
//                            Intent intent = new Intent();
//                            intent.setClass(MyInformationActivity.this, LoginActivity.class);
//                            startActivity(intent);
                            finish();
                        }
                    });

                } else {
                    Toast.makeText(MyInformationActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_information, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                String userID = (String) SPUtils.get(this, AppContants.KEY_USERID, "");
                String name = nickname.getText().toString().trim();
                String gen = "";
                if (gender.getText().toString().trim().equals(getResources().getString(R.string.man))) {
                    gen = "n";
                } else {
                    gen = "w";
                }

                if (TextUtils.isEmpty(imgPath)) {
                    imgPath = (String) SPUtils.get(this, AppContants.KEY_USERAVATAR, "");
                    LogUtil.d(TAG, "onOptionsItemSelected: imagepath"+imgPath);
                }

                String birtyday = birthday.getText().toString().trim();
                String phone = phoneNumber.getText().toString().trim();
                if(infoViewModel!=null){
                    infoViewModel.setUserinfo(userID, imgPath, name, gen, birtyday, phone);
                }

                return true;
            default:
                // If we got here, the us
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 图片选择结果回调
        if (requestCode == AppContants.IMAGE_SELECT_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
            imgPath = pathList.get(0);
            LogUtil.d(AppContants.TAG, "imgPath = " + imgPath);
            handler.sendEmptyMessage(1000);
//            setAvatar(imgPath);
        }
    }



    @OnClick({R.id.userAvatar, R.id.nicknameView, R.id.genderView, R.id.birthdayView, R.id.phonenumberView, R.id.signout,R.id.rl_head_icon})
    public void onViewClicked(View view) {


        switch (view.getId()) {
            case R.id.rl_head_icon:
                ImgSelActivity.startActivity(this, config, AppContants.IMAGE_SELECT_REQUEST_CODE);
                break;
            case R.id.nicknameView:
                setNameDialog = new SetNameDialog();
                setNameDialog.setArguments(bundle);
                setNameDialog.show(getSupportFragmentManager(), "SetNameDialog");
                setNameDialog.setNameClick(new SetNameDialog.SetNameClick() {
                    @Override
                    public void setNameClick(String name) {
                        nickname.setText(name);
                    }
                });
                break;
            case R.id.genderView:
                setGenderDialog = new SetGenderDialog();
                bundle.putString("name", userinfo.getObject().getName());
                setGenderDialog.setArguments(bundle);
                setGenderDialog.show(getSupportFragmentManager(), "SetGenderDialog");
                setGenderDialog.setGenderClick(new SetGenderDialog.SetGenderClick() {
                    @Override
                    public void setGenderClick(String gen) {
                        if (gen.equals("w")) {
                            gender.setText(getResources().getString(R.string.woman));
                        } else if (gen.equals("n")) {
                            gender.setText(getResources().getString(R.string.man));
                        }
                    }
                });
                break;
            case R.id.birthdayView:
                setBirthdayDialog = new SetBirthdayDialog();

//                setBirthdaydialog.setArguments(bundle);
                setBirthdayDialog.show(getSupportFragmentManager(), "SetBirthdayDialog");
                setBirthdayDialog.setBirthdayClick(new SetBirthdayDialog.SetBirthdayClick() {
                    @Override
                    public void setBirthdayClick(String date) {
                        birthday.setText(date);
                    }
                });
                break;
            case R.id.phonenumberView:
                skipActivity(ValidationOldPhoneActivity.class);
                    Intent intent = new Intent();

//                setPhoneDialog = new SetPhoneDialog();
//                setPhonedialog.setArguments(bundle);
//                setPhonedialog.show(getSupportFragmentManager(), "SetPhoneDialog");
//                setPhonedialog.setPhoneClick(new SetPhonedialog.SetPhoneClick() {
//                    @Override
//                    public void setPhoneClick(String phone) {
//                        phoneNumber.setText(phone);
//                    }
//                });
                break;
            case R.id.signout:
                showOutLogDialog();
                break;
        }


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);



    }

    @Override
    public void setUserinfoResult(EditU editU) {
        if (editU.getObject().getFlag().equals("1")) {
            T.showShort(this, "修改成功");
         sendRefresh();
        } else {
            T.showShort(this, "修改失败");
        }
    }
}
