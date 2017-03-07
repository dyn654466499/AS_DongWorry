package com.dev.dongworry.activities;

import android.app.Activity;
import android.os.Bundle;

import com.dev.dongworry.views.FindPWDView;


/**
 * “找回密码界面”的Activity
 * @author 邓耀宁
 *
 */
public class FindPWDActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new FindPWDView(this);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		FindPWDView.setActivityfinish(true);
	}
	
	
}
