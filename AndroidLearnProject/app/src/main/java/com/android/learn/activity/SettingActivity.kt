package com.android.learn.activity

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.AppCompatCheckBox
import android.view.View
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.android.learn.R
import com.android.learn.base.activity.BaseActivity
import com.android.learn.base.activity.BaseMvpActivity
import com.android.learn.base.event.ChangeNightEvent
import com.android.learn.base.event.LogoutEvent
import com.android.learn.base.event.RestartMainEvent
import com.android.learn.base.mpresenter.BasePresenter
import com.android.learn.base.thirdframe.retrofit.RetrofitProvider
import com.android.learn.base.utils.LanguageUtil
import com.android.learn.base.utils.SPUtils
import com.android.learn.base.utils.Utils
import com.android.learn.base.utils.account.UserUtil

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

import butterknife.BindView
import butterknife.OnClick


class SettingActivity : BaseMvpActivity<*>() {
    @BindView(R.id.iv_back)
    internal var iv_back: ImageView? = null
    @BindView(R.id.title)
    internal var title: TextView? = null
    @BindView(R.id.tv_versionName)
    internal var tv_versionName: TextView? = null
    @BindView(R.id.help_feedback_layout)
    internal var help_feedback_layout: LinearLayout? = null
    @BindView(R.id.version_update_layout)
    internal var version_update_layout: LinearLayout? = null
    @BindView(R.id.language_switch_layout)
    internal var language_switch_layout: LinearLayout? = null
    @BindView(R.id.font_size_layout)
    internal var font_size_layout: LinearLayout? = null
    @BindView(R.id.my_logout_layout)
    internal var my_logout_layout: LinearLayout? = null
    @BindView(R.id.cb_setting_night)
    internal var cb_setting_night: AppCompatCheckBox? = null

    protected override val layoutId: Int
        get() = R.layout.activity_setting

    //    private void checkUpdate() {
    //
    //        if (OnlineParamUtil.getParamResData() != null && OnlineParamUtil.getParamResData().rspBody != null) {
    //            String android_versionCode = OnlineParamUtil.getParamResData().rspBody.android_versionCode.content.trim();
    //
    //            String android_update_content = OnlineParamUtil.getParamResData().rspBody.android_update_content.content.trim();
    //            String android_must_update = OnlineParamUtil.getParamResData().rspBody.android_must_update.content.trim();
    //            final String android_app_download_url = OnlineParamUtil.getParamResData().rspBody.android_app_download_url.content.trim();
    //            if (Utils.stringToInt(android_versionCode) <= Utils.getVersionCode(this)) {
    //                Utils.showToast("当前是最新版本", true);
    //                return;
    //            }
    //            AlertDialog.Builder builder;
    //            builder = new AlertDialog.Builder(SettingActivity.this);
    //            builder.setTitle("升级提醒");
    //            builder.setIcon(R.drawable.update);
    //            builder.setMessage(android_update_content);
    //            builder.setCancelable(false);
    //            if ("false".equals(android_must_update)) {
    //                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
    //                    @Override
    //                    public void onClick(DialogInterface dialog, int which) {
    //                        dialog.cancel();
    //
    //                    }
    //                });
    //            }
    //            builder.setPositiveButton("升级", new DialogInterface.OnClickListener() {
    //                @Override
    //                public void onClick(DialogInterface dialog, int which) {
    //                    dialog.cancel();
    //                    UpdateApk.downFile(android_app_download_url, SettingActivity.this);
    //                }
    //            });
    //            builder.show();
    //        }
    //    }
    internal var yourChoice: Int = 0


    override fun initData(bundle: Bundle) {
        title!!.text = getString(R.string.my_setting)
        iv_back!!.visibility = View.VISIBLE
        tv_versionName!!.text = Utils.getVersionName(this)
        val isNightMode = SPUtils.getParam(this, "nightMode", false) as Boolean
        if (isNightMode) {
            cb_setting_night!!.isChecked = true
        } else {
            cb_setting_night!!.isChecked = false
        }
        cb_setting_night!!.setOnCheckedChangeListener { buttonView, isChecked ->
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
                RetrofitProvider.instance!!.sharedPrefsCookiePersistor.clear()
                finish()
            }
        }
    }

    override fun initPresenter(): BasePresenter<*>? {
        return null
    }

    override fun loadData() {

    }

    private fun showSingleChoiceDialog() {
        val items = arrayOf("跟随系统", "简体中文", "English")
        yourChoice = -1
        val singleChoiceDialog = AlertDialog.Builder(this@SettingActivity)
        singleChoiceDialog.setTitle("选择语言")
        // 第二个参数是默认选项，此处设置为0
        singleChoiceDialog.setSingleChoiceItems(items, 0
        ) { dialog, which -> yourChoice = which }
        singleChoiceDialog.setPositiveButton("确定"
        ) { dialog, which -> SPUtils.setParam(this@SettingActivity, "languageIndex", which) }
        singleChoiceDialog.show()
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
