package com.yascn.smartpark.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yascn.smartpark.R;
import com.yascn.smartpark.activity.OrderActivity;
import com.yascn.smartpark.adapter.RvOrderOngoingAdapter;
import com.yascn.smartpark.base.BaseFragment;
import com.yascn.smartpark.bean.Ordering;
import com.yascn.smartpark.contract.OrderOngoingContract;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.SPUtils;
import com.yascn.smartpark.utils.StringUtils;
import com.yascn.smartpark.utils.T;
import com.yascn.smartpark.viewmodel.OrderOngoingViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yascn.smartpark.utils.AppContants.PAYSUCCESSBROADCAST;
import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSFIILED;
import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSLOADING;
import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSNOMOREDATA;
import static com.yascn.smartpark.utils.AppContants.QUERYSTATUSSUCCESS;
import static com.yascn.smartpark.utils.AppContants.REFLESHORDER;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderOngoingFragment extends BaseFragment implements OrderOngoingContract.View {


    @BindView(R.id.rv_order_ongoing)
    RecyclerView rvOrderOngoing;

    @BindView(R.id.iv_empty_view)
    ImageView ivEmptyView;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.ll_error)
    LinearLayout llError;
    @BindView(R.id.ll_nodata)
    LinearLayout llNodata;
    private OrderActivity orderActivity;
    private OrderOngoingContract.Presenter presenter;
    private String userID;
    private RvOrderOngoingAdapter rvOrderOngoingAdapter;
    private OrderOngoingViewModel orderOngoingViewModel;

    public OrderOngoingFragment() {
        // Required empty public constructor
    }

    public BroadcastReceiver mainReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(REFLESHORDER)) {

                LogUtil.d(TAG, "onReceive: ");
                reloadData();

            }else if (intent.getAction().equals(PAYSUCCESSBROADCAST)) {
                LogUtil.d(TAG,"orderongoingfragment:paysuccessdialog");

                if(rvOrderOngoingAdapter!=null){
                    rvOrderOngoingAdapter.hidePayDialog();
                }

            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_ongoing, container, false);
        ButterKnife.bind(this, view);
        LogUtil.d(TAG, "onCreateView: " + rvOrderOngoing);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        orderActivity = (OrderActivity) getActivity();
        userID = (String) SPUtils.get(orderActivity, AppContants.KEY_USERID, "");


        rvOrderOngoing.setLayoutManager(new LinearLayoutManager(orderActivity));

        //1.
        orderOngoingViewModel = ViewModelProviders.of(this).get(OrderOngoingViewModel.class);

        //2
        getLifecycle().addObserver(orderOngoingViewModel);

        //3
        Observer<Ordering> orderingObserver = new Observer<Ordering>() {
            @Override
            public void onChanged(@Nullable Ordering ordering) {
                setOrdering(ordering);
            }
        };


        Observer<String> queryStatus = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                switch (s){
                    case QUERYSTATUSNOMOREDATA:
                        hideProgress();
                        serverError(StringUtils.getRString(orderActivity,R.string.no_more_data));

                        break;

                    case QUERYSTATUSFIILED:
                        hideProgress();
                        serverError(AppContants.SERVERERROR);

                        break;

                    case QUERYSTATUSSUCCESS:
                        hideProgress();

                        break;

                    case QUERYSTATUSLOADING:
                        showProgress();

                        break;

                }

            }
        };


        //4
        orderOngoingViewModel.getMutableLiveDataEntry().observe(this,orderingObserver);
        orderOngoingViewModel.getQueryStatus().observe(this,statusObserver);





        if(orderOngoingViewModel !=null){
            orderOngoingViewModel.getOrdering(userID);
        }


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(REFLESHORDER);
        intentFilter.addAction(PAYSUCCESSBROADCAST);

        orderActivity.registerReceiver(mainReceiver, intentFilter);

        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                isPullRefresh = true;
                reloadData();

            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mainReceiver);

    }

    @Override
    public void serverError(String errorMsg) {
        showContent(llError);
        T.showShort(orderActivity, errorMsg);

    }

    private void showContent(View  view){
        rvOrderOngoing.setVisibility(View.GONE);
        llNodata.setVisibility(View.GONE);
        llError.setVisibility(View.GONE);
        view.setVisibility(View.VISIBLE);

    }
    boolean isPullRefresh = false;
    @Override
    public void showProgress() {
        if(isPullRefresh){
            return;
        }
            orderActivity.showProgressDialog(getString(R.string.loadingProgress));


    }

    @Override
    public void hideProgress() {

        if(isPullRefresh){
            refreshLayout.finishRefresh();
            return;
        }
        orderActivity.hidProgressDialog();
    }

    private static final String TAG = "OrderOngoingFragment%s";

    @Override
    public void setOrdering(Ordering ordering) {


        if(ordering.getObject().size()==0){
            showContent(llNodata);
        }else{
            showContent(rvOrderOngoing);
        }
        rvOrderOngoingAdapter = new RvOrderOngoingAdapter(orderActivity, this, ordering);
        rvOrderOngoing.setAdapter(rvOrderOngoingAdapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();



    }

    @OnClick({R.id.ll_error, R.id.ll_nodata})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_error:
                reloadData();
                break;
            case R.id.ll_nodata:
                reloadData();
                break;
        }
    }

    private void reloadData(){
        if (orderOngoingViewModel != null && !TextUtils.isEmpty(userID)) {
            orderOngoingViewModel.getOrdering(userID);
        }
    }
}
