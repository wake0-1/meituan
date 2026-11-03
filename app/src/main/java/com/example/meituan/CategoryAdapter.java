package com.example.meituan;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * 左侧菜品分类适配器
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.VH> {

    private final List<String> categories;
    private int selected;
    private final OnCategoryClickListener listener;

    public interface OnCategoryClickListener {
        void onCategoryClick(int position);
    }

    public CategoryAdapter(List<String> categories, int selected, OnCategoryClickListener listener) {
        this.categories = categories;
        this.selected = selected;
        this.listener = listener;
    }

    public void setSelected(int selected) {
        this.selected = selected;
        notifyDataSetChanged();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView text;

        VH(View v) {
            super(v);
            text = (TextView) v;
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, final int position) {
        holder.text.setText(categories.get(position));
        if (position == selected) {
            holder.text.setBackgroundColor(Color.WHITE);
            holder.text.setTextColor(Color.parseColor("#222222"));
            holder.text.setTypeface(null, android.graphics.Typeface.BOLD);
        } else {
            holder.text.setBackgroundColor(Color.parseColor("#F2F2F2"));
            holder.text.setTextColor(Color.parseColor("#888888"));
            holder.text.setTypeface(null, android.graphics.Typeface.NORMAL);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCategoryClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
