package com.yascn.smartpark.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yascn.smartpark.R;
import com.yascn.smartpark.adapter.RvMonthCardAdapter;
import com.yascn.smartpark.base.BaseActivity;
import com.yascn.smartpark.bean.MonthCardListBean;
import com.yascn.smartpark.contract.MonthCardListContract;
import com.yascn.smartpark.utils.T;
import com.yascn.smartpark.viewmodel.MonthCardListViewmodel;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yascn.smartpark.utils.AppContants.ISREFILL;
import static com.yascn.smartpark.utils.AppContants.PARKIDTRANS;
import static com.yascn.smartpark.utils.AppContants.PARKNAMETRANS;

public class MonthCardActivity extends BaseActivity implements MonthCardListContract.View {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.index_toolbar)
    Toolbar indexToolbar;
    @BindView(R.id.activity_introduction)
    LinearLayout activityIntroduction;
    @BindView(R.id.rv_month_card)
    RecyclerView rvMonthCard;


    private MonthCardListViewmodel monthCardListViewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_card);
        ButterKnife.bind(this);
        initToolbar();
        initRecyclerView();

        //1
        monthCardListViewmodel = ViewModelProviders.of(this).get(MonthCardListViewmodel.class);

        //2
        getLifecycle().addObserver(monthCardListViewmodel);

        //3
        Observer<MonthCardListBean> monthCardListBeanObserver = new Observer<MonthCardListBean>() {
            @Override
            public void onChanged(@Nullable MonthCardListBean monthCardListBean) {
                setMonthCardList(monthCardListBean);
            }
        };

        //4
        monthCardListViewmodel.getMutableLiveDataEntry().observe(this,monthCardListBeanObserver);
        monthCardListViewmodel.getQueryStatus().observe(this,statusObserver);


        if(monthCardListViewmodel!=null){
            monthCardListViewmodel.getMonthCardList();
        }

    }


    private void initToolbar() {

        indexToolbar.setNavigationIcon(R.drawable.icon_back);

        indexToolbar.setTitleTextColor(Color.WHITE);
        indexToolbar.setTitle("");
        indexToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


    private void initRecyclerView() {

        rvMonthCard.setLayoutManager(new LinearLayoutManager(this));



    }

    @Override
    public void setMonthCardList(final MonthCardListBean monthCardListBean) {
        RvMonthCardAdapter rvMonthCardAdapter = new RvMonthCardAdapter(this,monthCardListBean);
        View mainRvContentHeader = View.inflate(this,R.layout.rv_month_card_header,null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mainRvContentHeader.setLayoutParams(layoutParams);
        rvMonthCardAdapter.setHeaderView(mainRvContentHeader);

        rvMonthCardAdapter.setOnItemClickListener(new RvMonthCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                T.showShort(MonthCardActivity.this,""+monthCardListBean.getObject().get(position).getName());
                Intent intent = new Intent(MonthCardActivity.this,MonthCardDetailActivity.class);
                intent.putExtra(PARKNAMETRANS,monthCardListBean.getObject().get(position).getName());
                intent.putExtra(PARKIDTRANS,monthCardListBean.getObject().get(position).getPark_id());
                intent.putExtra(ISREFILL,false);

                startActivity(intent);
//                skipActivity(MonthCardDetailActivity.class);

            }
        });

        rvMonthCard.setAdapter(rvMonthCardAdapter);

    }

    @Override
    public void serverError(String errorMsg) {
        T.showShort(this,errorMsg);
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


}
