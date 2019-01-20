package com.android.learn.view.fontsliderbar

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

/**
 * Created by Administrator on 2017/9/6 0006.
 */

class Thumb(var x: Float, private val mY: Float, private val mColorNormal: Int, private val mColorPressed: Int, private val mRadius: Float) {

    private val mTouchZone: Float
    var isPressed: Boolean = false
        private set

    private var mPaintNormal: Paint? = null
    private var mPaintPressed: Paint? = null

    init {

        mPaintNormal = Paint()
        mPaintNormal!!.color = mColorNormal
        mPaintNormal!!.isAntiAlias = true

        mPaintPressed = Paint()
        mPaintPressed!!.color = mColorPressed
        mPaintPressed!!.isAntiAlias = true

        mTouchZone = Math.max(MINIMUM_TARGET_RADIUS, mRadius).toInt().toFloat()
    }

    fun press() {
        isPressed = true
    }

    fun release() {
        isPressed = false
    }

    fun isInTargetZone(x: Float, y: Float): Boolean {
        return if (Math.abs(x - this.x) <= mTouchZone && Math.abs(y - mY) <= mTouchZone) {
            true
        } else false
    }

    fun draw(canvas: Canvas) {
        if (isPressed) {
            mPaintPressed!!.color = Color.WHITE
            mPaintPressed!!.style = Paint.Style.FILL
            canvas.drawCircle(x, mY, mRadius, mPaintPressed!!)
            mPaintPressed!!.color = mColorPressed
            mPaintPressed!!.style = Paint.Style.STROKE
            canvas.drawCircle(x, mY, mRadius - 1, mPaintPressed!!)
        } else {
            mPaintNormal!!.color = Color.WHITE
            mPaintNormal!!.style = Paint.Style.FILL
            canvas.drawCircle(x, mY, mRadius, mPaintNormal!!)
            mPaintNormal!!.color = mColorPressed
            mPaintNormal!!.style = Paint.Style.STROKE
            canvas.drawCircle(x, mY, mRadius - 1, mPaintNormal!!)
        }
    }

    fun destroyResources() {
        if (null != mPaintNormal) {
            mPaintNormal = null
        }
        if (null != mPaintPressed) {
            mPaintPressed = null
        }
    }

    companion object {

        private val MINIMUM_TARGET_RADIUS = 50f
    }
}
