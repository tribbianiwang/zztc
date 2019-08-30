package com.yascn.smartpark.contract;

import android.content.Context;

import com.yascn.smartpark.bean.Comment;

import java.util.ArrayList;

/**
 * Created by YASCN on 2018/7/18.
 */

public interface EvaluateContract {
    interface Model extends BaseNormalContract.Model{
        public void sumbitEvaluate(EvaluateContract.Presenter presenter,Context context,
                                   String userid, String orderFormId, String star, String content, ArrayList<String> imgs);

    }

    interface View extends BaseNormalContract.View{
        public void evaluateResult(Comment comment);
    }

    interface Presenter extends BaseNormalContract.Presenter{
        public void evaluateResult(Comment comment);
        public void sumbitEvaluate(Context context,
                                   String userid, String orderFormId, String star, String content, ArrayList<String> imgs);

    }
}
