package com.yascn.smartpark.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yascn.smartpark.R;
import com.yascn.smartpark.bean.CarLicense;
import com.yascn.smartpark.utils.RvItemClickListener;
import com.yascn.smartpark.utils.RvItemLongClickListener;
import com.yascn.smartpark.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yascn.smartpark.utils.AppContants.CARLICENSENUMSLIMIT;

/**
 * Created by YASCN on 2017/9/6.
 */


public class RvCarLicenseListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    private Activity mActivity;


    private RvItemClickListener mItemClickListener;
    private RvItemLongClickListener mItemLongListener;

    List<CarLicense.ObjectBean> carLicenseList = new ArrayList<>();

    private static final int ITEMTYPECARLICENSE = 0;
    private static final int ITEMTYPECARADD = 1;
    private String parkIdTrans;


    public RvCarLicenseListAdapter(Activity activity, List<CarLicense.ObjectBean> object, String parkIdTrans) {
        this.mActivity = activity;
        this.carLicenseList = object;
        this.parkIdTrans = parkIdTrans;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEMTYPECARLICENSE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_carlicense_list_item, parent, false);
            return new CarlicenseViewHolder(view, mItemClickListener, mItemLongListener);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_carlicense_list_item_add, parent, false);
            return new AddViewHolder(view, mItemClickListener, mItemLongListener);
        }
    }

    private static final String TAG = "RvCarLicenseListAdapter";
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CarlicenseViewHolder) {
            ((CarlicenseViewHolder)holder).tvCarlicense.setText(carLicenseList.get(position).getNUMBER());
            if(carLicenseList.get(position).getMCARD_LIST()!=null){
                if(carLicenseList.get(position).getMCARD_LIST().size()!=0){
                    for(int i=0;i<carLicenseList.get(position).getMCARD_LIST().size();i++){
                        Log.d(TAG, "onBindViewHolder: "+parkIdTrans+"----"+carLicenseList.get(position).getMCARD_LIST().get(i).getPARKING_ID());
                        if(parkIdTrans.equals(carLicenseList.get(position).getMCARD_LIST().get(i).getPARKING_ID())){
                            ((CarlicenseViewHolder)holder).tvRemainDays.setVisibility(View.VISIBLE);
                            ((CarlicenseViewHolder)holder).tvMonthCardTitle.setVisibility(View.VISIBLE);
                            ((CarlicenseViewHolder)holder).tvRemainDays.setText(StringUtils.getRString(mActivity,R.string.string_remain_days)+carLicenseList.get(position).getMCARD_LIST().get(i).getSY_DAYS()+StringUtils.getRString(mActivity,R.string.string_day));
                            break;

                        }else if(i==(carLicenseList.get(position).getMCARD_LIST().size()-1)){
                            ((CarlicenseViewHolder)holder).tvRemainDays.setVisibility(View.GONE);
                            ((CarlicenseViewHolder)holder).tvMonthCardTitle.setVisibility(View.GONE);
                        }
                    }



                }else{
                    ((CarlicenseViewHolder)holder).tvRemainDays.setVisibility(View.GONE);
                    ((CarlicenseViewHolder)holder).tvMonthCardTitle.setVisibility(View.GONE);
                }
            }else{
                ((CarlicenseViewHolder)holder).tvRemainDays.setVisibility(View.GONE);
                ((CarlicenseViewHolder)holder).tvMonthCardTitle.setVisibility(View.GONE);
            }



        } else if (holder instanceof AddViewHolder) {

        }




    }

    @Override
    public int getItemCount() {
        if (carLicenseList.size() == CARLICENSENUMSLIMIT) {
            return carLicenseList.size();
        } else {
            return carLicenseList.size() + 1;
        }


    }


    @Override
    public int getItemViewType(int position) {
        if (carLicenseList.size() == (CARLICENSENUMSLIMIT-1)) {
            if(position==(CARLICENSENUMSLIMIT-1)){
                return ITEMTYPECARADD;
            }else{
                return ITEMTYPECARLICENSE;
            }

        }else{
            return ITEMTYPECARLICENSE;
        }

    }

    public class CarlicenseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        @BindView(R.id.tv_carlicense)
        TextView tvCarlicense;
        @BindView(R.id.tv_month_card_title)
        TextView tvMonthCardTitle;
        @BindView(R.id.tv_remain_days)
        TextView tvRemainDays;

        private RvItemClickListener mListener;
        private RvItemLongClickListener mLongListener;


        public CarlicenseViewHolder(final View itemView, RvItemClickListener listener, RvItemLongClickListener longListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.mListener = listener;
            this.mLongListener = longListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);


        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (mLongListener != null) {
                mLongListener.onItemLongClick(v, getAdapterPosition());
            }
            return true;
        }

    }


    public class AddViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {


        private RvItemClickListener mListener;
        private RvItemLongClickListener mLongListener;


        public AddViewHolder(final View itemView, RvItemClickListener listener, RvItemLongClickListener longListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.mListener = listener;
            this.mLongListener = longListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);


        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (mLongListener != null) {
                mLongListener.onItemLongClick(v, getAdapterPosition());
            }
            return true;
        }

    }


    public void setOnItemClickListener(RvItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public void setOnItemLongClickListener(RvItemLongClickListener listener) {
        this.mItemLongListener = listener;
    }

}