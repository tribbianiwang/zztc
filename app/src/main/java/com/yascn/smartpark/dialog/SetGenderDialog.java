package com.yascn.smartpark.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;

import com.yascn.smartpark.R;
import com.yascn.smartpark.utils.T;

/**
 * Created by YASCN on 2017/9/8.
 */

public class SetGenderDialog extends DialogFragment {

    private RadioButton man, woman;
    private SetGenderClick setGenderClick;

    public interface SetGenderClick {
        void setGenderClick(String gender);
    }

    public void setGenderClick(SetGenderClick setGenderClick) {
        this.setGenderClick = setGenderClick;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String g = getArguments().getString("gender");
        g  = TextUtils.isEmpty(g)? "n" : g;

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("设置性别");
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_setgender, null, false);
        man = (RadioButton) view.findViewById(R.id.man);
        woman = (RadioButton) view.findViewById(R.id.woman);

        if (g.equals("n")) {
            man.setChecked(true);
        } else if (g.equals("w")) {
            woman.setChecked(true);
        }

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!man.isChecked() && !woman.isChecked()) {
                    T.showShort(getContext(), "请选择性别");
                } else {
                    if (man.isChecked()) {
                        setGenderClick.setGenderClick("n");
                    }

                    if (woman.isChecked()) {
                        setGenderClick.setGenderClick("w");
                    }
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setView(view);
        return builder.create();
    }
}
