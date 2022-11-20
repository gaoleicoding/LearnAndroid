package com.android.learn.view

import android.animation.Animator
import android.content.Context
import android.os.Build
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.EditText
import com.android.base.utils.KeyboardUtils


object SearchViewUtils {

    fun handleSearchLayout(context: Context, searchView: View, searchEt: EditText) {
        //隐藏
        if (searchView.visibility == View.VISIBLE) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val animatorHide = ViewAnimationUtils.createCircularReveal(
                    searchView,
                    searchView.width - dip2px(context, 56f),
                    dip2px(context, 23f),
                    //确定元的半径（算长宽的斜边长，这样半径不会太短也不会很长效果比较舒服）
                    Math.hypot(searchView.width.toDouble(), searchView.height.toDouble()).toFloat(),
                    0f
                )
                animatorHide.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {

                    }

                    override fun onAnimationEnd(animation: Animator) {
                        searchView.visibility = View.GONE
                        KeyboardUtils.hideKeyboard(searchEt)
                    }

                    override fun onAnimationCancel(animation: Animator) {

                    }

                    override fun onAnimationRepeat(animation: Animator) {

                    }
                })
                animatorHide.duration = 300
                animatorHide.start()
            } else {
                // 关闭输入法
                KeyboardUtils.hideKeyboard(searchEt)
                searchView.visibility = View.GONE
            }
            searchEt.setText("")
            searchView.isEnabled = false
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val animator = ViewAnimationUtils.createCircularReveal(
                    searchView,
                    searchView.width - dip2px(context, 56f),
                    dip2px(context, 23f),
                    0f,
                    Math.hypot(searchView.width.toDouble(), searchView.height.toDouble()).toFloat()
                )
                animator.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {}

                    override fun onAnimationEnd(animation: Animator) {
                        KeyboardUtils.showKeyboard(searchEt)
                    }

                    override fun onAnimationCancel(animation: Animator) {

                    }

                    override fun onAnimationRepeat(animation: Animator) {

                    }
                })
                searchView.visibility = View.VISIBLE
                if (searchView.visibility == View.VISIBLE) {
                    animator.duration = 300
                    animator.start()
                    searchView.isEnabled = true
                }
            } else {
                searchView.visibility = View.VISIBLE
                searchView.isEnabled = true
                //显示输入法
                KeyboardUtils.showKeyboard(searchEt)
            }
        }
    }


    fun dip2px(context: Context, dp: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5).toInt()
    }
}
