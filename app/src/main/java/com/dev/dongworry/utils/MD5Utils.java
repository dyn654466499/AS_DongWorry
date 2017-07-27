package com.dev.dongworry.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import Decoder.BASE64Encoder;

public class MD5Utils {
	public static String getMd5(String encryptText){
		String encryptStr = "";
		//确定计算方法
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			BASE64Encoder base64en = new BASE64Encoder();
			//加密后的字符串
			encryptStr=base64en.encode(md5.digest(encryptText.getBytes("utf-8")));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return encryptStr;
	}
}
