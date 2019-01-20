package com.android.learn.base.xskin


import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.support.annotation.ColorInt
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View

import com.gaolei.basemodule.R

/**
 * Created by Windy on 2018/1/10.
 */

class CustomTitleView
/**
 * 获得我自定义的样式属性
 *
 * @param context
 * @param attrs
 * @param defStyle
 */
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : View(context, attrs, defStyle) {
    /**
     * 文本
     */
    private var mTitleText: String? = null
    /**
     * 文本的颜色
     */
    private var mTitleTextColor: Int = 0
    /**
     * 文本的大小
     */
    private var mTitleTextSize: Int = 0

    /**
     * 绘制时控制文本绘制的范围
     */
    private val mBound: Rect
    private val mPaint: Paint
    private val mBounds = Rect()

    init {
        /**
         * 获得我们所定义的自定义样式属性
         */
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.CustomTitleView, defStyle, 0)
        val n = a.indexCount
        for (i in 0 until n) {
            val attr = a.getIndex(i)

            if (attr == R.styleable.CustomTitleView_titleText) {
                mTitleText = a.getString(attr)
            }
            if (attr == R.styleable.CustomTitleView_titleTextColor) {
                // 默认颜色设置为黑色
                mTitleTextColor = a.getColor(attr, Color.BLACK)
            }
            if (attr == R.styleable.CustomTitleView_titleTextSize) {
                // 默认设置为16sp，TypeValue也可以把sp转化为px
                mTitleTextSize = a.getDimensionPixelSize(attr, TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_SP, 16f, resources.displayMetrics).toInt())

            }

        }
        a.recycle()

        /**
         * 获得绘制文本的宽和高
         */
        mPaint = Paint()
        mPaint.textSize = mTitleTextSize.toFloat()
        // mPaint.setColor(mTitleTextColor);
        mBound = Rect()
        mPaint.getTextBounds(mTitleText, 0, mTitleText!!.length, mBound)

    }

    fun setTextColor(@ColorInt color: Int) {
        mTitleTextColor = color
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        mPaint.color = Color.YELLOW
        canvas.drawRect(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat(), mPaint)

        mPaint.color = mTitleTextColor
        canvas.drawText(mTitleText!!, (width / 2 - mBound.width() / 2).toFloat(), (height / 2 + mBound.height() / 2).toFloat(), mPaint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)
        val width: Int
        val height: Int
        if (widthMode == View.MeasureSpec.EXACTLY) {
            width = widthSize
        } else {
            mPaint.textSize = mTitleTextSize.toFloat()
            mPaint.getTextBounds(mTitleText, 0, mTitleText!!.length, mBounds)
            val textWidth = mBounds.width().toFloat()
            val desired = (paddingLeft.toFloat() + textWidth + paddingRight.toFloat()).toInt()
            width = desired
        }

        if (heightMode == View.MeasureSpec.EXACTLY) {
            height = heightSize
        } else {
            mPaint.textSize = mTitleTextSize.toFloat()
            mPaint.getTextBounds(mTitleText, 0, mTitleText!!.length, mBounds)
            val textHeight = mBounds.height().toFloat()
            val desired = (paddingTop.toFloat() + textHeight + paddingBottom.toFloat()).toInt()
            height = desired
        }
        setMeasuredDimension(width, height)
    }
}
