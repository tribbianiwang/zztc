package com.yascn.smartpark.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.yascn.smartpark.R;
import com.yascn.smartpark.utils.T;

import java.util.List;

/**
 * Created by YASCN on 2017/1/5.
 */

public class KeyboardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> list;
    private Context context;

    private OnKeyboardClick onKeyboardClick;

    public interface OnKeyboardClick {
        void onKeyboardClick(String key);
        void onBackClick();
    }

    public KeyboardAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnKeyboardClick(OnKeyboardClick onKeyboardClick) {
        this.onKeyboardClick = onKeyboardClick;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_keyboard_back, parent, false);
            BackViewHolder vh = new BackViewHolder((ImageButton) view);
            vh.back = (ImageButton) view.findViewById(R.id.back);
            return vh;
        }

        if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_keyboard, parent, false);
            KeyViewHolder vh = new KeyViewHolder((Button) view);
            vh.key = (Button) view.findViewById(R.id.key);
            return vh;
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).equals("back")) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof KeyViewHolder) {
            if (TextUtils.isEmpty(list.get(position))) {
                ((KeyViewHolder) holder).key.setVisibility(View.GONE);
            } else {
                ((KeyViewHolder) holder).key.setText(list.get(position));
                ((KeyViewHolder) holder).key.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (list.get(position).equals("I") || list.get(position).equals("O")) {
                            T.showShort(context, "无效字符");
                        } else {
                            onKeyboardClick.onKeyboardClick(list.get(position));
                        }
                    }
                });
            }
        }

        if (holder instanceof BackViewHolder) {
            ((BackViewHolder) holder).back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onKeyboardClick.onBackClick();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == 0
                            ? 2 : 1;
                }
            });
        }
    }

    public class KeyViewHolder extends RecyclerView.ViewHolder {

        private Button key;

        public KeyViewHolder(Button itemView) {
            super(itemView);
            key = itemView;
        }
    }

    public class BackViewHolder extends RecyclerView.ViewHolder {

        private ImageButton back;

        public BackViewHolder(ImageButton itemView) {
            super(itemView);
            back = itemView;
        }
    }

}
