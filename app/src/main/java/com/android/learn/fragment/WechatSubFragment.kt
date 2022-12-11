package com.android.learn.fragment

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import butterknife.BindView
import com.android.learn.R
import com.android.learn.activity.ArticleDetailActivity
import com.android.learn.adapter.ArticleQuickAdapter
import com.android.learn.adapter.DividerItemDecoration
import com.android.base.event.CancelCollectEvent
import com.android.base.event.LoginEvent
import com.android.base.fragment.BaseMvpFragment
import com.android.base.mmodel.FeedArticleListData
import com.android.base.mmodel.FeedArticleListData.FeedArticleData
import com.android.base.view.CustomProgressDialog
import com.android.learn.mcontract.WechatSubContract
import com.android.learn.mpresenter.WechatSubPresenter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*


class WechatSubFragment : BaseMvpFragment<WechatSubPresenter, WechatSubContract.View>(), WechatSubContract.View {
    @BindView(R.id.article_recyclerview)
    lateinit var article_recyclerview: RecyclerView
    @BindView(R.id.smartRefreshLayout)
    lateinit var smartRefreshLayout: SmartRefreshLayout
    lateinit var articleDataList: List<FeedArticleData>
    private var feedArticleAdapter: ArticleQuickAdapter? = null

    internal var userId: Int = 0
    internal var userName: String? = null


    override fun initData(bundle: Bundle?) {
        userId = bundle!!.getInt("id", 408)
        userName = bundle.getString("name")
        initSmartRefreshLayout()
        initRecyclerView()
    }

    override fun initView(view: View) {
        EventBus.getDefault().register(this)
    }

    override fun setContentLayout(): Int {
        return R.layout.fragment_wechat_sub
    }

    override fun reload() {

    }

    override fun initPresenter(): WechatSubPresenter {
        return WechatSubPresenter()
    }

    override public fun loadData() {
        CustomProgressDialog.show(activity)
        if (userId != 0)
            mPresenter!!.getWxArtileById(userId)
    }

    override fun showWxArticleById(datas: FeedArticleListData) {
        val newDataList = datas.datas
        if (newDataList.size == 0) {
            smartRefreshLayout.finishLoadMoreWithNoMoreData()
            return
        }
        smartRefreshLayout.finishLoadMore()
        feedArticleAdapter!!.addData(newDataList)

    }

    override fun showCollectArticleData(position: Int, feedArticleData: FeedArticleListData.FeedArticleData) {
        feedArticleAdapter!!.setData(position, feedArticleData)
    }

    override fun showCancelCollectArticleData(position: Int, feedArticleData: FeedArticleListData.FeedArticleData) {
        feedArticleAdapter!!.setData(position, feedArticleData)
    }

    override fun showCancelCollectArticleData(id: Int) {
        val position = feedArticleAdapter!!.getPosById(id)
        if (position == -1) return
        val feedArticleData = articleDataList[position]
        feedArticleData.isCollect = false
        feedArticleAdapter!!.setData(position, feedArticleData)
    }


    private fun initRecyclerView() {
        articleDataList = ArrayList()
        feedArticleAdapter = ArticleQuickAdapter(activity, articleDataList, "WechatSubFragment")
        article_recyclerview.addItemDecoration(DividerItemDecoration(activity!!,
                DividerItemDecoration.VERTICAL_LIST))
        article_recyclerview.layoutManager = LinearLayoutManager(activity)

        //解决数据加载完成后, 没有停留在顶部的问题
        article_recyclerview.isFocusable = false
        article_recyclerview.adapter = feedArticleAdapter

        feedArticleAdapter!!.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            val intent = Intent(activity, ArticleDetailActivity::class.java)
            val bundle = Bundle()
            bundle.putString("url", feedArticleAdapter!!.data[position].link)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        feedArticleAdapter!!.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
            if (feedArticleAdapter!!.data[position].isCollect) {
                mPresenter!!.cancelCollectArticle(position, feedArticleAdapter!!.data[position])
            } else {
                mPresenter!!.addCollectArticle(position, feedArticleAdapter!!.data[position])
            }
        }
    }

    //初始化下拉刷新控件
    private fun initSmartRefreshLayout() {
        smartRefreshLayout.isEnableLoadMore = true
        smartRefreshLayout.isEnableRefresh = false
        smartRefreshLayout.isEnableScrollContentWhenLoaded = true//是否在加载完成时滚动列表显示新的内容
        smartRefreshLayout.setEnableFooterFollowWhenLoadFinished(true)
        smartRefreshLayout.setOnLoadMoreListener { mPresenter!!.getWxArtileById(userId) }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: CancelCollectEvent) {
        mPresenter!!.cancelCollectArticle(event.id)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(accountEvent: LoginEvent) {
        feedArticleAdapter!!.data.clear()
        feedArticleAdapter!!.notifyDataSetChanged()
        mPresenter!!.getWxArtileById(userId)
        mPresenter!!.num = 0
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    companion object {

        fun newInstance(id: Int, name: String): WechatSubFragment {
            val testFragment = WechatSubFragment()
            val bundle = Bundle()
            bundle.putInt("id", id)
            bundle.putString("name", name)
            testFragment.arguments = bundle
            return testFragment
        }
    }
}
