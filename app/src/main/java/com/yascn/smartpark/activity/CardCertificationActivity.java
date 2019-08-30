package com.yascn.smartpark.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import com.yascn.smartpark.R;
import com.yascn.smartpark.base.BaseActivity;
import com.yascn.smartpark.bean.AliNotifyTotalBean;
import com.yascn.smartpark.bean.AliNotifyValueBean;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.StringUtils;
import com.yascn.smartpark.utils.T;
import com.yascn.smartpark.utils.aliutils.AliNotifyDemo;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnCompressListener;

import static com.yascn.smartpark.utils.AppContants.AFTERNOTIFYRESULTBROADCAST;
import static com.yascn.smartpark.utils.AppContants.AFTERNOTIFYRESULTRESULT;
import static com.yascn.smartpark.utils.AppContants.CARLICENSE;

public class CardCertificationActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.index_toolbar)
    Toolbar indexToolbar;
    @BindView(R.id.tv_vin)
    TextView tvVin;
    @BindView(R.id.tv_engine_num)
    TextView tvEngineNum;
    @BindView(R.id.tv_certification_now)
    TextView tvCertificationNow;
    @BindView(R.id.tv_upload_img_certification)
    TextView tvUploadImgCertification;
    @BindView(R.id.tv_car_license)
    TextView tvCarLicense;
    @BindView(R.id.et_owner_name)
    EditText etOwnerName;
    @BindView(R.id.et_vin)
    EditText etVin;
    @BindView(R.id.et_engine_num)
    EditText etEngineNum;
    @BindView(R.id.et_model)
    EditText etModel;
    private ImgSelConfig config;
    // 自定义图片加载器

    private String imgPath;

    private ImageLoader loader = new ImageLoader() {
        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    };
    private static final String TAG = "CardCertificationActivi";


    private BroadcastReceiver mainBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (AFTERNOTIFYRESULTBROADCAST.equals(intent.getAction())) {
                String afterNotifyResult = intent.getStringExtra(AFTERNOTIFYRESULTRESULT);
                LogUtil.d(TAG, "onReceive: " + afterNotifyResult);
                hidProgressDialog();

                if (!TextUtils.isEmpty(afterNotifyResult)) {
                    setNotifiedData(afterNotifyResult);
                } else {
                    Toast.makeText(context, "识别出错,请重新拍", Toast.LENGTH_SHORT).show();
                }


            }

        }
    };
    private String transCarLicense;

    private void setNotifiedData(String afterNotifyResult) {



        AliNotifyTotalBean aliNotifyTotalBean = new Gson().fromJson(afterNotifyResult, AliNotifyTotalBean.class);
        AliNotifyValueBean aliNotifyValueBean = new Gson().fromJson(aliNotifyTotalBean.getOutputs().get(0).getOutputValue().getDataValue(), AliNotifyValueBean.class);
        String owner = aliNotifyValueBean.getOwner();//车主
        String plateNum = aliNotifyValueBean.getPlate_num();//车牌号
        String model = aliNotifyValueBean.getModel();//品牌型号
        String vin = aliNotifyValueBean.getVin();//车辆识别代号
        String userCharater = aliNotifyValueBean.getUse_character();//使用性质
        String issueDate = aliNotifyValueBean.getIssue_date();//发证日期
        String vehicleType = aliNotifyValueBean.getVehicle_type();//车辆类型
        String engineNum = aliNotifyValueBean.getEngine_num();//发动机号码
        String registerDate = aliNotifyValueBean.getRegister_date();//注册日期
        String id = aliNotifyValueBean.getRequest_id();//id

        if(!TextUtils.isEmpty(transCarLicense)&&!TextUtils.isEmpty(plateNum)){
            if(!transCarLicense.equals(plateNum)){
                T.showShort(this,"识别出的车牌与绑定车牌不一致,请检查");
            }
        }


        etOwnerName.setText(owner);
        etVin.setText(vin);
        etEngineNum.setText(engineNum);
        etModel.setText(model);




    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_certification);
        ButterKnife.bind(this);

        transCarLicense = getIntent().getStringExtra(CARLICENSE);
        tvCarLicense.setText(transCarLicense);


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(AFTERNOTIFYRESULTBROADCAST);
        registerReceiver(mainBroadcast, intentFilter);


        initTitle(getString(R.string.car_certification));

        setTextViewStarCOlor(tvVin, "车架号码*:");
        setTextViewStarCOlor(tvEngineNum, "发动机号*:");

        initImageSelectorConfit();


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
                .cropSize(1, 1, 300, 300)
                .needCrop(true)
                // 第一个是否显示相机，默认true
                .needCamera(true)
                // 最大选择图片数量，默认9
                .maxNum(1)
                .build();


    }


    private void setTextViewStarCOlor(TextView tv, String text) {
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        style.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(new ForegroundColorSpan(Color.RED), 4, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(style);

    }

    @OnClick({R.id.tv_certification_now, R.id.tv_upload_img_certification})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_certification_now:
                break;
            case R.id.tv_upload_img_certification:
                ImgSelActivity.startActivity(this, config, AppContants.IMAGE_SELECT_REQUEST_CODE);


                break;
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
            if (imgPath != null) {

                showProgressDialog(StringUtils.getRString(CardCertificationActivity.this, R.string.loadingProgress));
                String aliCertificationReqBody = StringUtils.getAliCertificationReqBody(imgPath);
                AliNotifyDemo.asyncTest(aliCertificationReqBody, this);


            } else {
                T.showShort(this, "选择图片失败请重新选择");
            }


//            setAvatar(imgPath);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mainBroadcast);

    }
}
