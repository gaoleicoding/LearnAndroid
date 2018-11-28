package com.android.learn.base.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.android.learn.base.utils.ExitAppUtils;
import com.android.learn.base.utils.PermissionUtil;
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

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);

        ExitAppUtils.getInstance().addActivity(this);

        context=this;
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
        context=null;
        ExitAppUtils.getInstance().delActivity(this);

    }


}
