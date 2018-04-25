package com.gaolei.famousfameproject.mview;



import com.gaolei.famousfameproject.mmodel.BankInfoResponse;

import java.util.List;

/**
 * 倒计时mview
 * Created by caiwancheng on 2017/8/29.
 */

public interface BankListView extends BaseView {

    /**
     * 银行列表
     * @param itemBeans
     */
    void requstBankList(List<BankInfoResponse> itemBeans);
}
