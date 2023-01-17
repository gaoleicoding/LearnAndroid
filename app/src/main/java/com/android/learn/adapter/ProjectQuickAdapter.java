package com.android.learn.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.android.base.mmodel.ProjectListData.ProjectData;
import com.android.base.thirdframe.glide.ImageLoader;
import com.android.learn.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class ProjectQuickAdapter extends BaseQuickAdapter<ProjectData, BaseViewHolder> {
    private Context context;

    public ProjectQuickAdapter(Context context, List<ProjectData> list) {
        super(R.layout.item_project_list, list);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectData projectInfo) {
        helper.setText(R.id.tv_project_title, projectInfo.getTitle())
                .setText(R.id.tv_project_content, projectInfo.getDesc())
                .setText(R.id.tv_project_time, projectInfo.getNiceDate())
                .setText(R.id.tv_project_author, projectInfo.getAuthor());
        boolean isShowAuthor = !TextUtils.isEmpty(projectInfo.getAuthor());
        helper.getView(R.id.tv_project_author).setVisibility(isShowAuthor ? View.VISIBLE : View.GONE);
        ImageLoader.getInstance().load(context, projectInfo.getEnvelopePic(), (ImageView) helper.getView(R.id.iv_project));
    }
}
