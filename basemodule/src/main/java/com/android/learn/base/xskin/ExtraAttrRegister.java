package com.android.learn.base.xskin;

import com.wind.me.xskinloader.SkinResDeployerFactory;
import com.wind.me.xskinloader.StyleParserFactory;


/**
 * 扩展换肤属性和style中的换肤属性
 * Created by Windy on 2018/2/9.
 */

public class ExtraAttrRegister {

    public static final String CUSTIOM_VIEW_TEXT_COLOR = "titleTextColor";

    static {
        //增加自定义控件的自定义属性的换肤支持
        SkinResDeployerFactory.registerDeployer(CUSTIOM_VIEW_TEXT_COLOR, new CustomViewTextColorResDeployer());

        //增加xml里的style中指定的View background属性换肤
        StyleParserFactory.addStyleParser(new ViewBackgroundStyleParser());
    }

    //仅仅为了使类的静态方法被加载而已
    public static void init() {}

}
