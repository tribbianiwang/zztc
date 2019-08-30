package com.yascn.smartpark.utils;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by YASCN on 2016/11/24.
 */
public interface ImageDownLoadCallBack {
    void onDownLoadSuccess(File file);
    void onDownLoadSuccess(Bitmap bitmap);

    void onDownLoadFailed();
}