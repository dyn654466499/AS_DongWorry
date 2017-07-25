package com.dev.dongworry.consts;

/**
 * model通过该类的命令参数处理不同的业务。
 * @author 邓耀宁
 *
 */
public class ControlState {
	
	/**
	 * setting model control state
	 */
	public static final int MODEL_SETTING_CLEARCACHE = 20;
	
	
	/**
	 * Model 天气查询
	 */
	public static final int MODEL_SURE_REGISTER = 1;
	public static final int MODEL_WEATHER_QUERY = 2;
	public static final int MODEL_GET_VCODE = 3;
	public static final int MODEL_LOGIN = 4;
	/**
	 * View 天气查询 
	 */
	public static final int VIEW_WEATHER_QUERY = 300;
	public static final int VIEW_VCODE_CHANGE = 301;
	public static final int VIEW_REGISTER_SUCCESS = 302;
	public static final int VIEW_REGISTER_FAIL = 303;
	public static final int VIEW_LOGIN_SUCCESS = 304;
	public static final int VIEW_LOGIN_FAIL = 305;
}
