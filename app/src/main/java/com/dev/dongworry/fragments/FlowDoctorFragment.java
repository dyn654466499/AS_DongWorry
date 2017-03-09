package com.dev.dongworry.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.dev.dongworry.R;
import com.dev.dongworry.activities.EvaluateActivity;
import com.dev.dongworry.activities.GoodsActivity;
import com.dev.dongworry.activities.GoodsDetailActivity;
import com.dev.dongworry.activities.LoginActivity;
import com.dev.dongworry.activities.SettingActivity;
import com.dev.dongworry.utils.NetUtil;

public class FlowDoctorFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_flow_doctor, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		Button button_register = (Button) getActivity().findViewById(R.id.button_register);
		button_register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), GoodsActivity.class);
				getActivity().startActivity(intent);
//				Thread loginThread = new Thread() {
//					@Override
//					public void run() {
//						super.run();
//						HashMap<String, String> map = new HashMap<String, String>();
//						map.put("grant_type", "password");
//						map.put("client_id", "daemon_test");
//						map.put("client_secret", "123456");
//						map.put("scope", "");
//
//						NetworkUtils.setTimeout(10);
//						String json = NetworkUtils.HttpPost("http://192.168.137.1/daemon/OAuth2_Server/API/token.php", map);
//						Log.e(getTag(), json+"!");
//						
//					}
//
//				};
//
//				loginThread.setDaemon(true);
//				loginThread.start();
//				testLocalIpAndMac();
				
			}
		});
		
		
		Button button_login = (Button) getActivity().findViewById(R.id.button_login);
		button_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), LoginActivity.class);
				getActivity().startActivity(intent);
			}
		});
		
		Button button_setting= (Button) getActivity().findViewById(R.id.button_setting);
		button_setting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), SettingActivity.class);
				getActivity().startActivity(intent);
			}
		});
		
		Button button_ratingBar= (Button) getActivity().findViewById(R.id.button_ratingBar);
		button_ratingBar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
				getActivity().startActivity(intent);
			}
		});
		
		Button button_evaluate_test= (Button) getActivity().findViewById(R.id.button_evaluate_test);
		button_evaluate_test.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), EvaluateActivity.class);
				getActivity().startActivity(intent);
			}
		});
	}
    public void testLocalIpAndMac(){
        Log.e(getTag(), "IP: "+ NetUtil.getLocalIpAddress()+", MAC: "+ NetUtil.getLocalMacAddress(getActivity()));
    }
}
