package com.yascn.smartpark.adapter;

import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;

import com.yascn.smartpark.R;
import com.yascn.smartpark.activity.CardCertificationActivity;
import com.yascn.smartpark.activity.LincenseCertificationActivity;
import com.yascn.smartpark.activity.MyCarActivity;
import com.yascn.smartpark.bean.CarNumber;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.StringUtils;
import com.yascn.smartpark.utils.T;

import java.util.List;

import static com.yascn.smartpark.utils.AppContants.CARLICENSE;
import static com.yascn.smartpark.utils.AppContants.CARLICENSEID;
import static com.yascn.smartpark.utils.AppContants.LICENSECERTIFIED;
import static com.yascn.smartpark.utils.AppContants.LICENSENOTCERTIFIED;
import static com.yascn.smartpark.utils.AppContants.LICENSENOTMATCH;

/**
 * Created by YASCN on 2017/8/29.
 */

public class CarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CarNumber> carNumbers;
    private MyCarActivity context;

    private OnClickListener onClickListener;

    public interface OnClickListener {
        void onDefaultClick(Boolean isChecked, int position);

        void onAddClick();

        void onUnbindClick(int position);

        void onAutoPayClick(String carNumberID, String cate);
    }

    public void setOnDefaultClick(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public CarAdapter(MyCarActivity context, List<CarNumber> carNumbers) {
        this.context = context;
        this.carNumbers = carNumbers;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == AppContants.ITEM_CAR_CONTENT) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.rv_item_carmanager, parent, false);
            ContentViewHolder contentViewHolder = new ContentViewHolder(view);
            return contentViewHolder;
        } else {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.item_car_footer, parent, false);
            FooterViewHolder footerViewHolder = new FooterViewHolder(view);
            return footerViewHolder;
        }
    }

    public void setCertificationStatus(TextView tvCertification, boolean hasCertified) {
        tvCertification.setTextColor(hasCertified?StringUtils.getRColor(context, R.color.design_main_blue):StringUtils.getRColor(context, R.color.white));
        tvCertification.setBackgroundResource(hasCertified?R.drawable.rounded_white_cornor:R.drawable.rounded_gray_white_stroke);


        tvCertification.setText(hasCertified?StringUtils.getRString(context,R.string.has_certification):StringUtils.getRString(context,R.string.not_certification));

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof FooterViewHolder) {

            if (carNumbers.size() >= 4) {
                ((FooterViewHolder) holder).addCar.setVisibility(View.INVISIBLE);
                ((FooterViewHolder) holder).threeCar.setVisibility(View.VISIBLE);
            } else {
                ((FooterViewHolder) holder).addCar.setVisibility(View.VISIBLE);
                ((FooterViewHolder) holder).threeCar.setVisibility(View.INVISIBLE);
            }

            ((FooterViewHolder) holder).addCar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onAddClick();
                }
            });
        }

        if (holder instanceof ContentViewHolder) {


            final String isLicenseAuth = carNumbers.get(position).getNumberListBean().getIS_AUTH();
//            if(LICENSENOTCERTIFIED.equals(isLicenseAuth)|| LICENSENOTMATCH.equals(isLicenseAuth)){
//                setCertificationStatus(((ContentViewHolder) holder).defaultNumber,false);
//            }else if(LICENSECERTIFIED.equals(isLicenseAuth)){
//                setCertificationStatus(((ContentViewHolder) holder).defaultNumber,true);


//            }




            ((ContentViewHolder) holder).carNumber.setText(carNumbers.get(position).getNumberListBean().getNUMBER());

            if (carNumbers.get(position).isSetting()) {

                ((ContentViewHolder) holder).checkBox.setBackgroundResource(R.drawable.icon_car_selected_selected);
//                ((ContentViewHolder) holder).defaultNumber.setVisibility(View.INVISIBLE);
                ((ContentViewHolder) holder).autoPay.setVisibility(View.INVISIBLE);
            } else {
                ((ContentViewHolder) holder).checkBox.setBackgroundResource(R.drawable.icon_car_selected_defult);;
//                ((ContentViewHolder) holder).defaultNumber.setVisibility(View.VISIBLE);
                ((ContentViewHolder) holder).autoPay.setVisibility(View.VISIBLE);
            }

            if (carNumbers.get(position).getNumberListBean().getIS_DEFAULT().equals("1")) {
                ((ContentViewHolder) holder).defaultNumber.setText(R.string.defaultcarnumber);
                ((ContentViewHolder) holder).checkBox.setBackgroundResource(R.drawable.icon_car_selected_selected);;
            } else {
                ((ContentViewHolder) holder).defaultNumber.setText(R.string.setdefult_carnumber);
                ((ContentViewHolder) holder).checkBox.setBackgroundResource(R.drawable.icon_car_selected_defult);
            }

            if (carNumbers.get(position).getNumberListBean().getAUTOPAY().equals("1")) {
                ((ContentViewHolder) holder).autoPay.setChecked(true);
            } else {
                ((ContentViewHolder) holder).autoPay.setChecked(false);
            }

            ((ContentViewHolder) holder).checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onDefaultClick(((ContentViewHolder) holder).checkBox.isChecked(), position);
                }
            });

            ((ContentViewHolder) holder).defaultNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onDefaultClick(((ContentViewHolder) holder).checkBox.isChecked(), position);
                }
            });

            ((ContentViewHolder) holder).unbind.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onUnbindClick(position);
                }
            });

            ((ContentViewHolder) holder).autoPay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    LogUtil.d(TAG, "onCheckedChanged: " + isChecked);

                    if (isChecked) {
                        onClickListener.onAutoPayClick(carNumbers.get(position).getNumberListBean().getLICENSE_PLATE_ID(), "1");
                    } else {
                        onClickListener.onAutoPayClick(carNumbers.get(position).getNumberListBean().getLICENSE_PLATE_ID(), "0");
                    }



                }
            });

            ((ContentViewHolder) holder).rlCertification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(LICENSECERTIFIED.equals(isLicenseAuth)){
                        T.showShort(context,StringUtils.getRString(context,R.string.licensecertified));

                    }else{
                        Intent intent = new Intent(context, LincenseCertificationActivity.class);
                        intent.putExtra(CARLICENSE,carNumbers.get(position).getNumberListBean().getNUMBER());
                        intent.putExtra(CARLICENSEID,carNumbers.get(position).getNumberListBean().getLICENSE_PLATE_ID());
                        context.startActivity(intent);
                    }


                }
            });



            ((ContentViewHolder)holder).btDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    T.showShort(view.getContext(),"删除");
                    onClickListener.onUnbindClick(position);
                }
            });
           if(carNumbers.get(position).getMcardList()!=null){
               if(carNumbers.get(position).getMcardList().size()!=0){
                   ((ContentViewHolder)holder).rlMonthCard.setVisibility(View.VISIBLE);

                   ((ContentViewHolder)holder).rvMonthCard.setAdapter(new RvMonthCardCarManagerAdapter(context,carNumbers.get(position).getMcardList(),carNumbers.get(position).getNumberListBean().getNUMBER()));
               }else{
                   ((ContentViewHolder)holder).rlMonthCard.setVisibility(View.GONE);
               }

           }else{
               ((ContentViewHolder)holder).rlMonthCard.setVisibility(View.GONE);
           }

            ((ContentViewHolder)holder).rvMonthCard.setLayoutManager(new LinearLayoutManager(context));
            ((ContentViewHolder)holder).rlMonthCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(View.VISIBLE==((ContentViewHolder)holder).rvMonthCard.getVisibility()){
                        ((ContentViewHolder)holder).rvMonthCard.setVisibility(View.GONE);
                        ((ContentViewHolder)holder).ivMonthcardArrow.setImageResource(R.drawable.icon_arrow_down_gray);
                    }else{
                        ((ContentViewHolder)holder).rvMonthCard.setVisibility(View.VISIBLE);
                        ((ContentViewHolder)holder).ivMonthcardArrow.setImageResource(R.drawable.icon_arrow_up_gray);
                    }

                }
            });

        }
    }

    private static final String TAG = "CarAdapter";

    @Override
    public int getItemCount() {
        return carNumbers.size();
    }

    @Override
    public int getItemViewType(int position) {
        return carNumbers.get(position).getViewType();
    }

    class ContentViewHolder extends RecyclerView.ViewHolder {

        private TextView carNumber;
        private CheckBox checkBox;
        private TextView defaultNumber;

        private RelativeLayout unbind,rlCertification,rlMonthCard;
        private SwitchButton autoPay;
        private LinearLayout llDefaultNumber;
        private RecyclerView rvMonthCard;
        private ImageView ivMonthcardArrow;

        private Button btDelete;

        public ContentViewHolder(View itemView) {
            super(itemView);

            rlCertification = (RelativeLayout) itemView.findViewById(R.id.rl_certification);
            carNumber = (TextView) itemView.findViewById(R.id.carNumber);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
            defaultNumber = (TextView) itemView.findViewById(R.id.defaultNumber);
            unbind = (RelativeLayout) itemView.findViewById(R.id.unbind);
            autoPay = (SwitchButton) itemView.findViewById(R.id.autoPay);
            llDefaultNumber = (LinearLayout) itemView.findViewById(R.id.ll_default_number);
            btDelete = itemView.findViewById(R.id.bt_delete);
            rlMonthCard = itemView.findViewById(R.id.rl_month_card);
            rvMonthCard = itemView.findViewById(R.id.rv_month_card);
            ivMonthcardArrow = itemView.findViewById(R.id.iv_monthcard_arrow);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout addCar, threeCar;

        public FooterViewHolder(View itemView) {
            super(itemView);

            addCar = (LinearLayout) itemView.findViewById(R.id.addCar);
            threeCar = (LinearLayout) itemView.findViewById(R.id.threeCar);
        }
    }
}
