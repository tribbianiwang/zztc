package com.yascn.smartpark.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.yascn.smartpark.SearchHistoryDaoBeanDao;
import com.yascn.smartpark.base.AndroidApplication;
import com.yascn.smartpark.R;
import com.yascn.smartpark.adapter.RvHistoryAdapter;
import com.yascn.smartpark.base.BaseActivity;
import com.yascn.smartpark.bean.SearchHistoryDaoBean;
import com.yascn.smartpark.bean.SearchResult;
import com.yascn.smartpark.contract.SearchParkContract;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.RvItemClickListener;
import com.yascn.smartpark.utils.StringUtils;
import com.yascn.smartpark.utils.T;
import com.yascn.smartpark.viewmodel.SearchViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yascn.smartpark.utils.AppContants.BROADCASTACTIONSEARCHPARK;
import static com.yascn.smartpark.utils.AppContants.BROADCASTDATASEARCHPARK;



public class SearchActivity extends BaseActivity implements SearchParkContract.View {

    @BindView(R.id.rv_history)
    RecyclerView rvHistory;
    @BindView(R.id.activity_introduction)
    LinearLayout activityIntroduction;
    @BindView(R.id.et_input)
    EditText etInput;
    @BindView(R.id.rl_history)
    RelativeLayout rlHistory;
    @BindView(R.id.iv_delete_history)
    ImageView iv_delete_history;
    @BindView(R.id.ll_bar)
    LinearLayout llBar;


    private SearchHistoryDaoBeanDao searchHistoryDaoBeanDao;
    private static final String TAG = "SearchActivity";
    private SearchViewModel searchViewModel;


    @Override
    public int[] hideSoftByEditViewIds() {
        int[] ids = {R.id.et_input};
        return ids;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);


        //1
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);


        //2
        getLifecycle().addObserver(searchViewModel);


        //3
        Observer<SearchResult> searchResultObserver = new Observer<SearchResult>() {
            @Override
            public void onChanged(@Nullable SearchResult searchResult) {
                    setSearchResult(searchResult);
            }
        };


        //4
        searchViewModel.getMutableLiveDataEntry().observe(this,searchResultObserver);
        searchViewModel.getQueryStatus().observe(this,statusObserver);





        setEdittextInputListener();
        searchHistoryDaoBeanDao = AndroidApplication.getInstances().getDaoSession().getSearchHistoryDaoBeanDao();
        initRecyclerView();
        setHistoryRvAdapter();

        etInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
