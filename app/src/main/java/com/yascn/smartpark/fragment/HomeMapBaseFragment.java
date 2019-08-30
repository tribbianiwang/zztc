package com.yascn.smartpark.fragment;

import com.yascn.smartpark.base.BaseFragment;
import com.yascn.smartpark.bean.YascnParkListBean;

/**
 * Created by YASCN on 2018/4/17.
 */

public abstract class HomeMapBaseFragment extends BaseFragment {

    public abstract void serverError(String msg);
    public abstract void setYascnParkData(YascnParkListBean yascnParkListBean);
}
