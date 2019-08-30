package com.yascn.smartpark.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;


import com.yascn.smartpark.R;
import com.yascn.smartpark.activity.BigImgsActivity;
import com.yascn.smartpark.bean.ParkComment;
import com.yascn.smartpark.utils.AppContants;
import com.yascn.smartpark.utils.DensityUtils;
import com.yascn.smartpark.utils.ImageViewUtils;
import com.yascn.smartpark.utils.LogUtil;
import com.yascn.smartpark.utils.RvItemClickListener;
import com.yascn.smartpark.utils.RvItemLongClickListener;
import com.yascn.smartpark.utils.ScreenUtils;
import com.yascn.smartpark.utils.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by YASCN on 2017/9/6.
 */

public class XrvCommentAdapter extends RecyclerView.Adapter<XrvCommentAdapter.ViewHolder> {

    private static final String TAG = "XrvCommentAdapter";


    private Activity mActivity;


    private RvItemClickListener mItemClickListener;
    private RvItemLongClickListener mItemLongListener;
    private List<ParkComment.ObjectBean> totalComments;

    public XrvCommentAdapter(Activity activity, List<ParkComment.ObjectBean> totalComments) {

        LogUtil.d(TAG, "XrvCommentAdapter: ");
        this.mActivity = activity;
        this.totalComments = totalComments;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.xrv_comment_item, parent, false);
        return new ViewHolder(view, mItemClickListener, mItemLongListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ParkComment.ObjectBean parkObjectBean = totalComments.get(position);
        final List<ParkComment.ObjectBean.Img> imgs = parkObjectBean.getImgs();

//        holder.tv_author.setText(userName);
//        holder.tvCarType.setText("A"+position);
//        LogUtil.d(TAG, "onBindViewHolder: "+StringUtils.emptyParseInt(parkObjectBean.getSTAR())+holder.rb.getMax());
        holder.rb.setMax(5);
        if(StringUtils.isNumeric(parkObjectBean.getSTAR())){
            holder.rb.setProgress(StringUtils.emptyParseInt(parkObjectBean.getSTAR()));
        }



        holder.tvUserName.setText(StringUtils.getHiddenPhone(parkObjectBean.getUSERNAME()));
        ImageViewUtils.showImage(mActivity,parkObjectBean.getU_IMG(),holder.civHeadIcon,R.drawable.icon_plach_hoder_landscape,R.drawable.icon_laod_img_error);
        holder.tvComment.setText(parkObjectBean.getCONTENT());
        holder.rvImage.setLayoutManager(new GridLayoutManager(mActivity, 3, LinearLayoutManager.VERTICAL, false));

        ViewGroup.LayoutParams layoutParams = holder.rvImage.getLayoutParams();
        //计算行数
        int lineNumber = (imgs.size()) % 3 == 0 ? imgs.size() / 3 : imgs.size() / 3 + 1;

        LogUtil.d(TAG, "onBindViewHolder: " + lineNumber);
        //计算高度=行数＊每行的高度 ＋(行数－1)＊10dp的margin ＋ 10dp（为了居中）
        //因为每行显示3个条目，为了保持正方形，那么高度应该是也是宽度/3
        //高度的计算需要自己好好理解，否则会产生嵌套recyclerView可以滑动的现象
        layoutParams.height = lineNumber * (ScreenUtils.getScreenWidth(mActivity) / 3) - (lineNumber) * 2 * DensityUtils.dp2px(mActivity, 5);

        holder.rvImage.setLayoutParams(layoutParams);

        RvCommentImageAdapter rvCommentImageAdapter = new RvCommentImageAdapter(mActivity, imgs);
        rvCommentImageAdapter.setOnItemClickListener(new RvItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(mActivity, BigImgsActivity.class);
                intent.putExtra(AppContants.COMMMENTIMGS,parkObjectBean);
                intent.putExtra(AppContants.COMMENTIMGINDEX,position);
                mActivity.startActivity(intent);
            }
        });
        holder.rvImage.setAdapter(rvCommentImageAdapter);


    }

    @Override
    public int getItemCount() {
        return totalComments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {



        @BindView(R.id.civ_head_icon)
        CircleImageView civHeadIcon;
        @BindView(R.id.tv_user_name)
        TextView tvUserName;
        @BindView(R.id.rb)
        RatingBar rb;
        @BindView(R.id.tv_comment)
        TextView tvComment;
        @BindView(R.id.rv_image)
        RecyclerView rvImage;

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
