package com.dev.dongworry.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.dev.dongworry.views.RegisterView;


/**
 * “注册界面”的Activity
 * @author 邓耀宁
 *
 */
public class RegisterActivity extends Activity {
	private RegisterView registerView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		registerView = new RegisterView(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return registerView.onKeyDown(keyCode, event);
		//return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		RegisterView.setActivityfinish(true);
	}


}
