package com.yascn.smartpark.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yascn.smartpark.R;
import com.yascn.smartpark.bean.Consumption;

import java.util.List;

/**
 * Created by YASCN on 2017/8/30.
 */

public class ConsumptionAdapter extends RecyclerView.Adapter<ConsumptionAdapter.ViewHolder>{

    private List<Consumption> consumptions;
    private Context context;

    public ConsumptionAdapter(Context context, List<Consumption> consumptions) {
        this.context = context;
        this.consumptions = consumptions;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_consumption, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return consumptions.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
