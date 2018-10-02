package com.android.learn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * description: test
 * author: zlm
 * date: 2017/3/17 16:01
 */
public class SplashActivity extends Activity {


    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    };

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        handler.sendEmptyMessageDelayed(0, 3000);
    }

    public void jumpToMainActivity(View view){
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        handler.removeCallbacksAndMessages(null);
        finish();
    }

}
