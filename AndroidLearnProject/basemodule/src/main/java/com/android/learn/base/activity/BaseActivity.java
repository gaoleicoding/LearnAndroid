package com.android.learn.base.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.android.learn.base.utils.ExitAppUtils;
import com.android.learn.base.utils.LogUtil;
import com.android.learn.base.utils.PermissionUtil;
import com.android.learn.base.utils.SharedPreferencesUtils;
import com.android.learn.base.utils.StatusBarUtil;
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
    private PermissionUtil.RequestPermissionCallBack mRequestPermissionCallBack;

    public static Activity context;
    Boolean isNightMode;
    //    private IActivitySkinEventHandler mSkinEventHandler;
//    private boolean mFirstTimeApplySkin = true;
    public String TAG = "BaseActivity";

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isNightMode = (Boolean) SharedPreferencesUtils.getParam(this, "nightMode", new Boolean(false));
        if (isNightMode) {
//            setTheme(R.style.nightTheme);
        } else {
//            setTheme(R.style.dayTheme);
        }

//        mSkinEventHandler = SkinManager.newActivitySkinEventHandler()
//                .setSwitchSkinImmediately(isSwitchSkinImmediately())
//                .setSupportSkinChange(isSupportSkinChange())
//                .setWindowBackgroundResource(getWindowBackgroundResource())
//                .setNeedDelegateViewCreate(true);
//        mSkinEventHandler.onCreate(this);


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
        //皮肤相关，此通知放在此处，尽量让子类的view都添加到view树内
//        if (mFirstTimeApplySkin) {
//            mSkinEventHandler.onViewCreated();
//            mFirstTimeApplySkin = false;
//        }
//
//
//        mSkinEventHandler.onResume();
    }

    //    public void refreshSwitch() {
//        boolean isDefaultMode = SkinChangeHelper.getInstance().isDefaultMode();
//        boolean isSwitching = SkinChangeHelper.getInstance().isSwitching();
//        int drawableId;
//        if (isDefaultMode) {
//            //mImgBtnSwitch.setImageResource(R.drawable.news_switch_setting_off_nor);
//            drawableId = R.mipmap.news_switch_setting_off_nor;
//        } else {
//            //mImgBtnSwitch.setImageResource(R.drawable.news_switch_setting_on_nor);
//            drawableId = R.mipmap.news_switch_setting_on_nor;
//        }
//        SkinManager.with(mImgBtnSwitch)
//                .setViewAttrs(SkinAttrName.SRC, drawableId)
//                .applySkin(false);
////        mImgBtnSwitch.setEnabled(!isSwitching);
//    }
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
//        mSkinEventHandler.onPause();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        //皮肤相关
//        mSkinEventHandler.onWindowFocusChanged(hasFocus);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        mSkinEventHandler.onStop();
    }

    protected void onDestroy() {
        super.onDestroy();
        context = null;
        ExitAppUtils.getInstance().delActivity(this);
        //皮肤相关
//        mSkinEventHandler.onDestroy();
    }

    public void useNightMode(boolean isNight) {

        if (isNight) {
            changeSkin();
//            AppCompatDelegate.setDefaultNightMode(
//                    AppCompatDelegate.MODE_NIGHT_YES);
//            mColorful.setTheme(R.style.nightTheme);
//            SkinChangeHelper.getInstance().switchSkinMode(
//                    new SkinChangeHelper.OnSkinChangeListener() {
//                        @Override
//                        public void onSuccess() {
//                            LogUtil.d(TAG,"OnSkinChangeListener-------onError");
//
////                            refreshSwitch();
//                        }
//
//                        @Override
//                        public void onError() {
//                            LogUtil.d(TAG,"OnSkinChangeListener-------onError");
////                            refreshSwitch();
//                        }
//                    });

        } else {
//            AppCompatDelegate.setDefaultNightMode(
//                    AppCompatDelegate.MODE_NIGHT_NO);
//            mColorful.setTheme(R.style.dayTheme);
            restoreDefaultSkin();
        }
//        recreate();
//        Intent intent = new Intent(this, getClass());
//        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);//进入动画
//        finish();
//        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
//        startActivity(intent);

    }

//    public void refreshSwitch() {
//        boolean isDefaultMode = SkinChangeHelper.getInstance().isDefaultMode();
//        boolean isSwitching = SkinChangeHelper.getInstance().isSwitching();
//        int drawableId;
////        if (isDefaultMode) {
////            //mImgBtnSwitch.setImageResource(R.drawable.news_switch_setting_off_nor);
////            drawableId = R.mipmap.news_switch_setting_off_nor;
////        } else {
////            //mImgBtnSwitch.setImageResource(R.drawable.news_switch_setting_on_nor);
////            drawableId = R.mipmap.news_switch_setting_on_nor;
////        }
//        SkinManager.with(null)
//                .applySkin(false);
////        mImgBtnSwitch.setEnabled(!isSwitching);
//    }
//    @Override
//    public boolean isSupportSkinChange() {
//        //告知当前界面是否支持换肤：true支持换肤，false不支持
//        return true;
//    }
//
//    @Override
//    public boolean isSwitchSkinImmediately() {
//        //告知当切换皮肤时，是否立刻刷新当前界面；true立刻刷新，false表示在界面onResume时刷新；
//        //减轻换肤时性能压力
//        return true;
//    }
//
//    @Override
//    public void handleSkinChange() {
//        //当前界面在换肤时收到的回调，可以在此回调内做一些其他事情；
//        //比如：通知WebView内的页面切换到夜间模式等
//    }
//
//    /**
//     * 告知当前界面Window的background资源，换肤时会寻找对应的资源替换
//     */
//    protected int getWindowBackgroundResource() {
//        return R.color.activity_bg_color;
//    }


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
}