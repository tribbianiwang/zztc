package com.yascn.smartpark.viewmodel;

import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.OpinionBean;
import com.yascn.smartpark.model.SendOpinionModel;


public class OpinionViewModel extends BaseViewModel<OpinionBean> implements  BaseModel.DataResultListener<OpinionBean> {

    private SendOpinionModel sendOpinionModel;



    public void startSendOpinion(String userId,String type,String content,String img1,String img2,String img3){
        if(sendOpinionModel==null){
            sendOpinionModel = new SendOpinionModel(this);
            baseModel = sendOpinionModel;
        }
        sendOpinionModel.sendOpinion(userId,type,content,img1,img2,img3);

    }

    




}
