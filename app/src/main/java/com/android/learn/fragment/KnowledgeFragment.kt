package com.android.learn.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View

import com.android.learn.R
import com.android.learn.adapter.HorizontalPagerAdapter
import com.android.base.fragment.BaseMvpFragment
import com.android.base.mmodel.TreeBean
import com.android.base.view.CustomProgressDialog
import com.android.learn.mcontract.KnowledgeContract
import com.android.learn.mpresenter.KnowledgePresenter
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager

import butterknife.BindView


class KnowledgeFragment : BaseMvpFragment<KnowledgePresenter, KnowledgeContract.View>(), KnowledgeContract.View {
    @BindView(R.id.hicvp)
    lateinit var horizontalInfiniteCycleViewPager: HorizontalInfiniteCycleViewPager
    private var mPagerAdapter: HorizontalPagerAdapter? = null

    internal var handler: Handler = object : Handler() {
        override fun handleMessage(message: Message) {
            CustomProgressDialog.show(activity)
        }
    }

    override fun initData(bundle: Bundle?) {}

    override fun initView(view: View) {}

    override fun setContentLayout(): Int {
        return R.layout.fragment_knowledge
    }

    override fun reload() {}

    override fun initPresenter(): KnowledgePresenter {
        return KnowledgePresenter()
    }

    override fun loadData() {

        CustomProgressDialog.show(activity)
        mPresenter!!.getKnowledge()
    }

    override fun showKnowledge(data: List<TreeBean>) {
        mPagerAdapter = HorizontalPagerAdapter(context, data)
        horizontalInfiniteCycleViewPager.adapter = mPagerAdapter
        if (data.size > 1)
            horizontalInfiniteCycleViewPager.currentItem = 1
        horizontalInfiniteCycleViewPager.post { CustomProgressDialog.cancel() }

    }
}
