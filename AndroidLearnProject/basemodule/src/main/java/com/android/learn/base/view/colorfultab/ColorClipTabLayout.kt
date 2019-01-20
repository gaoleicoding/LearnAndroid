package com.android.learn.base.view.colorfultab

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout

import com.gaolei.basemodule.R

import java.lang.ref.WeakReference

import android.support.v4.view.ViewPager.SCROLL_STATE_DRAGGING
import android.support.v4.view.ViewPager.SCROLL_STATE_IDLE
import android.support.v4.view.ViewPager.SCROLL_STATE_SETTLING

/**
 * Created by rookie on 2018/4/24.
 */

class ColorClipTabLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : TabLayout(context, attrs, defStyleAttr) {

    private var tabTextSize: Int = 0//每个tab字体大小
    private var tabSelectedTextColor: Int = 0//每个tab选中字体颜色
    private var tabTextColor: Int = 0//每个tab未选中颜色

    //最后的选中位置
    private var lastSelectedTabPosition = INVALID_TAB_POS

    private var viewPager: ViewPager? = null//所绑定的viewpager

    private var colorClipTabLayoutOnPageChangeListener: ColorClipTabLayoutOnPageChangeListener? = null

    init {
        if (attrs != null) {
            // Text colors/sizes come from the text appearance first
            val ta = context.obtainStyledAttributes(attrs, R.styleable.ColorClipTabLayout)
            //Tab字体大小
            tabTextSize = ta.getDimensionPixelSize(R.styleable.ColorClipTabLayout_text_size, 72)
            //Tab文字颜色
            tabTextColor = ta.getColor(R.styleable.ColorClipTabLayout_text_unselected_color, Color.parseColor("#000000"))
            tabSelectedTextColor = ta.getColor(R.styleable.ColorClipTabLayout_text_selected_color, Color.parseColor("#cc0000"))
            ta.recycle()
        }
    }

    override fun addTab(tab: TabLayout.Tab, position: Int, setSelected: Boolean) {
        //通过addTab的方式将colorClipView作为customView传入tab
        val colorClipView = ColorClipView(context)
        colorClipView.setProgress((if (setSelected) 1 else 0).toFloat())
        colorClipView.setText(tab.text!!.toString() + "")
        colorClipView.setTextSize(tabTextSize)
        colorClipView.tag = position
        colorClipView.setTextSelectedColor(tabSelectedTextColor)
        colorClipView.setTextUnselectColor(tabTextColor)
        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        colorClipView.layoutParams = layoutParams
        tab.customView = colorClipView
        super.addTab(tab, position, setSelected)
        val selectedTabPosition = selectedTabPosition
        if (selectedTabPosition == INVALID_TAB_POS && position == 0 || selectedTabPosition == position) {
            setSelectedView(position)
        }

        setTabWidth(position, colorClipView)
    }

    override fun setupWithViewPager(viewPager: ViewPager?, autoRefresh: Boolean) {
        super.setupWithViewPager(viewPager, autoRefresh)
        try {
            if (viewPager != null)
                this.viewPager = viewPager
            //通过反射找到mPageChangeListener
            //            Field field = TabLayout.class.getDeclaredField("colorClipTabLayoutOnPageChangeListener");
            //            field.setAccessible(true);
            //            TabLayoutOnPageChangeListener listener = (TabLayoutOnPageChangeListener) field.get(this);
            //            if (listener != null) {
            //                //删除自带监听
            //                viewPager.removeOnPageChangeListener(listener);
            colorClipTabLayoutOnPageChangeListener = ColorClipTabLayoutOnPageChangeListener(this)
            colorClipTabLayoutOnPageChangeListener!!.reset()
            viewPager!!.addOnPageChangeListener(colorClipTabLayoutOnPageChangeListener!!)
            //            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun removeAllTabs() {
        lastSelectedTabPosition = selectedTabPosition
        super.removeAllTabs()
    }

    override fun getSelectedTabPosition(): Int {
        val selectedTabPositionAtParent = super.getSelectedTabPosition()
        return if (selectedTabPositionAtParent == INVALID_TAB_POS)
            lastSelectedTabPosition
        else
            selectedTabPositionAtParent
    }

    fun setLastSelectedTabPosition(lastSelectedTabPosition: Int) {
        var lastSelectedTabPosition = lastSelectedTabPosition
        lastSelectedTabPosition = lastSelectedTabPosition
    }

    fun setCurrentItem(position: Int) {
        if (viewPager != null)
            viewPager!!.currentItem = position
    }

    private fun setTabWidth(position: Int, colorClipView: ColorClipView) {
        val slidingTabStrip = getChildAt(0) as ViewGroup
        val tabView = slidingTabStrip.getChildAt(position) as ViewGroup
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)

        val w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED)
        val h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED)
        //手动测量一下
        colorClipView.measure(w, h)
        params.width = colorClipView.measuredWidth + tabView.paddingLeft + tabView.paddingRight
        //设置tabView的宽度
        tabView.layoutParams = params
    }

    private fun setSelectedView(position: Int) {
        val tabCount = tabCount
        if (position < tabCount) {
            for (i in 0 until tabCount) {
                getColorClipView(i).setProgress((if (i == position) 1 else 0).toFloat())
            }
        }
    }

    fun tabScrolled(position: Int, positionOffset: Float) {

        if (positionOffset == 0.0f) {
            return
        }
        val currentTrackView = getColorClipView(position)
        val nextTrackView = getColorClipView(position + 1)
        currentTrackView.setDirection(1)
        currentTrackView.setProgress(1.0f - positionOffset)
        nextTrackView.setDirection(0)
        nextTrackView.setProgress(positionOffset)
    }

    private fun getColorClipView(position: Int): ColorClipView {
        return getTabAt(position)!!.customView as ColorClipView?
    }

    class ColorClipTabLayoutOnPageChangeListener(tabLayout: TabLayout) : TabLayout.TabLayoutOnPageChangeListener(tabLayout) {

        private val mTabLayoutRef: WeakReference<ColorClipTabLayout>
        private var mPreviousScrollState: Int = 0
        private var mScrollState: Int = 0

        init {
            mTabLayoutRef = WeakReference(tabLayout as ColorClipTabLayout)
        }

        override fun onPageScrollStateChanged(state: Int) {
            mPreviousScrollState = mScrollState
            mScrollState = state
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            val tabLayout = mTabLayoutRef.get() ?: return
            val updateText = mScrollState != SCROLL_STATE_SETTLING || mPreviousScrollState == SCROLL_STATE_DRAGGING
            if (updateText) {
                Log.e("tag1", "positionOffset$positionOffset")
                tabLayout.tabScrolled(position, positionOffset)
            }
        }

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            val tabLayout = mTabLayoutRef.get()
            mPreviousScrollState = SCROLL_STATE_SETTLING
            tabLayout.setSelectedView(position)
        }

        internal fun reset() {
            mScrollState = SCROLL_STATE_IDLE
            mPreviousScrollState = mScrollState
        }

    }

    companion object {
        private val INVALID_TAB_POS = -1
    }
}
