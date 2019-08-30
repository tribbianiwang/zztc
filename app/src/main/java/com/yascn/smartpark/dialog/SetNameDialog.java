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

public class SetNameDialog extends DialogFragment {

    private EditText name;
    private SetNameClick setNameClick;

    public interface SetNameClick {
        void setNameClick(String name);
    }

    public void setNameClick(SetNameClick setNameClick) {
        this.setNameClick = setNameClick;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String nickname = getArguments().getString("name");
        nickname  = TextUtils.isEmpty(nickname)? "" : nickname;

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("设置昵称");
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_setname, null, false);
        name = (EditText) view.findViewById(R.id.nickname);
        name.setText(nickname);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (TextUtils.isEmpty(name.getText().toString().trim())) {
                    T.showShort(getContext(), "昵称不能为空");
                } else if(name.getText().toString().trim().length()>6){
//                    if (Validator.isUserName(name.getText().toString().trim())) {

//                    } else {
//                        T.showShort(getContext(), "格式不正确");
//                    }
                    T.showShort(getContext(), "昵称有点长哦");
                }else{
                    setNameClick.setNameClick(name.getText().toString().trim());
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
