package com.yascn.smartpark.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.TextView;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;

import com.yascn.smartpark.SearchGaodeHistoryBeanDao;
import com.yascn.smartpark.base.AndroidApplication;
import com.yascn.smartpark.R;
import com.yascn.smartpark.adapter.RvGaodeSearchAdapter;
import com.yascn.smartpark.adapter.RvSearchHistoryAdapter;
import com.yascn.smartpark.base.BaseActivity;
import com.yascn.smartpark.bean.SearchGaodeHistoryBean;
import com.yascn.smartpark.contract.SearchGaodeContract;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.RecyclerViewDivider;
import com.yascn.smartpark.utils.RvItemClickListener;
import com.yascn.smartpark.utils.StringUtils;
import com.yascn.smartpark.utils.T;
import com.yascn.smartpark.viewmodel.SearchGaodeViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yascn.smartpark.utils.AppContants.BROADCASTGAODE;
import static com.yascn.smartpark.utils.AppContants.BROADCASTGAODELAT;
import static com.yascn.smartpark.utils.AppContants.BROADCASTGAODELON;
import static com.yascn.smartpark.utils.AppContants.BROADCASTGAODENAME;
import static com.yascn.smartpark.utils.AppContants.BROADCASTGAODESNIPPT;
import static com.yascn.smartpark.utils.AppContants.BROADCASTSEARCHHISTORYEMPTY;

public class SearchGaodeActivity extends BaseActivity implements SearchGaodeContract.View {


    @BindView(R.id.et_input)
    EditText etInput;
    @BindView(R.id.activity_introduction)
    LinearLayout activityIntroduction;

    @BindView(R.id.ll_error)
    LinearLayout llError;
    @BindView(R.id.iv_empty_view)
    ImageView ivEmptyView;
    @BindView(R.id.ll_nodata)
    LinearLayout llNodata;
    @BindView(R.id.rv_search)
    RecyclerView rvSearch;
    @BindView(R.id.ll_bar)
    LinearLayout llBar;

    @BindView(R.id.rv_history)
    RecyclerView rvHistory;
    @BindView(R.id.ll_search_history)
    LinearLayout llSearchHistory;
    private SearchGaodeHistoryBeanDao searchGaodeHistoryBeanDao;


    public BroadcastReceiver mainReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(BROADCASTSEARCHHISTORYEMPTY)) {
                setRvSearchVisibility(llNodata);


            }
        }
    };
    private SearchGaodeViewModel searchGaodeViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_gaode);
        ButterKnife.bind(this);


        setToolbarMargin(llBar);

        initRv();
        searchGaodeHistoryBeanDao = AndroidApplication.getInstances().getDaoSession().getSearchGaodeHistoryBeanDao();


        //1
        searchGaodeViewModel = ViewModelProviders.of(this).get(SearchGaodeViewModel.class);

        //2
        getLifecycle().addObserver(searchGaodeViewModel);

        //3
        Observer<PoiResult> poiResultObserver = new Observer<PoiResult>() {
            @Override
            public void onChanged(@Nullable PoiResult poiResult) {
                    setGaodeResultBean(poiResult);

            }
        };

        //4
        searchGaodeViewModel.getMutableLiveDataEntry().observe(this,poiResultObserver);
        searchGaodeViewModel.getQueryStatus().observe(this,statusObserver);

        setSearchHistoryStatus();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BROADCASTSEARCHHISTORYEMPTY);

        registerReceiver(mainReceiver, intentFilter);

        etInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
// 先隐藏键盘
                    hideKeyBoard();

//实现自己的搜索逻辑
                    if (!TextUtils.isEmpty(etInput.getText().toString().trim())) {
//                        searchPresenter.getSearchResult(etInput.getText().toString().trim());
                        if(searchGaodeViewModel!=null){
                            searchGaodeViewModel.getGaodeResultBean(etInput.getText().toString().trim());
                        }


                    } else {
                        T.showShort(SearchGaodeActivity.this, StringUtils.getRString(SearchGaodeActivity.this, R.string.search_string_empty));
                    }


                    return true;
                }
                return false;
            }
        });

        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    setSearchHistoryStatus();