// 先隐藏键盘
                    ((InputMethodManager) etInput.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(

                                            SearchActivity.this.getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);


//实现自己的搜索逻辑
                    if(!TextUtils.isEmpty(etInput.getText().toString().trim())){
                        if(searchViewModel!=null){
                            searchViewModel.getSearchResult(etInput.getText().toString().trim());
                        }


                    }else{
                        T.showShort(SearchActivity.this,StringUtils.getRString(SearchActivity.this,R.string.search_string_empty));
                    }




                    return true;
                }
                return false;
            }
        });



    }



    private void setHistoryRvAdapter() {
        final List<SearchHistoryDaoBean> searchHistoryDaoBeen = searchHistoryDaoBeanDao.loadAll();
        ArrayList<SearchResult.ObjectBean> searchResultObjects = new ArrayList<>();
        if (searchHistoryDaoBeen.size() == 0) {
            rlHistory.setVisibility(View.GONE);
            LogUtil.d(TAG, "setHistoryRvAdapter: gone");
        } else {
            LogUtil.d(TAG, "setHistoryRvAdapter: visible");
            rlHistory.setVisibility(View.VISIBLE);
        }
        for (int i = 0; i < searchHistoryDaoBeen.size(); i++) {
            SearchHistoryDaoBean searchHistoryDaoBean = searchHistoryDaoBeen.get(i);

            SearchResult.ObjectBean searchObject = new SearchResult.ObjectBean();
            searchObject.setLNG(searchHistoryDaoBean.getLNG());
            searchObject.setPARKING_ID(searchHistoryDaoBean.getPARKING_ID());
            searchObject.setADDRESS(searchHistoryDaoBean.getADDRESS());
            searchObject.setCOLLECTION_ID(searchHistoryDaoBean.getCOLLECTION_ID());
            searchObject.setFREE_NUM(searchHistoryDaoBean.getFREE_NUM());
            searchObject.setLAT(searchHistoryDaoBean.getLAT());
            searchObject.setNAME(searchHistoryDaoBean.getNAME());
            searchResultObjects.add(searchObject);
        }
        LogUtil.d(TAG, "setHistoryRvAdapter: " + searchResultObjects.size());
        RvHistoryAdapter rvHistoryAdapter = new RvHistoryAdapter(this, searchResultObjects);
        rvHistory.setAdapter(rvHistoryAdapter);
        rvHistoryAdapter.setOnItemClickListener(new RvItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                sendSearchBroadcast(searchHistoryDaoBeen.get(position).getPARKING_ID());
            }
        });
    }


    private void sendSearchBroadcast(String parkId){
        Intent intent = new Intent();
        intent.putExtra(BROADCASTDATASEARCHPARK,parkId);
        intent.setAction(BROADCASTACTIONSEARCHPARK);
        sendBroadcast(intent);
         InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etInput.getWindowToken(), 0);
        this.finish();
    }

    private void setEdittextInputListener() {

        etInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 输入的内容变化的监听
                LogUtil.e("输入过程中执行该方法", "文字变化" + s);
                if (s.toString().trim().isEmpty()) {
                    setHistoryRvAdapter();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // 输入前的监听
                LogUtil.e("输入前确认执行该方法", "开始输入");

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 输入后的监听
                if (!etInput.getText().toString().toString().isEmpty()) {
                    if(searchViewModel!=null){
                        searchViewModel.getSearchResult(etInput.getText().toString().trim());
                    }

                }

                LogUtil.e("输入结束执行该方法", "输入结束");

            }
        });

    }

    private void initRecyclerView() {

        rvHistory.setLayoutManager(new LinearLayoutManager(this));
//        rvHistory.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.HORIZONTAL));


    }

    @Override
    public void serverError(String errorMsg) {
        T.showShort(this, errorMsg);

    }

    @Override
    public void setSearchResult(SearchResult searchResult) {
        rlHistory.setVisibility(View.GONE);
        final List<SearchResult.ObjectBean> searchResultObjects = searchResult.getObject();
        if(searchResultObjects.size()==0){
            T.showShort(SearchActivity.this,"无搜索结果");
        }
        for (int i = 0; i < searchResultObjects.size(); i++) {
            if (!isSearchHistoryContainsthisObject(searchResultObjects.get(i))) {
                addSearchObjectToHistory(searchResultObjects.get(i));
            }


        }

        RvHistoryAdapter rvHistoryAdapter = new RvHistoryAdapter(this, searchResultObjects);
        rvHistory.setAdapter(rvHistoryAdapter);

        rvHistoryAdapter.setOnItemClickListener(new RvItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                sendSearchBroadcast(searchResultObjects.get(position).getPARKING_ID());
            }
        });
    }

    private void addSearchObjectToHistory(SearchResult.ObjectBean objectBean) {
        LogUtil.d(TAG, "addSearchObjectToHistory: " + objectBean.getNAME());
        SearchHistoryDaoBean searchHistoryDaoBean = new SearchHistoryDaoBean();
        searchHistoryDaoBean.setLNG(objectBean.getLNG());
        searchHistoryDaoBean.setPARKING_ID(objectBean.getPARKING_ID());
        searchHistoryDaoBean.setADDRESS(objectBean.getADDRESS());
        searchHistoryDaoBean.setCOLLECTION_ID(objectBean.getCOLLECTION_ID());
        searchHistoryDaoBean.setFREE_NUM(objectBean.getFREE_NUM());
        searchHistoryDaoBean.setLAT(objectBean.getLAT());
        searchHistoryDaoBean.setNAME(objectBean.getNAME());
        searchHistoryDaoBeanDao.insert(searchHistoryDaoBean);


    }

    private boolean isSearchHistoryContainsthisObject(SearchResult.ObjectBean objectBean) {
        boolean result = false;
        List<SearchHistoryDaoBean> searchHistoryDaoBeen = searchHistoryDaoBeanDao.loadAll();
        for (int i = 0; i < searchHistoryDaoBeen.size(); i++) {
            if (searchHistoryDaoBeen.get(i).getPARKING_ID().equals(objectBean.getPARKING_ID())) {
                result = true;
                break;
            } else if (i == searchHistoryDaoBeen.size() - 1) {
                result = false;
            }

        }
        LogUtil.d(TAG, "isSearchHistoryContainsthisObject: " + result);
        return result;
    }


    @Override
    public void showProgress() {

        showProgressDialog(StringUtils.getRString(this, R.string.loadingProgress));
    }

    @Override
    public void hideProgress() {
        hidProgressDialog();

    }

    @Override
    public Context getContext() {
        return this;
    }

    @OnClick({R.id.iv_delete_history,R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_delete_history:
                searchHistoryDaoBeanDao.deleteAll();
                rlHistory.setVisibility(View.GONE);
                setHistoryRvAdapter();
                break;

            case R.id.iv_back:
                finish();

                break;
        }



    }


}
