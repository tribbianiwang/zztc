package com.yascn.smartpark.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yascn.smartpark.R;

import static com.yascn.smartpark.utils.AppContants.ISBAIDUNAVIAVAILABLE;
import static com.yascn.smartpark.utils.AppContants.ISGAODENAVIAVAILABLE;
import static com.yascn.smartpark.utils.AppContants.NAVITAPEBAIDU;
import static com.yascn.smartpark.utils.AppContants.NAVITYPEGAODE;
import static com.yascn.smartpark.utils.AppContants.NAVITYPELOCAL;

/**
 * Created by YASCN on 2017/1/10.
 */

public class NaviChooseDialog extends DialogFragment implements View.OnClickListener {

    RelativeLayout rlGaodeNavi,rlBaiduNavi,rlInsideNavi;



    TextView tvCancel;
    private Window dialogWindow;
    private Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_gaode_navi:
                payClickListener.choseNavi(NAVITYPEGAODE);
//                payClickListener.zhifu(2);

                break;
            case R.id.rl_baidu_navi:
                payClickListener.choseNavi(NAVITAPEBAIDU);
//                payClickListener.zhifu(1);
                break;

            case R.id.rl_inside_navi:
                payClickListener.choseNavi(NAVITYPELOCAL);

                break;

            case R.id.tv_cancel:
                if(dialog!=null){
                    if(dialog.isShowing()){
                        dialog.dismiss();
                    }
                }

                break;
        }
    }


    public interface chooseNaviListener {
        void choseNavi(int type);

    }

    private chooseNaviListener payClickListener;

    public void setOnChooseNaviListener(chooseNaviListener payClickListener) {
        this.payClickListener = payClickListener;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_navi_choose, null, false);
        rlGaodeNavi = (RelativeLayout) view.findViewById(R.id.rl_gaode_navi);
        rlBaiduNavi = (RelativeLayout) view.findViewById(R.id.rl_baidu_navi);
        rlInsideNavi = (RelativeLayout) view.findViewById(R.id.rl_inside_navi);
        tvCancel = (TextView) view.findViewById(R.id.tv_cancel);

        rlGaodeNavi.setOnClickListener(this);
        rlBaiduNavi.setOnClickListener(this);
        rlInsideNavi.setOnClickListener(this);
        tvCancel.setOnClickListener(this);

        boolean isBaiduAvailable = getArguments().getBoolean(ISBAIDUNAVIAVAILABLE);
        boolean isGaodeAvailable = getArguments().getBoolean(ISGAODENAVIAVAILABLE);
        rlGaodeNavi.setVisibility(isGaodeAvailable?View.VISIBLE:View.GONE);
        rlBaiduNavi.setVisibility(isBaiduAvailable?View.VISIBLE:View.GONE);

//
//        String address = getArguments().getString("address");
//        String intime = getArguments().getString("intime");
//        final String jine = getArguments().getString("paymoney");







        dialog.addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);
        dialogWindow.setGravity(Gravity.BOTTOM);
        return dialog;
    }
}
