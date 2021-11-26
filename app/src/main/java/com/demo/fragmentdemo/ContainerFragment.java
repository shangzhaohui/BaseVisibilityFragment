package com.demo.fragmentdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.library.base.BaseVisibilityFragment;

/**
 * @author shangzhaohui
 * @date 2021/11/22
 */
public class ContainerFragment  extends BaseVisibilityFragment {
    Fragment firstFragment;
    private final static String TAG ="ContainerFragment";
    @Override
    public void onVisibilityChanged(boolean visible) {
        Log.d(TAG, "onVisibilityChanged " +visible);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getChildFragmentManager().setFragmentResultListener("request_key",this,(requestKey, bundle) -> {
            String result = bundle.getString("bundleKey");

            Log.d(TAG, "result " + result);
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_container, container, false);
        ((TextView) view.findViewById(R.id.textview)).setText(""+this);
         firstFragment = ChildFragment.newInstance(0);
        getChildFragmentManager().beginTransaction()
                .add(R.id.fragment_container111, firstFragment).commit();
        return view;
    }

}
