package com.example.meituan;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.meituan.model.Shop;

import java.util.List;
import java.util.Locale;

/**
 * 店铺列表适配器
 */
public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.VH> {

    private final List<Shop> data;
    private final Context context;

    public ShopAdapter(List<Shop> data, Context context) {
        this.data = data;
        this.context = context;
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView logo;
        TextView name;
        TextView rating;
        TextView delivery;
        TextView notice;

        VH(View v) {
            super(v);
            logo = v.findViewById(R.id.tvShopLogo);
            name = v.findViewById(R.id.tvShopName);
            rating = v.findViewById(R.id.tvShopRating);
            delivery = v.findViewById(R.id.tvShopDelivery);
            notice = v.findViewById(R.id.tvShopNotice);
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        final Shop shop = data.get(position);
        holder.logo.setText(shop.logoEmoji);
        holder.name.setText(shop.name);
        holder.rating.setText(String.format(Locale.CHINA, "★ %.1f    月售 %d", shop.rating, shop.monthSales));
        holder.delivery.setText(String.format(Locale.CHINA, "约 %d 分钟    配送费 ¥%.1f", shop.deliveryTime, shop.deliveryFee));
        holder.notice.setText("公告：" + shop.notice);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShopDetailActivity.class);
                intent.putExtra("shopId", shop.id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
