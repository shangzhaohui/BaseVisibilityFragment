package com.demo.fragmentdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.demo.fragmentdemo.ui.dashboard.DashboardFragment;
import com.demo.fragmentdemo.ui.home.HomeFragment;


public class BottomNavigationActivity extends AppCompatActivity {
    public static final String  KEY = "viewpager_count";
    HomeFragment mHomeFragment;
    DashboardFragment mDashboardFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
        int viewPagerCount = getIntent().getIntExtra(KEY,1);
        mHomeFragment = HomeFragment.newInstance(viewPagerCount);

//        mDashboardFragment = new DashboardFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();


        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container,mHomeFragment);
        try {
            fragmentTransaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }

        findViewById(R.id.home).setOnClickListener(view -> {
             FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (mDashboardFragment !=null) {
                transaction.hide(mDashboardFragment);
            }


            if (mHomeFragment  == null) {
                mHomeFragment = HomeFragment.newInstance(viewPagerCount);
                transaction.add(R.id.fragment_container,mHomeFragment);
            }else {

                transaction.show(mHomeFragment);
            }


            try {
                transaction.commitAllowingStateLoss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        findViewById(R.id.dashboard).setOnClickListener(view -> {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (mHomeFragment !=null) {
                transaction.hide(mHomeFragment);
            }
            if (mDashboardFragment == null) {
                mDashboardFragment = new DashboardFragment();
                transaction.add(R.id.fragment_container,mDashboardFragment);
            } else{
                transaction.show(mDashboardFragment);
            }

            try {
                transaction.commitAllowingStateLoss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    public static void startActivity(Context context, int viewPagerCount){
        Intent intent = new Intent(context,BottomNavigationActivity.class);
        intent.putExtra(KEY,viewPagerCount);
        context.startActivity(intent);
    }

}