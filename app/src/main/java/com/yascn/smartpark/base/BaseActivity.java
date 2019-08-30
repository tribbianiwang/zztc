package com.yascn.smartpark.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yascn.smartpark.R;
import com.yascn.smartpark.utils.ActivityUtil;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.DensityUtils;
import com.yascn.smartpark.utils.KeyBoardUtils;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.NotchScreenUtils;
import com.yascn.smartpark.utils.T;
import com.yascn.smartpark.utils.ViewUtils;

import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSFIILED;
import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSLOADING;
import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSSUCCESS;


public abstract  class BaseActivity extends AppCompatActivity {
    public Activity mActivity;
    private Toolbar toolbar;
    private TextView title;
    private ConnectivityManager connectivityManager;
    private NetworkInfo info;
    public SmartRefreshLayout baseRefreshLayout;
    private ImmersionBar mImmersionBar;
    public View contentView;
    public View errorView;
    public View nodataView;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                LogUtil.d("mark", "网络状态已经改变");
                connectivityManager = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                info = connectivityManager.getActiveNetworkInfo();
                if(info != null && info.isAvailable()) {
                    String name = info.getTypeName();
                    LogUtil.d("mark", "当前网络名称：" + name);
                    netWorkIsSuccess();

                } else {
                    LogUtil.d("mark", "没有可用网络");
                    netWordIsFailed();
                }
            }
        }
    };
    public Observer<String> statusObserver;


    public void showTypeView(String viewType){
        if(contentView!=null){
            contentView.setVisibility(AppContants.VIEWTYPECONTENT.equals(viewType)?View.VISIBLE:View.GONE);
        }
        if(errorView!=null){
            errorView.setVisibility(AppContants.VIEWTYPEERROR.equals(viewType)?View.VISIBLE:View.GONE);
        }
        if(nodataView!=null){
            nodataView.setVisibility(AppContants.VIEWTYPENODATA.equals(viewType)?View.VISIBLE:View.GONE);
        }



    }

    private boolean isNetFialed = false;

    public void netWorkIsSuccess(){
        if (isNetFialed) {
            refreshData();
            isNetFialed = false;
        }



    }


    public void setToolbarMargin(View marginView) {
        mImmersionBar = ImmersionBar.with(this).statusBarDarkFont(true, 0.2f);
        mImmersionBar.init();   //所有子类都将继承这些相同的属性
        ViewUtils.setMargins(marginView,0, (int) DensityUtils.dp2px(this,NotchScreenUtils.getIntchHeight(this)),0,0);
    }

    public void initChildView(SmartRefreshLayout refreshLayout,View contentView,View errorView,View emptyView){
        this.baseRefreshLayout = refreshLayout;
        this.contentView = contentView;
        this.errorView = errorView;
        this.nodataView = emptyView;

        if(errorView!=null){
            errorView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    refreshData();
                }
            });
        }

        if(nodataView!=null){
            nodataView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    refreshData();
                }
            });

        }

    }

    public void netWordIsFailed(){
        isNetFialed = true;
        T.getSingleToast(this,"网络出错了");
//        T.showShort(this,"网络出错了");

    }

    public void refreshData(){

    }



    public void initTitle(String t) {


        toolbar = (Toolbar) findViewById(R.id.index_toolbar);
        title = (TextView) findViewById(R.id.tv_title);

        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.icon_back);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        title.setText(t);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtil.addActivity(this);
        final IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mReceiver, mFilter);

        mActivity = this;




        //设置状态
        statusObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.d(TAG, "onChanged: status"+s);

                //设置状态

                switch(s){
                    case QUERYSTATUSLOADING:
                        Log.d(TAG, "status: 加载中");
                        showProgress();

                        break;
                    case QUERYSTATUSFIILED:
                        Log.d(TAG, "status: 加载失败");
                        hideProgress();
                        serverError(AppContants.SERVERERROR);


                        break;

                    case QUERYSTATUSSUCCESS:
                        Log.d(TAG,"status:加载成功--");
                        hideProgress();


                        break;

                    default:
                        T.showShort(BaseActivity.this,s);

                        break;




                }

            }
        };

    }



    public void skipActivity(Class targetActivity){
        Intent intent = new Intent (this,targetActivity);
        startActivity(intent);
    }

    /**
     * 重写 activity切换方法 消除系统自带动画
     * @param intent
     */
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);

        overridePendingTransition(R.anim.anim_right_in,R.anim.anim_left_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_left_in,R.anim.anim_right_out);
    }



    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        overridePendingTransition(R.anim.anim_right_in,R.anim.anim_left_out);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.anim_right_in,R.anim.anim_left_out);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
            default:
                //对没有处理的事件，交给父类来处理
                return super.onOptionsItemSelected(item);

        }

        return true;
    }
    private Dialog progressDialog;
    private static final String TAG = "BaseActivity";
    public void showProgressDialog(String loadingText){
        LogUtil.d(TAG, "showProgressDialog: ");
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.layout_progress_dialog,null);
        TextView tvLoading = (TextView) view.findViewById(R.id.tv_loading);
        tvLoading.setText(loadingText);
        builder.setView(view);
        if(progressDialog==null){
            progressDialog = builder.show();
        }else{
            progressDialog.show();
        }
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.setCancelable(false);



        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }



    public void hidProgressDialog(){
        LogUtil.d(TAG, "hidProgressDialog: ");
        if(progressDialog!=null){
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }

        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        hidProgressDialog();
    }
    @Override
    protected void onDestroy() {

        ActivityUtil.removeActivity(this);
        unregisterReceiver(mReceiver);

        if(progressDialog != null) {
            progressDialog.dismiss();
        }

        if (mImmersionBar != null){
            mImmersionBar.destroy();
        }

        super.onDestroy();

    }


    //region软键盘的处理

    /**
     * 清除editText的焦点
     *
     * @param v   焦点所在View
     * @param ids 输入框
     */
    public void clearViewFocus(View v, int... ids) {
        if (null != v && null != ids && ids.length > 0) {
            for (int id : ids) {
                if (v.getId() == id) {
                    v.clearFocus();
                    break;
                }
            }
        }


    }

    /**
     * 隐藏键盘
     *
     * @param v   焦点所在View
     * @param ids 输入框
     * @return true代表焦点在edit上
     */
    public boolean isFocusEditText(View v, int... ids) {
        if (v instanceof EditText) {
            EditText tmp_et = (EditText) v;
            for (int id : ids) {
                if (tmp_et.getId() == id) {
                    return true;
                }
            }
        }
        return false;
    }

    //是否触摸在指定view上面,对某个控件过滤
    public boolean isTouchView(View[] views, MotionEvent ev) {
        if (views == null || views.length == 0) return false;
        int[] location = new int[2];
        for (View view : views) {
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            if (ev.getX() > x && ev.getX() < (x + view.getWidth())
                    && ev.getY() > y && ev.getY() < (y + view.getHeight())) {
                return true;
            }
        }
        return false;
    }

    //是否触摸在指定view上面,对某个控件过滤
    public boolean isTouchView(int[] ids, MotionEvent ev) {
        int[] location = new int[2];
        for (int id : ids) {
            View view = findViewById(id);
            if (view == null) continue;
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            if (ev.getX() > x && ev.getX() < (x + view.getWidth())
                    && ev.getY() > y && ev.getY() < (y + view.getHeight())) {
                return true;
            }
        }
        return false;
    }
    //endregion

    //region 右滑返回上级


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (isTouchView(filterViewByIds(), ev)) return super.dispatchTouchEvent(ev);
            if (hideSoftByEditViewIds() == null || hideSoftByEditViewIds().length == 0)
                return super.dispatchTouchEvent(ev);
            View v = getCurrentFocus();
            if (isFocusEditText(v, hideSoftByEditViewIds())) {
                if (isTouchView(hideSoftByEditViewIds(), ev))
                    return super.dispatchTouchEvent(ev);
                //隐藏键盘
                KeyBoardUtils.hideInputForce(this);
                clearViewFocus(v, hideSoftByEditViewIds());

            }
        }
        return super.dispatchTouchEvent(ev);

    }

    /**
     * 传入EditText的Id
     * 没有传入的EditText不做处理
     *
     * @return id 数组
     */
    public int[] hideSoftByEditViewIds() {
        return null;
    }

    /**
     * 传入要过滤的View
     * 过滤之后点击将不会有隐藏软键盘的操作
     *
     * @return id 数组
     */
    public View[] filterViewByIds() {
        return null;
    }



    public boolean isPullRefresh = false;

    public void showProgress() {
        LogUtil.d(TAG, "showProgress: ");
        if(baseRefreshLayout!=null&&isPullRefresh){
            return;
        }

        showProgressDialog(getString(R.string.loadingProgress));
    }


    public void hideProgress() {
        LogUtil.d(TAG, "hideProgress: "+baseRefreshLayout);
        if(baseRefreshLayout!=null&&isPullRefresh){
            Log.d(TAG, "hideProgress: -pullsh");
            baseRefreshLayout.finishRefresh();
            return;
        }

        hidProgressDialog();
    }


    public void serverError(String errorMsg) {
        showTypeView(AppContants.VIEWTYPEERROR);
        T.showShort(this, errorMsg);
    }
}
