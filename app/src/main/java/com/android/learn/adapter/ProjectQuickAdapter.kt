package com.android.learn.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.android.learn.R
import com.android.learn.base.mmodel.ProjectListData.ProjectData
import com.android.learn.base.thirdframe.glide.ImageLoader
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class ProjectQuickAdapter(internal var context: Context?, list: List<ProjectData>) : BaseQuickAdapter<ProjectData, BaseViewHolder>(R.layout.item_project_list, list) {

    override fun convert(helper: BaseViewHolder, projectInfo: ProjectData) {
        helper.setText(R.id.item_project_list_title_tv, projectInfo.title)
                .setText(R.id.item_project_list_content_tv, projectInfo.desc)
                .setText(R.id.item_project_list_time_tv, projectInfo.niceDate)
                .setText(R.id.item_project_list_author_tv, projectInfo.author)

        ImageLoader.instance.load(context!!, projectInfo.envelopePic, helper.getView<View>(R.id.item_project_list_iv) as ImageView)
    }
}
