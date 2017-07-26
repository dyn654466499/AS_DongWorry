package com.dev.dongworry.beans.login;

import java.io.Serializable;

/**
 * Created by dengyaoning on 17/5/10.
 */

public class LoginInfo implements Serializable{
    public String mobile = "";
//    public String imageUrl = "";
//    public String userName = "";
//    public String pwd = "";
    public String access_token = "";

    public String getAccess_token() {
        return access_token;
    }

    public LoginInfo setAccess_token(String access_token) {
        this.access_token = access_token;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public LoginInfo setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }
}
