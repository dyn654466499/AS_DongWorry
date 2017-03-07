package com.dev.dongworry.models;

import android.os.Handler;
import android.os.Message;

import com.dev.dongworry.consts.ControlState;

public class SettingModel extends BaseModel{

	public SettingModel(Handler handler) {
		super(handler);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void changeModelState(int changeState) {
		// TODO Auto-generated method stub
		switch (changeState) {
		case ControlState.MODEL_SETTING_CLEARCACHE:
			break;

		default:
			break;
		}
	}

	@Override
	public void changeModelState(Message changeStateMessage) {
		// TODO Auto-generated method stub
		
	}

}
