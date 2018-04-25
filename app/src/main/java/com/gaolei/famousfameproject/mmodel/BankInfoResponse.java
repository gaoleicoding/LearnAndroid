package com.gaolei.famousfameproject.mmodel;

import java.io.Serializable;

/**
 * Created by caiwancheng on 2017/8/29.
 */

public class BankInfoResponse implements Serializable{


    /**
     * bank_code : string,银行代码
     * bank_card_no : string,银行卡号
     * bank_card_id : integer,卡号ID
     * bank_name : string,银行名称
     * logo_big : string,大图
     * logo_middle : string,中图
     * logo_small : string,小图
     * logo_bg : 卡背景
     * once_limit : integer,单笔限额（单位：分）
     * single_day_limit : string,单日限额（单位：分）
     */

    public String bank_code;
    public String bank_card_no;
    public int bank_card_id;
    public String bank_name;
    public String logo_big;
    public String logo_small;
    public String once_limit;
    public String logo_back_small;
    public String logo_back_big;
    public long withdraw_once_limit;
    public long withdraw_day_limit;
    public long charge_day_limit;
    public long charge_once_limit;
}
