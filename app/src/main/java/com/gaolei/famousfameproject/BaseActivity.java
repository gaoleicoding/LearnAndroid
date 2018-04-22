package com.gaolei.famousfameproject;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.weiyankeji.library.utils.StatusBarUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by liuhaiyang on 2017/8/2.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder mBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContentLayout());
        getWindow().setBackgroundDrawable(null);
        mBinder = ButterKnife.bind(this);
        getIntentData();
        initData(savedInstanceState);
        setStatusBarColor(Color.WHITE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBinder != null) {
            mBinder.unbind();
        }
    }

    protected abstract int setContentLayout();

    protected void getIntentData() {
    }

    protected abstract void initData(Bundle bundle);

    /**
     * 设置状态栏颜色
     *
     * @param res
     */
    public void setStatusBarColor(int res) {
//        StatusBarUtil.setWindowStatusBarColor(this, res, true);
    }

    /**
     * 设置状态栏透明（可以顶上去）
     */
    public void setTranStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

}
