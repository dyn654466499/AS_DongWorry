package com.dev.dongworry.models;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.dev.dongworry.consts.BaseConfig;
import com.dev.dongworry.consts.ControlState;
import com.dev.dongworry.volley.MyStringRequest;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.dev.dongworry.consts.ControlState.VIEW_REGISTER_SUCCESS;
import static com.dev.dongworry.consts.ControlState.VIEW_VCODE_CHANGE;

/**
 * “注册”view对应的model
 * @author 邓耀宁
 *
 */
public class RegisterModel extends BaseModel{

	public RegisterModel(Handler handler, Context mContext) {
		super(handler,mContext);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void changeModelState(int changeState) {
		// TODO Auto-generated method stub
		switch (changeState) {
		case ControlState.MODEL_GET_VCODE:
			new Timer().schedule(new TimerTask() {
				//控制60秒
				int count = 60;
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Message.obtain(handler, VIEW_VCODE_CHANGE, count, 0,
							null).sendToTarget();
					//Activity结束时取消线程运作
					if (count == 0) {
						cancel();
					} else {
						count--;
					}
				}
			}, 0, 1000);
			break;
		default:
			break;
		}
	}

	@Override
	public void changeModelState(final Message changeStateMessage) {
		// TODO Auto-generated method stub
		switch (changeStateMessage.what) {
			case ControlState.MODEL_SURE_REGISTER:
				RequestQueue queue = Volley.newRequestQueue(mContext);
				MyStringRequest request = new MyStringRequest(
						Request.Method.POST,
						BaseConfig.getRegisterUrl(),
						(Map<String, String>)changeStateMessage.obj,
						new Response.Listener<String>() {
							@Override
							public void onResponse(String object) {
								Message.obtain(handler, VIEW_REGISTER_SUCCESS,object).sendToTarget();
							}
						},
						new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError volleyError) {

							}
						});
				queue.add(request);
				break;
			default:
				break;
		}
	}
       
	 
}
