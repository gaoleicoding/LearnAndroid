package com.wind.me.xskinloader.skinDeployer

import android.graphics.drawable.Drawable
import android.view.View

import com.wind.me.xskinloader.entity.SkinAttr
import com.wind.me.xskinloader.entity.SkinConfig
import com.wind.me.xskinloader.skinInterface.ISkinResDeployer
import com.wind.me.xskinloader.skinInterface.ISkinResourceManager

/**
 * Created by Windy on 2018/1/10.
 */

class BackgroundResDeployer : ISkinResDeployer {
    override fun deploy(view: View, skinAttr: SkinAttr, resource: ISkinResourceManager) {
        if (SkinConfig.RES_TYPE_NAME_COLOR == skinAttr.attrValueTypeName) {
            view.setBackgroundColor(resource.getColor(skinAttr.attrValueRefId))
        } else if (SkinConfig.RES_TYPE_NAME_DRAWABLE == skinAttr.attrValueTypeName) {
            val bg = resource.getDrawable(skinAttr.attrValueRefId)
            view.background = bg
        }
    }
}
