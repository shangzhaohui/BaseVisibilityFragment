package com.demo.fragmentdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.library.base.BaseVisibilityFragment;

/**
 * 可以包含ViewPager的Fragment
 */
public class ChildFragment extends BaseVisibilityFragment {
    public static final String TAG = ChildFragment.class.getSimpleName();
    private static final String KEY = "contains_viewpager";
    private static final String KEY_INPUT = "key_input";
    private int mViewPagerCounts;
    private String mInput = "";

    /**
     *
     * @param viewPagerCount 内部包含几个ViewPager
     * @return
     */
    public static ChildFragment newInstance(int viewPagerCount) {
        ChildFragment firstFragment = new ChildFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY, viewPagerCount);
        firstFragment.setArguments(bundle);
        return firstFragment;
    }

    public static ChildFragment newInstance(int viewPagerCount,String input ) {
        
        Bundle bundle = new Bundle();
        bundle.putInt(KEY, viewPagerCount);
        bundle.putString(KEY_INPUT,input);
        ChildFragment fragment = new ChildFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public ChildFragment() {
        Log.d(TAG, "ChildFragment constructor " + this);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mViewPagerCounts = getArguments().getInt(KEY);
            mInput = getArguments().getString(KEY_INPUT);
            if (mInput == null) {
                mInput ="";
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_content, container, false);

        if (mViewPagerCounts > 0) {
            ((TextView) view.findViewById(R.id.textview)).setText("" + this + " position " + 0 +" " + mInput);
            ViewPager viewPager = view.findViewById(R.id.sub_viewpager);
            adapter = new ViewPagerAdapter(getChildFragmentManager(),mViewPagerCounts);
            viewPager.setAdapter(adapter);
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    ((TextView) view.findViewById(R.id.textview)).setText("" + ChildFragment.this + " position " + position+" " + mInput);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        } else {
            ((TextView) view.findViewById(R.id.textview)).setText("" + this+" " + mInput);
            view.findViewById(R.id.sub_viewpager).setVisibility(View.GONE);

            ((TextView) view.findViewById(R.id.textview)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle result = new Bundle();
                    result.putString("bundleKey", "来自ChildFragment");
                    // The child fragment needs to still set the result on its parent fragment manager
                    getParentFragmentManager().setFragmentResult("request_key", result);
                }
            });
        }
        return view;
    }

    ViewPagerAdapter adapter;


    @Override
    public void onVisibilityChanged(boolean visible) {

    }

}
