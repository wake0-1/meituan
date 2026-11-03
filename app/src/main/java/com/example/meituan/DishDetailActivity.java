package com.example.meituan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meituan.model.Dish;
import com.example.meituan.model.Shop;

import java.util.List;
import java.util.Locale;

/**
 * 菜品详情页
 */
public class DishDetailActivity extends AppCompatActivity {

    private Dish dish;
    private int shopId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_detail);

        shopId = getIntent().getIntExtra("shopId", 1);
        int dishId = getIntent().getIntExtra("dishId", 0);

        // 全局按 id 查找菜品
        for (Shop shop : MockData.getShops()) {
            for (Dish d : shop.dishes) {
                if (d.id == dishId) {
                    dish = d;
                    break;
                }
            }
        }

        if (dish == null) {
            Toast.makeText(this, "菜品不存在", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        ((TextView) findViewById(R.id.tvDishEmoji)).setText(dish.emoji);
        ((TextView) findViewById(R.id.tvDishName)).setText(dish.name);
        ((TextView) findViewById(R.id.tvDishPrice)).setText(String.format(Locale.CHINA, "¥%.1f", dish.price));
        ((TextView) findViewById(R.id.tvDishDesc)).setText(dish.desc + "\n\n新鲜食材现做，保证口感，请尽快食用。");

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.btnAddCart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart cart = Cart.getInstance();
                // 购物车属于另一家店时先重置
                if (cart.shopId != shopId) {
                    cart.reset(shopId);
                }
                cart.add(dish);
                Toast.makeText(DishDetailActivity.this, "已加入购物车：" + dish.name, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
