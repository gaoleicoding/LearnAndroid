package com.android.base.event;

import android.app.Activity;

public class RestartMainEvent {
    public Activity activity;
    public RestartMainEvent(Activity activity){
        this.activity=activity;
    }
}