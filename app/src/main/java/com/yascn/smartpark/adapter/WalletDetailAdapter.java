package com.yascn.smartpark.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by YASCN on 2017/9/4.
 */

public class WalletDetailAdapter extends FragmentPagerAdapter {
    private static final String vpFragmentTitles[] = new String[]{"消费","充值"};
    private List<Fragment> fragments;

    public WalletDetailAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return vpFragmentTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
