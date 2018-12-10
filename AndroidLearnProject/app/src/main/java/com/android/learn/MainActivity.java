package com.android.learn;

import android.Manifest;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.learn.adapter.MainTabAdapter;
import com.android.learn.base.activity.BaseActivity;
import com.android.learn.base.event.FontSizeEvent;
import com.android.learn.base.event.LoginEvent;
import com.android.learn.base.utils.LogUtil;
import com.android.learn.base.utils.PermissionUtil;
import com.android.learn.base.utils.Utils;
import com.android.learn.base.view.TitleView;
import com.android.learn.fragment.HomeFragment;
import com.android.learn.fragment.KnowledgeFragment;
import com.android.learn.fragment.NavigationFragment;
import com.android.learn.fragment.ProjectFragment;
import com.android.learn.fragment.UserFragment;
import com.android.learn.view.CustomViewPager;
import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGADynamicEntity;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends BaseActivity {

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

    HomeFragment homeFragment;
    ProjectFragment projectFragment;

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
        titles.add(getString(R.string.home));
        titles.add(getString(R.string.project));
        titles.add(getString(R.string.knowledge));
        titles.add(getString(R.string.navigation));
        titles.add(getString(R.string.mine));

        MainTabAdapter adapter = new MainTabAdapter(getSupportFragmentManager(), mFragments);
        viewPager.setOffscreenPageLimit(mFragments.size());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        //将TabLayout和ViewPager关联起来
        tabLayout.setupWithViewPager(viewPager);
        initTab();
        loadAnimation();
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

    @OnClick({R.id.title, R.id.iv_svga})
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

            if ((System.currentTimeMillis() - exitTime) > 2000) {
                //弹出提示，可以有多种方式
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
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
     * 进行简单的文本替换
     *
     * @return
     */
    private SVGADynamicEntity requestDynamicItem() {
        SVGADynamicEntity dynamicEntity = new SVGADynamicEntity();
        TextPaint textPaint = new TextPaint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(28);
        dynamicEntity.setDynamicText("欢迎来到学Android", textPaint, "banner");
        return dynamicEntity;
    }

    /**
     * 你可以设置富文本到 ImageKey 相关的元素上
     * 富文本是会自动换行的，不要设置过长的文本
     *
     * @return
     */
    private SVGADynamicEntity requestDynamicItemWithSpannableText() {
        SVGADynamicEntity dynamicEntity = new SVGADynamicEntity();
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("欢迎来到 学Android");
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
                Layout.Alignment.ALIGN_CENTER,
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(FontSizeEvent fontSizeEvent) {
        Intent intent = getIntent();
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    public void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
