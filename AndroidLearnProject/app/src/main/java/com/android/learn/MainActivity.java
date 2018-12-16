package com.android.learn;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.learn.adapter.MainTabAdapter;
import com.android.learn.adapter.RvAdapter;
import com.android.learn.base.activity.BaseActivity;
import com.android.learn.base.activity.BaseMvpActivity;
import com.android.learn.base.event.ChangeNightEvent;
import com.android.learn.base.event.RestartMainEvent;
import com.android.learn.base.mmodel.HotKeyData;
import com.android.learn.base.utils.LanguageUtil;
import com.android.learn.base.utils.PermissionUtil;
import com.android.learn.base.utils.SPUtils;
import com.android.learn.base.view.TitleView;
import com.android.learn.fragment.HomeFragment;
import com.android.learn.fragment.KnowledgeFragment;
import com.android.learn.fragment.NavigationFragment;
import com.android.learn.fragment.ProjectFragment;
import com.android.learn.fragment.UserFragment;
import com.android.learn.mcontract.CollectContract;
import com.android.learn.mcontract.MainActivityContract;
import com.android.learn.mpresenter.CollectPresenter;
import com.android.learn.mpresenter.MainActivityPresenter;
import com.android.learn.view.CustomViewPager;
import com.android.learn.view.SearchViewUtils;
import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGADynamicEntity;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends BaseMvpActivity<MainActivityPresenter> implements MainActivityContract.View  {

    private ArrayList<Fragment> mFragments;
    private ArrayList<String> titles;

    @BindView(R.id.viewPager)
    CustomViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.header_layout)
    TitleView header_layout;

    @BindView(R.id.iv_svga)
    SVGAImageView iv_svga;
    @BindView(R.id.cardview_search)
    CardView cardview_search;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.iv_search)
    ImageView iv_search;
    @BindView(R.id.iv_search_back)
    ImageView iv_search_back;
    @BindView(R.id.history_recycleview)
    RecyclerView history_recycleview;
    @BindView(R.id.title_view_divider)
    View title_view_divider;

    HomeFragment homeFragment;
    ProjectFragment projectFragment;
    boolean isSearching;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData(Bundle bundle) {
        initView();
        requestPermission();
        EventBus.getDefault().register(this);
    }

    protected void initView() {
        mFragments = new ArrayList<Fragment>();
        homeFragment = new HomeFragment();
        projectFragment = new ProjectFragment();
        mFragments.add(homeFragment);
        mFragments.add(projectFragment);
        mFragments.add(new KnowledgeFragment());
        mFragments.add(new NavigationFragment());
        mFragments.add(new UserFragment());

        titles = new ArrayList<String>();
        titles.add(getResources().getString(R.string.home));
        titles.add(getResources().getString(R.string.project));
        titles.add(getResources().getString(R.string.knowledge));
        titles.add(getResources().getString(R.string.navigation));
        titles.add(getResources().getString(R.string.mine));

        MainTabAdapter adapter = new MainTabAdapter(getSupportFragmentManager(), mFragments);
        viewPager.setOffscreenPageLimit(mFragments.size());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        //将TabLayout和ViewPager关联起来
        tabLayout.setupWithViewPager(viewPager);
        initTab();
        loadAnimation();
        initResultItem();
        iv_search.setVisibility(View.VISIBLE);
        Boolean isNightMode = (Boolean) SPUtils.getParam(this, "nightMode", new Boolean(false));
        if (isNightMode) {
            title_view_divider.setVisibility(View.VISIBLE);
        } else {
            title_view_divider.setVisibility(View.GONE);
        }
    }


    /**
     * 设置添加Tab
     */
    private void initTab() {

        tabLayout.getTabAt(0).setCustomView(R.layout.tab_home);
        tabLayout.getTabAt(1).setCustomView(R.layout.tab_project);
        tabLayout.getTabAt(2).setCustomView(R.layout.tab_knowledge);
        tabLayout.getTabAt(3).setCustomView(R.layout.tab_navigation);
        tabLayout.getTabAt(4).setCustomView(R.layout.tab_mine);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            //标签选中之后执行的方法
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                title.setText(titles.get(position));

                if (position == 4) header_layout.setVisibility(View.GONE);
                else header_layout.setVisibility(View.VISIBLE);
            }

            //标签没选中
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //默认选中的Tab
        tabLayout.getTabAt(0).getCustomView().setSelected(true);
    }

    @OnClick({R.id.title, R.id.iv_svga, R.id.iv_search_back, R.id.iv_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title:
                int index = viewPager.getCurrentItem();
                if (index == 0)
                    homeFragment.scrollToTop();
                if (index == 1)
                    projectFragment.scrollToTop();
                break;

            case R.id.iv_svga:
                iv_svga.stopAnimation();
                iv_svga.setVisibility(View.GONE);
                break;
            case R.id.iv_search_back:
                SearchViewUtils.handleToolBar(getApplicationContext(), cardview_search, et_search);
                break;
            case R.id.iv_search:
                SearchViewUtils.handleToolBar(getApplicationContext(), cardview_search, et_search);
                isSearching = true;
                break;
        }

    }

    public void requestPermission() {
        requestPermission(this, new PermissionUtil.RequestPermissionCallBack() {

            @Override
            public void granted() {

            }

            @Override
            public void denied() {
            }
        }, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE});
    }

    public void onRestart() {
        super.onRestart();
        //跳转到设置界面后返回，重新检查权限
        requestPermission();
    }

    // 用来计算返回键的点击间隔时间
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (isSearching) {
                SearchViewUtils.handleToolBar(getApplicationContext(), cardview_search, et_search);
                isSearching = false;
                return false;
            }
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                //弹出提示，可以有多种方式
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.press_exit), Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    private void loadAnimation() {
        SVGAParser parser = new SVGAParser(this);
        resetDownloader(parser);
        try {
            parser.parse(new URL("https://github.com/yyued/SVGA-Samples/blob/master/kingset.svga?raw=true"), new SVGAParser.ParseCompletion() {
                @Override
                public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                    SVGADrawable drawable = new SVGADrawable(videoItem, requestDynamicItemWithSpannableText());
                    iv_svga.setImageDrawable(drawable);
                    iv_svga.startAnimation();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            /**
                             *要执行的操作
                             */
                            iv_svga.stopAnimation();
                            iv_svga.setVisibility(View.GONE);
                        }
                    }, 5000);

                }

                @Override
                public void onError() {

                }
            });
        } catch (Exception e) {
            System.out.print(true);
        }
    }

    /**
     * 你可以设置富文本到 ImageKey 相关的元素上
     * 富文本是会自动换行的，不要设置过长的文本
     *
     * @return
     */
    private SVGADynamicEntity requestDynamicItemWithSpannableText() {
        SVGADynamicEntity dynamicEntity = new SVGADynamicEntity();
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(getResources().getString(R.string.welcome_learn_android));
        spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.YELLOW), 0, 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        TextPaint textPaint = new TextPaint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(28);
        dynamicEntity.setDynamicText(new StaticLayout(
                spannableStringBuilder,
                0,
                spannableStringBuilder.length(),
                textPaint,
                0,
                android.text.Layout.Alignment.ALIGN_CENTER,
                1.0f,
                0.0f,
                false
        ), "banner");
        dynamicEntity.setDynamicDrawer(new Function2<Canvas, Integer, Boolean>() {
            @Override
            public Boolean invoke(Canvas canvas, Integer frameIndex) {
                Paint aPaint = new Paint();
                aPaint.setColor(Color.WHITE);
                canvas.drawCircle(50, 54, frameIndex % 5, aPaint);
                return false;
            }
        }, "banner");
        return dynamicEntity;
    }

    /**
     * 设置下载器，这是一个可选的配置项。
     *
     * @param parser
     */
    private void resetDownloader(SVGAParser parser) {
        parser.setFileDownloader(new SVGAParser.FileDownloader() {
            @Override
            public void resume(final URL url, final Function1<? super InputStream, Unit> complete, final Function1<? super Exception, Unit> failure) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder().url(url).get().build();
                        try {
                            Response response = client.newCall(request).execute();
                            complete.invoke(response.body().byteStream());
                        } catch (IOException e) {
                            e.printStackTrace();
                            failure.invoke(e);
                        }
                    }
                }).start();
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        //语言切换
        super.attachBaseContext(LanguageUtil.setLocal(newBase));

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RestartMainEvent event) {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
        event.activity.finish();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ChangeNightEvent event) {
        Boolean isNightMode = (Boolean) SPUtils.getParam(this, "nightMode", new Boolean(false));
        if (isNightMode) {
            title_view_divider.setVisibility(View.VISIBLE);
        } else {
            title_view_divider.setVisibility(View.GONE);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public MainActivityPresenter initPresenter() {
        return new MainActivityPresenter();
    }

    @Override
    protected void loadData() {
mPresenter.getHotKey();
    }

    private void initResultItem() {
        ArrayList<String> list = new ArrayList<>();
        list.add("优酷");
        list.add("土豆");
        list.add("爱奇艺");
        list.add("哔哩哔哩");
        list.add("youtube");
        list.add("斗鱼");
        list.add("熊猫");
        RvAdapter adapter = new RvAdapter(list, new RvAdapter.IListener() {
            @Override
            public void normalItemClick(String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void clearItemClick() {
                Toast.makeText(MainActivity.this, "清除历史记录", Toast.LENGTH_SHORT).show();
            }
        });
        history_recycleview.setAdapter(adapter);
        history_recycleview.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showHotKey(List<HotKeyData> list) {

    }
}
