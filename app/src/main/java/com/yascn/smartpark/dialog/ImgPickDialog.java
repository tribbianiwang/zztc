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

import static com.yascn.smartpark.utils.AppContants.IMGALBUM;
import static com.yascn.smartpark.utils.AppContants.IMGPHOTO;
import static com.yascn.smartpark.utils.AppContants.ISBAIDUNAVIAVAILABLE;
import static com.yascn.smartpark.utils.AppContants.ISGAODENAVIAVAILABLE;
import static com.yascn.smartpark.utils.AppContants.NAVITAPEBAIDU;
import static com.yascn.smartpark.utils.AppContants.NAVITYPEGAODE;
import static com.yascn.smartpark.utils.AppContants.NAVITYPELOCAL;

/**
 * Created by YASCN on 2017/1/10.
 */

public class ImgPickDialog extends DialogFragment implements View.OnClickListener {

    RelativeLayout rl_take_photo,rl_pick_from_album;



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
            case R.id.rl_take_photo:
                chooseImgListener.choseImg(IMGPHOTO);
//                payClickListener.zhifu(2);

                break;
            case R.id.rl_pick_from_album:
                chooseImgListener.choseImg(IMGALBUM);
//                payClickListener.zhifu(1);
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


    public interface chooseImgListener {
        void choseImg(int type);

    }

    private chooseImgListener chooseImgListener;

    public void setOnChooseImgListener(chooseImgListener chooseImgListener) {
        this.chooseImgListener = chooseImgListener;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_img_choose, null, false);
        rl_take_photo = (RelativeLayout) view.findViewById(R.id.rl_take_photo);
        rl_pick_from_album = (RelativeLayout) view.findViewById(R.id.rl_pick_from_album);
        tvCancel = (TextView) view.findViewById(R.id.tv_cancel);

        rl_take_photo.setOnClickListener(this);
        rl_pick_from_album.setOnClickListener(this);
        tvCancel.setOnClickListener(this);









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
