package com.yascn.smartpark.retrofit;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by YASCN on 2017/1/5.
 */

public interface  FileUploadService {
    @Multipart
    @POST("EditU")
    Call<ResponseBody> upload(@Part("description") RequestBody description,
                              @Part("IMG") MultipartBody.Part file);
}
