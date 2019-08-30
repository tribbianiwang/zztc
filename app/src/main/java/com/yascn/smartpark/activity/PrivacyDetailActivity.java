package com.yascn.smartpark.activity;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.yascn.smartpark.R;
import com.yascn.smartpark.base.BaseActivity;
import com.yascn.smartpark.utils.ActivityUtil;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.DensityUtils;
import com.yascn.smartpark.utils.NotchScreenUtils;
import com.yascn.smartpark.utils.SPUtils;
import com.yascn.smartpark.utils.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PrivacyDetailActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tv_disgree)
    TextView tvDisgree;
    @BindView(R.id.tv_agree)
    TextView tvAgree;
    @BindView(R.id.wv_privacy)
    WebView wvPrivacy;
    private AlertDialog refuseDialog;
    private ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_detail);
        mImmersionBar = ImmersionBar.with(this).statusBarDarkFont(true, 0.2f);
        mImmersionBar.init();   //所有子类都将继承这些相同的属性

        ButterKnife.bind(this);
        setToolbarMargin();
        showHtml();
    }


    private void setToolbarMargin() {

        ViewUtils.setMargins(tvTitle,0, (int) DensityUtils.dp2px(this,NotchScreenUtils.getIntchHeight(this)),0,0);
    }


    private void showHtml() {
        wvPrivacy.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        wvPrivacy.getSettings().setBuiltInZoomControls(false);
        wvPrivacy.getSettings().setSupportZoom(false);
        wvPrivacy.getSettings().setUseWideViewPort(false);
        wvPrivacy.loadUrl("file:///android_asset/PrivacyPolicy.html");


        wvPrivacy.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress >= 100) {
                    hideProgress();
                }else {
                    showProgress();
                }

            }
        });

    }

    @OnClick({R.id.tv_disgree, R.id.tv_agree})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_disgree:

                showRefuseDialog();
                break;
            case R.id.tv_agree:
                SPUtils.put(this, AppContants.PRIVACYAGREEMENT,true);
                finish();
                break;
        }
    }

    private void showRefuseDialog() {

            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
            View refuseDialogView = View.inflate(this,R.layout.layout_dialog_privacy_refuse,null);
            TextView tvQuit = refuseDialogView.findViewById(R.id.tv_quit);
            TextView tvReread = refuseDialogView.findViewById(R.id.tv_reread);

            builder.setView(refuseDialogView);
        refuseDialog = builder.show();
        tvQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtil.finishAll();
                System.exit(0);
            }
        });
        tvReread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refuseDialog.dismiss();
            }
        });


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (refuseDialog != null) {
            refuseDialog.cancel();
            refuseDialog = null;
        }
        if (mImmersionBar != null)
            mImmersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
    }
}



