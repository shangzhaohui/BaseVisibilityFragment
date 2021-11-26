package com.demo.fragmentdemo;

import android.util.Log;
import android.util.SparseArray;
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
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public static final String TAG = ViewPagerAdapter.class.getSimpleName();
    private int mViewPagerCount;

    private SparseArray<Fragment> mFragmentSparseArray = new SparseArray<>();
    public ViewPagerAdapter(@NonNull FragmentManager fm, int viewPagerCount) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        List<Fragment> fragments = fm.getFragments() ;
        if (fragments != null && fragments.size() >0) {
            for (Fragment fragment : fragments) {
                Log.d(TAG, "FragmentManager restore " + fragment);
            }
        }
        mViewPagerCount = viewPagerCount;
        mList.add("这是第0个");
        mList.add("这是第1个");
        mList.add("这是第2个");
    }

    private List<String> mList = new ArrayList<>();


    @NonNull
    @Override
    public Fragment getItem(int position) {
//        Fragment fragment = mList.get(position);
        Fragment fragment = ChildFragment.newInstance(mViewPagerCount-1,mList.get(position));
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

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
        mFragmentSparseArray.remove(position);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Fragment fragment = ((Fragment) super.instantiateItem(container, position));
        if (fragment != null) {
            mFragmentSparseArray.put(position,fragment);
        }
        return fragment;
    }

    public Fragment getFragment(int position) {
        return mFragmentSparseArray.get(position);
    }
}
