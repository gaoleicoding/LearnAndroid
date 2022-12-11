package com.android.learn.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.android.base.activity.BaseMvpActivity
import com.android.base.event.CancelCollectEvent
import com.android.base.mmodel.FeedArticleListData
import com.android.base.mmodel.FeedArticleListData.FeedArticleData
import com.android.base.view.CustomProgressDialog
import com.android.learn.R
import com.android.learn.adapter.ArticleQuickAdapter
import com.android.learn.adapter.DividerItemDecoration
import com.android.learn.mcontract.CollectContract
import com.android.learn.mpresenter.CollectPresenter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import org.greenrobot.eventbus.EventBus
import java.util.*


class MyCollectActivity : BaseMvpActivity<CollectPresenter, CollectContract.View>(),
    CollectContract.View {


    @BindView(R.id.iv_back)
    lateinit var iv_back: ImageView

    @BindView(R.id.title)
    lateinit var title: TextView

    @BindView(R.id.article_collect_recyclerview)
    lateinit var article_collect_recyclerview: RecyclerView

    @BindView(R.id.smartRefreshLayout)
    lateinit var smartRefreshLayout: SmartRefreshLayout

    @BindView(R.id.tv_empty_collect)
    lateinit var tv_empty_collect: TextView
    lateinit var articleDataList: List<FeedArticleData>
    private var feedArticleAdapter: ArticleQuickAdapter? = null

    override val layoutId: Int
        get() = R.layout.activity_collect

    override fun initData(bundle: Bundle?) {
        title!!.text = getString(R.string.collect)
        iv_back!!.visibility = View.VISIBLE
        initRecyclerView()
        initSmartRefreshLayout()

    }


    override fun initPresenter(): CollectPresenter {
        return CollectPresenter()
    }

    override fun loadData() {
        CustomProgressDialog.show(this)
        mPresenter!!.getCollectList()
    }


    override fun showCollectList(feedArticleListData: FeedArticleListData) {
        val newDataList = feedArticleListData.datas
        if (newDataList == null || newDataList.size == 0) {
            smartRefreshLayout!!.finishLoadMoreWithNoMoreData()
        }
        smartRefreshLayout!!.finishLoadMore()

        feedArticleAdapter!!.addData(newDataList!!)

        if (feedArticleAdapter!!.data.size == 0) {
            tv_empty_collect!!.visibility = View.VISIBLE
        } else
            tv_empty_collect!!.visibility = View.GONE

    }

    override fun showCancelCollectArticle(position: Int, id: Int) {
        feedArticleAdapter!!.remove(position)
        EventBus.getDefault().post(CancelCollectEvent(id))
    }

    private fun initRecyclerView() {
        articleDataList = ArrayList()
        feedArticleAdapter = ArticleQuickAdapter(this, articleDataList, "MyCollectActivity")
        article_collect_recyclerview!!.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL_LIST
            )
        )
        article_collect_recyclerview!!.layoutManager = LinearLayoutManager(this)

        article_collect_recyclerview!!.isFocusable = false
        article_collect_recyclerview!!.adapter = feedArticleAdapter
        feedArticleAdapter!!.onItemChildClickListener =
            BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
                mPresenter!!.cancelCollectArticle(
                    position,
                    feedArticleAdapter!!.data[position].originId
                )
            }
        feedArticleAdapter!!.onItemClickListener =
            BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
                val intent = Intent(this@MyCollectActivity, ArticleDetailActivity::class.java)
                val bundle = Bundle()
                bundle.putString("url", articleDataList!![position].link)
                intent.putExtras(bundle)
                startActivity(intent)
            }
    }

    private fun initSmartRefreshLayout() {
        smartRefreshLayout!!.isEnableLoadMore = true
        smartRefreshLayout!!.isEnableRefresh = false
        smartRefreshLayout!!.isEnableScrollContentWhenLoaded = true//是否在加载完成时滚动列表显示新的内容
        smartRefreshLayout!!.setEnableFooterFollowWhenLoadFinished(true)
        smartRefreshLayout!!.setOnLoadMoreListener { mPresenter!!.getCollectList() }
    }

    companion object {

        fun startActivity(context: Context) {
            val intent = Intent(context, MyCollectActivity::class.java)
            context.startActivity(intent)
        }
    }


}
