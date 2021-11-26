package com.library.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 *
 * 支持以下四种 case
 * 1. 支持 viewPager 嵌套 fragment，默认FragmentStatePagerAdapter构造方法使用 BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT，在onResume处理；如果设置为BEHAVIOR_SET_USER_VISIBLE_HINT(已经废弃),则在setUserVisibleHint中做兼容，
 * 2. 直接 fragment 直接 add， hide 主要是通过 onHiddenChanged
 * 3. 直接 fragment 直接 replace ，主要是在 onResume 做判断
 * 4. Fragment 里面用 ViewPager，ViewPager 里面有多个 Fragment的，前提是一级Fragment和 二级Fragment都必须继承 BaseVisibilityFragment, 且必须用 FragmentPagerAdapter 或者 FragmentStatePagerAdapter
 *
 */
abstract class BaseVisibilityFragment : Fragment(), View.OnAttachStateChangeListener {

    companion object {
        private const val TAG = "BaseVisibilityFragment"
    }

    /**
     * ParentActivity是否可见
     */
    private var onResumePauseVisible = false

    /**
     * 是否可见（Activity处于前台、Tab被选中、Fragment被添加、Fragment没有隐藏、Fragment.View已经Attach）
     */
    private var visible = false

    private var localParentFragment: BaseVisibilityFragment? = null

    override fun onAttach(context: Context) {
        info("onAttach")
        super.onAttach(context)
        val parentFragment = parentFragment
        if (parentFragment != null && parentFragment is BaseVisibilityFragment) {
            this.localParentFragment = parentFragment
        }
        checkVisibility(true)
    }

    override fun onDetach() {
        info("onDetach")
        super.onDetach()
        checkVisibility(false)
        localParentFragment = null
    }

    override fun onResume() {
        info("onResume")
        super.onResume()
        onResumePauseVisibilityChanged(true)
    }

    override fun onPause() {
        info("onPause")
        super.onPause()
        onResumePauseVisibilityChanged(false)
    }

    /**
     * ParentActivity可见性改变
     */
    private fun onResumePauseVisibilityChanged(visible: Boolean) {
        onResumePauseVisible = visible
        checkVisibility(visible)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        // 处理直接 replace 的 case
        view.addOnAttachStateChangeListener(this)
    }

    /**
     * 调用 fragment add hide 的时候回调用这个方法
     */
    override fun onHiddenChanged(hidden: Boolean) {
        info("onHiddenChanged hidden = $hidden")
        super.onHiddenChanged(hidden)
        checkVisibility(!hidden)
    }

    /**
     * Tab切换时会回调此方法。对于没有Tab的页面，[Fragment.getUserVisibleHint]默认为true。
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        info("setUserVisibleHint = $isVisibleToUser")
        super.setUserVisibleHint(isVisibleToUser)
        checkVisibility(isVisibleToUser)
    }

    override fun onViewAttachedToWindow(v: View?) {
        info("onViewAttachedToWindow")
        checkVisibility(true)
    }

    override fun onViewDetachedFromWindow(v: View) {
        info("onViewDetachedFromWindow")
        v.removeOnAttachStateChangeListener(this)
        checkVisibility(false)
    }

    /**
     * 检查可见性是否变化
     *
     * @param expected 可见性期望的值。只有当前值和expected不同，才需要做判断
     */
    private fun checkVisibility(expected: Boolean) {
        info("checkVisibility expected $expected visible $visible")
        if (expected == visible) return
        //  这里主要由四个值来判断；
        //  parentVisible表示父Fragment的可见性，
        val parentFragmentVisible =
            if (localParentFragment == null) true
            else localParentFragment?.isFragmentVisible() ?: false
//        自身是否隐藏
        val hintVisible = !isHidden
//        兼容BEHAVIOR_SET_USER_VISIBLE_HINT情况
        val userVisible = userVisibleHint
//        onResumeVisible：自身是resume
        val visible = parentFragmentVisible && hintVisible && onResumePauseVisible && userVisible
        info(
            String.format(
                "==> checkVisibility = %s  ( parentFragmentVisible = %s, hintVisible = %s, onResumePauseVisible =%s ,userVisible = %s)",
                visible, parentFragmentVisible, hintVisible, onResumePauseVisible, userVisible
            )
        )
        if (visible != this.visible) {
            this.visible = visible
            info("==> onVisibilityChanged = $visible")
            onVisibilityChanged(visible)

            notifyChildVisibleChange(visible)
        }
    }

    /**
     * 可见性改变
     */
    abstract fun onVisibilityChanged(visible: Boolean)

    /**
     * 是否可见（Activity处于前台、Tab被选中、Fragment被添加、Fragment没有隐藏、Fragment.View已经Attach）
     */
    fun isFragmentVisible(): Boolean {
        return visible
    }


    private fun info(s: String) {
        Log.i(TAG, "${this.javaClass.simpleName} ; $s ; this is $this")
    }


    //当自己的显示隐藏状态改变时，调用这个方法通知子Fragment
    private fun notifyChildVisibleChange(visible: Boolean) {
        if (isDetached || !isAdded) {
            return
        }
        val fragmentManager: FragmentManager = childFragmentManager
        val fragments: List<Fragment> = fragmentManager.fragments
        if (fragments == null || fragments.isEmpty()) {
            return
        }
        for (fragment in fragments) {
            if (fragment !is BaseVisibilityFragment) {
                continue
            }
            info("notifyChildVisibleChange visible = $visible")
            fragment.checkVisibility(visible)
        }
    }

}