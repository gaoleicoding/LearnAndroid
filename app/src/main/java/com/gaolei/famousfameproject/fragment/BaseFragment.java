package com.gaolei.famousfameproject.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.gaolei.famousfameproject.R;
import com.gaolei.famousfameproject.activity.FragmentContainerActivity;
import com.gaolei.famousfameproject.customview.ErrorPageView;
import com.gaolei.famousfameproject.customview.TitleBarView;
import com.gaolei.famousfameproject.utils.StatusBarUtil;

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

    public TitleBarView mTitleBar;

    private RelativeLayout mLlLoading;

    public ErrorPageView mErrorPageView;

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
        showTitleTop();
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
        mTitleBar = new TitleBarView(mContext);

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
     * 显示标题栏，默认会调用这个
     */
    public void showTitleTop() {
        mTitleTop.setVisibility(View.VISIBLE);
        mTitleTop.addView(mTitleBar.getTitleBarView());
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
        mTitleBar.setTitle(title);
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
        mTitleBar.setTitle(title);
        if (isBack) {
            addBackListener();
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

    /**
     * 设置标题字体颜色
     *
     * @param resid
     */
    public void setBaseTitleColor(@ColorRes int resid) {
        mTitleBar.setTitleColor(resid);
    }

    /**
     * 设置返回按钮的图片
     *
     * @param resId
     */
    public void setBackImage(@DrawableRes int resId) {
        mTitleBar.setBackImage(resId);
    }

    /**
     * 设置界面的背景色
     */
    public void setTitleBackground(int resid) {
        mTitleBar.setBackgroundResource(resid);
    }

    /**
     * 设置返回按钮的背景图
     */
    public void setFragmentBackground(int resid) {
        mLlContent.setBackgroundResource(resid);
    }

    /**
     * 添加返回事件
     */
    public void addBackListener() {
        mTitleBar.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    /**
     * 添加返回事件
     */
    public void addBackListener(View.OnClickListener onClick) {
        mTitleBar.setOnBackListener(onClick);
    }

    /**
     * 设置返回控件的Visable状态
     *
     * @param value
     */
    public void setBackViewVisable(int value) {
        mTitleBar.setBackViewVisable(value);
    }


    /**
     * 显示加载进度条
     *
     * @param isShow
     */
    public void setLoading(boolean isShow) {
        mLlLoading.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }


    /**
     * 显示/隐藏 错误页面
     *
     * @param isShow
     */
    public void showErrorPage(boolean isShow) {
        if (isShow) {
            if (mErrorPageView == null) {
                mErrorPageView = new ErrorPageView(mContext);
            }
            ViewGroup parent = (ViewGroup) mErrorPageView.getErrorView().getParent();
            if (parent != null) {
                parent.removeAllViews();
            }
            if (mLlErrorPage.getChildCount() == 0) {
                mLlErrorPage.addView(mErrorPageView.getErrorView());
            }
        }
        mLlContent.setVisibility(isShow ? View.GONE : View.VISIBLE);
        mLlErrorPage.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    public void showNetErrorPage() {
        showErrorPage(true);
    }

    /**
     * 设置加载无数据的其他页面内容
     *
     * @param imageRes
     * @param content
     * @param function
     */
    public void setErrorPageView(int imageRes, String content, String function, ErrorPageView.OnErrorFunctionClickListener listener) {
        showErrorPage(true);
        if (!TextUtils.isEmpty(content)) {
            mErrorPageView.setContent(content);
        }

        if (imageRes != 0) {
            mErrorPageView.setIcon(imageRes);

        }
        mErrorPageView.setFunctionText(function);
        mErrorPageView.setOnErrorFunctionClickListener(listener);
    }


    /**
     * 页面跳转
     *
     * @param mClazz
     */
    public void startFragment(Class<? extends Fragment> mClazz) {
        startFragment(mClazz, null);
    }

    /**
     * 页面跳转
     *
     * @param mClazz
     * @param bundle 无参传null
     */
    public void startFragment(Class<? extends Fragment> mClazz, Bundle bundle) {
        Intent mIntent;
        if (bundle == null) {
            mIntent = FragmentContainerActivity.getFragmentContainerActivityIntent(getActivity(), mClazz);
        } else {
            mIntent = FragmentContainerActivity.getFragmentContainerActivityIntent(getActivity(), mClazz, bundle);
        }
        startActivity(mIntent);

    }

    public void startFragmentForResult(Class<? extends Fragment> mClazz, int result) {
        startFragmentForResult(mClazz, null, result);
    }

    public void startFragmentForResult(Class<? extends Fragment> mClazz, Bundle bundle, int result) {
        Intent mIntent;
        if (bundle == null) {
            mIntent = FragmentContainerActivity.getFragmentContainerActivityIntent(getActivity(), mClazz);
        } else {
            mIntent = FragmentContainerActivity.getFragmentContainerActivityIntent(getActivity(), mClazz, bundle);
        }
        startActivityForResult(mIntent, result);

    }

    public View getView(@IdRes int viewId) {
        if (mTitleBar == null || mTitleBar.getTitleBarView() == null) {
            return null;
        }
        View view = mTitleBar.getTitleBarView().findViewById(viewId);
        return view;
    }

    /**
     * 获取错误和空页面
     *
     * @return
     */
    public ErrorPageView getErrorPageView() {
        if (mErrorPageView == null) {
            mErrorPageView = new ErrorPageView(mContext);
        }
        return mErrorPageView;
    }

    /**
     * 关闭Fragment
     */
    public void onFinishFragment() {
        getActivity().onBackPressed();
    }

    /**
     * 设置白色状态栏
     */
    public void setWhiteStateBar() {
        StatusBarUtil.setWindowStatusBarColor(getActivity(), R.color.white, true);
    }
}
