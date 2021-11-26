package com.demo.fragmentdemo.create;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.demo.fragmentdemo.R;

import java.util.List;

public class CreateFragmentActivity extends AppCompatActivity {
public static final String TAG = CreateFragmentActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_fragment_layout);
        FragmentManager fragmentManager = getSupportFragmentManager();
        ExampleViewModel exampleViewModel = new ViewModelProvider(this).get(ExampleViewModel.class);
        exampleViewModel.getSelectedItem().observe(this,s -> {
            Log.d(TAG, "observer " +s);
        });
        if (fragmentManager.findFragmentById(R.id.fragment_container) == null) {
            /**
             * 1、通过fragment.setArguments(args)设置参数
             */
//            fragmentManager.beginTransaction().add(R.id.fragment_container, ExampleFragment.newInstance("fragment.setArguments(args);")).commit();


            /**
             *2 、这里其实也是通过fragment.setArguments(args)设置参数，通过FragmentFactory#instantiate方式反射获取Fragment对象；和restore的过程一样。
             * 所以必须提供或使用无参的构造方法，否则restore时会反射失败
             *
             * 如果需要支持有参数的构造方法，则需要自行实现FragmentFactory，重写instantiate方法
             */

            Bundle bundle = new Bundle();
            bundle.putString(ExampleFragment.KEY, "fragmentManager.beginTransaction().add 中传Fragment的类，bundle方式");
            fragmentManager.beginTransaction().add(R.id.fragment_container, ExampleFragment.class, bundle, ExampleFragment.class.getSimpleName()).commit();

            List<Fragment> fragments = fragmentManager.getFragments();
            for (Fragment fragment:fragments){
                Log.d(TAG, "fragment "+fragment);
            }
        }
    }
}