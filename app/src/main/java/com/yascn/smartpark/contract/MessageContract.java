package com.yascn.smartpark.contract;

import android.content.Context;

import com.yascn.smartpark.bean.DeleteMessageBean;
import com.yascn.smartpark.bean.MessageBean;
import com.yascn.smartpark.bean.OrderList;

/**
 * Created by YASCN on 2018/7/6.
 */

public interface MessageContract {
    public interface View extends BaseNormalContract.View{
        public void setMessageBean(MessageBean messageBean);
    }

    public interface Presenter extends BaseNormalContract.Presenter{
        public void getMessageBean(String userId,int pageNumber);
        public void setMessageBean(MessageBean messageBean);
        public void setDeleteMessageBean(DeleteMessageBean deleteMessageBean);
        public void reloadData();
        public void queryList();


    }

    public interface Model{
        public void getMessageBean(MessageContract.Presenter presenter,String userId,int pageNumber);
        public void getDeleteMessageBean(MessageContract.Presenter presenter,String msgId,String flag);
        public void onDestory();
    }
}
