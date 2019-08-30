package com.yascn.smartpark.wxapi;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bumptech.glide.util.LogTime;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yascn.smartpark.R;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.T;

import static com.yascn.smartpark.utils.AppContants.REFLESHORDER;

public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    private IWXAPI api;
    private static final String TAG = "WXPayEntryActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, AppContants.WXAPPID);

        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            switch (resp.errCode) {
                case 0:
                    T.showShort(this, getString(R.string.payed_success_leave_now));

                    LogUtil.d(AppContants.TAG, "发起广播");
                    Intent refreshIntent = new Intent();
                    refreshIntent.setAction(REFLESHORDER);
                    sendBroadcast(refreshIntent);


                    Intent paySuccessIntent = new Intent();
                    paySuccessIntent.setAction(AppContants.PAYSUCCESSBROADCAST);
                    sendBroadcast(paySuccessIntent);


//                    Intent intent1 = new Intent();
//                    intent1.setAction(AppContants.SELECTCOMPLETE);
//                    sendBroadcast(intent1);
//                    Intent intent2 = new Intent();
//                    intent2.setAction(AppContants.PAY_SUCCESS);
//                    sendBroadcast(intent2);
//
//                    Intent intent5 = new Intent();
//                    intent5.setAction(AppContants.REFLESH_MONEY);
//                    sendBroadcast(intent5);

                    finish();
                    break;
                case -1:
                    T.showShort(this, "付款失败");
                    finish();
                    break;
                case -2:
                    T.showShort(this, "支付取消");
                    finish();
                    break;
                default:
                    finish();
                    break;
            }

        }
    }

    @Override
    protected void onDestroy() {
        LogUtil.d(TAG, "销毁");
        super.onDestroy();
    }
}