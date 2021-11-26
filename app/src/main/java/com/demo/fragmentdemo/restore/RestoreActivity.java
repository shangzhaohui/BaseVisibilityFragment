package com.demo.fragmentdemo.restore;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.demo.fragmentdemo.R;
import com.demo.fragmentdemo.create.ExampleFragment;

public class RestoreActivity extends AppCompatActivity {
    public static final String TAG = RestoreActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentById(R.id.fragment_container) == null) {
            Log.d(TAG, "没有找到"+RestoreFragment.class.getSimpleName()+"实例需重新创建");
            Bundle bundle = new Bundle();
            bundle.putString(RestoreFragment.KEY, "这是初始化的参数");
            fragmentManager.beginTransaction().add(R.id.fragment_container, RestoreFragment.class, bundle, ExampleFragment.class.getSimpleName()).commit();
        }else {
            Log.d(TAG, "已经存在 " + RestoreFragment.class.getSimpleName() +"实例");
        }
    }
}