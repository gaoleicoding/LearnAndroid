package com.gaolei.famousfameproject.api;


import android.support.v4.BuildConfig;

/**
 * Created by liuhaiyang on 2017/8/9.
 */

public final class UrlConfig {

    private String mRelativePath;
//    private static final String DEBUG_BASE_URL = "http://47.93.24.29:8193/api/mobile/";

    private static final String DEBUG_BASE_URL = "http://47.93.24.29:8183/api/mobile/";
    private static final String DEBUG_H5_BASE_URL = "http://47.93.24.29:8191/";

    //TODO: 正式环境必须替换
    private static final String RELEASE_H5_BASE_URL = "http://47.93.24.29:8191/";
    private static final String RELEASE_BASE_URL = "http://47.93.24.29:8190/api/mobile/";

    private UrlConfig(String uri) {
        mRelativePath = uri;
    }

    private static UrlConfig with(String url) {
        return new UrlConfig(url);
    }


    private String build() {
        StringBuilder mUrlBuilder = new StringBuilder();
        mUrlBuilder.append(BuildConfig.DEBUG ? DEBUG_BASE_URL : RELEASE_BASE_URL);
        mUrlBuilder.append(mRelativePath);
        return mUrlBuilder.toString();
    }


    /**
     * 检测更新
     */
    public static final String CHECK_UPDATE = getUrl("version-check");


    /**
     * 首页
     */
    //首页
    public static final String HOME_INDEX = getUrl("index/index");
    //消息提示
    public static final String NOTICE_MESSAGE = getUrl("cms/notice-tips");
    //新闻热点列表
    public static final String HOT_NEWS_LIST = getUrl("cms/news-list");
    //平台公告列表
    public static final String PLATFORM_NOTICE_LIST = getUrl("cms/notice_list");
    //平台公告详情
    public static final String PLATFORM_NOTICE_DETAIL = getUrl("cms/notice_detail");


    /**
     * 理财
     */
    //标的卡券列表
    public static final String TICKET_LIST = getUrl("sku/market-card");
    //理财首页
    public static final String INVEST_INDEX = getUrl("sku/index");
    //理财列表（标的列表）
    public static final String INVEST_LIST = getUrl("sku/lists");
    //理财详情（标的项目详情）
    public static final String INVEST_DETAIL = getUrl("sku/info");
    //理财 项目介绍
    public static final String INVEST_INTRODUCTION = getUrl("sku/project-info");
    //理财 风控措施
    public static final String INVEST_CONTROL = getUrl("sku/risk-control");
    //理财 风险提示
    public static final String INVEST_TIP = getUrl("sku/risk-tip");
    //理财 安全保障
    public static final String INVEST_SECURITY = getUrl("sku/security");
    //投资记录
    public static final String INVEST_RECORD = getUrl("sku/invest-list");
    //验证手机验证码
    public static final String VERIFY_CODE = getUrl("verify-code");
    //立即投资
    public static final String SKU_INVEST = getUrl("invest/create");
    //预计收益
    public static final String PREDICT_INCOME = getUrl("sku/predict-income");


    /**
     * 我的
     */

    //设置交易密码
    public static final String SET_TRADE_PASSWORD = getUrl("account/set-trade-passwd");
    //修改支付密码
    public static final String CHANG_PAYMENT_PASSWORD = getUrl("account/modify-trade-passwd");
    //我的卡券列表
    public static final String TICKET_LISTS = getUrl("user/market-cards");
    //我的定制top
    public static final String ACCOUNT_ASSET_INFO = "http://599ba5823a19ba0011949bf3.mockapi.io/api/mobile/asset-info";
    //用户反馈
    public static final String FEEK_BACK = getUrl("help/suggest");
    //忘记密码
    public static final String FORGET_PASSWORD = getUrl("reset-passwd");
    //账号信息
    public static final String ACCOUNT_INFO = getUrl("account/account-info");
    //首页我的
    public static final String MINE_INDEX = getUrl("user-account");
    //我的资产
    public static final String MINE_ASSETS = getUrl("account/asset-info");
    //定制、定期、网贷列表
    public static final String MINE_USER_INVEST_RECORD = getUrl("sku/user-invest-record");
    //回款计划
    public static final String MINE_BACK_PLAN = getUrl("invest/back-plan");
    //银行卡变动
    public static final String BANK_SEND_CODE = getUrl("account/send-code");
    //我的邀请 累计邀请
    public static final String MY_INVITE = getUrl("user-invite/my-invite");
    //我的邀请 累计奖励
    public static final String MY_REWARD = getUrl("user-invite/bonus-group");

