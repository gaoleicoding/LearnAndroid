package com.android.learn.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.android.learn.R
import com.android.learn.activity.KnowledgeChildActivity
import com.android.learn.base.mmodel.TreeBean
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

import java.util.ArrayList


class HorizontalPagerAdapter(private val mContext: Context?, mList: List<TreeBean>) : PagerAdapter() {
    private val mLayoutInflater: LayoutInflater
    private var mList: List<TreeBean> = ArrayList()

    init {
        this.mList = mList
        mLayoutInflater = LayoutInflater.from(mContext)
    }

    fun setList(list: List<TreeBean>) {
        mList = list
    }

    override fun getCount(): Int {
        return mList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view: View
        val page: Int
        view = mLayoutInflater.inflate(R.layout.item_tree, container, false)
        val txt = view.findViewById<View>(R.id.txt_item) as TextView
        val treeBean = mList[position]
        txt.text = treeBean.name
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        val adapter = ChildrenAdapter(treeBean.children)
        recyclerView.adapter = adapter
        adapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            KnowledgeChildActivity.startTreeChildrenActivity(mContext,
                    treeBean.children[position].id,
                    treeBean.children[position].name)
        }
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    private inner class ChildrenAdapter(data: List<TreeBean.ChildrenBean>?) : BaseQuickAdapter<TreeBean.ChildrenBean, BaseViewHolder>(R.layout.item_tree_children, data) {

        override fun convert(helper: BaseViewHolder, item: TreeBean.ChildrenBean) {
            helper.setText(R.id.tv, item.name)
        }
    }
}
