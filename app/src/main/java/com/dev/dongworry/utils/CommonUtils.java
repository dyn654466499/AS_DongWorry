package com.dev.dongworry.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.util.Linkify.TransformFilter;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.dev.dongworry.R;
import com.dev.dongworry.activities.WebActivity;

import java.io.DataOutputStream;
import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.dev.dongworry.consts.Constants.WEB_URL;

/**
 * 公共的工具类
 * @author 邓耀宁
 *
 */
public class CommonUtils {
	/** 
	 * 验证手机格式 
	 * @return 如果符合手机格式，返回true；否则返回false
	 */  
	public static boolean isPhoneNum(String phoneNumber) {  
	    /* 
	    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188 
	    联通：130、131、132、152、155、156、185、186 
	    电信：133、153、180、189、（1349卫通） 
	    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9 
	    */  
	    String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。  
	    if (TextUtils.isEmpty(phoneNumber)) return false;  
	    else return phoneNumber.matches(telRegex);  
	   }

	public static boolean checkPassword(String pwd){
		if(!TextUtils.isEmpty(pwd)){
			boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
			boolean isLowerCase = false;//定义一个boolean值，用来表示是否包含字母
			for (int i = 0; i < pwd.length(); i++) {
				if (Character.isDigit(pwd.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
					isDigit = true;
				} else if (Character.isLowerCase(pwd.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
					isLowerCase = true;
				}
			}
			String regex = "^[a-zA-Z0-9]+$";
			boolean isRight = pwd.length() >= 8 && isDigit && isLowerCase && pwd.matches(regex);
			return isRight;
		}
		return false;
	}
	
	/**
	 * 自定义middle()方法：取出语义理解返回的文本结果中的特定结果
	 * 
	 * @param input
	 *            （字符串结果）,index（ 截取开始的字符串位置，以1开始）,endPos（截取结束的字符串位置）
	 */
	public static String middle(String input, int index, int count) {
		if (input.isEmpty()) {
			return "";
		}
		count = (count > input.length() - index + 1) ? input.length()
				- index + 1 : count;
		return input.substring(index - 1, index + count - 1);
	}

	/**
	 * 判断是否text中是否含有小写字母
	 * @param text
	 * @return
	 */
	public static boolean isHaveLowerCase(String text) {
		return Pattern.compile("(?i)[a-z]").matcher(text).find();
	}
	
	/**
	 * 设置字母为大写
	 * @param text
	 * @return
	 */
	public static String setUpperCase(String text) {
		if (isHaveLowerCase(text)) {
			
			text = text.toUpperCase(Locale.getDefault());
		}
		return text;
	}

	/**
	 * 格式化时间
	 * @param time
	 * @return
	 */
	public static String getFormatDuration(String time){
		  String dur = null;
		  if(time!=null)
		  {		
			double time_=Double.parseDouble(time);
			int second=(int)time_%60;	
			int minute=(int)time_/60%60;
			int hour=(int)time_/3600;	
			String s= (second<10)?"0"+String.valueOf(second):String.valueOf(second);
			String m=(minute<10)?"0"+String.valueOf(minute):String.valueOf(minute);
			String h=(hour<10)?"0"+String.valueOf(hour):String.valueOf(hour);
			dur=h+":"+m+":"+s;										
		  }
		    return dur;
	}
	
	/**
	 * 判断手机是否连接网络
	 * @param context
	 * @return
	 */
    public static boolean isConnectingToInternet(Context context){
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
     * 格式化字节数
     * @param size 
     * @return 格式化后的数据
     */  
    public static String getFormatTrafficSize(long size) {  
    	long kiloByte = size / 1024;  
        if (kiloByte < 1) {  
        	return "没有使用流量";
        }  
  
        long megaByte = kiloByte / 1024;  
        if (megaByte < 1) {  
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));  
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)  
                    .toPlainString() + "KB";  
        }  
  
        long gigaByte = megaByte / 1024;  
        if (gigaByte < 1) {  
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));  
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)  
                    .toPlainString() + "MB";  
        }  
  
        long teraBytes = gigaByte / 1024;  
        if (teraBytes < 1) {  
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));  
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)  
                    .toPlainString() + "GB";  
        }  
        
        BigDecimal result4 = new BigDecimal(teraBytes);  
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()  
                + "TB";  
    }
    
    /**
     * 格式化手机号码(135*****7710)
     * @param phoneNum
     * @return 
     */
    public static String formatPhoneNum(String phoneNum){
        String target = phoneNum.substring(3, 7);
        phoneNum = phoneNum.replace(target, "*****");
    	return phoneNum;
    }
    
    /**
     * 根据参数改变指定文本的部分字体大小，颜色。
     * @param text 需要处理的整体文本
     * @param start 指定文本的开始位置
     * @param end 指定文本的结束位置
     * @param color 字体颜色
     * @param size 字体大小
     * @return 处理后的文本
     */
    public static SpannableString setFontType(String text, int start, int end, int color, int size)
	  {
	    SpannableString spannableString = new SpannableString(text);
		spannableString.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
		spannableString.setSpan(new AbsoluteSizeSpan(size,true), start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
	    return spannableString;
	  }
    
    /**
	 * 应用程序运行命令获取 Root权限，设备必须已破解(获得ROOT权限)
	 * 
	 * @return 应用程序是/否获取Root权限
	 */
	public static boolean upgradeRootPermission(String pkgCodePath) {
		Process process = null;
		DataOutputStream os = null;
		try {
			String cmd = "chmod 777 " + pkgCodePath;
			process = Runtime.getRuntime().exec("su"); // 切换到root帐号
			os = new DataOutputStream(process.getOutputStream());
			os.writeBytes(cmd + "\n");
			os.writeBytes("exit\n");
			os.flush();
			process.waitFor();
		} catch (Exception e) {
			return false;
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				process.destroy();
			} catch (Exception e) {
			}
		}
		return true;
	}
	
	/**
	 * 格式化时间
	 * @param time
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getFormatDate(long time){  
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault());
        Date date=new Date(time); 
        //SimpleDateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault()).format(date);
        return format.format(date);  
    }
	
	/**
	 * 格式化时间
	 * @param time
	 * @return HH:mm:ss
	 */
	public static String getFormatTime(long time){  
        SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss",Locale.getDefault());
        Date date=new Date(time); 
        //SimpleDateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault()).format(date);
        return format.format(date);  
    }

	/**
	 * 格式化时间
	 * @param time
	 * @return HH小时mm分钟
	 */
	public static String getFormatTime(int time){ 
       String str_time = String.valueOf(time/3600)+"小时"+
    		             ((time/60)%60 < 10 ? "0"+String.valueOf((time/60)%60):String.valueOf((time/60)%60))+"分钟";
       return str_time;
    }
	
	/**
	 * 格式化时间
	 * @param distance
	 * @return HH小时mm分钟
	 */
	public static String getFormatDistance(int distance){
		DecimalFormat decimalFormat=new DecimalFormat(".0");
		String str_distance = "";
		if(distance<1000){
			str_distance = "0"+decimalFormat.format((float)distance/1000f) + "公里";
		}else{
			str_distance = decimalFormat.format((float)distance/1000f) + "公里";
		}
        return str_distance;
    }
	
	/**
	 * 判断是否有SD卡
	 * @return
	 */
	public static boolean hasSdcard() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 取本地的apk进行发送
	 * @param context
	 */
	public static void sendAPKFromLocal(Context context) {
		PackageManager pm = context.getPackageManager();
		ApplicationInfo aInfo = null;
		try {
			aInfo = pm.getApplicationInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			Toast.makeText(context, "发送apk出错", Toast.LENGTH_LONG).show();
			return;
		}
		String apkArchive = aInfo.sourceDir;
		Intent shareMyAppIntent = new Intent(Intent.ACTION_SEND);
		shareMyAppIntent.setType("*/*");// text/plain
		shareMyAppIntent.putExtra(Intent.EXTRA_SUBJECT,
				context.getString(R.string.extra_subject));
		shareMyAppIntent.putExtra(Intent.EXTRA_TEXT,
				context.getString(R.string.extra_text));

		File file = new File(apkArchive);
		Uri uri = Uri.fromFile(file);
		shareMyAppIntent.putExtra(Intent.EXTRA_STREAM, uri);
		shareMyAppIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(Intent.createChooser(shareMyAppIntent, context.getString(R.string.local_app_name)));
	}
	
	public static float dipToPixels(Context context, float dipValue) {
	    DisplayMetrics metrics = context.getResources().getDisplayMetrics();
	    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
	}
	
	/**
	 * 获取版本名
	 * @param context
	 * @return
	 */
	public static String getVersionName(Context context) {
	    return getPackageInfo(context).versionName;
	}
	 
	/**
	 * 获取版本号
	 * @param context
	 * @return
	 */
	public static int getVersionCode(Context context) {
	    return getPackageInfo(context).versionCode;
	}
	 
	private static PackageInfo getPackageInfo(Context context) {
	    PackageInfo pi = null;
	 
	    try {
	        PackageManager pm = context.getPackageManager();
	        pi = pm.getPackageInfo(context.getPackageName(),
	                PackageManager.GET_CONFIGURATIONS);
	 
	        return pi;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	 
	    return pi;
	}
	
	/**
	 * 获取版本号
	 * @param context
	 * @param packageName
	 * @return 
	 */
	public static String getVersionName(Context context,String packageName) {
		PackageInfo pi = null;
	    try {
	        PackageManager pm = context.getPackageManager();
	        pi = pm.getPackageInfo(packageName,PackageManager.GET_CONFIGURATIONS);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return pi.versionName;
	}
	
	/**
	 * 去除特殊字符或将所有中文标号替换为英文标号
	 * @param str
	 * @return
	 */
	public static String stringFilter(String str) {
		str = str.replaceAll("【", "[").replaceAll("】", "]")
				.replaceAll("！", "!").replaceAll("：", ":");// 替换中文标号
		String regEx = "[『』]"; // 清除掉特殊字符
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	/***
	 * 半角转换为全角
	 * 
	 * @param input
	 * @return
	 */
	public static String ToSBC(String input) {
		if (!TextUtils.isEmpty(input)) {
			char c[] = input.toCharArray();
			for (int i = 0; i < c.length; i++) {
				if (c[i] == ' ') {
					c[i] = '\u3000';
				} else if (c[i] < '\177') {
					c[i] = (char) (c[i] + 65248);
				}
			}
			return new String(c);
		}
		return "";
	}
	
	public static final String makeUrl(String url, String[] prefixes,
            Matcher m, TransformFilter filter) {
        if (filter != null) {
            url = filter.transformUrl(m, url);
        }

        boolean hasPrefix = false;
        
        for (int i = 0; i < prefixes.length; i++) {
            if (url.regionMatches(true, 0, prefixes[i], 0,
                                  prefixes[i].length())) {
                hasPrefix = true;

                // Fix capitalization if necessary
                if (!url.regionMatches(false, 0, prefixes[i], 0,
                                       prefixes[i].length())) {
                    url = prefixes[i] + url.substring(prefixes[i].length());
                }

                break;
            }
        }

        if (!hasPrefix) {
            url = prefixes[0] + url;
        }

        return url;
    }
	
    /** 
     * 密度转换像素 
     **/
    public int Dp2Px(Context context, float dp) { 
        final float scale = context.getResources().getDisplayMetrics().density; 
        return (int) (dp * scale + 0.5f); 
    } 
    
    /** 
     * 像素转换密度 
     **/ 
    public int Px2Dp(Context context, float px) { 
        final float scale = context.getResources().getDisplayMetrics().density; 
        return (int) (px / scale + 0.5f); 
    }

		//腾讯应用宝
		private static final String PACKAGE_NAME_YINGYONGBAO = "com.tencent.android.qqdownloader";
		//百度手机助手
		private static final String PACKAGE_NAME_BAIDU = "com.baidu.appsearch";
		//360手机助手
		private static final String PACKAGE_NAME_360 = "com.qihoo.appstore";
		//小米应用市场
		private static final String PACKAGE_NAME_XIAOMI = "com.xiaomi.market";
		//华为应用市场
		private static final String PACKAGE_NAME_HUAWEI = "com.huawei.appmarket";
		//豌豆荚
		private static final String PACKAGE_NAME_WANDOUJIA = "com.wandoujia.phoenix2";

		/**
		 * 去评分
		 */
		public static void toScore(Context context, String appId) {
				HashMap<String, String> pkgNames = getLocalMarketApps(context);
				if (pkgNames != null && pkgNames.size() > 0) {
					//顺序按照需求来
					String[] marketNames = {
							PACKAGE_NAME_BAIDU,
							PACKAGE_NAME_YINGYONGBAO,
							PACKAGE_NAME_XIAOMI,
							PACKAGE_NAME_HUAWEI,
							PACKAGE_NAME_WANDOUJIA,
							PACKAGE_NAME_360,
					};
					for (int i = 0; i < marketNames.length; i++) {
						String marketName = marketNames[i];
						if (pkgNames.containsKey(marketName)) {
							launchAppDetail(context, context.getPackageName(), pkgNames.get(marketName),appId);
							return;
						}
					}
					//如果命中不了运营市场,则调起本手机已经安装的市场列表
					jumpWebMarket(context,appId);
				} else {
					jumpWebMarket(context,appId);
				}
		}

		private static boolean isAppInstalled(Context context, String packageName) {
			PackageInfo packageInfo;
			try {
				packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
			} catch (PackageManager.NameNotFoundException e) {
				packageInfo = null;
				e.printStackTrace();
			}
			if (packageInfo == null) {
				//没有安装
				return false;
			} else {
				//已经安装
				//Log.e("应用市场","name = " + packageInfo.packageName);
				return true;
			}
		}

		/**
		 * 获取已安装应用商店的包名列表
		 *
		 * @param context
		 * @return
		 */
		private static HashMap<String, String> getLocalMarketApps(Context context) {
			HashMap<String, String> pkgs = new HashMap<String, String>();
			if (context == null)
				return pkgs;
			Intent intent = new Intent();
			intent.setAction("android.intent.action.MAIN");
			intent.addCategory("android.intent.category.APP_MARKET");
			PackageManager pm = context.getPackageManager();
			List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);
			if (infos == null || infos.size() == 0)
				return pkgs;
			int size = infos.size();
			for (int i = 0; i < size; i++) {
				String pkgName = "";
				try {
					ActivityInfo activityInfo = infos.get(i).activityInfo;
					pkgName = activityInfo.packageName;
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (!TextUtils.isEmpty(pkgName)) {
					pkgs.put(pkgName, pkgName);
					//Log.e("应用市场","name = "+pkgName);
				}
			}
			//上述方法获取不到小米和华为应用市场的包名,需要另外判断

			if (isAppInstalled(context, PACKAGE_NAME_XIAOMI)) {
				pkgs.put(PACKAGE_NAME_XIAOMI, PACKAGE_NAME_XIAOMI);
			}

			if (isAppInstalled(context, PACKAGE_NAME_HUAWEI)) {
				pkgs.put(PACKAGE_NAME_HUAWEI, PACKAGE_NAME_HUAWEI);
			}
			return pkgs;
		}

		/**
		 * 跳转到app详情界面
		 *
		 * @param appPkg    App的包名
		 * @param marketPkg 应用商店包名 ,如果为""则由系统弹出应用商店列表供用户选择,否则调转到目标市场的应用详情界面，某些应用商店可能会失败
		 */
		private static void launchAppDetail(Context context, String appPkg, String marketPkg,String appId) {
			try {
				if (TextUtils.isEmpty(appPkg))
					return;
				Uri uri = Uri.parse("market://details?id=" + appPkg);
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				if (!TextUtils.isEmpty(marketPkg))
					intent.setPackage(marketPkg);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
			} catch (Exception e) {
				//如果打开失败,直接跳到应用宝web页面
				jumpWebMarket(context,appId);
			}
		}

		/**
		 * 跳转到应用宝的web页面
		 * @param context
		 * @param appId
		 */
		private static void jumpWebMarket(Context context,String appId){
			String url;
			if (!TextUtils.isEmpty(appId)) {
				url = "http://app.qq.com/#id=detail&appid=" + appId;
			} else {
				url = "http://app.qq.com/#id=detail&appid=100733732";
			}
			Intent intent = new Intent(context,WebActivity.class);
			intent.putExtra(WEB_URL, url);
			context.startActivity(intent);
		}

		/**
		 * 调起手机上已安装的应用市场列表
		 */
		public void openMarketInstalledInPhone(Context context){
			if(context==null){
				return;
			}
			try {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("market://details?id=" + context.getPackageName()));
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	/**
	 * ViewHolder简洁写法,避免适配器中重复定义ViewHolder,减少代码量 用法:
	 *
	 * <pre>
	 * if (convertView == null)
	 * {
	 * 	convertView = View.inflate(context, R.layout.ad_demo, null);
	 * }
	 * TextView tv_demo = ViewHolderUtils.get(convertView, R.id.tv_demo);
	 * ImageView iv_demo = ViewHolderUtils.get(convertView, R.id.iv_demo);
	 * </pre>
	 */
	public static <T extends View> T hold(View view, int id)
	{
		SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();

		if (viewHolder == null)
		{
			viewHolder = new SparseArray<View>();
			view.setTag(viewHolder);
		}

		View childView = viewHolder.get(id);

		if (childView == null)
		{
			childView = view.findViewById(id);
			viewHolder.put(id, childView);
		}

		return (T) childView;
	}

	/**
	 * 替代findviewById方法
	 */
	public static <T extends View> T find(View view, int id)
	{
		return (T) view.findViewById(id);
	}

}
