package com.wind.me.xskinloader

import android.text.TextUtils

import com.wind.me.xskinloader.entity.SkinAttr
import com.wind.me.xskinloader.skinInterface.ISkinResDeployer
import com.wind.me.xskinloader.skinDeployer.ActivityStatusBarColorResDeployer
import com.wind.me.xskinloader.skinDeployer.BackgroundResDeployer
import com.wind.me.xskinloader.skinDeployer.ImageDrawableResDeployer
import com.wind.me.xskinloader.skinDeployer.ListViewDividerResDeployer
import com.wind.me.xskinloader.skinDeployer.ListViewSelectorResDeployer
import com.wind.me.xskinloader.skinDeployer.ProgressBarIndeterminateDrawableDeployer
import com.wind.me.xskinloader.skinDeployer.TextColorHintResDeployer
import com.wind.me.xskinloader.skinDeployer.TextColorResDeployer

import java.util.HashMap

/**
 * Created by Windy on 2018/1/10.
 */

object SkinResDeployerFactory {

    val BACKGROUND = "background"
    val IMAGE_SRC = "src"
    val TEXT_COLOR = "textColor"
    val TEXT_COLOR_HINT = "textColorHint"
    val LIST_SELECTOR = "listSelector"
    val DIVIDER = "divider"

    val ACTIVITY_STATUS_BAR_COLOR = "statusBarColor"
    val PROGRESSBAR_INDETERMINATE_DRAWABLE = "indeterminateDrawable"


    //存放支持的换肤属性和对应的处理器
    private val sSupportedSkinDeployerMap = HashMap<String, ISkinResDeployer>()

    //静态注册支持的属性和处理器
    init {
        registerDeployer(BACKGROUND, BackgroundResDeployer())
        registerDeployer(IMAGE_SRC, ImageDrawableResDeployer())
        registerDeployer(TEXT_COLOR, TextColorResDeployer())
        registerDeployer(TEXT_COLOR_HINT, TextColorHintResDeployer())
        registerDeployer(LIST_SELECTOR, ListViewSelectorResDeployer())
        registerDeployer(DIVIDER, ListViewDividerResDeployer())
        registerDeployer(ACTIVITY_STATUS_BAR_COLOR, ActivityStatusBarColorResDeployer())
        registerDeployer(PROGRESSBAR_INDETERMINATE_DRAWABLE, ProgressBarIndeterminateDrawableDeployer())
    }

    fun registerDeployer(attrName: String, skinResDeployer: ISkinResDeployer?) {
        if (TextUtils.isEmpty(attrName) || null == skinResDeployer) {
            return
        }
        if (sSupportedSkinDeployerMap.containsKey(attrName)) {
            throw IllegalArgumentException("The attrName has been registed, please rename it")
        }
        sSupportedSkinDeployerMap[attrName] = skinResDeployer
    }

    fun of(attr: SkinAttr?): ISkinResDeployer? {
        return if (attr == null) {
            null
        } else of(attr.attrName)
    }

    fun of(attrName: String): ISkinResDeployer? {
        return if (TextUtils.isEmpty(attrName)) {
            null
        } else sSupportedSkinDeployerMap[attrName]
    }

    fun isSupportedAttr(attrName: String): Boolean {
        return of(attrName) != null
    }

    fun isSupportedAttr(attr: SkinAttr): Boolean {
        return of(attr) != null
    }

}
