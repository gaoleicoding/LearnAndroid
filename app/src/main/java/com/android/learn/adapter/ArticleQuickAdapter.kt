package com.android.learn.adapter

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.android.base.mmodel.FeedArticleListData.FeedArticleData
import com.android.learn.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class ArticleQuickAdapter(
    val context: Context?,
    internal var list: List<FeedArticleData>,
    internal var fromWhere: String
) : BaseQuickAdapter<FeedArticleData, BaseViewHolder>(R.layout.item_article_list, list) {

    fun getPosById(id: Int): Int {
        val size = list.size
        for (i in 0 until size) {
            if (id == list[i].id)
                return i
        }
        return -1
    }

    override fun convert(helper: BaseViewHolder, articleData: FeedArticleData) {
        helper.setText(R.id.tv_item_title, articleData.title)
            .setText(R.id.tv_article_time, articleData.niceDate)
            .setText(R.id.tv_article_author, articleData.author)
        helper.addOnClickListener(R.id.iv_article_collect)
        if ("HomeFragment" == fromWhere || "KnowledgeChildActivity" == fromWhere || "WechatSubFragment" == fromWhere) {
            if (articleData.isCollect) {
                helper.setImageResource(R.id.iv_article_collect, R.drawable.icon_collect_select)
            } else {
                helper.setImageResource(R.id.iv_article_collect, R.drawable.icon_collect_unselect)
            }
        }
        if ("MyCollectActivity" == fromWhere) {
            helper.setImageResource(R.id.iv_article_collect, R.drawable.icon_collect_select)
        }
        if (TextUtils.isEmpty(articleData.author)) {
            helper.getView<TextView>(R.id.tv_article_author).visibility = View.GONE
        } else {
            helper.getView<TextView>(R.id.tv_article_author).visibility = View.VISIBLE
        }
    }
}