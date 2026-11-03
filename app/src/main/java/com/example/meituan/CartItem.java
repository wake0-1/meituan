package com.example.meituan;

import com.example.meituan.model.Dish;

/**
 * 购物车条目：菜品 + 数量
 */
public class CartItem {
    public Dish dish;
    public int qty;

    public CartItem(Dish dish, int qty) {
        this.dish = dish;
        this.qty = qty;
    }
}
