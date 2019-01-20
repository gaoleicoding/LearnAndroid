package com.android.learn.base.utils

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.os.Build
import android.os.Environment
import android.os.Looper
import android.util.Log
import android.widget.Toast

import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.io.StringWriter
import java.lang.Thread.UncaughtExceptionHandler
import java.text.SimpleDateFormat
import java.util.Date
import java.util.HashMap
import java.util.TimeZone

class CustomCrashHandler private constructor() : UncaughtExceptionHandler {
    private var mContext: Context? = null


    override fun uncaughtException(thread: Thread, ex: Throwable) {
        savaInfoToSD(mContext, ex)

        showToast(mContext, "程序出错了，请先用其它功能，我们会尽快修复！")
        try {
            thread.sleep(2000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        //		android.os.Process.killProcess(android.os.Process.myPid());
        //        System.exit(0);

        ExitAppUtils.instance.exit()

    }


    fun init(context: Context) {
        mContext = context
        Thread.setDefaultUncaughtExceptionHandler(this)
    }


    private fun showToast(context: Context?, msg: String) {
        Thread(Runnable {
            Looper.prepare()
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
            Looper.loop()
        }).start()
    }


    private fun obtainSimpleInfo(context: Context): HashMap<String, String> {
        val map = HashMap<String, String>()
        val mPackageManager = context.packageManager
        var mPackageInfo: PackageInfo? = null
        try {
            mPackageInfo = mPackageManager.getPackageInfo(context.packageName, PackageManager.GET_ACTIVITIES)
        } catch (e: NameNotFoundException) {
            e.printStackTrace()
        }

        map["versionName"] = mPackageInfo!!.versionName
        map["versionCode"] = "" + mPackageInfo.versionCode

        map["MODEL"] = "" + Build.MODEL
        map["SDK_INT"] = "" + Build.VERSION.SDK_INT
        map["PRODUCT"] = "" + Build.PRODUCT

        return map
    }


    private fun obtainExceptionInfo(throwable: Throwable): String {
        val mStringWriter = StringWriter()
        val mPrintWriter = PrintWriter(mStringWriter)
        throwable.printStackTrace(mPrintWriter)
        mPrintWriter.close()

        Log.e(TAG, mStringWriter.toString())
        return mStringWriter.toString()
    }


    private fun savaInfoToSD(context: Context?, ex: Throwable): String? {
        var fileName: String? = null
        val sb = StringBuffer()

        for ((key, value) in obtainSimpleInfo(context!!)) {
            sb.append(key).append(" = ").append(value).append("\n")
        }

        sb.append(obtainExceptionInfo(ex))

        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            val dir = File(SDCARD_ROOT + File.separator + "crash" + File.separator)
            if (!dir.exists()) {
                dir.mkdir()
            }

            try {
                fileName = dir.toString() + File.separator + paserTime(System.currentTimeMillis()) + ".log"
                val fos = FileOutputStream(fileName)
                fos.write(sb.toString().toByteArray())
                fos.flush()
                fos.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        return fileName

    }


    private fun paserTime(milliseconds: Long): String {
        System.setProperty("user.timezone", "Asia/Shanghai")
        val tz = TimeZone.getTimeZone("Asia/Shanghai")
        TimeZone.setDefault(tz)
        val format = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")

        return format.format(Date(milliseconds))
    }

    companion object {
        private val TAG = "Activity"
        private val SDCARD_ROOT = Environment.getExternalStorageDirectory().toString()
        val instance = CustomCrashHandler()
    }
}
