package com.android.learn.base.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.widget.Toast;

import com.android.learn.base.application.CustomApplication;

import java.security.MessageDigest;

/**
 * Created by gaolei on 2018/6/15.
 */

public class Utils {

    public static void showToast(String content){
        Toast.makeText(CustomApplication.context, content, Toast.LENGTH_SHORT).show();
    }
    /**
     * md5 加密
     *
     * @param str 要加密的字符串
     * @return
     */
    public static String md5Encode(String str) {
        StringBuffer buf = new StringBuffer();
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes());
            byte[] bytes = md5.digest();
            for (int i = 0; i < bytes.length; i++) {
                String s = Integer.toHexString(bytes[i] & 0xff);
                if (s.length() == 1) {
                    buf.append("0");
                }
                buf.append(s);
            }

        } catch (Exception ex) {
        }
        return buf.toString();
    }
    public static String getTopActivity(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.RunningTaskInfo taskInfo = manager.getRunningTasks(1).get(0);
        ComponentName componentInfo = taskInfo.topActivity;
        String shortClassName = componentInfo.getShortClassName();    //类名
//        String className = info.topActivity.getClassName();              //完整类名
//        String packageName = info.topActivity.getPackageName();
        int index=shortClassName.lastIndexOf(".");
        shortClassName=shortClassName.substring(index+1);
        return shortClassName;
    }
}
