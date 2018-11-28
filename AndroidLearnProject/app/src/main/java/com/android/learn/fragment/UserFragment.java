package com.android.learn.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.learn.R;
import com.android.learn.activity.RegisterLoginActivity;
import com.android.learn.activity.SettingActivity;
import com.android.learn.base.event.LoginEvent;
import com.android.learn.base.event.LogoutEvent;
import com.android.learn.base.fragment.BaseMvpFragment;
import com.android.learn.base.mpresenter.BasePresenter;
import com.android.learn.base.utils.SharedPreferencesUtils;
import com.android.learn.base.utils.Utils;
import com.android.learn.base.utils.account.UserUtil;
import com.android.learn.mcontract.UserInfoContract;
import com.android.learn.mpresenter.UserInfoPresenter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author quchao
 * @date 2018/2/11
 */

public class UserFragment extends BaseMvpFragment<UserInfoPresenter> implements UserInfoContract.View {

    @BindView(R.id.iv_user_photo)
    public ImageView iv_user_photo;
    @BindView(R.id.tv_user_profile_not_login)
    TextView tv_user_profile_not_login;

    @Override
    public void initData(Bundle bundle) {
        EventBus.getDefault().register(this);
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
    public UserInfoPresenter initPresenter() {
        return new UserInfoPresenter();
    }

    @Override
    protected void loadData() {
        refreshUserInfo();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(LoginEvent accountEvent) {
        refreshUserInfo();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(LogoutEvent logoutEvent) {
        logout();
    }


    public void refreshUserInfo() {
        if (UserUtil.getUserInfo() == null) return;
        if (UserUtil.isLogined()) {
            String username = UserUtil.getUserInfo().data.username;

            tv_user_profile_not_login.setText(username);
            String photoUrl = UserUtil.getUserInfo().data.icon;
            if (photoUrl != null) {
                RequestOptions options = new RequestOptions().placeholder(R.drawable.user_default_photo);
                Glide.with(getActivity()).load(photoUrl).apply(options).into(iv_user_photo);
            }

        } else {

        }

    }

    private void logout() {

        tv_user_profile_not_login.setText(getString(R.string.login_register));
        iv_user_photo.setImageResource(R.drawable.user_default_photo);
        SharedPreferencesUtils.clear(getActivity(), "phone_num");
        SharedPreferencesUtils.clear(getActivity(), "password");
        mPresenter.getLogoutData();
        RegisterLoginActivity.startActivity(getActivity());
        UserUtil.setUserInfo(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(getActivity());
    }
}
