package com.dev.dongworry.volley;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.dev.dongworry.events.HttpEvent;
import com.dev.dongworry.volley.interfaces.onErrorAction;

import de.greenrobot.event.EventBus;

import static com.baidu.location.b.g.V;

/**
 * Created by dengyaoning on 17/7/25.
 */

public class ListenerFactory {
    private static Response.ErrorListener errorListener;
    private static onErrorAction errorAction;

    public static Response.ErrorListener getBaseErrorListener(){
        return getBaseErrorListener(null);
    }

    /**
     * 获取Volley错误监听器
     * @param action 各自实现处理错误的逻辑
     * @return
     */
    public static Response.ErrorListener getBaseErrorListener(onErrorAction action){
        if(errorAction != null){
            errorAction = action;
        }
        if(errorListener == null){
            synchronized (ListenerFactory.class){
                if(errorListener == null){
                    errorListener = new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            EventBus.getDefault().post(new HttpEvent().setEventFlag(HttpEvent.NETWORK_ERROR));
                            if(errorAction != null){
                                errorAction.onError();
                            }
                        }
                    };
                }
            }
        }
        return errorListener;
    }

}
