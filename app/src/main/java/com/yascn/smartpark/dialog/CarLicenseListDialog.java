package com.yascn.smartpark.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.yascn.smartpark.R;
import com.yascn.smartpark.activity.DiyParkLicenseActivity;
import com.yascn.smartpark.adapter.RvCarLicenseListAdapter;
import com.yascn.smartpark.bean.CarLicense;
import com.yascn.smartpark.contract.CarLicenseContract;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.RvItemClickListener;
import com.yascn.smartpark.utils.SPUtils;
import com.yascn.smartpark.utils.T;
import com.yascn.smartpark.viewmodel.CarLicenseViewModel;

import static com.yascn.smartpark.utils.AppContants.CARLICENSENUMSLIMIT;
import static com.yascn.smartpark.utils.AppContants.PARKIDTRANS;
import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSFIILED;
import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSLOADING;
import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSSUCCESS;
import static com.yascn.smartpark.utils.AppContants.REFRESHCARNUMBER;

/**
 * Created by YASCN on 2017/9/8.
 */

public class CarLicenseListDialog extends DialogFragment implements CarLicenseContract.View {

    private CarLicenseViewModel carLicenseViewModel;
    private String userId;
    private RecyclerView rvCarList;
    private LinearLayout llLoading;

    public BroadcastReceiver mainReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(REFRESHCARNUMBER)) {
                if(carLicenseViewModel!=null){
                    carLicenseViewModel.getCarLicense(userId);
                }


            }
        }
    };

    private CarLicenseListDialog.setCarLicenseChooseListener setCarLicenseChooseListener;
    private String parkIdTrans;

    public interface setCarLicenseChooseListener {
        void setChooseCarLicense(String carLicense);
    }

    public void setOnCarLicenseChooseListener(CarLicenseListDialog.setCarLicenseChooseListener setCarLicenseChooseListener) {
        this.setCarLicenseChooseListener = setCarLicenseChooseListener;
    }



    public Observer<String> statusObserver;
    private Activity activity;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setTitle("选择生日");



        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_car_list, null, false);
        rvCarList = view.findViewById(R.id.rv_carlist);
        llLoading = view.findViewById(R.id.ll_loading);



        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        parkIdTrans = (String) getArguments().getSerializable(PARKIDTRANS);
        activity = getActivity();
        rvCarList.setLayoutManager(new LinearLayoutManager(activity));
        userId = (String) SPUtils.get(activity, AppContants.KEY_USERID, "");

//        /设置状态
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


        //1
        carLicenseViewModel = ViewModelProviders.of(this).get(CarLicenseViewModel.class);

        //2
        getLifecycle().addObserver(carLicenseViewModel);

        //3
        Observer<CarLicense> carLicenseObserver = new Observer<CarLicense>() {
            @Override
            public void onChanged(@Nullable CarLicense carLicense) {
                setCarLicense(carLicense);
            }
        };

        //4

        carLicenseViewModel.getMutableLiveDataEntry().observe(this,carLicenseObserver);
        carLicenseViewModel.getQueryStatus().observe(this,statusObserver);

        if(carLicenseViewModel !=null){
            carLicenseViewModel.getCarLicense(userId);
        }

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(REFRESHCARNUMBER);

        activity.registerReceiver(mainReceiver, intentFilter);

    }

    @Override
    public void setCarLicense(final CarLicense carLicense) {
        RvCarLicenseListAdapter rvCarLicenseListAdapter = new RvCarLicenseListAdapter(activity,carLicense.getObject(),parkIdTrans);
        rvCarList.setAdapter(rvCarLicenseListAdapter);
        rvCarLicenseListAdapter.setOnItemClickListener(new RvItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(carLicense.getObject().size()==CARLICENSENUMSLIMIT){
                    setCarLicenseChooseListener.setChooseCarLicense(carLicense.getObject().get(position).getNUMBER());
                    CarLicenseListDialog.this.dismiss();
                }else{
                    if(position==carLicense.getObject().size()){
                        Intent intent = new Intent(activity, DiyParkLicenseActivity.class);
                        startActivity(intent);
                    }else{
                        setCarLicenseChooseListener.setChooseCarLicense(carLicense.getObject().get(position).getNUMBER());
                        CarLicenseListDialog.this.dismiss();
                    }
                }
            }
        });

    }

    @Override
    public void serverError(String errorMsg) {
        T.showShort(activity,errorMsg);

    }

    @Override
    public void showProgress() {
        llLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        llLoading.setVisibility(View.GONE);
    }

    private static final String TAG = "CarLicenseListDialog";
    private Dialog progressDialog;
    public void showProgressDialog(String loadingText){
        LogUtil.d(TAG, "showProgressDialog: ");
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        View view = View.inflate(getActivity(), R.layout.layout_progress_dialog,null);
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
    public void onDestroyView() {
        super.onDestroyView();
        hidProgressDialog();
        activity.unregisterReceiver(mainReceiver);
    }
}
