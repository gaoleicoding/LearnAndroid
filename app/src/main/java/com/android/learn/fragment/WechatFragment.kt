package com.android.learn.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import butterknife.BindView
import com.android.learn.R
import com.android.base.fragment.BaseMvpFragment
import com.android.base.mmodel.WxArticle
import com.android.base.view.colorfultab.ColorClipTabLayout
import com.android.learn.mcontract.WechatContract
import com.android.learn.mpresenter.WechatPresenter
import java.util.*


class WechatFragment : BaseMvpFragment<WechatPresenter, WechatContract.View>(), WechatContract.View {

    @BindView(R.id.tab_layout)
    lateinit var tab_layout: ColorClipTabLayout
    @BindView(R.id.view_pager)
    lateinit var view_pager: ViewPager

    internal var fragmentList: MutableList<Fragment> = ArrayList()
    internal var titleList: MutableList<String> = ArrayList()

    override fun loadData() {
        mPresenter!!.getWxArticle()
    }

    override fun initData(bundle: Bundle?) {

    }

    override fun initView(view: View) {

    }

    override fun setContentLayout(): Int {
        return R.layout.fragment_wechat
    }

    override fun reload() {}

    override fun initPresenter(): WechatPresenter {
        return WechatPresenter()
    }

    override fun showWxArticle(list: List<WxArticle>) {
        var firstSubFragment: WechatSubFragment? = null
        var firstId = 0
        val size = list.size
        for (i in 0 until size) {
            val wxArticle = list[i]
            titleList.add(wxArticle.name)
            val wechatSubFragment = WechatSubFragment.newInstance(wxArticle.id, wxArticle.name)
            fragmentList.add(wechatSubFragment)
            if (i == 0) {
                firstId = wxArticle.id
                firstSubFragment = wechatSubFragment
            }
        }
        val adapter = CustomPagerAdapter(fragmentManager, fragmentList)
        view_pager.adapter = adapter
        view_pager.offscreenPageLimit = list.size
        tab_layout.setupWithViewPager(view_pager)
        firstSubFragment!!.userId = firstId
        firstSubFragment.loadData()
    }


    inner class CustomPagerAdapter(fm: FragmentManager?, private val mFragments: List<Fragment>) : FragmentPagerAdapter(fm) {

        init {
            fm?.beginTransaction()?.commitAllowingStateLoss()
        }

        override fun getItem(position: Int): Fragment {
            return this.mFragments[position]
        }

        override fun getCount(): Int {
            return this.mFragments.size
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titleList[position]
        }
    }
}
