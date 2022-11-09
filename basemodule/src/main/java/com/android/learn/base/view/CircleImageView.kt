package com.android.learn.base.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet

/**
 * 功能描述：一个简洁而高效的圆形ImageView
 *
 * @author (作者) edward（冯丰枫）
 * @link http://www.jianshu.com/u/f7176d6d53d2
 * 创建时间： 2018/4/17 0017
 */
class CircleImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : AppCompatImageView(context, attrs, defStyleAttr) {
    private var width: Float = 0.toFloat()
    private var height: Float = 0.toFloat()
    private var radius: Float = 0.toFloat()
    private val paint: Paint
    private val mmatrix: Matrix

    init {
        paint = Paint()
        paint.isAntiAlias = true   //设置抗锯齿
        mmatrix = Matrix()      //初始化缩放矩阵
    }

    /**
     * 测量控件的宽高，并获取其内切圆的半径
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        width = measuredWidth.toFloat()
        height = measuredHeight.toFloat()
        radius = Math.min(width, height) / 2
    }

    override fun onDraw(canvas: Canvas) {
        paint.shader = initBitmapShader()//将着色器设置给画笔
        canvas.drawCircle(width / 2, height / 2, radius, paint)//使用画笔在画布上画圆
    }

    /**
     * 获取ImageView中资源图片的Bitmap，利用Bitmap初始化图片着色器,通过缩放矩阵将原资源图片缩放到铺满整个绘制区域，避免边界填充
     */
    private fun initBitmapShader(): BitmapShader? {
        if (drawable == null) return null
        val bitmap = (drawable as BitmapDrawable).bitmap
        val bitmapShader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        val scale = Math.max(width / bitmap.width, height / bitmap.height)
        mmatrix.setScale(scale, scale)//将图片宽高等比例缩放，避免拉伸
        bitmapShader.setLocalMatrix(mmatrix)
        return bitmapShader
    }
}
