package com.android.learn.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.android.learn.R
import com.android.base.activity.BaseActivity
import com.android.base.view.Html5Webview

class ArticleDetailActivity : BaseActivity() {

    @BindView(R.id.webview_article)
    lateinit var webview_article: Html5Webview
    @BindView(R.id.iv_back)
    lateinit var iv_back: ImageView
    @BindView(R.id.title)
    lateinit var title: TextView
    @BindView(R.id.iv_search)
    lateinit var iv_search: ImageView
    internal var url: String? = null

    override val layoutId: Int
        get() = R.layout.activity_article_detail

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initData(bundle: Bundle?) {
        title.text = getString(R.string.article_detail)
        iv_back.visibility = View.VISIBLE
        url = bundle?.getString("url")
        webview_article.loadUrl(url)
        iv_search.visibility = View.VISIBLE
        iv_search.setImageResource(R.drawable.icon_share)
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


    companion object {

        fun startActivity(context: Context, bundle: Bundle?) {
            val intent = Intent(context, ArticleDetailActivity::class.java)

            if (bundle != null)
                intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }
}
