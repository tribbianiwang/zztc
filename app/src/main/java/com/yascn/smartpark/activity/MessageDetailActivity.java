package com.yascn.smartpark.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.yascn.smartpark.R;
import com.yascn.smartpark.base.BaseActivity;
import com.yascn.smartpark.utils.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yascn.smartpark.utils.AppContants.MESSAGEBEANIDTRANS;
import static com.yascn.smartpark.utils.AppContants.MESSAGEDETAILROOTURL;

public class MessageDetailActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.index_toolbar)
    Toolbar indexToolbar;
    @BindView(R.id.wv)
    WebView webView;
    @BindView(R.id.activity_introduction)
    LinearLayout activityIntroduction;

    //    private MessageBean.ObjectBean messageBean;
    private String messageId;

    private static final String TAG = "MessageDetailActivity%s";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        ButterKnife.bind(this);
        messageId = getIntent().getStringExtra(MESSAGEBEANIDTRANS);
        initTitle(getString(R.string.string_info_detail));

        initWebView();

    }


    private void initWebView() {

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.loadUrl(MESSAGEDETAILROOTURL+messageId);

        LogUtil.d(TAG, "initWebView: "+MESSAGEDETAILROOTURL+messageId);



        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });


        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress >= 100) {
                   hidProgressDialog();
                }else {
                    showProgressDialog(getString(R.string.loadingProgress));
                }

            }
        });
    }




}
