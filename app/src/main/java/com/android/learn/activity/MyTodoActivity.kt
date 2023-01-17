package com.android.learn.activity


import android.content.Context
import android.content.Intent
import android.os.Bundle

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import butterknife.BindView
import butterknife.OnClick
import com.android.learn.R
import com.android.base.activity.BaseActivity
import com.android.learn.fragment.TodoFragment
import com.android.learn.view.CustomViewPager
import com.google.android.material.tabs.TabLayout
import java.util.*

class MyTodoActivity : BaseActivity() {
    @BindView(R.id.iv_back)
    lateinit var iv_back: ImageView
    @BindView(R.id.iv_search)
    lateinit var iv_search: ImageView
    @BindView(R.id.title)
    lateinit var tv_title: TextView
    @BindView(R.id.tabLayout)
    lateinit var tabLayout: TabLayout
    @BindView(R.id.viewPager)
    lateinit var viewPager: CustomViewPager
    lateinit var todoFragment: TodoFragment
    lateinit var doneFragment: TodoFragment

    private val mFragments = ArrayList<Fragment>()

    override val layoutId: Int
        get() = R.layout.activity_my_todo

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun initTab() {

        tabLayout.getTabAt(0)!!.setCustomView(R.layout.tab_todo)
        tabLayout.getTabAt(1)!!.setCustomView(R.layout.tab_done)


        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            //标签选中之后执行的方法
            override fun onTabSelected(tab: TabLayout.Tab) {
                //                title.setText(titles.get(tab.getPosition()));
                if (tab.position == 0)
                    iv_search.visibility = View.VISIBLE
                if (tab.position == 1)
                    iv_search.visibility = View.GONE

            }

            //标签没选中
            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
        //默认选中的Tab
        tabLayout.getTabAt(0)!!.customView!!.isSelected = true
    }

    override fun initData(bundle: Bundle?) {
        tv_title.text = getString(R.string.todo)
        iv_back.visibility = View.VISIBLE
        iv_search.visibility = View.VISIBLE
        iv_search.setImageResource(R.drawable.add_todo)
        val bundle1 = Bundle()
        bundle1.putInt("position", 0)
        todoFragment = TodoFragment.newInstance(bundle1)
        val bundle2 = Bundle()
        bundle2.putInt("position", 1)
        doneFragment = TodoFragment.newInstance(bundle2)
        mFragments.add(todoFragment)
        mFragments.add(doneFragment)
        val adapter = CourseDiscussAdapter(supportFragmentManager, mFragments)
        viewPager.offscreenPageLimit = mFragments.size
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))
        //将TabLayout和ViewPager关联起来
        tabLayout.setupWithViewPager(viewPager)
        initTab()
    }

    @OnClick(R.id.iv_search)
    fun click(view: View) {
        when (view.id) {
            R.id.iv_search -> TodoAddActivity.startActivity(this@MyTodoActivity, null)
        }
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
