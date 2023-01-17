package com.android.learn.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.android.learn.R
import com.android.base.activity.BaseActivity
import com.android.base.event.RestartMainEvent
import com.android.base.utils.LogUtil
import com.android.base.utils.SPUtils
import com.android.base.utils.ScreenUtils
import com.android.learn.view.fontsliderbar.FontSliderBar
import org.greenrobot.eventbus.EventBus


class FontSizeActivity : BaseActivity() {
    @BindView(R.id.iv_back)
    lateinit var iv_back: ImageView
    @BindView(R.id.title)
    lateinit var title: TextView
    @BindView(R.id.fontSliderBar)
    lateinit var fontSliderBar: FontSliderBar
    @BindView(R.id.tv_chatcontent1)
    lateinit var tvContent1: TextView
    @BindView(R.id.tv_chatcontent)
    lateinit var tvContent2: TextView
    @BindView(R.id.iv_userhead)
    lateinit var ivUserhead: ImageView
    private var textsize1: Float = 0.toFloat()
    private var textsize2: Float = 0.toFloat()
    private val textsize3: Float = 0.toFloat()
    private var textSizef: Float = 0.toFloat()//缩放比例
    private var isClickable = true
    internal var currentIndex: Int = 0
    internal var TAG = "FontSizeActivity"

    override val layoutId: Int
        get() = R.layout.activity_fontsizes

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initData(bundle: Bundle?) {
        title.text = getString(R.string.font_size)
        iv_back.visibility = View.VISIBLE
        initData()
    }

    private fun initData() {
        currentIndex = SPUtils.getParam(this, "currentIndex", 1) as Int
        textSizef = 1 + currentIndex * 0.1f
        val size1 = tvContent1.textSize
        val size2 = tvContent2.textSize
        textsize1 = size1 / textSizef
        textsize2 = size2 / textSizef
        val listener = object : FontSliderBar.OnSliderBarChangeListener {
            override fun onIndexChanged(rangeBar: FontSliderBar, index: Int) {
                var index = index
                if (index > 5) {
                    return
                }
                index = index - 1
                val textSizef = 1 + index * 0.1f
                setTextSize(textSizef)
            }
        }
        fontSliderBar.setTickCount(6).setTickHeight(ScreenUtils.dp2px(this@FontSizeActivity, 15).toFloat()).setBarColor(Color.GRAY)
                .setTextColor(Color.BLACK).setTextPadding(ScreenUtils.dp2px(this@FontSizeActivity, 10)).setTextSize(
                ScreenUtils.dp2px(this@FontSizeActivity, 14))
                .setThumbRadius(ScreenUtils.dp2px(this@FontSizeActivity, 10).toFloat()).setThumbColorNormal(Color.GRAY).setThumbColorPressed(Color.GRAY)
                .setOnSliderBarChangeListener(listener).setThumbIndex(currentIndex).withAnimation(false).applay(this)

    }

    @OnClick(R.id.iv_back)
    fun click(view: View) {

        when (view.id) {
            R.id.iv_back -> if (fontSliderBar.currentIndex != currentIndex) {
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
        tvContent1.textSize = ScreenUtils.px2sp(this@FontSizeActivity, size1).toFloat()
        tvContent2.textSize = ScreenUtils.px2sp(this@FontSizeActivity, size2).toFloat()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (currentIndex != fontSliderBar.currentIndex) {
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
        SPUtils.setParam(this, "currentIndex", fontSliderBar.currentIndex)
        //通知主页面重启
        EventBus.getDefault().post(RestartMainEvent(this))

    }

    public override fun onDestroy() {
        super.onDestroy()
        LogUtil.d(TAG, "$TAG   onDestroy--------")
    }


    companion object {

        fun startActivity(context: Context) {
            val intent = Intent(context, FontSizeActivity::class.java)
            context.startActivity(intent)
        }
    }
}
