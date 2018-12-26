package com.android.learn.base.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.android.learn.base.application.CustomApplication;
import com.android.learn.base.utils.ExitAppUtils;
import com.android.learn.base.utils.LogUtil;
import com.android.learn.base.utils.StatusUtil;
import com.gaolei.basemodule.R;
import com.umeng.analytics.MobclickAgent;
import com.wind.me.xskinloader.SkinInflaterFactory;
import com.wind.me.xskinloader.SkinManager;
import com.wind.me.xskinloader.util.AssetFileUtils;
import com.zh.swipebacklib.SwipeBackLayout;

import java.io.File;

import butterknife.ButterKnife;


/**
 * Created by gaolei on 2018/4/26.
 */

public abstract class BaseActivity extends BasePermisssionActivity implements View.OnClickListener {

//    public static Activity context;
    Boolean isNightMode;

//    private SwipeBackLayout mSwipeBackLayout;
protected SwipeBackLayout mSwipeBackLayout;
    String TAG = "BaseActivity";

    protected void onCreate(@Nullable Bundle savedInstanceState) {


        SkinInflaterFactory.setFactory(this);
        super.onCreate(savedInstanceState);
//        setContentView(getLayoutId());
        ButterKnife.bind(this);

        ExitAppUtils.getInstance().addActivity(this);
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setDirectionMode(SwipeBackLayout.FROM_LEFT);
//        //设置滑动方向，可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL, EDGE_BOTTOM
//        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        //mSwipeBackLayout.setEdgeSize(200);//滑动删除的效果只能从边界滑动才有效果，如果要扩大touch的范围，可以调用这个方法

//        setStatusBarColor(R.color.status_bar_color);
        SkinManager.get().setWindowStatusBarColor(this.getWindow(), R.color.status_bar_color);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            bundle = savedInstanceState;
        }
        initData(bundle);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(LanguageUtil.setLocal(newBase));
        //重写字体缩放比例  api>25
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            final Resources res = newBase.getResources();
            final Configuration config = res.getConfiguration();
            config.fontScale = CustomApplication.getInstance().getFontScale();//1 设置正常字体大小的倍数
            final Context newContext = newBase.createConfigurationContext(config);
            super.attachBaseContext(newContext);
        } else {
            super.attachBaseContext(newBase);
        }
    }

//    protected abstract int getLayoutId();

    protected abstract void initData(Bundle bundle);

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            finish();
        }
    }

    /**
     * 设置状态栏颜色
     */
    public void setStatusBarColor(int resColor) {
        StatusUtil.setWindowStatusBarColor(this, resColor, true);
    }

    protected void onRestart() {
        super.onRestart();
    }

    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
//        context = this;
        LogUtil.d(TAG,"BaseActivity ----onResume："+getClass().getName().toString());
    }


    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    protected void onDestroy() {
        super.onDestroy();
//        context = null;
        ExitAppUtils.getInstance().delActivity(this);
        LogUtil.d(TAG,"BaseActivity ----onDestroy："+getClass().getName().toString());
    }

    public void useNightMode(boolean isNight) {

        if (isNight) {
            changeSkin();

        } else {

            restoreDefaultSkin();
        }
    }


    private void changeSkin() {
        //将assets目录下的皮肤文件拷贝到data/data/.../cache目录下
        String saveDir = getCacheDir().getAbsolutePath() + "/skins";
        String savefileName = "/skin1.skin";
        String asset_dir = "skins/xskinloader-skin-apk-debug.apk";
        File file = new File(saveDir + File.separator + savefileName);
//        if (!file.exists()) {
        AssetFileUtils.copyAssetFile(this, asset_dir, saveDir, savefileName);
//        }
        SkinManager.get().loadNewSkin(file.getAbsolutePath());
    }

    private void restoreDefaultSkin() {
        SkinManager.get().restoreToDefaultSkin();
    }

    //重写字体缩放比例 api<25
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
            Configuration config = res.getConfiguration();
            config.fontScale = CustomApplication.getInstance().getFontScale();//1 设置正常字体大小的倍数
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        return res;
    }

}