package com.android.learn.mcontract


import com.android.base.mmodel.TreeBean
import com.android.base.mview.BaseView


class KnowledgeContract {

    interface View : BaseView {
        fun showKnowledge(data: List<TreeBean>)

    }

    interface Presenter {
        fun getKnowledge()

    }

}
