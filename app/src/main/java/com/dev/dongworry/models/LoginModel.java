package com.dev.dongworry.models;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.dev.dongworry.beans.login.LoginInfo;
import com.dev.dongworry.consts.BaseConfig;
import com.dev.dongworry.consts.ResponseCode;
import com.dev.dongworry.managers.login.LoginManager;
import com.dev.dongworry.utils.RSAUtils;
import com.dev.dongworry.volley.ListenerFactory;
import com.dev.dongworry.volley.MyStringRequest;

import org.json.JSONObject;

import java.util.Map;

import static com.dev.dongworry.consts.ControlState.MODEL_LOGIN;
import static com.dev.dongworry.consts.ControlState.VIEW_LOGIN_FAIL;
import static com.dev.dongworry.consts.ControlState.VIEW_LOGIN_SUCCESS;

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
			/**
			 * 登录
			 */
			case MODEL_LOGIN:
				RequestQueue queue = Volley.newRequestQueue(mContext);
				Map<String,String> params = (Map<String, String>)changeStateMessage.obj;
				final String mobile = params.get("mobile");
				params.put("mobile", RSAUtils.encryptByPublicKey(mobile));
				MyStringRequest request = new MyStringRequest(
						Request.Method.POST,
						BaseConfig.getLoginUrl(),
						params,
						new Response.Listener<String>() {
							@Override
							public void onResponse(String object) {
								try{
									JSONObject jsonObject = new JSONObject(object);
									switch (jsonObject.optString("code")){
										case ResponseCode.SUCCESS:
											LoginManager.getInstance().saveUserInfo(
													new LoginInfo()
															.setAccess_token(jsonObject.optString("access_token"))
															.setMobile(mobile)
											);
											Message.obtain(handler, VIEW_LOGIN_SUCCESS).sendToTarget();
											break;
										default:
											Message.obtain(handler, VIEW_LOGIN_FAIL, jsonObject.optString("msg")).sendToTarget();
											break;
									}
								}catch (Exception e){
									e.printStackTrace();
								}
							}
						},
						ListenerFactory.getBaseErrorListener());
				queue.add(request);
				break;
		}
	}

}
