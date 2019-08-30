package com.yascn.smartpark.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.yascn.smartpark.R;
import com.yascn.smartpark.adapter.RechargeAdapter;
import com.yascn.smartpark.adapter.TimeoutAdapter;
import com.yascn.smartpark.bean.ReRecordList;
import com.yascn.smartpark.utils.T;
import com.yascn.smartpark.utils.Validator;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.name;

/**
 * Created by YASCN on 2017/9/14.
 */

public class TimeoutDialog extends DialogFragment {

    private RecyclerView recyclerView;
    private List<ReRecordList.ObjectBean.RListBean> rListBeen;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        rListBeen = new ArrayList<ReRecordList.ObjectBean.RListBean>();
        rListBeen = (List<ReRecordList.ObjectBean.RListBean>) getArguments().getSerializable("rListBeen");

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_timeout, null, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        TimeoutAdapter timeoutAdapter = new TimeoutAdapter(getContext(), rListBeen);
        recyclerView.setAdapter(timeoutAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        builder.setView(view);

        return builder.create();
    }
}
