package com.dev.dongworry.views;

import android.app.Activity;
import android.graphics.Color;
import android.view.Window;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

import com.dev.dongworry.R;
import com.dev.dongworry.utils.CommonUtils;

public class EvaluateView extends BaseView{
    private RatingBar ratingBar_evaluate_whole;
	private TextView textView_evaluate_wholeNum,textView_evaluate_totalPrice,textView_evaluate_goodsNum;
	
	public EvaluateView(Activity mActivity) {
		super(mActivity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		mActivity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mActivity.setContentView(R.layout.activity_evaluate);
		
		textView_evaluate_wholeNum = (TextView)mActivity.findViewById(R.id.textView_evaluate_wholeNum);
		textView_evaluate_totalPrice = (TextView)mActivity.findViewById(R.id.textView_evaluate_totalPrice);
		textView_evaluate_goodsNum = (TextView)mActivity.findViewById(R.id.textView_evaluate_goodsNum);
		
		/**
		 * 监听ratingBar，改变分数
		 */
		ratingBar_evaluate_whole = (RatingBar)mActivity.findViewById(R.id.ratingBar_evaluate_whole);
		ratingBar_evaluate_whole.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				// TODO Auto-generated method stub
				textView_evaluate_wholeNum.setText(String.format(getString(R.string.score), rating));
			}
		});
		
		/**
		 * 显示并改变总价和数量的数字为红色。
		 */
		String totalPrice = String.format(getString(R.string.evaluate_totalPrice), "50");
		textView_evaluate_totalPrice.setText(CommonUtils.setFontType(totalPrice,3,totalPrice.length(),Color.RED,18));
		String evaluate_goodsNum = String.format(getString(R.string.evaluate_goodsNum), "1");
		textView_evaluate_goodsNum.setText(CommonUtils.setFontType(evaluate_goodsNum,3,evaluate_goodsNum.length(),Color.RED,18));
	}
	
}
