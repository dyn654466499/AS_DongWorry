package com.dev.dongworry.consts;

/**
 * Created by dengyaoning on 17/7/23.
 */

public class BaseConfig {

    public static final boolean isLocalTest = false;
    public static final boolean isPrd = false;

    private static final String STG_URL = "http://112.74.160.90:8080/DontWorry";
    private static final String STG_LOCAL_URL = "http://11.240.192.28:8080/DontWorry";

    private static final String PRD_URL = "http://112.74.160.90:8080/DontWorry";
    private static final String PRD_LOCAL_URL = "http://11.240.192.28:8080/DontWorry";

    private static final String BASE_STG_URL = isLocalTest ? STG_LOCAL_URL : STG_URL;
    private static final String BASE_PRD_URL = isLocalTest ? PRD_LOCAL_URL : PRD_URL;

    private static final String BASE_URL = isPrd ? BASE_PRD_URL : BASE_STG_URL;

    private static final String CLIENT_ID = "dontworry_client_id";

    public static String getRegisterUrl(){
        return BASE_URL + "/app/user/register";
    }

    public static String getLoginUrl(){
        return BASE_URL + "/app/user/login";
    }

    public static String getClientId(){
        return CLIENT_ID;
    }
}
