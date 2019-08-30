package com.yascn.smartpark.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class MoveTextView extends TextView {
    public MoveTextView(Context context) {
        super(context);
    }

    public MoveTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MoveTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //重写isFocused方法，让其一直返回true
    public boolean isFocused() {
        return true;
    }
}