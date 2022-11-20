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
import com.android.base.activity.BaseMvpActivity
import com.android.base.mmodel.FeedArticleListData
import com.android.base.mmodel.FeedArticleListData.FeedArticleData
import com.android.base.utils.LogUtil
import com.android.base.view.CustomProgressDialog
import com.android.learn.mcontract.KnowledgeChildContract
import com.android.learn.mpresenter.KnowledgeChildPresenter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener

import java.util.ArrayList

import butterknife.BindView

class KnowledgeChildActivity : BaseMvpActivity<KnowledgeChildPresenter, KnowledgeChildContract.View>(), KnowledgeChildContract.View {
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
    lateinit var articleDataList: List<FeedArticleData>
    private var feedArticleAdapter: ArticleQuickAdapter? = null
    internal var cid: Int = 0
    internal var titleStr: String? = null
    internal var TAG = "KnowledgeChildActivity"

    override val layoutId: Int
        get() = R.layout.activity_knowledge_article

    override fun initData(bundle: Bundle?) {
        cid = bundle!!.getInt(ID)
        titleStr = bundle.getString(TITLE)
        LogUtil.d(TAG, "id--------------$cid")
        LogUtil.d(TAG, "titleStr--------------" + titleStr!!)
        title!!.text = titleStr
        iv_back!!.visibility = View.VISIBLE
        initSmartRefreshLayout()
        initRecyclerView()
    }


    override fun initPresenter(): KnowledgeChildPresenter {
        return KnowledgeChildPresenter()
    }

    override fun loadData() {
        CustomProgressDialog.show(this)
        mPresenter!!.getKnowledgeArticleList(0, cid)
    }

    override fun showArticleList(listData: FeedArticleListData, isRefresh: Boolean) {
        val newDataList = listData.datas
        if (newDataList == null || newDataList.size == 0) {
            smartRefreshLayout!!.finishLoadMoreWithNoMoreData()
            return
        }
        smartRefreshLayout!!.finishLoadMore()

        feedArticleAdapter!!.addData(newDataList)

    }

    override fun showCollectArticleData(position: Int, feedArticleData: FeedArticleData) {
        feedArticleAdapter!!.setData(position, feedArticleData)
    }

    override fun showCancelCollectArticleData(position: Int, feedArticleData: FeedArticleData) {
        feedArticleAdapter!!.setData(position, feedArticleData)
    }

    private fun initRecyclerView() {
        articleDataList = ArrayList()
        feedArticleAdapter = ArticleQuickAdapter(this, articleDataList, "KnowledgeChildActivity")
        article_recyclerview!!.addItemDecoration(DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST))
        article_recyclerview!!.layoutManager = LinearLayoutManager(this)

        //解决数据加载完成后, 没有停留在顶部的问题
        article_recyclerview!!.isFocusable = false
        article_recyclerview!!.adapter = feedArticleAdapter

        feedArticleAdapter!!.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            val intent = Intent(this@KnowledgeChildActivity, ArticleDetailActivity::class.java)
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
        smartRefreshLayout!!.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                mPresenter!!.onLoadMore(cid)
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                mPresenter!!.onRefreshMore(cid)
            }
        })
    }

    companion object {
        val ID = "page"
        val TITLE = "TITLE"

        fun startTreeChildrenActivity(context: Context?, id: Int, name: String) {
            val intent = Intent(context, KnowledgeChildActivity::class.java)
            intent.putExtra(ID, id)
            intent.putExtra(TITLE, name)
            context?.startActivity(intent)
        }
    }

}
