package com.example.meituan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

/**
 * 首页：店铺列表
 */
public class ShopListActivity extends AppCompatActivity {

    private static final String[] CATEGORY_NAMES = {
            "美食", "甜品", "超市", "鲜花", "药品",
            "跑腿", "酒店", "蔬果", "饮品", "更多"
    };
    private static final String[] CATEGORY_EMOJI = {
            "🍔", "🍰", "🛒", "💐", "💊",
            "🛵", "🏨", "🍎", "🧋", "📦"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);

        // 分类宫格
        GridView gridView = findViewById(R.id.gridCategory);
        gridView.setAdapter(new CategoryAdapter());

        // 商家列表
        RecyclerView rvShop = findViewById(R.id.rvShop);
        rvShop.setLayoutManager(new LinearLayoutManager(this));
        rvShop.setAdapter(new ShopAdapter(MockData.getShops(), this));
    }

    private class CategoryAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return CATEGORY_NAMES.length;
        }

        @Override
        public Object getItem(int position) {
            return CATEGORY_NAMES[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(ShopListActivity.this)
                        .inflate(R.layout.category_item, parent, false);
            }
            ((TextView) convertView.findViewById(R.id.tvCategoryEmoji)).setText(CATEGORY_EMOJI[position]);
            ((TextView) convertView.findViewById(R.id.tvCategoryName)).setText(CATEGORY_NAMES[position]);
            return convertView;
        }
    }
}
