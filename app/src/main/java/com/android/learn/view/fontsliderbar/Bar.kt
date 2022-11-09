package com.android.learn.view.fontsliderbar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextUtils

import com.android.learn.R

/**
 * Created by Administrator on 2017/9/6 0006.
 */
class Bar(internal var context: Context, val leftX: Float, private val mY: Float, width: Float, tickCount: Int, private val mTickHeight: Float,
          barWidth: Float, barColor: Int, textColor: Int, private val mtextSize: Int, padding: Int) {

    private var mBarPaint: Paint? = null
    private var mTextPaint: Paint? = null
    val rightX: Float
    private val mPadding: Float

    private val mSegments: Int
    private val mTickDistance: Float
    private val mTickStartY: Float
    private val mTickEndY: Float

    init {
        rightX = leftX + width
        mPadding = padding.toFloat()

        mSegments = tickCount - 1
        mTickDistance = width / mSegments
        mTickStartY = mY - mTickHeight / 2f
        mTickEndY = mY + mTickHeight / 2f

        mBarPaint = Paint()
        mBarPaint!!.color = barColor
        mBarPaint!!.strokeWidth = barWidth
        mBarPaint!!.isAntiAlias = true

        mTextPaint = Paint()
        mTextPaint!!.color = textColor
        mTextPaint!!.textSize = mtextSize.toFloat()
        mTextPaint!!.isAntiAlias = true
    }

    fun draw(canvas: Canvas) {
        drawLine(canvas)
        drawTicks(canvas, context)
    }

    //获取标尺最近的刻度 通过圆所在的位置
    fun getNearestTickCoordinate(thumb: Thumb): Float {
        val nearestTickIndex = getNearestTickIndex(thumb)
        return leftX + nearestTickIndex * mTickDistance
    }

    //获取标尺最近的刻度 通过下标
    fun getNearestTickCoordinate(index: Int): Float {
        return leftX + index * mTickDistance
    }


    fun getNearestTickIndex(thumb: Thumb): Int {
        return getNearestTickIndex(thumb.x)
    }

    fun getNearestTickIndex(x: Float): Int {
        return ((x - leftX + mTickDistance / 2f) / mTickDistance).toInt()
    }

    private fun drawLine(canvas: Canvas) {
        canvas.drawLine(leftX, mY, rightX, mY, mBarPaint!!)
    }

    private fun drawTicks(canvas: Canvas, context: Context?) {
        for (i in 0..mSegments) {
            val x = i * mTickDistance + leftX
            canvas.drawLine(x, mTickStartY, x, mTickEndY, mBarPaint!!)
            //绘制头尾 A 以及标准
            var text = ""
            if (i == 0) {
                text = "A"
                mTextPaint!!.textSize = mtextSize * 0.9f
            }
            if (i == 1) {
                if (context != null)
                    text = context.getString(R.string.standrd)
                mTextPaint!!.textSize = mtextSize.toFloat()
            }
            if (i == mSegments) {
                text = "A"
                mTextPaint!!.textSize = mtextSize * 1.4f
            }
            if (!TextUtils.isEmpty(text)) {
                canvas.drawText(text, x - getTextWidth(text) / 2, mTickStartY - mPadding, mTextPaint!!)
            }
        }
    }

    internal fun getTextWidth(text: String): Float {
        return mTextPaint!!.measureText(text)
    }

    fun destroyResources() {
        if (null != mBarPaint) {
            mBarPaint = null
        }
        if (null != mTextPaint) {
            mTextPaint = null
        }
    }
}