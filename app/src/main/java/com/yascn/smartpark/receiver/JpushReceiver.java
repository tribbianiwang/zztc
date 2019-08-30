package com.yascn.smartpark.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.gson.Gson;

import com.yascn.smartpark.activity.MessageDetailActivity;
import com.yascn.smartpark.activity.OrderActivity;
import com.yascn.smartpark.bean.JPush;
import com.yascn.smartpark.bean.JpushTypeBean;
import com.yascn.smartpark.bean.MessageIdBean;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

import static com.yascn.smartpark.utils.AppContants.JPUSHTYPEMESSAGE;
import static com.yascn.smartpark.utils.AppContants.REFRESHMESSAGENUMBER;

/**
 * Created by YASCN on 2017/3/10.
 */

public class JpushReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";



    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        LogUtil.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            LogUtil.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            LogUtil.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
//            processCustomMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            LogUtil.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            LogUtil.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

            if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                LogUtil.d(TAG, "This message has no Extra data");
                return;
            }

            try {
                JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                Iterator<String> it = json.keys();

                while (it.hasNext()) {
                    String myKey = it.next().toString();

                    if (myKey.equals("val")) {
                        String value = json.optString(myKey);
                        LogUtil.d(AppContants.TAG, "推送收到的信息 = " + value.toString());
                        Gson gson = new Gson();

                        JpushTypeBean   jpushTypeBean = gson.fromJson(value,JpushTypeBean.class);
                        LogUtil.d(TAG, "onReceive: type type tpye"+jpushTypeBean.getType());
                        if(jpushTypeBean.getType().equals(AppContants.JPUSHTYPEPARKINFO)){
                            //车辆进出场
                            JPush jPush = gson.fromJson(value, JPush.class);
                            if (jPush.getFLAG().equals("1")) {
                                Intent intent1 = new Intent();
                                intent1.putExtra("jpush", jPush);
                                intent1.setAction(AppContants.REFLESHORDER);
                                context.sendBroadcast(intent1);
                            } else if (jPush.getFLAG().equals("3")) {
                                Intent intent1 = new Intent();
                                intent1.putExtra("jpush", jPush);
                                intent1.setAction(AppContants.REFLESHOUT);
                                context.sendBroadcast(intent1);
                            }

                        }else if(jpushTypeBean.getType().equals(JPUSHTYPEMESSAGE)){
                         //消息界面bean

                            Intent messageIntent = new Intent();
                            messageIntent.setAction(REFRESHMESSAGENUMBER);
                            context.sendBroadcast(messageIntent);


                        }


                    }
                }
            } catch (JSONException e) {
                LogUtil.e(TAG, "Get message extra JSON error!");
            }

            Intent gengxin = new Intent();
            gengxin.setAction(AppContants.REFLESHORDER);
            context.sendBroadcast(gengxin);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            LogUtil.d(TAG, "[MyReceiver] 用户点击打开了通知"+    bundle.getString(JPushInterface.EXTRA_EXTRA));


            try {
                JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                Iterator<String> it = json.keys();

                while (it.hasNext()) {
                    String myKey = it.next().toString();

                    if (myKey.equals("val")) {
                        String value = json.optString(myKey);
                        LogUtil.d(AppContants.TAG, "推送收到的信息 = " + value.toString());
                        Gson gson = new Gson();

                        JpushTypeBean   jpushTypeBean = gson.fromJson(value,JpushTypeBean.class);
                        LogUtil.d(TAG, "onReceive: type type tpye"+jpushTypeBean.getType());
                        if(jpushTypeBean.getType().equals(AppContants.JPUSHTYPEPARKINFO)){
                            //车辆进出场
                            Intent  i = new Intent(context, OrderActivity.class);
                            i.putExtras(bundle);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);

                        }else if(jpushTypeBean.getType().equals(JPUSHTYPEMESSAGE)){
                            MessageIdBean messageIdBean = gson.fromJson(value,MessageIdBean.class);
                            intent = new Intent(context, MessageDetailActivity.class);
                            intent.putExtra(AppContants.MESSAGEBEANIDTRANS,messageIdBean.getMsg_id());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);

                        }


                    }
                }
            } catch (JSONException e) {
                LogUtil.e(TAG, "Get message extra JSON error!");
            }





            try {
                JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                Iterator<String> it = json.keys();

                while (it.hasNext()) {
                    String myKey = it.next().toString();

                    if (myKey.equals("flag")) {
                        String value = json.optString(myKey);
                        if (value.equals("3")) {
                            LogUtil.d(TAG, "出场");
                        } else if (value.equals("1")) {
                            LogUtil.d(TAG, "进场");

                        }
                    }
                }
            } catch (JSONException e) {
                LogUtil.e(TAG, "Get message extra JSON error!");
            }

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            LogUtil.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            LogUtil.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            LogUtil.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    LogUtil.d(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    LogUtil.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }


}