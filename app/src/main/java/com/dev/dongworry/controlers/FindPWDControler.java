package com.dev.dongworry.controlers;

import android.app.Activity;

import com.dev.dongworry.models.FindPWDModel;
/**
 * “找回密码”view的controler
 * @author 邓耀宁
 *
 */
public class FindPWDControler extends BaseControler {

	/**
	 * 设置controler的model
	 * @param activity
	 */
	public FindPWDControler(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		modelDelegate = new FindPWDModel(handler);
	}

}
