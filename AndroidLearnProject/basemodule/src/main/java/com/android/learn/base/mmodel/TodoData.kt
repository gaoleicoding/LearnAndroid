package com.android.learn.base.mmodel

import java.io.Serializable

class TodoData {


    /**
     * curPage : 1
     * datas : [{"completeDate":null,"completeDateStr":"","content":"圣诞节快乐","date":1545667200000,"dateStr":"2018-12-25","id":5566,"priority":0,"status":0,"title":"圣诞节","type":0,"userId":13479},{"completeDate":null,"completeDateStr":"","content":"圣诞夜快乐","date":1545580800000,"dateStr":"2018-12-24","id":5564,"priority":0,"status":0,"title":"圣诞夜","type":0,"userId":13479}]
     * offset : 0
     * over : true
     * pageCount : 1
     * size : 20
     * total : 2
     */

    var curPage: Int = 0
    var offset: Int = 0
    var isOver: Boolean = false
    var pageCount: Int = 0
    var size: Int = 0
    var total: Int = 0
    var datas: List<DatasBean>? = null

    class DatasBean : Serializable {
        /**
         * completeDate : null
         * completeDateStr :
         * content : 圣诞节快乐
         * date : 1545667200000
         * dateStr : 2018-12-25
         * id : 5566
         * priority : 0
         * status : 0
         * title : 圣诞节
         * type : 0
         * userId : 13479
         */

        var completeDate: Any? = null
        var completeDateStr: String? = null
        var content: String? = null
        var date: Long = 0
        var dateStr: String? = null
        var id: Int = 0
        var priority: Int = 0
        var status: Int = 0
        var title: String? = null
        var type: Int = 0
        var userId: Int = 0
    }
}
