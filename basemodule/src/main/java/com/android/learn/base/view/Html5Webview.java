package com.android.learn.base.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Html5Webview extends WebView {
    private ProgressView progressView;//进度条
    private Context context;

    public Html5Webview(Context context) {
        this(context, null);
    }

    public Html5Webview(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Html5Webview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        //初始化进度条
        progressView = new ProgressView(context);
        progressView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(context, 3)));
        progressView.setColor(Color.parseColor("#79be37"));
        progressView.setProgress(10);
        //把进度条加到Webview中
        addView(progressView);
        //初始化设置
        initWebSettings();
        setWebChromeClient(new MyWebCromeClient());
        setWebViewClient(new MyWebviewClient());
    }
    private void initWebSettings() {
        WebSettings settings = getSettings();
        //默认是false 设置true允许和js交互
        settings.setJavaScriptEnabled(true);
        //  WebSettings.LOAD_DEFAULT 如果本地缓存可用且没有过期则使用本地缓存，否加载网络数据 默认值
        //  WebSettings.LOAD_CACHE_ELSE_NETWORK 优先加载本地缓存数据，无论缓存是否过期
        //  WebSettings.LOAD_NO_CACHE  只加载网络数据，不加载本地缓存
        //  WebSettings.LOAD_CACHE_ONLY 只加载缓存数据，不加载网络数据
        //Tips:有网络可以使用LOAD_DEFAULT 没有网时用LOAD_CACHE_ELSE_NETWORK
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //开启 DOM storage API 功能 较大存储空间，使用简单
        settings.setDomStorageEnabled(true);
        //开启 Application Caches 功能 方便构建离线APP 不推荐使用
        settings.setAppCacheEnabled(true);
        final String cachePath = context.getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        settings.setAppCachePath(cachePath);
        settings.setAppCacheMaxSize(5 * 1024 * 1024);
        //设置数据库缓存路径 存储管理复杂数据 方便对数据进行增加、删除、修改、查询 不推荐使用
        settings.setDatabaseEnabled(true);
        final String dbPath = context.getApplicationContext().getDir("db", Context.MODE_PRIVATE).getPath();
        settings.setDatabasePath(dbPath);
    }
    private class MyWebCromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                //加载完毕进度条消失
                progressView.setVisibility(View.GONE);
            } else {
                //更新进度
                progressView.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }
    }
    private class MyWebviewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Uri uri = Uri.parse(url);
            String scheme = uri.getScheme();
            if (scheme.equals("http") || scheme.equals("https")) {

            } else {
                //跳转外部浏览器
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                if (getContext().getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
                    context.startActivity(intent);
                }
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            Log.e("TTT", "shouldOverrideUrlLoading 1");
            return super.shouldOverrideUrlLoading(view, request);
        }
    }
    /**
     * dp转换成px
     *
     * @param context Context
     * @param dp      dp
     * @return px值
     */
    private int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

}