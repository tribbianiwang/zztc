package com.yascn.smartpark.base;


import android.app.Activity;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.yascn.smartpark.R;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.KeyBoardUtils;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.T;

import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSFIILED;
import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSLOADING;
import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSSUCCESS;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {
    public Activity mActivity;
    private Dialog progressDialog;
    private ConnectivityManager connectivityManager;
    private NetworkInfo info;

    public Observer<String> statusObserver;

    private static final String TAG = "BaseFragment";

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                LogUtil.d("mark", "网络状态已经改变");
                connectivityManager = (ConnectivityManager)
                        context.getSystemService(Context.CONNECTIVITY_SERVICE);
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
    private BaseActivity baseActivity;

    public void netWordIsFailed() {

    }

    public void netWorkIsSuccess() {
    }

    public BaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        baseActivity = (BaseActivity) getActivity();

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
                }

            }
        };


        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        getActivity().registerReceiver(mReceiver, mFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mReceiver);
    }

    public void showProgress(){
        Log.d(TAG, "showProgress: ");
        baseActivity.showProgressDialog(getString(R.string.loadingProgress));

    }
    public void hideProgress(){
        Log.d(TAG, "hideProgress: ");
        baseActivity.hidProgressDialog();

    }


    public void serverError(String errorMsg) {
        T.showShort(getActivity(), errorMsg);
    }


}
