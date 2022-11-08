package com.android.learn.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.android.base.mmodel.FeedArticleListData.FeedArticleData;
import com.android.learn.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class ArticleQuickAdapter extends BaseQuickAdapter<FeedArticleData, BaseViewHolder> {

    private List<FeedArticleData> list;
    private String fromWhere;

    public ArticleQuickAdapter(Context context, List<FeedArticleData> list, String fromWhere) {
        super(R.layout.item_article_list, list);
        this.list = list;
        this.fromWhere = fromWhere;
    }

    public int getPosById(int id) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (id == list.get(i).id)
                return i;
        }
        return -1;
    }

    @Override
    protected void convert(BaseViewHolder helper, FeedArticleData articleData) {
        helper.setText(R.id.tv_item_title, articleData.getTitle())
                .setText(R.id.tv_item_author, articleData.getAuthor())
                .setText(R.id.tv_item_time, articleData.getNiceDate());
        helper.addOnClickListener(R.id.iv_item_collect);
        boolean isShowAuthor = !TextUtils.isEmpty(articleData.getAuthor());
        helper.getView(R.id.tv_item_author).setVisibility(isShowAuthor ? View.VISIBLE : View.GONE);
        if ("HomeFragment".equals(fromWhere) || "KnowledgeChildActivity".equals(fromWhere) || "WechatSubFragment".equals(fromWhere)) {
            if (articleData.isCollect()) {
                helper.setImageResource(R.id.iv_item_collect, R.drawable.icon_collect_select);
            } else {
                helper.setImageResource(R.id.iv_item_collect, R.drawable.icon_collect_unselect);
            }
        }
        if ("MyCollectActivity".equals(fromWhere)) {
            helper.setImageResource(R.id.iv_item_collect, R.drawable.icon_collect_select);
        }

    }
}
