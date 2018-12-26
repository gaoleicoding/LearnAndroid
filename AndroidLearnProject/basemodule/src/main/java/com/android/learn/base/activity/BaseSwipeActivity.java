package com.android.learn.base.activity;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;

import com.android.learn.base.utils.StatusUtil;
import com.gaolei.basemodule.R;
import com.jaeger.library.StatusBarUtil;
import com.zh.swipebacklib.BaseSwipeBackActivity;
import com.zh.swipebacklib.SwipeBackLayout;

/**
 * Created by GongWen on 17/8/25.
 */

public abstract class BaseSwipeActivity extends BaseSwipeBackActivity  {
    protected final String TAG = getClass().getSimpleName();

    protected SwipeBackLayout mSwipeBackLayout;

    public boolean isMain = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        setTitle(TAG);
        mSwipeBackLayout = getSwipeBackLayout();
        if(isMain){
            return;
        }
//        setStatusBarColor(getResources().getColor(R.color.app_color), 0);

    }

    protected abstract int getLayoutId();
    /**
     * 设置状态栏颜色
     * @param color
     * @param statusBarAlpha 透明度
     */
    public void setStatusBarColor(@ColorInt int color, @IntRange(from = 0, to = 255) int statusBarAlpha) {
        StatusBarUtil.setColorForSwipeBack(this, color, statusBarAlpha);
    }

}

