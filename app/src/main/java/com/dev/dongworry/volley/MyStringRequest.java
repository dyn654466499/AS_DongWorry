package com.dev.dongworry.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dev.dongworry.utils.LogUtils;
import com.dev.dongworry.utils.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by dengyaoning on 17/7/24.
 */

public class MyStringRequest extends StringRequest {
    private static final String TAG = "DontWorryNet";
    private Map<String,String> params = null;
    public MyStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public MyStringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    public MyStringRequest(int method, String url, Map<String,String> params, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        if(params != null){
            this.params = params;
        }
    }

    @Override
    protected void deliverResponse(String response) {
        super.deliverResponse(response);
        try {
            JSONObject jsonObject = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogUtils.d(TAG,"返回成功：" + getUrl() + response);
    }

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);
        LogUtils.d(TAG,"返回异常：" + getUrl() + ":" + error.getMessage());
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        if(this.params != null){
            this.params.put("token", SPUtils.getToken());
            this.params.put("version", "");
            LogUtils.d(TAG,"请求:" + getUrl() + this.params.toString());
            return this.params;
        }else {
            LogUtils.d(TAG,"请求:" + getUrl() + super.getParams().toString());
            return super.getParams();
        }
    }
}
