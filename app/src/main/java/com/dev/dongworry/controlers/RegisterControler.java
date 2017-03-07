package com.dev.dongworry.controlers;

import android.app.Activity;
import com.dev.dongworry.models.RegisterModel;

/**
 * “注册”view的controler
 * @author 邓耀宁
 *
 */
public class RegisterControler extends BaseControler{

	/**
	 * 设置controler的model
	 * @param activity
	 */
	public RegisterControler(Activity activity) {
		super(activity);
		modelDelegate = new RegisterModel(handler);
	}
}
