package com.android.learn.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import com.android.learn.MainActivity
import com.android.learn.R
import com.android.base.activity.BaseMvpActivity
import com.android.base.mmodel.RegisterLoginData
import com.android.base.utils.SPUtils
import com.android.base.utils.account.UserUtil
import com.android.learn.mcontract.SplashLoginContract
import com.android.learn.mpresenter.SplashLoginPresenter
import com.jaeger.library.StatusBarUtil

class SplashActivity : BaseMvpActivity<SplashLoginPresenter, SplashLoginContract.View>(), SplashLoginContract.View {


    internal var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override val layoutId: Int
        get() = R.layout.activity_splash

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun initData(bundle: Bundle?) {}

    override fun initPresenter(): SplashLoginPresenter {
        return SplashLoginPresenter()
    }

    override fun loadData() {
        val phone_num = SPUtils.getParam(this, "username", "") as String
        val password = SPUtils.getParam(this, "password", "") as String
        if (phone_num.length > 0 && password.length > 0) {
            mPresenter!!.login(phone_num, password)
        } else
            handler.sendEmptyMessageDelayed(0, 2000)
    }

    fun jumpToMainActivity(view: View) {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        handler.removeCallbacksAndMessages(null)
        finish()
    }


    override fun showLoginResData(loginResData: RegisterLoginData) {
        UserUtil.assignUserInfo(loginResData)
        handler.sendEmptyMessageDelayed(0, 1500)
    }

    override fun setStatusBar() {
        StatusBarUtil.setTranslucent(this,0)
        Log.d("gaolei", "setStatusBar")
    }

    public override fun onDestroy() {
        super.onDestroy()
    }
}