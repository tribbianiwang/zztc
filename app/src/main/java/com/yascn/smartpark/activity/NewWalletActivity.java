package com.yascn.smartpark.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yascn.smartpark.R;
import com.yascn.smartpark.adapter.WalletVpAdapter;
import com.yascn.smartpark.base.BaseActivity;
import com.yascn.smartpark.base.BaseFragment;
import com.yascn.smartpark.fragment.WalletBalanceFragment;
import com.yascn.smartpark.fragment.WalletCouponingFragment;

import com.yascn.smartpark.utils.KeyBoardUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewWalletActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.index_toolbar)
    Toolbar indexToolbar;
    @BindView(R.id.activity_introduction)
    LinearLayout activityIntroduction;
    @BindView(R.id.tblayout_wallet)
    TabLayout tblayoutWallet;
    @BindView(R.id.vp_wallet)
    ViewPager vpWallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_wallet);
        ButterKnife.bind(this);
        initToolbar();
        initVp();
    }


    private void initVp() {
        ArrayList<BaseFragment> baseFragments = new ArrayList<>();
        baseFragments.add(new WalletBalanceFragment());
        baseFragments.add(new WalletCouponingFragment());

//set adapter to your ViewPager
        vpWallet.setAdapter(new WalletVpAdapter(getSupportFragmentManager(), baseFragments));

        tblayoutWallet.setupWithViewPager(vpWallet);

        vpWallet.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                KeyBoardUtils.hideInputForce(NewWalletActivity.this);
//                ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(NewWalletActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_wallet, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.detail:
                Intent intent = new Intent();
                intent.setClass(NewWalletActivity.this, WalletDetailActivity.class);
                startActivity(intent);
                return true;
            default:
                // If we got here, the us
                return super.onOptionsItemSelected(item);

        }
    }




    private void initToolbar() {

        indexToolbar.setNavigationIcon(R.drawable.icon_back);
        indexToolbar.inflateMenu(R.menu.menu_wallet);
        indexToolbar.setTitleTextColor(Color.WHITE);
        indexToolbar.setTitle("");
        indexToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        indexToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(R.id.detail==item.getItemId()) {
                    Intent intent = new Intent();
                    intent.setClass(NewWalletActivity.this, WalletDetailActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
