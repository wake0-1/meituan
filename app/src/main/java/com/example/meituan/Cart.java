package com.example.meituan;

import com.example.meituan.model.Dish;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 购物车单例：整个 App 共用一个购物车
 */
public class Cart {
    private static final Cart INSTANCE = new Cart();
    public int shopId = -1;
    private final LinkedHashMap<Integer, CartItem> items = new LinkedHashMap<>();

    private Cart() {
    }

    public static Cart getInstance() {
        return INSTANCE;
    }

    /** 进入某个店铺时重置购物车（外卖购物车只能属于一家店） */
    public void reset(int shopId) {
        this.shopId = shopId;
        items.clear();
    }

    public void add(Dish dish) {
        CartItem item = items.get(dish.id);
        if (item == null) {
            items.put(dish.id, new CartItem(dish, 1));
        } else {
            item.qty++;
        }
    }

    public void minus(Dish dish) {
        CartItem item = items.get(dish.id);
        if (item == null) {
            return;
        }
        item.qty--;
        if (item.qty <= 0) {
            items.remove(dish.id);
        }
    }

    public int getQty(Dish dish) {
        CartItem item = items.get(dish.id);
        return item == null ? 0 : item.qty;
    }

    public void clear() {
        items.clear();
    }

    public List<CartItem> getItems() {
        return new ArrayList<>(items.values());
    }

    public int getCount() {
        int count = 0;
        for (CartItem item : items.values()) {
            count += item.qty;
        }
        return count;
    }

    public double getTotal() {
        double total = 0;
        for (CartItem item : items.values()) {
            total += item.dish.price * item.qty;
        }
        return total;
    }
}
