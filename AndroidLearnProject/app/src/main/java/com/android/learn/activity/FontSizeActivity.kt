package com.android.learn.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.android.learn.R
import com.android.learn.base.activity.BaseActivity
import com.android.learn.base.activity.BaseMvpActivity
import com.android.learn.base.event.RestartMainEvent
import com.android.learn.base.mpresenter.BasePresenter
import com.android.learn.base.utils.LanguageUtil
import com.android.learn.base.utils.LogUtil
import com.android.learn.base.utils.SPUtils
import com.android.learn.base.utils.ScreenUtils
import com.android.learn.view.fontsliderbar.FontSliderBar

import org.greenrobot.eventbus.EventBus

import butterknife.BindView
import butterknife.OnClick


class FontSizeActivity : BaseMvpActivity<*>() {
    @BindView(R.id.iv_back)
    internal var iv_back: ImageView? = null
    @BindView(R.id.title)
    internal var title: TextView? = null
    @BindView(R.id.fontSliderBar)
    internal var fontSliderBar: FontSliderBar? = null
    @BindView(R.id.tv_chatcontent1)
    internal var tvContent1: TextView? = null
    @BindView(R.id.tv_chatcontent)
    internal var tvContent2: TextView? = null
    @BindView(R.id.iv_userhead)
    internal var ivUserhead: ImageView? = null
    private var textsize1: Float = 0.toFloat()
    private var textsize2: Float = 0.toFloat()
    private val textsize3: Float = 0.toFloat()
    private var textSizef: Float = 0.toFloat()//缩放比例
    private var isClickable = true
    internal var currentIndex: Int = 0
    internal var TAG = "FontSizeActivity"

    protected override val layoutId: Int
        get() = R.layout.activity_fontsizes

    override fun initData(bundle: Bundle) {
        title!!.text = getString(R.string.font_size)
        iv_back!!.visibility = View.VISIBLE
        initData()
    }

    private fun initData() {
        currentIndex = SPUtils.getParam(this, "currentIndex", 1) as Int
        textSizef = 1 + currentIndex * 0.1f
        val size1 = tvContent1!!.textSize
        val size2 = tvContent2!!.textSize
        textsize1 = size1 / textSizef
        textsize2 = size2 / textSizef
        fontSliderBar!!.setTickCount(6).setTickHeight(ScreenUtils.dp2px(this@FontSizeActivity, 15).toFloat()).setBarColor(Color.GRAY)
                .setTextColor(Color.BLACK).setTextPadding(ScreenUtils.dp2px(this@FontSizeActivity, 10)).setTextSize(ScreenUtils.dp2px(this@FontSizeActivity, 14))
                .setThumbRadius(ScreenUtils.dp2px(this@FontSizeActivity, 10).toFloat()).setThumbColorNormal(Color.GRAY).setThumbColorPressed(Color.GRAY)
                .setOnSliderBarChangeListener(FontSliderBar.OnSliderBarChangeListener { rangeBar, index ->
                    var index = index
                    if (index > 5) {
                        return@OnSliderBarChangeListener
                    }
                    index = index - 1
                    val textSizef = 1 + index * 0.1f
                    setTextSize(textSizef)
                }).setThumbIndex(currentIndex).withAnimation(false).applay(this)

    }

    @OnClick(R.id.iv_back)
    fun click(view: View) {

        when (view.id) {
            R.id.iv_back -> if (fontSliderBar!!.currentIndex != currentIndex) {
                if (isClickable) {
                    isClickable = false
                    refresh()
                }
            } else {
                finish()
            }
        }
    }

    private fun setTextSize(textSize: Float) {
        //改变当前页面的字体大小
        val size1 = textsize1 * textSize
        val size2 = textsize2 * textSize
        val size3 = textsize3 * textSize
        tvContent1!!.textSize = ScreenUtils.px2sp(this@FontSizeActivity, size1).toFloat()
        tvContent2!!.textSize = ScreenUtils.px2sp(this@FontSizeActivity, size2).toFloat()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (currentIndex != fontSliderBar!!.currentIndex) {
                if (isClickable) {
                    isClickable = false
                    refresh()
                }
            } else {
                finish()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun refresh() {
        //存储标尺的下标
        SPUtils.setParam(this, "currentIndex", fontSliderBar!!.currentIndex)
        //通知主页面重启
        EventBus.getDefault().post(RestartMainEvent(this))

    }

    public override fun onDestroy() {
        super.onDestroy()
        LogUtil.d(TAG, "$TAG   onDestroy--------")
    }

    override fun initPresenter(): BasePresenter<*>? {
        return null
    }

    override fun loadData() {

    }

    companion object {

        fun startActivity(context: Context) {
            val intent = Intent(context, FontSizeActivity::class.java)
            context.startActivity(intent)
        }
    }
}
