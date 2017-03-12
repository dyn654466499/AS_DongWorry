package com.dev.dongworry.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.dev.dongworry.utils.ImageUtil;
import com.dev.dongworry.R;
import com.tencent.open.utils.ApkExternalInfoTool;

import static com.dev.dongworry.consts.Constants.*;
/**
 *  邀请好友界面
 *  @author 邓耀宁
 */
public class InviteFriendActivity extends BaseActivity {

	private Button button_invite;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invite);

		setTitle(getString(R.string.title_inviteFriends));
		setViewChangeListener(this);

		button_invite = (Button)findViewById(R.id.button_invite);

		button_invite.setOnClickListener(this);

		WindowManager wm = this.getWindowManager();
	    int width = wm.getDefaultDisplay().getWidth()/2;
	    
		ImageView imageView_share_QRCode = (ImageView)findViewById(R.id.imageView_invite_QRCode);
		imageView_share_QRCode.setImageBitmap(ImageUtil.createQRImage(APK_MOBILE_DOWNLOAD_URL,width, width));
	}
	
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.button_invite:
//				Intent sendIntent = new Intent();
//				sendIntent.setAction(Intent.ACTION_SEND);
//				sendIntent.setType("text/*");
//				sendIntent.putExtra(Intent.EXTRA_TEXT, "www.xxx.com");
//				startActivity(sendIntent);
				startActivity(new Intent(InviteFriendActivity.this, ShareActivity.class));
				overridePendingTransition(0, 0);
				break;
				
			default:
				break;
			}
		}

		@Override
		public void onViewChange(Message msg) {
			// TODO Auto-generated method stub
			
		}
		
}
