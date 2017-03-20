package com.dev.dongworry.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SlidingDrawer;
import android.widget.TextView;

import com.dev.dongworry.R;
import com.dev.dongworry.customview.CButton;
import com.dev.dongworry.models.LoginModel;

import java.util.List;
import java.util.Map;

/**
 * “登录界面”的Activity
 * @author 邓耀宁
 *
 */
public class LoginActivity extends BaseActivity {
	private Button button_forgetPWD,button_login_register,button_more_loginOptions;
	private CButton button_sureLogin;
	private EditText editText_phone,editText_pwd;
	private SlidingDrawer drawer;

	private GridView gridView_login;
	private List<Map<String, Object>> login_data_list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
	}

	
	public void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_login);

		setModelDelegate(new LoginModel(handler));
		setViewChangeListener(this);

		TextView tv_title = (TextView)findViewById(R.id.tv_common_title);
		tv_title.setText(getText(R.string.login));
		editText_phone = (EditText) findViewById(R.id.editText_phone);
		editText_pwd = (EditText)findViewById(R.id.editText_password);
		
		button_sureLogin = (CButton)findViewById(R.id.button_sureLogin);
		button_forgetPWD = (Button)findViewById(R.id.button_forgetPWD);
		button_login_register = (Button)findViewById(R.id.button_login_register);
//		button_more_loginOptions = (Button) mActivity
//				.findViewById(R.id.button_more_loginOptions);

		button_sureLogin.setOnClickListener(this);
		button_forgetPWD.setOnClickListener(this);
		button_login_register.setOnClickListener(this);
//		button_more_loginOptions.setOnClickListener(this);

//		drawer = (SlidingDrawer) findViewById(R.id.slidingDrawer);
//		drawer.setOnDrawerOpenListener(new OnDrawerOpenListener() {
//			@Override
//			public void onDrawerOpened() {
//				Drawable drawable = getResources().getDrawable(
//						R.drawable.expander_open_holo_light);
//				drawable.setBounds(0, 0, drawable.getMinimumWidth(),
//						drawable.getMinimumHeight());
//				button_more_loginOptions.setCompoundDrawables(drawable, null, null, null);
//			}
//		});
//		drawer.setOnDrawerCloseListener(new OnDrawerCloseListener() {
//			@Override
//			public void onDrawerClosed() {
//				Drawable drawable = getResources().getDrawable(
//						R.drawable.expander_close_holo_light);
//				drawable.setBounds(0, 0, drawable.getMinimumWidth(),
//						drawable.getMinimumHeight());
//				button_more_loginOptions.setCompoundDrawables(drawable, null, null, null);
//			}
//		});

//		/**
//		 * 显示第三方登录
//		 */
//		gridView_login = (GridView) findViewById(R.id.gridView_slide);
//        //新建List
//        login_data_list = new ArrayList<Map<String, Object>>();
//        int[] icon = { R.drawable.login_qq,R.drawable.login_weixin,R.drawable.login_renren,
//        		R.drawable.login_tencentqq,R.drawable.login_weibo};
//        String[] iconName = { getString(R.string.login_qq),
//        		              getString(R.string.login_weixin),
//        		              getString(R.string.login_renren),
//        		              getString(R.string.login_tencentWeibo),
//        		              getString(R.string.login_sinaWeibo),
//        		              };
//        //获取数据
//        for(int i=0;i<icon.length;i++){
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("image", icon[i]);
//            map.put("text", iconName[i]);
//            login_data_list.add(map);
//        }
//        //新建适配器
//        String [] from ={"image","text"};
//        int [] to = {R.id.third_login_imageView,R.id.third_login_textView};
//        SimpleAdapter simpleAdapter = new SimpleAdapter(mActivity, login_data_list, R.layout.third_login_item, from, to);
//        //配置适配器
//        gridView_login.setAdapter(simpleAdapter);
//        //去掉item被点击时的效果
//        gridView_login.setSelector(new ColorDrawable(Color.TRANSPARENT));
//        gridView_login.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View view, int position,
//					long arg3) {
//				// TODO Auto-generated method stub
//			}
//
//		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.button_forgetPWD:
				startActivity(new Intent(this,FindPWDActivity.class));
				break;

			case R.id.button_login_register:
				startActivity(new Intent(this,RegisterActivity.class));
				break;
			default:
				break;
		}
	}

	@Override
	public void onViewChange(Message msg) {

	}

}
