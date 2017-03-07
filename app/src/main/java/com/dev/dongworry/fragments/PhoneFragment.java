package com.dev.dongworry.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.Toast;

import com.dev.dongworry.R;
import com.dev.dongworry.utils.CommonUtils;

/**
 * 输入手机号码的UI片段，可复用
 * @author 邓耀宁
 *
 */
@SuppressLint("NewApi") 
public class PhoneFragment extends Fragment{
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		final EditText editText_phone = (EditText)getActivity().findViewById(R.id.editText_phone);
		editText_phone.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				String phoneNum = editText_phone.getText().toString();
				if (!hasFocus && !CommonUtils.isPhoneNum(phoneNum)) {
					//editText_phone.setTag("");
					/**
					 * 如果Activity结束了，取消提示
					 */
					if (!getActivity().isDestroyed()) {
						Toast.makeText(getActivity(),
								getString(R.string.phoneNumIsNotFormat),
								Toast.LENGTH_SHORT).show();
					}
				} 
//				else {
//					editText_phone.setTag("complete");
//				}
			}
		});
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_phone, container, false);
		return view;
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
	}

}
