package com.android.learn.base.mmodel

import java.io.Serializable


class TreeBean : Serializable {

    /**
     * children : [{"children":[],"courseId":13,"id":60,"name":"Android Studio相关","order":1000,"parentChapterId":150,"visible":1},"..."]
     * courseId : 13
     * id : 150
     * name : 开发环境
     * order : 1
     * parentChapterId : 0
     * visible : 1
     */

    var courseId: Int = 0
    var id: Int = 0
    var name: String? = null
    var order: Int = 0
    var parentChapterId: Int = 0
    var visible: Int = 0
    var children: List<ChildrenBean>? = null

    class ChildrenBean {
        /**
         * children : []
         * courseId : 13
         * id : 60
         * name : Android Studio相关
         * order : 1000
         * parentChapterId : 150
         * visible : 1
         */

        var courseId: Int = 0
        var id: Int = 0
        var name: String? = null
        var order: Int = 0
        var parentChapterId: Int = 0
        var visible: Int = 0
        var children: List<*>? = null
    }
}
