package com.dev.dongworry.consts;

/**
 * Created by dengyaoning on 17/7/25.
 */

public class ResponseCode {
    public static final String SUCCESS = "000000";

    public static final String LOGIN_ERROR = "000200";//账号未登录
    public static final String LOGIN_FAIL = "000201";//登录失败
    public static final String LOGIN_INVALID = "000202";//登录失效，重新登录
    public static final String LOGIN_OFFLINE = "000203";//被挤下线
}
