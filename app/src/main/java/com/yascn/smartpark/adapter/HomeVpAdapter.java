package com.yascn.smartpark.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yascn.smartpark.base.BaseFragment;

import java.util.ArrayList;

/**
 * Created by YASCN on 2017/7/17.
 */

public class HomeVpAdapter extends FragmentPagerAdapter {



    ArrayList<BaseFragment> homeFragments;

    public HomeVpAdapter(FragmentManager fm, ArrayList<BaseFragment> homeFragments) {
        super(fm);
        this.homeFragments = homeFragments;
//        fm.beginTransaction().commitAllowingStateLoss();
    }

    @Override
    public Fragment getItem(int position) {
        return homeFragments.get(position);
    }

    @Override
    public int getCount() {
        return homeFragments.size();
    }


}
