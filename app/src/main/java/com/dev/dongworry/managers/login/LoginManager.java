package com.dev.dongworry.managers.login;

import android.content.Context;
import android.content.Intent;

import com.dev.dongworry.activities.LoginActivity;
import com.dev.dongworry.beans.login.LoginInfo;
import com.dev.dongworry.managers.AppManager;
import com.dev.dongworry.utils.SPUtils;

/**
 * Created by dengyaoning on 17/5/10.
 */

public class LoginManager {

    private static LoginManager loginManager;
    private LoginInfo loginInfo;
    private Context mContext = AppManager.getInstance().getAppContext();
    private LoginManager() {

    }
    public static LoginManager getInstance() {
        if (loginManager == null) {
            synchronized (LoginManager.class) {
                loginManager = new LoginManager();
            }
        }
        return loginManager;
    }

    /**
     * 保存用户信息
     */
    public void saveUserInfo(LoginInfo info){
        SPUtils.setUserInfo(mContext,info);
        SPUtils.setIsLogin(mContext,true);
    }

    public LoginInfo getUserInfo(){
        return SPUtils.getUserInfo(mContext);
    }

    public boolean isLogin(){
        return SPUtils.isLogin(mContext);
    }

    /**
     *  登录
     */
    public void login(Context context){
        Context ctx = context == null ? mContext : context;
        Intent intent = new Intent(ctx, LoginActivity.class);
        if(context == null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        ctx.startActivity(intent);
    }

    public void logout(){
        SPUtils.setUserInfo(mContext,new LoginInfo());
        SPUtils.setIsLogin(mContext,false);
    }
}
