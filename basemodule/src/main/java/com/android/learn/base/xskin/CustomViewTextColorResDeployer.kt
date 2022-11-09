package com.android.learn.base.xskin

import android.view.View

import com.wind.me.xskinloader.entity.SkinAttr
import com.wind.me.xskinloader.entity.SkinConfig
import com.wind.me.xskinloader.skinInterface.ISkinResDeployer
import com.wind.me.xskinloader.skinInterface.ISkinResourceManager

/**
 * Created by Windy on 2018/1/10.
 */

class CustomViewTextColorResDeployer : ISkinResDeployer {

    override fun deploy(view: View, skinAttr: SkinAttr, resource: ISkinResourceManager) {
        if (view !is CustomTitleView) {
            return
        }
        if (SkinConfig.RES_TYPE_NAME_COLOR == skinAttr.attrValueTypeName) {
            view.setTextColor(resource.getColor(skinAttr.attrValueRefId))
        }
    }
}
