package com.dev.dongworry.controlers;

import android.app.Activity;
import com.dev.dongworry.models.LoginModel;

/**
 * “登录”view的controler
 * @author 邓耀宁
 *
 */
public class LoginControler extends BaseControler{

	/**
	 * 设置controler的model
	 * @param activity
	 */
	public LoginControler(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		modelDelegate = new LoginModel(handler);
	}

}
