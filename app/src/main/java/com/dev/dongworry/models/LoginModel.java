package com.dev.dongworry.models;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.dev.dongworry.beans.login.LoginInfo;
import com.dev.dongworry.consts.BaseConfig;
import com.dev.dongworry.consts.ResponseCode;
import com.dev.dongworry.consts.login.LoginEvent;
import com.dev.dongworry.managers.login.LoginManager;
import com.dev.dongworry.utils.LogUtils;
import com.dev.dongworry.utils.MD5Utils;
import com.dev.dongworry.utils.RSAUtils;
import com.dev.dongworry.volley.ListenerFactory;
import com.dev.dongworry.volley.MyStringRequest;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import org.json.JSONObject;

import java.util.Map;

import de.greenrobot.event.EventBus;

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
				final String password = params.get("password");
				params.put("password", RSAUtils.encryptByPublicKey(password));
				MyStringRequest request = new MyStringRequest(
						Request.Method.POST,
						BaseConfig.getLoginUrl(),
						params,
						new Response.Listener<String>() {
							@Override
							public void onResponse(String object) {
								try{
									final JSONObject jsonObject = new JSONObject(object);
									switch (jsonObject.optString("code")){
										case ResponseCode.SUCCESS:
											 EMClient.getInstance().login(mobile, MD5Utils.getMd5(password), new EMCallBack() {//回调
												@Override
												public void onSuccess() {
													LoginManager.getInstance().saveUserInfo(
															new LoginInfo()
																	.setAccess_token(jsonObject.optString("access_token"))
																	.setMobile(mobile)
													);
													EMClient.getInstance().groupManager().loadAllGroups();
													EMClient.getInstance().chatManager().loadAllConversations();
													Message.obtain(handler, VIEW_LOGIN_SUCCESS).sendToTarget();
													LogUtils.d("登录聊天服务器成功！");
												}

												@Override
												public void onProgress(int progress, String status) {

												}

												@Override
												public void onError(int code, String message) {
													Message.obtain(handler, VIEW_LOGIN_FAIL, message).sendToTarget();
													LogUtils.d("登录聊天服务器失败！");
												}
											});
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
