package com.example.meituan;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

/**
 * 订单条目适配器
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.VH> {

    private final List<CartItem> items;

    public OrderAdapter(List<CartItem> items) {
        this.items = items;
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView emoji;
        TextView name;
        TextView qty;
        TextView subtotal;

        VH(View v) {
            super(v);
            emoji = v.findViewById(R.id.tvOrderEmoji);
            name = v.findViewById(R.id.tvOrderName);
            qty = v.findViewById(R.id.tvOrderQty);
            subtotal = v.findViewById(R.id.tvOrderSubtotal);
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        CartItem item = items.get(position);
        holder.emoji.setText(item.dish.emoji);
        holder.name.setText(item.dish.name);
        holder.qty.setText("x" + item.qty);
        holder.subtotal.setText(String.format(Locale.CHINA, "¥%.1f", item.dish.price * item.qty));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
