package com.android.learn.base.mmodel;

/**
 * @author quchao
 * @date 2018/2/12
 */

public class BaseData {

    public static final int SUCCESS = 0;
    public static final int FAIL = -1;
  /*
  失败信息
  {
        "data": null,
            "errorCode": -1,
            "errorMsg": "账号密码不匹配！"
    }*/

    /**
     * 0：成功，1：失败
     */
    public int errorCode;

    public String errorMsg;


    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }


}
