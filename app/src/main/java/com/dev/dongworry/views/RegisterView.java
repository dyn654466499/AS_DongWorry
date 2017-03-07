package com.dev.dongworry.views;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;

import com.dev.dongworry.R;
import com.dev.dongworry.activities.LoginActivity;
import com.dev.dongworry.consts.ControlState;
import com.dev.dongworry.controlers.BaseControler;
import com.dev.dongworry.controlers.RegisterControler;
import com.dev.dongworry.interfaces.Commands;
import com.dev.dongworry.utils.DialogUtils;

@SuppressLint("NewApi") 
public class RegisterView extends BaseView implements BaseControler.ViewChangeListener {

	// private Activity mActivity;
	private RegisterControler registerControler;

	private Button button_register_login, button_getVcode, button_sureRegister,
			button_register_back;
	private ImageButton imageButton_showPwd;
	private EditText editText_pwd, editText_phone;
	private CheckBox checkBox_userProtocol;

	public RegisterView(Activity mActivity) {
		super(mActivity);
		// this.mActivity = mActivity;
		registerControler = new RegisterControler(mActivity);
		registerControler.setViewChangeListener(this);
	}

	@Override
	public void initView() {
		mActivity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mActivity.setContentView(R.layout.activity_register);
		button_register_login = (Button) mActivity
				.findViewById(R.id.button_register_login);
		button_getVcode = (Button) mActivity.findViewById(R.id.button_getVcode);
		button_sureRegister = (Button) mActivity
				.findViewById(R.id.button_sureRegister);
		//button_register_back = (Button) mActivity.findViewById(R.id.button_register_back);
		imageButton_showPwd = (ImageButton) mActivity
				.findViewById(R.id.imageButton_showPwd);

		ButtonClickListener buttonClickListener = new ButtonClickListener();
		button_register_login.setOnClickListener(buttonClickListener);
		button_getVcode.setOnClickListener(buttonClickListener);
		button_sureRegister.setOnTouchListener(new OnTouchListener() {
		    private float primary_scaleX ;
		    private float primary_scaleY ;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					primary_scaleX = v.getScaleX();
					primary_scaleY = v.getScaleY();
                    v.setScaleX((float) (0.95*primary_scaleX));
                    v.setScaleY((float) (0.95*primary_scaleY));
					return true;
				case MotionEvent.ACTION_UP:
					v.setScaleX(primary_scaleX);
                    v.setScaleY(primary_scaleY);
                    String phoneNum = editText_phone.getText().toString();
                    String password = editText_pwd.getText().toString();
                    
                    registerControler.notifyModelChange(ControlState.MODEL_SURE_REGISTER);
					return true;
				default:
					break;
				}
				return false;
			}
		});
		//button_register_back.setOnClickListener(buttonClickListener);

		editText_pwd = (EditText) mActivity
				.findViewById(R.id.editText_password);
		/**
		 * 密码UI的初始化放到了PWDFragment进行
		 */
//		imageButton_showPwd.setColorFilter(mActivity.getResources().getColor(android.R.color.darker_gray));
//		imageButton_showPwd.setTag("hidden");
//		imageButton_showPwd.setOnClickListener(buttonClickListener);
//		editText_password.setOnFocusChangeListener(new OnFocusChangeListener() {
//			@Override
//			public void onFocusChange(View v, boolean hasFocus) {
//				// TODO Auto-generated method stub
//				int pwd_length = editText_password.getText().length();
//				if(!hasFocus&&pwd_length>0&&pwd_length<6){
//					Toast.makeText(mActivity, getString(R.string.pwdIsNotFormat), Toast.LENGTH_SHORT).show();
//				}
//			}
//		});
		
		editText_phone = (EditText) mActivity
				.findViewById(R.id.editText_phone);

		checkBox_userProtocol = (CheckBox)mActivity.findViewById(R.id.checkBox_userProtocol);
		checkBox_userProtocol.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				//button_getVcode.setEnabled(isChecked);
				if((String)button_getVcode.getTag() != "clicked"){
					button_getVcode.setEnabled(isChecked);
				}
				button_sureRegister.setEnabled(isChecked);
			}
		});
		//Log.e(getTAG(), GetCountryZipCode());
	}

	public class ButtonClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.button_register_login:
				DialogUtils.showDialog(mActivity,
						getString(R.string.registering),
						getString(R.string.exitRegisterUI), new Commands() {
							@Override
							public void executeCommand() {
								Intent intent = new Intent(mActivity,LoginActivity.class);
								mActivity.startActivity(intent);
								finish();
							}
						});
				break;
			case R.id.button_getVcode:
				if (checkBox_userProtocol.isChecked()) {
					button_getVcode.setTag("clicked");
					registerControler.notifyModelChange(ControlState.MODEL_GET_VCODE);
					button_getVcode.setEnabled(false);
				}

				break;
			case R.id.imageButton_showPwd:
				// 如果是hidden状态，则显示密码
//				if ((String) imageButton_showPwd.getTag() == "hidden") {
//					editText_password
//							.setTransformationMethod(HideReturnsTransformationMethod
//									.getInstance());
//					imageButton_showPwd.setTag("show");
//					imageButton_showPwd.setColorFilter(mActivity.getResources().getColor(android.R.color.holo_blue_dark));
//				} else {
//					editText_password
//							.setTransformationMethod(PasswordTransformationMethod
//									.getInstance());
//					imageButton_showPwd.setTag("hidden");
//					imageButton_showPwd.setColorFilter(mActivity.getResources().getColor(android.R.color.darker_gray));
//				}
//				editText_password.postInvalidate();
//				// 切换后将EditText光标置于末尾
//				CharSequence charSequence = editText_password.getText();
//				if (charSequence instanceof Spannable) {
//					Spannable spanText = (Spannable) charSequence;
//					Selection.setSelection(spanText, charSequence.length());
//				}
				break;

			default:
				
				break;
			}
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event){
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			return button_register_login.callOnClick();

		default:
			break;
		}
		return false;
	}
	
	@Override
	public void onViewChange(final Message msg) {
		switch (msg.what) {
		case ControlState.VIEW_VCODE_CHANGE:
			// Log.e(getTAG(), msg.arg1 + "秒后重发");
			button_getVcode.setText(msg.arg1
					+ getString(R.string.vcode_schedule));
			if (msg.arg1 == 0) {
				//验证码时间结束时，重新设置tag
				button_getVcode.setTag("");
				button_getVcode.setText(getString(R.string.obtainVcode));
				if(checkBox_userProtocol.isChecked())button_getVcode.setEnabled(true);
				return;
			}
			break;

		default:
			break;
		}
	}
}
