package com.android.learn.base.utils

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.LocaleList
import android.util.DisplayMetrics
import android.util.Log


import java.util.Locale

object LanguageUtil {

    private val TAG = "LanguageUtil"


    /**
     * 获取选择的语言设置
     *
     * @param context
     * @return
     */
    fun getSetLanguageLocale(context: Context): Locale {
        val language = SPUtils.getParam(context, "language", 0) as Int
        LogUtil.d(TAG, "language---------------$language")
        //        LogUtil.d(TAG, "getSystemLocale(context)---------------" + getSystemLocale(context));
        when (language) {
            0 -> return getSystemLocale(context)
            1 -> return Locale.CHINA
            2 -> return Locale.ENGLISH
            else -> return Locale.CHINA
        }
    }

    fun saveSelectLanguage(context: Context, select: Int) {
        SPUtils.setParam(context, "language", select)

        setApplicationLanguage(context)
    }

    fun setLocal(context: Context): Context {
        LogUtil.d(TAG, "getSetLanguageLocale---------------" + getSetLanguageLocale(context))
        return updateResources(context, getSetLanguageLocale(context))
    }

    private fun updateResources(context: Context, locale: Locale): Context {
        var context = context
        Locale.setDefault(locale)

        val res = context.resources
        val config = Configuration(res.configuration)
        if (Build.VERSION.SDK_INT >= 17) {
            config.setLocale(locale)
            context = context.createConfigurationContext(config)
        } else {
            config.locale = locale
            res.updateConfiguration(config, res.displayMetrics)
        }
        return context
    }

    /**
     * 我们都会在代码中调用context.getResource().getString()这句代码看起来没什么问题，但是你这个context要是用的是applicationContext那么问题就来了。
     * 你会发现当你切换语言后用这样方式设置的string没有改变，所以我们需要改动我们的代码。
     * 解决方法就是，在切换语言后把application的updateConfiguration也要更新了
     */
    fun setApplicationLanguage(context: Context) {
        val resources = context.applicationContext.resources
        val dm = resources.displayMetrics
        val config = resources.configuration
        val locale = getSetLanguageLocale(context)
        config.locale = locale
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val localeList = LocaleList(locale)
            LocaleList.setDefault(localeList)
            config.locales = localeList
            context.applicationContext.createConfigurationContext(config)
            Locale.setDefault(locale)
        }
        resources.updateConfiguration(config, dm)
    }

    fun getSystemLocale(context: Context): Locale {
        val locale: Locale
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0)
        } else {
            locale = Locale.getDefault()
        }
        return locale
    }

    //    public static void saveSystemCurrentLanguage(Context context) {
    //        Locale locale;
    //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
    //            locale = LocaleList.getDefault().get(0);
    //        } else {
    //            locale = Locale.getDefault();
    //        }
    //        Log.d(TAG, locale.getLanguage());
    //        SPUtil.getInstance(context).setSystemCurrentLocal(locale);
    //    }

    //    public static void onConfigurationChanged(Context context){
    ////        saveSystemCurrentLanguage(context);
    //        setLocal(context);
    //        setApplicationLanguage(context);
    //    }
}
