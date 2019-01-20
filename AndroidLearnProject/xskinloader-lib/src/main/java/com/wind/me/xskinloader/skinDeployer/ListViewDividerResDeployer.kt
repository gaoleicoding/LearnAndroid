package com.wind.me.xskinloader.skinDeployer

import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.ListView

import com.wind.me.xskinloader.entity.SkinAttr
import com.wind.me.xskinloader.entity.SkinConfig
import com.wind.me.xskinloader.skinInterface.ISkinResDeployer
import com.wind.me.xskinloader.skinInterface.ISkinResourceManager

/***
 * ListView divider属性的换肤支持（android:divider）
 * Created by Windy on 2018/1/11.
 */

class ListViewDividerResDeployer : ISkinResDeployer {

    override fun deploy(view: View, skinAttr: SkinAttr, resource: ISkinResourceManager) {
        if (view !is ListView) {
            return
        }
        if (SkinConfig.RES_TYPE_NAME_COLOR == skinAttr.attrValueTypeName) {
            val color = resource.getColor(skinAttr.attrValueRefId)
            view.divider = ColorDrawable(color)
        } else if (SkinConfig.RES_TYPE_NAME_DRAWABLE == skinAttr.attrValueTypeName) {
            view.divider = resource.getDrawable(skinAttr.attrValueRefId)
        }
    }
}
