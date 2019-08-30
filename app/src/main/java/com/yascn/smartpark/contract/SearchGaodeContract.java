package com.yascn.smartpark.contract;

import android.content.Context;

import com.amap.api.services.poisearch.PoiResult;

/**
 * Created by YASCN on 2018/3/26.
 */

public interface SearchGaodeContract {

    public interface View extends BaseNormalContract.View{
        public void setGaodeResultBean(PoiResult result);
    }

    public interface Presenter extends BaseNormalContract.Presenter{
        public void getGaodeResultBean(String key);
        public void setGaodeResultBean(PoiResult result);
    }

    public interface Model extends BaseNormalContract.Model{
        public void getGaodeResultBean(SearchGaodeContract.Presenter presenter, Context context, String key);
    }
}
