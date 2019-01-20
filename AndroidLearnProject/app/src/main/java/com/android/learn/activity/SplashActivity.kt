package com.android.learn.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View

import com.android.learn.MainActivity
import com.android.learn.R
import com.android.learn.base.activity.BaseMvpActivity
import com.android.learn.base.mmodel.RegisterLoginData
import com.android.learn.base.utils.SPUtils
import com.android.learn.base.utils.account.UserUtil
import com.android.learn.mcontract.SplashLoginContract
import com.android.learn.mpresenter.SplashLoginPresenter
import com.jaeger.library.StatusBarUtil

class SplashActivity : BaseMvpActivity<SplashLoginPresenter>(), SplashLoginContract.View {


    internal var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    protected override val layoutId: Int
        get() = R.layout.activity_splash

    override fun initData(bundle: Bundle) {}

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
        UserUtil.setUserInfo(loginResData)
        handler.sendEmptyMessageDelayed(0, 1500)
    }

    override fun setStatusBar() {
        StatusBarUtil.setTransparent(this)
    }

    public override fun onDestroy() {
        super.onDestroy()
    }
}
