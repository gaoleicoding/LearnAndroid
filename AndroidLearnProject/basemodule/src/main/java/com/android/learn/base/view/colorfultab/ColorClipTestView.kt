package com.android.learn.base.view.colorfultab

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

class ColorClipTestView : View {
    internal var per = 0f
    internal var x = 0f
    internal var y = 0f

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val paint = Paint()
        paint.isAntiAlias = true
        paint.strokeWidth = 80f
        paint.style = Paint.Style.FILL

        paint.textSize = 68f
        canvas.save()
        canvas.drawText("我是火车王", 200f, 200f, paint)
        canvas.restore()
        paint.color = Color.RED
        canvas.clipRect(200f, 0f, 320 * per, 400f)
        Log.e("per", "per:$per")
        canvas.drawText("我是火车王", 200f, 200f, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.e("log", "ACTION_DOWN")
                x = event.rawX
                y = event.rawY
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                Log.e("log", "ACTION_MOVE")
                val currentX = event.rawX
                val currentY = event.rawY
                if (currentY > y) {
                    per = (currentY - y) / y * 10
                }
                invalidate()
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    private fun move(canvas: Canvas, paint: Paint, per: Float) {
        canvas.save()
        val right = 300 * per

        canvas.restore()
    }
}
