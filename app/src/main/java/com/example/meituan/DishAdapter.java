package com.example.meituan;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.meituan.model.Dish;

import java.util.List;
import java.util.Locale;

/**
 * 右侧菜品列表适配器
 */
public class DishAdapter extends RecyclerView.Adapter<DishAdapter.VH> {

    private List<Dish> dishes;
    private final Context context;
    private final int shopId;

    public DishAdapter(List<Dish> dishes, Context context, int shopId) {
        this.dishes = dishes;
        this.context = context;
        this.shopId = shopId;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
        notifyDataSetChanged();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView emoji;
        TextView name;
        TextView desc;
        TextView price;
        TextView minus;
        TextView qty;
        TextView plus;

        VH(View v) {
            super(v);
            emoji = v.findViewById(R.id.tvDishEmoji);
            name = v.findViewById(R.id.tvDishName);
            desc = v.findViewById(R.id.tvDishDesc);
            price = v.findViewById(R.id.tvDishPrice);
            minus = v.findViewById(R.id.btnDishMinus);
            qty = v.findViewById(R.id.tvDishQty);
            plus = v.findViewById(R.id.btnDishPlus);
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dish, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        final Dish dish = dishes.get(position);
        holder.emoji.setText(dish.emoji);
        holder.name.setText(dish.name);
        holder.desc.setText(dish.desc);
        holder.price.setText(String.format(Locale.CHINA, "¥%.1f", dish.price));

        int qty = Cart.getInstance().getQty(dish);
        if (qty > 0) {
            holder.minus.setVisibility(View.VISIBLE);
            holder.qty.setVisibility(View.VISIBLE);
            holder.qty.setText(String.valueOf(qty));
        } else {
            holder.minus.setVisibility(View.GONE);
            holder.qty.setVisibility(View.GONE);
        }

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart.getInstance().add(dish);
                ShopDetailActivity.current.refreshCartUI();
            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart.getInstance().minus(dish);
                ShopDetailActivity.current.refreshCartUI();
            }
        });

        // 点菜品文字/图标 → 菜品详情
        View.OnClickListener detailListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DishDetailActivity.class);
                intent.putExtra("shopId", shopId);
                intent.putExtra("dishId", dish.id);
                context.startActivity(intent);
            }
        };
        holder.emoji.setOnClickListener(detailListener);
        holder.name.setOnClickListener(detailListener);
    }

    @Override
    public int getItemCount() {
        return dishes.size();
    }
}
