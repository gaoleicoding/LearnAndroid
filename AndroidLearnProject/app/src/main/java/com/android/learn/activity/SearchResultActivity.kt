package com.android.learn.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.android.learn.R
import com.android.learn.adapter.ArticleQuickAdapter
import com.android.learn.adapter.DividerItemDecoration
import com.android.learn.base.activity.BaseMvpActivity
import com.android.learn.base.mmodel.FeedArticleListData
import com.android.learn.base.mmodel.FeedArticleListData.FeedArticleData
import com.android.learn.base.mmodel.TodoData
import com.android.learn.base.utils.LanguageUtil
import com.android.learn.base.utils.LogUtil
import com.android.learn.base.utils.Utils
import com.android.learn.mcontract.SearchContract
import com.android.learn.mpresenter.SearchPresenter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener

import java.util.ArrayList

import butterknife.BindView

class SearchResultActivity : BaseMvpActivity<SearchPresenter, SearchContract.View>(), SearchContract.View {
    @BindView(R.id.iv_back)
    lateinit var iv_back: ImageView
    @BindView(R.id.title)
    lateinit var title: TextView
    @BindView(R.id.tv_empty_knowledge)
    lateinit var tv_empty_knowledge: TextView
    @BindView(R.id.article_recyclerview)
    lateinit var article_recyclerview: RecyclerView
    @BindView(R.id.smartRefreshLayout)
    lateinit var smartRefreshLayout: SmartRefreshLayout
    private var feedArticleAdapter: ArticleQuickAdapter? = null
    lateinit var keyword: String
    internal var TAG = "KnowledgeChildActivity"

    override val layoutId: Int
        get() = R.layout.activity_knowledge_article

    override fun initData(bundle: Bundle?) {
        keyword = bundle!!.getString("key")
        title!!.text = keyword
        iv_back!!.visibility = View.VISIBLE
        initSmartRefreshLayout()
        initRecyclerView()
    }


    override fun initPresenter(): SearchPresenter {
        return SearchPresenter()
    }

    override fun loadData() {
        mPresenter!!.getFeedArticleList(keyword)
    }

    override fun showArticleList(listData: FeedArticleListData) {
        val newDataList = listData.datas
        if (newDataList == null || newDataList.size == 0) {
            smartRefreshLayout!!.finishLoadMoreWithNoMoreData()
        }
        smartRefreshLayout!!.finishLoadMore()

        feedArticleAdapter!!.addData(newDataList!!)

        if (feedArticleAdapter!!.data.size == 0) {
            tv_empty_knowledge!!.visibility = View.VISIBLE
        } else
            tv_empty_knowledge!!.visibility = View.GONE
    }

    override fun showCollectArticleData(position: Int, feedArticleData: FeedArticleData) {
        feedArticleAdapter!!.setData(position, feedArticleData)
    }

    override fun showCancelCollectArticleData(position: Int, feedArticleData: FeedArticleData) {
        feedArticleAdapter!!.setData(position, feedArticleData)
    }

    private fun initRecyclerView() {
        val articleDataList = ArrayList<FeedArticleData>()
        feedArticleAdapter = ArticleQuickAdapter(this, articleDataList, "KnowledgeChildActivity")
        article_recyclerview!!.addItemDecoration(DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST))
        article_recyclerview!!.layoutManager = LinearLayoutManager(this)

        //解决数据加载完成后, 没有停留在顶部的问题
        article_recyclerview!!.isFocusable = false
        article_recyclerview!!.adapter = feedArticleAdapter
        feedArticleAdapter!!.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            val intent = Intent(this@SearchResultActivity, ArticleDetailActivity::class.java)
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
        smartRefreshLayout!!.isEnableLoadMore = true
        smartRefreshLayout!!.isEnableRefresh = false
        smartRefreshLayout!!.isEnableScrollContentWhenLoaded = true//是否在加载完成时滚动列表显示新的内容
        smartRefreshLayout!!.setEnableFooterFollowWhenLoadFinished(true)
        smartRefreshLayout!!.setOnLoadMoreListener { mPresenter!!.onLoadMore(keyword) }
    }

    companion object {


        fun startActivity(context: Context, bundle: Bundle) {
            val intent = Intent(context, SearchResultActivity::class.java)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }


}
