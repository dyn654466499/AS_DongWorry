package com.dev.dongworry.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SmartSortAdapter extends ArrayAdapter<String>{
    private int click_position = 0,resource;
	
	public SmartSortAdapter(Context context, int resource, String[] objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.resource = resource;
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return super.getItem(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return super.getItemId(position);
	}

	@Override
	public int getPosition(String item) {
		// TODO Auto-generated method stub
		return super.getPosition(item);
	}

	public void setSelectedPosition(int position){
		click_position = position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null){
			convertView = LayoutInflater.from(getContext()).inflate(resource, parent,false);
		}
		TextView textView = (TextView)convertView;
		textView.setText(getItem(position));
		if(click_position == position){
			textView.setTextColor(Color.RED);
			textView.setBackgroundColor(Color.rgb(244, 244, 244));
		}else{
			textView.setTextColor(Color.rgb(144, 144,144));
			textView.setBackgroundColor(Color.WHITE);
		}
		return textView;
	}
}
