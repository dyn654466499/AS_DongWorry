package com.dev.dongworry.activities;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.dev.dongworry.R;

public class ShowHelpActivity extends BaseActivity {

	private WebView webView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_help);
		webView = (WebView)findViewById(R.id.webView_showHelp);
		String path = "";
		try {
			path = getFilesDir().getPath()+"/help.html";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		webView.getSettings().setDefaultTextEncodingName("utf-8");
		webView.getSettings().setDefaultFontSize(20);	
		if("".equals(path)){
			webView.loadDataWithBaseURL(null,"暂无内容", "text/html", "utf-8", null);
		}
		else webView.loadUrl("file://"+path);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		/**
		 * 销毁webView。
		 */
		if(webView!=null){
		   final ViewGroup viewGroup = (ViewGroup)webView.getParent();
		   if(viewGroup!=null){
			   viewGroup.removeView(webView);
		   }
		webView.removeAllViews();
		webView.destroy();
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onViewChange(Message msg) {
		// TODO Auto-generated method stub
		
	}
}
