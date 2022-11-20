package com.android.base.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout

import com.android.base.utils.NetUtils

import butterknife.ButterKnife
import butterknife.Unbinder
import com.android.base.R


abstract class BaseFragment : Fragment(), View.OnClickListener {
    private var mLlContent: FrameLayout? = null
    lateinit var subFragmentView: View
    private var bt_error_refresh: Button? = null
    lateinit var mErrorPageView: LinearLayout
    private var mBinder: Unbinder? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mParentView = inflater.inflate(R.layout.fragment_base, container, false)
        initBaseView(mParentView)
        addContentView(inflater)
        var bundle = arguments
        if (bundle == null) {
            bundle = savedInstanceState
        }
        initData(bundle)
        initView(mParentView)
        return mParentView
    }

    /**
     * 用于布局加载完毕，子Fragment可以开始初始化数据
     *
     * @param bundle
     */
    abstract fun initData(bundle: Bundle?)

    abstract fun initView(view: View)

    private fun initBaseView(view: View) {
        mLlContent = view.findViewById(R.id.base_fragment_content)
        mErrorPageView = view.findViewById(R.id.ll_base_error_content)
        bt_error_refresh = view.findViewById(R.id.bt_error_refresh)
        //        if (!NetworkUtil.isNetworkAvailable(getActivity()))
        //            showErrorPage(true);
        bt_error_refresh!!.setOnClickListener(this)
    }

    /**
     * 设置子布局layout
     */
    abstract fun setContentLayout(): Int

    abstract fun reload()

    /**
     * 设置内容
     */
    fun addContentView(inflater: LayoutInflater) {
        subFragmentView = inflater.inflate(setContentLayout(), null)
        mLlContent!!.addView(subFragmentView)
        mBinder = ButterKnife.bind(this, subFragmentView)

    }


    /**
     * 显示/隐藏 错误页面
     *
     * @param isShow
     */
    fun showErrorPage(isShow: Boolean) {
        mErrorPageView.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    override fun onClick(v: View) {
        if (v.id == R.id.bt_error_refresh) {
            if (NetUtils.isConnected)
                mErrorPageView.visibility = View.GONE
            reload()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        if (mBinder != null) {
            mBinder!!.unbind()
        }
    }
}
