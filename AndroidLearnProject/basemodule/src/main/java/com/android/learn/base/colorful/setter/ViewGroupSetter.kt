package com.android.learn.base.colorful.setter

import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.HashSet

import android.content.res.Resources.Theme
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView

/**
 * ViewGroup类型的Setter,用于修改ListView、RecyclerView等ViewGroup类型的Item
 * View,核心思想为遍历每个Item View中的子控件,然后根据用户绑定的view
 * id与属性来将View修改为当前Theme下的最新属性值，达到ViewGroup控件的换肤效果。
 *
 * TODO : Color与Drawable的设计问题,是否需要修改为桥接模式 {@see ViewBackgroundColorSetter}、
 * {@see ViewBackgroundDrawableSetter}
 *
 * @author mrsimple
 */
class ViewGroupSetter : ViewSetter {

    /**
     * ListView的子试图的Setter
     */
    protected var mItemViewSetters: MutableSet<ViewSetter> = HashSet()

    /**
     *
     * @param targetView
     * @param resId
     */
    constructor(targetView: ViewGroup, resId: Int) : super(targetView, resId) {}

    constructor(targetView: ViewGroup) : super(targetView, 0) {}

    /**
     * 设置View的背景色
     *
     * @param viewId
     * @param colorId
     * @return
     */
    fun childViewBgColor(viewId: Int, colorId: Int): ViewGroupSetter {
        mItemViewSetters.add(ViewBackgroundColorSetter(viewId, colorId))
        return this
    }

    /**
     * 设置View的drawable背景
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    fun childViewBgDrawable(viewId: Int, drawableId: Int): ViewGroupSetter {
        mItemViewSetters.add(ViewBackgroundDrawableSetter(viewId,
                drawableId))
        return this
    }

    /**
     * 设置文本颜色,因此View的类型必须为TextView或者其子类
     *
     * @param viewId
     * @param colorId
     * @return
     */
    fun childViewTextColor(viewId: Int, colorId: Int): ViewGroupSetter {
        mItemViewSetters.add(TextColorSetter(viewId, colorId))
        return this
    }

    override fun setValue(newTheme: Theme, themeId: Int) {
        mView!!.setBackgroundColor(getColor(newTheme))
        // 清空AbsListView的元素
        clearListViewRecyclerBin(mView)
        // 清空RecyclerView
        clearRecyclerViewRecyclerBin(mView)
        // 修改所有子元素的相关属性
        changeChildenAttrs(mView as ViewGroup, newTheme, themeId)
    }

    /**
     *
     * @param viewId
     * @return
     */
    private fun findViewById(rootView: View, viewId: Int): View {
        val targetView = rootView.findViewById<View>(viewId)
        Log.d("", "### viewgroup find view : $targetView")
        return targetView
    }

    /**
     * 修改子视图的对应属性
     *
     * @param viewGroup
     * @param newTheme
     * @param themeId
     */
    private fun changeChildenAttrs(viewGroup: ViewGroup, newTheme: Theme,
                                   themeId: Int) {
        val childCount = viewGroup.childCount
        for (i in 0 until childCount) {
            val childView = viewGroup.getChildAt(i)
            // 深度遍历
            if (childView is ViewGroup) {
                changeChildenAttrs(childView, newTheme, themeId)
            }

            // 遍历子元素与要修改的属性,如果相同那么则修改子View的属性
            for (setter in mItemViewSetters) {
                // 每次都要从ViewGroup中查找数据
                setter.mView = findViewById(viewGroup, setter.mViewId)

                Log.e("", "### childView : " + childView + ", id = "
                        + childView.id)
                Log.e("", "### setter view : " + setter.mView + ", id = "
                        + setter.viewId)
                if (childView.id == setter.viewId) {
                    setter.setValue(newTheme, themeId)
                    Log.e("", "@@@ 修改新的属性: $childView")
                }
            }
        }
    }

    private fun clearListViewRecyclerBin(rootView: View) {
        if (rootView is AbsListView) {
            try {
                val localField = AbsListView::class.java
                        .getDeclaredField("mRecycler")
                localField.isAccessible = true
                val localMethod = Class.forName(
                        "android.widget.AbsListView\$RecycleBin")
                        .getDeclaredMethod("clear", *arrayOfNulls(0))
                localMethod.isAccessible = true
                localMethod.invoke(localField.get(rootView), *arrayOfNulls(0))
                Log.e("", "### 清空AbsListView的RecycerBin ")
            } catch (e1: NoSuchFieldException) {
                e1.printStackTrace()
            } catch (e2: ClassNotFoundException) {
                e2.printStackTrace()
            } catch (e3: NoSuchMethodException) {
                e3.printStackTrace()
            } catch (e4: IllegalAccessException) {
                e4.printStackTrace()
            } catch (e5: InvocationTargetException) {
                e5.printStackTrace()
            }

        }
    }

    private fun clearRecyclerViewRecyclerBin(rootView: View) {
        if (rootView is RecyclerView) {
            try {
                val localField = RecyclerView::class.java
                        .getDeclaredField("mRecycler")
                localField.isAccessible = true
                val localMethod = Class.forName(
                        "android.support.v7.widget.RecyclerView\$Recycler")
                        .getDeclaredMethod("clear", *arrayOfNulls(0))
                localMethod.isAccessible = true
                localMethod.invoke(localField.get(rootView), *arrayOfNulls(0))
                Log.e("", "### 清空RecyclerView的Recycer ")
                rootView.invalidate()
            } catch (e1: NoSuchFieldException) {
                e1.printStackTrace()
            } catch (e2: ClassNotFoundException) {
                e2.printStackTrace()
            } catch (e3: NoSuchMethodException) {
                e3.printStackTrace()
            } catch (e4: IllegalAccessException) {
                e4.printStackTrace()
            } catch (e5: InvocationTargetException) {
                e5.printStackTrace()
            }

        }
    }

}
