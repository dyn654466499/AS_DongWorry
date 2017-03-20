package com.dev.dongworry.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dev.dongworry.R;
import com.dev.dongworry.activities.FindPWDActivity;
import com.dev.dongworry.interfaces.OnViewListener;

import static com.dev.dongworry.R.id.button_getVcode;

/**
 * 获取手机验证码的UI片段，可复用
 * @author 邓耀宁
 *
 */
public class VCodeFragment extends BaseFragment{
	private Button button_getVcode;
	private OnViewListener onViewListener;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_vcode, container, false);
		button_getVcode = (Button)view.findViewById(R.id.button_getVcode);
		if(onViewListener != null){
			onViewListener.onViewCreated();
		}
		return view;
	}

	public Button getButtonVcode(){
		return button_getVcode;
	}

	public void setViewCreatedListener(OnViewListener onViewListener){
		this.onViewListener = onViewListener;
	}
}
