package com.yascn.smartpark.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yascn.smartpark.R;
import com.yascn.smartpark.bean.MessageBean;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.ImageViewUtils;
import com.yascn.smartpark.utils.RvItemClickListener;
import com.yascn.smartpark.utils.RvItemLongClickListener;
import com.yascn.smartpark.utils.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by YASCN on 2017/9/6.
 */


public class RvMessageAdapter extends RecyclerView.Adapter<RvMessageAdapter.ViewHolder> {


//
//    private Activity mActivity;


    private RvItemClickListener mItemClickListener;
    private RvItemLongClickListener mItemLongListener;


    private static final String TAG = "RvCarLicenseAdapter";
    private Context context;
    private List<MessageBean.ObjectBean> totalMessages;

    public RvMessageAdapter(Context context, List<MessageBean.ObjectBean> totalMessages) {
        this.context = context;
        this.totalMessages = totalMessages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_rv_message_item, parent, false);
        return new ViewHolder(view, mItemClickListener, mItemLongListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(totalMessages.get(position).getFlag().equals(AppContants.MSGHASREADED)){
            holder.tvTime.setTextColor(StringUtils.getRColor(context,R.color.gray_lite));
            holder.tvTitle.setTextColor(StringUtils.getRColor(context,R.color.gray_lite));
        }else{
            holder.tvTime.setTextColor(StringUtils.getRColor(context,R.color.design_text_black));
            holder.tvTitle.setTextColor(StringUtils.getRColor(context,R.color.design_text_black));
        }


        holder.tvTitle.setText(totalMessages.get(position).getTitle());
        holder.tvTime.setText(totalMessages.get(position).getDate());
        ImageViewUtils.showImage(context,totalMessages.get(position).getPic(),holder.ivImg,R.drawable.icon_plach_hoder_landscape,R.drawable.icon_plach_hoder_landscape);
//        holder.tv_author.setText(userName);
//        holder.tvCarType.setText("A"+position);


    }

    @Override
    public int getItemCount() {

        return totalMessages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.iv_img)
        ImageView ivImg;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;
        private RvItemClickListener mListener;
        private RvItemLongClickListener mLongListener;


        public ViewHolder(final View itemView, RvItemClickListener listener, RvItemLongClickListener longListener) {
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