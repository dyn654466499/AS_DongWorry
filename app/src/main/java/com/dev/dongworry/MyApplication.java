package com.dev.dongworry;

import android.app.Application;
import android.util.Log;

import com.dev.dongworry.utils.CrashHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MyApplication extends Application{

	@Override
	public void onCreate() {
		CrashHandler.getInstance().init(this);
		/**
		 * 将帮助文档写入本地存储
		 */
		String locale = this.getResources().getConfiguration().locale.toString();
		super.onCreate();
		
		InputStream inputstream = null;
		if (locale.contentEquals("zh_TW")) {
            inputstream = this.getResources().openRawResource(R.raw.chinese_traditional);
		} else {
			inputstream = this.getResources().openRawResource(R.raw.chinese_simplified);
		}
		final InputStream inputstream_copy = inputstream;
		final String path = getFilesDir().getPath()+"/help.html";
		new Thread(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				byte[] buffer = new byte[1024];
				int count = 0;
				FileOutputStream fos = null;
				try {
					File file = new File(path);
					try {
			              String command = "chmod 777 " + file.getAbsolutePath();
			              Log.i("zyl", "command = " + command);
			              Runtime runtime = Runtime.getRuntime();  
		                  runtime.exec(command);
			             } catch (IOException e) {
			              Log.i("zyl","chmod fail!!!!");
			              e.printStackTrace();
			             }
					fos = new FileOutputStream(file);
					while ((count = inputstream_copy.read(buffer)) > 0) {
						fos.write(buffer, 0, count);
					}
					fos.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}.start();
		
	}
}
