package com.android.learn.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.learn.base.mpresenter.BasePresenter;




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

    //实例presenter
    public abstract P initPresenter();
    //加载数据
    protected abstract void loadData();
}
