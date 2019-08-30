package com.yascn.smartpark.utils;

/**
 * Created by YASCN on 2018/3/20.
 */

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


import com.yascn.smartpark.R;

/**
 * Created by kevingo 2015/9/22.
 */
public class LicenseKeyboardUtil {
    private Context ctx;
    private KeyboardView keyboardView;
    private Keyboard k1;// 省份简称键盘
    private Keyboard k2;// 数字字母键盘
    private String provinceShort[];
    private String letterAndDigit[];
    private EditText edit;
    private int currentEditText = 0;//默认当前光标在第一个EditText
    public LicenseKeyboardUtil(Context ctx, EditText edits) {
        this.ctx = ctx;
        this.edit = edits;
        k1 = new Keyboard(ctx, R.xml.province_short_keyboard);
        k2 = new Keyboard(ctx, R.xml.lettersanddigit_keyboard);
        keyboardView = (KeyboardView) ((Activity)ctx).findViewById(R.id.keyboard_view);
        keyboardView.setKeyboard(k2);
        keyboardView.setEnabled(true);
        //设置为true时,当按下一个按键时会有一个popup来显示<key>元素设置的android:popupCharacters=""
        keyboardView.setPreviewEnabled(true);

        //设置键盘按键监听器
        keyboardView.setOnKeyboardActionListener(listener);
        provinceShort = new String[]{"京", "津", "冀", "鲁", "晋", "蒙", "辽", "吉", "黑"
                , "沪", "苏", "浙", "皖", "闽", "赣", "豫", "鄂", "湘"
                , "粤", "桂", "渝", "川", "贵", "云", "藏", "陕", "甘"
                , "青", "琼", "新", "宁"};

        letterAndDigit = new String[]{"0","1", "2", "3", "4", "5", "6", "7", "8", "9"
                , "Q", "W", "E", "R", "T", "Y", "U",  "P","港","澳"
                , "A", "S", "D", "F", "G", "H", "J", "K", "L"
                , "Z", "X", "C", "V", "B", "N", "M"};
//        keyboardView.getKeyboard().getKeys().get(0).onReleased(false);
//        k1.getKeys().get(1).onReleased(false);
    }

    private OnKeyboardActionListener listener = new OnKeyboardActionListener() {
        @Override
        public void swipeUp() {
        }

        @Override
        public void swipeRight() {
        }
        @Override
        public void swipeLeft() {
        }
        @Override
        public void swipeDown() {
        }
        @Override
        public void onText(CharSequence text) {
        }
        @Override
        public void onRelease(int primaryCode) {
        }

        @Override
        public void onPress(int primaryCode) {
        }


        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
//            LogUtil.d("primaryCode:%d",primaryCode);

            if(primaryCode == 112){ //xml中定义的删除键值为112

                edit.setText(edit.getText().delete(edit.getText().length()-1,edit.getText().length()));

                if(getCurrentIndex()==0){

                    keyboardView.setKeyboard(k1);
                }
            }else { //其它字符按键
                if(getCurrentIndex()==0){
                    keyboardView.setKeyboard(k1);
                    edit.setText(provinceShort[primaryCode]);
                    keyboardView.setKeyboard(k2);

                }else {
//                    第二位必须大写字母
                    LogUtil.d(TAG, "onKey: s"+getCurrentIndex()+"textnumber:"+textNums);


                    if(getCurrentIndex()==1&&!letterAndDigit[primaryCode].matches("[A-Z]{1}")){

                       return;
                    }else if(getCurrentIndex()>=textNums){

                        edit.setText(edit.getText().delete(edit.getText().length()-1,edit.getText().length()));
                        edit.setText(edit.getText().append(letterAndDigit[primaryCode]));
                    }else{

                        edit.setText(edit.getText().append(letterAndDigit[primaryCode]));
                    }

                }

            }
        }


    };



    private static final String TAG = "LicenseKeyboardUtil";

    private int getCurrentIndex(){
        return edit.getText().toString().trim().length();

    }

    /**
     * 显示键盘
     */
    public void showKeyboard() {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            keyboardView.setVisibility(View.VISIBLE);
        }
    }
    /**
     * 隐藏键盘
     */
    public void hideKeyboard() {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.VISIBLE) {
            keyboardView.setVisibility(View.INVISIBLE);
        }
    }

    int textNums = 7;
    boolean isNewEnergy;

    public void setIsNewEnergy(boolean isNewEnergy){
        textNums = isNewEnergy? 8:7;
        this.isNewEnergy = isNewEnergy;


    }

}