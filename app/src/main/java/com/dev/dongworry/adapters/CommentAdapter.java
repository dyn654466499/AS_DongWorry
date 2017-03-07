package com.dev.dongworry.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

import com.dev.dongworry.R;

public class CommentAdapter extends BaseAdapter {
	private Context context;
    private OnRatingBarChangeListener barChangeListener;
    
	public CommentAdapter(Context context) {
		super();
		this.context = context;
		barChangeListener = new OnRatingBarChangeListener()  
        {  
            //当拖动条的滑块位置发生改变时触发该方法  
            @Override  
            public void onRatingChanged(RatingBar arg0  
                , float rating, boolean fromUser)  
            {  
                //动态改变图片的透明度，其中255是星级评分条的最大值，  
                //5个星星就代表最大值255  
                //image.setAlpha((int)(rating * 255 / 5));  
            }  
        };
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.goods_comment_item, null, false);
			holder.ratingBar = (RatingBar)convertView.findViewById(R.id.ratingBar_comment_list);
			holder.textView_comment_list_user = (TextView) convertView
					.findViewById(R.id.textView_comment_list_user);
			holder.textView_comment_list_date = (TextView) convertView
					.findViewById(R.id.textView_comment_list_date);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		return convertView;
	}

	static class ViewHolder {
		RatingBar ratingBar;
		TextView textView_comment_list_user, textView_comment_list_date;
	}
}
