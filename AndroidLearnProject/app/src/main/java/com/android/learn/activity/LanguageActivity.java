package com.android.learn.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.android.learn.MainActivity;
import com.android.learn.R;
import com.android.learn.base.activity.BaseActivity;
import com.android.learn.base.event.FontSizeEvent;
import com.android.learn.base.utils.LocalManageUtil;

import org.greenrobot.eventbus.EventBus;


public class LanguageActivity extends BaseActivity {
    private TextView mUserSelect;
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LanguageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_language;
    }

    @Override
    protected void initData(Bundle bundle) {
        setClick();
    }

    public static void enter(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    private void selectLanguage(int select) {
        LocalManageUtil.saveSelectLanguage(this, select);
        EventBus.getDefault().post(new FontSizeEvent());
    }

    private void setClick() {
        //跟随系统
        findViewById(R.id.btn_auto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectLanguage(0);
            }
        });
        //简体中文
        findViewById(R.id.btn_cn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectLanguage(1);
            }
        });
        //english
        findViewById(R.id.btn_en).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectLanguage(2);
            }
        });
    }
}
