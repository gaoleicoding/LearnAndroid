package com.android.learn.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.learn.R;
import com.android.learn.activity.RegisterLoginActivity;
import com.android.learn.activity.SettingActivity;
import com.android.learn.base.fragment.BaseMvpFragment;
import com.android.learn.base.mpresenter.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author quchao
 * @date 2018/2/11
 */

public class UserFragment extends BaseMvpFragment {

    @BindView(R.id.iv_user_photo)
    public ImageView iv_user_photo;

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public void initView() {

    }

    @OnClick({R.id.iv_user_photo, R.id.my_setting_layout})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.my_setting_layout:
                SettingActivity.startActivity(getActivity());
                break;

            case R.id.iv_user_photo:
                RegisterLoginActivity.startActivity(getActivity());

        }
    }

    @Override
    public int setContentLayout() {
        return R.layout.fragment_user;
    }

    @Override
    public void reload() {
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void loadData() {

    }
}
