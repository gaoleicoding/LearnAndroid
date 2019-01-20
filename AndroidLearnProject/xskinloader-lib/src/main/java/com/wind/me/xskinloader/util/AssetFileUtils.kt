package com.wind.me.xskinloader.util

import android.content.Context
import android.util.Log

import com.wind.me.xskinloader.entity.SkinConfig

import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

/**
 * Created by Windy on 2018/1/11.
 */

object AssetFileUtils {

    fun copyAssetFile(context: Context, originAssetFileName: String, destFileDirectory: String,
                      destFileName: String): Boolean {
        val startTime = System.currentTimeMillis()
        var `is`: InputStream? = null
        var bos: BufferedOutputStream? = null
        try {
            `is` = context.assets.open(originAssetFileName)

            val destPathFile = File(destFileDirectory)
            if (!destPathFile.exists()) {
                destPathFile.mkdirs()
            }

            val destFile = File(destFileDirectory + File.separator + destFileName)
            if (!destFile.exists()) {
                destFile.createNewFile()
            }

            val fos = FileOutputStream(destFile)
            bos = BufferedOutputStream(fos)

            val buffer = ByteArray(256)
            var length = 0
            while ((length = `is`!!.read(buffer)) > 0) {
                bos.write(buffer, 0, length)
            }
            bos.flush()
            return true
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            if (SkinConfig.DEBUG) {
                Log.e("AssetFileUtils", "copyAssetFile time = " + (System.currentTimeMillis() - startTime))
            }
            if (null != `is`) {
                try {
                    `is`.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }

            if (null != bos) {
                try {
                    bos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }

        return false
    }

}
