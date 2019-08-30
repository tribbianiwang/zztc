package com.yascn.smartpark.model;

import android.content.Context;
import android.util.Log;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;

import com.yascn.smartpark.R;
import com.yascn.smartpark.base.AndroidApplication;
import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.contract.SearchGaodeContract;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.viewmodel.SearchGaodeViewModel;

import java.util.List;

/**
 * Created by YASCN on 2018/3/26.
 */

public class SearchGaodeModel extends BaseModel implements  PoiSearch.OnPoiSearchListener {

    private PoiSearch.Query query;



    public SearchGaodeModel(SearchGaodeViewModel searchGaodeViewModel) {
        dataResultListener = searchGaodeViewModel;
    }


    public void getGaodeResultBean( String key) {
        dataResultListener.setQueryStatus(AppContants.QUERYSTATUSLOADING);

        LogUtil.d(TAG, "getGaodeResultBean: ");
        // 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query = new PoiSearch.Query(key, "", AppContants.DEFULTCITY);
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(0);// 设置查第一页


        PoiSearch   poiSearch = new PoiSearch(AndroidApplication.getInstances().getApplicationContext(), query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();

    }

    private static final String TAG = "SearchGaodeModel";

    @Override
    public void onDestory() {

    }

    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        LogUtil.d(TAG, "onPoiSearched: "+rCode);
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果

                LogUtil.d(TAG, "onPoiSearched: notempty");

                dataResultListener.setQueryStatus(AppContants.QUERYSTATUSSUCCESS);

                dataResultListener.dataSuccess(result);

            } else {
                LogUtil.d(TAG, "onPoiSearched: empty");
                dataResultListener.setQueryStatus(AppContants.QUERYSTATUSFIILED);
                dataResultListener.setQueryStatus(AppContants.SEARCHNORESULT);

            }
        } else {
            dataResultListener.setQueryStatus(AppContants.QUERYSTATUSFIILED);
            dataResultListener.setQueryStatus(AppContants.SEARCHFIELD);
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }
}
