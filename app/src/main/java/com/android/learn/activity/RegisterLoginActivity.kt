package com.android.learn.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.android.base.activity.BaseMvpActivity
import com.android.base.mmodel.RegisterLoginData
import com.android.base.utils.SPUtils
import com.android.base.utils.Utils
import com.android.base.utils.account.UserUtil
import com.android.learn.R
import com.android.learn.mcontract.RegisterLoginContract
import com.android.learn.mpresenter.RegisterLoginPresenter


class RegisterLoginActivity : BaseMvpActivity<RegisterLoginPresenter, RegisterLoginContract.View>(),
    RegisterLoginContract.View {
    @BindView(R.id.iv_back)
    lateinit var iv_back: ImageView
    @BindView(R.id.title)
    lateinit var title: TextView
    @BindView(R.id.tv_login)
    lateinit var tv_login: TextView
    @BindView(R.id.tv_register_free)
    lateinit var tv_register_free: TextView
    @BindView(R.id.tv_register)
    lateinit var tv_register: TextView
    @BindView(R.id.layout_login)
    lateinit var layout_login: LinearLayout
    @BindView(R.id.layout_register)
    lateinit var layout_register: LinearLayout
    @BindView(R.id.et_register_phone_num)
    lateinit var et_register_phone_num: EditText
    @BindView(R.id.et_register_password)
    lateinit var et_register_password: EditText
    @BindView(R.id.et_register_confirm_password)
    lateinit var et_register_confirm_password: EditText
    @BindView(R.id.et_login_phone_num)
    lateinit var et_login_phone_num: EditText
    @BindView(R.id.et_login_password)
    lateinit var et_login_password: EditText
    internal var isInRegister = false
    lateinit var registerFlag: String

    override val layoutId: Int
        get() = R.layout.activity_login_register

    override fun initData(bundle: Bundle?) {
        title.text = getString(R.string.login)
        iv_back.visibility = View.VISIBLE
    }

    @OnClick(R.id.iv_back, R.id.tv_login, R.id.tv_register_free, R.id.tv_register)
    fun click(view: View) {
        when (view.id) {
            R.id.iv_back -> back()
            R.id.tv_login -> {
                val username = et_login_phone_num.text.toString()
                val password = et_login_password.text.toString()
                mPresenter!!.login(username, password)
            }
            R.id.tv_register_free -> {
                layout_register.visibility = View.VISIBLE
                title.text = getString(R.string.register)
                isInRegister = true
            }

            R.id.tv_register -> {
                registerFlag = "tv_register"

                val username = et_register_phone_num.text.toString().trim { it <= ' ' }
                val password = et_register_password.text.toString().trim { it <= ' ' }
                val confirmPassword =
                    et_register_confirm_password.text.toString().trim { it <= ' ' }

                if ("" == username) {
                    Utils.showToast(getString(R.string.please_input_account), true)
                    return
                }
                if (password.length < 6) {
                    Utils.showToast(getString(R.string.register_password_too_short), true)
                    return
                }
                if (password != confirmPassword) {
                    Utils.showToast(getString(R.string.password_dismatch), true)
                    return
                }

                mPresenter!!.register(username, password, confirmPassword)
            }
        }

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            back()
            return true
        }

        return super.onKeyDown(keyCode, event)
    }


    private fun back() {
        if (isInRegister) {
            layout_register.visibility = View.GONE
            title.text = getString(R.string.login)
            isInRegister = false
        } else {
            finish()

        }
    }


    public override fun onDestroy() {
        super.onDestroy()
    }

    override fun initPresenter(): RegisterLoginPresenter {
        return RegisterLoginPresenter()
    }

    override fun loadData() {

    }

    override fun showRegisterResData(registerResData: RegisterLoginData) {
        back()
    }

    override fun showLoginResData(data: RegisterLoginData) {
        UserUtil.assignUserInfo(data)
        SPUtils.setParam(this@RegisterLoginActivity, "username", et_login_phone_num.text.toString())
        SPUtils.setParam(this@RegisterLoginActivity, "password", et_login_password.text.toString())
        finish()
    }

    companion object {

        fun startActivity(context: Context) {
            val intent = Intent(context, RegisterLoginActivity::class.java)
            context.startActivity(intent)
        }
    }
}
