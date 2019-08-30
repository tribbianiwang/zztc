
package com.yascn.smartpark.activity;

        import android.content.Intent;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.support.design.widget.TabLayout;
        import android.support.v4.app.Fragment;
        import android.support.v4.view.ViewPager;
        import android.support.v7.widget.Toolbar;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.TextView;

        import com.flyco.tablayout.listener.OnTabSelectListener;
        import com.yascn.smartpark.R;
        import com.yascn.smartpark.activity.NewWalletActivity;
        import com.yascn.smartpark.adapter.WalletDetailAdapter;
        import com.yascn.smartpark.adapter.WalletVpAdapter;
        import com.yascn.smartpark.base.BaseActivity;
        import com.yascn.smartpark.base.BaseFragment;
        import com.yascn.smartpark.fragment.ConsumptionFragment;
        import com.yascn.smartpark.fragment.RechargeFragment;
        import com.yascn.smartpark.fragment.WalletBalanceFragment;
        import com.yascn.smartpark.fragment.WalletCouponingFragment;

        import java.util.ArrayList;
        import java.util.List;

        import butterknife.BindView;
        import butterknife.ButterKnife;

/**
 * Created by YASCN on 2017/8/30.
 */

public class WalletDetailActivity extends BaseActivity {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.index_toolbar)
    Toolbar indexToolbar;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;


    private WalletDetailAdapter walletDetailAdapter;
    private List<Fragment> fragments;
    private String[] mTitles = {"消费", "充值"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_detail);
        ButterKnife.bind(this);

        initTitle(getString(R.string.string_pay_detail));

        initVp();
//        initTabLayout();

    }

    private void initVp() {
        fragments = new ArrayList<Fragment>();
        fragments.add(new ConsumptionFragment());
        fragments.add(new RechargeFragment());

        walletDetailAdapter = new WalletDetailAdapter(getSupportFragmentManager(), fragments);;

//set adapter to your ViewPager
        viewPager.setAdapter(walletDetailAdapter);

        tabLayout.setupWithViewPager(viewPager);

    }

}
