package com.dev.dongworry.activities;


import static com.dev.dongworry.consts.Constants.*;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.dongworry.R;
import com.dev.dongworry.utils.CommonUtils;
import com.dev.dongworry.utils.ImageUtil;

/**
 * 关于app的界面
 * @author 邓耀宁
 *
 */
@SuppressLint("NewApi") 
public class AboutActivity extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

		setTitle(getString(R.string.title_about));
		setViewChangeListener(this);

		ImageView imageView_about_logo = (ImageView)findViewById(R.id.imageView_about_logo);
		imageView_about_logo.setImageBitmap(ImageUtil.getRoundedCornerBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.control), 80));
		TextView textView_about_version = (TextView)findViewById(R.id.textView_about_version);
		textView_about_version.setText(String.format(getString(R.string.current_version), CommonUtils.getVersionName(this)));
		
		Button button_about_help = (Button)findViewById(R.id.button_about_help);
		Button button_about_attention = (Button)findViewById(R.id.button_about_attention);
		Button button_about_userProtocol = (Button)findViewById(R.id.button_about_userProtocol);

		button_about_help.setOnClickListener(this);
		button_about_attention.setOnClickListener(this);
		button_about_userProtocol.setOnClickListener(this);
	}

	
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.button_about_attention:
				new AlertDialog.Builder(AboutActivity.this).setTitle("选择关注账号").setSingleChoiceItems(new String[]{"QQ号","微信号"}, -1, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						String account = "";
						switch (which) {
						case 0:
							try {
								startAPP("com.tencent.mobileqq");
							} catch (Exception e) {
								// TODO: handle exception
								showTip("您尚未安装QQ");
								return;
							}
							account = QQ_ACCOUNT;
							break;

						case 1:
							try {
								startAPP("com.tencent.mm");
							} catch (Exception e) {
								// TODO: handle exception
								showTip("您尚未安装微信");
								return;
							}
							account = WX_ACCOUNT;
							break;
							
						default:
							break;
						}
						if (android.os.Build.VERSION.SDK_INT > 11) {
							android.content.ClipboardManager c = (android.content.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
							c.setPrimaryClip(ClipData.newPlainText("VoiceControl",account));
						} else {
							android.text.ClipboardManager c = (android.text.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
							c.setText(account);
						}
						showTip("复制链接成功");
						dialog.dismiss();
					}
				}).create().show();
				break;

			case R.id.button_about_help:
                startActivity(new Intent(AboutActivity.this,ShowHelpActivity.class));
				break;

			case R.id.button_about_userProtocol:

				break;
				
			default:
				break;
			}
	}

	/**
	 *
	 * @param packageName
	 */
	private void startAPP(String packageName) {
		PackageManager pm = getPackageManager();
		Intent i = new Intent(Intent.ACTION_MAIN);
		i = pm.getLaunchIntentForPackage(packageName);
		i.addCategory(Intent.CATEGORY_LAUNCHER);
		startActivity(i);
	}
	@Override
	public void onViewChange(Message msg) {
		// TODO Auto-generated method stub

	}
	
}
