package com.yascn.smartpark.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yascn.smartpark.base.BaseFragment;

import java.util.ArrayList;

/**
 * Created by YASCN on 2018/4/23.
 */

public class OrderVpAdapter extends FragmentPagerAdapter {
    ArrayList<BaseFragment> vpFragments;
    private static final String vpFragmentTitles[] = new String[]{"进行中","已完成"};
    public OrderVpAdapter(FragmentManager fm, ArrayList<BaseFragment> vpFragments) {
        super(fm);

        this.vpFragments = vpFragments;

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return vpFragmentTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return vpFragments.get(0);
            case 1:
                return vpFragments.get(1);
            default:
                return vpFragments.get(0);
        }
    }

    @Override
    public int getCount() {
        return vpFragments.size();
    }





}
