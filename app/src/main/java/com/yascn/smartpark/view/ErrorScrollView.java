package com.yascn.smartpark.view;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by YASCN on 2018/4/28.
 */

public class ErrorScrollView extends ScrollView{
    public ErrorScrollView(Context context) {
        super(context);
    }

    public ErrorScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ErrorScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        try {
            super.onRestoreInstanceState(state);
        }catch (Exception e) {}
        state=null;
    }


}
