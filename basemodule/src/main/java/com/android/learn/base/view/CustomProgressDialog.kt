package com.android.learn.base.view

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.gaolei.basemodule.R


object CustomProgressDialog {
    var loadingDialog: Dialog? = null

    fun createLoadingDialog(context: Context?): Dialog? {
        if (context == null) return null
        val inflater = LayoutInflater.from(context)
        val v = inflater.inflate(R.layout.dialog_loading, null)// 得到加载view
        val layout = v.findViewById<View>(R.id.dialog_view) as LinearLayout// 加载布局
        // main.xml中的ImageView
        val spaceshipImage = v.findViewById<View>(R.id.img) as ImageView
        val tipTextView = v.findViewById<View>(R.id.tipTextView) as TextView// 提示文字
        // 加载动画
        val hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                context, R.anim.anim_dialog_load)
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation)

        loadingDialog = Dialog(context, R.style.loading_dialog)// 创建自定义样式dialog

        loadingDialog!!.setCancelable(true)// 不可以用“返回键”取消
        loadingDialog!!.setContentView(layout, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT))// 设置布局
        return loadingDialog

    }

    fun show(activity: Activity?) {
        if (loadingDialog != null) return
        if (loadingDialog != null && loadingDialog!!.isShowing) return
        loadingDialog = CustomProgressDialog.createLoadingDialog(activity)
        loadingDialog!!.show()//显示
    }

    fun cancel() {
        if (loadingDialog != null) {
            loadingDialog!!.cancel()
            loadingDialog = null
        }
    }
}

