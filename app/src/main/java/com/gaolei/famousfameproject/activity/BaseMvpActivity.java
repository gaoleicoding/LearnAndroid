package com.gaolei.famousfameproject.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gaolei.famousfameproject.mpresenter.BasePresenter;


/**
 * Created by liuhaiyang on 2017/8/2.
 */

public abstract class BaseMvpActivity<V, P extends BasePresenter<V>> extends BaseActivity {

    public P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = initPresenter();
        mPresenter.attach((V) this);
    }

    @Override
    protected void onDestroy() {
        mPresenter.dettach();
        super.onDestroy();
    }

    //实例presenter
    public abstract P initPresenter();
}
