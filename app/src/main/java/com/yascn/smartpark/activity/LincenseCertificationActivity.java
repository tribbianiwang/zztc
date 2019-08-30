package com.yascn.smartpark.activity;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import com.yascn.smartpark.R;
import com.yascn.smartpark.base.BaseActivity;
import com.yascn.smartpark.bean.LicenseCertificationResultBean;
import com.yascn.smartpark.contract.CarLincenseCertificationContract;
import com.yascn.smartpark.dialog.ImgPickDialog;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.ImageViewUtils;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.StringUtils;
import com.yascn.smartpark.utils.T;
import com.yascn.smartpark.viewmodel.CarCertificationViewModel;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yascn.smartpark.utils.AppContants.BROADCASTLICENSE;
import static com.yascn.smartpark.utils.AppContants.BROADCASTLICENSEDATA;
import static com.yascn.smartpark.utils.AppContants.CARLICENSE;
import static com.yascn.smartpark.utils.AppContants.CARLICENSEID;
import static com.yascn.smartpark.utils.AppContants.CERTIFICATIONLICENSEFAIL;
import static com.yascn.smartpark.utils.AppContants.CERTIFICATIONLICENSEFAILCODE;
import static com.yascn.smartpark.utils.AppContants.CERTIFICATIONLICENSENOTMATCH;
import static com.yascn.smartpark.utils.AppContants.CERTIFICATIONLICENSENOTMATCHCODE;
import static com.yascn.smartpark.utils.AppContants.CERTIFICATIONLICENSESUCCESS;
import static com.yascn.smartpark.utils.AppContants.CERTIFICATIONLICENSESUCCESSCODE;
import static com.yascn.smartpark.utils.AppContants.IMGALBUM;
import static com.yascn.smartpark.utils.AppContants.IMGPHOTO;
import static com.yascn.smartpark.utils.AppContants.REFRESHCARNUMBER;
import static com.yascn.smartpark.utils.AppContants.UPLOADLICENSEFAIL;
import static com.yascn.smartpark.utils.AppContants.UPLOADLICENSEFAILCODE;

public class LincenseCertificationActivity extends BaseActivity implements ImgPickDialog.chooseImgListener ,CarLincenseCertificationContract.View{

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.index_toolbar)
    Toolbar indexToolbar;
    @BindView(R.id.tv_not_certification)
    TextView tv_not_certification;
    @BindView(R.id.iv_license)
    ImageView ivLicense;
    @BindView(R.id.ll_button_get_pic)
    LinearLayout llButtonGetPic;
    @BindView(R.id.rl_get_photo)
    RelativeLayout rlGetPhoto;
    @BindView(R.id.tv_certification_now)
    TextView tvCertificationNow;

    private ImgPickDialog imgPickDialog;
    private AlertDialog noticeDialog;

    private static final String TAG = "LincenseCertificationAc";

    private ImgSelConfig config;
    // 自定义图片加载器

    private String imgPath;

    private ImageLoader loader = new ImageLoader() {
        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    };

    public BroadcastReceiver lincenseCertification = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(BROADCASTLICENSE)) {
                String licenseData = intent.getStringExtra(BROADCASTLICENSEDATA);
                LogUtil.d(TAG, "onReceive: "+licenseData);
                ImageViewUtils.showLocal(context,licenseData,ivLicense);
                imgPath = licenseData;

                setIvLicenseVisible(true);


            }
        }
    };

    private String trancarlicenseId;
    private String carLincense;
    private CarCertificationViewModel carCertificationViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lincense_certification);
        ButterKnife.bind(this);

        trancarlicenseId = getIntent().getStringExtra(CARLICENSEID);
        carLincense = getIntent().getStringExtra(CARLICENSE);
        tvTitle.setText(carLincense +StringUtils.getRString(this,R.string.car_certification));

        //1
        carCertificationViewModel = ViewModelProviders.of(this).get(CarCertificationViewModel.class);

        //2
        getLifecycle().addObserver(carCertificationViewModel);

        //3
        Observer<LicenseCertificationResultBean> certificationResultBeanObserver = new Observer<LicenseCertificationResultBean>() {
            @Override
            public void onChanged(@Nullable LicenseCertificationResultBean licenseCertificationResultBean) {
                setCertificationResult(licenseCertificationResultBean);
            }
        };

        //4
        carCertificationViewModel.getMutableLiveDataEntry().observe(this,certificationResultBeanObserver);
        carCertificationViewModel.getQueryStatus().observe(this,statusObserver);




        initImageSelectorConfit();
        initToolBar();
        setIvLicenseVisible(false);
        initBroadcast();
    }

    private void initBroadcast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BROADCASTLICENSE);

        registerReceiver(lincenseCertification, intentFilter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(lincenseCertification);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_license_certfication, menu);
        return true;
    }


    /**
     * 初始化toolbar
     */
    private void initToolBar() {

        indexToolbar.setNavigationIcon(R.drawable.ic_back);
        indexToolbar.inflateMenu(R.menu.menu_license_certfication);
//        setSupportActionBar(indexToolbar);
        indexToolbar.setTitleTextColor(Color.WHITE);
        indexToolbar.setTitle("");
        indexToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        indexToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (R.id.bt_considerations == item.getItemId()) {

                    showNoticeDailog();
                }
                return false;
            }
        });
    }

    private void showNoticeDailog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.dialog_fullscreen);
        final View noticeDialogView = View.inflate(this, R.layout.dialog_notice_certification, null);
        builder.setView(noticeDialogView);
        noticeDialog = builder.show();
        setDialogWindowAttr(noticeDialog, this);
        Button btIKnow = (Button) noticeDialogView.findViewById(R.id.bt_i_know);
        btIKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noticeDialog.dismiss();
            }
        });

    }

    //在diaLogger.show()之后调用
    public static void setDialogWindowAttr(Dialog dlg, Context ctx) {
        Window window = dlg.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();

        lp.gravity = Gravity.FILL;
        lp.width = WindowManager.LayoutParams.FILL_PARENT;//宽高可设置具体大小
        lp.height = WindowManager.LayoutParams.FILL_PARENT;
        dlg.getWindow().setAttributes(lp);
    }

    @OnClick({R.id.tv_certification_now, R.id.rl_get_photo, R.id.tv_not_certification})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_get_photo:
