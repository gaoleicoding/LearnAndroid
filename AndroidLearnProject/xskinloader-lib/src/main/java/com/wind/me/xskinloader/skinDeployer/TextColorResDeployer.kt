package com.wind.me.xskinloader.skinDeployer

import android.view.View
import android.widget.TextView

import com.wind.me.xskinloader.entity.SkinAttr
import com.wind.me.xskinloader.entity.SkinConfig
import com.wind.me.xskinloader.skinInterface.ISkinResDeployer
import com.wind.me.xskinloader.skinInterface.ISkinResourceManager

/**
 * Created by Windy on 2018/1/10.
 */

class TextColorResDeployer : ISkinResDeployer {
    override fun deploy(view: View, skinAttr: SkinAttr, resource: ISkinResourceManager) {
        if (view is TextView && SkinConfig.RES_TYPE_NAME_COLOR == skinAttr.attrValueTypeName) {
            view.setTextColor(resource.getColorStateList(skinAttr.attrValueRefId))
        }
    }
}
