package com.library.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;

/**
 * @author shangzhaohui
 * @date 2022/1/27
 */
public abstract class BaseVisibilityFragment extends Fragment implements View.OnAttachStateChangeListener {
    private boolean mOnResumePauseVisible;
    private boolean mVisible;
    private BaseVisibilityFragment mLocalParentFragment;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (getParentFragment() != null && getParentFragment() instanceof BaseVisibilityFragment) {
            mLocalParentFragment = ((BaseVisibilityFragment) getParentFragment());
        }
        checkVisibility(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        checkVisibility(false);
        mLocalParentFragment = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        onResumePauseVisibilityChanged(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        onResumePauseVisibilityChanged(false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.addOnAttachStateChangeListener(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        checkVisibility(!hidden);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        checkVisibility(isVisibleToUser);
    }

    @Override
    public void onViewAttachedToWindow(View v) {
        checkVisibility(true);
    }

    @Override
    public void onViewDetachedFromWindow(View v) {
        v.removeOnAttachStateChangeListener(this);
        checkVisibility(false);
    }

    private void checkVisibility(boolean expected) {
        if (mVisible == expected) {
            return;
        }

        boolean parentFragmentVisible = mLocalParentFragment == null ? true : mLocalParentFragment.isFragmentVisible();

        boolean hintVisible = !isHidden();

        boolean userVisible = getUserVisibleHint();

        boolean visible = parentFragmentVisible && hintVisible && mOnResumePauseVisible && userVisible;

        if (visible != this.mVisible) {
            this.mVisible = visible;
            onVisibilityChanged(visible);
            Log.d("BaseVisibilityFragment", visible + "  " + this);
            notifyChildVisibleChange(visible);
        }
    }

    /**
     * 是否可见（Activity处于前台、Tab被选中、Fragment被添加、Fragment没有隐藏、Fragment.View已经Attach）
     */
    private boolean isFragmentVisible() {
        return this.mVisible;
    }

    private void notifyChildVisibleChange(boolean visible) {
        if (isDetached() || !isAdded()) {
            return;
        }

        FragmentManager fragmentManager = getChildFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();

        if (fragments == null ||fragments.size()==0) {
            return;
        }

        for (Fragment fr : fragments) {
            if (!(fr instanceof BaseVisibilityFragment)) {
                continue;
            }

            ((BaseVisibilityFragment) fr).checkVisibility(visible);
        }
    }

    private void onResumePauseVisibilityChanged(boolean visible) {
        mOnResumePauseVisible = visible;
        checkVisibility(visible);
    }

    protected abstract void onVisibilityChanged(boolean visible);
}
