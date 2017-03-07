package com.dev.dongworry.views;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;

import com.dev.dongworry.R;
import com.dev.dongworry.activities.FindPWDActivity;
import com.dev.dongworry.activities.RegisterActivity;
import com.dev.dongworry.controlers.BaseControler;
import com.dev.dongworry.controlers.LoginControler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginView extends BaseView implements BaseControler.ViewChangeListener{

	private Button button_sureLogin,button_forgetPWD,button_login_register,button_more_loginOptions;
	private EditText editText_phone,editText_pwd;
	private LoginControler loginControler;
	
	private SlidingDrawer drawer;
	
    private GridView gridView_login;
    private List<Map<String, Object>> login_data_list;


    
	public LoginView(Activity mActivity) {
		super(mActivity);
		// TODO Auto-generated constructor stub
		loginControler = new LoginControler(mActivity);
		loginControler.setViewChangeListener(this);
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		mActivity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mActivity.setContentView(R.layout.activity_login);

		editText_phone = (EditText) mActivity.findViewById(R.id.editText_phone);
		editText_pwd = (EditText) mActivity
				.findViewById(R.id.editText_password);

		ButtonListener buttonListener = new ButtonListener();
		button_sureLogin = (Button) mActivity
				.findViewById(R.id.button_sureLogin);
		button_forgetPWD = (Button) mActivity
				.findViewById(R.id.button_forgetPWD);
		button_login_register = (Button) mActivity
				.findViewById(R.id.button_login_register);
		button_more_loginOptions = (Button) mActivity
				.findViewById(R.id.button_more_loginOptions);

		button_sureLogin.setOnClickListener(buttonListener);
		button_forgetPWD.setOnClickListener(buttonListener);
		button_login_register.setOnClickListener(buttonListener);
		button_more_loginOptions.setOnClickListener(buttonListener);
		
		button_sureLogin.setOnTouchListener(new OnTouchListener() {
		    private float primary_scaleX ;
		    private float primary_scaleY ;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					primary_scaleX = v.getScaleX();
					primary_scaleY = v.getScaleY();
                    v.setScaleX((float) (0.95*primary_scaleX));
                    v.setScaleY((float) (0.95*primary_scaleY));
					return true;
				case MotionEvent.ACTION_UP:
					v.setScaleX(primary_scaleX);
                    v.setScaleY(primary_scaleY);
                    String phoneNum = editText_phone.getText().toString();
                    String password = editText_pwd.getText().toString();
					return true;
				default:
					break;
				}
				return false;
			}
		});

		drawer = (SlidingDrawer) mActivity.findViewById(R.id.slidingDrawer);
		drawer.setOnDrawerOpenListener(new OnDrawerOpenListener() {
			@Override
			public void onDrawerOpened() {
				Drawable drawable = mActivity.getResources().getDrawable(
						R.drawable.expander_open_holo_light);
				drawable.setBounds(0, 0, drawable.getMinimumWidth(),
						drawable.getMinimumHeight());
				button_more_loginOptions.setCompoundDrawables(drawable, null, null, null);
			}
		});
		drawer.setOnDrawerCloseListener(new OnDrawerCloseListener() {
			@Override
			public void onDrawerClosed() {
				Drawable drawable = mActivity.getResources().getDrawable(
						R.drawable.expander_close_holo_light);
				drawable.setBounds(0, 0, drawable.getMinimumWidth(),
						drawable.getMinimumHeight());
				button_more_loginOptions.setCompoundDrawables(drawable, null, null, null);
			}
		});
		
		/**
		 * 显示第三方登录
		 */
		gridView_login = (GridView) mActivity.findViewById(R.id.gridView_slide);
        //新建List
        login_data_list = new ArrayList<Map<String, Object>>();
        int[] icon = { R.drawable.login_qq,R.drawable.login_weixin,R.drawable.login_renren,
        		R.drawable.login_tencentqq,R.drawable.login_weibo};
        String[] iconName = { getString(R.string.login_qq), 
        		              getString(R.string.login_weixin), 
        		              getString(R.string.login_renren), 
        		              getString(R.string.login_tencentWeibo), 
        		              getString(R.string.login_sinaWeibo),
        		              };
        //获取数据
        for(int i=0;i<icon.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            login_data_list.add(map);
        }
        //新建适配器
        String [] from ={"image","text"};
        int [] to = {R.id.third_login_imageView,R.id.third_login_textView};
        SimpleAdapter simpleAdapter = new SimpleAdapter(mActivity, login_data_list, R.layout.third_login_item, from, to);
        //配置适配器
        gridView_login.setAdapter(simpleAdapter);
        //去掉item被点击时的效果
        gridView_login.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridView_login.setOnItemClickListener(new OnItemClickListener() {
        	
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
				// TODO Auto-generated method stub
			}
			
		});
	}

	class ButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.button_forgetPWD:
                mActivity.startActivity(new Intent(mActivity,FindPWDActivity.class));
				break;
				
			case R.id.button_login_register:
                mActivity.startActivity(new Intent(mActivity,RegisterActivity.class));
                finish();
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
