package com.yascn.smartpark.utils;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by YASCN on 2018/8/3.
 */

public class ViewUtils {
    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

}
