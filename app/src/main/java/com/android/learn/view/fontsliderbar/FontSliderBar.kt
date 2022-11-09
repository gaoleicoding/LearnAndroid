package com.android.learn.view.fontsliderbar

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

/**
 * Created by Administrator on 2017/9/6 0006.
 */

class FontSliderBar : View {

    private var mTickCount = DEFAULT_TICK_COUNT
    private var mTickHeight = DEFAULT_TICK_HEIGHT

    private var mBarWidth = DEFAULT_BAR_WIDTH
    private var mBarColor = DEFAULT_BAR_COLOR

    private var xCoordinate = DEFAULT_THUMB_RADIUS
    private var mThumbColorNormal = DEFAULT_THUMB_COLOR_NORMAL
    private var mThumbColorPressed = DEFAULT_THUMB_COLOR_PRESSED

    private var mTextSize = DEFAULT_TEXT_SIZE
    private var mTextColor = DEFAULT_TEXT_COLOR
    private var mTextPadding = DEFAULT_TEXT_PADDING

    private val mDefaultWidth = 500

    private var mCurrentIndex = 1
    private var mAnimation = true

    lateinit var mThumb: Thumb
    private var mBar: Bar? = null
    private var mWith: Float = 0.toFloat()

    private var mAnimator: ValueAnimator? = null
    private var mListener: OnSliderBarChangeListener? = null

    private val minHeight: Int
        get() {
            val f = fontHeight
            return (f + mTextPadding.toFloat() + xCoordinate * 2).toInt()
        }

    val currentIndex: Int
        get() {
            if (mCurrentIndex > mTickCount - 1) {
                mCurrentIndex = mTickCount - 1
            }
            return mCurrentIndex
        }

    private val yCoordinate: Float
        get() = height - xCoordinate

    private val fontHeight: Float
        get() {
            val paint = Paint()
            paint.textSize = mTextSize.toFloat()
            paint.measureText("A")
            val fontMetrics = paint.fontMetrics
            return fontMetrics.descent - fontMetrics.ascent
        }

    private val barLength: Float
        get() = width - 2 * xCoordinate

    private val isAnimationRunning: Boolean
        get() = if (null != mAnimator && mAnimator!!.isRunning) {
            true
        } else false

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width: Int
        val height: Int

        val measureWidthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val measureHeightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val measureWidth = View.MeasureSpec.getSize(widthMeasureSpec)
        val measureHeight = View.MeasureSpec.getSize(heightMeasureSpec)

        if (measureWidthMode == View.MeasureSpec.AT_MOST) {
            width = measureWidth
        } else if (measureWidthMode == View.MeasureSpec.EXACTLY) {
            width = measureWidth
        } else {
            width = mDefaultWidth
        }
        mWith = width.toFloat()
        if (measureHeightMode == View.MeasureSpec.AT_MOST) {
            height = Math.min(minHeight, measureHeight)
        } else if (measureHeightMode == View.MeasureSpec.EXACTLY) {
            height = measureHeight
        } else {
            height = minHeight
        }
        setMeasuredDimension(width, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        createBar(context)
        createThumbs()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mBar!!.draw(canvas)
        mThumb!!.draw(canvas)
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        if (View.VISIBLE != visibility) {
            stopAnimation()
        }
    }

