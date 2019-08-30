package com.yascn.smartpark.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yascn.smartpark.R;
import com.yascn.smartpark.activity.OrderActivity;
import com.yascn.smartpark.base.BaseFragment;
import com.yascn.smartpark.bean.OrderList;
import com.yascn.smartpark.contract.OrderFinishContract;
import com.yascn.smartpark.presenter.OrderFinishPresenter;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.RecyclerViewDivider;
import com.yascn.smartpark.utils.SPUtils;
import com.yascn.smartpark.utils.T;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yascn.smartpark.utils.AppContants.REFLESHORDER;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFinishFragment extends BaseFragment implements OrderFinishContract.View{


    @BindView(R.id.rv_finish)
    RecyclerView rvFinish;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.ll_error)
    LinearLayout llError;
    @BindView(R.id.iv_empty_view)
    ImageView ivEmptyView;
    @BindView(R.id.ll_nodata)
    LinearLayout llNodata;
    private OrderActivity orderActivity;
    private OrderFinishContract.Presenter presenter;
    private String userID;

    public OrderFinishFragment() {
        // Required empty public constructor
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(REFLESHORDER)){
                presenter.reloadData();
            }


        }
    };



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_finish, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private static final String TAG = "OrderFinishFragment%s";

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        orderActivity = (OrderActivity) getActivity();
        userID = (String) SPUtils.get(orderActivity, AppContants.KEY_USERID, "");
        LogUtil.d(TAG, "onActivityCreated: userid"+userID);
        presenter = new OrderFinishPresenter(this,refreshLayout, rvFinish, llNodata, userID,llError);
        presenter.queryList();
        initRv();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(REFLESHORDER);
        orderActivity.registerReceiver(broadcastReceiver,intentFilter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(presenter!=null){
            presenter.onDestory();
        }
        orderActivity.unregisterReceiver(broadcastReceiver);

    }

    @Override
    public void showProgress() {

        LogUtil.d(TAG, "showProgress: ");
        orderActivity.showProgressDialog(orderActivity.getString(R.string.loadingProgress));
    }

    @Override
    public void hideProgress() {
        LogUtil.d(TAG, "hideProgress: ");
        orderActivity.hidProgressDialog();
    }

    @Override
    public void serverError(String errorMsg) {

        T.showShort(orderActivity,errorMsg);
    }

    @Override
    public void setOrderList(OrderList orderList) {

    }

    private void initRv() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvFinish.setLayoutManager(layoutManager);
    }


    public void showContent(View  view){
        rvFinish.setVisibility(View.GONE);
        llNodata.setVisibility(View.GONE);
        llError.setVisibility(View.GONE);
        view.setVisibility(View.VISIBLE);

    }
}
