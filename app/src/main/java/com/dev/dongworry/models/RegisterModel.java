package com.dev.dongworry.models;

import android.os.Handler;
import android.os.Message;

import com.dev.dongworry.consts.ControlState;

import java.util.Timer;
import java.util.TimerTask;

/**
 * “注册”view对应的model
 * @author 邓耀宁
 *
 */
public class RegisterModel extends BaseModel{

	public RegisterModel(Handler handler) {
		super(handler);
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
					Message.obtain(handler, ControlState.VIEW_VCODE_CHANGE, count, 0,
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
		case ControlState.MODEL_SURE_REGISTER:
			
			break;
			
			
		default:
			break;
		}
	}

	@Override
	public void changeModelState(Message changeStateMessage) {
		// TODO Auto-generated method stub
		switch (changeStateMessage.what) {
		
		
		default:
			break;
		}
	}
       
	 
}
