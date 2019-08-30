package com.yascn.smartpark.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.yascn.smartpark.R;
import com.yascn.smartpark.adapter.RvPayRulesAdapter;
import com.yascn.smartpark.base.BaseActivity;
import com.yascn.smartpark.bean.ParkDetailBean;
import com.yascn.smartpark.contract.ParkDetailContract;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.FullyLinearLayoutManager;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.StringUtils;
import com.yascn.smartpark.utils.T;
import com.yascn.smartpark.viewmodel.ParkDetailViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yascn.smartpark.utils.AppContants.BROADCASTAPPOINTNOW;
import static com.yascn.smartpark.utils.AppContants.BROADCASTAPPOINTNOWID;
import static com.yascn.smartpark.utils.AppContants.PARKFREENUM;
import static com.yascn.smartpark.utils.AppContants.SELECTPARKDETAILBEAN;

public class ParkDetailActivity extends BaseActivity implements ParkDetailContract.View {

    @BindView(R.id.index_toolbar)
    Toolbar indexToolbar;
    @BindView(R.id.tv_park_name)
    TextView tvParkName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.rb)
    RatingBar rb;

    @BindView(R.id.rl_time)
    RelativeLayout rlTime;
    @BindView(R.id.fl_freenum)
    FrameLayout flFreenum;
    @BindView(R.id.bt_appoint_now)
    Button btAppointNow;
    @BindView(R.id.bt_comment)
    Button btComment;
    @BindView(R.id.activity_park_detail)
    RelativeLayout rl_root;
    @BindView(R.id.iv_icon_time)
    ImageView ivIconTime;
    @BindView(R.id.tv_free_num)
    TextView tvFreeNum;
    @BindView(R.id.rv_pay_rules)
    RecyclerView rvPayRules;
    @BindView(R.id.ll_freenum_root)
    LinearLayout llFreenumRoot;
    private ParkDetailBean.ObjectBean parkObject;
    private String parkId;
    private ParkDetailViewModel parkDetailViewModel;

    //    private YascnParkListBean.ObjectBean yascnParkBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_detail);
        parkId = getIntent().getStringExtra(SELECTPARKDETAILBEAN);
//        parkId = "37d2497fde1b43f5a706a18008fe2502";
        ButterKnife.bind(this);
        initToolbar();
        //1
        parkDetailViewModel = ViewModelProviders.of(this).get(ParkDetailViewModel.class);

        //2
        getLifecycle().addObserver(parkDetailViewModel);


        //3
        Observer<ParkDetailBean> parkDetailBeanObserver = new Observer<ParkDetailBean>() {
            @Override
            public void onChanged(@Nullable ParkDetailBean parkDetailBean) {
                    setParkDetail(parkDetailBean);

            }
        };

        //4
        parkDetailViewModel.getMutableLiveDataEntry().observe(this,parkDetailBeanObserver);
        parkDetailViewModel.getQueryStatus().observe(this,statusObserver);


        if(parkDetailViewModel !=null){
            parkDetailViewModel.getParkDetail(parkId);
        }


    }

    private static final String TAG = "ParkDetailActivity";
    private void initView() {


            if (StringUtils.emptyParseInt(parkObject.getFREE_NUM()) < AppContants.PARKFREENUMREDLINE) {
                LogUtil.d(TAG, "initView: red");
                llFreenumRoot.setBackgroundResource(R.drawable.rounded_red_bg);
                btAppointNow.setBackgroundResource(R.drawable.rounded_red_bg);
                btComment.setBackgroundResource(R.drawable.rounded_red_bg);
            } else {
                LogUtil.d(TAG, "initView: blue");
                llFreenumRoot.setBackgroundResource(R.drawable.rounded_blue_bg);
                btAppointNow.setBackgroundResource(R.drawable.rounded_blue_bg);
                btComment.setBackgroundResource(R.drawable.rounded_blue_bg);
            }

        tvParkName.setText(parkObject.getNAME());
        tvAddress.setText(parkObject.getADDRESS());
        StringUtils.setParkFreeNum(tvFreeNum,parkObject.getFREE_NUM());
        initRecyclerView(parkObject);

    }

    private void initRecyclerView(ParkDetailBean.ObjectBean object) {


        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(this);
        rvPayRules.setNestedScrollingEnabled(false);
        //设置布局管理器
        rvPayRules.setLayoutManager(linearLayoutManager);
        rvPayRules.setAdapter(new RvPayRulesAdapter(this, object.getCostDetail()));
    }


    private void initToolbar() {

        indexToolbar.setNavigationIcon(R.drawable.icon_back);

        indexToolbar.setTitleTextColor(Color.WHITE);
        indexToolbar.setTitle("");
        indexToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                overridePendingTransition(R.anim.bottom_in,R.anim.top_out);
            }
        });


    }

    @OnClick({R.id.bt_appoint_now, R.id.bt_comment})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.bt_appoint_now:
                intent = new Intent();
                intent.setAction(BROADCASTAPPOINTNOW);
                intent.putExtra(BROADCASTAPPOINTNOWID,parkObject.getPARKING_ID());
                sendBroadcast(intent);
                finish();

                break;
            case R.id.bt_comment:
                //评论界面
                int freenum = 0;
                intent = new Intent(ParkDetailActivity.this, CommentActivity.class);
                intent.putExtra(AppContants.PARKID, parkId);
                if(parkObject!=null) {


                        freenum = StringUtils.emptyParseInt(parkObject.getFREE_NUM());
                        intent.putExtra(PARKFREENUM, freenum);

                        startActivity(intent);
                        overridePendingTransition(R.anim.bottom_in, R.anim.top_out);


                }

                break;
        }
    }




    @Override
    public void serverError(String errorMsg) {
        T.showShort(this, errorMsg);

    }

    @Override
    public void setParkDetail(ParkDetailBean parkDetail) {
        parkObject = parkDetail.getObject();


        initView();
    }

    @Override
    public void showProgress() {
        showProgressDialog(StringUtils.getRString(this, R.string.loadingProgress));

    }

    @Override
    public void hideProgress() {
        hidProgressDialog();

    }

    @Override
    public Context getContext() {
        return this;
    }

    private boolean isNetFailed = false;

    @Override
    public void netWordIsFailed() {
        super.netWordIsFailed();
        isNetFailed = true;
    }

    @Override
    public void netWorkIsSuccess() {
        super.netWorkIsSuccess();

        if ( parkDetailViewModel!= null && isNetFailed) {
            parkDetailViewModel.getParkDetail(parkId);
        }

    }

}
