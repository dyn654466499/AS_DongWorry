package com.dev.dongworry.models;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dev.dongworry.consts.BaseConfig;
import com.dev.dongworry.volley.MyStringRequest;

import java.util.HashMap;
import java.util.Map;

import static com.dev.dongworry.consts.ControlState.MODEL_LOGIN;
import static com.dev.dongworry.consts.ControlState.VIEW_LOGIN_SUCCESS;
import static com.dev.dongworry.consts.ControlState.VIEW_REGISTER_SUCCESS;

/**
 * “登录”view对应的model
 * @author 邓耀宁
 *
 */
public class LoginModel extends BaseModel{

	public LoginModel(Handler handler, Context mContext) {
		super(handler,mContext);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void changeModelState(int changeState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeModelState(final Message changeStateMessage) {
		// TODO Auto-generated method stub
		switch (changeStateMessage.what){
			case MODEL_LOGIN:
				RequestQueue queue = Volley.newRequestQueue(mContext);
				MyStringRequest request = new MyStringRequest(
						Request.Method.POST,
						BaseConfig.getLoginUrl(),
						(Map<String, String>)changeStateMessage.obj,
						new Response.Listener<String>() {
							@Override
							public void onResponse(String object) {
								Message.obtain(handler, VIEW_LOGIN_SUCCESS).sendToTarget();
							}
						},
						new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError volleyError) {

							}
						});
				queue.add(request);
				break;
		}
	}

}
