package com.wind.me.xskinloader.skinDeployer

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView

import com.wind.me.xskinloader.entity.SkinAttr
import com.wind.me.xskinloader.entity.SkinConfig
import com.wind.me.xskinloader.skinInterface.ISkinResDeployer
import com.wind.me.xskinloader.skinInterface.ISkinResourceManager

/**
 * Created by Windy on 2018/1/10.
 */

class ImageDrawableResDeployer : ISkinResDeployer {
    override fun deploy(view: View, skinAttr: SkinAttr, resource: ISkinResourceManager) {
        if (view !is ImageView) {
            return
        }
        var drawable: Drawable? = null
        if (SkinConfig.RES_TYPE_NAME_COLOR == skinAttr.attrValueTypeName) {
            drawable = ColorDrawable(resource.getColor(skinAttr.attrValueRefId))
        } else if (SkinConfig.RES_TYPE_NAME_DRAWABLE == skinAttr.attrValueTypeName) {
            drawable = resource.getDrawable(skinAttr.attrValueRefId)
        }
        if (drawable != null) {
            view.setImageDrawable(drawable)
        }
    }
}
