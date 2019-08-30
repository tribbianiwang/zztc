package com.yascn.smartpark.viewmodel;

import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.SearchResult;
import com.yascn.smartpark.model.SearchParkModelImpl;

public class SearchViewModel extends BaseViewModel<SearchResult> implements BaseModel.DataResultListener<SearchResult> {
    SearchParkModelImpl searchParkModel;


    public void getSearchResult(String keyWorld) {
        if(searchParkModel==null){
            searchParkModel = new SearchParkModelImpl(this);
            baseModel = searchParkModel;
        }

            searchParkModel.getSearchResult(keyWorld);


    }


}
