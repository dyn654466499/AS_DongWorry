package com.dev.dongworry.fragments.mine;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.dev.dongworry.R;
import com.dev.dongworry.activities.SettingActivity;
import com.dev.dongworry.consts.login.LoginEvent;
import com.dev.dongworry.customview.CButton;
import com.dev.dongworry.customview.RoundImageView;
import com.dev.dongworry.fragments.BaseFragment;
import com.dev.dongworry.managers.login.LoginManager;
import com.dev.dongworry.utils.CommonUtils;
import com.dev.dongworry.utils.ViewFactory;

import de.greenrobot.event.EventBus;

import static android.view.View.GONE;
import static com.dev.dongworry.R.id.img_user_image;
import static com.dev.dongworry.R.id.tv_usercenter_name;

public class UserCenterFragment extends BaseFragment implements View.OnClickListener{
	private boolean isLogin = false;
	private LinearLayout llayout_usercenter_own;
	private View rootView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		isLogin = LoginManager.getInstance().isLogin();
		EventBus.getDefault().register(this);
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_user_center, container, false);
		rootView = view;
		view.findViewById(R.id.tv_usercenter_auth).setOnClickListener(this);
		view.findViewById(R.id.tv_usercenter_setting).setOnClickListener(this);
		view.findViewById(R.id.tv_usercenter_task).setOnClickListener(this);
		view.findViewById(R.id.tv_usercenter_feedBack).setOnClickListener(this);
		view.findViewById(R.id.tv_usercenter_makeMoney).setOnClickListener(this);
		view.findViewById(R.id.tv_usercenter_securityTrading).setOnClickListener(this);

		llayout_usercenter_own = (LinearLayout)view.findViewById(R.id.llayout_usercenter_own);
		if(isLogin){
			setLoginViewState();
		}else{
			setUnLoginViewState();
		}
		rootView = view;
		return rootView;
	}

	@Override
	public void onClick(View v) {
		if(!isLogin){
			LoginManager.getInstance().login(getActivity());
			return;
		}
		Intent intent;
		switch (v.getId()){
			case R.id.tv_usercenter_auth:

				break;
			case R.id.tv_usercenter_login:

				break;
			case R.id.tv_usercenter_setting:
				intent = new Intent(getActivity(), SettingActivity.class);
				getActivity().startActivity(intent);
				break;
			case R.id.tv_usercenter_task:

				break;
			case R.id.tv_usercenter_feedBack:

				break;
			case R.id.tv_usercenter_makeMoney:

				break;
			case R.id.tv_usercenter_securityTrading:

				break;
			case R.id.btn_usercenter_logout:
				new AlertDialog.Builder(getActivity())
						.setTitle("是否要退出登录")
						.setNegativeButton("确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								LoginManager.getInstance().logout();
								EventBus.getDefault().post(Message.obtain(null,LoginEvent.DO_LOGOUT));
							}
						})
						.setPositiveButton("取消", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.cancel();
							}
						})
						.create().show();

				break;

			case R.id.img_user_image:

				break;

			default:
				break;
		}
	}

	public void onEventMainThread(Message msg){
		switch (msg.what){
			case LoginEvent.DO_LOGIN:
				isLogin = true;
				setLoginViewState();
				break;

			case LoginEvent.DO_LOGOUT:
				isLogin = false;
				setUnLoginViewState();
				break;
		}
	}

	private void setLoginViewState(){
		llayout_usercenter_own.setVisibility(View.VISIBLE);
		GridView gv_usercenter = (GridView) rootView.findViewById(R.id.gv_usercenter_own);
		String[] tops = {"300", "100", "10.0元"};
		String[] bottoms = {"订单", "金币", "余额"};
		gv_usercenter.setAdapter((ListAdapter) ViewFactory.getInstance().getTextGridViewAdapter(getActivity(), tops, bottoms));

		TextView tv_usercenter_login = (TextView) rootView.findViewById(R.id.tv_usercenter_login);
		tv_usercenter_login.setVisibility(GONE);

		CButton btn_usercenter_logout = (CButton) rootView.findViewById(R.id.btn_usercenter_logout);
		btn_usercenter_logout.setOnClickListener(this);
		btn_usercenter_logout.setVisibility(View.VISIBLE);

		RoundImageView img_user_image = (RoundImageView)rootView.findViewById(R.id.img_user_image);
		img_user_image.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.me));
		img_user_image.setOnClickListener(this);

		LinearLayout llayout_usercenter_info = (LinearLayout)rootView.findViewById(R.id.llayout_usercenter_info);
		llayout_usercenter_info.setVisibility(View.VISIBLE);
		TextView tv_usercenter_name = (TextView) rootView.findViewById(R.id.tv_usercenter_name);
		tv_usercenter_name.setText(LoginManager.getInstance().getUserInfo().userName);
		TextView tv_usercenter_phone = (TextView) rootView.findViewById(R.id.tv_usercenter_phone);
		tv_usercenter_phone.setText(CommonUtils.formatPhoneNum(LoginManager.getInstance().getUserInfo().phoneNum));
		TextView tv_usercenter_credibility = (TextView) rootView.findViewById(R.id.tv_usercenter_credibility);
		tv_usercenter_credibility.setText("9");
	}

	private void setUnLoginViewState(){
		llayout_usercenter_own.setVisibility(GONE);

		CButton btn_usercenter_logout = (CButton) rootView.findViewById(R.id.btn_usercenter_logout);
		btn_usercenter_logout.setVisibility(GONE);

		TextView tv_usercenter_login = (TextView) rootView.findViewById(R.id.tv_usercenter_login);
		tv_usercenter_login.setOnClickListener(this);
		tv_usercenter_login.setVisibility(View.VISIBLE);

		LinearLayout llayout_usercenter_info = (LinearLayout)rootView.findViewById(R.id.llayout_usercenter_info);
		llayout_usercenter_info.setVisibility(View.GONE);

		RoundImageView img_user_image = (RoundImageView)rootView.findViewById(R.id.img_user_image);
		img_user_image.setOnClickListener(this);
		img_user_image.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.user_default_image));
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
}
