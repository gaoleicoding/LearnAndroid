package com.android.learn.mcontract

import com.android.learn.base.mmodel.FeedArticleListData
import com.android.learn.base.mmodel.FeedArticleListData.FeedArticleData
import com.android.learn.base.mmodel.HotKeyData

/**
 * Created by gaolei on 2018/6/18.
 */

class MainActivityContract {

    interface Presenter {

        fun getHotKey()

    }

    interface View {

        fun showHotKey(list: List<HotKeyData>)

    }
}
