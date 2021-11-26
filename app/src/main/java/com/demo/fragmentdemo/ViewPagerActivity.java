package com.demo.fragmentdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * ViewPager 包含Fragment
 */
public class ViewPagerActivity extends AppCompatActivity {
    public static final String TAG = ViewPagerActivity.class.getSimpleName();
    private static final String KEY = "viewpager_count";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        int viewPagerCount = getIntent().getIntExtra(KEY, 1);
        ViewPager viewPager = findViewById(R.id.viewpager);

//        PagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), viewPagerCount);
        PagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), viewPagerCount);
        viewPager.setAdapter(adapter);
        TextView output = findViewById(R.id.output);
        output.setText("当前是第0个");
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                output.setText("当前是第" + position + "个");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    public static void startActivity(Context context, int viewPagerCount) {
        Intent intent = new Intent(context, ViewPagerActivity.class);
        intent.putExtra(KEY, viewPagerCount);
        context.startActivity(intent);
    }
}