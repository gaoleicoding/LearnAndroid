package com.android.learn.base.mmodel

import com.google.gson.Gson

class WxArticle {


    /**
     * children : []
     * courseId : 13
     * id : 408
     * name : 鸿洋
     * order : 190000
     * parentChapterId : 407
     * userControlSetTop : false
     * visible : 1
     */

    var courseId: Int = 0
    var id: Int = 0
    var name: String? = null
    var order: Int = 0
    var parentChapterId: Int = 0
    var isUserControlSetTop: Boolean = false
    var visible: Int = 0
    var children: List<*>? = null

    companion object {

        fun objectFromData(str: String): WxArticle {

            return Gson().fromJson(str, WxArticle::class.java)
        }
    }
}
