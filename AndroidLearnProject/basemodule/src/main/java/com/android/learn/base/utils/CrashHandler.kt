package com.android.learn.base.utils

import android.app.AlarmManager
import android.app.Dialog
import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Environment
import android.os.Looper
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import android.widget.Toast


import com.android.learn.base.application.CustomApplication

import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.io.StringWriter
import java.io.Writer
import java.text.SimpleDateFormat
import java.util.Date
import java.util.LinkedHashMap

class CrashHandler : Thread.UncaughtExceptionHandler {
    /**
     * 系统默认的异常处理类
     */
    private var mDefaultHandler: Thread.UncaughtExceptionHandler? = null
    internal var mcontext: Context
    internal var errorSavePath: String
    //用来存储设备信息和异常信息
    private val infos = LinkedHashMap()

    fun init(context: Context) {
        mcontext = context
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()// 获取默认的异常处理类
        Thread.setDefaultUncaughtExceptionHandler(this)// 设置当前处理类为默认的异常处理类
    }

    override fun uncaughtException(thread: Thread, ex: Throwable) {
        saveCrashInfoIntoSd(ex)
        showToast(mcontext, "程序出错了，请先用其它功能，我们会尽快修复！")

        try {
            Thread.sleep(2000)
        } catch (e: Exception) {
            // TODO: handle exception
        }


        //            if (SPUtils.contains("currentTime")) {
        //                String lastTimeMillis = (String) SPUtils.get("currentTime", String.valueOf("0"));
        //                LogUtil.d("lastTimeMillis:" + lastTimeMillis);
        //                LogUtil.d("System.currentTimeMillis():" + System.currentTimeMillis());
        //                LogUtil.d("priod:" + (System.currentTimeMillis() - Long.valueOf(lastTimeMillis)));
        //                long timeInterval = System.currentTimeMillis() - Long.valueOf(lastTimeMillis);
        //                if (timeInterval > 60 * 1000) {
        //                    SPUtils.put("currentTime", String.valueOf(System.currentTimeMillis()));
        //                }
        //            } else {
        //                SPUtils.put("currentTime", String.valueOf(System.currentTimeMillis()));
        //
        //            }

        //            android.os.Process.killProcess(android.os.Process.myPid());
        //            System.exit(0);
        ExitAppUtils.instance.exit()
        //        }
    }

    private fun showToast(context: Context, msg: String) {
        Thread(Runnable {
            Looper.prepare()
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
            Looper.loop()
        }).start()
    }


    // 收集设备、软件参数信息
    private fun collectDeviceInfo() {
        try {
            val pm = CustomApplication.context.packageManager
            val pi = pm.getPackageInfo(CustomApplication.context.packageName, PackageManager.GET_ACTIVITIES)
            if (pi != null) {
                val versionName = if (pi.versionName == null) "null" else pi.versionName
                val versionCode = pi.versionCode.toString() + ""
                infos.put("systemVersion", SystemUtil.systemVersion)
                infos.put("deviceModel", SystemUtil.systemModel)
                infos.put("deviceBrand", SystemUtil.deviceBrand)
                infos.put("versionName", versionName)
                infos.put("versionCode", versionCode)
            }
        } catch (e: PackageManager.NameNotFoundException) {
        }

    }

    // 保存错误信息到SD卡文件中
    private fun saveCrashInfoIntoSd(ex: Throwable) {
        collectDeviceInfo()
        //创建文件夹
        errorSavePath = Environment.getExternalStorageDirectory().path + "/" + mcontext.packageName + "/crash"
        val dir = File(errorSavePath)
        if (!dir.exists()) dir.mkdirs()

        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val time = formatter.format(Date())
        val sb = StringBuffer()
        sb.append("\n" + time + "\n")
        for (entry in infos.entries) {
            val key = entry.key
            val value = entry.value
            sb.append(key + "=" + value + "\n")
        }

        sb.append(getCrashInfo(ex))

        try {
            //创建文件
            val fileName = "crash-$time.txt"
            val file = File("$errorSavePath//$fileName")
            if (!file.exists()) file.createNewFile()

            val fos = FileOutputStream(file)
            fos.write(sb.toString().toByteArray())
            fos.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * 得到程序崩溃的详细信息
     */
    fun getCrashInfo(ex: Throwable): String {
        val result = StringWriter()
        val printWriter = PrintWriter(result)
        ex.stackTrace = ex.stackTrace
        ex.printStackTrace(printWriter)
        printWriter.close()
        return result.toString()
    }

    companion object {
        val instance = CrashHandler()
    }


}