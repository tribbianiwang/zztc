package com.yascn.smartpark.contract;

import com.yascn.smartpark.bean.SearchResult;

/**
 * Created by YASCN on 2017/9/12.
 */

public class SearchParkContract {

    public interface View extends BaseNormalContract.View{
        public void setSearchResult(SearchResult searchResult);
    }

    public interface Presenter extends BaseNormalContract.Presenter{
        public void getSearchResult(String keyWorld);
        public void setSearchResult(SearchResult searchResult);
    }

    public interface Model extends BaseNormalContract.Model{
        public void getSearchResult(SearchParkContract.Presenter presenter,String keyWorld);
    }

}