package com.dev.dongworry.adapters.city;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dev.dongworry.R;
import com.dev.dongworry.beans.city.City;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author dengyaoning420
 */
public class CitySelectAdapter extends BaseAdapter{
	private List<City> list = null;
	private List<City> originList = null;
	private Context	mContext;

	public CitySelectAdapter(Context mContext, List<City> list) {
		this.mContext = mContext;
		this.list = list;
		originList = new ArrayList<>();
		originList.addAll(list);
	}

	public void update(List<City> list){
		this.list = list;
		notifyDataSetChanged();
	}

	public int getCount() {
		return this.list.size();
	}

	public Object getItem(int position) {
		return this.list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder;
		final City mContent = list.get(position);
		if(view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.adapter_city_select, null);
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
			viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
			viewHolder.line = view.findViewById(R.id.line);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		String section = getSectionForPosition(position);

		if(position == getPositionForSection(section)) {
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setText(mContent.getLetter());
			viewHolder.line.setVisibility(View.VISIBLE);
		} else {
			viewHolder.tvLetter.setVisibility(View.GONE);
			viewHolder.line.setVisibility(View.GONE);
		}

		viewHolder.tvTitle.setText(list.get(position).getCityname());

		return view;

	}

	private static class ViewHolder {
		TextView tvLetter;
		TextView tvTitle;
		View line;
	}

	public String getSectionForPosition(int position) {
		String letter = list.get(position).getLetter();
		if(!TextUtils.isEmpty(letter)){
			return letter.substring(0,1);
		}
		return "";
	}

	public int getPositionForSection(String section) {
		//section可能不止一个字符,取第一个字符判断
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getLetter();
			if(!TextUtils.isEmpty(sortStr)) {
				if(sortStr.substring(0,1).toUpperCase().equals(section)) {
					return i;
				}
			}
		}

		return -1;
	}
}
