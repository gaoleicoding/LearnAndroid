package com.android.learn.base.mmodel

import java.io.Serializable

class FeedArticleListData {

    var curPage: Int = 0
    var datas: List<FeedArticleData>
    var offset: Int = 0
    var isOver: Boolean = false
    var pageCount: Int = 0
    var size: Int = 0
    var total: Int = 0

    inner class FeedArticleData : Serializable {

        var apkLink: String
        var author: String
        var chapterId: Int = 0
        var chapterName: String
        var isCollect = true
        var courseId: Int = 0
        var desc: String
        var envelopePic: String
        var id: Int = 0
        var link: String
        var niceDate: String
        var origin: String
        var projectLink: String
        var superChapterId: Int = 0
        var superChapterName: String
        var publishTime: Long = 0
        var title: String
        var visible: Int = 0
        var zan: Int = 0
        var originId: Int = 0
    }

}