package com.android.learn.base.activity;

import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.android.learn.base.application.CustomApplication;
import com.android.learn.base.utils.ExitAppUtils;
import com.android.learn.base.utils.LocalManageUtil;
import com.android.learn.base.utils.StatusBarUtil;
import com.android.learn.base.utils.Utils;
import com.gaolei.basemodule.R;
import com.umeng.analytics.MobclickAgent;
import com.wind.me.xskinloader.SkinInflaterFactory;
import com.wind.me.xskinloader.SkinManager;
import com.wind.me.xskinloader.util.AssetFileUtils;

import java.io.File;

import butterknife.ButterKnife;


/**
 * Created by gaolei on 2018/4/26.
 */

public abstract class BaseActivity extends BasePermisssionActivity implements View.OnClickListener {

    public static Activity context;
    Boolean isNightMode;

    public String TAG = "BaseActivity";

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SkinInflaterFactory.setFactory(this);
        setContentView(getLayoutId());
        ButterKnife.bind(this);

        ExitAppUtils.getInstance().addActivity(this);

        context = this;
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
//        if(Utils.getTopActivity(newBase).equals("LanguageActivity"))
        //语言切换
        super.attachBaseContext(LocalManageUtil.setLocal(newBase));
//        if(Utils.getTopActivity(newBase).equals("FontSizeActivity")) {
//            //重写字体缩放比例  api>25
//            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
//                final Resources res = newBase.getResources();
//                final Configuration config = res.getConfiguration();
//                config.fontScale = CustomApplication.getInstance().getFontScale();//1 设置正常字体大小的倍数
//                final Context newContext = newBase.createConfigurationContext(config);
//                super.attachBaseContext(newContext);
//            } else {
//                super.attachBaseContext(newBase);
//            }
//        }
    }

    protected abstract int getLayoutId();

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
        StatusBarUtil.setWindowStatusBarColor(this, resColor, true);
    }

    protected void onRestart() {
        super.onRestart();
    }

    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);

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
        context = null;
        ExitAppUtils.getInstance().delActivity(this);

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
        Resources res =super.getResources();
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
            Configuration config = res.getConfiguration();
            config.fontScale= CustomApplication.getInstance().getFontScale();//1 设置正常字体大小的倍数
            res.updateConfiguration(config,res.getDisplayMetrics());
        }
        return res;
    }

}