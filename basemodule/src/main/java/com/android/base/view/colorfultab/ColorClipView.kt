package com.android.base.view.colorfultab

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import com.android.base.R


/**
 * Created by rookie on 2018/4/24.
 */

class ColorClipView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private val paint: Paint//画笔
    private var text: String? = "我是不哦车网"//绘制的文本
    private var textSize = sp2px(18f)//文本字体大小

    private var textWidth: Int = 0//文本的宽度
    private var textHeight: Int = 0//文本的高度

    private var textUnselectColor = R.color.colorPrimary//文本未选中字体颜色
    private var textSelectedColor = R.color.colorAccent//文本选中颜色

    private var mDirection = DIRECTION_LEFT

    private val textRect = Rect()//文本显示区域

    private var startX: Int = 0//X轴开始绘制的坐标

    private var startY: Int = 0//y轴开始绘制的坐标

    private var baseLineY: Int = 0//基线的位置

    private var progress: Float = 0.toFloat()

    init {

        //初始化各个属性包括画笔

        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val ta = context.obtainStyledAttributes(attrs,
                R.styleable.ColorClipView)
        text = ta.getString(R.styleable.ColorClipView_text)
        textSize = ta.getDimensionPixelSize(R.styleable.ColorClipView_text_size, textSize)
        //        textUnselectColor = ta.getColor(R.styleable.ColorClipView_text_unselected_color, textUnselectColor);
        //        textSelectedColor = ta.getColor(R.styleable.ColorClipView_text_selected_color, textSelectedColor);
        mDirection = ta.getInt(R.styleable.ColorClipView_direction, mDirection)
        progress = ta.getFloat(R.styleable.ColorClipView_progress, 0f)
        ta.recycle()//用完就得收！
        paint.textSize = textSize.toFloat()
    }

    private fun sp2px(dpVal: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                dpVal, resources.displayMetrics).toInt()
    }

    fun setProgress(progress: Float) {
        this.progress = progress
        if (progress.toInt() == 1) {
            paint.setTypeface(Typeface.DEFAULT_BOLD);
        }else {
            paint.setTypeface(Typeface.DEFAULT);
        }
        invalidate()
    }

    fun setTextSize(mTextSize: Int) {
        this.textHeight = mTextSize
        paint.textSize = mTextSize.toFloat()
        requestLayout()
        invalidate()
    }

    fun setText(text: String) {
        this.text = text
        requestLayout()
        invalidate()
    }

    fun setDirection(direction: Int) {
        this.mDirection = direction
        invalidate()
    }

    fun setTextUnselectColor(unselectColor: Int) {
        this.textUnselectColor = unselectColor
        invalidate()
    }

    fun setTextSelectedColor(selectedColor: Int) {
        this.textSelectedColor = selectedColor
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureText()//测量文本的长宽

        val width = measureWidth(widthMeasureSpec)//通过模式的不同来测量出实际的宽度
        val height = measureHeight(heightMeasureSpec)//通过模式的不同来测量出实际的高度
        setMeasuredDimension(width, height)
        Log.e("tag", "七点" + (measuredWidth - paddingRight - paddingLeft))
        startX = (measuredWidth - paddingRight - paddingLeft) / 2 - textWidth / 2
        startY = textHeight - paddingBottom - paddingTop
    }

    private fun measureHeight(heightMeasureSpec: Int): Int {
        val mode = View.MeasureSpec.getMode(heightMeasureSpec)
        val size = View.MeasureSpec.getSize(heightMeasureSpec)
        var realSize = 0
        when (mode) {
            View.MeasureSpec.EXACTLY -> realSize = size
            View.MeasureSpec.AT_MOST, View.MeasureSpec.UNSPECIFIED -> {
                realSize = textHeight
                realSize += paddingTop + paddingBottom
            }
        }
        realSize = if (mode == View.MeasureSpec.AT_MOST) Math.min(realSize, size) else realSize
        return realSize
    }

    private fun measureWidth(widthMeasureSpec: Int): Int {
        val mode = View.MeasureSpec.getMode(widthMeasureSpec)//通过widthMeasureSpec拿到Mode
        val size = View.MeasureSpec.getSize(widthMeasureSpec)//同理
        var realSize = 0//最后返回的值
        when (mode) {
            View.MeasureSpec.EXACTLY//精确模式下直接用给出的宽度
            -> realSize = size
            View.MeasureSpec.AT_MOST//最大模式
                , View.MeasureSpec.UNSPECIFIED//未指定模式
            -> {
                //这两种情况下,用测量出的宽度加上左右padding
                realSize = textWidth
                realSize = realSize + paddingLeft + paddingRight
            }
        }
        //如果mode为最大模式,不应该大于父类传入的值,所以取最小
        realSize = if (mode == View.MeasureSpec.AT_MOST) Math.min(realSize, size) else realSize
        return realSize
    }

    private fun measureText() {
        textWidth = paint.measureText(text).toInt()//测量文本宽度
        Log.d("tag", "measureText=" + paint.measureText(text))


        //直接通过获得文本显示范围,再获得高度
        //参数里，text 是要测量的文字
        //start 和 end 分别是文字的起始和结束位置，textRect 是存储文字显示范围的对象，方法在测算完成之后会把结果写进 textRect。
        paint.getTextBounds(text, 0, text!!.length, textRect)
        textHeight = textRect.height()

        //通过文本的descent线与top线的距离来测量文本高度,这是其中一种测量方法
        val fm = paint.fontMetrics
        textHeight = Math.ceil((fm.descent - fm.top).toDouble()).toInt()

        baseLineY = ((textHeight / 2).toFloat() - (fm.bottom - fm.top) / 2 - fm.top).toInt()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //OK~开始绘制咯~
        //首先先判断方向是左还是右呢?  是上还是下呢? 真期待....
        Log.e("tag", "OnDraw")
        if (mDirection == DIRECTION_LEFT) {
            //绘制朝左的选中文字
            drawHorizontalText(canvas, textSelectedColor, startX,
                    (startX + progress * textWidth).toInt())
            //绘制朝左的未选中文字
            drawHorizontalText(canvas, textUnselectColor, (startX + progress * textWidth).toInt(), startX + textWidth)
        } else if (mDirection == DIRECTION_RIGHT) {
            //绘制朝右的选中文字
            drawHorizontalText(canvas, textSelectedColor,
                    (startX + (1 - progress) * textWidth).toInt(), startX + textWidth)
            //绘制朝右的未选中文字
            drawHorizontalText(canvas, textUnselectColor, startX,
                    (startX + (1 - progress) * textWidth).toInt())
        } else if (mDirection == DIRECTION_TOP) {
            //绘制朝上的选中文字
            drawVerticalText(canvas, textSelectedColor, startY,
                    (startY + progress * textHeight).toInt())
            //绘制朝上的未选中文字
            drawVerticalText(canvas, textUnselectColor, (startY + progress * textHeight).toInt(), startY + textHeight)
        } else {
            //绘制朝下的选中文字
            drawVerticalText(canvas, textSelectedColor,
                    (startY + (1 - progress) * textHeight).toInt(),
                    startY + textHeight)
            //绘制朝下的未选中文字
            drawVerticalText(canvas, textUnselectColor, startY,
                    (startY + (1 - progress) * textHeight).toInt())
        }

    }

    private fun drawHorizontalText(canvas: Canvas, color: Int, startX: Int, endX: Int) {
        paint.color = color
        canvas.save()
        Log.e("tag", "getMeasuredHeight$measuredHeight")
        canvas.clipRect(startX, 0, endX, measuredHeight)
        canvas.drawText(text!!, this.startX.toFloat(), baseLineY.toFloat(), paint)
        canvas.restore()
    }

    private fun drawVerticalText(canvas: Canvas, color: Int, startY: Int, endY: Int) {
        paint.color = color
        canvas.save()
        canvas.clipRect(0, startY, measuredWidth, endY)// left, top,
        canvas.drawText(text!!, this.startX.toFloat(),
                this.startY.toFloat(), paint)
        canvas.restore()
    }

    companion object {

        private val DIRECTION_LEFT = 0
        private val DIRECTION_RIGHT = 1
        private val DIRECTION_TOP = 2
        private val DIRECTION_BOTTOM = 3
    }
}
