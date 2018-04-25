package com.gaolei.famousfameproject.customview;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gaolei.famousfameproject.R;


/**
 * Created by caiwancheng on 2017/9/14.
 */

public class ErrorPageView implements View.OnClickListener {
    private Context mContext;

    private View mErrorPageView;

    private LinearLayout mLlError;
    private ImageView mIvErrorIcon;
    private TextView mTvErrorContent;
    private Button mBtError;

    private OnErrorBgClickListener mOnErrorBgClickListener;
    private OnErrorFunctionClickListener mBtClickListener;


    public ErrorPageView(Context context) {
        mContext = context;
        initView();
    }

    private void initView() {
        mErrorPageView = LayoutInflater.from(mContext).inflate(R.layout.layout_error_page, null);
        mLlError = mErrorPageView.findViewById(R.id.ll_base_hint_page);
        mIvErrorIcon = mErrorPageView.findViewById(R.id.iv_base_hint_page_icon);
        mTvErrorContent = mErrorPageView.findViewById(R.id.tv_base_hint_page_content);
        mBtError = mErrorPageView.findViewById(R.id.bt_base_hint_page_function);
        mBtError.setOnClickListener(this);
        mLlError.setOnClickListener(this);
    }

    /**
     * 获取错误页面
     *
     * @return
     */
    public View getErrorView() {
        return mErrorPageView;
    }

    /**
     * 设置功能按钮名字
     */
    public void setFunctionText(String text) {
        mBtError.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
        mBtError.setText(text);
    }

    /**
     * 设置icon
     *
     * @param resId
     */
    public void setIcon(int resId) {
        mIvErrorIcon.setBackgroundResource(resId);
    }

    /**
     * 设置文本
     *
     * @param text
     */
    public void setContent(String text) {
        mTvErrorContent.setText(text);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_base_hint_page:
                if (mOnErrorBgClickListener != null) {
                    mOnErrorBgClickListener.onClick();
                }
                break;
            case R.id.bt_base_hint_page_function:
                if (mBtClickListener != null) {
                    mBtClickListener.onClick();
                }
                break;
            default:
        }
    }


    /**
     * 设置背景点击监听
     *
     * @param listener
     */
    public void setOnErrorBgClickListener(OnErrorBgClickListener listener) {
        mOnErrorBgClickListener = listener;
    }


    /**
     * 背景的点击事件
     */
    public interface OnErrorBgClickListener {
        void onClick();
    }


    /**
     * 功能按钮点击事件
     *
     * @param listener
     */
    public void setOnErrorFunctionClickListener(OnErrorFunctionClickListener listener) {
        mBtClickListener = listener;
    }

    /**
     * 按钮点击事件
     */
    public interface OnErrorFunctionClickListener {
        void onClick();
    }
}
