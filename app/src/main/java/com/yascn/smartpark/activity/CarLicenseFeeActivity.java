package com.yascn.smartpark.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yascn.smartpark.GreenDaoCarLicenseDao;
import com.yascn.smartpark.base.AndroidApplication;
import com.yascn.smartpark.R;
import com.yascn.smartpark.adapter.RvSearchCarlicenseHistoryAdapter;
import com.yascn.smartpark.base.BaseActivity;
import com.yascn.smartpark.bean.GreenDaoCarLicense;
import com.yascn.smartpark.bean.QueryCarLicenseFeeBean;
import com.yascn.smartpark.contract.CarLicenseFeeContract;
import com.yascn.smartpark.dialog.DefaultDialog;
import com.yascn.smartpark.utils.ClickUtil;
import com.yascn.smartpark.utils.LicenseKeyboardUtil;
import com.yascn.smartpark.utils.LoginStatusUtils;
import com.yascn.smartpark.utils.RvItemClickListener;
import com.yascn.smartpark.utils.StringUtils;
import com.yascn.smartpark.utils.T;
import com.yascn.smartpark.view.InputView;
import com.yascn.smartpark.viewmodel.CarlicenseFeeViewModel;

import org.greenrobot.greendao.query.Query;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yascn.smartpark.utils.AppContants.CARLICENSE;
import static com.yascn.smartpark.utils.AppContants.CARLICENSEBEAN;

public class CarLicenseFeeActivity extends BaseActivity implements CarLicenseFeeContract.View {

    public static final String INPUT_LICENSE_COMPLETE = "me.kevingo.licensekeyboard.input.comp";
    public static final String INPUT_LICENSE_KEY = "LICENSE";
    @BindView(R.id.tv_car_license_title)
    TextView tvCarLicenseTitle;
    @BindView(R.id.rv_search_history)
    RecyclerView rvSearchHistory;
    @BindView(R.id.ll_head)
    LinearLayout llHead;
    @BindView(R.id.bt_query_fee)
    Button btQueryFee;
    @BindView(R.id.thirdLayout)
    RelativeLayout thirdLayout;
    @BindView(R.id.iv_delete)
    ImageView ivDelete;
    private DefaultDialog dialog;

    @BindView(R.id.input_view)
    InputView inputView;
    @BindView(R.id.acb_newenergy)
    AppCompatCheckBox acb_newenergy;
    @BindView(R.id.keyboard_view)
    KeyboardView keyboardView;
    @BindView(R.id.tv_history)
    TextView tvHistory;

    private GreenDaoCarLicenseDao greenDaoCarLicenseDao;
    private List<GreenDaoCarLicense> greenDaoCarLicenses;
    private RvSearchCarlicenseHistoryAdapter rvSearchCarlicenseHistoryAdapter;

    private CarlicenseFeeViewModel carlicenseFeeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_license_fee);

        ButterKnife.bind(this);

        //1
        carlicenseFeeViewModel = ViewModelProviders.of(this).get(CarlicenseFeeViewModel.class);

        //2
        getLifecycle().addObserver(carlicenseFeeViewModel);

        //3
        Observer<QueryCarLicenseFeeBean> queryCarLicenseFeeBeanObserver = new Observer<QueryCarLicenseFeeBean>() {
            @Override
            public void onChanged(@Nullable QueryCarLicenseFeeBean queryCarLicenseFeeBean) {

                setLicenseFee(queryCarLicenseFeeBean);
            }
        };

        //4
        carlicenseFeeViewModel.getMutableLiveDataEntry().observe(this,queryCarLicenseFeeBeanObserver);
        carlicenseFeeViewModel.getQueryStatus().observe(this,statusObserver);



        greenDaoCarLicenseDao = AndroidApplication.getInstances().getDaoSession().getGreenDaoCarLicenseDao();


        initTitle(getResources().getString(R.string.string_car_license_fee));
