package com.android.learn.fragment

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.android.learn.R
import com.android.learn.activity.MyCollectActivity
import com.android.learn.activity.MyTodoActivity
import com.android.learn.activity.RegisterLoginActivity
import com.android.learn.activity.SettingActivity
import com.android.learn.base.event.LoginEvent
import com.android.learn.base.event.LogoutEvent
import com.android.learn.base.fragment.BaseMvpFragment
import com.android.learn.base.utils.SPUtils
import com.android.learn.base.utils.Utils
import com.android.learn.base.utils.account.UserUtil
import com.android.learn.mcontract.UserInfoContract
import com.android.learn.mpresenter.UserInfoPresenter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

import butterknife.BindView
import butterknife.OnClick


class UserFragment : BaseMvpFragment<UserInfoPresenter, UserInfoContract.View>(), UserInfoContract.View {

    @BindView(R.id.iv_user_photo)
    lateinit var iv_user_photo: ImageView
    @BindView(R.id.tv_user_profile_not_login)
    lateinit internal var tv_user_profile_not_login: TextView

    override fun initData(bundle: Bundle?) {
        EventBus.getDefault().register(this)
    }

    override fun initView(view: View) {
        refreshUserInfo()
    }

    @OnClick(R.id.iv_user_photo, R.id.my_setting_layout, R.id.my_collect_layout, R.id.my_todo_layout)
    fun click(view: View) {
        when (view.id) {
            R.id.my_collect_layout -> {
                if (!UserUtil.isLogined) {
                    RegisterLoginActivity.startActivity(activity!!)
                    Utils.showToast(getString(R.string.user_not_login), true)
                    return
                }
                MyCollectActivity.startActivity(activity!!)
            }
            R.id.my_todo_layout -> MyTodoActivity.startActivity(activity!!)
            R.id.my_setting_layout -> SettingActivity.startActivity(activity!!)

            R.id.iv_user_photo -> if (!UserUtil.isLogined) {
                RegisterLoginActivity.startActivity(activity!!)
            }
        }
    }

    override fun setContentLayout(): Int {
        return R.layout.fragment_user
    }

    override fun reload() {}

    override fun initPresenter(): UserInfoPresenter {
        return UserInfoPresenter()
    }

    override fun loadData() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(accountEvent: LoginEvent) {
        refreshUserInfo()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(logoutEvent: LogoutEvent) {
        logout()
    }


    fun refreshUserInfo() {
        if (UserUtil.gainUserInfo() == null) return
        if (UserUtil.isLogined) {
            val username = UserUtil.gainUserInfo()?.data?.username
            if (tv_user_profile_not_login != null)
                tv_user_profile_not_login!!.text = username
            val photoUrl = UserUtil.gainUserInfo()?.data?.icon
            if (photoUrl != null) {
                val options = RequestOptions().placeholder(R.drawable.user_default_photo)
                if (iv_user_photo != null)
                    Glide.with(activity!!).load(photoUrl).apply(options).into(iv_user_photo!!)
            }

        } else {

        }

    }

    private fun logout() {

        tv_user_profile_not_login!!.text = getString(R.string.login_register)
        iv_user_photo!!.setImageResource(R.drawable.user_default_photo)
        SPUtils.clear(activity!!, "phone_num")
        SPUtils.clear(activity!!, "password")
        mPresenter!!.getLogoutData()
        RegisterLoginActivity.startActivity(activity!!)
        UserUtil.assignUserInfo(null)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(activity)
    }
}
