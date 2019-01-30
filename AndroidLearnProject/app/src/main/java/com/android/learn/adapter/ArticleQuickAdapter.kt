package com.android.learn.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView

import com.android.learn.R
import com.android.learn.base.mmodel.FeedArticleListData.FeedArticleData
import com.android.learn.base.thirdframe.glide.ImageLoader
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class ArticleQuickAdapter( val context: Context?, internal var list: List<FeedArticleData>, internal var fromWhere: String) : BaseQuickAdapter<FeedArticleData, BaseViewHolder>(R.layout.item_article_list, list) {

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
                .setText(R.id.tv_item_time, articleData.niceDate)
                .setText(R.id.tv_item_author, articleData.author)
        helper.addOnClickListener(R.id.iv_item_collect)
        if ("HomeFragment" == fromWhere || "KnowledgeChildActivity" == fromWhere || "WechatSubFragment" == fromWhere) {
            if (articleData.isCollect) {
                helper.setImageResource(R.id.iv_item_collect, R.drawable.icon_collect_select)
            } else {
                helper.setImageResource(R.id.iv_item_collect, R.drawable.icon_collect_unselect)
            }
        }
        if ("MyCollectActivity" == fromWhere) {
            helper.setImageResource(R.id.iv_item_collect, R.drawable.icon_collect_select)
        }

    }
}
