package com.dev.dongworry.activities;

import android.os.Bundle;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.dev.dongworry.R;
import com.dev.dongworry.consts.ControlState;
import com.dev.dongworry.models.RegisterModel;


/**
 * “注册界面”的Activity
 * @author 邓耀宁
 *
 */
public class RegisterActivity extends BaseActivity {

	private Button button_getVcode, button_sureRegister;
	private EditText editText_pwd, editText_phone;
	private CheckBox checkBox_userProtocol;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		setModelDelegate(new RegisterModel(handler));
		setViewChangeListener(this);

		button_getVcode = (Button)findViewById(R.id.button_getVcode);
		button_sureRegister = (Button)findViewById(R.id.button_sureRegister);

		button_getVcode.setOnClickListener(this);
		button_sureRegister.setOnTouchListener(new View.OnTouchListener() {
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

						notifyModelChange(ControlState.MODEL_SURE_REGISTER);
						return true;
					default:
						break;
				}
				return false;
			}
		});
		editText_pwd = (EditText)findViewById(R.id.editText_password);
		editText_phone = (EditText)findViewById(R.id.editText_phone);

		checkBox_userProtocol = (CheckBox)findViewById(R.id.checkBox_userProtocol);
		checkBox_userProtocol.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.button_getVcode:
				if (checkBox_userProtocol.isChecked()) {
					button_getVcode.setTag("clicked");
					notifyModelChange(ControlState.MODEL_GET_VCODE);
					button_getVcode.setEnabled(false);
				}

				break;
			default:

				break;
		}
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
