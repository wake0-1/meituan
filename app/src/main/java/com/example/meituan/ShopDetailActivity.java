package com.example.meituan;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meituan.model.Dish;
import com.example.meituan.model.Shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 店铺详情页：左侧分类 + 右侧菜品 + 底部购物车
 */
public class ShopDetailActivity extends AppCompatActivity {

    public static ShopDetailActivity current;

    private Shop shop;
    private List<String> categories;
    private int selectedCategory = 0;

    private CategoryAdapter categoryAdapter;
    private DishAdapter dishAdapter;
    private CartPopupAdapter cartPopupAdapter;

    private TextView btnCart;
    private TextView tvCartCount;
    private TextView tvTotal;
    private TextView btnCheckout;
    private View scrim;
    private LinearLayout cartPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        current = this;

        int shopId = getIntent().getIntExtra("shopId", 1);
        shop = MockData.getShopById(shopId);
        Cart.getInstance().reset(shopId);

        ((TextView) findViewById(R.id.tvShopName)).setText(shop.name);
        ((TextView) findViewById(R.id.tvShopNotice)).setText("公告：" + shop.notice);

        // 分类列表
        categories = new ArrayList<>();
        for (Dish dish : shop.dishes) {
            if (!categories.contains(dish.category)) {
                categories.add(dish.category);
            }
        }

        RecyclerView rvCategory = findViewById(R.id.rvCategory);
        rvCategory.setLayoutManager(new LinearLayoutManager(this));
        categoryAdapter = new CategoryAdapter(categories, 0, new CategoryAdapter.OnCategoryClickListener() {
            @Override
            public void onCategoryClick(int position) {
                selectedCategory = position;
                categoryAdapter.setSelected(position);
                dishAdapter.setDishes(getDishesByCategory(categories.get(position)));
            }
        });
        rvCategory.setAdapter(categoryAdapter);

        // 菜品列表
        RecyclerView rvDish = findViewById(R.id.rvDish);
        rvDish.setLayoutManager(new LinearLayoutManager(this));
        dishAdapter = new DishAdapter(getDishesByCategory(categories.get(0)), this, shopId);
        rvDish.setAdapter(dishAdapter);

        // 底部购物车栏
        btnCart = findViewById(R.id.btnCart);
        tvCartCount = findViewById(R.id.tvCartCount);
        tvTotal = findViewById(R.id.tvTotal);
        btnCheckout = findViewById(R.id.btnCheckout);
        scrim = findViewById(R.id.scrim);
        cartPopup = findViewById(R.id.cartPopup);

        RecyclerView rvCart = findViewById(R.id.rvCart);
        rvCart.setLayoutManager(new LinearLayoutManager(this));
        cartPopupAdapter = new CartPopupAdapter(Cart.getInstance().getItems());
        rvCart.setAdapter(cartPopupAdapter);

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Cart.getInstance().getCount() > 0) {
                    showPopup();
                } else {
                    Toast.makeText(ShopDetailActivity.this, "购物车还是空的，先选点好吃的吧～", Toast.LENGTH_SHORT).show();
                }
            }
        });

        scrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hidePopup();
            }
        });

        findViewById(R.id.btnClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart.getInstance().clear();
                refreshCartUI();
            }
        });

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Cart.getInstance().getCount() == 0) {
                    Toast.makeText(ShopDetailActivity.this, "请先选购商品", Toast.LENGTH_SHORT).show();
                    return;
                }
                android.content.Intent intent = new android.content.Intent(ShopDetailActivity.this, OrderActivity.class);
                intent.putExtra("shopId", shop.id);
                startActivity(intent);
            }
        });

        refreshCartUI();
    }

    private List<Dish> getDishesByCategory(String category) {
        List<Dish> result = new ArrayList<>();
        for (Dish dish : shop.dishes) {
            if (dish.category.equals(category)) {
                result.add(dish);
            }
        }
        return result;
    }

    private void showPopup() {
        cartPopupAdapter.setItems(Cart.getInstance().getItems());
        cartPopup.setVisibility(View.VISIBLE);
        scrim.setVisibility(View.VISIBLE);
    }

    private void hidePopup() {
        cartPopup.setVisibility(View.GONE);
        scrim.setVisibility(View.GONE);
    }

    /** 购物车发生任何变化后刷新所有相关界面 */
    public void refreshCartUI() {
        int count = Cart.getInstance().getCount();
        double total = Cart.getInstance().getTotal();

        // 角标
        if (count > 0) {
            tvCartCount.setVisibility(View.VISIBLE);
            tvCartCount.setText(String.valueOf(count));
            tvTotal.setText(String.format(Locale.CHINA, "¥%.2f  另需配送费 ¥%.1f", total, shop.deliveryFee));
            tvTotal.setTextColor(Color.parseColor("#222222"));
            btnCart.setBackgroundResource(R.drawable.bg_circle_yellow);
            btnCheckout.setBackgroundResource(R.drawable.bg_pay_bar);
            btnCheckout.setTextColor(Color.WHITE);
        } else {
            tvCartCount.setVisibility(View.GONE);
            tvTotal.setText("未选购商品");
            tvTotal.setTextColor(Color.parseColor("#888888"));
            btnCart.setBackgroundResource(R.drawable.bg_circle_gray);
            btnCheckout.setBackgroundColor(Color.parseColor("#CCCCCC"));
            btnCheckout.setTextColor(Color.WHITE);
            hidePopup();
        }

        // 弹窗与右侧列表刷新
        cartPopupAdapter.setItems(Cart.getInstance().getItems());
        dishAdapter.setDishes(getDishesByCategory(categories.get(selectedCategory)));
    }
}
