package com.dev.dongworry.views;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.dev.dongworry.R;
import com.dev.dongworry.activities.RegisterActivity;

public class MainView extends BaseView{

	public MainView(Activity mActivity) {
		super(mActivity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		mActivity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mActivity.setContentView(R.layout.fragment_flow_doctor);
		Button button_register = (Button)mActivity.findViewById(R.id.button_register);
		button_register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mActivity,RegisterActivity.class);
				mActivity.startActivity(intent);
			}
		});
	}

	
}
