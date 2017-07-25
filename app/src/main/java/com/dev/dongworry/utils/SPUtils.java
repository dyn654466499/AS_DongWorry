package com.dev.dongworry.utils;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import com.baidu.mapapi.model.LatLng;
import com.dev.dongworry.beans.login.LoginInfo;
import com.dev.dongworry.managers.AppManager;

import android.content.Context;
import android.content.SharedPreferences;

import static com.dev.dongworry.consts.Constants.*;

public class SPUtils {
	
	public static void setSearchHistory(Context context ,String searchKey){
		SharedPreferences preferences = context.getSharedPreferences(SEARCH_KEY, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();  
		String history = preferences.getString(SEARCH_KEY, "");  
		StringBuilder sb = new StringBuilder(history);  
		String appendString = searchKey + ",";
		if(history.contains(appendString)) {  
			int start = sb.indexOf(appendString);
			int end = start + appendString.length();
			sb.delete(start,end);
		} 
		sb.append(appendString);
		editor.putString(SEARCH_KEY, sb.toString());
	    editor.commit(); 
	}
	
	public static ArrayList<String> getSearchHistory(Context context){
		SharedPreferences preferences = context.getSharedPreferences(SEARCH_KEY, Context.MODE_PRIVATE);  
		SharedPreferences.Editor editor = preferences.edit();  
		String searchKey = preferences.getString(SEARCH_KEY, "");  
		if(searchKey == null || "".equals(searchKey)){
			return null;
		}
		ArrayList<String> searchKeys = new ArrayList<String>();
		String[] temp = searchKey.split(",");
		for(int i = temp.length;i>0;i--){
			searchKeys.add(temp[i-1]);
		}
		editor.commit();
		return searchKeys;
	}
	
	public static void clearSearchHistory(Context context){
		SharedPreferences preferences = context.getSharedPreferences(SEARCH_KEY, Context.MODE_PRIVATE);  
		SharedPreferences.Editor editor = preferences.edit();  
		editor.clear();  
        editor.commit();  
	}
	
	public static void setCurrentCity(Context context,String cityName){
		SharedPreferences preferences = context.getSharedPreferences(CURRENT_LOCATION, Context.MODE_PRIVATE);  
		SharedPreferences.Editor editor = preferences.edit();  
		editor.putString(CURRENT_CITY, cityName);
        editor.commit();  
	}
	
	public static String getCurrentCity(Context context){
		SharedPreferences preferences = context.getSharedPreferences(CURRENT_LOCATION, Context.MODE_PRIVATE);  
		return preferences.getString(CURRENT_CITY, "深圳");
	}
	
	public static void setCurrentAddress(Context context,String address){
		SharedPreferences preferences = context.getSharedPreferences(CURRENT_LOCATION, Context.MODE_PRIVATE);  
		SharedPreferences.Editor editor = preferences.edit();  
		editor.putString(CURRENT_ADDRESS, address);
        editor.commit();  
	}
	
	public static String getCurrentAddress(Context context){
		SharedPreferences preferences = context.getSharedPreferences(CURRENT_LOCATION, Context.MODE_PRIVATE);  
		return preferences.getString(CURRENT_ADDRESS, "世界之窗");
	}
	
	public static LatLng getCurrentLatLng(Context context){
		SharedPreferences preferences = context.getSharedPreferences(CURRENT_LOCATION, Context.MODE_PRIVATE);  
		LatLng ll = null;
		if(preferences.getFloat(CURRENT_LATITUDE, 0f) != 0f){
			ll = new LatLng((double)preferences.getFloat(CURRENT_LATITUDE, 0f),(double)preferences.getFloat(CURRENT_LONGITUDE, 0f));
		}
		return ll;
	}

	public static void setIsLogin(Context context,boolean isLogin){
		SharedPreferences preferences = context.getSharedPreferences(LOGIN_INFO, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean(IS_LOGIN, isLogin);
		editor.commit();
	}

	public static boolean isLogin(Context context){
		SharedPreferences preferences = context.getSharedPreferences(LOGIN_INFO, Context.MODE_PRIVATE);
		return preferences.getBoolean(IS_LOGIN,false);
	}

	public static synchronized void setUserInfo(Context context, LoginInfo user)
	{
		SharedPreferences preferences = context.getSharedPreferences(LOGIN_INFO, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		String str="";
		try {
			str = SerializableUtils.obj2Str(user);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		editor.putString(LOGIN_INFO,str);
		editor.commit();
	}

	public static synchronized LoginInfo getUserInfo(Context context)
	{
		SharedPreferences preferences = context.getSharedPreferences(LOGIN_INFO, Context.MODE_PRIVATE);
		//获取序列化的数据
		String str = preferences.getString(LOGIN_INFO, "");
		LoginInfo userInfo = null;
		try {
			Object obj = SerializableUtils.str2Obj(str);
			if(obj != null){
				userInfo = (LoginInfo)obj;
			}
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userInfo;
	}

	public static void setToken(Context context,String token){
		SharedPreferences preferences = context.getSharedPreferences(ACCESS_TOKEN, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(ACCESS_TOKEN, token);
		editor.commit();
	}

	public static String getToken(){
		Context context = AppManager.getInstance().getAppContext();
		SharedPreferences preferences = context.getSharedPreferences(ACCESS_TOKEN, Context.MODE_PRIVATE);
		return preferences.getString(ACCESS_TOKEN, "");
	}
}
