package com.yascn.smartpark.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.yascn.smartpark.R;
import com.yascn.smartpark.base.BaseActivity;
import com.yascn.smartpark.bean.OrderList;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yascn.smartpark.utils.AppContants.HASCOMMENTSTRING;
import static com.yascn.smartpark.utils.AppContants.NOTCOMMENTSTRING;
import static com.yascn.smartpark.utils.AppContants.ORDERDETAILBEAN;
import static com.yascn.smartpark.utils.AppContants.ORDERFORMID;
import static com.yascn.smartpark.utils.AppContants.ORDERSTATUSCANCELSTRING;
import static com.yascn.smartpark.utils.AppContants.ORDERSTATUSFINISHSTRING;

public class OrderDetailActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.index_toolbar)
    Toolbar indexToolbar;
    @BindView(R.id.view_park_name_left)
    View viewParkNameLeft;
    @BindView(R.id.tv_park_name)
    TextView tvParkName;
    @BindView(R.id.tv_pay_fee)
    TextView tvPayFee;
    @BindView(R.id.tv_in_time)
    TextView tvInTime;
    @BindView(R.id.tv_out_time)
    TextView tvOutTime;
    @BindView(R.id.tv_park_during_time)
    TextView tvParkDuringTime;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_pay_time)
    TextView tvPayTime;
    @BindView(R.id.tv_pay_type)
    TextView tvPayType;
    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;
    @BindView(R.id.iv_to_comment)
    ImageView ivToComment;
    @BindView(R.id.tv_comment_status)
    TextView tvCommentStatus;
    @BindView(R.id.activity_introduction)
    LinearLayout activityIntroduction;
    @BindView(R.id.rl_to_comment)
    RelativeLayout rlToComment;
    @BindView(R.id.tv_coupon_money)
    TextView tvCouponMoney;
    @BindView(R.id.rl_couponing)
    RelativeLayout rlCouponing;
    @BindView(R.id.tv_car_license)
    TextView tvCarLicense;
    private OrderList.ObjectBean.OrderListBean orderleistBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        orderleistBean = (OrderList.ObjectBean.OrderListBean) getIntent().getSerializableExtra(ORDERDETAILBEAN);

        initTitle(getString(R.string.string_order_detail));

        showData();

    }

    private static final String TAG = "OrderDetailActivity%s";
    private void showData() {
        LogUtil.d(TAG, "showData: getnumber"+orderleistBean.getNUMBER());
        tvCarLicense.setText(orderleistBean.getNUMBER());
        tvParkName.setText(orderleistBean.getNAME());
        tvPayFee.setText(getString(R.string.cny_unit) + orderleistBean.getMONEY());
        tvInTime.setText(orderleistBean.getSTART_TIME());
        tvOutTime.setText(orderleistBean.getEND_TINE());
        tvParkDuringTime.setText(orderleistBean.getTIME());
        tvStatus.setText(orderleistBean.getSTATUS().equals(AppContants.ORDERSTATUSCANCEL) ? ORDERSTATUSCANCELSTRING : ORDERSTATUSFINISHSTRING);
        tvPayTime.setText(orderleistBean.getPAY_TIME());
        tvPayType.setText(orderleistBean.getPAY_METHOD());
        tvOrderNumber.setText(orderleistBean.getORDER_NO());
        tvCouponMoney.setText(getString(R.string.cny_unit) + orderleistBean.getCOUPONM());
        tvCommentStatus.setText(orderleistBean.getIS_COM().equals(AppContants.HASCOMMENT) ? HASCOMMENTSTRING : NOTCOMMENTSTRING);


            if(0.0==StringUtils.emptyParseDouble(orderleistBean.getCOUPONM())){
                rlCouponing.setVisibility(View.GONE);
            }else{
                rlCouponing.setVisibility(View.VISIBLE);
            }


        if (orderleistBean.getIS_COM().equals(AppContants.HASCOMMENT)) {
            ivToComment.setVisibility(View.INVISIBLE);
            rlToComment.setClickable(false);
            tvCommentStatus.setTextColor(StringUtils.getRColor(this, R.color.design_text_gray));
        } else {
            ivToComment.setVisibility(View.VISIBLE);
            rlToComment.setClickable(true);
            tvCommentStatus.setTextColor(StringUtils.getRColor(this, R.color.design_main_blue));
        }

    }


    @OnClick(R.id.rl_to_comment)
    public void onViewClicked() {

        Intent intent = new Intent(mActivity, EvaluateActivity.class);
//                    LogUtil.d(TAG, "onClick:orderformid"+orderListBeans.get(position).getORDER_FORM_ID());
        intent.putExtra(ORDERFORMID, orderleistBean.getORDER_FORM_ID());
        mActivity.startActivity(intent);
        finish();
    }
}
