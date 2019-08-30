package com.yascn.smartpark.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.yascn.smartpark.R;
import com.yascn.smartpark.base.BaseActivity;
import com.yascn.smartpark.bean.BandNumber;
import com.yascn.smartpark.dialog.DefaultDialog;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LicenseKeyboardUtil;
import com.yascn.smartpark.utils.SPUtils;
import com.yascn.smartpark.utils.T;
import com.yascn.smartpark.view.InputView;
import com.yascn.smartpark.viewmodel.AddCarViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DiyParkLicenseActivity extends BaseActivity  {

    public static final String INPUT_LICENSE_COMPLETE = "me.kevingo.licensekeyboard.input.comp";
    public static final String INPUT_LICENSE_KEY = "LICENSE";
    private DefaultDialog dialog;

    @BindView(R.id.input_view)
    InputView inputView;
    @BindView(R.id.acb_newenergy)
    AppCompatCheckBox acb_newenergy;
    @BindView(R.id.keyboard_view)
    KeyboardView keyboardView;
    @BindView(R.id.thirdLayout)
    LinearLayout thirdLayout;
    @BindView(R.id.addCar)
    Button addCar;

    private AddCarViewModel addCarViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diy_park_license);
        ButterKnife.bind(this);
        initTitle(getResources().getString(R.string.add_car_number));
//        Toast.makeText(this, inputView.getText().toString(), Toast.LENGTH_SHORT).show();




        //1.获取viewmodel
        addCarViewModel = ViewModelProviders.of(this).get(AddCarViewModel.class);

        //2.给声明周期添加观察者
        getLifecycle().addObserver(addCarViewModel);

        //3.创建bean的观察者
        Observer<BandNumber> bindNumberObserver = new Observer<BandNumber>() {
            @Override
            public void onChanged(@Nullable BandNumber bandNumber) {
                backActivity();
                T.showShort(DiyParkLicenseActivity.this,getString(R.string.bind_car_success));
            }
        };

        //4.viewmodel绑定bean观察者
        addCarViewModel.getMutableLiveDataEntry().observe(this, bindNumberObserver);
        addCarViewModel.getQueryStatus().observe(this, statusObserver);
        
        
        
        
        
        
        
        final LicenseKeyboardUtil keyboardUtil = new LicenseKeyboardUtil(this, inputView);
        keyboardUtil.showKeyboard();

        acb_newenergy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                Toast.makeText(DiyParkLicenseActivity.this, "是否选择" + isChecked, Toast.LENGTH_SHORT).show();
                inputView.setIsNewEnergy(isChecked);
                keyboardUtil.setIsNewEnergy(isChecked);
            }
        });
    }




    @OnClick(R.id.addCar)
    public void onViewClicked() {

        if (TextUtils.isEmpty(getAddCar())) {
            T.showShort(this, getResources().getString(R.string.carnumber_not_complete));
        } else {

            if(inputView.getText().length()==inputView.getLawLicenseNums()){
                String userID = (String) SPUtils.get(this, AppContants.KEY_USERID, "");
                if(addCarViewModel!=null){
                    addCarViewModel.addCar(userID,getAddCar()); 
                }
              
               
            }else{
                T.showShort(this,getString(R.string.string_license_notfull));
            }


        }
    }







    public String getAddCar() {
        return inputView.getText().toString().trim();
    }


    public void backActivity() {

        sendRefleshBroadcast();

        finish();
    }





    public void sendRefleshBroadcast() {
        Intent intent = new Intent();
        intent.setAction(AppContants.REFRESHCARNUMBER);
        sendBroadcast(intent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
