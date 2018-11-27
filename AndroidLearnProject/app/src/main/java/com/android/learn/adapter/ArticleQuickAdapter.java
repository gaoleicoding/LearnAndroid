package com.android.learn.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.android.learn.R;
import com.android.learn.base.mmodel.ArticleListData.FeedArticleData;
import com.android.learn.base.thirdframe.glide.ImageLoader;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class ArticleQuickAdapter extends BaseQuickAdapter<FeedArticleData> {
    Context context;
    public ArticleQuickAdapter(Context context, List<FeedArticleData> list) {
        super(R.layout.item_article_list, list);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, FeedArticleData projectInfo) {
        helper.setText(R.id.item_project_list_title_tv, projectInfo.getTitle())
                .setText(R.id.item_project_list_time_tv, projectInfo.getNiceDate())
                .setText(R.id.item_project_list_author_tv, projectInfo.getAuthor());

    }
}
