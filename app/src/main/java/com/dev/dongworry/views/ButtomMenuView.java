package com.dev.dongworry.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dev.dongworry.R;

public class ButtomMenuView extends LinearLayout implements View.OnClickListener {
	// 菜单栏 流量医生，淘流量，我的 TextView 和布局
	private TextView tv_menu_doctor, tv_menu_flow, tv_menu_me;
	private LinearLayout lay_menu_doctor, lay_menu_flow, lay_menu_me;

	/**
	 * 各个textview按钮位置，设置背景需要
	 */
	private final static int DOCTOR_POSITION = 1;
	private final static int FLOW_POSITION = 2;
	private final static int ME_POSITION = 3;

	// 点击事件接口
	private MenuOnclickInterface menuOnclickInterface;

	public ButtomMenuView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		iniView();
	}

	public ButtomMenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		iniView();
	}

	public ButtomMenuView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		iniView();
	}

	/**
	 * 初始化view
	 */
	private void iniView() {
		// 加载view
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.buttom_menu_view, this);

		findViewId(view);

		setTextviewBg(DOCTOR_POSITION);
	}

	/**
	 * view 绑定
	 */
	private void findViewId(View view) {

		tv_menu_doctor = (TextView) view.findViewById(R.id.tv_home_menu_doctor);
		tv_menu_flow = (TextView) view.findViewById(R.id.tv_home_menu_flow);
		tv_menu_me = (TextView) view.findViewById(R.id.tv_home_menu_me);

		lay_menu_doctor = (LinearLayout) findViewById(R.id.lay_home_menu_doctor);
		lay_menu_flow = (LinearLayout) findViewById(R.id.lay_home_menu_flow);
		lay_menu_me = (LinearLayout) findViewById(R.id.lay_home_menu_me);

		// 布局按钮点击监听
		lay_menu_doctor.setOnClickListener(this);
		lay_menu_flow.setOnClickListener(this);
		lay_menu_me.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

		switch (arg0.getId()) {
		case R.id.lay_home_menu_doctor:
			setTextviewBg(DOCTOR_POSITION);
			
			//点击回调
			if(menuOnclickInterface!=null)
			{
				menuOnclickInterface.onclickDoctorLay();
			}
			break;
		case R.id.lay_home_menu_flow:
			setTextviewBg(FLOW_POSITION);
			//点击回调
			if(menuOnclickInterface!=null)
			{
				menuOnclickInterface.onclickFlowLay();
			}
			break;
		case R.id.lay_home_menu_me:
			setTextviewBg(ME_POSITION);
			//点击回调
			if(menuOnclickInterface!=null)
			{
				menuOnclickInterface.onclickMeLay();
			}
			break;

		default:
			break;
		}
	}

	/**
	 * 
	 * @param position
	 */
	private void setTextviewBg(int position) {
		switch (position) {
		case DOCTOR_POSITION:
			tv_menu_doctor.setTextColor(getContext().getResources().getColor(R.color.green));
			tv_menu_flow.setTextColor(getContext().getResources().getColor(R.color.gray));
			tv_menu_me.setTextColor(getContext().getResources().getColor(R.color.gray));

			break;

		case FLOW_POSITION:
			tv_menu_doctor.setTextColor(getContext().getResources().getColor(R.color.gray));
			tv_menu_flow.setTextColor(getContext().getResources().getColor(R.color.green));
			tv_menu_me.setTextColor(getContext().getResources().getColor(R.color.gray));
			break;
		case ME_POSITION:
			tv_menu_doctor.setTextColor(getContext().getResources().getColor(R.color.gray));
			tv_menu_flow.setTextColor(getContext().getResources().getColor(R.color.gray));
			tv_menu_me.setTextColor(getContext().getResources().getColor(R.color.green));
			break;

		default:
			break;
		}
	}
	

	public void setMenuOnclickInterface(MenuOnclickInterface menuOnclickInterface) {
		this.menuOnclickInterface = menuOnclickInterface;
	}


	/**
	 * 菜单按钮点击接口
	 * 
	 * @author ming
	 *
	 */
	public interface MenuOnclickInterface {
		/**
		 * 流量医生点击事件
		 */
		public void onclickDoctorLay();

		/**
		 * 淘流量点击事件
		 */
		public void onclickFlowLay();

		/**
		 * 个人点击事件
		 */
		public void onclickMeLay();
	}

}
