package com.android.learn.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.AppCompatCheckBox
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.android.learn.R
import com.android.learn.base.activity.BaseActivity
import com.android.learn.base.event.RestartMainEvent
import com.android.learn.base.utils.LanguageUtil
import com.android.learn.base.utils.LogUtil
import com.android.learn.base.utils.SPUtils
import org.greenrobot.eventbus.EventBus


class LanguageActivity : BaseActivity() {
    @BindView(R.id.iv_back)
    lateinit var iv_back: ImageView
    @BindView(R.id.title)
    lateinit var title: TextView
    @BindView(R.id.cb_system)
    lateinit var cb_system: AppCompatCheckBox
    @BindView(R.id.cb_chinese)
    lateinit var cb_chinese: AppCompatCheckBox
    @BindView(R.id.cb_english)
    lateinit var cb_english: AppCompatCheckBox
    internal var TAG = "LanguageActivity"

    override val layoutId: Int
        get() = R.layout.activity_language

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initData(bundle: Bundle?) {
        title!!.text = getString(R.string.language_set)
        iv_back!!.visibility = View.VISIBLE

        val language = SPUtils.getParam(this, "language", 0) as Int
        if (language == 0) {
            cb_system!!.isChecked = true
        } else if (language == 1) {
            cb_chinese!!.isChecked = true
        } else if (language == 2) {
            cb_english!!.isChecked = true
        }

    }


    private fun selectLanguage(select: Int) {
        LanguageUtil.saveSelectLanguage(this, select)
        EventBus.getDefault().post(RestartMainEvent(this))
    }


    @OnClick(R.id.cb_system, R.id.cb_chinese, R.id.cb_english)
    fun click(view: View) {
        cb_system!!.isChecked = false
        cb_chinese!!.isChecked = false
        cb_english!!.isChecked = false


        when (view.id) {
            R.id.cb_system -> selectLanguage(0)
            R.id.cb_chinese -> selectLanguage(1)
            R.id.cb_english -> selectLanguage(2)
        }
    }


    public override fun onDestroy() {
        super.onDestroy()
        LogUtil.d(TAG, "$TAG   onDestroy--------")
    }

    companion object {

        fun startActivity(context: Context) {
            val intent = Intent(context, LanguageActivity::class.java)
            context.startActivity(intent)
        }
    }
}
