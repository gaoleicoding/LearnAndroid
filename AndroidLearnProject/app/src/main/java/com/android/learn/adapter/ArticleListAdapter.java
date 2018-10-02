package com.android.learn.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.learn.R;
import com.android.learn.base.mmodel.ArticleListData.FeedArticleData;
import com.android.learn.base.thirdframe.glide.ImageLoader;

import java.util.List;


public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.MyViewHolder> {

    public Context context;
    int selectPosition = 0;
    OnItemClickListener listener;
    List<FeedArticleData> list;

    public ArticleListAdapter(Context context, List<FeedArticleData> list) {
        this.context = context;
        this.list = list;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_article_list, null);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int position = (int) view.getTag();
                if (listener != null) {
                    listener.onItemClick(view, position);
                }
            }
        });
        return holder;
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.itemView.setTag(position);
        FeedArticleData projectInfo = list.get(position);

        holder.item_project_list_title_tv.setText(projectInfo.getTitle());
        holder.item_project_list_content_tv.setText(projectInfo.getDesc());
        holder.item_project_list_time_tv.setText(projectInfo.getNiceDate());
        holder.item_project_list_author_tv.setText(context.getString(R.string.author) + projectInfo.getAuthor());
        ImageLoader.getInstance().load(context, projectInfo.getEnvelopePic(), holder.item_project_list_iv);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item_project_list_title_tv, item_project_list_content_tv, item_project_list_time_tv, item_project_list_author_tv;
        ImageView item_project_list_iv;

        public MyViewHolder(View view) {
            super(view);
            item_project_list_title_tv = view.findViewById(R.id.item_project_list_title_tv);
            item_project_list_content_tv = view.findViewById(R.id.item_project_list_content_tv);
            item_project_list_time_tv = view.findViewById(R.id.item_project_list_time_tv);
            item_project_list_author_tv = view.findViewById(R.id.item_project_list_author_tv);
            item_project_list_iv = view.findViewById(R.id.item_project_list_iv);
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}