package com.android.learn.adapter

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.android.learn.R
import com.android.base.mmodel.ProjectListData.ProjectData
import com.android.base.thirdframe.glide.ImageLoader
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class ProjectQuickAdapter(internal var context: Context?, list: List<ProjectData>) : BaseQuickAdapter<ProjectData, BaseViewHolder>(R.layout.item_project_list, list) {

    override fun convert(helper: BaseViewHolder, projectInfo: ProjectData) {
        helper.setText(R.id.tv_project_title, projectInfo.title)
                .setText(R.id.tv_project_content, projectInfo.desc)
                .setText(R.id.tv_project_time, projectInfo.niceDate)
                .setText(R.id.tv_project_author, projectInfo.author)
        if (TextUtils.isEmpty(projectInfo.author)) {
            helper.getView<TextView>(R.id.tv_project_author).visibility = View.GONE
        } else {
            helper.getView<TextView>(R.id.tv_project_author).visibility = View.VISIBLE
        }
        ImageLoader.instance.load(context!!, projectInfo.envelopePic, helper.getView<View>(R.id.iv_project) as ImageView)
    }
}
