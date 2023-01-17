package com.android.learn.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatCheckBox;

import com.android.base.activity.BaseMvpActivity;
import com.android.base.event.ChangeNightEvent;
import com.android.base.event.LogoutEvent;
import com.android.base.event.RestartMainEvent;
import com.android.base.mpresenter.BasePresenter;
import com.android.base.thirdframe.retrofit.RetrofitProvider;
import com.android.base.utils.SPUtils;
import com.android.base.utils.Utils;
import com.android.learn.R;
import com.android.manager.UserInfoManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;


public class SettingActivity extends BaseMvpActivity {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_versionName)
    TextView tv_versionName;
    @BindView(R.id.help_feedback_layout)
    LinearLayout help_feedback_layout;
    @BindView(R.id.version_update_layout)
    LinearLayout version_update_layout;
    @BindView(R.id.language_switch_layout)
    LinearLayout language_switch_layout;
    @BindView(R.id.font_size_layout)
    LinearLayout font_size_layout;
    @BindView(R.id.my_logout_layout)
    LinearLayout my_logout_layout;
    @BindView(R.id.cb_setting_night)
    AppCompatCheckBox cb_setting_night;


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }


    @Override
    protected void initData(Bundle bundle) {
        title.setText(getString(R.string.my_setting));
        iv_back.setVisibility(View.VISIBLE);
        tv_versionName.setText(Utils.getVersionName(this));
        Boolean isNightMode = (Boolean) SPUtils.getParam(this, "nightMode", Boolean.FALSE);
        cb_setting_night.setChecked(Boolean.TRUE.equals(isNightMode));
        cb_setting_night.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                    SPUtils.setParam(SettingActivity.this, "nightMode", Boolean.TRUE);
                else SPUtils.setParam(SettingActivity.this, "nightMode", Boolean.FALSE);
                useNightMode(isChecked);
                EventBus.getDefault().post(new ChangeNightEvent());
                setStatusBar();

            }
        });
        EventBus.getDefault().register(this);
    }


    @OnClick({R.id.help_feedback_layout, R.id.version_update_layout, R.id.language_switch_layout, R.id.font_size_layout, R.id.my_logout_layout})
    public void click(View view) {

        switch (view.getId()) {
            case R.id.help_feedback_layout:
                FeedbackActivity.startActivity(SettingActivity.this);
                break;
            case R.id.language_switch_layout:
                LanguageActivity.startActivity(SettingActivity.this);
                break;
            case R.id.font_size_layout:
                FontSizeActivity.startActivity(SettingActivity.this);
                break;
            case R.id.my_logout_layout:
                if (!UserInfoManager.get().isLogined()) {
                    Utils.showToast(getString(R.string.user_not_login), true);
                    return;
                }
                UserInfoManager.get().setLogined(false);
                EventBus.getDefault().post(new LogoutEvent());
                RetrofitProvider.getInstance().sharedPrefsCookiePersistor.clear();
                finish();
                break;
        }
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void loadData() {

    }

    int yourChoice;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RestartMainEvent event) {
        finish();
    }

    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
