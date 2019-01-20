package com.android.learn.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.android.learn.R
import com.android.learn.base.activity.BaseActivity
import com.android.learn.base.activity.BaseMvpActivity
import com.android.learn.base.mpresenter.BasePresenter
import com.android.learn.base.utils.LanguageUtil
import com.android.learn.base.view.Html5Webview

import butterknife.BindView
import butterknife.OnClick

class ArticleDetailActivity : BaseMvpActivity<*>() {

    @BindView(R.id.webview_article)
    internal var webview_article: Html5Webview? = null
    @BindView(R.id.iv_back)
    internal var iv_back: ImageView? = null
    @BindView(R.id.title)
    internal var title: TextView? = null
    @BindView(R.id.iv_search)
    internal var iv_search: ImageView? = null
    internal var url: String? = null
    protected override val layoutId: Int
        get() = R.layout.activity_article_detail

    override fun initData(bundle: Bundle) {
        title!!.text = getString(R.string.article_detail)
        iv_back!!.visibility = View.VISIBLE
        url = bundle.getString("url")
        webview_article!!.loadUrl(url)
        iv_search!!.visibility = View.VISIBLE
        iv_search!!.setImageResource(R.drawable.icon_share)
    }


    @OnClick(R.id.iv_search)
    fun click(view: View) {
        when (view.id) {
            R.id.iv_search -> share("分享地址", url)
        }
    }

    private fun share(title: String, content: String?) {
        var share_intent = Intent()
        share_intent.action = Intent.ACTION_SEND//设置分享行为
        share_intent.type = "text/plain"//设置分享内容的类型
        share_intent.putExtra(Intent.EXTRA_SUBJECT, title)//添加分享内容标题
        share_intent.putExtra(Intent.EXTRA_TEXT, content)//添加分享内容
        //创建分享的Dialog
        share_intent = Intent.createChooser(share_intent, "分享")
        startActivity(share_intent)
    }

    override fun initPresenter(): BasePresenter<*>? {
        return null
    }

    override fun loadData() {

    }

    companion object {
        fun startActivity(context: Context, bundle: Bundle?) {
            val intent = Intent(context, ArticleDetailActivity::class.java)

            if (bundle != null)
                intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }
}
