package com.dev.dongworry.consts;

/**
 * model通过该类的命令参数处理不同的业务。
 * @author 邓耀宁
 *
 */
public class ControlState {
	
	public static final int MODEL_SURE_REGISTER = 1;
	
	/**
	 * setting model control state
	 */
	public static final int MODEL_SETTING_CLEARCACHE = 20;
	
	
	/**
	 * Model 天气查询
	 */
	public static final int MODEL_WEATHER_QUERY = 200;
	
	/**
	 * View 天气查询 
	 */
	public static final int VIEW_WEATHER_QUERY = 300;

	public static final int MODEL_GET_VCODE = 401;
	public static final int VIEW_VCODE_CHANGE = 402;
	public static final int VIEW_REGISTER = 403;
}
