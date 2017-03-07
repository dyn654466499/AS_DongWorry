package com.dev.dongworry.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class TokenManager {

	public static void clearAccessToken(Context ctx){
		SharedPreferences preferences = ctx.getSharedPreferences("OAuth2",Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.remove("accessToken");
		editor.commit();
	}
	
	public static void saveAccessToken(Context ctx,String uToken){
		SharedPreferences preferences = ctx.getSharedPreferences("OAuth2",Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("accessToken",uToken);
		editor.commit();
	}
	
	public static void clearRefreshToken(Context ctx){
		SharedPreferences preferences = ctx.getSharedPreferences("OAuth2",Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.remove("refreshToken");
		editor.commit();
	}
	
	public static void saveRefreshToken(Context ctx,String refreshUToken){
		SharedPreferences preferences = ctx.getSharedPreferences("OAuth2",Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("refreshToken",refreshUToken);
		editor.commit();
	}
}