    /**
     * 用户信息
     */
    //注册
    public static final String MULTI_REGISTER = getUrl("register");
    //登录
    public static final String LOGIN = getUrl("login");
    //发送验证码
    public static final String MULTI_SEND_CODE = getUrl("send-code");
    //用户信息
    public static final String USER_INFO = getUrl("user-info");
    //退出登录
    public static final String LOGOUT = getUrl("logout");

    //忘记/重置交易密码
    public static final String RESET_TRADE_PASSWORD = getUrl("account/reset-trade-passwd");
    //验证手机验证码
    public static final String VALIDA_PHONE_CODE = getUrl("verify-code");
    //验证银行卡号
    public static final String VALIDA_BANK_NO = getUrl("account/verify-bank-card");
    //绑定银行卡
    public static final String BIND_BANK_CARD = getUrl("account/bind-bank-card");
    //获取银行卡列表
    public static final String BANK_LIST = getUrl("account/bank-list");
    //认证码
    public static final String VERIFY_SKU_CODE = getUrl("sku/verify-sku-code");
    //实名认证
    public static final String IDCARD_AUTH = getUrl("idcard-auth");
    //交易记录
    public static final String TRADE_RECORD = getUrl("account/trade-records");
    //银行卡信息
    public static final String BANK_INFO = getUrl("account/user-bank-card");
    //检查银行卡是否更换
    public static final String CHECK_BANK_CARD = getUrl("account/check-bank-card");
    //修改绑定手机
    public static final String REBIND_PHONE = getUrl("rebind-phone");
    //修改登陆密码
    public static final String CHANGE_LOGIN_PW = getUrl("modify-passwd");
    //充值
    public static final String ACCOUNT_CHARGE = getUrl("account/charge");
    //提现
    public static final String WITHDRAW_CASH = getUrl("account/withdraw");
    //根据邀请人奖励情况
    public static final String BONUS_INVITEE = getUrl("user-invite/bonus-invitee");
    //按天奖励情况
    public static final String BONUS_DAY = getUrl("user-invite/bonus-day");


    /**
     * H5
     **/
    //帮助中心
    public static final String H5_HELP = getH5Url("#/help");

    //注册协议
    public static final String H5_REGISTERPROTOCOL = getH5Url("#/registerprotocol");

    //关于我们
    public static final String H5_ABOUT = getH5Url("#/about");

    //安全保障
    public static final String H5_SECURITY = getH5Url("#/security");

    //邀请注册
    public static final String H5_REGISTER = getH5Url("#/register");

    //邀请规则
    public static final String H5_RULE = getH5Url("#/rule");

    //邀请成功
    public static final String H5_SUCCESS = getH5Url("#/success");

    //面对面邀请
    public static final String H5_QRCODE = getH5Url("#/qrcode");

    //投资协议
    public static final String H5_CONTRACT = getH5Url("#/tranfercontract");

    /**
     * 获取完整URL
     *
     * @param path
     * @return
     */
    private static String getUrl(String path) {
        return UrlConfig.with(path).build();
    }


    /**
     * 获取H5的完整链接
     *
     * @param path
     * @return
     */
    private static String getH5Url(String path) {
        StringBuilder mUrlBuilder = new StringBuilder();
        mUrlBuilder.append(BuildConfig.DEBUG ? DEBUG_H5_BASE_URL : RELEASE_H5_BASE_URL);
        mUrlBuilder.append(path);
        return mUrlBuilder.toString();
    }
}
