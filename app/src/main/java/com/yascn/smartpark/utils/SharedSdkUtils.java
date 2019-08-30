package com.yascn.smartpark.utils;

import android.app.Activity;

import cn.sharesdk.onekeyshare.OnekeyShare;

import static com.yascn.smartpark.utils.AppContants.SHAREDIMGURL;
import static com.yascn.smartpark.utils.AppContants.SHAREDTEXT;
import static com.yascn.smartpark.utils.AppContants.SHAREDTITLE;
import static com.yascn.smartpark.utils.AppContants.SHAREDURL;

/**
 * Created by YASCN on 2017/10/11.
 */

public class SharedSdkUtils {

    public static void beginToShared(Activity activity) {

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

// 启动分享GUI
        oks.show(activity);

    }
}
