package com.android.learn.base.view

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView

import com.gaolei.basemodule.R

class TitleView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    private val iv_back: ImageView

    private val title: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.title_view, this)
        iv_back = findViewById(R.id.iv_back)
        title = findViewById(R.id.title)
        iv_back.setOnClickListener { (getContext() as Activity).finish() }
    }

    fun setTitleText(text: String) {
        title.text = text
    }

}
