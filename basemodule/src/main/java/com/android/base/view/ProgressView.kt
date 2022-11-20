package com.android.base.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class ProgressView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    private var mPaint: Paint? = null
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var progress: Int = 0//加载进度

    init {
        init()
    }

    private fun init() {
        //初始化画笔
        mPaint = Paint()
        mPaint!!.isDither = true
        mPaint!!.isAntiAlias = true
        mPaint!!.strokeWidth = 5f
        mPaint!!.color = Color.parseColor("#88b221")
    }

    override fun onSizeChanged(w: Int, h: Int, ow: Int, oh: Int) {
        mWidth = w
        mHeight = h
        super.onSizeChanged(w, h, ow, oh)
    }


    override fun onDraw(canvas: Canvas) {
        canvas.drawRect(0f, 0f, (mWidth * progress / 100).toFloat(), mHeight.toFloat(), mPaint!!)
        super.onDraw(canvas)
    }

    /**
     * 设置新进度 重新绘制
     *
     * @param newProgress 新进度
     */
    fun setProgress(newProgress: Int) {
        this.progress = newProgress
        invalidate()
    }

    /**
     * 设置进度条颜色
     *
     * @param color 色值
     */
    fun setColor(color: Int) {
        mPaint!!.color = color
    }
}
