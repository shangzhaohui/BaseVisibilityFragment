package com.demo.fragmentdemo;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class FragmentInFragmentActivity extends AppCompatActivity {
    public static final String TAG = FragmentInFragmentActivity.class.getSimpleName();
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         fragmentManager = getSupportFragmentManager();
        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                Log.d(TAG, "savedInstanceState 恢复的");
                return;
            }

            Fragment firstFragment = new ContainerFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }


    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
    }
}