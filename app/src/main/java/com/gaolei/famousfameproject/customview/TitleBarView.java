package com.gaolei.famousfameproject.customview;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gaolei.famousfameproject.R;


public class TitleBarView {

    private Context mContext;

    private RelativeLayout mBackground;
    private LinearLayout mLlBack;
    private LinearLayout mBarLayout;
    private TextView mNameText;
    private ImageView mBackImage;

    private OnItemClickBarListener mOnItemClick;
    private OnItemLongClickBarListener mOnItemLongClick;

    private View mView;

    public TitleBarView(Context context) {
        this.mContext = context;
        initView();
    }

    private void initView() {
        mView = LayoutInflater.from(mContext).inflate(R.layout.layout_title_bar, null);
        mBackground = mView.findViewById(R.id.title_background);
        mNameText = mView.findViewById(R.id.title_name);

        mBackImage = mView.findViewById(R.id.title_back_image);

        mLlBack = mView.findViewById(R.id.title_back_layout);
        mBarLayout = mView.findViewById(R.id.title_bar);
    }

    public View getTitleBarView() {
        return mView;
    }

    /**
     * 设置标题
     *
     * @param name
     */
    public void setTitle(CharSequence name) {
        if (!TextUtils.isEmpty(name)) {

            mNameText.setText(name);
        }
    }

    /**
     * 不设置标题
     */
    public void setNoTitleName() {
        mNameText.setVisibility(View.GONE);
    }

    /**
     * 设置标题字体颜色
     *
     * @param resid
     */
    public void setTitleColor(int resid) {
        mNameText.setTextColor(mContext.getResources().getColor(resid));
    }

    /**
     * 设置返回按钮的图片
     *
     * @param resid
     */
    public void setBackImage(int resid) {
        mLlBack.setVisibility(View.VISIBLE);
        mBackImage.setImageResource(resid);
    }

    /**
     * 设置返回控件的Visable状态
     */
    public void setBackViewVisable(int value) {
        mLlBack.setVisibility(value);
    }

    /**
     * 添加返回事件
     */
    public void setOnBackListener(View.OnClickListener listener) {
        mLlBack.setVisibility(View.VISIBLE);
        mLlBack.setOnClickListener(listener);
    }

    public void setBackgroundResource(int resid) {
        mBackground.setBackgroundResource(resid);
    }


    public RelativeLayout getBackgroupLayout() {
        return mBackground;
    }

    public LinearLayout getBarLayout() {
        return mBarLayout;
    }



    /**
     * 菜单显示
     */
    public void setBarVisable(boolean isVisable) {
        if (isVisable) {
            mBarLayout.setVisibility(View.VISIBLE);
        } else {
            mBarLayout.setVisibility(View.GONE);
        }
    }


    public interface OnItemClickBarListener {
        void onClick(View v, int postion);
    }

    public interface OnItemLongClickBarListener {
        void onLongClick(View v, int postion);
    }


}
