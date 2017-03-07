package com.dev.dongworry.views;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.dongworry.R;
import com.dev.dongworry.consts.ControlState;
import com.dev.dongworry.controlers.BaseControler;
import com.dev.dongworry.controlers.FindPWDControler;
import com.dev.dongworry.fragments.PWDFragment;
import com.dev.dongworry.fragments.PhoneFragment;
import com.dev.dongworry.fragments.VCodeFragment;
import com.dev.dongworry.utils.CommonUtils;


/**
 * “找回密码”界面的实现
 * @author 邓耀宁
 *
 */

public class FindPWDView extends BaseView implements BaseControler.ViewChangeListener {

	private Button button_findpwd_next,button_getVcode;
	private TextView textView_findpwd_tips;
	private Fragment fragment_phone, fragment_vcode,fragment_pwd;
	private EditText editText_phone, editText_vcode,editText_pwd,editText_surePWD;
    private LinearLayout fragment_findpwd_container;
    private FindPWDControler findPWDControler;
	
	public FindPWDView(Activity mActivity) {
		super(mActivity);
		// TODO Auto-generated constructor stub
		findPWDControler = new FindPWDControler(mActivity);
		findPWDControler.setViewChangeListener(this);
	}

	
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		mActivity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mActivity.setContentView(R.layout.activity_find_pwd);
		// 若要动态添加fragment，第一个fragment必须要是动态加上去，不能用xml，否则第二个fragment将无法动态替换第一个fragment。
		fragment_phone = new PhoneFragment();
		
		fragment_pwd = new PWDFragment();
		mActivity.getFragmentManager().beginTransaction()
				.replace(R.id.fragment_findpwd_container, fragment_phone)
				.commit();
        
		
		fragment_findpwd_container = (LinearLayout)mActivity.findViewById(R.id.fragment_findpwd_container);
		
		ButtonClickListener buttonClickListener = new ButtonClickListener();

		ImageButton imageButton_findpwd_back = (ImageButton) mActivity
				.findViewById(R.id.imageButton_findpwd_back);
		imageButton_findpwd_back.setOnClickListener(buttonClickListener);

		button_findpwd_next = (Button) mActivity
				.findViewById(R.id.button_findpwd_next);
		button_findpwd_next.setOnClickListener(buttonClickListener);


		
		textView_findpwd_tips = (TextView) mActivity
				.findViewById(R.id.textView_findpwd_tips);
	}

	class ButtonClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.imageButton_findpwd_back:
				finish();
				break;
			case R.id.button_findpwd_next:
				/**
				 *  密码找回功能第一步，如果当前是输入手机号码的fragment，则跳转。
				 */
				if (fragment_phone.isAdded()) {
					editText_phone = (EditText) mActivity
							.findViewById(R.id.editText_phone);
					String phoneNumber = editText_phone.getText().toString();
					// 如果符合手机号码格式
					if (CommonUtils.isPhoneNum(phoneNumber)) {
						fragment_vcode = new VCodeFragment() {

							@Override
							public void onActivityCreated(
									Bundle savedInstanceState) {
								// TODO Auto-generated method stub
								super.onActivityCreated(savedInstanceState);
								button_getVcode = (Button) mActivity
										.findViewById(R.id.button_getVcode);
								button_getVcode
										.setOnClickListener(ButtonClickListener.this);
							}

							@Override
							public void onDetach() {
								// TODO Auto-generated method stub
								super.onDetach();
							}
						};
						mActivity
								.getFragmentManager()
								.beginTransaction()
								.replace(R.id.fragment_findpwd_container,
										fragment_vcode).commit();
						textView_findpwd_tips.setText(String.format(
								getString(R.string.sendPhoneNumber),
								phoneNumber));

					} else {
						Toast.makeText(mActivity,
								getString(R.string.phoneNumIsNotFormat),
								Toast.LENGTH_SHORT).show();
					}
				}
				/**
				 *  密码找回功能第二步，如果当前是输入验证码的fragment，则跳转
				 */
				else if (fragment_vcode.isAdded()) {
					editText_vcode = (EditText) mActivity
							.findViewById(R.id.editText_vcode);
					String vcode = editText_vcode.getText().toString();
					// 判断vcode是否正确
					if ("".equals(vcode)) {
						mActivity
								.getFragmentManager()
								.beginTransaction()
								.replace(R.id.fragment_findpwd_container,
										fragment_pwd).commit();
						button_findpwd_next
								.setText(getString(R.string.complete));
						fragment_findpwd_container
								.setBackgroundResource(R.drawable.frame);
						textView_findpwd_tips
								.setText(getString(R.string.verificateSuccessfully));

						// 重新设置大小
						LinearLayout linearLayout_findpwd = (LinearLayout) mActivity
								.findViewById(R.id.linearLayout_findpwd);
						LayoutParams params = (LayoutParams) linearLayout_findpwd
								.getLayoutParams();
						params.height = 360;
						linearLayout_findpwd.setLayoutParams(params);

						// 显示确认密码UI
						LinearLayout linearLayout_findpwd_surePWD = (LinearLayout) mActivity
								.findViewById(R.id.linearLayout_findpwd_surePWD);
						linearLayout_findpwd_surePWD
								.setVisibility(View.VISIBLE);

					} else {

					}
				}
				/**
				 *  密码找回功能第三步。
				 */
				else {
					editText_pwd = (EditText) mActivity
							.findViewById(R.id.editText_password);
					editText_surePWD = (EditText) mActivity
							.findViewById(R.id.editText_surePWD);
					String pwd = editText_pwd.getText().toString();
					String sure_pwd = editText_surePWD.getText().toString();
					//if ("complete".equals((String) editText_pwd.getTag())) {
						if (pwd.equals(sure_pwd)) {
                          //完成
							
						}else{
							//两次输入密码不一致
							Toast.makeText(mActivity,
									getString(R.string.pwdIsNotMatched),
									Toast.LENGTH_SHORT).show();
						}
//					}else{
//						//密码不符合6-12位
//						Toast.makeText(mActivity,
//								getString(R.string.pwdIsNotFormat),
//								Toast.LENGTH_SHORT).show();
//					}
				}
				break;

			case R.id.button_getVcode:
				button_getVcode.setTag("clicked");
				findPWDControler.notifyModelChange(ControlState.MODEL_GET_VCODE);
				button_getVcode.setEnabled(false);

				break;

			default:
				break;
			}
		}
	}

	@Override
	public void onViewChange(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
		case ControlState.VIEW_VCODE_CHANGE:
			// Log.e(getTAG(), msg.arg1 + "秒后重发");
			button_getVcode.setText(msg.arg1
					+ getString(R.string.vcode_schedule));
			if (msg.arg1 == 0) {
				//验证码时间结束时，重新设置tag
				button_getVcode.setTag("");
				button_getVcode.setText(getString(R.string.obtainVcode));
				button_getVcode.setEnabled(true);
				return;
			}
			break;

		default:
			break;
		}
	
		
	}

}
