package com.android.learn.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.View
import android.widget.ImageView
import android.widget.TextView


import com.android.learn.R
import com.android.learn.base.activity.BaseMvpActivity
import com.android.learn.base.mpresenter.BasePresenter
import com.android.learn.fragment.TodoFragment
import com.android.learn.view.CustomViewPager

import java.util.ArrayList

import butterknife.BindView
import butterknife.OnClick

class MyTodoActivity : BaseMvpActivity<*>() {
    @BindView(R.id.iv_back)
    internal var iv_back: ImageView? = null
    @BindView(R.id.iv_search)
    internal var iv_search: ImageView? = null
    @BindView(R.id.title)
    internal var tv_title: TextView? = null
    @BindView(R.id.tabLayout)
    internal var tabLayout: TabLayout? = null
    @BindView(R.id.viewPager)
    internal var viewPager: CustomViewPager? = null
    internal var todoFragment: TodoFragment
    internal var doneFragment: TodoFragment

    private val mFragments = ArrayList<Fragment>()

    protected override val layoutId: Int
        get() = R.layout.activity_my_todo

    private fun initTab() {

        tabLayout!!.getTabAt(0)!!.setCustomView(R.layout.tab_todo)
        tabLayout!!.getTabAt(1)!!.setCustomView(R.layout.tab_done)


        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            //标签选中之后执行的方法
            override fun onTabSelected(tab: TabLayout.Tab) {
                //                title.setText(titles.get(tab.getPosition()));
                if (tab.position == 0)
                    iv_search!!.visibility = View.VISIBLE
                if (tab.position == 1)
                    iv_search!!.visibility = View.GONE

            }

            //标签没选中
            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
        //默认选中的Tab
        tabLayout!!.getTabAt(0)!!.customView!!.isSelected = true
    }

    override fun initData(bundle: Bundle) {
        tv_title!!.text = getString(R.string.todo)
        iv_back!!.visibility = View.VISIBLE
        iv_search!!.visibility = View.VISIBLE
        iv_search!!.setImageResource(R.drawable.add_todo)
        val bundle1 = Bundle()
        bundle1.putInt("position", 0)
        todoFragment = TodoFragment.newInstance(bundle1)
        val bundle2 = Bundle()
        bundle2.putInt("position", 1)
        doneFragment = TodoFragment.newInstance(bundle2)
        mFragments.add(todoFragment)
        mFragments.add(doneFragment)
        val adapter = CourseDiscussAdapter(supportFragmentManager, mFragments)
        viewPager!!.offscreenPageLimit = mFragments.size
        viewPager!!.adapter = adapter
        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout!!.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))
        //将TabLayout和ViewPager关联起来
        tabLayout!!.setupWithViewPager(viewPager)
        initTab()
    }

    @OnClick(R.id.iv_search)
    fun click(view: View) {
        when (view.id) {
            R.id.iv_search -> TodoAddActivity.startActivity(this@MyTodoActivity, null)
        }

    }

    override fun initPresenter(): BasePresenter<*>? {
        return null
    }

    override fun loadData() {

    }

    inner class CourseDiscussAdapter(fm: FragmentManager, internal var fragments: List<Fragment>) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {

            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }
    }

    companion object {

        fun startActivity(context: Context) {
            val intent = Intent(context, MyTodoActivity::class.java)
            context.startActivity(intent)
        }
    }
}
