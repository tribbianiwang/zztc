package com.yascn.smartpark.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yascn.smartpark.R;
import com.yascn.smartpark.adapter.RvMessageAdapter;
import com.yascn.smartpark.base.BaseActivity;
import com.yascn.smartpark.bean.MessageBean;
import com.yascn.smartpark.contract.MessageContract;
import com.yascn.smartpark.presenter.MessagePresenter;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.SPUtils;
import com.yascn.smartpark.utils.T;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yascn.smartpark.utils.AppContants.REFLESHORDER;
import static com.yascn.smartpark.utils.AppContants.REFRESHMESSAGENUMBER;

public class MessageActivity extends BaseActivity implements MessageContract.View{

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.index_toolbar)
    Toolbar indexToolbar;
    @BindView(R.id.activity_introduction)
    LinearLayout activityIntroduction;
    @BindView(R.id.rv_message)
    RecyclerView rvMessage;
    @BindView(R.id.ll_error)
    LinearLayout llError;
    @BindView(R.id.iv_empty_view)
    ImageView ivEmptyView;
    @BindView(R.id.ll_nodata)
    LinearLayout llNodata;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    String userID;

    private MessageContract.Presenter presenter;


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(REFLESHORDER)){
                presenter.reloadData();
            }


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        userID = (String) SPUtils.get(this, AppContants.KEY_USERID, "");

        ButterKnife.bind(this);
        presenter = new MessagePresenter(this,refreshLayout, rvMessage, llNodata, userID,llError);
        presenter.queryList();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(REFLESHORDER);
        this.registerReceiver(broadcastReceiver,intentFilter);

        initTitle(getString(R.string.string_my_message));
        initRv();
    }



    @Override
    public void serverError(String errorMsg) {
        T.showShort(this,errorMsg);
    }

    @Override
    public void setMessageBean(MessageBean messageBean) {

    }

    private void initRv() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMessage.setLayoutManager(layoutManager);
    }

    @Override
    public void showProgress() {
        showProgressDialog(getString(R.string.loadingProgress));
    }

    @Override
    public void hideProgress() {
        hidProgressDialog();
    }

    @Override
    public Context getContext() {
        return this;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        REFRESHMESSAGENUMBER
                Intent intent = new Intent();
        intent.setAction(REFRESHMESSAGENUMBER);
        sendBroadcast(intent);

        if(presenter!=null){
            presenter.onDestory();
        }
        this.unregisterReceiver(broadcastReceiver);
    }
}
