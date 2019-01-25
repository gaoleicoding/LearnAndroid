package com.android.learn.fragment

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View

import com.android.learn.R
import com.android.learn.adapter.HorizontalPagerAdapter
import com.android.learn.base.activity.BaseActivity
import com.android.learn.base.fragment.BaseMvpFragment
import com.android.learn.base.mmodel.TreeBean
import com.android.learn.base.thirdframe.rxjava.BaseObserver
import com.android.learn.base.view.CustomProgressDialog
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

    override fun showKnowledge(datas: List<TreeBean>) {
        mPagerAdapter = HorizontalPagerAdapter(context, datas)
        horizontalInfiniteCycleViewPager!!.adapter = mPagerAdapter
        if (horizontalInfiniteCycleViewPager != null && datas.size > 1)
            horizontalInfiniteCycleViewPager!!.currentItem = 1
        horizontalInfiniteCycleViewPager!!.post { CustomProgressDialog.cancel() }

    }
}
