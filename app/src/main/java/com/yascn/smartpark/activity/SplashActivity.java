package com.yascn.smartpark.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.gyf.barlibrary.ImmersionBar;

import com.yascn.smartpark.BuildConfig;
import com.yascn.smartpark.R;
import com.yascn.smartpark.base.BaseActivity;

import com.yascn.smartpark.utils.DensityUtils;
import com.yascn.smartpark.utils.ImageViewUtils;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.NotchScreenUtils;
import com.yascn.smartpark.utils.SPUtils;
import com.yascn.smartpark.utils.ViewUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yascn.smartpark.utils.AppContants.ISFIRSTLOADAPP;


public class SplashActivity extends BaseActivity {

    @BindView(R.id.iv_splash)
    ImageView ivSplash;

    private ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        closeAndroidPAlertDialot();

//        //隐藏标题栏
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        //隐藏状态栏
//        //定义全屏参数
//        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
//        //获得当前窗体对象
//        Window window = SplashActivity.this.getWindow();
//        //设置当前窗体为全屏显示
//        window.setFlags(flag, flag);

//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏

        setContentView(R.layout.activity_splash);




        ButterKnife.bind(this);
        mImmersionBar = ImmersionBar.with(this).statusBarDarkFont(true, 0.2f);
        mImmersionBar.init();   //所有子类都将继承这些相同的属性

        ImageViewUtils.showLocalImg(this,R.drawable.bg_splash,ivSplash);




        startAnim();
    }


    private void closeAndroidPAlertDialot() {
        if(Build.VERSION.SDK_INT >= 28){
            try {
                Class<?> aClass = Class.forName("android.content.pm.PackageParser$Package");
                Constructor<?> declaredConstructor = aClass.getDeclaredConstructor(String.class);
                declaredConstructor.setAccessible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Class<?> cls = Class.forName("android.app.ActivityThread");
                Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
                declaredMethod.setAccessible(true);
                Object activityThread = declaredMethod.invoke(null);
                Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
                mHiddenApiWarningShown.setAccessible(true);
                mHiddenApiWarningShown.setBoolean(activityThread, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            return;
        }



    }

    private void startAnim() {
        AlphaAnimation aa = new AlphaAnimation(0.4f, 1.0f);
        aa.setDuration(500);

        aa.setFillAfter(true);
        ivSplash.startAnimation(aa);

        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(500);
                            boolean isFirstLoadApp = (boolean) SPUtils.get(SplashActivity.this, ISFIRSTLOADAPP, true);
                            Intent intent;
                            if (isFirstLoadApp) {
                                intent = new Intent(SplashActivity.this, GuideActivity.class);
                            }else{
                                intent = new Intent(SplashActivity.this, MainActivity.class);
                            }

//                            else if ((boolean)SPUtils.get(SplashActivity.this,KEY_LOGIN,false)) {
//                                intent = new Intent(SplashActivity.this, MainActivity.class);
//
//                            } else {
//                                intent = new Intent(SplashActivity.this, LoginActivity.class);
//                            }


                            startActivity(intent);
//                            finish();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private static final String TAG = "SplashActivity";
    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.d(TAG, "onStart: ");
    }
}
