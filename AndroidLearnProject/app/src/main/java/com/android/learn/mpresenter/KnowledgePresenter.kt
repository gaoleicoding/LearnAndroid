package com.android.learn.mpresenter

import com.android.learn.base.mmodel.BaseResponse
import com.android.learn.base.mmodel.TreeBean
import com.android.learn.base.mpresenter.BasePresenter
import com.android.learn.base.thirdframe.rxjava.BaseObserver
import com.android.learn.mcontract.KnowledgeContract

import io.reactivex.Observable
import io.reactivex.disposables.Disposable


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
        val baseObserver = object : BaseObserver<BaseResponse<List<TreeBean>>>(false) {
            override fun onNext(datas: BaseResponse<List<TreeBean>>) {
                mView!!.showKnowledge(datas.data)
            }
        }
        baseObserver.setCancelDialog(false)
        addSubscribe(observable, baseObserver)


    }


}
