package com.example.meituan.model;

import java.util.List;

/**
 * 店铺数据模型
 */
public class Shop {
    public int id;
    public String name;
    public String notice;
    public String logoEmoji;
    public double rating;
    public int monthSales;
    public double deliveryFee;
    public int deliveryTime;
    public List<Dish> dishes;

    public Shop(int id, String name, String notice, String logoEmoji, double rating,
                int monthSales, double deliveryFee, int deliveryTime, List<Dish> dishes) {
        this.id = id;
        this.name = name;
        this.notice = notice;
        this.logoEmoji = logoEmoji;
        this.rating = rating;
        this.monthSales = monthSales;
        this.deliveryFee = deliveryFee;
        this.deliveryTime = deliveryTime;
        this.dishes = dishes;
    }
}
