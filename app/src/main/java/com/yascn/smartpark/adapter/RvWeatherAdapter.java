package com.yascn.smartpark.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.weather.LocalDayWeatherForecast;
import com.github.promeg.pinyinhelper.Pinyin;
import com.yascn.smartpark.R;
import com.yascn.smartpark.activity.WeatherActivity;
import com.yascn.smartpark.utils.ImageViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yascn.smartpark.utils.DateUtils.getWeatherDate;

/**
 * Created by YASCN on 2018/8/1.
 */

public class RvWeatherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private WeatherActivity weatherActivity;

    private ArrayList<String> mDatas = new ArrayList<>();

    private View mHeaderView;

    private OnItemClickListener mListener;
    List<LocalDayWeatherForecast> weatherForecast;

    public RvWeatherAdapter(WeatherActivity weatherActivity, List<LocalDayWeatherForecast> weatherForecast) {
        this.weatherActivity = weatherActivity;
        this.weatherForecast = weatherForecast;
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

    public void addDatas(ArrayList<String> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
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
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_weather_item, parent, false);

        return new Holder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) return;

        final int pos = getRealPosition(viewHolder);
//        final String data = mDatas.get(pos);
        if (viewHolder instanceof Holder) {
//            ((Holder) viewHolder).text.setText(data);
//            if (mListener == null) return;
//            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mListener.onItemClick(pos);
//                }
//            });

            ((Holder) viewHolder).tvDate.setText(getWeatherDate(weatherForecast.get(pos).getDate()));
            ((Holder) viewHolder).tvWeatherTemperatureMax.setText(weatherForecast.get(pos).getDayTemp()+weatherActivity.getString(R.string.degree));
            ((Holder) viewHolder).tvWeatherTemperatureMin.setText(weatherForecast.get(pos).getNightTemp()+weatherActivity.getString(R.string.degree));
            ImageViewUtils.setImageFromAssetsFile(weatherActivity, "weathericon_gray/icon_" + Pinyin.toPinyin(weatherForecast.get(pos).getDayWeather(), "").toLowerCase() + ".png", ((Holder) viewHolder).ivWeather);
        }
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? weatherForecast.size() : weatherForecast.size() + 1;
    }

    class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.iv_weather)
        ImageView ivWeather;
        @BindView(R.id.tv_weather_temperature_min)
        TextView tvWeatherTemperatureMin;
        @BindView(R.id.tv_weather_temperature_max)
        TextView tvWeatherTemperatureMax;

//        TextView text;

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

