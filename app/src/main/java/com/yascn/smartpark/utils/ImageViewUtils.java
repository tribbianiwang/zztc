package com.yascn.smartpark.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import com.yascn.smartpark.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/**
 * Created by YASCN on 2016/11/25.
 */

public  class ImageViewUtils {

    public static void showImage(Context context,String imageUrl,ImageView imageView){

        final RequestOptions options = new RequestOptions();
        options.centerCrop().diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(context)
                .load(imageUrl)
                .apply(options)
                .into(imageView);






    }



    public static void showImage(Context context,String imageUrl,ImageView imageView,int errorImg){


        final RequestOptions options = new RequestOptions();
        options.centerCrop().diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(errorImg);
        Glide.with(context)
                .load(imageUrl)
                .apply(options)
                .into(imageView);



    }


    public static void showImage(Context context,String imageUrl,ImageView imageView,int loadingImg,int errorImg){

        final RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(loadingImg)
                .error(errorImg);
        Glide.with(context)
                .load(imageUrl)
                .apply(options)
                .into(imageView);
    }


    public static void showParkImage(Context context,String imageUrl,ImageView imageView,int loadingImg,int errorImg){

        final RequestOptions options = new RequestOptions();
        options.centerCrop().diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(loadingImg)
                .error(errorImg);
        Glide.with(context)
                .load(imageUrl)
                .apply(options)
                .into(imageView);
    }

    public static void showLocalImg(Context context,int ImgId,ImageView imageView,int loadingImg){
        final RequestOptions options = new RequestOptions();
        options.centerCrop().diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(loadingImg);
        Glide.with(context)
                .load(ImgId)
                .apply(options)
                .into(imageView);
    }

    public static void showLocalImg(Context context,int ImgId,ImageView imageView){
        final RequestOptions options = new RequestOptions();
        options.centerCrop().diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(ImgId);
        Glide.with(context)
                .load(ImgId)
                .apply(options)
                .into(imageView);
    }

    public static void showLoadingGif(Context context,ImageView ivSvFooter){
        Glide.with(context).load(R.drawable.sv_loading_header).into(ivSvFooter);
    }



    public static void showLocal(Context context,String imageUrl,ImageView imageView){

        final RequestOptions options = new RequestOptions();
        options.centerCrop().diskCacheStrategy(DiskCacheStrategy.NONE)
                ;
        Glide.with(context)
                .load(imageUrl)
                .apply(options)
                .into(imageView);
    }

    public static Bitmap getDrawableBitmap(Context context,int drawable){
        return BitmapFactory.decodeResource(context.getResources(),drawable );
    }

    private static final String TAG = "ImageViewUtils";
    public static Bitmap setImageFromAssetsFile(Context context, String fileName,ImageView iv) {
        Bitmap image = null;
        AssetManager am = context.getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
            iv.setImageBitmap(image);
        } catch (IOException e) {
            LogUtil.d(TAG, "setImageFromAssetsFile: "+e.getMessage());
            e.printStackTrace();
        }
        return image;
    }


    public static int getDefaultHead(){


        Random random = new Random(3);
        if(random.nextInt()==0){
            return R.drawable.icon_default_one;
        }else if(random.nextInt()==1){
            return R.drawable.icon_default_two;
        }else {
            return R.drawable.icon_default_three;
        }
    }
}
