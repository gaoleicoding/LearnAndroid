package com.android.learn.adapter

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.android.learn.R
import com.android.learn.base.mmodel.TodoData.DatasBean
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class TodoQuickAdapter(internal var context: Context?, internal var list: List<DatasBean>, internal var position: Int) : BaseQuickAdapter<DatasBean, BaseViewHolder>(R.layout.todo_item_view, list) {


    override fun convert(helper: BaseViewHolder, bean: DatasBean) {
        helper.setText(R.id.item_name, bean.title)
        helper.setText(R.id.item_des, bean.content)
        helper.setText(R.id.tv_todo_time, bean.dateStr)
        if (position == 1) {
            val tv_todo_time = helper.getView<TextView>(R.id.tv_todo_time)
            val iv_item_todo = helper.getView<ImageView>(R.id.iv_item_todo)
            tv_todo_time.setTextColor(Color.parseColor("#E4A340"))
            tv_todo_time.setBackgroundColor(Color.parseColor("#FFFAF1"))
            iv_item_todo.setImageResource(R.drawable.icon_done)
            val tv_item_done_time = helper.getView<TextView>(R.id.tv_item_done_time)
            tv_item_done_time.visibility = View.VISIBLE
            tv_item_done_time.text = context?.getString(R.string.todo_finish_time) + bean.completeDateStr
        }
        helper.addOnClickListener(R.id.iv_item_todo)
        helper.addOnClickListener(R.id.iv_item_delete)
    }
}
