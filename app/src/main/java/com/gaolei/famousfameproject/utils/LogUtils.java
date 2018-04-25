/**
 * Copyright (C) 2006-2014 Tuniu All rights reserved
 */
package com.gaolei.famousfameproject.utils;

import android.util.Log;

/**
 * 写日志工具，支持模板化日志字符串
 * Date: 2014-01-26
 *
 * @author zhangjun
 */
public class LogUtils {

    private static boolean IS_PRINTABLE = false;

    public static void init(boolean isPrintable) {
        IS_PRINTABLE = isPrintable;
    }

    /**
     * Send a verbose log message.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void v(String tag, String msg) {
        if (!IS_PRINTABLE) {
            return;
        }
        Log.v(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (!IS_PRINTABLE) {
            return;
        }
        Log.d(tag, msg);
    }


    public static void i(String tag, String msg) {
        if (!IS_PRINTABLE) {
            return;
        }
        Log.i(tag, msg);
    }


    public static void w(String tag, String msg) {
        if (!IS_PRINTABLE) {
            return;
        }
        Log.w(tag, msg);
    }


    public static void e(String tag, String msg) {
        if (!IS_PRINTABLE) {
            return;
        }
        Log.e(tag, msg);
    }


}
