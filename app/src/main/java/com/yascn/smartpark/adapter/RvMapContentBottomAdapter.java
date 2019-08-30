package com.yascn.smartpark.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import com.yascn.smartpark.utils.ScreenUtils;
import com.yascn.smartpark.utils.StringUtils;
import com.yascn.smartpark.utils.T;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by YASCN on 2018/5/2.
 */

public class RvMapContentBottomAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;

    public static final int TYPE_EMPTY = 2;
    public static final int TYPE_ERROR = 3;

    private static final String TAG = "RvMapContentBottomAdapt";

    private List<ParkComment.ObjectBean> totalComments;


    private ArrayList<String> mDatas = new ArrayList<>();

    private View mHeaderView;

    private OnItemClickListener mListener;
    private int itemCount;
    private int headerEmptyErrorCount;

    public void setOnItemClickListener(OnItemClickListener li) {
        mListener = li;
    }

    private Activity activity;
    public RvMapContentBottomAdapter(List<ParkComment.ObjectBean> totalComments, Activity activity) {
        this.totalComments = totalComments;
        this.activity = activity;

    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
    }


    private View errorView;
    public void setErrorView(View errorView){
        this.errorView = errorView;
        notifyItemChanged(1);
    }

    public View getErrorView(){
        return errorView;
    }


    public View emptyView;

    public void setEmptyView(View emptyView){
        this.emptyView = emptyView;
        notifyItemChanged(1);
    }

    public View getEmptyView() {
        return emptyView;
    }

    public void addDatas(ArrayList<String> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return TYPE_HEADER;
        }else if(position==1){
            if(getEmptyView()==null&&getErrorView()==null){
                return TYPE_NORMAL;
            }else if(getEmptyView()!=null){
                return TYPE_EMPTY;
            }else {
                return TYPE_ERROR;
            }

        }else{
            return TYPE_NORMAL;
        }

//        if(mHeaderView == null) return TYPE_NORMAL;
//        if(position == 0) return TYPE_HEADER;
//        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mHeaderView != null && viewType == TYPE_HEADER){
//            mHeaderView.getBackground().setAlpha(0);
            return new Holder(mHeaderView);
        }else if(emptyView!=null&&viewType==TYPE_EMPTY){
            return new Holder(emptyView);
        }else if(errorView!=null&&viewType==TYPE_ERROR){
            return new Holder(errorView);
        }else{
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.xrv_comment_item, parent, false);
            layout.setBackgroundColor(Color.WHITE);

            return new Holder(layout);
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        LogUtil.d(TAG, "onBindViewHolder: ");
        if(getItemViewType(position) == TYPE_HEADER||getItemViewType(position)==TYPE_ERROR||getItemViewType(position)==TYPE_EMPTY)
            return;

        final int realPosition = getRealPosition(holder);

        if(holder instanceof Holder) {
            LogUtil.d(TAG, "onBindViewHolder: size"+totalComments.size()+"----"+realPosition);
            final ParkComment.ObjectBean parkObjectBean = totalComments.get(realPosition);
            final List<ParkComment.ObjectBean.Img> imgs = parkObjectBean.getImgs();

             if(!TextUtils.isEmpty(parkObjectBean.getDATE())){
            ((Holder) holder).tvDate.setText(parkObjectBean.getDATE());
             }





             ((Holder) holder).tvUserName.setText(StringUtils.getHiddenPhone(parkObjectBean.getUSERNAME()));
            ImageViewUtils.showImage(activity,parkObjectBean.getU_IMG(), ((Holder) holder).civHeadIcon,R.drawable.icon_plach_hoder_landscape,R.drawable.icon_laod_img_error);
             ((Holder) holder).tvComment.setText(parkObjectBean.getCONTENT());
             ((Holder) holder).rvImage.setLayoutManager(new GridLayoutManager(activity, 3, LinearLayoutManager.VERTICAL, false));

            ViewGroup.LayoutParams layoutParams =  ((Holder) holder).rvImage.getLayoutParams();
            //计算行数
            int lineNumber = (imgs.size()) % 3 == 0 ? imgs.size() / 3 : imgs.size() / 3 + 1;


            LogUtil.d(TAG, "onBindView ((Holder) holder): " + lineNumber);
            //计算高度=行数＊每行的高度 ＋(行数－1)＊10dp的margin ＋ 10dp（为了居中）
            //因为每行显示3个条目，为了保持正方形，那么高度应该是也是宽度/3
            //高度的计算需要自己好好理解，否则会产生嵌套recyclerView可以滑动的现象
            layoutParams.height = lineNumber * (ScreenUtils.getScreenWidth(activity) / 3) - (lineNumber) * 2 * DensityUtils.dp2px(activity, 5);

             ((Holder) holder).rvImage.setLayoutParams(layoutParams);

            RvCommentImageAdapter rvCommentImageAdapter = new RvCommentImageAdapter(activity, imgs);
            rvCommentImageAdapter.setOnItemClickListener(new RvItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
//                    T.showShort(activity,realPosition+"");
                    Intent intent = new Intent(activity, BigImgsActivity.class);
                    intent.putExtra(AppContants.COMMMENTIMGS,parkObjectBean);
                    intent.putExtra(AppContants.COMMENTIMGINDEX,position);
                    activity.startActivity(intent);
                }
            });
             ((Holder) holder).rvImage.setAdapter(rvCommentImageAdapter);



            if(mListener == null) return;
             ((Holder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(realPosition);
                }
            });



        }

//        if(position==(itemCount-1)){
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            layoutParams.bottomMargin = DensityUtils.dp2px(activity,52);
//            ((Holder) holder).llRoot.setLayoutParams(layoutParams);
//        }

    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();

        LogUtil.d(TAG, "getRealPosition: "+itemCount+"----"+(itemCount == 0 ? position : position - itemCount)+"----"+position);

        return itemCount == 0 ? position : position - headerEmptyErrorCount;
    }

    @Override
    public int getItemCount() {
        headerEmptyErrorCount = 0;
        itemCount = totalComments.size();
        LogUtil.d(TAG, "getItemCount: "+totalComments.size());
        if(mHeaderView!=null){
            itemCount++;
            headerEmptyErrorCount++;
        }

        if(emptyView!=null){
            itemCount++;
            headerEmptyErrorCount++;
        }

        if(errorView!=null){
            itemCount++;
            headerEmptyErrorCount++;
        }

        LogUtil.d(TAG, "getItemCount: "+itemCount+errorView+"--"+emptyView);
        return itemCount;
    }

    class Holder extends RecyclerView.ViewHolder {

        CircleImageView civHeadIcon;

        TextView tvUserName;



        TextView tvComment;

        RecyclerView rvImage;

        LinearLayout llRoot;
        TextView tvDate;

//        TextView text;

        public Holder(View itemView) {
            super(itemView);
            if(itemView == mHeaderView) return;
            civHeadIcon = (CircleImageView) itemView.findViewById(R.id.civ_head_icon);
            tvUserName = (TextView) itemView.findViewById(R.id.tv_user_name);

            tvComment = (TextView) itemView.findViewById(R.id.tv_comment);
            rvImage = (RecyclerView) itemView.findViewById(R.id.rv_image);
            llRoot = (LinearLayout) itemView.findViewById(R.id.ll_root);
            tvDate = itemView.findViewById(R.id.tv_date);

//            text = (TextView) itemView.findViewById(R.id.text);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
