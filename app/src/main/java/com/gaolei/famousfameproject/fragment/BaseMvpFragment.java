package com.gaolei.famousfameproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gaolei.famousfameproject.mpresenter.BasePresenter;


/**
 * Created by liuhaiyang on 2017/1/2.
 */

public abstract class BaseMvpFragment<V, P extends BasePresenter<V>> extends BaseFragment {

    public P mPresenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = initPresenter();

        mPresenter.attach((V) this);
    }

    @Override
    public void onDestroy() {
        mPresenter.dettach();
        super.onDestroy();
    }

    //实例presenter
    public abstract P initPresenter();
}
