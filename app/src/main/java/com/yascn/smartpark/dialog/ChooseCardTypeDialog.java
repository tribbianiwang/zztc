package com.yascn.smartpark.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.yascn.smartpark.R;
import com.yascn.smartpark.adapter.RvCardTypeAdapter;
import com.yascn.smartpark.bean.MonthCardDetailBean;
import com.yascn.smartpark.utils.RvItemClickListener;
import com.yascn.smartpark.utils.StringUtils;

import cn.aigestudio.datepicker.views.DatePicker;

import static com.yascn.smartpark.utils.AppContants.CARDTYPEDAYS;
import static com.yascn.smartpark.utils.AppContants.CARDTYPENAME;
import static com.yascn.smartpark.utils.AppContants.MONTHCARDDETAILBEAN;

/**
 * Created by YASCN on 2017/9/8.
 */

public class ChooseCardTypeDialog extends DialogFragment {

    private DatePicker datePicker;

    private SetOnCardTypeChangeListener setOnCardTypeChangeListener;
    private RecyclerView rvCardType;
    private Activity activity;



    public interface SetOnCardTypeChangeListener {
        void setCartype(String cartype,int position);
    }

    public void setOnCardTypeChange(SetOnCardTypeChangeListener setOnCardTypeChangeListener) {
        this.setOnCardTypeChangeListener = setOnCardTypeChangeListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setTitle("选择生日");
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_choose_card_type, null, false);
        rvCardType = view.findViewById(R.id.rv_card_type);


        builder.setView(view);
        return builder.create();
    }
    private MonthCardDetailBean monthCardDetailBean;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        monthCardDetailBean = (MonthCardDetailBean) this.getArguments().getSerializable(MONTHCARDDETAILBEAN);
        activity = getActivity();
        rvCardType.setLayoutManager(new LinearLayoutManager(activity));

        RvCardTypeAdapter rvCardTypeAdapter = new RvCardTypeAdapter(activity,monthCardDetailBean);
        rvCardType.setAdapter(rvCardTypeAdapter);
        rvCardTypeAdapter.setOnItemClickListener(new RvItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                setOnCardTypeChangeListener.setCartype(monthCardDetailBean.getObject().get(position).getType(),position);
                ChooseCardTypeDialog.this.dismiss();
            }
        });

    }
}
