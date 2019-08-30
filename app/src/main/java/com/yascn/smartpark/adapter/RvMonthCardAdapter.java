package com.yascn.smartpark.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yascn.smartpark.R;
import com.yascn.smartpark.activity.MonthCardActivity;
import com.yascn.smartpark.bean.MonthCardListBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by YASCN on 2017/9/6.
 */


public class RvMonthCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;


    private View mHeaderView;

    private OnItemClickListener mListener;
    private MonthCardActivity monthCardActivity;
    private MonthCardListBean monthCardListBean;

    public RvMonthCardAdapter(MonthCardActivity monthCardActivity, MonthCardListBean monthCardListBean) {
        this.monthCardActivity = monthCardActivity;
        this.monthCardListBean = monthCardListBean;

    }

    public void setOnItemClickListener(OnItemClickListener li) {
        mListener = li;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
    }


    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) return new Holder(mHeaderView);
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_month_card_item, parent, false);

        return new Holder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) return;

        final int pos = getRealPosition(viewHolder);
//        final String data = mDatas.get(pos);
        if (viewHolder instanceof Holder) {
//            ((Holder) viewHolder).text.setText(data);
            ((Holder) viewHolder).tvParkName.setText(monthCardListBean.getObject().get(pos).getName());
            ((Holder) viewHolder).tvAddress.setText(monthCardListBean.getObject().get(pos).getAddress());
//            ((Holder) viewHolder).tvParkName.setText(monthCardListBean.getObject().get(pos).getName());


            if (mListener == null) return;
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(pos);
                }
            });
        }
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? monthCardListBean.getObject().size() : monthCardListBean.getObject().size() + 1;
    }

    class Holder extends RecyclerView.ViewHolder {

        //        TextView text;
        @BindView(R.id.tv_park_name)
        TextView tvParkName;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_month_card)
        TextView tvMonthCard;
        @BindView(R.id.tv_season_card)
        TextView tvSeasonCard;
        @BindView(R.id.tv_yeard_card)
        TextView tvYeardCard;
        @BindView(R.id.tv_optional_card)
        TextView tvOptionalCard;

        public Holder(View itemView) {
            super(itemView);
            if (itemView == mHeaderView) return;
            ButterKnife.bind(this, itemView);
//            text = (TextView) itemView.findViewById(R.id.text);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
