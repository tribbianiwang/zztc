package com.yascn.smartpark.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yascn.smartpark.R;
import com.yascn.smartpark.activity.MainActivity;
import com.yascn.smartpark.activity.MessageActivity;
import com.yascn.smartpark.activity.MyCarActivity;
import com.yascn.smartpark.activity.MyInformationActivity;
import com.yascn.smartpark.activity.NewOpinionActivity;
import com.yascn.smartpark.activity.NewWalletActivity;
import com.yascn.smartpark.activity.OnActivity;
import com.yascn.smartpark.activity.OrderActivity;
import com.yascn.smartpark.activity.PickCouponingActivity;
import com.yascn.smartpark.activity.ShareActivity;
import com.yascn.smartpark.activity.UserGuideActivity;
import com.yascn.smartpark.base.BaseFragment;
import com.yascn.smartpark.bean.Userinfo;
import com.yascn.smartpark.contract.MineContract;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.DensityUtils;
import com.yascn.smartpark.utils.ImageViewUtils;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.LoginStatusUtils;
import com.yascn.smartpark.utils.NotchScreenUtils;
import com.yascn.smartpark.utils.SPUtils;
import com.yascn.smartpark.utils.StringUtils;
import com.yascn.smartpark.utils.T;
import com.yascn.smartpark.utils.ViewUtils;
import com.yascn.smartpark.view.CircleImageView;
import com.yascn.smartpark.viewmodel.MineViewModel;

