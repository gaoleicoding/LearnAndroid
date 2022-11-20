package com.android.learn.mpresenter

import com.android.base.mmodel.BaseListResponse
import com.android.base.mmodel.TreeBean
import com.android.base.mpresenter.BasePresenter
import com.android.base.thirdframe.rxjava.BaseObserver
import com.android.learn.mcontract.KnowledgeContract


/**
 * <pre>
 * author : lex
 * e-mail : ldlywt@163.com
 * time   : 2018/09/05
 * desc   :
 * version: 1.0
</pre> *
 */
class KnowledgePresenter : BasePresenter<KnowledgeContract.View>(), KnowledgeContract.Presenter {

    override fun getKnowledge() {

        val observable = mRestService.knowledgeTree
        val baseObserver = object : BaseObserver<BaseListResponse<List<TreeBean>>>(false) {
            override fun onNext(datas: BaseListResponse<List<TreeBean>>) {
                mView!!.showKnowledge(datas.data)
            }
        }
        baseObserver.setCancelDialog(false)
        addSubscribe(observable, baseObserver)


    }


}
