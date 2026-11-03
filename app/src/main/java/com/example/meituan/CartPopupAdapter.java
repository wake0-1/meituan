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
 * 购物车弹窗适配器
 */
public class CartPopupAdapter extends RecyclerView.Adapter<CartPopupAdapter.VH> {

    private List<CartItem> items;

    public CartPopupAdapter(List<CartItem> items) {
        this.items = items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView emoji;
        TextView name;
        TextView subtotal;
        TextView minus;
        TextView qty;
        TextView plus;

        VH(View v) {
            super(v);
            emoji = v.findViewById(R.id.tvCartEmoji);
            name = v.findViewById(R.id.tvCartName);
            subtotal = v.findViewById(R.id.tvCartSubtotal);
            minus = v.findViewById(R.id.btnCartMinus);
            qty = v.findViewById(R.id.tvCartQty);
            plus = v.findViewById(R.id.btnCartPlus);
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        final CartItem item = items.get(position);
        holder.emoji.setText(item.dish.emoji);
        holder.name.setText(item.dish.name);
        holder.subtotal.setText(String.format(Locale.CHINA, "¥%.1f", item.dish.price * item.qty));
        holder.qty.setText(String.valueOf(item.qty));

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart.getInstance().add(item.dish);
                ShopDetailActivity.current.refreshCartUI();
            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart.getInstance().minus(item.dish);
                ShopDetailActivity.current.refreshCartUI();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
