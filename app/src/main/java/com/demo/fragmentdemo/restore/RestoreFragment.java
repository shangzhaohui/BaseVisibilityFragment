package com.demo.fragmentdemo.restore;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.demo.fragmentdemo.R;

/**
 * @author shangzhaohui
 * @date 2021/11/24
 */
public class RestoreFragment extends Fragment {
    public static final String TAG  = RestoreActivity.TAG +" " + RestoreFragment.class.getSimpleName();
   public static final String KEY ="input";

    /**
     * 这里不能定义带参数的构造方法，否则会挂
     */
//   public  RestoreFragment (int a, int b) {
//
//   }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate " + this);
        if (requireArguments() != null) {
            Log.d(TAG, "requireArguments args: " + requireArguments().getString(KEY));
        }

        if (savedInstanceState != null) {
            Log.d(TAG, "savedInstanceState args:  " + savedInstanceState.getString(KEY));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView " + this);
        View view = inflater.inflate(R.layout.example_layout, container,false);
        TextView textView= view.findViewById(R.id.output);

        if (requireArguments()!= null){
            String input = requireArguments().getString(KEY);
            textView.setText(input);
        }

        if (savedInstanceState != null) {
            textView.setText(savedInstanceState.getString(KEY));
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY, "这是onSaveInstanceState的 args");
    }
}
