package com.dev.dongworry.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dev.dongworry.R;


public class ScreeningCheckBoxAdapter extends BaseAdapter{
    private Context context;
    private String[] names;
    private int click_position = -1;
	
	public ScreeningCheckBoxAdapter(Context context,String[] names) {
		super();
		this.context = context;
		this.names = names;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return names.length;
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return names[position];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
    
	public void setSelectedPosition(int position){
		click_position = position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {
		// TODO Auto-generated method stub
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.category_screening_checkbox, viewGroup,false);
		}
		TextView textView = (TextView)convertView;
		textView.setText(getItem(position));
		if(click_position == position){
			if("checked".equals(textView.getTag())){
				Log.e("", "set uncheck");
				textView.setTag("uncheck");
				Drawable btn_check_off = context.getResources().getDrawable(R.drawable.btn_check_off);
				btn_check_off.setBounds(0, 0, btn_check_off.getMinimumWidth(), btn_check_off.getMinimumWidth());
		        textView.setCompoundDrawables(btn_check_off, null, null, null);
			}else{
				Log.e("", "set checked");
				textView.setTag("checked");
				Drawable btn_check_on = context.getResources().getDrawable(R.drawable.btn_check_on);
				btn_check_on.setBounds(0, 0, btn_check_on.getMinimumWidth(), btn_check_on.getMinimumWidth());
		        textView.setCompoundDrawables(btn_check_on, null, null, null);
			}
		}
		return textView;
	}

}
