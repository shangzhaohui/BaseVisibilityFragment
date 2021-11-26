package com.demo.fragmentdemo;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shangzhaohui
 * @date 2021/11/23
 */
public class BadViewPagerAdapter extends FragmentStatePagerAdapter {
    public static final String TAG = BadViewPagerAdapter.class.getSimpleName();
    private int mViewPagerCount;

    public BadViewPagerAdapter(@NonNull FragmentManager fm, int viewPagerCount) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        List<Fragment> fragments = fm.getFragments() ;
        if (fragments != null && fragments.size() >0) {
            for (Fragment fragment : fragments) {
                Log.d(TAG, "FragmentManager restore " + fragment);
            }
        }
        mViewPagerCount = viewPagerCount;
        mList.add(ChildFragment.newInstance(viewPagerCount-1));
        mList.add(ChildFragment.newInstance(viewPagerCount-1));
        mList.add(ChildFragment.newInstance(viewPagerCount-1));
    }

    private List<Fragment> mList = new ArrayList<>();


    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = mList.get(position);
        Log.d(TAG, "getItem position " +position +" " + fragment);
        return fragment;
    }

    @Override
    public int getCount() {
        return mList.size();
    }
    private Fragment currentFragment;
    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.setPrimaryItem(container, position, object);
        if (currentFragment != object) {
            currentFragment = (Fragment) object;
            Log.d(TAG, "setPrimaryItem " + object);
        }
    }

    public Fragment getFragment(int position) {
        return mList.get(position);
    }
}
