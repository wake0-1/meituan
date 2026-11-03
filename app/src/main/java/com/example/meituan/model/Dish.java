package com.example.meituan.model;

/**
 * 菜品数据模型
 */
public class Dish {
    public int id;
    public String name;
    public String category;   // 所属分类
    public String desc;
    public double price;
    public String emoji;

    public Dish(int id, String name, String category, String desc, double price, String emoji) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.desc = desc;
        this.price = price;
        this.emoji = emoji;
    }
}
