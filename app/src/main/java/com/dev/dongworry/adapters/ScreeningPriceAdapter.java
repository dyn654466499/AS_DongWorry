package com.dev.dongworry.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dev.dongworry.R;

@SuppressLint("NewApi")
public class ScreeningPriceAdapter extends ArrayAdapter<String> {
	private String[] names;
	private int click_position = 0;
	private int resource;

	public ScreeningPriceAdapter(Context context, int resource, String[] objects) {
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

	public void setSelectedPosition(int position) {
		click_position = position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(resource,
					parent, false);
		}
		TextView textView = (TextView) convertView;
		textView.setText(getItem(position));
		if (click_position == position) {
			Drawable comment_impress_good_checked = getContext().getResources()
					.getDrawable(R.drawable.comment_impress_good_checked);
			textView.setBackground(comment_impress_good_checked);
			textView.setTextColor(Color.RED);
		} else {
			textView.setBackground(null);
			textView.setTextColor(Color.rgb(114, 114, 114));
		}
		return textView;
	}

}
