package com.android.learn.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;


import com.android.learn.R;
import com.android.learn.base.mmodel.TodoData;
import com.android.learn.base.mmodel.TodoData.DatasBean;
import com.android.learn.base.utils.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class TodoQuickAdapter extends BaseQuickAdapter<DatasBean, BaseViewHolder> {

    Context context;
    List<TodoData.DatasBean> list;
    int position;

    public TodoQuickAdapter(Context context, List<DatasBean> list, int position) {
        super(R.layout.todo_item_view, list);
        this.context = context;
        this.list = list;
        this.position = position;
    }


    @Override
    protected void convert(BaseViewHolder helper, DatasBean bean) {
        helper.setText(R.id.item_name, bean.getTitle());
        helper.setText(R.id.item_des, bean.getContent());
        helper.setText(R.id.tv_todo_time, bean.getDateStr());
        if(position==1){
            TextView tv_todo_time=helper.getView(R.id.tv_todo_time);
            tv_todo_time.setTextColor(Color.parseColor("#E4A340"));
            tv_todo_time.setBackgroundColor(Color.parseColor("#FFFAF1"));
        }
    }
}
