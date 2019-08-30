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
import android.widget.EditText;

import com.yascn.smartpark.R;
import com.yascn.smartpark.utils.T;
import com.yascn.smartpark.utils.Validator;

/**
 * Created by YASCN on 2017/9/8.
 */

public class SetPhoneDialog extends DialogFragment {

    private EditText phone;
    private SetPhoneClick setPhoneClick;

    public interface SetPhoneClick {
        void setPhoneClick(String phone);
    }

    public void setPhoneClick(SetPhoneClick setPhoneClick) {
        this.setPhoneClick = setPhoneClick;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String p = getArguments().getString("phone");
        p  = TextUtils.isEmpty(p)? "" : p;

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("设置手机号");
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_setphone, null, false);
        phone = (EditText) view.findViewById(R.id.phone);
        phone.setText(p);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (TextUtils.isEmpty(phone.getText().toString().trim())) {
                    T.showShort(getContext(), "手机号不能为空");
                } else {
                    if (Validator.isMobile(phone.getText().toString().trim())) {
                        setPhoneClick.setPhoneClick(phone.getText().toString().trim());
                    } else {
                        T.showShort(getContext(), "格式不正确");
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
