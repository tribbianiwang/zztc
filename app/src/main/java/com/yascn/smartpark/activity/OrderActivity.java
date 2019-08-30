package com.yascn.smartpark.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yascn.smartpark.R;
import com.yascn.smartpark.adapter.OrderVpAdapter;
import com.yascn.smartpark.base.BaseActivity;
import com.yascn.smartpark.base.BaseFragment;
import com.yascn.smartpark.bean.Ordering;
import com.yascn.smartpark.contract.OrderOngoingContract;
import com.yascn.smartpark.fragment.OrderFinishFragment;
import com.yascn.smartpark.fragment.OrderOngoingFragment;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.SPUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yascn.smartpark.utils.AppContants.REFLESHORDER;

public class OrderActivity extends BaseActivity{

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.index_toolbar)
    Toolbar indexToolbar;
    @BindView(R.id.activity_introduction)
    LinearLayout activityIntroduction;
    @BindView(R.id.tblayout_order)
    TabLayout tblayoutOrder;
    @BindView(R.id.vp_order)
    ViewPager vpOrder;



    //REFLESHORDER

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        initTitle(getString(R.string.string_my_order));

        initVp();



    }

    private void initVp() {
        ArrayList<BaseFragment> baseFragments = new ArrayList<>();
        baseFragments.add(new OrderOngoingFragment());
        baseFragments.add(new OrderFinishFragment());



//set adapter to your ViewPager
        vpOrder.setAdapter(new OrderVpAdapter(getSupportFragmentManager(),baseFragments));

        tblayoutOrder.setupWithViewPager(vpOrder);


    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
