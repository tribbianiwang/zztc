package com.yascn.smartpark.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yascn.smartpark.base.BaseFragment;

import java.util.List;

/**
 * Created by YASCN on 2017/9/4.
 */

public class OpinionAdapter extends FragmentPagerAdapter {
    private static final String vpFragmentTitles[] = new String[]{"服务问题","功能问题","充值问题"};
    private List<BaseFragment> fragments;

    public OpinionAdapter(FragmentManager fm, List<BaseFragment> fragments) {
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
