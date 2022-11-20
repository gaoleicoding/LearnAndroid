package com.android.learn.adapter

import android.content.Context

import com.android.learn.R
import com.android.base.db.SearchRecord
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by yuepeng on 2017/6/9.
 */

class SearchRecordAdapter(internal var context: Context, internal var list: List<SearchRecord>) : BaseQuickAdapter<SearchRecord, BaseViewHolder>(R.layout.item_search_record, list) {

    override fun convert(helper: BaseViewHolder, searchRecord: SearchRecord) {
        helper.setText(R.id.tv_history_item, searchRecord.name)
    }
}
