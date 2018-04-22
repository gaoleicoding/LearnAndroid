package com.gaolei.famousfameproject;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by liuhaiyang on 2017/1/2.
 */

public abstract class BaseFragment extends Fragment {

    public Context mContext;

    public View mParentView;

    private Unbinder mBind;

    private FrameLayout mLlContent;
    private LinearLayout mTitleTop;

    private LayoutInflater mInflater;


    private RelativeLayout mLlLoading;


    private LinearLayout mLlErrorPage;

    /**
     * 子Fragment 的布局文件 R.layout.xx
     *
     * @return
     */
    public abstract
    @LayoutRes
    int setContentLayout();


    /**
     * 用于布局加载完毕，子Fragment可以开始初始化数据
     *
     * @param bundle
     */
    public abstract void finishCreateView(Bundle bundle);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        mContext = getActivity();
        mParentView = inflater.inflate(R.layout.fragment_base, container, false);
        initBaseView(mParentView);
        setContentView();
        return mParentView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mBind != null) {
            mBind.unbind();
        }
    }



    private void initBaseView(View view) {
        mLlContent = view.findViewById(R.id.base_content);
        mTitleTop = view.findViewById(R.id.base_title_top);

        mInflater = LayoutInflater.from(mContext);

        mLlErrorPage = view.findViewById(R.id.ll_base_error_content);

        //loading
        mLlLoading = view.findViewById(R.id.ll_loading);
//        mIvLoad = view.findViewById(R.id.iv_load);
//        ImageLoaderUtil.loadGif(mContext, R.raw.loading, mIvLoad);
    }


    /**
     * 设置内容
     */
    public void setContentView() {
        View view = mInflater.inflate(setContentLayout(), null);
        setContentView(view);
    }

    /**
     * 设置内容
     */
    public void setContentView(View view) {
        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        mLlContent.addView(view);
        mBind = ButterKnife.bind(this, view);
    }


    /**
     * 去掉标题
     */
    public void setNotTitle() {
        mTitleTop.setVisibility(View.GONE);
    }

    /**
     * 显示数据内容
     *
     * @param bool
     */
    public void showContentView(boolean bool) {
        mLlContent.setVisibility(bool ? View.VISIBLE : View.GONE);
        if (mLlErrorPage != null) {
            mLlErrorPage.setVisibility(bool ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * 设置界面的标题
     */
    public void setTitle(CharSequence title) {

    }


    /**
     * 设置界面的标题
     */
    public void setTitle(int title) {
        setTitle(getString(title));
    }

    /**
     * 设置界面的标题
     *
     * @param title  标题
     * @param isBack 如果要自定义返回事件 这里用false 再掉用 getTitleBar().addBackListener(listener);
     */
    public void setTitle(CharSequence title, boolean isBack) {
        if (isBack) {
        }
    }

    /**
     * 设置界面的标题
     *
     * @param title  标题
     * @param isBack 如果要自定义返回事件 这里用false 再掉用 getTitleBar().addBackListener(listener);
     */
    public void setTitle(int title, boolean isBack) {
        setTitle(getString(title), isBack);
    }







}
