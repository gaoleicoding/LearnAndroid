package com.android.learn.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import butterknife.BindView
import com.android.learn.R
import com.android.learn.activity.ArticleDetailActivity
import com.android.learn.adapter.ArticleQuickAdapter
import com.android.learn.adapter.DividerItemDecoration
import com.android.learn.base.event.CancelCollectEvent
import com.android.learn.base.event.LoginEvent
import com.android.learn.base.fragment.BaseMvpFragment
import com.android.learn.base.mmodel.BannerListData
import com.android.learn.base.mmodel.FeedArticleListData
import com.android.learn.base.mmodel.FeedArticleListData.FeedArticleData
import com.android.learn.base.view.CustomProgressDialog
import com.android.learn.mcontract.HomeContract
import com.android.learn.mpresenter.HomePresenter
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*


class HomeFragment : BaseMvpFragment<HomePresenter, HomeContract.View>(), HomeContract.View {

    @BindView(R.id.article_recyclerview)
    lateinit var article_recyclerview: RecyclerView
    @BindView(R.id.scrollview_nested)
    lateinit var scrollview_nested: NestedScrollView
    @BindView(R.id.banner)
    lateinit var banner: Banner
    @BindView(R.id.smartRefreshLayout_home)
    lateinit var smartRefreshLayout: SmartRefreshLayout

    lateinit var articleDataList: List<FeedArticleData>
    private var feedArticleAdapter: ArticleQuickAdapter? = null

    override fun initData(bundle: Bundle?) {

        EventBus.getDefault().register(this)
    }

    override fun initView(view: View) {
        initSmartRefreshLayout()
        initRecyclerView()
    }

    override fun setContentLayout(): Int {
        return R.layout.fragment_home
    }

    override fun reload() {
        mPresenter!!.getFeedArticleList(0)
        mPresenter!!.getBannerInfo()
    }

    override fun initPresenter(): HomePresenter {
        return HomePresenter()
    }

    override fun loadData() {
        CustomProgressDialog.show(activity)
        mPresenter!!.getFeedArticleList(0)
        mPresenter!!.getBannerInfo()

    }


    override fun showArticleList(itemBeans: FeedArticleListData, isRefresh: Boolean) {
        val newDataList = itemBeans.datas
        if (newDataList.size == 0) {
            smartRefreshLayout.finishLoadMoreWithNoMoreData()
            return
        }
        smartRefreshLayout.finishLoadMore()

        feedArticleAdapter!!.addData(newDataList)

    }

    override fun showBannerList(itemBeans: BannerListData) {

        val linkList = ArrayList<String>()
        val imageList = ArrayList<String>()
        val titleList = ArrayList<String>()
        val size = itemBeans.data.size

        for (i in 0 until size) {
            imageList.add(itemBeans.data[i].imagePath)
            titleList.add(itemBeans.data[i].title)
            linkList.add(itemBeans.data[i].url)
        }
        banner.setImageLoader(object : com.youth.banner.loader.ImageLoader() {
            override fun displayImage(context: Context, path: Any, imageView: ImageView) {
                Glide.with(activity!!).load(path).into(imageView)
            }
        })

        //设置样式,默认为:Banner.NOT_INDICATOR(不显示指示器和标题)
        //可选样式如下:
        //1. Banner.CIRCLE_INDICATOR    显示圆形指示器
        //2. Banner.NUM_INDICATOR   显示数字指示器
        //3. Banner.NUM_INDICATOR_TITLE 显示数字指示器和标题
        //4. Banner.CIRCLE_INDICATOR_TITLE  显示圆形指示器和标题
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)//设置圆形指示器与标题
        //设置banner动画效果
        //        Tansformer.CubeIn
        //        Transformer.CubeOut
        //        Transformer.DepthPage
        //        Transformer.FlipHorizontal
        //        Transformer.FlipVertical
        //        banner.setBannerAnimation(Transformer.FlipHorizontal);
        banner.setIndicatorGravity(BannerConfig.CENTER)//设置指示器位置
        banner.setDelayTime(4000)//设置轮播时间
        banner.setImages(imageList)//设置图片源
        banner.setBannerTitles(titleList)//设置标题源

        banner.start()


        banner.setOnBannerListener { position ->
            val intent = Intent(activity, ArticleDetailActivity::class.java)
            val bundle = Bundle()
            bundle.putString("url", linkList[position])
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }

    private fun initRecyclerView() {
        articleDataList = ArrayList()
        feedArticleAdapter = ArticleQuickAdapter(activity, articleDataList, "HomeFragment")
        article_recyclerview.addItemDecoration(DividerItemDecoration(activity!!,
                DividerItemDecoration.VERTICAL_LIST))
        article_recyclerview.itemAnimator = null
        article_recyclerview.layoutManager = object : LinearLayoutManager(activity) {
            override fun canScrollVertically(): Boolean {
                //解决ScrollView里存在多个RecyclerView时滑动卡顿的问题
                //如果你的RecyclerView是水平滑动的话可以重写canScrollHorizontally方法
                return false
            }
        }
        //解决数据加载不完的问题
        //        project_recyclerview.setNestedScrollingEnabled(false);
        //        project_recyclerview.setHasFixedSize(true);
        //        //解决数据加载完成后, 没有停留在顶部的问题
        article_recyclerview.isFocusable = false
        article_recyclerview.adapter = feedArticleAdapter
        feedArticleAdapter!!.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            val intent = Intent(activity, ArticleDetailActivity::class.java)
            val bundle = Bundle()
            bundle.putString("url", articleDataList[position].link)
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
        smartRefreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                mPresenter!!.onLoadMore()
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                mPresenter!!.onRefreshMore()
            }
        })
    }

    fun scrollToTop() {
        scrollview_nested.scrollTo(0, 0)
    }

    override fun onResume() {
        super.onResume()

    }

    override fun showCollectArticleData(position: Int, feedArticleData: FeedArticleData) {
        feedArticleAdapter!!.setData(position, feedArticleData)
    }

    override fun showCancelCollectArticleData(position: Int, feedArticleData: FeedArticleData) {
        feedArticleAdapter!!.setData(position, feedArticleData)
    }

    override fun showCancelCollectArticleData(id: Int) {
        val position = feedArticleAdapter!!.getPosById(id)
        if (position == -1) return
        val feedArticleData = articleDataList[position]
        feedArticleData.isCollect = false
        feedArticleAdapter!!.setData(position, feedArticleData)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: CancelCollectEvent) {
        mPresenter!!.cancelCollectArticle(event.id)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(accountEvent: LoginEvent) {
        feedArticleAdapter!!.data.clear()
        feedArticleAdapter!!.notifyDataSetChanged()
        mPresenter!!.getFeedArticleList(0)
        mPresenter!!.mCurrentPage = 0
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}
