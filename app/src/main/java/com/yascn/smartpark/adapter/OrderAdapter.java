package com.yascn.smartpark.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;


import com.yascn.smartpark.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YASCN on 2017/9/4.
 */

public class OrderAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragments;
    private FragmentManager fm;

    public OrderAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.mFragments = fragments;
        this.fm = fm;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    private static final String TAG = "OrderAdapter";

    @Override
    public int getCount() {
        LogUtil.d(TAG, "getCount: "+mFragments.size());
        return mFragments.size();
    }

    public void setFragments(ArrayList<Fragment> fragments) {
        if (this.mFragments != null) {
            FragmentTransaction ft = fm.beginTransaction();
            for (Fragment f : this.mFragments) {
                ft.remove(f);
            }
            ft.commit();
            ft = null;
            fm.executePendingTransactions();
        }
        this.mFragments = fragments;
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
