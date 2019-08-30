//
//  Created by  fred on 2017/1/12.
//  Copyright © 2016年 Alibaba. All rights reserved.
//

package com.yascn.smartpark.utils.aliutils;
import com.alibaba.cloudapi.sdk.BaseApiClient;
import com.alibaba.cloudapi.sdk.BaseApiClientBuildParam;
import com.alibaba.cloudapi.sdk.CallMethod;
import com.alibaba.cloudapi.sdk.HttpConstant;
import com.alibaba.cloudapi.sdk.SdkException;

import okhttp3.Callback;


public class SyncApiClient extends BaseApiClient {
    public final static String HOST = "dm-53.data.aliyun.com";

    public static SyncApiClient build(BaseApiClientBuildParam buildParam){
        if(null == buildParam){
            throw new SdkException("buildParam must not be null");
        }

        buildParam.check();
        return  new SyncApiClient(buildParam);
    }

    private SyncApiClient(BaseApiClientBuildParam buildParam){
        super(buildParam);
    }



    public void 印刷文字识别_行驶证识别(byte[] body , Callback callback) {
        String path = "/rest/160601/ocr/ocr_vehicle.json";
        
        httpPost(HttpConstant.CLOUDAPI_HTTP, HOST , path , null , null , body , null , callback , CallMethod.SYNC);
    }
}