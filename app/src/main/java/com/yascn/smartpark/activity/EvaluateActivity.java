package com.yascn.smartpark.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import com.yascn.smartpark.JPushDao;
import com.yascn.smartpark.base.AndroidApplication;
import com.yascn.smartpark.R;
import com.yascn.smartpark.adapter.PhotoAdapter;
import com.yascn.smartpark.base.BaseActivity;
import com.yascn.smartpark.bean.Comment;
import com.yascn.smartpark.bean.JPush;
import com.yascn.smartpark.contract.EvaluateContract;
import com.yascn.smartpark.dialog.DefaultDialog;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.DoubleClickUtil;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.RecyclerItemClickListener;
import com.yascn.smartpark.utils.SPUtils;
import com.yascn.smartpark.utils.T;
import com.yascn.smartpark.viewmodel.EvaluateViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

import static com.yascn.smartpark.utils.AppContants.COMMINTSUCCESS;
import static com.yascn.smartpark.utils.AppContants.ORDERFORMID;

/**
 * Created by YASCN on 2017/9/16.
 */

public class EvaluateActivity extends BaseActivity implements EvaluateContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private DefaultDialog dialog;

    @BindView(R.id.room_ratingbar)
    SimpleRatingBar ratingBar;

    @BindView(R.id.content)
    EditText evaluateContent;

    @BindView(R.id.submit)
    Button submit;

    private ArrayList<String> imgs = new ArrayList<>();
    private String userID, orderFormId;

    @BindView(R.id.tv_input_nums)
    TextView tvInputNums;
    private PhotoAdapter photoAdapter;
    private static final String TAG = "EvaluateActivity";
    private EvaluateViewModel evaluateViewModel;


    @Override
    public int[] hideSoftByEditViewIds() {
        int[] ids = {R.id.content};
        return ids;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        ButterKnife.bind(this);

        initTitle(getResources().getString(R.string.evaluate));
        init();
//
        orderFormId = getIntent().getStringExtra(ORDERFORMID);
        userID = (String) SPUtils.get(this, AppContants.KEY_USERID, "");
        LogUtil.d(TAG, "onCreate: "+orderFormId);

        //1
        evaluateViewModel = ViewModelProviders.of(this).get(EvaluateViewModel.class);

        //2
        getLifecycle().addObserver(evaluateViewModel);

        //3
        android.arch.lifecycle.Observer<Comment> commentObserver = new android.arch.lifecycle.Observer<Comment>() {
            @Override
            public void onChanged(@Nullable Comment comment) {

                evaluateResult(comment);
            }
        };

        //4
        evaluateViewModel.getMutableLiveDataEntry().observe(this,commentObserver);
        evaluateViewModel.getQueryStatus().observe(this,statusObserver);


        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        photoAdapter = new PhotoAdapter(this, imgs);
        recyclerView.setAdapter(photoAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (photoAdapter.getItemViewType(position) == PhotoAdapter.TYPE_ADD) {
                            PhotoPicker.builder()
                                    .setPhotoCount(AppContants.MAXUPLOADIMGNUMBER)
                                    .setShowCamera(true)
                                    .setPreviewEnabled(false)
                                    .setSelected(imgs)
                                    .start(EvaluateActivity.this);
                        } else {
                            PhotoPreview.builder()
                                    .setPhotos(imgs)
                                    .setCurrentItem(position)
                                    .start(EvaluateActivity.this);
                        }
                    }
                }));
    }

    public void init() {


        evaluateContent.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 输入的内容变化的监听

                if (s.length() == 140) {

                    T.showShort(EvaluateActivity.this, "评论最多140字");
                }
                tvInputNums.setText(s.length() + "");

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // 输入前的监听


            }

            @Override
            public void afterTextChanged(Editable s) {
                // 输入后的监听
                LogUtil.e("输入结束执行该方法", "输入结束");

            }
        });
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





    public String getStarNum() {
        return (int) ratingBar.getRating()+"";
    }


    public String getEvaluateContent() {
        LogUtil.d(AppContants.TAG, "内容：" + evaluateContent.getText().toString().trim());
        return evaluateContent.getText().toString().trim();
    }


    public ArrayList<String> getImags() {
        return imgs;
    }



    public void finishActivity() {
        finish();
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK &&
                (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {

            List<String> photos = null;
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }
            imgs.clear();

            if (photos != null) {

                imgs.addAll(photos);
            }
            photoAdapter.notifyDataSetChanged();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        JPushDao jPushDao = AndroidApplication.getInstances().getDaoSession().getJPushDao();
        List<JPush> jPushs = jPushDao.loadAll();
        for (int i = 0; i < jPushs.size(); i++) {
            if (jPushs.get(i).getPARKING_ID().equals(orderFormId)) {
                jPushDao.delete(jPushs.get(i));
            }
        }

        Intent intent = new Intent();
        intent.setAction(AppContants.REFLESHORDER);
        sendBroadcast(intent);
    }

    @OnClick(R.id.submit)
    public void onViewClicked() {

                if (DoubleClickUtil.isFastDoubleClick()) {
                    return;
                }
                if(!TextUtils.isEmpty(evaluateContent.getText().toString())){
                    if(evaluateViewModel!=null){
                        evaluateViewModel.sumbitEvaluate(userID, orderFormId,getStarNum(),getEvaluateContent(),getImags());
                    }

                }else{
                    T.showShort(EvaluateActivity.this,"请填写评论内容");
                }


    }

    @Override
    public void evaluateResult(Comment comment) {
        if(COMMINTSUCCESS.equals(comment.getObject().getFlag())){
            T.showShort(this,getString(R.string.string_comment_sucess));
            finish();
        }


    }
}
