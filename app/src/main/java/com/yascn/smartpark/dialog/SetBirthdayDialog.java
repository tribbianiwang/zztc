package com.yascn.smartpark.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.yascn.smartpark.R;
import com.yascn.smartpark.utils.DateUtils;

import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;

/**
 * Created by YASCN on 2017/9/8.
 */

public class SetBirthdayDialog extends DialogFragment {

    private DatePicker datePicker;

    private  SetBirthdayClick setBirthdayClick;

    public interface SetBirthdayClick {
        void setBirthdayClick(String date);
    }

    public void setBirthdayClick(SetBirthdayClick setBirthdayClick) {
        this.setBirthdayClick = setBirthdayClick;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setTitle("选择生日");
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_setbirthday, null, false);
        datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        datePicker.setDate(DateUtils.getNowYear(), DateUtils.getNowMonth());
        datePicker.setMode(DPMode.SINGLE);





        datePicker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(String date) {
                setBirthdayClick.setBirthdayClick(date);
                dismiss();
            }
        });
        builder.setView(view);
        return builder.create();
    }


}
