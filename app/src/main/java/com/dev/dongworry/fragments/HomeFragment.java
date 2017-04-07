package com.dev.dongworry.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import com.dev.dongworry.R;
import com.dev.dongworry.customview.NestGridView;
import com.dev.dongworry.utils.ViewFactory;


/**
 * 显示国家和地区的UI片段，可复用
 * @author 邓耀宁
 *
 */
public class HomeFragment extends BaseFragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		NestGridView gv_home_service = (NestGridView)view.findViewById(R.id.gv_home_service);
		String[] names = getActivity().getResources().getStringArray(R.array.label_home);
		int[] icons = {R.drawable.icon_family_education,
				R.drawable.icon_maintain,
				R.drawable.icon_home_marking,
				R.drawable.icon_fitment,
				R.drawable.icon_beauty,
				R.drawable.icon_health_care,
				R.drawable.icon_amusement,
				R.drawable.icon_old_man,
				R.drawable.icon_all };
		final ListAdapter adapter = (ListAdapter) ViewFactory.getInstance().getSimpleGridViewAdapter(getActivity(), names, icons);
		gv_home_service.setAdapter(adapter);
		gv_home_service.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

			}
		});
		return view;
	}
}
