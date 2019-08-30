package com.yascn.smartpark.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by YASCN on 2017/9/9.
 */

public class DeleteCarNumberDialog extends DialogFragment{

    private DeleteNumberClick deleteNumberClick;

    public interface DeleteNumberClick{
        void onDeleteNumberClick();
    }

    public void setOnDeleteNumberClick(DeleteNumberClick deleteNumberClick) {
        this.deleteNumberClick = deleteNumberClick;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("是否删除车牌");
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteNumberClick.onDeleteNumberClick();
            }
        });
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }
}
