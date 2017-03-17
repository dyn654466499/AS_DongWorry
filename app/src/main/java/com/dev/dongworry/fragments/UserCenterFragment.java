package com.dev.dongworry.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev.dongworry.R;
import com.dev.dongworry.activities.LoginActivity;
import com.dev.dongworry.activities.SettingActivity;

public class UserCenterFragment extends BaseFragment implements View.OnClickListener{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_user_center, container, false);
		view.findViewById(R.id.tv_usercenter_ability).setOnClickListener(this);
		view.findViewById(R.id.tv_usercenter_pushInfo).setOnClickListener(this);
		view.findViewById(R.id.tv_usercenter_task).setOnClickListener(this);
		view.findViewById(R.id.tv_usercenter_service).setOnClickListener(this);
		view.findViewById(R.id.tv_usercenter_setting).setOnClickListener(this);
		view.findViewById(R.id.tv_usercenter_login).setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()){
			case R.id.tv_usercenter_ability:

				break;
			case R.id.tv_usercenter_pushInfo:

				break;
			case R.id.tv_usercenter_task:

				break;
			case R.id.tv_usercenter_service:

				break;
			case R.id.tv_usercenter_setting:
				intent = new Intent(getActivity(), SettingActivity.class);
				startActivity(intent);
				break;
			case R.id.tv_usercenter_login:
				intent = new Intent(getActivity(),LoginActivity.class);
				startActivity(intent);
				break;
			default:
				break;
		}
	}
}
