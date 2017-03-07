package com.dev.dongworry.controlers;

import android.app.Activity;

import com.dev.dongworry.models.SettingModel;

/**
 * “更多设置”view的controler
 * @author 邓耀宁
 *
 */
public class SettingControler extends BaseControler{
	/**
	 * 设置controler的model
	 * @param activity
	 */
	public SettingControler(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		modelDelegate = new SettingModel(handler);
		
	}

}
