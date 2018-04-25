package com.gaolei.famousfameproject.net.client;


import com.gaolei.famousfameproject.fragment.BaseFragment;
import com.gaolei.famousfameproject.api.RestService;
import com.gaolei.famousfameproject.api.UrlConfig;
import com.gaolei.famousfameproject.application.FJDApplication;
import com.gaolei.famousfameproject.mmodel.BankInfoResponse;
import com.gaolei.famousfameproject.mmodel.BaseRequest;
import com.gaolei.famousfameproject.mpresenter.BasePresenter;
import com.gaolei.famousfameproject.mview.BankListView;
import com.gaolei.famousfameproject.utils.ToastUtil;

import java.util.List;

/**
 * Created by caiwancheng on 2017/8/30.
 */

public class BankListPresenter extends BasePresenter<BankListView> {
    /**
     * 加载银行列表
     */
    public void loadBankList(final BaseFragment fragment) {
        mView.showLoading();
        RestService restService = RestApiProvider.getInstance().getApiService(RestService.class);

        mCall = restService.postData(UrlConfig.BANK_LIST, new BaseRequest());
        mCall.enqueue(new RestBaseCallBack<List<BankInfoResponse>>() {
            @Override
            public void onResponse(List<BankInfoResponse> itemBeans) {
                mView.hideLoading();
                    mView.requstBankList(itemBeans);
            }


            @Override
            public void onFailure(Throwable error, int code, String msg) {
                if (mView != null) {
                        ToastUtil.showShortToast(FJDApplication.getZMApplication(), msg);
                    mView.hideLoading();
                }
            }
        });
    }
}