//        Toast.makeText(this, inputView.getText().toString(), Toast.LENGTH_SHORT).show();

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

        rvSearchHistory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        setHistoryView();

        ;

    }

    private void setHistoryView() {
//        if(greenDaoCarLicenses!=null){
//            greenDaoCarLicenses.clear();
//        }
        greenDaoCarLicenses = greenDaoCarLicenseDao.loadAll();
        if (greenDaoCarLicenses.size() == 0) {
            rvSearchHistory.setVisibility(View.GONE);
            tvHistory.setVisibility(View.GONE);
            ivDelete.setVisibility(View.GONE);
        } else {
            rvSearchHistory.setVisibility(View.VISIBLE);
            tvHistory.setVisibility(View.VISIBLE);
            ivDelete.setVisibility(View.VISIBLE);
            Collections.reverse(greenDaoCarLicenses);
//            if(rvSearchCarlicenseHistoryAdapter==null){
            rvSearchCarlicenseHistoryAdapter = new RvSearchCarlicenseHistoryAdapter(this, greenDaoCarLicenses);

            rvSearchHistory.setAdapter(rvSearchCarlicenseHistoryAdapter);
//            }else{
//                rvSearchCarlicenseHistoryAdapter.notifyDataSetChanged();
//            }

            rvSearchCarlicenseHistoryAdapter.setOnItemClickListener(new RvItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                    carLicenseString = greenDaoCarLicenses.get(position).getCarLicense();
                    if(ClickUtil.isFastClick()){
                        T.showShort(view.getContext(),StringUtils.getRString(view.getContext(),R.string.string_click_to_quick));
                        return;
                    }
                    if(carlicenseFeeViewModel!=null){
                        carlicenseFeeViewModel.getLicenseFee(carLicenseString,LoginStatusUtils.getUserId(CarLicenseFeeActivity.this));
                    }


                }
            });
        }
    }





    @OnClick({R.id.bt_query_fee, R.id.iv_delete})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.bt_query_fee:


                if (TextUtils.isEmpty(getAddCar())) {
                    T.showShort(this, getResources().getString(R.string.carnumber_not_complete));
                } else {

                    if (inputView.getText().length() == inputView.getLawLicenseNums()) {

                        Query<GreenDaoCarLicense> query = greenDaoCarLicenseDao.queryBuilder().where(
                                GreenDaoCarLicenseDao.Properties.CarLicense.eq(inputView.getText().toString().trim()))
                                .build();


                        if (query.list().size()!= 0){

                            greenDaoCarLicenseDao.deleteByKey(query.list().get(0).getId());
                        }


                            GreenDaoCarLicense greenDaoCarLicense = new GreenDaoCarLicense();
                            greenDaoCarLicense.setCarLicense(inputView.getText().toString().trim());
                            greenDaoCarLicenseDao.insert(greenDaoCarLicense);



                        carLicenseString = inputView.getText().toString().trim();
                        if(ClickUtil.isFastClick()){
                            T.showShort(view.getContext(),StringUtils.getRString(view.getContext(),R.string.string_click_to_quick));
                            return;
                        }

                        if(carlicenseFeeViewModel!=null){
                            carlicenseFeeViewModel.getLicenseFee(inputView.getText().toString().trim(),LoginStatusUtils.getUserId(CarLicenseFeeActivity.this));
                        }

                        setHistoryView();
                    } else {
                        T.showShort(this, getString(R.string.string_license_notfull));
                    }


                }

                break;

            case R.id.iv_delete:
                if (greenDaoCarLicenseDao.loadAll().size() != 0) {
                    greenDaoCarLicenseDao.deleteAll();
                    greenDaoCarLicenses.clear();
                    rvSearchCarlicenseHistoryAdapter.notifyDataSetChanged();
                    ivDelete.setVisibility(View.GONE);
                    tvHistory.setVisibility(View.GONE);

                }


                break;


        }

    }

    public String getAddCar() {
        return inputView.getText().toString().trim();
    }

    private String carLicenseString;

    @Override
    public void serverError(String errorMsg) {
        T.showShort(this, errorMsg);
    }

    @Override
    public void setLicenseFee(QueryCarLicenseFeeBean carLicense) {
        if (TextUtils.isEmpty(carLicense.getObject().getPark_name())) {
            T.showShort(this, StringUtils.getRString(this, R.string.string_no_payinfo));
//            finish();
        } else {
            Intent intent = new Intent(CarLicenseFeeActivity.this, PayParkFeeActivity.class);
            intent.putExtra(CARLICENSEBEAN,carLicense);
            intent.putExtra(CARLICENSE,carLicenseString);
            startActivity(intent);
        }
    }



    @Override
    public void showProgress() {
        showProgressDialog(getString(R.string.loadingProgress));
    }

    @Override
    public void hideProgress() {
        hidProgressDialog();
    }

    @Override
    public Context getContext() {
        return this;
    }


}
