package com.android.learn.base.activity

import android.content.Context
import android.os.Bundle

import com.android.learn.base.mpresenter.BasePresenter
import com.android.learn.base.mview.BaseView
import com.android.learn.base.utils.LanguageUtil
import org.greenrobot.eventbus.EventBus


abstract class BaseMvpActivity<P : BasePresenter<V>, V : BaseView> : BaseActivity() {

    var mPresenter: P? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = initPresenter()
        if (mPresenter != null)
            mPresenter!!.attach(this  as? V)
        loadData()
    }

    override fun onDestroy() {
        if (mPresenter != null)
            mPresenter!!.dettach()
        super.onDestroy()
    }

    override fun attachBaseContext(newBase: Context) {
        //语言切换
        super.attachBaseContext(LanguageUtil.setLocal(newBase))
    }

    //实例presenter
    abstract fun initPresenter(): P

    //加载数据
    protected abstract fun loadData()
}
