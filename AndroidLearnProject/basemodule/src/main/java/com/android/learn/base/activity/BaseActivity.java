package com.android.learn.base.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;

import com.android.learn.base.utils.ExitAppUtils;
import com.android.learn.base.utils.PermissionUtil;
import com.android.learn.base.utils.SharedPreferencesUtils;
import com.android.learn.base.utils.StatusBarUtil;
import com.gaolei.basemodule.R;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;


/**
 * Created by gaolei on 2018/4/26.
 */

public abstract class BaseActivity extends BasePermisssionActivity implements View.OnClickListener {
    private PermissionUtil.RequestPermissionCallBack mRequestPermissionCallBack;

    public static Activity context;
    Boolean isNightMode;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isNightMode = (Boolean) SharedPreferencesUtils.getParam(this, "nightMode", new Boolean(false));
        if (isNightMode) {
            setTheme(R.style.nightTheme);
        } else {
            setTheme(R.style.dayTheme);
        }
        setContentView(getLayoutId());
        ButterKnife.bind(this);

        ExitAppUtils.getInstance().addActivity(this);

        context = this;
        setStatusBarColor(R.color.app_color);

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
    }

    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    protected void onDestroy() {
        super.onDestroy();
        context = null;
        ExitAppUtils.getInstance().delActivity(this);

    }

    public void useNightMode(boolean isNight) {

        if (isNight) {
//            AppCompatDelegate.setDefaultNightMode(
//                    AppCompatDelegate.MODE_NIGHT_YES);
            setTheme(R.style.nightTheme);
        } else {
//            AppCompatDelegate.setDefaultNightMode(
//                    AppCompatDelegate.MODE_NIGHT_NO);
            setTheme(R.style.dayTheme);
        }
//        recreate();
        Intent intent = new Intent(this, getClass());
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);//进入动画
        finish();
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
        startActivity(intent);

    }
}