package com.android.learn.base.mmodel

import java.io.Serializable

class FeedArticleListData: BaseData() {

    var curPage: Int = 0
    lateinit var datas: List<FeedArticleData>
    var offset: Int = 0
    var isOver: Boolean = false
    var pageCount: Int = 0
    var size: Int = 0
    var total: Int = 0

    inner class FeedArticleData : Serializable {

        lateinit var apkLink: String
        lateinit var author: String
        var chapterId: Int = 0
        lateinit var chapterName: String
        var isCollect = true
        var courseId: Int = 0
        lateinit var desc: String
        lateinit var envelopePic: String
        var id: Int = 0
        lateinit var link: String
        lateinit var niceDate: String
        lateinit var origin: String
        lateinit var projectLink: String
        var superChapterId: Int = 0
        lateinit var superChapterName: String
        var publishTime: Long = 0
        lateinit var title: String
        var visible: Int = 0
        var zan: Int = 0
        var originId: Int = 0
    }

}