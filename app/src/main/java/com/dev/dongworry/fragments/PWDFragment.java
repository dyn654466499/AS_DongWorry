package com.dev.dongworry.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.dev.dongworry.R;


/**
 * 输入密码的UI片段，可复用
 * @author 邓耀宁
 *
 */
@SuppressLint("NewApi") 
public class PWDFragment extends BaseFragment {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		final EditText editText_password = (EditText) getActivity()
				.findViewById(R.id.editText_password);
		editText_password.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				int pwd_length = editText_password.getText().length();
				if (!hasFocus && pwd_length >= 0 && pwd_length < 6) {
					//editText_password.setTag("");
					/**
					 * 如果Activity结束了，取消提示
					 */
					if (!getActivity().isDestroyed()) {
						showTip(R.string.tips_pwdIsNotFormat);
					}
				}
//				else{
//					editText_password.setTag("complete");
//				}
			}
		});
		final ImageButton imageButton_showPwd = (ImageButton) getActivity()
				.findViewById(R.id.imageButton_showPwd);
		imageButton_showPwd.setColorFilter(getColor(android.R.color.darker_gray));
		//设置隐藏标识
		imageButton_showPwd.setTag("hidden");
		imageButton_showPwd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LinearLayout linearLayout_findpwd_surePWD = (LinearLayout) getActivity()
						.findViewById(R.id.linearLayout_findpwd_surePWD);
				EditText editText_surePWD = null;
				//增加“找回密码”界面，确认密码框是否显示密码的判断
				boolean isShowSurePWD = linearLayout_findpwd_surePWD!=null;

				if ((String) imageButton_showPwd.getTag() == "hidden") {
					editText_password
							.setTransformationMethod(HideReturnsTransformationMethod
									.getInstance());
					
					if (isShowSurePWD) {
						editText_surePWD = (EditText) getActivity()
								.findViewById(R.id.editText_surePWD);
						editText_surePWD
								.setTransformationMethod(HideReturnsTransformationMethod
										.getInstance());
					}

					imageButton_showPwd.setTag("show");
					imageButton_showPwd.setColorFilter(getColor(R.color.themeColor));
				} else {
					editText_password
							.setTransformationMethod(PasswordTransformationMethod
									.getInstance());
					//
					if (isShowSurePWD) {
						editText_surePWD = (EditText) getActivity()
								.findViewById(R.id.editText_surePWD);
						editText_surePWD
								.setTransformationMethod(PasswordTransformationMethod
										.getInstance());
					}

					imageButton_showPwd.setTag("hidden");
					imageButton_showPwd.setColorFilter(getColor(android.R.color.darker_gray));
				}
				editText_password.postInvalidate();
				// 切换后将EditText光标置于末尾
				CharSequence charSequence = editText_password.getText();
				if (charSequence instanceof Spannable) {
					Spannable spanText = (Spannable) charSequence;
					Selection.setSelection(spanText, charSequence.length());
				}
				if(isShowSurePWD){
					charSequence = editText_surePWD.getText();
					if (charSequence instanceof Spannable) {
						Spannable spanText = (Spannable) charSequence;
						Selection.setSelection(spanText, charSequence.length());
					}
				}
				
			}
		});
	}

	@Override
	public void onAttach(final Activity mActivity) {
		// TODO Auto-generated method stub
		super.onAttach(mActivity);
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
		View view = inflater.inflate(R.layout.fragment_pwd, container, false);
		return view;
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
	}

}
