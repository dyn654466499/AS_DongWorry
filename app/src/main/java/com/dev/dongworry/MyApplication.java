package com.dev.dongworry;

import android.app.ActivityManager;
import android.app.Application;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.dev.dongworry.managers.AppManager;
import com.dev.dongworry.managers.login.LoginManager;
import com.dev.dongworry.utils.CrashHandler;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.iflytek.cloud.SpeechUtility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import static com.hyphenate.chat.EMGCMListenerService.TAG;

public class MyApplication extends Application{

	@Override
	public void onCreate() {
		MultiDex.install(this);
		AppManager.getInstance().setAppContext(this);
		CrashHandler.getInstance().init(this);
		SpeechUtility.createUtility(getApplicationContext(), "appid="+getString(R.string.iflyteck_app_id));
		/**
		 * 将帮助文档写入本地存储
		 */
		String locale = this.getResources().getConfiguration().locale.toString();
		super.onCreate();
		
		InputStream inputstream;
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

		int pid = android.os.Process.myPid();
		String processAppName = getAppName(pid);
		// 如果APP启用了远程的service，此application:onCreate会被调用2次
		// 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
		// 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回
		if (processAppName == null ||!processAppName.equalsIgnoreCase(getPackageName())) {
			Log.e(TAG, "enter the service process!");

			// 则此application::onCreate 是被service 调用的，直接返回
			return;
		}
		EMOptions options = new EMOptions();
		// 默认添加好友时，是不需要验证的，改成需要验证
		options.setAcceptInvitationAlways(false);
		//初始化
//		EMClient.getInstance().init(this, options);
//		//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
//		EMClient.getInstance().setDebugMode(true);
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		LoginManager.getInstance().logout();
	}

	private String getAppName(int pID) {
		String processName = null;
		ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
		List l = am.getRunningAppProcesses();
		Iterator i = l.iterator();
		PackageManager pm = this.getPackageManager();
		while (i.hasNext()) {
			ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
			try {
				if (info.pid == pID) {
					processName = info.processName;
					return processName;
				}
			} catch (Exception e) {
				// Log.d("Process", "Error>> :"+ e.toString());
			}
		}
		return processName;
	}
}