//                skipActivity(CardCameraActivity.class);
                imgPickDialog = new ImgPickDialog();
                imgPickDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomDialogStyle);
                imgPickDialog.setOnChooseImgListener(this);
                imgPickDialog.show(getSupportFragmentManager(), "zhifuDialog");

                break;

            case R.id.tv_not_certification:

                finish();

                break;

            case R.id.tv_certification_now:
                if(!TextUtils.isEmpty(imgPath)){
                    if(carCertificationViewModel!=null){
                        carCertificationViewModel.postCarLicense(trancarlicenseId,imgPath);
                    }

                }else{
                    T.showShort(LincenseCertificationActivity.this,StringUtils.getRString(this,R.string.please_select_img));
                }

//                T.showShort(LincenseCertificationActivity.this,imgPath);

                break;

        }


    }


    @Override
    public void choseImg(int type) {
        switch (type) {
            case IMGPHOTO:

                imgPickDialog.dismiss();
                skipActivity(CardCameraActivity.class);

                break;

            case IMGALBUM:
                ImgSelActivity.startActivity(this, config, AppContants.IMAGE_SELECT_REQUEST_CODE);
                imgPickDialog.dismiss();
                break;

        }


    }


    public void setIvLicenseVisible(boolean ivLicenseVisible){
        ivLicense.setVisibility(ivLicenseVisible?View.VISIBLE:View.GONE);
        llButtonGetPic.setVisibility(ivLicenseVisible?View.GONE:View.VISIBLE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 图片选择结果回调
        if (requestCode == AppContants.IMAGE_SELECT_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
            imgPath = pathList.get(0);


            LogUtil.d(AppContants.TAG, "imgPath = " + imgPath);
            if (imgPath != null) {

                ImageViewUtils.showLocal(this,imgPath,ivLicense);
                setIvLicenseVisible(true);


            } else {
                T.showShort(this, "选择图片失败请重新选择");
            }


//            setAvatar(imgPath);
        }
    }

    private void initImageSelectorConfit() {

        // 自由配置选项
        config = new ImgSelConfig.Builder(this, loader)
                // 是否多选, 默认true
                .multiSelect(false)
                // 是否记住上次选中记录, 仅当multiSelect为true的时候配置，默认为true
                .rememberSelected(false)
                // “确定”按钮背景色
                .btnBgColor(getResources().getColor(R.color.colorPrimary))
                // “确定”按钮文字颜色
                .btnTextColor(Color.BLUE)
                // 使用沉浸式状态栏
                .statusBarColor(getResources().getColor(R.color.colorPrimary))
                // 返回图标ResId
                .backResId(R.drawable.icon_back)
                // 标题
                .title("选择行驶证")
                // 标题文字颜色
                .titleColor(Color.WHITE)
                // TitleBar背景色
                .titleBgColor(getResources().getColor(R.color.colorPrimary))
                // 裁剪大小。needCrop为true的时候配置
                .cropSize(300, 209, 300, 209)
                .needCrop(true)
                // 第一个是否显示相机，默认true
                .needCamera(false)
                // 最大选择图片数量，默认9
                .maxNum(1)
                .build();


    }

    @Override
    public void serverError(String errorMsg) {
        T.showShort(this,StringUtils.getRString(this,R.string.nodata_error));

    }

    @Override
    public void setCertificationResult(LicenseCertificationResultBean carLicenseResult) {
        hideProgress();
        String resultFlag = carLicenseResult.getObject().getFlag();
        switch (resultFlag){
            case UPLOADLICENSEFAILCODE:
                T.showShort(this,UPLOADLICENSEFAIL);
                break;

            case CERTIFICATIONLICENSESUCCESSCODE:
                T.showShort(this,CERTIFICATIONLICENSESUCCESS);
                sendRefreshCarNumberBroadcast();
                finish();
                break;

            case CERTIFICATIONLICENSENOTMATCHCODE:
                T.showShort(this,CERTIFICATIONLICENSENOTMATCH);
                break;

            case CERTIFICATIONLICENSEFAILCODE:
                T.showShort(this,CERTIFICATIONLICENSEFAIL);
                break;
        }



    }

    private void sendRefreshCarNumberBroadcast(){
        Intent intent = new Intent();
        intent.setAction(REFRESHCARNUMBER);
        sendBroadcast(intent);
    }

    @Override
    public void showProgress() {

        showProgressDialog(StringUtils.getRString(this,R.string.uploading));
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
