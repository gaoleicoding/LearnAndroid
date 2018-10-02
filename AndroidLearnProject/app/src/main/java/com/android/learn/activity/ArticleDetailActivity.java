package com.android.learn.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.learn.R;
import com.android.learn.base.activity.BaseActivity;
import com.android.learn.base.view.Html5Webview;

import butterknife.BindView;

public class ArticleDetailActivity extends BaseActivity {

    @BindView(R.id.webview_article)
    Html5Webview webview_article;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.title)
    TextView title;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article_detail;
    }

    @Override
    protected void initData(Bundle bundle) {
        title.setText(getString(R.string.article_detail));
        iv_back.setVisibility(View.VISIBLE);
        String url = bundle.getString("url");
        webview_article.loadUrl(url);
    }

}
