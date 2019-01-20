package com.wind.me.xskinloader.skinDeployer

import android.content.res.ColorStateList
import android.view.View
import android.widget.TextView

import com.wind.me.xskinloader.entity.SkinAttr
import com.wind.me.xskinloader.entity.SkinConfig
import com.wind.me.xskinloader.skinInterface.ISkinResDeployer
import com.wind.me.xskinloader.skinInterface.ISkinResourceManager

/**
 * Created by Windy on 2018/1/11.
 * 文字提示颜色属性的换肤支持（android:textColorHint）
 */
class TextColorHintResDeployer : ISkinResDeployer {
    override fun deploy(view: View, skinAttr: SkinAttr, resource: ISkinResourceManager) {
        if (view !is TextView) {
            return
        }

        if (SkinConfig.RES_TYPE_NAME_COLOR == skinAttr.attrValueTypeName) {
            val textHintColor = resource.getColorStateList(skinAttr.attrValueRefId)
            view.setHintTextColor(textHintColor)
        }
    }
}