    override fun onDetachedFromWindow() {
        destroyResources()
        super.onDetachedFromWindow()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!isEnabled || isAnimationRunning) {
            return false
        }
        when (event.action) {
            MotionEvent.ACTION_DOWN -> return onActionDown(event.x, event.y)
            MotionEvent.ACTION_MOVE -> {
                this.parent.requestDisallowInterceptTouchEvent(true)
                return onActionMove(event.x)
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                this.parent.requestDisallowInterceptTouchEvent(false)
                return onActionUp(event.x, event.y)
            }
            else -> return true
        }
    }

    fun setOnSliderBarChangeListener(listener: OnSliderBarChangeListener): FontSliderBar {
        mListener = listener
        return this@FontSliderBar
    }

    fun setTickCount(tickCount: Int): FontSliderBar {
        if (isValidTickCount(tickCount)) {
            mTickCount = tickCount
        } else {
            Log.e(TAG, "tickCount less than 2; invalid tickCount.")
            throw IllegalArgumentException("tickCount less than 2; invalid tickCount.")
        }
        return this@FontSliderBar
    }

    fun setTickHeight(tickHeight: Float): FontSliderBar {
        mTickHeight = tickHeight
        return this@FontSliderBar
    }

    fun setBarWeight(barWeight: Float): FontSliderBar {
        mBarWidth = barWeight
        return this@FontSliderBar
    }

    fun setBarColor(barColor: Int): FontSliderBar {
        mBarColor = barColor
        return this@FontSliderBar
    }

    fun setTextSize(textSize: Int): FontSliderBar {
        mTextSize = textSize
        return this@FontSliderBar
    }

    fun setTextColor(textColor: Int): FontSliderBar {
        mTextColor = textColor
        return this@FontSliderBar
    }

    fun setTextPadding(textPadding: Int): FontSliderBar {
        mTextPadding = textPadding
        return this@FontSliderBar
    }

    fun setThumbRadius(thumbRadius: Float): FontSliderBar {
        xCoordinate = thumbRadius
        return this@FontSliderBar
    }

    fun setThumbColorNormal(thumbColorNormal: Int): FontSliderBar {
        mThumbColorNormal = thumbColorNormal
        return this@FontSliderBar
    }

    fun setThumbColorPressed(thumbColorPressed: Int): FontSliderBar {
        mThumbColorPressed = thumbColorPressed
        return this@FontSliderBar
    }


    fun setThumbIndex(currentIndex: Int): FontSliderBar {
        //处理越界
        if (indexOutOfRange(currentIndex)) {
            if (currentIndex < 0) {
                mCurrentIndex = 0
            }
            if (currentIndex > mTickCount - 1) {
                mCurrentIndex = mTickCount - 1
            }
            //            throw new IllegalArgumentException(
            //                    "A thumb index is out of bounds. Check that it is between 0 and mTickCount - 1");
        } else {
            if (mCurrentIndex != currentIndex) {
                mCurrentIndex = currentIndex
            }
        }
        if (mListener != null) {
            mListener!!.onIndexChanged(this, mCurrentIndex)
        }
        return this@FontSliderBar
    }

    fun withAnimation(animation: Boolean): FontSliderBar {
        mAnimation = animation
        return this@FontSliderBar
    }

    fun applay(context: Context) {
        createThumbs()
        createBar(context)
        requestLayout()
        invalidate()
    }

    private fun createBar(context: Context) {
        mBar = Bar(context, xCoordinate, yCoordinate, barLength, mTickCount, mTickHeight, mBarWidth,
                mBarColor, mTextColor, mTextSize, mTextPadding)
    }

    private fun createThumbs() {
        if (mCurrentIndex == 0) {
            mThumb = Thumb(xCoordinate + (mWith / mTickCount + xCoordinate / 2) * mCurrentIndex, yCoordinate, mThumbColorNormal, mThumbColorPressed, xCoordinate)
        } else {
            mThumb = Thumb(xCoordinate + (mWith / mTickCount + xCoordinate / 2) * mCurrentIndex - 4, yCoordinate, mThumbColorNormal, mThumbColorPressed, xCoordinate)
        }
        //getWidth() - 2 * getXCoordinate()
        //        float xCoordinate = getBarLength() / (mTickCount - 1) * mCurrentIndex + getXCoordinate();
        //        mThumb = new Thumb(xCoordinate, getYCoordinate(), mThumbColorNormal, mThumbColorPressed, mThumbRadius);
    }

    private fun indexOutOfRange(thumbIndex: Int): Boolean {
        return thumbIndex < 0 || thumbIndex >= mTickCount
    }

    private fun isValidTickCount(tickCount: Int): Boolean {
        return tickCount > 1
    }

    private fun onActionDown(x: Float, y: Float): Boolean {
        if (!mThumb!!.isPressed && mThumb!!.isInTargetZone(x, y)) {
            pressThumb(mThumb!!)
        }
        return true
    }

    private fun onActionMove(x: Float): Boolean {
        if (mThumb!!.isPressed) {
            moveThumb(mThumb, x)
        }
        return true
    }

    private fun onActionUp(x: Float, y: Float): Boolean {
        if (mThumb!!.isPressed) {
            releaseThumb(mThumb)
        } else {
            //点击事件
            val tempIndex = mBar!!.getNearestTickIndex(x)
            if (tempIndex != mCurrentIndex) {
                mCurrentIndex = tempIndex
                if (null != mListener) {
                    mListener!!.onIndexChanged(this, mCurrentIndex)
                }
                var end = mBar!!.getNearestTickCoordinate(mCurrentIndex)
                if (end > mBar!!.rightX) {
                    end = mBar!!.rightX
                }
                mThumb!!.x = end
                invalidate()
            }
            mThumb!!.release()//回收
        }
        return true
    }

    private fun pressThumb(thumb: Thumb) {
        thumb.press()
        invalidate()
    }

    private fun releaseThumb(thumb: Thumb) {
        val tempIndex = mBar!!.getNearestTickIndex(thumb)
        if (tempIndex != mCurrentIndex) {
            mCurrentIndex = tempIndex
            if (null != mListener) {
                mListener!!.onIndexChanged(this, mCurrentIndex)
            }
        }

        val start = thumb.x
        val end = mBar!!.getNearestTickCoordinate(thumb)
        if (mAnimation) {
            startAnimation(thumb, start, end)
        } else {
            thumb.x = end
            invalidate()
        }
        thumb.release()
    }

    private fun startAnimation(thumb: Thumb, start: Float, end: Float) {
        stopAnimation()
        mAnimator = ValueAnimator.ofFloat(start, end)
        mAnimator!!.duration = 80
        mAnimator!!.addUpdateListener { animation ->
            val x = animation.animatedValue as Float
            thumb.x = x
            invalidate()
        }
        mAnimator!!.start()
    }

    private fun destroyResources() {
        stopAnimation()
        if (null != mBar) {
            mBar!!.destroyResources()
            mBar = null
        }
        if (null != mThumb) {
            mThumb!!.destroyResources()
        }
    }

    private fun stopAnimation() {
        if (null != mAnimator) {
            mAnimator!!.cancel()
            mAnimator = null
        }
    }

    private fun moveThumb(thumb: Thumb, x: Float) {
        if (x < mBar!!.leftX || x > mBar!!.rightX) {
            // Do nothing.
        } else {
            thumb.x = x
            invalidate()
        }
    }

    interface OnSliderBarChangeListener {
        fun onIndexChanged(rangeBar: FontSliderBar, index: Int)
    }

    companion object {

        private val TAG = "SliderBar"

        private val DEFAULT_TICK_COUNT = 3
        private val DEFAULT_TICK_HEIGHT = 24f

        private val DEFAULT_BAR_WIDTH = 3f
        private val DEFAULT_BAR_COLOR = Color.LTGRAY

        private val DEFAULT_TEXT_SIZE = 14
        private val DEFAULT_TEXT_COLOR = Color.LTGRAY
        private val DEFAULT_TEXT_PADDING = 20

        private val DEFAULT_THUMB_RADIUS = 20f
        private val DEFAULT_THUMB_COLOR_NORMAL = -0xcc4a1b
        private val DEFAULT_THUMB_COLOR_PRESSED = -0xcc4a1b
    }
}
