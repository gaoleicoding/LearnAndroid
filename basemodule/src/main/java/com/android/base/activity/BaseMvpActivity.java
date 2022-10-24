package com.android.base.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.base.mpresenter.BasePresenter;
import com.android.base.utils.LanguageUtil;


public abstract class BaseMvpActivity< P extends BasePresenter> extends BaseActivity {

    public P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = initPresenter();
        if(mPresenter!=null)
        mPresenter.attach(this);
        loadData();
    }

    @Override
    protected void onDestroy() {
        if(mPresenter!=null)
        mPresenter.dettach();
        super.onDestroy();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        //语言切换
        super.attachBaseContext(LanguageUtil.setLocal(newBase));
    }
    //实例presenter
    public abstract P initPresenter();
    //加载数据
    protected abstract void loadData();
}
