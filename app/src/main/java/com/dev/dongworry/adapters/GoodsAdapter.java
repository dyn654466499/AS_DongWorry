package com.dev.dongworry.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.dongworry.R;

public class GoodsAdapter extends BaseAdapter{
    private Context context;
    
	public GoodsAdapter(Context context) {
		super();
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 10;
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
		if(convertView == null){
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.goods_item, null, false);
			holder.imageView_goodsIcon = (ImageView)convertView.findViewById(R.id.imageView_goodsIcon);
			holder.imageView_goodsTimedSpecials = (ImageView)convertView.findViewById(R.id.imageView_goodsTimedSpecials);
			holder.textView_goodsTitle = (TextView)convertView.findViewById(R.id.textView_goodsTitle);
			holder.textView_goodsDes = (TextView)convertView.findViewById(R.id.textView_goodsDes);
			holder.textView_goodsReducePrice = (TextView)convertView.findViewById(R.id.textView_goodsReducePrice);
			holder.textView_goodsPrimaryPrice = (TextView)convertView.findViewById(R.id.textView_goodsPrimaryPrice);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		
		
		
		return convertView;
	}
	
	static class ViewHolder{
		ImageView imageView_goodsIcon,imageView_goodsTimedSpecials;
		TextView textView_goodsTitle,textView_goodsDes,textView_goodsReducePrice,textView_goodsPrimaryPrice;
	}

}