import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import static com.yascn.smartpark.utils.AppContants.HASCLICKUSERGUIDE;
import static com.yascn.smartpark.utils.AppContants.KEY_JPUSH;
import static com.yascn.smartpark.utils.AppContants.MINEDATADEFULT;
import static com.yascn.smartpark.utils.AppContants.REFRESHMESSAGENUMBER;
import static com.yascn.smartpark.utils.ImageViewUtils.getDefaultHead;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment implements MineContract.View {


    @BindView(R.id.userAvatar)
    CircleImageView userAvatar;

    @BindView(R.id.car)
    RelativeLayout car;

    @BindView(R.id.ll_wallet)
    LinearLayout wallet;

    @BindView(R.id.record)
    RelativeLayout record;

    @BindView(R.id.opinion)
    RelativeLayout opinion;

    @BindView(R.id.twitter)
    RelativeLayout twitter;

    @BindView(R.id.on)
    RelativeLayout on;




    @BindView(R.id.content)
    LinearLayout content;

    @BindView(R.id.userName)
    TextView userName;


    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.iv_opinion)
    ImageView ivOpinion;

    @BindView(R.id.tv_use_direction)
    TextView tvUseDirection;
    @BindView(R.id.view_red_circle)
    View viewRedCircle;
    @BindView(R.id.rl_use_guide)
    RelativeLayout rlUseGuide;
    @BindView(R.id.iv_message)
    ImageView ivMessage;
    @BindView(R.id.view_red_circle_message)
    View viewRedCircleMessage;
    @BindView(R.id.iv_car_manager)
    ImageView ivCarManager;
    @BindView(R.id.iv_wallet)
    ImageView ivWallet;
    @BindView(R.id.iv_my_order)
    ImageView ivMyOrder;
    @BindView(R.id.iv_recommand)
    ImageView ivRecommand;
    @BindView(R.id.iv_use)
    ImageView ivUse;
    @BindView(R.id.iv_about_us)
    ImageView ivAboutUs;
    @BindView(R.id.tv_wallet_balance)
    TextView tvWalletBalance;
    @BindView(R.id.ll_wallet_coupon_points)
    LinearLayout llWalletCouponPoints;

    @BindView(R.id.tv_coupon_number)
    TextView tvCouponNumber;


    private Userinfo userinfo;
    private String userID;
    private MainActivity mainActivity;
    private MineViewModel mineViewModel;


    public MineFragment() {
        // Required empty public constructor
    }

    private void checkJPush() {
        if (!LoginStatusUtils.isLogin(getActivity())) {
            return;
        }

        boolean hasSighed = (boolean) SPUtils.get(getActivity(), KEY_JPUSH, false);
        LogUtil.d(TAG, "signJPush: hasSigned" + hasSighed);
        if (hasSighed) {
            return;
        }

        JPushInterface.resumePush(getActivity());
//        LogUtil.d(TAG, "onSuccess: "+phone);
        String phone = (String) SPUtils.get(getActivity(), AppContants.KEY_PHONE, "");


        JPushInterface.setAlias(getActivity(), phone, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                if (i == 0) {
                    LogUtil.d(TAG, "gotResultlogin:jpushsuccess ");
                    SPUtils.put(getActivity(), KEY_JPUSH, false);

                } else {
                    LogUtil.d(TAG, "gotResultlogin:jpushfailed" + i + "--" + s);
                    LogUtil.d(AppContants.TAG, "i = " + i);
                    LogUtil.d(AppContants.TAG, "s = " + s);
//                    T.showShort(loginView.getContext(), "登录失败,请重试" );
//                    loginView.hideDialog();
                    SPUtils.put(getActivity(), KEY_JPUSH, true);
                }
            }
        });


    }


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(AppContants.REFRESHUSERINFO)) {
                LogUtil.d(TAG, "刷新");
                getuserInfo();

            } else if (intent.getAction().equals(MINEDATADEFULT)) {
                updateLoginViewStatus();
            }else if(intent.getAction().equals(REFRESHMESSAGENUMBER)){
                getuserInfo();
            }
        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainActivity = (MainActivity) getActivity();

        setHeadMargin();
        boolean hasclickUserGuide = (boolean) SPUtils.get(mainActivity, HASCLICKUSERGUIDE, false);

        viewRedCircle.setVisibility(hasclickUserGuide ? View.GONE : View.VISIBLE);
        checkJPush();


        mineViewModel = ViewModelProviders.of(this).get(MineViewModel.class);
        getLifecycle().addObserver(mineViewModel);
        Observer<Userinfo> userinfoObserver = new Observer<Userinfo>() {
            @Override
            public void onChanged(@Nullable Userinfo userinfo) {
          setUserInfo(userinfo);

            }
        };

        mineViewModel.getMutableLiveDataEntry().observe(this,userinfoObserver);
        mineViewModel.getQueryStatus().observe(this,statusObserver);








        getuserInfo();


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(AppContants.REFRESHUSERINFO);
        intentFilter.addAction(REFRESHMESSAGENUMBER);
        intentFilter.addAction(MINEDATADEFULT);
        getActivity().registerReceiver(broadcastReceiver, intentFilter);
        updateLoginViewStatus();

    }


    private void updateLoginViewStatus(){
        if (TextUtils.isEmpty((CharSequence) SPUtils.get(mainActivity, AppContants.KEY_USERID, ""))) {

            userName.setText(StringUtils.getRString(mainActivity, R.string.login));
            userName.setBackgroundResource(R.drawable.rounded_white_stroke);
            userAvatar.setImageResource(R.drawable.icon_avatar);
            llWalletCouponPoints.setVisibility(View.GONE);
        } else {
            userName.setText(StringUtils.getSecretPhoneNum((String) SPUtils.get(getContext(), AppContants.KEY_PHONE, "")));
            userName.setBackgroundColor(Color.TRANSPARENT);
            llWalletCouponPoints.setVisibility(View.VISIBLE);

        }
    }

    private void getuserInfo() {
        userID = (String) SPUtils.get(getContext(), AppContants.KEY_USERID, "");
        LogUtil.d(TAG, "getuserInfo: " + userID);
        if (!TextUtils.isEmpty(userID) && mineViewModel != null) {
            mineViewModel.getUserInfo(userID);
        }



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);

        ButterKnife.bind(this, view);
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setEnableRefresh(true);

        return view;
    }

    private void setHeadMargin() {

        LogUtil.d(TAG, "onCreate: "+ NotchScreenUtils.getIntchHeight(mainActivity));
//        rlHead.setPadding(0, DensityUtils.dp2px(mainActivity,NotchScreenUtils.getIntchHeight(mainActivity)),0,0);
        ViewUtils.setMargins(rlHead,0, (int) DensityUtils.dp2px(mainActivity,NotchScreenUtils.getIntchHeight(mainActivity)),0,0);
    }
    public void setAvatar(String imgPath) {
        if (TextUtils.isEmpty(imgPath)) {
            userAvatar.setImageResource(R.drawable.icon_avatar);
        } else {
            userAvatar.setImageBitmap(BitmapFactory.decodeFile(imgPath));
        }
    }


    public void jumpActivity(Class activity) {
        Intent intent = new Intent();
        intent.putExtra("userinfo", userinfo);
        intent.setClass(getActivity(), activity);
        startActivity(intent);
    }

    private static final String TAG = "MineFragment";





    public void setProgressVisible(View view) {
//        error.setVisibility(View.GONE);
//        content.setVisibility(View.GONE);
        content.setVisibility(View.VISIBLE);

        view.setVisibility(View.VISIBLE);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(broadcastReceiver);



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();


    }

    @OnClick({ R.id.userAvatar, R.id.car, R.id.ll_wallet, R.id.record, R.id.opinion, R.id.twitter, R.id.on, R.id.userName, R.id.rl_head, R.id.rl_use_guide,R.id.rl_message,R.id.ll_coupon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.userAvatar:

                if (LoginStatusUtils.isLogin(getActivity())) {
                    if (userinfo != null) {

                        Intent intent = new Intent(getActivity(), MyInformationActivity.class);
                        intent.putExtra("userinfo", userinfo);
                        startActivity(intent);
                    } else {
                        T.showShort(mainActivity, getString(R.string.string_server_error));
                    }

                } else {
                    mainActivity.showInputPhoneDialog();
                }

                break;


            case R.id.car:
                if (LoginStatusUtils.isLogin(getActivity())) {
                    jumpActivity(MyCarActivity.class);
                } else {
                    mainActivity.showInputPhoneDialog();
                }


                break;
            case R.id.ll_wallet:

                if (LoginStatusUtils.isLogin(getActivity())) {
                    jumpActivity(NewWalletActivity.class);
                } else {
                    mainActivity.showInputPhoneDialog();
                }


                break;
            case R.id.record:
                if (LoginStatusUtils.isLogin(getActivity())) {
                    jumpActivity(OrderActivity.class);
                } else {
                    mainActivity.showInputPhoneDialog();
                }


                break;
            case R.id.opinion:
                if (LoginStatusUtils.isLogin(getActivity())) {
                    jumpActivity(NewOpinionActivity.class);
                } else {
                    mainActivity.showInputPhoneDialog();
                }


                break;
            case R.id.twitter:
                jumpActivity(ShareActivity.class);

//                SharedSdkUtils.beginToShared(getActivity());


                break;
            case R.id.on:
                jumpActivity(OnActivity.class);
                break;

            case R.id.userName:
                if (LoginStatusUtils.isLogin(getActivity())) {
                    if (userinfo != null) {

                        Intent intent = new Intent(getActivity(), MyInformationActivity.class);
                        intent.putExtra("userinfo", userinfo);
                        startActivity(intent);
                    } else {
                        T.showShort(mainActivity, getString(R.string.string_server_error));
                    }

                } else {
                    mainActivity.showInputPhoneDialog();
                }


                break;
            case R.id.rl_use_guide:
                Intent intent = new Intent(mainActivity, UserGuideActivity.class);
                startActivity(intent);
                viewRedCircle.setVisibility(View.GONE);
                SPUtils.put(mainActivity, HASCLICKUSERGUIDE, true);


                break;


            case R.id.iv_message:



                break;

            case R.id.rl_message:
                if (LoginStatusUtils.isLogin(getActivity())) {
                    jumpActivity(MessageActivity.class);
                } else {
                    mainActivity.showInputPhoneDialog();
                }

                break;
            case R.id.ll_coupon:
                if (LoginStatusUtils.isLogin(getActivity())) {
                     intent = new Intent(getActivity(), PickCouponingActivity.class);
                    intent.putExtra(AppContants.ISNEEDRETURNCOUPON,false);
                    startActivity(intent);
                } else {
                    mainActivity.showInputPhoneDialog();
                }

                break;

        }

    }


    private boolean networkFailedBefore = false;

    @Override
    public void netWordIsFailed() {
        super.netWordIsFailed();
        networkFailedBefore = true;
    }

    @Override
    public void netWorkIsSuccess() {
        super.netWorkIsSuccess();
        if (networkFailedBefore) {
            userID = (String) SPUtils.get(getContext(), AppContants.KEY_USERID, "");
            LogUtil.d(AppContants.TAG, "userID = " + userID);

                getuserInfo();

        }

    }

    @Override
    public void serverError(String errorMsg) {
        T.showShort(mainActivity,errorMsg);
    }
    boolean isPullRefresh = false;
    @Override
    public void showProgress() {
        if(isPullRefresh){
            return;
        }
        super.showProgress();
        if (mainActivity != null) {
            mainActivity.showProgressDialog(StringUtils.getRString(mainActivity, R.string.loadingProgress));
        }
    }

    @Override
    public void hideProgress() {
        if(isPullRefresh){
            refreshLayout.finishRefresh();
            return;
        }
        super.hideProgress();
        if (mainActivity != null) {
            mainActivity.hidProgressDialog();
        }
    }

    @Override
    public void setUserInfo(Userinfo userinfo) {
        checkJPush();

        this.userinfo = userinfo;
//        T.showShort(getActivity(), userinfo.getObject().getMsgNum());

        if(StringUtils.emptyParseInt(userinfo.getObject().getMsgNum())>0){
            viewRedCircleMessage.setVisibility(View.VISIBLE);
        }else{
            viewRedCircleMessage.setVisibility(View.GONE);
        }

        tvWalletBalance.setText(userinfo.getObject().getMoney());

//        SPUtils.put(getContext(), AppContants.KEY_USERAVATAR, userinfo.getObject().getImg());
        LogUtil.d(TAG, "setData: img" + userinfo.getObject().getImg());

        ImageViewUtils.showImage(getActivity(), userinfo.getObject().getImg(), userAvatar, getDefaultHead(), getDefaultHead());
        userName.setBackgroundColor(Color.TRANSPARENT);
        llWalletCouponPoints.setVisibility(View.VISIBLE);

        if (TextUtils.isEmpty(userinfo.getObject().getName())) {

            userName.setText(StringUtils.getSecretPhoneNum((String) SPUtils.get(getContext(), AppContants.KEY_PHONE, "")));
        } else if (userinfo.getObject().getName().equals(AppContants.NOTSETYET)) {
            userName.setText(StringUtils.getSecretPhoneNum((String) SPUtils.get(getContext(), AppContants.KEY_PHONE, "")));
            LogUtil.d(TAG, "setData: " + StringUtils.getSecretPhoneNum(userinfo.getObject().getName()));

        } else {
            userName.setText(userinfo.getObject().getName());
        }
        LogUtil.d(TAG, "setData: userinfo" + userinfo.getObject().getName());




        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                isPullRefresh = true;

                    if (LoginStatusUtils.isLogin(getActivity())) {

                        getuserInfo();
                    }



            }
        });

    }
}
