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
import com.dev.dongworry.utils.RSAUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.dev.dongworry.consts.ControlState.MODEL_SURE_REGISTER;
import static com.dev.dongworry.consts.ControlState.VIEW_REGISTER;
import static com.dev.dongworry.consts.ControlState.VIEW_VCODE_CHANGE;


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

		setModelDelegate(new RegisterModel(handler,this));
		setViewChangeListener(this);
		setTitle(getString(R.string.title_register));

		button_getVcode = (Button)findViewById(R.id.button_getVcode);
		button_sureRegister = (Button)findViewById(R.id.button_sureRegister);
		button_sureRegister.setOnClickListener(this);
		button_getVcode.setOnClickListener(this);

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
	}

	@Override
	public void onClick(View v) {
		HashMap<String,String> params;
		switch (v.getId()) {
			case R.id.button_getVcode:
				if (checkBox_userProtocol.isChecked()) {
					button_getVcode.setTag("clicked");
					notifyModelChange(ControlState.MODEL_GET_VCODE);
					button_getVcode.setEnabled(false);
				}

				break;
			case R.id.button_sureRegister:
				showLoadingDialog();
				params = new HashMap<>();
				params.put("password", RSAUtils.encryptAndToHex(editText_pwd.getText().toString()));
				params.put("mobile",editText_phone.getText().toString());
				notifyModelChange(Message.obtain(handler,MODEL_SURE_REGISTER,params));
				break;
			default:

				break;
		}
	}

	@Override
	public void onViewChange(final Message msg) {
		switch (msg.what) {
			case VIEW_VCODE_CHANGE:
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

			case VIEW_REGISTER:
				dismissLoadingDialog();
				if(msg.obj instanceof String){
					String json = (String)msg.obj;
					try {
						JSONObject jsonObject = new JSONObject(json);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				break;
			default:
				break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		handler.removeCallbacksAndMessages(null);
		setModelDelegate(null);
	}
}
