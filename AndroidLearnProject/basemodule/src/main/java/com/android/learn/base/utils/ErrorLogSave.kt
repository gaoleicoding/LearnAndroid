package com.android.learn.base.utils

import android.content.Context
import android.os.Environment
import android.os.Handler
import android.text.TextUtils
import android.util.Log

import java.io.BufferedReader
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date

class ErrorLogSave(internal var context: Context) : Thread() {

    protected val mHandler: Handler

    init {
        mHandler = Handler()
        packageName = context.packageName
    }


    override fun run() {
        val str = getExceptionLog(context)
        mHandler.post(SaveErrorLog2SD(str))
        super.run()
    }

    /**
     * 保存错误日志到sd卡
     *
     * @author pengjun
     */
    private inner class SaveErrorLog2SD internal constructor(internal var errorLog: String) : Runnable {

        override fun run() {
            // TODO 执行保存错误日志到卡
            //            String deviceInfo = CollectDataManager.getDebugInfosToErrorMessage(context);
            //            errorLog += "\n deviceInfo:" + deviceInfo;
            //            cacheErrorLogToSDFile(context, errorLog);

        }

    }

    companion object {

        private val TAG = "ErrorLogSave"

        private val SD_PATH = Environment.getExternalStorageDirectory()
                .absolutePath

        private val FILE_PATH = "$SD_PATH/xuetangx_error_log/"

        private var packageName: String? = null


        fun onError(context: Context) {
            ErrorLogSave(context).start()
        }

        /**
         * 获取异常日志信息
         *
         * @param paramContext
         * @return
         */
        private fun getExceptionLog(paramContext: Context): String {
            var localObject = ""
            try {
                val str1 = paramContext.packageName
                var str2 = ""
                var i1 = 0
                var i2 = 0
                val localArrayList = ArrayList<String>()
                localArrayList.add("logcat")
                localArrayList.add("-d")
                localArrayList.add("-v")
                localArrayList.add("raw")
                localArrayList.add("-s")
                localArrayList.add("AndroidRuntime:E")
                localArrayList.add("-p")
                localArrayList.add(str1)
                val localProcess = Runtime.getRuntime().exec(
                        localArrayList.toTypedArray())
                val localBufferedReader = BufferedReader(InputStreamReader(
                        localProcess.inputStream), 1024)
                var str3: String? = localBufferedReader.readLine()
                while (str3 != null) {
                    if (str3.indexOf("thread attach failed") < 0) {
                        str2 = str2 + str3 + '\n'.toString()
                    }
                    if (i2 == 0 && str3.toLowerCase().indexOf("exception") >= 0) {
                        i2 = 1
                    }
                    if (i1 != 0 || str3.indexOf(str1) < 0) {
                        str3 = localBufferedReader
                                .readLine()
                        continue
                    }
                    i1 = 1
                    str3 = localBufferedReader.readLine()
                }
                if (str2.length > 0 && i2 != 0 && i1 != 0) {
                    localObject = str2
                }
                try {
                    Runtime.getRuntime().exec("logcat -c")
                } catch (localException2: Exception) {
                    Log.e(TAG, "Failed to clear log")
                }

            } catch (localException1: Exception) {
                Log.e(TAG, "Failed to catch error log")
            }

            return localObject
        }

        /**
         * 存储错误日志到sd卡 目录名称:"/sd/itotem_error_log/" 文件名称:
         * packagename_tiem(YYYY_MM_DD-HH_mm_ss)
         *
         * @param context
         * @param errorLog
         */
        protected fun cacheErrorLogToSDFile(context: Context, errorLog: String) {
            Log.e(TAG, "cache Error Log To SD File is ruuning:$errorLog")
            if (TextUtils.isEmpty(errorLog)) {
                return
            }
            var outputStream: FileOutputStream? = null
            try {
                val curTimeM = System.currentTimeMillis()
                if (TextUtils.isEmpty(packageName)) {
                    packageName = context.packageName
                }
                // 获取新的文件名称
                val fileName = packageName + "_" + getTime(curTimeM) + ".txt"
                Log.i(TAG, "new file is :$fileName")
                // File file = new File(FILE_PATH, fileName);
                val pathFile = File(FILE_PATH)
                val file = File(pathFile, fileName)
                if (!pathFile.exists()) {
                    pathFile.mkdirs()
                }
                if (!file.exists()) {
                    file.createNewFile()
                }
                // 打开一个新的文件
                outputStream = FileOutputStream(file)
                outputStream.write(errorLog.toByteArray())
                outputStream.close()

            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                return
            } catch (e: IOException) {
                e.printStackTrace()
                return
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                    outputStream = null
                }
            }
        }

        private fun getTime(curTime: Long): String {
            val df1 = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")
            val date = Date(curTime)
            return df1.format(date)
        }

        private val a = Any()// 线程锁
    }

}
