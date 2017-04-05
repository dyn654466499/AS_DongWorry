package com.dev.dongworry.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dev.dongworry.R;
import com.dev.dongworry.activities.CityActivity;
import com.dev.dongworry.activities.SearchActivity;
import com.dev.dongworry.customview.CustomEditText;


/**
 * 显示国家和地区的UI片段，可复用
 * @author 邓耀宁
 *
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener{
    private PopupWindow popupWindow;
	private TextView tv_home_localCity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_home, container, false);

		CustomEditText et_home_search = (CustomEditText)view.findViewById(R.id.et_home_search);
		et_home_search.setOnClickListener(this);

		ImageButton imageBtn_home_more = (ImageButton)view.findViewById(R.id.imageBtn_home_more);
		imageBtn_home_more.setOnClickListener(this);

		popupWindow = createMenuPopupWindow(getActivity());

		return view;
	}

	/**
	 * 生成菜单popupwindow
	 * @return PopupWindow实例
	 */
	private PopupWindow createMenuPopupWindow(Activity activity){
		ListView listView = new ListView(activity);
		listView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		listView.setBackgroundColor(getColor(R.color.themeColor));
		listView.setAlpha(0.8f);

		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int mScreenWidth = dm.widthPixels;// 获取屏幕分辨率宽度

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, R.layout.item_textview, new String[]{"扫一扫","收款","付款","帮助与反馈"});
		listView.setAdapter(adapter);
		final PopupWindow popupWindow = new PopupWindow(listView,mScreenWidth / 4, ViewGroup.LayoutParams.WRAP_CONTENT, false);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
					case 0:

						break;
					case 1:

						break;
					case 2:

						break;
					case 3:

						break;
					default:
						break;
				}
				/**
				 * 延迟popupwindow消失
				 */
				view.postDelayed(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						popupWindow.dismiss();
					}
				}, 100);
			}
		});
		popupWindow.setOutsideTouchable(true);
		popupWindow.setFocusable(true);
		/**
		 * 必须设置drawable
		 */
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		return popupWindow;
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()){
			case R.id.et_home_search:
				intent = new Intent(getActivity(), SearchActivity.class);
				getActivity().startActivity(intent);
				break;
			case R.id.imageBtn_home_more:
				if(popupWindow == null){
					popupWindow = createMenuPopupWindow(getActivity());
				}
				popupWindow.showAsDropDown(v, 999, 10);
				break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode){
			case 0:
				if(data != null){
					String cityname = data.getStringExtra("cityname");
					if(tv_home_localCity != null && cityname != null) {
						tv_home_localCity.setText(cityname + " ");
					}
				}
				break;
		}
	}
}
