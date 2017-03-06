package com.dev.dongworry.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 网络工具类
 * @author 邓耀宁 （The module was passed by my testing.）
 */
@SuppressLint("NewApi")
public class NetUtil {
	private final static String ping_passTag = "5 packets transmitted, 5 received, 0% packet loss";

	
	/**
	 * 判断是否能ping通某个IP地址。
	 * @param ip
	 * @return
	 */
	public static boolean ping(final String ip) {
		synchronized (NetUtil.class) {
			FutureTask<Boolean> task = new FutureTask<Boolean>(
					new Callable<Boolean>() {

						@Override
						public Boolean call() throws IOException, Exception {
							Process p = Runtime.getRuntime().exec(
									"ping -c 5 " + ip);
							InputStream inStream = p.getInputStream();
							if (inStream != null) {
								int code = -1;
								char ch = 0;
								StringBuffer buffer = new StringBuffer();
								while ((code = inStream.read()) != -1) {
									ch = (char) code;
									buffer.append(ch);
								}
								inStream.close();
								String resultinfo = buffer.toString();
								if (resultinfo.contains(ping_passTag)) {
									return true;
								}
							}
							return false;
						}

					});
			new Thread(task).start();

			try {
				return task.get(10, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			} catch (TimeoutException e) {
				e.printStackTrace();
			}

			return false;
		}
	}
	
	/**
     * 获取Android本机IP地址
     * @return
     */
	public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {

        }
        return null;
    }

    /**
     * 获取Android本机MAC
     * @return MAC地址
     */
    public static String getLocalMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    } 
    
    /**
     * 判断是否连接网络。
     * @param context
     * @return
     */
    public static boolean isConnectedToNet(Context context){
        ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
          if (connectivity != null)
          {
              NetworkInfo[] info = connectivity.getAllNetworkInfo();
              if (info != null)
                  for (int i = 0; i < info.length; i++)
                      if (info[i].getState() == NetworkInfo.State.CONNECTED)
                      {
                          return true;
                      }
 
          }
          return false;
    }
    
    /**
     * 判断wifi是否连接
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mWiFiNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (mWiFiNetworkInfo != null) {
				return mWiFiNetworkInfo.isAvailable();
			}
		}
		return false;
	}
    
  /**
   * 将map型转为请求参数型
   * @param data
   * @return
   */
    public static String urlencode(Map<String, ?> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, ?> i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    
    
}
