package com.yascn.smartpark.viewmodel;

import android.content.Context;

import com.yascn.smartpark.base.BaseModel;
import com.yascn.smartpark.bean.Comment;
import com.yascn.smartpark.model.EvaluateModel;

import java.util.ArrayList;

public class EvaluateViewModel extends BaseViewModel<Comment> implements BaseModel.DataResultListener<Comment> {
    private EvaluateModel evaluateModel;

    public void sumbitEvaluate(String userid, String orderFormId, String star, String content, ArrayList<String> imgs) {
        if(evaluateModel==null){
            evaluateModel = new EvaluateModel(this);
            baseModel = evaluateModel;
        }

        evaluateModel.sumbitEvaluate(userid,orderFormId,star,content,imgs);
    }

}
