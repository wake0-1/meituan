package com.example.meituan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meituan.model.Shop;

import java.util.List;
import java.util.Locale;

/**
 * 订单确认与支付页
 */
public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        int shopId = getIntent().getIntExtra("shopId", 1);
        Shop shop = MockData.getShopById(shopId);
        Cart cart = Cart.getInstance();

        List<CartItem> items = cart.getItems();

        RecyclerView rvOrder = findViewById(R.id.rvOrder);
        rvOrder.setLayoutManager(new LinearLayoutManager(this));
        rvOrder.setAdapter(new OrderAdapter(items));

        double dishTotal = cart.getTotal();
        double grand = dishTotal + shop.deliveryFee;

        ((TextView) findViewById(R.id.tvDeliveryFee)).setText(String.format(Locale.CHINA, "配送费：¥%.1f", shop.deliveryFee));
        ((TextView) findViewById(R.id.tvGrandTotal)).setText(String.format(Locale.CHINA, "合计（含配送费）：¥%.2f", grand));
        ((TextView) findViewById(R.id.tvPayTotal)).setText(String.format(Locale.CHINA, "实付 ¥%.2f", grand));

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.btnPay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OrderActivity.this, "支付成功！骑手正在快马加鞭赶来～", Toast.LENGTH_LONG).show();
                Cart.getInstance().clear();
                // 回到首页并清空中间页面栈
                Intent intent = new Intent(OrderActivity.this, ShopListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }
}
