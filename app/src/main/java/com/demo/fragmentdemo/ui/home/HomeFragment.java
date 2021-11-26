package com.demo.fragmentdemo.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.demo.fragmentdemo.BottomNavigationActivity;
import com.demo.fragmentdemo.ViewPagerAdapter;
import com.demo.fragmentdemo.databinding.FragmentHomeBinding;
import com.library.base.BaseVisibilityFragment;

public class HomeFragment extends BaseVisibilityFragment {
    private int mViewPagerCount;
    public static HomeFragment newInstance(int viewPagerCount) {
        Bundle args = new Bundle();
        args.putInt(BottomNavigationActivity.KEY,viewPagerCount);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    ViewPagerAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mViewPagerCount = getArguments().getInt(BottomNavigationActivity.KEY);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        textView.setText("" +this +"  position " +0);

        ViewPager viewPager = binding.viewpager;
        adapter = new ViewPagerAdapter(getChildFragmentManager(),mViewPagerCount);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                textView.setText("" +HomeFragment.this +"  position " +position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onVisibilityChanged(boolean visible) {

    }


}