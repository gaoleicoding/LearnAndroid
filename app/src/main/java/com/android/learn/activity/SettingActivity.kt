package com.android.learn.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.AppCompatCheckBox
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.android.learn.R
import com.android.base.activity.BaseActivity
import com.android.base.event.ChangeNightEvent
import com.android.base.event.LogoutEvent
import com.android.base.event.RestartMainEvent
import com.android.base.thirdframe.retrofit.RetrofitProvider
import com.android.base.utils.SPUtils
import com.android.base.utils.Utils
import com.android.base.utils.account.UserUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class SettingActivity : BaseActivity() {
    @BindView(R.id.iv_back)
    lateinit var iv_back: ImageView
    @BindView(R.id.title)
    lateinit var title: TextView
    @BindView(R.id.tv_versionName)
    lateinit var tv_versionName: TextView
    @BindView(R.id.help_feedback_layout)
    lateinit var help_feedback_layout: LinearLayout
    @BindView(R.id.version_update_layout)
    lateinit var version_update_layout: LinearLayout
    @BindView(R.id.language_switch_layout)
    lateinit var language_switch_layout: LinearLayout
    @BindView(R.id.font_size_layout)
    lateinit var font_size_layout: LinearLayout
    @BindView(R.id.my_logout_layout)
    lateinit var my_logout_layout: LinearLayout
    @BindView(R.id.cb_setting_night)
    lateinit var cb_setting_night: AppCompatCheckBox

    override val layoutId: Int
        get() = R.layout.activity_setting

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initData(bundle: Bundle?) {
        title.text = getString(R.string.my_setting)
        iv_back.visibility = View.VISIBLE
        tv_versionName.text = Utils.getVersionName(this)
        val isNightMode = SPUtils.getParam(this, "nightMode", false) as Boolean
        if (isNightMode) {
            cb_setting_night.isChecked = true
        } else {
            cb_setting_night.isChecked = false
        }
        cb_setting_night.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
                SPUtils.setParam(this@SettingActivity, "nightMode", true)
            else
                SPUtils.setParam(this@SettingActivity, "nightMode", false)
            useNightMode(isChecked)
            EventBus.getDefault().post(ChangeNightEvent())
            setStatusBar()
        }
        EventBus.getDefault().register(this)
    }


    @OnClick(R.id.help_feedback_layout, R.id.version_update_layout, R.id.language_switch_layout, R.id.font_size_layout, R.id.my_logout_layout)
    fun click(view: View) {

        when (view.id) {
            R.id.help_feedback_layout -> FeedbackActivity.startActivity(this@SettingActivity)
            R.id.language_switch_layout -> LanguageActivity.startActivity(this@SettingActivity)
            R.id.font_size_layout -> FontSizeActivity.startActivity(this@SettingActivity)
            R.id.my_logout_layout -> {
                if (!UserUtil.isLogined) {
                    RegisterLoginActivity.startActivity(this)
                    return
                }
                UserUtil.isLogined = false
                EventBus.getDefault().post(LogoutEvent())
                RetrofitProvider.instance.sharedPrefsCookiePersistor.clear()
                finish()
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: RestartMainEvent) {
        finish()
    }

    public override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    companion object {

        fun startActivity(context: Context) {
            val intent = Intent(context, SettingActivity::class.java)
            context.startActivity(intent)
        }
    }
}