//                    setRvSearchVisibility(llNodata);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }




    private void hideKeyBoard() {
        ((InputMethodManager) etInput.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(

                        SearchGaodeActivity.this.getCurrentFocus()
                                .getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void initRv() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvSearch.setLayoutManager(layoutManager);
        rvSearch.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.HORIZONTAL));


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mainReceiver);
    }

    @OnClick({R.id.tv_back, R.id.et_input,R.id.iv_delete_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                hideKeyBoard();
                finish();


                break;
            case R.id.iv_delete_all:
                searchGaodeHistoryBeanDao.deleteAll();

                if(searchGaodeHistoryBeanDao.loadAll().size()==0){
                    Intent intent = new Intent();
                    intent.setAction(BROADCASTSEARCHHISTORYEMPTY);
                    mActivity.sendBroadcast(intent);
                }


                break;
//            case R.id.et_input:
//                break;
        }
    }

    private boolean isNetFailed = false;

    @Override
    public void netWordIsFailed() {
        super.netWordIsFailed();
        isNetFailed = true;
    }

    @Override
    public void netWorkIsSuccess() {
        super.netWorkIsSuccess();

        if (searchGaodeViewModel != null && isNetFailed) {
            if (!TextUtils.isEmpty(etInput.getText().toString().trim())) {
                searchGaodeViewModel.getGaodeResultBean(etInput.getText().toString().trim());
            }

        }

    }


    @Override
    public void serverError(String errorMsg) {
        setRvSearchVisibility(llError);
        T.showShort(this, errorMsg);

    }

    private static final String TAG = "SearchGaodeActivity";

    @Override
    public void setGaodeResultBean(final PoiResult result) {
//        for(int i=0;i<result.getPois().size();i++){
//            LogUtil.d(TAG, "setGaodeResultBean: "+result.getPois().get(i).getTitle());
//        }
        RvGaodeSearchAdapter rvGaodeSearchAdapter = new RvGaodeSearchAdapter(this, result.getPois());
        rvSearch.setAdapter(rvGaodeSearchAdapter);

        LogUtil.d(TAG, "setGaodeResultBean: " + (result == null) + "******" + (result.getPois() == null));

        if (result != null && result.getPois() != null) {
            if (result.getPois().size() != 0) {
                setRvSearchVisibility(rvSearch);
                rvGaodeSearchAdapter.setOnItemClickListener(new RvItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        sendSearchBroadcast(result.getPois().get(position));
                    }
                });

            } else {
                LogUtil.d(TAG, "setGaodeResultBean: nodata1");
                setRvSearchVisibility(llNodata);
            }


        } else {
            LogUtil.d(TAG, "setGaodeResultBean: nodata");
            setRvSearchVisibility(llNodata);
        }


    }


    private void sendSearchBroadcast(PoiItem poiItem) {
        org.greenrobot.greendao.query.Query<SearchGaodeHistoryBean> query = searchGaodeHistoryBeanDao.queryBuilder().where(
                SearchGaodeHistoryBeanDao.Properties.Id.eq(poiItem.getPoiId()))
                .build();
        List idList = query.list();
        if(idList.size()==0){
            SearchGaodeHistoryBean searchGaodeHistoryBean = new SearchGaodeHistoryBean();
            searchGaodeHistoryBean.setLat(poiItem.getLatLonPoint().getLatitude());
            searchGaodeHistoryBean.setLon(poiItem.getLatLonPoint().getLongitude());
            searchGaodeHistoryBean.setSnaippt(poiItem.getSnippet());
            searchGaodeHistoryBean.setTitle(poiItem.getTitle());
            searchGaodeHistoryBean.setId(poiItem.getPoiId());
            searchGaodeHistoryBeanDao.insert(searchGaodeHistoryBean);
        }



//        searchGaodeHistoryBean.setla

        Intent intent = new Intent();
        intent.putExtra(BROADCASTGAODENAME, poiItem.getTitle());
        intent.putExtra(BROADCASTGAODESNIPPT, poiItem.getSnippet());
        intent.putExtra(BROADCASTGAODELAT, poiItem.getLatLonPoint().getLatitude());
        intent.putExtra(BROADCASTGAODELON, poiItem.getLatLonPoint().getLongitude());
        intent.setAction(BROADCASTGAODE);
        sendBroadcast(intent);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etInput.getWindowToken(), 0);
        this.finish();
    }

    @Override
    public void showProgress() {
        LogUtil.d(TAG, "showProgress: ");
        showProgressDialog(getString(R.string.loadingProgress));

    }

    @Override
    public void hideProgress() {
        LogUtil.d(TAG, "hideProgress: ");
        hidProgressDialog();
    }

    @Override
    public Context getContext() {
        return this;
    }

    private void setRvSearchVisibility(View view) {

        LogUtil.d(TAG, "setRvSearchVisibility: " + view.getId());
        llError.setVisibility(llError == view ? View.VISIBLE : View.GONE);
        llNodata.setVisibility(llNodata == view ? View.VISIBLE : View.GONE);
        rvSearch.setVisibility(rvSearch == view ? View.VISIBLE : View.GONE);
        llSearchHistory.setVisibility(llSearchHistory == view? View.VISIBLE:View.GONE);

    }



    private void setSearchHistoryStatus(){
        final List<SearchGaodeHistoryBean> searchGaodeHistoryBeans = searchGaodeHistoryBeanDao.loadAll();
        if(searchGaodeHistoryBeans.size()==0){
            setRvSearchVisibility(llNodata);
        }else{
            rvHistory.setLayoutManager(new LinearLayoutManager(this));
            RvSearchHistoryAdapter rvSearchHistoryAdapter = new RvSearchHistoryAdapter(this, searchGaodeHistoryBeans,searchGaodeHistoryBeanDao);
            rvHistory.setAdapter(rvSearchHistoryAdapter);
            setRvSearchVisibility(llSearchHistory);

            rvSearchHistoryAdapter.setOnItemClickListener(new RvItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    SearchGaodeHistoryBean searchGaodeHistoryBean = searchGaodeHistoryBeans.get(position);
                    PoiItem poiItem = new PoiItem(searchGaodeHistoryBean.getId(),new LatLonPoint(searchGaodeHistoryBean.getLat(),searchGaodeHistoryBean.getLon()),searchGaodeHistoryBean.getTitle(),searchGaodeHistoryBean.getSnaippt());
                    sendSearchBroadcast(poiItem);
                }
            });


        }


    }

}
