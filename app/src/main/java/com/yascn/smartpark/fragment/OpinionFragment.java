package com.yascn.smartpark.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yascn.smartpark.R;
import com.yascn.smartpark.activity.NewOpinionActivity;
import com.yascn.smartpark.base.BaseFragment;
import com.yascn.smartpark.utils.AppContants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


import static com.yascn.smartpark.utils.AppContants.IMGSELECTBROADCAST;
import static com.yascn.smartpark.utils.AppContants.IMGSELECTDATA;
import static com.yascn.smartpark.utils.AppContants.IMGSELECTREQUESTCODE;
import static com.yascn.smartpark.utils.AppContants.IMGSELECTRESULTCODE;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpinionFragment extends BaseFragment {


    private NewOpinionActivity newOpinionActivity;



    public OpinionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_opinion, container, false);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
