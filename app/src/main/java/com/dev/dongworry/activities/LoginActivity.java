package com.dev.dongworry.activities;

import android.app.Activity;
import android.os.Bundle;

import com.dev.dongworry.views.LoginView;

/**
 * “登录界面”的Activity
 * @author 邓耀宁
 *
 */
public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new LoginView(this);
	}
}
