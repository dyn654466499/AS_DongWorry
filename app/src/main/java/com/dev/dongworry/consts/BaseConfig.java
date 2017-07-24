package com.dev.dongworry.consts;

/**
 * Created by dengyaoning on 17/7/23.
 */

public class BaseConfig {
    private static final String STG_URL = "http://112.74.160.90/DontWorry";
    private static final String STG_LOCAL_URL = "http://192.168.1.100:8080/DontWorry";

    private static final String PRD_URL = "http://112.74.160.90/DontWorry";
    private static final String PRD_LOCAL_URL = "http://192.168.1.100:8080/DontWorry";

    private static boolean isLocalTest = true;
    private static boolean isPrd = true;

    private static final String BASE_STG_URL = isLocalTest ? STG_LOCAL_URL : STG_URL;
    private static final String BASE_PRD_URL = isLocalTest ? PRD_LOCAL_URL : PRD_URL;

    private static final String BASE_URL = isPrd ? BASE_PRD_URL : BASE_STG_URL;

    public static String getRegisterUrl(){
        return BASE_URL + "/app/user/register";
    }
}
