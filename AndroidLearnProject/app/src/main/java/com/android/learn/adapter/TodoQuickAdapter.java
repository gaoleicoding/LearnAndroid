package com.android.learn.adapter;

import android.content.Context;

import com.android.learn.R;
import com.android.learn.base.mmodel.FeedArticleListData.FeedArticleData;
import com.android.learn.base.mmodel.TodoData;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class TodoQuickAdapter extends BaseQuickAdapter<TodoData, BaseViewHolder> {

    Context context;
    List<TodoData> list;
    String fromWhere;

    public TodoQuickAdapter(Context context, List<TodoData> list, String fromWhere) {
        super(R.layout.item_article_list, list);
        this.context = context;
        this.list = list;
        this.fromWhere = fromWhere;
    }


    @Override
    protected void convert(BaseViewHolder helper, TodoData articleData) {

    }
}
