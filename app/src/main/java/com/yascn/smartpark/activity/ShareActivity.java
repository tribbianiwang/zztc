package com.yascn.smartpark.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yascn.smartpark.R;
import com.yascn.smartpark.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;

import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

import static com.yascn.smartpark.utils.AppContants.SHAREDIMGURL;
import static com.yascn.smartpark.utils.AppContants.SHAREDTEXT;
import static com.yascn.smartpark.utils.AppContants.SHAREDTITLE;
import static com.yascn.smartpark.utils.AppContants.SHAREDURL;
import static com.yascn.smartpark.utils.ImageViewUtils.getDrawableBitmap;

public class ShareActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.index_toolbar)
    Toolbar indexToolbar;
    @BindView(R.id.activity_introduction)
    LinearLayout activityIntroduction;
    @BindView(R.id.iv_qr_code)
    ImageView ivQrCode;
    @BindView(R.id.ll_weixin_friends)
    LinearLayout llWeixinFriends;
    @BindView(R.id.ll_weixin_circles)
    LinearLayout llWeixinCircles;
    @BindView(R.id.ll_qq)
    LinearLayout llQq;
    @BindView(R.id.ll_weibo)
    LinearLayout llWeibo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        ButterKnife.bind(this);
        initToolbar();


        Bitmap mBitmap = CodeUtils.createImage(SHAREDURL, 400, 400, getDrawableBitmap(this,R.mipmap.icon_logo));
        ivQrCode.setImageBitmap(mBitmap);

    }

    private void initToolbar() {

        indexToolbar.setNavigationIcon(R.drawable.icon_back);

        indexToolbar.setTitleTextColor(Color.WHITE);
        indexToolbar.setTitle("");
        indexToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


    public void showShare(String platForm) {
        OnekeyShare oks = new OnekeyShare();

        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle(SHAREDTITLE);
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl(SHAREDURL);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(SHAREDTEXT);
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(SHAREDIMGURL);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(SHAREDURL);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(SHAREDTEXT);
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(SHAREDURL);
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(SHAREDURL);

        oks.setPlatform(platForm);
        oks.show(this);
    }

    @OnClick({R.id.ll_weixin_friends, R.id.ll_weixin_circles, R.id.ll_qq, R.id.ll_weibo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_weixin_friends:
                showShare(Wechat.NAME);
////                showShare(Wechat.SHARE_WXMINIPROGRAM);
//                OnekeyShare oks = new OnekeyShare();
//                oks.setText(SHAREDTEXT);
//                oks.setPlatform(Wechat.NAME);
//                oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
//                    @Override
//                    public void onShare(Platform platform, Platform.ShareParams shareParams) {
//                        shareParams.setText(SHAREDTEXT);
//                        shareParams.setUrl(SHAREDURL);
//                        shareParams.setTitle(SHAREDTITLE);
//                        shareParams.setImageUrl(SHAREDIMGURL);
//                        shareParams.setShareType(Platform.SHARE_WXMINIPROGRAM);
//                    }
//                });
//
//                oks.show(this);
                break;
            case R.id.ll_weixin_circles:
                showShare(WechatMoments.NAME);
                break;
            case R.id.ll_qq:
                showShare(QQ.NAME);
                break;
            case R.id.ll_weibo:
                showShare(SinaWeibo.NAME);
                break;
        }
    }


}
