package com.demo.fragmentdemo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.fragmentdemo.create.CreateFragmentActivity;
import com.demo.fragmentdemo.restore.RestoreActivity;

public class NavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        findViewById(R.id.create_fragment).setOnClickListener(view -> {
            Intent intent =  new Intent(NavigationActivity.this, CreateFragmentActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.restore_fragment).setOnClickListener(view -> {
            Intent intent =  new Intent(NavigationActivity.this, RestoreActivity.class);
            startActivity(intent);
        });


        findViewById(R.id.activity_fragment_fragment).setOnClickListener(view -> {
            Intent intent =  new Intent(NavigationActivity.this, FragmentInFragmentActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.viewpager_contains_fragment).setOnClickListener(view -> {
            int viewpagerCount = 1;
            try {
                viewpagerCount = Integer.parseInt(((EditText) findViewById(R.id.viewpager_count_1)).getText().toString());
            }catch (NumberFormatException e) {

            }
            ViewPagerActivity.startActivity(NavigationActivity.this,viewpagerCount);
        });


        findViewById(R.id.fragment_viewpager_fragment).setOnClickListener(view -> {
            int viewpagerCount = 1;
            try {
                viewpagerCount = Integer.parseInt(((EditText) findViewById(R.id.viewpager_count_2)).getText().toString());
            }catch (NumberFormatException e) {

            }
            BottomNavigationActivity.startActivity(NavigationActivity.this,viewpagerCount);
        });
    }
}