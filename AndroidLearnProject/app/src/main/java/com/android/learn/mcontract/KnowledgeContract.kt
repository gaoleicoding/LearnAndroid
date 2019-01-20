package com.android.learn.mcontract


import com.android.learn.base.mmodel.TreeBean


class KnowledgeContract {

    interface View {
        fun showKnowledge(data: List<TreeBean>)

    }

    interface Presenter {
        fun getKnowledge()

    }

}
