package com.yascn.smartpark.presenter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;


import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yascn.smartpark.R;
import com.yascn.smartpark.activity.MessageDetailActivity;
import com.yascn.smartpark.adapter.RvMessageAdapter;
import com.yascn.smartpark.bean.DeleteMessageBean;
import com.yascn.smartpark.bean.MessageBean;
import com.yascn.smartpark.contract.MessageContract;
import com.yascn.smartpark.model.MessageModel;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.RvItemClickListener;
import com.yascn.smartpark.utils.RvItemLongClickListener;
import com.yascn.smartpark.utils.T;

import java.util.ArrayList;
import java.util.List;

import static com.yascn.smartpark.utils.AppContants.MESSAGEDELETEFLAG;
import static com.yascn.smartpark.utils.AppContants.MSGHASREADED;

/**
 * Created by YASCN on 2018/7/6.
 */

public class MessagePresenter implements MessageContract.Presenter {
    private MessageContract.View messageView;
    private SmartRefreshLayout refreshLayout;
    private  RecyclerView rvMessage;
    private  LinearLayout llNodata;
    private String userID;
    private LinearLayout llError;
    private MessageContract.Model model;

    private int pageNum = DEFAULTPAGENUMBER;
    private static final int DEFAULTPAGENUMBER=   1;

    private boolean isFirstLoading = true;
    private RvMessageAdapter rvMessageAdapter;
    List<MessageBean.ObjectBean> totalMessages;
    private boolean isLoadingData = false;
    private AlertDialog deleteDialog;


    public MessagePresenter(MessageContract.View messageView, SmartRefreshLayout refreshLayout, RecyclerView rvMessage, LinearLayout llNodata, String userID, LinearLayout llError) {
        this.messageView = messageView;
        this.refreshLayout = refreshLayout;
        this.rvMessage = rvMessage;
        this.llNodata = llNodata;
        this.userID = userID;
        this.llError = llError;
        this.model = new MessageModel();
        this.llNodata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFirstLoading = true;
                rvMessageAdapter = null;
                totalMessages.clear();
                pageNum = DEFAULTPAGENUMBER;
                queryList();
            }
        });

        this.llError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFirstLoading = true;
                rvMessageAdapter = null;
                totalMessages.clear();
                pageNum = DEFAULTPAGENUMBER;
                queryList();
            }
        });
    }

    @Override
    public void serverError(String errorMsg) {
        if(isFirstLoading){
            isShowContent(llError);
        }else {
            messageView.serverError(errorMsg);
        }

        isLoadingData = false;

        messageView.hideProgress();
        messageView.serverError(errorMsg);

    }

    private static final String TAG = "MessagePresenter";
    public void isShowContent(View view) {
        LogUtil.d(TAG, "isShowContent: "+view.getId());
        llNodata.setVisibility(View.GONE);
        rvMessage.setVisibility(View.GONE);
        llError.setVisibility(View.GONE);
        view.setVisibility(View.VISIBLE);

//        llNodata.setVisibility(hasComment ? View.GONE : View.VISIBLE);
//        rvFinish.setVisibility(hasComment ? View.VISIBLE : View.GONE);
    }


    @Override
    public void getMessageBean(String userId, int pageNumber) {

        if (messageView != null) {
            if(isFirstLoading){
                messageView.showProgress();
            }


            this.model.getMessageBean(this,userId, pageNumber);
        }
    }

    @Override
    public void setMessageBean(MessageBean messageBean) {
        messageView.hideProgress();

        final List<MessageBean.ObjectBean> object = messageBean.getObject();
        LogUtil.d(TAG, "setparkCommentData: objectsize"+object.size()+isFirstLoading);
        if (object.size() == 0) {

            if (isFirstLoading) {
                isShowContent(llNodata);

            } else {
                isShowContent(rvMessage);
                T.showShort(messageView.getContext(),messageView.getContext().getString(R.string.no_more_data));
            }
        } else {
            isShowContent(rvMessage);
        }


        final List<MessageBean.ObjectBean> resultInfos = new ArrayList<>();
        for (int i = 0; i < object.size(); i++) {
            resultInfos.add(object.get(i));

        }

        if (rvMessageAdapter == null) {
            totalMessages = resultInfos;
            rvMessageAdapter = new RvMessageAdapter(messageView.getContext(), totalMessages);
            rvMessage.setAdapter(rvMessageAdapter);
        } else {
            totalMessages.addAll(resultInfos);
            rvMessageAdapter.notifyDataSetChanged();
        }

        LogUtil.d(TAG, "setOrderList: "+totalMessages.size());
        isFirstLoading = false;

        isLoadingData = false;
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                totalMessages.clear();
                rvMessageAdapter = null;
                pageNum = DEFAULTPAGENUMBER;
                queryList();
                refreshlayout.finishRefresh(2000);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (object.size() != 0) {
                    // 上拉的时候添加选项
                    pageNum++;
                    queryList();
                } else {

                    T.showShort(messageView.getContext(),messageView.getContext().getString(R.string.no_more_data));

                }

                refreshlayout.finishLoadmore(2000);
            }
        });

        rvMessageAdapter.setOnItemClickListener(new RvItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(messageView.getContext(), MessageDetailActivity.class);
                intent.putExtra(AppContants.MESSAGEBEANIDTRANS,totalMessages.get(position).getMsg_id());
                totalMessages.get(position).setFlag(MSGHASREADED);
                rvMessageAdapter.notifyItemChanged(position);
                messageView.getContext().startActivity(intent);
            }
        });

        rvMessageAdapter.setOnItemLongClickListener(new RvItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                deleteMessagePosition = position;
                showDeleteDialog();
            }
        });


    }

    private void showDeleteDialog() {
  /*
  这里使用了 android.support.v7.app.AlertDiaLogger.Builder
  可以直接在头部写 import android.support.v7.app.AlertDialog
  那么下面就可以写成 AlertDiaLogger.Builder


  */
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(messageView.getContext());
        builder.setMessage("确认删除"+totalMessages.get(deleteMessagePosition).getTitle());
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                model.getDeleteMessageBean(MessagePresenter.this,totalMessages.get(deleteMessagePosition).getMsg_id(),MESSAGEDELETEFLAG);
            }
        });
        deleteDialog = builder.show();
    }

    private int deleteMessagePosition;

    @Override
    public void setDeleteMessageBean(DeleteMessageBean deleteMessageBean) {
        reloadData();
//        rvMessageAdapter.notifyItemChanged(deleteMessagePosition);
    }

    @Override
    public void onDestory() {
        model.onDestory();
        if (deleteDialog != null) {
            deleteDialog.cancel();
            deleteDialog = null;
        }
    }



    @Override
    public void reloadData() {
        LogUtil.d(TAG, "reloadData: ");
        isFirstLoading = true;
        if(totalMessages!=null){
            if(totalMessages.size()!=0){
                totalMessages.clear();
            }
        }

        rvMessageAdapter = null;

        pageNum = 1;
        queryList();
    }

    @Override
    public void queryList() {
        if (isLoadingData) {

            return;

        }

        this.getMessageBean(userID, pageNum);
        isLoadingData = true;
    }
}
