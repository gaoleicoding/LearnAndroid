package com.android.learn.base.xskin

import com.wind.me.xskinloader.SkinResDeployerFactory
import com.wind.me.xskinloader.StyleParserFactory


/**
 * 扩展换肤属性和style中的换肤属性
 * Created by Windy on 2018/2/9.
 */

object ExtraAttrRegister {

    val CUSTIOM_VIEW_TEXT_COLOR = "titleTextColor"

    init {
        //增加自定义控件的自定义属性的换肤支持
        SkinResDeployerFactory.registerDeployer(CUSTIOM_VIEW_TEXT_COLOR, CustomViewTextColorResDeployer())

        //增加xml里的style中指定的View background属性换肤
        StyleParserFactory.addStyleParser(ViewBackgroundStyleParser())
    }

    //仅仅为了使类的静态方法被加载而已
    fun init() {}

}
