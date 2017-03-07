package com.dev.dongworry.views;

import android.annotation.SuppressLint;
import android.app.Activity;


/**
 * view的抽象类，负责初始化并显示UI。
 * @author 邓耀宁
 *
 */
@SuppressLint("NewApi") 
public abstract class BaseView {
	/**
	 * 切勿将Activity设置为static，如在使用startActivityForResult时，原Activity则出现错误。
	 */
	protected Activity mActivity;
	private static boolean isActivityfinish = false;

	/**
	 * 绑定view对应的Activity
	 */

	public BaseView(Activity mActivity) {
		super();
		this.mActivity = mActivity;
		isActivityfinish = false;
		initView();
	}

	/**
	 * 子类可将各控件的初始化写在该函数中
	 */
	public abstract void initView();
	
	protected String getTAG() {
		return this.getClass().getName();
	}
	
	protected String getString(int resID) {
		return mActivity.getString(resID);
	}
	
	protected void finish() {
		mActivity.finish();
	}

	/**
	 * 判断Activity是否结束
	 * @return 如果Activity结束则返回true。
	 */
	public static boolean isActivityfinish() {
		return isActivityfinish;
	}
	
	public static void setActivityfinish(boolean isActivityfinish) {
		BaseView.isActivityfinish = isActivityfinish;
	}
}
