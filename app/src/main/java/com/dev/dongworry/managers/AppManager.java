package com.dev.dongworry.managers;

import android.content.Context;

import com.dev.dongworry.managers.login.LoginManager;
import com.dev.dongworry.utils.CrashHandler;

/**
 * Created by dengyaoning on 17/5/10.
 */

public class AppManager {
    private static AppManager appManager;
    private AppManager() {

    }
    public static AppManager getInstance() {
        if (appManager == null) {
            synchronized (AppManager.class) {
                appManager = new AppManager();
            }
        }
        return appManager;
    }

    private Context appContext;

    public void setAppContext(Context context){
        appContext = context.getApplicationContext();
    }

    public Context getAppContext(){
        return appContext;
    }
}
