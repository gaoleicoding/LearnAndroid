package com.android.learn.view

import android.animation.Animator
import android.content.Context
import android.os.Build
import android.support.v7.widget.CardView
import android.view.View
import android.view.ViewAnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText


object SearchViewUtils {
    fun handleToolBar(context: Context, search: View, editText: EditText) {
        //隐藏
        if (search.visibility == View.VISIBLE) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val animatorHide = ViewAnimationUtils.createCircularReveal(search,
                        search.width - dip2px(context, 56f),
                        dip2px(context, 23f),
                        //确定元的半径（算长宽的斜边长，这样半径不会太短也不会很长效果比较舒服）
                        Math.hypot(search.width.toDouble(), search.height.toDouble()).toFloat(),
                        0f)
                animatorHide.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {

                    }

                    override fun onAnimationEnd(animation: Animator) {
                        search.visibility = View.GONE
                        (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(search.windowToken, 0)
                    }

                    override fun onAnimationCancel(animation: Animator) {

                    }

                    override fun onAnimationRepeat(animation: Animator) {

                    }
                })
                animatorHide.duration = 300
                animatorHide.start()
            } else {
                //                关闭输入法
                (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(search.windowToken, 0)
                search.visibility = View.GONE
            }
            editText.setText("")
            search.isEnabled = false
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val animator = ViewAnimationUtils.createCircularReveal(search,
                        search.width - dip2px(context, 56f),
                        dip2px(context, 23f),
                        0f,
                        Math.hypot(search.width.toDouble(), search.height.toDouble()).toFloat())
                animator.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {}

                    override fun onAnimationEnd(animation: Animator) {
                        (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
                    }

                    override fun onAnimationCancel(animation: Animator) {

                    }

                    override fun onAnimationRepeat(animation: Animator) {

                    }
                })
                search.visibility = View.VISIBLE
                if (search.visibility == View.VISIBLE) {
                    animator.duration = 300
                    animator.start()
                    search.isEnabled = true
                }
            } else {
                search.visibility = View.VISIBLE
                search.isEnabled = true
                //                关闭输入法
                (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
            }
        }//显示
    }


    fun dip2px(context: Context, dp: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5).toInt()
    }
}
