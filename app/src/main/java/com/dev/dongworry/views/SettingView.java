package com.dev.dongworry.views;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import com.dev.dongworry.R;
import com.dev.dongworry.controlers.BaseControler;
import com.dev.dongworry.controlers.SettingControler;
import com.dev.dongworry.interfaces.Commands;
import com.dev.dongworry.utils.DataCacheUtils;
import com.dev.dongworry.utils.DialogUtils;

/**
 * “设置”UI
 * @author 邓耀宁
 *
 */
public class SettingView extends BaseView implements BaseControler.ViewChangeListener{
    private SettingControler settingControler;
    
    private Button button_setting_clearCache,button_setting_evaluate,button_setting_inviteFriends;
    private ImageButton imageBtn_setting_back;
	public SettingView(Activity mActivity) {
		super(mActivity);
		// TODO Auto-generated constructor stub
		settingControler = new SettingControler(mActivity);
		settingControler.setViewChangeListener(this);
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		mActivity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mActivity.setContentView(R.layout.activity_setting);
		ButtonListener buttonListener = new ButtonListener();
		button_setting_clearCache = (Button)mActivity.findViewById(R.id.button_setting_clearCache);
		button_setting_clearCache.setOnClickListener(buttonListener);
		try {
			button_setting_clearCache.setText(
					String.format(getString(R.string.clearCache), 
							DataCacheUtils.getCacheSize(mActivity.getCacheDir())));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		imageBtn_setting_back = (ImageButton)mActivity.findViewById(R.id.imageBtn_setting_back);
		imageBtn_setting_back.setOnClickListener(buttonListener);
		
		button_setting_evaluate = (Button)mActivity.findViewById(R.id.button_setting_evaluate);
		button_setting_evaluate.setOnClickListener(buttonListener);
		
		button_setting_inviteFriends = (Button)mActivity.findViewById(R.id.button_setting_inviteFriends);
		button_setting_inviteFriends.setOnClickListener(buttonListener);
	}

	
	class ButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.button_setting_clearCache:
				DialogUtils.showDialog(mActivity, "", "确定要清除缓存吗？", new Commands() {
					
					@Override
					public void executeCommand() {
						// TODO Auto-generated method stub
						DataCacheUtils.cleanInternalCache(mActivity);
						try {
							button_setting_clearCache.setText(
									String.format(getString(R.string.clearCache), 
											DataCacheUtils.getCacheSize(mActivity.getCacheDir())));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				break;
			case R.id.imageBtn_setting_back:
				finish();
				break;
			/**
			 * 去相应的应用市场进行评价	
			 */
			case R.id.button_setting_evaluate:
				Uri uri = Uri.parse("market://details?id="+mActivity.getPackageName());  
				Intent intent = new Intent(Intent.ACTION_VIEW,uri);  
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
				mActivity.startActivity(intent); 
				break;
			/**
			 * 分享给好友	
			 */
			case R.id.button_setting_inviteFriends:
				Intent sendIntent = new Intent();
				sendIntent.setAction(Intent.ACTION_SEND);
				sendIntent.setType("text/*");
				sendIntent.putExtra(Intent.EXTRA_TEXT, "www.xxx.com");
				mActivity.startActivity(sendIntent);
				break;
			default:
				break;
			}
		}
	}
	
	
	@Override
	public void onViewChange(Message msg) {
		// TODO Auto-generated method stub
		
	}
	
	

}
