package com.android.base.utils

import android.app.ActivityManager
import android.content.ClipboardManager
import android.content.Context
import android.os.Looper
import android.text.TextUtils
import android.view.Gravity
import android.widget.Toast


import com.android.base.application.CustomApplication

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.regex.Pattern

/**
 * Created by gaolei on 2018/6/15.
 */

object Utils {

    var intPattern = Pattern.compile("^[-+]?[0-9]")
    var decimalPattern = Pattern.compile("^[-+]?[0-9]+(\\.[0-9]+)?$")

    /**
     * md5 加密
     *
     * @param str 要加密的字符串
     * @return
     */
    fun md5Encode(text: String): String {
        try {
            //获取md5加密对象
            val instance: MessageDigest = MessageDigest.getInstance("MD5")
            //对字符串加密，返回字节数组
            val digest:ByteArray = instance.digest(text.toByteArray())
            var sb : StringBuffer = StringBuffer()
            for (b in digest) {
                //获取低八位有效值
                var i :Int = b.toInt() and 0xff
                //将整数转化为16进制
                var hexString = Integer.toHexString(i)
                if (hexString.length < 2) {
                    //如果是一位的话，补0
                    hexString = "0" + hexString
                }
                sb.append(hexString)
            }
            return sb.toString()

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return ""
    }


    fun getTopActivity(context: Context): String {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val taskInfo = manager.getRunningTasks(1)[0]
        val componentInfo = taskInfo.topActivity
        var shortClassName = componentInfo.shortClassName    //类名
        //        String className = info.topActivity.getClassName();              //完整类名
        //        String packageName = info.topActivity.getPackageName();
        val index = shortClassName.lastIndexOf(".")
        shortClassName = shortClassName.substring(index + 1)
        return shortClassName
    }

    //判断手机号是否正确
    fun isMobileNO(mobile: String): Boolean {

        val telRegex = "[1][23456789]\\d{9}"//"[1]"代表第1位为数字1，"[35678]"代表第二位可以为23456789中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。

        if (TextUtils.isEmpty(mobile)) {
            showToast("请输入正确的手机号", true)
            return false
        } else if (!mobile.matches(telRegex.toRegex())) {
            showToast("请输入正确的手机号", true)
            return false
        }
        return true

    }

    /**
     * 判断是否是银行卡号
     *
     * @param cardId
     * @return
     */
    fun checkBankCard(cardId: String): Boolean {
        val bit = getBankCardCheckCode(cardId
                .substring(0, cardId.length - 1))
        return if (bit == 'N') {
            false
        } else cardId[cardId.length - 1] == bit
    }

    private fun getBankCardCheckCode(nonCheckCodeCardId: String?): Char {
        if (nonCheckCodeCardId == null
                || nonCheckCodeCardId.trim { it <= ' ' }.length == 0
                || !nonCheckCodeCardId.matches("//d+".toRegex())) {
            // 如果传的不是数据返回N
            return 'N'
        }
        val chs = nonCheckCodeCardId.trim { it <= ' ' }.toCharArray()
        var luhmSum = 0
        var i = chs.size - 1
        var j = 0
        while (i >= 0) {
            var k = chs[i] - '0'
            if (j % 2 == 0) {
                k *= 2
                k = k / 10 + k % 10
            }
            luhmSum += k
            i--
            j++
        }
        return if (luhmSum % 10 == 0) '0' else (10 - luhmSum % 10 + '0'.toInt()).toChar()
    }

    fun showToast(content: String, isShort: Boolean) {
        try {
            val toast = Toast.makeText(CustomApplication.context, content, if (isShort) Toast.LENGTH_SHORT else Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        } catch (e: Exception) {
            //解决在子线程中调用Toast的异常情况处理
            Looper.prepare()
            Toast.makeText(CustomApplication.context, content, if (isShort) Toast.LENGTH_SHORT else Toast.LENGTH_LONG).show()
            Looper.loop()
        }

    }

    fun showToast(content: String, isShort: Boolean, gravity: Int) {
        try {
            val toast = Toast.makeText(CustomApplication.context, content, if (isShort) Toast.LENGTH_SHORT else Toast.LENGTH_LONG)
            toast.setGravity(gravity, 0, 0)
            toast.show()
        } catch (e: Exception) {
            //解决在子线程中调用Toast的异常情况处理
            Looper.prepare()
            Toast.makeText(CustomApplication.context, content, if (isShort) Toast.LENGTH_SHORT else Toast.LENGTH_LONG).show()
            Looper.loop()
        }

    }

    fun ms2Date(_ms: Long): String {
        val date = Date(_ms)
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        return format.format(date)
    }

    fun keepDecimal2(value: Float): String {
        val ddf1 = NumberFormat.getNumberInstance()
        ddf1.maximumFractionDigits = 2
        return ddf1.format(value.toDouble())
    }

    fun copyTxt(context: Context, content: String) {
        val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        // 将文本内容放到系统剪贴板里。
        cm.text = content
        Toast.makeText(context, "复制成功，可以分享给朋友们了", Toast.LENGTH_LONG).show()
    }

    fun stringToInt(value: String): Int {
        return Integer.parseInt(value.trim { it <= ' ' })
    }

    fun stringToDouble(value: String): Double {
        return java.lang.Double.parseDouble(value.trim { it <= ' ' })
    }

    fun stringToFloat(value: String): Float {
        return java.lang.Float.parseFloat(value.trim { it <= ' ' })
    }

    fun stringToDigit(value: String): Float {


        if (intPattern.matcher(value).matches()) {
            //数字
            return Integer.parseInt(value.trim { it <= ' ' }).toFloat()
        } else if (decimalPattern.matcher(value).matches()) {
            //小数
            return java.lang.Float.parseFloat(value.trim { it <= ' ' })
        } else {
            //非数字
            showToast("数据格式转化出错", true)
        }
        return java.lang.Float.parseFloat(value.trim { it <= ' ' })
    }

    fun getVersionCode(context: Context): Int {
        try {
            val packageManager = context.packageManager
            val packageInfo = packageManager.getPackageInfo(
                    context.packageName, 0)
            return packageInfo.versionCode
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return 0
    }

    fun getVersionName(context: Context): String {
        try {
            val packageManager = context.packageManager
            val packageInfo = packageManager.getPackageInfo(
                    context.packageName, 0)
            return packageInfo.versionName
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    //假设传入的日期格式是yyyy-MM-dd HH:mm:ss, 也可以传入yyyy-MM-dd，如2018-1-1或者2018-01-01格式

    fun isValidDate(strDate: String): Boolean {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2018-02-29会被接受，并转换成2018-03-01

            format.isLenient = false
            val date = format.parse(strDate)

            //判断传入的yyyy年-MM月-dd日 字符串是否为数字
            val sArray = strDate.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (s in sArray) {
                val isNum = s.matches("[0-9]+".toRegex())
                //+表示1个或多个（如"3"或"225"），*表示0个或多个（[0-9]*）（如""或"1"或"22"），?表示0个或1个([0-9]?)(如""或"7")
                if (!isNum) {
                    return false
                }
            }
        } catch (e: Exception) {
            // e.printStackTrace();
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return false
        }

        return true
    }

}
