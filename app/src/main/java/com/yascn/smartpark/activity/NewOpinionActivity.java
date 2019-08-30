package com.yascn.smartpark.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.yascn.smartpark.R;
import com.yascn.smartpark.adapter.OpinionAdapter;
import com.yascn.smartpark.adapter.PhotoAdapter;
import com.yascn.smartpark.base.BaseActivity;
import com.yascn.smartpark.base.BaseFragment;
import com.yascn.smartpark.bean.OpinionBean;
import com.yascn.smartpark.fragment.OpinionFragment;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.RecyclerItemClickListener;
import com.yascn.smartpark.utils.SPUtils;
import com.yascn.smartpark.utils.T;
import com.yascn.smartpark.viewmodel.OpinionViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;


public class NewOpinionActivity extends BaseActivity{

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.index_toolbar)
    Toolbar indexToolbar;
    @BindView(R.id.tb_opinion)
    TabLayout tabLayout;
    @BindView(R.id.vp)
    ViewPager vp;
    private ArrayList<String> imgs = new ArrayList<>();

    @BindView(R.id.content)
    EditText content;
    @BindView(R.id.tv_input_nums)
    TextView tvInputNums;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.submit)
    Button submit;

    private String userID;
    private PhotoAdapter photoAdapter;
    
    private OpinionViewModel opinionViewModel;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_opinion);
        ButterKnife.bind(this);
        userID = (String) SPUtils.get(this, AppContants.KEY_USERID,"");

        initTitle(getString(R.string.string_opinion));

        initvp();
        
        opinionViewModel = ViewModelProviders.of(this).get(OpinionViewModel.class);
        getLifecycle().addObserver(opinionViewModel);
        
        final Observer<OpinionBean> opinionBeanObserver = new Observer<OpinionBean>() {
            @Override
            public void onChanged(@Nullable OpinionBean opinionBean) {
                if(opinionBean.getObject().getFlag().equals(AppContants.COMMINTSUCCESS)){
                    T.showShort(NewOpinionActivity.this,getString(R.string.commit_success));
                }
                
            }
        };
        
        opinionViewModel.getMutableLiveDataEntry().observe(this,opinionBeanObserver);
        opinionViewModel.getQueryStatus().observe(this,statusObserver);
        
        
        
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        photoAdapter = new PhotoAdapter(this, imgs);
        recyclerView.setAdapter(photoAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (photoAdapter.getItemViewType(position) == PhotoAdapter.TYPE_ADD) {
                            PhotoPicker.builder()
                                    .setPhotoCount(3)
                                    .setShowCamera(true)
                                    .setPreviewEnabled(false)
                                    .setSelected(imgs)
                                    .start(NewOpinionActivity.this);
                        } else {
                            PhotoPreview.builder()
                                    .setPhotos(imgs)
                                    .setCurrentItem(position)
                                    .start(NewOpinionActivity.this);
                        }
                    }
                }));

        content.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 输入的内容变化的监听

                if (s.length() == 140) {

                    T.showShort(NewOpinionActivity.this, "评论最多140字");
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



    ArrayList<BaseFragment> baseFragments = new ArrayList<>();

    private void initvp() {
        for (int i = 0; i < 3; i++) {
            OpinionFragment opinionFragment = new OpinionFragment();
            baseFragments.add(opinionFragment);

        }
        //set adapter to your ViewPager
        vp.setAdapter(new OpinionAdapter(getSupportFragmentManager(), baseFragments));
        tabLayout.setupWithViewPager(vp);
        vp.setOffscreenPageLimit(3);
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
    private static final String TAG = "NewOpinionActivity";
    private String img1="";
    private String img2="";
    private String img3="";
    @OnClick(R.id.submit)
    public void onViewClicked() {
        int opinionType = vp.getCurrentItem();

        if(TextUtils.isEmpty(content.getText().toString().trim())){
            T.showShort(this,getString(R.string.comment_is_empty));
        }else{
            if(imgs.size()>0&&!TextUtils.isEmpty(imgs.get(0))){
                img1 = imgs.get(0);
            }
            if(imgs.size()>1&&!TextUtils.isEmpty(imgs.get(1))){
                img2 = imgs.get(1);
            }
            if(imgs.size()>2&&!TextUtils.isEmpty(imgs.get(2))){
                img3 = imgs.get(2);
            }

            opinionViewModel.startSendOpinion(userID,opinionType+"",content.getText().toString().trim(),img1,img2,img3);
        }

    }





}
