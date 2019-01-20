package com.android.learn.base.mmodel


class BannerListData : BaseData() {

    //    {
    //        "desc":"最新项目上线啦~", "id":13, "imagePath":
    //        "http://www.wanandroid.com/blogimgs/5ae04af4-72b9-4696-81cb-1644cdcd2d29.jpg", "isVisible":
    //        1, "order":0, "title":"最新项目上线啦~", "type":0, "url":"http://www.wanandroid.com/pindex"
    //    }


    var data: List<BannerData>? = null

    inner class BannerData {

        var id: Int = 0
        var url: String? = null
        var imagePath: String? = null
        var title: String? = null
        var desc: String? = null
        var isVisible: Int = 0
        var order: Int = 0
        var type: Int = 0

    }


}