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


public class AsyncApiClient extends BaseApiClient {
    public final static String HOST = "dm-53.data.aliyun.com";

    public static AsyncApiClient build(BaseApiClientBuildParam buildParam){
        if(null == buildParam){
            throw new SdkException("buildParam must not be null");
        }

        buildParam.check();
        return  new AsyncApiClient(buildParam);
    }

    private AsyncApiClient(BaseApiClientBuildParam buildParam){
        super(buildParam);
    }



    public void startCardNotify(byte[] body , Callback callback) {
        String path = "/rest/160601/ocr/ocr_vehicle.json";
        
        httpPost(HttpConstant.CLOUDAPI_HTTP, HOST , path , null , null , body , null , callback , CallMethod.ASYNC);
    }
}