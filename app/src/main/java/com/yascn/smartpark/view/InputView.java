package com.yascn.smartpark.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;


import com.yascn.smartpark.R;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.StringUtils;

/**
 * Created by YASCN on 2018/3/20.
 */

public class InputView extends android.support.v7.widget.AppCompatEditText{

    int strockWidth =4;//画线宽度
    int strockRadius = 10;//圆角半径
    int textNums = 7;//数量
    int textSize = 18;
    int bolderColor = getResources().getColor(R.color.primary);
    int contentMargin = 20;
    int bgColor;

    public InputView(Context context) {
        super(context);
    }


    public InputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initParams();
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.InputView);//TypedArray属性对象
        bgColor = ta.getColor(R.styleable.InputView_bg_color, getResources().getColor(R.color.white));//获取属性对象中对应的属性值
        LogUtil.d(TAG, "InputView: "+bgColor);
        ta.recycle();
    }

    private void initParams() {



    }

    public InputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
//        int height = getHeight();


        //画出边框
        Paint boderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        boderPaint.setStrokeWidth(strockWidth);


        int rectFWidth = (width-(contentMargin*(textNums-1)))/(textNums);


       int height = rectFWidth;


//        画出内容遮挡
        boderPaint.setStyle(Paint.Style.FILL);
        boderPaint.setColor(bgColor);
        RectF contentRF = new RectF(0,0,width,height+10);
        canvas.drawRect(contentRF,boderPaint);




        boderPaint.setColor(bolderColor);
        boderPaint.setStyle(Paint.Style.STROKE);

        for(int i=0;i<textNums;i++){

            LogUtil.d(TAG, "onDraw: left:"+(i-1)*(contentMargin+rectFWidth));
            canvas.drawRect((i)*(contentMargin+rectFWidth),0,(i)*contentMargin+(i+1)*rectFWidth,height,boderPaint);
        }

        boderPaint.setColor(bgColor);
        boderPaint.setStyle(Paint.Style.FILL);

        for(int i=0;i<textNums;i++){

            LogUtil.d(TAG, "onDraw: left:"+(i-1)*(contentMargin+rectFWidth));
            canvas.drawRect((i)*(contentMargin+rectFWidth)+strockWidth,strockWidth,(i)*contentMargin+(i+1)*rectFWidth-strockWidth,height,boderPaint);
        }







        //画出字体
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(dp2px(getContext(),textSize));
        textPaint.setColor(Color.BLACK);
        textPaint.setStyle(Paint.Style.STROKE);


        for(int i=0;i<textContexts.length();i++){
            int textStartX = width*i/textNums;
            int halfBoderWidth = width/(2*textNums);
            String textContent = String.valueOf(textContexts.charAt(i));
            int textWidth = (int) textPaint.measureText(textContent);


            canvas.drawText(textContent,textStartX+halfBoderWidth-(textWidth/2),(height- (textPaint.descent() - textPaint.ascent())) / 2 - textPaint.ascent(),textPaint);

        }


    }


    private static final String TAG = "InputView";

    CharSequence textContexts;
    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        this.textContexts =text;
        postInvalidate();
        if(text.length()!=0){

            LogUtil.d(TAG, "onTextChanged:"+text+"start:"+start+"lengthBeofore:"+lengthBefore+"lengthAfter:"+lengthAfter);
        }

    }

    public void setIsNewEnergy(boolean isNewEnergy){
        textNums = isNewEnergy? 8:7;
        postInvalidate();

    }

    public int getLawLicenseNums(){
        return textNums;
    }


    /**
     * 根据手机的分辨率from dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


}
